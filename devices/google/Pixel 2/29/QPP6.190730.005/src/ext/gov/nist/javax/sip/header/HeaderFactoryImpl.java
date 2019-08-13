/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.AcceptLanguage;
import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.AllowEvents;
import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.CallInfo;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContentDisposition;
import gov.nist.javax.sip.header.ContentEncoding;
import gov.nist.javax.sip.header.ContentLanguage;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.HeaderFactoryExt;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.MimeVersion;
import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.RAck;
import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.ReferTo;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.SIPDateHeader;
import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.SIPIfMatch;
import gov.nist.javax.sip.header.Server;
import gov.nist.javax.sip.header.SipRequestLine;
import gov.nist.javax.sip.header.SipStatusLine;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.Subject;
import gov.nist.javax.sip.header.SubscriptionState;
import gov.nist.javax.sip.header.Supported;
import gov.nist.javax.sip.header.TimeStamp;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Unsupported;
import gov.nist.javax.sip.header.UserAgent;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.WWWAuthenticate;
import gov.nist.javax.sip.header.Warning;
import gov.nist.javax.sip.header.extensions.Join;
import gov.nist.javax.sip.header.extensions.JoinHeader;
import gov.nist.javax.sip.header.extensions.MinSE;
import gov.nist.javax.sip.header.extensions.References;
import gov.nist.javax.sip.header.extensions.ReferencesHeader;
import gov.nist.javax.sip.header.extensions.ReferredBy;
import gov.nist.javax.sip.header.extensions.ReferredByHeader;
import gov.nist.javax.sip.header.extensions.Replaces;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import gov.nist.javax.sip.header.extensions.SessionExpires;
import gov.nist.javax.sip.header.extensions.SessionExpiresHeader;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfo;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfoHeader;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssertedIdentityHeader;
import gov.nist.javax.sip.header.ims.PAssertedService;
import gov.nist.javax.sip.header.ims.PAssertedServiceHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PAssociatedURIHeader;
import gov.nist.javax.sip.header.ims.PCalledPartyID;
import gov.nist.javax.sip.header.ims.PCalledPartyIDHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddresses;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddressesHeader;
import gov.nist.javax.sip.header.ims.PChargingVector;
import gov.nist.javax.sip.header.ims.PChargingVectorHeader;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentity;
import gov.nist.javax.sip.header.ims.PPreferredIdentityHeader;
import gov.nist.javax.sip.header.ims.PPreferredService;
import gov.nist.javax.sip.header.ims.PPreferredServiceHeader;
import gov.nist.javax.sip.header.ims.PProfileKey;
import gov.nist.javax.sip.header.ims.PProfileKeyHeader;
import gov.nist.javax.sip.header.ims.PServedUser;
import gov.nist.javax.sip.header.ims.PServedUserHeader;
import gov.nist.javax.sip.header.ims.PUserDatabase;
import gov.nist.javax.sip.header.ims.PUserDatabaseHeader;
import gov.nist.javax.sip.header.ims.PVisitedNetworkID;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDHeader;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.PathHeader;
import gov.nist.javax.sip.header.ims.Privacy;
import gov.nist.javax.sip.header.ims.PrivacyHeader;
import gov.nist.javax.sip.header.ims.SecurityClient;
import gov.nist.javax.sip.header.ims.SecurityClientHeader;
import gov.nist.javax.sip.header.ims.SecurityServer;
import gov.nist.javax.sip.header.ims.SecurityServerHeader;
import gov.nist.javax.sip.header.ims.SecurityVerify;
import gov.nist.javax.sip.header.ims.SecurityVerifyHeader;
import gov.nist.javax.sip.header.ims.ServiceRoute;
import gov.nist.javax.sip.header.ims.ServiceRouteHeader;
import gov.nist.javax.sip.parser.RequestLineParser;
import gov.nist.javax.sip.parser.StatusLineParser;
import gov.nist.javax.sip.parser.StringMsgParser;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.sip.InvalidArgumentException;
import javax.sip.address.Address;
import javax.sip.address.URI;
import javax.sip.header.AcceptEncodingHeader;
import javax.sip.header.AcceptHeader;
import javax.sip.header.AcceptLanguageHeader;
import javax.sip.header.AlertInfoHeader;
import javax.sip.header.AllowEventsHeader;
import javax.sip.header.AllowHeader;
import javax.sip.header.AuthenticationInfoHeader;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.CallInfoHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentEncodingHeader;
import javax.sip.header.ContentLanguageHeader;
import javax.sip.header.ContentLengthHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.DateHeader;
import javax.sip.header.ErrorInfoHeader;
import javax.sip.header.EventHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.ExtensionHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;
import javax.sip.header.InReplyToHeader;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.MimeVersionHeader;
import javax.sip.header.MinExpiresHeader;
import javax.sip.header.OrganizationHeader;
import javax.sip.header.PriorityHeader;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.ProxyAuthorizationHeader;
import javax.sip.header.ProxyRequireHeader;
import javax.sip.header.RAckHeader;
import javax.sip.header.RSeqHeader;
import javax.sip.header.ReasonHeader;
import javax.sip.header.RecordRouteHeader;
import javax.sip.header.ReferToHeader;
import javax.sip.header.ReplyToHeader;
import javax.sip.header.RequireHeader;
import javax.sip.header.RetryAfterHeader;
import javax.sip.header.RouteHeader;
import javax.sip.header.SIPETagHeader;
import javax.sip.header.SIPIfMatchHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.SubjectHeader;
import javax.sip.header.SubscriptionStateHeader;
import javax.sip.header.SupportedHeader;
import javax.sip.header.TimeStampHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.UnsupportedHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.header.ViaHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.header.WarningHeader;

