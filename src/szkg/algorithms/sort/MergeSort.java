package szkg.algorithms.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Merge_sort

public class MergeSort implements ISorter{

	public ArrayList<Integer> sort(ArrayList<Integer> input) {
		if(input.size() <= 1) return input;

		int middle = input.size() / 2;
		ArrayList<Integer> left = new ArrayList<Integer>(input.subList(0, middle));
		ArrayList<Integer> right = new ArrayList<Integer>(input.subList(middle, input.size()));

		right = sort(right);
		left = sort(left);
		ArrayList<Integer> result = merge(left, right);

		return result;
	}

	public ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right){
		ArrayList<Integer> result = new ArrayList<Integer>();
		Iterator<Integer> it1 = left.iterator();
		Iterator<Integer> it2 = right.iterator();

		Integer x = it1.next();
		Integer y = it2.next();
		while (true){
			//change the direction of this comparison to change the direction of the sort
			if(x <= y){
				result.add(x);
				if(it1.hasNext()){
					x = it1.next();
				}else{
					result.add(y);
					while(it2.hasNext()){
						result.add(it2.next());
					}
					break;
				}
			}else{
				result.add(y);
				if(it2.hasNext()){
					y = it2.next();
				}else{
					result.add(x);
					while (it1.hasNext()){
						result.add(it1.next());
					}
					break;
				}
			}
		}
		return result;
	}

	public LinkedList<Integer> sort(LinkedList<Integer> input) {
		if(input.size() <= 1) return input;

		int middle = input.size() / 2;
		LinkedList<Integer> left = new LinkedList<Integer>(input.subList(0, middle));
		LinkedList<Integer> right = new LinkedList<Integer>(input.subList(middle, input.size()));

		right = sort(right);
		left = sort(left);
		LinkedList<Integer> result = merge(left, right);

		return result;
	}

	public LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right){
		LinkedList<Integer> result = new LinkedList<Integer>();
		Iterator<Integer> it1 = left.iterator();
		Iterator<Integer> it2 = right.iterator();

		Integer x = it1.next();
		Integer y = it2.next();
		while (true){
			//change the direction of this comparison to change the direction of the sort
			if(x <= y){
				result.add(x);
				if(it1.hasNext()){
					x = it1.next();
				}else{
					result.add(y);
					while(it2.hasNext()){
						result.add(it2.next());
					}
					break;
				}
			}else{
				result.add(y);
				if(it2.hasNext()){
					y = it2.next();
				}else{
					result.add(x);
					while (it1.hasNext()){
						result.add(it1.next());
					}
					break;
				}
			}
		}
		return result;
	}
}