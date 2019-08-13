/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.TransactionExt;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.HandshakeCompletedListenerImpl;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import gov.nist.javax.sip.stack.SIPTransactionErrorEvent;
import gov.nist.javax.sip.stack.SIPTransactionEventListener;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.TLSMessageChannel;
import java.io.IOException;
import java.net.InetAddress;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.sip.Dialog;
import javax.sip.IOExceptionEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipProvider;
import javax.sip.Transaction;
import javax.sip.TransactionState;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public abstract class SIPTransaction
extends MessageChannel
implements Transaction,
TransactionExt {
    public static final TransactionState CALLING_STATE;
    public static final TransactionState COMPLETED_STATE;
    public static final TransactionState CONFIRMED_STATE;
    public static final TransactionState INITIAL_STATE;
    protected static final int MAXIMUM_RETRANSMISSION_TICK_COUNT = 8;
    public static final TransactionState PROCEEDING_STATE;
    protected static final int T1 = 1;
    public static final TransactionState TERMINATED_STATE;
    protected static final int TIMER_A = 1;
    protected static final int TIMER_B = 64;
    protected static final int TIMER_F = 64;
    protected static final int TIMER_H = 64;
    protected static final int TIMER_J = 64;
    public static final TransactionState TRYING_STATE;
    protected int BASE_TIMER_INTERVAL;
    protected int T2;
    protected int T4;
    protected int TIMER_D;
    protected int TIMER_I;
    protected int TIMER_K;
    protected transient Object applicationData;
    public long auditTag;
    private String branch;
    private long cSeq;
    protected CallID callId;
    protected int collectionTime;
    private TransactionState currentState;
    private transient MessageChannel encapsulatedChannel;
    protected Event event;
    private transient Set<SIPTransactionEventListener> eventListeners;
    protected From from;
    protected String fromTag;
    protected boolean isMapped;
    protected boolean isSemaphoreAquired;
    protected SIPResponse lastResponse;
    private String method;
    protected SIPRequest originalRequest;
    protected String peerAddress;
    protected InetAddress peerInetAddress;
    protected InetAddress peerPacketSourceAddress;
    protected int peerPacketSourcePort;
    protected int peerPort;
    protected String peerProtocol;
    private transient int retransmissionTimerLastTickCount;
    private transient int retransmissionTimerTicksLeft;
    private Semaphore semaphore;
    protected transient SIPTransactionStack sipStack;
    private boolean terminatedEventDelivered;
    protected int timeoutTimerTicksLeft;
    protected To to;
    protected boolean toListener;
    protected String toTag;
    protected String transactionId;
    protected AtomicBoolean transactionTimerStarted;

    static {
        INITIAL_STATE = null;
        TRYING_STATE = TransactionState.TRYING;
        CALLING_STATE = TransactionState.CALLING;
        PROCEEDING_STATE = TransactionState.PROCEEDING;
        COMPLETED_STATE = TransactionState.COMPLETED;
        CONFIRMED_STATE = TransactionState.CONFIRMED;
        TERMINATED_STATE = TransactionState.TERMINATED;
    }

    protected SIPTransaction(SIPTransactionStack sIPTransactionStack, MessageChannel object) {
        int n;
        int n2 = this.BASE_TIMER_INTERVAL = 500;
        this.T4 = 5000 / n2;
        this.T2 = 4000 / n2;
        this.TIMER_I = n = this.T4;
        this.TIMER_K = n;
        this.TIMER_D = 32000 / n2;
        this.auditTag = 0L;
        this.transactionTimerStarted = new AtomicBoolean(false);
        this.sipStack = sIPTransactionStack;
        this.semaphore = new Semaphore(1, true);
        this.encapsulatedChannel = object;
        this.peerPort = ((MessageChannel)object).getPeerPort();
        this.peerAddress = ((MessageChannel)object).getPeerAddress();
        this.peerInetAddress = ((MessageChannel)object).getPeerInetAddress();
        this.peerPacketSourcePort = ((MessageChannel)object).getPeerPacketSourcePort();
        this.peerPacketSourceAddress = ((MessageChannel)object).getPeerPacketSourceAddress();
        this.peerProtocol = ((MessageChannel)object).getPeerProtocol();
        if (this.isReliable()) {
            object = this.encapsulatedChannel;
            ++((MessageChannel)object).useCount;
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("use count for encapsulated channel");
                stringBuilder.append(this);
                stringBuilder.append(" ");
                stringBuilder.append(this.encapsulatedChannel.useCount);
                object.logDebug(stringBuilder.toString());
            }
        }
        this.currentState = null;
        this.disableRetransmissionTimer();
        this.disableTimeoutTimer();
        this.eventListeners = Collections.synchronizedSet(new HashSet());
        this.addEventListener(sIPTransactionStack);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean acquireSem() {
        Throwable throwable2222;
        Object object2;
        Object object;
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = bl = false;
        boolean bl4 = bl2;
        if (this.sipStack.getStackLogger().isLoggingEnabled()) {
            bl3 = bl;
            bl4 = bl2;
            object2 = this.sipStack.getStackLogger();
            bl3 = bl;
            bl4 = bl2;
            bl3 = bl;
            bl4 = bl2;
            object = new StringBuilder();
            bl3 = bl;
            bl4 = bl2;
            ((StringBuilder)object).append("acquireSem [[[[");
            bl3 = bl;
            bl4 = bl2;
            ((StringBuilder)object).append(this);
            bl3 = bl;
            bl4 = bl2;
            object2.logDebug(((StringBuilder)object).toString());
            bl3 = bl;
            bl4 = bl2;
            this.sipStack.getStackLogger().logStackTrace();
        }
        bl3 = bl;
        bl4 = bl2;
        bl3 = bl = this.semaphore.tryAcquire(1000L, TimeUnit.MILLISECONDS);
        bl4 = bl;
        if (this.sipStack.isLoggingEnabled()) {
            bl3 = bl;
            bl4 = bl;
            object = this.sipStack.getStackLogger();
            bl3 = bl;
            bl4 = bl;
            bl3 = bl;
            bl4 = bl;
            object2 = new StringBuilder();
            bl3 = bl;
            bl4 = bl;
            ((StringBuilder)object2).append("acquireSem() returning : ");
            bl3 = bl;
            bl4 = bl;
            ((StringBuilder)object2).append(bl);
            bl3 = bl;
            bl4 = bl;
            object.logDebug(((StringBuilder)object2).toString());
        }
        this.isSemaphoreAquired = bl;
        return bl;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            bl3 = bl4;
            {
                this.sipStack.getStackLogger().logError("Unexpected exception acquiring sem", exception);
                bl3 = bl4;
                InternalErrorHandler.handleException(exception);
                this.isSemaphoreAquired = bl4;
                return false;
            }
        }
        this.isSemaphoreAquired = bl3;
        throw throwable2222;
    }

    public void addEventListener(SIPTransactionEventListener sIPTransactionEventListener) {
        this.eventListeners.add(sIPTransactionEventListener);
    }

    @Override
    public void close() {
        this.encapsulatedChannel.close();
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Closing ");
            stringBuilder.append(this.encapsulatedChannel);
            stackLogger.logDebug(stringBuilder.toString());
        }
    }

    protected final void disableRetransmissionTimer() {
        this.retransmissionTimerTicksLeft = -1;
    }

    protected final void disableTimeoutTimer() {
        this.timeoutTimerTicksLeft = -1;
    }

    public boolean doesCancelMatchTransaction(SIPRequest sIPRequest) {
        boolean bl = false;
        if (this.getOriginalRequest() != null && !this.getOriginalRequest().getMethod().equals("CANCEL")) {
            Object object = sIPRequest.getViaHeaders();
            boolean bl2 = bl;
            if (object != null) {
                Via via = (Via)((SIPHeaderList)object).getFirst();
                CharSequence charSequence = via.getBranch();
                object = charSequence;
                if (charSequence != null) {
                    object = charSequence;
                    if (!((String)charSequence).toLowerCase().startsWith("z9hg4bk")) {
                        object = null;
                    }
                }
                if (object != null && this.getBranch() != null) {
                    bl2 = bl;
                    if (this.getBranch().equalsIgnoreCase((String)object)) {
                        bl2 = bl;
                        if (via.getSentBy().equals(((Via)this.getOriginalRequest().getViaHeaders().getFirst()).getSentBy())) {
                            bl2 = bl = true;
                            if (this.sipStack.isLoggingEnabled()) {
                                this.sipStack.getStackLogger().logDebug("returning  true");
                                bl2 = bl;
                            }
                        }
                    }
                } else {
                    if (this.sipStack.isLoggingEnabled()) {
                        object = this.sipStack.getStackLogger();
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("testing against ");
                        ((StringBuilder)charSequence).append(this.getOriginalRequest());
                        object.logDebug(((StringBuilder)charSequence).toString());
                    }
                    bl2 = bl;
                    if (this.getOriginalRequest().getRequestURI().equals(sIPRequest.getRequestURI())) {
                        bl2 = bl;
                        if (this.getOriginalRequest().getTo().equals(sIPRequest.getTo())) {
                            bl2 = bl;
                            if (this.getOriginalRequest().getFrom().equals(sIPRequest.getFrom())) {
                                bl2 = bl;
                                if (this.getOriginalRequest().getCallId().getCallId().equals(sIPRequest.getCallId().getCallId())) {
                                    bl2 = bl;
                                    if (this.getOriginalRequest().getCSeq().getSeqNumber() == sIPRequest.getCSeq().getSeqNumber()) {
                                        bl2 = bl;
                                        if (via.equals(this.getOriginalRequest().getViaHeaders().getFirst())) {
                                            bl2 = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (bl2) {
                this.setPassToListener();
            }
            return bl2;
        }
        return false;
    }

    protected final void enableRetransmissionTimer() {
        this.enableRetransmissionTimer(1);
    }

    protected final void enableRetransmissionTimer(int n) {
        this.retransmissionTimerTicksLeft = this.isInviteTransaction() && this instanceof SIPClientTransaction ? n : Math.min(n, 8);
        this.retransmissionTimerLastTickCount = this.retransmissionTimerTicksLeft;
    }

    protected final void enableTimeoutTimer(int n) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("enableTimeoutTimer ");
            stringBuilder.append(this);
            stringBuilder.append(" tickCount ");
            stringBuilder.append(n);
            stringBuilder.append(" currentTickCount = ");
            stringBuilder.append(this.timeoutTimerTicksLeft);
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.timeoutTimerTicksLeft = n;
    }

    protected abstract void fireRetransmissionTimer();

    protected abstract void fireTimeoutTimer();

    final void fireTimer() {
        int n = this.timeoutTimerTicksLeft;
        if (n != -1) {
            this.timeoutTimerTicksLeft = --n;
            if (n == 0) {
                this.fireTimeoutTimer();
            }
        }
        if ((n = this.retransmissionTimerTicksLeft) != -1) {
            this.retransmissionTimerTicksLeft = --n;
            if (n == 0) {
                this.enableRetransmissionTimer(this.retransmissionTimerLastTickCount * 2);
                this.fireRetransmissionTimer();
            }
        }
    }

    @Override
    public Object getApplicationData() {
        return this.applicationData;
    }

    public final String getBranch() {
        if (this.branch == null) {
            this.branch = this.getOriginalRequest().getTopmostVia().getBranch();
        }
        return this.branch;
    }

    @Override
    public String getBranchId() {
        return this.branch;
    }

    public final long getCSeq() {
        return this.cSeq;
    }

    @Override
    public String getCipherSuite() throws UnsupportedOperationException {
        if (this.getMessageChannel() instanceof TLSMessageChannel) {
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener() == null) {
                return null;
            }
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent() == null) {
                return null;
            }
            return ((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent().getCipherSuite();
        }
        throw new UnsupportedOperationException("Not a TLS channel");
    }

    @Override
    public abstract Dialog getDialog();

    @Override
    public String getHost() {
        return this.encapsulatedChannel.getHost();
    }

    @Override
    public String getKey() {
        return this.encapsulatedChannel.getKey();
    }

    public SIPResponse getLastResponse() {
        return this.lastResponse;
    }

    @Override
    public Certificate[] getLocalCertificates() throws UnsupportedOperationException {
        if (this.getMessageChannel() instanceof TLSMessageChannel) {
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener() == null) {
                return null;
            }
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent() == null) {
                return null;
            }
            return ((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent().getLocalCertificates();
        }
        throw new UnsupportedOperationException("Not a TLS channel");
    }

    public MessageChannel getMessageChannel() {
        return this.encapsulatedChannel;
    }

    @Override
    public MessageProcessor getMessageProcessor() {
        return this.encapsulatedChannel.getMessageProcessor();
    }

    public final String getMethod() {
        return this.method;
    }

    public SIPRequest getOriginalRequest() {
        return this.originalRequest;
    }

    @Override
    public String getPeerAddress() {
        return this.peerAddress;
    }

    @Override
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        if (this.getMessageChannel() instanceof TLSMessageChannel) {
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener() == null) {
                return null;
            }
            if (((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent() == null) {
                return null;
            }
            return ((TLSMessageChannel)this.getMessageChannel()).getHandshakeCompletedListener().getHandshakeCompletedEvent().getPeerCertificates();
        }
        throw new UnsupportedOperationException("Not a TLS channel");
    }

    @Override
    protected InetAddress getPeerInetAddress() {
        return this.peerInetAddress;
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
    protected String getPeerProtocol() {
        return this.peerProtocol;
    }

    @Override
    public int getPort() {
        return this.encapsulatedChannel.getPort();
    }

    @Override
    public Request getRequest() {
        return this.originalRequest;
    }

    public Response getResponse() {
        return this.lastResponse;
    }

    @Override
    public int getRetransmitTimer() {
        return 500;
    }

    @Override
    public SIPTransactionStack getSIPStack() {
        return this.sipStack;
    }

    @Override
    public SipProviderImpl getSipProvider() {
        return this.getMessageProcessor().getListeningPoint().getProvider();
    }

    @Override
    public TransactionState getState() {
        return this.currentState;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    @Override
    public String getTransport() {
        return this.encapsulatedChannel.getTransport();
    }

    @Override
    public Via getViaHeader() {
        Via via = super.getViaHeader();
        try {
            via.setBranch(this.branch);
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return via;
    }

    @Override
    public String getViaHost() {
        return this.getViaHeader().getHost();
    }

    @Override
    public int getViaPort() {
        return this.getViaHeader().getPort();
    }

    public int hashCode() {
        String string = this.transactionId;
        if (string == null) {
            return -1;
        }
        return string.hashCode();
    }

    public final boolean isByeTransaction() {
        return this.getMethod().equals("BYE");
    }

    public final boolean isCancelTransaction() {
        return this.getMethod().equals("CANCEL");
    }

    public final boolean isInviteTransaction() {
        return this.getMethod().equals("INVITE");
    }

    public abstract boolean isMessagePartOfTransaction(SIPMessage var1);

    @Override
    public boolean isReliable() {
        return this.encapsulatedChannel.isReliable();
    }

    @Override
    public boolean isSecure() {
        return this.encapsulatedChannel.isSecure();
    }

    protected boolean isServerTransaction() {
        return this instanceof SIPServerTransaction;
    }

    public final boolean isTerminated() {
        boolean bl = this.getState() == TERMINATED_STATE;
        return bl;
    }

    public boolean passToListener() {
        return this.toListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void raiseErrorEvent(int n) {
        SIPTransactionErrorEvent sIPTransactionErrorEvent = new SIPTransactionErrorEvent(this, n);
        Set<SIPTransactionEventListener> set = this.eventListeners;
        // MONITORENTER : set
        Iterator<SIPTransactionEventListener> iterator = this.eventListeners.iterator();
        do {
            if (!iterator.hasNext()) {
                // MONITOREXIT : set
                if (n == 3) return;
                this.eventListeners.clear();
                this.setState(TransactionState.TERMINATED);
                if (!(this instanceof SIPServerTransaction)) return;
                if (!this.isByeTransaction()) return;
                if (this.getDialog() == null) return;
                ((SIPDialog)this.getDialog()).setState(SIPDialog.TERMINATED_STATE);
                return;
            }
            iterator.next().transactionErrorEvent(sIPTransactionErrorEvent);
        } while (true);
    }

    public void raiseIOExceptionEvent() {
        this.setState(TransactionState.TERMINATED);
        IOExceptionEvent iOExceptionEvent = new IOExceptionEvent(this, this.getPeerAddress(), this.getPeerPort(), this.getTransport());
        this.getSipProvider().handleEvent(iOExceptionEvent, this);
    }

    public void releaseSem() {
        try {
            this.toListener = false;
            this.semRelease();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Unexpected exception releasing sem", exception);
        }
    }

    public void removeEventListener(SIPTransactionEventListener sIPTransactionEventListener) {
        this.eventListeners.remove(sIPTransactionEventListener);
    }

    protected void semRelease() {
        try {
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("semRelease ]]]]");
                stringBuilder.append(this);
                stackLogger.logDebug(stringBuilder.toString());
                this.sipStack.getStackLogger().logStackTrace();
            }
            this.isSemaphoreAquired = false;
            this.semaphore.release();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Unexpected exception releasing sem", exception);
        }
    }

    @Override
    public void sendMessage(SIPMessage sIPMessage) throws IOException {
        try {
            this.encapsulatedChannel.sendMessage(sIPMessage, this.peerInetAddress, this.peerPort);
            return;
        }
        finally {
            this.startTransactionTimer();
        }
    }

    @Override
    protected void sendMessage(byte[] arrby, InetAddress inetAddress, int n, boolean bl) throws IOException {
        throw new IOException("Cannot send unparsed message through Transaction Channel!");
    }

    @Override
    public void setApplicationData(Object object) {
        this.applicationData = object;
    }

    public final void setBranch(String string) {
        this.branch = string;
    }

    public abstract void setDialog(SIPDialog var1, String var2);

    public void setEncapsulatedChannel(MessageChannel messageChannel) {
        this.encapsulatedChannel = messageChannel;
        this.peerInetAddress = messageChannel.getPeerInetAddress();
        this.peerPort = messageChannel.getPeerPort();
    }

    public void setOriginalRequest(SIPRequest object) {
        Object object2 = this.originalRequest;
        if (object2 != null && !((SIPMessage)object2).getTransactionId().equals(((SIPMessage)object).getTransactionId())) {
            this.sipStack.removeTransactionHash(this);
        }
        this.originalRequest = object;
        this.method = ((SIPRequest)object).getMethod();
        this.from = (From)((SIPMessage)object).getFrom();
        this.to = (To)((SIPMessage)object).getTo();
        this.toTag = this.to.getTag();
        this.fromTag = this.from.getTag();
        this.callId = (CallID)((SIPMessage)object).getCallId();
        this.cSeq = ((SIPMessage)object).getCSeq().getSeqNumber();
        this.event = (Event)((SIPMessage)object).getHeader("Event");
        this.transactionId = ((SIPMessage)object).getTransactionId();
        this.originalRequest.setTransaction(this);
        object2 = ((Via)((SIPMessage)object).getViaHeaders().getFirst()).getBranch();
        if (object2 != null) {
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Setting Branch id : ");
                stringBuilder.append((String)object2);
                object.logDebug(stringBuilder.toString());
            }
            this.setBranch((String)object2);
        } else {
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Branch id is null - compute TID!");
                stringBuilder.append(((SIPRequest)object).encode());
                object2.logDebug(stringBuilder.toString());
            }
            this.setBranch(((SIPMessage)object).getTransactionId());
        }
    }

    public void setPassToListener() {
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logDebug("setPassToListener()");
        }
        this.toListener = true;
    }

    @Override
    public void setRetransmitTimer(int n) {
        if (n > 0) {
            if (!this.transactionTimerStarted.get()) {
                int n2 = this.BASE_TIMER_INTERVAL = n;
                this.T4 = 5000 / n2;
                this.T2 = 4000 / n2;
                this.TIMER_I = n = this.T4;
                this.TIMER_K = n;
                this.TIMER_D = 32000 / n2;
                return;
            }
            throw new IllegalStateException("Transaction timer is already started");
        }
        throw new IllegalArgumentException("Retransmit timer must be positive!");
    }

    public void setState(TransactionState transactionState) {
        Object object = transactionState;
        if (this.currentState == TransactionState.COMPLETED) {
            object = transactionState;
            if (transactionState != TransactionState.TERMINATED) {
                object = transactionState;
                if (transactionState != TransactionState.CONFIRMED) {
                    object = TransactionState.COMPLETED;
                }
            }
        }
        transactionState = object;
        if (this.currentState == TransactionState.CONFIRMED) {
            transactionState = object;
            if (object != TransactionState.TERMINATED) {
                transactionState = TransactionState.CONFIRMED;
            }
        }
        if (this.currentState != TransactionState.TERMINATED) {
            this.currentState = transactionState;
        } else {
            transactionState = this.currentState;
        }
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Transaction:setState ");
            ((StringBuilder)object).append((Object)((Object)transactionState));
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" branchID = ");
            ((StringBuilder)object).append(this.getBranch());
            ((StringBuilder)object).append(" isClient = ");
            ((StringBuilder)object).append(this instanceof SIPClientTransaction);
            stackLogger.logDebug(((StringBuilder)object).toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
    }

    protected abstract void startTransactionTimer();

    protected boolean testAndSetTransactionTerminatedEvent() {
        synchronized (this) {
            boolean bl = !this.terminatedEventDelivered;
            this.terminatedEventDelivered = true;
            return bl;
        }
    }

    class LingerTimer
    extends SIPStackTimerTask {
        public LingerTimer() {
            if (SIPTransaction.this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = SIPTransaction.this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("LingerTimer : ");
                stringBuilder.append(SIPTransaction.this.getTransactionId());
                stackLogger.logDebug(stringBuilder.toString());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void runTask() {
            Object object;
            int n;
            Object object2;
            Object object3 = SIPTransaction.this;
            Object object4 = ((SIPTransaction)object3).getSIPStack();
            if (((SIPTransactionStack)object4).isLoggingEnabled()) {
                object2 = ((SIPTransactionStack)object4).getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("LingerTimer: run() : ");
                ((StringBuilder)object).append(SIPTransaction.this.getTransactionId());
                object2.logDebug(((StringBuilder)object).toString());
            }
            if (object3 instanceof SIPClientTransaction) {
                ((SIPTransactionStack)object4).removeTransaction((SIPTransaction)object3);
                ((SIPTransaction)object3).close();
                return;
            }
            if (!(object3 instanceof ServerTransaction)) return;
            if (((SIPTransactionStack)object4).isLoggingEnabled()) {
                object = ((SIPTransactionStack)object4).getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("removing");
                ((StringBuilder)object2).append(object3);
                object.logDebug(((StringBuilder)object2).toString());
            }
            ((SIPTransactionStack)object4).removeTransaction((SIPTransaction)object3);
            if (!((SIPTransactionStack)object4).cacheServerConnections) {
                object2 = ((SIPTransaction)object3).encapsulatedChannel;
                ((MessageChannel)object2).useCount = n = ((MessageChannel)object2).useCount - 1;
                if (n <= 0) {
                    ((SIPTransaction)object3).close();
                    return;
                }
            }
            if (!((SIPTransactionStack)object4).isLoggingEnabled()) return;
            if (((SIPTransactionStack)object4).cacheServerConnections) return;
            if (!((SIPTransaction)object3).isReliable()) return;
            n = SIPTransaction.access$000((SIPTransaction)object3).useCount;
            object3 = ((SIPTransactionStack)object4).getStackLogger();
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Use Count = ");
            ((StringBuilder)object4).append(n);
            object3.logDebug(((StringBuilder)object4).toString());
        }
    }

}

