/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.core.ThreadAuditor;
import gov.nist.core.net.AddressResolver;
import gov.nist.core.net.NetworkLayer;
import gov.nist.core.net.SslNetworkLayer;
import gov.nist.javax.sip.EventScanner;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.LogRecordFactory;
import gov.nist.javax.sip.NistSipMessageFactoryImpl;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.SipStackExt;
import gov.nist.javax.sip.clientauthutils.AccountManager;
import gov.nist.javax.sip.clientauthutils.AuthenticationHelper;
import gov.nist.javax.sip.clientauthutils.AuthenticationHelperImpl;
import gov.nist.javax.sip.clientauthutils.SecureAccountManager;
import gov.nist.javax.sip.parser.StringMsgParser;
import gov.nist.javax.sip.stack.DefaultMessageLogFactory;
import gov.nist.javax.sip.stack.DefaultRouter;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.StackMessageFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
import javax.sip.ProviderDoesNotExistException;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TransportNotSupportedException;
import javax.sip.address.Router;
import javax.sip.header.HeaderFactory;

public class SipStackImpl
extends SIPTransactionStack
implements SipStack,
SipStackExt {
    public static final Integer MAX_DATAGRAM_SIZE = 8192;
    private String[] cipherSuites;
    boolean deliverTerminatedEventForAck;
    boolean deliverUnsolicitedNotify;
    private String[] enabledProtocols;
    private EventScanner eventScanner;
    private Hashtable<String, ListeningPointImpl> listeningPoints;
    boolean reEntrantListener;
    SipListener sipListener;
    private LinkedList<SipProviderImpl> sipProviders;
    private Semaphore stackSemaphore;

    protected SipStackImpl() {
        this.deliverTerminatedEventForAck = false;
        this.deliverUnsolicitedNotify = false;
        this.stackSemaphore = new Semaphore(1);
        this.cipherSuites = new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA", "TLS_DH_anon_WITH_AES_128_CBC_SHA", "SSL_DH_anon_WITH_3DES_EDE_CBC_SHA"};
        this.enabledProtocols = new String[]{"SSLv3", "SSLv2Hello", "TLSv1"};
        super.setMessageFactory(new NistSipMessageFactoryImpl(this));
        this.eventScanner = new EventScanner(this);
        this.listeningPoints = new Hashtable();
        this.sipProviders = new LinkedList();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SipStackImpl(Properties serializable) throws PeerUnavailableException {
        block99 : {
            block97 : {
                block98 : {
                    block95 : {
                        block96 : {
                            block94 : {
                                block93 : {
                                    block92 : {
                                        block90 : {
                                            block88 : {
                                                block87 : {
                                                    block86 : {
                                                        block85 : {
                                                            this();
                                                            object2 = serializable.getProperty("javax.sip.IP_ADDRESS");
                                                            if (object2 != null) {
                                                                try {
                                                                    super.setHostAddress((String)object2);
                                                                }
                                                                catch (UnknownHostException unknownHostException) {
                                                                    stringBuilder = new StringBuilder();
                                                                    stringBuilder.append("bad address ");
                                                                    stringBuilder.append((String)object2);
                                                                    throw new PeerUnavailableException(stringBuilder.toString());
                                                                }
                                                            }
                                                            if ((object2 = serializable.getProperty("javax.sip.STACK_NAME")) == null) throw new PeerUnavailableException("stack name is missing");
                                                            super.setStackName((String)object2);
                                                            object2 = serializable.getProperty("gov.nist.javax.sip.STACK_LOGGER");
                                                            if (object2 == null) {
                                                                object2 = "gov.nist.core.LogWriter";
                                                            }
                                                            object = Class.forName((String)object2);
                                                            object = (StackLogger)object.getConstructor(new Class[0]).newInstance(new Object[0]);
                                                            object.setStackProperties((Properties)serializable);
                                                            super.setStackLogger((StackLogger)object);
                                                            object = serializable.getProperty("gov.nist.javax.sip.SERVER_LOGGER");
                                                            if (object != null) break block85;
                                                            object = "gov.nist.javax.sip.stack.ServerLog";
                                                        }
                                                        object = Class.forName((String)object);
                                                        this.serverLogger = (ServerLogger)object.getConstructor(new Class[0]).newInstance(new Object[0]);
                                                        this.serverLogger.setSipStack(this);
                                                        this.serverLogger.setStackProperties((Properties)serializable);
                                                        this.outboundProxy = serializable.getProperty("javax.sip.OUTBOUND_PROXY");
                                                        this.defaultRouter = new DefaultRouter(this, this.outboundProxy);
                                                        object2 = serializable.getProperty("javax.sip.ROUTER_PATH");
                                                        if (object2 != null) break block86;
                                                        object2 = "gov.nist.javax.sip.stack.DefaultRouter";
                                                    }
                                                    super.setRouter((Router)Class.forName((String)object2).getConstructor(new Class[]{SipStack.class, String.class}).newInstance(new Object[]{this, this.outboundProxy}));
                                                    object2 = serializable.getProperty("javax.sip.USE_ROUTER_FOR_ALL_URIS");
                                                    this.useRouterForAll = true;
                                                    if (object2 != null) {
                                                        this.useRouterForAll = "true".equalsIgnoreCase((String)object2);
                                                    }
                                                    if ((object2 = serializable.getProperty("javax.sip.EXTENSION_METHODS")) == null) break block87;
                                                    object = new StringTokenizer((String)object2);
                                                    while (object.hasMoreTokens()) {
                                                        object2 = object.nextToken(":");
                                                        if (!(object2.equalsIgnoreCase("BYE") || object2.equalsIgnoreCase("INVITE") || object2.equalsIgnoreCase("SUBSCRIBE") || object2.equalsIgnoreCase("NOTIFY") || object2.equalsIgnoreCase("ACK") || object2.equalsIgnoreCase("OPTIONS"))) {
                                                            this.addExtensionMethod((String)object2);
                                                            continue;
                                                        }
                                                        serializable = new StringBuilder();
                                                        serializable.append("Bad extension method ");
                                                        serializable.append((String)object2);
                                                        throw new PeerUnavailableException(serializable.toString());
                                                    }
                                                }
                                                object = serializable.getProperty("javax.net.ssl.keyStore");
                                                object2 = serializable.getProperty("javax.net.ssl.trustStore");
                                                if (object != null) {
                                                    if (object2 == null) {
                                                        object2 = object;
                                                    }
                                                    charSequence = serializable.getProperty("javax.net.ssl.keyStorePassword");
                                                    try {
                                                        object3 = new SslNetworkLayer((String)object2, (String)object, charSequence.toCharArray(), serializable.getProperty("javax.net.ssl.keyStoreType"));
                                                        this.networkLayer = object3;
                                                    }
                                                    catch (Exception exception) {
                                                        this.getStackLogger().logError("could not instantiate SSL networking", exception);
                                                    }
                                                }
                                                this.isAutomaticDialogSupportEnabled = serializable.getProperty("javax.sip.AUTOMATIC_DIALOG_SUPPORT", "on").equalsIgnoreCase("on");
                                                this.isAutomaticDialogErrorHandlingEnabled = serializable.getProperty("gov.nist.javax.sip.AUTOMATIC_DIALOG_ERROR_HANDLING", "true").equals(Boolean.TRUE.toString());
                                                if (this.isAutomaticDialogSupportEnabled) {
                                                    this.isAutomaticDialogErrorHandlingEnabled = true;
                                                }
                                                if (serializable.getProperty("gov.nist.javax.sip.MAX_LISTENER_RESPONSE_TIME") != null) {
                                                    this.maxListenerResponseTime = Integer.parseInt(serializable.getProperty("gov.nist.javax.sip.MAX_LISTENER_RESPONSE_TIME"));
                                                    if (this.maxListenerResponseTime <= 0) throw new PeerUnavailableException("Bad configuration parameter gov.nist.javax.sip.MAX_LISTENER_RESPONSE_TIME : should be positive");
                                                } else {
                                                    this.maxListenerResponseTime = -1;
                                                }
                                                this.deliverTerminatedEventForAck = serializable.getProperty("gov.nist.javax.sip.DELIVER_TERMINATED_EVENT_FOR_ACK", "false").equalsIgnoreCase("true");
                                                this.deliverUnsolicitedNotify = serializable.getProperty("gov.nist.javax.sip.DELIVER_UNSOLICITED_NOTIFY", "false").equalsIgnoreCase("true");
                                                object2 = serializable.getProperty("javax.sip.FORKABLE_EVENTS");
                                                if (object2 != null) {
                                                    object2 = new StringTokenizer((String)object2);
                                                    while (object2.hasMoreTokens()) {
                                                        object = object2.nextToken();
                                                        this.forkedEvents.add(object);
                                                    }
                                                }
                                                if (serializable.containsKey("gov.nist.javax.sip.NETWORK_LAYER")) {
                                                    block89 : {
                                                        object2 = serializable.getProperty("gov.nist.javax.sip.NETWORK_LAYER");
                                                        object = Class.forName((String)object2);
                                                        try {
                                                            this.networkLayer = (NetworkLayer)object.getConstructor(new Class[0]).newInstance(new Object[0]);
                                                            break block88;
                                                        }
                                                        catch (Exception exception) {
                                                            break block89;
                                                        }
                                                        catch (Exception exception) {
                                                            // empty catch block
                                                        }
                                                    }
                                                    stringBuilder = new StringBuilder();
                                                    stringBuilder.append("can't find or instantiate NetworkLayer implementation: ");
                                                    stringBuilder.append((String)object2);
                                                    throw new PeerUnavailableException(stringBuilder.toString());
                                                }
                                            }
                                            if (serializable.containsKey("gov.nist.javax.sip.ADDRESS_RESOLVER")) {
                                                block91 : {
                                                    object2 = serializable.getProperty("gov.nist.javax.sip.ADDRESS_RESOLVER");
                                                    object = Class.forName((String)object2);
                                                    try {
                                                        this.addressResolver = (AddressResolver)object.getConstructor(new Class[0]).newInstance(new Object[0]);
                                                        break block90;
                                                    }
                                                    catch (Exception exception) {
                                                        break block91;
                                                    }
                                                    catch (Exception exception) {
                                                        // empty catch block
                                                    }
                                                }
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append("can't find or instantiate AddressResolver implementation: ");
                                                stringBuilder.append((String)object2);
                                                throw new PeerUnavailableException(stringBuilder.toString());
                                            }
                                        }
                                        if ((object2 = serializable.getProperty("gov.nist.javax.sip.MAX_CONNECTIONS")) != null) {
                                            try {
                                                super((String)object2);
                                                this.maxConnections = object.intValue();
                                            }
                                            catch (NumberFormatException numberFormatException) {
                                                if (!this.isLoggingEnabled()) break block92;
                                                object3 = this.getStackLogger();
                                                object = new StringBuilder();
                                                object.append("max connections - bad value ");
                                                object.append(numberFormatException.getMessage());
                                                object3.logError(object.toString());
                                            }
                                        }
                                    }
                                    if ((object2 = serializable.getProperty("gov.nist.javax.sip.THREAD_POOL_SIZE")) != null) {
                                        try {
                                            super((String)object2);
                                            this.threadPoolSize = object.intValue();
                                        }
                                        catch (NumberFormatException numberFormatException) {
                                            if (!this.isLoggingEnabled()) break block93;
                                            object2 = this.getStackLogger();
                                            object3 = new StringBuilder();
                                            object3.append("thread pool size - bad value ");
                                            object3.append(numberFormatException.getMessage());
                                            object2.logError(object3.toString());
                                        }
                                    }
                                }
                                if ((object2 = serializable.getProperty("gov.nist.javax.sip.MAX_SERVER_TRANSACTIONS")) != null) {
                                    try {
                                        super((String)object2);
                                        this.serverTransactionTableHighwaterMark = object.intValue();
                                        this.serverTransactionTableLowaterMark = this.serverTransactionTableHighwaterMark * 80 / 100;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        if (this.isLoggingEnabled()) {
                                            object = this.getStackLogger();
                                            object3 = new StringBuilder();
                                            object3.append("transaction table size - bad value ");
                                            object3.append(numberFormatException.getMessage());
                                            object.logError(object3.toString());
                                        }
                                    }
                                } else {
                                    this.unlimitedServerTransactionTableSize = true;
                                }
                                if ((object2 = serializable.getProperty("gov.nist.javax.sip.MAX_CLIENT_TRANSACTIONS")) != null) {
                                    try {
                                        object = new Integer((String)object2);
                                        this.clientTransactionTableHiwaterMark = object.intValue();
                                        this.clientTransactionTableLowaterMark = this.clientTransactionTableLowaterMark * 80 / 100;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        if (this.isLoggingEnabled()) {
                                            object3 = this.getStackLogger();
                                            object = new StringBuilder();
                                            object.append("transaction table size - bad value ");
                                            object.append(numberFormatException.getMessage());
                                            object3.logError(object.toString());
                                        }
                                    }
                                } else {
                                    this.unlimitedClientTransactionTableSize = true;
                                }
                                this.cacheServerConnections = true;
                                object2 = serializable.getProperty("gov.nist.javax.sip.CACHE_SERVER_CONNECTIONS");
                                if (object2 != null && "false".equalsIgnoreCase(object2.trim())) {
                                    this.cacheServerConnections = false;
                                }
                                this.cacheClientConnections = true;
                                object2 = serializable.getProperty("gov.nist.javax.sip.CACHE_CLIENT_CONNECTIONS");
                                if (object2 != null && "false".equalsIgnoreCase(object2.trim())) {
                                    this.cacheClientConnections = false;
                                }
                                if ((object = serializable.getProperty("gov.nist.javax.sip.READ_TIMEOUT")) == null) break block95;
                                n = Integer.parseInt((String)object);
                                if (n < 100) break block94;
                                try {
                                    this.readTimeout = n;
                                    break block95;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    break block96;
                                }
                            }
                            try {
                                object2 = System.err;
                                object3 = new StringBuilder();
                            }
                            catch (NumberFormatException numberFormatException) {}
                            try {
                                object3.append("Value too low ");
                                object3.append((String)object);
                                object2.println(object3.toString());
                                break block95;
                            }
                            catch (NumberFormatException numberFormatException) {
                                break block96;
                            }
                            break block96;
                            catch (NumberFormatException numberFormatException) {
                                // empty catch block
                            }
                        }
                        if (this.isLoggingEnabled()) {
                            object2 = this.getStackLogger();
                            object3 = new StringBuilder();
                            object3.append("Bad read timeout ");
                            object3.append((String)object);
                            object2.logError(object3.toString());
                        }
                    }
                    if (serializable.getProperty("gov.nist.javax.sip.STUN_SERVER") != null) {
                        this.getStackLogger().logWarning("Ignoring obsolete property gov.nist.javax.sip.STUN_SERVER");
                    }
                    if ((object = serializable.getProperty("gov.nist.javax.sip.MAX_MESSAGE_SIZE")) == null) ** GOTO lbl248
                    object2 = new Integer((String)object);
                    n = this.maxMessageSize = object2.intValue();
                    if (n >= 4096) break block97;
                    {
                        catch (NumberFormatException numberFormatException) {}
                    }
                    try {
                        this.maxMessageSize = 4096;
                        break block97;
                        break block98;
lbl248: // 1 sources:
                        this.maxMessageSize = 0;
                        break block97;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                if (this.isLoggingEnabled()) {
                    object3 = this.getStackLogger();
                    object = new StringBuilder();
                    object.append("maxMessageSize - bad value ");
                    object.append(object2.getMessage());
                    object3.logError(object.toString());
                }
            }
            object2 = serializable.getProperty("gov.nist.javax.sip.REENTRANT_LISTENER");
            bl = object2 != null && "true".equalsIgnoreCase((String)object2) != false;
            this.reEntrantListener = bl;
            object = serializable.getProperty("gov.nist.javax.sip.THREAD_AUDIT_INTERVAL_IN_MILLISECS");
            if (object != null) {
                block100 : {
                    object2 = this.getThreadAuditor();
                    l = Long.valueOf((String)object);
                    try {
                        object2.setPingIntervalInMillisecs(l / 2L);
                        break block99;
                    }
                    catch (NumberFormatException numberFormatException) {
                        break block100;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                if (this.isLoggingEnabled()) {
                    object3 = this.getStackLogger();
                    charSequence = new StringBuilder();
                    charSequence.append("THREAD_AUDIT_INTERVAL_IN_MILLISECS - bad value [");
                    charSequence.append((String)object);
                    charSequence.append("] ");
                    charSequence.append(object2.getMessage());
                    object3.logError(charSequence.toString());
                }
            }
        }
        this.setNon2XXAckPassedToListener(Boolean.valueOf(serializable.getProperty("gov.nist.javax.sip.PASS_INVITE_NON_2XX_ACK_TO_LISTENER", "false")));
        this.generateTimeStampHeader = Boolean.valueOf(serializable.getProperty("gov.nist.javax.sip.AUTO_GENERATE_TIMESTAMP", "false"));
        object2 = serializable.getProperty("gov.nist.javax.sip.LOG_FACTORY");
        if (object2 != null) {
            try {
                this.logRecordFactory = (LogRecordFactory)Class.forName((String)object2).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                if (this.isLoggingEnabled()) {
                    this.getStackLogger().logError("Bad configuration value for LOG_FACTORY -- using default logger");
                }
                this.logRecordFactory = new DefaultMessageLogFactory();
            }
        } else {
            this.logRecordFactory = new DefaultMessageLogFactory();
        }
        bl = serializable.getProperty("gov.nist.javax.sip.COMPUTE_CONTENT_LENGTH_FROM_MESSAGE_BODY", "false").equalsIgnoreCase("true");
        StringMsgParser.setComputeContentLengthFromMessage(bl);
        object2 = serializable.getProperty("gov.nist.javax.sip.TLS_CLIENT_PROTOCOLS");
        if (object2 != null) {
            object = new StringTokenizer((String)object2, " ,");
            object2 = new String[object.countTokens()];
            n = 0;
            while (object.hasMoreTokens()) {
                object2[n] = object.nextToken();
                ++n;
            }
            this.enabledProtocols = object2;
        }
        this.rfc2543Supported = serializable.getProperty("gov.nist.javax.sip.RFC_2543_SUPPORT_ENABLED", "true").equalsIgnoreCase("true");
        this.cancelClientTransactionChecked = serializable.getProperty("gov.nist.javax.sip.CANCEL_CLIENT_TRANSACTION_CHECKED", "true").equalsIgnoreCase("true");
        this.logStackTraceOnMessageSend = serializable.getProperty("gov.nist.javax.sip.LOG_STACK_TRACE_ON_MESSAGE_SEND", "false").equalsIgnoreCase("true");
        if (this.isLoggingEnabled()) {
            object = this.getStackLogger();
            object2 = new StringBuilder();
            object2.append("created Sip stack. Properties = ");
            object2.append(serializable);
            object.logDebug(object2.toString());
        }
        if ((object2 = this.getClass().getResourceAsStream("/TIMESTAMP")) != null) {
            object = new BufferedReader(new InputStreamReader((InputStream)object2));
            try {
                object = object.readLine();
                object2.close();
                this.getStackLogger().setBuildTimeStamp((String)object);
            }
            catch (IOException iOException) {
                this.getStackLogger().logError("Could not open build timestamp.");
            }
        }
        super.setReceiveUdpBufferSize(new Integer(serializable.getProperty("gov.nist.javax.sip.RECEIVE_UDP_BUFFER_SIZE", SipStackImpl.MAX_DATAGRAM_SIZE.toString())));
        super.setSendUdpBufferSize(new Integer(serializable.getProperty("gov.nist.javax.sip.SEND_UDP_BUFFER_SIZE", SipStackImpl.MAX_DATAGRAM_SIZE.toString())));
        this.stackDoesCongestionControl = Boolean.parseBoolean(serializable.getProperty("gov.nist.javax.sip.CONGESTION_CONTROL_ENABLED", Boolean.TRUE.toString()));
        this.isBackToBackUserAgent = Boolean.parseBoolean(serializable.getProperty("gov.nist.javax.sip.IS_BACK_TO_BACK_USER_AGENT", Boolean.FALSE.toString()));
        this.checkBranchId = Boolean.parseBoolean(serializable.getProperty("gov.nist.javax.sip.REJECT_STRAY_RESPONSES", Boolean.FALSE.toString()));
        this.isDialogTerminatedEventDeliveredForNullDialog = Boolean.parseBoolean(serializable.getProperty("gov.nist.javax.sip.DELIVER_TERMINATED_EVENT_FOR_NULL_DIALOG", Boolean.FALSE.toString()));
        this.maxForkTime = Integer.parseInt(serializable.getProperty("gov.nist.javax.sip.MAX_FORK_TIME_SECONDS", "0"));
        return;
        catch (Exception exception) {
            this.getStackLogger().logError("could not instantiate router", (Exception)exception.getCause());
            throw new PeerUnavailableException("Could not instantiate router", exception);
        }
        catch (InvocationTargetException invocationTargetException) {
            this.getStackLogger().logError("could not instantiate router -- invocation target problem", (Exception)invocationTargetException.getCause());
            throw new PeerUnavailableException("Cound not instantiate router - check constructor", invocationTargetException);
        }
        catch (Exception exception) {
            serializable = new StringBuilder();
            serializable.append("Cound not instantiate server logger ");
            serializable.append((String)object2);
            serializable.append("- check that it is present on the classpath and that there is a no-args constructor defined");
            throw new IllegalArgumentException(serializable.toString(), exception);
        }
        catch (InvocationTargetException invocationTargetException) {
            object = new StringBuilder();
            object.append("Cound not instantiate server logger ");
            object.append((String)object2);
            object.append("- check that it is present on the classpath and that there is a no-args constructor defined");
            throw new IllegalArgumentException(object.toString(), invocationTargetException);
        }
        catch (Exception exception) {
            serializable = new StringBuilder();
            serializable.append("Cound not instantiate stack logger ");
            serializable.append((String)object2);
            serializable.append("- check that it is present on the classpath and that there is a no-args constructor defined");
            throw new IllegalArgumentException(serializable.toString(), exception);
        }
        catch (InvocationTargetException invocationTargetException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cound not instantiate stack logger ");
            stringBuilder.append((String)object2);
            stringBuilder.append("- check that it is present on the classpath and that there is a no-args constructor defined");
            throw new IllegalArgumentException(stringBuilder.toString(), invocationTargetException);
        }
    }

    private void reInitialize() {
        super.reInit();
        this.eventScanner = new EventScanner(this);
        this.listeningPoints = new Hashtable();
        this.sipProviders = new LinkedList();
        this.sipListener = null;
    }

    public boolean acquireSem() {
        try {
            boolean bl = this.stackSemaphore.tryAcquire(10L, TimeUnit.SECONDS);
            return bl;
        }
        catch (InterruptedException interruptedException) {
            return false;
        }
    }

    @Override
    public ListeningPoint createListeningPoint(int n, String string) throws TransportNotSupportedException, InvalidArgumentException {
        if (this.stackAddress != null) {
            return this.createListeningPoint(this.stackAddress, n, string);
        }
        throw new NullPointerException("Stack does not have a default IP Address!");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ListeningPoint createListeningPoint(String object, int n, String string) throws TransportNotSupportedException, InvalidArgumentException {
        synchronized (this) {
            Object object2;
            void var3_3;
            void var2_2;
            Object object3;
            if (this.isLoggingEnabled()) {
                object2 = this.getStackLogger();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("createListeningPoint : address = ");
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append(" port = ");
                ((StringBuilder)object3).append((int)var2_2);
                ((StringBuilder)object3).append(" transport = ");
                ((StringBuilder)object3).append((String)var3_3);
                object2.logDebug(((StringBuilder)object3).toString());
            }
            if (object == null) {
                object = new NullPointerException("Address for listening point is null!");
                throw object;
            }
            if (var3_3 == null) {
                object = new NullPointerException("null transport");
                throw object;
            }
            if (var2_2 <= 0) {
                object = new InvalidArgumentException("bad port");
                throw object;
            }
            if (!(var3_3.equalsIgnoreCase("UDP") || var3_3.equalsIgnoreCase("TLS") || var3_3.equalsIgnoreCase("TCP") || var3_3.equalsIgnoreCase("SCTP"))) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bad transport ");
                ((StringBuilder)object).append((String)var3_3);
                object3 = new TransportNotSupportedException(((StringBuilder)object).toString());
                throw object3;
            }
            if (!this.isAlive()) {
                this.toExit = false;
                this.reInitialize();
            }
            if ((object2 = this.listeningPoints.get(object3 = ListeningPointImpl.makeKey((String)object, (int)var2_2, (String)var3_3))) != null) {
                return object2;
            }
            try {
                Object object4;
                object2 = InetAddress.getByName((String)object);
                object2 = this.createMessageProcessor((InetAddress)object2, (int)var2_2, (String)var3_3);
                if (this.isLoggingEnabled()) {
                    StackLogger stackLogger = this.getStackLogger();
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("Created Message Processor: ");
                    ((StringBuilder)object4).append((String)object);
                    ((StringBuilder)object4).append(" port = ");
                    ((StringBuilder)object4).append((int)var2_2);
                    ((StringBuilder)object4).append(" transport = ");
                    ((StringBuilder)object4).append((String)var3_3);
                    stackLogger.logDebug(((StringBuilder)object4).toString());
                }
                object4 = new ListeningPointImpl(this, (int)var2_2, (String)var3_3);
                ((ListeningPointImpl)object4).messageProcessor = object2;
                ((MessageProcessor)object2).setListeningPoint((ListeningPointImpl)object4);
                this.listeningPoints.put((String)object3, (ListeningPointImpl)object4);
                ((MessageProcessor)object2).start();
                return object4;
            }
            catch (IOException iOException) {
                if (this.isLoggingEnabled()) {
                    object2 = this.getStackLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid argument address = ");
                    stringBuilder.append((String)object);
                    stringBuilder.append(" port = ");
                    stringBuilder.append((int)var2_2);
                    stringBuilder.append(" transport = ");
                    stringBuilder.append((String)var3_3);
                    object2.logError(stringBuilder.toString());
                }
                object = new InvalidArgumentException(iOException.getMessage(), iOException);
                throw object;
            }
        }
    }

    @Override
    public SipProvider createSipProvider(ListeningPoint object) throws ObjectInUseException {
        if (object != null) {
            Object object2;
            if (this.isLoggingEnabled()) {
                StackLogger stackLogger = this.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("createSipProvider: ");
                ((StringBuilder)object2).append(object);
                stackLogger.logDebug(((StringBuilder)object2).toString());
            }
            object2 = (ListeningPointImpl)object;
            if (((ListeningPointImpl)object2).sipProvider == null) {
                object = new SipProviderImpl(this);
                ((SipProviderImpl)object).setListeningPoint((ListeningPoint)object2);
                ((ListeningPointImpl)object2).sipProvider = object;
                this.sipProviders.add((SipProviderImpl)object);
                return object;
            }
            throw new ObjectInUseException("Provider already attached!");
        }
        throw new NullPointerException("null listeningPoint");
    }

    @Override
    public void deleteListeningPoint(ListeningPoint object) throws ObjectInUseException {
        if (object != null) {
            object = (ListeningPointImpl)object;
            super.removeMessageProcessor(((ListeningPointImpl)object).messageProcessor);
            object = ((ListeningPointImpl)object).getKey();
            this.listeningPoints.remove(object);
            return;
        }
        throw new NullPointerException("null listeningPoint arg");
    }

    @Override
    public void deleteSipProvider(SipProvider sipProvider) throws ObjectInUseException {
        if (sipProvider != null) {
            SipProviderImpl sipProviderImpl = (SipProviderImpl)sipProvider;
            if (sipProviderImpl.getSipListener() == null) {
                sipProviderImpl.removeListeningPoints();
                sipProviderImpl.stop();
                this.sipProviders.remove(sipProvider);
                if (this.sipProviders.isEmpty()) {
                    this.stopStack();
                }
                return;
            }
            throw new ObjectInUseException("SipProvider still has an associated SipListener!");
        }
        throw new NullPointerException("null provider arg");
    }

    protected void finalize() {
        this.stopStack();
    }

    @Override
    public AuthenticationHelper getAuthenticationHelper(AccountManager accountManager, HeaderFactory headerFactory) {
        return new AuthenticationHelperImpl(this, accountManager, headerFactory);
    }

    public String[] getEnabledCipherSuites() {
        return this.cipherSuites;
    }

    public String[] getEnabledProtocols() {
        return this.enabledProtocols;
    }

    @Deprecated
    public EventScanner getEventScanner() {
        return this.eventScanner;
    }

    @Override
    public String getIPAddress() {
        return super.getHostAddress();
    }

    @Override
    public Iterator getListeningPoints() {
        return this.listeningPoints.values().iterator();
    }

    public LogRecordFactory getLogRecordFactory() {
        return this.logRecordFactory;
    }

    @Override
    public AuthenticationHelper getSecureAuthenticationHelper(SecureAccountManager secureAccountManager, HeaderFactory headerFactory) {
        return new AuthenticationHelperImpl(this, secureAccountManager, headerFactory);
    }

    public SipListener getSipListener() {
        return this.sipListener;
    }

    @Override
    public Iterator<SipProviderImpl> getSipProviders() {
        return this.sipProviders.iterator();
    }

    @Override
    public String getStackName() {
        return this.stackName;
    }

    public boolean isAutomaticDialogErrorHandlingEnabled() {
        return this.isAutomaticDialogErrorHandlingEnabled;
    }

    boolean isAutomaticDialogSupportEnabled() {
        return this.isAutomaticDialogSupportEnabled;
    }

    public boolean isBackToBackUserAgent() {
        return this.isBackToBackUserAgent;
    }

    @Override
    public boolean isRetransmissionFilterActive() {
        return true;
    }

    public void releaseSem() {
        this.stackSemaphore.release();
    }

    @Override
    public void setEnabledCipherSuites(String[] arrstring) {
        this.cipherSuites = arrstring;
    }

    public void setEnabledProtocols(String[] arrstring) {
        this.enabledProtocols = arrstring;
    }

    public void setIsBackToBackUserAgent(boolean bl) {
        this.isBackToBackUserAgent = bl;
    }

    @Override
    public void start() throws ProviderDoesNotExistException, SipException {
        if (this.eventScanner == null) {
            this.eventScanner = new EventScanner(this);
        }
    }

    @Override
    public void stop() {
        if (this.isLoggingEnabled()) {
            this.getStackLogger().logDebug("stopStack -- stoppping the stack");
        }
        this.stopStack();
        this.sipProviders = new LinkedList();
        this.listeningPoints = new Hashtable();
        EventScanner eventScanner = this.eventScanner;
        if (eventScanner != null) {
            eventScanner.forceStop();
        }
        this.eventScanner = null;
    }
}

