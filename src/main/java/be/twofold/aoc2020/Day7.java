package be.twofold.aoc2020;

import java.util.*;
import java.util.stream.*;

public class Day7 {
    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day7.txt");

        List<Edge> edges = lines.stream()
            .map(Day7::parseBag)
            .flatMap(bag -> bag.content.entrySet().stream()
                .map(e -> new Edge(bag.color, e.getKey(), e.getValue())))
            .collect(Collectors.toUnmodifiableList());

//        part1(edges);
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
        List<Edge> edges = adj.getOrDefault(edge.target, List.of());
        if (edges.isEmpty()) {
            return edge.amount;
        }

        int sum = edge.amount;
        for (Edge e : edges) {
            sum += countBags(adj, e) * edge.amount;
        }
        return sum;
    }

    private static Bag parseBag(String line) {
        int contain = line.indexOf(" bags contain ");
        String color = line.substring(0, contain);

        String contents = line.substring(contain + " bags contain ".length());
        Map<String, Integer> content = Arrays.stream(contents.split(", "))
            .map(s -> s.substring(0, s.lastIndexOf(' ')))
            .filter(s -> !s.equals("no other"))
            .collect(Collectors.toUnmodifiableMap(
                s -> s.substring(s.indexOf(' ') + 1),
                s -> Integer.parseInt(s, 0, s.indexOf(' '), 10)
            ));

        return new Bag(color, content);
    }

    private static final class Bag {
        private final String color;
        private final Map<String, Integer> content;

        public Bag(String color, Map<String, Integer> content) {
            this.color = color;
            this.content = content;
        }
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
