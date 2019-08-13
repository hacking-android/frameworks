/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.ReasonList;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.RequireList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.header.extensions.SessionExpires;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.ResponseExt;
import gov.nist.javax.sip.message.SIPDuplicateHeaderException;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ReasonHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Response;

public final class SIPResponse
extends SIPMessage
implements Response,
ResponseExt {
    protected StatusLine statusLine;

    public static String getReasonPhrase(int n) {
        String string;
        if (n != 420) {
            if (n != 421) {
                if (n != 603) {
                    if (n != 604) {
                        block0 : switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        switch (n) {
                                            default: {
                                                switch (n) {
                                                    default: {
                                                        switch (n) {
                                                            default: {
                                                                switch (n) {
                                                                    default: {
                                                                        switch (n) {
                                                                            default: {
                                                                                string = "Unknown Status";
                                                                                break block0;
                                                                            }
                                                                            case 505: {
                                                                                string = "SIP version not supported";
                                                                                break block0;
                                                                            }
                                                                            case 504: {
                                                                                string = "Gateway timeout";
                                                                                break block0;
                                                                            }
                                                                            case 503: {
                                                                                string = "Service unavailable";
                                                                                break block0;
                                                                            }
                                                                            case 502: {
                                                                                string = "Bad gateway";
                                                                                break block0;
                                                                            }
                                                                            case 501: {
                                                                                string = "Not implemented";
                                                                                break block0;
                                                                            }
                                                                            case 500: 
                                                                        }
                                                                        string = "Server Internal Error";
                                                                        break block0;
                                                                    }
                                                                    case 489: {
                                                                        string = "Bad Event";
                                                                        break block0;
                                                                    }
                                                                    case 488: {
                                                                        string = "Not Acceptable here";
                                                                        break block0;
                                                                    }
                                                                    case 487: {
                                                                        string = "Request Terminated";
                                                                        break block0;
                                                                    }
                                                                    case 486: {
                                                                        string = "Busy here";
                                                                        break block0;
                                                                    }
                                                                    case 485: {
                                                                        string = "Ambiguous";
                                                                        break block0;
                                                                    }
                                                                    case 484: {
                                                                        string = "Address incomplete";
                                                                        break block0;
                                                                    }
                                                                    case 483: {
                                                                        string = "Too many hops";
                                                                        break block0;
                                                                    }
                                                                    case 482: {
                                                                        string = "Loop detected";
                                                                        break block0;
                                                                    }
                                                                    case 481: {
                                                                        string = "Call leg/Transaction does not exist";
                                                                        break block0;
                                                                    }
                                                                    case 480: 
                                                                }
                                                                string = "Temporarily Unavailable";
                                                                break block0;
                                                            }
                                                            case 416: {
                                                                string = "Unsupported URI Scheme";
                                                                break block0;
                                                            }
                                                            case 415: {
                                                                string = "Unsupported media type";
                                                                break block0;
                                                            }
                                                            case 414: {
                                                                string = "Request-URI too large";
                                                                break block0;
                                                            }
                                                            case 413: {
                                                                string = "Request entity too large";
                                                                break block0;
                                                            }
                                                            case 412: 
                                                        }
                                                        string = "Conditional request failed";
                                                        break block0;
                                                    }
                                                    case 408: {
                                                        string = "Request timeout";
                                                        break block0;
                                                    }
                                                    case 407: {
                                                        string = "Proxy Authentication required";
                                                        break block0;
                                                    }
                                                    case 406: {
                                                        string = "Not acceptable";
                                                        break block0;
                                                    }
                                                    case 405: {
                                                        string = "Method not allowed";
                                                        break block0;
                                                    }
                                                    case 404: {
                                                        string = "Not found";
                                                        break block0;
                                                    }
                                                    case 403: {
                                                        string = "Forbidden";
                                                        break block0;
                                                    }
                                                    case 402: {
                                                        string = "Payment required";
                                                        break block0;
                                                    }
                                                    case 401: {
                                                        string = "Unauthorized";
                                                        break block0;
                                                    }
                                                    case 400: 
                                                }
                                                string = "Bad request";
                                                break block0;
                                            }
                                            case 302: {
                                                string = "Moved Temporarily";
                                                break block0;
                                            }
                                            case 301: {
                                                string = "Moved permanently";
                                                break block0;
                                            }
                                            case 300: 
                                        }
                                        string = "Multiple choices";
                                        break block0;
                                    }
                                    case 183: {
                                        string = "Session progress";
                                        break block0;
                                    }
                                    case 182: {
                                        string = "Queued";
                                        break block0;
                                    }
                                    case 181: {
                                        string = "Call is being forwarded";
                                        break block0;
                                    }
                                    case 180: 
                                }
                                string = "Ringing";
                                break;
                            }
                            case 606: {
                                string = "Session Not acceptable";
                                break;
                            }
                            case 600: {
                                string = "Busy everywhere";
                                break;
                            }
                            case 513: {
                                string = "Message Too Large";
                                break;
                            }
                            case 493: {
                                string = "Undecipherable";
                                break;
                            }
                            case 491: {
                                string = "Request Pending";
                                break;
                            }
                            case 423: {
                                string = "Interval too brief";
                                break;
                            }
                            case 410: {
                                string = "Gone";
                                break;
                            }
                            case 380: {
                                string = "Alternative service";
                                break;
                            }
                            case 305: {
                                string = "Use proxy";
                                break;
                            }
                            case 202: {
                                string = "Accepted";
                                break;
                            }
                            case 200: {
                                string = "OK";
                                break;
                            }
                            case 100: {
                                string = "Trying";
                                break;
                            }
                        }
                    } else {
                        string = "Does not exist anywhere";
                    }
                } else {
                    string = "Decline";
                }
            } else {
                string = "Etension Required";
            }
        } else {
            string = "Bad extension";
        }
        return string;
    }

    public static boolean isFinalResponse(int n) {
        boolean bl = n >= 200 && n < 700;
        return bl;
    }

    private final void setBranch(Via via, String string) {
        block5 : {
            block4 : {
                block3 : {
                    if (!string.equals("ACK")) break block3;
                    string = this.statusLine.getStatusCode() >= 300 ? this.getTopmostVia().getBranch() : Utils.getInstance().generateBranchId();
                    break block4;
                }
                if (!string.equals("CANCEL")) break block5;
                string = this.getTopmostVia().getBranch();
            }
            try {
                via.setBranch(string);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            return;
        }
    }

    public void checkHeaders() throws ParseException {
        if (this.getCSeq() != null) {
            if (this.getTo() != null) {
                if (this.getFrom() != null) {
                    if (this.getViaHeaders() != null) {
                        if (this.getCallId() != null) {
                            if (this.getStatusCode() <= 699) {
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown error code!");
                            stringBuilder.append(this.getStatusCode());
                            throw new ParseException(stringBuilder.toString(), 0);
                        }
                        throw new ParseException("Call-ID Is missing ", 0);
                    }
                    throw new ParseException("Via Is missing ", 0);
                }
                throw new ParseException("From Is missing ", 0);
            }
            throw new ParseException("To Is missing ", 0);
        }
        throw new ParseException("CSeq Is missing ", 0);
    }

    @Override
    public Object clone() {
        SIPResponse sIPResponse = (SIPResponse)super.clone();
        StatusLine statusLine = this.statusLine;
        if (statusLine != null) {
            sIPResponse.statusLine = (StatusLine)statusLine.clone();
        }
        return sIPResponse;
    }

    public SIPRequest createRequest(SipUri genericObject, Via sIPHeader, CSeq object, From from, To to) {
        SIPRequest sIPRequest = new SIPRequest();
        String string = ((CSeq)object).getMethod();
        sIPRequest.setMethod(string);
        sIPRequest.setRequestURI((URI)((Object)genericObject));
        this.setBranch((Via)sIPHeader, string);
        sIPRequest.setHeader(sIPHeader);
        sIPRequest.setHeader((Header)object);
        object = this.getHeaders();
        while (object.hasNext()) {
            sIPHeader = (SIPHeader)object.next();
            if (SIPMessage.isResponseHeader(sIPHeader) || sIPHeader instanceof ViaList || sIPHeader instanceof CSeq || sIPHeader instanceof ContentType || sIPHeader instanceof ContentLength || sIPHeader instanceof RecordRouteList || sIPHeader instanceof RequireList || sIPHeader instanceof ContactList || sIPHeader instanceof ContentLength || sIPHeader instanceof ServerHeader || sIPHeader instanceof ReasonHeader || sIPHeader instanceof SessionExpires || sIPHeader instanceof ReasonList) continue;
            if (sIPHeader instanceof To) {
                genericObject = to;
            } else {
                genericObject = sIPHeader;
                if (sIPHeader instanceof From) {
                    genericObject = from;
                }
            }
            try {
                sIPRequest.attachHeader((SIPHeader)genericObject, false);
            }
            catch (SIPDuplicateHeaderException sIPDuplicateHeaderException) {
                sIPDuplicateHeaderException.printStackTrace();
            }
        }
        try {
            genericObject = new MaxForwards(70);
            sIPRequest.attachHeader((SIPHeader)genericObject, false);
        }
        catch (Exception exception) {
            // empty catch block
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
        this.sprint(SIPResponse.class.getCanonicalName());
        this.sprint("{");
        StatusLine statusLine = this.statusLine;
        if (statusLine != null) {
            this.sprint(statusLine.debugDump());
        }
        this.sprint(string);
        this.sprint("}");
        return this.stringRepresentation;
    }

    @Override
    public String encode() {
        CharSequence charSequence;
        if (this.statusLine != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.statusLine.encode());
            ((StringBuilder)charSequence).append(super.encode());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = super.encode();
        }
        return charSequence;
    }

    @Override
    public byte[] encodeAsBytes(String arrby) {
        byte[] arrby2 = null;
        StatusLine statusLine = this.statusLine;
        byte[] arrby3 = arrby2;
        if (statusLine != null) {
            try {
                arrby3 = statusLine.encode().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                InternalErrorHandler.handleException(unsupportedEncodingException);
                arrby3 = arrby2;
            }
        }
        arrby2 = super.encodeAsBytes((String)arrby);
        arrby = new byte[arrby3.length + arrby2.length];
        System.arraycopy(arrby3, 0, arrby, 0, arrby3.length);
        System.arraycopy(arrby2, 0, arrby, arrby3.length, arrby2.length);
        return arrby;
    }

    @Override
    public String encodeMessage() {
        CharSequence charSequence;
        if (this.statusLine != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.statusLine.encode());
            ((StringBuilder)charSequence).append(super.encodeSIPHeaders());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = super.encodeSIPHeaders();
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
        SIPResponse sIPResponse = (SIPResponse)object;
        bl = bl2;
        if (this.statusLine.equals(sIPResponse.statusLine)) {
            bl = bl2;
            if (super.equals(object)) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public String getDialogId(boolean bl) {
        Serializable serializable = (CallID)this.getCallId();
        From from = (From)this.getFrom();
        To to = (To)this.getTo();
        serializable = new StringBuffer(((CallID)serializable).getCallId());
        if (!bl) {
            if (from.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(from.getTag());
            }
            if (to.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(to.getTag());
            }
        } else {
            if (to.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(to.getTag());
            }
            if (from.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(from.getTag());
            }
        }
        return ((StringBuffer)serializable).toString().toLowerCase();
    }

    public String getDialogId(boolean bl, String string) {
        Serializable serializable = (CallID)this.getCallId();
        From from = (From)this.getFrom();
        serializable = new StringBuffer(((CallID)serializable).getCallId());
        if (!bl) {
            if (from.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(from.getTag());
            }
            if (string != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(string);
            }
        } else {
            if (string != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(string);
            }
            if (from.getTag() != null) {
                ((StringBuffer)serializable).append(":");
                ((StringBuffer)serializable).append(from.getTag());
            }
        }
        return ((StringBuffer)serializable).toString().toLowerCase();
    }

    @Override
    public String getFirstLine() {
        StatusLine statusLine = this.statusLine;
        if (statusLine == null) {
            return null;
        }
        return statusLine.encode();
    }

    public LinkedList getMessageAsEncodedStrings() {
        LinkedList<String> linkedList = super.getMessageAsEncodedStrings();
        StatusLine statusLine = this.statusLine;
        if (statusLine != null) {
            linkedList.addFirst(statusLine.encode());
        }
        return linkedList;
    }

    @Override
    public String getReasonPhrase() {
        StatusLine statusLine = this.statusLine;
        if (statusLine != null && statusLine.getReasonPhrase() != null) {
            return this.statusLine.getReasonPhrase();
        }
        return "";
    }

    @Override
    public String getSIPVersion() {
        return this.statusLine.getSipVersion();
    }

    @Override
    public int getStatusCode() {
        return this.statusLine.getStatusCode();
    }

    public StatusLine getStatusLine() {
        return this.statusLine;
    }

    public boolean isFinalResponse() {
        return SIPResponse.isFinalResponse(this.statusLine.getStatusCode());
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
        SIPResponse sIPResponse = (SIPResponse)object;
        StatusLine statusLine = sIPResponse.statusLine;
        if (this.statusLine == null && statusLine != null) {
            return false;
        }
        StatusLine statusLine2 = this.statusLine;
        if (statusLine2 == statusLine) {
            return super.match(object);
        }
        if (!statusLine2.match(sIPResponse.statusLine) || !super.match(object)) {
            bl = false;
        }
        return bl;
    }

    @Override
    public void setReasonPhrase(String string) {
        if (string != null) {
            if (this.statusLine == null) {
                this.statusLine = new StatusLine();
            }
            this.statusLine.setReasonPhrase(string);
            return;
        }
        throw new IllegalArgumentException("Bad reason phrase");
    }

    @Override
    public void setSIPVersion(String string) {
        this.statusLine.setSipVersion(string);
    }

    @Override
    public void setStatusCode(int n) throws ParseException {
        if (n >= 100 && n <= 699) {
            if (this.statusLine == null) {
                this.statusLine = new StatusLine();
            }
            this.statusLine.setStatusCode(n);
            return;
        }
        throw new ParseException("bad status code", 0);
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    @Override
    public String toString() {
        if (this.statusLine == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.statusLine.encode());
        stringBuilder.append(super.encode());
        return stringBuilder.toString();
    }
}

