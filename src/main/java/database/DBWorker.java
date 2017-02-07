package database;

import model.Question;
import model.TestResult;
import model.User;
import util.PasswordHashing;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class DBWorker {

    private DataSource dataSource;
    private String query;

    public DBWorker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean userExists(String username) throws SQLException {
        boolean result = false;
        query = String.format("SELECT * FROM users WHERE username='%s'", username);
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    public void addNewUser(String username, String password) throws SQLException {
        query = String.format("INSERT INTO users(username, password) VALUES('%s', '%s')",
                username, PasswordHashing.getSaltedHash(password));
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public User getUserObject(String username) throws SQLException {
        query = String.format("SELECT id, last_result, best_result FROM users WHERE username='%s'", username);
        User user = new User();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLastResult(rs.getInt("last_result"));
                user.setBestResult(rs.getInt("best_result"));
                user.setUsername(username);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return user;
    }

    public void updateUserResults(User user) throws SQLException {
        query = String.format("UPDATE users SET last_result='%d' WHERE id='%d'", user.getLastResult(), user.getId());
        String query1 = String.format("UPDATE users SET best_result='%d' WHERE id='%d'", user.getBestResult(), user.getId());
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean verifyUser(String username, String password) throws SQLException {
        boolean result;
        query = String.format("SELECT password FROM users WHERE username='%s'", username);
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            result = rs.next() && PasswordHashing.check(password, rs.getString("password"));
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    public void addQuestion(String question, String code, String choice, String choiceType, String answer)
            throws SQLException {
        query = "INSERT INTO questions(question, code, choice, choiceType, answer) VALUES(?, ?, ?, ?, ?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
                ) {
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, code);
            preparedStatement.setString(3, choice);
            preparedStatement.setString(4, choiceType);
            preparedStatement.setString(5, answer);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Question> getAllQuestions() throws SQLException {
        ArrayList<Question> result = new ArrayList<>();
        query = "SELECT * FROM questions";
        Question question;
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            while (rs.next()) {
                String qn = rs.getString("question");
                String code = rs.getString("code");
                String[] choice = rs.getString("choice").split("&");
                String choiceType = rs.getString("choiceType");
                String[] answers = rs.getString("answer").split("&");
                question = new Question(qn, code, choiceType, choice, answers);
                result.add(question);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    public void addTestResult(TestResult result, User user) throws SQLException {
        query = "INSERT INTO test_results(user_id, date, time, result, duration) VALUES(?, ?, ?, ?, ?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
                ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, result.getDate());
            preparedStatement.setString(3, result.getTime());
            preparedStatement.setString(4, result.getResult());
            preparedStatement.setString(5, result.getDuration());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<TestResult> getAllUsersResults(User user) throws SQLException {
        ArrayList<TestResult> results = new ArrayList<>();

        query = String.format("SELECT * FROM test_results WHERE user_id='%d'", user.getId());
        TestResult testResult;

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            while (rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                String result = rs.getString("result");
                String duration = rs.getString("duration");

                testResult = new TestResult(date, time, result, duration);
                results.add(testResult);
            }
        }

        if (results.isEmpty())
            return null;

        Collections.reverse(results);

        return results;
    }
}
