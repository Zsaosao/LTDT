package graph;

public abstract class AGraph {

    protected int[][] adjMatrix;

    public abstract void addEdge(int v1, int v2);

    public abstract void removeEdge(int v1, int v2);

    public abstract int degree(int v);

    public abstract int edges();

    public abstract boolean isConnected();

    public abstract boolean isConnected(int v1, int v2);

    public abstract boolean isEulerian();

}
