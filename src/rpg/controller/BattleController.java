package rpg.controller;

import javax.swing.Timer;
import rpg.model.Battle;
import rpg.model.BattleResult;
import rpg.model.TurnResult;
import rpg.view.BattleView;
import rpg.view.MainFrame;

public class BattleController {
    private final MainFrame frame;
    private final BattleView battleView;
    private final Battle battle;
    private final String menuScreenName;
    private final Runnable saveAction;
    private final Runnable playerWinAction;
    private final Runnable enemyWinAction;
    private boolean battleFinished;

    public BattleController(
            MainFrame frame,
            BattleView battleView,
            Battle battle,
            String menuScreenName,
            Runnable saveAction,
            Runnable playerWinAction,
            Runnable enemyWinAction) {
        this.frame = frame;
        this.battleView = battleView;
        this.battle = battle;
        this.menuScreenName = menuScreenName;
        this.saveAction = saveAction;
        this.playerWinAction = playerWinAction;
        this.enemyWinAction = enemyWinAction;
    }

    public void start() {
        battleFinished = false;
        battleView.updateBattle(battle);
        battleView.setSkills(battle.getPlayer(), this::handleSkillClick);
        battleView.setMenuButtonAction(event -> frame.showScreen(menuScreenName));
        battleView.setSaveButtonAction(event -> saveAction.run());
        battleView.appendLog("앗! 야생의 " + battle.getEnemy().getName() + "이(가) 나타났다! ∑(ﾟДﾟ)w");
    }

    private void handleSkillClick(int skillIndex) {
        if (battleFinished) {
            return;
        }
        battleView.setSkillButtonsEnabled(false);
        TurnResult playerTurn = battle.playerUsesSkill(skillIndex);
        battleView.appendLog(playerTurn.toMessage());
        battleView.updateBattle(battle);

        if (finishIfBattleEnded(playerTurn.getBattleResult())) {
            return;
        }

        Timer timer = new Timer(700, event -> runEnemyTurn());
        timer.setRepeats(false);
        timer.start();
    }

    private void runEnemyTurn() {
        TurnResult enemyTurn = battle.enemyUsesRandomSkill();
        battleView.appendLog(enemyTurn.toMessage());
        battleView.updateBattle(battle);
        if (!finishIfBattleEnded(enemyTurn.getBattleResult())) {
            battleView.setSkills(battle.getPlayer(), this::handleSkillClick);
        }
    }

    private boolean finishIfBattleEnded(BattleResult result) {
        if (result == BattleResult.PLAYER_WIN) {
            battleFinished = true;
            battleView.appendLog("전투에서 이겼습니다!");
            playerWinAction.run();
            return true;
        }
        if (result == BattleResult.ENEMY_WIN) {
            battleFinished = true;
            battleView.appendLog("고블린이 쓰러졌습니다.");
            enemyWinAction.run();
            return true;
        }
        return false;
    }
}
