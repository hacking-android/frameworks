/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public interface EntityResolver {
    public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException;
}

