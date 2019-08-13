/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

public interface RSeqHeader
extends Header {
    public static final String NAME = "RSeq";

    public long getSeqNumber();

    public int getSequenceNumber();

    public void setSeqNumber(long var1) throws InvalidArgumentException;

    public void setSequenceNumber(int var1) throws InvalidArgumentException;
}

