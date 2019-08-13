/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface MimeVersionHeader
extends Header {
    public static final String NAME = "MIME-Version";

    public int getMajorVersion();

    public int getMinorVersion();

    public void setMajorVersion(int var1) throws InvalidArgumentException;

    public void setMinorVersion(int var1) throws InvalidArgumentException;
}

