package rpg.model;

import java.io.Serializable;

public abstract class Skill implements Serializable {
    private final String name;
    private final int power;
    private final ElementType type;

    protected Skill(String name, int power, ElementType type) {
        this.name = name;
        this.power = power;
        this.type = type;
    }

    public abstract int use(Monster attacker, Monster defender);

    protected int calculateBaseDamage(Monster attacker, Monster defender) {
        double typeBonus = type.multiplierAgainst(defender.getType());
        return Math.max(1, (int) Math.round((power + attacker.getAttack()) * typeBonus));
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public ElementType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + power + ")";
    }
}
