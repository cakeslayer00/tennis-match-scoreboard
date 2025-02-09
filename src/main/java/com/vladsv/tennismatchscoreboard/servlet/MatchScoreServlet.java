package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.service.MatchScoreCalculationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private MatchScoreCalculationService calculationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        OngoingMatchDao ongoingMatchDao = (OngoingMatchDao) getServletContext()
                .getAttribute("ongoingMatchDao");
        calculationService = new MatchScoreCalculationService(ongoingMatchDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));


        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("WEB-INF/jsp/match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        String winnerId = req.getParameter("winnerId");

        calculationService.updateMatchState(matchId);
        calculationService.updateScore(matchId, winnerId);

        resp.sendRedirect("/match-score?uuid=" + matchId);
    }

}
