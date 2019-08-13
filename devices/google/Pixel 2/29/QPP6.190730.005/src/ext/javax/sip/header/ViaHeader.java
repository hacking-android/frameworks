/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface ViaHeader
extends Header,
Parameters {
    public static final String NAME = "Via";

    public String getBranch();

    public String getHost();

    public String getMAddr();

    public int getPort();

    public String getProtocol();

    public int getRPort();

    public String getReceived();

    public String getSentByField();

    public String getSentProtocolField();

    public int getTTL();

    public String getTransport();

    public void setBranch(String var1) throws ParseException;

    public void setHost(String var1) throws ParseException;

    public void setMAddr(String var1) throws ParseException;

    public void setPort(int var1) throws InvalidArgumentException;

    public void setProtocol(String var1) throws ParseException;

    public void setRPort() throws InvalidArgumentException;

    public void setReceived(String var1) throws ParseException;

    public void setTTL(int var1) throws InvalidArgumentException;

    public void setTransport(String var1) throws ParseException;
}

