package szkg.algorithms.sort;

import java.util.ArrayList;
import java.util.LinkedList;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Insertion_sort#Java

public class InsertionSort implements ISorter {
	public ArrayList<Integer> sort(ArrayList<Integer> input) {
		for(int i = 1; i < input.size(); i++){
			int value = input.get(i);
			int j = i - 1;
			while(j >= 0 && input.get(j) > value){
				input.set(j + 1, input.get(j));
				j = j - 1;
			}
			input.set(j + 1, value);
		}

		return input;
	}
	
	public LinkedList<Integer> sort(LinkedList<Integer> input) {
		for(int i = 1; i < input.size(); i++){
			int value = input.get(i);
			int j = i - 1;
			while(j >= 0 && input.get(j) > value){
				input.set(j + 1, input.get(j));
				j = j - 1;
			}
			input.set(j + 1, value);
		}

		return input;
	}
}
