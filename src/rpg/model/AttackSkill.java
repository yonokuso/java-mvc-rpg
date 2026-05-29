package rpg.model;

public class AttackSkill extends Skill {
    public AttackSkill(String name, int power, ElementType type) {
        super(name, power, type);
    }

    @Override
    public int use(Monster attacker, Monster defender) {
        int damage = calculateBaseDamage(attacker, defender);
        defender.takeDamage(damage);
        return damage;
    }
}
