package servlets;

import database.DBWorker;
import model.TestResult;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "SignInServlet", urlPatterns = "/sign-in")
public class SignInServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
        String message = null;
        try {
            if (!dbWorker.verifyUser(username, password)) {
                message = "The username or password is incorrect.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "SQL Exception");
            return;
        }

        if (message == null) {
            User user;
            try {
                user = dbWorker.getUserObject(username);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500, "SQL Exception");
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            try {
                ArrayList<TestResult> results = dbWorker.getAllUsersResults(user);
                if (results != null)
                    session.setAttribute("results", results);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500, "SQL Exception");
                return;
            }
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        } else {
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/signIn.jsp").forward(request, response);
        }
    }
}
