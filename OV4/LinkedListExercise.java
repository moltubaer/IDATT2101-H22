import java.util.Arrays;

class CircularDoubleLinkedList {
	private Node head = null;
	private Node tail = null;
	private int length = 0;		// Number of elements in the linked list

	public Node getHead() {
		return this.head;
	}

	public Node getTail() {
		return this.tail;
	}

	public int getLength() {
		return this.length;
	}

	public void insertNodeFirst(int value) {
		head = new Node(value, head, null);
		if (tail == null) {
			tail = head;
		} else {
			head.next.previous = head;
		}
		length++;
	}

	public void insertNodeLast(int value) {
        Node newNode = new Node(value, null, tail);
        if (tail != null) {
			tail.next = newNode;
		} else {
			head = newNode;
		}
        tail = newNode;
        length++;
	}

	public String printList() {

	    Node element = getHead();
		String text;
		if (element.getValue() != 0) {
			text = String.valueOf(element.getValue());
		} else {
			text = "";
		}
		boolean isFinished = false;

		while (!isFinished) {
			element = element.getNext();
			if (element.getValue() != 0 || (element.getValue() == 0 && !text.equals(""))) {
				text += String.valueOf(element.getValue());
			} else {
				text += "";
			}
			if (element.getNext() == null) {
				isFinished = true;
			}
		}
		return text.equals("") ? "0" : text;
	}
}

class Node {
	int value;
	Node next;
	Node previous;

	Node (int value, Node next, Node previous) {
        this.value = value;
        this.next = next;
        this.previous = previous;
    }

	public int getValue() {
		return value;
	}
	public Node getNext() {
		return next;
	}
	public Node getPrevious() {
		return previous;
	}
}

public class LinkedListExercise {

	public static void Calculate(String num1, String num2, String operator) {
		int maxlength = (num1.length() >= num2.length()) ? (num1.length()+1) : (num2.length()+1);
		CircularDoubleLinkedList list1 = convertStringtoList(num1, maxlength);
		CircularDoubleLinkedList list2 = convertStringtoList(num2, maxlength);
		CircularDoubleLinkedList result = new CircularDoubleLinkedList();
		boolean rest = false;
		int count = 0;
		Node node1 = list1.getTail();
		Node node2 = list2.getTail();

		boolean negativeResult = (num1.length() < num2.length() || (num1.length() == num2.length() && num1.charAt(0) < num2.charAt(0)));

		while (count != maxlength) {
			if (operator == "+") {
				// System.out.println("addition");
				rest = addition(result, node1.getValue(), node2.getValue(), rest);
			} else if (operator == "-") {
				// System.out.println("subtraction");
				if (negativeResult) {
					rest = subtraction(result, node2.getValue(), node1.getValue(), rest);
				} else {
					rest = subtraction(result, node1.getValue(), node2.getValue(), rest);
				}
			}
			node1 = node1.getPrevious();
			node2 = (node2 != null) ? node2.getPrevious() : null;
			count++;
		}
		if (operator == "+") {
			System.out.println(list1.printList() + " + " + list2.printList() + " = " + result.printList());
		} else if (operator == "-") {
			if (negativeResult) {
				System.out.println(list1.printList() + " - " + list2.printList() + " = -" + result.printList());
			} else {
				System.out.println(list1.printList() + " - " + list2.printList() + " = " + result.printList());
			}
		}
		// System.out.println(result.getLength());
	}

	public static boolean addition(CircularDoubleLinkedList list, int first, int last, boolean rest) {
		int value = first + last;
		boolean valueOver9 = false;
		if (value > 9) valueOver9 = true;
		if (rest) value++;
		list.insertNodeFirst((value % 10));
		return valueOver9;
	}

	public static boolean subtraction(CircularDoubleLinkedList list, int first, int last, boolean rest) {
		boolean loanNext = false;
		if (rest == true) first--;
		if (first < last) {
			first += 10;
			loanNext = true;
		}
		int value = first - last;
		// System.out.println(first + " - " + last + " = " + value);
		list.insertNodeFirst((value));
		return loanNext;
	}

	public static CircularDoubleLinkedList convertStringtoList(String input, int length) {
        CircularDoubleLinkedList list = new CircularDoubleLinkedList();
		Arrays.stream(input.split("")).forEach(s -> list.insertNodeLast(Integer.parseInt(s)));
		while (list.getLength() < length) {
            list.insertNodeFirst(0);
		}
		return list;
	}

	public static void main(String[] args) {

		String num1 = "11111111111176761296663380825390103670082238690104261201865299";
		String num2 = "93154830849960715720286817854970639637715960319325";
		// String num1 = "6154";
		// String num2 = "4327";

		// String num1 = "123";
		// String num2 = "987";


		String add = "+";
		String subtract = "-";

		// Calculate(num1, num2, add);
		Calculate(num1, num2, subtract);
		// Calculate(num2, num1, subtract);

	}
}








