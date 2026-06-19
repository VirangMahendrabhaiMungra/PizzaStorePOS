package decorator;

public class SauceDecorator extends ToppingDecorator {
    private String sauceName;
    private double saucePrice;

    public SauceDecorator(Product product, String sauceName, double saucePrice) {
        super(product);
        this.sauceName = sauceName;
        this.saucePrice = saucePrice;
    }

    @Override
    public String getDescription() { return product.getDescription() + ", " + sauceName; }
    
    @Override
    public double getPrice() { return product.getPrice() + saucePrice; }
}
