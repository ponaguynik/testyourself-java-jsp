package servlets;

import database.DBWorker;
import model.Question;
import model.TestResult;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "FinishTestServlet", urlPatterns = "/finish")
public class FinishTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");

        if (questions == null) {
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;
        }

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

        String result;
        String unansweredQnsCount;
        String duration;
        String date;
        String time;
        Question question = (Question) session.getAttribute("currentQn");

        int questionsTotal = questions.size();
        int correctAnswers = 0;
        for (Question qn : questions) {
             if (qn.isCorrect())
                 correctAnswers++;
        }
        int percent = Math.round(correctAnswers/(float)questionsTotal * 100);
        result = String.format("%d/%d (%d%%)", correctAnswers, questionsTotal, percent);

        int unansweredCount = 0;
        for (Question qn : questions) {
            if (!qn.isAnswered())
                unansweredCount++;
        }
        unansweredQnsCount = String.valueOf(unansweredCount);

        //Date and time information
        Date currentDate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        time = ft.format(currentDate);
        ft.applyPattern("dd.MM.yyyy");
        date = ft.format(currentDate);


        //Duration
        long currentTime = System.nanoTime();
        long startTime = (long) session.getAttribute("startTime");
        long diff = currentTime - startTime;
        duration = String.format("%02d:%02d:%02d",
                TimeUnit.NANOSECONDS.toHours(diff),
                TimeUnit.NANOSECONDS.toMinutes(diff),
                TimeUnit.NANOSECONDS.toSeconds(diff));

        TestResult testResult = new TestResult(date, time, result, duration, unansweredQnsCount, question);
        request.setAttribute("testResult", testResult);
        request.setAttribute("questions", questions);

        DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<TestResult> results;
        dbWorker.addTestResult(testResult, user);
        results = dbWorker.getAllUserResults(user);

        session.setAttribute("currentQn", null);
        session.setAttribute("questions", null);
        session.setAttribute("startTime", null);
        session.setAttribute("results", results);

        user.setLastResult(percent);
        if (percent > user.getBestResult())
            user.setBestResult(percent);

        dbWorker.updateUserResults(user);

        session.setAttribute("user", user);

        getServletContext().getRequestDispatcher("/finishResult.jsp").forward(request, response);
    }
}
