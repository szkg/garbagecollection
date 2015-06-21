package szkg.processManager;

import org.apache.log4j.Logger;
import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

import java.io.BufferedReader;
import java.io.FileReader;


public class ProcessCreator {

    private Logger logger = Logger.getLogger(ProcessCreator.class);

    public static void main(String[] args) {
        new ProcessCreator().run(args);
    }

    private void run(String[] args) {

        try {
            logger.debug("ProcessCreator Started");

            String jarName = args[0];

            runApplication(jarName, 10000000, 10000000, 1);
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

        String[] collectors = new String[]{
                "UseSerialGC",
                "UseParallelGC",
                "UseG1GC"
        };

        int iterationCount = 100;
        int fileIndex = 0;

        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            for (String collector : collectors) {
                for (int listSize = listSizeStart; listSize <= listSizeEnd; listSize = listSize + listSizeIncrement) {
                    for (ListType listType : ListType.values()) {
                        for (SorterType algorithm : new SorterType[]{SorterType.Merge, SorterType.Quick}) {

                            String garbageLogFile = String.format("%d.txt", fileIndex++);
                            String stopwatchFile = String.format("%d.txt", fileIndex++);

                            runProcess(
                                    jarName,
                                    garbageLogFile,
                                    stopwatchFile,
                                    collector,
                                    algorithm,
                                    listType,
                                    listSize
                            );

                            Double collectionTimeofAnIteration = getCollectionTimeofAnIteration(garbageLogFile);
                            Double executionTimeofAnIteration = getExecutionTimeofAnIteration(stopwatchFile);
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

        logger.debug(
                String.format(
                        "Process started. Parameters: listSize: %d, listType: %s, algorithm: %s",
                        listSize,
                        listType.name(),
                        algorithm.name()));

        Process process = Runtime.getRuntime().exec(String.format(
                "java -Xloggc:%s -XX:+%s -jar %s %s %d %d %d",
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

    public Double getCollectionTimeofAnIteration(String garbageLogFile) throws Exception {
        Double collectionTimeofAnIteration = 0.0;
        BufferedReader br = new BufferedReader(new FileReader(garbageLogFile));
        String line;
        while ((line = br.readLine()) != null) {
            int timeStartIndex = line.indexOf(" secs") - 10;
            collectionTimeofAnIteration += Double.parseDouble(line.substring(timeStartIndex, timeStartIndex + 9));
        }
        br.close();

        return collectionTimeofAnIteration * 1000;
    }

    public Double getExecutionTimeofAnIteration(String stopWatchLogFile) throws Exception {
        Double executionTimeofAnIteration = 0.0;
        BufferedReader br = new BufferedReader(new FileReader(stopWatchLogFile));
        String line;
        while ((line = br.readLine()) != null) {
            executionTimeofAnIteration += Double.parseDouble(line);
        }
        br.close();

        return executionTimeofAnIteration;
    }
}
