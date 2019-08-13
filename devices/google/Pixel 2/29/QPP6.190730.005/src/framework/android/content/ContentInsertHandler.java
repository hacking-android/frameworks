/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentResolver;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface ContentInsertHandler
extends ContentHandler {
    public void insert(ContentResolver var1, InputStream var2) throws IOException, SAXException;

    public void insert(ContentResolver var1, String var2) throws SAXException;
}

