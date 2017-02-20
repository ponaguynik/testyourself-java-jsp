package database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBQuery {

    private DataSource dataSource;

    public DBQuery(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insert new row into table with values
     * @throws SQLException
     */
    public void insert(String table, String[] columns, String[] values) throws SQLException {
        if (columns.length != values.length)
            throw new SQLException("Columns length not equals values length.");
        if (columns.length == 0)
            throw new SQLException("No column selected.");

        StringBuilder columnsStr = new StringBuilder();
        for (String s : columns)
            columnsStr.append(s).append(",");
        columnsStr.deleteCharAt(columnsStr.length()-1);
        StringBuilder valuesStr = new StringBuilder();
        for (String s : values)
            valuesStr.append(String.format("'%s'", s)).append(",");
        valuesStr.deleteCharAt(valuesStr.length()-1);

        String query = String.format("INSERT INTO %s(%s) VALUES(%s)",
                table, columnsStr.toString(), valuesStr.toString());

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.execute(query);
        }
    }

    /**
     *
     * @param conditions Strings pattern: "column='value'"
     * @throws SQLException
     */
    public void delete(String table, String[] conditions) throws SQLException {
        if (conditions.length == 0)
            throw new SQLException("No any condition.");
        StringBuilder sb = new StringBuilder(String.format("DELETE FROM %s WHERE ", table));
        for (String s : conditions) {
            if (!s.matches(".+='.+'"))
                throw new SQLException("Condition must to fit pattern \"column='value'\".");
            sb.append(s).append(" AND ");
        }
        sb.delete(sb.length()-5, sb.length());
        String query = sb.toString();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.execute(query);
        }
    }

    /**
     * @param conditions Strings pattern: "column='value'"
     * @return List of Maps with "(String) column, (Object) value".
     *         Return null if list is empty.
     * @throws SQLException
     */
    public ArrayList<Map<String, Object>> select(String table, String[] columns, String[] conditions) throws SQLException {
        if (columns.length == 0)
            throw new SQLException("No column selected.");
        if (conditions != null && conditions.length == 0)
            throw new SQLException("No condition.");

        StringBuilder columnsStr = new StringBuilder();
        for (String s : columns)
            columnsStr.append(s).append(",");
        columnsStr.deleteCharAt(columnsStr.length()-1);

        String query;
        if (conditions != null) {
            StringBuilder conditionsStr = new StringBuilder();
            for (String s : conditions) {
                if (!s.matches(".+='.+'"))
                    throw new SQLException("Condition must to fit pattern \"column='value'\".");
                conditionsStr.append(s).append(" AND ");
            }
            conditionsStr.delete(conditionsStr.length() - 5, conditionsStr.length());

            query = String.format("SELECT %s FROM %s WHERE %s", columnsStr.toString(), table, conditionsStr.toString());
        } else {
            query = String.format("SELECT %s FROM %s", columnsStr.toString(), table);
        }
        ArrayList<Map<String, Object>> results = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
                ) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                    map.put(rsMetaData.getColumnName(i), rs.getObject(i));
                results.add(map);
            }
            if (results.isEmpty())
                return null;
        }
        return results;
    }

    public List<Map<String, Object>> selectAll(String table) throws SQLException {
        return select(table, new String[]{"*"}, null);
    }

    /**
     * @param updates Strings pattern: "column='value'"
     * @param conditions Strings pattern: "column='value'"
     * @throws SQLException
     */
    public void update(String table, String[] updates, String[] conditions) throws SQLException {
        if (updates.length == 0)
            throw new SQLException("No updates.");
        if (conditions.length == 0)
            throw new SQLException("No condition.");

        StringBuilder updatesStr = new StringBuilder();
        for (String s : updates)
            updatesStr.append(s).append(",");
        updatesStr.deleteCharAt(updatesStr.length()-1);

        StringBuilder conditionsStr = new StringBuilder();
        for (String s : conditions)
            conditionsStr.append(s).append("  AND ");
        conditionsStr.delete(conditionsStr.length()-5, conditionsStr.length());

        String query = String.format("UPDATE %s SET %s WHERE %s",
                table, updatesStr.toString(), conditionsStr.toString());

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        }
    }

}
