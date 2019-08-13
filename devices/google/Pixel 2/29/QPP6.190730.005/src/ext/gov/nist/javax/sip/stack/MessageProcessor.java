/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public abstract class MessageProcessor
implements Runnable {
    protected static final String IN6_ADDR_ANY = "::0";
    protected static final String IN_ADDR_ANY = "0.0.0.0";
    private InetAddress ipAddress;
    private ListeningPointImpl listeningPoint;
    private int port;
    private String savedIpAddress;
    private String sentBy;
    private HostPort sentByHostPort;
    private boolean sentBySet;
    protected SIPTransactionStack sipStack;
    protected String transport;

    protected MessageProcessor(String string) {
        this.transport = string;
    }

    protected MessageProcessor(InetAddress inetAddress, int n, String string, SIPTransactionStack sIPTransactionStack) {
        this(string);
        this.initialize(inetAddress, n, sIPTransactionStack);
    }

    public static int getDefaultPort(String string) {
        int n = string.equalsIgnoreCase("TLS") ? 5061 : 5060;
        return n;
    }

    public abstract MessageChannel createMessageChannel(HostPort var1) throws IOException;

    public abstract MessageChannel createMessageChannel(InetAddress var1, int var2) throws IOException;

    public abstract int getDefaultTargetPort();

    public InetAddress getIpAddress() {
        return this.ipAddress;
    }

    public ListeningPointImpl getListeningPoint() {
        if (this.listeningPoint == null && this.getSIPStack().isLoggingEnabled()) {
            StackLogger stackLogger = this.getSIPStack().getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getListeningPoint");
            stringBuilder.append(this);
            stringBuilder.append(" returning null listeningpoint");
            stackLogger.logError(stringBuilder.toString());
        }
        return this.listeningPoint;
    }

    public abstract int getMaximumMessageSize();

    public int getPort() {
        return this.port;
    }

    public abstract SIPTransactionStack getSIPStack();

    public String getSavedIpAddress() {
        return this.savedIpAddress;
    }

    public String getSentBy() {
        HostPort hostPort;
        if (this.sentBy == null && (hostPort = this.sentByHostPort) != null) {
            this.sentBy = hostPort.toString();
        }
        return this.sentBy;
    }

    public String getTransport() {
        return this.transport;
    }

    public Via getViaHeader() {
        try {
            Via via = new Via();
            if (this.sentByHostPort != null) {
                via.setSentBy(this.sentByHostPort);
                via.setTransport(this.getTransport());
            } else {
                Host host = new Host();
                host.setHostname(this.getIpAddress().getHostAddress());
                via.setHost(host);
                via.setPort(this.getPort());
                via.setTransport(this.getTransport());
            }
            return via;
        }
        catch (InvalidArgumentException invalidArgumentException) {
            invalidArgumentException.printStackTrace();
            return null;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
            return null;
        }
    }

    public abstract boolean inUse();

    public final void initialize(InetAddress inetAddress, int n, SIPTransactionStack sIPTransactionStack) {
        this.sipStack = sIPTransactionStack;
        this.savedIpAddress = inetAddress.getHostAddress();
        this.ipAddress = inetAddress;
        this.port = n;
        this.sentByHostPort = new HostPort();
        this.sentByHostPort.setHost(new Host(inetAddress.getHostAddress()));
        this.sentByHostPort.setPort(n);
    }

    public abstract boolean isSecure();

    public boolean isSentBySet() {
        return this.sentBySet;
    }

    @Override
    public abstract void run();

    protected void setIpAddress(InetAddress inetAddress) {
        this.sentByHostPort.setHost(new Host(inetAddress.getHostAddress()));
        this.ipAddress = inetAddress;
    }

    public void setListeningPoint(ListeningPointImpl listeningPointImpl) {
        if (this.getSIPStack().isLoggingEnabled()) {
            StackLogger stackLogger = this.getSIPStack().getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setListeningPoint");
            stringBuilder.append(this);
            stringBuilder.append(" listeningPoint = ");
            stringBuilder.append(listeningPointImpl);
            stackLogger.logDebug(stringBuilder.toString());
        }
        if (listeningPointImpl.getPort() != this.getPort()) {
            InternalErrorHandler.handleException("lp mismatch with provider", this.getSIPStack().getStackLogger());
        }
        this.listeningPoint = listeningPointImpl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setSentBy(String string) throws ParseException {
        int n = string.indexOf(":");
        if (n == -1) {
            this.sentByHostPort = new HostPort();
            this.sentByHostPort.setHost(new Host(string));
        } else {
            this.sentByHostPort = new HostPort();
            this.sentByHostPort.setHost(new Host(string.substring(0, n)));
            String string2 = string.substring(n + 1);
            int n2 = Integer.parseInt(string2);
            this.sentByHostPort.setPort(n2);
        }
        this.sentBySet = true;
        this.sentBy = string;
        return;
        catch (NumberFormatException numberFormatException) {
            throw new ParseException("Bad format encountered at ", n);
        }
    }

    public abstract void start() throws IOException;

    public abstract void stop();
}

