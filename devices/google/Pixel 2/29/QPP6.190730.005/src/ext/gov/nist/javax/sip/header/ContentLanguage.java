/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.util.Locale;
import javax.sip.header.ContentLanguageHeader;

public class ContentLanguage
extends SIPHeader
implements ContentLanguageHeader {
    private static final long serialVersionUID = -5195728427134181070L;
    protected Locale locale;

    public ContentLanguage() {
        super("Content-Language");
    }

    public ContentLanguage(String string) {
        super("Content-Language");
        this.setLanguageTag(string);
    }

    @Override
    public Object clone() {
        ContentLanguage contentLanguage = (ContentLanguage)super.clone();
        Locale locale = this.locale;
        if (locale != null) {
            contentLanguage.locale = (Locale)locale.clone();
        }
        return contentLanguage;
    }

    @Override
    public String encodeBody() {
        return this.getLanguageTag();
    }

    @Override
    public Locale getContentLanguage() {
        return this.locale;
    }

    @Override
    public String getLanguageTag() {
        if ("".equals(this.locale.getCountry())) {
            return this.locale.getLanguage();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.locale.getLanguage());
        stringBuilder.append('-');
        stringBuilder.append(this.locale.getCountry());
        return stringBuilder.toString();
    }

    @Override
    public void setContentLanguage(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setLanguageTag(String string) {
        int n = string.indexOf(45);
        this.locale = n >= 0 ? new Locale(string.substring(0, n), string.substring(n + 1)) : new Locale(string);
    }
}

