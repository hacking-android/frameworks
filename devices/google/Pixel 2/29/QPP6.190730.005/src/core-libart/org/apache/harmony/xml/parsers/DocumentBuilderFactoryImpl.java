/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.parsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.harmony.xml.parsers.DocumentBuilderImpl;

public class DocumentBuilderFactoryImpl
extends DocumentBuilderFactory {
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String VALIDATION = "http://xml.org/sax/features/validation";

    @Override
    public Object getAttribute(String string) throws IllegalArgumentException {
        throw new IllegalArgumentException(string);
    }

    @Override
    public boolean getFeature(String string) throws ParserConfigurationException {
        if (string != null) {
            if (NAMESPACES.equals(string)) {
                return this.isNamespaceAware();
            }
            if (VALIDATION.equals(string)) {
                return this.isValidating();
            }
            throw new ParserConfigurationException(string);
        }
        throw new NullPointerException("name == null");
    }

    @Override
    public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        if (!this.isValidating()) {
            DocumentBuilderImpl documentBuilderImpl = new DocumentBuilderImpl();
            documentBuilderImpl.setCoalescing(this.isCoalescing());
            documentBuilderImpl.setIgnoreComments(this.isIgnoringComments());
            documentBuilderImpl.setIgnoreElementContentWhitespace(this.isIgnoringElementContentWhitespace());
            documentBuilderImpl.setNamespaceAware(this.isNamespaceAware());
            return documentBuilderImpl;
        }
        throw new ParserConfigurationException("No validating DocumentBuilder implementation available");
    }

    @Override
    public void setAttribute(String string, Object object) throws IllegalArgumentException {
        throw new IllegalArgumentException(string);
    }

    @Override
    public void setFeature(String string, boolean bl) throws ParserConfigurationException {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (string == null) break block2;
                        if (!NAMESPACES.equals(string)) break block3;
                        this.setNamespaceAware(bl);
                        break block4;
                    }
                    if (!VALIDATION.equals(string)) break block5;
                    this.setValidating(bl);
                }
                return;
            }
            throw new ParserConfigurationException(string);
        }
        throw new NullPointerException("name == null");
    }
}

