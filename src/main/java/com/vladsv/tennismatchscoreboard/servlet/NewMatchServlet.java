package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.impl.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dao.impl.PlayerDao;
import com.vladsv.tennismatchscoreboard.dto.NewMatchRequestDto;
import com.vladsv.tennismatchscoreboard.service.NewMatchService;
import com.vladsv.tennismatchscoreboard.utils.Validator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.exception.DataException;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/new-match")
public class NewMatchServlet extends HttpServlet {

    private static final String NEW_MATCH_JSP_PATH = "/WEB-INF/jsp/new-match.jsp";
    private static final String ERROR_JSP_PATH = "WEB-INF/jsp/error.jsp";

    private NewMatchService newMatchService;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        SessionFactory sessionFactory = (SessionFactory)
                config.getServletContext().getAttribute("hibernateSessionFactory");

        PlayerDao playerDao = new PlayerDao(sessionFactory);
        OngoingMatchDao ongoingMatchDao = (OngoingMatchDao)
                config.getServletContext().getAttribute("ongoingMatchDao");
        newMatchService = new NewMatchService(playerDao, ongoingMatchDao);
        validator = new Validator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(NEW_MATCH_JSP_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String firstPlayerName = validator.getValidPlayerName(req.getParameter("firstPlayer"));
            String secondPlayerName = validator.getValidPlayerName(req.getParameter("secondPlayer"));

            validator.checkForUniqueNames(firstPlayerName, secondPlayerName);

            UUID uuid = UUID.randomUUID();
            NewMatchRequestDto requestDto = NewMatchRequestDto.builder()
                    .firstPlayerName(firstPlayerName)
                    .secondPlayerName(secondPlayerName).build();

            newMatchService.startNewMatch(uuid, requestDto);

            if(validator.condition(firstPlayerName,secondPlayerName)) {
                resp.sendRedirect("https://www.youtube.com/watch?v=xvFZjo5PgG0?autoplay=1&mute=1");
            } else {
                resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
            }

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher(NEW_MATCH_JSP_PATH).forward(req, resp);
        } catch (DataException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("errorMessage", "Name is too long");
            req.getRequestDispatcher(NEW_MATCH_JSP_PATH).forward(req, resp);
        }
    }

}
