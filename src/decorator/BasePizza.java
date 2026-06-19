package decorator;

public class BasePizza implements Product {
    private String description;
    private double price;

    public BasePizza(String description, double price) {
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() { return description; }
    @Override
    public double getPrice() { return price; }
}
