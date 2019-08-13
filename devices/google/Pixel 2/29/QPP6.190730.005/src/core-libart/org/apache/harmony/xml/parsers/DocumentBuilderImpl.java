/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.parsers;

import com.android.org.kxml2.io.KXmlParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import libcore.io.IoUtils;
import org.apache.harmony.xml.dom.AttrImpl;
import org.apache.harmony.xml.dom.CDATASectionImpl;
import org.apache.harmony.xml.dom.CommentImpl;
import org.apache.harmony.xml.dom.DOMImplementationImpl;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.DocumentTypeImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.EntityReferenceImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.apache.harmony.xml.dom.ProcessingInstructionImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;
import org.xmlpull.v1.XmlPullParserException;

class DocumentBuilderImpl
extends DocumentBuilder {
    private static DOMImplementationImpl dom = DOMImplementationImpl.getInstance();
    private boolean coalescing;
    private EntityResolver entityResolver;
    private ErrorHandler errorHandler;
    private boolean ignoreComments;
    private boolean ignoreElementContentWhitespace;
    private boolean namespaceAware;

    DocumentBuilderImpl() {
    }

    private void appendText(DocumentImpl leafNodeImpl, Node node, int n, String string) {
        Node node2;
        if (string.isEmpty()) {
            return;
        }
        if ((this.coalescing || n != 5) && (node2 = node.getLastChild()) != null && node2.getNodeType() == 3) {
            ((Text)node2).appendData(string);
            return;
        }
        leafNodeImpl = n == 5 ? new CDATASectionImpl((DocumentImpl)leafNodeImpl, string) : new TextImpl((DocumentImpl)leafNodeImpl, string);
        node.appendChild(leafNodeImpl);
    }

    private void parse(KXmlParser kXmlParser, DocumentImpl documentImpl, Node node, int n) throws XmlPullParserException, IOException {
        int n2 = kXmlParser.getEventType();
        while (n2 != n && n2 != 1) {
            String string;
            Object object;
            Object object2 = "";
            if (n2 == 8) {
                string = kXmlParser.getText();
                n2 = string.indexOf(32);
                object = n2 != -1 ? string.substring(0, n2) : string;
                if (n2 != -1) {
                    object2 = string.substring(n2 + 1);
                }
                node.appendChild(documentImpl.createProcessingInstruction((String)object, (String)object2));
            } else if (n2 == 10) {
                documentImpl.appendChild(new DocumentTypeImpl(documentImpl, kXmlParser.getRootElementName(), kXmlParser.getPublicId(), kXmlParser.getSystemId()));
            } else if (n2 == 9) {
                if (!this.ignoreComments) {
                    node.appendChild(documentImpl.createComment(kXmlParser.getText()));
                }
            } else if (n2 == 7) {
                if (!this.ignoreElementContentWhitespace && documentImpl != node) {
                    this.appendText(documentImpl, node, n2, kXmlParser.getText());
                }
            } else if (n2 != 4 && n2 != 5) {
                if (n2 == 6) {
                    object = kXmlParser.getName();
                    object2 = this.entityResolver;
                    object2 = this.resolvePredefinedOrCharacterEntity((String)object);
                    if (object2 != null) {
                        this.appendText(documentImpl, node, n2, (String)object2);
                    } else {
                        node.appendChild(documentImpl.createEntityReference((String)object));
                    }
                } else if (n2 == 2) {
                    Object object3;
                    if (this.namespaceAware) {
                        object2 = kXmlParser.getNamespace();
                        object3 = kXmlParser.getName();
                        string = kXmlParser.getPrefix();
                        object = object2;
                        if ("".equals(object2)) {
                            object = null;
                        }
                        ElementImpl elementImpl = documentImpl.createElementNS((String)object, (String)object3);
                        elementImpl.setPrefix(string);
                        node.appendChild(elementImpl);
                        for (n2 = 0; n2 < kXmlParser.getAttributeCount(); ++n2) {
                            string = kXmlParser.getAttributeNamespace(n2);
                            String string2 = kXmlParser.getAttributePrefix(n2);
                            String string3 = kXmlParser.getAttributeName(n2);
                            String string4 = kXmlParser.getAttributeValue(n2);
                            object2 = string;
                            if ("".equals(string)) {
                                object2 = null;
                            }
                            object2 = documentImpl.createAttributeNS((String)object2, string3);
                            object2.setPrefix(string2);
                            object2.setValue(string4);
                            elementImpl.setAttributeNodeNS((Attr)object2);
                        }
                        kXmlParser.nextToken();
                        this.parse(kXmlParser, documentImpl, elementImpl, 3);
                        kXmlParser.require(3, (String)object, (String)object3);
                    } else {
                        string = kXmlParser.getName();
                        object = documentImpl.createElement(string);
                        node.appendChild((Node)object);
                        for (n2 = 0; n2 < kXmlParser.getAttributeCount(); ++n2) {
                            object3 = kXmlParser.getAttributeName(n2);
                            object2 = kXmlParser.getAttributeValue(n2);
                            object3 = documentImpl.createAttribute((String)object3);
                            object3.setValue((String)object2);
                            object.setAttributeNode((Attr)object3);
                        }
                        kXmlParser.nextToken();
                        this.parse(kXmlParser, documentImpl, (Node)object, 3);
                        kXmlParser.require(3, "", string);
                    }
                }
            } else {
                this.appendText(documentImpl, node, n2, kXmlParser.getText());
            }
            n2 = kXmlParser.nextToken();
        }
    }

    private String resolveCharacterReference(String string, int n) {
        try {
            n = Integer.parseInt(string, n);
            if (Character.isBmpCodePoint(n)) {
                return String.valueOf((char)n);
            }
            string = new String(Character.toChars(n));
            return string;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private String resolvePredefinedOrCharacterEntity(String string) {
        if (string.startsWith("#x")) {
            return this.resolveCharacterReference(string.substring(2), 16);
        }
        if (string.startsWith("#")) {
            return this.resolveCharacterReference(string.substring(1), 10);
        }
        if ("lt".equals(string)) {
            return "<";
        }
        if ("gt".equals(string)) {
            return ">";
        }
        if ("amp".equals(string)) {
            return "&";
        }
        if ("apos".equals(string)) {
            return "'";
        }
        if ("quot".equals(string)) {
            return "\"";
        }
        return null;
    }

    @Override
    public DOMImplementation getDOMImplementation() {
        return dom;
    }

    @Override
    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    @Override
    public boolean isValidating() {
        return false;
    }

    @Override
    public Document newDocument() {
        return dom.createDocument(null, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Document parse(InputSource var1_1) throws SAXException, IOException {
        block6 : {
            block5 : {
                block8 : {
                    block9 : {
                        block7 : {
                            if (var1_1 == null) throw new IllegalArgumentException("source == null");
                            var2_3 = var1_1.getEncoding();
                            var3_5 = var1_1.getSystemId();
                            var4_6 = new DocumentImpl(DocumentBuilderImpl.dom, null, null, null, (String)var2_3);
                            var4_6.setDocumentURI(var3_5);
                            var5_7 = new KXmlParser();
                            var5_7.keepNamespaceAttributes();
                            var5_7.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", this.namespaceAware);
                            if (var1_1.getByteStream() == null) break block7;
                            var5_7.setInput(var1_1.getByteStream(), (String)var2_3);
                            break block8;
                        }
                        if (var1_1.getCharacterStream() == null) break block9;
                        var5_7.setInput(var1_1.getCharacterStream());
                        break block8;
                    }
                    if (var3_5 == null) ** GOTO lbl34
                    var6_8 = new URL(var3_5);
                    var6_8 = var6_8.openConnection();
                    var6_8.connect();
                    var5_7.setInput(var6_8.getInputStream(), (String)var2_3);
                }
                if (var5_7.nextToken() == 1) break block5;
                this.parse(var5_7, (DocumentImpl)var4_6, (Node)var4_6, 1);
                var5_7.require(1, null, null);
                IoUtils.closeQuietly(var5_7);
                return var4_6;
            }
            try {
                var2_3 = new SAXParseException("Unexpected end of document", null);
                throw var2_3;
lbl34: // 1 sources:
                var2_3 = new SAXParseException("InputSource needs a stream, reader or URI", null);
                throw var2_3;
            }
            catch (Throwable var1_2) {
                break block6;
            }
            catch (XmlPullParserException var2_4) {
                var4_6 = var2_4.getDetail();
                if (var4_6 instanceof IOException != false) throw (IOException)var4_6;
                if (var4_6 instanceof RuntimeException != false) throw (RuntimeException)var4_6;
                var4_6 = new LocatorImpl();
                var4_6.setPublicId(var1_1.getPublicId());
                var4_6.setSystemId(var3_5);
                var4_6.setLineNumber(var2_4.getLineNumber());
                var4_6.setColumnNumber(var2_4.getColumnNumber());
                var1_1 = new SAXParseException(var2_4.getMessage(), (Locator)var4_6);
                if (this.errorHandler == null) throw var1_1;
                this.errorHandler.error((SAXParseException)var1_1);
                throw var1_1;
            }
        }
        IoUtils.closeQuietly(var5_7);
        throw var1_2;
    }

    @Override
    public void reset() {
        this.coalescing = false;
        this.entityResolver = null;
        this.errorHandler = null;
        this.ignoreComments = false;
        this.ignoreElementContentWhitespace = false;
        this.namespaceAware = false;
    }

    public void setCoalescing(boolean bl) {
        this.coalescing = bl;
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setIgnoreComments(boolean bl) {
        this.ignoreComments = bl;
    }

    public void setIgnoreElementContentWhitespace(boolean bl) {
        this.ignoreElementContentWhitespace = bl;
    }

    public void setNamespaceAware(boolean bl) {
        this.namespaceAware = bl;
    }
}

