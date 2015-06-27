package szkg.repositories;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Gencay on 21.06.2015.
 */
public class H2Repository implements IRepository {

    private Connection connection;

    private void openConnection() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:~/GarbageCollection", "sa", "");
    }

    private void closeConnection() throws Exception {
        connection.close();
    }

    private void createTable() throws Exception {

        openConnection();

        String createTableSql = "CREATE TABLE IF NOT EXISTS EXECUTIONS(\n" +
                "ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "COLLECTOR VARCHAR(50),\n" +
                "LISTSIZE INT,\n" +
                "ALGORITHM INT,\n" +
                "LISTTYPE INT,\n" +
                "COLLECTIONTIME DOUBLE,\n" +
                "EXECUTIONTIME DOUBLE,\n" +
                "CREATEDATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP()\n" +
                ");";

        PreparedStatement createTableStatement = connection.prepareStatement(createTableSql);
        createTableStatement.execute();
        createTableStatement.close();

        closeConnection();
    }

    @Override
    public void open() throws Exception {
        createTable();
    }

    @Override
    public void addExecution(String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime) throws Exception {

        openConnection();

        String addExecutionSql = "INSERT INTO EXECUTIONS " +
                "(COLLECTOR , LISTSIZE, ALGORITHM , LISTTYPE , COLLECTIONTIME , EXECUTIONTIME )" +
                " VALUES (?,?,?,?,?,?)";

        PreparedStatement addExecutionStatement = connection.prepareStatement(addExecutionSql);

        addExecutionStatement.setString(1, collector);
        addExecutionStatement.setInt(2, listSize);
        addExecutionStatement.setInt(3, algorithm.getValue());

        addExecutionStatement.setInt(4, listType.getValue());
        addExecutionStatement.setDouble(5, collectionTime);
        addExecutionStatement.setDouble(6, executionTime);

        addExecutionStatement.execute();
        addExecutionStatement.close();

        closeConnection();
    }

}
