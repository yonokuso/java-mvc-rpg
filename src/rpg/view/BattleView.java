package rpg.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import rpg.model.Battle;
import rpg.model.Monster;
import rpg.model.Skill;

public class BattleView extends JPanel {
    private final JLabel playerStatus = new JLabel("", SwingConstants.CENTER);
    private final JLabel enemyStatus = new JLabel("", SwingConstants.CENTER);
    private final JPanel skillPanel = new JPanel(new GridLayout(1, 0, 8, 8));
    private final JTextArea logArea = new JTextArea();
    private final JButton menuButton = new JButton("Menu");

    public BattleView() {
        setLayout(new BorderLayout(8, 8));
        logArea.setEditable(false);

        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        statusPanel.add(playerStatus);
        statusPanel.add(enemyStatus);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.add(skillPanel, BorderLayout.CENTER);
        bottomPanel.add(menuButton, BorderLayout.EAST);

        add(statusPanel, BorderLayout.NORTH);
        add(logArea, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateBattle(Battle battle) {
        Monster playerMonster = battle.getPlayer().getSelectedMonster();
        Monster enemy = battle.getEnemy();
        playerStatus.setText(formatMonster(playerMonster));
        enemyStatus.setText(formatMonster(enemy));
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

    public interface SkillClickHandler {
        void onSkillClicked(int skillIndex);
    }
}
