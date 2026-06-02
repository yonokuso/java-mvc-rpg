package rpg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import rpg.model.Battle;
import rpg.model.Monster;
import rpg.model.Skill;

public class BattleView extends JPanel {
    private static final int MONSTER_IMAGE_SIZE = 150;
    private static final String IMAGE_DIRECTORY = "assets/images/";
    private static final String[] IMAGE_EXTENSIONS = {".gif", ".png", ".jpg", ".jpeg"};

    private final JLabel playerStatus = new JLabel("", SwingConstants.CENTER);
    private final JLabel enemyStatus = new JLabel("", SwingConstants.CENTER);
    private final JLabel playerImage = new JLabel("", SwingConstants.CENTER);
    private final JLabel enemyImage = new JLabel("", SwingConstants.CENTER);
    private final JPanel skillPanel = new JPanel(new GridLayout(1, 0, 8, 8));
    private final JTextArea logArea = new JTextArea();
    private final JScrollPane logScrollPane = new JScrollPane(logArea);
    private final JButton menuButton = new JButton("메뉴로 돌아가기");

    public BattleView() {
        setLayout(new BorderLayout(8, 8));
        logArea.setEditable(false);
        logScrollPane.setPreferredSize(new Dimension(260, 0));

        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        statusPanel.add(playerStatus);
        statusPanel.add(enemyStatus);

        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 24, 8));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        configureImageLabel(playerImage);
        configureImageLabel(enemyImage);
        imagePanel.add(playerImage);
        imagePanel.add(enemyImage);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.add(skillPanel, BorderLayout.CENTER);
        bottomPanel.add(menuButton, BorderLayout.EAST);

        add(statusPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateBattle(Battle battle) {
        Monster playerMonster = battle.getPlayer().getSelectedMonster();
        Monster enemy = battle.getEnemy();
        playerStatus.setText(formatMonster(playerMonster));
        enemyStatus.setText(formatMonster(enemy));
        setMonsterImage(playerImage, playerMonster);
        setMonsterImage(enemyImage, enemy);
    }

    public void setSkills(List<Skill> skills, SkillClickHandler handler) {
        skillPanel.removeAll();
        for (int i = 0; i < skills.size(); i++) {
            int skillIndex = i;
            JButton button = new JButton(skills.get(i).toString());
            button.addActionListener(event -> handler.onSkillClicked(skillIndex));
            skillPanel.add(button);
        }
        skillPanel.revalidate();
        skillPanel.repaint();
    }

    public void setSkillButtonsEnabled(boolean enabled) {
        for (int i = 0; i < skillPanel.getComponentCount(); i++) {
            skillPanel.getComponent(i).setEnabled(enabled);
        }
    }

    public void appendLog(String message) {
        logArea.append(message + System.lineSeparator());
    }

    public void clearLog() {
        logArea.setText("");
    }

    public JButton getMenuButton() {
        return menuButton;
    }

    private String formatMonster(Monster monster) {
        return monster.getName() + " [" + monster.getType() + "] HP "
                + monster.getCurrentHp() + "/" + monster.getMaxHp();
    }

    private void configureImageLabel(JLabel label) {
        Dimension size = new Dimension(MONSTER_IMAGE_SIZE, MONSTER_IMAGE_SIZE);
        label.setPreferredSize(size);
        label.setMinimumSize(size);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    private void setMonsterImage(JLabel label, Monster monster) {
        String baseName = toImageFileName(monster);
        File imageFile = findImageFile(baseName);
        if (imageFile == null) {
            label.setIcon(null);
            label.setText(baseName + ".gif / .png");
            return;
        }

        ImageIcon original = new ImageIcon(imageFile.getPath());
        if (imageFile.getName().toLowerCase().endsWith(".gif")) {
            label.setText("");
            label.setIcon(original);
            return;
        }

        Image scaled = original.getImage().getScaledInstance(
                MONSTER_IMAGE_SIZE,
                MONSTER_IMAGE_SIZE,
                Image.SCALE_SMOOTH);
        label.setText("");
        label.setIcon(new ImageIcon(scaled));
    }

    private String toImageFileName(Monster monster) {
        if (monster.getName().equals("칼 든 고블린")) {
            return "spr_sword";
        }
        if (monster.getName().equals("망치 고블린")) {
            return "spr_hammering";
        }
        if (monster.getName().equals("채찍 고블린")) {
            return "spr_caught";
        }
        if (monster.getName().equals("스켈레톤")) {
            return "skeleton_attack";
        }
        return monster.getName()
                .replace("야생의 ", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "");
    }

    private File findImageFile(String baseName) {
        for (String extension : IMAGE_EXTENSIONS) {
            File imageFile = new File(IMAGE_DIRECTORY + baseName + extension);
            if (imageFile.exists()) {
                return imageFile;
            }
        }
        return null;
    }

    public interface SkillClickHandler {
        void onSkillClicked(int skillIndex);
    }
}
