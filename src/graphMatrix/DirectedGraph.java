package graphMatrix;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DirectedGraph extends AGraph {

    public DirectedGraph(int n) {
        super(n);

    }

    @Override
    public void addEdge(int v1, int v2) {
        this.adjMatrix[v1][v2] = 1;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        this.adjMatrix[v1][v2] = 0;
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
        if (!this.isConnected()) {
            return false;
        }
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.inDegree(i) != this.outDegree(i)) {
                return false;
            }
        }
        return true;
    }

    public int inDegree(int v) {
        int degree = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            degree += this.adjMatrix[i][v];
        }
        return degree;
    }

    public int outDegree(int v) {
        int degree = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            degree += this.adjMatrix[v][i];
        }
        return degree;
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
                if (this.adjMatrix[v][i] == 1) {
                    if (color[i] == -1) {
                        color[i] = 1 - color[v];
                        queue.add(i);
                    } else if (color[i] == color[v]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isHalfEulerian() {
        int in = 0;
        int out = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.inDegree(i) - this.outDegree(i) == 1) {
                in++;
            } else if (this.outDegree(i) - this.inDegree(i) == 1) {
                out++;
            } else if (this.inDegree(i) != this.outDegree(i)) {
                return false;
            }
        }
        return in == 1 && out == 1;
    }

    @Override
    public List<Integer> eulerian() {
        if (!this.isEulerian() && !this.isHalfEulerian()) {
            return null;
        }
        List<Integer> path = new LinkedList<>();

        int[][] temp = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                temp[i][j] = this.adjMatrix[i][j];
            }
        }
        int v = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            if (this.outDegree(i) - this.inDegree(i) == 1) {
                v = i;
                break;
            }
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        while (!stack.isEmpty()) {
            v = stack.peek();
            int i;
            for (i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] == 1) {
                    break;
                }
            }
            if (i == this.adjMatrix.length) {
                path.add(v);
                stack.pop();
            } else {
                stack.push(i);
                this.adjMatrix[v][i] = 0;
            }
        }
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                this.adjMatrix[i][j] = temp[i][j];
            }
        }
        Collections.reverse(path);
        return path;
    }

}
