/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface PriorityHeader
extends Header {
    public static final String EMERGENCY = "Emergency";
    public static final String NAME = "Priority";
    public static final String NON_URGENT = "Non-Urgent";
    public static final String NORMAL = "Normal";
    public static final String URGENT = "Urgent";

    public String getPriority();

    public void setPriority(String var1) throws ParseException;
}

