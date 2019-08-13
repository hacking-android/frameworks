/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup.jaxp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.jaxp.SAX1ParserAdapter;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class SAXParserImpl
extends SAXParser {
    final Parser parser = new Parser();

    protected SAXParserImpl() {
    }

    public static SAXParserImpl newInstance(Map object) throws SAXException {
        SAXParserImpl sAXParserImpl = new SAXParserImpl();
        if (object != null) {
            for (Map.Entry entry : object.entrySet()) {
                sAXParserImpl.setFeature((String)entry.getKey(), (Boolean)entry.getValue());
            }
        }
        return sAXParserImpl;
    }

    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.parser.getFeature(string);
    }

    @Override
    public org.xml.sax.Parser getParser() throws SAXException {
        return new SAX1ParserAdapter(this.parser);
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.parser.getProperty(string);
    }

    @Override
    public XMLReader getXMLReader() {
        return this.parser;
    }

    @Override
    public boolean isNamespaceAware() {
        try {
            boolean bl = this.parser.getFeature("http://xml.org/sax/features/namespaces");
            return bl;
        }
        catch (SAXException sAXException) {
            throw new RuntimeException(sAXException.getMessage());
        }
    }

    @Override
    public boolean isValidating() {
        try {
            boolean bl = this.parser.getFeature("http://xml.org/sax/features/validation");
            return bl;
        }
        catch (SAXException sAXException) {
            throw new RuntimeException(sAXException.getMessage());
        }
    }

    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.parser.setFeature(string, bl);
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.parser.setProperty(string, object);
    }
}

