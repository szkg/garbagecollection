package szkg.factory;

import java.util.ArrayList;
import java.util.LinkedList;

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

	public ArrayList<Integer> CreateReverseSortedArrayList(int size){

		ArrayList<Integer> list = new ArrayList<Integer>(size);

		for (int i = 0; i < size; i++) {			
			list.add(new Integer(size - i));
		}

		return list;
	}

	public ArrayList<Integer> CreateSortedArrayList(int size){

		ArrayList<Integer> list = new ArrayList<Integer>(size);

		for (int i = 0; i < size; i++) {			
			list.add(new Integer(i));
		}

		return list;
	}

	public ArrayList<Integer> CreateMergeSortWorstCaseArrayList(int size){

		ArrayList<Integer> list = new ArrayList<Integer>(size);

		for (int i = 0; i < size / 2; i = i + 2) {
			list.add(new Integer(i));
		}

		for (int i = size / 2; i < size ; i = i + 2) {
			list.add(new Integer(i - size / 2));
		}

		return list;
	}

	public ArrayList<Integer> CreateWorstCaseArrayList(int size, SorterType sorterType)
	{
		ArrayList<Integer> list;

		switch(sorterType)
		{
		case Bubble:
			list = this.CreateReverseSortedArrayList(size);
			break;
		case Heap:
			list = this.CreateReverseSortedArrayList(size);
			break;
		case Insertion:
			list = this.CreateReverseSortedArrayList(size);
			break;
		case Merge:
			list = this.CreateMergeSortWorstCaseArrayList(size);
			break;
		case Quick:
			list = this.CreateSortedArrayList(size);
			break;
		default:
			list = null;
			break;
		}

		return list;
	}

	public LinkedList<Integer> CreateReverseSortedLinkedList(int size){

		LinkedList<Integer> list = new LinkedList<Integer>();

		for (int i = 0; i < size; i++) {			
			list.add(new Integer(size - i));
		}

		return list;
	}

	public LinkedList<Integer> CreateSortedLinkedList(int size){

		LinkedList<Integer> list = new LinkedList<Integer>();

		for (int i = 0; i < size; i++) {			
			list.add(new Integer(i));
		}

		return list;
	}

	public LinkedList<Integer> CreateMergeSortWorstCaseLinkedList(int size){

		LinkedList<Integer> list = new LinkedList<Integer>();

		for (int i = 0; i < size / 2; i = i + 2) {
			list.add(new Integer(i));
		}

		for (int i = size / 2; i < size ; i = i + 2) {
			list.add(new Integer(i - size / 2));
		}

		return list;
	}

	public LinkedList<Integer> CreateWorstCaseLinkedList(int size, SorterType sorterType)
	{
		LinkedList<Integer> list;

		switch(sorterType)
		{
		case Bubble:
			list = this.CreateReverseSortedLinkedList(size);
			break;
		case Heap:
			list = this.CreateReverseSortedLinkedList(size);
			break;
		case Insertion:
			list = this.CreateReverseSortedLinkedList(size);
			break;
		case Merge:
			list = this.CreateMergeSortWorstCaseLinkedList(size);
			break;
		case Quick:
			list = this.CreateSortedLinkedList(size);
			break;
		default:
			list = null;
			break;
		}

		return list;
	}

}
