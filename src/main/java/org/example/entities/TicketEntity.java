package org.example.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class TicketEntity extends ProductEntity {
    private String author;
    private long isbn;
    private int copies;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    public TicketEntity() {
    }

    public TicketEntity(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public TicketEntity(String title, double price, int copies, long isbn, String description, String author) {
        this.description = description;
        this.price = price;
        this.copies = copies;
        this.isbn = isbn;
        this.author = author;
    }

    @Override
    public void sellCopy() {
        System.out.println(this);
    }

    @Override
    public void edit() {
        System.out.println("Edit ticket text");
        setDescription(getInput(getDescription()));
        System.out.println("Edit ticket price");
        setPrice(getInput(getPrice()));
    }

    @Override
    public void initialize() {
        System.out.println("Enter ticket text");
        setDescription(getInput(getDescription()));
        System.out.println("Edit ticket price");
        setPrice(getInput(getPrice()));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "description='" + description + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}