import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class WeightedGraph {
    public static void main(String [] args) {
        String name = "vg1";
        Wgraph graph =null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(name)));
            graph = new Wgraph(br);
        } catch (IOException error) {
            error.printStackTrace();
        }

    }
}