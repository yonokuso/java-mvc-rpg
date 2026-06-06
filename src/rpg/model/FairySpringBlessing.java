package rpg.model;

public enum FairySpringBlessing {
    SKILL_POWER("스킬 공격력 up"),
    HP("HP up"),
    SP("SP up"),
    ALL("모두 다 up (50%)");

    private final String label;

    FairySpringBlessing(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
