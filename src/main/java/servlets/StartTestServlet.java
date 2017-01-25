package servlets;

import database.DBWorker;
import model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(name = "StartTestServlet", urlPatterns = "/startTest")
public class StartTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("confirmed").equals("false")) {
            response.sendRedirect("index.jsp");
            return;
        }

        DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
        ArrayList<Question> questions;
        try {
            questions = dbWorker.getAllQuestions();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "SQL Exception");
            return;
        }

        assert questions != null;
        ArrayList<Question> questions1 = new ArrayList<>(questions);
        ArrayList<Question> randomQns = new ArrayList<>();
        for (int i = 0, random; i < 10; i++) {
            if (questions1.isEmpty())
                break;
            random = ThreadLocalRandom.current().nextInt(0, questions1.size());
            Question qsn = questions1.get(random);
            qsn.setNum(i+1);
            if (qsn.getNum() == 1)
                qsn.setActive(true);
            randomQns.add(qsn);
            questions1.remove(random);
        }
        request.getSession().setAttribute("questions", randomQns);

        getServletContext().getRequestDispatcher("/test").forward(request, response);
    }
}
