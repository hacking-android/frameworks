/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.text.ParseException;
import java.util.Iterator;
import javax.sip.InvalidArgumentException;
import javax.sip.address.URI;
import javax.sip.header.Parameters;

public interface SipURI
extends URI,
Parameters {
    public String getHeader(String var1);

    public Iterator getHeaderNames();

    public String getHost();

    public String getLrParam();

    public String getMAddrParam();

    public String getMethodParam();

    public int getPort();

    public int getTTLParam();

    public String getTransportParam();

    public String getUser();

    public String getUserAtHost();

    public String getUserAtHostPort();

    public String getUserParam();

    public String getUserPassword();

    public String getUserType();

    public boolean hasLrParam();

    public boolean hasTransport();

    public boolean isSecure();

    public void removeUserType();

    public void setHeader(String var1, String var2);

    public void setHost(String var1) throws ParseException;

    public void setLrParam();

    public void setMAddrParam(String var1) throws ParseException;

    public void setMethodParam(String var1) throws ParseException;

    public void setPort(int var1) throws InvalidArgumentException;

    public void setSecure(boolean var1);

    public void setTTLParam(int var1);

    public void setTransportParam(String var1) throws ParseException;

    public void setUser(String var1);

    public void setUserParam(String var1);

    public void setUserPassword(String var1);
}

