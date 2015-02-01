package szkg.algorithms.sort;

//taken from http://rosettacode.org/wiki/Sorting_algorithms/Insertion_sort#Java

public class InsertionSort {
	public void insertSort(int[] A){
		for(int i = 1; i < A.length; i++){
			int value = A[i];
			int j = i - 1;
			while(j >= 0 && A[j] > value){
				A[j + 1] = A[j];
				j = j - 1;
			}
			A[j + 1] = value;
		}
	}
}
