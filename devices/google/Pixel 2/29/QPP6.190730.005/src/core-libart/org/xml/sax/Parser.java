/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import java.io.IOException;
import java.util.Locale;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Deprecated
public interface Parser {
    public void parse(String var1) throws SAXException, IOException;

    public void parse(InputSource var1) throws SAXException, IOException;

    public void setDTDHandler(DTDHandler var1);

    public void setDocumentHandler(DocumentHandler var1);

    public void setEntityResolver(EntityResolver var1);

    public void setErrorHandler(ErrorHandler var1);

    public void setLocale(Locale var1) throws SAXException;
}

