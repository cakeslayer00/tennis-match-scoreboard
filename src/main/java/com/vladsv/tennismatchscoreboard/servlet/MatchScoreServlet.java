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

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private MatchScoreCalculationService calculationService;
    private FinishedMatchService finishedMatchService;

    private OngoingMatchDao ongoingMatchDao;

    private Validator validator;
    private ModelMapper modelMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory = (SessionFactory)
                config.getServletContext().getAttribute("hibernateSessionFactory");

        FinishedMatchDao finishedMatchDao = new FinishedMatchDao(sessionFactory);
        ongoingMatchDao = (OngoingMatchDao)
                config.getServletContext().getAttribute("ongoingMatchDao");

        calculationService = new MatchScoreCalculationService();
        finishedMatchService = new FinishedMatchService(finishedMatchDao);

        validator = new Validator();
        modelMapper = new ModelMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            UUID uuid = UUID.fromString(req.getParameter("uuid"));
            OngoingMatch ongoingMatch = ongoingMatchDao.findById(uuid).orElseThrow(
                    () -> new IllegalArgumentException("Match with current id doesn't exist")
            );

            req.setAttribute("ongoingMatch", modelMapper.map(ongoingMatch, OngoingMatchViewDto.class));
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

            calculationService.updateMatchState(ongoingMatch);
            calculationService.updateMatchScore(ongoingMatch, winnerId);

            if (ongoingMatch.isMatchFinished()) {
                ongoingMatch.setWinnerPlayer(
                        calculationService.getWinnerInstance(ongoingMatch, winnerId)
                );
                finishedMatchService.proceedMatch(ongoingMatch);
                ongoingMatchDao.delete(matchId);

                req.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(req, resp);
            } else {
                req.setAttribute("ongoingMatch", modelMapper.map(ongoingMatch, OngoingMatchViewDto.class));
                req.setAttribute("uuid", matchId);
                req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
                //is this better that redirect though?
            }

        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
