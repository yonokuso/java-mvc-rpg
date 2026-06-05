package rpg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import rpg.model.Monster;
import rpg.model.Player;

public class MonsterSelectView extends JPanel {
    private static final int PREVIEW_IMAGE_SIZE = 180;
    private static final String IMAGE_DIRECTORY = "assets/images/";
    private static final String[] IMAGE_EXTENSIONS = {".gif", ".png", ".jpg", ".jpeg"};

    private final DefaultListModel<Monster> monsterListModel = new DefaultListModel<>();
    private final JList<Monster> monsterList = new JList<>(monsterListModel);
    private final JLabel previewName = new JLabel("몬스터를 선택하세요", SwingConstants.CENTER);
    private final JLabel previewImage = new JLabel("", SwingConstants.CENTER);
    private final JButton battleButton = new JButton("모험하기");
    private final JButton backButton = new JButton("돌아가기");

    public MonsterSelectView() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel previewPanel = new JPanel(new BorderLayout(8, 8));
        previewPanel.setPreferredSize(new Dimension(260, 0));
        previewPanel.setBorder(BorderFactory.createEtchedBorder());
        previewImage.setPreferredSize(new Dimension(PREVIEW_IMAGE_SIZE, PREVIEW_IMAGE_SIZE));
        previewPanel.add(previewName, BorderLayout.NORTH);
        previewPanel.add(previewImage, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 12, 12));
        contentPanel.add(new JScrollPane(monsterList));
        contentPanel.add(previewPanel);

        JPanel actions = new JPanel();
        actions.add(backButton);
        actions.add(battleButton);

        monsterList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                updatePreview(monsterList.getSelectedValue());
            }
        });

        add(contentPanel, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    public void setPlayer(Player player) {
        monsterListModel.clear();
        for (Monster monster : player.getMonsters()) {
            monsterListModel.addElement(monster);
        }
        monsterList.setSelectedIndex(0);
        updatePreview(monsterList.getSelectedValue());
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

    private void updatePreview(Monster monster) {
        if (monster == null) {
            previewName.setText("몬스터를 선택하세요");
            previewImage.setIcon(null);
            previewImage.setText("");
            return;
        }

        previewName.setText(monster.toString());
        setMonsterImage(monster);
    }

    private void setMonsterImage(Monster monster) {
        String baseName = toImageFileName(monster);
        File imageFile = findImageFile(baseName);
        if (imageFile == null) {
            previewImage.setIcon(null);
            previewImage.setText(baseName + ".gif / .png");
            return;
        }

        ImageIcon original = new ImageIcon(imageFile.getPath());
        if (imageFile.getName().toLowerCase().endsWith(".gif")) {
            previewImage.setText("");
            previewImage.setIcon(original);
            return;
        }

        Image scaled = original.getImage().getScaledInstance(
                PREVIEW_IMAGE_SIZE,
                PREVIEW_IMAGE_SIZE,
                Image.SCALE_SMOOTH);
        previewImage.setText("");
        previewImage.setIcon(new ImageIcon(scaled));
    }

    private String toImageFileName(Monster monster) {
        String name = monster.getName().toLowerCase();
        if (name.contains("sword") || name.contains("칼")) {
            return "spr_sword";
        }
        if (name.contains("axe") || name.contains("도끼")) {
            return "spr_axe";
        }
        if (name.contains("hammer") || name.contains("망치")) {
            return "spr_hammering";
        }
        if (name.contains("caught") || name.contains("채찍")) {
            return "spr_caught";
        }
        if (name.contains("skeleton") || name.contains("스켈레톤")) {
            return "skeleton_attack";
        }
        if (name.contains("death") || name.contains("흑화")) {
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
}
