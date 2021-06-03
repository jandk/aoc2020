package be.twofold.aoc2020;

public class Day1 {

    public static void main(String[] args) {
        int[] numbers = Util.readResouce("/day1.txt").stream()
            .mapToInt(Integer::parseInt)
            .toArray();

        part1(numbers);
        part2(numbers);
    }

    private static void part1(int[] numbers) {
        for (int n1 : numbers) {
            for (int n2 : numbers) {
                if (n1 + n2 == 2020) {
                    System.out.println(n1 * n2);
                    return;
                }
            }
        }
    }

    private static void part2(int[] numbers) {
        for (int n1 : numbers) {
            for (int n2 : numbers) {
                for (int n3 : numbers) {
                    if (n1 + n2 + n3 == 2020) {
                        System.out.println(n1 * n2 * n3);
                        return;
                    }
                }
            }
        }
    }

}
