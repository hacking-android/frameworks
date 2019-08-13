/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.message.Content;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentTypeHeader;

public class ContentImpl
implements Content {
    private String boundary;
    private Object content;
    private ContentDispositionHeader contentDispositionHeader;
    private ContentTypeHeader contentTypeHeader;

    public ContentImpl(String string, String string2) {
        this.content = string;
        this.boundary = string2;
    }

    @Override
    public Object getContent() {
        return this.content;
    }

    @Override
    public ContentDispositionHeader getContentDispositionHeader() {
        return this.contentDispositionHeader;
    }

    @Override
    public ContentTypeHeader getContentTypeHeader() {
        return this.contentTypeHeader;
    }

    @Override
    public void setContent(Object object) {
        this.content = object;
    }

    public void setContentDispositionHeader(ContentDispositionHeader contentDispositionHeader) {
        this.contentDispositionHeader = contentDispositionHeader;
    }

    public void setContentTypeHeader(ContentTypeHeader contentTypeHeader) {
        this.contentTypeHeader = contentTypeHeader;
    }

    @Override
    public String toString() {
        if (this.boundary == null) {
            return this.content.toString();
        }
        if (this.contentDispositionHeader != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--");
            stringBuilder.append(this.boundary);
            stringBuilder.append("\r\n");
            stringBuilder.append(this.getContentTypeHeader());
            stringBuilder.append(this.getContentDispositionHeader().toString());
            stringBuilder.append("\r\n");
            stringBuilder.append(this.content.toString());
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(this.boundary);
        stringBuilder.append("\r\n");
        stringBuilder.append(this.getContentTypeHeader());
        stringBuilder.append("\r\n");
        stringBuilder.append(this.content.toString());
        return stringBuilder.toString();
    }
}

