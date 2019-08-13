/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.core.ThreadAuditor;
import gov.nist.core.net.AddressResolver;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.parser.ParseExceptionListener;
import gov.nist.javax.sip.parser.StringMsgParser;
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
import gov.nist.javax.sip.stack.UDPMessageProcessor;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.sip.address.Hop;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;

public class UDPMessageChannel
extends MessageChannel
implements ParseExceptionListener,
Runnable,
RawMessageChannel {
    private DatagramPacket incomingPacket;
    private String myAddress;
    protected StringMsgParser myParser;
    protected int myPort;
    private InetAddress peerAddress;
    private InetAddress peerPacketSourceAddress;
    private int peerPacketSourcePort;
    private int peerPort;
    private String peerProtocol;
    private Hashtable<String, PingBackTimerTask> pingBackRecord = new Hashtable();
    private long receptionTime;
    protected SIPTransactionStack sipStack;

    protected UDPMessageChannel(SIPTransactionStack object, UDPMessageProcessor uDPMessageProcessor) {
        this.messageProcessor = uDPMessageProcessor;
        this.sipStack = object;
        object = new Thread(this);
        this.myAddress = uDPMessageProcessor.getIpAddress().getHostAddress();
        this.myPort = uDPMessageProcessor.getPort();
        ((Thread)object).setName("UDPMessageChannelThread");
        ((Thread)object).setDaemon(true);
        ((Thread)object).start();
    }

    protected UDPMessageChannel(SIPTransactionStack object, UDPMessageProcessor uDPMessageProcessor, DatagramPacket datagramPacket) {
        this.incomingPacket = datagramPacket;
        this.messageProcessor = uDPMessageProcessor;
        this.sipStack = object;
        this.myAddress = uDPMessageProcessor.getIpAddress().getHostAddress();
        this.myPort = uDPMessageProcessor.getPort();
        object = new Thread(this);
        ((Thread)object).setDaemon(true);
        ((Thread)object).setName("UDPMessageChannelThread");
        ((Thread)object).start();
    }

    protected UDPMessageChannel(InetAddress inetAddress, int n, SIPTransactionStack object, UDPMessageProcessor object2) {
        this.peerAddress = inetAddress;
        this.peerPort = n;
        this.peerProtocol = "UDP";
        this.messageProcessor = object2;
        this.myAddress = ((MessageProcessor)object2).getIpAddress().getHostAddress();
        this.myPort = ((UDPMessageProcessor)object2).getPort();
        this.sipStack = object;
        if (((SIPTransactionStack)object).isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Creating message channel ");
            ((StringBuilder)object).append(inetAddress.getHostAddress());
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(n);
            object2.logDebug(((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processIncomingDataPacket(DatagramPacket object) throws Exception {
        Object object2;
        Object object3;
        Object object4;
        block16 : {
            this.peerAddress = ((DatagramPacket)object).getAddress();
            int n = ((DatagramPacket)object).getLength();
            object3 = ((DatagramPacket)object).getData();
            object4 = new byte[n];
            System.arraycopy(object3, 0, object4, 0, n);
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("UDPMessageChannel: processIncomingDataPacket : peerAddress = ");
                ((StringBuilder)object3).append(this.peerAddress.getHostAddress());
                ((StringBuilder)object3).append("/");
                ((StringBuilder)object3).append(((DatagramPacket)object).getPort());
                ((StringBuilder)object3).append(" Length = ");
                ((StringBuilder)object3).append(n);
                object2.logDebug(((StringBuilder)object3).toString());
            }
            try {
                this.receptionTime = System.currentTimeMillis();
                object3 = this.myParser.parseSIPMessage((byte[])object4);
                this.myParser = null;
                if (object3 != null) break block16;
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("Rejecting message !  + Null message parsed.");
                }
                object3 = this.pingBackRecord;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(((DatagramPacket)object).getAddress().getHostAddress());
            }
            catch (ParseException parseException) {
                this.myParser = null;
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Rejecting message !  ");
                    ((StringBuilder)object2).append(new String((byte[])object4));
                    stackLogger.logDebug(((StringBuilder)object2).toString());
                    stackLogger = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("error message ");
                    ((StringBuilder)object2).append(parseException.getMessage());
                    stackLogger.logDebug(((StringBuilder)object2).toString());
                    this.sipStack.getStackLogger().logException(parseException);
                }
                if (((String)(object4 = new String((byte[])object4, 0, n))).startsWith("SIP/")) return;
                if (((String)object4).startsWith("ACK ")) return;
                String string = this.createBadReqRes((String)object4, parseException);
                if (string == null) {
                    if (!this.sipStack.isLoggingEnabled()) return;
                    this.sipStack.getStackLogger().logDebug("Could not formulate automatic 400 Bad Request");
                    return;
                }
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("Sending automatic 400 Bad Request:");
                    this.sipStack.getStackLogger().logDebug(string);
                }
                try {
                    this.sendMessage(string.getBytes(), this.peerAddress, ((DatagramPacket)object).getPort(), "UDP", false);
                    return;
                }
                catch (IOException iOException) {
                    this.sipStack.getStackLogger().logException(iOException);
                    return;
                }
            }
            ((StringBuilder)object4).append(":");
            ((StringBuilder)object4).append(((DatagramPacket)object).getPort());
            if (((Hashtable)object3).get(((StringBuilder)object4).toString()) != null) return;
            object3 = "\r\n\r\n".getBytes();
            object3 = new DatagramPacket((byte[])object3, 0, ((Object)object3).length, ((DatagramPacket)object).getAddress(), ((DatagramPacket)object).getPort());
            ((UDPMessageProcessor)this.messageProcessor).sock.send((DatagramPacket)object3);
            this.sipStack.getTimer().schedule((TimerTask)new PingBackTimerTask(((DatagramPacket)object).getAddress().getHostAddress(), ((DatagramPacket)object).getPort()), 1000L);
            return;
        }
        object2 = ((SIPMessage)object3).getViaHeaders();
        if (((SIPMessage)object3).getFrom() != null && ((SIPMessage)object3).getTo() != null && ((SIPMessage)object3).getCallId() != null && ((SIPMessage)object3).getCSeq() != null && ((SIPMessage)object3).getViaHeaders() != null) {
            if (object3 instanceof SIPRequest) {
                object2 = (Via)((SIPHeaderList)object2).getFirst();
                object4 = this.sipStack.addressResolver.resolveAddress(((Via)object2).getHop());
                this.peerPort = object4.getPort();
                this.peerProtocol = ((Via)object2).getTransport();
                this.peerPacketSourceAddress = ((DatagramPacket)object).getAddress();
                this.peerPacketSourcePort = ((DatagramPacket)object).getPort();
                try {
                    this.peerAddress = ((DatagramPacket)object).getAddress();
                    boolean bl = ((ParametersHeader)object2).hasParameter("rport");
                    if (bl || !object4.getHost().equals(this.peerAddress.getHostAddress())) {
                        ((ParametersHeader)object2).setParameter("received", this.peerAddress.getHostAddress());
                    }
                    if (bl) {
                        ((ParametersHeader)object2).setParameter("rport", Integer.toString(this.peerPacketSourcePort));
                    }
                }
                catch (ParseException parseException) {
                    InternalErrorHandler.handleException(parseException);
                }
            } else {
                this.peerPacketSourceAddress = ((DatagramPacket)object).getAddress();
                this.peerPacketSourcePort = ((DatagramPacket)object).getPort();
                this.peerAddress = ((DatagramPacket)object).getAddress();
                this.peerPort = ((DatagramPacket)object).getPort();
                this.peerProtocol = ((Via)((SIPHeaderList)object2).getFirst()).getTransport();
            }
            this.processMessage((SIPMessage)object3);
            return;
        }
        object2 = new String((byte[])object4);
        if (!this.sipStack.isLoggingEnabled()) return;
        object = this.sipStack.getStackLogger();
        object4 = new StringBuilder();
        ((StringBuilder)object4).append("bad message ");
        ((StringBuilder)object4).append((String)object2);
        object.logError(((StringBuilder)object4).toString());
        object4 = this.sipStack.getStackLogger();
        object = new StringBuilder();
        ((StringBuilder)object).append(">>> Dropped Bad Msg From = ");
        ((StringBuilder)object).append(((SIPMessage)object3).getFrom());
        ((StringBuilder)object).append("To = ");
        ((StringBuilder)object).append(((SIPMessage)object3).getTo());
        ((StringBuilder)object).append("CallId = ");
        ((StringBuilder)object).append(((SIPMessage)object3).getCallId());
        ((StringBuilder)object).append("CSeq = ");
        ((StringBuilder)object).append(((SIPMessage)object3).getCSeq());
        ((StringBuilder)object).append("Via = ");
        ((StringBuilder)object).append(((SIPMessage)object3).getViaHeaders());
        object4.logError(((StringBuilder)object).toString());
    }

    @Override
    public void close() {
    }

    public boolean equals(Object object) {
        boolean bl;
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            bl = false;
        } else {
            object = (UDPMessageChannel)object;
            bl = this.getKey().equals(((UDPMessageChannel)object).getKey());
        }
        return bl;
    }

    @Override
    public String getHost() {
        return this.messageProcessor.getIpAddress().getHostAddress();
    }

    @Override
    public String getKey() {
        return UDPMessageChannel.getKey(this.peerAddress, this.peerPort, "UDP");
    }

    @Override
    public String getPeerAddress() {
        return this.peerAddress.getHostAddress();
    }

    @Override
    protected InetAddress getPeerInetAddress() {
        return this.peerAddress;
    }

    public String getPeerName() {
        return this.peerAddress.getHostName();
    }

    @Override
    public InetAddress getPeerPacketSourceAddress() {
        return this.peerPacketSourceAddress;
    }

    @Override
    public int getPeerPacketSourcePort() {
        return this.peerPacketSourcePort;
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
    public int getPort() {
        return ((UDPMessageProcessor)this.messageProcessor).getPort();
    }

    @Override
    public SIPTransactionStack getSIPStack() {
        return this.sipStack;
    }

    @Override
    public String getTransport() {
        return "udp";
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
    public void handleException(ParseException parseException, SIPMessage sIPMessage, Class class_, String string, String string2) throws ParseException {
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logException(parseException);
        }
        if (class_ != null && (class_.equals(From.class) || class_.equals(To.class) || class_.equals(CSeq.class) || class_.equals(Via.class) || class_.equals(CallID.class) || class_.equals(RequestLine.class) || class_.equals(StatusLine.class))) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("BAD MESSAGE!");
                this.sipStack.getStackLogger().logError(string2);
            }
            throw parseException;
        }
        sIPMessage.addUnparsed(string);
    }

    @Override
    public boolean isReliable() {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void processMessage(SIPMessage object) {
        block19 : {
            block20 : {
                ServerResponseInterface serverResponseInterface;
                block21 : {
                    Object object2;
                    Object object3;
                    if (!(object instanceof SIPRequest)) break block21;
                    SIPRequest sIPRequest = (SIPRequest)object;
                    if (this.sipStack.getStackLogger().isLoggingEnabled(16)) {
                        ServerLogger serverLogger = this.sipStack.serverLogger;
                        object3 = this.getPeerHostPort().toString();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(this.getHost());
                        ((StringBuilder)object2).append(":");
                        ((StringBuilder)object2).append(this.myPort);
                        serverLogger.logMessage((SIPMessage)object, (String)object3, ((StringBuilder)object2).toString(), false, this.receptionTime);
                    }
                    if ((object = this.sipStack.newSIPServerRequest(sIPRequest, this)) == null) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logWarning("Null request interface returned -- dropping request");
                        }
                        return;
                    }
                    if (this.sipStack.isLoggingEnabled()) {
                        object3 = this.sipStack.getStackLogger();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("About to process ");
                        ((StringBuilder)object2).append(sIPRequest.getFirstLine());
                        ((StringBuilder)object2).append("/");
                        ((StringBuilder)object2).append(object);
                        object3.logDebug(((StringBuilder)object2).toString());
                    }
                    object.processRequest(sIPRequest, this);
                    if (!this.sipStack.isLoggingEnabled()) break block19;
                    object2 = this.sipStack.getStackLogger();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Done processing ");
                    ((StringBuilder)object3).append(sIPRequest.getFirstLine());
                    ((StringBuilder)object3).append("/");
                    ((StringBuilder)object3).append(object);
                    object2.logDebug(((StringBuilder)object3).toString());
                    break block19;
                    finally {
                        if (object instanceof SIPTransaction && !((SIPServerTransaction)object).passToListener()) {
                            ((SIPTransaction)object).releaseSem();
                        }
                    }
                }
                object = (SIPResponse)object;
                try {
                    ((SIPResponse)object).checkHeaders();
                    serverResponseInterface = this.sipStack.newSIPServerResponse((SIPResponse)object, this);
                    if (serverResponseInterface == null) break block20;
                }
                catch (ParseException parseException) {
                    if (this.sipStack.isLoggingEnabled()) {
                        StackLogger stackLogger = this.sipStack.getStackLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Dropping Badly formatted response message >>> ");
                        stringBuilder.append(object);
                        stackLogger.logError(stringBuilder.toString());
                    }
                    return;
                }
                try {
                    if (serverResponseInterface instanceof SIPClientTransaction && !((SIPClientTransaction)serverResponseInterface).checkFromTag((SIPResponse)object)) {
                        if (this.sipStack.isLoggingEnabled()) {
                            StackLogger stackLogger = this.sipStack.getStackLogger();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Dropping response message with invalid tag >>> ");
                            stringBuilder.append(object);
                            stackLogger.logError(stringBuilder.toString());
                        }
                        return;
                    }
                    serverResponseInterface.processResponse((SIPResponse)object, this);
                }
                finally {
                    if (serverResponseInterface instanceof SIPTransaction && !((SIPTransaction)((Object)serverResponseInterface)).passToListener()) {
                        ((SIPTransaction)((Object)serverResponseInterface)).releaseSem();
                    }
                }
            }
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("null sipServerResponse!");
            }
        }
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
        var1_1 = null;
        do {
            block12 : {
                block11 : {
                    if (this.myParser == null) {
                        this.myParser = new StringMsgParser();
                        this.myParser.setParseExceptionListener(this);
                    }
                    if (this.sipStack.threadPoolSize != -1) break block11;
                    var3_3 = this.incomingPacket;
                    break block12;
                }
                var2_2 = ((UDPMessageProcessor)this.messageProcessor).messageQueue;
                // MONITORENTER : var2_2
                while (((UDPMessageProcessor)this.messageProcessor).messageQueue.isEmpty()) {
                    if (!((UDPMessageProcessor)this.messageProcessor).isRunning) {
                        // MONITOREXIT : var2_2
                        return;
                    }
                    var3_3 = var1_1;
                    if (var1_1 != null) ** GOTO lbl20
                    try {
                        var3_3 = this.sipStack.getThreadAuditor().addCurrentThread();
lbl20: // 2 sources:
                        var1_1 = var3_3;
                        var3_3.ping();
                        var1_1 = var3_3;
                        ((UDPMessageProcessor)this.messageProcessor).messageQueue.wait(var3_3.getPingIntervalInMillisecs());
                        var1_1 = var3_3;
                    }
                    catch (InterruptedException var3_4) {
                        if (((UDPMessageProcessor)this.messageProcessor).isRunning) continue;
                        // MONITOREXIT : var2_2
                        return;
                    }
                }
                var3_3 = (DatagramPacket)((UDPMessageProcessor)this.messageProcessor).messageQueue.removeFirst();
                // MONITOREXIT : var2_2
                this.incomingPacket = var3_3;
            }
            try {
                this.processIncomingDataPacket((DatagramPacket)var3_3);
            }
            catch (Exception var3_5) {
                this.sipStack.getStackLogger().logError("Error while processing incoming UDP packet", var3_5);
            }
        } while (this.sipStack.threadPoolSize != -1);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void sendMessage(SIPMessage var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[FORLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void sendMessage(byte[] object, InetAddress object2, int n, String object3, boolean bl) throws IOException {
        StringBuilder stringBuilder;
        Object object4;
        if (n == -1) {
            if (!this.sipStack.isLoggingEnabled()) throw new IOException("Receiver port not set ");
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.getClass().getName());
            ((StringBuilder)object2).append(":sendMessage: Dropping reply!");
            object.logDebug(((StringBuilder)object2).toString());
            throw new IOException("Receiver port not set ");
        }
        if (this.sipStack.isLoggingEnabled()) {
            object4 = this.sipStack.getStackLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append(":sendMessage ");
            stringBuilder.append(((InetAddress)object2).getHostAddress());
            stringBuilder.append("/");
            stringBuilder.append(n);
            stringBuilder.append("\n messageSize = ");
            stringBuilder.append(((Object)object).length);
            object4.logDebug(stringBuilder.toString());
        }
        if (((String)object3).compareToIgnoreCase("UDP") == 0) {
            object4 = new DatagramPacket((byte[])object, ((Object)object).length, (InetAddress)object2, n);
            try {
                object3 = this.sipStack.udpFlag ? ((UDPMessageProcessor)this.messageProcessor).sock : this.sipStack.getNetworkLayer().createDatagramSocket();
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("sendMessage ");
                    stringBuilder.append(((InetAddress)object2).getHostAddress());
                    stringBuilder.append("/");
                    stringBuilder.append(n);
                    stringBuilder.append("\n");
                    object2 = new String((byte[])object);
                    stringBuilder.append((String)object2);
                    stackLogger.logDebug(stringBuilder.toString());
                }
                ((DatagramSocket)object3).send((DatagramPacket)object4);
                if (this.sipStack.udpFlag) return;
                ((DatagramSocket)object3).close();
                return;
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
                return;
            }
            catch (IOException iOException) {
                throw iOException;
            }
        } else {
            object2 = this.sipStack.ioHandler.sendBytes(this.messageProcessor.getIpAddress(), (InetAddress)object2, n, "tcp", (byte[])object, bl, this).getOutputStream();
            ((OutputStream)object2).write((byte[])object, 0, ((Object)object).length);
            ((OutputStream)object2).flush();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void sendMessage(byte[] object, InetAddress object2, int n, boolean bl) throws IOException {
        if (this.sipStack.isLoggingEnabled() && this.sipStack.isLogStackTraceOnMessageSend()) {
            this.sipStack.getStackLogger().logStackTrace(16);
        }
        if (n == -1) {
            if (!this.sipStack.isLoggingEnabled()) throw new IOException("Receiver port not set ");
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.getClass().getName());
            ((StringBuilder)object2).append(":sendMessage: Dropping reply!");
            object.logDebug(((StringBuilder)object2).toString());
            throw new IOException("Receiver port not set ");
        }
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendMessage ");
            stringBuilder.append(((InetAddress)object2).getHostAddress());
            stringBuilder.append("/");
            stringBuilder.append(n);
            stringBuilder.append("\nmessageSize =  ");
            stringBuilder.append(((Object)object).length);
            stringBuilder.append(" message = ");
            stringBuilder.append(new String((byte[])object));
            stackLogger.logDebug(stringBuilder.toString());
            this.sipStack.getStackLogger().logDebug("*******************\n");
        }
        object2 = new DatagramPacket((byte[])object, ((Object)object).length, (InetAddress)object2, n);
        n = 0;
        try {
            if (this.sipStack.udpFlag) {
                object = ((UDPMessageProcessor)this.messageProcessor).sock;
            } else {
                object = new DatagramSocket();
                n = 1;
            }
            ((DatagramSocket)object).send((DatagramPacket)object2);
            if (n == 0) return;
            ((DatagramSocket)object).close();
            return;
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException(exception);
        }
        return;
        catch (IOException iOException) {
            throw iOException;
        }
    }

    class PingBackTimerTask
    extends TimerTask {
        String ipAddress;
        int port;

        public PingBackTimerTask(String string, int n) {
            this.ipAddress = string;
            this.port = n;
            Hashtable hashtable = ((UDPMessageChannel)UDPMessageChannel.this).pingBackRecord;
            UDPMessageChannel.this = new StringBuilder();
            ((StringBuilder)UDPMessageChannel.this).append(string);
            ((StringBuilder)UDPMessageChannel.this).append(":");
            ((StringBuilder)UDPMessageChannel.this).append(n);
            hashtable.put(((StringBuilder)UDPMessageChannel.this).toString(), this);
        }

        public int hashCode() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.ipAddress);
            stringBuilder.append(":");
            stringBuilder.append(this.port);
            return stringBuilder.toString().hashCode();
        }

        @Override
        public void run() {
            Hashtable hashtable = UDPMessageChannel.this.pingBackRecord;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.ipAddress);
            stringBuilder.append(":");
            stringBuilder.append(this.port);
            hashtable.remove(stringBuilder.toString());
        }
    }

}

