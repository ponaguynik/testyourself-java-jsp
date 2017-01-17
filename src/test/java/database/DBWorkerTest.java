package database;

import junit.framework.TestCase;
import model.Question;

import java.util.ArrayList;
import java.util.Arrays;

public class DBWorkerTest extends TestCase {

    private DBWorker dbWorker;
    private DBConnectionManager connectionManager;

    public void setUp() throws Exception {
        connectionManager =
                new DBConnectionManager("jdbc:mysql://localhost:3306/testyourself_java?autoReconnect=true&useSSL=false", "root", "root");
        dbWorker = new DBWorker(connectionManager.getConnection());
    }

    public void tearDown() throws Exception {
        connectionManager.getConnection().close();
    }

    public void testGetAllQuestions() throws Exception {
        ArrayList<Question> questions = dbWorker.getAllQuestions();
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            System.out.println(question.getCode());
            System.out.println(Arrays.toString(question.getChoice()));
            System.out.println(Arrays.toString(question.getCorrectAnswers()));
        }
    }
}