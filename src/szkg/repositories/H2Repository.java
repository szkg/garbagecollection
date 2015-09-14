package szkg.repositories;

import org.apache.log4j.Logger;
import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Gencay on 21.06.2015.
 */
public class H2Repository implements IRepository {

    private Logger logger = Logger.getLogger(H2Repository.class);
    private Connection connection;

    private void openConnection() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:/home/azureuser/GarbageCollection", "sa", "");
    }

    private void closeConnection() throws Exception {
        connection.close();
    }

    private void createTable() throws Exception {

        openConnection();

        String createTableSql = "CREATE TABLE IF NOT EXISTS EXECUTIONS(\n" +
                "ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "COLLECTOR VARCHAR(50),\n" +
                "CORECOUNT INT,\n" +
                "LISTSIZE INT,\n" +
                "ALGORITHM INT,\n" +
                "LISTTYPE INT,\n" +
                "COLLECTIONTIME DOUBLE,\n" +
                "EXECUTIONTIME DOUBLE,\n" +
                "COLLECTEDGARBAGE INT,\n" +
                "PEAKHEAPSIZE INT,\n" +
                "CREATEDATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP()\n" +
                ");";

        PreparedStatement createTableStatement = connection.prepareStatement(createTableSql);
        createTableStatement.execute();
        createTableStatement.close();

        closeConnection();
    }

    @Override
    public void exportToScript() throws Exception {
        openConnection();

        String selectSql = "SELECT * FROM EXECUTIONS";

        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet resultSet = selectStatement.executeQuery();


        ArrayList<String> lines = new ArrayList<String>();
        while (resultSet.next()) {
            String collector = resultSet.getString("COLLECTOR");
            int listSize = resultSet.getInt("LISTSIZE");
            int algorithm = resultSet.getInt("ALGORITHM");
            int listType = resultSet.getInt("LISTTYPE");
            double collectionTime = resultSet.getDouble("COLLECTIONTIME");
            double executionTime = resultSet.getDouble("EXECUTIONTIME");
            Timestamp createDate = resultSet.getTimestamp("CREATEDATE");
            int coreCount = resultSet.getInt("CORECOUNT");
            int collectedgarbage = resultSet.getInt("COLLECTEDGARBAGE");
            int peakheapsize = resultSet.getInt("PEAKHEAPSIZE");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String format = "INSERT INTO Executions " +
                    "(Collector, ListSize, Algorithm, ListType, CollectionTime, ExecutionTime, CreateDate, CoreCount, CollectedGarbage, PeakHeapSize)" +
                    " VALUES ('%s', %d, %d, %d, %f, %f, '%s', %d, %d, %d);";

            String line = String.format(Locale.ENGLISH, format, collector, listSize, algorithm, listType, collectionTime, executionTime, dateFormat.format(createDate), coreCount, collectedgarbage, peakheapsize);
            lines.add(line);
        }

        writeAllLines("Script.sql", lines);

        selectStatement.close();
        resultSet.close();
        closeConnection();
    }

    @Override
    public void open() throws Exception {
        createTable();
    }

    @Override
    public void addExecution(int coreCount, String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize) throws Exception {

        openConnection();

        String addExecutionSql = "INSERT INTO EXECUTIONS " +
                "(COLLECTOR , LISTSIZE, ALGORITHM , LISTTYPE , COLLECTIONTIME , EXECUTIONTIME, COLLECTEDGARBAGE, PEAKHEAPSIZE, CORECOUNT)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";

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


        addExecutionStatement.execute();
        addExecutionStatement.close();

        closeConnection();
    }

    private void writeAllLines(String path, ArrayList<String> lines) throws Exception {

        PrintWriter writer = new PrintWriter(path, "UTF-8");

        for (String line : lines) {
            writer.println(line);
        }

        writer.close();
    }
}
