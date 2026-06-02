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

    public BattleController(MainFrame frame, BattleView battleView, Battle battle, String menuScreenName) {
        this.frame = frame;
        this.battleView = battleView;
        this.battle = battle;
        this.menuScreenName = menuScreenName;
    }

    public void start() {
        battleView.clearLog();
        battleView.updateBattle(battle);
        battleView.setSkills(battle.getPlayer().getSelectedMonster().getSkills(), this::handleSkillClick);
        battleView.getMenuButton().addActionListener(event -> frame.showScreen(menuScreenName));
        battleView.appendLog("앗! 야생의 " + battle.getEnemy().getName() + "이(가) 나타났다! ∑(ﾟДﾟ)w");
    }

    private void handleSkillClick(int skillIndex) {
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
            battleView.setSkillButtonsEnabled(true);
        }
    }

    private boolean finishIfBattleEnded(BattleResult result) {
        if (result == BattleResult.PLAYER_WIN) {
            battleView.appendLog("전투에서 이겼습니다!");
            return true;
        }
        if (result == BattleResult.ENEMY_WIN) {
            battleView.appendLog("고블린이 쓰러졌습니다.");
            return true;
        }
        return false;
    }
}
