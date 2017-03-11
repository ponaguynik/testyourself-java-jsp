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

    /**
     * Verify username and password fields. Sign in a user or send error message.
     * @throws ServletException
     * @throws IOException
     */
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

        /*If username and password are correct sign in the user,
         if not - send error message.*/
        if (message == null) {
            //Get User object from the database.
            User user = dbWorker.getUserObject(username);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            //Get user results from the database.
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
