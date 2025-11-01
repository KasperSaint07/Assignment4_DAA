package common;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Загрузка графа из JSON по формату tasks.json. */
public class GraphIO {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private static class JsonEdge {
        int u; int v; int w;
    }
    private static class JsonGraph {
        boolean directed;
        int n;
        List<JsonEdge> edges = new ArrayList<>();
        Integer source;
        @SerializedName("weight_model")
        String weightModel;
    }

    public static Graph load(Path path) throws IOException {
        try (Reader r = Files.newBufferedReader(path)) {
            JsonGraph jg = GSON.fromJson(r, JsonGraph.class);
            if (jg == null) throw new IOException("Empty/invalid JSON: " + path);
            if (jg.n < 0) throw new IllegalArgumentException("n must be >= 0");

            List<Edge> edges = new ArrayList<>();
            if (jg.edges != null) {
                for (JsonEdge e : jg.edges) edges.add(new Edge(e.u, e.v, e.w));
            }

            Graph g = new Graph(jg.n, jg.directed, edges, jg.source, jg.weightModel);

            if (!jg.directed) {
                System.err.println("[WARN] JSON 'directed' = false; задание предполагает directed-граф.");
            }
            if (jg.weightModel != null && !"edge".equalsIgnoreCase(jg.weightModel)) {
                System.err.println("[WARN] weight_model='" + jg.weightModel + "'; пока поддерживаем 'edge'.");
            }
            return g;
        }
    }
}
