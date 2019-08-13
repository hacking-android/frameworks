/*
 * Decompiled with CFR 0.145.
 */
package org.xmlpull.v1.sax2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class Driver
implements Locator,
XMLReader,
Attributes {
    protected static final String APACHE_DYNAMIC_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/dynamic";
    protected static final String APACHE_SCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
    protected static final String DECLARATION_HANDLER_PROPERTY = "http://xml.org/sax/properties/declaration-handler";
    protected static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
    protected static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    protected static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
    protected static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    protected ContentHandler contentHandler = new DefaultHandler();
    protected ErrorHandler errorHandler = new DefaultHandler();
    protected XmlPullParser pp;
    protected String systemId;

    public Driver() throws XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(true);
        this.pp = xmlPullParserFactory.newPullParser();
    }

    public Driver(XmlPullParser xmlPullParser) throws XmlPullParserException {
        this.pp = xmlPullParser;
    }

    @Override
    public int getColumnNumber() {
        return this.pp.getColumnNumber();
    }

    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override
    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (NAMESPACES_FEATURE.equals(string)) {
            return this.pp.getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces");
        }
        if (NAMESPACE_PREFIXES_FEATURE.equals(string)) {
            return this.pp.getFeature("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes");
        }
        if (VALIDATION_FEATURE.equals(string)) {
            return this.pp.getFeature("http://xmlpull.org/v1/doc/features.html#validation");
        }
        return this.pp.getFeature(string);
    }

    @Override
    public int getIndex(String string) {
        for (int i = 0; i < this.pp.getAttributeCount(); ++i) {
            if (!this.pp.getAttributeName(i).equals(string)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getIndex(String string, String string2) {
        for (int i = 0; i < this.pp.getAttributeCount(); ++i) {
            if (!this.pp.getAttributeNamespace(i).equals(string) || !this.pp.getAttributeName(i).equals(string2)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getLength() {
        return this.pp.getAttributeCount();
    }

    @Override
    public int getLineNumber() {
        return this.pp.getLineNumber();
    }

    @Override
    public String getLocalName(int n) {
        return this.pp.getAttributeName(n);
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (DECLARATION_HANDLER_PROPERTY.equals(string)) {
            return null;
        }
        if (LEXICAL_HANDLER_PROPERTY.equals(string)) {
            return null;
        }
        return this.pp.getProperty(string);
    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public String getQName(int n) {
        String string = this.pp.getAttributePrefix(n);
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(':');
            stringBuilder.append(this.pp.getAttributeName(n));
            return stringBuilder.toString();
        }
        return this.pp.getAttributeName(n);
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    @Override
    public String getType(int n) {
        return this.pp.getAttributeType(n);
    }

    @Override
    public String getType(String string) {
        for (int i = 0; i < this.pp.getAttributeCount(); ++i) {
            if (!this.pp.getAttributeName(i).equals(string)) continue;
            return this.pp.getAttributeType(i);
        }
        return null;
    }

    @Override
    public String getType(String string, String string2) {
        for (int i = 0; i < this.pp.getAttributeCount(); ++i) {
            if (!this.pp.getAttributeNamespace(i).equals(string) || !this.pp.getAttributeName(i).equals(string2)) continue;
            return this.pp.getAttributeType(i);
        }
        return null;
    }

    @Override
    public String getURI(int n) {
        return this.pp.getAttributeNamespace(n);
    }

    @Override
    public String getValue(int n) {
        return this.pp.getAttributeValue(n);
    }

    @Override
    public String getValue(String string) {
        return this.pp.getAttributeValue(null, string);
    }

    @Override
    public String getValue(String string, String string2) {
        return this.pp.getAttributeValue(string, string2);
    }

    @Override
    public void parse(String string) throws SAXException, IOException {
        this.parse(new InputSource(string));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void parse(InputSource var1_1) throws SAXException, IOException {
        block11 : {
            this.systemId = var1_1.getSystemId();
            this.contentHandler.setDocumentLocator(this);
            var2_8 = var1_1.getCharacterStream();
            if (var2_8 != null) ** GOTO lbl34
            var3_10 = var1_1.getByteStream();
            var4_11 = var1_1.getEncoding();
            var2_8 = var3_10;
            if (var3_10 == null) {
                this.systemId = var1_1.getSystemId();
                if (this.systemId == null) {
                    var1_1 = new SAXParseException("null source systemId", this);
                    this.errorHandler.fatalError((SAXParseException)var1_1);
                    return;
                }
                try {
                    var1_1 = new URL(this.systemId);
                    var2_8 = var1_1.openStream();
                }
                catch (MalformedURLException var1_2) {
                    try {
                        var2_8 = new FileInputStream(this.systemId);
                    }
                    catch (FileNotFoundException var2_9) {
                        var3_10 = new StringBuilder();
                        var3_10.append("could not open file with systemId ");
                        var3_10.append(this.systemId);
                        var1_3 = new SAXParseException(var3_10.toString(), this, var2_9);
                        this.errorHandler.fatalError(var1_3);
                        return;
                    }
                }
            }
            this.pp.setInput((InputStream)var2_8, var4_11);
            break block11;
lbl34: // 1 sources:
            this.pp.setInput((Reader)var2_8);
        }
        try {
            this.contentHandler.startDocument();
            this.pp.next();
            if (this.pp.getEventType() != 2) {
                var2_8 = new StringBuilder();
                var2_8.append("expected start tag not");
                var2_8.append(this.pp.getPositionDescription());
                var1_1 = new SAXParseException(var2_8.toString(), this);
                this.errorHandler.fatalError((SAXParseException)var1_1);
                return;
            }
            this.parseSubTree(this.pp);
            this.contentHandler.endDocument();
            return;
        }
        catch (XmlPullParserException var1_4) {
            var2_8 = new StringBuilder();
            var2_8.append("parsing initialization error: ");
            var2_8.append(var1_4);
            var1_5 = new SAXParseException(var2_8.toString(), this, var1_4);
            this.errorHandler.fatalError(var1_5);
            return;
        }
        catch (XmlPullParserException var1_6) {
            var2_8 = new StringBuilder();
            var2_8.append("parsing initialization error: ");
            var2_8.append(var1_6);
            var1_7 = new SAXParseException(var2_8.toString(), this, var1_6);
            this.errorHandler.fatalError(var1_7);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void parseSubTree(XmlPullParser xmlPullParser) throws SAXException, IOException {
        Object object;
        this.pp = xmlPullParser;
        boolean bl = xmlPullParser.getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces");
        try {
            if (xmlPullParser.getEventType() != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("start tag must be read before skiping subtree");
                stringBuilder.append(xmlPullParser.getPositionDescription());
                SAXException sAXException = new SAXException(stringBuilder.toString());
                throw sAXException;
            }
            int[] arrn = new int[2];
            StringBuilder stringBuilder = new StringBuilder(16);
            int n = xmlPullParser.getDepth() - 1;
            int n2 = 2;
            do {
                Object object2;
                String string;
                if (n2 == 1) return;
                int n3 = 0;
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 == 4) {
                            object = xmlPullParser.getTextCharacters(arrn);
                            this.contentHandler.characters((char[])object, arrn[0], arrn[1]);
                        }
                    } else if (bl) {
                        string = xmlPullParser.getName();
                        object = xmlPullParser.getPrefix();
                        if (object != null) {
                            stringBuilder.setLength(0);
                            stringBuilder.append((String)object);
                            stringBuilder.append(':');
                            stringBuilder.append(string);
                        }
                        object2 = this.contentHandler;
                        String string2 = xmlPullParser.getNamespace();
                        object = object != null ? string : stringBuilder.toString();
                        object2.endElement(string2, string, (String)object);
                        n2 = n3;
                        if (n > xmlPullParser.getDepth()) {
                            n2 = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth());
                        }
                        for (n3 = xmlPullParser.getNamespaceCount((int)(xmlPullParser.getDepth() - 1)) - 1; n3 >= n2; --n3) {
                            this.contentHandler.endPrefixMapping(xmlPullParser.getNamespacePrefix(n3));
                        }
                    } else {
                        this.contentHandler.endElement(xmlPullParser.getNamespace(), xmlPullParser.getName(), xmlPullParser.getName());
                    }
                } else if (bl) {
                    n3 = xmlPullParser.getDepth() - 1;
                    n2 = n > n3 ? xmlPullParser.getNamespaceCount(n3) : 0;
                    n3 = xmlPullParser.getNamespaceCount(n3 + 1);
                    while (n2 < n3) {
                        this.contentHandler.startPrefixMapping(xmlPullParser.getNamespacePrefix(n2), xmlPullParser.getNamespaceUri(n2));
                        ++n2;
                    }
                    string = xmlPullParser.getName();
                    object = xmlPullParser.getPrefix();
                    if (object != null) {
                        stringBuilder.setLength(0);
                        stringBuilder.append((String)object);
                        stringBuilder.append(':');
                        stringBuilder.append(string);
                    }
                    object2 = xmlPullParser.getNamespace();
                    object = object == null ? string : stringBuilder.toString();
                    this.startElement((String)object2, string, (String)object);
                } else {
                    this.startElement(xmlPullParser.getNamespace(), xmlPullParser.getName(), xmlPullParser.getName());
                }
                n2 = xmlPullParser.next();
            } while (xmlPullParser.getDepth() > n);
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            object = new StringBuilder();
            object.append("parsing error: ");
            object.append(xmlPullParserException);
            object = new SAXParseException(object.toString(), this, xmlPullParserException);
            xmlPullParserException.printStackTrace();
            this.errorHandler.fatalError((SAXParseException)object);
        }
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            if (NAMESPACES_FEATURE.equals(string)) {
                this.pp.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", bl);
            } else if (NAMESPACE_PREFIXES_FEATURE.equals(string)) {
                if (this.pp.getFeature("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes") != bl) {
                    this.pp.setFeature("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes", bl);
                }
            } else if (VALIDATION_FEATURE.equals(string)) {
                this.pp.setFeature("http://xmlpull.org/v1/doc/features.html#validation", bl);
            } else {
                this.pp.setFeature(string, bl);
            }
        }
        catch (XmlPullParserException xmlPullParserException) {
            // empty catch block
        }
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (!DECLARATION_HANDLER_PROPERTY.equals(string)) {
            if (!LEXICAL_HANDLER_PROPERTY.equals(string)) {
                try {
                    this.pp.setProperty(string, object);
                    return;
                }
                catch (XmlPullParserException xmlPullParserException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("not supported set property ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append(xmlPullParserException);
                    throw new SAXNotSupportedException(((StringBuilder)object).toString());
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("not supported setting property ");
            ((StringBuilder)object).append(string);
            throw new SAXNotSupportedException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("not supported setting property ");
        ((StringBuilder)object).append(string);
        throw new SAXNotSupportedException(((StringBuilder)object).toString());
    }

    protected void startElement(String string, String string2, String string3) throws SAXException {
        this.contentHandler.startElement(string, string2, string3, this);
    }
}

