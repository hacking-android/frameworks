/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.SubscriptionStateHeader;

public class SubscriptionState
extends ParametersHeader
implements SubscriptionStateHeader {
    private static final long serialVersionUID = -6673833053927258745L;
    protected int expires = -1;
    protected String reasonCode;
    protected int retryAfter = -1;
    protected String state;

    public SubscriptionState() {
        super("Subscription-State");
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        String string = this.state;
        if (string != null) {
            stringBuffer.append(string);
        }
        if (this.reasonCode != null) {
            stringBuffer.append(";reason=");
            stringBuffer.append(this.reasonCode);
        }
        if (this.expires != -1) {
            stringBuffer.append(";expires=");
            stringBuffer.append(this.expires);
        }
        if (this.retryAfter != -1) {
            stringBuffer.append(";retry-after=");
            stringBuffer.append(this.retryAfter);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public int getExpires() {
        return this.expires;
    }

    @Override
    public String getReasonCode() {
        return this.reasonCode;
    }

    @Override
    public int getRetryAfter() {
        return this.retryAfter;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setExpires(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.expires = n;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SubscriptionState, setExpires(), the expires parameter is  < 0");
    }

    @Override
    public void setReasonCode(String string) throws ParseException {
        if (string != null) {
            this.reasonCode = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SubscriptionState, setReasonCode(), the reasonCode parameter is null");
    }

    @Override
    public void setRetryAfter(int n) throws InvalidArgumentException {
        if (n > 0) {
            this.retryAfter = n;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SubscriptionState, setRetryAfter(), the retryAfter parameter is <=0");
    }

    @Override
    public void setState(String string) throws ParseException {
        if (string != null) {
            this.state = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SubscriptionState, setState(), the state parameter is null");
    }
}

