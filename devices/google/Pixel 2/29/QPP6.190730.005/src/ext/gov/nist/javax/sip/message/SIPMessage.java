/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.ErrorInfoList;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.ProxyRequireList;
import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.SIPHeaderNamesCache;
import gov.nist.javax.sip.header.SIPIfMatch;
import gov.nist.javax.sip.header.Server;
import gov.nist.javax.sip.header.Subject;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Unsupported;
import gov.nist.javax.sip.header.UserAgent;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.header.WWWAuthenticate;
import gov.nist.javax.sip.header.Warning;
import gov.nist.javax.sip.message.HeaderIterator;
import gov.nist.javax.sip.message.ListMap;
import gov.nist.javax.sip.message.MessageExt;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.MessageObject;
import gov.nist.javax.sip.message.MultipartMimeContent;
import gov.nist.javax.sip.message.MultipartMimeContentImpl;
import gov.nist.javax.sip.message.SIPDuplicateHeaderException;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.parser.ParserFactory;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentEncodingHeader;
import javax.sip.header.ContentLanguageHeader;
import javax.sip.header.ContentLengthHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.RecordRouteHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Message;

public abstract class SIPMessage
extends MessageObject
implements Message,
MessageExt {
    private static final String AUTHORIZATION_LOWERCASE;
    private static final String CONTACT_LOWERCASE;
    private static final String CONTENT_DISPOSITION_LOWERCASE;
    private static final String CONTENT_ENCODING_LOWERCASE;
    private static final String CONTENT_LANGUAGE_LOWERCASE;
    private static final String CONTENT_TYPE_LOWERCASE;
    private static final String ERROR_LOWERCASE;
    private static final String EXPIRES_LOWERCASE;
    private static final String RECORDROUTE_LOWERCASE;
    private static final String ROUTE_LOWERCASE;
    private static final String VIA_LOWERCASE;
    protected Object applicationData;
    protected CSeq cSeqHeader;
    protected CallID callIdHeader;
    private String contentEncodingCharset = MessageFactoryImpl.getDefaultContentEncodingCharset();
    protected ContentLength contentLengthHeader;
    protected From fromHeader;
    protected ConcurrentLinkedQueue<SIPHeader> headers = new ConcurrentLinkedQueue();
    protected MaxForwards maxForwardsHeader;
    private String messageContent;
    private byte[] messageContentBytes;
    private Object messageContentObject;
    private Hashtable<String, SIPHeader> nameTable = new Hashtable();
    protected boolean nullRequest;
    protected int size;
    protected To toHeader;
    protected LinkedList<String> unrecognizedHeaders = new LinkedList();

    static {
        CONTENT_TYPE_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Content-Type");
        ERROR_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Error-Info");
        CONTACT_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Contact");
        VIA_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Via");
        AUTHORIZATION_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Authorization");
        ROUTE_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Route");
        RECORDROUTE_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Record-Route");
        CONTENT_DISPOSITION_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Content-Disposition");
        CONTENT_ENCODING_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Content-Encoding");
        CONTENT_LANGUAGE_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Content-Language");
        EXPIRES_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Expires");
    }

    public SIPMessage() {
        try {
            ContentLength contentLength = new ContentLength(0);
            this.attachHeader(contentLength, false);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void attachHeader(SIPHeader sIPHeader) {
        if (sIPHeader != null) {
            try {
                if (sIPHeader instanceof SIPHeaderList && ((SIPHeaderList)sIPHeader).isEmpty()) {
                    return;
                }
                this.attachHeader(sIPHeader, false, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalArgumentException("null header!");
    }

    private void computeContentLength(Object object) {
        int n = 0;
        int n2 = 0;
        if (object != null) {
            if (object instanceof String) {
                try {
                    n = ((String)object).getBytes(this.getCharset()).length;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    InternalErrorHandler.handleException(unsupportedEncodingException);
                    n = n2;
                }
            } else {
                n = object instanceof byte[] ? ((byte[])object).length : object.toString().length();
            }
        }
        try {
            this.contentLengthHeader.setContentLength(n);
        }
        catch (InvalidArgumentException invalidArgumentException) {
            // empty catch block
        }
    }

    private List<SIPHeader> getHeaderList(String object) {
        if ((object = this.nameTable.get(SIPHeaderNamesCache.toLowerCase((String)object))) == null) {
            return null;
        }
        if (object instanceof SIPHeaderList) {
            return ((SIPHeaderList)object).getHeaderList();
        }
        LinkedList<SIPHeader> linkedList = new LinkedList<SIPHeader>();
        linkedList.add((SIPHeader)object);
        return linkedList;
    }

    private Header getHeaderLowerCase(String object) {
        if (object != null) {
            if ((object = this.nameTable.get(object)) instanceof SIPHeaderList) {
                return ((SIPHeaderList)object).getFirst();
            }
            return object;
        }
        throw new NullPointerException("bad name");
    }

    private SIPHeader getSIPHeaderListLowerCase(String string) {
        return this.nameTable.get(string);
    }

    public static boolean isRequestHeader(SIPHeader sIPHeader) {
        boolean bl = sIPHeader instanceof AlertInfo || sIPHeader instanceof InReplyTo || sIPHeader instanceof Authorization || sIPHeader instanceof MaxForwards || sIPHeader instanceof UserAgent || sIPHeader instanceof Priority || sIPHeader instanceof ProxyAuthorization || sIPHeader instanceof ProxyRequire || sIPHeader instanceof ProxyRequireList || sIPHeader instanceof Route || sIPHeader instanceof RouteList || sIPHeader instanceof Subject || sIPHeader instanceof SIPIfMatch;
        return bl;
    }

    public static boolean isResponseHeader(SIPHeader sIPHeader) {
        boolean bl = sIPHeader instanceof ErrorInfo || sIPHeader instanceof ProxyAuthenticate || sIPHeader instanceof Server || sIPHeader instanceof Unsupported || sIPHeader instanceof RetryAfter || sIPHeader instanceof Warning || sIPHeader instanceof WWWAuthenticate || sIPHeader instanceof SIPETag || sIPHeader instanceof RSeq;
        return bl;
    }

    @Override
    public void addFirst(Header header) throws SipException, NullPointerException {
        if (header != null) {
            try {
                this.attachHeader((SIPHeader)header, false, true);
                return;
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                throw new SipException("Cannot add header - header already exists");
            }
        }
        throw new NullPointerException("null arg!");
    }

    public void addHeader(String string) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string.trim());
        charSequence.append("\n");
        charSequence = charSequence.toString();
        try {
            this.attachHeader(ParserFactory.createParser(string).parse(), false);
        }
        catch (ParseException parseException) {
            this.unrecognizedHeaders.add((String)charSequence);
        }
    }

    @Override
    public void addHeader(Header header) {
        SIPHeader sIPHeader = (SIPHeader)header;
        try {
            if (!(header instanceof ViaHeader) && !(header instanceof RecordRouteHeader)) {
                this.attachHeader(sIPHeader, false, false);
            } else {
                this.attachHeader(sIPHeader, false, true);
            }
        }
        catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
            try {
                if (header instanceof ContentLength) {
                    header = (ContentLength)header;
                    this.contentLengthHeader.setContentLength(((ContentLength)header).getContentLength());
                }
            }
            catch (InvalidArgumentException invalidArgumentException) {
                // empty catch block
            }
        }
    }

    @Override
    public void addLast(Header header) throws SipException, NullPointerException {
        if (header != null) {
            try {
                this.attachHeader((SIPHeader)header, false, false);
                return;
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                throw new SipException("Cannot add header - header already exists");
            }
        }
        throw new NullPointerException("null arg!");
    }

    public void addUnparsed(String string) {
        this.unrecognizedHeaders.add(string);
    }

    public void attachHeader(SIPHeader sIPHeader, boolean bl) throws SIPDuplicateHeaderException {
        this.attachHeader(sIPHeader, bl, false);
    }

    public void attachHeader(SIPHeader object, boolean bl, boolean bl2) throws SIPDuplicateHeaderException {
        if (object != null) {
            SIPHeaderList<SIPHeader> sIPHeaderList;
            if (ListMap.hasList((SIPHeader)object) && !SIPHeaderList.class.isAssignableFrom(object.getClass())) {
                sIPHeaderList = ListMap.getList((SIPHeader)object);
                sIPHeaderList.add((SIPHeader)object);
            } else {
                sIPHeaderList = object;
            }
            String string = SIPHeaderNamesCache.toLowerCase(((SIPHeader)sIPHeaderList).getName());
            if (bl) {
                this.nameTable.remove(string);
            } else if (this.nameTable.containsKey(string) && !(sIPHeaderList instanceof SIPHeaderList)) {
                if (sIPHeaderList instanceof ContentLength) {
                    try {
                        object = (ContentLength)((Object)sIPHeaderList);
                        this.contentLengthHeader.setContentLength(((ContentLength)object).getContentLength());
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        // empty catch block
                    }
                }
                return;
            }
            SIPHeader sIPHeader = (SIPHeader)this.getHeader(((SIPHeader)object).getName());
            if (sIPHeader != null) {
                object = this.headers.iterator();
                while (object.hasNext()) {
                    if (!((SIPHeader)object.next()).equals(sIPHeader)) continue;
                    object.remove();
                }
            }
            if (!this.nameTable.containsKey(string)) {
                this.nameTable.put(string, sIPHeaderList);
                this.headers.add(sIPHeaderList);
            } else if (sIPHeaderList instanceof SIPHeaderList) {
                object = (SIPHeaderList)this.nameTable.get(string);
                if (object != null) {
                    ((SIPHeaderList)object).concatenate(sIPHeaderList, bl2);
                } else {
                    this.nameTable.put(string, sIPHeaderList);
                }
            } else {
                this.nameTable.put(string, sIPHeaderList);
            }
            if (sIPHeaderList instanceof From) {
                this.fromHeader = (From)((Object)sIPHeaderList);
            } else if (sIPHeaderList instanceof ContentLength) {
                this.contentLengthHeader = (ContentLength)((Object)sIPHeaderList);
            } else if (sIPHeaderList instanceof To) {
                this.toHeader = (To)((Object)sIPHeaderList);
            } else if (sIPHeaderList instanceof CSeq) {
                this.cSeqHeader = (CSeq)((Object)sIPHeaderList);
            } else if (sIPHeaderList instanceof CallID) {
                this.callIdHeader = (CallID)((Object)sIPHeaderList);
            } else if (sIPHeaderList instanceof MaxForwards) {
                this.maxForwardsHeader = (MaxForwards)((Object)sIPHeaderList);
            }
            return;
        }
        throw new NullPointerException("null header");
    }

    @Override
    public Object clone() {
        Object object;
        SIPMessage sIPMessage = (SIPMessage)super.clone();
        sIPMessage.nameTable = new Hashtable();
        sIPMessage.fromHeader = null;
        sIPMessage.toHeader = null;
        sIPMessage.cSeqHeader = null;
        sIPMessage.callIdHeader = null;
        sIPMessage.contentLengthHeader = null;
        sIPMessage.maxForwardsHeader = null;
        if (this.headers != null) {
            sIPMessage.headers = new ConcurrentLinkedQueue();
            object = this.headers.iterator();
            while (object.hasNext()) {
                sIPMessage.attachHeader((SIPHeader)((SIPHeader)object.next()).clone());
            }
        }
        if ((object = this.messageContentBytes) != null) {
            sIPMessage.messageContentBytes = (byte[])object.clone();
        }
        if ((object = this.messageContentObject) != null) {
            sIPMessage.messageContentObject = SIPMessage.makeClone(object);
        }
        sIPMessage.unrecognizedHeaders = this.unrecognizedHeaders;
        return sIPMessage;
    }

    @Override
    public String debugDump() {
        block6 : {
            Field[] arrfield;
            int n;
            this.stringRepresentation = "";
            this.sprint("SIPMessage:");
            this.sprint("{");
            try {
                arrfield = this.getClass().getDeclaredFields();
                n = 0;
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
            }
            do {
                block7 : {
                    if (n >= arrfield.length) break block6;
                    Field field = arrfield[n];
                    Serializable serializable = field.getType();
                    String string = field.getName();
                    if (field.get(this) == null || !SIPHeader.class.isAssignableFrom((Class<?>)serializable) || string.compareTo("headers") == 0) break block7;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(string);
                    ((StringBuilder)serializable).append("=");
                    this.sprint(((StringBuilder)serializable).toString());
                    this.sprint(((SIPHeader)field.get(this)).debugDump());
                }
                ++n;
            } while (true);
        }
        this.sprint("List of headers : ");
        this.sprint(this.headers.toString());
        this.sprint("messageContent = ");
        this.sprint("{");
        this.sprint(this.messageContent);
        this.sprint("}");
        if (this.getContent() != null) {
            this.sprint(this.getContent().toString());
        }
        this.sprint("}");
        return this.stringRepresentation;
    }

    @Override
    public String encode() {
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        Object object2 = this.headers.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            if (object instanceof ContentLength) continue;
            stringBuffer.append(((SIPHeader)object).encode());
        }
        object = this.unrecognizedHeaders.iterator();
        while (object.hasNext()) {
            stringBuffer.append((String)object.next());
            stringBuffer.append("\r\n");
        }
        stringBuffer.append(this.contentLengthHeader.encode());
        stringBuffer.append("\r\n");
        if (this.messageContentObject != null) {
            stringBuffer.append(this.getContent().toString());
        } else if (this.messageContent != null || this.messageContentBytes != null) {
            block8 : {
                object = null;
                if (this.messageContent == null) break block8;
                object = object2 = this.messageContent;
            }
            try {
                object = object2 = new String(this.messageContentBytes, this.getCharset());
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                InternalErrorHandler.handleException(unsupportedEncodingException);
            }
            stringBuffer.append((String)object);
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] encodeAsBytes(String object) {
        if (this instanceof SIPRequest && ((SIPRequest)this).isNullRequest()) {
            return "\r\n\r\n".getBytes();
        }
        Serializable serializable = (ViaHeader)this.getHeader("Via");
        try {
            serializable.setTransport((String)object);
        }
        catch (ParseException parseException) {
            InternalErrorHandler.handleException(parseException);
        }
        serializable = new StringBuffer();
        object = this.headers;
        synchronized (object) {
            for (SIPHeader sIPHeader : this.headers) {
                if (sIPHeader instanceof ContentLength) continue;
                sIPHeader.encode((StringBuffer)serializable);
            }
        }
        this.contentLengthHeader.encode((StringBuffer)serializable);
        ((StringBuffer)serializable).append("\r\n");
        object = null;
        byte[] arrby = this.getRawContent();
        if (arrby != null) {
            object = null;
            try {
                serializable = ((StringBuffer)serializable).toString().getBytes(this.getCharset());
                object = serializable;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                InternalErrorHandler.handleException(unsupportedEncodingException);
            }
            serializable = new byte[((Object)object).length + arrby.length];
            System.arraycopy(object, 0, serializable, 0, ((Object)object).length);
            System.arraycopy(arrby, 0, serializable, ((Object)object).length, arrby.length);
            return serializable;
        }
        try {
            serializable = ((StringBuffer)serializable).toString().getBytes(this.getCharset());
            return serializable;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            InternalErrorHandler.handleException(unsupportedEncodingException);
        }
        return object;
    }

    public abstract String encodeMessage();

    protected String encodeSIPHeaders() {
        StringBuffer stringBuffer = new StringBuffer();
        for (SIPHeader sIPHeader : this.headers) {
            if (sIPHeader instanceof ContentLength) continue;
            sIPHeader.encode(stringBuffer);
        }
        StringBuffer stringBuffer2 = this.contentLengthHeader.encode(stringBuffer);
        stringBuffer2.append("\r\n");
        return stringBuffer2.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        SIPMessage sIPMessage = (SIPMessage)object;
        Iterator<SIPHeader> iterator = this.nameTable.values().iterator();
        if (this.nameTable.size() != sIPMessage.nameTable.size()) {
            return false;
        }
        while (iterator.hasNext()) {
            object = iterator.next();
            SIPHeader sIPHeader = sIPMessage.nameTable.get(SIPHeaderNamesCache.toLowerCase(((SIPHeader)object).getName()));
            if (sIPHeader == null) {
                return false;
            }
            if (sIPHeader.equals(object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public Object getApplicationData() {
        return this.applicationData;
    }

    public Authorization getAuthorization() {
        return (Authorization)this.getHeaderLowerCase(AUTHORIZATION_LOWERCASE);
    }

    public CSeqHeader getCSeq() {
        return this.cSeqHeader;
    }

    @Override
    public CSeqHeader getCSeqHeader() {
        return this.cSeqHeader;
    }

    public CallIdHeader getCallId() {
        return this.callIdHeader;
    }

    @Override
    public CallIdHeader getCallIdHeader() {
        return this.callIdHeader;
    }

    protected final String getCharset() {
        Object object = this.getContentTypeHeader();
        if (object != null) {
            if ((object = ((ContentType)object).getCharset()) == null) {
                object = this.contentEncodingCharset;
            }
            return object;
        }
        return this.contentEncodingCharset;
    }

    public Contact getContactHeader() {
        ContactList contactList = this.getContactHeaders();
        if (contactList != null) {
            return (Contact)contactList.getFirst();
        }
        return null;
    }

    public ContactList getContactHeaders() {
        return (ContactList)this.getSIPHeaderListLowerCase(CONTACT_LOWERCASE);
    }

    @Override
    public Object getContent() {
        Object object = this.messageContentObject;
        if (object != null) {
            return object;
        }
        object = this.messageContent;
        if (object != null) {
            return object;
        }
        object = this.messageContentBytes;
        if (object != null) {
            return object;
        }
        return null;
    }

    @Override
    public ContentDispositionHeader getContentDisposition() {
        return (ContentDispositionHeader)this.getHeaderLowerCase(CONTENT_DISPOSITION_LOWERCASE);
    }

    @Override
    public ContentEncodingHeader getContentEncoding() {
        return (ContentEncodingHeader)this.getHeaderLowerCase(CONTENT_ENCODING_LOWERCASE);
    }

    @Override
    public ContentLanguageHeader getContentLanguage() {
        return (ContentLanguageHeader)this.getHeaderLowerCase(CONTENT_LANGUAGE_LOWERCASE);
    }

    @Override
    public ContentLengthHeader getContentLength() {
        return this.contentLengthHeader;
    }

    @Override
    public ContentLengthHeader getContentLengthHeader() {
        return this.getContentLength();
    }

    @Override
    public ContentType getContentTypeHeader() {
        return (ContentType)this.getHeaderLowerCase(CONTENT_TYPE_LOWERCASE);
    }

    public abstract String getDialogId(boolean var1);

    public ErrorInfoList getErrorInfoHeaders() {
        return (ErrorInfoList)this.getSIPHeaderListLowerCase(ERROR_LOWERCASE);
    }

    @Override
    public ExpiresHeader getExpires() {
        return (ExpiresHeader)this.getHeaderLowerCase(EXPIRES_LOWERCASE);
    }

    @Override
    public abstract String getFirstLine();

    public FromHeader getFrom() {
        return this.fromHeader;
    }

    @Override
    public FromHeader getFromHeader() {
        return this.fromHeader;
    }

    public String getFromTag() {
        Object object = this.fromHeader;
        object = object == null ? null : ((From)object).getTag();
        return object;
    }

    @Override
    public Header getHeader(String string) {
        return this.getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(string));
    }

    public String getHeaderAsFormattedString(String string) {
        String string2 = string.toLowerCase();
        if (this.nameTable.containsKey(string2)) {
            return this.nameTable.get(string2).toString();
        }
        return this.getHeader(string).toString();
    }

    @Override
    public ListIterator<String> getHeaderNames() {
        Iterator<SIPHeader> iterator = this.headers.iterator();
        LinkedList<String> linkedList = new LinkedList<String>();
        while (iterator.hasNext()) {
            linkedList.add(iterator.next().getName());
        }
        return linkedList.listIterator();
    }

    public Iterator<SIPHeader> getHeaders() {
        return this.headers.iterator();
    }

    @Override
    public ListIterator<SIPHeader> getHeaders(String object) {
        if (object != null) {
            if ((object = this.nameTable.get(SIPHeaderNamesCache.toLowerCase((String)object))) == null) {
                return new LinkedList<E>().listIterator();
            }
            if (object instanceof SIPHeaderList) {
                return ((SIPHeaderList)object).listIterator();
            }
            return new HeaderIterator(this, (SIPHeader)object);
        }
        throw new NullPointerException("null headerName");
    }

    public MaxForwardsHeader getMaxForwards() {
        return this.maxForwardsHeader;
    }

    public LinkedList<String> getMessageAsEncodedStrings() {
        LinkedList<String> linkedList = new LinkedList<String>();
        for (SIPHeader sIPHeader : this.headers) {
            if (sIPHeader instanceof SIPHeaderList) {
                linkedList.addAll(((SIPHeaderList)sIPHeader).getHeadersAsEncodedStrings());
                continue;
            }
            linkedList.add(sIPHeader.encode());
        }
        return linkedList;
    }

    public String getMessageContent() throws UnsupportedEncodingException {
        if (this.messageContent == null && this.messageContentBytes == null) {
            return null;
        }
        if (this.messageContent == null) {
            this.messageContent = new String(this.messageContentBytes, this.getCharset());
        }
        return this.messageContent;
    }

    @Override
    public MultipartMimeContent getMultipartMimeContent() throws ParseException {
        if (this.contentLengthHeader.getContentLength() == 0) {
            return null;
        }
        MultipartMimeContentImpl multipartMimeContentImpl = new MultipartMimeContentImpl(this.getContentTypeHeader());
        byte[] arrby = this.getRawContent();
        try {
            String string = new String(arrby, this.getCharset());
            multipartMimeContentImpl.createContentList(string);
            return multipartMimeContentImpl;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            InternalErrorHandler.handleException(unsupportedEncodingException);
            return null;
        }
    }

    @Override
    public byte[] getRawContent() {
        try {
            if (this.messageContentBytes == null) {
                if (this.messageContentObject != null) {
                    this.messageContentBytes = this.messageContentObject.toString().getBytes(this.getCharset());
                } else if (this.messageContent != null) {
                    this.messageContentBytes = this.messageContent.getBytes(this.getCharset());
                }
            }
            byte[] arrby = this.messageContentBytes;
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            InternalErrorHandler.handleException(unsupportedEncodingException);
            return null;
        }
    }

    public RecordRouteList getRecordRouteHeaders() {
        return (RecordRouteList)this.getSIPHeaderListLowerCase(RECORDROUTE_LOWERCASE);
    }

    public RouteList getRouteHeaders() {
        return (RouteList)this.getSIPHeaderListLowerCase(ROUTE_LOWERCASE);
    }

    @Override
    public abstract String getSIPVersion();

    public int getSize() {
        return this.size;
    }

    public ToHeader getTo() {
        return this.toHeader;
    }

    @Override
    public ToHeader getToHeader() {
        return this.toHeader;
    }

    public String getToTag() {
        Object object = this.toHeader;
        object = object == null ? null : ((To)object).getTag();
        return object;
    }

    public Via getTopmostVia() {
        if (this.getViaHeaders() == null) {
            return null;
        }
        return (Via)this.getViaHeaders().getFirst();
    }

    @Override
    public ViaHeader getTopmostViaHeader() {
        return this.getTopmostVia();
    }

    public String getTransactionId() {
        Serializable serializable = null;
        if (!this.getViaHeaders().isEmpty()) {
            serializable = (Via)this.getViaHeaders().getFirst();
        }
        if (serializable != null && ((Via)serializable).getBranch() != null && ((Via)serializable).getBranch().toUpperCase().startsWith("Z9HG4BK")) {
            if (this.getCSeq().getMethod().equals("CANCEL")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((Via)serializable).getBranch());
                stringBuilder.append(":");
                stringBuilder.append(this.getCSeq().getMethod());
                return stringBuilder.toString().toLowerCase();
            }
            return ((Via)serializable).getBranch().toLowerCase();
        }
        StringBuffer stringBuffer = new StringBuffer();
        From from = (From)this.getFrom();
        To to = (To)this.getTo();
        if (from.hasTag()) {
            stringBuffer.append(from.getTag());
            stringBuffer.append("-");
        }
        stringBuffer.append(this.callIdHeader.getCallId());
        stringBuffer.append("-");
        stringBuffer.append(this.cSeqHeader.getSequenceNumber());
        stringBuffer.append("-");
        stringBuffer.append(this.cSeqHeader.getMethod());
        if (serializable != null) {
            stringBuffer.append("-");
            stringBuffer.append(((Via)serializable).getSentBy().encode());
            if (!((Via)serializable).getSentBy().hasPort()) {
                stringBuffer.append("-");
                stringBuffer.append(5060);
            }
        }
        if (this.getCSeq().getMethod().equals("CANCEL")) {
            stringBuffer.append("CANCEL");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(stringBuffer.toString().toLowerCase().replace(":", "-").replace("@", "-"));
        ((StringBuilder)serializable).append(Utils.getSignature());
        return ((StringBuilder)serializable).toString();
    }

    @Override
    public ListIterator<String> getUnrecognizedHeaders() {
        return this.unrecognizedHeaders.listIterator();
    }

    public ViaList getViaHeaders() {
        return (ViaList)this.getSIPHeaderListLowerCase(VIA_LOWERCASE);
    }

    public boolean hasContent() {
        boolean bl = this.messageContent != null || this.messageContentBytes != null;
        return bl;
    }

    public boolean hasFromTag() {
        From from = this.fromHeader;
        boolean bl = from != null && from.getTag() != null;
        return bl;
    }

    public boolean hasHeader(String string) {
        return this.nameTable.containsKey(SIPHeaderNamesCache.toLowerCase(string));
    }

    public boolean hasToTag() {
        To to = this.toHeader;
        boolean bl = to != null && to.getTag() != null;
        return bl;
    }

    @Override
    public int hashCode() {
        CallID callID = this.callIdHeader;
        if (callID != null) {
            return callID.getCallId().hashCode();
        }
        throw new RuntimeException("Invalid message! Cannot compute hashcode! call-id header is missing !");
    }

    public boolean isNullRequest() {
        return this.nullRequest;
    }

    @Override
    public boolean match(Object iterator) {
        if (iterator == null) {
            return true;
        }
        if (!iterator.getClass().equals(this.getClass())) {
            return false;
        }
        iterator = ((SIPMessage)((Object)iterator)).getHeaders();
        while (iterator.hasNext()) {
            SIPHeader sIPHeader = iterator.next();
            List<SIPHeader> list = this.getHeaderList(sIPHeader.getHeaderName());
            if (list != null && list.size() != 0) {
                boolean bl;
                block9 : {
                    boolean bl2;
                    if (sIPHeader instanceof SIPHeaderList) {
                        ListIterator<HDR> listIterator = ((SIPHeaderList)sIPHeader).listIterator();
                        while (listIterator.hasNext()) {
                            block8 : {
                                sIPHeader = (SIPHeader)listIterator.next();
                                if (sIPHeader instanceof ContentLength) continue;
                                ListIterator<E> listIterator2 = list.listIterator();
                                bl2 = false;
                                do {
                                    bl = bl2;
                                    if (!listIterator2.hasNext()) break block8;
                                } while (!((SIPHeader)listIterator2.next()).match(sIPHeader));
                                bl = true;
                            }
                            if (bl) continue;
                            return false;
                        }
                        continue;
                    }
                    list = list.listIterator();
                    bl2 = false;
                    do {
                        bl = bl2;
                        if (!list.hasNext()) break block9;
                    } while (!((SIPHeader)list.next()).match(sIPHeader));
                    bl = true;
                }
                if (bl) continue;
                return false;
            }
            return false;
        }
        return true;
    }

    @Override
    public void merge(Object object) {
        if (object.getClass().equals(this.getClass())) {
            Object[] arrobject = ((SIPMessage)object).headers.toArray();
            for (int i = 0; i < arrobject.length; ++i) {
                object = (SIPHeader)arrobject[i];
                List<SIPHeader> list = this.getHeaderList(((SIPHeader)object).getHeaderName());
                if (list == null) {
                    this.attachHeader((SIPHeader)object);
                    continue;
                }
                list = list.listIterator();
                while (list.hasNext()) {
                    ((SIPHeader)list.next()).merge(object);
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad class ");
        stringBuilder.append(object.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void removeContent() {
        this.messageContent = null;
        this.messageContentBytes = null;
        this.messageContentObject = null;
        try {
            this.contentLengthHeader.setContentLength(0);
        }
        catch (InvalidArgumentException invalidArgumentException) {
            // empty catch block
        }
    }

    @Override
    public void removeFirst(String string) throws NullPointerException {
        if (string != null) {
            this.removeHeader(string, true);
            return;
        }
        throw new NullPointerException("Null argument Provided!");
    }

    @Override
    public void removeHeader(String string) {
        if (string != null) {
            Object object = this.nameTable.remove(string = SIPHeaderNamesCache.toLowerCase(string));
            if (object == null) {
                return;
            }
            if (object instanceof From) {
                this.fromHeader = null;
            } else if (object instanceof To) {
                this.toHeader = null;
            } else if (object instanceof CSeq) {
                this.cSeqHeader = null;
            } else if (object instanceof CallID) {
                this.callIdHeader = null;
            } else if (object instanceof MaxForwards) {
                this.maxForwardsHeader = null;
            } else if (object instanceof ContentLength) {
                this.contentLengthHeader = null;
            }
            object = this.headers.iterator();
            while (object.hasNext()) {
                if (!((SIPHeader)object.next()).getName().equalsIgnoreCase(string)) continue;
                object.remove();
            }
            return;
        }
        throw new NullPointerException("null arg");
    }

    public void removeHeader(String iterator, boolean bl) {
        Object object = SIPHeaderNamesCache.toLowerCase((String)((Object)iterator));
        SIPHeader sIPHeader = this.nameTable.get(object);
        if (sIPHeader == null) {
            return;
        }
        if (sIPHeader instanceof SIPHeaderList) {
            iterator = (SIPHeaderList)sIPHeader;
            if (bl) {
                ((SIPHeaderList)((Object)iterator)).removeFirst();
            } else {
                ((SIPHeaderList)((Object)iterator)).removeLast();
            }
            if (((SIPHeaderList)((Object)iterator)).isEmpty()) {
                iterator = this.headers.iterator();
                while (iterator.hasNext()) {
                    if (!iterator.next().getName().equalsIgnoreCase((String)object)) continue;
                    iterator.remove();
                }
                this.nameTable.remove(object);
            }
        } else {
            this.nameTable.remove(object);
            if (sIPHeader instanceof From) {
                this.fromHeader = null;
            } else if (sIPHeader instanceof To) {
                this.toHeader = null;
            } else if (sIPHeader instanceof CSeq) {
                this.cSeqHeader = null;
            } else if (sIPHeader instanceof CallID) {
                this.callIdHeader = null;
            } else if (sIPHeader instanceof MaxForwards) {
                this.maxForwardsHeader = null;
            } else if (sIPHeader instanceof ContentLength) {
                this.contentLengthHeader = null;
            }
            object = this.headers.iterator();
            while (object.hasNext()) {
                if (!((SIPHeader)object.next()).getName().equalsIgnoreCase((String)((Object)iterator))) continue;
                object.remove();
            }
        }
    }

    @Override
    public void removeLast(String string) {
        if (string != null) {
            this.removeHeader(string, false);
            return;
        }
        throw new NullPointerException("Null argument Provided!");
    }

    @Override
    public void setApplicationData(Object object) {
        this.applicationData = object;
    }

    public void setCSeq(CSeqHeader cSeqHeader) {
        this.setHeader(cSeqHeader);
    }

    public void setCallId(String string) throws ParseException {
        if (this.callIdHeader == null) {
            this.setHeader(new CallID());
        }
        this.callIdHeader.setCallId(string);
    }

    public void setCallId(CallIdHeader callIdHeader) {
        this.setHeader(callIdHeader);
    }

    @Override
    public void setContent(Object object, ContentTypeHeader contentTypeHeader) throws ParseException {
        if (object != null) {
            this.setHeader(contentTypeHeader);
            this.messageContent = null;
            this.messageContentBytes = null;
            this.messageContentObject = null;
            if (object instanceof String) {
                this.messageContent = (String)object;
            } else if (object instanceof byte[]) {
                this.messageContentBytes = (byte[])object;
            } else {
                this.messageContentObject = object;
            }
            this.computeContentLength(object);
            return;
        }
        throw new NullPointerException("null content");
    }

    @Override
    public void setContentDisposition(ContentDispositionHeader contentDispositionHeader) {
        this.setHeader(contentDispositionHeader);
    }

    @Override
    public void setContentEncoding(ContentEncodingHeader contentEncodingHeader) {
        this.setHeader(contentEncodingHeader);
    }

    @Override
    public void setContentLanguage(ContentLanguageHeader contentLanguageHeader) {
        this.setHeader(contentLanguageHeader);
    }

    @Override
    public void setContentLength(ContentLengthHeader contentLengthHeader) {
        try {
            this.contentLengthHeader.setContentLength(contentLengthHeader.getContentLength());
        }
        catch (InvalidArgumentException invalidArgumentException) {
            // empty catch block
        }
    }

    @Override
    public void setExpires(ExpiresHeader expiresHeader) {
        this.setHeader(expiresHeader);
    }

    public void setFrom(FromHeader fromHeader) {
        this.setHeader(fromHeader);
    }

    public void setFromTag(String string) {
        try {
            this.fromHeader.setTag(string);
        }
        catch (ParseException parseException) {
            // empty catch block
        }
    }

    public void setHeader(SIPHeaderList<Via> sIPHeaderList) {
        this.setHeader((Header)sIPHeaderList);
    }

    @Override
    public void setHeader(Header header) {
        if ((header = (SIPHeader)header) != null) {
            try {
                if (header instanceof SIPHeaderList && ((SIPHeaderList)header).isEmpty()) {
                    return;
                }
                this.removeHeader(((SIPHeader)header).getHeaderName());
                this.attachHeader((SIPHeader)header, true, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                InternalErrorHandler.handleException(sIPDuplicateHeaderException);
            }
            return;
        }
        throw new IllegalArgumentException("null header!");
    }

    public void setHeaders(List<SIPHeader> object) {
        object = object.listIterator();
        while (object.hasNext()) {
            SIPHeader sIPHeader = (SIPHeader)object.next();
            try {
                this.attachHeader(sIPHeader, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {}
        }
    }

    public void setMaxForwards(MaxForwardsHeader maxForwardsHeader) {
        this.setHeader(maxForwardsHeader);
    }

    public void setMessageContent(String string, String string2, String string3) {
        if (string3 != null) {
            this.setHeader(new ContentType(string, string2));
            this.messageContent = string3;
            this.messageContentBytes = null;
            this.messageContentObject = null;
            this.computeContentLength(string3);
            return;
        }
        throw new IllegalArgumentException("messgeContent is null");
    }

    public void setMessageContent(String string, String string2, byte[] arrby) {
        this.setHeader(new ContentType(string, string2));
        this.setMessageContent(arrby);
        this.computeContentLength(arrby);
    }

    public void setMessageContent(String charSequence, boolean bl, boolean bl2, int n) throws ParseException {
        this.computeContentLength(charSequence);
        if (!bl2 && (!bl && this.contentLengthHeader.getContentLength() != n || this.contentLengthHeader.getContentLength() < n)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid content length ");
            ((StringBuilder)charSequence).append(this.contentLengthHeader.getContentLength());
            ((StringBuilder)charSequence).append(" / ");
            ((StringBuilder)charSequence).append(n);
            throw new ParseException(((StringBuilder)charSequence).toString(), 0);
        }
        this.messageContent = charSequence;
        this.messageContentBytes = null;
        this.messageContentObject = null;
    }

    public void setMessageContent(byte[] arrby) {
        this.computeContentLength(arrby);
        this.messageContentBytes = arrby;
        this.messageContent = null;
        this.messageContentObject = null;
    }

    public void setMessageContent(byte[] object, boolean bl, int n) throws ParseException {
        this.computeContentLength(object);
        if (!bl && this.contentLengthHeader.getContentLength() < n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid content length ");
            ((StringBuilder)object).append(this.contentLengthHeader.getContentLength());
            ((StringBuilder)object).append(" / ");
            ((StringBuilder)object).append(n);
            throw new ParseException(((StringBuilder)object).toString(), 0);
        }
        this.messageContentBytes = object;
        this.messageContent = null;
        this.messageContentObject = null;
    }

    public void setNullRequest() {
        this.nullRequest = true;
    }

    @Override
    public abstract void setSIPVersion(String var1) throws ParseException;

    public void setSize(int n) {
        this.size = n;
    }

    public void setTo(ToHeader toHeader) {
        this.setHeader(toHeader);
    }

    public void setToTag(String string) {
        try {
            this.toHeader.setTag(string);
        }
        catch (ParseException parseException) {
            // empty catch block
        }
    }

    public void setVia(List object) {
        ViaList viaList = new ViaList();
        object = object.listIterator();
        while (object.hasNext()) {
            viaList.add((Via)object.next());
        }
        this.setHeader(viaList);
    }

    @Override
    public abstract String toString();
}

