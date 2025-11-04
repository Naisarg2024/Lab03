package org.example.pojos;

import java.time.LocalDate;

public class Magazine extends Publication{

    private int orderQty;
    private LocalDate currentIssue;

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty){
        this.orderQty = orderQty;
    }

    public LocalDate getCurrentIssue(){
        return currentIssue;
    }

    public void setCurrentIssue(LocalDate currentIssue){
        this.currentIssue = currentIssue;
    }

    public Magazine() {}

    public Magazine(int orderQty, LocalDate currentIssue, String title, double price, int copies){
        super(title, price, copies);
        this.currentIssue = currentIssue;
        this.orderQty = orderQty;
    }

    public Magazine(long id, String title, double price, int copies, long isbn, String description,
                    int orderQty, LocalDate currentIssue) {
        super(id, title, price, copies, isbn, description);
        this.orderQty = orderQty;
        this.currentIssue = currentIssue;
    }

    public void edit(){
        System.out.printf("Edit Title (%s [enter for no changes])%n", getTitle());
        String t = getInput();
        if (!t.isBlank()) setTitle(t);

        System.out.printf("Edit copies (%d [enter for no changes])%n", getCopies());
        String cs = getInput();
        if (!cs.isBlank()) setCopies(Integer.parseInt(cs));

        System.out.printf("Edit price (%.2f [enter for no changes])%n", getPrice());
        String ps = getInput();
        if (!ps.isBlank()) setPrice(Double.parseDouble(ps));

        System.out.printf("Edit orderQty (%d [enter for no changes])%n", getOrderQty());
        String oq = getInput();
        if (!oq.isBlank()) setOrderQty(Integer.parseInt(oq));

        System.out.printf("Edit current issue (YYYY-MM-DD) (%s [enter for no changes])%n", getCurrentIssue());
        String ci = getInput();
        if (!ci.isBlank()) setCurrentIssue(LocalDate.parse(ci));
    }

    @Override
    public void initialize(){
        System.out.print("Enter Title: ");
        setTitle(getInput());

        System.out.print("Enter copies: ");
        setCopies(Integer.parseInt(getInput()));

        System.out.print("Enter price: ");
        setPrice(Double.parseDouble(getInput()));

        System.out.print("Enter orderQty: ");
        setOrderQty(Integer.parseInt(getInput()));

        System.out.print("Enter current issue (YYYY-MM-DD): ");
        setCurrentIssue(LocalDate.parse(getInput()));
    }

    @Override
    public void sellItem() {
        if (getCopies() > 0) {
            setCopies(getCopies()-1);
            System.out.println("Sold 1 copy of "+ getTitle() + " magazine!");
        } else {
            System.out.println(getTitle() + " magazine is currently out of stock!");
        }
    }

    @Override
    public String toString() {
        return "Magazine-> [" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", copies=" + getCopies() +
                ", isbn='" + getIsbn() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", orderQty=" + orderQty +
                ", currentIssue=" + currentIssue +
                ']';
    }
}
