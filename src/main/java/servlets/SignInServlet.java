package servlets;

import database.DBWorker;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
            message = "The Username or Password is incorrect.";
        }

        if (message == null) {
            User user = new User(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        } else {
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/sign-in.jsp").forward(request, response);
        }
    }
}
