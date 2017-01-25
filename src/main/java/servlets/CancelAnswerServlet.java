package servlets;

import model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CancelAnswerServlet", urlPatterns = "/cancel")
public class CancelAnswerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Question currentQn = (Question) request.getSession().getAttribute("currentQn");
        currentQn.setAnswers(null);
        currentQn.setAnswered(false);
        currentQn.setCorrect(false);
        request.setAttribute("qnNum", currentQn.getNum());
        getServletContext().getRequestDispatcher("/test").forward(request, response);
    }
}
