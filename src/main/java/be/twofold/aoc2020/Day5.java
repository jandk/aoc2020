package be.twofold.aoc2020;

import java.util.*;

public class Day5 {
    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day5.txt");

        int[] boardingPasses = lines.stream()
            .mapToInt(Day5::parseBoardingPass)
            .toArray();

        part1(boardingPasses);
        part2(boardingPasses);
    }

    private static void part1(int[] boardingPasses) {
        int max = Arrays.stream(boardingPasses)
            .max()
            .orElseThrow();

        System.out.println(max);
    }

    private static void part2(int[] boardingPasses) {
        BitSet seats = new BitSet();
        for (int boardingPass : boardingPasses) {
            seats.set(boardingPass);
        }

        int start = seats.nextSetBit(0);
        int freeSeat = seats.nextClearBit(start);
        System.out.println(freeSeat);
    }

    private static int parseBoardingPass(String s) {
        int row = 0;
        for (int i = 0; i < 7; i++) {
            row = row << 1 | (s.charAt(i) == 'F' ? 1 : 0);
        }
        row = 127 - row;

        int seat = 0;
        for (int i = 7; i < 10; i++) {
            seat = seat << 1 | (s.charAt(i) == 'R' ? 1 : 0);
        }

        return row * 8 + seat;
    }
}
