package graphMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DirectedGraph extends AGraph {

    public DirectedGraph(int n) {
        super(n);

    }

    public DirectedGraph(String path) {
        super(path);

    }

    @Override
    public void addEdge(int v1, int v2) {
        this.adjMatrix[v1][v2]++;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        this.adjMatrix[v1][v2]--;
    }

    @Override
    public int degree(int v) {
        return inDegree(v) + outDegree(v);
    }

    @Override
    public int edges() {
        int edges = 0;
        for (int i = 0; i < this.adjMatrix.length; i++) {
            edges += this.degree(i);
        }
        return edges / 2;
    }

    public int[][] diagonalSymmetry() {
        int tempDiagonalSymmetry[][] = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                tempDiagonalSymmetry[i][j] = this.adjMatrix[i][j];

            }
        }
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (tempDiagonalSymmetry[i][j] > 0) {
                    tempDiagonalSymmetry[j][i] = tempDiagonalSymmetry[i][j];
                } else if (tempDiagonalSymmetry[j][i] > 0) {
                    tempDiagonalSymmetry[i][j] = tempDiagonalSymmetry[j][i];
                }
            }
        }
        return tempDiagonalSymmetry;
    }

    public boolean isConnectedWeak() {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.adjMatrix.length];
        queue.add(0);
        visited[0] = true;
        int diagonalSymmetry[][] = diagonalSymmetry();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int i = 0; i < diagonalSymmetry.length; i++) {
                if (diagonalSymmetry[v][i] > 0 && !visited[i]) {
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

    public boolean isConnectedStrong() {
        if (!isConnectedWeak()) {
            return false;
        }
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                if (!isConnected(i, j)) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public boolean isEulerian() {
        if (!this.isConnectedWeak()) {
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
                if (this.adjMatrix[v][i] > 0) {
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
        if (!this.isConnectedWeak()) {
            return false;
        }
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
                if (this.adjMatrix[v][i] > 0) {
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

    @Override
    public boolean isConnected() {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.adjMatrix.length];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int i = 0; i < this.adjMatrix.length; i++) {
                if (this.adjMatrix[v][i] > 0 && !visited[i]) {
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
                if (this.adjMatrix[v][i] > 0 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
        return visited[v2];
    }

    public List<Integer> euler2() {
        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        int[][] temp = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                temp[i][j] = this.adjMatrix[i][j];
            }
        }
        DirectedGraph undirectedGraph = new DirectedGraph(this.adjMatrix.length);
        undirectedGraph.adjMatrix = temp;
        int i;

        while (undirectedGraph.edges() > 0) {
            for (i = 0; i < path.size(); i++) {
                if (undirectedGraph.degree(path.get(i)) > 0) {
                    break;
                }
            }
            List<Integer> sub = new ArrayList<Integer>();
            Integer k = i;
            i = path.get(i);
            while (undirectedGraph.degree(i) > 0) {
                sub.add(i);
                for (int j = 0; j < undirectedGraph.adjMatrix.length; j++) {
                    if (undirectedGraph.adjMatrix[i][j] > 0) {
                        undirectedGraph.removeEdge(i, j);
                        i = j;
                        break;
                    }
                }
            }
            int index = path.indexOf(k);
            path.addAll(index, sub);
        }
        return path;
    }

    public List<Integer> HalfEulerian2() {
        if (!this.isHalfEulerian()) {
            return null;
        }
        int[][] temp = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                temp[i][j] = this.adjMatrix[i][j];
            }
        }
        DirectedGraph directedGraph = new DirectedGraph(this.adjMatrix.length);
        directedGraph.adjMatrix = temp;
        List<Integer> add = new ArrayList<Integer>();
        for (int i = 0; i < directedGraph.adjMatrix.length; i++) {
            if (directedGraph.degree(i) % 2 == 1) {
                add.add(i);
            }
        }

        directedGraph.addEdge(add.get(0), add.get(1));
        List<Integer> path = directedGraph.euler2();
        List<Integer> pathResult = new ArrayList<Integer>();

        for (int i = 0; i < path.size(); i++) {
            if ((path.get(i) == add.get(0) && path.get(i + 1) == add.get(1))
                    || (path.get(i) == add.get(1) && path.get(i + 1) == add.get(0))) {
                List<Integer> path1 = path.subList(1, i);
                List<Integer> path2 = path.subList(i, path.size());
                pathResult.addAll(0, path2);
                pathResult.addAll(path2.size(), path1);
                break;

            }
        }
        return pathResult;
    }

}
