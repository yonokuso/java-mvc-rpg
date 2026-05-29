package rpg.model;

public enum ElementType {
    FIRE,
    WATER,
    GRASS,
    NORMAL;

    public double multiplierAgainst(ElementType target) {
        if (this == FIRE && target == GRASS) {
            return 1.5;
        }
        if (this == WATER && target == FIRE) {
            return 1.5;
        }
        if (this == GRASS && target == WATER) {
            return 1.5;
        }
        if (this == GRASS && target == FIRE) {
            return 0.75;
        }
        if (this == FIRE && target == WATER) {
            return 0.75;
        }
        if (this == WATER && target == GRASS) {
            return 0.75;
        }
        return 1.0;
    }
}
