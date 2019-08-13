/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.RequireHeader;

public class Require
extends SIPHeader
implements RequireHeader {
    private static final long serialVersionUID = -3743425404884053281L;
    protected String optionTag;

    public Require() {
        super("Require");
    }

    public Require(String string) {
        super("Require");
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
        throw new NullPointerException("JAIN-SIP Exception, Require, setOptionTag(), the optionTag parameter is null");
    }
}

