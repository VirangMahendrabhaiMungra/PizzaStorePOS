package state;

import model.Order;

public class ReadyState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("Order is already Ready.");
    }

    @Override
    public void prev(Order order) {
        order.setState(new BakingState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is Ready.");
    }

    @Override
    public String getStatus() {
        return "Ready";
    }
}
