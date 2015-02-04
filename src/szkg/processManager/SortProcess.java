package szkg.processManager;

import java.util.List;

import szkg.algorithms.sort.ISorter;
import szkg.algorithms.sort.SorterType;
import szkg.factory.SorterFactory;

public class SortProcess {

	public static void main(String[] args) {
		
		int iterationCount = Integer.parseInt(args[0]);
		SorterType sorterType = SorterType.values()[Integer.parseInt(args[1])];
		int listSize = Integer.parseInt(args[1]);
		
		SorterFactory sorterFactory = new SorterFactory();
		
		ISorter sorter = sorterFactory.CreateSorter(sorterType);
		
		for (int iteration = 0; iteration < iterationCount; iteration++) {
			List<Integer> unsortedList = sorterFactory.CreateRandomUnsortedList(listSize);
			List<Integer> sortedList = sorter.sort(unsortedList);
		}

	}

}
