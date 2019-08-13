/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface CallIdHeader
extends Header {
    public static final String NAME = "Call-ID";

    public String getCallId();

    public void setCallId(String var1) throws ParseException;
}

