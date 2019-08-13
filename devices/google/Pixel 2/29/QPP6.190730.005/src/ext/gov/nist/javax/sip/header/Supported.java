/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.SupportedHeader;

public class Supported
extends SIPHeader
implements SupportedHeader {
    private static final long serialVersionUID = -7679667592702854542L;
    protected String optionTag;

    public Supported() {
        super("Supported");
        this.optionTag = null;
    }

    public Supported(String string) {
        super("Supported");
        this.optionTag = string;
    }

    @Override
    public String encode() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.headerName);
        ((StringBuilder)charSequence).append(":");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.optionTag != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.optionTag);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\r\n");
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public String encodeBody() {
        String string = this.optionTag;
        if (string == null) {
            string = "";
        }
        return string;
    }

    @Override
    public String getOptionTag() {
        return this.optionTag;
    }

    @Override
    public void setOptionTag(String string) throws ParseException {
        if (string != null) {
            this.optionTag = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Supported, setOptionTag(), the optionTag parameter is null");
    }
}

