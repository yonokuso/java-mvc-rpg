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
        return attackerName + "이(가) " + defenderName + "에게 "  + skillName + "을(를) 사용했다!" + damage + " 데미지.";
    }

    public BattleResult getBattleResult() {
        return battleResult;
    }
}
