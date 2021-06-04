package be.twofold.aoc2020;

import java.util.*;

public class Day9 {
    public static void main(String[] args) {
        long[] numbers = Util.readResouce("/day9.txt").stream()
            .mapToLong(Long::parseLong)
            .toArray();

        Queue<Long> queue = new ArrayDeque<>();
        for (int i = 0; i < 25; i++) {
            queue.add(numbers[i]);
        }

        long result = part1(numbers, queue);
        System.out.println(result);
        System.out.println(part2(numbers, result));
    }

    private static long part1(long[] numbers, Queue<Long> queue) {
        for (int i = 25; i < numbers.length; i++) {
            long number = numbers[i];
            if (!findSum(queue, number)) {
                return number;
            }
            queue.remove();
            queue.add(number);
        }
        throw new UnsupportedOperationException();
    }

    private static long part2(long[] numbers, long number) {
        for (int i = 0; i < numbers.length; i++) {
            long sum = 0;
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                if (sum > number) {
                    sum = 0;
                    break;
                } else if (sum == number) {
                    long min = numbers[i];
                    long max = numbers[i];
                    for (int k = i + 1; k < j; k++) {
                        min = Math.min(min, numbers[k]);
                        max = Math.max(max, numbers[k]);
                    }
                    return min + max;
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    private static boolean findSum(Queue<Long> queue, long number) {
        for (long n1 : queue) {
            for (long n2 : queue) {
                if (n1 + n2 == number) {
                    return true;
                }
            }
        }
        return false;
    }
}
