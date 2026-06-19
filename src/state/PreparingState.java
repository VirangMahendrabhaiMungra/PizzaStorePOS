package state;

import model.Order;

public class PreparingState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new BakingState());
    }

    @Override
    public void prev(Order order) {
        order.setState(new NewOrderState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is being Prepared.");
    }

    @Override
    public String getStatus() {
        return "Preparing";
    }
}
