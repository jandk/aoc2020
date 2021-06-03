package be.twofold.aoc2020;

import java.util.*;
import java.util.stream.*;

public class Day6 {
    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day6.txt");
        lines.add("");

        List<List<String>> answers = new ArrayList<>();
        List<String> groupAnswers = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                answers.add(List.copyOf(groupAnswers));
                groupAnswers.clear();
            } else {
                groupAnswers.add(line);
            }
        }

        part1(answers);
        part2(answers);
    }

    private static void part1(List<List<String>> answers) {
        long answerCount = answers.stream()
            .mapToLong(groupAnswers -> groupAnswers.stream()
                .flatMapToInt(String::chars)
                .distinct().count())
            .sum();

        System.out.println(answerCount);
    }

    private static void part2(List<List<String>> answers) {
        long answerCount = answers.stream()
            .mapToLong(groupAnswers -> groupAnswers.stream()
                .map(s -> s.chars().boxed().collect(Collectors.toSet()))
                .reduce((s1, s2) -> {
                    s1.retainAll(s2);
                    return s1;
                })
                .map(Set::size)
                .orElse(0))
            .sum();

        System.out.println(answerCount);
    }
}
