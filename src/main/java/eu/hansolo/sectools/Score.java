package eu.hansolo.sectools;

public enum Score {
    NONE("0", 0, 0),
    ZERO_TO_ONE("0-1", 0, 1),
    ONE_TO_TWO("1-2", 1, 2),
    TWO_TO_THREE("2-3", 2, 3),
    THREE_TO_FOUR("3-4", 3, 4),
    FOUR_TO_FIVE("4-5", 4, 5),
    FIVE_TO_SIX("5-6", 5, 6),
    SIX_TO_SEVEN("6-7", 6, 7),
    SEVEN_TO_EIGHT("7-8", 7, 8),
    EIGHT_TO_NINE("8-9", 8, 9),
    NINE_TO_TEN("9-10", 9, 10);

    public final String uiString;
    public final double minValue;
    public final double maxValue;


    Score(final String uiString, final double minValue, final double maxValue) {
        this.uiString   = uiString;
        this.minValue   = minValue;
        this.maxValue   = maxValue;
    }


    public static final Score getScoreByValue(final double value) {
        if (value >= 9) {
            return Score.NINE_TO_TEN;
        } else if (value >= 8) {
            return Score.EIGHT_TO_NINE;
        } else if (value >= 7) {
            return Score.SEVEN_TO_EIGHT;
        } else if (value >= 6) {
            return Score.SIX_TO_SEVEN;
        } else if (value >= 5) {
            return Score.FIVE_TO_SIX;
        } else if (value >= 4) {
            return Score.FOUR_TO_FIVE;
        } else if (value >= 3) {
            return Score.THREE_TO_FOUR;
        } else if (value >= 2) {
            return Score.TWO_TO_THREE;
        } else if (value >= 1) {
            return Score.ONE_TO_TWO;
        } else {
            return Score.ZERO_TO_ONE;
        }
    }
}
