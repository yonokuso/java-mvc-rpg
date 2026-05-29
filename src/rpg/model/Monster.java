package rpg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monster implements Serializable {
    private final String name;
    private final ElementType type;
    private final int maxHp;
    private final int attack;
    private int currentHp;
    private final List<Skill> skills;

    public Monster(String name, ElementType type, int maxHp, int attack, List<Skill> skills) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.attack = attack;
        this.currentHp = maxHp;
        this.skills = new ArrayList<>(skills);
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public void healFull() {
        currentHp = maxHp;
    }

    public boolean isDefeated() {
        return currentHp <= 0;
    }

    public String getName() {
        return name;
    }

    public ElementType getType() {
        return type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    @Override
    public String toString() {
        return name + " [" + type + "] HP " + currentHp + "/" + maxHp;
    }
}
