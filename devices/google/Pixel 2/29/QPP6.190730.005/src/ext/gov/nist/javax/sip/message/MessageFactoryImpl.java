/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.Content;
import gov.nist.javax.sip.message.ContentImpl;
import gov.nist.javax.sip.message.MessageFactoryExt;
import gov.nist.javax.sip.message.MultipartMimeContent;
import gov.nist.javax.sip.message.MultipartMimeContentImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.parser.ParseExceptionListener;
import gov.nist.javax.sip.parser.StringMsgParser;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.message.Message;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class MessageFactoryImpl
implements MessageFactory,
MessageFactoryExt {
    private static String defaultContentEncodingCharset = "UTF-8";
    private static ServerHeader server;
    private static UserAgentHeader userAgent;
    private boolean strict = true;
    private boolean testing = false;

    public static String getDefaultContentEncodingCharset() {
        return defaultContentEncodingCharset;
    }

    public static ServerHeader getDefaultServerHeader() {
        return server;
    }

    public static UserAgentHeader getDefaultUserAgentHeader() {
        return userAgent;
    }

    @Override
    public MultipartMimeContent createMultipartMimeContent(ContentTypeHeader object, String[] arrstring, String[] arrstring2, String[] arrstring3) {
        String string = object.getParameter("boundary");
        object = new MultipartMimeContentImpl((ContentTypeHeader)object);
        for (int i = 0; i < arrstring.length; ++i) {
            ContentType contentType = new ContentType(arrstring[i], arrstring2[i]);
            ContentImpl contentImpl = new ContentImpl(arrstring3[i], string);
            contentImpl.setContentTypeHeader(contentType);
            ((MultipartMimeContentImpl)object).add(contentImpl);
        }
        return object;
    }

    @Override
    public Request createRequest(String object) throws ParseException {
        if (object != null && !((String)object).equals("")) {
            Object object2 = new StringMsgParser();
            ((StringMsgParser)object2).setStrict(this.strict);
            ParseExceptionListener parseExceptionListener = new ParseExceptionListener(){

                @Override
                public void handleException(ParseException parseException, SIPMessage sIPMessage, Class class_, String string, String string2) throws ParseException {
                    if (MessageFactoryImpl.this.testing) {
                        if (class_ != From.class && class_ != To.class && class_ != CallID.class && class_ != MaxForwards.class && class_ != Via.class && class_ != RequestLine.class && class_ != StatusLine.class && class_ != CSeq.class) {
                            sIPMessage.addUnparsed(string);
                        } else {
                            throw parseException;
                        }
                    }
                }
            };
            if (this.testing) {
                ((StringMsgParser)object2).setParseExceptionListener(parseExceptionListener);
            }
            if ((object2 = ((StringMsgParser)object2).parseSIPMessage((String)object)) instanceof SIPRequest) {
                return (SIPRequest)object2;
            }
            throw new ParseException((String)object, 0);
        }
        object = new SIPRequest();
        ((SIPMessage)object).setNullRequest();
        return object;
    }

    @Override
    public Request createRequest(URI cloneable, String string, CallIdHeader callIdHeader, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader) throws ParseException {
        if (cloneable != null && string != null && callIdHeader != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null) {
            SIPRequest sIPRequest = new SIPRequest();
            sIPRequest.setRequestURI((URI)cloneable);
            sIPRequest.setMethod(string);
            sIPRequest.setCallId(callIdHeader);
            sIPRequest.setCSeq(cSeqHeader);
            sIPRequest.setFrom(fromHeader);
            sIPRequest.setTo(toHeader);
            sIPRequest.setVia(list);
            sIPRequest.setMaxForwards(maxForwardsHeader);
            cloneable = userAgent;
            if (cloneable != null) {
                sIPRequest.setHeader((Header)cloneable);
            }
            return sIPRequest;
        }
        throw new ParseException("JAIN-SIP Exception, some parameters are missing, unable to create the request", 0);
    }

    @Override
    public Request createRequest(URI cloneable, String string, CallIdHeader callIdHeader, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader, ContentTypeHeader contentTypeHeader, Object object) throws ParseException {
        if (cloneable != null && string != null && callIdHeader != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null && object != null && contentTypeHeader != null) {
            SIPRequest sIPRequest = new SIPRequest();
            sIPRequest.setRequestURI((URI)cloneable);
            sIPRequest.setMethod(string);
            sIPRequest.setCallId(callIdHeader);
            sIPRequest.setCSeq(cSeqHeader);
            sIPRequest.setFrom(fromHeader);
            sIPRequest.setTo(toHeader);
            sIPRequest.setVia(list);
            sIPRequest.setMaxForwards(maxForwardsHeader);
            sIPRequest.setContent(object, contentTypeHeader);
            cloneable = userAgent;
            if (cloneable != null) {
                sIPRequest.setHeader((Header)cloneable);
            }
            return sIPRequest;
        }
        throw new NullPointerException("Null parameters");
    }

    @Override
    public Request createRequest(URI cloneable, String string, CallIdHeader callIdHeader, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader, ContentTypeHeader contentTypeHeader, byte[] arrby) throws ParseException {
        if (cloneable != null && string != null && callIdHeader != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null && arrby != null && contentTypeHeader != null) {
            SIPRequest sIPRequest = new SIPRequest();
            sIPRequest.setRequestURI((URI)cloneable);
            sIPRequest.setMethod(string);
            sIPRequest.setCallId(callIdHeader);
            sIPRequest.setCSeq(cSeqHeader);
            sIPRequest.setFrom(fromHeader);
            sIPRequest.setTo(toHeader);
            sIPRequest.setVia(list);
            sIPRequest.setMaxForwards(maxForwardsHeader);
            sIPRequest.setContent(arrby, contentTypeHeader);
            cloneable = userAgent;
            if (cloneable != null) {
                sIPRequest.setHeader((Header)cloneable);
            }
            return sIPRequest;
        }
        throw new NullPointerException("missing parameters");
    }

    public Request createRequest(URI cloneable, String string, CallIdHeader callIdHeader, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader, byte[] arrby, ContentTypeHeader contentTypeHeader) throws ParseException {
        if (cloneable != null && string != null && callIdHeader != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null && arrby != null && contentTypeHeader != null) {
            SIPRequest sIPRequest = new SIPRequest();
            sIPRequest.setRequestURI((URI)cloneable);
            sIPRequest.setMethod(string);
            sIPRequest.setCallId(callIdHeader);
            sIPRequest.setCSeq(cSeqHeader);
            sIPRequest.setFrom(fromHeader);
            sIPRequest.setTo(toHeader);
            sIPRequest.setVia(list);
            sIPRequest.setMaxForwards(maxForwardsHeader);
            sIPRequest.setHeader((ContentType)contentTypeHeader);
            sIPRequest.setMessageContent(arrby);
            cloneable = userAgent;
            if (cloneable != null) {
                sIPRequest.setHeader((Header)cloneable);
            }
            return sIPRequest;
        }
        throw new ParseException("JAIN-SIP Exception, some parameters are missing, unable to create the request", 0);
    }

    @Override
    public Response createResponse(int n, CallIdHeader header, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader) throws ParseException {
        if (header != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null) {
            SIPResponse sIPResponse = new SIPResponse();
            sIPResponse.setStatusCode(n);
            sIPResponse.setCallId((CallIdHeader)header);
            sIPResponse.setCSeq(cSeqHeader);
            sIPResponse.setFrom(fromHeader);
            sIPResponse.setTo(toHeader);
            sIPResponse.setVia(list);
            sIPResponse.setMaxForwards(maxForwardsHeader);
            header = userAgent;
            if (header != null) {
                sIPResponse.setHeader(header);
            }
            return sIPResponse;
        }
        throw new ParseException("JAIN-SIP Exception, some parameters are missing, unable to create the response", 0);
    }

    public Response createResponse(int n, CallIdHeader header, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader, Object object, ContentTypeHeader contentTypeHeader) throws ParseException {
        if (header != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null && object != null && contentTypeHeader != null) {
            SIPResponse sIPResponse = new SIPResponse();
            StatusLine statusLine = new StatusLine();
            statusLine.setStatusCode(n);
            statusLine.setReasonPhrase(SIPResponse.getReasonPhrase(n));
            sIPResponse.setStatusLine(statusLine);
            sIPResponse.setCallId((CallIdHeader)header);
            sIPResponse.setCSeq(cSeqHeader);
            sIPResponse.setFrom(fromHeader);
            sIPResponse.setTo(toHeader);
            sIPResponse.setVia(list);
            sIPResponse.setMaxForwards(maxForwardsHeader);
            sIPResponse.setContent(object, contentTypeHeader);
            header = userAgent;
            if (header != null) {
                sIPResponse.setHeader(header);
            }
            return sIPResponse;
        }
        throw new NullPointerException(" unable to create the response");
    }

    @Override
    public Response createResponse(int n, CallIdHeader serializable, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader cloneable, ContentTypeHeader contentTypeHeader, Object object) throws ParseException {
        if (serializable != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && cloneable != null && object != null && contentTypeHeader != null) {
            cloneable = new SIPResponse();
            StatusLine statusLine = new StatusLine();
            statusLine.setStatusCode(n);
            String string = SIPResponse.getReasonPhrase(n);
            if (string != null) {
                statusLine.setReasonPhrase(string);
                ((SIPResponse)cloneable).setStatusLine(statusLine);
                ((SIPMessage)cloneable).setCallId((CallIdHeader)serializable);
                ((SIPMessage)cloneable).setCSeq(cSeqHeader);
                ((SIPMessage)cloneable).setFrom(fromHeader);
                ((SIPMessage)cloneable).setTo(toHeader);
                ((SIPMessage)cloneable).setVia(list);
                ((SIPMessage)cloneable).setContent(object, contentTypeHeader);
                serializable = userAgent;
                if (serializable != null) {
                    ((SIPMessage)cloneable).setHeader((Header)serializable);
                }
                return cloneable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append(" Unknown");
            throw new ParseException(((StringBuilder)serializable).toString(), 0);
        }
        throw new NullPointerException("missing parameters");
    }

    @Override
    public Response createResponse(int n, CallIdHeader serializable, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader cloneable, ContentTypeHeader contentTypeHeader, byte[] arrby) throws ParseException {
        if (serializable != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && cloneable != null && arrby != null && contentTypeHeader != null) {
            cloneable = new SIPResponse();
            StatusLine statusLine = new StatusLine();
            statusLine.setStatusCode(n);
            String string = SIPResponse.getReasonPhrase(n);
            if (string != null) {
                statusLine.setReasonPhrase(string);
                ((SIPResponse)cloneable).setStatusLine(statusLine);
                ((SIPMessage)cloneable).setCallId((CallIdHeader)serializable);
                ((SIPMessage)cloneable).setCSeq(cSeqHeader);
                ((SIPMessage)cloneable).setFrom(fromHeader);
                ((SIPMessage)cloneable).setTo(toHeader);
                ((SIPMessage)cloneable).setVia(list);
                ((SIPMessage)cloneable).setContent(arrby, contentTypeHeader);
                serializable = userAgent;
                if (serializable != null) {
                    ((SIPMessage)cloneable).setHeader((Header)serializable);
                }
                return cloneable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append(" : Unknown");
            throw new ParseException(((StringBuilder)serializable).toString(), 0);
        }
        throw new NullPointerException("missing parameters");
    }

    public Response createResponse(int n, CallIdHeader header, CSeqHeader cSeqHeader, FromHeader fromHeader, ToHeader toHeader, List list, MaxForwardsHeader maxForwardsHeader, byte[] arrby, ContentTypeHeader contentTypeHeader) throws ParseException {
        if (header != null && cSeqHeader != null && fromHeader != null && toHeader != null && list != null && maxForwardsHeader != null && arrby != null && contentTypeHeader != null) {
            SIPResponse sIPResponse = new SIPResponse();
            sIPResponse.setStatusCode(n);
            sIPResponse.setCallId((CallIdHeader)header);
            sIPResponse.setCSeq(cSeqHeader);
            sIPResponse.setFrom(fromHeader);
            sIPResponse.setTo(toHeader);
            sIPResponse.setVia(list);
            sIPResponse.setMaxForwards(maxForwardsHeader);
            sIPResponse.setHeader((ContentType)contentTypeHeader);
            sIPResponse.setMessageContent(arrby);
            header = userAgent;
            if (header != null) {
                sIPResponse.setHeader(header);
            }
            return sIPResponse;
        }
        throw new NullPointerException("Null params ");
    }

    @Override
    public Response createResponse(int n, Request message) throws ParseException {
        if (message != null) {
            message = ((SIPRequest)message).createResponse(n);
            ((SIPMessage)message).removeContent();
            ((SIPMessage)message).removeHeader("Content-Type");
            ServerHeader serverHeader = server;
            if (serverHeader != null) {
                ((SIPMessage)message).setHeader(serverHeader);
            }
            return message;
        }
        throw new NullPointerException("null parameters");
    }

    @Override
    public Response createResponse(int n, Request message, ContentTypeHeader header, Object object) throws ParseException {
        if (message != null && object != null && header != null) {
            message = ((SIPRequest)message).createResponse(n);
            ((SIPMessage)message).setContent(object, (ContentTypeHeader)header);
            header = server;
            if (header != null) {
                ((SIPMessage)message).setHeader(header);
            }
            return message;
        }
        throw new NullPointerException("null parameters");
    }

    @Override
    public Response createResponse(int n, Request message, ContentTypeHeader header, byte[] arrby) throws ParseException {
        if (message != null && arrby != null && header != null) {
            message = ((SIPRequest)message).createResponse(n);
            ((SIPMessage)message).setHeader((ContentType)header);
            ((SIPMessage)message).setMessageContent(arrby);
            header = server;
            if (header != null) {
                ((SIPMessage)message).setHeader(header);
            }
            return message;
        }
        throw new NullPointerException("null Parameters");
    }

    @Override
    public Response createResponse(String string) throws ParseException {
        if (string == null) {
            return new SIPResponse();
        }
        SIPMessage sIPMessage = new StringMsgParser().parseSIPMessage(string);
        if (sIPMessage instanceof SIPResponse) {
            return (SIPResponse)sIPMessage;
        }
        throw new ParseException(string, 0);
    }

    @Override
    public void setDefaultContentEncodingCharset(String string) throws NullPointerException, IllegalArgumentException {
        if (string != null) {
            defaultContentEncodingCharset = string;
            return;
        }
        throw new NullPointerException("Null argument!");
    }

    @Override
    public void setDefaultServerHeader(ServerHeader serverHeader) {
        server = serverHeader;
    }

    @Override
    public void setDefaultUserAgentHeader(UserAgentHeader userAgentHeader) {
        userAgent = userAgentHeader;
    }

    public void setStrict(boolean bl) {
        this.strict = bl;
    }

    public void setTest(boolean bl) {
        this.testing = bl;
    }

}

