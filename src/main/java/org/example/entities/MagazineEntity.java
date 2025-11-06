package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDate;

    @Entity
    public class MagazineEntity extends ProductEntity {
        @Column(name="id", nullable = false)
        private Long id;

        @Column(name ="title")
        private String title;

        @Column(name="copies", nullable=false)
        private int copies;

        @Column(name="price", nullable=false)
        private double price;

        @Column(name="description")
        private String description;

        @Column(name="orderQty")
        private int orderQty;

        @Column(name = "current_issue")
        private LocalDate currentIssue;

        public MagazineEntity() {
        }

        // convenient constructor for testing
        public MagazineEntity(Long id,String title,
                              double price,
                              int copies,
                              String description,
                              int orderQty,
                              LocalDate currentIssue) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.copies = copies;
            this.description = description;
            this.orderQty = orderQty;
            this.currentIssue = currentIssue;
        }

        public int getOrderQty() {
            return orderQty;
        }

        public void setOrderQty(int orderQty) {
            this.orderQty = orderQty;
        }

        public LocalDate getCurrentIssue() {
            return currentIssue;
        }

        public void setCurrentIssue(LocalDate currentIssue) {
            this.currentIssue = currentIssue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCopies() {
            return copies;
        }

        public void setCopies(int copies) {
            this.copies = copies;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public void edit() {
            System.out.println("Editing Magazine details...");

            System.out.println("Edit title (" + title + "): ");
            setTitle(getInput(getTitle()));

            System.out.println("Edit price (" + price + "): ");
            setPrice(getInput(getPrice()));

            System.out.println("Edit copies (" + copies + "): ");
            setCopies(getInput(getCopies()));

            System.out.println("Edit description (" + description + "): ");
            setDescription(getInput(getDescription()));

            System.out.println("Edit order quantity (" + orderQty + "): ");
            setOrderQty(getInput(getOrderQty()));

            System.out.println("Edit current issue date (" + currentIssue + "): ");
            setCurrentIssue(getInput(getCurrentIssue()));
        }

        @Override
        public void initialize() {
            System.out.println("Initializing new Magazine...");

            System.out.println("Enter magazine title: ");
            setTitle(getInput(title));

            System.out.println("Enter magazine price: ");
            setPrice(getInput(price));

            System.out.println("Enter number of copies: ");
            setCopies(getInput(copies));

            System.out.println("Enter description: ");
            setDescription(getInput(description));

            System.out.println("Enter order quantity: ");
            setOrderQty(getInput(orderQty));

            System.out.println("Enter current issue date (YYYY-MM-DD): ");
            setCurrentIssue(getInput(currentIssue));
        }

        @Override
        public void sellCopy() {
            if (copies > 0) {
                copies--;
                System.out.println("One copy of \"" + title + "\" sold.");
                System.out.println("Remaining copies: " + copies);
            } else {
                System.out.println("No copies available for \"" + title + "\".");
            }
        }

        @Override
        public String toString() {
            return "MagazineEntity{" +
                    "id=" + getId() +
                    ", title='" + this.title + '\'' +
                    ", price=" + price +
                    ", copies=" + copies +
                    ", description='" + description + '\'' +
                    ", orderQty=" + orderQty +
                    ", currentIssue=" + currentIssue +
                    '}';
        }
    }
