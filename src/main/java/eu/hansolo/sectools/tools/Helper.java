package eu.hansolo.sectools.tools;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.hansolo.sectools.CVERecords;
import eu.hansolo.sectools.CVERecords.CVERecord;
import eu.hansolo.sectools.CVERecords.Description;
import eu.hansolo.sectools.CVSS;
import eu.hansolo.sectools.Constants;
import eu.hansolo.sectools.EPSS;
import eu.hansolo.sectools.KevCatalog;
import eu.hansolo.sectools.Vulnerability;
import eu.hansolo.sectools.metrics.AttackComplexity;
import eu.hansolo.sectools.metrics.AttackRequirements;
import eu.hansolo.sectools.metrics.AttackVector;
import eu.hansolo.sectools.metrics.Authentification;
import eu.hansolo.sectools.metrics.AvailabilityImpact;
import eu.hansolo.sectools.metrics.AvailabilityImpactSubsequentSystem;
import eu.hansolo.sectools.metrics.AvailabilityImpactVulnerableSystem;
import eu.hansolo.sectools.metrics.ConfidentialityImpact;
import eu.hansolo.sectools.metrics.ConfidentialityImpactSubsequentSystem;
import eu.hansolo.sectools.metrics.ConfidentialityImpactVulnerableSystem;
import eu.hansolo.sectools.metrics.ExploitMaturity;
import eu.hansolo.sectools.metrics.IntegrityImpact;
import eu.hansolo.sectools.metrics.IntegrityImpactSubsequentSystem;
import eu.hansolo.sectools.metrics.IntegrityImpactVulnerableSystem;
import eu.hansolo.sectools.metrics.Metric;
import eu.hansolo.sectools.metrics.PrivilegesRequired;
import eu.hansolo.sectools.metrics.Scope;
import eu.hansolo.sectools.metrics.UserInteraction;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static eu.hansolo.sectools.Constants.COMMA;
import static eu.hansolo.sectools.Constants.NEW_LINE;
import static java.nio.charset.StandardCharsets.UTF_8;


public class Helper {
    private static HttpClient httpClient;

