/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface RAckHeader
extends Header {
    public static final String NAME = "RAck";

    public int getCSeqNumber();

    public long getCSequenceNumber();

    public String getMethod();

    public int getRSeqNumber();

    public long getRSequenceNumber();

    public void setCSeqNumber(int var1) throws InvalidArgumentException;

    public void setCSequenceNumber(long var1) throws InvalidArgumentException;

    public void setMethod(String var1) throws ParseException;

    public void setRSeqNumber(int var1) throws InvalidArgumentException;

    public void setRSequenceNumber(long var1) throws InvalidArgumentException;
}

