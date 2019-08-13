/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import libcore.io.IoUtils;
import org.apache.harmony.xml.ExpatParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

public class ExpatReader
implements XMLReader {
    private static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
    @UnsupportedAppUsage
    ContentHandler contentHandler;
    DTDHandler dtdHandler;
    EntityResolver entityResolver;
    ErrorHandler errorHandler;
    LexicalHandler lexicalHandler;
    private boolean processNamespacePrefixes = false;
    private boolean processNamespaces = true;

    private void parse(InputStream inputStream, String string, String string2, String string3) throws IOException, SAXException {
        new ExpatParser(string, this, this.processNamespaces, string2, string3).parseDocument(inputStream);
    }

    private void parse(Reader reader, String string, String string2) throws IOException, SAXException {
        new ExpatParser("UTF-16", this, this.processNamespaces, string, string2).parseDocument(reader);
    }

    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override
    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string != null) {
            if (!(string.equals("http://xml.org/sax/features/validation") || string.equals("http://xml.org/sax/features/external-general-entities") || string.equals("http://xml.org/sax/features/external-parameter-entities"))) {
                if (string.equals("http://xml.org/sax/features/namespaces")) {
                    return this.processNamespaces;
                }
                if (string.equals("http://xml.org/sax/features/namespace-prefixes")) {
                    return this.processNamespacePrefixes;
                }
                if (string.equals("http://xml.org/sax/features/string-interning")) {
                    return true;
                }
                throw new SAXNotRecognizedException(string);
            }
            return false;
        }
        throw new NullPointerException("name == null");
    }

    public LexicalHandler getLexicalHandler() {
        return this.lexicalHandler;
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string != null) {
            if (string.equals(LEXICAL_HANDLER_PROPERTY)) {
                return this.lexicalHandler;
            }
            throw new SAXNotRecognizedException(string);
        }
        throw new NullPointerException("name == null");
    }

    public boolean isNamespaceProcessingEnabled() {
        return this.processNamespaces;
    }

    @Override
    public void parse(String string) throws IOException, SAXException {
        this.parse(new InputSource(string));
    }

    @Override
    public void parse(InputSource inputSource) throws IOException, SAXException {
        if (this.processNamespacePrefixes && this.processNamespaces) {
            throw new SAXNotSupportedException("The 'namespace-prefix' feature is not supported while the 'namespaces' feature is enabled.");
        }
        Object object = inputSource.getCharacterStream();
        if (object != null) {
            try {
                this.parse((Reader)object, inputSource.getPublicId(), inputSource.getSystemId());
                return;
            }
            finally {
                IoUtils.closeQuietly((AutoCloseable)object);
            }
        }
        InputStream inputStream = inputSource.getByteStream();
        object = inputSource.getEncoding();
        if (inputStream != null) {
            try {
                this.parse(inputStream, (String)object, inputSource.getPublicId(), inputSource.getSystemId());
                return;
            }
            finally {
                IoUtils.closeQuietly(inputStream);
            }
        }
        String string = inputSource.getSystemId();
        if (string != null) {
            inputStream = ExpatParser.openUrl(string);
            try {
                this.parse(inputStream, (String)object, inputSource.getPublicId(), string);
                return;
            }
            finally {
                IoUtils.closeQuietly(inputStream);
            }
        }
        throw new SAXException("No input specified.");
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.dtdHandler = dTDHandler;
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string != null) {
            if (!(string.equals("http://xml.org/sax/features/validation") || string.equals("http://xml.org/sax/features/external-general-entities") || string.equals("http://xml.org/sax/features/external-parameter-entities"))) {
                if (string.equals("http://xml.org/sax/features/namespaces")) {
                    this.processNamespaces = bl;
                    return;
                }
                if (string.equals("http://xml.org/sax/features/namespace-prefixes")) {
                    this.processNamespacePrefixes = bl;
                    return;
                }
                if (string.equals("http://xml.org/sax/features/string-interning")) {
                    if (bl) {
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot disable ");
                    stringBuilder.append(string);
                    throw new SAXNotSupportedException(stringBuilder.toString());
                }
                throw new SAXNotRecognizedException(string);
            }
            if (!bl) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot enable ");
            stringBuilder.append(string);
            throw new SAXNotSupportedException(stringBuilder.toString());
        }
        throw new NullPointerException("name == null");
    }

    public void setLexicalHandler(LexicalHandler lexicalHandler) {
        this.lexicalHandler = lexicalHandler;
    }

    public void setNamespaceProcessingEnabled(boolean bl) {
        this.processNamespaces = bl;
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string != null) {
            if (string.equals(LEXICAL_HANDLER_PROPERTY)) {
                if (!(object instanceof LexicalHandler) && object != null) {
                    throw new SAXNotSupportedException("value doesn't implement org.xml.sax.ext.LexicalHandler");
                }
                this.lexicalHandler = (LexicalHandler)object;
                return;
            }
            throw new SAXNotRecognizedException(string);
        }
        throw new NullPointerException("name == null");
    }

    private static class Feature {
        private static final String BASE_URI = "http://xml.org/sax/features/";
        private static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
        private static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
        private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
        private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
        private static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
        private static final String VALIDATION = "http://xml.org/sax/features/validation";

        private Feature() {
        }
    }

}

