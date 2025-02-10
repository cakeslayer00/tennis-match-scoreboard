package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.dao.impl.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dto.OngoingMatchViewDto;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.service.FinishedMatchService;
import com.vladsv.tennismatchscoreboard.service.MatchScoreCalculationService;
import com.vladsv.tennismatchscoreboard.utils.Validator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private MatchScoreCalculationService calculationService;
    private FinishedMatchService finishedMatchService;

    private OngoingMatchDao ongoingMatchDao;

    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory = (SessionFactory)
                getServletContext().getAttribute("hibernateSessionFactory");

        FinishedMatchDao finishedMatchDao = new FinishedMatchDao(sessionFactory);
        ongoingMatchDao = (OngoingMatchDao)
                getServletContext().getAttribute("ongoingMatchDao");

        calculationService = new MatchScoreCalculationService();
        finishedMatchService = new FinishedMatchService(finishedMatchDao);

        validator = new Validator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            UUID uuid = UUID.fromString(req.getParameter("uuid"));
            OngoingMatch ongoingMatch = ongoingMatchDao.findById(uuid).orElseThrow(
                    () -> new IllegalArgumentException("Match with current id doesn't exist")
            );

            req.setAttribute("ongoingMatch", ongoingMatch);
            req.setAttribute("uuid", uuid);
            req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            UUID matchId = UUID.fromString(req.getParameter("uuid"));
            String winnerId = validator.getValidatedWinnerId(req.getParameter("winnerId"));

            OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                    () -> new RuntimeException("Match with current id doesn't exist")
            );

            calculationService.updateState(ongoingMatch);
            calculationService.updateScore(ongoingMatch, winnerId);

            if (ongoingMatch.isMatchFinished()) {
                ongoingMatch.setWinnerPlayerId(Long.valueOf(winnerId));
                finishedMatchService.proceedMatch(ongoingMatch);
                ongoingMatchDao.delete(matchId);

                req.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/match-score?uuid=" + matchId);
            }

        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
