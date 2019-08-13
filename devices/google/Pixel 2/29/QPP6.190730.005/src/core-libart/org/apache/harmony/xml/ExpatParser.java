/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.ReachabilitySensitive;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import libcore.io.IoUtils;
import org.apache.harmony.xml.ExpatAttributes;
import org.apache.harmony.xml.ExpatException;
import org.apache.harmony.xml.ExpatReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;

class ExpatParser {
    private static final int BUFFER_SIZE = 8096;
    static final String CHARACTER_ENCODING = "UTF-16";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String OUTSIDE_START_ELEMENT = "Attributes can only be used within the scope of startElement().";
    private static final int TIMEOUT = 20000;
    private int attributeCount = -1;
    private long attributePointer = 0L;
    @UnsupportedAppUsage
    private final ExpatAttributes attributes = new CurrentAttributes();
    private final String encoding;
    private boolean inStartElement = false;
    private final Locator locator = new ExpatLocator();
    @ReachabilitySensitive
    private long pointer;
    private final String publicId;
    private final String systemId;
    @UnsupportedAppUsage
    private final ExpatReader xmlReader;

    static {
        ExpatParser.staticInitialize("");
    }

    private ExpatParser(String string, ExpatReader expatReader, long l, String string2, String string3) {
        this.encoding = string;
        this.xmlReader = expatReader;
        this.pointer = l;
        this.systemId = string3;
        this.publicId = string2;
    }

    @UnsupportedAppUsage
    ExpatParser(String string, ExpatReader expatReader, boolean bl, String string2, String string3) {
        this.publicId = string2;
        this.systemId = string3;
        this.xmlReader = expatReader;
        if (string == null) {
            string = DEFAULT_ENCODING;
        }
        this.encoding = string;
        this.pointer = this.initialize(this.encoding, bl);
    }

    private native void appendBytes(long var1, byte[] var3, int var4, int var5) throws SAXException, ExpatException;

    private native void appendChars(long var1, char[] var3, int var4, int var5) throws SAXException, ExpatException;

    private native void appendString(long var1, String var3, boolean var4) throws SAXException, ExpatException;

    private static native long cloneAttributes(long var0, int var2);

    private int column() {
        return ExpatParser.column(this.pointer);
    }

    private static native int column(long var0);

    private static native long createEntityParser(long var0, String var2);

