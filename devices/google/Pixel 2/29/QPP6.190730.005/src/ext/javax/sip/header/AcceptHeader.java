/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.MediaType;
import javax.sip.header.Parameters;

public interface AcceptHeader
extends Header,
MediaType,
Parameters {
    public static final String NAME = "Accept";

    public boolean allowsAllContentSubTypes();

    public boolean allowsAllContentTypes();

    public float getQValue();

    public boolean hasQValue();

    public void removeQValue();

    public void setQValue(float var1) throws InvalidArgumentException;
}

