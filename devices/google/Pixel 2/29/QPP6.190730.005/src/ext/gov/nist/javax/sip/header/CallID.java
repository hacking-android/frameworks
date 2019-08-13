/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.CallIdHeader;

public class CallID
extends SIPHeader
implements CallIdHeader {
    private static final long serialVersionUID = -6463630258703731156L;
    protected CallIdentifier callIdentifier;

    public CallID() {
        super("Call-ID");
    }

    public CallID(String string) throws IllegalArgumentException {
        super("Call-ID");
        this.callIdentifier = new CallIdentifier(string);
    }

    @Override
    public Object clone() {
        CallID callID = (CallID)super.clone();
        CallIdentifier callIdentifier = this.callIdentifier;
        if (callIdentifier != null) {
            callID.callIdentifier = (CallIdentifier)callIdentifier.clone();
        }
        return callID;
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        CallIdentifier callIdentifier = this.callIdentifier;
        if (callIdentifier != null) {
            callIdentifier.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof CallIdHeader) {
            object = (CallIdHeader)object;
            return this.getCallId().equalsIgnoreCase(object.getCallId());
        }
        return false;
    }

    @Override
    public String getCallId() {
        return this.encodeBody();
    }

    public CallIdentifier getCallIdentifer() {
        return this.callIdentifier;
    }

    @Override
    public void setCallId(String string) throws ParseException {
        try {
            CallIdentifier callIdentifier;
            this.callIdentifier = callIdentifier = new CallIdentifier(string);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ParseException(string, 0);
        }
    }

    public void setCallIdentifier(CallIdentifier callIdentifier) {
        this.callIdentifier = callIdentifier;
    }
}

