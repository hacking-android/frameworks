/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.FilePathToURI;
import javax.xml.validation.Schema;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SAXParser {
    private static final boolean DEBUG = false;

    protected SAXParser() {
    }

    public abstract Parser getParser() throws SAXException;

    public abstract Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException;

    public Schema getSchema() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract XMLReader getXMLReader() throws SAXException;

    public abstract boolean isNamespaceAware();

    public abstract boolean isValidating();

    public boolean isXIncludeAware() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public void parse(File file, HandlerBase handlerBase) throws SAXException, IOException {
        if (file != null) {
            this.parse(new InputSource(FilePathToURI.filepath2URI(file.getAbsolutePath())), handlerBase);
            return;
        }
        throw new IllegalArgumentException("File cannot be null");
    }

    public void parse(File file, DefaultHandler defaultHandler) throws SAXException, IOException {
        if (file != null) {
            this.parse(new InputSource(FilePathToURI.filepath2URI(file.getAbsolutePath())), defaultHandler);
            return;
        }
        throw new IllegalArgumentException("File cannot be null");
    }

    public void parse(InputStream inputStream, HandlerBase handlerBase) throws SAXException, IOException {
        if (inputStream != null) {
            this.parse(new InputSource(inputStream), handlerBase);
            return;
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public void parse(InputStream object, HandlerBase handlerBase, String string) throws SAXException, IOException {
        if (object != null) {
            object = new InputSource((InputStream)object);
            ((InputSource)object).setSystemId(string);
            this.parse((InputSource)object, handlerBase);
            return;
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public void parse(InputStream inputStream, DefaultHandler defaultHandler) throws SAXException, IOException {
        if (inputStream != null) {
            this.parse(new InputSource(inputStream), defaultHandler);
            return;
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public void parse(InputStream object, DefaultHandler defaultHandler, String string) throws SAXException, IOException {
        if (object != null) {
            object = new InputSource((InputStream)object);
            ((InputSource)object).setSystemId(string);
            this.parse((InputSource)object, defaultHandler);
            return;
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public void parse(String string, HandlerBase handlerBase) throws SAXException, IOException {
        if (string != null) {
            this.parse(new InputSource(string), handlerBase);
            return;
        }
        throw new IllegalArgumentException("uri cannot be null");
    }

    public void parse(String string, DefaultHandler defaultHandler) throws SAXException, IOException {
        if (string != null) {
            this.parse(new InputSource(string), defaultHandler);
            return;
        }
        throw new IllegalArgumentException("uri cannot be null");
    }

    public void parse(InputSource inputSource, HandlerBase handlerBase) throws SAXException, IOException {
        if (inputSource != null) {
            Parser parser = this.getParser();
            if (handlerBase != null) {
                parser.setDocumentHandler(handlerBase);
                parser.setEntityResolver(handlerBase);
                parser.setErrorHandler(handlerBase);
                parser.setDTDHandler(handlerBase);
            }
            parser.parse(inputSource);
            return;
        }
        throw new IllegalArgumentException("InputSource cannot be null");
    }

    public void parse(InputSource inputSource, DefaultHandler defaultHandler) throws SAXException, IOException {
        if (inputSource != null) {
            XMLReader xMLReader = this.getXMLReader();
            if (defaultHandler != null) {
                xMLReader.setContentHandler(defaultHandler);
                xMLReader.setEntityResolver(defaultHandler);
                xMLReader.setErrorHandler(defaultHandler);
                xMLReader.setDTDHandler(defaultHandler);
            }
            xMLReader.parse(inputSource);
            return;
        }
        throw new IllegalArgumentException("InputSource cannot be null");
    }

    public void reset() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This SAXParser, \"");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("\", does not support the reset functionality.  Specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException;
}

