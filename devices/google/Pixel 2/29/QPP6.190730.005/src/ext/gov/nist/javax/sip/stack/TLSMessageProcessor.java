/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.StackLogger;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.TLSMessageChannel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import javax.net.ssl.SSLServerSocket;

public class TLSMessageProcessor
extends MessageProcessor {
    private ArrayList<TLSMessageChannel> incomingTlsMessageChannels;
    private boolean isRunning;
    protected int nConnections;
    private ServerSocket sock;
    private Hashtable<String, TLSMessageChannel> tlsMessageChannels;
    protected int useCount = 0;

    protected TLSMessageProcessor(InetAddress inetAddress, SIPTransactionStack sIPTransactionStack, int n) {
        super(inetAddress, n, "tls", sIPTransactionStack);
        this.sipStack = sIPTransactionStack;
        this.tlsMessageChannels = new Hashtable();
        this.incomingTlsMessageChannels = new ArrayList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void cacheMessageChannel(TLSMessageChannel tLSMessageChannel) {
        synchronized (this) {
            Object object;
            String string = tLSMessageChannel.getKey();
            Object object2 = this.tlsMessageChannels.get(string);
            if (object2 != null) {
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Closing ");
                    ((StringBuilder)object).append(string);
                    stackLogger.logDebug(((StringBuilder)object).toString());
                }
                ((TLSMessageChannel)object2).close();
            }
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Caching ");
                ((StringBuilder)object2).append(string);
                object.logDebug(((StringBuilder)object2).toString());
            }
            this.tlsMessageChannels.put(string, tLSMessageChannel);
            return;
        }
    }

    @Override
    public MessageChannel createMessageChannel(HostPort object) throws IOException {
        synchronized (this) {
            CharSequence charSequence;
            block5 : {
                charSequence = MessageChannel.getKey((HostPort)object, "TLS");
                if (this.tlsMessageChannels.get(charSequence) == null) break block5;
                object = this.tlsMessageChannels.get(charSequence);
                return object;
            }
            TLSMessageChannel tLSMessageChannel = new TLSMessageChannel(((HostPort)object).getInetAddress(), ((HostPort)object).getPort(), this.sipStack, this);
            this.tlsMessageChannels.put((String)charSequence, tLSMessageChannel);
            tLSMessageChannel.isCached = true;
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("key ");
                stringBuilder.append((String)charSequence);
                object.logDebug(stringBuilder.toString());
                object = this.sipStack.getStackLogger();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Creating ");
                ((StringBuilder)charSequence).append(tLSMessageChannel);
                object.logDebug(((StringBuilder)charSequence).toString());
            }
            return tLSMessageChannel;
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
                    CharSequence charSequence = MessageChannel.getKey((InetAddress)object, n, "TLS");
                    if (this.tlsMessageChannels.get(charSequence) != null) {
                        return this.tlsMessageChannels.get(charSequence);
                    }
                    TLSMessageChannel tLSMessageChannel = new TLSMessageChannel((InetAddress)object, n, this.sipStack, this);
                    this.tlsMessageChannels.put((String)charSequence, tLSMessageChannel);
                    tLSMessageChannel.isCached = true;
                    if (!this.sipStack.isLoggingEnabled()) return tLSMessageChannel;
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("key ");
                    ((StringBuilder)object).append((String)charSequence);
                    stackLogger.logDebug(((StringBuilder)object).toString());
                    object = this.sipStack.getStackLogger();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Creating ");
                    ((StringBuilder)charSequence).append(tLSMessageChannel);
                    object.logDebug(((StringBuilder)charSequence).toString());
                    return tLSMessageChannel;
                }
                catch (UnknownHostException unknownHostException) {
                    object = new IOException(unknownHostException.getMessage());
                    throw object;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public int getDefaultTargetPort() {
        return 5061;
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
    public boolean inUse() {
        boolean bl = this.useCount != 0;
        return bl;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    protected void remove(TLSMessageChannel tLSMessageChannel) {
        synchronized (this) {
            String string = tLSMessageChannel.getKey();
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Thread.currentThread());
                stringBuilder.append(" removing ");
                stringBuilder.append(string);
                stackLogger.logDebug(stringBuilder.toString());
            }
            if (this.tlsMessageChannels.get(string) == tLSMessageChannel) {
                this.tlsMessageChannels.remove(string);
            }
            this.incomingTlsMessageChannels.remove(tLSMessageChannel);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void start() throws IOException {
        Thread thread = new Thread(this);
        thread.setName("TLSMessageProcessorThread");
        thread.setPriority(10);
        thread.setDaemon(true);
        this.sock = this.sipStack.getNetworkLayer().createSSLServerSocket(this.getPort(), 0, this.getIpAddress());
        ((SSLServerSocket)this.sock).setNeedClientAuth(false);
        ((SSLServerSocket)this.sock).setUseClientMode(false);
        ((SSLServerSocket)this.sock).setWantClientAuth(true);
        String[] arrstring = ((SipStackImpl)this.sipStack).getEnabledCipherSuites();
        ((SSLServerSocket)this.sock).setEnabledCipherSuites(arrstring);
        ((SSLServerSocket)this.sock).setWantClientAuth(true);
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
            boolean bl = this.isRunning;
            if (!bl) {
                return;
            }
            this.isRunning = false;
            try {
                this.sock.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            Iterator<TLSMessageChannel> iterator = this.tlsMessageChannels.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().close();
            }
            iterator = this.incomingTlsMessageChannels.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.notify();
                    return;
                }
                iterator.next().close();
            } while (true);
        }
    }
}

