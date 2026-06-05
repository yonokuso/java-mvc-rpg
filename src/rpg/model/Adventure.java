package rpg.model;

import java.util.List;
import java.util.Random;

public class Adventure {
    public static final int ESCAPE_STAGE_INDEX = 5;
    private static final int FAIRY_SPRING_FIRST_STAGE_INDEX = 2;
    private static final int FAIRY_SPRING_SECOND_STAGE_INDEX = 4;
    private static final double FAIRY_SPRING_CHANCE = 0.45;

    private final Random random = new Random();

    public Monster createEnemy() {
        List<Monster> enemies = GameFactory.createEnemyCandidates();
        return enemies.get(random.nextInt(enemies.size()));
    }

    public boolean isEscaped(Player player) {
        return player.getStageIndex() >= ESCAPE_STAGE_INDEX;
    }

    public void completeCurrentStage(Player player) {
        player.advanceStage();
    }

    public boolean shouldShowFairySpring(Player player) {
        int stageIndex = player.getStageIndex();
        return isFairySpringStage(stageIndex) && random.nextDouble() < FAIRY_SPRING_CHANCE;
    }

    private boolean isFairySpringStage(int stageIndex) {
        return stageIndex == FAIRY_SPRING_FIRST_STAGE_INDEX
                || stageIndex == FAIRY_SPRING_SECOND_STAGE_INDEX;
    }
}
