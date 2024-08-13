package eu.hansolo.sectools;

import java.util.List;


public class EPSSFilter implements Comparable<EPSSFilter> {
    public static final EPSSFilter       ALL      = new EPSSFilter(" 0% - 100%", "all", -1, 0.0, 1.0);
    public static final EPSSFilter       LOW      = new EPSSFilter(" 0% -  25%", "low", 0, 0.0, 0.25);
    public static final EPSSFilter       MEDIUM   = new EPSSFilter("25% -  50%", "medium", 1, 0.25, 0.50);
    public static final EPSSFilter       HIGH     = new EPSSFilter("50% -  75%", "high", 2, 0.50, 0.75);
    public static final EPSSFilter       CRITICAL = new EPSSFilter("75% - 100%", "critical", 3, 0.75, 1.0);
    public static final List<EPSSFilter> VALUES   = List.of(EPSSFilter.LOW, EPSSFilter.MEDIUM, EPSSFilter.HIGH, EPSSFilter.CRITICAL);

    public final int    order;
    public final String uiString;
    public final String apiString;
    public final double minValue;
    public final double maxValue;


    EPSSFilter(final String uiString, final String apiString, final int order, final double minValue, final double maxValue) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.order     = order;
        this.minValue  = minValue;
        this.maxValue  = maxValue;
    }


    public static final EPSSFilter getCVSSFromValue(final double score) {
        for (final EPSSFilter epssFilter : VALUES) {
            if (epssFilter.contains(score)) { return epssFilter; }
        }
        return ALL;
    }

    public boolean contains(final double score) {
        if (score < 0 || score > maxValue) { return false; }
        return maxValue >= score && minValue <= score;
    }

    @Override public int compareTo(final EPSSFilter other) {
        return Integer.compare(this.order, other.order);
    }

    @Override public String toString() { return this.uiString; }
}
