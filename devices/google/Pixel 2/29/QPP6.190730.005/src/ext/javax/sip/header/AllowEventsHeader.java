/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface AllowEventsHeader
extends Header {
    public static final String NAME = "Allow-Events";

    public String getEventType();

    public void setEventType(String var1) throws ParseException;
}

