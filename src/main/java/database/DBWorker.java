package database;

import java.sql.Connection;

public class DBWorker {

    private Connection connection;

    public DBWorker(Connection connection) {
        this.connection = connection;
    }

}
