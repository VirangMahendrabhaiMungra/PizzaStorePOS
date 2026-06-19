package command;
import composite.MenuComponent;
import model.Order;

public class AddItemCommand implements Command {
    private Order order;
    private MenuComponent item;

    public AddItemCommand(Order order, MenuComponent item) {
        this.order = order;
        this.item = item;
    }

    @Override
    public void execute() {
        order.addItem(item);
    }

    @Override
    public void undo() {
        order.removeItem(item);
    }
}
