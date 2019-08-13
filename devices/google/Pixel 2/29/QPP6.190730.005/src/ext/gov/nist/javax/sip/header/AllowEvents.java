/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.AllowEventsHeader;

public final class AllowEvents
extends SIPHeader
implements AllowEventsHeader {
    private static final long serialVersionUID = 617962431813193114L;
    protected String eventType;

    public AllowEvents() {
        super("Allow-Events");
    }

    public AllowEvents(String string) {
        super("Allow-Events");
        this.eventType = string;
    }

    @Override
    protected String encodeBody() {
        return this.eventType;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventType(String string) throws ParseException {
        if (string != null) {
            this.eventType = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,AllowEvents, setEventType(), the eventType parameter is null");
    }
}

