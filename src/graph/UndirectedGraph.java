package graph;

import java.util.LinkedList;
import java.util.Queue;

public class UndirectedGraph extends AGraph {

    @Override
    public void addEdge(int v1, int v2) {
        this.adjMatrix[v1][v2] = 1;
        if (v1 != v2) {
            this.adjMatrix[v2][v1] = 1;
        }
    }

    @Override
    public void removeEdge(int v1, int v2) {
        this.adjMatrix[v1][v2] = 0;
        if (v1 != v2) {
            this.adjMatrix[v2][v1] = 0;
        }
    }

    @Override
    public int degree(int v) {
        int degree = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            degree += this.adjMatrix[v][i];
            if (v == i) {
                degree += this.adjMatrix[i][v] * 2;
            }
        }
        return degree;
    }

    @Override
    public int edges() {
        int edges = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            edges += this.degree(i);
        }
        return edges / 2;
    }

    @Override
    public boolean isConnected() {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.adjMatrix.length];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConnected(int v1, int v2) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.adjMatrix.length];
        queue.add(v1);
        visited[v1] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
        return visited[v2];
    }

    @Override
    public boolean isEulerian() {
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.degree(i) % 2 != 0) {
                return false;
            }
        }
        return this.isConnected();
    }

}
