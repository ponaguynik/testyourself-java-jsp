import model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "FinishTestServlet", urlPatterns = "/finish")
public class FinishTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");

        String finishTestAnyway = request.getParameter("finishTestAnyway");
        if (finishTestAnyway == null) {
            int unansweredCount = 0;
            for (Question question : questions) {
                if (!question.isAnswered())
                    unansweredCount++;
            }
            if (unansweredCount != 0) {
                request.setAttribute("finishMessage", "You have " + unansweredCount + " unanswered question(s). Do you want to finish the test anyway?");
                getServletContext().getRequestDispatcher("/test.jsp").forward(request, response);
                return;
            }
        }

        //Temporary code
        System.out.println("Everything works fine");
        response.sendRedirect("index.jsp");
    }
}
