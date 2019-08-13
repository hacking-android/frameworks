/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.message.MultipartMimeContent;
import java.text.ParseException;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContentLengthHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Message;

public interface MessageExt
extends Message {
    @Override
    public Object getApplicationData();

    public CSeqHeader getCSeqHeader();

    public CallIdHeader getCallIdHeader();

    public ContentLengthHeader getContentLengthHeader();

    public ContentTypeHeader getContentTypeHeader();

    public String getFirstLine();

    public FromHeader getFromHeader();

    public MultipartMimeContent getMultipartMimeContent() throws ParseException;

    public ToHeader getToHeader();

    public ViaHeader getTopmostViaHeader();

    @Override
    public void setApplicationData(Object var1);
}

