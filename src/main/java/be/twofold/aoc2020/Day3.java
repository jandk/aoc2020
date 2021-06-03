package be.twofold.aoc2020;

import java.util.*;

public class Day3 {

    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day3.txt");

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int count = checkSlope(lines, 3, 1);

        System.out.println(count);
    }

    private static void part2(List<String> lines) {
        long slope11 = checkSlope(lines, 1, 1);
        long slope31 = checkSlope(lines, 3, 1);
        long slope51 = checkSlope(lines, 5, 1);
        long slope71 = checkSlope(lines, 7, 1);
        long slope12 = checkSlope(lines, 1, 2);

        System.out.println(slope11 * slope31 * slope51 * slope71 * slope12);
    }

    private static int checkSlope(List<String> lines, int right, int down) {
        int length = lines.get(0).length();

        int col = 0;
        int count = 0;
        for (int i = down; i < lines.size(); i += down) {
            col = (col + right) % length;
            if (lines.get(i).charAt(col) == '#') {
                count++;
            }
        }
        return count;
    }

}
