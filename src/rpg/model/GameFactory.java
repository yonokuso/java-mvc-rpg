package rpg.model;

import java.util.List;

public final class GameFactory {
    private GameFactory() {
    }

    public static Player createDefaultPlayer() {
        return new Player("Player", List.of(
                new Monster("Flameling", ElementType.FIRE, 110, 14, List.of(
                        new AttackSkill("Spark Claw", 16, ElementType.FIRE),
                        new AttackSkill("Tackle", 10, ElementType.NORMAL))),
                new Monster("Aquaffin", ElementType.WATER, 120, 12, List.of(
                        new AttackSkill("Bubble Shot", 15, ElementType.WATER),
                        new AttackSkill("Tail Hit", 11, ElementType.NORMAL))),
                new Monster("Leafairy", ElementType.GRASS, 100, 16, List.of(
                        new AttackSkill("Vine Lash", 17, ElementType.GRASS),
                        new AttackSkill("Quick Tap", 9, ElementType.NORMAL)))));
    }

    public static Monster createDefaultEnemy() {
        return new Monster("Wild Emberox", ElementType.FIRE, 115, 13, List.of(
                new AttackSkill("Fire Fang", 15, ElementType.FIRE),
                new AttackSkill("Body Slam", 10, ElementType.NORMAL)));
    }
}
