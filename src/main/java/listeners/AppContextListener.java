package listeners;

import database.DBConnectionManager;
import database.DBWorker;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener()
public class AppContextListener implements ServletContextListener {

    private Connection connection = null;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        //Initialize Database Connection
        String dbURL = servletContext.getInitParameter("dbURL");
        String dbUser = servletContext.getInitParameter("dbUser");
        String dbPassword = servletContext.getInitParameter("dbPassword");

        try {
            DBConnectionManager connectionManager = new DBConnectionManager(dbURL, dbUser, dbPassword);
            connection = connectionManager.getConnection();
            DBWorker dbWorker = new DBWorker(connection);
            servletContext.setAttribute("DBWorker", dbWorker);
            System.out.println("Connection to the database has been successfully established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
