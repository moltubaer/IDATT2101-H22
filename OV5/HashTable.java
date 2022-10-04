import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class HashNode {
	String data;
	HashNode next;

	HashNode (String data) {
        this.data = data;
    }

	public String getData() {
		return data;
	}

	public void setNext(HashNode next) {
		this.next = next;
	}
}

public class HashTable {
	HashNode[] hashTable;
	int collisions;
	int checksum;
	int tableLength;

	public HashTable(int length) {
		tableLength = length*10/8;
		hashTable = new HashNode[tableLength];
		collisions = 0;
		checksum = 0;
	}

	public double loadFactor(double elements, double length) {
		return elements/length;
	}

	public double numberOfCollision(double collisions, double people) {
		return collisions/people;
	}

	public int hashFunction(String word) {
		int sum = 0;
		int counter = 1;
		for (char c : word.toCharArray()) {
			sum += c * counter;
			counter++;
		}
		return sum % hashTable.length;
	}

	public void insertNodeFirst(String word) {
		int index = hashFunction(word);
		HashNode node = new HashNode(word);

		if (hashTable[index] == null) {
			hashTable[index] = node;
		} else {
			HashNode temp = hashTable[index];
			node.next = temp;
			hashTable[index] = node;
			collisions++;
		}
	}
	
	public HashNode findNode(String word) {
		int index = hashFunction(word);
		HashNode temp = hashTable[index];
		String collisionAlert = "findNode() collision at index " + index + ": ";

		while (!temp.data.equals(word)) {
			collisionAlert += temp.data + " -> ";
			if (temp.next == null) {
				System.out.println(word + " not found");
				return null;
			}
			temp = temp.next;
		}
		System.out.println(collisionAlert + temp.data + " <- Found!");
		return temp;
	}

	public void pushNode(String word) {
		int index = hashFunction(word);
		if (hashTable[index] == null) {
			hashTable[index] = new HashNode(word);
			System.out.println("Added to hashtable at index " + index +  ": " + word);
			checksum++;
		} else {
			HashNode temp = hashTable[index];
			String collisionAlert = "Collision at index " + index + ": ";
			collisions++;
			while (temp.next != null) {
				temp = temp.next;
				collisions++;
				collisionAlert += temp.data + " -> ";
			}
			System.out.println(collisionAlert + word);
			temp.setNext(new HashNode(word));
			checksum++;
		}
	}

	public static void main(String[] args) {
		ArrayList<String> temp = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("./navn.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				temp.add(line);
			}
			br.close();
		} catch (FileNotFoundException element) {
			element.printStackTrace();
		} catch (IOException element) {
			element.printStackTrace();
		}

		HashTable ht = new HashTable(temp.size());
		for (String s : temp) {
			ht.pushNode(s);
		}

		String name1 = "Alexander Moltu";
		String name2 = "Jonas Kosmo";
		System.out.println();
		ht.findNode(name1);
		ht.findNode(name2);
		System.out.println();
		System.out.println("Average amount of collisions per person: " + ht.numberOfCollision(ht.collisions, temp.size()));
		System.out.println("Load factor: " + ht.loadFactor(temp.size(), ht.tableLength));
	}
}
