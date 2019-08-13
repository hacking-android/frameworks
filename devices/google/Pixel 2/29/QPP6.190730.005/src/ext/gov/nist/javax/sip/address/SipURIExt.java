/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import javax.sip.address.SipURI;

public interface SipURIExt
extends SipURI {
    public boolean hasGrParam();

    public void removeHeader(String var1);

    public void removeHeaders();

    public void setGrParam(String var1);
}

