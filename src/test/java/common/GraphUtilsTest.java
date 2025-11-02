package common;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUtilsTest {

    @Test
    void validate_ok_on_tasksJson() throws Exception {
        Graph g = GraphIO.load(Path.of("data", "tasks.json"));
        assertDoesNotThrow(() -> GraphUtils.validate(g));
        int sumAdj = g.adj.stream().mapToInt(list -> list.size()).sum();
        assertEquals(g.edgeCount(), sumAdj);
    }
}
