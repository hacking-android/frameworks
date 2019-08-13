/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup.jaxp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class SAXFactoryImpl
extends SAXParserFactory {
    private HashMap features = null;
    private SAXParserImpl prototypeParser = null;

    private SAXParserImpl getPrototype() {
        if (this.prototypeParser == null) {
            this.prototypeParser = new SAXParserImpl();
        }
        return this.prototypeParser;
    }

    @Override
    public boolean getFeature(String string) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return this.getPrototype().getFeature(string);
    }

    @Override
    public SAXParser newSAXParser() throws ParserConfigurationException {
        try {
            SAXParserImpl sAXParserImpl = SAXParserImpl.newInstance(this.features);
            return sAXParserImpl;
        }
        catch (SAXException sAXException) {
            throw new ParserConfigurationException(sAXException.getMessage());
        }
    }

    @Override
    public void setFeature(String string, boolean bl) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        this.getPrototype().setFeature(string, bl);
        if (this.features == null) {
            this.features = new LinkedHashMap();
        }
        HashMap hashMap = this.features;
        Boolean bl2 = bl ? Boolean.TRUE : Boolean.FALSE;
        hashMap.put(string, bl2);
    }
}

