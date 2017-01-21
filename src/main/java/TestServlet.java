import model.Question;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int qnNum = Integer.parseInt(request.getParameter("qnNum"));
        HttpSession session = request.getSession();
        ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
        for (Question question : questions) {
            if (question.getNum() == qnNum)
                question.setActive(true);
            else {
                question.setActive(false);
            }
        }
        request.setAttribute("currentQn", questions.get(qnNum-1));
        getServletContext().getRequestDispatcher("/test.jsp").forward(request, response);
    }
}
