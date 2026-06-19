package decorator;

public class PepperoniTopping extends ToppingDecorator {
    public PepperoniTopping(Product product) { super(product); }
    @Override
    public String getDescription() { return product.getDescription() + ", Pepperoni"; }
    @Override
    public double getPrice() { return product.getPrice() + 2.00; }
}
