package graphMatrix;

public class Test {
    public static void main(String[] args) {
        // DirectedGraph directedGraph = new DirectedGraph(4);
        // directedGraph.addEdge(0, 1);
        // directedGraph.addEdge(1, 2);
        // directedGraph.addEdge(2, 0);
        // directedGraph.addEdge(0, 3);

        // directedGraph.printGraph();
        // System.out.println("Is connected: " + directedGraph.isConnected());
        // System.out.println("Degree of 0: " + directedGraph.degree(0));
        // System.out.println("Edges: " + directedGraph.edges());
        // System.out.println("Is Eulerian: " + directedGraph.isEulerian());
        // System.out.println("Is half Eulerian: " + directedGraph.isHalfEulerian());
        // System.out.println("Is bipartite: " + directedGraph.isBipartite());
        // System.out.println("Eulerian path: " + directedGraph.eulerian());

        // UndirectedGraph undirectedGraph = new UndirectedGraph(5);
        // undirectedGraph.addEdge(0, 1);
        // undirectedGraph.addEdge(1, 2);
        // undirectedGraph.addEdge(2, 3);
        // undirectedGraph.addEdge(3, 0);
        // undirectedGraph.addEdge(0, 4);

        // undirectedGraph.printGraph();
        // System.out.println("Is connected: " + undirectedGraph.isConnected());
        // System.out.println("Degree of 1: " + undirectedGraph.degree(1));
        // System.out.println("Edges: " + undirectedGraph.edges());
        // System.out.println("Is Connected: " + undirectedGraph.isConnected());
        // System.out.println("Is Eulerian: " + undirectedGraph.isEulerian());
        // System.out.println("Is half Eulerian: " + undirectedGraph.isHalfEulerian());
        // System.out.println("Is bipartite: " + undirectedGraph.isBipartite());
        // System.out.println("Eulerian path: " + undirectedGraph.eulerian());

        DirectedGraph directedGraph = new DirectedGraph(3);
        directedGraph.loadData();
        directedGraph.printGraph();

        System.out.println(directedGraph.degree(0));

    }
}
