package org.example;

import common.Graph;
import common.GraphIO;
import common.GraphUtils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import graph.scc.TarjanSCC;
import graph.scc.SCCResult;


public class Main {
    public static void main(String[] args) {
        Map<String, String> opts = parseArgs(args);
        String task = opts.getOrDefault("--task", "help");

        if ("help".equals(task) || !opts.containsKey("--file")) {
            printUsage();
            return;
        }

        try {
            Graph g = GraphIO.load(Path.of(opts.get("--file")));
            GraphUtils.validate(g);

            switch (task) {
                case "info" -> {
                    System.out.println("GRAPH SUMMARY");
                    GraphUtils.printSummary(g);
                }
                case "scc" -> {
                    TarjanSCC tarjan = new TarjanSCC();
                    SCCResult s = tarjan.find(g);

                    System.out.println("SCC COUNT: " + s.components.size());
                    for (int i = 0; i < s.components.size(); i++) {
                        var comp = s.components.get(i);
                        System.out.println("  #" + i + " size=" + comp.size() + " -> " + comp);
                    }
                    System.out.println("METRICS: dfsVisits=" + s.dfsVisits + ", dfsEdges=" + s.dfsEdges);
                }
                default -> {
                    System.out.println("Unknown task: " + task);
                    printUsage();
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("""
            Runner
            Usage:
              --file <path to json>   path to dataset (e.g., data/tasks.json)
              --task info|scc|topo|dag-sp|dag-longest
              [--source <id>]         override source from JSON (for DAG-SP)
              [--print all|paths|metrics]
            """);
    }

    private static Map<String,String> parseArgs(String[] args) {
        Map<String,String> m = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a.startsWith("--")) {
                if (i + 1 < args.length && !args[i+1].startsWith("--")) {
                    m.put(a, args[++i]);
                } else {
                    m.put(a, "true");
                }
            }
        }
        return m;
    }
}
