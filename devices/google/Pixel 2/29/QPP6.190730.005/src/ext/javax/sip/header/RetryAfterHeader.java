/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface RetryAfterHeader
extends Header,
Parameters {
    public static final String NAME = "Retry-After";

    public String getComment();

    public int getDuration();

    public int getRetryAfter();

    public boolean hasComment();

    public void removeComment();

    public void removeDuration();

    public void setComment(String var1) throws ParseException;

    public void setDuration(int var1) throws InvalidArgumentException;

    public void setRetryAfter(int var1) throws InvalidArgumentException;
}

