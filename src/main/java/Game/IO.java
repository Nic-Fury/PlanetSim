package Game;

import java.util.Scanner;

public class IO {
    private static final Scanner scanner = new Scanner(System.in);

//    normalSpeed
    public static void println(String s) {System.out.println(s);}
//    public static void println(String s) {printlnSlow(s);}
    public static void println(int delay, String s) {printlnSlow(delay, s);}

    public static void println() {
        System.out.println();
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static String readln(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prints a string character by character with a delay between each character,
     * followed by a newline – creates a "typewriter" effect.
     *
     * @param s     the string to print
     * @param delayMs delay in milliseconds between each character (e.g. 40)
     */
    public static void printlnSlowByChar(int delayMs, String s) {
        for (char c : s.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(s.substring(s.indexOf(c)));
                return;
            }
        }
        System.out.println();
    }

    /**
     * Convenience overload with a default delay of 40 ms per character.
     */
    public static void printlnSlowByChar(String s) {
        printlnSlowByChar(60,s);
    }

    /**
     * Prints multiple lines one by one with a delay between each line.
     *
     * @param lines   the lines to print
     * @param delayMs delay in milliseconds between each line
     */
    public static void printlnSlow(int delayMs, String... lines) {
        for (String line : lines) {

            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println(line);
        }
    }

    /**
     * Convenience overload with a default delay of 500 ms per line.
     */
    public static void printlnSlow(String... lines) {
        printlnSlow(1000, lines);
    }


    public static void delay() {
        delay(1000);
    }

    public static void delay(int delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
