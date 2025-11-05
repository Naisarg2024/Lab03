package org.example.pojos;

import java.util.UUID;

public class Ticket implements SaleableItem, Serializable{
    private long id;
    private String description;
    private double price;

    public Ticket() {
    }

    // random id for POJO usage
    public Ticket(String description, double price) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.description = description;
        this.price = price;
    }

    // create from an existing id
    public Ticket(long id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    // match the field type
    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void sellCopy() {
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
