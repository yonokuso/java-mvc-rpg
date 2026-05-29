package rpg.model;

public class TurnResult {
    private final String attackerName;
    private final String skillName;
    private final String defenderName;
    private final int damage;
    private final BattleResult battleResult;

    public TurnResult(String attackerName, String skillName, String defenderName, int damage, BattleResult battleResult) {
        this.attackerName = attackerName;
        this.skillName = skillName;
        this.defenderName = defenderName;
        this.damage = damage;
        this.battleResult = battleResult;
    }

    public String toMessage() {
        return attackerName + " used " + skillName + " on " + defenderName + " for " + damage + " damage.";
    }

    public BattleResult getBattleResult() {
        return battleResult;
    }
}
