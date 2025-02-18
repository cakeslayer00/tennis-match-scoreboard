package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import com.vladsv.tennismatchscoreboard.service.FinishedMatchesService;
import com.vladsv.tennismatchscoreboard.utils.Validator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/matches")
public class FinishedMatchesServlet extends HttpServlet {

    private static final String ERROR_JSP_PATH = "WEB-INF/jsp/error.jsp";
    private static final String FINISHED_MATCHES_JSP_PATH = "/WEB-INF/jsp/matches.jsp";

    private FinishedMatchesService finishedMatchesService;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory =
                (SessionFactory) config.getServletContext().getAttribute("hibernateSessionFactory");

        FinishedMatchDao finishedMatchDao = new FinishedMatchDao(sessionFactory);
        finishedMatchesService = new FinishedMatchesService(finishedMatchDao);

        validator = new Validator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String page = validator.getValidPageNumber(req.getParameter("page"));
            String filter = validator.getValidFilterName(req.getParameter("filter_by_player_name"));

            int quantityOfPages = finishedMatchesService.getQuantityOfPages(filter);
            validator.verifyPageNumber(page, quantityOfPages);

            List<FinishedMatch> matches = finishedMatchesService.getMatches(page, filter);

            req.setAttribute("pageCount", quantityOfPages);
            req.setAttribute("matches", matches);
            req.getRequestDispatcher(FINISHED_MATCHES_JSP_PATH).forward(req, resp);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher(ERROR_JSP_PATH).forward(req, resp);
        }
    }

}
