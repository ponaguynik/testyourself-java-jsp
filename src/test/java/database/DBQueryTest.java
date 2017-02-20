package database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("Duplicates")
public class DBQueryTest {

    private static DBConnectionManager dbConnectionManager;
    private static DBQuery dbQuery;

    @BeforeClass
    public static void init() throws Exception {
        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,
                "org.apache.naming");
        InitialContext ic = new InitialContext();

        try {
            ic.createSubcontext("java:");
        } catch (NameAlreadyBoundException e) {
            ic.destroySubcontext("java:");
            ic.createSubcontext("java:");
        }
        ic.createSubcontext("java:/comp");
        ic.createSubcontext("java:/comp/env");
        ic.createSubcontext("java:/comp/env/jdbc");

        // Construct DataSource
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/testyourself_java");
        ds.setUser("root");
        ds.setPassword("root");

        ic.bind("java:/comp/env/jdbc/TestYourselfJavaDB", ds);

        dbConnectionManager = new DBConnectionManager();
        dbConnectionManager.connect("TestYourselfJavaDB");
        dbQuery = new DBQuery(dbConnectionManager.getDataSource());
    }

    @AfterClass
    public static void close() {
        dbConnectionManager.close();
    }

    @Test
    public void testInsertAndDelete() throws Exception {
        dbQuery.delete("users", new String[]{"username='junit'"});
        dbQuery.insert("users", new String[]{"username", "password"}, new String[]{"junit", "junit"});
        dbQuery.delete("users", new String[]{"username='junit'"});
    }

    @Test
    public void testSelect() throws Exception {
        Assert.assertNotNull(dbQuery.select("test_results", new String[]{"date", "time"},
                new String[]{"user_id='1'"}));
    }

    @Test
    public void testSelectAll() throws Exception {
        List<Map<String, Object>> list = dbQuery.selectAll("questions");
        Assert.assertTrue(list != null);
    }

    @Test
    public void testUpdate() throws Exception {
        dbQuery.update("test_results", new String[]{"date='testDate'", "time='testTime'"}, new String[]{"id='1'", "user_id='1'"});
    }
}