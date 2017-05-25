package database;

import model.Question;
import model.TestResult;
import model.User;
import util.PasswordHashing;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DBWorker {

    private DBQuery dbQuery;

    public DBWorker(DataSource dataSource) {
        dbQuery = new DBQuery(dataSource);
    }

    /**
     * Check whether user with such username exists.
     * @throws ServletException
     */
    public synchronized boolean userExists(String username) throws ServletException {
        try {
            return (dbQuery.select("users", new String[]{"username"}, new String[]{"username='" + username + "'"}) != null);
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Add new user to the database.
     * @throws ServletException
     */
    public synchronized void addNewUser(String username, String password) throws ServletException {
        try {
            dbQuery.insert("users", new String[]{"username", "password"},
                    new String[]{username, PasswordHashing.getSaltedHash(password)});
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Delete user with such username from the database.
     * @throws ServletException
     */
    public synchronized void deleteUser(String username) throws ServletException {
        try {
            dbQuery.delete("users", new String[]{"username='" + username + "'"});
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * @return new User object by username with filled fields.
     * @throws ServletException
     */
    public synchronized User getUserObject(String username) throws ServletException {
        User user = new User();
        try {
            List<Map<String, Object>> list = dbQuery.select("users", new String[]{"id", "last_result", "best_result"},
                    new String[]{"username='" + username + "'"});
            for (Map<String, Object> map : list) {
                user.setId((Integer) map.get("id"));
                user.setLastResult((Integer) map.get("last_result"));
                user.setBestResult((Integer) map.get("best_result"));
                user.setUsername(username);
            }
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        return user;
    }


    /**
     * Update user fields in the database by User object.
     * @throws ServletException
     */
    public synchronized void updateUserResults(User user) throws ServletException {
        try {
            dbQuery.update("users",
                    new String[]{"last_result='" + user.getLastResult() + "'",
                            "best_result='" + user.getBestResult() + "'"},
                    new String[]{"id='" + user.getId() + "'"});
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Verify whether password of user with such username is correct.
     * @return true if user's password is correct.
     * @throws ServletException
     */
    public synchronized boolean verifyUser(String username, String password) throws ServletException {
        try {
            ArrayList<Map<String, Object>> result = dbQuery.select("users", new String[]{"password"},
                    new String[]{"username='" + username + "'"});
            String pwd;
            if (result != null) {
                pwd = (String) result.get(0).get("password");
                return PasswordHashing.check(password, pwd);
            } else
                return false;
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Add the question in the database.
     * @throws ServletException
     */
    public synchronized void addQuestion(String question, String code, String choice, String choiceType, String answer)
            throws ServletException {
        try {
            dbQuery.insert("questions", new String[]{"question", "code", "choice", "choiceType", "answer"},
                    new String[]{question, code, choice, choiceType, answer});
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Retrieve all questions from the database.
     * @return List of all questions in the database.
     * @throws ServletException
     */
    public synchronized ArrayList<Question> getAllQuestions() throws ServletException {
        ArrayList<Question> result = new ArrayList<>();
        Question question;
        try {
            List<Map<String, Object>> list = dbQuery.selectAll("questions");
            for (Map<String, Object> map : list) {
                String qn = (String) map.get("question");
                String code = (String) map.get("code");
                String[] choice = ((String) map.get("choice")).split("&");
                String choiceType = (String) map.get("choiceType");
                String[] answers = ((String) map.get("answer")).split("&");
                question = new Question(qn, code, choiceType, choice, answers);
                result.add(question);
            }
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        return result;
    }

    /**
     * Add test result to the database.
     * @throws ServletException
     */
    public synchronized void addTestResult(TestResult result, User user) throws ServletException {
        try {
            dbQuery.insert("test_results", new String[]{"user_id", "date", "time", "result", "duration"},
                    new String[]{String.valueOf(user.getId()), result.getDate(), result.getTime(), result.getResult(),
                            result.getDuration()});
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Retrieve all test results of user from the database.
     * @return List of TestResult objects.
     * @throws ServletException
     */
    public synchronized ArrayList<TestResult> getAllUserResults(User user) throws ServletException {
        ArrayList<TestResult> results = new ArrayList<>();

        TestResult testResult;
        try {
            List<Map<String, Object>> list = dbQuery.select("test_results", new String[]{"*"},
                    new String[]{"user_id='" + user.getId() + "'"});
            if (list == null)
                return null;
            for (Map<String, Object> map : list) {
                String date = (String) map.get("date");
                String time = (String) map.get("time");
                String result = (String) map.get("result");
                String duration = (String) map.get("duration");

                testResult = new TestResult(date, time, result, duration);
                results.add(testResult);
            }
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        if (results.isEmpty())
            return null;

        Collections.reverse(results);

        return results;
    }
}
