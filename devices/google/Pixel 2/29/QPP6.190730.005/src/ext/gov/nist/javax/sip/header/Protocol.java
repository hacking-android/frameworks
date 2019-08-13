/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPObject;
import java.text.ParseException;

public class Protocol
extends SIPObject {
    private static final long serialVersionUID = 2216758055974073280L;
    protected String protocolName = "SIP";
    protected String protocolVersion = "2.0";
    protected String transport = "UDP";

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.protocolName.toUpperCase());
        stringBuffer.append("/");
        stringBuffer.append(this.protocolVersion);
        stringBuffer.append("/");
        stringBuffer.append(this.transport.toUpperCase());
        return stringBuffer;
    }

    public String getProtocol() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.protocolName);
        stringBuilder.append('/');
        stringBuilder.append(this.protocolVersion);
        return stringBuilder.toString();
    }

    public String getProtocolName() {
        return this.protocolName;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getTransport() {
        return this.transport;
    }

    public void setProtocol(String string) throws ParseException {
        int n = string.indexOf(47);
        if (n > 0) {
            this.protocolName = string.substring(0, n);
            this.protocolVersion = string.substring(n + 1);
            return;
        }
        throw new ParseException("Missing '/' in protocol", 0);
    }

    public void setProtocolName(String string) {
        this.protocolName = string;
    }

    public void setProtocolVersion(String string) {
        this.protocolVersion = string;
    }

    public void setTransport(String string) {
        this.transport = string;
    }
}

