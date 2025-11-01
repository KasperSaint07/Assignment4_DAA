package common;

import java.util.ArrayList;
import java.util.List;

/** Направленный граф с весами рёбер, хранит и список рёбер, и adjacency-list. */
public class Graph {
    public final int n;
    public final boolean directed;
    public final List<Edge> edges;
    public final List<List<Edge>> adj;


    public final Integer source;
    public final String weightModel;

    public Graph(int n, boolean directed, List<Edge> edges, Integer source, String weightModel) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        this.n = n;
        this.directed = directed;
        this.edges = edges == null ? new ArrayList<>() : edges;
        this.source = source;
        this.weightModel = weightModel;

        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (Edge e : this.edges) {
            validateEdge(e);
            adj.get(e.u).add(e);
            if (!directed) { // на всякий случай поддержим
                adj.get(e.v).add(new Edge(e.v, e.u, e.w));
            }
        }
    }

    private void validateEdge(Edge e) {
        if (e.u < 0 || e.u >= n || e.v < 0 || e.v >= n) {
            throw new IllegalArgumentException("Edge endpoint out of range: " + e);
        }
    }

    public int edgeCount() { return edges.size(); }

    @Override public String toString() {
        return "Graph(n=" + n + ", directed=" + directed + ", |E|=" + edgeCount() +
                ", source=" + source + ", weightModel=" + weightModel + ")";
    }
}
