package database;

import model.Question;
import model.TestResult;
import model.User;
import util.PasswordHashing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class DBWorker {

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private String query;

    public DBWorker(Connection connection) {
        this.connection = connection;
    }

    public boolean userExists(String username) throws SQLException {
        boolean result = false;
        query = String.format("select * from users where username='%s'", username);
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }
        return result;
    }

    public void addNewUser(String username, String password) throws SQLException {
        query = String.format("insert into users(username, password) VALUES('%s', '%s')",
                username, PasswordHashing.getSaltedHash(password));
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }
    }

    public User getUserObject(String username) throws SQLException {
        query = String.format("select id, last_result, best_result from users where username='%s'", username);
        ResultSet rs = null;
        User user = new User();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLastResult(rs.getInt("last_result"));
                user.setBestResult(rs.getInt("best_result"));
                user.setUsername(username);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }

        return user;
    }

    public void updateUserResults(User user) throws SQLException {
        query = String.format("update users set last_result=%d where id=%d", user.getLastResult(), user.getId());
        statement = connection.createStatement();
        statement.executeUpdate(query);
        query = String.format("update users set best_result=%d where id=%d", user.getBestResult(), user.getId());
        statement.executeUpdate(query);
        if (statement != null)
            statement.close();
    }

    public boolean verifyUser(String username, String password) throws SQLException {
        boolean result = false;
        query = String.format("select password from users where username='%s'", username);
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            result = rs.next() && PasswordHashing.check(password, rs.getString("password"));
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }
        return result;
    }

    public void addQuestion(String question, String code, String choice, String choiceType, String answer)
            throws SQLException {
        query = "insert into questions(question, code, choice, choiceType, answer) values(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, code);
            preparedStatement.setString(3, choice);
            preparedStatement.setString(4, choiceType);
            preparedStatement.setString(5, answer);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }
    }

    public ArrayList<Question> getAllQuestions() throws SQLException {
        ArrayList<Question> result = new ArrayList<>();
        query = "select * from questions";
        ResultSet rs = null;
        Question question;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }

        return result;
    }

    public void addTestResult(TestResult result, User user) throws SQLException {
        query = "insert into test_results(user_id, date, time, result, duration) values(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, result.getDate());
            preparedStatement.setString(3, result.getTime());
            preparedStatement.setString(4, result.getResult());
            preparedStatement.setString(5, result.getDuration());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new SQLException();
                }
            }
        }
    }

    public ArrayList<TestResult> getAllUsersResults(User user) throws SQLException {
        ArrayList<TestResult> results = new ArrayList<>();

        query = String.format("select * from test_results where user_id = '%d'", user.getId());
        ResultSet rs = null;
        TestResult testResult;

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                String result = rs.getString("result");
                String duration = rs.getString("duration");

                testResult = new TestResult(date, time, result, duration);
                results.add(testResult);
            }
        }
        finally {
            if (statement != null)
                statement.close();
            if (rs != null)
                rs.close();
        }

        if (results.isEmpty())
            return null;

        Collections.reverse(results);

        return results;
    }
}
