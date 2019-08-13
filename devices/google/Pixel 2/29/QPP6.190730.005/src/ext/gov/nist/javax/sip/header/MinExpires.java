/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.MinExpiresHeader;

public class MinExpires
extends SIPHeader
implements MinExpiresHeader {
    private static final long serialVersionUID = 7001828209606095801L;
    protected int expires;

    public MinExpires() {
        super("Min-Expires");
    }

    @Override
    public String encodeBody() {
        return Integer.toString(this.expires);
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

