/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;
import javax.sip.header.SIPETagHeader;

public class SIPETag
extends SIPHeader
implements SIPETagHeader,
ExtensionHeader {
    private static final long serialVersionUID = 3837543366074322107L;
    protected String entityTag;

    public SIPETag() {
        super("SIP-ETag");
    }

    public SIPETag(String string) throws ParseException {
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
        throw new NullPointerException("JAIN-SIP Exception,SIP-ETag, setETag(), the etag parameter is null");
    }

    @Override
    public void setValue(String string) throws ParseException {
        this.setETag(string);
    }
}

