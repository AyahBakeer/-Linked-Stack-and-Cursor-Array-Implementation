package application;

public class CursorList {
	cursorNode[] cursorArray;

	public CursorList(int capacity) {
		cursorArray = new cursorNode[capacity];
		initialization();
	}

	private int initialization() {
		for (int i = 0; i < cursorArray.length - 1; i++)
			cursorArray[i] = new cursorNode(null, i + 1);
		cursorArray[cursorArray.length - 1] = new cursorNode(null, 0);
		return 0;
	}

	private int malloc() {
		int p = cursorArray[0].next;
		cursorArray[0].next = cursorArray[p].next;
		return p;
	}

	private void free(int p) {
		cursorArray[p] = new cursorNode(null, cursorArray[0].next);
		cursorArray[0].next = p;
	}

	private boolean isNull(int l) {
		return cursorArray[l] == null;
	}

	public boolean isEmpty(int l) {
		return cursorArray[l].next == 0;
	}

	private boolean isLast(int p) {
		return cursorArray[p].next == 0;
	}

	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!");
		else
			cursorArray[l] = new cursorNode("-", 0);
		return l;
	}

	public boolean insertAtHead(Object data, int l) {
		if (isNull(l)) // list not created
			return false;
		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new cursorNode(data, cursorArray[l].next);
			cursorArray[l].next = p;
		} else {
//			System.out.println("Error: Out of space!");
			return false;
		}
		return true;
	}

	public void insertAtLast(Object data, int l) {
		if (isNull(l)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			while (!isLast(l))
				l = cursorArray[l].next;
			cursorArray[p] = new cursorNode(data, 0);
			cursorArray[l].next = p;
		} else
			System.out.println("Error: Out of space!!!");
	}

	public void deleteAtLast(int l) {
		if (isNull(l) || isEmpty(l)) { // list not created
			System.out.println("Empty List!!!");
			return;
		}

		while (!isLast(cursorArray[l].next))
			l = cursorArray[l].next;
		int p = cursorArray[l].next;
		cursorArray[l].next = 0;
		free(p);

	}

	public void traversList(int l) {
		System.out.print("list_" + l + "-->");
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].next;
			System.out.print(cursorArray[l] + "-->");
		}
		System.out.println("null");
	}

	public int find(Object data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].next;
			if (cursorArray[l].element.equals(data))
				return l;
		}
		return -1; // not found
	}

	public int findPrevious(Object data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (cursorArray[cursorArray[l].next].element.equals(data))
				return l;
			l = cursorArray[l].next;
		}
		return -1; // not found
	}

	public Object deleteFirst(int l) {
		if (!isNull(l) && !isEmpty(l)) {
			int p = cursorArray[l].next;
			cursorArray[l].next = cursorArray[cursorArray[l].next].next;
			cursorNode temp = cursorArray[p];
			return temp.element;
		}
		return null;
	}

	public Object getFirst(int l) {
		if (!isNull(l) && !isEmpty(l))
			return cursorArray[cursorArray[l].next].element;
		return null;
	}

	public cursorNode delete(Object data, int l) {
		int p = findPrevious(data, l);
		if (p != -1) {
			int c = cursorArray[p].next;
			cursorNode temp = cursorArray[c];
			cursorArray[p].next = temp.next;
			free(c);
		}
		return null;
	}
}
