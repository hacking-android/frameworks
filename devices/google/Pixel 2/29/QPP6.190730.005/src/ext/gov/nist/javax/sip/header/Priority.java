/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.PriorityHeader;

public class Priority
extends SIPHeader
implements PriorityHeader {
    public static final String EMERGENCY = "emergency";
    public static final String NON_URGENT = "non-urgent";
    public static final String NORMAL = "normal";
    public static final String URGENT = "urgent";
    private static final long serialVersionUID = 3837543366074322106L;
    protected String priority;

    public Priority() {
        super("Priority");
    }

    @Override
    public String encodeBody() {
        return this.priority;
    }

    @Override
    public String getPriority() {
        return this.priority;
    }

    @Override
    public void setPriority(String string) throws ParseException {
        if (string != null) {
            this.priority = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,Priority, setPriority(), the priority parameter is null");
    }
}

