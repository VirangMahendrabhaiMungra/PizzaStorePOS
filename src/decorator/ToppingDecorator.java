package decorator;

public abstract class ToppingDecorator implements Product {
    protected Product product;

    public ToppingDecorator(Product product) {
        this.product = product;
    }

    @Override
    public abstract String getDescription();
}
