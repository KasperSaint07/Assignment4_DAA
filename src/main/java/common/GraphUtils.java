package common;

import java.util.Arrays;

public final class GraphUtils {
    private GraphUtils() {}

    public static void validate(Graph g) {
        if (g == null) throw new IllegalArgumentException("Graph is null");
        if (!g.directed) {

            System.err.println("[WARN] Graph is undirected; SCC/Topo предполагают directed.");
        }
        int sumAdj = g.adj.stream().mapToInt(list -> list.size()).sum();
        if (sumAdj != g.edgeCount()) {
            throw new IllegalArgumentException("Adjacency size (" + sumAdj + ") != edgeCount (" + g.edgeCount() + ")");
        }
        for (Edge e : g.edges) {
            if (e.u < 0 || e.u >= g.n || e.v < 0 || e.v >= g.n) {
                throw new IllegalArgumentException("Edge endpoint out of range: " + e);
            }
        }
    }

    public static void printSummary(Graph g) {
        System.out.println(g.toString());
        int[] out = new int[g.n];
        for (int u = 0; u < g.n; u++) out[u] = g.adj.get(u).size();
        System.out.println("Out-degrees: " + Arrays.toString(out));
        if (g.source != null) {
            System.out.println("Source: " + g.source);
        }
        if (g.weightModel != null) {
            System.out.println("Weight model: " + g.weightModel);
        }
    }
}
