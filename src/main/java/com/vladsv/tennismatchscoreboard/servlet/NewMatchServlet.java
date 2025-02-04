package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dao.PlayerDao;
import com.vladsv.tennismatchscoreboard.dto.NewMatchRequestDto;
import com.vladsv.tennismatchscoreboard.service.NewMatchService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/new-match")
public class NewMatchServlet extends HttpServlet {

    private NewMatchService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("hibernate.session_factory");

        PlayerDao playerDao = new PlayerDao(sessionFactory);
        OngoingMatchDao ongoingMatchDao = (OngoingMatchDao) getServletContext().getAttribute("ongoingMatchDao");
        service = new NewMatchService(playerDao, ongoingMatchDao);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter("firstPlayer");
        String secondPlayerName = req.getParameter("secondPlayer");

        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            req.setAttribute("error", "Player names cannot be the same!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

        UUID uuid = UUID.randomUUID();
        NewMatchRequestDto requestDto = NewMatchRequestDto.builder()
                .firstPlayerName(firstPlayerName.toLowerCase())
                .secondPlayerName(secondPlayerName.toLowerCase())
                .build();

        service.initiateNewMatch(uuid, requestDto);
        resp.sendRedirect("/match-score?uuid=" + uuid);
    }

}
