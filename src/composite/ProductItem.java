package composite;

import decorator.Product;

public class ProductItem extends MenuComponent {
    private Product product;
    private String name;

    public ProductItem(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    @Override
    public String getName() { return name; }
    @Override
    public String getDescription() { return product.getDescription(); }
    @Override
    public double getPrice() { return product.getPrice(); }

    @Override
    public void print() {
        System.out.println("  " + getName() + " - $" + getPrice() + " (" + getDescription() + ")");
    }
}
