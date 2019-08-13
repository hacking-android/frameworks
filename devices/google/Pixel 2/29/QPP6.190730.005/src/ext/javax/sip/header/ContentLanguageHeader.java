/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.util.Locale;
import javax.sip.header.Header;

public interface ContentLanguageHeader
extends Header {
    public static final String NAME = "Content-Language";

    public Locale getContentLanguage();

    public String getLanguageTag();

    public void setContentLanguage(Locale var1);

    public void setLanguageTag(String var1);
}

