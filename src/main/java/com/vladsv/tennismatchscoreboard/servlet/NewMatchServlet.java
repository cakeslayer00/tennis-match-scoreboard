package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.PlayerDao;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Player;
import com.vladsv.tennismatchscoreboard.model.Score;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@WebServlet(value = "/new-match")
public class NewMatchServlet extends HttpServlet {

    private PlayerDao playerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        final SessionFactory sessionFactory = (SessionFactory) getServletContext()
                .getAttribute("hibernate.session_factory");

        playerDao = new PlayerDao(sessionFactory);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String firstPlayerName = req.getParameter("firstPlayer");
        final String secondPlayerName = req.getParameter("secondPlayer");

        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

        Optional<Player> optionalFirstPlayer = playerDao.findByName(firstPlayerName);
        Optional<Player> optionalSecondPlayer = playerDao.findByName(secondPlayerName);

        if (optionalFirstPlayer.isEmpty()) {
            playerDao.persist(Player.builder().name(firstPlayerName).build());
        }

        if (optionalSecondPlayer.isEmpty()) {
            playerDao.persist(Player.builder().name(secondPlayerName).build());
        }

        optionalFirstPlayer = playerDao.findByName(firstPlayerName);
        optionalSecondPlayer = playerDao.findByName(secondPlayerName);

        UUID uuid = UUID.randomUUID();
        OngoingMatch ongoingMatch = OngoingMatch.builder()
                .firstPlayerId(optionalFirstPlayer.orElseThrow().getId())
                .secondPlayerId(optionalSecondPlayer.orElseThrow().getId())
                .score(new Score())
                .build();

        Map<UUID, OngoingMatch> ongoingMatches = (HashMap<UUID, OngoingMatch>)getServletContext().getAttribute("ongoingMatches");
        ongoingMatches.put(uuid, ongoingMatch);
        resp.sendRedirect("/match-score?uuid=" + uuid);

    }


}
