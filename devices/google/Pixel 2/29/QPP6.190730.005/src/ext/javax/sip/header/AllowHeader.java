/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface AllowHeader
extends Header {
    public static final String NAME = "Allow";

    public String getMethod();

    public void setMethod(String var1) throws ParseException;
}

