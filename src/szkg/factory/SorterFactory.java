package szkg.factory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import szkg.algorithms.sort.*;

public class SorterFactory {

	public ISorter CreateSorter(SorterType type){

		ISorter sorter;

		switch(type)
		{
		case Bubble:
			sorter = new BubbleSort();
			break;
		case Heap:
			sorter = new Heapsort();
			break;
		case Insertion:
			sorter = new InsertionSort();
			break;
		case Merge:
			sorter = new MergeSort();
			break;
		case Quick:
			sorter = new Quicksort();
			break;
		default:
			sorter = null;
			break;
		}

		return sorter;
	}
	
	public List<Integer> CreateRandomUnsortedList(int size)
	{
		List<Integer> list = new LinkedList<Integer>();
		
		Random random = new Random();
		
		for (int i = 0; i < size; i++) {			
			list.add(new Integer(random.nextInt()));
		}
		
		return list;
	}
}
