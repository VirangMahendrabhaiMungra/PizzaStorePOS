package decorator;

public class CheeseDecorator extends ToppingDecorator {
    private String cheeseName;
    private double cheesePrice;

    public CheeseDecorator(Product product, String cheeseName, double cheesePrice) {
        super(product);
        this.cheeseName = cheeseName;
        this.cheesePrice = cheesePrice;
    }

    @Override
    public String getDescription() { return product.getDescription() + ", " + cheeseName; }
    
    @Override
    public double getPrice() { return product.getPrice() + cheesePrice; }
}
