package application;

public class cursorNode {
	public Object element;
	public int next;
	public cursorNode(Object element, int next) {
	//	super();
		this.element = element;
		this.next = next;
	}
	
	public void setElement(Object element) {
		this.element = element;
	}

	public int getNext() {
		return next;
	}
	
	public void setNext(int next) {
		this.next = next;
	}

}
