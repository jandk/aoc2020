package be.twofold.aoc2020;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day4 {

    private static final Pattern HairColor = Pattern.compile("#[\\da-f]{6}");
    private static final Set<String> EyeColor = Set.of("amb", "blu", "brn", "grn", "gry", "hzl", "oth");

    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day4.txt");
        lines.add("");

        List<Map<String, String>> passports = readPassports(lines);

        checkPassports(passports, Day4::checkPassport);
        checkPassports(passports, Day4::checkPassportStrict);
    }

    private static List<Map<String, String>> readPassports(List<String> lines) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> passport = new HashMap<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                result.add(Map.copyOf(passport));
                passport.clear();
            } else {
                passport.putAll(parsePassportLine(line));
            }
        }
        return result;
    }

    private static Map<String, String> parsePassportLine(String s) {
        return Arrays.stream(s.split(" "))
            .map(e -> e.split(":"))
            .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    private static void checkPassports(List<Map<String, String>> passports, Predicate<Map<String, String>> predicate) {
        long count = passports.stream()
            .filter(predicate)
            .count();

        System.out.println(count);
    }


    private static final Set<String> RequiredFields = Set.of("byr", "ecl", "eyr", "hcl", "hgt", "iyr", "pid");

    private static boolean checkPassport(Map<String, String> passport) {
        return passport.keySet().containsAll(RequiredFields);
    }

    private static boolean checkPassportStrict(Map<String, String> passport) {
        if (!passport.keySet().containsAll(RequiredFields)) {
            return false;
        }

        int byr = Integer.parseInt(passport.get("byr"));
        if (byr < 1920 || byr > 2002) return false;

        int iyr = Integer.parseInt(passport.get("iyr"));
        if (iyr < 2010 || iyr > 2020) return false;

        int eyr = Integer.parseInt(passport.get("eyr"));
        if (eyr < 2020 || eyr > 2030) return false;

        String hgt = passport.get("hgt");
        if (!validateHeight(hgt)) return false;

        String hcl = passport.get("hcl");
        if (!HairColor.matcher(hcl).matches()) return false;

        String ecl = passport.get("ecl");
        if (!EyeColor.contains(ecl)) return false;

        String pid = passport.get("pid");
        return pid.length() == 9 && pid.chars().allMatch(Character::isDigit);
    }

    private static boolean validateHeight(String hgt) {
        if (hgt.endsWith("cm")) {
            int cm = Integer.parseInt(hgt, 0, hgt.length() - 2, 10);
            return cm >= 150 && cm <= 193;
        } else if (hgt.endsWith("in")) {
            int in = Integer.parseInt(hgt, 0, hgt.length() - 2, 10);
            return in >= 59 && in <= 76;
        } else {
            return false;
        }
    }

}
