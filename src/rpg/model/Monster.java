package rpg.model;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monster implements Serializable {
    private final String name;
    private final int maxHp;
    private final int maxSp;
    private int currentHp;
    private int currentSp;
    private int maxHpBonus;
    private int maxSpBonus;
    private final List<Skill> skills;
    private int level;

    public Monster(String name, int maxHp, int maxSp, List<Skill> skills) {
        this.name = name;
        this.maxHp = maxHp;
        this.maxSp = maxSp;
        this.currentHp = maxHp;
        this.currentSp = maxSp;
        this.maxHpBonus = 0;
        this.maxSpBonus = 0;
        this.skills = new ArrayList<>(skills);
        this.level = 1;
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public void healFull() {
        currentHp = getMaxHp();
    }

    public boolean isDefeated() {
        return currentHp <= 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return (maxHp + maxHpBonus) * level;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxSp() {
        return maxSp + maxSpBonus;
    }

    public int getCurrentSp() {
        return currentSp;
    }

    public void consumeSp(int amount) {
        currentSp = Math.max(0, currentSp - amount);
    }

    public void increaseSkillPower(int amount) {
        for (Skill skill : skills) {
            skill.increasePower(amount);
        }
    }

    public void increaseMaxHp(int amount) {
        int increaseAmount = Math.max(0, amount);
        int beforeMaxHp = getMaxHp();
        maxHpBonus += increaseAmount;
        int actualIncreaseAmount = getMaxHp() - beforeMaxHp;
        currentHp = Math.min(getMaxHp(), currentHp + actualIncreaseAmount);
    }

    public void increaseMaxSp(int amount) {
        int increaseAmount = Math.max(0, amount);
        maxSpBonus += increaseAmount;
        currentSp = Math.min(getMaxSp(), currentSp + increaseAmount);
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    @Override
    public String toString() {
        return name + " HP " + currentHp + "/" + getMaxHp()
                + " SP " + currentSp + "/" + getMaxSp();
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        if (level <= 0) {
            level = 1;
        }
        if (maxHpBonus < 0) {
            maxHpBonus = 0;
        }
        if (maxSpBonus < 0) {
            maxSpBonus = 0;
        }
        if (currentHp < 0) {
            currentHp = 0;
        }
        if (currentHp > getMaxHp()) {
            currentHp = getMaxHp();
        }
        if (currentSp < 0) {
            currentSp = 0;
        }
        if (currentSp > getMaxSp()) {
            currentSp = getMaxSp();
        }
    }
}
