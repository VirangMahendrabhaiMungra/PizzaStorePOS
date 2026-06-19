package pos;

import model.Order;
import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private Queue<Order> queue;

    public OrderQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(Order order) {
        queue.add(order);
    }

    public Order dequeue() {
        return queue.poll();
    }
    
    public Order peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public int size() {
        return queue.size();
    }
}
