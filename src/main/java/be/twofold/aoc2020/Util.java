package be.twofold.aoc2020;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static List<String> readResouce(String name) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream(name)))) {
            return reader.lines()
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
