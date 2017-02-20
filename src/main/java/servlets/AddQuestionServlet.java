package servlets;

import database.DBWorker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "AddQuestionServlet", urlPatterns = "/addQuestion")
public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = null;
        String msgColor = "red";

        String choice = retrieveChoice(request);

        String answer = retrieveAnswer(request);
        if (answer == null)
            message = "The correct answer(s) has to be chosen.";

        String choiceType = request.getParameter("choiceType");

        String question = request.getParameter("question");

        String code = request.getParameter("code");

        if (message == null) {
            DBWorker dbWorker = (DBWorker) getServletContext().getAttribute("DBWorker");
            dbWorker.addQuestion(question, code, choice, choiceType, answer);
            message = "The question has been successfully added.";
            msgColor = "green";
        }
        request.setAttribute("message", message);
        request.setAttribute("msgColor", msgColor);
        getServletContext().getRequestDispatcher("/addQuestion.jsp").include(request, response);
    }

    private String retrieveChoice(HttpServletRequest request) {
        byte num = Byte.parseByte(request.getParameter("num"));
        StringBuilder sb = new StringBuilder();
        for (byte i = 1; i <= num; i++) {
            sb.append(request.getParameter("option" + i));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String retrieveAnswer(HttpServletRequest request) {
        String[] answers = request.getParameterValues("option");
        StringBuilder sb = new StringBuilder();
        if (answers != null) {
            for (String s : answers) {
                sb.append(s);
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else {
            return null;
        }
    }
}
