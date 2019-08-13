/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.message.Content;
import java.util.Iterator;
import javax.sip.header.ContentTypeHeader;

public interface MultipartMimeContent {
    public boolean add(Content var1);

    public void addContent(Content var1);

    public int getContentCount();

    public ContentTypeHeader getContentTypeHeader();

    public Iterator<Content> getContents();

    public String toString();
}

