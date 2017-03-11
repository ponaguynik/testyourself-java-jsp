package servlets;

import model.Question;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    /**
     * Set active question by question number.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
          Get question number from request-parameter scope,
          if it is null get from request-attribute scope,
          else questions number = 1
         */
        int qnNum;
        try {
            qnNum = Integer.parseInt(request.getParameter("qnNum"));
        } catch (NumberFormatException e) {
            try {
                qnNum = (int) request.getAttribute("qnNum");
            } catch (NullPointerException e1) {
                qnNum = 1;
            }
        }

        /*
          Get list of test questions from session scope and
          set to each if it is active or not.
         */
        HttpSession session = request.getSession();
        ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
        for (Question question : questions) {
            if (question.getNum() == qnNum)
                question.setActive(true);
            else {
                question.setActive(false);
            }
        }

        //Set current question to the session scope.
        session.setAttribute("currentQn", questions.get(qnNum-1));

        response.sendRedirect(response.encodeRedirectURL("test.jsp"));
    }
}
