package rpg.model;

import java.util.List;

public final class GameFactory {
    private GameFactory() {
    }

    public static Player createDefaultPlayer() {
        return new Player("플레이어", List.of(
                new Monster("칼 든 고블린", ElementType.NORMAL, 110, 15, List.of(
                        new AttackSkill("스워드 슬래시", 17, ElementType.NORMAL),
                        new AttackSkill("찌르기", 11, ElementType.NORMAL))),
                new Monster("망치 고블린", ElementType.NORMAL, 125, 17, List.of(
                        new AttackSkill("망치 날리기", 19, ElementType.NORMAL),
                        new AttackSkill("몸통박치기", 13, ElementType.NORMAL))),
                new Monster("채찍 고블린", ElementType.NORMAL, 130, 13, List.of(
                        new AttackSkill("채찍 날리기", 15, ElementType.NORMAL),
                        new AttackSkill("끌어당기기", 12, ElementType.NORMAL)))
                
                ));
    }

    public static Monster createDefaultEnemy() {
        return new Monster("스켈레톤", ElementType.NORMAL, 120, 14, List.of(
                new AttackSkill("뼈 던지기", 16, ElementType.NORMAL),
                new AttackSkill("검은 손톱", 12, ElementType.NORMAL)));
    }
}
