package database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import model.Question;
import model.TestResult;
import model.User;
import org.junit.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;


@SuppressWarnings("Duplicates")
public class DBWorkerTest {

    private static DBConnectionManager dbConnectionManager;
    private static DBWorker dbWorker;
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

        DataSource dataSource = dbConnectionManager.getDataSource();

        dbQuery = new DBQuery(dataSource);
        dbWorker = new DBWorker(dataSource);
    }

    @AfterClass
    public static void close() {
        dbConnectionManager.close();
    }

    @Test
    public void testUserExists() throws Exception {
        Assert.assertTrue(dbWorker.userExists("test"));
    }

    @Test
    public void testAddNewUserAndDeleteUser() throws Exception {
        //Delete user "junit" if exists
        dbWorker.deleteUser("junit");
        //Add and delete user "junit"
        dbWorker.addNewUser("junit", "junit");
        Assert.assertTrue(dbWorker.userExists("junit"));
        dbWorker.deleteUser("junit");
        Assert.assertFalse(dbWorker.userExists("junit"));
    }

    @Test
    public void testGetUserObject() throws Exception {
        User user = dbWorker.getUserObject("test");
        Assert.assertTrue(user.getId() == 1);
    }

    @Test
    public void testUpdateUserResults() throws Exception {
        User user = new User("test");
        user.setId(1);
        user.setLastResult(50);
        user.setBestResult(50);
        dbWorker.updateUserResults(user);
        User user1 = dbWorker.getUserObject("test");
        Assert.assertTrue(user1.getLastResult() == 50);
    }

    @Test
    public void testVerifyUser() throws Exception {
        Assert.assertTrue(dbWorker.verifyUser("test", "test"));
    }

    @Test
    public void testAddQuestion() throws Exception {
        dbWorker.addQuestion("junit test", "junit test", "junit test", "junit test", "junit test");
        ArrayList<Map<String, Object>> list = dbQuery.select("questions", new String[]{"code"},
                new String[]{"question='junit test'"});
        Assert.assertEquals(list.get(0).get("code"), "junit test");
        dbQuery.delete("questions", new String[]{"question='junit test'"});
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        ArrayList<Question> list = dbWorker.getAllQuestions();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testAddTestResult() throws Exception {
        TestResult result = new TestResult("junit test", "junit test", "junit test", "junit test");
        User user = new User();
        user.setId(1);
        dbWorker.addTestResult(result, user);
        ArrayList<Map<String, Object>> list = dbQuery.select("test_results", new String[]{"date"},
                new String[]{"time='junit test'"});
        Assert.assertEquals(list.get(0).get("date"), "junit test");

        dbQuery.delete("test_results", new String[]{"user_id='1'", "date='junit test'"});
    }

    @Test
    public void testGetAllUsersResults() throws Exception {
        User user = new User();
        user.setId(1);
        ArrayList<TestResult> list = dbWorker.getAllUserResults(user);
        Assert.assertNotNull(list);
    }
}