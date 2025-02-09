package com.vladsv.tennismatchscoreboard.listener;

import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import com.vladsv.tennismatchscoreboard.model.Player;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@WebListener
public class HibernateInitializeListener implements ServletContextListener {

    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(FinishedMatch.class)
                    .buildMetadata()
                    .buildSessionFactory();

            sce.getServletContext().setAttribute("hibernateSessionFactory", sessionFactory);
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
