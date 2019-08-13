/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

public interface PServedUserHeader {
    public static final String NAME = "P-Served-User";

    public String getRegistrationState();

    public String getSessionCase();

    public void setRegistrationState(String var1);

    public void setSessionCase(String var1);
}

