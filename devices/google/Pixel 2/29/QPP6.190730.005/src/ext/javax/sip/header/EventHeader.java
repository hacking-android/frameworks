/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface EventHeader
extends Header,
Parameters {
    public static final String NAME = "Event";

    public String getEventId();

    public String getEventType();

    public void setEventId(String var1) throws ParseException;

    public void setEventType(String var1) throws ParseException;
}

