package rpg.controller;

import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import rpg.model.GameFactory;
import rpg.model.Player;
import rpg.service.SaveService;
import rpg.view.BattleView;
import rpg.view.MainFrame;
import rpg.view.MainMenuView;
import rpg.view.MonsterSelectView;

public class GameController {
    private static final String MENU = "menu";
    private static final String MONSTER_SELECT = "고블린 선택";
    private static final String BATTLE = "battle";

    private final MainFrame frame = new MainFrame();
    private final MainMenuView menuView = new MainMenuView();
    private final MonsterSelectView monsterSelectView = new MonsterSelectView();
    private final BattleView battleView = new BattleView();
    private final SaveService saveService = new SaveService(Path.of("player.dat"));
    private Player player = GameFactory.createDefaultPlayer();
    private boolean gameSaveEnabled;

    public void start() {
        frame.addScreen(MENU, menuView);
        frame.addScreen(MONSTER_SELECT, monsterSelectView);
        frame.addScreen(BATTLE, battleView);
        bindMenuActions();
        bindMonsterSelectActions();
        setGameSaveEnabled(false);
        frame.showScreen(MENU);
        frame.setVisible(true);
    }

    private void bindMenuActions() {
        menuView.getNewGameButton().addActionListener(event -> {
            player = GameFactory.createDefaultPlayer();
            setGameSaveEnabled(false);
            showMonsterSelect();
        });
        menuView.getLoadButton().addActionListener(event -> loadGame());
        menuView.getExitButton().addActionListener(event -> System.exit(0));
    }

    private void bindMonsterSelectActions() {
        monsterSelectView.getBackButton().addActionListener(event -> frame.showScreen(MENU));
        monsterSelectView.getBattleButton().addActionListener(event -> {
            int selectedIndex = monsterSelectView.getSelectedMonsterIndex();
            if (selectedIndex < 0) {
                showMessage("고블린을 선택해 주세요.");
                return;
            }
            player.selectMonster(selectedIndex);
            new AdventureController(
                    frame,
                    battleView,
                    player,
                    MENU,
                    this::saveGame,
                    this::setGameSaveEnabled).start();
            frame.showScreen(BATTLE);
        });
    }

    private void showMonsterSelect() {
        monsterSelectView.setPlayer(player);
        frame.showScreen(MONSTER_SELECT);
    }

    private void saveGame() {
        if (!gameSaveEnabled) {
            showMessage("게임 진행 중에만 저장할 수 있습니다.");
            return;
        }
        setMenuButtonsEnabled(false);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                saveService.save(player);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    showMessage("게임을 저장했습니다. (player.dat)");
                } catch (Exception exception) {
                    showMessage("Save failed: " + getErrorMessage(exception));
                } finally {
                    setMenuButtonsEnabled(true);
                }
            }
        }.execute();
    }

    private void loadGame() {
        setMenuButtonsEnabled(false);
        new SwingWorker<Player, Void>() {
            @Override
            protected Player doInBackground() throws Exception {
                return saveService.load();
            }

            @Override
            protected void done() {
                try {
                    player = get();
                    setGameSaveEnabled(false);
                    showMessage("게임을 불러왔습니다.");
                    showMonsterSelect();
                } catch (Exception exception) {
                    showMessage("Load failed: " + getErrorMessage(exception));
                } finally {
                    setMenuButtonsEnabled(true);
                }
            }
        }.execute();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private void setMenuButtonsEnabled(boolean enabled) {
        menuView.getNewGameButton().setEnabled(enabled);
        menuView.getLoadButton().setEnabled(enabled);
        menuView.getExitButton().setEnabled(enabled);
        battleView.setSaveButtonEnabled(enabled && gameSaveEnabled);
    }

    private void setGameSaveEnabled(boolean enabled) {
        gameSaveEnabled = enabled;
        battleView.setSaveButtonEnabled(enabled);
    }

    private String getErrorMessage(Exception exception) {
        Throwable cause = exception.getCause();
        if (cause != null && cause.getMessage() != null) {
            return cause.getMessage();
        }
        return exception.getMessage();
    }
}
