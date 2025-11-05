package org.example.pojos;
import java.util.Scanner;
import java.time.LocalDate;
public abstract class Editable {
    private final Scanner input = new Scanner(System.in);

    public String getInput() {
        return input.nextLine();
    }

    public String getInput(String message) {
        return message;
    }

    public int getInput(int number) {
        return Integer.parseInt(input.nextLine());
    }

    public double getInput(double number) {
        return Double.parseDouble(input.nextLine());
    }

    public boolean getInput(boolean bool) {
        return Boolean.parseBoolean(input.nextLine());
    }

    public LocalDate getInput(LocalDate date) {
        return date;
    }

    public Enum getInput(Enum e) {
        return e;
    }

    public abstract void edit();
    public abstract void initialize();

}
