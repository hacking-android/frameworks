/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface ReasonHeader
extends Header,
Parameters {
    public static final String NAME = "Reason";

    public int getCause();

    public String getProtocol();

    public String getText();

    public void setCause(int var1) throws InvalidArgumentException;

    public void setProtocol(String var1) throws ParseException;

    public void setText(String var1) throws ParseException;
}

