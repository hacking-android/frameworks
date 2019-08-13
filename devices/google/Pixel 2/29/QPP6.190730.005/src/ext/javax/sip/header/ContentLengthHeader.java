/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface ContentLengthHeader
extends Header {
    public static final String NAME = "Content-Length";

    public int getContentLength();

    public void setContentLength(int var1) throws InvalidArgumentException;
}

