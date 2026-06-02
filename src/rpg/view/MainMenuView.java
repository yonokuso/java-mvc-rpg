package rpg.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuView extends JPanel {
    private final JButton newGameButton = new JButton("New Game");
    private final JButton loadButton = new JButton("Load");
    private final JButton saveButton = new JButton("Save");
    private final JButton exitButton = new JButton("Exit");

    public MainMenuView() {
        setLayout(new BorderLayout(16, 16));
        JLabel title = new JLabel("고블린 키우기: Text RPG", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(28f));

        JPanel buttons = new JPanel(new GridLayout(4, 1, 8, 8));
        buttons.add(newGameButton);
        buttons.add(loadButton);
        buttons.add(saveButton);
        buttons.add(exitButton);

        add(title, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
