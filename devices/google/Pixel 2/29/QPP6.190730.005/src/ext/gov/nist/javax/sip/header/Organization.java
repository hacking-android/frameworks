/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.OrganizationHeader;

public class Organization
extends SIPHeader
implements OrganizationHeader {
    private static final long serialVersionUID = -2775003113740192712L;
    protected String organization;

    public Organization() {
        super("Organization");
    }

    @Override
    public String encodeBody() {
        return this.organization;
    }

    @Override
    public String getOrganization() {
        return this.organization;
    }

    @Override
    public void setOrganization(String string) throws ParseException {
        if (string != null) {
            this.organization = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Organization, setOrganization(), the organization parameter is null");
    }
}

