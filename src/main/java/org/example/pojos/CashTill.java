package org.example.pojos;

public class CashTill {
    private double runningTotal;

    public void showTotal() {
        System.out.printf("Till Total is: $%.2f%n", runningTotal);
    }
    public void sellITem(SaleableItem item) {
        if (item == null) {
            System.out.println("No item provided.");
            return;
        }
        System.out.println("Processing...");
        item.sellCopy();
        runningTotal += item.getPrice();
        System.out.printf("Added $%.2f to till.%n", item.getPrice());
        showTotal();
        System.out.println("End of sale.");
    }
}
