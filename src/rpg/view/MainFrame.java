package rpg.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    public MainFrame() {
        super("고블린 키우기: Text RPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setContentPane(root);
    }

    public void addScreen(String name, JPanel panel) {
        root.add(panel, name);
    }

    public void showScreen(String name) {
        cardLayout.show(root, name);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
