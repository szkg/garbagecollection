package szkg.algorithms.sort;

import java.util.ArrayList;
import java.util.LinkedList;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Quicksort

public class Quicksort implements ISorter {

	public ArrayList<Integer> sort(ArrayList<Integer> input) {
		if (!input.isEmpty()) {

			Integer pivot = input.get(0); //This pivot can change to get faster results

			ArrayList<Integer> less = new ArrayList<Integer>();
			ArrayList<Integer> pivotList = new ArrayList<Integer>();
			ArrayList<Integer> more = new ArrayList<Integer>();

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

	public LinkedList<Integer> sort(LinkedList<Integer> input) {
		if (!input.isEmpty()) {

			Integer pivot = input.get(0); //This pivot can change to get faster results

			LinkedList<Integer> less = new LinkedList<Integer>();
			LinkedList<Integer> pivotList = new LinkedList<Integer>();
			LinkedList<Integer> more = new LinkedList<Integer>();

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
