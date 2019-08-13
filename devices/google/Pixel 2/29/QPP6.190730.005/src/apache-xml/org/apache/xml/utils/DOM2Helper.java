/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.DOMOrder;
import org.apache.xml.utils.DefaultErrorHandler;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOM2Helper
extends DOMHelper {
    private Document m_doc;

    public static Node getParentOfNode(Node node) {
        Node node2;
        Node node3 = node2 = node.getParentNode();
        if (node2 == null) {
            node3 = node2;
            if (2 == node.getNodeType()) {
                node3 = ((Attr)node).getOwnerElement();
            }
        }
        return node3;
    }

    public static boolean isNodeAfter(Node node, Node node2) {
        if (node instanceof DOMOrder && node2 instanceof DOMOrder) {
            boolean bl = ((DOMOrder)((Object)node)).getUid() <= ((DOMOrder)((Object)node2)).getUid();
            return bl;
        }
        return DOMHelper.isNodeAfter(node, node2);
    }

    public void checkNode(Node node) throws TransformerException {
    }

    public Document getDocument() {
        return this.m_doc;
    }

    @Override
    public Element getElementByID(String string, Document document) {
        return document.getElementById(string);
    }

    @Override
    public String getLocalNameOfNode(Node object) {
        String string = object.getLocalName();
        object = string == null ? super.getLocalNameOfNode((Node)object) : string;
        return object;
    }

    @Override
    public String getNamespaceOfNode(Node node) {
        return node.getNamespaceURI();
    }

    public void parse(InputSource inputSource) throws TransformerException {
        try {
            Object object = DocumentBuilderFactory.newInstance();
            ((DocumentBuilderFactory)object).setNamespaceAware(true);
            ((DocumentBuilderFactory)object).setValidating(true);
            object = ((DocumentBuilderFactory)object).newDocumentBuilder();
            DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
            ((DocumentBuilder)object).setErrorHandler(defaultErrorHandler);
            this.setDocument(((DocumentBuilder)object).parse(inputSource));
            return;
        }
        catch (IOException iOException) {
            throw new TransformerException(iOException);
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new TransformerException(parserConfigurationException);
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    public void setDocument(Document document) {
        this.m_doc = document;
    }

    public boolean supportsSAX() {
        return true;
    }
}

