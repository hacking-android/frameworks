/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.RAckHeader;

public class RAck
extends SIPHeader
implements RAckHeader {
    private static final long serialVersionUID = 743999286077404118L;
    protected long cSeqNumber;
    protected String method;
    protected long rSeqNumber;

    public RAck() {
        super("RAck");
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.rSeqNumber);
        stringBuffer.append(" ");
        stringBuffer.append(this.cSeqNumber);
        stringBuffer.append(" ");
        stringBuffer.append(this.method);
        return stringBuffer.toString();
    }

    @Override
    public int getCSeqNumber() {
        return (int)this.cSeqNumber;
    }

    public long getCSeqNumberLong() {
        return this.cSeqNumber;
    }

    @Override
    public long getCSequenceNumber() {
        return this.cSeqNumber;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public int getRSeqNumber() {
        return (int)this.rSeqNumber;
    }

    @Override
    public long getRSequenceNumber() {
        return this.rSeqNumber;
    }

    @Override
    public void setCSeqNumber(int n) throws InvalidArgumentException {
        this.setCSequenceNumber(n);
    }

    @Override
    public void setCSequenceNumber(long l) throws InvalidArgumentException {
        if (l > 0L && l <= 0x80000000L) {
            this.cSeqNumber = l;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad CSeq # ");
        stringBuilder.append(l);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setMethod(String string) throws ParseException {
        this.method = string;
    }

    @Override
    public void setRSeqNumber(int n) throws InvalidArgumentException {
        this.setRSequenceNumber(n);
    }

    @Override
    public void setRSequenceNumber(long l) throws InvalidArgumentException {
        if (l > 0L && this.cSeqNumber <= 0x80000000L) {
            this.rSeqNumber = l;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad rSeq # ");
        stringBuilder.append(l);
        throw new InvalidArgumentException(stringBuilder.toString());
    }
}

