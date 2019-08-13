/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.NodeConsumer;
import org.apache.xml.utils.XMLString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

public class DTMTreeWalker {
    private ContentHandler m_contentHandler = null;
    protected DTM m_dtm;
    boolean nextIsRaw = false;

    public DTMTreeWalker() {
    }

    public DTMTreeWalker(ContentHandler contentHandler, DTM dTM) {
        this.m_contentHandler = contentHandler;
        this.m_dtm = dTM;
    }

    private final void dispatachChars(int n) throws SAXException {
        this.m_dtm.dispatchCharactersEvents(n, this.m_contentHandler, false);
    }

    protected void endNode(int n) throws SAXException {
        int n2 = this.m_dtm.getNodeType(n);
        if (n2 != 1) {
            if (n2 != 9) {
                ContentHandler contentHandler;
                if (n2 != 4 && n2 == 5 && (contentHandler = this.m_contentHandler) instanceof LexicalHandler) {
                    ((LexicalHandler)((Object)contentHandler)).endEntity(this.m_dtm.getNodeName(n));
                }
            } else {
                this.m_contentHandler.endDocument();
            }
        } else {
            String string;
            String string2 = string = this.m_dtm.getNamespaceURI(n);
            if (string == null) {
                string2 = "";
            }
            this.m_contentHandler.endElement(string2, this.m_dtm.getLocalName(n), this.m_dtm.getNodeName(n));
            n2 = this.m_dtm.getFirstNamespaceNode(n, true);
            while (-1 != n2) {
                string2 = this.m_dtm.getNodeNameX(n2);
                this.m_contentHandler.endPrefixMapping(string2);
                n2 = this.m_dtm.getNextNamespaceNode(n, n2, true);
            }
        }
    }

    public ContentHandler getcontentHandler() {
        return this.m_contentHandler;
    }

    public void setDTM(DTM dTM) {
        this.m_dtm = dTM;
    }

    public void setcontentHandler(ContentHandler contentHandler) {
        this.m_contentHandler = contentHandler;
    }

    protected void startNode(int n) throws SAXException {
        boolean bl = this.m_contentHandler instanceof NodeConsumer;
        int n2 = this.m_dtm.getNodeType(n);
        if (n2 != 1) {
            if (n2 != 11) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        if (n2 != 5) {
                            if (n2 != 7) {
                                if (n2 != 8) {
                                    if (n2 == 9) {
                                        this.m_contentHandler.startDocument();
                                    }
                                } else {
                                    XMLString xMLString = this.m_dtm.getStringValue(n);
                                    ContentHandler contentHandler = this.m_contentHandler;
                                    if (contentHandler instanceof LexicalHandler) {
                                        xMLString.dispatchAsComment((LexicalHandler)((Object)contentHandler));
                                    }
                                }
                            } else {
                                String string = this.m_dtm.getNodeName(n);
                                if (string.equals("xslt-next-is-raw")) {
                                    this.nextIsRaw = true;
                                } else {
                                    this.m_contentHandler.processingInstruction(string, this.m_dtm.getNodeValue(n));
                                }
                            }
                        } else {
                            ContentHandler contentHandler = this.m_contentHandler;
                            if (contentHandler instanceof LexicalHandler) {
                                ((LexicalHandler)((Object)contentHandler)).startEntity(this.m_dtm.getNodeName(n));
                            }
                        }
                    } else {
                        Object object = this.m_contentHandler;
                        bl = object instanceof LexicalHandler;
                        object = bl ? (LexicalHandler)object : null;
                        if (bl) {
                            object.startCDATA();
                        }
                        this.dispatachChars(n);
                        if (bl) {
                            object.endCDATA();
                        }
                    }
                } else if (this.nextIsRaw) {
                    this.nextIsRaw = false;
                    this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
                    this.dispatachChars(n);
                    this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
                } else {
                    this.dispatachChars(n);
                }
            }
        } else {
            String string;
            DTM dTM = this.m_dtm;
            n2 = dTM.getFirstNamespaceNode(n, true);
            while (-1 != n2) {
                string = dTM.getNodeNameX(n2);
                this.m_contentHandler.startPrefixMapping(string, dTM.getNodeValue(n2));
                n2 = dTM.getNextNamespaceNode(n, n2, true);
            }
            Object object = dTM.getNamespaceURI(n);
            string = object;
            if (object == null) {
                string = "";
            }
            object = new AttributesImpl();
            n2 = dTM.getFirstAttribute(n);
            while (n2 != -1) {
                ((AttributesImpl)object).addAttribute(dTM.getNamespaceURI(n2), dTM.getLocalName(n2), dTM.getNodeName(n2), "CDATA", dTM.getNodeValue(n2));
                n2 = dTM.getNextAttribute(n2);
            }
            this.m_contentHandler.startElement(string, this.m_dtm.getLocalName(n), this.m_dtm.getNodeName(n), (Attributes)object);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(int var1_1) throws SAXException {
        var2_2 = var1_1;
        while (-1 != var2_2) {
            block4 : {
                this.startNode(var2_2);
                var3_3 = this.m_dtm.getFirstChild(var2_2);
                var4_4 = var2_2;
                var2_2 = var3_3;
                do lbl-1000: // 3 sources:
                {
                    var3_3 = var2_2;
                    if (-1 != var2_2) break block4;
                    this.endNode(var4_4);
                    if (var1_1 == var4_4) {
                        var3_3 = var2_2;
                        break block4;
                    }
                    var2_2 = var5_5 = this.m_dtm.getNextSibling(var4_4);
                    if (-1 != var5_5) ** GOTO lbl-1000
                    var3_3 = this.m_dtm.getParent(var4_4);
                    if (-1 == var3_3) break;
                    var2_2 = var5_5;
                    var4_4 = var3_3;
                } while (var1_1 != var3_3);
                if (-1 != var3_3) {
                    this.endNode(var3_3);
                }
                var3_3 = -1;
            }
            var2_2 = var3_3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(int var1_1, int var2_2) throws SAXException {
        while (-1 != var1_1) {
            block3 : {
                this.startNode(var1_1);
                var3_3 = this.m_dtm.getFirstChild(var1_1);
                var4_4 = var1_1;
                var1_1 = var3_3;
                do lbl-1000: // 4 sources:
                {
                    var3_3 = var1_1;
                    if (-1 != var1_1) break block3;
                    this.endNode(var4_4);
                    if (-1 != var2_2 && var2_2 == var4_4) {
                        var3_3 = var1_1;
                        break block3;
                    }
                    var1_1 = var3_3 = this.m_dtm.getNextSibling(var4_4);
                    if (-1 != var3_3) ** GOTO lbl-1000
                    var5_5 = this.m_dtm.getParent(var4_4);
                    if (-1 == var5_5) break;
                    var1_1 = var3_3;
                    var4_4 = var5_5;
                    if (-1 == var2_2) ** GOTO lbl-1000
                    var1_1 = var3_3;
                    var4_4 = var5_5;
                } while (var2_2 != var5_5);
                var3_3 = -1;
            }
            var1_1 = var3_3;
        }
    }
}

