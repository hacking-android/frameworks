/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.io.IOException;
import java.text.ParseException;
import javax.sip.header.ContactHeader;

public interface ListeningPoint
extends Cloneable {
    public static final int PORT_5060 = 5060;
    public static final int PORT_5061 = 5061;
    public static final String SCTP = "SCTP";
    public static final String TCP = "TCP";
    public static final String TLS = "TLS";
    public static final String UDP = "UDP";

    public ContactHeader createContactHeader();

    public String getIPAddress();

    public int getPort();

    public String getSentBy();

    public String getTransport();

    public void sendHeartbeat(String var1, int var2) throws IOException;

    public void setSentBy(String var1) throws ParseException;
}

