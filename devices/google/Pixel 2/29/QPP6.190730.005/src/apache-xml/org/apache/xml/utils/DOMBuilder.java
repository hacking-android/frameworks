/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Writer;
import java.util.Stack;
import java.util.Vector;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class DOMBuilder
implements ContentHandler,
LexicalHandler {
    protected Node m_currentNode = null;
    public Document m_doc;
    public DocumentFragment m_docFrag = null;
    protected Stack m_elemStack = new Stack();
    protected boolean m_inCData = false;
    protected Node m_nextSibling = null;
    protected Vector m_prefixMappings = new Vector();
    protected Node m_root = null;

    public DOMBuilder(Document document) {
        this.m_doc = document;
    }

    public DOMBuilder(Document document, DocumentFragment documentFragment) {
        this.m_doc = document;
        this.m_docFrag = documentFragment;
    }

    public DOMBuilder(Document document, Node node) {
        this.m_doc = document;
        this.m_root = node;
        this.m_currentNode = node;
        if (node instanceof Element) {
            this.m_elemStack.push(node);
        }
    }

    private boolean isOutsideDocElem() {
        Node node;
        boolean bl = this.m_docFrag == null && this.m_elemStack.size() == 0 && ((node = this.m_currentNode) == null || node.getNodeType() == 9);
        return bl;
    }

    protected void append(Node node) throws SAXException {
        Object object = this.m_currentNode;
        if (object != null) {
            Node node2;
            if (object == this.m_root && (node2 = this.m_nextSibling) != null) {
                object.insertBefore(node, node2);
            } else {
                object.appendChild(node);
            }
        } else {
            object = this.m_docFrag;
            if (object != null) {
                Node node3 = this.m_nextSibling;
                if (node3 != null) {
                    object.insertBefore(node, node3);
                } else {
                    object.appendChild(node);
                }
            } else {
                boolean bl;
                boolean bl2 = true;
                short s = node.getNodeType();
                if (s == 3) {
                    object = node.getNodeValue();
                    if (object != null && ((String)object).trim().length() > 0) {
                        throw new SAXException(XMLMessages.createXMLMessage("ER_CANT_OUTPUT_TEXT_BEFORE_DOC", null));
                    }
                    bl = false;
                } else {
                    bl = bl2;
                    if (s == 1) {
                        if (this.m_doc.getDocumentElement() == null) {
                            bl = bl2;
                        } else {
                            throw new SAXException(XMLMessages.createXMLMessage("ER_CANT_HAVE_MORE_THAN_ONE_ROOT", null));
                        }
                    }
                }
                if (bl) {
                    object = this.m_nextSibling;
                    if (object != null) {
                        this.m_doc.insertBefore(node, (Node)object);
                    } else {
                        this.m_doc.appendChild(node);
                    }
                }
            }
        }
    }

    public void cdata(char[] object, int n, int n2) throws SAXException {
        if (this.isOutsideDocElem() && XMLCharacterRecognizer.isWhiteSpace(object, n, n2)) {
            return;
        }
        object = new String((char[])object, n, n2);
        ((CDATASection)this.m_currentNode.getLastChild()).appendData((String)object);
    }

    @Override
    public void characters(char[] object, int n, int n2) throws SAXException {
        if (this.isOutsideDocElem() && XMLCharacterRecognizer.isWhiteSpace((char[])object, n, n2)) {
            return;
        }
        if (this.m_inCData) {
            this.cdata((char[])object, n, n2);
            return;
        }
        String string = new String((char[])object, n, n2);
        object = this.m_currentNode;
        object = object != null ? object.getLastChild() : null;
        if (object != null && object.getNodeType() == 3) {
            ((Text)object).appendData(string);
        } else {
            this.append(this.m_doc.createTextNode(string));
        }
    }

    public void charactersRaw(char[] object, int n, int n2) throws SAXException {
        if (this.isOutsideDocElem() && XMLCharacterRecognizer.isWhiteSpace(object, n, n2)) {
            return;
        }
        object = new String((char[])object, n, n2);
        this.append(this.m_doc.createProcessingInstruction("xslt-next-is-raw", "formatter-to-dom"));
        this.append(this.m_doc.createTextNode((String)object));
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.append(this.m_doc.createComment(new String(arrc, n, n2)));
    }

    @Override
    public void endCDATA() throws SAXException {
        this.m_inCData = false;
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String object, String string, String string2) throws SAXException {
        this.m_elemStack.pop();
        object = this.m_elemStack.isEmpty() ? null : (Node)this.m_elemStack.peek();
        this.m_currentNode = object;
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    public void entityReference(String string) throws SAXException {
        this.append(this.m_doc.createEntityReference(string));
    }

    public Node getCurrentNode() {
        return this.m_currentNode;
    }

    public Node getNextSibling() {
        return this.m_nextSibling;
    }

    public Node getRootDocument() {
        Node node = this.m_docFrag;
        if (node == null) {
            node = this.m_doc;
        }
        return node;
    }

    public Node getRootNode() {
        return this.m_root;
    }

    public Writer getWriter() {
        return null;
    }

    @Override
    public void ignorableWhitespace(char[] object, int n, int n2) throws SAXException {
        if (this.isOutsideDocElem()) {
            return;
        }
        object = new String((char[])object, n, n2);
        this.append(this.m_doc.createTextNode((String)object));
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.append(this.m_doc.createProcessingInstruction(string, string2));
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    public void setIDAttribute(String string, Element element) {
    }

    public void setNextSibling(Node node) {
        this.m_nextSibling = node;
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
        this.m_inCData = true;
        this.append(this.m_doc.createCDATASection(""));
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String string, String object, String string2, Attributes attributes) throws SAXException {
        int n;
        int n2;
        block13 : {
            object = string != null && string.length() != 0 ? this.m_doc.createElementNS(string, string2) : this.m_doc.createElementNS(null, string2);
            this.append((Node)object);
            n = attributes.getLength();
            if (n == 0) break block13;
            for (n2 = 0; n2 < n; ++n2) {
                block15 : {
                    block14 : {
                        if (attributes.getType(n2).equalsIgnoreCase("ID")) {
                            this.setIDAttribute(attributes.getValue(n2), (Element)object);
                        }
                        string = string2 = attributes.getURI(n2);
                        if (!"".equals(string2)) break block14;
                        string = null;
                    }
                    string2 = attributes.getQName(n2);
                    if (!string2.startsWith("xmlns:") && !string2.equals("xmlns")) break block15;
                    string = "http://www.w3.org/2000/xmlns/";
                }
                object.setAttributeNS(string, string2, attributes.getValue(n2));
            }
        }
        n = this.m_prefixMappings.size();
        for (n2 = 0; n2 < n; n2 += 2) {
            string = (String)this.m_prefixMappings.elementAt(n2);
            if (string == null) continue;
            object.setAttributeNS("http://www.w3.org/2000/xmlns/", string, (String)this.m_prefixMappings.elementAt(n2 + 1));
        }
        try {
            this.m_prefixMappings.clear();
            this.m_elemStack.push(object);
            this.m_currentNode = object;
            return;
        }
        catch (Exception exception) {
            throw new SAXException(exception);
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        if (string != null && !string.equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("xmlns:");
            stringBuilder.append(string);
            string = stringBuilder.toString();
        } else {
            string = "xmlns";
        }
        this.m_prefixMappings.addElement(string);
        this.m_prefixMappings.addElement(string2);
    }
}

