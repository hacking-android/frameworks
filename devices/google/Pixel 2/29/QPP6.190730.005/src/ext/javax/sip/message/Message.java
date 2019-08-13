/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.message;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ListIterator;
import javax.sip.SipException;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentEncodingHeader;
import javax.sip.header.ContentLanguageHeader;
import javax.sip.header.ContentLengthHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.Header;

public interface Message
extends Cloneable,
Serializable {
    public void addFirst(Header var1) throws SipException, NullPointerException;

    public void addHeader(Header var1);

    public void addLast(Header var1) throws SipException, NullPointerException;

    public Object clone();

    public boolean equals(Object var1);

    public Object getApplicationData();

    public Object getContent();

    public ContentDispositionHeader getContentDisposition();

    public ContentEncodingHeader getContentEncoding();

    public ContentLanguageHeader getContentLanguage();

    public ContentLengthHeader getContentLength();

    public ExpiresHeader getExpires();

    public Header getHeader(String var1);

    public ListIterator getHeaderNames();

    public ListIterator getHeaders(String var1);

    public byte[] getRawContent();

    public String getSIPVersion();

    public ListIterator getUnrecognizedHeaders();

    public int hashCode();

    public void removeContent();

    public void removeFirst(String var1) throws NullPointerException;

    public void removeHeader(String var1);

    public void removeLast(String var1) throws NullPointerException;

    public void setApplicationData(Object var1);

    public void setContent(Object var1, ContentTypeHeader var2) throws ParseException;

    public void setContentDisposition(ContentDispositionHeader var1);

    public void setContentEncoding(ContentEncodingHeader var1);

    public void setContentLanguage(ContentLanguageHeader var1);

    public void setContentLength(ContentLengthHeader var1);

    public void setExpires(ExpiresHeader var1);

    public void setHeader(Header var1);

    public void setSIPVersion(String var1) throws ParseException;

    public String toString();
}

