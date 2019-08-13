/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.IOException;
import java.io.Reader;
import org.ccil.cowan.tagsoup.ScanHandler;
import org.xml.sax.SAXException;

public interface Scanner {
    public void resetDocumentLocator(String var1, String var2);

    public void scan(Reader var1, ScanHandler var2) throws IOException, SAXException;

    public void startCDATA();
}

