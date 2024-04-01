package graphMatrix;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UndirectedGraph extends AGraph {

    public UndirectedGraph(int n) {
        super(n);
        // TODO Auto-generated constructor stub
    }

    public UndirectedGraph(String path) {
        super(path);
        // TODO Auto-generated constructor stub
    }

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
                degree += this.adjMatrix[i][v];
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
        if (!this.isConnected()) {
            return false;
        }
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.degree(i) % 2 != 0) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean isBipartite() {
        int[] color = new int[this.adjMatrix.length];
        for (int i = 0; i < color.length; i++) {
            color[i] = -1;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        color[0] = 1;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] == 1 && color[i] == -1) {
                    color[i] = 1 - color[v];
                    queue.add(i);
                } else if (this.adjMatrix[v][i] == 1 && color[i] == color[v]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isHalfEulerian() {
        int odd = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.degree(i) % 2 != 0) {
                odd++;
            }
        }
        return odd == 2;

    }

    @Override
    public List<Integer> eulerian() {
        if (!this.isEulerian() && !this.isHalfEulerian()) {
            return null;
        }
        List<Integer> path = new LinkedList<>();
        int v = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.degree(i) % 2 != 0) {
                v = i;
                break;
            }
        }
        int[][] temp = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                temp[i][j] = this.adjMatrix[i][j];
            }
        }
        while (this.edges() > 0) {
            int i;
            for (i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] == 1) {
                    break;
                }
            }
            this.removeEdge(v, i);
            path.add(v);
            v = i;
        }
        path.add(v);
        this.adjMatrix = temp;
        return path;

    }

}
