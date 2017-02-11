package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DBConnectionManager {

    private static Context context = null;
    private static DataSource dataSource = null;

    public static void connect() {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/TestYourselfJavaDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void close() {
        if (context != null) {
            try {
                dataSource.getConnection().close();
                context.close();
            } catch (NamingException|SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
