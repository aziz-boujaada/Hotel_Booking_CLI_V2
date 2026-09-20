package Helpers;

import java.util.Scanner;

public class InputsReader {

    Scanner scanner = new Scanner(System.in);

    public String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
            System.out.println("This field is required.");
        }
    }

    public int readIntWithRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                int choice = Integer.parseInt(value.trim());
                if (choice < min || choice > max) {
                    throw new IllegalArgumentException("Choice must be between " + min + " and " + max + ".");
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return Double.parseDouble(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                int parsed = Integer.parseInt(value.trim());
                if (parsed <= 0) {
                    throw new IllegalArgumentException("Value must be greater than zero.");
                }
                return parsed;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
