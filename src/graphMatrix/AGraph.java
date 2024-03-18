package graphMatrix;

import java.util.List;

public abstract class AGraph {

    protected int[][] adjMatrix;

    public AGraph(int n) {
        this.adjMatrix = new int[n][n];
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

}
