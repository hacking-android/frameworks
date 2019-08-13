/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.Protocol;
import gov.nist.javax.sip.header.ViaHeaderExt;
import gov.nist.javax.sip.stack.HopImpl;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.address.Hop;
import javax.sip.header.Parameters;
import javax.sip.header.ViaHeader;

public class Via
extends ParametersHeader
implements ViaHeader,
ViaHeaderExt {
    public static final String BRANCH = "branch";
    public static final String MADDR = "maddr";
    public static final String RECEIVED = "received";
    public static final String RPORT = "rport";
    public static final String TTL = "ttl";
    private static final long serialVersionUID = 5281728373401351378L;
    protected String comment;
    private boolean rPortFlag = false;
    protected HostPort sentBy;
    protected Protocol sentProtocol = new Protocol();

    public Via() {
        super("Via");
    }

    @Override
    public Object clone() {
        Via via = (Via)super.clone();
        GenericObject genericObject = this.sentProtocol;
        if (genericObject != null) {
            via.sentProtocol = (Protocol)genericObject.clone();
        }
        if ((genericObject = this.sentBy) != null) {
            via.sentBy = (HostPort)((HostPort)genericObject).clone();
        }
        if (this.getRPort() != -1) {
            via.setParameter(RPORT, this.getRPort());
        }
        return via;
    }

    @Override
    protected String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        this.sentProtocol.encode(stringBuffer);
        stringBuffer.append(" ");
        this.sentBy.encode(stringBuffer);
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        if (this.comment != null) {
            stringBuffer.append(" ");
            stringBuffer.append("(");
            stringBuffer.append(this.comment);
            stringBuffer.append(")");
        }
        if (this.rPortFlag) {
            stringBuffer.append(";rport");
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof ViaHeader) {
            object = (ViaHeader)object;
            if (!(this.getProtocol().equalsIgnoreCase(object.getProtocol()) && this.getTransport().equalsIgnoreCase(object.getTransport()) && this.getHost().equalsIgnoreCase(object.getHost()) && this.getPort() == object.getPort() && this.equalParameters((Parameters)object))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public String getBranch() {
        return this.getParameter(BRANCH);
    }

    public String getComment() {
        return this.comment;
    }

    public Hop getHop() {
        return new HopImpl(this.sentBy.getHost().getHostname(), this.sentBy.getPort(), this.sentProtocol.getTransport());
    }

    @Override
    public String getHost() {
        GenericObject genericObject = this.sentBy;
        if (genericObject == null) {
            return null;
        }
        if ((genericObject = ((HostPort)genericObject).getHost()) == null) {
            return null;
        }
        return ((Host)genericObject).getHostname();
    }

    @Override
    public String getMAddr() {
        return this.getParameter(MADDR);
    }

    @Override
    public int getPort() {
        HostPort hostPort = this.sentBy;
        if (hostPort == null) {
            return -1;
        }
        return hostPort.getPort();
    }

    @Override
    public String getProtocol() {
        Protocol protocol = this.sentProtocol;
        if (protocol == null) {
            return null;
        }
        return protocol.getProtocol();
    }

    public String getProtocolVersion() {
        Protocol protocol = this.sentProtocol;
        if (protocol == null) {
            return null;
        }
        return protocol.getProtocolVersion();
    }

    @Override
    public int getRPort() {
        String string = this.getParameter(RPORT);
        if (string != null && !string.equals("")) {
            return Integer.valueOf(string);
        }
        return -1;
    }

    @Override
    public String getReceived() {
        return this.getParameter(RECEIVED);
    }

    public HostPort getSentBy() {
        return this.sentBy;
    }

    @Override
    public String getSentByField() {
        HostPort hostPort = this.sentBy;
        if (hostPort != null) {
            return hostPort.encode();
        }
        return null;
    }

    public Protocol getSentProtocol() {
        return this.sentProtocol;
    }

    @Override
    public String getSentProtocolField() {
        Protocol protocol = this.sentProtocol;
        if (protocol != null) {
            return protocol.encode();
        }
        return null;
    }

    @Override
    public int getTTL() {
        return this.getParameterAsInt(TTL);
    }

    @Override
    public String getTransport() {
        Protocol protocol = this.sentProtocol;
        if (protocol == null) {
            return null;
        }
        return protocol.getTransport();
    }

    public NameValueList getViaParms() {
        return this.parameters;
    }

    public boolean hasComment() {
        boolean bl = this.comment != null;
        return bl;
    }

    public boolean hasPort() {
        return this.getSentBy().hasPort();
    }

    public void removeComment() {
        this.comment = null;
    }

    public void removePort() {
        this.sentBy.removePort();
    }

    @Override
    public void setBranch(String string) throws ParseException {
        if (string != null && string.length() != 0) {
            this.setParameter(BRANCH, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Via, setBranch(), the branch parameter is null or length 0.");
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setHost(Host host) {
        if (this.sentBy == null) {
            this.sentBy = new HostPort();
        }
        this.sentBy.setHost(host);
    }

    @Override
    public void setHost(String string) throws ParseException {
        if (this.sentBy == null) {
            this.sentBy = new HostPort();
        }
        try {
            Host host = new Host(string);
            this.sentBy.setHost(host);
            return;
        }
        catch (Exception exception) {
            throw new NullPointerException(" host parameter is null");
        }
    }

    @Override
    public void setMAddr(String string) throws ParseException {
        if (string != null) {
            Host host = new Host();
            host.setAddress(string);
            this.setParameter(new NameValue(MADDR, host));
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Via, setMAddr(), the mAddr parameter is null.");
    }

    @Override
    public void setPort(int n) throws InvalidArgumentException {
        if (n != -1 && (n < 1 || n > 65535)) {
            throw new InvalidArgumentException("Port value out of range -1, [1..65535]");
        }
        if (this.sentBy == null) {
            this.sentBy = new HostPort();
        }
        this.sentBy.setPort(n);
    }

    @Override
    public void setProtocol(String string) throws ParseException {
        if (string != null) {
            if (this.sentProtocol == null) {
                this.sentProtocol = new Protocol();
            }
            this.sentProtocol.setProtocol(string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Via, setProtocol(), the protocol parameter is null.");
    }

    public void setProtocolVersion(String string) {
        if (this.sentProtocol == null) {
            this.sentProtocol = new Protocol();
        }
        this.sentProtocol.setProtocolVersion(string);
    }

    @Override
    public void setRPort() {
        this.rPortFlag = true;
    }

    @Override
    public void setReceived(String string) throws ParseException {
        if (string != null) {
            this.setParameter(RECEIVED, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Via, setReceived(), the received parameter is null.");
    }

    public void setSentBy(HostPort hostPort) {
        this.sentBy = hostPort;
    }

    public void setSentProtocol(Protocol protocol) {
        this.sentProtocol = protocol;
    }

    @Override
    public void setTTL(int n) throws InvalidArgumentException {
        if (n < 0 && n != -1) {
            throw new InvalidArgumentException("JAIN-SIP Exception, Via, setTTL(), the ttl parameter is < 0");
        }
        this.setParameter(new NameValue(TTL, n));
    }

    @Override
    public void setTransport(String string) throws ParseException {
        if (string != null) {
            if (this.sentProtocol == null) {
                this.sentProtocol = new Protocol();
            }
            this.sentProtocol.setTransport(string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Via, setTransport(), the transport parameter is null.");
    }
}

