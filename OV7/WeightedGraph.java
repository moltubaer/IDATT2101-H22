import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Node {
    Vertex nodeVertex;
    Last data;
    int index;
    int currentIndex;

    Node(int i, int c) {
        this.index = i;
        this.currentIndex = i;
    }    
}

class Vertex {
    Vertex nextVertex;
    Node to;
    int weight;
    
    public Vertex(Node node, Vertex nextVertex, int weight) {
        this.to = node;
        this.nextVertex = nextVertex;
        this.weight = weight;
    }
}

class Last {
    int dist;
    Node last;
    static int infinity = 1000000000;
    
    Last() {
        this.dist = infinity;
    }
}

class WeightedGraph {
    int nodesNum;
    int vertexNum;
    Node [] nodeList;
    PriorityQueue<Node> queue;

    Wgraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.nodesNum = Integer.parseInt(st.nextToken());  
        this.nodeList = new Node[nodesNum]; 
        for(int i = 0; i < nodesNum; i++) {
            nodeList[i] = new Node(i,i); 
        }

        int vertexNum = Integer.parseInt(st.nextToken());
        for (int j = 0; j < vertexNum; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            Vertex vertex = new Vertex(nodeList[to], nodeList[from].nodeVertex, weight);
            nodeList[from].nodeVertex = vertex;
        }

        for (int i = 0; i < nodesNum; i++) {
            nodeList[i].data = new Last();
        }
    }

    void shorten(Node node, Vertex vertex) {
        Last nodeLast = node.data;
        Last verNodeLast = vertex.to.data;
        if(verNodeLast.dist > nodeLast.dist + vertex.weight) {
            verNodeLast.dist = nodeLast.dist + vertex.weight;
            verNodeLast.last = node;
            this.queue.remove(vertex.to);
            this.queue.add(vertex.to);
        }
    }
    
    void dijkstra(Node startNode) {
        startNode.data.dist = 0;
        this.queue = new PriorityQueue<>(this.nodesNum, (a,b) ->(a.data).dist - (b.data).dist);
        for (int i = 0; i < nodesNum; i++) {
            this.queue.add(nodeList[i]);
        }   

        for(int i = this.nodesNum; i > 1; i--) {
            Node node = this.queue.poll();
            for(Vertex vertex = node.nodeVertex; vertex != null; vertex = vertex.nextVertex) {
                shorten(node, vertex);
            }
        }
    }

    public static void main(String [] args) {
        String name = "vg1";
        Wgraph graph =null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(name)));
            graph = new Wgraph(br);
        } catch (IOException error) {
            error.printStackTrace();
        }

        int startNode = 1;
        Node node = graph.nodeList[startNode];
        graph.dijkstra(node);

        System.out.println(name +" with start in " + startNode);
        System.out.println();
        System.out.format("%-7s%-7s%-7s%n", "Node","From node", "Distance");
        for (int i = 0; i < graph.nodesNum; i++) {
            if(graph.nodeList[i].data.dist != Last.infinity) {
                String from = (graph.nodeList[i].index == startNode) ? "Start": String.valueOf(graph.nodeList[i].data.last.index);
                System.out.format("%-7s%-7s%-7s%n",graph.nodeList[i].index, from, graph.nodeList[i].data.dist);
            } else {
                System.out.format("%-7s%-7s%-7s%n",graph.nodeList[i].index,"","Not reached");
            }
        }
    }
}