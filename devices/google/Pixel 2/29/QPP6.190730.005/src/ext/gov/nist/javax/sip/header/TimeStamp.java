/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.TimeStampHeader;

public class TimeStamp
extends SIPHeader
implements TimeStampHeader {
    private static final long serialVersionUID = -3711322366481232720L;
    protected int delay = -1;
    protected float delayFloat = -1.0f;
    protected long timeStamp = -1L;
    private float timeStampFloat = -1.0f;

    public TimeStamp() {
        super("Timestamp");
    }

    private String getDelayAsString() {
        if (this.delay == -1 && this.delayFloat == -1.0f) {
            return "";
        }
        int n = this.delay;
        if (n != -1) {
            return Integer.toString(n);
        }
        return Float.toString(this.delayFloat);
    }

    private String getTimeStampAsString() {
        if (this.timeStamp == -1L && this.timeStampFloat == -1.0f) {
            return "";
        }
        long l = this.timeStamp;
        if (l != -1L) {
            return Long.toString(l);
        }
        return Float.toString(this.timeStampFloat);
    }

    @Override
    public String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.getTimeStampAsString();
        String string2 = this.getDelayAsString();
        if (string.equals("") && string2.equals("")) {
            return "";
        }
        if (!string.equals("")) {
            stringBuffer.append(string);
        }
        if (!string2.equals("")) {
            stringBuffer.append(" ");
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    @Override
    public float getDelay() {
        float f;
        float f2 = f = this.delayFloat;
        if (f == -1.0f) {
            f2 = Float.valueOf(this.delay).floatValue();
        }
        return f2;
    }

    @Override
    public long getTime() {
        long l;
        long l2 = l = this.timeStamp;
        if (l == -1L) {
            l2 = (long)this.timeStampFloat;
        }
        return l2;
    }

    @Override
    public int getTimeDelay() {
        int n;
        int n2 = n = this.delay;
        if (n == -1) {
            n2 = (int)this.delayFloat;
        }
        return n2;
    }

    @Override
    public float getTimeStamp() {
        float f;
        block0 : {
            f = this.timeStampFloat;
            if (f != -1.0f) break block0;
            f = Float.valueOf(this.timeStamp).floatValue();
        }
        return f;
    }

    @Override
    public boolean hasDelay() {
        boolean bl = this.delay != -1;
        return bl;
    }

    @Override
    public void removeDelay() {
        this.delay = -1;
    }

    @Override
    public void setDelay(float f) throws InvalidArgumentException {
        if (f < 0.0f && f != -1.0f) {
            throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setDelay(), the delay parameter is <0");
        }
        this.delayFloat = f;
        this.delay = -1;
    }

    @Override
    public void setTime(long l) throws InvalidArgumentException {
        if (l >= -1L) {
            this.timeStamp = l;
            this.timeStampFloat = -1.0f;
            return;
        }
        throw new InvalidArgumentException("Illegal timestamp");
    }

    @Override
    public void setTimeDelay(int n) throws InvalidArgumentException {
        if (n >= -1) {
            this.delay = n;
            this.delayFloat = -1.0f;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value out of range ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public void setTimeStamp(float f) throws InvalidArgumentException {
        if (!(f < 0.0f)) {
            this.timeStamp = -1L;
            this.timeStampFloat = f;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setTimeStamp(), the timeStamp parameter is <0");
    }
}

