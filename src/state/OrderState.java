package state;
import model.Order;

public interface OrderState {
    void next(Order order);
    void prev(Order order);
    void printStatus();
    String getStatus();
}
