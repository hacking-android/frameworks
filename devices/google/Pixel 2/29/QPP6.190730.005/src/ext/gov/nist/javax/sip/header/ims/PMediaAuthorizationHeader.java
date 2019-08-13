/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface PMediaAuthorizationHeader
extends Header {
    public static final String NAME = "P-Media-Authorization";

    public String getToken();

    public void setMediaAuthorizationToken(String var1) throws InvalidArgumentException;
}

