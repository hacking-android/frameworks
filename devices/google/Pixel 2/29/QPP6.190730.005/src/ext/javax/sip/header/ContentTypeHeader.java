/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.MediaType;
import javax.sip.header.Parameters;

public interface ContentTypeHeader
extends Header,
MediaType,
Parameters {
    public static final String NAME = "Content-Type";

    public String getCharset();

    public void setContentType(String var1, String var2) throws ParseException;
}

