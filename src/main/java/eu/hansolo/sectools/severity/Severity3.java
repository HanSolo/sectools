package eu.hansolo.sectools.severity;

import java.util.List;


public class Severity3 implements Severity, Comparable<Severity3> {
    public static final Severity3       ALL      = new Severity3(-1, "All 0.0 - 10.0", "all", "ALL", 0.0, 10.0);
    public static final Severity3       NONE     = new Severity3(0, "None 0.0", "none", "NONE", 0.0, 0.0);
    public static final Severity3       LOW      = new Severity3(1, "Low 0.1 - 3.9", "low", "LOW", 0.1, 3.9);
    public static final Severity3       MEDIUM   = new Severity3(2, "Medium 4.0 - 6.9", "medium", "MEDIUM", 4.0, 6.9);
    public static final Severity3       HIGH     = new Severity3(3, "High 7.0 - 8.9", "high", "HIGH", 7.0, 8.9);
    public static final Severity3       CRITICAL = new Severity3(4, "Critical 9.0 - 10.0", "critical", "CRITICAL", 9.0, 10.0);
    public static final List<Severity3> VALUES   = List.of(NONE, LOW, MEDIUM, HIGH, CRITICAL);

    public final int    order;
    public final String uiString;
    public final String apiString;
    public final String name;
    public final double minValue;
    public final double maxValue;


    private Severity3(final int order, final String uiString, final String apiString, final String name, final double minValue, final double maxValue) {
        this.order     = order;
        this.uiString  = uiString;
        this.apiString = apiString;
        this.name      = name;
        this.minValue  = minValue;
        this.maxValue  = maxValue;
    }


    public boolean contains(final double score) {
        if (score < 0 || score > maxValue) { return false; }
        return maxValue >= score && minValue <= score;
    }

    @Override public int compareTo(final Severity3 other) {
        return Integer.compare(this.order, other.order);
    }

    @Override public String toString() { return this.uiString; }


    public static final Severity3 fromValue(final double score) {
        for (final Severity3 severity3 : VALUES) {
            if (severity3.contains(score)) { return severity3; }
        }
        return NONE;
    }

    public static final Severity3 fromText(final String text) {
        switch (text) {
            case "all", "All", "ALL"                -> { return ALL; }
            case "none", "None", "NONE"             -> { return NONE; }
            case "low", "Low", "LOW"                -> { return LOW; }
            case "medium", "Medium", "MEDIUM"       -> { return MEDIUM; }
            case "high", "High", "HIGH"             -> { return HIGH; }
            case "critical", "Critical", "CRITICAL" -> { return CRITICAL; }
            default                                 -> { return NONE; }
        }
    }
}
