package rpg.model;

import java.util.List;

public final class GameFactory {
    private GameFactory() {
    }

    public static Player createDefaultPlayer() {
        return new Player("플레이어", List.of(
                new Monster("칼 고블린", 110, 15, List.of(
                        new AttackSkill("스워드 슬래시", 17,1),
                        new AttackSkill("찌르기", 11,0))),
                new Monster("망치 고블린", 125, 17, List.of(
                        new AttackSkill("망치 날리기", 17,1),
                        new AttackSkill("몸통박치기", 8,0))),
                new Monster("채찍 고블린", 130, 13, List.of(
                        new AttackSkill("채찍 날리기", 20,1),
                        new AttackSkill("끌어당기기", 7,0))),
                new Monster("도끼 고블린", 105, 13, List.of(
                        new AttackSkill("도끼 날리기", 19,1),
                        new AttackSkill("내려찍기", 9,0)))

                ));
    }

    public static Monster createDefaultEnemy() {
        return createSkeletonEnemy();
    }

    public static List<Monster> createEnemyCandidates() {
        return List.of(
                createSkeletonEnemy(),
                createDarkGoblinEnemy());
    }

    private static Monster createSkeletonEnemy() {
        return new Monster("스켈레톤", 60, 7, List.of(
                new AttackSkill("뼈 던지기", 8,1),
                new AttackSkill("검은 손톱", 6,0)));
    }

    private static Monster createDarkGoblinEnemy() {
        return new Monster("흑화한 고블린", 75, 8, List.of(
                new AttackSkill("어둠 베기", 9,1),
                new AttackSkill("검은 난동", 7,0)));
    }
}
