package com.vladsv.tennismatchscoreboard.listener;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;

@WebListener
public class OngoingMatchDaoListener implements ServletContextListener {

    private OngoingMatchDao ongoingMatchDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ongoingMatchDao = new OngoingMatchDao(new HashMap<>());
        sce.getServletContext().setAttribute("ongoingMatchDao", ongoingMatchDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ongoingMatchDao = null;
    }

}
