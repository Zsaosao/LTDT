package graphMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class AGraph {

    protected int[][] adjMatrix;

    public AGraph(int n) {
        this.adjMatrix = new int[n][n];
    }

    public AGraph(String path) {
        this.loadData(path);
    }

    public abstract void addEdge(int v1, int v2);

    public abstract void removeEdge(int v1, int v2);

    public abstract int degree(int v);

    public abstract int edges();

    public abstract boolean isConnected();

    public abstract boolean isConnected(int v1, int v2);

    public abstract boolean isEulerian();

    public abstract boolean isHalfEulerian();

    public abstract boolean isBipartite();

    public abstract List<Integer> eulerian();

    public void printGraph() {
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                System.out.print(this.adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void loadData(String path) {
        try {
            // BufferedReader bf = new BufferedReader(new FileReader("src\\data.txt"));
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String line = bf.readLine();
            int len = Integer.parseInt(line);
            this.adjMatrix = new int[len][len];
            int i = 0;
            line = bf.readLine();
            while (line != null) {
                String[] data = line.split(" ");

                for (int j = 0; j < data.length; j++) {
                    if (Integer.parseInt(data[j]) > 0) {
                        this.adjMatrix[i][j] = Integer.parseInt(data[j]);
                    }
                }
                i++;

                line = bf.readLine();
            }
            bf.close();

        }  catch (IOException e) {
               e.printStackTrace();
        }

    }

}
