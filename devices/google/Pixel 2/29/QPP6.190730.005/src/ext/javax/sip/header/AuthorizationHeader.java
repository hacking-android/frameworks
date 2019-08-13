/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface AuthorizationHeader
extends Header,
Parameters {
    public static final String NAME = "Authorization";

    public String getAlgorithm();

    public String getCNonce();

    public String getNonce();

    public int getNonceCount();

    public String getOpaque();

    public String getQop();

    public String getRealm();

    public String getResponse();

    public String getScheme();

    public URI getURI();

    public String getUsername();

    public boolean isStale();

    public void setAlgorithm(String var1) throws ParseException;

    public void setCNonce(String var1) throws ParseException;

    public void setNonce(String var1) throws ParseException;

    public void setNonceCount(int var1) throws ParseException;

    public void setOpaque(String var1) throws ParseException;

    public void setQop(String var1) throws ParseException;

    public void setRealm(String var1) throws ParseException;

    public void setResponse(String var1) throws ParseException;

    public void setScheme(String var1);

    public void setStale(boolean var1);

    public void setURI(URI var1);

    public void setUsername(String var1) throws ParseException;
}

