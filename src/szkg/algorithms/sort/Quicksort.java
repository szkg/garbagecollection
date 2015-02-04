package szkg.algorithms.sort;

import java.util.LinkedList;
import java.util.List;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Quicksort

public class Quicksort implements ISorter {

	public List<Integer> sort(List<Integer> input) {
		if (!input.isEmpty()) {

			Integer pivot = input.get(0); //This pivot can change to get faster results

			List<Integer> less = new LinkedList<Integer>();
			List<Integer> pivotList = new LinkedList<Integer>();
			List<Integer> more = new LinkedList<Integer>();

			// Partition
			for (Integer i: input) {
				if (i < pivot)
				{
					less.add(i);
				}
				else if (i > pivot)
				{
					more.add(i);
				}
				else
				{
					pivotList.add(i);
				}
			}

			// Recursively sort sublists
			less = sort(less);
			more = sort(more);

			// Concatenate results
			less.addAll(pivotList);
			less.addAll(more);
			return less;
		}

		return input;

	}



}
