/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.message.MultipartMimeContent;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.message.MessageFactory;

public interface MessageFactoryExt
extends MessageFactory {
    public MultipartMimeContent createMultipartMimeContent(ContentTypeHeader var1, String[] var2, String[] var3, String[] var4);

    @Override
    public void setDefaultContentEncodingCharset(String var1) throws NullPointerException, IllegalArgumentException;

    @Override
    public void setDefaultServerHeader(ServerHeader var1);

    @Override
    public void setDefaultUserAgentHeader(UserAgentHeader var1);
}

