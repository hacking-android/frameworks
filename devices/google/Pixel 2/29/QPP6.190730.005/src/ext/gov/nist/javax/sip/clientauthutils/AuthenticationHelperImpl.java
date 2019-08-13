/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import gov.nist.core.StackLogger;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.clientauthutils.AccountManager;
import gov.nist.javax.sip.clientauthutils.AuthenticationHelper;
import gov.nist.javax.sip.clientauthutils.CredentialsCache;
import gov.nist.javax.sip.clientauthutils.MessageDigestAlgorithm;
import gov.nist.javax.sip.clientauthutils.SecureAccountManager;
import gov.nist.javax.sip.clientauthutils.UserCredentialHash;
import gov.nist.javax.sip.clientauthutils.UserCredentials;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.address.Hop;
import javax.sip.address.Router;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.ProxyAuthorizationHeader;
import javax.sip.header.ViaHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class AuthenticationHelperImpl
implements AuthenticationHelper {
    private Object accountManager = null;
    private CredentialsCache cachedCredentials;
    private HeaderFactory headerFactory;
    private SipStackImpl sipStack;
    Timer timer;

    public AuthenticationHelperImpl(SipStackImpl sipStackImpl, AccountManager accountManager, HeaderFactory headerFactory) {
        this.accountManager = accountManager;
        this.headerFactory = headerFactory;
        this.sipStack = sipStackImpl;
        this.cachedCredentials = new CredentialsCache(sipStackImpl.getTimer());
    }

    public AuthenticationHelperImpl(SipStackImpl sipStackImpl, SecureAccountManager secureAccountManager, HeaderFactory headerFactory) {
        this.accountManager = secureAccountManager;
        this.headerFactory = headerFactory;
        this.sipStack = sipStackImpl;
        this.cachedCredentials = new CredentialsCache(sipStackImpl.getTimer());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private AuthorizationHeader getAuthorization(String object, String string, String string2, WWWAuthenticateHeader wWWAuthenticateHeader, UserCredentialHash userCredentialHash) {
        String string3 = wWWAuthenticateHeader.getQop() != null ? "auth" : null;
        string2 = MessageDigestAlgorithm.calculateResponse(wWWAuthenticateHeader.getAlgorithm(), userCredentialHash.getHashUserDomainPassword(), wWWAuthenticateHeader.getNonce(), "00000001", "xyz", (String)object, string, string2, string3, this.sipStack.getStackLogger());
        object = wWWAuthenticateHeader instanceof ProxyAuthenticateHeader ? this.headerFactory.createProxyAuthorizationHeader(wWWAuthenticateHeader.getScheme()) : this.headerFactory.createAuthorizationHeader(wWWAuthenticateHeader.getScheme());
        object.setUsername(userCredentialHash.getUserName());
        object.setRealm(wWWAuthenticateHeader.getRealm());
        object.setNonce(wWWAuthenticateHeader.getNonce());
        try {
            object.setParameter("uri", string);
            object.setResponse(string2);
            if (wWWAuthenticateHeader.getAlgorithm() != null) {
                object.setAlgorithm(wWWAuthenticateHeader.getAlgorithm());
            }
            if (wWWAuthenticateHeader.getOpaque() != null) {
                object.setOpaque(wWWAuthenticateHeader.getOpaque());
            }
            if (string3 != null) {
                object.setQop(string3);
                object.setCNonce("xyz");
                object.setNonceCount(Integer.parseInt("00000001"));
            }
            object.setResponse(string2);
            return object;
        }
        catch (ParseException parseException) {
            throw new RuntimeException("Failed to create an authorization header!");
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        throw new RuntimeException("Failed to create an authorization header!");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private AuthorizationHeader getAuthorization(String object, String string, String string2, WWWAuthenticateHeader wWWAuthenticateHeader, UserCredentials userCredentials) {
        String string3 = wWWAuthenticateHeader.getQop() != null ? "auth" : null;
        string2 = MessageDigestAlgorithm.calculateResponse(wWWAuthenticateHeader.getAlgorithm(), userCredentials.getUserName(), wWWAuthenticateHeader.getRealm(), userCredentials.getPassword(), wWWAuthenticateHeader.getNonce(), "00000001", "xyz", (String)object, string, string2, string3, this.sipStack.getStackLogger());
        object = wWWAuthenticateHeader instanceof ProxyAuthenticateHeader ? this.headerFactory.createProxyAuthorizationHeader(wWWAuthenticateHeader.getScheme()) : this.headerFactory.createAuthorizationHeader(wWWAuthenticateHeader.getScheme());
        object.setUsername(userCredentials.getUserName());
        object.setRealm(wWWAuthenticateHeader.getRealm());
        object.setNonce(wWWAuthenticateHeader.getNonce());
        try {
            object.setParameter("uri", string);
            object.setResponse(string2);
            if (wWWAuthenticateHeader.getAlgorithm() != null) {
                object.setAlgorithm(wWWAuthenticateHeader.getAlgorithm());
            }
            if (wWWAuthenticateHeader.getOpaque() != null) {
                object.setOpaque(wWWAuthenticateHeader.getOpaque());
            }
            if (string3 != null) {
                object.setQop(string3);
                object.setCNonce("xyz");
                object.setNonceCount(Integer.parseInt("00000001"));
            }
            object.setResponse(string2);
            return object;
        }
        catch (ParseException parseException) {
            throw new RuntimeException("Failed to create an authorization header!");
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        throw new RuntimeException("Failed to create an authorization header!");
    }

    private void removeBranchID(Request request) {
        ((ViaHeader)request.getHeader("Via")).removeParameter("branch");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ClientTransaction handleChallenge(Response object, ClientTransaction serializable, SipProvider object2, int n) throws SipException, NullPointerException {
        Object object3;
        Serializable serializable2;
        Object object4;
        Object object5;
        try {
            block25 : {
                block24 : {
                    SIPRequest sIPRequest;
                    if (this.sipStack.isLoggingEnabled()) {
                        object4 = this.sipStack.getStackLogger();
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("handleChallenge: ");
                        ((StringBuilder)object5).append(object);
                        object4.logDebug(((StringBuilder)object5).toString());
                    }
                    if ((sIPRequest = (SIPRequest)serializable.getRequest()).getToTag() == null && serializable.getDialog() != null && serializable.getDialog().getState() == DialogState.CONFIRMED) {
                        object4 = serializable.getDialog().createRequest(sIPRequest.getMethod());
                        object5 = sIPRequest.getHeaderNames();
                        while (object5.hasNext()) {
                            object3 = (String)object5.next();
                            if (object4.getHeader((String)object3) == null) continue;
                            object3 = object4.getHeaders((String)object3);
                            while (object3.hasNext()) {
                                object4.addHeader((Header)object3.next());
                            }
                        }
                    } else {
                        object4 = (Request)sIPRequest.clone();
                    }
                    this.removeBranchID((Request)object4);
                    if (object == null || object4 == null) break block24;
                    if (object.getStatusCode() == 401) {
                        object5 = object.getHeaders("WWW-Authenticate");
                    } else {
                        if (object.getStatusCode() != 407) {
                            object = new IllegalArgumentException("Unexpected status code ");
                            throw object;
                        }
                        object5 = object.getHeaders("Proxy-Authenticate");
                    }
                    if (object5 == null) {
                        object = new IllegalArgumentException("Could not find WWWAuthenticate or ProxyAuthenticate headers");
                        throw object;
                    }
                    object4.removeHeader("Authorization");
                    object4.removeHeader("Proxy-Authorization");
                    object3 = (CSeqHeader)object4.getHeader("CSeq");
                    try {
                        object3.setSeqNumber(object3.getSeqNumber() + 1L);
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid CSeq -- could not increment : ");
                        stringBuilder.append(object3.getSeqNumber());
                        serializable = new SipException(stringBuilder.toString());
                        throw serializable;
                    }
                    if (sIPRequest.getRouteHeaders() == null) {
                        object = ((SIPClientTransaction)serializable).getNextHop();
                        serializable2 = (SipURI)object4.getRequestURI();
                        if (!object.getHost().equalsIgnoreCase(serializable2.getHost()) && !object.equals(this.sipStack.getRouter(sIPRequest).getOutboundProxy())) {
                            serializable2.setMAddrParam(object.getHost());
                        }
                        if (object.getPort() != -1) {
                            serializable2.setPort(object.getPort());
                        }
                    }
                    break block25;
                }
                object = new NullPointerException("A null argument was passed to handle challenge.");
                throw object;
            }
            serializable2 = object2.getNewClientTransaction((Request)object4);
            object = (SipURI)serializable.getRequest().getRequestURI();
            object = object4;
            object2 = object3;
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Unexpected exception ", exception);
            throw new SipException("Unexpected exception ", exception);
        }
        catch (SipException sipException) {
            throw sipException;
        }
        while (object5.hasNext()) {
            String string;
            String string2;
            Object object6;
            Object object7 = (WWWAuthenticateHeader)object5.next();
            object3 = object7.getRealm();
            boolean bl = this.accountManager instanceof SecureAccountManager;
            object4 = "";
            if (bl) {
                object6 = ((SecureAccountManager)this.accountManager).getCredentialHash((ClientTransaction)serializable, (String)object3);
                object4 = object.getRequestURI();
                object3 = object6.getSipDomain();
                string2 = object.getMethod();
                string = object4.toString();
                object4 = object.getContent() == null ? "" : new String(object.getRawContent());
                object4 = this.getAuthorization(string2, string, (String)object4, (WWWAuthenticateHeader)object7, (UserCredentialHash)object6);
            } else {
                object6 = ((AccountManager)this.accountManager).getCredentials((ClientTransaction)serializable, (String)object3);
                object3 = object6.getSipDomain();
                string2 = object.getMethod();
                string = object.getRequestURI().toString();
                if (object.getContent() != null) {
                    object4 = new String(object.getRawContent());
                }
                object4 = this.getAuthorization(string2, string, (String)object4, (WWWAuthenticateHeader)object7, (UserCredentials)object6);
            }
            if (this.sipStack.isLoggingEnabled()) {
                object7 = this.sipStack.getStackLogger();
                object6 = new StringBuilder();
                ((StringBuilder)object6).append("Created authorization header: ");
                ((StringBuilder)object6).append(object4.toString());
                object7.logDebug(((StringBuilder)object6).toString());
            }
            if (n != 0) {
                this.cachedCredentials.cacheAuthorizationHeader((String)object3, (AuthorizationHeader)object4, n);
            }
            object.addHeader((Header)object4);
        }
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Returning authorization transaction.");
            ((StringBuilder)serializable).append(serializable2);
            object.logDebug(((StringBuilder)serializable).toString());
        }
        return serializable2;
    }

    @Override
    public void removeCachedAuthenticationHeaders(String string) {
        if (string != null) {
            this.cachedCredentials.removeAuthenticationHeader(string);
            return;
        }
        throw new NullPointerException("Null callId argument ");
    }

    @Override
    public void setAuthenticationHeaders(Request serializable) {
        Object object = ((SIPRequest)serializable).getCallId().getCallId();
        serializable.removeHeader("Authorization");
        Object object2 = this.cachedCredentials.getCachedAuthorizationHeaders((String)object);
        if (object2 == null) {
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Could not find authentication headers for ");
                ((StringBuilder)serializable).append((String)object);
                object2.logDebug(((StringBuilder)serializable).toString());
            }
            return;
        }
        object = object2.iterator();
        while (object.hasNext()) {
            serializable.addHeader((AuthorizationHeader)object.next());
        }
    }
}

