/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.core.ThreadAuditor;
import gov.nist.core.net.AddressResolver;
import gov.nist.core.net.DefaultNetworkLayer;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.DefaultAddressResolver;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.LogRecordFactory;
import gov.nist.javax.sip.SipListenerExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.extensions.JoinHeader;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.DefaultRouter;
import gov.nist.javax.sip.stack.IOHandler;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPDialogErrorEvent;
import gov.nist.javax.sip.stack.SIPDialogEventListener;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionErrorEvent;
import gov.nist.javax.sip.stack.SIPTransactionEventListener;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import gov.nist.javax.sip.stack.ServerResponseInterface;
import gov.nist.javax.sip.stack.StackMessageFactory;
import gov.nist.javax.sip.stack.TCPMessageProcessor;
import gov.nist.javax.sip.stack.TLSMessageProcessor;
import gov.nist.javax.sip.stack.UDPMessageProcessor;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.DialogTerminatedEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.Transaction;
import javax.sip.TransactionState;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.Hop;
import javax.sip.address.Router;
import javax.sip.address.URI;
import javax.sip.header.CallIdHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public abstract class SIPTransactionStack
implements SIPTransactionEventListener,
SIPDialogEventListener {
    public static final int BASE_TIMER_INTERVAL = 500;
    public static final int CONNECTION_LINGER_TIME = 8;
    protected static final Set<String> dialogCreatingMethods = new HashSet<String>();
    private AtomicInteger activeClientTransactionCount = new AtomicInteger(0);
    protected AddressResolver addressResolver = new DefaultAddressResolver();
    protected boolean cacheClientConnections = true;
    protected boolean cacheServerConnections = true;
    protected boolean cancelClientTransactionChecked = true;
    protected boolean checkBranchId;
    private ConcurrentHashMap<String, SIPClientTransaction> clientTransactionTable = new ConcurrentHashMap();
    protected int clientTransactionTableHiwaterMark = 1000;
    protected int clientTransactionTableLowaterMark = 800;
    protected DefaultRouter defaultRouter;
    protected ConcurrentHashMap<String, SIPDialog> dialogTable = new ConcurrentHashMap();
    protected ConcurrentHashMap<String, SIPDialog> earlyDialogTable = new ConcurrentHashMap();
    private ConcurrentHashMap<String, SIPClientTransaction> forkedClientTransactionTable = new ConcurrentHashMap();
    protected HashSet<String> forkedEvents = new HashSet();
    protected boolean generateTimeStampHeader;
    protected IOHandler ioHandler = new IOHandler(this);
    protected boolean isAutomaticDialogErrorHandlingEnabled = true;
    protected boolean isAutomaticDialogSupportEnabled;
    protected boolean isBackToBackUserAgent = false;
    protected boolean isDialogTerminatedEventDeliveredForNullDialog = false;
    protected LogRecordFactory logRecordFactory;
    protected boolean logStackTraceOnMessageSend = true;
    protected int maxConnections = -1;
    protected int maxContentLength;
    protected int maxForkTime = 0;
    protected int maxListenerResponseTime = -1;
    protected int maxMessageSize;
    private ConcurrentHashMap<String, SIPServerTransaction> mergeTable = new ConcurrentHashMap();
    private Collection<MessageProcessor> messageProcessors = new ArrayList<MessageProcessor>();
    protected boolean needsLogging;
    protected NetworkLayer networkLayer;
    private boolean non2XXAckPassedToListener;
    protected String outboundProxy;
    private ConcurrentHashMap<String, SIPServerTransaction> pendingTransactions = new ConcurrentHashMap();
    protected int readTimeout = -1;
    protected int receiveUdpBufferSize;
    protected boolean remoteTagReassignmentAllowed = true;
    protected ConcurrentHashMap<String, SIPServerTransaction> retransmissionAlertTransactions = new ConcurrentHashMap();
    protected boolean rfc2543Supported = true;
    protected Router router;
    protected String routerPath;
    protected int sendUdpBufferSize;
    protected ServerLogger serverLogger;
    private ConcurrentHashMap<String, SIPServerTransaction> serverTransactionTable = new ConcurrentHashMap();
    protected int serverTransactionTableHighwaterMark = 5000;
    protected int serverTransactionTableLowaterMark = 4000;
    protected StackMessageFactory sipMessageFactory;
    protected String stackAddress;
    protected boolean stackDoesCongestionControl = true;
    protected InetAddress stackInetAddress;
    private StackLogger stackLogger;
    protected String stackName;
    private ConcurrentHashMap<String, SIPServerTransaction> terminatedServerTransactionsPendingAck = new ConcurrentHashMap();
    protected ThreadAuditor threadAuditor = new ThreadAuditor();
    protected int threadPoolSize = -1;
    private Timer timer = new Timer();
    protected boolean toExit = false;
    boolean udpFlag;
    protected boolean unlimitedClientTransactionTableSize = true;
    protected boolean unlimitedServerTransactionTableSize = true;
    protected boolean useRouterForAll;

    static {
        dialogCreatingMethods.add("REFER");
        dialogCreatingMethods.add("INVITE");
        dialogCreatingMethods.add("SUBSCRIBE");
    }

    protected SIPTransactionStack() {
        if (this.getThreadAuditor().isEnabled()) {
            this.timer.schedule((TimerTask)new PingTimer(null), 0L);
        }
    }

    protected SIPTransactionStack(StackMessageFactory stackMessageFactory) {
        this();
        this.sipMessageFactory = stackMessageFactory;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void addTransactionHash(SIPTransaction var1_1) {
        block6 : {
            block8 : {
                block7 : {
                    var2_2 = var1_1.getOriginalRequest();
                    if (!(var1_1 instanceof SIPClientTransaction)) break block6;
                    if (this.unlimitedClientTransactionTableSize) break block7;
                    if (this.activeClientTransactionCount.get() <= this.clientTransactionTableHiwaterMark) break block8;
                    try {
                        var3_3 = this.clientTransactionTable;
                        // MONITORENTER : var3_3
                    }
                    catch (Exception var3_4) {
                        if (!this.stackLogger.isLoggingEnabled()) ** GOTO lbl22
                        this.stackLogger.logError("Exception occured while waiting for room", var3_4);
                    }
                    this.clientTransactionTable.wait();
                    this.activeClientTransactionCount.incrementAndGet();
                    // MONITOREXIT : var3_3
                    break block8;
                    break block8;
                }
                this.activeClientTransactionCount.incrementAndGet();
            }
            var2_2 = var2_2.getTransactionId();
            this.clientTransactionTable.put((String)var2_2, (SIPClientTransaction)var1_1);
            if (this.stackLogger.isLoggingEnabled() == false) return;
            var1_1 = this.stackLogger;
            var3_3 = new StringBuilder();
            var3_3.append(" putTransactionHash :  key = ");
            var3_3.append((String)var2_2);
            var1_1.logDebug(var3_3.toString());
            return;
        }
        var4_6 = var2_2.getTransactionId();
        if (this.stackLogger.isLoggingEnabled()) {
            var2_2 = this.stackLogger;
            var3_5 = new StringBuilder();
            var3_5.append(" putTransactionHash :  key = ");
            var3_5.append(var4_6);
            var2_2.logDebug(var3_5.toString());
        }
        this.serverTransactionTable.put(var4_6, (SIPServerTransaction)var1_1);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String auditDialogs(Set object, long l) {
        Object object2;
        Object object3 = "  Leaked dialogs:\n";
        int n = 0;
        long l2 = System.currentTimeMillis();
        Object object4 = this.dialogTable;
        synchronized (object4) {
            object2 = new LinkedList(this.dialogTable.values());
        }
        Iterator iterator = ((AbstractSequentialList)object2).iterator();
        while (iterator.hasNext()) {
            int n2;
            Serializable serializable = (SIPDialog)iterator.next();
            object2 = null;
            object4 = serializable != null ? ((SIPDialog)serializable).getCallId() : null;
            if (object4 != null) {
                object2 = object4.getCallId();
            }
            if (serializable != null && object2 != null) {
                object4 = object3;
                n2 = n;
                if (!object.contains(object2)) {
                    if (((SIPDialog)serializable).auditTag == 0L) {
                        ((SIPDialog)serializable).auditTag = l2;
                        object4 = object3;
                        n2 = n;
                    } else {
                        void var2_2;
                        object4 = object3;
                        n2 = n++;
                        if (l2 - ((SIPDialog)serializable).auditTag >= var2_2) {
                            object4 = ((SIPDialog)serializable).getState();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("dialog id: ");
                            ((StringBuilder)object2).append(((SIPDialog)serializable).getDialogId());
                            ((StringBuilder)object2).append(", dialog state: ");
                            object4 = object4 != null ? ((Enum)object4).toString() : "null";
                            ((StringBuilder)object2).append((String)object4);
                            object2 = ((StringBuilder)object2).toString();
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append((String)object3);
                            ((StringBuilder)object4).append("    ");
                            ((StringBuilder)object4).append((String)object2);
                            ((StringBuilder)object4).append("\n");
                            object3 = ((StringBuilder)object4).toString();
                            ((SIPDialog)serializable).setState(SIPDialog.TERMINATED_STATE);
                            object4 = object3;
                            n2 = n;
                            if (this.stackLogger.isLoggingEnabled()) {
                                object4 = this.stackLogger;
                                serializable = new StringBuilder();
                                ((StringBuilder)serializable).append("auditDialogs: leaked ");
                                ((StringBuilder)serializable).append((String)object2);
                                object4.logDebug(((StringBuilder)serializable).toString());
                                object4 = object3;
                                n2 = n;
                            }
                        }
                    }
                }
            } else {
                n2 = n;
                object4 = object3;
            }
            object3 = object4;
            n = n2;
        }
        if (n <= 0) return null;
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object3);
        ((StringBuilder)object).append("    Total: ");
        ((StringBuilder)object).append(Integer.toString(n));
        ((StringBuilder)object).append(" leaked dialogs detected and removed.\n");
        return ((StringBuilder)object).toString();
    }

    private String auditTransactions(ConcurrentHashMap object, long l) {
        Object object2 = "  Leaked transactions:\n";
        int n = 0;
        long l2 = System.currentTimeMillis();
        for (SIPTransaction sIPTransaction : new LinkedList(((ConcurrentHashMap)object).values())) {
            object = object2;
            int n2 = n;
            if (sIPTransaction != null) {
                if (sIPTransaction.auditTag == 0L) {
                    sIPTransaction.auditTag = l2;
                    object = object2;
                    n2 = n;
                } else {
                    object = object2;
                    n2 = n++;
                    if (l2 - sIPTransaction.auditTag >= l) {
                        Object object3 = sIPTransaction.getState();
                        object = sIPTransaction.getOriginalRequest();
                        object = object != null ? ((SIPRequest)object).getMethod() : null;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(sIPTransaction.getClass().getName());
                        stringBuilder.append(", state: ");
                        Object object4 = "null";
                        object3 = object3 != null ? object3.toString() : "null";
                        stringBuilder.append((String)object3);
                        stringBuilder.append(", OR: ");
                        object3 = object4;
                        if (object != null) {
                            object3 = object;
                        }
                        stringBuilder.append((String)object3);
                        object3 = stringBuilder.toString();
                        object = new StringBuilder();
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("    ");
                        ((StringBuilder)object).append((String)object3);
                        ((StringBuilder)object).append("\n");
                        object2 = ((StringBuilder)object).toString();
                        this.removeTransaction(sIPTransaction);
                        object = object2;
                        n2 = n;
                        if (this.isLoggingEnabled()) {
                            object4 = this.stackLogger;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("auditTransactions: leaked ");
                            ((StringBuilder)object).append((String)object3);
                            object4.logDebug(((StringBuilder)object).toString());
                            n2 = n;
                            object = object2;
                        }
                    }
                }
            }
            object2 = object;
            n = n2;
        }
        if (n > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("    Total: ");
            ((StringBuilder)object).append(Integer.toString(n));
            ((StringBuilder)object).append(" leaked transactions detected and removed.\n");
            object = ((StringBuilder)object).toString();
        } else {
            object = null;
        }
        return object;
    }

    public static boolean isDialogCreated(String string) {
        return dialogCreatingMethods.contains(string);
    }

    public void addExtensionMethod(String string) {
        if (string.equals("NOTIFY")) {
            if (this.stackLogger.isLoggingEnabled()) {
                this.stackLogger.logDebug("NOTIFY Supported Natively");
            }
        } else {
            dialogCreatingMethods.add(string.trim().toUpperCase());
        }
    }

    public void addForkedClientTransaction(SIPClientTransaction sIPClientTransaction) {
        this.forkedClientTransactionTable.put(sIPClientTransaction.getTransactionId(), sIPClientTransaction);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void addMessageProcessor(MessageProcessor messageProcessor) throws IOException {
        Collection<MessageProcessor> collection = this.messageProcessors;
        synchronized (collection) {
            this.messageProcessors.add(messageProcessor);
            return;
        }
    }

    public void addTransaction(SIPClientTransaction sIPClientTransaction) {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("added transaction ");
            stringBuilder.append(sIPClientTransaction);
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.addTransactionHash(sIPClientTransaction);
    }

    public void addTransaction(SIPServerTransaction sIPServerTransaction) throws IOException {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("added transaction ");
            stringBuilder.append(sIPServerTransaction);
            stackLogger.logDebug(stringBuilder.toString());
        }
        sIPServerTransaction.map();
        this.addTransactionHash(sIPServerTransaction);
    }

    public void addTransactionPendingAck(SIPServerTransaction sIPServerTransaction) {
        String string = ((SIPRequest)sIPServerTransaction.getRequest()).getTopmostVia().getBranch();
        if (string != null) {
            this.terminatedServerTransactionsPendingAck.put(string, sIPServerTransaction);
        }
    }

    public String auditStack(Set object, long l, long l2) {
        block5 : {
            String string;
            String string2;
            String string3;
            String string4;
            block4 : {
                string4 = null;
                string2 = this.auditDialogs((Set)object, l);
                string3 = this.auditTransactions(this.serverTransactionTable, l2);
                string = this.auditTransactions(this.clientTransactionTable, l2);
                if (string2 != null || string3 != null) break block4;
                object = string4;
                if (string == null) break block5;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SIP Stack Audit:\n");
            string4 = "";
            object = string2 != null ? string2 : "";
            stringBuilder.append((String)object);
            object = string3 != null ? string3 : "";
            stringBuilder.append((String)object);
            object = string4;
            if (string != null) {
                object = string;
            }
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        }
        return object;
    }

    public boolean checkBranchId() {
        return this.checkBranchId;
    }

    public SIPClientTransaction createClientTransaction(SIPRequest sIPRequest, MessageChannel messageChannel) {
        messageChannel = new SIPClientTransaction(this, messageChannel);
        ((SIPTransaction)messageChannel).setOriginalRequest(sIPRequest);
        return messageChannel;
    }

    public SIPDialog createDialog(SipProviderImpl sipProviderImpl, SIPResponse sIPResponse) {
        return new SIPDialog(sipProviderImpl, sIPResponse);
    }

    public SIPDialog createDialog(SIPClientTransaction serializable, SIPResponse sIPResponse) {
        String string = ((SIPRequest)serializable.getRequest()).getDialogId(false);
        if (this.earlyDialogTable.get(string) != null) {
            SIPDialog sIPDialog = this.earlyDialogTable.get(string);
            serializable = sIPDialog;
            if (sIPResponse.isFinalResponse()) {
                this.earlyDialogTable.remove(string);
                serializable = sIPDialog;
            }
        } else {
            serializable = new SIPDialog((SIPClientTransaction)serializable, sIPResponse);
        }
        return serializable;
    }

    public SIPDialog createDialog(SIPTransaction serializable) {
        if (serializable instanceof SIPClientTransaction) {
            String string = ((SIPRequest)serializable.getRequest()).getDialogId(false);
            if (this.earlyDialogTable.get(string) != null) {
                SIPDialog sIPDialog = this.earlyDialogTable.get(string);
                if (sIPDialog.getState() != null && sIPDialog.getState() != DialogState.EARLY) {
                    serializable = new SIPDialog((SIPTransaction)serializable);
                    this.earlyDialogTable.put(string, (SIPDialog)serializable);
                } else {
                    serializable = sIPDialog;
                }
            } else {
                serializable = new SIPDialog((SIPTransaction)serializable);
                this.earlyDialogTable.put(string, (SIPDialog)serializable);
            }
        } else {
            serializable = new SIPDialog((SIPTransaction)serializable);
        }
        return serializable;
    }

    public MessageChannel createMessageChannel(SIPRequest serializable, MessageProcessor object, Hop hop) throws IOException {
        Host host = new Host();
        host.setHostname(hop.getHost());
        HostPort hostPort = new HostPort();
        hostPort.setHost(host);
        hostPort.setPort(hop.getPort());
        object = ((MessageProcessor)object).createMessageChannel(hostPort);
        if (object == null) {
            return null;
        }
        serializable = this.createClientTransaction((SIPRequest)serializable, (MessageChannel)object);
        ((SIPClientTransaction)serializable).setViaPort(hop.getPort());
        ((SIPClientTransaction)serializable).setViaHost(hop.getHost());
        this.addTransactionHash((SIPTransaction)serializable);
        return serializable;
    }

    protected MessageProcessor createMessageProcessor(InetAddress object, int n, String object2) throws IOException {
        if (((String)object2).equalsIgnoreCase("udp")) {
            object = new UDPMessageProcessor((InetAddress)object, this, n);
            this.addMessageProcessor((MessageProcessor)object);
            this.udpFlag = true;
            return object;
        }
        if (((String)object2).equalsIgnoreCase("tcp")) {
            object = new TCPMessageProcessor((InetAddress)object, this, n);
            this.addMessageProcessor((MessageProcessor)object);
            return object;
        }
        if (((String)object2).equalsIgnoreCase("tls")) {
            object = new TLSMessageProcessor((InetAddress)object, this, n);
            this.addMessageProcessor((MessageProcessor)object);
            return object;
        }
        if (((String)object2).equalsIgnoreCase("sctp")) {
            try {
                object2 = (MessageProcessor)ClassLoader.getSystemClassLoader().loadClass("gov.nist.javax.sip.stack.sctp.SCTPMessageProcessor").newInstance();
                ((MessageProcessor)object2).initialize((InetAddress)object, n, this);
                this.addMessageProcessor((MessageProcessor)object2);
                return object2;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalArgumentException("Error initializing SCTP", illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                throw new IllegalArgumentException("Error initializing SCTP", instantiationException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new IllegalArgumentException("SCTP not supported (needs Java 7 and SCTP jar in classpath)");
            }
        }
        throw new IllegalArgumentException("bad transport");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MessageChannel createRawMessageChannel(String string, int n, Hop hop) throws UnknownHostException {
        Object object = new Host();
        ((Host)object).setHostname(hop.getHost());
        HostPort hostPort = new HostPort();
        hostPort.setHost((Host)object);
        hostPort.setPort(hop.getPort());
        object = null;
        Iterator<MessageProcessor> iterator = this.messageProcessors.iterator();
        while (iterator.hasNext()) {
            Object object2;
            block4 : {
                if (object != null) return object;
                object2 = iterator.next();
                if (!hop.getTransport().equalsIgnoreCase(((MessageProcessor)object2).getTransport()) || !string.equals(((MessageProcessor)object2).getIpAddress().getHostAddress()) || n != ((MessageProcessor)object2).getPort()) continue;
                try {
                    object2 = ((MessageProcessor)object2).createMessageChannel(hostPort);
                }
                catch (IOException iOException) {
                    object2 = object;
                    if (!this.stackLogger.isLoggingEnabled()) break block4;
                    this.stackLogger.logException(iOException);
                    object2 = object;
                }
            }
            object = object2;
        }
        return object;
        catch (UnknownHostException unknownHostException) {
            if (!this.stackLogger.isLoggingEnabled()) throw unknownHostException;
            this.stackLogger.logException(unknownHostException);
            throw unknownHostException;
        }
    }

    public SIPServerTransaction createServerTransaction(MessageChannel messageChannel) {
        if (this.unlimitedServerTransactionTableSize) {
            return new SIPServerTransaction(this, messageChannel);
        }
        int n = this.serverTransactionTable.size();
        int n2 = this.serverTransactionTableLowaterMark;
        float f = (float)(n - n2) / (float)(this.serverTransactionTableHighwaterMark - n2);
        n = Math.random() > 1.0 - (double)f ? 1 : 0;
        if (n != 0) {
            return null;
        }
        return new SIPServerTransaction(this, messageChannel);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void decrementActiveClientTransactionCount() {
        if (this.activeClientTransactionCount.decrementAndGet() > this.clientTransactionTableLowaterMark) return;
        if (this.unlimitedClientTransactionTableSize) return;
        ConcurrentHashMap<String, SIPClientTransaction> concurrentHashMap = this.clientTransactionTable;
        synchronized (concurrentHashMap) {
            this.clientTransactionTable.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dialogErrorEvent(SIPDialogErrorEvent serializable) {
        synchronized (this) {
            serializable = (SIPDialog)((EventObject)serializable).getSource();
            SipListener sipListener = ((SipStackImpl)this).getSipListener();
            if (serializable != null && !(sipListener instanceof SipListenerExt)) {
                ((SIPDialog)serializable).delete();
            }
            return;
        }
    }

    public void disableLogging() {
        this.getStackLogger().disableLogging();
    }

    public void enableLogging() {
        this.getStackLogger().enableLogging();
    }

    public SIPTransaction findCancelTransaction(SIPRequest sIPRequest, boolean bl) {
        Object object;
        Object object2;
        if (this.stackLogger.isLoggingEnabled()) {
            object2 = this.stackLogger;
            object = new StringBuilder();
            ((StringBuilder)object).append("findCancelTransaction request= \n");
            ((StringBuilder)object).append(sIPRequest);
            ((StringBuilder)object).append("\nfindCancelRequest isServer=");
            ((StringBuilder)object).append(bl);
            object2.logDebug(((StringBuilder)object).toString());
        }
        if (bl) {
            object2 = this.serverTransactionTable.values().iterator();
            while (object2.hasNext()) {
                object = (SIPServerTransaction)((SIPTransaction)object2.next());
                if (!((SIPTransaction)object).doesCancelMatchTransaction(sIPRequest)) continue;
                return object;
            }
        } else {
            object = this.clientTransactionTable.values().iterator();
            while (object.hasNext()) {
                object2 = (SIPClientTransaction)((SIPTransaction)object.next());
                if (!((SIPTransaction)object2).doesCancelMatchTransaction(sIPRequest)) continue;
                return object2;
            }
        }
        if (this.stackLogger.isLoggingEnabled()) {
            this.stackLogger.logDebug("Could not find transaction for cancel request");
        }
        return null;
    }

    public SIPServerTransaction findMergedTransaction(SIPRequest sIPRequest) {
        if (!sIPRequest.getMethod().equals("INVITE")) {
            return null;
        }
        Object object = sIPRequest.getMergeId();
        Serializable serializable = this.mergeTable.get(object);
        if (object == null) {
            return null;
        }
        if (serializable != null && !((SIPServerTransaction)serializable).isMessagePartOfTransaction(sIPRequest)) {
            return serializable;
        }
        Iterator<SIPDialog> iterator = this.dialogTable.values().iterator();
        while (iterator.hasNext()) {
            SIPDialog sIPDialog = iterator.next();
            if (sIPDialog.getFirstTransaction() == null || !(sIPDialog.getFirstTransaction() instanceof ServerTransaction)) continue;
            object = (SIPServerTransaction)sIPDialog.getFirstTransaction();
            serializable = ((SIPServerTransaction)sIPDialog.getFirstTransaction()).getOriginalRequest();
            if (((SIPServerTransaction)object).isMessagePartOfTransaction(sIPRequest) || !sIPRequest.getMergeId().equals(((SIPRequest)serializable).getMergeId())) continue;
            return (SIPServerTransaction)sIPDialog.getFirstTransaction();
        }
        return null;
    }

    public SIPServerTransaction findPendingTransaction(SIPRequest sIPRequest) {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("looking for pending tx for :");
            stringBuilder.append(sIPRequest.getTransactionId());
            stackLogger.logDebug(stringBuilder.toString());
        }
        return this.pendingTransactions.get(sIPRequest.getTransactionId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SIPClientTransaction findSubscribeTransaction(SIPRequest object, ListeningPointImpl object2) {
        Object object3;
        Iterator<SIPClientTransaction> iterator;
        Serializable serializable;
        Event event;
        block13 : {
            block14 : {
                block11 : {
                    block12 : {
                        object2 = null;
                        iterator = this.clientTransactionTable.values().iterator();
                        if (this.stackLogger.isLoggingEnabled()) {
                            object3 = this.stackLogger;
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("ct table size = ");
                            ((StringBuilder)serializable).append(this.clientTransactionTable.size());
                            object3.logDebug(((StringBuilder)serializable).toString());
                        }
                        if ((object3 = ((SIPMessage)object).getTo().getTag()) != null) break block11;
                        if (!this.stackLogger.isLoggingEnabled()) break block12;
                        object = this.stackLogger;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("findSubscribeTransaction : returning ");
                        ((StringBuilder)object2).append((Object)null);
                        object.logDebug(((StringBuilder)object2).toString());
                    }
                    return null;
                }
                event = (Event)((SIPMessage)object).getHeader("Event");
                if (event != null) break block13;
                if (this.stackLogger.isLoggingEnabled()) {
                    this.stackLogger.logDebug("event Header is null -- returning null");
                }
                if (!this.stackLogger.isLoggingEnabled()) break block14;
                object = this.stackLogger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("findSubscribeTransaction : returning ");
                ((StringBuilder)object2).append((Object)null);
                object.logDebug(((StringBuilder)object2).toString());
            }
            return null;
        }
        try {
            while (iterator.hasNext()) {
                serializable = iterator.next();
                if (!((SIPTransaction)serializable).getMethod().equals("SUBSCRIBE")) continue;
                String string = ((SIPClientTransaction)serializable).from.getTag();
                Event event2 = ((SIPClientTransaction)serializable).event;
                if (event2 == null) continue;
                if (this.stackLogger.isLoggingEnabled()) {
                    Object object4 = this.stackLogger;
                    Object object5 = new StringBuilder();
                    ((StringBuilder)object5).append("ct.fromTag = ");
                    ((StringBuilder)object5).append(string);
                    object4.logDebug(((StringBuilder)object5).toString());
                    object4 = this.stackLogger;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append("thisToTag = ");
                    ((StringBuilder)object5).append((String)object3);
                    object4.logDebug(((StringBuilder)object5).toString());
                    object5 = this.stackLogger;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("hisEvent = ");
                    ((StringBuilder)object4).append(event2);
                    object5.logDebug(((StringBuilder)object4).toString());
                    object5 = this.stackLogger;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("eventHdr ");
                    ((StringBuilder)object4).append(event);
                    object5.logDebug(((StringBuilder)object4).toString());
                }
                if (!string.equalsIgnoreCase((String)object3) || !event.match(event2) || !((SIPMessage)object).getCallId().getCallId().equalsIgnoreCase(((SIPClientTransaction)serializable).callId.getCallId())) continue;
                boolean bl = ((SIPTransaction)serializable).acquireSem();
                object = object2;
                if (bl) {
                    object = serializable;
                }
                if (!this.stackLogger.isLoggingEnabled()) break block15;
                object2 = this.stackLogger;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("findSubscribeTransaction : returning ");
            }
        }
        catch (Throwable throwable) {
            if (this.stackLogger.isLoggingEnabled()) {
                object = this.stackLogger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("findSubscribeTransaction : returning ");
                ((StringBuilder)object2).append((Object)null);
                object.logDebug(((StringBuilder)object2).toString());
            }
            throw throwable;
        }
        {
            block15 : {
                ((StringBuilder)serializable).append(object);
                object2.logDebug(((StringBuilder)serializable).toString());
            }
            return object;
        }
        if (this.stackLogger.isLoggingEnabled()) {
            object2 = this.stackLogger;
            object = new StringBuilder();
            ((StringBuilder)object).append("findSubscribeTransaction : returning ");
            ((StringBuilder)object).append((Object)null);
            object2.logDebug(((StringBuilder)object).toString());
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SIPTransaction findTransaction(SIPMessage object, boolean bl) {
        Object object3;
        Object object2;
        Object object4;
        block14 : {
            object3 = null;
            object2 = null;
            object4 = null;
            if (bl) {
                block12 : {
                    Object object5;
                    block11 : {
                        object3 = object2;
                        if (((SIPMessage)object).getTopmostVia().getBranch() == null) break block11;
                        object3 = object2;
                        object4 = ((SIPMessage)object).getTransactionId();
                        object3 = object2;
                        object3 = object2 = (SIPTransaction)this.serverTransactionTable.get(object4);
                        if (this.stackLogger.isLoggingEnabled()) {
                            object3 = object2;
                            StackLogger stackLogger = this.getStackLogger();
                            object3 = object2;
                            object3 = object2;
                            object5 = new StringBuilder();
                            object3 = object2;
                            ((StringBuilder)object5).append("serverTx: looking for key ");
                            object3 = object2;
                            ((StringBuilder)object5).append((String)object4);
                            object3 = object2;
                            ((StringBuilder)object5).append(" existing=");
                            object3 = object2;
                            ((StringBuilder)object5).append(this.serverTransactionTable);
                            object3 = object2;
                            stackLogger.logDebug(((StringBuilder)object5).toString());
                        }
                        object3 = object2;
                        bl = ((String)object4).startsWith("z9hg4bk");
                        object4 = object2;
                        if (!bl) break block11;
                        if (!this.getStackLogger().isLoggingEnabled()) return object2;
                        object3 = this.getStackLogger();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("findTransaction: returning  : ");
                        ((StringBuilder)object).append(object2);
                        object3.logDebug(((StringBuilder)object).toString());
                        return object2;
                    }
                    object3 = object4;
                    object5 = this.serverTransactionTable.values().iterator();
                    do {
                        object3 = object4;
                        if (!object5.hasNext()) break block12;
                        object3 = object4;
                        object2 = object5.next();
                        object3 = object4;
                    } while (!(bl = ((SIPServerTransaction)object2).isMessagePartOfTransaction((SIPMessage)object)));
                    if (!this.getStackLogger().isLoggingEnabled()) return object2;
                    object3 = this.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("findTransaction: returning  : ");
                    ((StringBuilder)object).append(object2);
                    object3.logDebug(((StringBuilder)object).toString());
                    return object2;
                }
                object2 = object4;
            } else {
                Object object6;
                block13 : {
                    object4 = object3;
                    object3 = object2;
                    if (((SIPMessage)object).getTopmostVia().getBranch() == null) break block13;
                    object3 = object2;
                    object4 = ((SIPMessage)object).getTransactionId();
                    object3 = object2;
                    if (this.stackLogger.isLoggingEnabled()) {
                        object3 = object2;
                        object6 = this.getStackLogger();
                        object3 = object2;
                        object3 = object2;
                        StringBuilder stringBuilder = new StringBuilder();
                        object3 = object2;
                        stringBuilder.append("clientTx: looking for key ");
                        object3 = object2;
                        stringBuilder.append((String)object4);
                        object3 = object2;
                        object6.logDebug(stringBuilder.toString());
                    }
                    object3 = object2;
                    object3 = object2 = (SIPTransaction)this.clientTransactionTable.get(object4);
                    bl = ((String)object4).startsWith("z9hg4bk");
                    object4 = object2;
                    if (!bl) break block13;
                    if (!this.getStackLogger().isLoggingEnabled()) return object2;
                    object = this.getStackLogger();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("findTransaction: returning  : ");
                    ((StringBuilder)object3).append(object2);
                    object.logDebug(((StringBuilder)object3).toString());
                    return object2;
                }
                object3 = object4;
                object6 = this.clientTransactionTable.values().iterator();
                do {
                    object2 = object4;
                    object3 = object4;
                    if (!object6.hasNext()) break block14;
                    object3 = object4;
                    object2 = (SIPClientTransaction)object6.next();
                    object3 = object4;
                } while (!(bl = ((SIPClientTransaction)object2).isMessagePartOfTransaction((SIPMessage)object)));
                if (!this.getStackLogger().isLoggingEnabled()) return object2;
                object3 = this.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("findTransaction: returning  : ");
                ((StringBuilder)object).append(object2);
                object3.logDebug(((StringBuilder)object).toString());
                return object2;
            }
        }
        if (!this.getStackLogger().isLoggingEnabled()) return object2;
        object3 = this.getStackLogger();
        object = new StringBuilder();
        ((StringBuilder)object).append("findTransaction: returning  : ");
        ((StringBuilder)object).append(object2);
        object3.logDebug(((StringBuilder)object).toString());
        return object2;
        catch (Throwable throwable) {
            if (!this.getStackLogger().isLoggingEnabled()) throw throwable;
            object = this.getStackLogger();
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("findTransaction: returning  : ");
            ((StringBuilder)object4).append(object3);
            object.logDebug(((StringBuilder)object4).toString());
            throw throwable;
        }
    }

    public SIPServerTransaction findTransactionPendingAck(SIPRequest sIPRequest) {
        return this.terminatedServerTransactionsPendingAck.get(sIPRequest.getTopmostVia().getBranch());
    }

    public int getActiveClientTransactionCount() {
        return this.activeClientTransactionCount.get();
    }

    public AddressResolver getAddressResolver() {
        return this.addressResolver;
    }

    public int getClientTransactionTableSize() {
        return this.clientTransactionTable.size();
    }

    public SIPDialog getDialog(String string) {
        SIPDialog sIPDialog = this.dialogTable.get(string);
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getDialog(");
            stringBuilder.append(string);
            stringBuilder.append(") : returning ");
            stringBuilder.append(sIPDialog);
            stackLogger.logDebug(stringBuilder.toString());
        }
        return sIPDialog;
    }

    public Collection<Dialog> getDialogs() {
        HashSet<Dialog> hashSet = new HashSet<Dialog>();
        hashSet.addAll(this.dialogTable.values());
        hashSet.addAll(this.earlyDialogTable.values());
        return hashSet;
    }

    public Collection<Dialog> getDialogs(DialogState dialogState) {
        HashSet<Dialog> hashSet = new HashSet<Dialog>();
        if (DialogState.EARLY.equals((Object)dialogState)) {
            hashSet.addAll(this.earlyDialogTable.values());
        } else {
            for (SIPDialog sIPDialog : this.dialogTable.values()) {
                if (sIPDialog.getState() == null || !sIPDialog.getState().equals((Object)dialogState)) continue;
                hashSet.add(sIPDialog);
            }
        }
        return hashSet;
    }

    public SIPClientTransaction getForkedTransaction(String string) {
        return this.forkedClientTransactionTable.get(string);
    }

    public String getHostAddress() {
        return this.stackAddress;
    }

    public Dialog getJoinDialog(JoinHeader object) {
        CharSequence charSequence = object.getCallId();
        String string = object.getFromTag();
        object = object.getToTag();
        charSequence = new StringBuffer((String)charSequence);
        if (object != null) {
            ((StringBuffer)charSequence).append(":");
            ((StringBuffer)charSequence).append((String)object);
        }
        if (string != null) {
            ((StringBuffer)charSequence).append(":");
            ((StringBuffer)charSequence).append(string);
        }
        return this.dialogTable.get(((StringBuffer)charSequence).toString().toLowerCase());
    }

    public int getMaxMessageSize() {
        return this.maxMessageSize;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected MessageProcessor[] getMessageProcessors() {
        Collection<MessageProcessor> collection = this.messageProcessors;
        synchronized (collection) {
            return this.messageProcessors.toArray(new MessageProcessor[0]);
        }
    }

    public NetworkLayer getNetworkLayer() {
        NetworkLayer networkLayer = this.networkLayer;
        if (networkLayer == null) {
            return DefaultNetworkLayer.SINGLETON;
        }
        return networkLayer;
    }

    public Hop getNextHop(SIPRequest sIPRequest) throws SipException {
        if (this.useRouterForAll) {
            Router router = this.router;
            if (router != null) {
                return router.getNextHop(sIPRequest);
            }
            return null;
        }
        if (!sIPRequest.getRequestURI().isSipURI() && sIPRequest.getRouteHeaders() == null) {
            Router router = this.router;
            if (router != null) {
                return router.getNextHop(sIPRequest);
            }
            return null;
        }
        return this.defaultRouter.getNextHop(sIPRequest);
    }

    public int getReceiveUdpBufferSize() {
        return this.receiveUdpBufferSize;
    }

    public Dialog getReplacesDialog(ReplacesHeader object) {
        block4 : {
            CharSequence charSequence = object.getCallId();
            Object object2 = object.getFromTag();
            object = object.getToTag();
            charSequence = new StringBuffer((String)charSequence);
            if (object != null) {
                ((StringBuffer)charSequence).append(":");
                ((StringBuffer)charSequence).append((String)object);
            }
            if (object2 != null) {
                ((StringBuffer)charSequence).append(":");
                ((StringBuffer)charSequence).append((String)object2);
            }
            charSequence = ((StringBuffer)charSequence).toString().toLowerCase();
            if (this.stackLogger.isLoggingEnabled()) {
                object = this.stackLogger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Looking for dialog ");
                ((StringBuilder)object2).append((String)charSequence);
                object.logDebug(((StringBuilder)object2).toString());
            }
            object = object2 = (Dialog)this.dialogTable.get(charSequence);
            if (object2 != null) break block4;
            Iterator<SIPClientTransaction> iterator = this.clientTransactionTable.values().iterator();
            do {
                object = object2;
                if (!iterator.hasNext()) break block4;
            } while (((SIPClientTransaction)(object = iterator.next())).getDialog((String)charSequence) == null);
            object = ((SIPClientTransaction)object).getDialog((String)charSequence);
        }
        return object;
    }

    public SIPServerTransaction getRetransmissionAlertTransaction(String string) {
        return this.retransmissionAlertTransactions.get(string);
    }

    public Router getRouter() {
        return this.router;
    }

    public Router getRouter(SIPRequest object) {
        if (((SIPRequest)object).getRequestLine() == null) {
            return this.defaultRouter;
        }
        if (this.useRouterForAll) {
            return this.router;
        }
        if (!((SIPRequest)object).getRequestURI().getScheme().equals("sip") && !((SIPRequest)object).getRequestURI().getScheme().equals("sips")) {
            object = this.router;
            if (object != null) {
                return object;
            }
            return this.defaultRouter;
        }
        return this.defaultRouter;
    }

    public int getSendUdpBufferSize() {
        return this.sendUdpBufferSize;
    }

    public ServerLogger getServerLogger() {
        return this.serverLogger;
    }

    public int getServerTransactionTableSize() {
        return this.serverTransactionTable.size();
    }

    public StackLogger getStackLogger() {
        return this.stackLogger;
    }

    public ThreadAuditor getThreadAuditor() {
        return this.threadAuditor;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public boolean isAlive() {
        return this.toExit ^ true;
    }

    public boolean isCancelClientTransactionChecked() {
        return this.cancelClientTransactionChecked;
    }

    public boolean isEventForked(String string) {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isEventForked: ");
            stringBuilder.append(string);
            stringBuilder.append(" returning ");
            stringBuilder.append(this.forkedEvents.contains(string));
            stackLogger.logDebug(stringBuilder.toString());
        }
        return this.forkedEvents.contains(string);
    }

    public boolean isLogStackTraceOnMessageSend() {
        return this.logStackTraceOnMessageSend;
    }

    public boolean isLoggingEnabled() {
        StackLogger stackLogger = this.stackLogger;
        boolean bl = stackLogger == null ? false : stackLogger.isLoggingEnabled();
        return bl;
    }

    public boolean isNon2XXAckPassedToListener() {
        return this.non2XXAckPassedToListener;
    }

    public boolean isRemoteTagReassignmentAllowed() {
        return this.remoteTagReassignmentAllowed;
    }

    public boolean isRfc2543Supported() {
        return this.rfc2543Supported;
    }

    public boolean isTransactionPendingAck(SIPServerTransaction object) {
        object = ((SIPRequest)((SIPTransaction)object).getRequest()).getTopmostVia().getBranch();
        return this.terminatedServerTransactionsPendingAck.contains(object);
    }

    public void mapTransaction(SIPServerTransaction sIPServerTransaction) {
        if (sIPServerTransaction.isMapped) {
            return;
        }
        this.addTransactionHash(sIPServerTransaction);
        sIPServerTransaction.isMapped = true;
    }

    public ServerRequestInterface newSIPServerRequest(SIPRequest sIPMessage, MessageChannel object) {
        Object object2;
        Object object3;
        block20 : {
            String string;
            block19 : {
                string = sIPMessage.getTransactionId();
                ((SIPRequest)sIPMessage).setMessageChannel(object);
                object2 = this.serverTransactionTable.get(string);
                if (object2 == null) break block19;
                object3 = object2;
                if (((SIPServerTransaction)object2).isMessagePartOfTransaction(sIPMessage)) break block20;
            }
            Iterator<SIPServerTransaction> iterator = this.serverTransactionTable.values().iterator();
            object2 = null;
            object3 = null;
            if (!string.toLowerCase().startsWith("z9hg4bk")) {
                do {
                    object2 = object3;
                    if (!iterator.hasNext()) break;
                    object2 = object3;
                    if (object3 != null) break;
                    object2 = iterator.next();
                    if (!((SIPServerTransaction)object2).isMessagePartOfTransaction(sIPMessage)) continue;
                    object3 = object2;
                } while (true);
            }
            object3 = object2;
            if (object2 == null) {
                object3 = this.findPendingTransaction((SIPRequest)sIPMessage);
                if (object3 != null) {
                    ((SIPRequest)sIPMessage).setTransaction(object3);
                    if (((SIPTransaction)object3).acquireSem()) {
                        return object3;
                    }
                    return null;
                }
                object3 = object = this.createServerTransaction((MessageChannel)object);
                if (object != null) {
                    ((SIPServerTransaction)object).setOriginalRequest((SIPRequest)sIPMessage);
                    ((SIPRequest)sIPMessage).setTransaction(object);
                    object3 = object;
                }
            }
        }
        if (this.stackLogger.isLoggingEnabled()) {
            object2 = this.stackLogger;
            object = new StringBuilder();
            ((StringBuilder)object).append("newSIPServerRequest( ");
            ((StringBuilder)object).append(((SIPRequest)sIPMessage).getMethod());
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(sIPMessage.getTopmostVia().getBranch());
            ((StringBuilder)object).append("):");
            ((StringBuilder)object).append(object3);
            object2.logDebug(((StringBuilder)object).toString());
        }
        if (object3 != null) {
            ((SIPServerTransaction)object3).setRequestInterface(this.sipMessageFactory.newSIPServerRequest((SIPRequest)sIPMessage, (MessageChannel)object3));
        }
        if (object3 != null && ((SIPTransaction)object3).acquireSem()) {
            return object3;
        }
        if (object3 != null) {
            block18 : {
                try {
                    if (((SIPServerTransaction)object3).isMessagePartOfTransaction(sIPMessage) && ((SIPTransaction)object3).getMethod().equals(((SIPRequest)sIPMessage).getMethod())) {
                        sIPMessage = ((SIPRequest)sIPMessage).createResponse(100);
                        sIPMessage.removeContent();
                        ((SIPTransaction)object3).getMessageChannel().sendMessage(sIPMessage);
                    }
                }
                catch (Exception exception) {
                    if (!this.isLoggingEnabled()) break block18;
                    this.stackLogger.logError("Exception occured sending TRYING");
                }
            }
            return null;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ServerResponseInterface newSIPServerResponse(SIPResponse var1_1, MessageChannel var2_2) {
        block11 : {
            block10 : {
                var3_3 = var1_1.getTransactionId();
                var4_4 = this.clientTransactionTable.get(var3_3);
                if (var4_4 == null) break block10;
                var5_5 = var4_4;
                if (var4_4.isMessagePartOfTransaction((SIPMessage)var1_1)) break block11;
                var5_5 = var4_4;
                if (var3_3.startsWith("z9hg4bk")) break block11;
            }
            var3_3 = this.clientTransactionTable.values().iterator();
            var4_4 = null;
            while (var3_3.hasNext() && var4_4 == null) {
                var5_5 = (SIPClientTransaction)var3_3.next();
                if (!var5_5.isMessagePartOfTransaction((SIPMessage)var1_1)) continue;
                var4_4 = var5_5;
            }
            var5_5 = var4_4;
            if (var4_4 == null) {
                if (this.stackLogger.isLoggingEnabled(16) == false) return this.sipMessageFactory.newSIPServerResponse((SIPResponse)var1_1, var2_2);
                var2_2.logResponse((SIPResponse)var1_1, System.currentTimeMillis(), "before processing");
                return this.sipMessageFactory.newSIPServerResponse((SIPResponse)var1_1, var2_2);
            }
        }
        var6_6 = var5_5.acquireSem();
        if (this.stackLogger.isLoggingEnabled(16)) {
            var5_5.logResponse((SIPResponse)var1_1, System.currentTimeMillis(), "before processing");
        }
        if (!var6_6) ** GOTO lbl33
        if ((var1_1 = this.sipMessageFactory.newSIPServerResponse((SIPResponse)var1_1, var5_5)) != null) {
            var5_5.setResponseInterface((ServerResponseInterface)var1_1);
        } else {
            if (this.stackLogger.isLoggingEnabled()) {
                this.stackLogger.logDebug("returning null - serverResponseInterface is null!");
            }
            var5_5.releaseSem();
            return null;
lbl33: // 1 sources:
            if (this.stackLogger.isLoggingEnabled()) {
                this.stackLogger.logDebug("Could not aquire semaphore !!");
            }
        }
        if (var6_6 == false) return null;
        return var5_5;
    }

    public SocketAddress obtainLocalAddress(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        return this.ioHandler.obtainLocalAddress(inetAddress, n, inetAddress2, n2);
    }

    public void printDialogTable() {
        if (this.isLoggingEnabled()) {
            Object object = this.getStackLogger();
            Appendable appendable = new StringBuilder();
            ((StringBuilder)appendable).append("dialog table  = ");
            ((StringBuilder)appendable).append(this.dialogTable);
            object.logDebug(((StringBuilder)appendable).toString());
            appendable = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("dialog table = ");
            ((StringBuilder)object).append(this.dialogTable);
            ((PrintStream)appendable).println(((StringBuilder)object).toString());
        }
    }

    public void putDialog(SIPDialog serializable) {
        String string = ((SIPDialog)serializable).getDialogId();
        if (this.dialogTable.containsKey(string)) {
            if (this.stackLogger.isLoggingEnabled()) {
                StackLogger stackLogger = this.stackLogger;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("putDialog: dialog already exists");
                ((StringBuilder)serializable).append(string);
                ((StringBuilder)serializable).append(" in table = ");
                ((StringBuilder)serializable).append(this.dialogTable.get(string));
                stackLogger.logDebug(((StringBuilder)serializable).toString());
            }
            return;
        }
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putDialog dialogId=");
            stringBuilder.append(string);
            stringBuilder.append(" dialog = ");
            stringBuilder.append(serializable);
            stackLogger.logDebug(stringBuilder.toString());
        }
        ((SIPDialog)serializable).setStack(this);
        if (this.stackLogger.isLoggingEnabled()) {
            this.stackLogger.logStackTrace();
        }
        this.dialogTable.put(string, (SIPDialog)serializable);
    }

    public void putInMergeTable(SIPServerTransaction sIPServerTransaction, SIPRequest object) {
        if ((object = ((SIPRequest)object).getMergeId()) != null) {
            this.mergeTable.put((String)object, sIPServerTransaction);
        }
    }

    public void putPendingTransaction(SIPServerTransaction sIPServerTransaction) {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putPendingTransaction: ");
            stringBuilder.append(sIPServerTransaction);
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.pendingTransactions.put(sIPServerTransaction.getTransactionId(), sIPServerTransaction);
    }

    protected void reInit() {
        if (this.stackLogger.isLoggingEnabled()) {
            this.stackLogger.logDebug("Re-initializing !");
        }
        this.messageProcessors = new ArrayList<MessageProcessor>();
        this.ioHandler = new IOHandler(this);
        this.pendingTransactions = new ConcurrentHashMap();
        this.clientTransactionTable = new ConcurrentHashMap();
        this.serverTransactionTable = new ConcurrentHashMap();
        this.retransmissionAlertTransactions = new ConcurrentHashMap();
        this.mergeTable = new ConcurrentHashMap();
        this.dialogTable = new ConcurrentHashMap();
        this.earlyDialogTable = new ConcurrentHashMap();
        this.terminatedServerTransactionsPendingAck = new ConcurrentHashMap();
        this.forkedClientTransactionTable = new ConcurrentHashMap();
        this.timer = new Timer();
        this.activeClientTransactionCount = new AtomicInteger(0);
    }

    public void removeDialog(SIPDialog sIPDialog) {
        block3 : {
            Object object;
            block2 : {
                object = sIPDialog.getDialogId();
                String string = sIPDialog.getEarlyDialogId();
                if (string != null) {
                    this.earlyDialogTable.remove(string);
                    this.dialogTable.remove(string);
                }
                if (object == null) break block2;
                if (this.dialogTable.get(object) == sIPDialog) {
                    this.dialogTable.remove(object);
                }
                if (sIPDialog.testAndSetIsDialogTerminatedEventDelivered()) break block3;
                object = new DialogTerminatedEvent(sIPDialog.getSipProvider(), sIPDialog);
                sIPDialog.getSipProvider().handleEvent((EventObject)object, null);
                break block3;
            }
            if (!this.isDialogTerminatedEventDeliveredForNullDialog || sIPDialog.testAndSetIsDialogTerminatedEventDelivered()) break block3;
            object = new DialogTerminatedEvent(sIPDialog.getSipProvider(), sIPDialog);
            sIPDialog.getSipProvider().handleEvent((EventObject)object, null);
        }
    }

    public void removeDialog(String string) {
        if (this.stackLogger.isLoggingEnabled()) {
            this.stackLogger.logWarning("Silently removing dialog from table");
        }
        this.dialogTable.remove(string);
    }

    public void removeFromMergeTable(SIPServerTransaction object) {
        if (this.stackLogger.isLoggingEnabled()) {
            this.stackLogger.logDebug("Removing tx from merge table ");
        }
        if ((object = ((SIPRequest)((SIPTransaction)object).getRequest()).getMergeId()) != null) {
            this.mergeTable.remove(object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void removeMessageProcessor(MessageProcessor messageProcessor) {
        Collection<MessageProcessor> collection = this.messageProcessors;
        synchronized (collection) {
            if (this.messageProcessors.remove(messageProcessor)) {
                messageProcessor.stop();
            }
            return;
        }
    }

    public void removePendingTransaction(SIPServerTransaction sIPServerTransaction) {
        if (this.stackLogger.isLoggingEnabled()) {
            StackLogger stackLogger = this.stackLogger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removePendingTx: ");
            stringBuilder.append(sIPServerTransaction.getTransactionId());
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.pendingTransactions.remove(sIPServerTransaction.getTransactionId());
    }

    public void removeTransaction(SIPTransaction sIPTransaction) {
        Object object;
        Object object2;
        if (this.stackLogger.isLoggingEnabled()) {
            object = this.stackLogger;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Removing Transaction = ");
            ((StringBuilder)object2).append(sIPTransaction.getTransactionId());
            ((StringBuilder)object2).append(" transaction = ");
            ((StringBuilder)object2).append(sIPTransaction);
            object.logDebug(((StringBuilder)object2).toString());
        }
        if (sIPTransaction instanceof SIPServerTransaction) {
            if (this.stackLogger.isLoggingEnabled()) {
                this.stackLogger.logStackTrace();
            }
            object2 = sIPTransaction.getTransactionId();
            object2 = this.serverTransactionTable.remove(object2);
            object = sIPTransaction.getMethod();
            this.removePendingTransaction((SIPServerTransaction)sIPTransaction);
            this.removeTransactionPendingAck((SIPServerTransaction)sIPTransaction);
            if (((String)object).equalsIgnoreCase("INVITE")) {
                this.removeFromMergeTable((SIPServerTransaction)sIPTransaction);
            }
            object = sIPTransaction.getSipProvider();
            if (object2 != null && sIPTransaction.testAndSetTransactionTerminatedEvent()) {
                ((SipProviderImpl)object).handleEvent(new TransactionTerminatedEvent(object, (ServerTransaction)((Object)sIPTransaction)), sIPTransaction);
            }
        } else {
            String string = sIPTransaction.getTransactionId();
            object2 = this.clientTransactionTable.remove(string);
            if (this.stackLogger.isLoggingEnabled()) {
                object = this.stackLogger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("REMOVED client tx ");
                stringBuilder.append(object2);
                stringBuilder.append(" KEY = ");
                stringBuilder.append(string);
                object.logDebug(stringBuilder.toString());
                if (object2 != null && ((SIPTransaction)(object = (SIPClientTransaction)object2)).getMethod().equals("INVITE") && this.maxForkTime != 0) {
                    object = new RemoveForkedTransactionTimerTask((SIPClientTransaction)object);
                    this.timer.schedule((TimerTask)object, this.maxForkTime * 1000);
                }
            }
            if (object2 != null && sIPTransaction.testAndSetTransactionTerminatedEvent()) {
                object2 = sIPTransaction.getSipProvider();
                ((SipProviderImpl)object2).handleEvent(new TransactionTerminatedEvent(object2, (ClientTransaction)((Object)sIPTransaction)), sIPTransaction);
            }
        }
    }

    protected void removeTransactionHash(SIPTransaction object) {
        block4 : {
            block3 : {
                if (((SIPTransaction)object).getOriginalRequest() == null) {
                    return;
                }
                if (!(object instanceof SIPClientTransaction)) break block3;
                String string = ((SIPTransaction)object).getTransactionId();
                if (this.stackLogger.isLoggingEnabled()) {
                    this.stackLogger.logStackTrace();
                    object = this.stackLogger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("removing client Tx : ");
                    stringBuilder.append(string);
                    object.logDebug(stringBuilder.toString());
                }
                this.clientTransactionTable.remove(string);
                break block4;
            }
            if (!(object instanceof SIPServerTransaction)) break block4;
            String string = ((SIPTransaction)object).getTransactionId();
            this.serverTransactionTable.remove(string);
            if (this.stackLogger.isLoggingEnabled()) {
                StackLogger stackLogger = this.stackLogger;
                object = new StringBuilder();
                ((StringBuilder)object).append("removing server Tx : ");
                ((StringBuilder)object).append(string);
                stackLogger.logDebug(((StringBuilder)object).toString());
            }
        }
    }

    public boolean removeTransactionPendingAck(SIPServerTransaction object) {
        if ((object = ((SIPRequest)((SIPTransaction)object).getRequest()).getTopmostVia().getBranch()) != null && this.terminatedServerTransactionsPendingAck.containsKey(object)) {
            this.terminatedServerTransactionsPendingAck.remove(object);
            return true;
        }
        return false;
    }

    public void setAddressResolver(AddressResolver addressResolver) {
        this.addressResolver = addressResolver;
    }

    public void setDeliverDialogTerminatedEventForNullDialog() {
        this.isDialogTerminatedEventDeliveredForNullDialog = true;
    }

    protected void setHostAddress(String string) throws UnknownHostException {
        if (string.indexOf(58) != string.lastIndexOf(58) && string.trim().charAt(0) != '[') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(string);
            stringBuilder.append(']');
            this.stackAddress = stringBuilder.toString();
        } else {
            this.stackAddress = string;
        }
        this.stackInetAddress = InetAddress.getByName(string);
    }

    public void setLogRecordFactory(LogRecordFactory logRecordFactory) {
        this.logRecordFactory = logRecordFactory;
    }

    public void setLogStackTraceOnMessageSend(boolean bl) {
        this.logStackTraceOnMessageSend = bl;
    }

    public void setMaxConnections(int n) {
        this.maxConnections = n;
    }

    protected void setMessageFactory(StackMessageFactory stackMessageFactory) {
        this.sipMessageFactory = stackMessageFactory;
    }

    public void setNon2XXAckPassedToListener(boolean bl) {
        this.non2XXAckPassedToListener = bl;
    }

    public void setReceiveUdpBufferSize(int n) {
        this.receiveUdpBufferSize = n;
    }

    protected void setRouter(Router router) {
        this.router = router;
    }

    public void setSendUdpBufferSize(int n) {
        this.sendUdpBufferSize = n;
    }

    public void setSingleThreaded() {
        this.threadPoolSize = 1;
    }

    public void setStackLogger(StackLogger stackLogger) {
        this.stackLogger = stackLogger;
    }

    public void setStackName(String string) {
        this.stackName = string;
    }

    public void setThreadPoolSize(int n) {
        this.threadPoolSize = n;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopStack() {
        Object object = this.timer;
        if (object != null) {
            ((Timer)object).cancel();
        }
        this.timer = null;
        this.pendingTransactions.clear();
        this.toExit = true;
        synchronized (this) {
            this.notifyAll();
        }
        object = this.clientTransactionTable;
        synchronized (object) {
            this.clientTransactionTable.notifyAll();
        }
        object = this.messageProcessors;
        synchronized (object) {
            MessageProcessor[] arrmessageProcessor = this.getMessageProcessors();
            for (int i = 0; i < arrmessageProcessor.length; ++i) {
                this.removeMessageProcessor(arrmessageProcessor[i]);
            }
            this.ioHandler.closeAll();
        }
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        this.clientTransactionTable.clear();
        this.serverTransactionTable.clear();
        this.dialogTable.clear();
        this.serverLogger.closeLogFile();
    }

    @Override
    public void transactionErrorEvent(SIPTransactionErrorEvent sIPTransactionErrorEvent) {
        synchronized (this) {
            SIPTransaction sIPTransaction = (SIPTransaction)sIPTransactionErrorEvent.getSource();
            if (sIPTransactionErrorEvent.getErrorID() == 2) {
                sIPTransaction.setState(SIPTransaction.TERMINATED_STATE);
                if (sIPTransaction instanceof SIPServerTransaction) {
                    ((SIPServerTransaction)sIPTransaction).collectionTime = 0;
                }
                sIPTransaction.disableTimeoutTimer();
                sIPTransaction.disableRetransmissionTimer();
            }
            return;
        }
    }

    class PingTimer
    extends SIPStackTimerTask {
        ThreadAuditor.ThreadHandle threadHandle;

        public PingTimer(ThreadAuditor.ThreadHandle threadHandle) {
            this.threadHandle = threadHandle;
        }

        @Override
        protected void runTask() {
            if (SIPTransactionStack.this.getTimer() != null) {
                if (this.threadHandle == null) {
                    this.threadHandle = SIPTransactionStack.this.getThreadAuditor().addCurrentThread();
                }
                this.threadHandle.ping();
                SIPTransactionStack.this.getTimer().schedule((TimerTask)new PingTimer(this.threadHandle), this.threadHandle.getPingIntervalInMillisecs());
            }
        }
    }

    class RemoveForkedTransactionTimerTask
    extends SIPStackTimerTask {
        private SIPClientTransaction clientTransaction;

        public RemoveForkedTransactionTimerTask(SIPClientTransaction sIPClientTransaction) {
            this.clientTransaction = sIPClientTransaction;
        }

        @Override
        protected void runTask() {
            SIPTransactionStack.this.forkedClientTransactionTable.remove(this.clientTransaction.getTransactionId());
        }
    }

}

