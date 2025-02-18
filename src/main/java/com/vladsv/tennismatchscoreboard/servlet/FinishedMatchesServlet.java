package com.vladsv.tennismatchscoreboard.servlet;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
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

    private FinishedMatchDao finishedMatchDao;

    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionFactory sessionFactory =
                (SessionFactory) config.getServletContext().getAttribute("hibernateSessionFactory");

        finishedMatchDao = new FinishedMatchDao(sessionFactory);
        validator = new Validator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String page = validator.getValidPageNumber(req.getParameter("page"));
            String filter = validator.getValidFilterName(req.getParameter("filter_by_player_name"));

            long matchesCount = filter.isEmpty()
                    ? finishedMatchDao.getMatchesCount()
                    : finishedMatchDao.getFilteredMatchesCount(filter);
            validator.verifyPageNumber(matchesCount, page, 6);

            List<FinishedMatch> matches = filter.isEmpty()
                    ? finishedMatchDao.findByPage(Integer.parseInt(page), 6)
                    : finishedMatchDao.findByFilter(Integer.parseInt(page), 6, filter);

            req.setAttribute("pageCount", (int) Math.ceil(matchesCount / (double) 6));
            req.setAttribute("matches", matches);
            req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }

}
