/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

public interface ExtensionHeader
extends Header {
    public String getValue();

    public void setValue(String var1) throws ParseException;
}

