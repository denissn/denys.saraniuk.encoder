package ua.javarush.encoder.utility;

import java.util.Scanner;

public class ConsoleProvider {
    private final Scanner scanner = new Scanner(System.in);
    public String read(){
        return scanner.nextLine();
    }

    public int readInt(){
        return scanner.nextInt();
    }

    public void print(String message) {
        System.out.println(message);
    }
}
