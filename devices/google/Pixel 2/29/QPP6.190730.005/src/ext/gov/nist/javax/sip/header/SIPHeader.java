/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.HeaderExt;
import gov.nist.javax.sip.header.SIPHeaderNames;
import gov.nist.javax.sip.header.SIPObject;
import javax.sip.header.Header;

public abstract class SIPHeader
extends SIPObject
implements SIPHeaderNames,
Header,
HeaderExt {
    protected String headerName;

    public SIPHeader() {
    }

    protected SIPHeader(String string) {
        this.headerName = string;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.headerName);
        stringBuffer.append(":");
        stringBuffer.append(" ");
        this.encodeBody(stringBuffer);
        stringBuffer.append("\r\n");
        return stringBuffer;
    }

    protected abstract String encodeBody();

    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.encodeBody());
        return stringBuffer;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    public String getHeaderValue() {
        CharSequence charSequence;
        try {
            charSequence = this.encode();
            charSequence = new StringBuffer((String)charSequence);
        }
        catch (Exception exception) {
            return null;
        }
        while (((StringBuffer)charSequence).length() > 0 && ((StringBuffer)charSequence).charAt(0) != ':') {
            ((StringBuffer)charSequence).deleteCharAt(0);
        }
        if (((StringBuffer)charSequence).length() > 0) {
            ((StringBuffer)charSequence).deleteCharAt(0);
        }
        return ((StringBuffer)charSequence).toString().trim();
    }

    @Override
    public String getName() {
        return this.headerName;
    }

    @Override
    public String getValue() {
        return this.getHeaderValue();
    }

    @Override
    public int hashCode() {
        return this.headerName.hashCode();
    }

    public boolean isHeaderList() {
        return false;
    }

    public void setHeaderName(String string) {
        this.headerName = string;
    }

    @Override
    public final String toString() {
        return this.encode();
    }
}

