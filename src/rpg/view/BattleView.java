package rpg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
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
import rpg.model.Adventure;
import rpg.model.Monster;
import rpg.model.Player;
import rpg.model.Skill;

public class BattleView extends JPanel {
    private static final int MONSTER_PANEL_WIDTH = 190;
    private static final int MONSTER_PANEL_HEIGHT = 260;
    private static final int MONSTER_IMAGE_SIZE = 130;
    private static final int STATUS_LABEL_HEIGHT = 44;
    private static final int LOG_PANEL_WIDTH = 260;
    private static final int LOG_PANEL_HEIGHT = 260;
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
    private final JButton saveButton = new JButton("저장");

    public BattleView() {
        setLayout(new BorderLayout(8, 8));
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logScrollPane.setPreferredSize(new Dimension(LOG_PANEL_WIDTH, LOG_PANEL_HEIGHT));
        logScrollPane.setMinimumSize(new Dimension(LOG_PANEL_WIDTH, LOG_PANEL_HEIGHT));

        JPanel playerPanel = createMonsterPanel(playerStatus, playerImage);
        JPanel enemyPanel = createMonsterPanel(enemyStatus, enemyImage);

        JPanel battlePanel = new JPanel(new GridBagLayout());
        battlePanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.weightx = 0.2;
        constraints.weighty = 1.0;
        battlePanel.add(playerPanel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.6;
        constraints.insets.set(0, 12, 0, 12);
        battlePanel.add(logScrollPane, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.2;
        constraints.insets.set(0, 0, 0, 0);
        battlePanel.add(enemyPanel, constraints);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        actionPanel.add(saveButton);
        actionPanel.add(menuButton);
        bottomPanel.add(skillPanel, BorderLayout.CENTER);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        add(battlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateBattle(Battle battle) {
        Monster playerMonster = battle.getPlayer().getSelectedMonster();
        Monster enemy = battle.getEnemy();
        playerStatus.setText(formatPlayerMonster(battle));
        enemyStatus.setText(formatMonster(enemy));
        setMonsterImage(playerImage, playerMonster);
        setMonsterImage(enemyImage, enemy);
    }

    public void showExploration(Player player) {
        playerStatus.setText(formatPlayerMonster(player));
        setMonsterImage(playerImage, player.getSelectedMonster());
        enemyStatus.setText("동굴 탐험 중 - Stage "
                + player.getStageIndex() + "/" + Adventure.ESCAPE_STAGE_INDEX);
        enemyImage.setIcon(null);
        enemyImage.setText("...");
        clearSkills();
    }

    public void showAdventureComplete(Player player) {
        playerStatus.setText(formatPlayerMonster(player));
        setMonsterImage(playerImage, player.getSelectedMonster());
        enemyStatus.setText("동굴 탈출 완료 - Stage "
                + player.getStageIndex() + "/" + Adventure.ESCAPE_STAGE_INDEX);
        enemyImage.setIcon(null);
        enemyImage.setText("탈출!");
        clearSkills();
    }

    public void showFairySpring(Player player) {
        playerStatus.setText(formatPlayerMonster(player));
        setMonsterImage(playerImage, player.getSelectedMonster());
        enemyStatus.setText("요정의 샘물");
        setImage(enemyImage, "fairy_pond");
        clearSkills();
    }

    public void setSkills(Player player, SkillClickHandler handler) {
        skillPanel.removeAll();
        Monster attacker = player.getSelectedMonster();
        List<Skill> skills = attacker.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            int skillIndex = i;
            Skill skill = skills.get(i);
            JButton button = new JButton(formatSkillButton(skill, player.getLevel()));
            button.setEnabled(skill.canUse(attacker));
            button.addActionListener(event -> handler.onSkillClicked(skillIndex));
            skillPanel.add(button);
        }
        skillPanel.revalidate();
        skillPanel.repaint();
    }

    public void clearSkills() {
        skillPanel.removeAll();
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
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void clearLog() {
        logArea.setText("");
    }

    public void setMenuButtonAction(ActionListener listener) {
        for (ActionListener actionListener : menuButton.getActionListeners()) {
            menuButton.removeActionListener(actionListener);
        }
        menuButton.addActionListener(listener);
    }

    public void setSaveButtonAction(ActionListener listener) {
        for (ActionListener actionListener : saveButton.getActionListeners()) {
            saveButton.removeActionListener(actionListener);
        }
        saveButton.addActionListener(listener);
    }

    public void setSaveButtonEnabled(boolean enabled) {
        saveButton.setEnabled(enabled);
    }

    private JPanel createMonsterPanel(JLabel statusLabel, JLabel imageLabel) {
        configureStatusLabel(statusLabel);
        configureImageLabel(imageLabel);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(MONSTER_PANEL_WIDTH, MONSTER_PANEL_HEIGHT));
        panel.setMinimumSize(new Dimension(MONSTER_PANEL_WIDTH, MONSTER_PANEL_HEIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    private String formatMonster(Monster monster) {
        return monster.getName() + " HP " + monster.getCurrentHp() + "/" + monster.getMaxHp()
                + " SP " + monster.getCurrentSp() + "/" + monster.getMaxSp();
    }

    private String formatPlayerMonster(Battle battle) {
        return formatPlayerMonster(battle.getPlayer());
    }

    private String formatPlayerMonster(Player player) {
        return player.getName() + " Lv." + player.getLevel()
                + " - " + formatMonster(player.getSelectedMonster());
    }

    private String formatSkillButton(Skill skill, int attackerLevel) {
        return skill.getName() + " (" + skill.calculateDamage(attackerLevel)
                + " 데미지 / SP " + skill.getSpCost() + ")";
    }

    private void configureStatusLabel(JLabel label) {
        Dimension size = new Dimension(MONSTER_PANEL_WIDTH, STATUS_LABEL_HEIGHT);
        label.setPreferredSize(size);
        label.setMinimumSize(size);
        label.setHorizontalAlignment(SwingConstants.CENTER);
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
        setImage(label, baseName);
    }

    private void setImage(JLabel label, String baseName) {
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
        String name = monster.getName().toLowerCase();
        if (monster.getName().equals("칼 고블린")) {
            return "spr_sword";
        }
        if (monster.getName().equals("망치 고블린")) {
            return "spr_hammering";
        }
        if (monster.getName().equals("채찍 고블린")) {
            return "spr_caught";
        }
        if (monster.getName().equals("도끼 고블린")) {
            return "spr_axe";
        }
        if (monster.getName().equals("스켈레톤")) {
            return "skeleton_attack";
        }
        if (monster.getName().equals("흑화한 고블린")) {
            return "spr_death";
        }
        return name.replace("야생의 ", "").replaceAll("[^a-z0-9]+", "");
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
