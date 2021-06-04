package be.twofold.aoc2020;

import java.util.*;

public class Day9 {
    public static void main(String[] args) {
        long[] numbers = Util.readResouce("/day9.txt").stream()
            .mapToLong(Long::parseLong)
            .toArray();

        long result = part1(numbers);
        System.out.println(result);
        System.out.println(part2(numbers, result));
    }

    private static long part1(long[] numbers) {
        for (int i = 25; i < numbers.length; i++) {
            long number = numbers[i];
            if (!findSum(numbers, i, number)) {
                return number;
            }
        }
        throw new UnsupportedOperationException();
    }

    private static long part2(long[] numbers, long number) {
        for (int i = 0; i < numbers.length; i++) {
            long sum = 0;
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                if (sum > number) {
                    break;
                } else if (sum == number) {
                    LongSummaryStatistics statistics = Arrays
                        .stream(numbers, i, j)
                        .summaryStatistics();

                    long min = statistics.getMin();
                    long max = statistics.getMax();
                    return min + max;
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    private static boolean findSum(long[] numbers, int toIndex, long number) {
        for (int i = toIndex - 25; i < toIndex; i++) {
            for (int j = toIndex - 25; j < toIndex; j++) {
                if (numbers[i] + numbers[j] == number) {
                    return true;
                }
            }
        }
        return false;
    }
}
