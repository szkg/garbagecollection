package szkg.repositories;

import org.apache.log4j.Logger;
import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Gencay on 21.06.2015.
 */
public class SqliteRepository implements IRepository {

    private Logger logger = Logger.getLogger(SqliteRepository.class);
    private Connection connection;

    private void openConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:GarbageCollection.sqlite");
    }

    private void closeConnection() throws Exception {
        connection.close();
    }

    private void createSortingTable() throws Exception {

        openConnection();

        String createTableSql = "CREATE  TABLE  IF NOT EXISTS \"main\".\"SortingExecutions\" " +
                "(\"Id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "\"Collector\" VARCHAR NOT NULL , " +
                "\"CoreCount\" INTEGER NOT NULL , " +
                "\"ListSize\" INTEGER NOT NULL , " +
                "\"Algorithm\" INTEGER NOT NULL , " +
                "\"ListType\" INTEGER NOT NULL , " +
                "\"PauseTime\" DOUBLE NOT NULL , " +
                "\"ExecutionTime\" DOUBLE NOT NULL , " +
                "\"JdkVersion\" VARCHAR NOT NULL , " +
                "\"CreateDate\" DATETIME NOT NULL DEFAULT CURRENT_DATE)";

        PreparedStatement createTableStatement = connection.prepareStatement(createTableSql);
        createTableStatement.execute();
        createTableStatement.close();

        closeConnection();
    }


    @Override
    public void open() throws Exception {
        createSortingTable();
    }

    @Override
    public void addSortingExecution(int coreCount, String collector, int listSize, SorterType algorithm, ListType listType, double pauseTime, double executionTime, String jdkVersion) throws Exception {

        openConnection();

        String addExecutionSql = "INSERT INTO SortingExecutions " +
                "(Collector, ListSize, Algorithm, ListType, PauseTime, ExecutionTime, CoreCount, JdkVersion)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement addExecutionStatement = connection.prepareStatement(addExecutionSql);

        addExecutionStatement.setString(1, collector);
        addExecutionStatement.setInt(2, listSize);
        addExecutionStatement.setInt(3, algorithm.getValue());

        addExecutionStatement.setInt(4, listType.getValue());
        addExecutionStatement.setDouble(5, pauseTime);
        addExecutionStatement.setDouble(6, executionTime);

        addExecutionStatement.setInt(7, coreCount);
        addExecutionStatement.setString(8, jdkVersion);


        addExecutionStatement.execute();
        addExecutionStatement.close();

        closeConnection();
    }


}