    public static final int clamp(final int min, final int max, final int value) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    public static final long clamp(final long min, final long max, final long value) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    public static final double clamp(final double min, final double max, final double value) {
        if (Double.compare(value, min) < 0) return min;
        if (Double.compare(value, max) > 0) return max;
        return value;
    }
    public static final Instant clamp(final Instant min, final Instant max, final Instant value) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }
    public static final LocalDateTime clamp(final LocalDateTime min, final LocalDateTime max, final LocalDateTime value) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }
    public static final LocalDate clamp(final LocalDate min, final LocalDate max, final LocalDate value) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }

    public static final String getCVEDetails(final String cveId) {
        final Optional<CVERecord> cveRecordOpt = Helper.getCveRecord(cveId);
        if (cveRecordOpt.isEmpty()) {
            return "";
        } else {
            final CVERecord cveRecord = cveRecordOpt.get();
            StringBuilder infoBuilder = new StringBuilder().append("CVE ID        : ").append(cveId).append(NEW_LINE);
            if (cveRecord.cveMetadata() != null) {
                if (cveRecord.cveMetadata().datePublished() != null) {
                    final String        datePublishedString = cveRecord.cveMetadata().datePublished();
                    /*
                    Constants.CVE_TIMESTAMP_MATCHER.reset(datePublishedString);
                    if (Constants.CVE_TIMESTAMP_MATCHER.matches()) {
                        final int groupCount = Constants.CVE_TIMESTAMP_MATCHER.groupCount();
                        int year;
                        int month;
                        int day;
                        if (groupCount == 24) {
                            year  = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(16));
                            month = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(18));
                            day   = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(19));
                        } else if (groupCount == 22) {
                            year  = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(11));
                            month = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(13));
                            day   = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(14));
                        } else {
                            year  = 0;
                            month = 0;
                            day   = 0;
                        }
                        final int hour    = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(20));
                        final int minute  = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(21));
                        final int second  = Integer.parseInt(Constants.CVE_TIMESTAMP_MATCHER.group(22));
                        LocalDateTime datePubl = LocalDateTime.of(year, month, day, hour, minute, second);
                    }
                    */
                    final LocalDateTime datePublished = datePublishedString.contains("Z") ? LocalDateTime.parse(datePublishedString, Constants.CVE_INFO_DTF) : LocalDateTime.parse(datePublishedString);
                    infoBuilder.append("Date published: ").append(Constants.MAIN_DTF.format(datePublished)).append(NEW_LINE);
                }
                if (cveRecord.cveMetadata().dateUpdated() != null) {
                    final String        dateUpdateString = cveRecord.cveMetadata().dateUpdated();
                    final LocalDateTime dateUpdated      = dateUpdateString.contains("Z") ? LocalDateTime.parse(dateUpdateString, Constants.CVE_INFO_DTF) : LocalDateTime.parse(dateUpdateString);
                    infoBuilder.append("Date updated  : ").append(Constants.MAIN_DTF.format(dateUpdated)).append(NEW_LINE);
                }
                if (cveRecord.cveMetadata().state() != null) {
                    infoBuilder.append("State         : ").append(cveRecord.cveMetadata().state()).append(NEW_LINE);
                }
            }
            if (cveRecord.containers().cna().descriptions() != null) {
                for (Description description : cveRecord.containers().cna().descriptions()) {
                    if (null == description) { continue; }
                    if (description.lang() != null) {
                        if (description.lang().equals("en")) {
                            if (description.value() != null) {
                                infoBuilder.append("Description   : ").append(NEW_LINE).append(description.value()).append(NEW_LINE);
                            }
                        }
                    }
                }
            }

            if (cveRecord.containers().cna().metrics() != null) {
                String cvss         = "";
                double baseScore    = 0.0;
                String vectorString = "";
                for (CVERecords.Metric metric : cveRecord.containers().cna().metrics()) {
                    if (null != metric.cvssV2_0()) {
                        cvss         = metric.cvssV2_0().version();
                        baseScore    = metric.cvssV2_0().baseScore();
                        vectorString = metric.cvssV2_0().vectorString();
                    } else if (null != metric.cvssV3_0()) {
                        cvss         = metric.cvssV3_0().version();
                        baseScore    = metric.cvssV3_0().baseScore();
                        vectorString = metric.cvssV3_0().vectorString();
                    } else if (null != metric.cvssV3_1()) {
                        cvss         = metric.cvssV3_1().version();
                        baseScore    = metric.cvssV3_1().baseScore();
                        vectorString = metric.cvssV3_1().vectorString();
                    } else if (null != metric.cvssV4_0()) {
                        cvss         = metric.cvssV4_0().version();
                        baseScore    = metric.cvssV4_0().baseScore();
                        vectorString = metric.cvssV4_0().vectorString();
                    }
                }
                if (!cvss.isEmpty()) {
                    infoBuilder.append("CVSS          : ").append(cvss).append(NEW_LINE)
                               .append("Base score    : ").append(String.format(Locale.US, "%.1f", baseScore)).append(" (Source GitHub, Inc.)").append(NEW_LINE)
                               .append("VectorString  : ").append(vectorString);
                }
            }
            return infoBuilder.toString();
        }
    }
    public static final Optional<CVERecord> getCveRecord(final String cveId) {
        if (null == cveId || cveId.isEmpty()) { return Optional.empty(); }
        final String uri      = getPathFromCveId(cveId);
        final String jsonText = getTextFromUrl(uri);
        try {
            CVERecord cveRecord = new Gson().fromJson(jsonText, CVERecord.class);
            return Optional.of(cveRecord);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    public static final String getPathFromCveId(final String cveId) {
        if (null == cveId || cveId.isEmpty()) { return ""; }
        final String CVE_ID = cveId.toUpperCase();
        StringBuilder pathBuilder = new StringBuilder(Constants.CVE_LIST_GITHUB_URL).append("cves").append(File.separator);
        Constants.CVE_ID_MATCHER.reset(CVE_ID);
        if (Constants.CVE_ID_MATCHER.matches()) {
            final String year   = Constants.CVE_ID_MATCHER.group(1);
            final String number = Constants.CVE_ID_MATCHER.group(2);
            if (null != year && null != number && number.length() > 3) {
                pathBuilder.append(year).append(File.separator).append(number.substring(0, number.length() - 3)).append("xxx").append(File.separator).append(CVE_ID).append(".json");
            }
        }
        return pathBuilder.toString();
    }

    public static final Map<String, Metric> getMetricsFromVectorString(final String vectorString) {
        final Map<String, Metric> metrics = new HashMap<>();
        final String[] parts = removeBackslashes(vectorString).split("\\/");
        Arrays.stream(parts).forEach(part -> {
            final String[] metricParts = part.split(":");
            final String metric = metricParts[0];
            final String value  = metricParts[1];
            switch (metric) {
                case AttackVector.METRIC_SHORT                          -> metrics.put(AttackVector.METRIC_SHORT, AttackVector.fromText(value));
                case AttackComplexity.METRIC_SHORT                      -> metrics.put(AttackComplexity.METRIC_SHORT, AttackComplexity.fromText(value));
                case AttackRequirements.METRIC_SHORT                    -> metrics.put(AttackRequirements.METRIC_SHORT, AttackRequirements.fromText(value));
                case Authentification.METRIC_SHORT                      -> metrics.put(Authentification.METRIC_SHORT, Authentification.fromText(value));
                case PrivilegesRequired.METRIC_SHORT                    -> metrics.put(PrivilegesRequired.METRIC_SHORT, PrivilegesRequired.fromText(value));
                case UserInteraction.METRIC_SHORT                       -> metrics.put(UserInteraction.METRIC_SHORT, UserInteraction.fromText(value));
                case Scope.METRIC_SHORT                                 -> metrics.put(Scope.METRIC_SHORT, Scope.fromText(value));
                case ConfidentialityImpact.METRIC_SHORT                 -> metrics.put(ConfidentialityImpact.METRIC_SHORT, ConfidentialityImpact.fromText(value));
                case IntegrityImpact.METRIC_SHORT                       -> metrics.put(IntegrityImpact.METRIC_SHORT, IntegrityImpact.fromText(value));
                case AvailabilityImpact.METRIC_SHORT                    -> metrics.put(AvailabilityImpact.METRIC_SHORT, AvailabilityImpact.fromText(value));
                case ConfidentialityImpactVulnerableSystem.METRIC_SHORT -> metrics.put(ConfidentialityImpactVulnerableSystem.METRIC_SHORT, ConfidentialityImpactVulnerableSystem.fromText(value));
                case IntegrityImpactVulnerableSystem.METRIC_SHORT       -> metrics.put(IntegrityImpactVulnerableSystem.METRIC_SHORT, IntegrityImpactVulnerableSystem.fromText(value));
                case AvailabilityImpactVulnerableSystem.METRIC_SHORT    -> metrics.put(AvailabilityImpactVulnerableSystem.METRIC_SHORT, AvailabilityImpactVulnerableSystem.fromText(value));
                case ConfidentialityImpactSubsequentSystem.METRIC_SHORT -> metrics.put(ConfidentialityImpactSubsequentSystem.METRIC_SHORT, ConfidentialityImpactSubsequentSystem.fromText(value));
                case IntegrityImpactSubsequentSystem.METRIC_SHORT       -> metrics.put(IntegrityImpactSubsequentSystem.METRIC_SHORT, IntegrityImpactSubsequentSystem.fromText(value));
                case AvailabilityImpactSubsequentSystem.METRIC_SHORT    -> metrics.put(AvailabilityImpactSubsequentSystem.METRIC_SHORT, AvailabilityImpactSubsequentSystem.fromText(value));
                case ExploitMaturity.METRIC_SHORT                       -> metrics.put(ExploitMaturity.METRIC_SHORT, ExploitMaturity.fromText(value));
            }
        });
        return metrics;
    }
    public static final CVSS getCVSSFromVectorString(final String vectorString) {
        if (vectorString == null || vectorString.isEmpty()) { return CVSS.NOT_FOUND; }
        if (vectorString.startsWith(CVSS.CVSSV40.vectorStringPrefix)) {
            return CVSS.CVSSV40;
        } else if (vectorString.startsWith(CVSS.CVSSV31.vectorStringPrefix)) {
            return CVSS.CVSSV31;
        } else if (vectorString.startsWith(CVSS.CVSSV30.vectorStringPrefix)) {
            return CVSS.CVSSV30;
        } else {
            return CVSS.CVSSV20;
        }
    }
    public static final boolean metricsValid(final CVSS cvss, final Map<String, Metric> metrics) {
        return (metrics.values().stream().filter(metric -> metric.getApiString().isEmpty()).count() == 0 && metrics.values().stream().map(metric -> metric.getMetricShort()).toList().containsAll(cvss.mandatoryMetrics));
    }

    public static final String getKevCatalogFilename() {
        final String filename = new StringBuilder(Constants.HOME_FOLDER).append(Constants.HOME_FOLDER.endsWith(File.separator) ? "" : File.separator).append(Constants.KEV_CATALOG_FILENAME).toString();
        return filename;
    }
    public static final KevCatalog loadKevCatalog() {
        final String filename = getKevCatalogFilename();
        boolean kevCatalogDownloaded = getKevCatalog();
        if (!kevCatalogDownloaded) {
            if (!new File(filename).exists()) {
                System.out.println("There was a problem downloading the KVE catalog");
                return null;
            } else {
                System.out.println("There was a problem downloading the KVE catalog, so we load the existing one");
            }
        }
        try {
            String jsonText = readTextFile(filename, Charset.forName("UTF-8"));
            Gson   gson     = new Gson();
            JsonElement element = gson.fromJson(jsonText, JsonElement.class);
            if (element instanceof JsonObject) {
                final JsonObject json           = element.getAsJsonObject();
                final String     title          = json.has(Constants.KEV_CATALOG_TITLE)           ? json.get(Constants.KEV_CATALOG_TITLE).getAsString()           : "";
                final String     catalogVersion = json.has(Constants.KEV_CATALOG_CATALOG_VERSION) ? json.get(Constants.KEV_CATALOG_CATALOG_VERSION).getAsString() : "";
                final String     dateReleased   = json.has(Constants.KEV_CATALOG_DATE_RELEASED)   ? json.get(Constants.KEV_CATALOG_DATE_RELEASED).getAsString()   : "";
                final long       count          = json.has(Constants.KEV_CATALOG_COUNT)           ? json.get(Constants.KEV_CATALOG_COUNT).getAsLong()             : 0;
                if (!json.has(Constants.KEV_CATALOG_VULNERABILITIES)) { return null; }
                final List<Vulnerability> vulnerabilities     = new ArrayList<>();
                final JsonArray           vulnerabilitesArray = json.getAsJsonArray(Constants.KEV_CATALOG_VULNERABILITIES);
                for (int i = 0 ; i < vulnerabilitesArray.size() ; i++) {
                    final JsonObject jsonObj = vulnerabilitesArray.get(i).getAsJsonObject();
                    final String     cveId                      = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_CVE_ID)                        ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_CVE_ID).getAsString()                        : "";
                    final String     vendorProject              = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_VENDOR_PROJECT)                ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_VENDOR_PROJECT).getAsString()                : "";
                    final String     product                    = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_PRODUCT)                       ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_PRODUCT).getAsString()                       : "";
                    final String     vulnerabilityName          = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_VULNERABILITY_NAME)            ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_VULNERABILITY_NAME).getAsString()            : "";
                    final String     dateAdded                  = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_DATE_ADDED)                    ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_DATE_ADDED).getAsString()                    : "";
                    final String     shortDescription           = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_SHORT_DESCRIPTION)             ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_SHORT_DESCRIPTION).getAsString()             : "";
                    final String     requiredAction             = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_REQUIRED_ACTION)               ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_REQUIRED_ACTION).getAsString()               : "";
                    final String     dueDate                    = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_DUE_DATE)                      ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_DUE_DATE).getAsString()                      : "";
                    final String     knownRansomwareCampaignUse = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_KNOWN_RANSOMWARE_CAMPAIGN_USE) ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_KNOWN_RANSOMWARE_CAMPAIGN_USE).getAsString() : "";
                    final String     notes                      = jsonObj.has(Constants.KEV_CATALOG_VULNERABILITY_NOTES)                         ? jsonObj.get(Constants.KEV_CATALOG_VULNERABILITY_NOTES).getAsString()                         : "";
                    if (cveId.isEmpty()) { continue; }
                    vulnerabilities.add(new Vulnerability(cveId, vendorProject, product, vulnerabilityName, LocalDate.parse(dateAdded, Constants.KEV_DF), shortDescription, requiredAction, LocalDate.parse(dueDate), knownRansomwareCampaignUse, notes));
                }
                return new KevCatalog(title, catalogVersion, LocalDateTime.parse(dateReleased, Constants.KEV_CATALOG_DTF), count, vulnerabilities);
            }
            return null;
        } catch (IOException e) {
            System.out.println("Error loading KVE catalog " + filename + " with exception " + e);
            return null;
        }
    }
    public static final boolean getKevCatalog() {
        final String filename = getKevCatalogFilename();
        if (new File(filename).exists()) {
            final Instant lastModified = getLastModifiedDateFor(filename);
            if (Instant.now().getEpochSecond() - lastModified.getEpochSecond() < Constants.KEV_CATALOG_UPDATE_INTERVAL) {
                System.out.println("KVE catalog still up to date");
                return true;
            }
        }

        HttpResponse<String> response = get(Constants.KEV_CATALOG_URL);
        if (null == response || null == response.body() || response.body().isEmpty()) {
            return false;
        } else {
            final String jsonText       = response.body();
            saveTextFile(jsonText, filename);
            return true;
        }
    }

    public static final Map<String, EPSS> getListOfEPSS(final List<String> cveIds) {
        if (null == cveIds || cveIds.isEmpty()) { return new HashMap<>(); }
        StringBuilder builder = new StringBuilder();
        cveIds.forEach(cveId -> builder.append(cveId).append(COMMA));
        if (builder.length() > 14) { builder.setLength(builder.length() - 1); }
        HttpResponse<String> response = Helper.get(Constants.FIRST_EPSS_URL + builder);
        if (null == response || null == response.body() || response.body().isEmpty()) {
            return new HashMap<>();
        } else {
            Map<String, EPSS> epssMap   = new HashMap<>();
            final String      jsonText  = response.body();
            final Gson        gson      = new Gson();
            final JsonObject  resultObj = gson.fromJson(jsonText, JsonElement.class).getAsJsonObject();
            if (resultObj.has(Constants.EPSS_DATA)) {
                JsonArray dataArray = resultObj.get(Constants.EPSS_DATA).getAsJsonArray();
                for (JsonElement jsonElement : dataArray) {
                    JsonObject dataObj    = jsonElement.getAsJsonObject();
                    String     cveId      = dataObj.has(Constants.EPSS_CVE)        ? dataObj.get(Constants.EPSS_CVE).getAsString()                                      : null;
                    double     score      = dataObj.has(Constants.EPSS_EPSS)       ? Double.parseDouble(dataObj.get(Constants.EPSS_EPSS).getAsString())                 : 0.0;
                    double     percentile = dataObj.has(Constants.EPSS_PERCENTILE) ? Double.parseDouble(dataObj.get(Constants.EPSS_PERCENTILE).getAsString())           : 0.0;
                    LocalDate  date       = dataObj.has(Constants.EPSS_DATE)       ? LocalDate.parse(dataObj.get(Constants.EPSS_DATE).getAsString(), Constants.EPSS_DF) : LocalDate.MIN;
                    if (cveId != null && score > 0) {
                        epssMap.put(cveId, new EPSS(score, percentile, date));
                    }
                }
            }
            return epssMap;
        }
    }

    public static final String addSuffixTo(final int number) {
        if (number >= 11 && number <= 13) { return number + "th"; }
        switch(number % 10) {
            case 1  -> { return number + "st"; }
            case 2  -> { return number + "nd"; }
            case 3  -> { return number + "rd"; }
            default -> { return number + "th"; }
        }
    }

    public static final String removeBackslashes(final String text) {
        return text.replaceAll("\\\\", "");
    }

    public static final String getTextFromUrl(final String uri) {
        try (var stream = URI.create(uri).toURL().openStream()) {
            return new String(stream.readAllBytes(), UTF_8);
        } catch(Exception e) {
            System.out.println("Error reading text from: " + uri);
            return "";
        }
    }

    public static final String readTextFile(final String filename, final Charset charset) throws IOException {
        //Charset charset = Charset.forName("UTF-8");
        return Files.readString(Paths.get(filename), null == charset ? Charset.forName("UTF-8") : charset);
    }
    public static final boolean saveTextFile(final String text, final String filename) {
        try {
            Files.write(Paths.get(filename), text.getBytes());
            return true;
        } catch (IOException e) {
            System.out.println("Error saving text file " + filename + ". " + e);
            return false;
        }
    }

    public static final Instant getLastModifiedDateFor(String filename) {
        try {
            Path file = Paths.get(filename);
            if (!file.toFile().exists()) { throw new IllegalArgumentException("File does not exist"); }
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            //FileTime            creationTime   = attr.creationTime();
            //FileTime            lastAccessTime = attr.lastAccessTime();
            FileTime lastModified = attr.lastModifiedTime();
            return lastModified.toInstant();
        } catch (IOException e) {
            return Instant.MIN;
        }
    }


    // ******************** REST calls ****************************************
    public static HttpClient createHttpClient() {
        return HttpClient.newBuilder()
                         .connectTimeout(Duration.ofSeconds(20))
                         .followRedirects(Redirect.NORMAL)
                         .version(java.net.http.HttpClient.Version.HTTP_2)
                         .build();
    }

    // Synchronous
    public static final HttpResponse<String> post(final String uri, final String apiKey, final String body) {
        return post(uri, apiKey, body, "");
    }
    public static final HttpResponse<String> post(final String uri, final String apiKey, final String body, final String userAgent) {
        if (null == httpClient) { httpClient = createHttpClient(); }
        final String userAgentText = (null == userAgent || userAgent.isEmpty()) ? "sectools" : "sectools (" + userAgent + ")";
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .POST(HttpRequest.BodyPublishers.ofString(body, Charset.forName("utf-8")))
                                         .setHeader("x-api-key", apiKey)
                                         .setHeader("Accept", "application/json")
                                         .setHeader("Content-Type", "application/json; charset=utf-8")
                                         .setHeader("User-Agent", userAgentText)
                                         .timeout(Duration.ofSeconds(60))
                                         .build();
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() > 199 && response.statusCode() < 203) {
                return response;
            } else {
                // Problem with url request
                return response;
            }
        } catch (CompletionException | InterruptedException | IOException e) {
            return null;
        }
    }

    public static final HttpResponse<String> get(final String uri) {
        if (null == httpClient) { httpClient = createHttpClient(); }
        HttpRequest request = HttpRequest.newBuilder()
                                         .GET()
                                         .uri(URI.create(uri))
                                         .setHeader("Accept", "application/json")
                                         .timeout(Duration.ofSeconds(60))
                                         .build();
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response;
            } else {
                // Problem with url request
                System.out.println("Error connecting to " + uri + " with response code: " + response.statusCode());
                return response;
            }
        } catch (CompletionException | InterruptedException | IOException e) {
            System.out.println("Error connecting to " + uri + " with exception: " + e);
            return null;
        }
    }
    public static final HttpResponse<String> get(final String uri, final String apiKey) { return get(uri, apiKey, ""); }
    public static final HttpResponse<String> get(final String uri, final String apiKey, final String userAgent) {
        if (null == httpClient) { httpClient = createHttpClient(); }
        final String userAgentText = (null == userAgent || userAgent.isEmpty()) ? "sectools" : "sectools (" + userAgent + ")";
        HttpRequest request = HttpRequest.newBuilder()
                                         .GET()
                                         .uri(URI.create(uri))
                                         .setHeader("Accept", "application/json")
                                         .setHeader("User-Agent", userAgentText)
                                         .setHeader("x-api-key", apiKey) // needed for Intelligence Cloud authentification
                                         .timeout(Duration.ofSeconds(60))
                                         .build();
        //System.out.println(request.toString());
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response;
            } else if (response.statusCode() == 503) {
                System.out.println("Rate limited");
                return response;
            } else if (response.statusCode() == 403) {
                System.out.println("Forbidden");
                return response;
            } else {
                // Problem with url request
                return response;
            }
        } catch (CompletionException | InterruptedException | IOException e) {
            return null;
        }
    }

    public static final HttpResponse<String> httpHeadRequestSync(final String uri) {
        if (null == httpClient) { httpClient = createHttpClient(); }

        final HttpRequest request = HttpRequest.newBuilder()
                                               .HEAD()
                                               .method("HEAD", HttpRequest.BodyPublishers.noBody())
                                               .uri(URI.create(uri))
                                               .build();

        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            return response;
        } catch (CompletionException | InterruptedException | IOException e) {
            return null;
        }
    }
}
