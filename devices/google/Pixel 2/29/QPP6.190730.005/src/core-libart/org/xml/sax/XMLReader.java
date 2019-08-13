/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public interface XMLReader {
    public ContentHandler getContentHandler();

    public DTDHandler getDTDHandler();

    public EntityResolver getEntityResolver();

    public ErrorHandler getErrorHandler();

    public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException;

    public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException;

    public void parse(String var1) throws IOException, SAXException;

    public void parse(InputSource var1) throws IOException, SAXException;

    public void setContentHandler(ContentHandler var1);

    public void setDTDHandler(DTDHandler var1);

    public void setEntityResolver(EntityResolver var1);

    public void setErrorHandler(ErrorHandler var1);

    public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException;

    public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException;
}

