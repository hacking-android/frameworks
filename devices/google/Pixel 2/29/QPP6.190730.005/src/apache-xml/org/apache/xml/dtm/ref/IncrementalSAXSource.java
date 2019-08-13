/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public interface IncrementalSAXSource {
    public Object deliverMoreNodes(boolean var1);

    public void setContentHandler(ContentHandler var1);

    public void setDTDHandler(DTDHandler var1);

    public void setLexicalHandler(LexicalHandler var1);

    public void startParse(InputSource var1) throws SAXException;
}

