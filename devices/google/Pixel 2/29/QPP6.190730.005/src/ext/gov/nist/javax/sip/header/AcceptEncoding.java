/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.AcceptEncodingHeader;

public final class AcceptEncoding
extends ParametersHeader
implements AcceptEncodingHeader {
    private static final long serialVersionUID = -1476807565552873525L;
    protected String contentCoding;

    public AcceptEncoding() {
        super("Accept-Encoding");
    }

    @Override
    protected String encodeBody() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        String string = this.contentCoding;
        if (string != null) {
            stringBuffer.append(string);
        }
        if (this.parameters != null && !this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer;
    }

    @Override
    public String getEncoding() {
        return this.contentCoding;
    }

    @Override
    public float getQValue() {
        return this.getParameterAsFloat("q");
    }

    @Override
    public void setEncoding(String string) throws ParseException {
        if (string != null) {
            this.contentCoding = string;
            return;
        }
        throw new NullPointerException(" encoding parameter is null");
    }

    @Override
    public void setQValue(float f) throws InvalidArgumentException {
        if (!((double)f < 0.0) && !((double)f > 1.0)) {
            super.setParameter("q", f);
            return;
        }
        throw new InvalidArgumentException("qvalue out of range!");
    }
}

