package eu.hansolo.sectools;

import eu.hansolo.sectools.tools.Helper;

import java.time.LocalDate;
import java.util.Locale;

import static eu.hansolo.sectools.Constants.NEW_LINE;


public class EPSS implements Comparable<EPSS> {
    private double    score;
    private double    percentile;
    private LocalDate date;


    public EPSS() {
        this(0.0, 0.0, LocalDate.MIN);
    }
    public EPSS(final double score, final double percentile,  LocalDate date) {
        this.score      = score;
        this.percentile = percentile;
        this.date       = date;
    }


    public double getScore() { return this.score; }
    public void setScore(final double score) { this.score = Helper.clamp(0.0, 1.0, score); }

    public int getPercentileAsInt() { return Integer.parseInt(String.format(Locale.US, "%.0f", this.percentile * 100.0)); }
    public double getPercentile() { return this.percentile; }
    public void setPercentile(final double percentile) { this.percentile = percentile; }

    public LocalDate getDate() { return this.date;}
    public void setDate(final LocalDate date) { this.date = date;}

    public String getText() {
        return new StringBuilder().append(String.format(Locale.US, "%.0f%%", this.score * 100.0)).append(NEW_LINE).append("(").append(Helper.addSuffixTo(getPercentileAsInt())).append(")").toString();
    }


    @Override public int compareTo(final EPSS other) {
        return Double.compare(this.score, other.score);
    }

    @Override public String toString() {
        return new StringBuilder().append(String.format(Locale.US, "%.2f%%", this.score * 100.0)).append(" (").append(Helper.addSuffixTo(getPercentileAsInt())).append(") ").append(Constants.MAIN_DF.format(this.date)).toString();
    }
}
