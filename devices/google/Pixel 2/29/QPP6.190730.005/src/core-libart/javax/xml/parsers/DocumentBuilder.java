/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.FilePathToURI;
import javax.xml.validation.Schema;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class DocumentBuilder {
    private static final boolean DEBUG = false;

    protected DocumentBuilder() {
    }

    public abstract DOMImplementation getDOMImplementation();

    public Schema getSchema() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

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

    public abstract Document newDocument();

    public Document parse(File file) throws SAXException, IOException {
        if (file != null) {
            return this.parse(new InputSource(FilePathToURI.filepath2URI(file.getAbsolutePath())));
        }
        throw new IllegalArgumentException("File cannot be null");
    }

    public Document parse(InputStream inputStream) throws SAXException, IOException {
        if (inputStream != null) {
            return this.parse(new InputSource(inputStream));
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public Document parse(InputStream object, String string) throws SAXException, IOException {
        if (object != null) {
            object = new InputSource((InputStream)object);
            ((InputSource)object).setSystemId(string);
            return this.parse((InputSource)object);
        }
        throw new IllegalArgumentException("InputStream cannot be null");
    }

    public Document parse(String string) throws SAXException, IOException {
        if (string != null) {
            return this.parse(new InputSource(string));
        }
        throw new IllegalArgumentException("URI cannot be null");
    }

    public abstract Document parse(InputSource var1) throws SAXException, IOException;

    public void reset() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This DocumentBuilder, \"");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("\", does not support the reset functionality.  Specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract void setEntityResolver(EntityResolver var1);

    public abstract void setErrorHandler(ErrorHandler var1);
}

