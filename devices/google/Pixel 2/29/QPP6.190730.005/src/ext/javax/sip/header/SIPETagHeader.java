/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public interface SIPETagHeader
extends ExtensionHeader {
    public static final String NAME = "SIP-ETag";

    public String getETag();

    public void setETag(String var1) throws ParseException;
}

