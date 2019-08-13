/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentTypeHeader;

public interface Content {
    public Object getContent();

    public ContentDispositionHeader getContentDispositionHeader();

    public ContentTypeHeader getContentTypeHeader();

    public void setContent(Object var1);

    public String toString();
}

