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
                "\"CollectionTime\" DOUBLE NOT NULL , " +
                "\"ExecutionTime\" DOUBLE NOT NULL , " +
                "\"CollectedGarbage\" INTEGER NOT NULL , " +
                "\"PeakHeapSize\" INTEGER NOT NULL , " +
                "\"JdkVersion\" VARCHAR NOT NULL , " +
                "\"CreateDate\" DATETIME NOT NULL DEFAULT CURRENT_DATE)";

        PreparedStatement createTableStatement = connection.prepareStatement(createTableSql);
        createTableStatement.execute();
        createTableStatement.close();

        closeConnection();
    }

    private void createDeCapoTable() throws Exception {

        openConnection();

        String createTableSql = "CREATE  TABLE  IF NOT EXISTS \"main\".\"SortingExecutions\" " +
                "(\"Id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "\"Collector\" VARCHAR NOT NULL , " +
                "\"Benchmark\" VARCHAR NOT NULL , " +
                "\"CoreCount\" INTEGER NOT NULL , " +
                "\"ListSize\" INTEGER NOT NULL , " +
                "\"Algorithm\" INTEGER NOT NULL , " +
                "\"ListType\" INTEGER NOT NULL , " +
                "\"CollectionTime\" DOUBLE NOT NULL , " +
                "\"ExecutionTime\" DOUBLE NOT NULL , " +
                "\"CollectedGarbage\" INTEGER NOT NULL , " +
                "\"PeakHeapSize\" INTEGER NOT NULL , " +
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
        createDeCapoTable();
    }

    @Override
    public void addSortingExecution(int coreCount, String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize, String jdkVersion) throws Exception {

        openConnection();

        String addExecutionSql = "INSERT INTO SortingExecutions " +
                "(Collector , ListSize, Algorithm , ListType , CollectionTime , ExecutionTime, CollectedGarbage, PeakHeapSize, CoreCount, JdkVersion)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement addExecutionStatement = connection.prepareStatement(addExecutionSql);

        addExecutionStatement.setString(1, collector);
        addExecutionStatement.setInt(2, listSize);
        addExecutionStatement.setInt(3, algorithm.getValue());

        addExecutionStatement.setInt(4, listType.getValue());
        addExecutionStatement.setDouble(5, collectionTime);
        addExecutionStatement.setDouble(6, executionTime);

        addExecutionStatement.setInt(7, collectedGarbage);
        addExecutionStatement.setInt(8, peakHeapSize);
        addExecutionStatement.setInt(9, coreCount);
        addExecutionStatement.setString(10, jdkVersion);


        addExecutionStatement.execute();
        addExecutionStatement.close();

        closeConnection();
    }

    @Override
    public void addDeCapoExecution(int coreCount, String collector, String benchmark, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize, String jdkVersion) throws Exception {

        openConnection();

        String addExecutionSql = "INSERT INTO SortingExecutions " +
                "(Collector , CollectionTime , ExecutionTime, CollectedGarbage, PeakHeapSize, CoreCount, JdkVersion, Benchmark)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement addExecutionStatement = connection.prepareStatement(addExecutionSql);

        addExecutionStatement.setString(1, collector);
        addExecutionStatement.setDouble(2, collectionTime);
        addExecutionStatement.setDouble(3, executionTime);

        addExecutionStatement.setInt(4, collectedGarbage);
        addExecutionStatement.setInt(5, peakHeapSize);
        addExecutionStatement.setInt(6, coreCount);
        addExecutionStatement.setString(7, jdkVersion);
        addExecutionStatement.setString(8, benchmark);

        addExecutionStatement.execute();
        addExecutionStatement.close();

        closeConnection();
    }
}
