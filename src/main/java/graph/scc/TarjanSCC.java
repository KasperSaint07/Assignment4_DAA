package graph.scc;

import common.Edge;
import common.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** Классический Tarjan SCC с метриками dfsVisits/dfsEdges. */
public class TarjanSCC {
    private Graph g;

    private int time = 0;
    private int[] disc;     // discovery time
    private int[] low;      // lowlink
    private boolean[] inStack;
    private Deque<Integer> st;

    private SCCResult res;

    public SCCResult find(Graph g) {
        this.g = g;
        int n = g.n;
        this.disc = new int[n];
        this.low = new int[n];
        this.inStack = new boolean[n];
        this.st = new ArrayDeque<>();
        this.res = new SCCResult(n);

        for (int i = 0; i < n; i++) {
            disc[i] = 0;
            low[i] = 0;
            inStack[i] = false;
        }

        for (int v = 0; v < n; v++) {
            if (disc[v] == 0) dfs(v);
        }
        return res;
    }

    private void dfs(int u) {
        res.dfsVisits++;
        disc[u] = low[u] = ++time;
        st.push(u);
        inStack[u] = true;

        for (Edge e : g.adj.get(u)) {
            res.dfsEdges++;
            int v = e.v;
            if (disc[v] == 0) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        // корень компоненты?
        if (low[u] == disc[u]) {
            // извлекаем вершины до u включительно
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int x = st.pop();
                inStack[x] = false;
                res.compId[x] = res.components.size();
                comp.add(x);
                if (x == u) break;
            }
            res.components.add(comp);
        }
    }
}
