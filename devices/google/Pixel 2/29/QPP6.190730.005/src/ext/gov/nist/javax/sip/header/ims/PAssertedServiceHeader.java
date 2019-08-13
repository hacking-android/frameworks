/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import javax.sip.header.Header;

public interface PAssertedServiceHeader
extends Header {
    public static final String NAME = "P-Asserted-Service";

    public String getApplicationIdentifiers();

    public String getSubserviceIdentifiers();

    public void setApplicationIdentifiers(String var1);

    public void setSubserviceIdentifiers(String var1);
}

