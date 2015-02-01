package szkg.algorithms.sort;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Bubble_sort

public class BubbleSort {
	public <E extends Comparable<? super E>> void sort(E[] comparable) {
		boolean changed = false;
		do {
			changed = false;
			for (int a = 0; a < comparable.length - 1; a++) {
				if (comparable[a].compareTo(comparable[a + 1]) > 0) {
					E tmp = comparable[a];
					comparable[a] = comparable[a + 1];
					comparable[a + 1] = tmp;
					changed = true;
				}
			}
		} while (changed);
	}
}
