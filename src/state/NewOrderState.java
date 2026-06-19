package state;

import model.Order;

public class NewOrderState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new PreparingState());
    }

    @Override
    public void prev(Order order) {
        System.out.println("Order is in its root state.");
    }

    @Override
    public void printStatus() {
        System.out.println("Order is New.");
    }

    @Override
    public String getStatus() {
        return "New Order";
    }
}
