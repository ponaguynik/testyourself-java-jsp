package database;

import util.PasswordHashing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWorker {

    private Statement statement;
    private Connection connection;
    private String query;

    public DBWorker(Connection connection) {
        this.connection = connection;
    }

    public boolean userExists(String username) {
        boolean result = false;
        query = String.format("select * from users where username='%s'", username);
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void addNewUser(String username, String password) {
        query = String.format("insert into users(username, password) VALUES('%s', '%s')",
                username, PasswordHashing.getSaltedHash(password));
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean verifyUser(String username, String password) {
        boolean result = false;
        query = String.format("select password from users where username='%s'", username);
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            result = rs.next() && PasswordHashing.check(rs.getString("password"), password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
