/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.UnsupportedHeader;

public class Unsupported
extends SIPHeader
implements UnsupportedHeader {
    private static final long serialVersionUID = -2479414149440236199L;
    protected String optionTag;

    public Unsupported() {
        super("Unsupported");
    }

    public Unsupported(String string) {
        super("Unsupported");
        this.optionTag = string;
    }

    @Override
    public String encodeBody() {
        return this.optionTag;
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
        throw new NullPointerException("JAIN-SIP Exception,  Unsupported, setOptionTag(), The option tag parameter is null");
    }
}

