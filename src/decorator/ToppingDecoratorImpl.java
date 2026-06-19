package decorator;

public class ToppingDecoratorImpl extends ToppingDecorator {
    private String toppingName;
    private double toppingPrice;

    public ToppingDecoratorImpl(Product product, String toppingName, double toppingPrice) {
        super(product);
        this.toppingName = toppingName;
        this.toppingPrice = toppingPrice;
    }

    @Override
    public String getDescription() { return product.getDescription() + ", " + toppingName; }
    
    @Override
    public double getPrice() { return product.getPrice() + toppingPrice; }
}
