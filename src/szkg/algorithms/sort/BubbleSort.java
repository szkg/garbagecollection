package szkg.algorithms.sort;

import java.util.List;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Bubble_sort

public class BubbleSort implements ISorter {
	
	public List<Integer> sort(List<Integer> input) {
		boolean changed = false;
		do {
			changed = false;
			for (int a = 0; a < input.size() - 1; a++) {
				if (input.get(a) > input.get(a + 1)) {
					Integer tmp = input.get(a);
					input.set(a, input.get(a + 1));
					input.set(a + 1, tmp);
					changed = true;
				}
			}
		} while (changed);

		return input;
	}
	
}
