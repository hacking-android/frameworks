/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.InReplyToHeader;

public class InReplyTo
extends SIPHeader
implements InReplyToHeader {
    private static final long serialVersionUID = 1682602905733508890L;
    protected CallIdentifier callId;

    public InReplyTo() {
        super("In-Reply-To");
    }

    public InReplyTo(CallIdentifier callIdentifier) {
        super("In-Reply-To");
        this.callId = callIdentifier;
    }

    @Override
    public Object clone() {
        InReplyTo inReplyTo = (InReplyTo)super.clone();
        CallIdentifier callIdentifier = this.callId;
        if (callIdentifier != null) {
            inReplyTo.callId = (CallIdentifier)callIdentifier.clone();
        }
        return inReplyTo;
    }

    @Override
    public String encodeBody() {
        return this.callId.encode();
    }

    @Override
    public String getCallId() {
        CallIdentifier callIdentifier = this.callId;
        if (callIdentifier == null) {
            return null;
        }
        return callIdentifier.encode();
    }

    @Override
    public void setCallId(String string) throws ParseException {
        try {
            CallIdentifier callIdentifier;
            this.callId = callIdentifier = new CallIdentifier(string);
            return;
        }
        catch (Exception exception) {
            throw new ParseException(exception.getMessage(), 0);
        }
    }
}

