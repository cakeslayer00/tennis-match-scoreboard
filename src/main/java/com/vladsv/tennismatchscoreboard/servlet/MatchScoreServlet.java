package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dao.PlayerDao;
import com.vladsv.tennismatchscoreboard.dto.MatchScoreViewDto;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.service.FinishedMatchService;
import com.vladsv.tennismatchscoreboard.service.MatchScoreCalculationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private PlayerDao playerDao;
    private OngoingMatchDao ongoingMatchDao;

    private MatchScoreCalculationService calculationService;
    private FinishedMatchService finishedMatchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("hibernateSessionFactory");

        FinishedMatchDao finishedMatchDao = new FinishedMatchDao(sessionFactory);
        playerDao = new PlayerDao(sessionFactory);
        ongoingMatchDao = (OngoingMatchDao) getServletContext()
                .getAttribute("ongoingMatchDao");

        calculationService = new MatchScoreCalculationService();
        finishedMatchService = new FinishedMatchService(finishedMatchDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID uuid = UUID.fromString(req.getParameter("uuid"));
            OngoingMatch ongoingMatch = ongoingMatchDao.findById(uuid).orElseThrow(
                    () -> new RuntimeException("No ongoing Match found")
            );

            MatchScoreViewDto matchScoreViewDto = MatchScoreViewDto.builder()
                    .firstPlayerName(playerDao.findById(ongoingMatch.getFirstPlayerId()).orElseThrow().getName())
                    .secondPlayerName(playerDao.findById(ongoingMatch.getSecondPlayerId()).orElseThrow().getName())
                    .firstPlayerScore(ongoingMatch.getScore().getPlayerScoreMap().get("1"))
                    .secondPlayerScore(ongoingMatch.getScore().getPlayerScoreMap().get("2"))
                    .isTieBreak(calculationService.isTieBreak(ongoingMatch.getScore()))
                    .build();

            req.setAttribute("matchScoreDto", matchScoreViewDto);
            req.setAttribute("uuid", uuid);
            req.getRequestDispatcher("WEB-INF/jsp/match.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            //TODO: invoke some view to show an error
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        String winnerId = req.getParameter("winnerId");

        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new RuntimeException("No ongoing Match found")
        );

        calculationService.updateMatchState(ongoingMatch);
        calculationService.updateScore(ongoingMatch, winnerId);

        if (calculationService.isMatchFinished(ongoingMatch)) {
            finishedMatchService.proceedMatch(ongoingMatch);
            ongoingMatchDao.delete(matchId);
        }

        resp.sendRedirect("/match-score?uuid=" + matchId);
    }

}
