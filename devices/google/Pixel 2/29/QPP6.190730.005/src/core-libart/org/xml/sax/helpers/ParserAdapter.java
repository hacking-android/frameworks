/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.ParserFactory;

public class ParserAdapter
implements XMLReader,
DocumentHandler {
    private static final String FEATURES = "http://xml.org/sax/features/";
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    private static final String XMLNS_URIs = "http://xml.org/sax/features/xmlns-uris";
    @UnsupportedAppUsage
    private AttributeListAdapter attAdapter;
    @UnsupportedAppUsage
    private AttributesImpl atts = null;
    @UnsupportedAppUsage
    ContentHandler contentHandler = null;
    @UnsupportedAppUsage
    DTDHandler dtdHandler = null;
    @UnsupportedAppUsage
    EntityResolver entityResolver = null;
    @UnsupportedAppUsage
    ErrorHandler errorHandler = null;
    @UnsupportedAppUsage
    Locator locator;
    @UnsupportedAppUsage
    private String[] nameParts = new String[3];
    @UnsupportedAppUsage
    private boolean namespaces = true;
    @UnsupportedAppUsage
    private NamespaceSupport nsSupport;
    @UnsupportedAppUsage
    private Parser parser = null;
    @UnsupportedAppUsage
    private boolean parsing = false;
    @UnsupportedAppUsage
    private boolean prefixes = false;
    @UnsupportedAppUsage
    private boolean uris = false;

    public ParserAdapter() throws SAXException {
        String string = System.getProperty("org.xml.sax.parser");
        try {
            this.setup(ParserFactory.makeParser());
            return;
        }
        catch (NullPointerException nullPointerException) {
            throw new SAXException("System property org.xml.sax.parser not specified");
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX1 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" does not implement org.xml.sax.Parser");
            throw new SAXException(stringBuilder.toString());
        }
        catch (InstantiationException instantiationException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX1 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" loaded but cannot be instantiated");
            throw new SAXException(stringBuilder.toString(), instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX1 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" found but cannot be loaded");
            throw new SAXException(stringBuilder.toString(), illegalAccessException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot find SAX1 driver class ");
            stringBuilder.append(string);
            throw new SAXException(stringBuilder.toString(), classNotFoundException);
        }
    }

    public ParserAdapter(Parser parser) {
        this.setup(parser);
    }

    @UnsupportedAppUsage
    private void checkNotParsing(String string, String string2) throws SAXNotSupportedException {
        if (!this.parsing) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot change ");
        stringBuilder.append(string);
        stringBuilder.append(' ');
        stringBuilder.append(string2);
        stringBuilder.append(" while parsing");
        throw new SAXNotSupportedException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private SAXParseException makeException(String string) {
        Locator locator = this.locator;
        if (locator != null) {
            return new SAXParseException(string, locator);
        }
        return new SAXParseException(string, null, null, -1, -1);
    }

    @UnsupportedAppUsage
    private String[] processName(String string, boolean bl, boolean bl2) throws SAXException {
        String[] arrstring = this.nsSupport.processName(string, this.nameParts, bl);
        Object object = arrstring;
        if (arrstring == null) {
            if (!bl2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Undeclared prefix: ");
                ((StringBuilder)object).append(string);
                this.reportError(((StringBuilder)object).toString());
                object = new String[3];
                object[1] = "";
                object[0] = "";
                object[2] = string.intern();
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Undeclared prefix: ");
                ((StringBuilder)object).append(string);
                throw this.makeException(((StringBuilder)object).toString());
            }
        }
        return object;
    }

    @UnsupportedAppUsage
    private void setup(Parser parser) {
        if (parser != null) {
            this.parser = parser;
            this.atts = new AttributesImpl();
            this.nsSupport = new NamespaceSupport();
            this.attAdapter = new AttributeListAdapter();
            return;
        }
        throw new NullPointerException("Parser argument must not be null");
    }

    @UnsupportedAppUsage
    private void setupParser() {
        Object object;
        if (!this.prefixes && !this.namespaces) {
            throw new IllegalStateException();
        }
        this.nsSupport.reset();
        if (this.uris) {
            this.nsSupport.setNamespaceDeclUris(true);
        }
        if ((object = this.entityResolver) != null) {
            this.parser.setEntityResolver((EntityResolver)object);
        }
        if ((object = this.dtdHandler) != null) {
            this.parser.setDTDHandler((DTDHandler)object);
        }
        if ((object = this.errorHandler) != null) {
            this.parser.setErrorHandler((ErrorHandler)object);
        }
        this.parser.setDocumentHandler(this);
        this.locator = null;
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.characters(arrc, n, n2);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }

    @Override
    public void endElement(String object) throws SAXException {
        if (!this.namespaces) {
            ContentHandler contentHandler = this.contentHandler;
            if (contentHandler != null) {
                contentHandler.endElement("", "", object.intern());
            }
            return;
        }
        object = this.processName((String)object, false, false);
        Object object2 = this.contentHandler;
        if (object2 != null) {
            object2.endElement(object[0], object[1], object[2]);
            object2 = this.nsSupport.getDeclaredPrefixes();
            while (object2.hasMoreElements()) {
                object = (String)object2.nextElement();
                this.contentHandler.endPrefixMapping((String)object);
            }
        }
        this.nsSupport.popContext();
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
        if (string.equals(NAMESPACES)) {
            return this.namespaces;
        }
        if (string.equals(NAMESPACE_PREFIXES)) {
            return this.prefixes;
        }
        if (string.equals(XMLNS_URIs)) {
            return this.uris;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Feature: ");
        stringBuilder.append(string);
        throw new SAXNotRecognizedException(stringBuilder.toString());
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Property: ");
        stringBuilder.append(string);
        throw new SAXNotRecognizedException(stringBuilder.toString());
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.ignorableWhitespace(arrc, n, n2);
        }
    }

    @Override
    public void parse(String string) throws IOException, SAXException {
        this.parse(new InputSource(string));
    }

    @Override
    public void parse(InputSource inputSource) throws IOException, SAXException {
        if (!this.parsing) {
            this.setupParser();
            this.parsing = true;
            try {
                this.parser.parse(inputSource);
                this.parsing = false;
                return;
            }
            finally {
                this.parsing = false;
            }
        }
        throw new SAXException("Parser is already in use");
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.processingInstruction(string, string2);
        }
    }

    @UnsupportedAppUsage
    void reportError(String string) throws SAXException {
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            errorHandler.error(this.makeException(string));
        }
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
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
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
        block9 : {
            block7 : {
                block8 : {
                    block6 : {
                        if (!string.equals(NAMESPACES)) break block6;
                        this.checkNotParsing("feature", string);
                        this.namespaces = bl;
                        if (!this.namespaces && !this.prefixes) {
                            this.prefixes = true;
                        }
                        break block7;
                    }
                    if (!string.equals(NAMESPACE_PREFIXES)) break block8;
                    this.checkNotParsing("feature", string);
                    this.prefixes = bl;
                    if (!this.prefixes && !this.namespaces) {
                        this.namespaces = true;
                    }
                    break block7;
                }
                if (!string.equals(XMLNS_URIs)) break block9;
                this.checkNotParsing("feature", string);
                this.uris = bl;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Feature: ");
        stringBuilder.append(string);
        throw new SAXNotRecognizedException(stringBuilder.toString());
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        object = new StringBuilder();
        ((StringBuilder)object).append("Property: ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public void startDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.startDocument();
        }
    }

    @Override
    public void startElement(String arrstring, AttributeList object5) throws SAXException {
        block17 : {
            Object object;
            int n;
            Object object2;
            int n2;
            Object object3;
            if (!this.namespaces) {
                if (this.contentHandler != null) {
                    this.attAdapter.setAttributeList((AttributeList)object5);
                    this.contentHandler.startElement("", "", arrstring.intern(), this.attAdapter);
                }
                return;
            }
            this.nsSupport.pushContext();
            int n3 = object5.getLength();
            for (n2 = 0; n2 < n3; ++n2) {
                object = object5.getName(n2);
                if (!object.startsWith("xmlns")) continue;
                n = object.indexOf(58);
                if (n == -1 && object.length() == 5) {
                    object = "";
                } else {
                    if (n != 5) continue;
                    object = object.substring(n + 1);
                }
                object3 = object5.getValue(n2);
                if (!this.nsSupport.declarePrefix((String)object, (String)object3)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Illegal Namespace prefix: ");
                    ((StringBuilder)object2).append((String)object);
                    this.reportError(((StringBuilder)object2).toString());
                    continue;
                }
                object2 = this.contentHandler;
                if (object2 == null) continue;
                object2.startPrefixMapping((String)object, (String)object3);
            }
            this.atts.clear();
            object = null;
            for (n2 = 0; n2 < n3; ++n2) {
                String string = object5.getName(n2);
                String string2 = object5.getType(n2);
                String string3 = object5.getValue(n2);
                if (string.startsWith("xmlns") && (object2 = (n = string.indexOf(58)) == -1 && string.length() == 5 ? "" : (n != 5 ? null : string.substring(6))) != null) {
                    object3 = object;
                    if (this.prefixes) {
                        if (this.uris) {
                            AttributesImpl attributesImpl = this.atts;
                            object3 = this.nsSupport;
                            attributesImpl.addAttribute("http://www.w3.org/XML/1998/namespace", (String)object2, string.intern(), string2, string3);
                            object3 = object;
                        } else {
                            this.atts.addAttribute("", "", string.intern(), string2, string3);
                            object3 = object;
                        }
                    }
                } else {
                    try {
                        object2 = this.processName(string, true, true);
                        this.atts.addAttribute(object2[0], object2[1], (String)object2[2], string2, string3);
                        object3 = object;
                    }
                    catch (SAXException sAXException) {
                        object2 = object;
                        if (object == null) {
                            object2 = new ArrayList();
                        }
                        ((ArrayList)object2).add((SAXParseException)sAXException);
                        this.atts.addAttribute("", string, string, string2, string3);
                        object3 = object2;
                    }
                }
                object = object3;
            }
            if (object != null && this.errorHandler != null) {
                for (SAXParseException sAXParseException : object) {
                    this.errorHandler.error(sAXParseException);
                }
            }
            if (this.contentHandler == null) break block17;
            arrstring = this.processName((String)arrstring, false, false);
            this.contentHandler.startElement(arrstring[0], arrstring[1], arrstring[2], this.atts);
        }
    }

    final class AttributeListAdapter
    implements Attributes {
        private AttributeList qAtts;

        @UnsupportedAppUsage
        AttributeListAdapter() {
        }

        @Override
        public int getIndex(String string) {
            int n = ParserAdapter.this.atts.getLength();
            for (int i = 0; i < n; ++i) {
                if (!this.qAtts.getName(i).equals(string)) continue;
                return i;
            }
            return -1;
        }

        @Override
        public int getIndex(String string, String string2) {
            return -1;
        }

        @Override
        public int getLength() {
            return this.qAtts.getLength();
        }

        @Override
        public String getLocalName(int n) {
            return "";
        }

        @Override
        public String getQName(int n) {
            return this.qAtts.getName(n).intern();
        }

        @Override
        public String getType(int n) {
            return this.qAtts.getType(n).intern();
        }

        @Override
        public String getType(String string) {
            return this.qAtts.getType(string).intern();
        }

        @Override
        public String getType(String string, String string2) {
            return null;
        }

        @Override
        public String getURI(int n) {
            return "";
        }

        @Override
        public String getValue(int n) {
            return this.qAtts.getValue(n);
        }

        @Override
        public String getValue(String string) {
            return this.qAtts.getValue(string);
        }

        @Override
        public String getValue(String string, String string2) {
            return null;
        }

        void setAttributeList(AttributeList attributeList) {
            this.qAtts = attributeList;
        }
    }

}

