package servlets;

import model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "AnswerServlet", urlPatterns = "/answer")
public class AnswerServlet extends HttpServlet {

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] answers = request.getParameterValues("answer");
        if (answers == null) {
            request.setAttribute("message", "Choose at least one option.");
            getServletContext().getRequestDispatcher("/test.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        Question currentQn = (Question) session.getAttribute("currentQn");
        if (currentQn == null) {
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;
        }

        currentQn.setAnswers(answers);
        currentQn.setAnswered(true);
        if (Arrays.equals(answers, currentQn.getCorrectAnswers()))
            currentQn.setCorrect(true);
        else
            currentQn.setCorrect(false);

        ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
        int nextNum = currentQn.getNum()+1;
        try {
            questions.get(nextNum-1);
        } catch (IndexOutOfBoundsException e) {
            getServletContext().getRequestDispatcher("/finish").forward(request, response);
            return;
        }

        request.setAttribute("qnNum", nextNum);
        getServletContext().getRequestDispatcher("/test").forward(request, response);
    }
}
