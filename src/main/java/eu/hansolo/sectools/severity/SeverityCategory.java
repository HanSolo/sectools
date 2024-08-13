package eu.hansolo.sectools.severity;

import java.util.Locale;


public enum SeverityCategory {
    ZERO(0, 0, SeverityLevel.NONE),
    ZERO_TO_ONE(0, 1, SeverityLevel.LOW),
    ONE_TO_TWO(1, 2, SeverityLevel.LOW),
    TWO_TO_THREE(2, 3, SeverityLevel.LOW),
    THREE_TO_FOUR(3, 4, SeverityLevel.LOW),
    FOUR_TO_FIVE(4, 5, SeverityLevel.MEDIUM),
    FIVE_TO_SIX(5, 6, SeverityLevel.MEDIUM),
    SIX_TO_SEVEN(6, 7, SeverityLevel.MEDIUM),
    SEVEN_TO_EIGHT(7, 8, SeverityLevel.HIGH),
    EIGHT_TO_NINE(8, 9, SeverityLevel.HIGH),
    NINE_TO_TEN(9, 10, SeverityLevel.CRITICAL);

    public double        from;
    public double        to;
    public SeverityLevel severityLevel;


    SeverityCategory(final double from, final double to, final SeverityLevel severityLevel) {
        this.from          = from;
        this.to            = to;
        this.severityLevel = severityLevel;
    }


    public String getRangeText() {
        return String.join(" - ", String.format(Locale.US, "%.0f", from), String.format(Locale.US, "%.0f", to));
    }

    public final boolean contains(final double value) {
        return value > this.from && value <= this.to;
    }

    public static final SeverityCategory getCVSSCategoryByValue(final double value) {
        if (value >= 9) {
            return SeverityCategory.NINE_TO_TEN;
        } else if (value >= 8) {
            return SeverityCategory.EIGHT_TO_NINE;
        } else if (value >= 7) {
            return SeverityCategory.SEVEN_TO_EIGHT;
        } else if (value >= 6) {
            return SeverityCategory.SIX_TO_SEVEN;
        } else if (value >= 5) {
            return SeverityCategory.FIVE_TO_SIX;
        } else if (value >= 4) {
            return SeverityCategory.FOUR_TO_FIVE;
        } else if (value >= 3) {
            return SeverityCategory.THREE_TO_FOUR;
        } else if (value >= 2) {
            return SeverityCategory.TWO_TO_THREE;
        } else if (value >= 1) {
            return SeverityCategory.ONE_TO_TWO;
        } else if (value > 0) {
            return SeverityCategory.ZERO_TO_ONE;
        } else {
            return SeverityCategory.ZERO;
        }
    }
}
