package listeners;

import database.DBConnectionManager;
import database.DBWorker;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener()
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        //Connect to the database
        DBConnectionManager.connect();

        //Add DBWorker object in Servlet Context scope
        DBWorker dbWorker = new DBWorker(DBConnectionManager.getDataSource());
        servletContext.setAttribute("DBWorker", dbWorker);
        System.out.println("Database connection has been successfully established");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DBConnectionManager.close();
    }
}
