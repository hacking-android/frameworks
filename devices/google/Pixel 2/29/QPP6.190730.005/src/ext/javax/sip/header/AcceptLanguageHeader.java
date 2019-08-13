/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.util.Locale;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface AcceptLanguageHeader
extends Header,
Parameters {
    public static final String NAME = "Accept-Language";

    public Locale getAcceptLanguage();

    public float getQValue();

    public boolean hasQValue();

    public void removeQValue();

    public void setAcceptLanguage(Locale var1);

    public void setLanguageRange(String var1);

    public void setQValue(float var1) throws InvalidArgumentException;
}