    private void endDocument() throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }

    private native long initialize(String var1, boolean var2);

    private int line() {
        return ExpatParser.line(this.pointer);
    }

    private static native int line(long var0);

    static InputStream openUrl(String object) throws IOException {
        try {
            Object object2 = new URL((String)object);
            object2 = ((URL)object2).openConnection();
            ((URLConnection)object2).setConnectTimeout(20000);
            ((URLConnection)object2).setReadTimeout(20000);
            ((URLConnection)object2).setDoInput(true);
            ((URLConnection)object2).setDoOutput(false);
            object2 = ((URLConnection)object2).getInputStream();
            return object2;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't open ");
            stringBuilder.append((String)object);
            object = new IOException(stringBuilder.toString());
            ((Throwable)object).initCause(exception);
            throw object;
        }
    }

    private void parseExternalEntity(ExpatParser expatParser, InputSource object) throws IOException, SAXException {
        Closeable closeable = ((InputSource)object).getCharacterStream();
        if (closeable != null) {
            try {
                expatParser.append("<externalEntity>");
                expatParser.parseFragment((Reader)closeable);
                expatParser.append("</externalEntity>");
                return;
            }
            finally {
                IoUtils.closeQuietly(closeable);
            }
        }
        closeable = ((InputSource)object).getByteStream();
        if (closeable != null) {
            try {
                expatParser.append("<externalEntity>".getBytes(expatParser.encoding));
                expatParser.parseFragment((InputStream)closeable);
                expatParser.append("</externalEntity>".getBytes(expatParser.encoding));
                return;
            }
            finally {
                IoUtils.closeQuietly(closeable);
            }
        }
        if ((object = ((InputSource)object).getSystemId()) != null) {
            object = ExpatParser.openUrl((String)object);
            try {
                expatParser.append("<externalEntity>".getBytes(expatParser.encoding));
                expatParser.parseFragment((InputStream)object);
                expatParser.append("</externalEntity>".getBytes(expatParser.encoding));
                return;
            }
            finally {
                IoUtils.closeQuietly((AutoCloseable)object);
            }
        }
        throw new ParseException("No input specified.", this.locator);
    }

    private void parseFragment(InputStream inputStream) throws IOException, SAXException {
        int n;
        byte[] arrby = new byte[8096];
        while ((n = inputStream.read(arrby)) != -1) {
            try {
                this.appendBytes(this.pointer, arrby, 0, n);
            }
            catch (ExpatException expatException) {
                throw new ParseException(expatException.getMessage(), this.locator);
            }
        }
    }

    private void parseFragment(Reader reader) throws IOException, SAXException {
        int n;
        char[] arrc = new char[4048];
        while ((n = reader.read(arrc)) != -1) {
            try {
                this.appendChars(this.pointer, arrc, 0, n);
            }
            catch (ExpatException expatException) {
                throw new ParseException(expatException.getMessage(), this.locator);
            }
        }
    }

    private String pickEncoding(InputSource object) {
        block1 : {
            if (((InputSource)object).getCharacterStream() != null) {
                return "UTF-16";
            }
            if ((object = ((InputSource)object).getEncoding()) != null) break block1;
            object = "UTF-8";
        }
        return object;
    }

    private native void release(long var1);

    private static native void releaseParser(long var0);

    private void startDocument() throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(this.locator);
            contentHandler.startDocument();
        }
    }

    private static native void staticInitialize(String var0);

    void append(String string) throws SAXException {
        try {
            this.appendString(this.pointer, string, false);
            return;
        }
        catch (ExpatException expatException) {
            throw new ParseException(expatException.getMessage(), this.locator);
        }
    }

    void append(byte[] arrby) throws SAXException {
        this.append(arrby, 0, arrby.length);
    }

    @UnsupportedAppUsage
    void append(byte[] arrby, int n, int n2) throws SAXException {
        try {
            this.appendBytes(this.pointer, arrby, n, n2);
            return;
        }
        catch (ExpatException expatException) {
            throw new ParseException(expatException.getMessage(), this.locator);
        }
    }

    @UnsupportedAppUsage
    void append(char[] arrc, int n, int n2) throws SAXException {
        try {
            this.appendChars(this.pointer, arrc, n, n2);
            return;
        }
        catch (ExpatException expatException) {
            throw new ParseException(expatException.getMessage(), this.locator);
        }
    }

    @UnsupportedAppUsage
    Attributes cloneAttributes() {
        if (this.inStartElement) {
            int n = this.attributeCount;
            if (n == 0) {
                return ClonedAttributes.EMPTY;
            }
            long l = ExpatParser.cloneAttributes(this.attributePointer, n);
            return new ClonedAttributes(this.pointer, l, this.attributeCount);
        }
        throw new IllegalStateException("Attributes can only be used within the scope of startElement().");
    }

    void comment(char[] arrc, int n) throws SAXException {
        LexicalHandler lexicalHandler = this.xmlReader.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.comment(arrc, 0, n);
        }
    }

    void endCdata() throws SAXException {
        LexicalHandler lexicalHandler = this.xmlReader.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endCDATA();
        }
    }

    void endDtd() throws SAXException {
        LexicalHandler lexicalHandler = this.xmlReader.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endDTD();
        }
    }

    void endElement(String string, String string2, String string3) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.endElement(string, string2, string3);
        }
    }

    void endNamespace(String string) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.endPrefixMapping(string);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void finalize() throws Throwable {
        void var3_4;
        block7 : {
            block6 : {
                // MONITORENTER : this
                long l = this.pointer;
                if (l == 0L) break block6;
                try {
                    this.release(this.pointer);
                    this.pointer = 0L;
                }
                catch (Throwable throwable) {
                    break block7;
                }
            }
            super.finalize();
            // MONITOREXIT : this
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        super.finalize();
        throw var3_4;
    }

    @UnsupportedAppUsage
    void finish() throws SAXException {
        try {
            this.appendString(this.pointer, "", true);
            return;
        }
        catch (ExpatException expatException) {
            throw new ParseException(expatException.getMessage(), this.locator);
        }
    }

    void handleExternalEntity(String object, String object2, String string) throws SAXException, IOException {
        EntityResolver entityResolver;
        Object object3;
        block11 : {
            entityResolver = this.xmlReader.entityResolver;
            if (entityResolver == null) {
                return;
            }
            object3 = string;
            if (this.systemId != null) {
                Serializable serializable = new URI(string);
                object3 = string;
                if (((URI)serializable).isAbsolute()) break block11;
                object3 = string;
                try {
                    if (!((URI)serializable).isOpaque()) {
                        object3 = new URI(this.systemId);
                        object3 = ((URI)object3).resolve((URI)serializable).toString();
                    }
                }
                catch (Exception exception) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Could not resolve '");
                    ((StringBuilder)serializable).append(string);
                    ((StringBuilder)serializable).append("' relative to '");
                    ((StringBuilder)serializable).append(this.systemId);
                    ((StringBuilder)serializable).append("' at ");
                    ((StringBuilder)serializable).append(this.locator);
                    System.logI((String)((StringBuilder)serializable).toString(), (Throwable)exception);
                    object3 = string;
                }
            }
        }
        if ((object2 = entityResolver.resolveEntity((String)object2, (String)object3)) == null) {
            return;
        }
        string = this.pickEncoding((InputSource)object2);
        long l = ExpatParser.createEntityParser(this.pointer, (String)object);
        try {
            object = new EntityParser(string, this.xmlReader, l, ((InputSource)object2).getPublicId(), ((InputSource)object2).getSystemId());
            this.parseExternalEntity((ExpatParser)object, (InputSource)object2);
            return;
        }
        finally {
            ExpatParser.releaseParser(l);
        }
    }

    void notationDecl(String string, String string2, String string3) throws SAXException {
        DTDHandler dTDHandler = this.xmlReader.dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.notationDecl(string, string2, string3);
        }
    }

    void parseDocument(InputStream inputStream) throws IOException, SAXException {
        this.startDocument();
        this.parseFragment(inputStream);
        this.finish();
        this.endDocument();
    }

    void parseDocument(Reader reader) throws IOException, SAXException {
        this.startDocument();
        this.parseFragment(reader);
        this.finish();
        this.endDocument();
    }

    void processingInstruction(String string, String string2) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.processingInstruction(string, string2);
        }
    }

    void startCdata() throws SAXException {
        LexicalHandler lexicalHandler = this.xmlReader.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
        }
    }

    void startDtd(String string, String string2, String string3) throws SAXException {
        LexicalHandler lexicalHandler = this.xmlReader.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startDTD(string, string2, string3);
        }
    }

    void startElement(String string, String string2, String string3, long l, int n) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler == null) {
            return;
        }
        try {
            this.inStartElement = true;
            this.attributePointer = l;
            this.attributeCount = n;
            contentHandler.startElement(string, string2, string3, this.attributes);
            return;
        }
        finally {
            this.inStartElement = false;
            this.attributeCount = -1;
            this.attributePointer = 0L;
        }
    }

    void startNamespace(String string, String string2) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.startPrefixMapping(string, string2);
        }
    }

    void text(char[] arrc, int n) throws SAXException {
        ContentHandler contentHandler = this.xmlReader.contentHandler;
        if (contentHandler != null) {
            contentHandler.characters(arrc, 0, n);
        }
    }

    void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        DTDHandler dTDHandler = this.xmlReader.dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.unparsedEntityDecl(string, string2, string3, string4);
        }
    }

    private static class ClonedAttributes
    extends ExpatAttributes {
        private static final Attributes EMPTY = new ClonedAttributes(0L, 0L, 0);
        private final int length;
        private final long parserPointer;
        private long pointer;

        private ClonedAttributes(long l, long l2, int n) {
            this.parserPointer = l;
            this.pointer = l2;
            this.length = n;
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        protected void finalize() throws Throwable {
            void var3_4;
            block7 : {
                block6 : {
                    // MONITORENTER : this
                    long l = this.pointer;
                    if (l == 0L) break block6;
                    try {
                        this.freeAttributes(this.pointer);
                        this.pointer = 0L;
                    }
                    catch (Throwable throwable) {
                        break block7;
                    }
                }
                Object.super.finalize();
                // MONITOREXIT : this
                return;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            Object.super.finalize();
            throw var3_4;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public long getParserPointer() {
            return this.parserPointer;
        }

        @Override
        public long getPointer() {
            return this.pointer;
        }
    }

    private class CurrentAttributes
    extends ExpatAttributes {
        private CurrentAttributes() {
        }

        @Override
        public int getLength() {
            if (ExpatParser.this.inStartElement) {
                return ExpatParser.this.attributeCount;
            }
            throw new IllegalStateException(ExpatParser.OUTSIDE_START_ELEMENT);
        }

        @Override
        public long getParserPointer() {
            return ExpatParser.this.pointer;
        }

        @Override
        public long getPointer() {
            if (ExpatParser.this.inStartElement) {
                return ExpatParser.this.attributePointer;
            }
            throw new IllegalStateException(ExpatParser.OUTSIDE_START_ELEMENT);
        }
    }

    private static class EntityParser
    extends ExpatParser {
        @UnsupportedAppUsage
        private int depth = 0;

        private EntityParser(String string, ExpatReader expatReader, long l, String string2, String string3) {
            super(string, expatReader, l, string2, string3);
        }

        @Override
        void endElement(String string, String string2, String string3) throws SAXException {
            int n;
            this.depth = n = this.depth - 1;
            if (n > 0) {
                super.endElement(string, string2, string3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected void finalize() throws Throwable {
            // MONITORENTER : this
            // MONITOREXIT : this
        }

        @Override
        void startElement(String string, String string2, String string3, long l, int n) throws SAXException {
            int n2 = this.depth;
            this.depth = n2 + 1;
            if (n2 > 0) {
                super.startElement(string, string2, string3, l, n);
            }
        }
    }

    private class ExpatLocator
    implements Locator {
        private ExpatLocator() {
        }

        @Override
        public int getColumnNumber() {
            return ExpatParser.this.column();
        }

        @Override
        public int getLineNumber() {
            return ExpatParser.this.line();
        }

        @Override
        public String getPublicId() {
            return ExpatParser.this.publicId;
        }

        @Override
        public String getSystemId() {
            return ExpatParser.this.systemId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Locator[publicId: ");
            stringBuilder.append(ExpatParser.this.publicId);
            stringBuilder.append(", systemId: ");
            stringBuilder.append(ExpatParser.this.systemId);
            stringBuilder.append(", line: ");
            stringBuilder.append(this.getLineNumber());
            stringBuilder.append(", column: ");
            stringBuilder.append(this.getColumnNumber());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class ParseException
    extends SAXParseException {
        private ParseException(String string, Locator locator) {
            super(ParseException.makeMessage(string, locator), locator);
        }

        private static String makeMessage(String string, int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("At line ");
            stringBuilder.append(n);
            stringBuilder.append(", column ");
            stringBuilder.append(n2);
            stringBuilder.append(": ");
            stringBuilder.append(string);
            return stringBuilder.toString();
        }

        private static String makeMessage(String string, Locator locator) {
            return ParseException.makeMessage(string, locator.getLineNumber(), locator.getColumnNumber());
        }
    }

}

