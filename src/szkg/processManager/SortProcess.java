package szkg.processManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import szkg.algorithms.sort.ISorter;
import szkg.algorithms.sort.SorterType;
import szkg.algorithms.sort.ListType;
import szkg.factory.SorterFactory;

public class SortProcess {

	public static void main(String[] args) {

		try{

			SorterType sorterType = SorterType.values()[Integer.parseInt(args[0])];
			ListType listType = ListType.values()[Integer.parseInt(args[1])];
			int listSize = Integer.parseInt(args[2]);
//			int iterationCount = Integer.parseInt(args[3]);
			String stopWatchLogFile = args[4];

			SorterFactory sorterFactory = new SorterFactory();

			ISorter sorter = sorterFactory.CreateSorter(sorterType);


			Date startTime, endTime;


			switch (listType) {
			case ArrayList:
			{
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
				break;

			}

		}
		catch(Exception ex){
			PrintWriter file;

			try {

				file = new PrintWriter(new BufferedWriter(new FileWriter("Error.txt", true)));
				file.println(String.format("%s", 				
						ex.toString()
						));
				file.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}

	}

	public static void logElapsedTime(String stopWatchLogFile, long elapsedTime) throws Exception {

		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(stopWatchLogFile, true)));
		file.println(String.format("%d", 				
				elapsedTime
				));

		file.close();
	}

}
