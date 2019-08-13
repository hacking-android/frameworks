/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.TCPMessageChannel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class TCPMessageProcessor
extends MessageProcessor {
    private ArrayList<TCPMessageChannel> incomingTcpMessageChannels;
    private boolean isRunning;
    protected int nConnections;
    private ServerSocket sock;
    private Hashtable tcpMessageChannels;
    protected int useCount;

    protected TCPMessageProcessor(InetAddress inetAddress, SIPTransactionStack sIPTransactionStack, int n) {
        super(inetAddress, n, "tcp", sIPTransactionStack);
        this.sipStack = sIPTransactionStack;
        this.tcpMessageChannels = new Hashtable();
        this.incomingTcpMessageChannels = new ArrayList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void cacheMessageChannel(TCPMessageChannel tCPMessageChannel) {
        synchronized (this) {
            StackLogger stackLogger;
            String string = tCPMessageChannel.getKey();
            Object object = (TCPMessageChannel)this.tcpMessageChannels.get(string);
            if (object != null) {
                if (this.sipStack.isLoggingEnabled()) {
                    stackLogger = this.sipStack.getStackLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Closing ");
                    stringBuilder.append(string);
                    stackLogger.logDebug(stringBuilder.toString());
                }
                ((TCPMessageChannel)object).close();
            }
            if (this.sipStack.isLoggingEnabled()) {
                stackLogger = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("Caching ");
                ((StringBuilder)object).append(string);
                stackLogger.logDebug(((StringBuilder)object).toString());
            }
            this.tcpMessageChannels.put(string, tCPMessageChannel);
            return;
        }
    }

    @Override
    public MessageChannel createMessageChannel(HostPort object) throws IOException {
        synchronized (this) {
            CharSequence charSequence;
            block5 : {
                charSequence = MessageChannel.getKey((HostPort)object, "TCP");
                if (this.tcpMessageChannels.get(charSequence) == null) break block5;
                object = (TCPMessageChannel)this.tcpMessageChannels.get(charSequence);
                return object;
            }
            TCPMessageChannel tCPMessageChannel = new TCPMessageChannel(((HostPort)object).getInetAddress(), ((HostPort)object).getPort(), this.sipStack, this);
            this.tcpMessageChannels.put(charSequence, tCPMessageChannel);
            tCPMessageChannel.isCached = true;
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("key ");
                ((StringBuilder)object).append((String)charSequence);
                stackLogger.logDebug(((StringBuilder)object).toString());
                object = this.sipStack.getStackLogger();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Creating ");
                ((StringBuilder)charSequence).append(tCPMessageChannel);
                object.logDebug(((StringBuilder)charSequence).toString());
            }
            return tCPMessageChannel;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public MessageChannel createMessageChannel(InetAddress object, int n) throws IOException {
        synchronized (this) {
            try {
                try {
                    CharSequence charSequence = MessageChannel.getKey((InetAddress)object, n, "TCP");
                    if (this.tcpMessageChannels.get(charSequence) != null) {
                        return (TCPMessageChannel)this.tcpMessageChannels.get(charSequence);
                    }
                    TCPMessageChannel tCPMessageChannel = new TCPMessageChannel((InetAddress)object, n, this.sipStack, this);
                    this.tcpMessageChannels.put(charSequence, tCPMessageChannel);
                    tCPMessageChannel.isCached = true;
                    if (!this.sipStack.isLoggingEnabled()) return tCPMessageChannel;
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("key ");
                    ((StringBuilder)object).append((String)charSequence);
                    stackLogger.logDebug(((StringBuilder)object).toString());
                    object = this.sipStack.getStackLogger();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Creating ");
                    ((StringBuilder)charSequence).append(tCPMessageChannel);
                    object.logDebug(((StringBuilder)charSequence).toString());
                    return tCPMessageChannel;
                }
                catch (UnknownHostException unknownHostException) {
                    IOException iOException = new IOException(unknownHostException.getMessage());
                    throw iOException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public int getDefaultTargetPort() {
        return 5060;
    }

    @Override
    public int getMaximumMessageSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public SIPTransactionStack getSIPStack() {
        return this.sipStack;
    }

    @Override
    public String getTransport() {
        return "tcp";
    }

    @Override
    public boolean inUse() {
        boolean bl = this.useCount != 0;
        return bl;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    protected void remove(TCPMessageChannel tCPMessageChannel) {
        synchronized (this) {
            String string = tCPMessageChannel.getKey();
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Thread.currentThread());
                stringBuilder.append(" removing ");
                stringBuilder.append(string);
                stackLogger.logDebug(stringBuilder.toString());
            }
            if (this.tcpMessageChannels.get(string) == tCPMessageChannel) {
                this.tcpMessageChannels.remove(string);
            }
            this.incomingTcpMessageChannels.remove(tCPMessageChannel);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        while (this.isRunning) {
            int n;
            int n2;
            while (this.sipStack.maxConnections != -1 && (n2 = this.nConnections) >= (n = this.sipStack.maxConnections)) {
                try {
                    this.wait();
                    boolean bl = this.isRunning;
                    if (bl) continue;
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break;
                }
                // MONITOREXIT : this
                return;
            }
            ++this.nConnections;
            // MONITOREXIT : this
            try {
                Socket socket = this.sock.accept();
                if (this.sipStack.isLoggingEnabled()) {
                    this.getSIPStack().getStackLogger().logDebug("Accepting new connection!");
                }
                ArrayList<TCPMessageChannel> arrayList = this.incomingTcpMessageChannels;
                TCPMessageChannel tCPMessageChannel = new TCPMessageChannel(socket, this.sipStack, this);
                arrayList.add(tCPMessageChannel);
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
            }
            catch (IOException iOException) {
                if (!this.sipStack.isLoggingEnabled()) continue;
                this.getSIPStack().getStackLogger().logException(iOException);
            }
            catch (SocketException socketException) {
                this.isRunning = false;
            }
        }
    }

    @Override
    public void start() throws IOException {
        Thread thread = new Thread(this);
        thread.setName("TCPMessageProcessorThread");
        thread.setPriority(10);
        thread.setDaemon(true);
        this.sock = this.sipStack.getNetworkLayer().createServerSocket(this.getPort(), 0, this.getIpAddress());
        if (this.getIpAddress().getHostAddress().equals("0.0.0.0") || this.getIpAddress().getHostAddress().equals("::0")) {
            super.setIpAddress(this.sock.getInetAddress());
        }
        this.isRunning = true;
        thread.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void stop() {
        synchronized (this) {
            this.isRunning = false;
            try {
                this.sock.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            Iterator<Object> iterator = this.tcpMessageChannels.values().iterator();
            while (iterator.hasNext()) {
                ((TCPMessageChannel)iterator.next()).close();
            }
            iterator = this.incomingTcpMessageChannels.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.notify();
                    return;
                }
                ((TCPMessageChannel)iterator.next()).close();
            } while (true);
        }
    }
}

