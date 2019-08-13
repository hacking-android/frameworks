/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Encoding;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface AcceptEncodingHeader
extends Encoding,
Header,
Parameters {
    public static final String NAME = "Accept-Encoding";

    public float getQValue();

    public void setQValue(float var1) throws InvalidArgumentException;
}

