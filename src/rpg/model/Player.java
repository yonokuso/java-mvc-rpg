package rpg.model;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Serializable {
    private final String name;
    private final List<Monster> monsters;
    private int selectedMonsterIndex;
    private int gold;
    private int level;
    private int stageIndex;

    public Player(String name, List<Monster> monsters) {
        this.name = name;
        this.monsters = new ArrayList<>(monsters);
        this.selectedMonsterIndex = 0;
        this.gold = 0;
        this.level = 1;
        this.stageIndex = 0;
    }

    public Monster getSelectedMonster() {
        return monsters.get(selectedMonsterIndex);
    }

    public void selectMonster(int index) {
        if (index < 0 || index >= monsters.size()) {
            throw new IllegalArgumentException("Invalid monster index: " + index);
        }
        selectedMonsterIndex = index;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        level++;
    }

    public int getStageIndex() {
        return stageIndex;
    }

    public void advanceStage() {
        stageIndex++;
    }

    public List<Monster> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        if (level <= 0) {
            level = 1;
        }
        if (stageIndex < 0) {
            stageIndex = 0;
        }
    }
}
