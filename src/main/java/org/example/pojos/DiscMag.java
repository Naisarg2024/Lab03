package org.example.pojos;
import java.time.LocalDate;
public class DiscMag extends Magazine{
    private boolean hasDisc;

    public DiscMag() {}

    public DiscMag(boolean hasDisc, int orderQty, LocalDate currentIssue, String title, double price, int copies) {
        super(orderQty,currentIssue,title,price,copies);
        this.hasDisc = hasDisc;
    }
    public DiscMag(Magazine m, boolean hasDisc) {
        super(
                m.getId(),
                m.getTitle(),
                m.getPrice(),
                m.getCopies(),
                m.getIsbn(),
                m.getDescription(),
                m.getOrderQty(),
                m.getCurrentIssue()
        );
        this.hasDisc = hasDisc;
    }
    public boolean isHasDisc() { return hasDisc; }
    public void setHasDisc(boolean hasDisc) { this.hasDisc = hasDisc; }

    @Override
    public void edit() {
        super.edit();
        System.out.printf("Includes disc (%s) [true/false, enter for no changes]%n", hasDisc);
        String v = getInput();
        if (!v.isBlank()) setHasDisc(Boolean.parseBoolean(v));
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.print("Includes disc? (true/false): ");
        setHasDisc(Boolean.parseBoolean(getInput()));
    }

    @Override
    public void sellCopy() {
        super.sellCopy();
        System.out.println("Includes disc: " + hasDisc);
    }
}
