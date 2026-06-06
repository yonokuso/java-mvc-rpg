package rpg.model;

import java.io.Serializable;

public abstract class Skill implements Serializable {
    private final String name;
    private final int power;
    private final int spCost;
    private int powerBonus;

    protected Skill(String name, int power, int spCost) {
        if (spCost < 0) {
            throw new IllegalArgumentException("SP cost cannot be negative.");
        }
        this.name = name;
        this.power = power;
        this.spCost = spCost;
        this.powerBonus = 0;
    }

    public abstract int use(Monster attacker, Monster defender, int attackerLevel);

    public int calculateDamage(int attackerLevel) {
        return Math.max(1, (int) Math.round(getPower() * (1 + 0.1 * attackerLevel)));
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power + powerBonus;
    }

    public int getSpCost() {
        return spCost;
    }

    public boolean canUse(Monster attacker) {
        return attacker.getCurrentSp() >= spCost;
    }

    public void increasePower(int amount) {
        powerBonus += Math.max(0, amount);
    }

    @Override
    public String toString() {
        return name + " (" + getPower() + ")";
    }
}
