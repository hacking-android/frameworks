/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.URLConnection;

public abstract class ContentHandler {
    public abstract Object getContent(URLConnection var1) throws IOException;

    public Object getContent(URLConnection object, Class[] arrclass) throws IOException {
        object = this.getContent((URLConnection)object);
        for (int i = 0; i < arrclass.length; ++i) {
            if (!arrclass[i].isInstance(object)) continue;
            return object;
        }
        return null;
    }
}

