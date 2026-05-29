package rpg.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Battle implements Serializable {
    private final Player player;
    private final Monster enemy;
    private final Random random = new Random();

    public Battle(Player player, Monster enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public TurnResult playerUsesSkill(int skillIndex) {
        Monster playerMonster = player.getSelectedMonster();
        Skill skill = playerMonster.getSkills().get(skillIndex);
        int damage = skill.use(playerMonster, enemy);
        return new TurnResult(playerMonster.getName(), skill.getName(), enemy.getName(), damage, getResult());
    }

    public TurnResult enemyUsesRandomSkill() {
        List<Skill> skills = enemy.getSkills();
        Skill skill = skills.get(random.nextInt(skills.size()));
        Monster playerMonster = player.getSelectedMonster();
        int damage = skill.use(enemy, playerMonster);
        return new TurnResult(enemy.getName(), skill.getName(), playerMonster.getName(), damage, getResult());
    }

    public BattleResult getResult() {
        if (enemy.isDefeated()) {
            return BattleResult.PLAYER_WIN;
        }
        if (player.getSelectedMonster().isDefeated()) {
            return BattleResult.ENEMY_WIN;
        }
        return BattleResult.ONGOING;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getEnemy() {
        return enemy;
    }
}
