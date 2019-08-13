/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.header.SIPHeaderNamesCache;
import gov.nist.javax.sip.parser.AcceptEncodingParser;
import gov.nist.javax.sip.parser.AcceptLanguageParser;
import gov.nist.javax.sip.parser.AcceptParser;
import gov.nist.javax.sip.parser.AlertInfoParser;
import gov.nist.javax.sip.parser.AllowEventsParser;
import gov.nist.javax.sip.parser.AllowParser;
import gov.nist.javax.sip.parser.AuthenticationInfoParser;
import gov.nist.javax.sip.parser.AuthorizationParser;
import gov.nist.javax.sip.parser.CSeqParser;
import gov.nist.javax.sip.parser.CallIDParser;
import gov.nist.javax.sip.parser.CallInfoParser;
import gov.nist.javax.sip.parser.ContactParser;
import gov.nist.javax.sip.parser.ContentDispositionParser;
import gov.nist.javax.sip.parser.ContentEncodingParser;
import gov.nist.javax.sip.parser.ContentLanguageParser;
import gov.nist.javax.sip.parser.ContentLengthParser;
import gov.nist.javax.sip.parser.ContentTypeParser;
import gov.nist.javax.sip.parser.DateParser;
import gov.nist.javax.sip.parser.ErrorInfoParser;
import gov.nist.javax.sip.parser.EventParser;
import gov.nist.javax.sip.parser.ExpiresParser;
import gov.nist.javax.sip.parser.FromParser;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.InReplyToParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.MaxForwardsParser;
import gov.nist.javax.sip.parser.MimeVersionParser;
import gov.nist.javax.sip.parser.MinExpiresParser;
import gov.nist.javax.sip.parser.OrganizationParser;
import gov.nist.javax.sip.parser.PriorityParser;
import gov.nist.javax.sip.parser.ProxyAuthenticateParser;
import gov.nist.javax.sip.parser.ProxyAuthorizationParser;
import gov.nist.javax.sip.parser.ProxyRequireParser;
import gov.nist.javax.sip.parser.RAckParser;
import gov.nist.javax.sip.parser.RSeqParser;
import gov.nist.javax.sip.parser.ReasonParser;
import gov.nist.javax.sip.parser.RecordRouteParser;
import gov.nist.javax.sip.parser.ReferToParser;
import gov.nist.javax.sip.parser.ReplyToParser;
import gov.nist.javax.sip.parser.RequireParser;
import gov.nist.javax.sip.parser.RetryAfterParser;
import gov.nist.javax.sip.parser.RouteParser;
import gov.nist.javax.sip.parser.SIPETagParser;
import gov.nist.javax.sip.parser.SIPIfMatchParser;
import gov.nist.javax.sip.parser.ServerParser;
import gov.nist.javax.sip.parser.SubjectParser;
import gov.nist.javax.sip.parser.SubscriptionStateParser;
import gov.nist.javax.sip.parser.SupportedParser;
import gov.nist.javax.sip.parser.TimeStampParser;
import gov.nist.javax.sip.parser.ToParser;
import gov.nist.javax.sip.parser.UnsupportedParser;
import gov.nist.javax.sip.parser.UserAgentParser;
import gov.nist.javax.sip.parser.ViaParser;
import gov.nist.javax.sip.parser.WWWAuthenticateParser;
import gov.nist.javax.sip.parser.WarningParser;
import gov.nist.javax.sip.parser.extensions.JoinParser;
import gov.nist.javax.sip.parser.extensions.MinSEParser;
import gov.nist.javax.sip.parser.extensions.ReferencesParser;
import gov.nist.javax.sip.parser.extensions.ReferredByParser;
import gov.nist.javax.sip.parser.extensions.ReplacesParser;
import gov.nist.javax.sip.parser.extensions.SessionExpiresParser;
import gov.nist.javax.sip.parser.ims.PAccessNetworkInfoParser;
import gov.nist.javax.sip.parser.ims.PAssertedIdentityParser;
import gov.nist.javax.sip.parser.ims.PAssociatedURIParser;
import gov.nist.javax.sip.parser.ims.PCalledPartyIDParser;
import gov.nist.javax.sip.parser.ims.PChargingFunctionAddressesParser;
import gov.nist.javax.sip.parser.ims.PChargingVectorParser;
import gov.nist.javax.sip.parser.ims.PMediaAuthorizationParser;
import gov.nist.javax.sip.parser.ims.PPreferredIdentityParser;
import gov.nist.javax.sip.parser.ims.PVisitedNetworkIDParser;
import gov.nist.javax.sip.parser.ims.PathParser;
import gov.nist.javax.sip.parser.ims.PrivacyParser;
import gov.nist.javax.sip.parser.ims.SecurityClientParser;
import gov.nist.javax.sip.parser.ims.SecurityServerParser;
import gov.nist.javax.sip.parser.ims.SecurityVerifyParser;
import gov.nist.javax.sip.parser.ims.ServiceRouteParser;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.Hashtable;

