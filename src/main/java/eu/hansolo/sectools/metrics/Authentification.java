package eu.hansolo.sectools.metrics;


public enum Authentification implements Metric {
    MULTIPLE("Multiple", "multiple", "M"),
    SINGLE("Single", "single", "S"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "Au";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    Authentification(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final Authentification fromText(final String text) {
        switch (text) {
            case "MULTIPLE", "Multiple", "multiple", "M" -> { return Authentification.MULTIPLE; }
            case "SINGLE", "Single", "single", "S"       -> { return Authentification.SINGLE; }
            case "NONE", "None", "none", "N"             -> { return Authentification.NONE; }
            default                                      -> { return Authentification.NOT_FOUND; }
        }
    }

    public static final Authentification fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return Authentification.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(Authentification.METRIC_SHORT)) { return Authentification.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
