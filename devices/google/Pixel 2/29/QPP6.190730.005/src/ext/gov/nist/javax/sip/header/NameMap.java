/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.PackageNames;
import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.AcceptLanguage;
import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.AllowEvents;
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
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.MimeVersion;
import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.SIPDateHeader;
import gov.nist.javax.sip.header.SIPHeaderNames;
import gov.nist.javax.sip.header.Server;
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
import gov.nist.javax.sip.header.ims.PAccessNetworkInfo;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PCalledPartyID;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddresses;
import gov.nist.javax.sip.header.ims.PChargingVector;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import gov.nist.javax.sip.header.ims.PPreferredIdentity;
import gov.nist.javax.sip.header.ims.PVisitedNetworkID;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.Privacy;
import gov.nist.javax.sip.header.ims.ServiceRoute;
import java.util.Hashtable;

public class NameMap
implements SIPHeaderNames,
PackageNames {
    static Hashtable nameMap;

    static {
        NameMap.initializeNameMap();
    }

    public static void addExtensionHeader(String string, String string2) {
        nameMap.put(string.toLowerCase(), string2);
    }

    public static Class getClassFromName(String object) {
        if ((object = (String)nameMap.get(((String)object).toLowerCase())) == null) {
            return null;
        }
        try {
            object = Class.forName((String)object);
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    private static void initializeNameMap() {
        nameMap = new Hashtable();
        NameMap.putNameMap("Min-Expires", MinExpires.class.getName());
        NameMap.putNameMap("Error-Info", ErrorInfo.class.getName());
        NameMap.putNameMap("MIME-Version", MimeVersion.class.getName());
        NameMap.putNameMap("In-Reply-To", InReplyTo.class.getName());
        NameMap.putNameMap("Allow", Allow.class.getName());
        NameMap.putNameMap("Content-Language", ContentLanguage.class.getName());
        NameMap.putNameMap("Call-Info", CallInfo.class.getName());
        NameMap.putNameMap("CSeq", CSeq.class.getName());
        NameMap.putNameMap("Alert-Info", AlertInfo.class.getName());
        NameMap.putNameMap("Accept-Encoding", AcceptEncoding.class.getName());
        NameMap.putNameMap("Accept", Accept.class.getName());
        NameMap.putNameMap("Accept-Language", AcceptLanguage.class.getName());
        NameMap.putNameMap("Record-Route", RecordRoute.class.getName());
        NameMap.putNameMap("Timestamp", TimeStamp.class.getName());
        NameMap.putNameMap("To", To.class.getName());
        NameMap.putNameMap("Via", Via.class.getName());
        NameMap.putNameMap("From", From.class.getName());
        NameMap.putNameMap("Call-ID", CallID.class.getName());
        NameMap.putNameMap("Authorization", Authorization.class.getName());
        NameMap.putNameMap("Proxy-Authenticate", ProxyAuthenticate.class.getName());
        NameMap.putNameMap("Server", Server.class.getName());
        NameMap.putNameMap("Unsupported", Unsupported.class.getName());
        NameMap.putNameMap("Retry-After", RetryAfter.class.getName());
        NameMap.putNameMap("Content-Type", ContentType.class.getName());
        NameMap.putNameMap("Content-Encoding", ContentEncoding.class.getName());
        NameMap.putNameMap("Content-Length", ContentLength.class.getName());
        NameMap.putNameMap("Route", Route.class.getName());
        NameMap.putNameMap("Contact", Contact.class.getName());
        NameMap.putNameMap("WWW-Authenticate", WWWAuthenticate.class.getName());
        NameMap.putNameMap("Max-Forwards", MaxForwards.class.getName());
        NameMap.putNameMap("Organization", Organization.class.getName());
        NameMap.putNameMap("Proxy-Authorization", ProxyAuthorization.class.getName());
        NameMap.putNameMap("Proxy-Require", ProxyRequire.class.getName());
        NameMap.putNameMap("Require", Require.class.getName());
        NameMap.putNameMap("Content-Disposition", ContentDisposition.class.getName());
        NameMap.putNameMap("Subject", Subject.class.getName());
        NameMap.putNameMap("User-Agent", UserAgent.class.getName());
        NameMap.putNameMap("Warning", Warning.class.getName());
        NameMap.putNameMap("Priority", Priority.class.getName());
        NameMap.putNameMap("Date", SIPDateHeader.class.getName());
        NameMap.putNameMap("Expires", Expires.class.getName());
        NameMap.putNameMap("Supported", Supported.class.getName());
        NameMap.putNameMap("Reply-To", ReplyTo.class.getName());
        NameMap.putNameMap("Subscription-State", SubscriptionState.class.getName());
        NameMap.putNameMap("Event", Event.class.getName());
        NameMap.putNameMap("Allow-Events", AllowEvents.class.getName());
        NameMap.putNameMap("Referred-By", "ReferredBy");
        NameMap.putNameMap("Session-Expires", "SessionExpires");
        NameMap.putNameMap("Min-SE", "MinSE");
        NameMap.putNameMap("Replaces", "Replaces");
        NameMap.putNameMap("Join", "Join");
        NameMap.putNameMap("P-Access-Network-Info", PAccessNetworkInfo.class.getName());
        NameMap.putNameMap("P-Asserted-Identity", PAssertedIdentity.class.getName());
        NameMap.putNameMap("P-Associated-URI", PAssociatedURI.class.getName());
        NameMap.putNameMap("P-Called-Party-ID", PCalledPartyID.class.getName());
        NameMap.putNameMap("P-Charging-Function-Addresses", PChargingFunctionAddresses.class.getName());
        NameMap.putNameMap("P-Charging-Vector", PChargingVector.class.getName());
        NameMap.putNameMap("P-Media-Authorization", PMediaAuthorization.class.getName());
        NameMap.putNameMap("Path", Path.class.getName());
        NameMap.putNameMap("P-Preferred-Identity", PPreferredIdentity.class.getName());
        NameMap.putNameMap("Privacy", Privacy.class.getName());
        NameMap.putNameMap("Service-Route", ServiceRoute.class.getName());
        NameMap.putNameMap("P-Visited-Network-ID", PVisitedNetworkID.class.getName());
    }

    protected static void putNameMap(String string, String string2) {
        nameMap.put(string.toLowerCase(), string2);
    }
}

