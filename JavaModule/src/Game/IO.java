package Game;

import java.util.Scanner;

public class IO {
    private static final Scanner scanner = new Scanner(System.in);

    public static void println(String s) {
        System.out.println(s);
    }

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
}

