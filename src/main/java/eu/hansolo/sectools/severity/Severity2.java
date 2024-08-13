package eu.hansolo.sectools.severity;

import java.util.List;


public class Severity2 implements Severity, Comparable<Severity2> {
    public static final Severity2       ALL    = new Severity2(-1, "All 0.0 - 10.0", "all", "ALL", 0.0, 10.0);
    public static final Severity2       LOW    = new Severity2(1, "Low 0.0 - 3.9", "low", "LOW", 0.0, 3.9);
    public static final Severity2       MEDIUM = new Severity2(2, "Medium 4.0 - 6.9", "medium", "MEDIUM", 4.0, 6.9);
    public static final Severity2       HIGH   = new Severity2(3, "High 7.0 - 10.0", "high", "HIGH", 7.0, 10.0);
    public static final List<Severity2> VALUES = List.of(LOW, MEDIUM, HIGH);

    public final int    order;
    public final String uiString;
    public final String apiString;
    public final String name;
    public final double minValue;
    public final double maxValue;


    private Severity2(final int order, final String uiString, final String apiString, final String name, final double minValue, final double maxValue) {
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

    @Override public int compareTo(final Severity2 other) {
        return Integer.compare(this.order, other.order);
    }

    @Override public String toString() { return this.uiString; }


    public static final Severity2 fromValue(final double score) {
        for (final Severity2 severity3 : VALUES) {
            if (severity3.contains(score)) { return severity3; }
        }
        return LOW;
    }

    public static final Severity2 fromText(final String text) {
        switch (text) {
            case "all", "All", "ALL"                -> { return ALL; }
            case "low", "Low", "LOW"                -> { return LOW; }
            case "medium", "Medium", "MEDIUM"       -> { return MEDIUM; }
            case "high", "High", "HIGH"             -> { return HIGH; }
            default                                 -> { return LOW; }
        }
    }
}
