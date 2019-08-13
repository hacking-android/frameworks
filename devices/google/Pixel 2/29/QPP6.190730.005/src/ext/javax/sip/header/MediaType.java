/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;

public interface MediaType {
    public String getContentSubType();

    public String getContentType();

    public void setContentSubType(String var1) throws ParseException;

    public void setContentType(String var1) throws ParseException;
}

