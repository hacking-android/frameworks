/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.File;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.utils.AttList;
import org.apache.xml.serializer.utils.DOM2Helper;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.LocatorImpl;

public final class TreeWalker {
    private final SerializationHandler m_Serializer;
    private final ContentHandler m_contentHandler;
    protected final DOM2Helper m_dh;
    private final LocatorImpl m_locator = new LocatorImpl();
    boolean nextIsRaw = false;

    public TreeWalker(ContentHandler contentHandler) {
        this(contentHandler, null);
    }

    public TreeWalker(ContentHandler object, String object2) {
        this.m_contentHandler = object;
        object = this.m_contentHandler;
        this.m_Serializer = object instanceof SerializationHandler ? (SerializationHandler)object : null;
        this.m_contentHandler.setDocumentLocator(this.m_locator);
        if (object2 != null) {
            this.m_locator.setSystemId((String)object2);
        } else {
            try {
                object2 = this.m_locator;
                object = new StringBuilder();
                ((StringBuilder)object).append(System.getProperty("user.dir"));
                ((StringBuilder)object).append(File.separator);
                ((StringBuilder)object).append("dummy.xsl");
                ((LocatorImpl)object2).setSystemId(((StringBuilder)object).toString());
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
        object = this.m_contentHandler;
        if (object != null) {
            object.setDocumentLocator(this.m_locator);
        }
        try {
            object2 = this.m_locator;
            object = new StringBuilder();
            ((StringBuilder)object).append(System.getProperty("user.dir"));
            ((StringBuilder)object).append(File.separator);
            ((StringBuilder)object).append("dummy.xsl");
            ((LocatorImpl)object2).setSystemId(((StringBuilder)object).toString());
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        this.m_dh = new DOM2Helper();
    }

    private final void dispatachChars(Node object) throws SAXException {
        SerializationHandler serializationHandler = this.m_Serializer;
        if (serializationHandler != null) {
            serializationHandler.characters((Node)object);
        } else {
            object = ((Text)object).getData();
            this.m_contentHandler.characters(((String)object).toCharArray(), 0, ((String)object).length());
        }
    }

    protected void endNode(Node object) throws SAXException {
        int n = object.getNodeType();
        if (n != 1) {
            if (n != 9 && n != 4 && n == 5) {
                EntityReference entityReference = (EntityReference)object;
                object = this.m_contentHandler;
                if (object instanceof LexicalHandler) {
                    ((LexicalHandler)object).endEntity(entityReference.getNodeName());
                }
            }
        } else {
            Object object2;
            Object object3 = object2 = this.m_dh.getNamespaceOfNode((Node)object);
            if (object2 == null) {
                object3 = "";
            }
            this.m_contentHandler.endElement((String)object3, this.m_dh.getLocalNameOfNode((Node)object), object.getNodeName());
            if (this.m_Serializer == null) {
                object2 = (Element)object;
                object3 = object2.getAttributes();
                for (n = object3.getLength() - 1; n >= 0; --n) {
                    object = object3.item(n).getNodeName();
                    int n2 = ((String)object).indexOf(58);
                    if (!((String)object).equals("xmlns") && !((String)object).startsWith("xmlns:")) {
                        if (n2 <= 0) continue;
                        object = ((String)object).substring(0, n2);
                        this.m_contentHandler.endPrefixMapping((String)object);
                        continue;
                    }
                    object = n2 < 0 ? "" : ((String)object).substring(n2 + 1);
                    this.m_contentHandler.endPrefixMapping((String)object);
                }
                if (object2.getNamespaceURI() != null) {
                    object = object3 = object2.getPrefix();
                    if (object3 == null) {
                        object = "";
                    }
                    this.m_contentHandler.endPrefixMapping((String)object);
                }
            }
        }
    }

    public ContentHandler getContentHandler() {
        return this.m_contentHandler;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void startNode(Node object) throws SAXException {
        Object object2;
        if (object instanceof Locator) {
            object2 = (Locator)object;
            this.m_locator.setColumnNumber(object2.getColumnNumber());
            this.m_locator.setLineNumber(object2.getLineNumber());
            this.m_locator.setPublicId(object2.getPublicId());
            this.m_locator.setSystemId(object2.getSystemId());
        } else {
            this.m_locator.setColumnNumber(0);
            this.m_locator.setLineNumber(0);
        }
        int n = object.getNodeType();
        if (n != 1) {
            if (n == 11) return;
            if (n != 3) {
                if (n != 4) {
                    if (n != 5) {
                        if (n != 7) {
                            if (n != 8) {
                                if (n == 9) return;
                            }
                            object = ((Comment)object).getData();
                            object2 = this.m_contentHandler;
                            if (!(object2 instanceof LexicalHandler)) return;
                            ((LexicalHandler)object2).comment(((String)object).toCharArray(), 0, ((String)object).length());
                            return;
                        } else if ((object = (ProcessingInstruction)object).getNodeName().equals("xslt-next-is-raw")) {
                            this.nextIsRaw = true;
                            return;
                        } else {
                            this.m_contentHandler.processingInstruction(object.getNodeName(), object.getData());
                        }
                        return;
                    } else {
                        object = (EntityReference)object;
                        object2 = this.m_contentHandler;
                        if (!(object2 instanceof LexicalHandler)) return;
                        ((LexicalHandler)object2).startEntity(object.getNodeName());
                    }
                    return;
                } else {
                    object2 = this.m_contentHandler;
                    boolean bl = object2 instanceof LexicalHandler;
                    object2 = bl ? (LexicalHandler)object2 : null;
                    if (bl) {
                        object2.startCDATA();
                    }
                    this.dispatachChars((Node)object);
                    if (!bl) return;
                    object2.endCDATA();
                }
                return;
            } else if (this.nextIsRaw) {
                this.nextIsRaw = false;
                this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
                this.dispatachChars((Node)object);
                this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
                return;
            } else {
                this.dispatachChars((Node)object);
            }
            return;
        } else {
            Object object3;
            Element element = (Element)object;
            Object object4 = element.getNamespaceURI();
            if (object4 != null) {
                object2 = object3 = element.getPrefix();
                if (object3 == null) {
                    object2 = "";
                }
                this.m_contentHandler.startPrefixMapping((String)object2, (String)object4);
            }
            object4 = element.getAttributes();
            int n2 = object4.getLength();
            for (n = 0; n < n2; ++n) {
                object3 = object4.item(n);
                object2 = object3.getNodeName();
                int n3 = ((String)object2).indexOf(58);
                if (!((String)object2).equals("xmlns") && !((String)object2).startsWith("xmlns:")) {
                    if (n3 <= 0) continue;
                    object2 = ((String)object2).substring(0, n3);
                    if ((object3 = object3.getNamespaceURI()) == null) continue;
                    this.m_contentHandler.startPrefixMapping((String)object2, (String)object3);
                    continue;
                }
                object2 = n3 < 0 ? "" : ((String)object2).substring(n3 + 1);
                this.m_contentHandler.startPrefixMapping((String)object2, object3.getNodeValue());
            }
            object2 = object3 = this.m_dh.getNamespaceOfNode((Node)object);
            if (object3 == null) {
                object2 = "";
            }
            this.m_contentHandler.startElement((String)object2, this.m_dh.getLocalNameOfNode((Node)object), object.getNodeName(), new AttList((NamedNodeMap)object4, this.m_dh));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(Node var1_1) throws SAXException {
        this.m_contentHandler.startDocument();
        var2_2 = var1_1;
        do {
            block5 : {
                if (var2_2 == null) {
                    this.m_contentHandler.endDocument();
                    return;
                }
                this.startNode(var2_2);
                var3_3 = var2_2.getFirstChild();
                var4_4 = var2_2;
                var2_2 = var3_3;
                do lbl-1000: // 3 sources:
                {
                    var3_3 = var2_2;
                    if (var2_2 != null) break block5;
                    this.endNode(var4_4);
                    if (var1_1.equals(var4_4)) {
                        var3_3 = var2_2;
                        break block5;
                    }
                    var2_2 = var5_5 = var4_4.getNextSibling();
                    if (var5_5 != null) ** GOTO lbl-1000
                    var3_3 = var4_4.getParentNode();
                    if (var3_3 == null) break;
                    var2_2 = var5_5;
                    var4_4 = var3_3;
                } while (!var1_1.equals(var3_3));
                if (var3_3 != null) {
                    this.endNode(var3_3);
                }
                var3_3 = null;
            }
            var2_2 = var3_3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(Node var1_1, Node var2_2) throws SAXException {
        this.m_contentHandler.startDocument();
        do {
            block4 : {
                if (var1_1 == null) {
                    this.m_contentHandler.endDocument();
                    return;
                }
                this.startNode(var1_1);
                var3_3 = var1_1.getFirstChild();
                var4_4 = var1_1;
                var1_1 = var3_3;
                do lbl-1000: // 4 sources:
                {
                    var3_3 = var1_1;
                    if (var1_1 != null) break block4;
                    this.endNode(var4_4);
                    if (var2_2 != null && var2_2.equals(var4_4)) {
                        var3_3 = var1_1;
                        break block4;
                    }
                    var1_1 = var5_5 = var4_4.getNextSibling();
                    if (var5_5 != null) ** GOTO lbl-1000
                    var3_3 = var4_4.getParentNode();
                    if (var3_3 == null) break;
                    var1_1 = var5_5;
                    var4_4 = var3_3;
                    if (var2_2 == null) ** GOTO lbl-1000
                    var1_1 = var5_5;
                    var4_4 = var3_3;
                } while (!var2_2.equals(var3_3));
                var3_3 = null;
            }
            var1_1 = var3_3;
        } while (true);
    }
}

