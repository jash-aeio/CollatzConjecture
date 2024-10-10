/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package collatzconjecture;

/**
 *
 * @author Jasper
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Comparator;

class CollatzSystem {

    private int number;
    private List<Record> records;

    private static class Record {

        int input;
        int cycles;
        List<Integer> sequence;
        long timestamp;

        public Record(int input, int cycles, List<Integer> sequence) {
            this.input = input;
            this.cycles = cycles;
            this.sequence = sequence;
            this.timestamp = System.currentTimeMillis();
        }
    }

    public CollatzSystem() {
        number = 1;
        records = new ArrayList<>();
    }

    public static int collatzCalculator(int number) {
        if (number % 2 == 0) {
            number /= 2;
        } else {
            number = 3 * number + 1;
        }
        return number;
    }

    public void collatzDisplay() {
        Scanner scanner = new Scanner(System.in);

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter any positive integer: ");
                number = scanner.nextInt();
                if (number > 0) {
                    validInput = true;
                } else {
                    System.out.println("Error: Only positive integers are accepted. Try again!\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid option.\n");
                scanner.next();
            }
        }

        List<Integer> sequence = new ArrayList<>();
        sequence.add(number);
        int cycles = 0;

        System.out.println("\n===========================");
        System.out.println("Collatz Conjecture of #" + sequence.get(0));
        System.out.println("===========================");
        System.out.printf("%-15s%-15s%n", "Cycle", "Result");
        System.out.println("---------------------------");

        while (number != 1) {
            number = collatzCalculator(number);
            cycles++;
            sequence.add(number);

            System.out.printf("%-15d%-15d%n", cycles, number);
        }

        records.add(new Record(sequence.get(0), cycles, sequence));

        System.out.println("==================================");
        System.out.println("Terminal ONE(1) has been reached!");
        System.out.println("==================================");

        boolean nextAction = true;
        while (nextAction) {
            try {
                System.out.println("Try New Number      [1]");
                System.out.println("Back to Main Menu   [0]\n");
                System.out.print("Enter choice: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        collatzDisplay();
                        nextAction = false;
                        break;
                    case 0:
                        nextAction = false;
                        break;
                    default:
                        System.out.println("Error: Please enter a valid option.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid option.\n");
                scanner.next();
            }
        }
    }

    public void displayRecords() {
        if (records.isEmpty()) {
            System.out.println("No records available yet.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean backToMenu = false;
        while (!backToMenu) {
            try {
                System.out.println("\n===========================");
                System.out.println("       ENTRY RECORDS");
                System.out.println("===========================\n");
                System.out.println("  Sort by Number      [1]");
                System.out.println("  Sort by Recent      [2]");
                System.out.println("  Back to Main Menu   [0]\n");
                System.out.print("Enter choice: ");

                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("\n==============");
                        System.out.println("SORT BY NUMBER");
                        records.sort(Comparator.comparingInt(r -> r.input));
                        break;
                    case 2:
                        System.out.println("\n==============");
                        System.out.println("SORT BY RECENT");
                        records.sort((r1, r2) -> Long.compare(r2.timestamp, r1.timestamp));
                        break;
                    case 0:
                        backToMenu = true;
                        continue;
                    default:
                        System.out.println("Error: Please enter a valid option.\n");
                        continue;
                }

                System.out.println("==============================================================");
                System.out.printf("%-10s%-15s%-20s%n", "Number", "Cycles", "Sequence");
                System.out.println("--------------------------------------------------------------");

                for (Record record : records) {
                    System.out.printf("%-10d%-15d%-20s%n", record.input, record.cycles, record.sequence.toString());
                }
                System.out.println("==============================================================");
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid option.\n");
                scanner.next();
            }
        }
    }

    public void displayCollatzGraph() {
        if (records.isEmpty()) {
            System.out.println("No records to display. Please generate a Collatz sequence first.");
            return;
        }

        Record lastRecord = records.get(records.size() - 1);
        List<Integer> sequence = lastRecord.sequence;
        System.out.println("===============================");
        System.out.println("Visual Collatz Conjecture Graph");
        System.out.println("==============================================================");
        int max = sequence.stream().max(Integer::compare).orElse(0);

        for (int num : sequence) {
            int barLength = (int) (((double) num / max) * 50);
            System.out.printf("%d: %s%n", num, "*".repeat(barLength));
        }

        System.out.println("==============================================================");
        Scanner scanner = new Scanner(System.in);
        boolean validOption = false;
        while (!validOption) {
            try {
                System.out.println("Back to Main Menu   [0]\n");
                System.out.print("Enter choice: ");
                int opt = scanner.nextInt();
                if (opt == 0) {
                    validOption = true;
                } else {
                    System.out.println("Error: Please enter a valid option.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.\n");
                scanner.next();
            }
        }
    }

    public void aboutCollatz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=====");
        System.out.println("ABOUT");
        System.out.println("=================================================================================");
        System.out.println("The Collatz Conjecture states that if you take any positive integer n");
        System.out.println("and apply a simple set of rules repeatedly, eventually you will always");
        System.out.println("end up with the number 1.The rules are as follows:  if n is even, divide");
        System.out.println("it by 2; if n is odd, multiply it by 3 and add 1.\n");
        System.out.println("This program is in compliance with the course CS0001 - Discrete Structures.\n");
        System.out.println("Submitted by Jasper C. Bagayan - TN29");
        System.out.println("=================================================================================");
        boolean validOption1 = false;
        while (!validOption1) {
            try {
                System.out.println("Back to Main Menu   [0]\n");
                System.out.print("Enter choice: ");
                int opt1 = scanner.nextInt();
                if (opt1 == 0) {
                    validOption1 = true;
                } else {
                    System.out.println("Error: Please enter a valid option.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.\n");
                scanner.next();
            }
        }
    }
}

public class CollatzConjecture {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CollatzSystem cs = new CollatzSystem();
            boolean menu = true;

            while (menu) {
                System.out.println("========================");
                System.out.println("   COLLATZ CONJECTURE");
                System.out.println("========================");
                System.out.println("       Main Menu\n");
                System.out.println("     Start     [1]");
                System.out.println("     Records   [2]");
                System.out.println("     Graph     [3]");
                System.out.println("     About     [4]");
                System.out.println("     End       [0]\n");
                System.out.print("Enter choice: ");

                try {
                    int opt = scanner.nextInt();
                    switch (opt) {
                        case 1: {
                            cs.collatzDisplay();
                            break;
                        }
                        case 2: {
                            cs.displayRecords();
                            break;
                        }
                        case 3: {
                            cs.displayCollatzGraph();
                            break;
                        }
                        case 4: {
                            cs.aboutCollatz();
                            break;
                        }
                        case 0: {
                            System.out.println("Thank you for using this program! Exiting...");
                            menu = false;
                            break;
                        }
                        default: {
                            System.out.println("Error: Please enter a valid option.\n");
                            break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid option.\n");
                    scanner.next();
                }
            }
        }
    }
}
