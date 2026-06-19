package decorator;

public class CheeseTopping extends ToppingDecorator {
    public CheeseTopping(Product product) { super(product); }
    @Override
    public String getDescription() { return product.getDescription() + ", Extra Cheese"; }
    @Override
    public double getPrice() { return product.getPrice() + 1.50; }
}
