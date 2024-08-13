package eu.hansolo.sectools;

import eu.hansolo.sectools.metrics.AttackComplexity;
import eu.hansolo.sectools.metrics.AttackRequirements;
import eu.hansolo.sectools.metrics.AttackVector;
import eu.hansolo.sectools.metrics.AvailabilityImpact;
import eu.hansolo.sectools.metrics.ConfidentialityImpact;
import eu.hansolo.sectools.metrics.IntegrityImpact;
import eu.hansolo.sectools.metrics.PrivilegesRequired;
import eu.hansolo.sectools.metrics.UserInteraction;
import eu.hansolo.sectools.severity.Severity3;

import java.util.Locale;

import static eu.hansolo.sectools.Constants.NEW_LINE;


public record CVEInfo(String cveId, String description, AttackVector attackVector, AttackComplexity attackComplexity, AttackRequirements attackRequirements, PrivilegesRequired privilegesRequired, UserInteraction userInteraction, ConfidentialityImpact confidentialityImpact, IntegrityImpact integrityImpact, AvailabilityImpact availabilityImpact, double baseScore, Severity3 severity3, double exploitabilityScore, double impactScore) {

    @Override public String toString() {
        return new StringBuilder().append("Description: ").append(NEW_LINE).append(description).append(NEW_LINE).append(NEW_LINE)
                                  .append("Attack vector         : ").append(attackVector.uiString).append(NEW_LINE)
                                  .append("Attack complexity     : ").append(attackComplexity.uiString).append(NEW_LINE)
                                  //.append("Attack requirements   : ").append(attackRequirements.uiString).append(NEW_LINE)
                                  .append("Privileges required   : ").append(privilegesRequired.uiString).append(NEW_LINE)
                                  .append("User interaction      : ").append(userInteraction.uiString).append(NEW_LINE)
                                  .append("Confidentiality impact: ").append(confidentialityImpact.uiString).append(NEW_LINE)
                                  .append("Integrity impact      : ").append(integrityImpact.uiString).append(NEW_LINE)
                                  .append("Availability impact   : ").append(availabilityImpact.uiString).append(NEW_LINE)
                                  .append("Base score            : ").append(String.format(Locale.US, "%.1f", baseScore)).append(NEW_LINE)
                                  .append("Severity              : ").append(severity3.name).append(NEW_LINE)
                                  .append("Exploitability score  : ").append(String.format(Locale.US, "%.1f", exploitabilityScore)).append(NEW_LINE)
                                  .append("Impact score          : ").append(String.format(Locale.US, "%.1f", impactScore))
                                  .toString();
    }
}
