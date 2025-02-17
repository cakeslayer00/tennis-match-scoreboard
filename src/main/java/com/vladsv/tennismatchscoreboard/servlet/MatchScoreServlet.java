package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.dao.impl.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dto.OngoingMatchViewDto;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.service.MatchProcessingService;
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

    private MatchProcessingService matchProcessingService;

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

        MatchScoreCalculationService calculationService = new MatchScoreCalculationService();
        matchProcessingService = new MatchProcessingService(
                calculationService,
                ongoingMatchDao,
                finishedMatchDao);

        validator = new Validator();
        modelMapper = new ModelMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            UUID matchId = validator.getValidUuid(req.getParameter("uuid"));
            OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                    () -> new IllegalArgumentException("Match with current id doesn't exist")
            );

            req.setAttribute("ongoingMatch", modelMapper.map(ongoingMatch, OngoingMatchViewDto.class));
            req.setAttribute("uuid", matchId);
            req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            UUID matchId = validator.getValidUuid(req.getParameter("uuid"));
            String winnerId = validator.getValidWinnerId(req.getParameter("winnerId"));

            OngoingMatch processedMatch = matchProcessingService.getProcessedMatch(matchId, winnerId);

            if (processedMatch.isMatchFinished()) {
                req.setAttribute("winnerName", processedMatch.getWinnerPlayer().getName());
                req.getRequestDispatcher("WEB-INF/jsp/match-result.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
            }

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
