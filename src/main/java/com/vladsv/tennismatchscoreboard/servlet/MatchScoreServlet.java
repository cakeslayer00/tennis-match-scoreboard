package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Score;
import com.vladsv.tennismatchscoreboard.service.MatchScoreCalculationService;
import com.vladsv.tennismatchscoreboard.utils.TennisPoint;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchDao ongoingMatchDao;
    private MatchScoreCalculationService matchScoreCalculationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ongoingMatchDao = (OngoingMatchDao) getServletContext().getAttribute("ongoingMatchDao");
        matchScoreCalculationService = new MatchScoreCalculationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(req.getParameter("uuid"));
        req.setAttribute("id", id);
        req.getRequestDispatcher("WEB-INF/jsp/match.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(req.getParameter("uuid"));
        String pointWinnerId = req.getParameter("pointWinnerId");

        OngoingMatch ongoingMatch = ongoingMatchDao.findById(id).orElseThrow(
                () -> new RuntimeException("No ongoing Match found with id " + id)
        );

        Score score = ongoingMatch.getScore();
        updatePoints(pointWinnerId, score);

    }

    private void updatePoints(String pointWinnerId, Score score) {
        Map<String, TennisPoint> points = score.getPoints();
        if (!points.containsKey(pointWinnerId)) {
            points.put(pointWinnerId, TennisPoint.FIRST_POINT);
        }
    }
}
