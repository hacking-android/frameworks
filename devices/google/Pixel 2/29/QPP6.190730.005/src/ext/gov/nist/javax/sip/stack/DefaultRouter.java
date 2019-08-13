/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.stack.HopImpl;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.sip.SipException;
import javax.sip.SipStack;
import javax.sip.address.Address;
import javax.sip.address.Hop;
import javax.sip.address.Router;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;

public class DefaultRouter
implements Router {
    private Hop defaultRoute;
    private SipStackImpl sipStack;

    private DefaultRouter() {
    }

    public DefaultRouter(SipStack sipStack, String string) {
        this.sipStack = (SipStackImpl)sipStack;
        if (string != null) {
            try {
                AddressResolver addressResolver = this.sipStack.getAddressResolver();
                HopImpl hopImpl = new HopImpl(string);
                this.defaultRoute = addressResolver.resolveAddress(hopImpl);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ((SIPTransactionStack)((Object)sipStack)).getStackLogger().logError("Invalid default route specification - need host:port/transport");
                throw illegalArgumentException;
            }
        }
    }

    private final Hop createHop(SipURI object, Request request) {
        String string = object.isSecure() ? "tls" : object.getTransportParam();
        String string2 = string;
        if (string == null) {
            string2 = ((ViaHeader)request.getHeader("Via")).getTransport();
        }
        int n = object.getPort() != -1 ? object.getPort() : (string2.equalsIgnoreCase("tls") ? 5061 : 5060);
        object = object.getMAddrParam() != null ? object.getMAddrParam() : object.getHost();
        return this.sipStack.getAddressResolver().resolveAddress(new HopImpl((String)object, n, string2));
    }

    public void fixStrictRouting(SIPRequest sIPRequest) {
        Serializable serializable = sIPRequest.getRouteHeaders();
        Object object = (SipUri)((Route)((SIPHeaderList)serializable).getFirst()).getAddress().getURI();
        ((SIPHeaderList)serializable).removeFirst();
        AddressImpl addressImpl = new AddressImpl();
        addressImpl.setAddess(sIPRequest.getRequestURI());
        ((SIPHeaderList)serializable).add(new Route(addressImpl));
        sIPRequest.setRequestURI((URI)object);
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("post: fixStrictRouting");
            ((StringBuilder)serializable).append(sIPRequest);
            object.logDebug(((StringBuilder)serializable).toString());
        }
    }

    @Override
    public Hop getNextHop(Request object) throws SipException {
        Object object2 = (SIPRequest)object;
        Object object3 = ((SIPRequest)object2).getRequestLine();
        if (object3 == null) {
            return this.defaultRoute;
        }
        GenericURI genericURI = ((RequestLine)object3).getUri();
        if (genericURI != null) {
            object3 = ((SIPMessage)object2).getRouteHeaders();
            if (object3 != null) {
                if ((object3 = ((Route)((SIPHeaderList)object3).getFirst()).getAddress().getURI()).isSipURI()) {
                    if (!(object3 = (SipURI)object3).hasLrParam()) {
                        this.fixStrictRouting((SIPRequest)object2);
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("Route post processing fixed strict routing");
                        }
                    }
                    object = this.createHop((SipURI)object3, (Request)object);
                    if (this.sipStack.isLoggingEnabled()) {
                        object2 = this.sipStack.getStackLogger();
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("NextHop based on Route:");
                        ((StringBuilder)object3).append(object);
                        object2.logDebug(((StringBuilder)object3).toString());
                    }
                    return object;
                }
                throw new SipException("First Route not a SIP URI");
            }
            if (genericURI.isSipURI() && ((SipURI)((Object)genericURI)).getMAddrParam() != null) {
                object3 = this.createHop((SipURI)((Object)genericURI), (Request)object);
                if (this.sipStack.isLoggingEnabled()) {
                    object = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Using request URI maddr to route the request = ");
                    ((StringBuilder)object2).append(object3.toString());
                    object.logDebug(((StringBuilder)object2).toString());
                }
                return object3;
            }
            if (this.defaultRoute != null) {
                if (this.sipStack.isLoggingEnabled()) {
                    object2 = this.sipStack.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Using outbound proxy to route the request = ");
                    ((StringBuilder)object).append(this.defaultRoute.toString());
                    object2.logDebug(((StringBuilder)object).toString());
                }
                return this.defaultRoute;
            }
            if (genericURI.isSipURI()) {
                object3 = this.createHop((SipURI)((Object)genericURI), (Request)object);
                if (object3 != null && this.sipStack.isLoggingEnabled()) {
                    object = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Used request-URI for nextHop = ");
                    ((StringBuilder)object2).append(object3.toString());
                    object.logDebug(((StringBuilder)object2).toString());
                } else if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("returning null hop -- loop detected");
                }
                return object3;
            }
            InternalErrorHandler.handleException("Unexpected non-sip URI", this.sipStack.getStackLogger());
            return null;
        }
        throw new IllegalArgumentException("Bad message: Null requestURI");
    }

    @Override
    public ListIterator getNextHops(Request object) {
        try {
            LinkedList<Hop> linkedList = new LinkedList<Hop>();
            linkedList.add(this.getNextHop((Request)object));
            object = linkedList.listIterator();
            return object;
        }
        catch (SipException sipException) {
            return null;
        }
    }

    @Override
    public Hop getOutboundProxy() {
        return this.defaultRoute;
    }
}

