/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface ExpiresHeader
extends Header {
    public static final String NAME = "Expires";

    public int getExpires();

    public void setExpires(int var1) throws InvalidArgumentException;
}

