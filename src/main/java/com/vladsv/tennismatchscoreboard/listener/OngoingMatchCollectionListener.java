package com.vladsv.tennismatchscoreboard.listener;

import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class OngoingMatchCollectionListener implements ServletContextListener {

    private Map<UUID, OngoingMatch> matches;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        matches = new HashMap<>();
        sce.getServletContext().setAttribute("ongoingMatches", matches);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        matches = null;
    }

}
