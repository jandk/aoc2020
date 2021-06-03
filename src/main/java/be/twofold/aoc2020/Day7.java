package be.twofold.aoc2020;

import java.util.*;
import java.util.stream.*;

public class Day7 {
    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day7.txt");

        List<Edge> edges = lines.stream()
            .flatMap(line -> parseBag(line).stream())
            .collect(Collectors.toUnmodifiableList());

        part1(edges);
        part2(edges);
    }

    private static void part1(List<Edge> edges) {
        Map<String, Set<String>> adj = new HashMap<>();
        edges.forEach(edge -> adj
            .computeIfAbsent(edge.target, __ -> new HashSet<>())
            .add(edge.source));

        Set<String> visited = new HashSet<>();
        dfs(adj, visited, "shiny gold");
        System.out.println(visited.size() - 1);
    }

    private static void dfs(Map<String, Set<String>> adj, Set<String> visited, String node) {
        visited.add(node);
        for (String n : adj.getOrDefault(node, Set.of())) {
            if (!visited.contains(n)) {
                dfs(adj, visited, n);
            }
        }
    }

    private static void part2(List<Edge> edges) {
        Map<String, List<Edge>> adj = edges.stream()
            .collect(Collectors.groupingBy(edge -> edge.source));

        Edge start = new Edge(null, "shiny gold", 1);

        int count = countBags(adj, start);
        System.out.println(count - 1);
    }

    private static int countBags(Map<String, List<Edge>> adj, Edge edge) {
        int sum = edge.amount;
        for (Edge e : adj.getOrDefault(edge.target, List.of())) {
            sum += countBags(adj, e) * edge.amount;
        }
        return sum;
    }

    private static List<Edge> parseBag(String line) {
        int contain = line.indexOf(" bags contain ");
        String color = line.substring(0, contain);

        String[] content = line.substring(contain + " bags contain ".length()).split(", ");

        return Arrays.stream(content)
            .map(s -> s.substring(0, s.lastIndexOf(' ')))
            .filter(s -> !s.equals("no other"))
            .map(s -> new Edge(
                color,
                s.substring(s.indexOf(' ') + 1),
                Integer.parseInt(s, 0, s.indexOf(' '), 10)))
            .collect(Collectors.toUnmodifiableList());
    }

    private static final class Edge {
        private final String source;
        private final String target;
        private final int amount;

        private Edge(String source, String target, int amount) {
            this.source = source;
            this.target = target;
            this.amount = amount;
        }
    }
}
