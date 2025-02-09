package com.vladsv.tennismatchscoreboard.listener;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class OngoingMatchDaoListener implements ServletContextListener {

    private OngoingMatchDao ongoingMatchDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ongoingMatchDao = new OngoingMatchDao(new ConcurrentHashMap<>());
        sce.getServletContext().setAttribute("ongoingMatchDao", ongoingMatchDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ongoingMatchDao = null;
    }

}
