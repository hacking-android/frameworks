/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URLConnection;

class UnknownContentHandler
extends ContentHandler {
    static final ContentHandler INSTANCE = new UnknownContentHandler();

    UnknownContentHandler() {
    }

    @Override
    public Object getContent(URLConnection uRLConnection) throws IOException {
        return uRLConnection.getInputStream();
    }
}

