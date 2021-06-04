package be.twofold.aoc2020;

import java.util.*;
import java.util.regex.*;

public class Day7 {
    private static final Pattern FullPattern = Pattern.compile("([\\w ]+) bags contain (.+)");
    private static final Pattern PartPattern = Pattern.compile("(\\d+) ([\\w ]+) bags?");

    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day7.txt");

        Map<String, Map<String, Integer>> adj = new HashMap<>();

        for (String line : lines) {
            Matcher fullMatcher = FullPattern.matcher(line);
            if (!fullMatcher.matches()) {
                throw new UnsupportedOperationException();
            }

            String source = fullMatcher.group(1);
            String content = fullMatcher.group(2);
            Matcher partMatcher = PartPattern.matcher(content);
            while (partMatcher.find()) {
                int amount = Integer.parseInt(partMatcher.group(1));
                String target = partMatcher.group(2);
                adj
                    .computeIfAbsent(source, __ -> new HashMap<>())
                    .put(target, amount);
            }
        }

        part1(adj);
        part2(adj);
    }

    private static void part1(Map<String, Map<String, Integer>> adj) {
        Map<String, Map<String, Integer>> rev = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> e1 : adj.entrySet()) {
            for (Map.Entry<String, Integer> e2 : e1.getValue().entrySet()) {
                String source = e1.getKey();
                String target = e2.getKey();
                int amount = e2.getValue();
                rev
                    .computeIfAbsent(target, __ -> new HashMap<>())
                    .put(source, amount);
            }
        }

        Set<String> visited = new HashSet<>();
        dfs(rev, visited, "shiny gold");
        System.out.println(visited.size() - 1);
    }

    private static void dfs(Map<String, Map<String, Integer>> adj, Set<String> visited, String node) {
        visited.add(node);
        for (String e : adj.getOrDefault(node, Map.of()).keySet()) {
            if (!visited.contains(e)) {
                dfs(adj, visited, e);
            }
        }
    }

    private static void part2(Map<String, Map<String, Integer>> adj) {
        int count = countBags(adj, "shiny gold", 1);
        System.out.println(count - 1);
    }

    private static int countBags(Map<String, Map<String, Integer>> adj, String bag, int amount) {
        int sum = amount;
        for (Map.Entry<String, Integer> e : adj.getOrDefault(bag, Map.of()).entrySet()) {
            sum += countBags(adj, e.getKey(), e.getValue()) * amount;
        }
        return sum;
    }
}
