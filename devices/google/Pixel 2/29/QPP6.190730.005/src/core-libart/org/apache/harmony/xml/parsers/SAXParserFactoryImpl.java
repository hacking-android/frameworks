/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.parsers;

import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.harmony.xml.parsers.SAXParserImpl;
import org.xml.sax.SAXNotRecognizedException;

public class SAXParserFactoryImpl
extends SAXParserFactory {
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String VALIDATION = "http://xml.org/sax/features/validation";
    private Map<String, Boolean> features = new HashMap<String, Boolean>();

    @Override
    public boolean getFeature(String string) throws SAXNotRecognizedException {
        if (string != null) {
            if (string.startsWith("http://xml.org/sax/features/")) {
                return Boolean.TRUE.equals(this.features.get(string));
            }
            throw new SAXNotRecognizedException(string);
        }
        throw new NullPointerException("name == null");
    }

    @Override
    public boolean isNamespaceAware() {
        try {
            boolean bl = this.getFeature(NAMESPACES);
            return bl;
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new AssertionError(sAXNotRecognizedException);
        }
    }

    @Override
    public boolean isValidating() {
        try {
            boolean bl = this.getFeature(VALIDATION);
            return bl;
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new AssertionError(sAXNotRecognizedException);
        }
    }

    @Override
    public SAXParser newSAXParser() throws ParserConfigurationException {
        if (!this.isValidating()) {
            try {
                SAXParserImpl sAXParserImpl = new SAXParserImpl(this.features);
                return sAXParserImpl;
            }
            catch (Exception exception) {
                throw new ParserConfigurationException(exception.toString());
            }
        }
        throw new ParserConfigurationException("No validating SAXParser implementation available");
    }

    @Override
    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException {
        if (string != null) {
            if (string.startsWith("http://xml.org/sax/features/")) {
                if (bl) {
                    this.features.put(string, Boolean.TRUE);
                } else {
                    this.features.put(string, Boolean.FALSE);
                }
                return;
            }
            throw new SAXNotRecognizedException(string);
        }
        throw new NullPointerException("name == null");
    }

    @Override
    public void setNamespaceAware(boolean bl) {
        try {
            this.setFeature(NAMESPACES, bl);
            return;
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new AssertionError(sAXNotRecognizedException);
        }
    }

    @Override
    public void setValidating(boolean bl) {
        try {
            this.setFeature(VALIDATION, bl);
            return;
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new AssertionError(sAXNotRecognizedException);
        }
    }
}

