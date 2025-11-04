package org.example.pojos;

public class Book extends Publication{
    public String author;

    public Book() {}
//    public Book(String author, String title, double price, int copies) {
//        super(title, price, copies);
//        this.author = author;
//    }
    public Book(long id, String title, double price, int copies, long isbn, String description, String author) {
        super(id, title, price, copies, isbn, description);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Book-> [" +
                "Editable id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", copies=" + getCopies() +
                ']';
    }

    @Override
    public void edit() {
        System.out.printf("Edit Title (%s [enter for no changes])%n", getTitle());
        String t = getInput();
        if (!t.isBlank()) setTitle(t);

        System.out.printf("Edit Author (%s [enter for no changes])%n", author);
        String a = getInput();
        if (!a.isBlank()) setAuthor(a);

        System.out.printf("Edit copies (%d [enter for no changes])%n", getCopies());
        String cs = getInput();
        if (!cs.isBlank()) setCopies(Integer.parseInt(cs));

        System.out.printf("Edit price (%.2f [enter for no changes])%n", getPrice());
        String ps = getInput();
        if (!ps.isBlank()) setPrice(Double.parseDouble(ps));
    }

    @Override
    public void initialize() {
        System.out.print("Enter Title: ");
        setTitle(getInput());

        System.out.print("Enter Author: ");
        setAuthor(getInput());

        System.out.print("Enter copies: ");
        setCopies(Integer.parseInt(getInput()));

        System.out.print("Enter price: ");
        setPrice(Double.parseDouble(getInput()));
    }

    @Override
    public void sellItem() {
        if (getCopies() > 0) {
            setCopies(getCopies() - 1);
            System.out.println("Sold 1 book: " + getTitle() + " by " + author);
        } else {
            System.out.println("Book out of stock: " + getTitle());
        }
    }
}
