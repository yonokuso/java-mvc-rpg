package rpg.view;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import rpg.model.Monster;
import rpg.model.Player;

public class MonsterSelectView extends JPanel {
    private final DefaultListModel<Monster> monsterListModel = new DefaultListModel<>();
    private final JList<Monster> monsterList = new JList<>(monsterListModel);
    private final JButton battleButton = new JButton("Start Battle");
    private final JButton backButton = new JButton("Back");

    public MonsterSelectView() {
        setLayout(new BorderLayout(8, 8));
        JPanel actions = new JPanel();
        actions.add(backButton);
        actions.add(battleButton);

        add(monsterList, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    public void setPlayer(Player player) {
        monsterListModel.clear();
        for (Monster monster : player.getMonsters()) {
            monsterListModel.addElement(monster);
        }
        monsterList.setSelectedIndex(0);
    }

    public int getSelectedMonsterIndex() {
        return monsterList.getSelectedIndex();
    }

    public JButton getBattleButton() {
        return battleButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
