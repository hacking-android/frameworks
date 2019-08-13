/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.header.ExtensionHeader;

public class ExtensionHeaderImpl
extends SIPHeader
implements ExtensionHeader {
    private static final long serialVersionUID = -8693922839612081849L;
    protected String value;

    public ExtensionHeaderImpl() {
    }

    public ExtensionHeaderImpl(String string) {
        super(string);
    }

    @Override
    public String encode() {
        StringBuffer stringBuffer = new StringBuffer(this.headerName);
        stringBuffer.append(":");
        stringBuffer.append(" ");
        stringBuffer.append(this.value);
        stringBuffer.append("\r\n");
        return stringBuffer.toString();
    }

    @Override
    public String encodeBody() {
        return this.getHeaderValue();
    }

    @Override
    public String getHeaderValue() {
        CharSequence charSequence = this.value;
        if (charSequence != null) {
            return charSequence;
        }
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
        ((StringBuffer)charSequence).deleteCharAt(0);
        this.value = ((StringBuffer)charSequence).toString().trim();
        return this.value;
    }

    public void setName(String string) {
        this.headerName = string;
    }

    @Override
    public void setValue(String string) {
        this.value = string;
    }
}

