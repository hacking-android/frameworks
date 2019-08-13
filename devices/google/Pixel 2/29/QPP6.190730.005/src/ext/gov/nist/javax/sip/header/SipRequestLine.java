/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import javax.sip.address.URI;

public interface SipRequestLine {
    public String getMethod();

    public String getSipVersion();

    public URI getUri();

    public String getVersionMajor();

    public String getVersionMinor();

    public void setMethod(String var1);

    public void setSipVersion(String var1);

    public void setUri(URI var1);
}

