package graphMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class UndirectedGraph extends AGraph {

    public UndirectedGraph(int n) {
        super(n);
    }

    public UndirectedGraph(String path) {
        super(path);
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
                if (this.adjMatrix[v][i] > 0 && color[i] == -1) {
                    color[i] = 1 - color[v];
                    queue.add(i);
                } else if (this.adjMatrix[v][i] > 0 && color[i] == color[v]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isHalfEulerian() {
        if (!this.isConnected()) {
            return false;
        }
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

    public List<Integer> euler2() {
        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        int[][] temp = new int[this.adjMatrix.length][this.adjMatrix.length];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                temp[i][j] = this.adjMatrix[i][j];
            }
        }
        UndirectedGraph undirectedGraph = new UndirectedGraph(this.adjMatrix.length);
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
        UndirectedGraph undirectedGraph = new UndirectedGraph(this.adjMatrix.length);
        undirectedGraph.adjMatrix = temp;
        List<Integer> add = new ArrayList<Integer>();
        for (int i = 0; i < undirectedGraph.adjMatrix.length; i++) {
            if (undirectedGraph.degree(i) % 2 == 1) {
                add.add(i);
            }
        }
        undirectedGraph.addEdge(add.get(0), add.get(1));
        List<Integer> path = undirectedGraph.euler2();
        for (int i = 0; i < path.size(); i++) {
            // System.out.println(path.get(i) + "" + path.get(i+1) + " " + add.get(0) + "" +
            // add.get(1));
            if (path.get(i) == add.get(0) && path.get(i + 1) == add.get(1)) {
                List<Integer> path1 = path.subList(0, i);
                List<Integer> path2 = path.subList(i, path.size() - 2);
                Collections.reverse(path1);
                Collections.reverse(path2);
                System.out.println("" + path2);
                // path.clear();
                // path.addAll(0, path1);
                // path.addAll(path.size()-1,path2);

            }
        }
        return path;
    }

}
