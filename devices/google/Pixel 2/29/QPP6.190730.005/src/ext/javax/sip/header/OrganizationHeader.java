/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface OrganizationHeader
extends Header {
    public static final String NAME = "Organization";

    public String getOrganization();

    public void setOrganization(String var1) throws ParseException;
}

