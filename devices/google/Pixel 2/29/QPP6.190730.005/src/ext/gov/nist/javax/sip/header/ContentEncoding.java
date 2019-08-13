/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.ContentEncodingHeader;

public class ContentEncoding
extends SIPHeader
implements ContentEncodingHeader {
    private static final long serialVersionUID = 2034230276579558857L;
    protected String contentEncoding;

    public ContentEncoding() {
        super("Content-Encoding");
    }

    public ContentEncoding(String string) {
        super("Content-Encoding");
        this.contentEncoding = string;
    }

    @Override
    public String encodeBody() {
        return this.contentEncoding;
    }

    @Override
    public String getEncoding() {
        return this.contentEncoding;
    }

    @Override
    public void setEncoding(String string) throws ParseException {
        if (string != null) {
            this.contentEncoding = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  encoding is null");
    }
}

