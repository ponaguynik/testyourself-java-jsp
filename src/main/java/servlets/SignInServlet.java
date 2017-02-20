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
        if (!dbWorker.verifyUser(username, password)) {
            message = "The username or password is incorrect.";
        }

        if (message == null) {
            User user;
            user = dbWorker.getUserObject(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            ArrayList<TestResult> results = dbWorker.getAllUserResults(user);
            if (results != null)
                session.setAttribute("results", results);
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        } else {
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/signIn.jsp").forward(request, response);
        }
    }
}
