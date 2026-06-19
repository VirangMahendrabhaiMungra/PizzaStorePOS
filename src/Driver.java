import pos.PizzaStoreGUI;
import javax.swing.SwingUtilities;

public class Driver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaStoreGUI gui = new PizzaStoreGUI();
            gui.setVisible(true);
        });
    }
}
