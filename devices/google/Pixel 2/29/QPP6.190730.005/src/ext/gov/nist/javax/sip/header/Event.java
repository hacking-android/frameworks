/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.EventHeader;

public class Event
extends ParametersHeader
implements EventHeader {
    private static final long serialVersionUID = -6458387810431874841L;
    protected String eventType;

    public Event() {
        super("Event");
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        String string = this.eventType;
        if (string != null) {
            stringBuffer.append(string);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public String getEventId() {
        return this.getParameter("id");
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    public boolean match(Event event) {
        boolean bl;
        block5 : {
            String string = event.eventType;
            bl = false;
            if (string == null && this.eventType != null) {
                return false;
            }
            if (event.eventType != null && this.eventType == null) {
                return false;
            }
            if (this.eventType == null && event.eventType == null) {
                return false;
            }
            if (this.getEventId() == null && event.getEventId() != null) {
                return false;
            }
            if (this.getEventId() != null && event.getEventId() == null) {
                return false;
            }
            if (!event.eventType.equalsIgnoreCase(this.eventType) || this.getEventId() != event.getEventId() && !this.getEventId().equalsIgnoreCase(event.getEventId())) break block5;
            bl = true;
        }
        return bl;
    }

    @Override
    public void setEventId(String string) throws ParseException {
        if (string != null) {
            this.setParameter("id", string);
            return;
        }
        throw new NullPointerException(" the eventId parameter is null");
    }

    @Override
    public void setEventType(String string) throws ParseException {
        if (string != null) {
            this.eventType = string;
            return;
        }
        throw new NullPointerException(" the eventType is null");
    }
}

