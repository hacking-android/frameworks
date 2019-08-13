/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.util.Locale;
import javax.sip.InvalidArgumentException;
import javax.sip.header.AcceptLanguageHeader;

public final class AcceptLanguage
extends ParametersHeader
implements AcceptLanguageHeader {
    private static final long serialVersionUID = -4473982069737324919L;
    protected String languageRange;

    public AcceptLanguage() {
        super("Accept-Language");
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.languageRange;
        if (string != null) {
            stringBuffer.append(string);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override
    public Locale getAcceptLanguage() {
        String string = this.languageRange;
        if (string == null) {
            return null;
        }
        int n = string.indexOf(45);
        if (n >= 0) {
            return new Locale(this.languageRange.substring(0, n), this.languageRange.substring(n + 1));
        }
        return new Locale(this.languageRange);
    }

    public String getLanguageRange() {
        return this.languageRange;
    }

    @Override
    public float getQValue() {
        if (!this.hasParameter("q")) {
            return -1.0f;
        }
        return ((Float)this.parameters.getValue("q")).floatValue();
    }

    @Override
    public boolean hasQValue() {
        return this.hasParameter("q");
    }

    @Override
    public void removeQValue() {
        this.removeParameter("q");
    }

    @Override
    public void setAcceptLanguage(Locale locale) {
        if ("".equals(locale.getCountry())) {
            this.languageRange = locale.getLanguage();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(locale.getLanguage());
            stringBuilder.append('-');
            stringBuilder.append(locale.getCountry());
            this.languageRange = stringBuilder.toString();
        }
    }

    @Override
    public void setLanguageRange(String string) {
        this.languageRange = string.trim();
    }

    @Override
    public void setQValue(float f) throws InvalidArgumentException {
        if (!((double)f < 0.0) && !((double)f > 1.0)) {
            if (f == -1.0f) {
                this.removeParameter("q");
            } else {
                this.setParameter(new NameValue("q", Float.valueOf(f)));
            }
            return;
        }
        throw new InvalidArgumentException("qvalue out of range!");
    }
}

