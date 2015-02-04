package szkg.algorithms.sort;

public enum SorterType {
	Bubble(0),
	Heap(1),
	Insertion(2),
	Merge(3),
	Quick(4);

	private final int value;
	private SorterType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
