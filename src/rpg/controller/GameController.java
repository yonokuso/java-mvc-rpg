package rpg.controller;

import java.nio.file.Path;
import javax.swing.JOptionPane;
import rpg.model.Battle;
import rpg.model.GameFactory;
import rpg.model.Player;
import rpg.service.SaveService;
import rpg.view.BattleView;
import rpg.view.MainFrame;
import rpg.view.MainMenuView;
import rpg.view.MonsterSelectView;

public class GameController {
    private static final String MENU = "menu";
    private static final String MONSTER_SELECT = "monsterSelect";
    private static final String BATTLE = "battle";

    private final MainFrame frame = new MainFrame();
    private final MainMenuView menuView = new MainMenuView();
    private final MonsterSelectView monsterSelectView = new MonsterSelectView();
    private final BattleView battleView = new BattleView();
    private final SaveService saveService = new SaveService(Path.of("player.dat"));
    private Player player = GameFactory.createDefaultPlayer();

    public void start() {
        frame.addScreen(MENU, menuView);
        frame.addScreen(MONSTER_SELECT, monsterSelectView);
        frame.addScreen(BATTLE, battleView);
        bindMenuActions();
        bindMonsterSelectActions();
        frame.showScreen(MENU);
        frame.setVisible(true);
    }

    private void bindMenuActions() {
        menuView.getNewGameButton().addActionListener(event -> {
            player = GameFactory.createDefaultPlayer();
            showMonsterSelect();
        });
        menuView.getLoadButton().addActionListener(event -> loadGame());
        menuView.getSaveButton().addActionListener(event -> saveGame());
        menuView.getExitButton().addActionListener(event -> System.exit(0));
    }

    private void bindMonsterSelectActions() {
        monsterSelectView.getBackButton().addActionListener(event -> frame.showScreen(MENU));
        monsterSelectView.getBattleButton().addActionListener(event -> {
            int selectedIndex = monsterSelectView.getSelectedMonsterIndex();
            if (selectedIndex < 0) {
                showMessage("Choose a monster first.");
                return;
            }
            player.selectMonster(selectedIndex);
            for (var monster : player.getMonsters()) {
                monster.healFull();
            }
            new BattleController(frame, battleView, new Battle(player, GameFactory.createDefaultEnemy()), MENU).start();
            frame.showScreen(BATTLE);
        });
    }

    private void showMonsterSelect() {
        monsterSelectView.setPlayer(player);
        frame.showScreen(MONSTER_SELECT);
    }

    private void saveGame() {
        try {
            saveService.save(player);
            showMessage("Game saved to player.dat.");
        } catch (Exception exception) {
            showMessage("Save failed: " + exception.getMessage());
        }
    }

    private void loadGame() {
        try {
            player = saveService.load();
            showMessage("Game loaded.");
            showMonsterSelect();
        } catch (Exception exception) {
            showMessage("Load failed: " + exception.getMessage());
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