public class HeaderFactoryImpl
implements HeaderFactory,
HeaderFactoryExt {
    private boolean stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");

    @Override
    public AcceptEncodingHeader createAcceptEncodingHeader(String string) throws ParseException {
        if (string != null) {
            AcceptEncoding acceptEncoding = new AcceptEncoding();
            acceptEncoding.setEncoding(string);
            return acceptEncoding;
        }
        throw new NullPointerException("the encoding parameter is null");
    }

    @Override
    public AcceptHeader createAcceptHeader(String string, String string2) throws ParseException {
        if (string != null && string2 != null) {
            Accept accept = new Accept();
            accept.setContentType(string);
            accept.setContentSubType(string2);
            return accept;
        }
        throw new NullPointerException("contentType or subtype is null ");
    }

    @Override
    public AcceptLanguageHeader createAcceptLanguageHeader(Locale locale) {
        if (locale != null) {
            AcceptLanguage acceptLanguage = new AcceptLanguage();
            acceptLanguage.setAcceptLanguage(locale);
            return acceptLanguage;
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public AlertInfoHeader createAlertInfoHeader(URI uRI) {
        if (uRI != null) {
            AlertInfo alertInfo = new AlertInfo();
            alertInfo.setAlertInfo(uRI);
            return alertInfo;
        }
        throw new NullPointerException("null arg alertInfo");
    }

    @Override
    public AllowEventsHeader createAllowEventsHeader(String string) throws ParseException {
        if (string != null) {
            AllowEvents allowEvents = new AllowEvents();
            allowEvents.setEventType(string);
            return allowEvents;
        }
        throw new NullPointerException("null arg eventType");
    }

    @Override
    public AllowHeader createAllowHeader(String string) throws ParseException {
        if (string != null) {
            Allow allow = new Allow();
            allow.setMethod(string);
            return allow;
        }
        throw new NullPointerException("null arg method");
    }

    @Override
    public AuthenticationInfoHeader createAuthenticationInfoHeader(String string) throws ParseException {
        if (string != null) {
            AuthenticationInfo authenticationInfo = new AuthenticationInfo();
            authenticationInfo.setResponse(string);
            return authenticationInfo;
        }
        throw new NullPointerException("null arg response");
    }

    @Override
    public AuthorizationHeader createAuthorizationHeader(String string) throws ParseException {
        if (string != null) {
            Authorization authorization = new Authorization();
            authorization.setScheme(string);
            return authorization;
        }
        throw new NullPointerException("null arg scheme ");
    }

    @Override
    public CSeqHeader createCSeqHeader(int n, String string) throws ParseException, InvalidArgumentException {
        return this.createCSeqHeader((long)n, string);
    }

    @Override
    public CSeqHeader createCSeqHeader(long l, String charSequence) throws ParseException, InvalidArgumentException {
        if (l >= 0L) {
            if (charSequence != null) {
                CSeq cSeq = new CSeq();
                cSeq.setMethod((String)charSequence);
                cSeq.setSeqNumber(l);
                return cSeq;
            }
            throw new NullPointerException("null arg method");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("bad arg ");
        ((StringBuilder)charSequence).append(l);
        throw new InvalidArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public CallIdHeader createCallIdHeader(String string) throws ParseException {
        if (string != null) {
            CallID callID = new CallID();
            callID.setCallId(string);
            return callID;
        }
        throw new NullPointerException("null arg callId");
    }

    @Override
    public CallInfoHeader createCallInfoHeader(URI uRI) {
        if (uRI != null) {
            CallInfo callInfo = new CallInfo();
            callInfo.setInfo(uRI);
            return callInfo;
        }
        throw new NullPointerException("null arg callInfo");
    }

    @Override
    public PChargingVectorHeader createChargingVectorHeader(String string) throws ParseException {
        if (string != null) {
            PChargingVector pChargingVector = new PChargingVector();
            pChargingVector.setICID(string);
            return pChargingVector;
        }
        throw new NullPointerException("null icid arg!");
    }

    @Override
    public ContactHeader createContactHeader() {
        Contact contact = new Contact();
        contact.setWildCardFlag(true);
        contact.setExpires(0);
        return contact;
    }

    @Override
    public ContactHeader createContactHeader(Address address) {
        if (address != null) {
            Contact contact = new Contact();
            contact.setAddress(address);
            return contact;
        }
        throw new NullPointerException("null arg address");
    }

    @Override
    public ContentDispositionHeader createContentDispositionHeader(String string) throws ParseException {
        if (string != null) {
            ContentDisposition contentDisposition = new ContentDisposition();
            contentDisposition.setDispositionType(string);
            return contentDisposition;
        }
        throw new NullPointerException("null arg contentDisposition");
    }

    @Override
    public ContentEncodingHeader createContentEncodingHeader(String string) throws ParseException {
        if (string != null) {
            ContentEncoding contentEncoding = new ContentEncoding();
            contentEncoding.setEncoding(string);
            return contentEncoding;
        }
        throw new NullPointerException("null encoding");
    }

    @Override
    public ContentLanguageHeader createContentLanguageHeader(Locale locale) {
        if (locale != null) {
            ContentLanguage contentLanguage = new ContentLanguage();
            contentLanguage.setContentLanguage(locale);
            return contentLanguage;
        }
        throw new NullPointerException("null arg contentLanguage");
    }

    @Override
    public ContentLengthHeader createContentLengthHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            ContentLength contentLength = new ContentLength();
            contentLength.setContentLength(n);
            return contentLength;
        }
        throw new InvalidArgumentException("bad contentLength");
    }

    @Override
    public ContentTypeHeader createContentTypeHeader(String string, String string2) throws ParseException {
        if (string != null && string2 != null) {
            ContentType contentType = new ContentType();
            contentType.setContentType(string);
            contentType.setContentSubType(string2);
            return contentType;
        }
        throw new NullPointerException("null contentType or subType");
    }

    @Override
    public DateHeader createDateHeader(Calendar calendar) {
        SIPDateHeader sIPDateHeader = new SIPDateHeader();
        if (calendar != null) {
            sIPDateHeader.setDate(calendar);
            return sIPDateHeader;
        }
        throw new NullPointerException("null date");
    }

    @Override
    public ErrorInfoHeader createErrorInfoHeader(URI uRI) {
        if (uRI != null) {
            return new ErrorInfo((GenericURI)uRI);
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public EventHeader createEventHeader(String string) throws ParseException {
        if (string != null) {
            Event event = new Event();
            event.setEventType(string);
            return event;
        }
        throw new NullPointerException("null eventType");
    }

    @Override
    public ExpiresHeader createExpiresHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            Expires expires = new Expires();
            expires.setExpires(n);
            return expires;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad value ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public ExtensionHeader createExtensionHeader(String string, String string2) throws ParseException {
        if (string != null) {
            ExtensionHeaderImpl extensionHeaderImpl = new ExtensionHeaderImpl();
            extensionHeaderImpl.setName(string);
            extensionHeaderImpl.setValue(string2);
            return extensionHeaderImpl;
        }
        throw new NullPointerException("bad name");
    }

    @Override
    public FromHeader createFromHeader(Address address, String string) throws ParseException {
        if (address != null) {
            From from = new From();
            from.setAddress(address);
            if (string != null) {
                from.setTag(string);
            }
            return from;
        }
        throw new NullPointerException("null address arg");
    }

    @Override
    public Header createHeader(String object) throws ParseException {
        Serializable serializable = new StringMsgParser().parseSIPHeader(((String)object).trim());
        if (serializable instanceof SIPHeaderList) {
            if (((SIPHeaderList)serializable).size() <= 1) {
                if (((SIPHeaderList)serializable).size() == 0) {
                    try {
                        object = (Header)((SIPHeaderList)serializable).getMyClass().newInstance();
                        return object;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                        return null;
                    }
                    catch (InstantiationException instantiationException) {
                        instantiationException.printStackTrace();
                        return null;
                    }
                }
                return ((SIPHeaderList)serializable).getFirst();
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Only singleton allowed ");
            ((StringBuilder)serializable).append((String)object);
            throw new ParseException(((StringBuilder)serializable).toString(), 0);
        }
        return serializable;
    }

    @Override
    public Header createHeader(String string, String string2) throws ParseException {
        if (string != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(string);
            stringBuffer.append(":");
            stringBuffer.append(string2);
            return this.createHeader(stringBuffer.toString());
        }
        throw new NullPointerException("header name is null");
    }

    @Override
    public List createHeaders(String object) throws ParseException {
        if (object != null) {
            if ((object = new StringMsgParser().parseSIPHeader((String)object)) instanceof SIPHeaderList) {
                return (SIPHeaderList)object;
            }
            throw new ParseException("List of headers of this type is not allowed in a message", 0);
        }
        throw new NullPointerException("null arg!");
    }

    @Override
    public InReplyToHeader createInReplyToHeader(String string) throws ParseException {
        if (string != null) {
            InReplyTo inReplyTo = new InReplyTo();
            inReplyTo.setCallId(string);
            return inReplyTo;
        }
        throw new NullPointerException("null callId arg");
    }

    @Override
    public JoinHeader createJoinHeader(String string, String string2, String string3) throws ParseException {
        Join join = new Join();
        join.setCallId(string);
        join.setFromTag(string3);
        join.setToTag(string2);
        return join;
    }

    @Override
    public MaxForwardsHeader createMaxForwardsHeader(int n) throws InvalidArgumentException {
        if (n >= 0 && n <= 255) {
            MaxForwards maxForwards = new MaxForwards();
            maxForwards.setMaxForwards(n);
            return maxForwards;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad maxForwards arg ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public MimeVersionHeader createMimeVersionHeader(int n, int n2) throws InvalidArgumentException {
        if (n >= 0 && n2 >= 0) {
            MimeVersion mimeVersion = new MimeVersion();
            mimeVersion.setMajorVersion(n);
            mimeVersion.setMinorVersion(n2);
            return mimeVersion;
        }
        throw new InvalidArgumentException("bad major/minor version");
    }

    @Override
    public MinExpiresHeader createMinExpiresHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            MinExpires minExpires = new MinExpires();
            minExpires.setExpires(n);
            return minExpires;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad minExpires ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    public ExtensionHeader createMinSEHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            MinSE minSE = new MinSE();
            minSE.setExpires(n);
            return minSE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad value ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public OrganizationHeader createOrganizationHeader(String string) throws ParseException {
        if (string != null) {
            Organization organization = new Organization();
            organization.setOrganization(string);
            return organization;
        }
        throw new NullPointerException("bad organization arg");
    }

    @Override
    public PAccessNetworkInfoHeader createPAccessNetworkInfoHeader() {
        return new PAccessNetworkInfo();
    }

    @Override
    public PAssertedIdentityHeader createPAssertedIdentityHeader(Address address) throws NullPointerException, ParseException {
        if (address != null) {
            PAssertedIdentity pAssertedIdentity = new PAssertedIdentity();
            pAssertedIdentity.setAddress(address);
            return pAssertedIdentity;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public PAssertedServiceHeader createPAssertedServiceHeader() {
        return new PAssertedService();
    }

    @Override
    public PAssociatedURIHeader createPAssociatedURIHeader(Address address) {
        if (address != null) {
            PAssociatedURI pAssociatedURI = new PAssociatedURI();
            pAssociatedURI.setAddress(address);
            return pAssociatedURI;
        }
        throw new NullPointerException("null associatedURI!");
    }

    @Override
    public PCalledPartyIDHeader createPCalledPartyIDHeader(Address address) {
        if (address != null) {
            PCalledPartyID pCalledPartyID = new PCalledPartyID();
            pCalledPartyID.setAddress(address);
            return pCalledPartyID;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public PChargingFunctionAddressesHeader createPChargingFunctionAddressesHeader() {
        return new PChargingFunctionAddresses();
    }

    @Override
    public PMediaAuthorizationHeader createPMediaAuthorizationHeader(String string) throws InvalidArgumentException, ParseException {
        if (string != null && string != "") {
            PMediaAuthorization pMediaAuthorization = new PMediaAuthorization();
            pMediaAuthorization.setMediaAuthorizationToken(string);
            return pMediaAuthorization;
        }
        throw new InvalidArgumentException("The Media-Authorization-Token parameter is null or empty");
    }

    @Override
    public PPreferredIdentityHeader createPPreferredIdentityHeader(Address address) {
        if (address != null) {
            PPreferredIdentity pPreferredIdentity = new PPreferredIdentity();
            pPreferredIdentity.setAddress(address);
            return pPreferredIdentity;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public PPreferredServiceHeader createPPreferredServiceHeader() {
        return new PPreferredService();
    }

    @Override
    public PProfileKeyHeader createPProfileKeyHeader(Address address) {
        if (address != null) {
            PProfileKey pProfileKey = new PProfileKey();
            pProfileKey.setAddress(address);
            return pProfileKey;
        }
        throw new NullPointerException("Address is null");
    }

    @Override
    public PServedUserHeader createPServedUserHeader(Address address) {
        if (address != null) {
            PServedUser pServedUser = new PServedUser();
            pServedUser.setAddress(address);
            return pServedUser;
        }
        throw new NullPointerException("Address is null");
    }

    @Override
    public PUserDatabaseHeader createPUserDatabaseHeader(String string) {
        if (string != null && !string.equals(" ")) {
            PUserDatabase pUserDatabase = new PUserDatabase();
            pUserDatabase.setDatabaseName(string);
            return pUserDatabase;
        }
        throw new NullPointerException("Database name is null");
    }

    @Override
    public PVisitedNetworkIDHeader createPVisitedNetworkIDHeader() {
        return new PVisitedNetworkID();
    }

    @Override
    public PathHeader createPathHeader(Address address) {
        if (address != null) {
            Path path = new Path();
            path.setAddress(address);
            return path;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public PriorityHeader createPriorityHeader(String string) throws ParseException {
        if (string != null) {
            Priority priority = new Priority();
            priority.setPriority(string);
            return priority;
        }
        throw new NullPointerException("bad priority arg");
    }

    @Override
    public PrivacyHeader createPrivacyHeader(String string) {
        if (string != null) {
            return new Privacy(string);
        }
        throw new NullPointerException("null privacyType arg");
    }

    @Override
    public ProxyAuthenticateHeader createProxyAuthenticateHeader(String string) throws ParseException {
        if (string != null) {
            ProxyAuthenticate proxyAuthenticate = new ProxyAuthenticate();
            proxyAuthenticate.setScheme(string);
            return proxyAuthenticate;
        }
        throw new NullPointerException("bad scheme arg");
    }

    @Override
    public ProxyAuthorizationHeader createProxyAuthorizationHeader(String string) throws ParseException {
        if (string != null) {
            ProxyAuthorization proxyAuthorization = new ProxyAuthorization();
            proxyAuthorization.setScheme(string);
            return proxyAuthorization;
        }
        throw new NullPointerException("bad scheme arg");
    }

    @Override
    public ProxyRequireHeader createProxyRequireHeader(String string) throws ParseException {
        if (string != null) {
            ProxyRequire proxyRequire = new ProxyRequire();
            proxyRequire.setOptionTag(string);
            return proxyRequire;
        }
        throw new NullPointerException("bad optionTag arg");
    }

    @Override
    public RAckHeader createRAckHeader(int n, int n2, String string) throws InvalidArgumentException, ParseException {
        return this.createRAckHeader((long)n, (long)n2, string);
    }

    @Override
    public RAckHeader createRAckHeader(long l, long l2, String string) throws InvalidArgumentException, ParseException {
        if (string != null) {
            if (l2 >= 0L && l >= 0L) {
                RAck rAck = new RAck();
                rAck.setMethod(string);
                rAck.setCSequenceNumber(l2);
                rAck.setRSequenceNumber(l);
                return rAck;
            }
            throw new InvalidArgumentException("bad cseq/rseq arg");
        }
        throw new NullPointerException("Bad method");
    }

    @Override
    public RSeqHeader createRSeqHeader(int n) throws InvalidArgumentException {
        return this.createRSeqHeader((long)n);
    }

    @Override
    public RSeqHeader createRSeqHeader(long l) throws InvalidArgumentException {
        if (l >= 0L) {
            RSeq rSeq = new RSeq();
            rSeq.setSeqNumber(l);
            return rSeq;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid sequenceNumber arg ");
        stringBuilder.append(l);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public ReasonHeader createReasonHeader(String string, int n, String string2) throws InvalidArgumentException, ParseException {
        if (string != null) {
            if (n >= 0) {
                Reason reason = new Reason();
                reason.setProtocol(string);
                reason.setCause(n);
                reason.setText(string2);
                return reason;
            }
            throw new InvalidArgumentException("bad cause");
        }
        throw new NullPointerException("bad protocol arg");
    }

    @Override
    public RecordRouteHeader createRecordRouteHeader(Address address) {
        if (address != null) {
            RecordRoute recordRoute = new RecordRoute();
            recordRoute.setAddress(address);
            return recordRoute;
        }
        throw new NullPointerException("Null argument!");
    }

    @Override
    public ReferToHeader createReferToHeader(Address address) {
        if (address != null) {
            ReferTo referTo = new ReferTo();
            referTo.setAddress(address);
            return referTo;
        }
        throw new NullPointerException("null address!");
    }

    public ReferencesHeader createReferencesHeader(String string, String string2) throws ParseException {
        References references = new References();
        references.setCallId(string);
        references.setRel(string2);
        return references;
    }

    @Override
    public ReferredByHeader createReferredByHeader(Address address) {
        if (address != null) {
            ReferredBy referredBy = new ReferredBy();
            referredBy.setAddress(address);
            return referredBy;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public ReplacesHeader createReplacesHeader(String string, String string2, String string3) throws ParseException {
        Replaces replaces = new Replaces();
        replaces.setCallId(string);
        replaces.setFromTag(string3);
        replaces.setToTag(string2);
        return replaces;
    }

    @Override
    public ReplyToHeader createReplyToHeader(Address address) {
        if (address != null) {
            ReplyTo replyTo = new ReplyTo();
            replyTo.setAddress(address);
            return replyTo;
        }
        throw new NullPointerException("null address");
    }

    @Override
    public SipRequestLine createRequestLine(String string) throws ParseException {
        return new RequestLineParser(string).parse();
    }

    @Override
    public RequireHeader createRequireHeader(String string) throws ParseException {
        if (string != null) {
            Require require = new Require();
            require.setOptionTag(string);
            return require;
        }
        throw new NullPointerException("null optionTag");
    }

    @Override
    public RetryAfterHeader createRetryAfterHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            RetryAfter retryAfter = new RetryAfter();
            retryAfter.setRetryAfter(n);
            return retryAfter;
        }
        throw new InvalidArgumentException("bad retryAfter arg");
    }

    @Override
    public RouteHeader createRouteHeader(Address address) {
        if (address != null) {
            Route route = new Route();
            route.setAddress(address);
            return route;
        }
        throw new NullPointerException("null address arg");
    }

    @Override
    public SIPETagHeader createSIPETagHeader(String string) throws ParseException {
        return new SIPETag(string);
    }

    @Override
    public SIPIfMatchHeader createSIPIfMatchHeader(String string) throws ParseException {
        return new SIPIfMatch(string);
    }

    @Override
    public SecurityClientHeader createSecurityClientHeader() {
        return new SecurityClient();
    }

    @Override
    public SecurityServerHeader createSecurityServerHeader() {
        return new SecurityServer();
    }

    @Override
    public SecurityVerifyHeader createSecurityVerifyHeader() {
        return new SecurityVerify();
    }

    @Override
    public ServerHeader createServerHeader(List list) throws ParseException {
        if (list != null) {
            Server server = new Server();
            server.setProduct(list);
            return server;
        }
        throw new NullPointerException("null productList arg");
    }

    @Override
    public ServiceRouteHeader createServiceRouteHeader(Address address) {
        if (address != null) {
            ServiceRoute serviceRoute = new ServiceRoute();
            serviceRoute.setAddress(address);
            return serviceRoute;
        }
        throw new NullPointerException("null address!");
    }

    @Override
    public SessionExpiresHeader createSessionExpiresHeader(int n) throws InvalidArgumentException {
        if (n >= 0) {
            SessionExpires sessionExpires = new SessionExpires();
            sessionExpires.setExpires(n);
            return sessionExpires;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad value ");
        stringBuilder.append(n);
        throw new InvalidArgumentException(stringBuilder.toString());
    }

    @Override
    public SipStatusLine createStatusLine(String string) throws ParseException {
        return new StatusLineParser(string).parse();
    }

    @Override
    public SubjectHeader createSubjectHeader(String string) throws ParseException {
        if (string != null) {
            Subject subject = new Subject();
            subject.setSubject(string);
            return subject;
        }
        throw new NullPointerException("null subject arg");
    }

    @Override
    public SubscriptionStateHeader createSubscriptionStateHeader(String string) throws ParseException {
        if (string != null) {
            SubscriptionState subscriptionState = new SubscriptionState();
            subscriptionState.setState(string);
            return subscriptionState;
        }
        throw new NullPointerException("null subscriptionState arg");
    }

    @Override
    public SupportedHeader createSupportedHeader(String string) throws ParseException {
        if (string != null) {
            Supported supported = new Supported();
            supported.setOptionTag(string);
            return supported;
        }
        throw new NullPointerException("null optionTag arg");
    }

    @Override
    public TimeStampHeader createTimeStampHeader(float f) throws InvalidArgumentException {
        if (!(f < 0.0f)) {
            TimeStamp timeStamp = new TimeStamp();
            timeStamp.setTimeStamp(f);
            return timeStamp;
        }
        throw new IllegalArgumentException("illegal timeStamp");
    }

    @Override
    public ToHeader createToHeader(Address address, String string) throws ParseException {
        if (address != null) {
            To to = new To();
            to.setAddress(address);
            if (string != null) {
                to.setTag(string);
            }
            return to;
        }
        throw new NullPointerException("null address");
    }

    @Override
    public UnsupportedHeader createUnsupportedHeader(String string) throws ParseException {
        if (string != null) {
            Unsupported unsupported = new Unsupported();
            unsupported.setOptionTag(string);
            return unsupported;
        }
        throw new NullPointerException(string);
    }

    @Override
    public UserAgentHeader createUserAgentHeader(List list) throws ParseException {
        if (list != null) {
            UserAgent userAgent = new UserAgent();
            userAgent.setProduct(list);
            return userAgent;
        }
        throw new NullPointerException("null user agent");
    }

    @Override
    public ViaHeader createViaHeader(String charSequence, int n, String string, String string2) throws ParseException, InvalidArgumentException {
        if (charSequence != null && string != null) {
            Via via = new Via();
            if (string2 != null) {
                via.setBranch(string2);
            }
            string2 = charSequence;
            if (((String)charSequence).indexOf(58) >= 0) {
                string2 = charSequence;
                if (((String)charSequence).indexOf(91) < 0) {
                    string2 = charSequence;
                    if (this.stripAddressScopeZones) {
                        int n2 = ((String)charSequence).indexOf(37);
                        string2 = charSequence;
                        if (n2 != -1) {
                            string2 = ((String)charSequence).substring(0, n2);
                        }
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append('[');
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(']');
                    string2 = ((StringBuilder)charSequence).toString();
                }
            }
            via.setHost(string2);
            via.setPort(n);
            via.setTransport(string);
            return via;
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public WWWAuthenticateHeader createWWWAuthenticateHeader(String string) throws ParseException {
        if (string != null) {
            WWWAuthenticate wWWAuthenticate = new WWWAuthenticate();
            wWWAuthenticate.setScheme(string);
            return wWWAuthenticate;
        }
        throw new NullPointerException("null scheme");
    }

    @Override
    public WarningHeader createWarningHeader(String string, int n, String string2) throws ParseException, InvalidArgumentException {
        if (string != null) {
            Warning warning = new Warning();
            warning.setAgent(string);
            warning.setCode(n);
            warning.setText(string2);
            return warning;
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public void setPrettyEncoding(boolean bl) {
        SIPHeaderList.setPrettyEncode(bl);
    }
}

