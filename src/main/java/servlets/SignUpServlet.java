package servlets;

import database.DBWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "SignUpServlet", urlPatterns = "/sign-up")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");

        DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");

        ArrayList<String> messages = new ArrayList<>();

        if (!password.equals(confPassword))
            messages.add("Password does not match the confirm password.");
        try {
            if (dbWorker.userExists(username))
                messages.add("User with such username already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "SQL Exception");
        }

        if (messages.isEmpty()) {
            try {
                dbWorker.addNewUser(username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500, "SQL Exception");
            }
            response.sendRedirect("signIn.jsp");
        } else {
            request.setAttribute("messages", messages);
            getServletContext().getRequestDispatcher("/signUp.jsp").forward(request, response);
        }
    }
}
