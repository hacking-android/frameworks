/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;
import javax.sip.header.SIPIfMatchHeader;

public class SIPIfMatch
extends SIPHeader
implements SIPIfMatchHeader,
ExtensionHeader {
    private static final long serialVersionUID = 3833745477828359730L;
    protected String entityTag;

    public SIPIfMatch() {
        super("SIP-If-Match");
    }

    public SIPIfMatch(String string) throws ParseException {
        this();
        this.setETag(string);
    }

    @Override
    public String encodeBody() {
        return this.entityTag;
    }

    @Override
    public String getETag() {
        return this.entityTag;
    }

    @Override
    public void setETag(String string) throws ParseException {
        if (string != null) {
            this.entityTag = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,SIP-If-Match, setETag(), the etag parameter is null");
    }

    @Override
    public void setValue(String string) throws ParseException {
        this.setETag(string);
    }
}

