package be.twofold.aoc2020;

import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day11.txt");

        int sizeY = lines.size();
        int sizeX = lines.get(0).length();

        BitSet layout = new BitSet();
        for (int y = 0, o = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++, o++) {
                layout.set(o, lines.get(y).charAt(x) == 'L');
            }
        }

        Seats seats = new Seats(sizeX, sizeY, layout);
        System.out.println(part1(seats));
    }

    private static int part1(Seats seats) {
        while (true) {
            System.out.println(seats);
            if (!seats.simulate()) {
                break;
            }
        }

        return seats.getTakenSeats();
    }

    private static final class Seats {
        private final int sizeX;
        private final int sizeY;
        private final BitSet layout;
        private BitSet occupation = new BitSet();

        public Seats(int sizeX, int sizeY, BitSet layout) {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.layout = layout;
        }

        public int getTakenSeats() {
            return occupation.cardinality();
        }

        public boolean simulate() {
            BitSet newOccupation = (BitSet) occupation.clone();
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    int index = index(x, y);
                    if (layout.get(index)) {
                        int n = checkSeats(x, y);
                        if (n == 0) {
                            newOccupation.set(index);
                        } else if (n >= 4) {
                            newOccupation.clear(index);
                        }
                    }
                }
            }
            if (newOccupation.equals(occupation)) {
                return false;
            }
            occupation = newOccupation;
            return true;
        }

        private int index(int x, int y) {
            return y * sizeX + x;
        }

        private int checkSeats(int x, int y) {
            int n = 0;
            for (int yy = y - 1; yy <= y + 1; yy++) {
                for (int xx = x - 1; xx <= x + 1; xx++) {
                    if (yy != y || xx != x) {
                        n += checkSeat(xx, yy) ? 1 : 0;
                    }
                }
            }
            return n;
        }

        private boolean checkSeat(int x, int y) {
            if (x < 0 || y < 0 || x >= sizeX || y >= sizeY) {
                return false;
            }
            return occupation.get(index(x, y));
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int y = 0, o = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++, o++) {
                    if (!layout.get(o)) {
                        builder.append('.');
                    } else {
                        builder.append(occupation.get(o) ? '#' : 'L');
                    }
                }
                builder.append('\n');
            }
            return builder.toString();
        }
    }

}
