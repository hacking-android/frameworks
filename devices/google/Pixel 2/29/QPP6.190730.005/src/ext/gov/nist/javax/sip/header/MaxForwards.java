/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.TooManyHopsException;

public class MaxForwards
extends SIPHeader
implements MaxForwardsHeader {
    private static final long serialVersionUID = -3096874323347175943L;
    protected int maxForwards;

    public MaxForwards() {
        super("Max-Forwards");
    }

    public MaxForwards(int n) throws InvalidArgumentException {
        super("Max-Forwards");
        this.setMaxForwards(n);
    }

    @Override
    public void decrementMaxForwards() throws TooManyHopsException {
        int n = this.maxForwards;
        if (n > 0) {
            this.maxForwards = n - 1;
            return;
        }
        throw new TooManyHopsException("has already reached 0!");
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.maxForwards);
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof MaxForwardsHeader) {
            object = (MaxForwardsHeader)object;
            if (this.getMaxForwards() != object.getMaxForwards()) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int getMaxForwards() {
        return this.maxForwards;
    }

    @Override
    public boolean hasReachedZero() {
        boolean bl = this.maxForwards == 0;
        return bl;
    }

    @Override
    public void setMaxForwards(int n) throws InvalidArgumentException {
        if (n >= 0 && n <= 255) {
            this.maxForwards = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad max forwards value ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }
}

