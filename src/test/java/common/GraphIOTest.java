package common;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class GraphIOTest {
    @Test
    void loadsTasksJson() throws Exception {
        Graph g = GraphIO.load(Path.of("data", "tasks.json"));
        assertNotNull(g);
        assertTrue(g.directed, "graph must be directed");
        assertEquals(8, g.n, "n must be 8");
        assertNotNull(g.source);
        assertEquals(4, g.source);
        assertEquals("edge", g.weightModel);
        assertEquals(7, g.edgeCount(), "edge count must match json");
        assertEquals(7, g.adj.stream().mapToInt(list -> list.size()).sum(), "adj size must match edge count");
    }
}
