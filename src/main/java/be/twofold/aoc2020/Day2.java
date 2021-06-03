package be.twofold.aoc2020;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day2 {

    public static void main(String[] args) {
        List<Password> passwords = Util.readResouce("/day2.txt").stream()
            .map(Password::parse)
            .collect(Collectors.toList());

        solve(passwords, Password::check1);
        solve(passwords, Password::check2);
    }

    private static void solve(List<Password> passwords, Predicate<Password> predicate) {
        long count = passwords.stream()
            .filter(predicate)
            .count();

        System.out.println(count);
    }

    private static class Password {
        private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        private final int min;
        private final int max;
        private final char c;
        private final String password;

        public Password(int min, int max, char c, String password) {
            this.min = min;
            this.max = max;
            this.c = c;
            this.password = password;
        }

        public static Password parse(String s) {
            Matcher matcher = PATTERN.matcher(s);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(s);
            }
            return new Password(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                matcher.group(3).charAt(0),
                matcher.group(4)
            );
        }

        private boolean check1() {
            long count = password.chars()
                .filter(c -> c == this.c)
                .count();

            return count >= min && count <= max;
        }

        private boolean check2() {
            return password.charAt(min - 1) == c ^ password.charAt(max - 1) == c;
        }
    }

}
