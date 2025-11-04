package org.example.pojos;

import java.util.Objects;

public abstract class Publication extends Editable implements SaleableItem {
    private String title;
    private double price;
    private int copies;
    private long id;
    private long isbn;
    private String description;
    public Publication() {

    }

    public Publication(String title, double price, int copies) {
        this.title = title;
        this.price = price;
        this.copies = copies;
    }
    public Publication(long id, String title, double price, int copies, long isbn, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.copies = copies;
        this.isbn = isbn;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public double getPrice() {
        return this.price;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Publication-> [" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", copies=" + copies +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication that)) return false;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                copies == that.copies &&
                Objects.equals(title, that.title) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {return Objects.hash(id, title, price, copies, isbn, description);}

}
