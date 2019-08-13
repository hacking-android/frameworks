/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface TimeStampHeader
extends Header {
    public static final String NAME = "Timestamp";

    public float getDelay();

    public long getTime();

    public int getTimeDelay();

    public float getTimeStamp();

    public boolean hasDelay();

    public void removeDelay();

    public void setDelay(float var1) throws InvalidArgumentException;

    public void setTime(long var1) throws InvalidArgumentException;

    public void setTimeDelay(int var1) throws InvalidArgumentException;

    public void setTimeStamp(float var1) throws InvalidArgumentException;
}

