package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DBConnectionManager {

    private Context context = null;
    private DataSource dataSource = null;

    public void connect(String dbName) throws NamingException {
        context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/" + dbName);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void close() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }
}
