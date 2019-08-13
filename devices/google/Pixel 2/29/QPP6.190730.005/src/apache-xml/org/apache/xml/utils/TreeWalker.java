/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.File;
import org.apache.xml.dtm.ref.dom2dtm.DOM2DTM;
import org.apache.xml.utils.AttList;
import org.apache.xml.utils.DOM2Helper;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.NodeConsumer;
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

public class TreeWalker {
    private ContentHandler m_contentHandler = null;
    protected DOMHelper m_dh;
    private LocatorImpl m_locator = new LocatorImpl();
    boolean nextIsRaw = false;

    public TreeWalker(ContentHandler object) {
        this.m_contentHandler = object;
        object = this.m_contentHandler;
        if (object != null) {
            object.setDocumentLocator(this.m_locator);
        }
        try {
            LocatorImpl locatorImpl = this.m_locator;
            object = new StringBuilder();
            ((StringBuilder)object).append(System.getProperty("user.dir"));
            ((StringBuilder)object).append(File.separator);
            ((StringBuilder)object).append("dummy.xsl");
            locatorImpl.setSystemId(((StringBuilder)object).toString());
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        this.m_dh = new DOM2Helper();
    }

    public TreeWalker(ContentHandler object, DOMHelper dOMHelper) {
        this.m_contentHandler = object;
        this.m_contentHandler.setDocumentLocator(this.m_locator);
        try {
            LocatorImpl locatorImpl = this.m_locator;
            object = new StringBuilder();
            ((StringBuilder)object).append(System.getProperty("user.dir"));
            ((StringBuilder)object).append(File.separator);
            ((StringBuilder)object).append("dummy.xsl");
            locatorImpl.setSystemId(((StringBuilder)object).toString());
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        this.m_dh = dOMHelper;
    }

    public TreeWalker(ContentHandler object, DOMHelper dOMHelper, String object2) {
        this.m_contentHandler = object;
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
        this.m_dh = dOMHelper;
    }

    private final void dispatachChars(Node object) throws SAXException {
        ContentHandler contentHandler = this.m_contentHandler;
        if (contentHandler instanceof DOM2DTM.CharacterNodeHandler) {
            ((DOM2DTM.CharacterNodeHandler)((Object)contentHandler)).characters((Node)object);
        } else {
            object = ((Text)object).getData();
            this.m_contentHandler.characters(((String)object).toCharArray(), 0, ((String)object).length());
        }
    }

    protected void endNode(Node object) throws SAXException {
        block3 : {
            int n;
            block2 : {
                n = object.getNodeType();
                if (n == 1) break block2;
                if (n == 9 || n == 4 || n != 5) break block3;
                object = (EntityReference)object;
                ContentHandler contentHandler = this.m_contentHandler;
                if (!(contentHandler instanceof LexicalHandler)) break block3;
                ((LexicalHandler)((Object)contentHandler)).endEntity(object.getNodeName());
                break block3;
            }
            String string = this.m_dh.getNamespaceOfNode((Node)object);
            Object object2 = string;
            if (string == null) {
                object2 = "";
            }
            this.m_contentHandler.endElement((String)object2, this.m_dh.getLocalNameOfNode((Node)object), object.getNodeName());
            object2 = ((Element)object).getAttributes();
            int n2 = object2.getLength();
            for (n = 0; n < n2; ++n) {
                object = object2.item(n).getNodeName();
                if (!((String)object).equals("xmlns") && !((String)object).startsWith("xmlns:")) continue;
                int n3 = ((String)object).indexOf(":");
                object = n3 < 0 ? "" : ((String)object).substring(n3 + 1);
                this.m_contentHandler.endPrefixMapping((String)object);
            }
        }
    }

    public ContentHandler getContentHandler() {
        return this.m_contentHandler;
    }

    public void setContentHandler(ContentHandler contentHandler) {
        this.m_contentHandler = contentHandler;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void startNode(Node object) throws SAXException {
        Object object2 = this.m_contentHandler;
        if (object2 instanceof NodeConsumer) {
            ((NodeConsumer)object2).setOriginatingNode((Node)object);
        }
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
                        object2 = (EntityReference)object;
                        object = this.m_contentHandler;
                        if (!(object instanceof LexicalHandler)) return;
                        ((LexicalHandler)object).startEntity(object2.getNodeName());
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
            NamedNodeMap namedNodeMap = ((Element)object).getAttributes();
            int n2 = namedNodeMap.getLength();
            for (n = 0; n < n2; ++n) {
                object3 = namedNodeMap.item(n);
                object2 = object3.getNodeName();
                if (!((String)object2).equals("xmlns") && !((String)object2).startsWith("xmlns:")) continue;
                int n3 = ((String)object2).indexOf(":");
                object2 = n3 < 0 ? "" : ((String)object2).substring(n3 + 1);
                this.m_contentHandler.startPrefixMapping((String)object2, object3.getNodeValue());
            }
            object2 = object3 = this.m_dh.getNamespaceOfNode((Node)object);
            if (object3 == null) {
                object2 = "";
            }
            this.m_contentHandler.startElement((String)object2, this.m_dh.getLocalNameOfNode((Node)object), object.getNodeName(), new AttList(namedNodeMap, this.m_dh));
        }
    }

    public void traverse(Node node) throws SAXException {
        this.m_contentHandler.startDocument();
        this.traverseFragment(node);
        this.m_contentHandler.endDocument();
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverseFragment(Node var1_1) throws SAXException {
        var2_2 = var1_1;
        while (var2_2 != null) {
            block4 : {
                this.startNode(var2_2);
                var3_3 = var2_2.getFirstChild();
                var4_4 = var2_2;
                var2_2 = var3_3;
                do lbl-1000: // 3 sources:
                {
                    var3_3 = var2_2;
                    if (var2_2 != null) break block4;
                    this.endNode(var4_4);
                    if (var1_1.equals(var4_4)) {
                        var3_3 = var2_2;
                        break block4;
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
        }
    }
}

