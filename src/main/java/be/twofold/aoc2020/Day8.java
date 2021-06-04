package be.twofold.aoc2020;

import java.util.*;
import java.util.stream.*;

public class Day8 {
    public static void main(String[] args) {
        List<String> lines = Util.readResouce("/day8.txt");

        List<Instruction> instructions = lines.stream()
            .map(Instruction::parse)
            .collect(Collectors.toList());

//        part1(instructions);
        part2(instructions);
    }

    private static void part1(List<Instruction> instructions) {
        Machine machine = new Machine();
        machine.run(instructions);
        System.out.println(machine.acc);
    }

    private static void part2(List<Instruction> instructions) {
        Machine machine = new Machine();
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            if (!instruction.op.equals("acc")) {
                List<Instruction> newInstructions = changeInstruction(instructions, i);
                if (machine.run(newInstructions)) {
                    System.out.println(machine.acc);
                    return;
                }
            }
        }
    }

    private static List<Instruction> changeInstruction(List<Instruction> instructions, int index) {
        List<Instruction> instructionsCopy = new ArrayList<>(instructions);

        Instruction oldInstruction = instructionsCopy.get(index);
        Instruction newInstruction;
        switch (oldInstruction.op) {
            case "nop":
                newInstruction = new Instruction("jmp", oldInstruction.amount);
                break;
            case "jmp":
                newInstruction = new Instruction("nop", oldInstruction.amount);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        instructionsCopy.set(index, newInstruction);
        return instructionsCopy;
    }

    private static final class Machine {
        private int acc = 0;

        private boolean run(List<Instruction> instructions) {
            reset();
            BitSet visited = new BitSet();
            int pc = 0;

            while (!visited.get(pc)) {
                if (pc >= instructions.size()) {
                    return true;
                }

                visited.set(pc);
                Instruction instruction = instructions.get(pc);
                switch (instruction.op) {
                    case "acc":
                        acc += instruction.amount;
                        pc++;
                        break;
                    case "jmp":
                        pc += instruction.amount;
                        break;
                    case "nop":
                        pc++;
                        break;
                }
            }

            return false;
        }

        private void reset() {
            acc = 0;
        }
    }

    private static final class Instruction {
        private final String op;
        private final int amount;

        private Instruction(String op, int amount) {
            this.op = op;
            this.amount = amount;
        }

        static Instruction parse(String s) {
            String op = s.substring(0, 3);
            int amount = Integer.parseInt(s, 4, s.length(), 10);
            return new Instruction(op, amount);
        }

        @Override
        public String toString() {
            return "Instruction{" +
                "op='" + op + '\'' +
                ", amount=" + amount +
                '}';
        }
    }
}