public class ParserFactory {
    private static Class[] constructorArgs;
    private static Hashtable parserConstructorCache;
    private static Hashtable<String, Class<? extends HeaderParser>> parserTable;

    static {
        parserTable = new Hashtable();
        parserConstructorCache = new Hashtable();
        constructorArgs = new Class[1];
        ParserFactory.constructorArgs[0] = String.class;
        parserTable.put("Reply-To".toLowerCase(), ReplyToParser.class);
        parserTable.put("In-Reply-To".toLowerCase(), InReplyToParser.class);
        parserTable.put("Accept-Encoding".toLowerCase(), AcceptEncodingParser.class);
        parserTable.put("Accept-Language".toLowerCase(), AcceptLanguageParser.class);
        parserTable.put("t", ToParser.class);
        parserTable.put("To".toLowerCase(), ToParser.class);
        parserTable.put("From".toLowerCase(), FromParser.class);
        parserTable.put("f", FromParser.class);
        parserTable.put("CSeq".toLowerCase(), CSeqParser.class);
        parserTable.put("Via".toLowerCase(), ViaParser.class);
        parserTable.put("v", ViaParser.class);
        parserTable.put("Contact".toLowerCase(), ContactParser.class);
        parserTable.put("m", ContactParser.class);
        parserTable.put("Content-Type".toLowerCase(), ContentTypeParser.class);
        parserTable.put("c", ContentTypeParser.class);
        parserTable.put("Content-Length".toLowerCase(), ContentLengthParser.class);
        parserTable.put("l", ContentLengthParser.class);
        parserTable.put("Authorization".toLowerCase(), AuthorizationParser.class);
        parserTable.put("WWW-Authenticate".toLowerCase(), WWWAuthenticateParser.class);
        parserTable.put("Call-ID".toLowerCase(), CallIDParser.class);
        parserTable.put("i", CallIDParser.class);
        parserTable.put("Route".toLowerCase(), RouteParser.class);
        parserTable.put("Record-Route".toLowerCase(), RecordRouteParser.class);
        parserTable.put("Date".toLowerCase(), DateParser.class);
        parserTable.put("Proxy-Authorization".toLowerCase(), ProxyAuthorizationParser.class);
        parserTable.put("Proxy-Authenticate".toLowerCase(), ProxyAuthenticateParser.class);
        parserTable.put("Retry-After".toLowerCase(), RetryAfterParser.class);
        parserTable.put("Require".toLowerCase(), RequireParser.class);
        parserTable.put("Proxy-Require".toLowerCase(), ProxyRequireParser.class);
        parserTable.put("Timestamp".toLowerCase(), TimeStampParser.class);
        parserTable.put("Unsupported".toLowerCase(), UnsupportedParser.class);
        parserTable.put("User-Agent".toLowerCase(), UserAgentParser.class);
        parserTable.put("Supported".toLowerCase(), SupportedParser.class);
        parserTable.put("k", SupportedParser.class);
        parserTable.put("Server".toLowerCase(), ServerParser.class);
        parserTable.put("Subject".toLowerCase(), SubjectParser.class);
        parserTable.put("s", SubjectParser.class);
        parserTable.put("Subscription-State".toLowerCase(), SubscriptionStateParser.class);
        parserTable.put("Max-Forwards".toLowerCase(), MaxForwardsParser.class);
        parserTable.put("MIME-Version".toLowerCase(), MimeVersionParser.class);
        parserTable.put("Min-Expires".toLowerCase(), MinExpiresParser.class);
        parserTable.put("Organization".toLowerCase(), OrganizationParser.class);
        parserTable.put("Priority".toLowerCase(), PriorityParser.class);
        parserTable.put("RAck".toLowerCase(), RAckParser.class);
        parserTable.put("RSeq".toLowerCase(), RSeqParser.class);
        parserTable.put("Reason".toLowerCase(), ReasonParser.class);
        parserTable.put("Warning".toLowerCase(), WarningParser.class);
        parserTable.put("Expires".toLowerCase(), ExpiresParser.class);
        parserTable.put("Event".toLowerCase(), EventParser.class);
        parserTable.put("o", EventParser.class);
        parserTable.put("Error-Info".toLowerCase(), ErrorInfoParser.class);
        parserTable.put("Content-Language".toLowerCase(), ContentLanguageParser.class);
        parserTable.put("Content-Encoding".toLowerCase(), ContentEncodingParser.class);
        parserTable.put("e", ContentEncodingParser.class);
        parserTable.put("Content-Disposition".toLowerCase(), ContentDispositionParser.class);
        parserTable.put("Call-Info".toLowerCase(), CallInfoParser.class);
        parserTable.put("Authentication-Info".toLowerCase(), AuthenticationInfoParser.class);
        parserTable.put("Allow".toLowerCase(), AllowParser.class);
        parserTable.put("Allow-Events".toLowerCase(), AllowEventsParser.class);
        parserTable.put("u", AllowEventsParser.class);
        parserTable.put("Alert-Info".toLowerCase(), AlertInfoParser.class);
        parserTable.put("Accept".toLowerCase(), AcceptParser.class);
        parserTable.put("Refer-To".toLowerCase(), ReferToParser.class);
        parserTable.put("r", ReferToParser.class);
        parserTable.put("SIP-ETag".toLowerCase(), SIPETagParser.class);
        parserTable.put("SIP-If-Match".toLowerCase(), SIPIfMatchParser.class);
        parserTable.put("P-Access-Network-Info".toLowerCase(), PAccessNetworkInfoParser.class);
        parserTable.put("P-Asserted-Identity".toLowerCase(), PAssertedIdentityParser.class);
        parserTable.put("P-Preferred-Identity".toLowerCase(), PPreferredIdentityParser.class);
        parserTable.put("P-Charging-Vector".toLowerCase(), PChargingVectorParser.class);
        parserTable.put("P-Charging-Function-Addresses".toLowerCase(), PChargingFunctionAddressesParser.class);
        parserTable.put("P-Media-Authorization".toLowerCase(), PMediaAuthorizationParser.class);
        parserTable.put("Path".toLowerCase(), PathParser.class);
        parserTable.put("Privacy".toLowerCase(), PrivacyParser.class);
        parserTable.put("Service-Route".toLowerCase(), ServiceRouteParser.class);
        parserTable.put("P-Visited-Network-ID".toLowerCase(), PVisitedNetworkIDParser.class);
        parserTable.put("P-Associated-URI".toLowerCase(), PAssociatedURIParser.class);
        parserTable.put("P-Called-Party-ID".toLowerCase(), PCalledPartyIDParser.class);
        parserTable.put("Security-Server".toLowerCase(), SecurityServerParser.class);
        parserTable.put("Security-Client".toLowerCase(), SecurityClientParser.class);
        parserTable.put("Security-Verify".toLowerCase(), SecurityVerifyParser.class);
        parserTable.put("Referred-By".toLowerCase(), ReferredByParser.class);
        parserTable.put("b", ReferToParser.class);
        parserTable.put("Session-Expires".toLowerCase(), SessionExpiresParser.class);
        parserTable.put("x", SessionExpiresParser.class);
        parserTable.put("Min-SE".toLowerCase(), MinSEParser.class);
        parserTable.put("Replaces".toLowerCase(), ReplacesParser.class);
        parserTable.put("Join".toLowerCase(), JoinParser.class);
        parserTable.put("References".toLowerCase(), ReferencesParser.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HeaderParser createParser(String object) throws ParseException {
        Constructor<? extends HeaderParser> constructor = Lexer.getHeaderName((String)object);
        Object object2 = Lexer.getHeaderValue((String)object);
        if (constructor == null) throw new ParseException("The header name or value is null", 0);
        if (object2 == null) throw new ParseException("The header name or value is null", 0);
        Class<? extends HeaderParser> class_ = parserTable.get(SIPHeaderNamesCache.toLowerCase((String)((Object)constructor)));
        if (class_ == null) return new HeaderParser((String)object);
        try {
            object2 = (Constructor)parserConstructorCache.get(class_);
            constructor = object2;
            if (object2 != null) return constructor.newInstance(object);
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException(exception);
            return null;
        }
        constructor = class_.getConstructor(constructorArgs);
        parserConstructorCache.put(class_, constructor);
        return constructor.newInstance(object);
    }
}

