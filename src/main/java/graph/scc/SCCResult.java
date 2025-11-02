package graph.scc;

import java.util.ArrayList;
import java.util.List;

public class SCCResult {
    public final List<List<Integer>> components = new ArrayList<>();
    public final int[] compId;
    public long dfsVisits = 0;
    public long dfsEdges  = 0;

    public SCCResult(int n) {
        this.compId = new int[n];
        for (int i = 0; i < n; i++) compId[i] = -1;
    }
}
