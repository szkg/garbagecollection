package szkg.algorithms.sort;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Merge_sort

public class MergeSort implements ISorter{

	public List<Integer> sort(List<Integer> input) {
		if(input.size() <= 1) return input;

		int middle = input.size() / 2;
		List<Integer> left = input.subList(0, middle);
		List<Integer> right = input.subList(middle, input.size());

		right = sort(right);
		left = sort(left);
		List<Integer> result = merge(left, right);

		return result;
	}

	public List<Integer> merge(List<Integer> left, List<Integer> right){
		List<Integer> result = new LinkedList<Integer>();
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