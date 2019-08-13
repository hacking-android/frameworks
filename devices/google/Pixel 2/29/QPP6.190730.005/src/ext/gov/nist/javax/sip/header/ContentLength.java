/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ContentLengthHeader;

public class ContentLength
extends SIPHeader
implements ContentLengthHeader {
    private static final long serialVersionUID = 1187190542411037027L;
    protected Integer contentLength;

    public ContentLength() {
        super("Content-Length");
    }

    public ContentLength(int n) {
        super("Content-Length");
        this.contentLength = n;
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        Integer n = this.contentLength;
        if (n == null) {
            stringBuffer.append("0");
        } else {
            stringBuffer.append(n.toString());
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof ContentLengthHeader;
        boolean bl2 = false;
        if (bl) {
            object = (ContentLengthHeader)object;
            if (this.getContentLength() == object.getContentLength()) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public boolean match(Object object) {
        return object instanceof ContentLength;
    }

    @Override
    public void setContentLength(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.contentLength = n;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, ContentLength, setContentLength(), the contentLength parameter is <0");
    }
}

