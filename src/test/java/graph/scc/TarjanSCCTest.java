package graph.scc;

import common.Graph;
import common.GraphIO;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCTest {

    @Test
    void scc_on_tasksJson() throws Exception {
        Graph g = GraphIO.load(Path.of("data", "tasks.json"));
        TarjanSCC t = new TarjanSCC();
        SCCResult r = t.find(g);
        assertEquals(6, r.components.size());

        Set<Integer> cyc = new HashSet<>(Set.of(1,2,3));
        boolean foundCycle = r.components.stream().anyMatch(list -> new HashSet<>(list).equals(cyc));
        assertTrue(foundCycle, "SCC {1,2,3} must exist");


        Set<Integer> singletons = new HashSet<>(Set.of(0,4,5,6,7));
        long singlesFound = r.components.stream().filter(list -> list.size()==1 && singletons.contains(list.get(0))).count();
        assertEquals(5, singlesFound);
    }
}
