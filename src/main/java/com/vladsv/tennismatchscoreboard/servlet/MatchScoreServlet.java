package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(req.getParameter("uuid"));

        Object object = getServletContext().getAttribute("ongoingMatches");

    }
}
