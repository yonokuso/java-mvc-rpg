package rpg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Serializable {
    private final String name;
    private final List<Monster> monsters;
    private int selectedMonsterIndex;

    public Player(String name, List<Monster> monsters) {
        this.name = name;
        this.monsters = new ArrayList<>(monsters);
        this.selectedMonsterIndex = 0;
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

    public List<Monster> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }
}
