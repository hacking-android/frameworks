/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.util.Calendar;
import javax.sip.header.Header;

public interface DateHeader
extends Header {
    public static final String NAME = "Date";

    public Calendar getDate();

    public void setDate(Calendar var1);
}

