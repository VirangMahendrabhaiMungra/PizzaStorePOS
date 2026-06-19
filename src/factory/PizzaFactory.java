package factory;

import composite.ProductItem;
import decorator.*;

public class PizzaFactory {
    public static ProductItem createMargherita() {
        Product p = new BasePizza("Medium Pan Pizza", 10.00);
        p = new SauceDecorator(p, "Tomato Sauce", 0.0);
        p = new CheeseDecorator(p, "Fresh Mozzarella", 1.0);
        return new ProductItem("Margherita Pizza", p);
    }
    
    public static ProductItem createHawaiian() {
        Product p = new BasePizza("Large Thin Crust", 12.00);
        p = new SauceDecorator(p, "Tomato Sauce", 0.0);
        p = new CheeseDecorator(p, "Mozzarella", 0.0);
        p = new ToppingDecoratorImpl(p, "Pineapple", 1.5);
        p = new ToppingDecoratorImpl(p, "Ham", 2.0);
        return new ProductItem("Hawaiian Pizza", p);
    }

    public static ProductItem createMeatLovers() {
        Product p = new BasePizza("Large Pan Pizza", 14.00);
        p = new SauceDecorator(p, "BBQ Sauce", 0.5);
        p = new CheeseDecorator(p, "Mozzarella", 0.0);
        p = new ToppingDecoratorImpl(p, "Pepperoni", 2.0);
        p = new ToppingDecoratorImpl(p, "Sausage", 2.0);
        p = new ToppingDecoratorImpl(p, "Bacon", 2.0);
        return new ProductItem("Meat Lovers Pizza", p);
    }
    
    public static ProductItem createVeggieSupreme() {
        Product p = new BasePizza("Medium Hand-Tossed", 11.00);
        p = new SauceDecorator(p, "Pesto Sauce", 1.0);
        p = new CheeseDecorator(p, "Vegan Cheese", 1.5);
        p = new ToppingDecoratorImpl(p, "Mushrooms", 1.0);
        p = new ToppingDecoratorImpl(p, "Onions", 1.0);
        p = new ToppingDecoratorImpl(p, "Bell Peppers", 1.0);
        p = new ToppingDecoratorImpl(p, "Olives", 1.0);
        return new ProductItem("Veggie Supreme Pizza", p);
    }
}
