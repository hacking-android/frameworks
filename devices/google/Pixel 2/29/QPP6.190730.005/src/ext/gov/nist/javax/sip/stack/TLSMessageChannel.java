/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.parser.Pipeline;
import gov.nist.javax.sip.parser.PipelinedMsgParser;
import gov.nist.javax.sip.parser.SIPMessageListener;
import gov.nist.javax.sip.stack.HandshakeCompletedListenerImpl;
import gov.nist.javax.sip.stack.IOHandler;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.RawMessageChannel;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import gov.nist.javax.sip.stack.ServerResponseInterface;
import gov.nist.javax.sip.stack.TLSMessageProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.Timer;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.sip.address.Hop;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContentLengthHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;

public final class TLSMessageChannel
extends MessageChannel
implements SIPMessageListener,
Runnable,
RawMessageChannel {
    private HandshakeCompletedListener handshakeCompletedListener;
    protected boolean isCached;
    protected boolean isRunning;
    private String key;
    private String myAddress;
    private InputStream myClientInputStream;
    private PipelinedMsgParser myParser;
    private int myPort;
    private Socket mySock;
    private Thread mythread;
    private InetAddress peerAddress;
    private int peerPort;
    private String peerProtocol;
    private SIPTransactionStack sipStack;
    private TLSMessageProcessor tlsMessageProcessor;

    protected TLSMessageChannel(InetAddress inetAddress, int n, SIPTransactionStack sIPTransactionStack, TLSMessageProcessor tLSMessageProcessor) throws IOException {
        if (sIPTransactionStack.isLoggingEnabled()) {
            sIPTransactionStack.getStackLogger().logDebug("creating new TLSMessageChannel (outgoing)");
            sIPTransactionStack.getStackLogger().logStackTrace();
        }
        this.peerAddress = inetAddress;
        this.peerPort = n;
        this.myPort = tLSMessageProcessor.getPort();
        this.peerProtocol = "TLS";
        this.sipStack = sIPTransactionStack;
        this.tlsMessageProcessor = tLSMessageProcessor;
        this.myAddress = tLSMessageProcessor.getIpAddress().getHostAddress();
        this.key = MessageChannel.getKey(this.peerAddress, this.peerPort, "TLS");
        this.messageProcessor = tLSMessageProcessor;
    }

    protected TLSMessageChannel(Socket socket, SIPTransactionStack sIPTransactionStack, TLSMessageProcessor tLSMessageProcessor) throws IOException {
        if (sIPTransactionStack.isLoggingEnabled()) {
            sIPTransactionStack.getStackLogger().logDebug("creating new TLSMessageChannel (incoming)");
            sIPTransactionStack.getStackLogger().logStackTrace();
        }
        this.mySock = (SSLSocket)socket;
        if (socket instanceof SSLSocket) {
            socket = (SSLSocket)socket;
            ((SSLSocket)socket).setNeedClientAuth(true);
            this.handshakeCompletedListener = new HandshakeCompletedListenerImpl(this);
            ((SSLSocket)socket).addHandshakeCompletedListener(this.handshakeCompletedListener);
            ((SSLSocket)socket).startHandshake();
        }
        this.peerAddress = this.mySock.getInetAddress();
        this.myAddress = tLSMessageProcessor.getIpAddress().getHostAddress();
        this.myClientInputStream = this.mySock.getInputStream();
        this.mythread = new Thread(this);
        this.mythread.setDaemon(true);
        this.mythread.setName("TLSMessageChannelThread");
        this.sipStack = sIPTransactionStack;
        this.tlsMessageProcessor = tLSMessageProcessor;
        this.myPort = this.tlsMessageProcessor.getPort();
        this.peerPort = this.mySock.getPort();
        this.messageProcessor = tLSMessageProcessor;
        this.mythread.start();
    }

    private void sendMessage(byte[] object, boolean bl) throws IOException {
        Socket socket;
        object = this.sipStack.ioHandler.sendBytes(this.getMessageProcessor().getIpAddress(), this.peerAddress, this.peerPort, this.peerProtocol, (byte[])object, bl, this);
        if (object != (socket = this.mySock) && object != null) {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            this.mySock = object;
            this.myClientInputStream = this.mySock.getInputStream();
            object = new Thread(this);
            ((Thread)object).setDaemon(true);
            ((Thread)object).setName("TLSMessageChannelThread");
            ((Thread)object).start();
        }
    }

    @Override
    public void close() {
        block4 : {
            try {
                if (this.mySock != null) {
                    this.mySock.close();
                }
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Closing message Channel ");
                    stringBuilder.append(this);
                    stackLogger.logDebug(stringBuilder.toString());
                }
            }
            catch (IOException iOException) {
                if (!this.sipStack.isLoggingEnabled()) break block4;
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error closing socket ");
                stringBuilder.append(iOException);
                stackLogger.logDebug(stringBuilder.toString());
            }
        }
    }

    public boolean equals(Object object) {
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        object = (TLSMessageChannel)object;
        return this.mySock == ((TLSMessageChannel)object).mySock;
    }

    public HandshakeCompletedListenerImpl getHandshakeCompletedListener() {
        return (HandshakeCompletedListenerImpl)this.handshakeCompletedListener;
    }

    @Override
    public String getKey() {
        String string = this.key;
        if (string != null) {
            return string;
        }
        this.key = MessageChannel.getKey(this.peerAddress, this.peerPort, "TLS");
        return this.key;
    }

    @Override
    public String getPeerAddress() {
        InetAddress inetAddress = this.peerAddress;
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return this.getHost();
    }

    @Override
    protected InetAddress getPeerInetAddress() {
        return this.peerAddress;
    }

    @Override
    public InetAddress getPeerPacketSourceAddress() {
        return this.peerAddress;
    }

    @Override
    public int getPeerPacketSourcePort() {
        return this.peerPort;
    }

    @Override
    public int getPeerPort() {
        return this.peerPort;
    }

    @Override
    public String getPeerProtocol() {
        return this.peerProtocol;
    }

    @Override
    public SIPTransactionStack getSIPStack() {
        return this.sipStack;
    }

    @Override
    public String getTransport() {
        return "tls";
    }

    @Override
    public String getViaHost() {
        return this.myAddress;
    }

    @Override
    public int getViaPort() {
        return this.myPort;
    }

    @Override
    public void handleException(ParseException parseException, SIPMessage object, Class object2, String charSequence, String string) throws ParseException {
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logException(parseException);
        }
        if (object2 != null && (object2.equals(From.class) || object2.equals(To.class) || object2.equals(CSeq.class) || object2.equals(Via.class) || object2.equals(CallID.class) || object2.equals(RequestLine.class) || object2.equals(StatusLine.class))) {
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Encountered bad message \n");
                ((StringBuilder)charSequence).append(string);
                object2.logDebug(((StringBuilder)charSequence).toString());
            }
            if (!((String)(object = ((SIPMessage)object).toString())).startsWith("SIP/") && !((String)object).startsWith("ACK ")) {
                if ((object = this.createBadReqRes((String)object, parseException)) != null) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("Sending automatic 400 Bad Request:");
                        this.sipStack.getStackLogger().logDebug((String)object);
                    }
                    try {
                        this.sendMessage(((String)object).getBytes(), this.getPeerInetAddress(), this.getPeerPort(), false);
                    }
                    catch (IOException iOException) {
                        this.sipStack.getStackLogger().logException(iOException);
                    }
                } else if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("Could not formulate automatic 400 Bad Request");
                }
            }
            throw parseException;
        }
        ((SIPMessage)object).addUnparsed((String)charSequence);
    }

    @Override
    public boolean isReliable() {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void processMessage(SIPMessage object) throws Exception {
        if (((SIPMessage)object).getFrom() != null && ((SIPMessage)object).getTo() != null && ((SIPMessage)object).getCallId() != null && ((SIPMessage)object).getCSeq() != null && ((SIPMessage)object).getViaHeaders() != null) {
            Object object2;
            Object object3;
            block29 : {
                object3 = ((SIPMessage)object).getViaHeaders();
                if (object instanceof SIPRequest) {
                    object2 = (Via)((SIPHeaderList)object3).getFirst();
                    object3 = this.sipStack.addressResolver.resolveAddress(((Via)object2).getHop());
                    this.peerProtocol = ((Via)object2).getTransport();
                    try {
                        this.peerAddress = this.mySock.getInetAddress();
                        if (((ParametersHeader)object2).hasParameter("rport") || !object3.getHost().equals(this.peerAddress.getHostAddress())) {
                            ((ParametersHeader)object2).setParameter("received", this.peerAddress.getHostAddress());
                        }
                        ((ParametersHeader)object2).setParameter("rport", Integer.toString(this.peerPort));
                    }
                    catch (ParseException parseException) {
                        InternalErrorHandler.handleException(parseException);
                    }
                    if (!this.isCached) {
                        ((TLSMessageProcessor)this.messageProcessor).cacheMessageChannel(this);
                        this.isCached = true;
                        object3 = IOHandler.makeKey(this.mySock.getInetAddress(), this.peerPort);
                        this.sipStack.ioHandler.putSocket((String)object3, this.mySock);
                    }
                }
                long l = System.currentTimeMillis();
                boolean bl = object instanceof SIPRequest;
                int n = 0;
                if (bl) {
                    int n2;
                    object3 = (SIPRequest)object;
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("----Processing Message---");
                    }
                    if (this.sipStack.getStackLogger().isLoggingEnabled(16)) {
                        object2 = this.sipStack.serverLogger;
                        String string = this.getPeerHostPort().toString();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.messageProcessor.getIpAddress().getHostAddress());
                        stringBuilder.append(":");
                        stringBuilder.append(this.messageProcessor.getPort());
                        object2.logMessage((SIPMessage)object, string, stringBuilder.toString(), false, l);
                    }
                    if (this.sipStack.getMaxMessageSize() > 0 && (n2 = ((SIPMessage)object3).getSize()) + (n = ((SIPMessage)object3).getContentLength() == null ? 0 : ((SIPMessage)object3).getContentLength().getContentLength()) > this.sipStack.getMaxMessageSize()) {
                        this.sendMessage(((SIPRequest)object3).createResponse(513).encodeAsBytes(this.getTransport()), false);
                        throw new Exception("Message size exceeded");
                    }
                    object = this.sipStack.newSIPServerRequest((SIPRequest)object3, this);
                    if (object != null) {
                        try {
                            object.processRequest((SIPRequest)object3, this);
                            return;
                        }
                        finally {
                            if (object instanceof SIPTransaction && !((SIPServerTransaction)object).passToListener()) {
                                ((SIPTransaction)object).releaseSem();
                            }
                        }
                    }
                    object = ((SIPRequest)object3).createResponse(503);
                    object3 = new RetryAfter();
                    try {
                        ((RetryAfter)object3).setRetryAfter((int)(Math.random() * 10.0));
                        ((SIPMessage)object).setHeader((Header)object3);
                        this.sendMessage((SIPMessage)object);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (!this.sipStack.isLoggingEnabled()) return;
                    this.sipStack.getStackLogger().logWarning("Dropping message -- could not acquire semaphore");
                    return;
                }
                object = (SIPResponse)object;
                try {
                    ((SIPResponse)object).checkHeaders();
                    if (this.sipStack.getMaxMessageSize() <= 0) break block29;
                }
                catch (ParseException parseException) {
                    if (!this.sipStack.isLoggingEnabled()) return;
                    object2 = this.sipStack.getStackLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Dropping Badly formatted response message >>> ");
                    stringBuilder.append(object);
                    object2.logError(stringBuilder.toString());
                    return;
                }
                int n3 = ((SIPMessage)object).getSize();
                if (((SIPMessage)object).getContentLength() != null) {
                    n = ((SIPMessage)object).getContentLength().getContentLength();
                }
                if (n3 + n > this.sipStack.getMaxMessageSize()) {
                    if (!this.sipStack.isLoggingEnabled()) return;
                    this.sipStack.getStackLogger().logDebug("Message size exceeded");
                    return;
                }
            }
            if ((object3 = this.sipStack.newSIPServerResponse((SIPResponse)object, this)) != null) {
                try {
                    if (object3 instanceof SIPClientTransaction && !((SIPClientTransaction)object3).checkFromTag((SIPResponse)object)) {
                        if (!this.sipStack.isLoggingEnabled()) return;
                        object2 = this.sipStack.getStackLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Dropping response message with invalid tag >>> ");
                        stringBuilder.append(object);
                        object2.logError(stringBuilder.toString());
                        return;
                    }
                    object3.processResponse((SIPResponse)object, this);
                    return;
                }
                finally {
                    if (object3 instanceof SIPTransaction && !((SIPTransaction)object3).passToListener()) {
                        ((SIPTransaction)object3).releaseSem();
                    }
                }
            } else {
                this.sipStack.getStackLogger().logWarning("Could not get semaphore... dropping response");
            }
            return;
        }
        String string = ((SIPMessage)object).encode();
        if (!this.sipStack.isLoggingEnabled()) return;
        object = this.sipStack.getStackLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad message ");
        stringBuilder.append(string);
        object.logError(stringBuilder.toString());
        this.sipStack.getStackLogger().logError(">>> Dropped Bad Msg");
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
        block27 : {
            var1_1 = new Pipeline(this.myClientInputStream, this.sipStack.readTimeout, this.sipStack.getTimer());
            this.myParser = new PipelinedMsgParser(this, (Pipeline)var1_1, this.sipStack.getMaxMessageSize());
            this.myParser.processInput();
            var2_6 /* !! */  = this.tlsMessageProcessor;
            ++var2_6 /* !! */ .useCount;
            this.isRunning = true;
            do {
                try {
                    do {
                        block24 : {
                            var2_6 /* !! */  = new byte[4096];
                            var3_10 = this.myClientInputStream.read(var2_6 /* !! */ , 0, 4096);
                            if (var3_10 != -1) break block24;
                            var1_1.write("\r\n\r\n".getBytes("UTF-8"));
                            try {
                                if (this.sipStack.maxConnections == -1) ** GOTO lbl-1000
                                var2_6 /* !! */  = this.tlsMessageProcessor;
                                // MONITORENTER : var2_6 /* !! */ 
                                var4_11 = this.tlsMessageProcessor;
                                --var4_11.nConnections;
                                this.tlsMessageProcessor.notify();
                            }
                            catch (IOException var1_2) {
                                // empty catch block
                            }
                            // MONITOREXIT : var2_6 /* !! */ 
lbl-1000: // 2 sources:
                            {
                                var1_1.close();
                                this.mySock.close();
                            }
                            this.isRunning = false;
                            this.tlsMessageProcessor.remove(this);
                            var1_1 = this.tlsMessageProcessor;
                            --var1_1.useCount;
                            this.myParser.close();
                            return;
                        }
                        var1_1.write(var2_6 /* !! */ , 0, var3_10);
                        continue;
                        break;
                    } while (true);
                }
                catch (Throwable var1_3) {
                    ** GOTO lbl73
                }
                catch (Exception var2_7) {
                    InternalErrorHandler.handleException(var2_7);
                    continue;
                }
                break;
            } while (true);
            catch (IOException var2_8) {
                try {
                    var1_1.write("\r\n\r\n".getBytes("UTF-8"));
                }
                catch (Exception var4_12) {
                    // empty catch block
                }
                try {
                    block26 : {
                        if (this.sipStack.isLoggingEnabled()) {
                            var4_13 = this.sipStack.getStackLogger();
                            var5_14 = new StringBuilder();
                            var5_14.append("IOException  closing sock ");
                            var5_14.append(var2_8);
                            var4_13.logDebug(var5_14.toString());
                        }
                        try {
                            if (this.sipStack.maxConnections == -1) break block26;
                            var2_9 = this.tlsMessageProcessor;
                            // MONITORENTER : var2_9
                            var4_13 = this.tlsMessageProcessor;
                            --var4_13.nConnections;
                            this.tlsMessageProcessor.notify();
                        }
                        catch (IOException var1_4) {
                        }
                    }
                    this.mySock.close();
                    var1_1.close();
                    break block27;
                }
                catch (Exception var1_5) {
                    // empty catch block
                }
lbl73: // 1 sources:
                this.isRunning = false;
                this.tlsMessageProcessor.remove(this);
                var2_6 /* !! */  = this.tlsMessageProcessor;
                --var2_6 /* !! */ .useCount;
                this.myParser.close();
                throw var1_3;
            }
        }
        this.isRunning = false;
        this.tlsMessageProcessor.remove(this);
        var1_1 = this.tlsMessageProcessor;
        --var1_1.useCount;
        this.myParser.close();
    }

    @Override
    public void sendMessage(SIPMessage sIPMessage) throws IOException {
        byte[] arrby = sIPMessage.encodeAsBytes(this.getTransport());
        long l = System.currentTimeMillis();
        this.sendMessage(arrby, sIPMessage instanceof SIPRequest);
        if (this.sipStack.getStackLogger().isLoggingEnabled(16)) {
            this.logMessage(sIPMessage, this.peerAddress, this.peerPort, l);
        }
    }

    @Override
    public void sendMessage(byte[] object, InetAddress object2, int n, boolean bl) throws IOException {
        if (object != null && object2 != null) {
            object = this.sipStack.ioHandler.sendBytes(this.messageProcessor.getIpAddress(), (InetAddress)object2, n, "TLS", (byte[])object, bl, this);
            object2 = this.mySock;
            if (object != object2 && object != null) {
                if (object2 != null) {
                    try {
                        ((Socket)object2).close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                this.mySock = object;
                this.myClientInputStream = this.mySock.getInputStream();
                object = new Thread(this);
                ((Thread)object).setDaemon(true);
                ((Thread)object).setName("TLSMessageChannelThread");
                ((Thread)object).start();
            }
            return;
        }
        throw new IllegalArgumentException("Null argument");
    }

    public void setHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        this.handshakeCompletedListener = handshakeCompletedListener;
    }

    @Override
    protected void uncache() {
        if (this.isCached && !this.isRunning) {
            this.tlsMessageProcessor.remove(this);
        }
    }
}

