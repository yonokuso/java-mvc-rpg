package rpg.controller;

import java.util.function.Consumer;
import javax.swing.Timer;
import rpg.model.Adventure;
import rpg.model.Battle;
import rpg.model.FairySpringBlessing;
import rpg.model.Player;
import rpg.view.BattleView;
import rpg.view.MainFrame;

public class AdventureController {
    private static final int WALK_STEP_COUNT = 3;
    private static final int WALK_DELAY_MILLIS = 650;
    private static final int NEXT_ADVENTURE_DELAY_MILLIS = 900;

    private final MainFrame frame;
    private final BattleView battleView;
    private final Player player;
    private final Adventure adventure = new Adventure();
    private final String menuScreenName;
    private final Runnable saveAction;
    private final Consumer<Boolean> saveEnabledHandler;

    private Battle currentBattle;
    private int walkStep;

    public AdventureController(
            MainFrame frame,
            BattleView battleView,
            Player player,
            String menuScreenName,
            Runnable saveAction,
            Consumer<Boolean> saveEnabledHandler) {
        this.frame = frame;
        this.battleView = battleView;
        this.player = player;
        this.menuScreenName = menuScreenName;
        this.saveAction = saveAction;
        this.saveEnabledHandler = saveEnabledHandler;
    }

    public void start() {
        battleView.clearLog();
        battleView.setMenuButtonAction(event -> frame.showScreen(menuScreenName));
        battleView.setSaveButtonAction(event -> saveAction.run());
        saveEnabledHandler.accept(true);
        startExploration();
    }

    private void startExploration() {
        if (adventure.isEscaped(player)) {
            finishAdventure();
            return;
        }

        saveEnabledHandler.accept(true);
        walkStep = 0;
        battleView.showExploration(player);
        battleView.appendLog("Stage " + player.getStageIndex() + ": 동굴 탐험을 시작합니다.");

        boolean fairySpringWillAppear = adventure.shouldShowFairySpring(player);
        Timer walkTimer = new Timer(WALK_DELAY_MILLIS, null);
        walkTimer.addActionListener(event -> {
            walkStep++;
            battleView.appendLog("걸어다닌다..");

            if (fairySpringWillAppear && walkStep == 2) {
                walkTimer.stop();
                showFairySpring();
                startBattle();
                return;
            }

            if (walkStep >= WALK_STEP_COUNT) {
                walkTimer.stop();
                startBattle();
            }
        });
        walkTimer.start();
    }

    private void showFairySpring() {
        battleView.showFairySpring(player);
        battleView.appendLog("앗, 요정의 샘물을 발견했다!");
        FairySpringBlessing blessing = battleView.chooseFairySpringBlessing();
        if (blessing != null) {
            String resultMessage = adventure.applyFairySpringBlessing(player, blessing);
            battleView.showFairySpring(player);
            battleView.appendLog(resultMessage);
        } else {
            battleView.appendLog("요정의 샘물을 지나쳤다.");
        }
    }

    private void startBattle() {
        currentBattle = new Battle(player, adventure.createEnemy());
        new BattleController(
                frame,
                battleView,
                currentBattle,
                menuScreenName,
                saveAction,
                this::handlePlayerWin,
                this::handleEnemyWin).start();
    }

    private void handlePlayerWin() {
        player.levelUp();
        adventure.completeCurrentStage(player);
        battleView.updateBattle(currentBattle);
        battleView.appendLog(player.getName() + "의 레벨이 "
                + player.getLevel() + "이 되었습니다.");
        battleView.setSkillButtonsEnabled(false);

        Timer timer = new Timer(NEXT_ADVENTURE_DELAY_MILLIS, event -> startExploration());
        timer.setRepeats(false);
        timer.start();
    }

    private void handleEnemyWin() {
        saveEnabledHandler.accept(false);
        battleView.setSkillButtonsEnabled(false);
        battleView.appendLog("탐험에 실패했습니다. 쓰러진 뒤에는 저장할 수 없습니다.");
    }

    private void finishAdventure() {
        saveEnabledHandler.accept(false);
        battleView.showAdventureComplete(player);
        battleView.appendLog("동굴을 탈출했습니다. 게임이 종료되었습니다.");
        battleView.setSkillButtonsEnabled(false);
    }
}
