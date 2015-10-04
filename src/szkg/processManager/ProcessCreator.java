package szkg.processManager;

import org.apache.log4j.Logger;
import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;
import szkg.models.GarbageCollectionResult;
import szkg.repositories.IRepository;
import szkg.repositories.SqliteRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class ProcessCreator {

    private Logger logger = Logger.getLogger(ProcessCreator.class);
    private IRepository repository = new SqliteRepository();

    public static void main(String[] args) {
        new ProcessCreator().run(args);
    }

    private void run(String[] args) {

        try {
            logger.debug("ProcessCreator Started");

            String jarName = "SortProcess.jar";

            runApplication(jarName, 1000000, 5000000, 1000000);
        } catch (Exception ex) {
            logger.error("ProcessCreator Error", ex);
        } finally {
            logger.debug("ProcessCreator Ended");
        }
    }

    public void runApplication(
            String jarName,
            int listSizeStart,
            int listSizeEnd,
            int listSizeIncrement
    ) throws Exception {

        int cores = Runtime.getRuntime().availableProcessors();
        String jdkVersion = System.getProperty("java.version");

        String[] collectors = new String[]{
                "UseSerialGC",
                "UseParallelGC",
                "UseG1GC"
        };

        int iterationCount = 10;

        repository.open();

        int fileIndex = 0;

        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            for (String collector : collectors) {
                for (int listSize = listSizeStart; listSize <= listSizeEnd; listSize = listSize + listSizeIncrement) {
                    for (SorterType algorithm : new SorterType[]{SorterType.Quick, SorterType.Merge}) {
                        for (ListType listType : new ListType[]{ListType.ArrayList, ListType.LinkedList}) {

                            try {

                                String garbageLogFile = String.format("Logs/g_%d_%s_%d_%d_%d.txt", iterationIndex, collector, listSize, listType.getValue(), algorithm.getValue());
                                String stopwatchFile = String.format("Logs/s_%d_%s_%d_%d_%d.txt", iterationIndex, collector, listSize, listType.getValue(), algorithm.getValue());

                                logger.debug(
                                        String.format(
                                                "Process started. Parameters: iteration: %d, collector: %s, listSize: %d, listType: %s, algorithm: %s",
                                                iterationIndex, collector, listSize, listType.name(), algorithm.name()));

                                runProcess(
                                        jarName,
                                        garbageLogFile,
                                        stopwatchFile,
                                        collector,
                                        algorithm,
                                        listType,
                                        listSize
                                );

                                GarbageCollectionResult garbageCollectionResult = getCollectionResultofAnIteration(garbageLogFile);
                                Double executionTimeofAnIteration = getExecutionTimeofAnIteration(stopwatchFile);

                                repository.addSortingExecution(cores,
                                        collector, listSize, algorithm,
                                        listType, garbageCollectionResult.collectionTime,
                                        executionTimeofAnIteration,
                                        garbageCollectionResult.collectedGarbage,
                                        garbageCollectionResult.peakHeapSize,
                                        jdkVersion);

                            } catch (Exception ex) {
                                logger.error("Sort Error", ex);
                            }
                        }
                    }
                }
            }
        }
    }

    public void runProcess(
            String jarName,
            String garbageLogFile,
            String stopWatchLogFile,
            String collector,
            SorterType algorithm,
            ListType listType,
            int listSize
    ) throws Exception {

        Thread.sleep(1000);

        Process process = Runtime.getRuntime().exec(String.format(
                "java -Xms1024m -Xmx1024m -Xloggc:%s -XX:+%s -jar %s %s %d %d %d",
                garbageLogFile,
                collector,
                jarName,
                stopWatchLogFile,
                algorithm.getValue(),
                listType.getValue(),
                listSize
        ));

        process.waitFor();
    }

    public GarbageCollectionResult getCollectionResultofAnIteration(String garbageLogFile) throws Exception {

        GarbageCollectionResult result = new GarbageCollectionResult();

        ArrayList<String> lines = readAllLines(garbageLogFile);
        for (String line : lines) {
            if (line.contains(" secs") && line.contains("->")) {

                String[] allParts = line.split("->");
                String[] initialParts = allParts[0].split(" ");

                int initialHeapSize = getSizeInKBytes(initialParts[initialParts.length - 1]);
                String[] timeParts = allParts[1].split("\\(|\\)| ");

                result.collectionTime += Double.parseDouble(timeParts[3]) * 1000;

                int resultHeapSize = getSizeInKBytes(timeParts[0]);

                if (initialHeapSize > result.peakHeapSize)
                    result.peakHeapSize = initialHeapSize;

                result.collectedGarbage += initialHeapSize - resultHeapSize;
            }
        }

        return result;
    }

    public int getSizeInKBytes(String value) {

        String metric = value.substring(value.length() - 1);
        value = value.substring(0, value.length() - 1);

        int size = Integer.parseInt(value);

        if (metric.equals("M"))
            size *= 1024;

        return size;
    }

    public Double getExecutionTimeofAnIteration(String stopWatchLogFile) throws Exception {
        Double executionTimeofAnIteration = 0.0;
        ArrayList<String> lines = readAllLines(stopWatchLogFile);

        for (String line : lines) {
            executionTimeofAnIteration += Double.parseDouble(line);
        }

        return executionTimeofAnIteration;
    }

    public ArrayList<String> readAllLines(String path) throws Exception {

        ArrayList<String> lines = new ArrayList<String>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        bufferedReader.close();
        return lines;
    }


}

