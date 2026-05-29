package rpg;

import javax.swing.SwingUtilities;
import rpg.controller.GameController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameController().start());
    }
}
