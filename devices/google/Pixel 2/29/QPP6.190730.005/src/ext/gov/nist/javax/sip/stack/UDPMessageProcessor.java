/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.core.ThreadAuditor;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.UDPMessageChannel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class UDPMessageProcessor
extends MessageProcessor {
    private static final int HIGHWAT = 5000;
    private static final int LOWAT = 2500;
    protected boolean isRunning;
    protected LinkedList messageChannels;
    protected LinkedList messageQueue;
    private int port;
    protected DatagramSocket sock;
    protected int threadPoolSize;

    protected UDPMessageProcessor(InetAddress inetAddress, SIPTransactionStack sIPTransactionStack, int n) throws IOException {
        super(inetAddress, n, "udp", sIPTransactionStack);
        this.sipStack = sIPTransactionStack;
        this.messageQueue = new LinkedList();
        this.port = n;
        try {
            this.sock = sIPTransactionStack.getNetworkLayer().createDatagramSocket(n, inetAddress);
            this.sock.setReceiveBufferSize(sIPTransactionStack.getReceiveUdpBufferSize());
            this.sock.setSendBufferSize(sIPTransactionStack.getSendUdpBufferSize());
            if (sIPTransactionStack.getThreadAuditor().isEnabled()) {
                this.sock.setSoTimeout((int)sIPTransactionStack.getThreadAuditor().getPingIntervalInMillisecs());
            }
            if (inetAddress.getHostAddress().equals("0.0.0.0") || inetAddress.getHostAddress().equals("::0")) {
                super.setIpAddress(this.sock.getLocalAddress());
            }
            return;
        }
        catch (SocketException socketException) {
            throw new IOException(socketException.getMessage());
        }
    }

    @Override
    public MessageChannel createMessageChannel(HostPort hostPort) throws UnknownHostException {
        return new UDPMessageChannel(hostPort.getInetAddress(), hostPort.getPort(), this.sipStack, this);
    }

    @Override
    public MessageChannel createMessageChannel(InetAddress inetAddress, int n) throws IOException {
        return new UDPMessageChannel(inetAddress, n, this.sipStack, this);
    }

    @Override
    public int getDefaultTargetPort() {
        return 5060;
    }

    @Override
    public int getMaximumMessageSize() {
        return 8192;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public SIPTransactionStack getSIPStack() {
        return this.sipStack;
    }

    @Override
    public String getTransport() {
        return "udp";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean inUse() {
        LinkedList linkedList = this.messageQueue;
        synchronized (linkedList) {
            if (this.messageQueue.size() == 0) return false;
            return true;
        }
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.messageChannels = new LinkedList<E>();
        if (this.sipStack.threadPoolSize != -1) {
            for (var1_1 = 0; var1_1 < this.sipStack.threadPoolSize; ++var1_1) {
                var2_2 = new UDPMessageChannel(this.sipStack, this);
                this.messageChannels.add(var2_2);
            }
        }
        var2_2 = this.sipStack.getThreadAuditor().addCurrentThread();
        while (this.isRunning != false) {
            try {
                var2_2.ping();
                var1_1 = this.sock.getReceiveBufferSize();
                var3_4 = new byte[var1_1];
                var4_5 = new DatagramPacket((byte[])var3_4, var1_1);
                this.sock.receive((DatagramPacket)var4_5);
                if (this.sipStack.stackDoesCongestionControl) {
                    if (this.messageQueue.size() >= 5000) {
                        if (!this.sipStack.isLoggingEnabled()) continue;
                        this.sipStack.getStackLogger().logDebug("Dropping message -- queue length exceeded");
                        continue;
                    }
                    if (this.messageQueue.size() > 2500 && this.messageQueue.size() < 5000) {
                        var5_9 = (float)(this.messageQueue.size() - 2500) / 2500.0f;
                        var1_1 = Math.random() > 1.0 - (double)var5_9 ? 1 : 0;
                        if (var1_1 != 0) {
                            if (!this.sipStack.isLoggingEnabled()) continue;
                            var3_4 = this.sipStack.getStackLogger();
                            var4_5 = new StringBuilder();
                            var4_5.append("Dropping message with probability  ");
                            var4_5.append(1.0 - (double)var5_9);
                            var3_4.logDebug(var4_5.toString());
                            continue;
                        }
                    }
                }
                if (this.sipStack.threadPoolSize == -1) ** GOTO lbl-1000
                var3_4 = this.messageQueue;
                // MONITORENTER : var3_4
                this.messageQueue.add(var4_5);
            }
            catch (Exception var2_3) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.getSIPStack().getStackLogger().logDebug("UDPMessageProcessor: Unexpected Exception - quitting");
                }
                InternalErrorHandler.handleException(var2_3);
                return;
            }
            catch (IOException var4_6) {
                this.isRunning = false;
                var4_6.printStackTrace();
                if (!this.sipStack.isLoggingEnabled()) continue;
                this.getSIPStack().getStackLogger().logDebug("UDPMessageProcessor: Got an IO Exception");
                continue;
            }
            catch (SocketException var4_7) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.getSIPStack().getStackLogger().logDebug("UDPMessageProcessor: Stopping");
                }
                this.isRunning = false;
                var4_5 = this.messageQueue;
                // MONITORENTER : var4_5
                this.messageQueue.notifyAll();
                // MONITOREXIT : var4_5
                continue;
            }
            catch (SocketTimeoutException var4_8) {
                continue;
            }
            this.messageQueue.notify();
            // MONITOREXIT : var3_4
            continue;
lbl-1000: // 1 sources:
            {
                new UDPMessageChannel(this.sipStack, this, (DatagramPacket)var4_5);
            }
        }
    }

    @Override
    public void start() throws IOException {
        this.isRunning = true;
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.setName("UDPMessageProcessorThread");
        thread.setPriority(10);
        thread.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void stop() {
        LinkedList linkedList = this.messageQueue;
        synchronized (linkedList) {
            this.isRunning = false;
            this.messageQueue.notifyAll();
            this.sock.close();
            return;
        }
    }
}

