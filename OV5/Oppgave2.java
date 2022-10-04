import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class HashNode {
	String data;
	HashNode next;

	HashNode (String data, HashNode next) {
        this.data = data;
        this.next = next;
    }

	public String getData() {
		return data;
	}
	public HashNode getNext() {
		return next;
	}

}

public class Oppgave1 {

	HashNode hashTable[];
	int collisions;

	public createHashTable(int length) {
		arr = new HashNode[length/2]
		collisions = 0;
	}

	public void insertIntoHashTable(String word) {
		int index = hashFunction(word);
		HashNode node = new HashNode(word, null);

		if (hashTable[index] == null) {
			hashTable[index] = node;
		} else {
			HashNode temp = hashTable[index];
			node.next = temp;
			hashTable[index] = node;
		}
	}

	public int hashFunc(String word) {
		int sum = 0;
		int counter = 1;
		for (char c : word.toCharArray()) {
			sum += c * counter;
			counter++;
		}
		return sum % hashTable.length;
	}

	public static void main(String[] args) {
//															HUSK Å ENDRE PÅ KOK!!!!!!!!!!!
// ----------------------------------------------------------------------------------------------------------------------------------------
		ArrayList<String> temp = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("./navn.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				temp.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
// ----------------------------------------------------------------------------------------------------------------------------------------





	}
}
