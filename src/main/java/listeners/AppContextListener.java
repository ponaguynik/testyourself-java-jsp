package listeners;

import database.DBConnectionManager;
import database.DBWorker;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener()
public class AppContextListener implements ServletContextListener {

    private DBConnectionManager dbConnectionManager;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        //Connect to the database
        try {
            dbConnectionManager = new DBConnectionManager();
            dbConnectionManager.connect(servletContext.getInitParameter("dbName"));
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }

        //Add DBWorker object in Servlet Context scope
        DBWorker dbWorker = new DBWorker(dbConnectionManager.getDataSource());
        servletContext.setAttribute("DBWorker", dbWorker);
        System.out.println("Database connection has been successfully established");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        dbConnectionManager.close();
    }
}
