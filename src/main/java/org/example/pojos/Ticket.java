package org.example.pojos;

import java.util.UUID;

public class Ticket implements SaleableItem, Serializable{
    private long id;
    private String description;
    private double price;

    public Ticket() {}
    public Ticket(long id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
    public Ticket(String description, double price) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void sellItem() {
        System.out.println("Sold ticket: " + description + " for $" + price);
    }

    @Override
    public String toString() {
        return "Ticket-> [" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ']';
    }
}
