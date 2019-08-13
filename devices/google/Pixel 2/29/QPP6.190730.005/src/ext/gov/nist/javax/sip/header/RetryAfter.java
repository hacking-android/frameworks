/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.io.Serializable;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.RetryAfterHeader;

public class RetryAfter
extends ParametersHeader
implements RetryAfterHeader {
    public static final String DURATION = "duration";
    private static final long serialVersionUID = -1029458515616146140L;
    protected String comment;
    protected Integer retryAfter = new Integer(0);

    public RetryAfter() {
        super("Retry-After");
    }

    @Override
    public String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        Serializable serializable = this.retryAfter;
        if (serializable != null) {
            stringBuffer.append(serializable);
        }
        if (this.comment != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(" (");
            ((StringBuilder)serializable).append(this.comment);
            ((StringBuilder)serializable).append(")");
            stringBuffer.append(((StringBuilder)serializable).toString());
        }
        if (!this.parameters.isEmpty()) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(";");
            ((StringBuilder)serializable).append(this.parameters.encode());
            stringBuffer.append(((StringBuilder)serializable).toString());
        }
        return stringBuffer.toString();
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public int getDuration() {
        if (this.getParameter(DURATION) == null) {
            return -1;
        }
        return super.getParameterAsInt(DURATION);
    }

    @Override
    public int getRetryAfter() {
        return this.retryAfter;
    }

    @Override
    public boolean hasComment() {
        boolean bl = this.comment != null;
        return bl;
    }

    @Override
    public void removeComment() {
        this.comment = null;
    }

    @Override
    public void removeDuration() {
        super.removeParameter(DURATION);
    }

    @Override
    public void setComment(String string) throws ParseException {
        if (string != null) {
            this.comment = string;
            return;
        }
        throw new NullPointerException("the comment parameter is null");
    }

    @Override
    public void setDuration(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.setParameter(DURATION, n);
            return;
        }
        throw new InvalidArgumentException("the duration parameter is <0");
    }

    @Override
    public void setRetryAfter(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.retryAfter = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }
}

