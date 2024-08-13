package eu.hansolo.sectools;

import java.util.Locale;
import java.util.Objects;

import static eu.hansolo.sectools.Constants.QUOTES;


public class CVE implements Comparable<CVE> {
    private String    cveId;
    private double    cvss;
    private EPSS      epss;
    private boolean   isKEV;


    public CVE(final String cveId, final double cvss) {
        this.cveId = cveId;
        this.cvss  = cvss;
        this.epss  = new EPSS();
        this.isKEV = false;
    }


    public String getCveId() { return cveId; }

    public double getCvss() { return cvss; }

    public EPSS getEpss() { return epss; }
    public void setEpss(final EPSS epss) { this.epss = epss; }

    public boolean isKEV() { return isKEV; }
    public void setKEV(final boolean isKEV) { this.isKEV = isKEV; }

    public String getUrl() { return new StringBuilder(Constants.CVE_DETAIL_BASE_URL).append(this.cveId.toUpperCase()).toString(); }

    public String getCsvString() {
        StringBuilder csvBuilder = new StringBuilder();
        if (epss.getScore() < 0) {
            csvBuilder.append(QUOTES).append(cveId).append(QUOTES).append(Constants.CSV_DELIMITER)
                      .append(cvss).append(Constants.CSV_DELIMITER)
                      .append(isKEV ? "1" : "0").append(Constants.CSV_DELIMITER)
                      .append(Constants.CSV_DELIMITER)
                      .append(Constants.CSV_DELIMITER)
                      .append(QUOTES).append(QUOTES);
        } else {
            csvBuilder.append(QUOTES).append(cveId).append(QUOTES).append(Constants.CSV_DELIMITER)
                      .append(cvss).append(Constants.CSV_DELIMITER)
                      .append(isKEV ? "1" : "0").append(Constants.CSV_DELIMITER)
                      .append(String.format(Locale.US, "%.2f", epss.getScore() * 100.0)).append(Constants.CSV_DELIMITER)
                      .append(epss.getPercentileAsInt()).append(Constants.CSV_DELIMITER)
                      .append(QUOTES).append(Constants.MAIN_DF.format(epss.getDate())).append(QUOTES);
        }
        return csvBuilder.toString();
    }

    @Override public int compareTo(final CVE other) {
        return Double.compare(this.cvss, other.cvss);
    }

    @Override public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        CVE cve = (CVE) o;
        return Objects.equals(cveId, cve.cveId);
    }

    @Override public int hashCode() {
        return Objects.hashCode(cveId);
    }

    @Override public String toString() {
        return new StringBuilder(this.cveId).append(" (").append(this.cvss).append(")").toString();
    }
}
