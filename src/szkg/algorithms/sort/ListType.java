package szkg.algorithms.sort;

public enum ListType {
	ArrayList(0),
	LinkedList(1);

	private final int value;
	private ListType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
