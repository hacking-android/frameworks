/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  gov.nist.javax.sip.clientauthutils.AccountManager
 *  gov.nist.javax.sip.clientauthutils.UserCredentials
 *  gov.nist.javax.sip.header.ProxyAuthenticate
 *  gov.nist.javax.sip.header.StatusLine
 *  gov.nist.javax.sip.header.WWWAuthenticate
 *  gov.nist.javax.sip.header.extensions.ReferredByHeader
 *  gov.nist.javax.sip.header.extensions.ReplacesHeader
 *  gov.nist.javax.sip.message.SIPMessage
 *  gov.nist.javax.sip.message.SIPResponse
 *  javax.sip.ClientTransaction
 *  javax.sip.Dialog
 *  javax.sip.DialogState
 *  javax.sip.DialogTerminatedEvent
 *  javax.sip.IOExceptionEvent
 *  javax.sip.ListeningPoint
 *  javax.sip.ObjectInUseException
 *  javax.sip.RequestEvent
 *  javax.sip.ResponseEvent
 *  javax.sip.ServerTransaction
 *  javax.sip.SipException
 *  javax.sip.SipFactory
 *  javax.sip.SipListener
 *  javax.sip.SipProvider
 *  javax.sip.SipStack
 *  javax.sip.TimeoutEvent
 *  javax.sip.Transaction
 *  javax.sip.TransactionState
 *  javax.sip.TransactionTerminatedEvent
 *  javax.sip.address.Address
 *  javax.sip.address.SipURI
 *  javax.sip.address.URI
 *  javax.sip.header.CSeqHeader
 *  javax.sip.header.ContactHeader
 *  javax.sip.header.ExpiresHeader
 *  javax.sip.header.Header
 *  javax.sip.header.HeaderAddress
 *  javax.sip.header.ReferToHeader
 *  javax.sip.header.ViaHeader
 *  javax.sip.message.Message
 *  javax.sip.message.Request
 *  javax.sip.message.Response
 */
package com.android.server.sip;

import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;
import android.net.sip.SipSession;
import android.net.sip.SipSessionAdapter;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.server.sip.SipHelper;
import com.android.server.sip.SipSessionListenerProxy;
import com.android.server.sip.SipWakeLock;
import com.android.server.sip.SipWakeupTimer;
import gov.nist.javax.sip.clientauthutils.AccountManager;
import gov.nist.javax.sip.clientauthutils.UserCredentials;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.WWWAuthenticate;
import gov.nist.javax.sip.header.extensions.ReferredByHeader;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TimeoutEvent;
import javax.sip.Transaction;
import javax.sip.TransactionState;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;
import javax.sip.header.ReferToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Message;
import javax.sip.message.Request;
import javax.sip.message.Response;

