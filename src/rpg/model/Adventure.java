package rpg.model;

import java.util.List;
import java.util.Random;

public class Adventure {
    public static final int ESCAPE_STAGE_INDEX = 5;
    private static final int FAIRY_SPRING_FIRST_STAGE_INDEX = 2;
    private static final int FAIRY_SPRING_SECOND_STAGE_INDEX = 4;
    private static final double FAIRY_SPRING_CHANCE = 0.9;
    private static final double FAIRY_SPRING_ALL_UP_CHANCE = 0.5;

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

    public String applyFairySpringBlessing(Player player, FairySpringBlessing blessing) {
        Monster monster = player.getSelectedMonster();
        int skillPowerAmount = getSkillPowerBlessingAmount(player);
        int hpAmount = getHpBlessingAmount(player);
        int spAmount = getSpBlessingAmount(player);

        switch (blessing) {
            case SKILL_POWER:
                monster.increaseSkillPower(skillPowerAmount);
                return "요정의 샘물이 스킬 공격력을 "
                        + skillPowerAmount + " 올려주었다.";
            case HP:
                monster.increaseMaxHp(hpAmount);
                return "요정의 샘물이 최대 HP를 "
                        + hpAmount + " 올려주었다.";
            case SP:
                monster.increaseMaxSp(spAmount);
                return "요정의 샘물이 최대 SP를 "
                        + spAmount + " 올려주었다.";
            case ALL:
                if (random.nextDouble() >= FAIRY_SPRING_ALL_UP_CHANCE) {
                    return "요정의 샘물이 흔들렸지만 아무 일도 일어나지 않았다.";
                }
                monster.increaseSkillPower(skillPowerAmount);
                monster.increaseMaxHp(hpAmount);
                monster.increaseMaxSp(spAmount);
                return "요정의 축복이 성공했다! 스킬 공격력 +"
                        + skillPowerAmount + ", 최대 HP +" + hpAmount
                        + ", 최대 SP +" + spAmount + ".";
            default:
                throw new IllegalArgumentException("Unknown fairy spring blessing: " + blessing);
        }
    }

    private boolean isFairySpringStage(int stageIndex) {
        return stageIndex == FAIRY_SPRING_FIRST_STAGE_INDEX
                || stageIndex == FAIRY_SPRING_SECOND_STAGE_INDEX;
    }

    private int getSkillPowerBlessingAmount(Player player) {
        return Math.max(1, player.getLevel());
    }

    private int getHpBlessingAmount(Player player) {
        return 10 * Math.max(1, player.getLevel());
    }

    private int getSpBlessingAmount(Player player) {
        return Math.max(1, player.getLevel());
    }
}
