import java.io.*;  
import java.util.*;
import java.net.*; 

class Node {		
    int index;

    public Node (int i) {
        this.index = i;
    }	
}

class Graph {
    private int time; 
    private int count;
    private int number;
    private ArrayList<LinkedList<Node>> adj;
    
    Graph(BufferedReader br) throws IOException {
        this.time = 0;
        this.count = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.number = Integer.parseInt(st.nextToken());
        this.adj = new ArrayList<LinkedList<Node>>();

        for(int i = 0; i < number; ++i) {
            adj.add(new LinkedList<Node>());
        }

        int t = Integer.parseInt(st.nextToken());
        for (int j = 0; j < t; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            addEdge(from, to);
        }
    }

    void addEdge(int from,int to) {
        adj.get(from).add(new Node(to));
    }

    void SCCPrint(int nodeNum, int lowest[], int discovery[], boolean stackMember[],  Stack<Integer> stack) {
        int node = -1;
        if (lowest[nodeNum] == discovery[nodeNum]) {
            System.out.print("" + (this.count + 1) + ": ");
            while (node != nodeNum) {
                    node = (int)stack.pop();
                    System.out.print(node + " ");
                    stackMember[node] = false;
                }
            this.count++;
            System.out.println();
        }
    }

    void SCC(){
        int discovery[] = new int[number];
        int lowest[] = new int[number];

        for(int i = 0; i < number; i++){
            discovery[i] = -1;
            lowest[i] = -1;
        }

        boolean stackMember[] = new boolean[number];
        Stack<Integer> stack = new Stack<Integer>();

        for(int i = 0; i < number; i++){
            if (discovery[i] == -1) {
                SCCUtil(i, lowest, discovery, stackMember, stack);
            }
        }
    }

    void SCCUtil(int nodeNum, int lowest[], int discovery[], boolean stackMember[],  Stack<Integer> stack) {
        discovery[nodeNum] = time;
        lowest[nodeNum] = time;
        time++;
        stackMember[nodeNum] = true;
        stack.push(nodeNum);

        int node;
        Iterator<Node> iterator = adj.get(nodeNum).iterator();

        while (iterator.hasNext()) {
            node = iterator.next().index;
            if (discovery[node] == -1) {
                SCCUtil(node, lowest, discovery, stackMember, stack);
                lowest[nodeNum] = Math.min(lowest[nodeNum], lowest[node]);
            } else if (stackMember[node] == true) {
                lowest[nodeNum] = Math.min(lowest[nodeNum], discovery[node]);
            }
        }
        SCCPrint(nodeNum, lowest, discovery, stackMember, stack);
    }

    public static void main(String [] args) {
        // String urlString = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/ø6g1";
        // String urlString = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/ø6g2";
        // String urlString = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/ø6g5";
        String urlString = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/ø6g6";
        Graph graph = null;

        try {
            URL url = new URL(urlString);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            graph = new Graph(br);
        } catch (IOException error) {
            error.printStackTrace();
        }

        System.out.println("Graph acquired from url: " + urlString);
        graph.SCC();
        System.out.println("There are " + graph.count + " Strongly connected components in this graph");
    }
}