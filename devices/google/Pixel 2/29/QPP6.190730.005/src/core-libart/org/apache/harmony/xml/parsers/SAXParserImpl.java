/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.parsers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import org.apache.harmony.xml.ExpatReader;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;

final class SAXParserImpl
extends SAXParser {
    private Map<String, Boolean> initialFeatures;
    private Parser parser;
    private XMLReader reader;

    SAXParserImpl(Map<String, Boolean> map) throws SAXNotRecognizedException, SAXNotSupportedException {
        map = map.isEmpty() ? Collections.emptyMap() : new HashMap<String, Boolean>(map);
        this.initialFeatures = map;
        this.resetInternal();
    }

    private void resetInternal() throws SAXNotSupportedException, SAXNotRecognizedException {
        this.reader = new ExpatReader();
        for (Map.Entry<String, Boolean> entry : this.initialFeatures.entrySet()) {
            this.reader.setFeature(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Parser getParser() {
        if (this.parser == null) {
            this.parser = new XMLReaderAdapter(this.reader);
        }
        return this.parser;
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.reader.getProperty(string);
    }

    @Override
    public XMLReader getXMLReader() {
        return this.reader;
    }

    @Override
    public boolean isNamespaceAware() {
        try {
            boolean bl = this.reader.getFeature("http://xml.org/sax/features/namespaces");
            return bl;
        }
        catch (SAXException sAXException) {
            return false;
        }
    }

    @Override
    public boolean isValidating() {
        return false;
    }

    @Override
    public void reset() {
        try {
            this.resetInternal();
            return;
        }
        catch (SAXNotSupportedException sAXNotSupportedException) {
            throw new AssertionError();
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new AssertionError();
        }
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.reader.setProperty(string, object);
    }
}

