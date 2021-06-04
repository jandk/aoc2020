package be.twofold.aoc2020;

import java.util.*;
import java.util.stream.*;

public class Day10 {

    public static void main(String[] args) {
        int[] adapters = Util.readResouce("/day10.txt").stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();

        System.out.println(part1(adapters));
        System.out.println(part2(adapters));
    }

    private static int part1(int[] adapters) {
        int count1s = 0;
        int count3s = 1;

        int lastAdapter = 0;
        for (int adapter : adapters) {
            if ((adapter - lastAdapter) == 3) count3s++;
            if ((adapter - lastAdapter) == 1) count1s++;
            lastAdapter = adapter;
        }

        return count1s * count3s;
    }

    private static long part2(int[] adapters) {
        int start = adapters[adapters.length - 1] + 3;
        Set<Integer> joltages = Arrays.stream(adapters).boxed().collect(Collectors.toSet());
        joltages.add(start);

        return new Day10().calculate(joltages, start);
    }

    private final Map<Integer, Long> cache = new HashMap<>();

    private long calculate(Set<Integer> adapters, int start) {
        Long cached = cache.get(start);
        if (cached != null) {
            return cached;
        } else if (start == 0) {
            return 1;
        } else if (!adapters.contains(start)) {
            cache.put(start, 0L);
            return 0;
        }

        long result1 = calculate(adapters, start - 1);
        long result2 = calculate(adapters, start - 2);
        long result3 = calculate(adapters, start - 3);
        long endresult = result1 + result2 + result3;
        cache.put(start, endresult);
        return endresult;
    }

}
