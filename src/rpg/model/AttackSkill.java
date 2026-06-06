package rpg.model;

public class AttackSkill extends Skill {
    public AttackSkill(String name, int power, int spCost) {
        super(name, power, spCost);
    }

    @Override
    public int use(Monster attacker, Monster defender, int attackerLevel) {
        if (!canUse(attacker)) {
            throw new IllegalStateException("SP가 부족합니다.");
        }
        int damage = calculateDamage(attackerLevel);
        defender.takeDamage(damage);
        attacker.consumeSp(getSpCost());
        return damage;
    }
}
