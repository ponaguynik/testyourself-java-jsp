package database;

import model.Question;
import util.PasswordHashing;

import java.sql.*;
import java.util.ArrayList;

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
}
