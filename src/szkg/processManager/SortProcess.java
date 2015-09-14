package szkg.processManager;

import org.apache.log4j.Logger;
import szkg.algorithms.sort.ISorter;
import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;
import szkg.factory.SorterFactory;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class SortProcess {

    private Logger logger = Logger.getLogger(SortProcess.class);

	public static void main(String[] args) {

        new SortProcess().run(args);

	}

	public static void logElapsedTime(String stopWatchLogFile, long elapsedTime) throws Exception {

		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(stopWatchLogFile, true)));
        file.println(String.format("%d",
                elapsedTime
        ));

		file.close();
	}

    private void run(String[] args) {

        try {

            String stopWatchLogFile = args[0];
            SorterType sorterType = SorterType.values()[Integer.parseInt(args[1])];
            ListType listType = ListType.values()[Integer.parseInt(args[2])];
            int listSize = Integer.parseInt(args[3]);


            SorterFactory sorterFactory = new SorterFactory();

            ISorter sorter = sorterFactory.CreateSorter(sorterType);


            Date startTime, endTime;


            switch (listType) {
                case ArrayList: {
                    ArrayList<Integer> unsortedArrayList = sorterFactory.CreateRandomArrayList(listSize);
                    startTime = new Date();
                    @SuppressWarnings("unused")
                    ArrayList<Integer> sortedArrayList = sorter.sort(unsortedArrayList);
                    endTime = new Date();
                    long elapsedTime = endTime.getTime() - startTime.getTime();

                    logElapsedTime(stopWatchLogFile, elapsedTime);

                }
                break;

                case LinkedList:

                    LinkedList<Integer> unsortedLinkedList = sorterFactory.CreateRandomLinkedList(listSize);
                    startTime = new Date();
                    @SuppressWarnings("unused")
                    LinkedList<Integer> sortedLinkedList = sorter.sort(unsortedLinkedList);
                    endTime = new Date();
                    long elapsedTime = endTime.getTime() - startTime.getTime();

                    logElapsedTime(stopWatchLogFile, elapsedTime);

                    break;
                default:
                    logger.debug("Default");
                    break;

            }


        } catch (Exception ex) {
            logger.error("SortProcess Error", ex);
        }
    }

    private String getOwnPid() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        pid = pid.split("@")[0];
        return pid;
    }

    private void printVmPeak() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        String[] commands = {"grep", "VmPeak", "/proc/" + getOwnPid() + "/status"};
        Process process = null;

        process = runtime.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }

}
