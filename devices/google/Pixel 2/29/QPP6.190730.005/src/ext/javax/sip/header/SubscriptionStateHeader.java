/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.Parameters;

public interface SubscriptionStateHeader
extends ExpiresHeader,
Parameters {
    public static final String ACTIVE = "Active";
    public static final String DEACTIVATED = "Deactivated";
    public static final String GIVE_UP = "Give-Up";
    public static final String NAME = "Subscription-State";
    public static final String NO_RESOURCE = "No-Resource";
    public static final String PENDING = "Pending";
    public static final String PROBATION = "Probation";
    public static final String REJECTED = "Rejected";
    public static final String TERMINATED = "Terminated";
    public static final String TIMEOUT = "Timeout";
    public static final String UNKNOWN = "Unknown";

    public String getReasonCode();

    public int getRetryAfter();

    public String getState();

    public void setReasonCode(String var1) throws ParseException;

    public void setRetryAfter(int var1) throws InvalidArgumentException;

    public void setState(String var1) throws ParseException;
}

