package state;

import model.Order;

public class BakingState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new ReadyState());
    }

    @Override
    public void prev(Order order) {
        order.setState(new PreparingState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is Baking.");
    }

    @Override
    public String getStatus() {
        return "Baking";
    }
}
