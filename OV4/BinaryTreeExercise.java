import java.util.ArrayList;
import java.util.LinkedList;

class BinaryTree {
	Node root;

	public BinaryTree() {
		root = null;
	}

	public Node getRoot() {
		return root;
	}

	public Node addNodeRecursive(String word, Node currententNode) {
		if (root == null) {
			return new Node (word);
		}

		Node parent = null;
		if (word.compareToIgnoreCase(currententNode.getWord()) <= 0) {
			if (currententNode.getLeft() == null) {
				currententNode.left = new Node(word);
			} else {
				addNodeRecursive(word, currententNode.getLeft());
			}
		} else {
			if (currententNode.getRight() == null) {
				currententNode.right = new Node(word);
			} else {
				addNodeRecursive(word, currententNode.getRight());
			}
		}
		return currententNode;
	}

	public void add(String word) {
		root = addNodeRecursive(word, root);
	}

	public int findHeight(Node node) {
		if ( node == null ) {
			return -1;
		} else {
			int leftHeight = findHeight(node.left) ;
			int rightHeight = findHeight(node.right) ;
			if (leftHeight >= rightHeight) {
				return leftHeight+1;
			} else {
				return rightHeight+1;
			}
		}
	}

	void printTree() {
		LinkedList<QueueItem> queue = new LinkedList<>();
		ArrayList<Node> level = new ArrayList<>();
		int depth = findHeight(root); depth++;
		queue.add(new QueueItem(root, depth));

		while (true) {
			QueueItem current = queue.poll();
			if (current.depth < depth) {
				depth = current.depth;
				for (int i = 0; i < (int)Math.pow(2, depth)-1; i++) {
					System.out.print(" ");
				}
				for (Node n : level) {
					System.out.print(n == null ? " " : n.word);
					for (int i = (int)Math.pow(2, depth + 1); i > 1; i--) {
						System.out.print(" ");
					}
				}
				System.out.println();
				level.clear();
				if (current.depth <= 0) break;
			}
			level.add(current.node);
			if (current.node == null) {
				queue.add(new QueueItem(null, depth - 1));
				queue.add(new QueueItem(null, depth - 1));
			} else {
				queue.add(new QueueItem(current.node.left, depth - 1));
				queue.add(new QueueItem(current.node.right, depth - 1));
			}
		}
	}
}

class Node {
	String word;
	Node right;
	Node left;

	Node(String word) {
        this.word = word;
		right = null;
		left = null;
    }

	public String getWord() {
		return word;
	}
	public Node getRight() {
		return right;
	}
	public Node getLeft() {
		return left;
	}
}

class QueueItem {
	Node node;
	int depth;

	public QueueItem(Node node, int depth) {
		this.node = node;
		this.depth = depth;
	}
}

public class BinaryTreeExercise {

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();

        tree.add("hode");
        tree.add("ben");
        tree.add("legg");
        tree.add("albue");
        tree.add("hake");
        tree.add("arm");
        tree.add("tå");
        tree.add("tann");

		tree.printTree();

	}
}
