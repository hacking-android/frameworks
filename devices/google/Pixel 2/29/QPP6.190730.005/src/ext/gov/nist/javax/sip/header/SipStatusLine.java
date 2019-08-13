/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

public interface SipStatusLine {
    public String getReasonPhrase();

    public String getSipVersion();

    public int getStatusCode();

    public String getVersionMajor();

    public String getVersionMinor();

    public void setReasonPhrase(String var1);

    public void setSipVersion(String var1);

    public void setStatusCode(int var1);
}

