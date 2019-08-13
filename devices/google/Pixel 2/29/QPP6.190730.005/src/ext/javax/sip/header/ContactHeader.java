/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

public interface ContactHeader
extends HeaderAddress,
Header,
Parameters {
    public static final String NAME = "Contact";

    public int getExpires();

    public float getQValue();

    public boolean isWildCard();

    public void setExpires(int var1) throws InvalidArgumentException;

    public void setQValue(float var1) throws InvalidArgumentException;

    public void setWildCard();

    public void setWildCardFlag(boolean var1);
}

