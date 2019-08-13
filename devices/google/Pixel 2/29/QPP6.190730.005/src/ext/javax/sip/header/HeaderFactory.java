/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

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

public interface HeaderFactory {
    public AcceptEncodingHeader createAcceptEncodingHeader(String var1) throws ParseException;

    public AcceptHeader createAcceptHeader(String var1, String var2) throws ParseException;

    public AcceptLanguageHeader createAcceptLanguageHeader(Locale var1);

    public AlertInfoHeader createAlertInfoHeader(URI var1);

    public AllowEventsHeader createAllowEventsHeader(String var1) throws ParseException;

    public AllowHeader createAllowHeader(String var1) throws ParseException;

    public AuthenticationInfoHeader createAuthenticationInfoHeader(String var1) throws ParseException;

    public AuthorizationHeader createAuthorizationHeader(String var1) throws ParseException;

    public CSeqHeader createCSeqHeader(int var1, String var2) throws ParseException, InvalidArgumentException;

    public CSeqHeader createCSeqHeader(long var1, String var3) throws ParseException, InvalidArgumentException;

    public CallIdHeader createCallIdHeader(String var1) throws ParseException;

    public CallInfoHeader createCallInfoHeader(URI var1);

    public ContactHeader createContactHeader();

    public ContactHeader createContactHeader(Address var1);

    public ContentDispositionHeader createContentDispositionHeader(String var1) throws ParseException;

    public ContentEncodingHeader createContentEncodingHeader(String var1) throws ParseException;

    public ContentLanguageHeader createContentLanguageHeader(Locale var1);

    public ContentLengthHeader createContentLengthHeader(int var1) throws InvalidArgumentException;

    public ContentTypeHeader createContentTypeHeader(String var1, String var2) throws ParseException;

    public DateHeader createDateHeader(Calendar var1);

    public ErrorInfoHeader createErrorInfoHeader(URI var1);

    public EventHeader createEventHeader(String var1) throws ParseException;

    public ExpiresHeader createExpiresHeader(int var1) throws InvalidArgumentException;

    public ExtensionHeader createExtensionHeader(String var1, String var2) throws ParseException;

    public FromHeader createFromHeader(Address var1, String var2) throws ParseException;

    public Header createHeader(String var1) throws ParseException;

    public Header createHeader(String var1, String var2) throws ParseException;

    public List createHeaders(String var1) throws ParseException;

    public InReplyToHeader createInReplyToHeader(String var1) throws ParseException;

    public MaxForwardsHeader createMaxForwardsHeader(int var1) throws InvalidArgumentException;

    public MimeVersionHeader createMimeVersionHeader(int var1, int var2) throws InvalidArgumentException;

    public MinExpiresHeader createMinExpiresHeader(int var1) throws InvalidArgumentException;

    public OrganizationHeader createOrganizationHeader(String var1) throws ParseException;

    public PriorityHeader createPriorityHeader(String var1) throws ParseException;

    public ProxyAuthenticateHeader createProxyAuthenticateHeader(String var1) throws ParseException;

    public ProxyAuthorizationHeader createProxyAuthorizationHeader(String var1) throws ParseException;

    public ProxyRequireHeader createProxyRequireHeader(String var1) throws ParseException;

    public RAckHeader createRAckHeader(int var1, int var2, String var3) throws InvalidArgumentException, ParseException;

    public RAckHeader createRAckHeader(long var1, long var3, String var5) throws InvalidArgumentException, ParseException;

    public RSeqHeader createRSeqHeader(int var1) throws InvalidArgumentException;

    public RSeqHeader createRSeqHeader(long var1) throws InvalidArgumentException;

    public ReasonHeader createReasonHeader(String var1, int var2, String var3) throws InvalidArgumentException, ParseException;

    public RecordRouteHeader createRecordRouteHeader(Address var1);

    public ReferToHeader createReferToHeader(Address var1);

    public ReplyToHeader createReplyToHeader(Address var1);

    public RequireHeader createRequireHeader(String var1) throws ParseException;

    public RetryAfterHeader createRetryAfterHeader(int var1) throws InvalidArgumentException;

    public RouteHeader createRouteHeader(Address var1);

    public SIPETagHeader createSIPETagHeader(String var1) throws ParseException;

    public SIPIfMatchHeader createSIPIfMatchHeader(String var1) throws ParseException;

    public ServerHeader createServerHeader(List var1) throws ParseException;

    public SubjectHeader createSubjectHeader(String var1) throws ParseException;

    public SubscriptionStateHeader createSubscriptionStateHeader(String var1) throws ParseException;

    public SupportedHeader createSupportedHeader(String var1) throws ParseException;

    public TimeStampHeader createTimeStampHeader(float var1) throws InvalidArgumentException;

    public ToHeader createToHeader(Address var1, String var2) throws ParseException;

    public UnsupportedHeader createUnsupportedHeader(String var1) throws ParseException;

    public UserAgentHeader createUserAgentHeader(List var1) throws ParseException;

    public ViaHeader createViaHeader(String var1, int var2, String var3, String var4) throws InvalidArgumentException, ParseException;

    public WWWAuthenticateHeader createWWWAuthenticateHeader(String var1) throws ParseException;

    public WarningHeader createWarningHeader(String var1, int var2, String var3) throws InvalidArgumentException, ParseException;

    public void setPrettyEncoding(boolean var1);
}

