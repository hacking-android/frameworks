/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface SubjectHeader
extends Header {
    public static final String NAME = "Subject";

    public String getSubject();

    public void setSubject(String var1) throws ParseException;
}

