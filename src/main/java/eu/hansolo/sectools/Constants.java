package eu.hansolo.sectools;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constants {
    public static final String            SQUARE_BRACKET_OPEN                                     = "[";
    public static final String            SQUARE_BRACKET_CLOSE                                    = "]";
    public static final String            CURLY_BRACKET_OPEN                                      = "{";
    public static final String            CURLY_BRACKET_CLOSE                                     = "}";
    public static final String            INDENTED_QUOTES                                         = "  \"";
    public static final String            QUOTES                                                  = "\"";
    public static final String            COLON                                                   = ":";
    public static final String            QUOTES_COLON                                            = "\":";
    public static final String            QUOTES_COLON_QUOTES                                     = "\":\"";
    public static final String            COMMA                                                   = ",";
    public static final String            SLASH                                                   = "/";
    public static final String            NEW_LINE                                                = "\n";
    public static final String            COMMA_NEW_LINE                                          = ",\n";
    public static final String            NULL                                                    = "null";
    public static final String            INDENT                                                  = "  ";
    public static final String            QUOTES_COMMA_QUOTES                                     = "\",\"";
    public static final String            SQUARE_BRACKET_OPEN_QUOTES                              = "[\"";
    public static final String            SQUARE_BRACKET_CLOSE_QUOTES                             = "\"]";
    public static final String            EMPTY_SQUARE_BRACKETS                                   = "[]";

    public static final String            CSV_DELIMITER                                           = "|";

    public static final String            HOME_FOLDER                                             = new StringBuilder(System.getProperty("user.home")).append(File.separator).toString();

    public static final DateTimeFormatter MAIN_DTF                                                = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter MAIN_DF                                                 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter CVE_INFO_DTF                                            = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static final String            CVE_LIST_GITHUB_URL                                     = "https://github.com/CVEProject/cvelistV5/raw/main/";
    public static final Pattern           CVE_ID_PATTERN                                          = Pattern.compile("CVE-([0-9]{4})-([0-9]+)");
    public static final Matcher           CVE_ID_MATCHER                                          = CVE_ID_PATTERN.matcher("");
    public static final String            CVE_DETAIL_BASE_URL                                     = "https://www.cvedetails.com/cve/";

    public static final String            KEV_CATALOG_URL                                         = "https://www.cisa.gov/sites/default/files/feeds/known_exploited_vulnerabilities.json";
    public static final String            KEV_CATALOG_FILENAME                                    = "kev_catalog.json";
    public static final long              KEV_CATALOG_UPDATE_INTERVAL                             = 172_800; // 48h
    public static final DateTimeFormatter KEV_CATALOG_DTF                                         = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'");
    public static final DateTimeFormatter KEV_DF                                                  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String            KEV_CATALOG_TITLE                                       = "title";
    public static final String            KEV_CATALOG_CATALOG_VERSION                             = "catalogVersion";
    public static final String            KEV_CATALOG_DATE_RELEASED                               = "dateReleased";
    public static final String            KEV_CATALOG_COUNT                                       = "count";
    public static final String            KEV_CATALOG_VULNERABILITIES                             = "vulnerabilities";
    public static final String            KEV_CATALOG_VULNERABILITY_CVE_ID                        = "cveID";
    public static final String            KEV_CATALOG_VULNERABILITY_VENDOR_PROJECT                = "vendorProject";
    public static final String            KEV_CATALOG_VULNERABILITY_PRODUCT                       = "product";
    public static final String            KEV_CATALOG_VULNERABILITY_VULNERABILITY_NAME            = "vulnerabilityName";
    public static final String            KEV_CATALOG_VULNERABILITY_DATE_ADDED                    = "dateAdded";
    public static final String            KEV_CATALOG_VULNERABILITY_SHORT_DESCRIPTION             = "shortDescription";
    public static final String            KEV_CATALOG_VULNERABILITY_REQUIRED_ACTION               = "requiredAction";
    public static final String            KEV_CATALOG_VULNERABILITY_DUE_DATE                      = "dueDate";
    public static final String            KEV_CATALOG_VULNERABILITY_KNOWN_RANSOMWARE_CAMPAIGN_USE = "knownRansomwareCampaignUse";
    public static final String            KEV_CATALOG_VULNERABILITY_NOTES                         = "notes";

    public static final String            FIRST_EPSS_URL                                          = "https://api.first.org/data/v1/epss?pretty=true&cve=";
    public static final DateTimeFormatter EPSS_DF                                                 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String            EPSS_DATA                                               = "data";
    public static final String            EPSS_CVE                                                = "cve";
    public static final String            EPSS_EPSS                                               = "epss";
    public static final String            EPSS_PERCENTILE                                         = "percentile";
    public static final String            EPSS_DATE                                               = "date";

}
