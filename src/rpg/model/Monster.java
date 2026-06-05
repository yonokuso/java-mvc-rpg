package rpg.model;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private int level;

    public Monster(String name, ElementType type, int maxHp, int attack, List<Skill> skills) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.attack = attack;
        this.currentHp = maxHp;
        this.skills = new ArrayList<>(skills);
        this.level = 1;
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public void healFull() {
        currentHp = getMaxHp();
    }

    public int drinkFairySpring(int currentLevel) {
        int beforeHp = currentHp;
        int recoveryAmount = Math.max(0, 10 * currentLevel);
        currentHp = Math.min(getMaxHp(), currentHp + recoveryAmount);
        return currentHp - beforeHp;
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
        return maxHp * level;
    }

    public int getAttack() {
        return attack * level;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    @Override
    public String toString() {
        return name + " [" + type + "] HP " + currentHp + "/" + getMaxHp();
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        if (level <= 0) {
            level = 1;
        }
    }
}
