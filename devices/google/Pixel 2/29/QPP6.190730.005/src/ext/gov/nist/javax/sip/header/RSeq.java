/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.RSeqHeader;

public class RSeq
extends SIPHeader
implements RSeqHeader {
    private static final long serialVersionUID = 8765762413224043394L;
    protected long sequenceNumber;

    public RSeq() {
        super("RSeq");
    }

    @Override
    protected String encodeBody() {
        return Long.toString(this.sequenceNumber);
    }

    @Override
    public long getSeqNumber() {
        return this.sequenceNumber;
    }

    @Override
    public int getSequenceNumber() {
        return (int)this.sequenceNumber;
    }

    @Override
    public void setSeqNumber(long l) throws InvalidArgumentException {
        if (l > 0L && l <= 0x80000000L) {
            this.sequenceNumber = l;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad seq number ");
        stringBuilder.append(l);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setSequenceNumber(int n) throws InvalidArgumentException {
        this.setSeqNumber(n);
    }
}

