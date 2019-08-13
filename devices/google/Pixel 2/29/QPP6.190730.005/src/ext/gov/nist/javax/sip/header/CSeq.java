/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPRequest;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.CSeqHeader;

public class CSeq
extends SIPHeader
implements CSeqHeader {
    private static final long serialVersionUID = -5405798080040422910L;
    protected String method;
    protected Long seqno;

    public CSeq() {
        super("CSeq");
    }

    public CSeq(long l, String string) {
        this();
        this.seqno = l;
        this.method = SIPRequest.getCannonicalName(string);
    }

    @Override
    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.headerName);
        stringBuilder.append(":");
        stringBuilder.append(" ");
        stringBuilder.append(this.encodeBody());
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.seqno);
        stringBuffer.append(" ");
        stringBuffer.append(this.method.toUpperCase());
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CSeqHeader;
        boolean bl2 = false;
        if (bl) {
            object = (CSeqHeader)object;
            if (this.getSeqNumber() == object.getSeqNumber() && this.getMethod().equals(object.getMethod())) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public long getSeqNumber() {
        return this.seqno;
    }

    @Override
    public int getSequenceNumber() {
        Long l = this.seqno;
        if (l == null) {
            return 0;
        }
        return l.intValue();
    }

    @Override
    public void setMethod(String string) throws ParseException {
        if (string != null) {
            this.method = SIPRequest.getCannonicalName(string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, CSeq, setMethod(), the meth parameter is null");
    }

    @Override
    public void setSeqNumber(long l) throws InvalidArgumentException {
        if (l >= 0L) {
            if (l <= 0x80000000L) {
                this.seqno = l;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is too large : ");
            stringBuilder.append(l);
            throw new InvalidArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is < 0 : ");
        stringBuilder.append(l);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setSequenceNumber(int n) throws InvalidArgumentException {
        this.setSeqNumber(n);
    }
}

