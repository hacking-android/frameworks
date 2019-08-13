/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.TimeStamp;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.RequestExt;
import gov.nist.javax.sip.message.SIPDuplicateHeaderException;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.address.Address;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public final class SIPRequest
extends SIPMessage
implements Request,
RequestExt {
    private static final String DEFAULT_TRANSPORT = "udp";
    private static final String DEFAULT_USER = "ip";
    private static final Hashtable<String, String> nameTable;
    private static final long serialVersionUID = 3360720013577322927L;
    private static final Set<String> targetRefreshMethods;
    private transient Object inviteTransaction;
    private transient Object messageChannel;
    private RequestLine requestLine;
    private transient Object transactionPointer;

    static {
        targetRefreshMethods = new HashSet<String>();
        nameTable = new Hashtable();
        targetRefreshMethods.add("INVITE");
        targetRefreshMethods.add("UPDATE");
        targetRefreshMethods.add("SUBSCRIBE");
        targetRefreshMethods.add("NOTIFY");
        targetRefreshMethods.add("REFER");
        SIPRequest.putName("INVITE");
        SIPRequest.putName("BYE");
        SIPRequest.putName("CANCEL");
        SIPRequest.putName("ACK");
        SIPRequest.putName("PRACK");
        SIPRequest.putName("INFO");
        SIPRequest.putName("MESSAGE");
        SIPRequest.putName("NOTIFY");
        SIPRequest.putName("OPTIONS");
        SIPRequest.putName("PRACK");
        SIPRequest.putName("PUBLISH");
        SIPRequest.putName("REFER");
        SIPRequest.putName("REGISTER");
        SIPRequest.putName("SUBSCRIBE");
        SIPRequest.putName("UPDATE");
    }

    public static String getCannonicalName(String string) {
        if (nameTable.containsKey(string)) {
            return nameTable.get(string);
        }
        return string;
    }

    public static boolean isDialogCreating(String string) {
        return SIPTransactionStack.isDialogCreated(string);
    }

    public static boolean isTargetRefresh(String string) {
        return targetRefreshMethods.contains(string);
    }

    private final boolean mustCopyRR(int n) {
        boolean bl = false;
        if (n > 100 && n < 300) {
            boolean bl2 = bl;
            if (SIPRequest.isDialogCreating(this.getMethod())) {
                bl2 = bl;
                if (this.getToTag() == null) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    private static void putName(String string) {
        nameTable.put(string, string);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void checkHeaders() throws ParseException {
        if (this.getCSeq() == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Missing a required header : ");
            stringBuilder.append("CSeq");
            throw new ParseException(stringBuilder.toString(), 0);
        }
        if (this.getTo() == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Missing a required header : ");
            stringBuilder.append("To");
            throw new ParseException(stringBuilder.toString(), 0);
        }
        if (this.callIdHeader != null && this.callIdHeader.getCallId() != null && !this.callIdHeader.getCallId().equals("")) {
            RequestLine requestLine;
            if (this.getFrom() == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing a required header : ");
                stringBuilder.append("From");
                throw new ParseException(stringBuilder.toString(), 0);
            }
            if (this.getViaHeaders() == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing a required header : ");
                stringBuilder.append("Via");
                throw new ParseException(stringBuilder.toString(), 0);
            }
            if (this.getTopmostVia() == null) throw new ParseException("No via header in request! ", 0);
            if (this.getMethod().equals("NOTIFY")) {
                if (this.getHeader("Subscription-State") == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Missing a required header : ");
                    stringBuilder.append("Subscription-State");
                    throw new ParseException(stringBuilder.toString(), 0);
                }
                if (this.getHeader("Event") == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Missing a required header : ");
                    stringBuilder.append("Event");
                    throw new ParseException(stringBuilder.toString(), 0);
                }
            } else if (this.getMethod().equals("PUBLISH") && this.getHeader("Event") == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing a required header : ");
                stringBuilder.append("Event");
                throw new ParseException(stringBuilder.toString(), 0);
            }
            if (this.requestLine.getMethod().equals("INVITE") || this.requestLine.getMethod().equals("SUBSCRIBE") || this.requestLine.getMethod().equals("REFER")) {
                SipUri sipUri;
                if (this.getContactHeader() == null && this.getToTag() == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Missing a required header : ");
                    stringBuilder.append("Contact");
                    throw new ParseException(stringBuilder.toString(), 0);
                }
                if (this.requestLine.getUri() instanceof SipUri && "sips".equalsIgnoreCase(((SipUri)this.requestLine.getUri()).getScheme()) && !(sipUri = (SipUri)this.getContactHeader().getAddress().getURI()).getScheme().equals("sips")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Scheme for contact should be sips:");
                    stringBuilder.append(sipUri);
                    throw new ParseException(stringBuilder.toString(), 0);
                }
            }
            if (this.getContactHeader() == null) {
                if (this.getMethod().equals("INVITE")) throw new ParseException("Contact Header is Mandatory for a SIP INVITE", 0);
                if (this.getMethod().equals("REFER")) throw new ParseException("Contact Header is Mandatory for a SIP INVITE", 0);
                if (this.getMethod().equals("SUBSCRIBE")) throw new ParseException("Contact Header is Mandatory for a SIP INVITE", 0);
            }
            if ((requestLine = this.requestLine) == null) return;
            if (requestLine.getMethod() == null) return;
            if (this.getCSeq().getMethod() == null) return;
            if (this.requestLine.getMethod().compareTo(this.getCSeq().getMethod()) != 0) throw new ParseException("CSEQ method mismatch with  Request-Line ", 0);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Missing a required header : ");
        stringBuilder.append("Call-ID");
        throw new ParseException(stringBuilder.toString(), 0);
    }

    @Override
    public Object clone() {
        SIPRequest sIPRequest = (SIPRequest)super.clone();
        sIPRequest.transactionPointer = null;
        RequestLine requestLine = this.requestLine;
        if (requestLine != null) {
            sIPRequest.requestLine = (RequestLine)requestLine.clone();
        }
        return sIPRequest;
    }

    public SIPRequest createACKRequest() {
        RequestLine requestLine = (RequestLine)this.requestLine.clone();
        requestLine.setMethod("ACK");
        return this.createSIPRequest(requestLine, false);
    }

    public SIPRequest createAckRequest(To to) {
        SIPRequest sIPRequest = new SIPRequest();
        sIPRequest.setRequestLine((RequestLine)this.requestLine.clone());
        sIPRequest.setMethod("ACK");
        Iterator<SIPHeader> iterator = this.getHeaders();
        while (iterator.hasNext()) {
            SIPHeader sIPHeader = iterator.next();
            if (sIPHeader instanceof RouteList || sIPHeader instanceof ProxyAuthorization) continue;
            if (sIPHeader instanceof ContentLength) {
                sIPHeader = (SIPHeader)sIPHeader.clone();
                try {
                    ((ContentLength)sIPHeader).setContentLength(0);
                }
                catch (InvalidArgumentException invalidArgumentException) {}
            } else {
                if (sIPHeader instanceof ContentType) continue;
                if (sIPHeader instanceof CSeq) {
                    sIPHeader = (CSeq)sIPHeader.clone();
                    try {
                        ((CSeq)sIPHeader).setMethod("ACK");
                    }
                    catch (ParseException parseException) {}
                } else if (sIPHeader instanceof To) {
                    sIPHeader = to != null ? to : (SIPHeader)sIPHeader.clone();
                } else {
                    if (sIPHeader instanceof ContactList || sIPHeader instanceof Expires) continue;
                    sIPHeader = sIPHeader instanceof ViaList ? (SIPHeader)((ViaList)sIPHeader).getFirst().clone() : (SIPHeader)sIPHeader.clone();
                }
            }
            try {
                sIPRequest.attachHeader(sIPHeader, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                sIPDuplicateHeaderException.printStackTrace();
            }
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            sIPRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
        }
        return sIPRequest;
    }

    public SIPRequest createBYERequest(boolean bl) {
        RequestLine requestLine = (RequestLine)this.requestLine.clone();
        requestLine.setMethod("BYE");
        return this.createSIPRequest(requestLine, bl);
    }

    public SIPRequest createCancelRequest() throws SipException {
        if (this.getMethod().equals("INVITE")) {
            SIPRequest sIPRequest = new SIPRequest();
            sIPRequest.setRequestLine((RequestLine)this.requestLine.clone());
            sIPRequest.setMethod("CANCEL");
            sIPRequest.setHeader((Header)this.callIdHeader.clone());
            sIPRequest.setHeader((Header)this.toHeader.clone());
            sIPRequest.setHeader((Header)this.cSeqHeader.clone());
            try {
                sIPRequest.getCSeq().setMethod("CANCEL");
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            sIPRequest.setHeader((Header)this.fromHeader.clone());
            sIPRequest.addFirst((Header)this.getTopmostVia().clone());
            sIPRequest.setHeader((Header)this.maxForwardsHeader.clone());
            if (this.getRouteHeaders() != null) {
                sIPRequest.setHeader((Header)((SIPHeaderList)this.getRouteHeaders().clone()));
            }
            if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
                sIPRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
            }
            return sIPRequest;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempt to create CANCEL for ");
        stringBuilder.append(this.getMethod());
        throw new SipException(stringBuilder.toString());
    }

    public final SIPRequest createErrorAck(To to) throws SipException, ParseException {
        SIPRequest sIPRequest = new SIPRequest();
        sIPRequest.setRequestLine((RequestLine)this.requestLine.clone());
        sIPRequest.setMethod("ACK");
        sIPRequest.setHeader((Header)this.callIdHeader.clone());
        sIPRequest.setHeader((Header)this.maxForwardsHeader.clone());
        sIPRequest.setHeader((Header)this.fromHeader.clone());
        sIPRequest.setHeader((Header)to.clone());
        sIPRequest.addFirst((Header)this.getTopmostVia().clone());
        sIPRequest.setHeader((Header)this.cSeqHeader.clone());
        sIPRequest.getCSeq().setMethod("ACK");
        if (this.getRouteHeaders() != null) {
            sIPRequest.setHeader((SIPHeaderList)this.getRouteHeaders().clone());
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            sIPRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
        }
        return sIPRequest;
    }

    public SIPResponse createResponse(int n) {
        return this.createResponse(n, SIPResponse.getReasonPhrase(n));
    }

    public SIPResponse createResponse(int n, String object) {
        SIPResponse sIPResponse;
        block9 : {
            sIPResponse = new SIPResponse();
            try {
                sIPResponse.setStatusCode(n);
                if (object != null) {
                    sIPResponse.setReasonPhrase((String)object);
                    break block9;
                }
                sIPResponse.setReasonPhrase(SIPResponse.getReasonPhrase(n));
            }
            catch (ParseException parseException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad code ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        object = this.getHeaders();
        while (object.hasNext()) {
            SIPHeader sIPHeader = (SIPHeader)object.next();
            if (!(sIPHeader instanceof From) && !(sIPHeader instanceof To) && !(sIPHeader instanceof ViaList) && !(sIPHeader instanceof CallID) && (!(sIPHeader instanceof RecordRouteList) || !this.mustCopyRR(n)) && !(sIPHeader instanceof CSeq) && !(sIPHeader instanceof TimeStamp)) continue;
            try {
                sIPResponse.attachHeader((SIPHeader)sIPHeader.clone(), false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                sIPDuplicateHeaderException.printStackTrace();
            }
        }
        if (MessageFactoryImpl.getDefaultServerHeader() != null) {
            sIPResponse.setHeader(MessageFactoryImpl.getDefaultServerHeader());
        }
        if (sIPResponse.getStatusCode() == 100) {
            sIPResponse.getTo().removeParameter("tag");
        }
        if ((object = MessageFactoryImpl.getDefaultServerHeader()) != null) {
            sIPResponse.setHeader((Header)object);
        }
        return sIPResponse;
    }

    public SIPRequest createSIPRequest(RequestLine requestLine, boolean bl) {
        SIPRequest sIPRequest = new SIPRequest();
        sIPRequest.requestLine = requestLine;
        Iterator<SIPHeader> iterator = this.getHeaders();
        while (iterator.hasNext()) {
            SIPHeader sIPHeader;
            SIPHeader sIPHeader2 = iterator.next();
            if (sIPHeader2 instanceof CSeq) {
                sIPHeader = sIPHeader2 = (CSeq)sIPHeader2.clone();
                try {
                    ((CSeq)sIPHeader2).setMethod(requestLine.getMethod());
                }
                catch (ParseException parseException) {}
            } else if (sIPHeader2 instanceof ViaList) {
                sIPHeader = (Via)((ViaList)sIPHeader2).getFirst().clone();
                ((ParametersHeader)sIPHeader).removeParameter("branch");
            } else if (sIPHeader2 instanceof To) {
                sIPHeader = (To)sIPHeader2;
                if (bl) {
                    sIPHeader = new From((To)sIPHeader);
                    ((From)sIPHeader).removeTag();
                } else {
                    sIPHeader = (SIPHeader)((AddressParametersHeader)sIPHeader).clone();
                    ((To)sIPHeader).removeTag();
                }
            } else if (sIPHeader2 instanceof From) {
                sIPHeader = (From)sIPHeader2;
                if (bl) {
                    sIPHeader = new To((From)sIPHeader);
                    ((To)sIPHeader).removeTag();
                } else {
                    sIPHeader = (SIPHeader)((AddressParametersHeader)sIPHeader).clone();
                    ((From)sIPHeader).removeTag();
                }
            } else if (sIPHeader2 instanceof ContentLength) {
                sIPHeader = (ContentLength)sIPHeader2.clone();
                try {
                    ((ContentLength)sIPHeader).setContentLength(0);
                }
                catch (InvalidArgumentException invalidArgumentException) {}
            } else {
                sIPHeader = sIPHeader2;
                if (!(sIPHeader2 instanceof CallID)) {
                    sIPHeader = sIPHeader2;
                    if (!(sIPHeader2 instanceof MaxForwards)) continue;
                }
            }
            try {
                sIPRequest.attachHeader(sIPHeader, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                sIPDuplicateHeaderException.printStackTrace();
            }
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            sIPRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
        }
        return sIPRequest;
    }

    @Override
    public String debugDump() {
        String string = super.debugDump();
        this.stringRepresentation = "";
        this.sprint(SIPRequest.class.getName());
        this.sprint("{");
        RequestLine requestLine = this.requestLine;
        if (requestLine != null) {
            this.sprint(requestLine.debugDump());
        }
        this.sprint(string);
        this.sprint("}");
        return this.stringRepresentation;
    }

    @Override
    public String encode() {
        CharSequence charSequence;
        if (this.requestLine != null) {
            this.setRequestLineDefaults();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.requestLine.encode());
            ((StringBuilder)charSequence).append(super.encode());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this.isNullRequest() ? "\r\n\r\n" : super.encode();
        }
        return charSequence;
    }

    @Override
    public byte[] encodeAsBytes(String arrby) {
        byte[] arrby2;
        if (this.isNullRequest()) {
            return "\r\n\r\n".getBytes();
        }
        RequestLine requestLine = this.requestLine;
        if (requestLine == null) {
            return new byte[0];
        }
        byte[] arrby3 = arrby2 = null;
        if (requestLine != null) {
            try {
                arrby3 = requestLine.encode().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                InternalErrorHandler.handleException(unsupportedEncodingException);
                arrby3 = arrby2;
            }
        }
        arrby = super.encodeAsBytes((String)arrby);
        arrby2 = new byte[arrby3.length + arrby.length];
        System.arraycopy(arrby3, 0, arrby2, 0, arrby3.length);
        System.arraycopy(arrby, 0, arrby2, arrby3.length, arrby.length);
        return arrby2;
    }

    @Override
    public String encodeMessage() {
        CharSequence charSequence;
        if (this.requestLine != null) {
            this.setRequestLineDefaults();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.requestLine.encode());
            ((StringBuilder)charSequence).append(super.encodeSIPHeaders());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this.isNullRequest() ? "\r\n\r\n" : super.encodeSIPHeaders();
        }
        return charSequence;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = this.getClass().equals(object.getClass());
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        SIPRequest sIPRequest = (SIPRequest)object;
        bl = bl2;
        if (this.requestLine.equals(sIPRequest.requestLine)) {
            bl = bl2;
            if (super.equals(object)) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public String getDialogId(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer(((CallID)this.getCallId()).getCallId());
        From from = (From)this.getFrom();
        To to = (To)this.getTo();
        if (!bl) {
            if (from.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(from.getTag());
            }
            if (to.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(to.getTag());
            }
        } else {
            if (to.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(to.getTag());
            }
            if (from.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(from.getTag());
            }
        }
        return stringBuffer.toString().toLowerCase();
    }

    public String getDialogId(boolean bl, String string) {
        From from = (From)this.getFrom();
        StringBuffer stringBuffer = new StringBuffer(((CallID)this.getCallId()).getCallId());
        if (!bl) {
            if (from.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(from.getTag());
            }
            if (string != null) {
                stringBuffer.append(":");
                stringBuffer.append(string);
            }
        } else {
            if (string != null) {
                stringBuffer.append(":");
                stringBuffer.append(string);
            }
            if (from.getTag() != null) {
                stringBuffer.append(":");
                stringBuffer.append(from.getTag());
            }
        }
        return stringBuffer.toString().toLowerCase();
    }

    @Override
    public String getFirstLine() {
        RequestLine requestLine = this.requestLine;
        if (requestLine == null) {
            return null;
        }
        return requestLine.encode();
    }

    public Object getInviteTransaction() {
        return this.inviteTransaction;
    }

    public String getMergeId() {
        String string = this.getFromTag();
        String string2 = this.cSeqHeader.toString();
        String string3 = this.callIdHeader.getCallId();
        String string4 = this.getRequestURI().toString();
        if (string != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(string4);
            stringBuffer.append(":");
            stringBuffer.append(string);
            stringBuffer.append(":");
            stringBuffer.append(string2);
            stringBuffer.append(":");
            stringBuffer.append(string3);
            return stringBuffer.toString();
        }
        return null;
    }

    public LinkedList getMessageAsEncodedStrings() {
        LinkedList<String> linkedList = super.getMessageAsEncodedStrings();
        if (this.requestLine != null) {
            this.setRequestLineDefaults();
            linkedList.addFirst(this.requestLine.encode());
        }
        return linkedList;
    }

    public Object getMessageChannel() {
        return this.messageChannel;
    }

    @Override
    public String getMethod() {
        RequestLine requestLine = this.requestLine;
        if (requestLine == null) {
            return null;
        }
        return requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    @Override
    public URI getRequestURI() {
        RequestLine requestLine = this.requestLine;
        if (requestLine == null) {
            return null;
        }
        return requestLine.getUri();
    }

    @Override
    public String getSIPVersion() {
        return this.requestLine.getSipVersion();
    }

    public Object getTransaction() {
        return this.transactionPointer;
    }

    public String getViaHost() {
        return ((Via)this.getViaHeaders().getFirst()).getHost();
    }

    public int getViaPort() {
        Via via = (Via)this.getViaHeaders().getFirst();
        if (via.hasPort()) {
            return via.getPort();
        }
        return 5060;
    }

    @Override
    public boolean match(Object object) {
        boolean bl = true;
        if (object == null) {
            return true;
        }
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        if (object == this) {
            return true;
        }
        SIPRequest sIPRequest = (SIPRequest)object;
        RequestLine requestLine = sIPRequest.requestLine;
        if (this.requestLine == null && requestLine != null) {
            return false;
        }
        RequestLine requestLine2 = this.requestLine;
        if (requestLine2 == requestLine) {
            return super.match(object);
        }
        if (!requestLine2.match(sIPRequest.requestLine) || !super.match(object)) {
            bl = false;
        }
        return bl;
    }

    protected void setDefaults() {
        Object object = this.requestLine;
        if (object == null) {
            return;
        }
        if ((object = ((RequestLine)object).getMethod()) == null) {
            return;
        }
        GenericURI genericURI = this.requestLine.getUri();
        if (genericURI == null) {
            return;
        }
        if ((((String)object).compareTo("REGISTER") == 0 || ((String)object).compareTo("INVITE") == 0) && genericURI instanceof SipUri) {
            object = (SipUri)genericURI;
            ((SipUri)object).setUserParam(DEFAULT_USER);
            try {
                ((SipUri)object).setTransportParam(DEFAULT_TRANSPORT);
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
    }

    public void setInviteTransaction(Object object) {
        this.inviteTransaction = object;
    }

    public void setMessageChannel(Object object) {
        this.messageChannel = object;
    }

    @Override
    public void setMethod(String string) {
        if (string != null) {
            if (this.requestLine == null) {
                this.requestLine = new RequestLine();
            }
            string = SIPRequest.getCannonicalName(string);
            this.requestLine.setMethod(string);
            if (this.cSeqHeader != null) {
                try {
                    this.cSeqHeader.setMethod(string);
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
            return;
        }
        throw new IllegalArgumentException("null method");
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    protected void setRequestLineDefaults() {
        Object object;
        if (this.requestLine.getMethod() == null && (object = (CSeq)this.getCSeq()) != null) {
            object = SIPRequest.getCannonicalName(((CSeq)object).getMethod());
            this.requestLine.setMethod((String)object);
        }
    }

    @Override
    public void setRequestURI(URI uRI) {
        if (uRI != null) {
            if (this.requestLine == null) {
                this.requestLine = new RequestLine();
            }
            this.requestLine.setUri((GenericURI)uRI);
            this.nullRequest = false;
            return;
        }
        throw new NullPointerException("Null request URI");
    }

    @Override
    public void setSIPVersion(String string) throws ParseException {
        if (string != null && string.equalsIgnoreCase("SIP/2.0")) {
            this.requestLine.setSipVersion(string);
            return;
        }
        throw new ParseException("sipVersion", 0);
    }

    public void setTransaction(Object object) {
        this.transactionPointer = object;
    }

    @Override
    public String toString() {
        return this.encode();
    }
}