class SipSessionGroup
implements SipListener {
    private static final String ANONYMOUS = "anonymous";
    private static final int CANCEL_CALL_TIMER = 3;
    private static final boolean DBG = false;
    private static final boolean DBG_PING = false;
    private static final EventObject DEREGISTER = new EventObject("Deregister");
    private static final EventObject END_CALL = new EventObject("End call");
    private static final int END_CALL_TIMER = 3;
    private static final int EXPIRY_TIME = 3600;
    private static final int INCALL_KEEPALIVE_INTERVAL = 10;
    private static final int KEEPALIVE_TIMEOUT = 5;
    private static final String TAG = "SipSession";
    private static final String THREAD_POOL_SIZE = "1";
    private static final long WAKE_LOCK_HOLDING_TIME = 500L;
    private SipSessionImpl mCallReceiverSession;
    private String mExternalIp;
    private int mExternalPort;
    private String mLocalIp;
    private final SipProfile mLocalProfile;
    private final String mPassword;
    private Map<String, SipSessionImpl> mSessionMap = new HashMap<String, SipSessionImpl>();
    private SipHelper mSipHelper;
    private SipStack mSipStack;
    private SipWakeLock mWakeLock;
    private SipWakeupTimer mWakeupTimer;

    public SipSessionGroup(SipProfile sipProfile, String string, SipWakeupTimer sipWakeupTimer, SipWakeLock sipWakeLock) throws SipException {
        this.mLocalProfile = sipProfile;
        this.mPassword = string;
        this.mWakeupTimer = sipWakeupTimer;
        this.mWakeLock = sipWakeLock;
        this.reset();
    }

    static /* synthetic */ void access$2100(SipSessionGroup sipSessionGroup, ResponseEvent responseEvent) {
        sipSessionGroup.extractExternalAddress(responseEvent);
    }

    private void addSipSession(SipSessionImpl object2) {
        synchronized (this) {
            this.removeSipSession((SipSessionImpl)object2);
            String string = ((SipSessionImpl)object2).getCallId();
            this.mSessionMap.put(string, (SipSessionImpl)object2);
            if (SipSessionGroup.isLoggable((SipSessionImpl)object2)) {
                for (String string2 : this.mSessionMap.keySet()) {
                }
            }
            return;
        }
    }

    private SipSessionImpl createNewSession(RequestEvent requestEvent, ISipSessionListener object, ServerTransaction serverTransaction, int n) throws SipException {
        object = new SipSessionImpl((ISipSessionListener)object);
        ((SipSessionImpl)object).mServerTransaction = serverTransaction;
        ((SipSessionImpl)object).mState = n;
        ((SipSessionImpl)object).mDialog = ((SipSessionImpl)object).mServerTransaction.getDialog();
        ((SipSessionImpl)object).mInviteReceived = requestEvent;
        ((SipSessionImpl)object).mPeerProfile = SipSessionGroup.createPeerProfile((HeaderAddress)requestEvent.getRequest().getHeader("From"));
        ((SipSessionImpl)object).mPeerSessionDescription = this.extractContent((Message)requestEvent.getRequest());
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static SipProfile createPeerProfile(HeaderAddress object) throws SipException {
        SipURI sipURI;
        Object object2;
        Address address;
        block5 : {
            address = object.getAddress();
            sipURI = (SipURI)address.getURI();
            object = object2 = sipURI.getUser();
            if (object2 != null) break block5;
            object = ANONYMOUS;
        }
        int n = sipURI.getPort();
        object2 = new SipProfile.Builder((String)object, sipURI.getHost());
        object = ((SipProfile.Builder)object2).setDisplayName(address.getDisplayName());
        if (n <= 0) return ((SipProfile.Builder)object).build();
        try {
            ((SipProfile.Builder)object).setPort(n);
            return ((SipProfile.Builder)object).build();
        }
        catch (ParseException parseException) {
            throw new SipException("createPeerProfile()", (Throwable)parseException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SipException("createPeerProfile()", (Throwable)illegalArgumentException);
        }
    }

    private static boolean expectResponse(String string, EventObject eventObject) {
        if (eventObject instanceof ResponseEvent) {
            return string.equalsIgnoreCase(SipSessionGroup.getCseqMethod((Message)((ResponseEvent)eventObject).getResponse()));
        }
        return false;
    }

    private String extractContent(Message object) {
        byte[] arrby = object.getRawContent();
        if (arrby != null) {
            try {
                if (object instanceof SIPMessage) {
                    return ((SIPMessage)object).getMessageContent();
                }
                object = new String(arrby, "UTF-8");
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return null;
    }

    private void extractExternalAddress(ResponseEvent object) {
        if ((object = (ViaHeader)object.getResponse().getHeader("Via")) == null) {
            return;
        }
        int n = object.getRPort();
        object = object.getReceived();
        if (n > 0 && object != null) {
            this.mExternalIp = object;
            this.mExternalPort = n;
        }
    }

    private static String getCseqMethod(Message message) {
        return ((CSeqHeader)message.getHeader("CSeq")).getMethod();
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable throwable2 = throwable.getCause();
        Throwable throwable3 = throwable;
        throwable = throwable2;
        while (throwable != null) {
            throwable3 = throwable;
            throwable = throwable3.getCause();
        }
        return throwable3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SipSessionImpl getSipSession(EventObject object) {
        synchronized (this) {
            object = SipHelper.getCallId((EventObject)object);
            object = this.mSessionMap.get(object);
            if (object != null && SipSessionGroup.isLoggable((SipSessionImpl)object)) {
                for (String string : this.mSessionMap.keySet()) {
                }
            }
            if (object == null) return this.mCallReceiverSession;
            return object;
        }
    }

    private String getStackName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("stack");
        stringBuilder.append(System.currentTimeMillis());
        return stringBuilder.toString();
    }

    private static boolean isLoggable(SipSessionImpl sipSessionImpl) {
        if (sipSessionImpl != null && sipSessionImpl.mState == 9) {
            return false;
        }
        return false;
    }

    private static boolean isLoggable(SipSessionImpl sipSessionImpl, EventObject eventObject) {
        if (!SipSessionGroup.isLoggable(sipSessionImpl)) {
            return false;
        }
        if (eventObject == null) {
            return false;
        }
        if (eventObject instanceof ResponseEvent) {
            if ("OPTIONS".equals((Object)((ResponseEvent)eventObject).getResponse().getHeader("CSeq"))) {
                return false;
            }
            return false;
        }
        if (eventObject instanceof RequestEvent) {
            if (SipSessionGroup.isRequestEvent("OPTIONS", eventObject)) {
                return false;
            }
            return false;
        }
        return false;
    }

    private static boolean isLoggable(EventObject eventObject) {
        return SipSessionGroup.isLoggable(null, eventObject);
    }

    private static boolean isRequestEvent(String string, EventObject eventObject) {
        try {
            if (eventObject instanceof RequestEvent) {
                boolean bl = string.equals(((RequestEvent)eventObject).getRequest().getMethod());
                return bl;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return false;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private static String logEvt(EventObject eventObject) {
        if (eventObject instanceof RequestEvent) {
            return ((RequestEvent)eventObject).getRequest().toString();
        }
        if (eventObject instanceof ResponseEvent) {
            return ((ResponseEvent)eventObject).getResponse().toString();
        }
        return eventObject.toString();
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void process(EventObject eventObject) {
        synchronized (this) {
            SipSessionImpl sipSessionImpl = this.getSipSession(eventObject);
            try {
                boolean bl = SipSessionGroup.isLoggable(sipSessionImpl, eventObject);
                boolean bl2 = sipSessionImpl != null && sipSessionImpl.process(eventObject);
                if (bl && bl2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("process: event new state after: ");
                    stringBuilder.append(SipSession.State.toString(sipSessionImpl.mState));
                    this.log(stringBuilder.toString());
                }
            }
            catch (Throwable throwable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("process: error event=");
                stringBuilder.append(eventObject);
                this.loge(stringBuilder.toString(), this.getRootCause(throwable));
                sipSessionImpl.onError(throwable);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeSipSession(SipSessionImpl iterator) {
        synchronized (this) {
            SipSessionImpl sipSessionImpl = this.mCallReceiverSession;
            if (iterator == sipSessionImpl) {
                return;
            }
            Object object2 = ((SipSessionImpl)((Object)iterator)).getCallId();
            SipSessionImpl sipSessionImpl2 = this.mSessionMap.remove(object2);
            if (sipSessionImpl2 != null && sipSessionImpl2 != iterator) {
                this.mSessionMap.put((String)object2, sipSessionImpl2);
                for (Object object2 : this.mSessionMap.entrySet()) {
                    if (object2.getValue() != sipSessionImpl2) continue;
                    object2 = (String)object2.getKey();
                    this.mSessionMap.remove(object2);
                }
            }
            if (sipSessionImpl2 != null && SipSessionGroup.isLoggable(sipSessionImpl2)) {
                for (String string : this.mSessionMap.keySet()) {
                }
            }
            return;
        }
    }

    public void close() {
        synchronized (this) {
            this.onConnectivityChanged();
            this.mSessionMap.clear();
            this.closeToNotReceiveCalls();
            if (this.mSipStack != null) {
                this.mSipStack.stop();
                this.mSipStack = null;
                this.mSipHelper = null;
            }
            this.resetExternalAddress();
            return;
        }
    }

    public void closeToNotReceiveCalls() {
        synchronized (this) {
            this.mCallReceiverSession = null;
            return;
        }
    }

    boolean containsSession(String string) {
        synchronized (this) {
            boolean bl = this.mSessionMap.containsKey(string);
            return bl;
        }
    }

    public ISipSession createSession(ISipSessionListener object) {
        object = this.isClosed() ? null : new SipSessionImpl((ISipSessionListener)object);
        return object;
    }

    public SipProfile getLocalProfile() {
        return this.mLocalProfile;
    }

    public String getLocalProfileUri() {
        return this.mLocalProfile.getUriString();
    }

    public boolean isClosed() {
        synchronized (this) {
            SipStack sipStack = this.mSipStack;
            boolean bl = sipStack == null;
            return bl;
        }
    }

    void onConnectivityChanged() {
        synchronized (this) {
            SipSessionImpl[] arrsipSessionImpl = this.mSessionMap.values().toArray(new SipSessionImpl[this.mSessionMap.size()]);
            int n = arrsipSessionImpl.length;
            for (int i = 0; i < n; ++i) {
                arrsipSessionImpl[i].onError(-10, "data connection lost");
            }
            return;
        }
    }

    public void openToReceiveCalls(ISipSessionListener iSipSessionListener) {
        synchronized (this) {
            if (this.mCallReceiverSession == null) {
                SipSessionCallReceiverImpl sipSessionCallReceiverImpl = new SipSessionCallReceiverImpl(iSipSessionListener);
                this.mCallReceiverSession = sipSessionCallReceiverImpl;
            } else {
                this.mCallReceiverSession.setListener(iSipSessionListener);
            }
            return;
        }
    }

    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        this.process((EventObject)dialogTerminatedEvent);
    }

    public void processIOException(IOExceptionEvent iOExceptionEvent) {
        this.process((EventObject)iOExceptionEvent);
    }

    public void processRequest(RequestEvent requestEvent) {
        if (SipSessionGroup.isRequestEvent("INVITE", (EventObject)requestEvent)) {
            this.mWakeLock.acquire(500L);
        }
        this.process((EventObject)requestEvent);
    }

    public void processResponse(ResponseEvent responseEvent) {
        this.process((EventObject)responseEvent);
    }

    public void processTimeout(TimeoutEvent timeoutEvent) {
        this.process((EventObject)timeoutEvent);
    }

    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        this.process((EventObject)transactionTerminatedEvent);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void reset() throws SipException {
        synchronized (this) {
            Object object;
            int n;
            Properties properties;
            Object object2;
            String string;
            block15 : {
                int n2;
                int n3;
                properties = new Properties();
                string = this.mLocalProfile.getProtocol();
                int n32 = this.mLocalProfile.getPort();
                object2 = this.mLocalProfile.getProxyAddress();
                if (!TextUtils.isEmpty((CharSequence)object2)) {
                    object = new StringBuilder();
                    object.append((String)object2);
                    object.append(':');
                    object.append(n32);
                    object.append('/');
                    object.append(string);
                    properties.setProperty("javax.sip.OUTBOUND_PROXY", object.toString());
                } else {
                    object2 = this.mLocalProfile.getSipDomain();
                }
                object = object2;
                if (((String)object2).startsWith("[")) {
                    object = object2;
                    if (((String)object2).endsWith("]")) {
                        object = ((String)object2).substring(1, ((String)object2).length() - 1);
                    }
                }
                Object var6_9 = null;
                Object var7_10 = null;
                n = n32;
                object2 = var6_9;
                try {
                    object = InetAddress.getAllByName((String)object);
                    n = n32;
                    object2 = var6_9;
                    n3 = ((CharSequence)object).length;
                    n2 = 0;
                }
                catch (Exception object3) {
                    // empty catch block
                    break block15;
                }
                do {
                    n = n32;
                    object2 = var7_10;
                    if (n2 >= n3) break;
                    CharSequence charSequence = object[n2];
                    n = n32;
                    object2 = var6_9;
                    n = n32;
                    object2 = var6_9;
                    DatagramSocket datagramSocket = new DatagramSocket();
                    n = n32;
                    object2 = var6_9;
                    datagramSocket.connect((InetAddress)((Object)charSequence), n32);
                    n = n32;
                    object2 = var6_9;
                    if (datagramSocket.isConnected()) {
                        n = n32;
                        object2 = var6_9;
                        object = datagramSocket.getLocalAddress().getHostAddress();
                        n = n32;
                        object2 = object;
                        n = n32 = datagramSocket.getLocalPort();
                        object2 = object;
                        datagramSocket.close();
                        n = n32;
                        object2 = object;
                        break;
                    }
                    n = n32;
                    object2 = var6_9;
                    datagramSocket.close();
                    ++n2;
                } while (true);
            }
            if (object2 == null) {
                return;
            }
            this.close();
            this.mLocalIp = object2;
            properties.setProperty("javax.sip.STACK_NAME", this.getStackName());
            properties.setProperty("gov.nist.javax.sip.THREAD_POOL_SIZE", THREAD_POOL_SIZE);
            this.mSipStack = SipFactory.getInstance().createSipStack(properties);
            try {
                object = this.mSipStack.createSipProvider(this.mSipStack.createListeningPoint((String)object2, n, string));
                object.addSipListener((SipListener)this);
                this.mSipHelper = object2 = new SipHelper(this.mSipStack, (SipProvider)object);
            }
            catch (Exception exception) {
                object = new SipException("failed to initialize SIP stack", (Throwable)exception);
                throw object;
            }
            catch (SipException sipException) {
                throw sipException;
            }
            this.mSipStack.start();
            return;
        }
    }

    void resetExternalAddress() {
        synchronized (this) {
            this.mExternalIp = null;
            this.mExternalPort = 0;
            return;
        }
    }

    void setWakeupTimer(SipWakeupTimer sipWakeupTimer) {
        this.mWakeupTimer = sipWakeupTimer;
    }

    static interface KeepAliveProcessCallback {
        public void onError(int var1, String var2);

        public void onResponse(boolean var1);
    }

    static class KeepAliveProcessCallbackProxy
    implements KeepAliveProcessCallback {
        private static final String KAPCP_TAG = "KeepAliveProcessCallbackProxy";
        private KeepAliveProcessCallback mCallback;

        KeepAliveProcessCallbackProxy(KeepAliveProcessCallback keepAliveProcessCallback) {
            this.mCallback = keepAliveProcessCallback;
        }

        private void loge(String string, Throwable throwable) {
            Rlog.e((String)KAPCP_TAG, (String)string, (Throwable)throwable);
        }

        private void proxy(Runnable runnable) {
            new Thread(runnable, "SIP-KeepAliveProcessCallbackThread").start();
        }

        @Override
        public void onError(final int n, final String string) {
            if (this.mCallback == null) {
                return;
            }
            this.proxy(new Runnable(){

                @Override
                public void run() {
                    try {
                        mCallback.onError(n, string);
                    }
                    catch (Throwable throwable) {
                        this.loge("onError", throwable);
                    }
                }
            });
        }

        @Override
        public void onResponse(final boolean bl) {
            if (this.mCallback == null) {
                return;
            }
            this.proxy(new Runnable(){

                @Override
                public void run() {
                    try {
                        mCallback.onResponse(bl);
                    }
                    catch (Throwable throwable) {
                        this.loge("onResponse", throwable);
                    }
                }
            });
        }

    }

    private class MakeCallCommand
    extends EventObject {
        private String mSessionDescription;
        private int mTimeout;

        public MakeCallCommand(SipProfile sipProfile, String string, int n) {
            super(sipProfile);
            this.mSessionDescription = string;
            this.mTimeout = n;
        }

        public SipProfile getPeerProfile() {
            return (SipProfile)this.getSource();
        }

        public String getSessionDescription() {
            return this.mSessionDescription;
        }

        public int getTimeout() {
            return this.mTimeout;
        }
    }

    private class RegisterCommand
    extends EventObject {
        private int mDuration;

        public RegisterCommand(int n) {
            super(SipSessionGroup.this);
            this.mDuration = n;
        }

        public int getDuration() {
            return this.mDuration;
        }
    }

    private class SipSessionCallReceiverImpl
    extends SipSessionImpl {
        private static final boolean SSCRI_DBG = true;
        private static final String SSCRI_TAG = "SipSessionCallReceiverImpl";

        public SipSessionCallReceiverImpl(ISipSessionListener iSipSessionListener) {
            super(iSipSessionListener);
        }

        private void log(String string) {
            Rlog.d((String)SSCRI_TAG, (String)string);
        }

        private int processInviteWithReplaces(RequestEvent requestEvent, ReplacesHeader replacesHeader) {
            Object object = replacesHeader.getCallId();
            object = (SipSessionImpl)SipSessionGroup.this.mSessionMap.get(object);
            if (object == null) {
                return 481;
            }
            object = ((SipSessionImpl)object).mDialog;
            if (object == null) {
                return 603;
            }
            if (object.getLocalTag().equals(replacesHeader.getToTag()) && object.getRemoteTag().equals(replacesHeader.getFromTag())) {
                if ((requestEvent = (ReferredByHeader)requestEvent.getRequest().getHeader("Referred-By")) != null && object.getRemoteParty().equals((Object)requestEvent.getAddress())) {
                    return 200;
                }
                return 481;
            }
            return 481;
        }

        private void processNewInviteRequest(RequestEvent object) throws SipException {
            ReplacesHeader replacesHeader = (ReplacesHeader)object.getRequest().getHeader("Replaces");
            SipSessionImpl sipSessionImpl = null;
            if (replacesHeader != null) {
                int n = this.processInviteWithReplaces((RequestEvent)object, replacesHeader);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("processNewInviteRequest: ");
                stringBuilder.append((Object)replacesHeader);
                stringBuilder.append(" response=");
                stringBuilder.append(n);
                this.log(stringBuilder.toString());
                if (n == 200) {
                    sipSessionImpl = (SipSessionImpl)SipSessionGroup.this.mSessionMap.get(replacesHeader.getCallId());
                    object = SipSessionGroup.this.createNewSession(object, sipSessionImpl.mProxy.getListener(), SipSessionGroup.this.mSipHelper.getServerTransaction((RequestEvent)object), 3);
                    object.mProxy.onCallTransferring((ISipSession)object, object.mPeerSessionDescription);
                } else {
                    SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)object, n);
                    object = sipSessionImpl;
                }
            } else {
                object = SipSessionGroup.this.createNewSession(object, this.mProxy, SipSessionGroup.this.mSipHelper.sendRinging((RequestEvent)object, this.generateTag()), 3);
                this.mProxy.onRinging((ISipSession)object, object.mPeerProfile, object.mPeerSessionDescription);
            }
            if (object != null) {
                SipSessionGroup.this.addSipSession((SipSessionImpl)object);
            }
        }

        @Override
        public boolean process(EventObject eventObject) throws SipException {
            if (SipSessionGroup.isLoggable(this, eventObject)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("process: ");
                stringBuilder.append(this);
                stringBuilder.append(": ");
                stringBuilder.append(SipSession.State.toString(this.mState));
                stringBuilder.append(": processing ");
                stringBuilder.append(SipSessionGroup.logEvt(eventObject));
                this.log(stringBuilder.toString());
            }
            if (SipSessionGroup.isRequestEvent("INVITE", eventObject)) {
                this.processNewInviteRequest((RequestEvent)eventObject);
                return true;
            }
            if (SipSessionGroup.isRequestEvent("OPTIONS", eventObject)) {
                SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 200);
                return true;
            }
            return false;
        }
    }

    class SipSessionImpl
    extends ISipSession.Stub {
        private static final boolean SSI_DBG = true;
        private static final String SSI_TAG = "SipSessionImpl";
        int mAuthenticationRetryCount;
        ClientTransaction mClientTransaction;
        Dialog mDialog;
        boolean mInCall;
        RequestEvent mInviteReceived;
        SipProfile mPeerProfile;
        String mPeerSessionDescription;
        SipSessionListenerProxy mProxy = new SipSessionListenerProxy();
        SipSessionImpl mReferSession;
        ReferredByHeader mReferredBy;
        String mReplaces;
        ServerTransaction mServerTransaction;
        SessionTimer mSessionTimer;
        private SipKeepAlive mSipKeepAlive;
        private SipSessionImpl mSipSessionImpl;
        int mState = 0;

        public SipSessionImpl(ISipSessionListener iSipSessionListener) {
            this.setListener(iSipSessionListener);
        }

        private void cancelSessionTimer() {
            SessionTimer sessionTimer = this.mSessionTimer;
            if (sessionTimer != null) {
                sessionTimer.cancel();
                this.mSessionTimer = null;
            }
        }

        private String createErrorMessage(Response response) {
            return String.format("%s (%d)", response.getReasonPhrase(), response.getStatusCode());
        }

        private boolean crossDomainAuthenticationRequired(Response object) {
            String string = this.getRealmFromResponse((Response)object);
            object = string;
            if (string == null) {
                object = "";
            }
            return SipSessionGroup.this.mLocalProfile.getSipDomain().trim().equals(((String)object).trim()) ^ true;
        }

        private void doCommandAsync(final EventObject eventObject) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        SipSessionImpl.this.processCommand(eventObject);
                    }
                    catch (Throwable throwable) {
                        SipSessionGroup sipSessionGroup = SipSessionGroup.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("command error: ");
                        stringBuilder.append(eventObject);
                        stringBuilder.append(": ");
                        stringBuilder.append(SipSessionGroup.this.mLocalProfile.getUriString());
                        sipSessionGroup.loge(stringBuilder.toString(), SipSessionGroup.this.getRootCause(throwable));
                        SipSessionImpl.this.onError(throwable);
                    }
                }
            }, "SipSessionAsyncCmdThread").start();
        }

        private void enableKeepAlive() {
            SipSessionImpl sipSessionImpl = this.mSipSessionImpl;
            if (sipSessionImpl != null) {
                sipSessionImpl.stopKeepAliveProcess();
            } else {
                this.mSipSessionImpl = this.duplicate();
            }
            try {
                this.mSipSessionImpl.startKeepAliveProcess(10, this.mPeerProfile, null);
            }
            catch (SipException sipException) {
                SipSessionGroup.this.loge("keepalive cannot be enabled; ignored", sipException);
                this.mSipSessionImpl.stopKeepAliveProcess();
            }
        }

        private void endCallNormally() {
            this.reset();
            this.mProxy.onCallEnded(this);
        }

        private void endCallOnBusy() {
            this.reset();
            this.mProxy.onCallBusy(this);
        }

        private void endCallOnError(int n, String string) {
            this.reset();
            this.mProxy.onError(this, n, string);
        }

        private boolean endingCall(EventObject eventObject) throws SipException {
            if (SipSessionGroup.expectResponse("BYE", eventObject)) {
                int n = (eventObject = (ResponseEvent)eventObject).getResponse().getStatusCode();
                if ((n == 401 || n == 407) && this.handleAuthentication((ResponseEvent)eventObject)) {
                    return true;
                }
                this.cancelSessionTimer();
                this.reset();
                return true;
            }
            return false;
        }

        private void establishCall(boolean bl) {
            this.mState = 8;
            this.cancelSessionTimer();
            if (!this.mInCall && bl) {
                this.enableKeepAlive();
            }
            this.mInCall = true;
            this.mProxy.onCallEstablished(this, this.mPeerSessionDescription);
        }

        private AccountManager getAccountManager() {
            return new AccountManager(){

                public UserCredentials getCredentials(ClientTransaction clientTransaction, String string) {
                    return new UserCredentials(){

                        public String getPassword() {
                            return SipSessionGroup.this.mPassword;
                        }

                        public String getSipDomain() {
                            return SipSessionGroup.this.mLocalProfile.getSipDomain();
                        }

                        public String getUserName() {
                            String string = SipSessionGroup.this.mLocalProfile.getAuthUserName();
                            if (TextUtils.isEmpty((CharSequence)string)) {
                                string = SipSessionGroup.this.mLocalProfile.getUserName();
                            }
                            return string;
                        }
                    };
                }

            };
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private int getErrorCode(int n) {
            if (n == 403 || n == 404 || n == 406) return -7;
            if (n == 408) return -5;
            if (n == 410) return -7;
            if (n == 414) return -6;
            if (n == 480 || n == 488) return -7;
            if (n == 484 || n == 485) return -6;
            if (n >= 500) return -2;
            return -4;
        }

        private int getErrorCode(Throwable throwable) {
            throwable.getMessage();
            if (throwable instanceof UnknownHostException) {
                return -12;
            }
            if (throwable instanceof IOException) {
                return -1;
            }
            return -4;
        }

        private int getExpiryTime(Response object) {
            int n;
            int n2;
            block7 : {
                ContactHeader contactHeader;
                block8 : {
                    n = -1;
                    contactHeader = (ContactHeader)object.getHeader("Contact");
                    if (contactHeader != null) {
                        n = contactHeader.getExpires();
                    }
                    contactHeader = (ExpiresHeader)object.getHeader("Expires");
                    n2 = n;
                    if (contactHeader == null) break block7;
                    if (n < 0) break block8;
                    n2 = n;
                    if (n <= contactHeader.getExpires()) break block7;
                }
                n2 = contactHeader.getExpires();
            }
            n = n2;
            if (n2 <= 0) {
                n = 3600;
            }
            object = (ExpiresHeader)object.getHeader("Min-Expires");
            n2 = n;
            if (object != null) {
                n2 = n;
                if (n < object.getExpires()) {
                    n2 = object.getExpires();
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Expiry time = ");
            ((StringBuilder)object).append(n2);
            this.log(((StringBuilder)object).toString());
            return n2;
        }

        private String getNonceFromResponse(Response object) {
            WWWAuthenticate wWWAuthenticate = (WWWAuthenticate)object.getHeader("WWW-Authenticate");
            if (wWWAuthenticate != null) {
                return wWWAuthenticate.getNonce();
            }
            object = (object = (ProxyAuthenticate)object.getHeader("Proxy-Authenticate")) == null ? null : object.getNonce();
            return object;
        }

        private String getRealmFromResponse(Response object) {
            WWWAuthenticate wWWAuthenticate = (WWWAuthenticate)object.getHeader("WWW-Authenticate");
            if (wWWAuthenticate != null) {
                return wWWAuthenticate.getRealm();
            }
            object = (object = (ProxyAuthenticate)object.getHeader("Proxy-Authenticate")) == null ? null : object.getRealm();
            return object;
        }

        private String getResponseString(int n) {
            StatusLine statusLine = new StatusLine();
            statusLine.setStatusCode(n);
            statusLine.setReasonPhrase(SIPResponse.getReasonPhrase((int)n));
            return statusLine.encode();
        }

        private Transaction getTransaction() {
            ClientTransaction clientTransaction = this.mClientTransaction;
            if (clientTransaction != null) {
                return clientTransaction;
            }
            clientTransaction = this.mServerTransaction;
            if (clientTransaction != null) {
                return clientTransaction;
            }
            return null;
        }

        private boolean handleAuthentication(ResponseEvent object) throws SipException {
            Response response = object.getResponse();
            if (this.getNonceFromResponse(response) == null) {
                this.onError(-2, "server does not provide challenge");
                return false;
            }
            if (this.mAuthenticationRetryCount < 2) {
                this.mClientTransaction = SipSessionGroup.this.mSipHelper.handleChallenge((ResponseEvent)object, this.getAccountManager());
                this.mDialog = this.mClientTransaction.getDialog();
                ++this.mAuthenticationRetryCount;
                if (SipSessionGroup.isLoggable(this, (EventObject)object)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("   authentication retry count=");
                    ((StringBuilder)object).append(this.mAuthenticationRetryCount);
                    this.log(((StringBuilder)object).toString());
                }
                return true;
            }
            if (this.crossDomainAuthenticationRequired(response)) {
                this.onError(-11, this.getRealmFromResponse(response));
            } else {
                this.onError(-8, "incorrect username or password");
            }
            return false;
        }

        private boolean inCall(EventObject eventObject) throws SipException {
            if (END_CALL == eventObject) {
                this.mState = 10;
                SipSessionGroup.this.mSipHelper.sendBye(this.mDialog);
                this.mProxy.onCallEnded(this);
                this.startSessionTimer(3);
                return true;
            }
            if (SipSessionGroup.isRequestEvent("INVITE", eventObject)) {
                this.mState = 3;
                eventObject = (RequestEvent)eventObject;
                this.mInviteReceived = eventObject;
                this.mPeerSessionDescription = SipSessionGroup.this.extractContent((Message)eventObject.getRequest());
                this.mServerTransaction = null;
                this.mProxy.onRinging(this, this.mPeerProfile, this.mPeerSessionDescription);
                return true;
            }
            if (SipSessionGroup.isRequestEvent("BYE", eventObject)) {
                SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 200);
                this.endCallNormally();
                return true;
            }
            if (SipSessionGroup.isRequestEvent("REFER", eventObject)) {
                return this.processReferRequest((RequestEvent)eventObject);
            }
            if (eventObject instanceof MakeCallCommand) {
                this.mState = 5;
                this.mClientTransaction = SipSessionGroup.this.mSipHelper.sendReinvite(this.mDialog, ((MakeCallCommand)eventObject).getSessionDescription());
                this.startSessionTimer(((MakeCallCommand)eventObject).getTimeout());
                return true;
            }
            return eventObject instanceof ResponseEvent && SipSessionGroup.expectResponse("NOTIFY", eventObject);
        }

        private boolean incomingCall(EventObject eventObject) throws SipException {
            if (eventObject instanceof MakeCallCommand) {
                this.mState = 4;
                this.mServerTransaction = SipSessionGroup.this.mSipHelper.sendInviteOk(this.mInviteReceived, SipSessionGroup.this.mLocalProfile, ((MakeCallCommand)eventObject).getSessionDescription(), this.mServerTransaction, SipSessionGroup.this.mExternalIp, SipSessionGroup.this.mExternalPort);
                this.startSessionTimer(((MakeCallCommand)eventObject).getTimeout());
                return true;
            }
            if (END_CALL == eventObject) {
                SipSessionGroup.this.mSipHelper.sendInviteBusyHere(this.mInviteReceived, this.mServerTransaction);
                this.endCallNormally();
                return true;
            }
            if (SipSessionGroup.isRequestEvent("CANCEL", eventObject)) {
                eventObject = (RequestEvent)eventObject;
                SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 200);
                SipSessionGroup.this.mSipHelper.sendInviteRequestTerminated(this.mInviteReceived.getRequest(), this.mServerTransaction);
                this.endCallNormally();
                return true;
            }
            return false;
        }

        private boolean incomingCallToInCall(EventObject object) {
            if (SipSessionGroup.isRequestEvent("ACK", (EventObject)object)) {
                if ((object = SipSessionGroup.this.extractContent((Message)((RequestEvent)object).getRequest())) != null) {
                    this.mPeerSessionDescription = object;
                }
                if (this.mPeerSessionDescription == null) {
                    this.onError(-4, "peer sdp is empty");
                } else {
                    this.establishCall(false);
                }
                return true;
            }
            return SipSessionGroup.isRequestEvent("CANCEL", (EventObject)object);
        }

        private boolean isCurrentTransaction(TransactionTerminatedEvent object) {
            Object object2 = object.isServerTransaction() ? this.mServerTransaction : this.mClientTransaction;
            if (object2 != (object = object.isServerTransaction() ? object.getServerTransaction() : object.getClientTransaction()) && this.mState != 9) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("not the current transaction; current=");
                stringBuilder.append(this.toString((Transaction)object2));
                stringBuilder.append(", target=");
                stringBuilder.append(this.toString((Transaction)object));
                this.log(stringBuilder.toString());
                return false;
            }
            if (object2 != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("transaction terminated: ");
                ((StringBuilder)object).append(this.toString((Transaction)object2));
                this.log(((StringBuilder)object).toString());
                return true;
            }
            return true;
        }

        private void log(String string) {
            Rlog.d((String)SSI_TAG, (String)string);
        }

        private void onError(int n, String string) {
            this.cancelSessionTimer();
            int n2 = this.mState;
            if (n2 != 1 && n2 != 2) {
                this.endCallOnError(n, string);
            } else {
                this.onRegistrationFailed(n, string);
            }
        }

        private void onError(Throwable throwable) {
            throwable = SipSessionGroup.this.getRootCause(throwable);
            this.onError(this.getErrorCode(throwable), throwable.toString());
        }

        private void onError(Response response) {
            int n = response.getStatusCode();
            if (!this.mInCall && n == 486) {
                this.endCallOnBusy();
            } else {
                this.onError(this.getErrorCode(n), this.createErrorMessage(response));
            }
        }

        private void onRegistrationDone(int n) {
            this.reset();
            this.mProxy.onRegistrationDone(this, n);
        }

        private void onRegistrationFailed(int n, String string) {
            this.reset();
            this.mProxy.onRegistrationFailed(this, n, string);
        }

        private void onRegistrationFailed(Response response) {
            this.onRegistrationFailed(this.getErrorCode(response.getStatusCode()), this.createErrorMessage(response));
        }

        private boolean outgoingCall(EventObject eventObject) throws SipException {
            if (SipSessionGroup.expectResponse("INVITE", eventObject)) {
                Response response = (eventObject = (ResponseEvent)eventObject).getResponse();
                int n = response.getStatusCode();
                if (n != 200) {
                    if (n != 401 && n != 407) {
                        if (n != 491) {
                            switch (n) {
                                default: {
                                    if (this.mReferSession != null) {
                                        SipSessionGroup.this.mSipHelper.sendReferNotify(this.mReferSession.mDialog, this.getResponseString(503));
                                    }
                                    if (n >= 400) {
                                        this.onError(response);
                                        return true;
                                    }
                                    return n < 300;
                                }
                                case 180: 
                                case 181: 
                                case 182: 
                                case 183: 
                            }
                            if (this.mState == 5) {
                                this.mState = 6;
                                this.cancelSessionTimer();
                                this.mProxy.onRingingBack(this);
                            }
                            return true;
                        }
                        return true;
                    }
                    if (this.handleAuthentication((ResponseEvent)eventObject)) {
                        SipSessionGroup.this.addSipSession(this);
                    }
                    return true;
                }
                if (this.mReferSession != null) {
                    SipSessionGroup.this.mSipHelper.sendReferNotify(this.mReferSession.mDialog, this.getResponseString(200));
                    this.mReferSession = null;
                }
                SipSessionGroup.this.mSipHelper.sendInviteAck((ResponseEvent)eventObject, this.mDialog);
                this.mPeerSessionDescription = SipSessionGroup.this.extractContent((Message)response);
                this.establishCall(true);
                return true;
            }
            if (END_CALL == eventObject) {
                this.mState = 7;
                SipSessionGroup.this.mSipHelper.sendCancel(this.mClientTransaction);
                this.startSessionTimer(3);
                return true;
            }
            if (SipSessionGroup.isRequestEvent("INVITE", eventObject)) {
                eventObject = (RequestEvent)eventObject;
                SipSessionGroup.this.mSipHelper.sendInviteBusyHere((RequestEvent)eventObject, eventObject.getServerTransaction());
                return true;
            }
            return false;
        }

        private boolean outgoingCallToReady(EventObject eventObject) throws SipException {
            block8 : {
                block2 : {
                    block5 : {
                        block6 : {
                            block7 : {
                                int n;
                                Response response;
                                block4 : {
                                    block3 : {
                                        if (!(eventObject instanceof ResponseEvent)) break block2;
                                        response = ((ResponseEvent)eventObject).getResponse();
                                        n = response.getStatusCode();
                                        if (!SipSessionGroup.expectResponse("CANCEL", eventObject)) break block3;
                                        if (n == 200) {
                                            return true;
                                        }
                                        break block4;
                                    }
                                    if (!SipSessionGroup.expectResponse("INVITE", eventObject)) break block5;
                                    if (n == 200) break block6;
                                    if (n == 487) break block7;
                                }
                                if (n >= 400) {
                                    this.onError(response);
                                    return true;
                                }
                                break block8;
                            }
                            this.endCallNormally();
                            return true;
                        }
                        this.outgoingCall(eventObject);
                        return true;
                    }
                    return false;
                }
                if (!(eventObject instanceof TransactionTerminatedEvent)) break block8;
                this.onError(new SipException("timed out"));
            }
            return false;
        }

        private void processCommand(EventObject eventObject) throws SipException {
            StringBuilder stringBuilder;
            if (SipSessionGroup.isLoggable(eventObject)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("process cmd: ");
                stringBuilder.append(eventObject);
                this.log(stringBuilder.toString());
            }
            if (!this.process(eventObject)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("cannot initiate a new transaction to execute: ");
                stringBuilder.append(eventObject);
                this.onError(-9, stringBuilder.toString());
            }
        }

        private void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
            if (this.mDialog == dialogTerminatedEvent.getDialog()) {
                this.onError(new SipException("dialog terminated"));
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("not the current dialog; current=");
                stringBuilder.append((Object)this.mDialog);
                stringBuilder.append(", terminated=");
                stringBuilder.append((Object)dialogTerminatedEvent.getDialog());
                this.log(stringBuilder.toString());
            }
        }

        private boolean processExceptions(EventObject eventObject) throws SipException {
            if (SipSessionGroup.isRequestEvent("BYE", eventObject)) {
                SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 200);
                this.endCallNormally();
                return true;
            }
            if (SipSessionGroup.isRequestEvent("CANCEL", eventObject)) {
                SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 481);
                return true;
            }
            if (eventObject instanceof TransactionTerminatedEvent) {
                if (this.isCurrentTransaction((TransactionTerminatedEvent)eventObject)) {
                    if (eventObject instanceof TimeoutEvent) {
                        this.processTimeout((TimeoutEvent)eventObject);
                    } else {
                        this.processTransactionTerminated((TransactionTerminatedEvent)eventObject);
                    }
                    return true;
                }
            } else {
                if (SipSessionGroup.isRequestEvent("OPTIONS", eventObject)) {
                    SipSessionGroup.this.mSipHelper.sendResponse((RequestEvent)eventObject, 200);
                    return true;
                }
                if (eventObject instanceof DialogTerminatedEvent) {
                    this.processDialogTerminated((DialogTerminatedEvent)eventObject);
                    return true;
                }
            }
            return false;
        }

        private boolean processReferRequest(RequestEvent requestEvent) throws SipException {
            ReferToHeader referToHeader;
            Object object;
            String string;
            block3 : {
                try {
                    referToHeader = (ReferToHeader)requestEvent.getRequest().getHeader("Refer-To");
                    object = (SipURI)referToHeader.getAddress().getURI();
                    string = object.getHeader("Replaces");
                    if (object.getUser() != null) break block3;
                    SipSessionGroup.this.mSipHelper.sendResponse(requestEvent, 400);
                    return false;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new SipException("createPeerProfile()", (Throwable)illegalArgumentException);
                }
            }
            SipSessionGroup.this.mSipHelper.sendResponse(requestEvent, 202);
            object = SipSessionGroup.this.createNewSession(requestEvent, this.mProxy.getListener(), SipSessionGroup.this.mSipHelper.getServerTransaction(requestEvent), 0);
            object.mReferSession = this;
            object.mReferredBy = (ReferredByHeader)requestEvent.getRequest().getHeader("Referred-By");
            object.mReplaces = string;
            object.mPeerProfile = SipSessionGroup.createPeerProfile((HeaderAddress)referToHeader);
            object.mProxy.onCallTransferring((ISipSession)object, null);
            return true;
        }

        private void processTimeout(TimeoutEvent timeoutEvent) {
            this.log("processing Timeout...");
            int n = this.mState;
            if (n != 1 && n != 2) {
                if (n != 3 && n != 4 && n != 5 && n != 7) {
                    this.log("   do nothing");
                } else {
                    this.onError(-5, timeoutEvent.toString());
                }
            } else {
                this.reset();
                this.mProxy.onRegistrationTimeout(this);
            }
        }

        private void processTransactionTerminated(TransactionTerminatedEvent object) {
            int n = this.mState;
            if (n != 0 && n != 8) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Transaction terminated early: ");
                ((StringBuilder)object).append(this);
                this.log(((StringBuilder)object).toString());
                this.onError(-3, "transaction terminated");
            } else {
                this.log("Transaction terminated; do nothing");
            }
        }

        private boolean readyForCall(EventObject eventObject) throws SipException {
            if (eventObject instanceof MakeCallCommand) {
                this.mState = 5;
                eventObject = (MakeCallCommand)eventObject;
                this.mPeerProfile = ((MakeCallCommand)eventObject).getPeerProfile();
                if (this.mReferSession != null) {
                    SipSessionGroup.this.mSipHelper.sendReferNotify(this.mReferSession.mDialog, this.getResponseString(100));
                }
                this.mClientTransaction = SipSessionGroup.this.mSipHelper.sendInvite(SipSessionGroup.this.mLocalProfile, this.mPeerProfile, ((MakeCallCommand)eventObject).getSessionDescription(), this.generateTag(), this.mReferredBy, this.mReplaces);
                this.mDialog = this.mClientTransaction.getDialog();
                SipSessionGroup.this.addSipSession(this);
                this.startSessionTimer(((MakeCallCommand)eventObject).getTimeout());
                this.mProxy.onCalling(this);
                return true;
            }
            if (eventObject instanceof RegisterCommand) {
                this.mState = 1;
                int n = ((RegisterCommand)eventObject).getDuration();
                this.mClientTransaction = SipSessionGroup.this.mSipHelper.sendRegister(SipSessionGroup.this.mLocalProfile, this.generateTag(), n);
                this.mDialog = this.mClientTransaction.getDialog();
                SipSessionGroup.this.addSipSession(this);
                this.mProxy.onRegistering(this);
                return true;
            }
            if (DEREGISTER == eventObject) {
                this.mState = 2;
                this.mClientTransaction = SipSessionGroup.this.mSipHelper.sendRegister(SipSessionGroup.this.mLocalProfile, this.generateTag(), 0);
                this.mDialog = this.mClientTransaction.getDialog();
                SipSessionGroup.this.addSipSession(this);
                this.mProxy.onRegistering(this);
                return true;
            }
            return false;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean registeringToReady(EventObject eventObject) throws SipException {
            if (!SipSessionGroup.expectResponse("REGISTER", eventObject)) return false;
            ResponseEvent responseEvent = (ResponseEvent)eventObject;
            Response response = responseEvent.getResponse();
            int n = response.getStatusCode();
            if (n != 200) {
                if (n != 401 && n != 407) {
                    if (n < 500) return false;
                    this.onRegistrationFailed(response);
                    return true;
                }
                this.handleAuthentication(responseEvent);
                return true;
            }
            n = this.mState == 1 ? this.getExpiryTime(((ResponseEvent)eventObject).getResponse()) : -1;
            this.onRegistrationDone(n);
            return true;
        }

        private void reset() {
            this.mInCall = false;
            SipSessionGroup.this.removeSipSession(this);
            this.mPeerProfile = null;
            this.mState = 0;
            this.mInviteReceived = null;
            this.mPeerSessionDescription = null;
            this.mAuthenticationRetryCount = 0;
            this.mReferSession = null;
            this.mReferredBy = null;
            this.mReplaces = null;
            Object object = this.mDialog;
            if (object != null) {
                object.delete();
            }
            this.mDialog = null;
            try {
                if (this.mServerTransaction != null) {
                    this.mServerTransaction.terminate();
                }
            }
            catch (ObjectInUseException objectInUseException) {
                // empty catch block
            }
            this.mServerTransaction = null;
            try {
                if (this.mClientTransaction != null) {
                    this.mClientTransaction.terminate();
                }
            }
            catch (ObjectInUseException objectInUseException) {
                // empty catch block
            }
            this.mClientTransaction = null;
            this.cancelSessionTimer();
            object = this.mSipSessionImpl;
            if (object != null) {
                ((SipSessionImpl)object).stopKeepAliveProcess();
                this.mSipSessionImpl = null;
            }
        }

        private void startSessionTimer(int n) {
            if (n > 0) {
                this.mSessionTimer = new SessionTimer();
                this.mSessionTimer.start(n);
            }
        }

        private String toString(Transaction object) {
            if (object == null) {
                return "null";
            }
            Object object2 = object.getRequest();
            Dialog dialog = object.getDialog();
            CSeqHeader cSeqHeader = (CSeqHeader)object2.getHeader("CSeq");
            object2 = object2.getMethod();
            long l = cSeqHeader.getSeqNumber();
            cSeqHeader = object.getState();
            object = dialog == null ? "-" : dialog.getState();
            return String.format("req=%s,%s,s=%s,ds=%s,", new Object[]{object2, l, cSeqHeader, object});
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void answerCall(String string, int n) {
            SipSessionGroup sipSessionGroup = SipSessionGroup.this;
            synchronized (sipSessionGroup) {
                if (this.mPeerProfile == null) {
                    return;
                }
                MakeCallCommand makeCallCommand = new MakeCallCommand(this.mPeerProfile, string, n);
                this.doCommandAsync(makeCallCommand);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void changeCall(String string, int n) {
            SipSessionGroup sipSessionGroup = SipSessionGroup.this;
            synchronized (sipSessionGroup) {
                if (this.mPeerProfile == null) {
                    return;
                }
                MakeCallCommand makeCallCommand = new MakeCallCommand(this.mPeerProfile, string, n);
                this.doCommandAsync(makeCallCommand);
                return;
            }
        }

        SipSessionImpl duplicate() {
            return new SipSessionImpl(this.mProxy.getListener());
        }

        @Override
        public void endCall() {
            this.doCommandAsync(END_CALL);
        }

        protected String generateTag() {
            return String.valueOf((long)(Math.random() * 4.294967296E9));
        }

        @Override
        public String getCallId() {
            return SipHelper.getCallId(this.getTransaction());
        }

        @Override
        public String getLocalIp() {
            return SipSessionGroup.this.mLocalIp;
        }

        @Override
        public SipProfile getLocalProfile() {
            return SipSessionGroup.this.mLocalProfile;
        }

        @Override
        public SipProfile getPeerProfile() {
            return this.mPeerProfile;
        }

        @Override
        public int getState() {
            return this.mState;
        }

        @Override
        public boolean isInCall() {
            return this.mInCall;
        }

        @Override
        public void makeCall(SipProfile sipProfile, String string, int n) {
            this.doCommandAsync(new MakeCallCommand(sipProfile, string, n));
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public boolean process(EventObject eventObject) throws SipException {
            block23 : {
                block22 : {
                    if (SipSessionGroup.access$600(this, eventObject)) {
                        var2_2 = new StringBuilder();
                        var2_2.append(" ~~~~~   ");
                        var2_2.append(this);
                        var2_2.append(": ");
                        var2_2.append(SipSession.State.toString(this.mState));
                        var2_2.append(": processing ");
                        var2_2.append(SipSessionGroup.access$700(eventObject));
                        this.log(var2_2.toString());
                    }
                    var3_3 = SipSessionGroup.this;
                    // MONITORENTER : var3_3
                    var4_4 = SipSessionGroup.this.isClosed();
                    var5_5 = false;
                    if (var4_4) {
                        // MONITOREXIT : var3_3
                        return false;
                    }
                    if (this.mSipKeepAlive != null && this.mSipKeepAlive.process(eventObject)) {
                        // MONITOREXIT : var3_3
                        return true;
                    }
                    var2_2 = null;
                    if (eventObject instanceof RequestEvent) {
                        var2_2 = ((RequestEvent)eventObject).getDialog();
                    } else if (eventObject instanceof ResponseEvent) {
                        var2_2 = ((ResponseEvent)eventObject).getDialog();
                        SipSessionGroup.access$2100(SipSessionGroup.this, (ResponseEvent)eventObject);
                    }
                    if (var2_2 != null) {
                        this.mDialog = var2_2;
                    }
                    switch (this.mState) {
                        default: {
                            var4_4 = false;
                            ** break;
                        }
                        case 10: {
                            var4_4 = this.endingCall(eventObject);
                            ** break;
                        }
                        case 8: {
                            var4_4 = this.inCall(eventObject);
                            ** break;
                        }
                        case 7: {
                            var4_4 = this.outgoingCallToReady(eventObject);
                            ** break;
                        }
                        case 5: 
                        case 6: {
                            var4_4 = this.outgoingCall(eventObject);
                            ** break;
                        }
                        case 4: {
                            var4_4 = this.incomingCallToInCall(eventObject);
                            ** break;
                        }
                        case 3: {
                            var4_4 = this.incomingCall(eventObject);
                            ** break;
                        }
                        case 1: 
                        case 2: {
                            var4_4 = this.registeringToReady(eventObject);
                            ** break;
                        }
                        case 0: 
                    }
                    var4_4 = this.readyForCall(eventObject);
lbl62: // 9 sources:
                    if (var4_4) break block22;
                    var4_4 = var5_5;
                    if (!this.processExceptions(eventObject)) break block23;
                }
                var4_4 = true;
            }
            // MONITOREXIT : var3_3
            return var4_4;
        }

        @Override
        public void register(int n) {
            this.doCommandAsync(new RegisterCommand(n));
        }

        @Override
        public void setListener(ISipSessionListener iSipSessionListener) {
            SipSessionListenerProxy sipSessionListenerProxy = this.mProxy;
            if (iSipSessionListener instanceof SipSessionListenerProxy) {
                iSipSessionListener = ((SipSessionListenerProxy)iSipSessionListener).getListener();
            }
            sipSessionListenerProxy.setListener(iSipSessionListener);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void startKeepAliveProcess(int n, SipProfile object, KeepAliveProcessCallback keepAliveProcessCallback) throws SipException {
            SipSessionGroup sipSessionGroup = SipSessionGroup.this;
            synchronized (sipSessionGroup) {
                if (this.mSipKeepAlive == null) {
                    this.mPeerProfile = object;
                    object = new SipKeepAlive();
                    this.mSipKeepAlive = object;
                    this.mProxy.setListener(this.mSipKeepAlive);
                    this.mSipKeepAlive.start(n, keepAliveProcessCallback);
                    return;
                }
                object = new SipException("Cannot create more than one keepalive process in a SipSession");
                throw object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void startKeepAliveProcess(int n, KeepAliveProcessCallback keepAliveProcessCallback) throws SipException {
            SipSessionGroup sipSessionGroup = SipSessionGroup.this;
            synchronized (sipSessionGroup) {
                this.startKeepAliveProcess(n, SipSessionGroup.this.mLocalProfile, keepAliveProcessCallback);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void stopKeepAliveProcess() {
            SipSessionGroup sipSessionGroup = SipSessionGroup.this;
            synchronized (sipSessionGroup) {
                if (this.mSipKeepAlive != null) {
                    this.mSipKeepAlive.stop();
                    this.mSipKeepAlive = null;
                }
                return;
            }
        }

        public String toString() {
            try {
                String string = Object.super.toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string.substring(string.indexOf("@")));
                stringBuilder.append(":");
                stringBuilder.append(SipSession.State.toString(this.mState));
                string = stringBuilder.toString();
                return string;
            }
            catch (Throwable throwable) {
                return Object.super.toString();
            }
        }

        @Override
        public void unregister() {
            this.doCommandAsync(DEREGISTER);
        }

        class SessionTimer {
            private boolean mRunning = true;

            SessionTimer() {
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void sleep(int n) {
                synchronized (this) {
                    long l = n * 1000;
                    try {
                        try {
                            this.wait(l);
                        }
                        catch (InterruptedException interruptedException) {
                            SipSessionGroup.this.loge("session timer interrupted!", interruptedException);
                        }
                        return;
                    }
                    catch (Throwable throwable2) {}
                    throw throwable2;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void timeout() {
                SipSessionGroup sipSessionGroup = SipSessionGroup.this;
                synchronized (sipSessionGroup) {
                    SipSessionImpl.this.onError(-5, "Session timed out!");
                    return;
                }
            }

            void cancel() {
                synchronized (this) {
                    this.mRunning = false;
                    this.notify();
                    return;
                }
            }

            void start(final int n) {
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        SessionTimer.this.sleep(n);
                        if (SessionTimer.this.mRunning) {
                            SessionTimer.this.timeout();
                        }
                    }
                }, "SipSessionTimerThread").start();
            }

        }

        class SipKeepAlive
        extends SipSessionAdapter
        implements Runnable {
            private static final boolean SKA_DBG = true;
            private static final String SKA_TAG = "SipKeepAlive";
            private KeepAliveProcessCallback mCallback;
            private int mInterval;
            private boolean mPortChanged = false;
            private int mRPort = 0;
            private boolean mRunning = false;

            SipKeepAlive() {
            }

            private int getRPortFromResponse(Response response) {
                int n = (response = (ViaHeader)response.getHeader("Via")) == null ? -1 : response.getRPort();
                return n;
            }

            private void log(String string) {
                Rlog.d((String)SKA_TAG, (String)string);
            }

            private boolean parseOptionsResult(EventObject serializable) {
                if (SipSessionGroup.expectResponse("OPTIONS", (EventObject)serializable)) {
                    int n = this.getRPortFromResponse(((ResponseEvent)serializable).getResponse());
                    if (n != -1) {
                        int n2;
                        if (this.mRPort == 0) {
                            this.mRPort = n;
                        }
                        if ((n2 = this.mRPort) != n) {
                            this.mPortChanged = true;
                            this.log(String.format("rport is changed: %d <> %d", n2, n));
                            this.mRPort = n;
                        } else {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("rport is the same: ");
                            ((StringBuilder)serializable).append(n);
                            this.log(((StringBuilder)serializable).toString());
                        }
                    } else {
                        this.log("peer did not respond rport");
                    }
                    return true;
                }
                return false;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void sendKeepAlive() throws SipException {
                SipSessionGroup sipSessionGroup = SipSessionGroup.this;
                synchronized (sipSessionGroup) {
                    SipSessionImpl.this.mState = 9;
                    SipSessionImpl.this.mClientTransaction = SipSessionGroup.this.mSipHelper.sendOptions(SipSessionGroup.this.mLocalProfile, SipSessionImpl.this.mPeerProfile, SipSessionImpl.this.generateTag());
                    SipSessionImpl.this.mDialog = SipSessionImpl.this.mClientTransaction.getDialog();
                    SipSessionGroup.this.addSipSession(SipSessionImpl.this);
                    SipSessionImpl.this.startSessionTimer(5);
                    return;
                }
            }

            @Override
            public void onError(ISipSession iSipSession, int n, String string) {
                this.stop();
                this.mCallback.onError(n, string);
            }

            boolean process(EventObject eventObject) {
                if (this.mRunning && SipSessionImpl.this.mState == 9 && eventObject instanceof ResponseEvent && this.parseOptionsResult(eventObject)) {
                    if (this.mPortChanged) {
                        SipSessionGroup.this.resetExternalAddress();
                        this.stop();
                    } else {
                        SipSessionImpl.this.cancelSessionTimer();
                        SipSessionGroup.this.removeSipSession(SipSessionImpl.this);
                    }
                    this.mCallback.onResponse(this.mPortChanged);
                    return true;
                }
                return false;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                SipSessionGroup sipSessionGroup = SipSessionGroup.this;
                synchronized (sipSessionGroup) {
                    block6 : {
                        if (!this.mRunning) {
                            return;
                        }
                        try {
                            this.sendKeepAlive();
                        }
                        catch (Throwable throwable) {
                            SipSessionGroup sipSessionGroup2 = SipSessionGroup.this;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("keepalive error: ");
                            stringBuilder.append(SipSessionGroup.this.mLocalProfile.getUriString());
                            sipSessionGroup2.loge(stringBuilder.toString(), SipSessionGroup.this.getRootCause(throwable));
                            if (!this.mRunning) break block6;
                            SipSessionImpl.this.onError(throwable);
                        }
                    }
                    return;
                }
            }

            void start(int n, KeepAliveProcessCallback object) {
                if (this.mRunning) {
                    return;
                }
                this.mRunning = true;
                this.mInterval = n;
                this.mCallback = new KeepAliveProcessCallbackProxy((KeepAliveProcessCallback)object);
                SipSessionGroup.this.mWakeupTimer.set(n * 1000, this);
                object = new StringBuilder();
                ((StringBuilder)object).append("start keepalive:");
                ((StringBuilder)object).append(SipSessionGroup.this.mLocalProfile.getUriString());
                this.log(((StringBuilder)object).toString());
                this.run();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            void stop() {
                SipSessionGroup sipSessionGroup = SipSessionGroup.this;
                synchronized (sipSessionGroup) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("stop keepalive:");
                    stringBuilder.append(SipSessionGroup.this.mLocalProfile.getUriString());
                    stringBuilder.append(",RPort=");
                    stringBuilder.append(this.mRPort);
                    this.log(stringBuilder.toString());
                    this.mRunning = false;
                    SipSessionGroup.this.mWakeupTimer.cancel(this);
                    SipSessionImpl.this.reset();
                    return;
                }
            }
        }

    }

}

