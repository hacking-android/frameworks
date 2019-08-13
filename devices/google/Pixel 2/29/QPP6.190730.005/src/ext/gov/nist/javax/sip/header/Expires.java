/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ExpiresHeader;

public class Expires
extends SIPHeader
implements ExpiresHeader {
    private static final long serialVersionUID = 3134344915465784267L;
    protected int expires;

    public Expires() {
        super("Expires");
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.expires);
        return stringBuffer;
    }

    @Override
    public int getExpires() {
        return this.expires;
    }

    @Override
    public void setExpires(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.expires = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad argument ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }
}

