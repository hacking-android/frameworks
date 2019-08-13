/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.extensions;

import javax.sip.InvalidArgumentException;
import javax.sip.header.ExtensionHeader;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface SessionExpiresHeader
extends Parameters,
Header,
ExtensionHeader {
    public static final String NAME = "Session-Expires";

    public int getExpires();

    public String getRefresher();

    public void setExpires(int var1) throws InvalidArgumentException;

    public void setRefresher(String var1);
}

