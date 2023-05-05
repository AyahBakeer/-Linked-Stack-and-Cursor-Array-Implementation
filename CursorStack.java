package application;

public class CursorStack  {

	CursorList ca = new CursorList(100);

	int l = ca.createList();

	public void Push(Object data) {
		if (!ca.insertAtHead(data, l)) {
			System.out.println("Error: Satck Overflow!");
		}

	}

	
	public Object pop() {
		return ca.deleteFirst(l);

	}

	public Object peek() {
		return ca.getFirst(l);

	}

	public boolean isEmpty() {
		return ca.isEmpty(l);
	}

	
	public void clear() {
		while (true) {
			if (ca.isEmpty(l)) {
				break;
			} else {
				ca.deleteFirst(l);
			}
		}
	}

	public String convert(String G) {

		return null;
	}


	public int malloc() {
		// TODO Auto-generated method stub
		return malloc();
	}

	
}
