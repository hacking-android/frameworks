/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface AuthenticationInfoHeader
extends Header,
Parameters {
    public static final String NAME = "Authentication-Info";

    public String getCNonce();

    public String getNextNonce();

    public int getNonceCount();

    public String getQop();

    public String getResponse();

    public void setCNonce(String var1) throws ParseException;

    public void setNextNonce(String var1) throws ParseException;

    public void setNonceCount(int var1) throws ParseException;

    public void setQop(String var1) throws ParseException;

    public void setResponse(String var1) throws ParseException;
}

