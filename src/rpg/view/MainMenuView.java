package rpg.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuView extends JPanel {
    private static final String IMAGE_DIRECTORY = "assets/images";

    private final JButton newGameButton = new JButton("New Game");
    private final JButton loadButton = new JButton("Load");
    private final JButton exitButton = new JButton("Exit");

    public MainMenuView() {
        setLayout(new BorderLayout(16, 16));

        JLabel title = new JLabel("고블린 키우기: Text RPG", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(28f));
        title.setForeground(Color.RED);

        JLabel goblinImage = new JLabel("", SwingConstants.CENTER);
        Icon icon = loadTitleGoblinIcon();
        if (icon == null) {
            goblinImage.setText("spr_sword.gif");
        } else {
            goblinImage.setIcon(icon);
        }

        JLabel grass = new JLabel("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", SwingConstants.CENTER);
        grass.setFont(grass.getFont().deriveFont(20f));
        grass.setForeground(Color.GREEN);


        JPanel titlePanel = new JPanel(new BorderLayout(8, 16));
        titlePanel.setOpaque(true);
        titlePanel.setBackground(Color.CYAN);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(56, 0, 44, 0));
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(goblinImage, BorderLayout.CENTER);
        titlePanel.add(grass, BorderLayout.SOUTH);


        JPanel buttons = new JPanel(new GridLayout(3, 1, 8, 8));
        buttons.add(newGameButton);
        buttons.add(loadButton);
        buttons.add(exitButton);

        add(titlePanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    private Icon loadTitleGoblinIcon() {
        File imageFile = findImageFile("spr_watering");
        if (imageFile == null) {
            return null;
        }
        return new ImageIcon(imageFile.getAbsolutePath());
    }

    private File findImageFile(String baseName) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        for (int depth = 0; currentDirectory != null && depth < 6; depth++) {
            File imageDirectory = new File(currentDirectory, IMAGE_DIRECTORY);
            File imageFile = findInDirectory(imageDirectory, baseName);
            if (imageFile != null) {
                return imageFile;
            }
            currentDirectory = currentDirectory.getParentFile();
        }
        return null;
    }

    private File findInDirectory(File imageDirectory, String baseName) {
        String[] extensions = {".gif", ".png", ".jpg", ".jpeg"};
        for (String extension : extensions) {
            File imageFile = new File(imageDirectory, baseName + extension);
            if (imageFile.exists()) {
                return imageFile;
            }
        }
        return null;
    }
}
