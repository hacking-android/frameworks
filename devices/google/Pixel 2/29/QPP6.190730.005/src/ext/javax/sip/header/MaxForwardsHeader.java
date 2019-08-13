/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.TooManyHopsException;

public interface MaxForwardsHeader
extends Header {
    public static final String NAME = "Max-Forwards";

    public void decrementMaxForwards() throws TooManyHopsException;

    public int getMaxForwards();

    public boolean hasReachedZero();

    public void setMaxForwards(int var1) throws InvalidArgumentException;
}

