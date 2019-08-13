/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.CharacterDataImpl;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class TextImpl
extends CharacterDataImpl
implements Text {
    public TextImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl, string);
    }

    private TextImpl firstTextNodeInCurrentRun() {
        short s;
        TextImpl textImpl = this;
        for (Node node = this.getPreviousSibling(); node != null && ((s = node.getNodeType()) == 3 || s == 4); node = node.getPreviousSibling()) {
            textImpl = (TextImpl)node;
        }
        return textImpl;
    }

    private TextImpl nextTextNode() {
        Node node = this.getNextSibling();
        TextImpl textImpl = null;
        if (node == null) {
            return null;
        }
        short s = node.getNodeType();
        if (s == 3 || s == 4) {
            textImpl = (TextImpl)node;
        }
        return textImpl;
    }

    @Override
    public String getNodeName() {
        return "#text";
    }

    @Override
    public short getNodeType() {
        return 3;
    }

    @Override
    public final String getWholeText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextImpl textImpl = this.firstTextNodeInCurrentRun(); textImpl != null; textImpl = textImpl.nextTextNode()) {
            textImpl.appendDataTo(stringBuilder);
        }
        return stringBuilder.toString();
    }

    @Override
    public final boolean isElementContentWhitespace() {
        return false;
    }

    public final TextImpl minimize() {
        if (this.getLength() == 0) {
            this.parent.removeChild(this);
            return null;
        }
        Node node = this.getPreviousSibling();
        if (node != null && node.getNodeType() == 3) {
            node = (TextImpl)node;
            ((TextImpl)node).buffer.append(this.buffer);
            this.parent.removeChild(this);
            return node;
        }
        return this;
    }

    @Override
    public final Text replaceWholeText(String string) throws DOMException {
        Node node = this.getParentNode();
        TextImpl textImpl = null;
        TextImpl textImpl2 = this.firstTextNodeInCurrentRun();
        while (textImpl2 != null) {
            if (textImpl2 == this && string != null && string.length() > 0) {
                this.setData(string);
                textImpl = this;
                textImpl2 = textImpl2.nextTextNode();
                continue;
            }
            TextImpl textImpl3 = textImpl2.nextTextNode();
            node.removeChild(textImpl2);
            textImpl2 = textImpl3;
        }
        return textImpl;
    }

    @Override
    public final Text splitText(int n) throws DOMException {
        TextImpl textImpl = this.document.createTextNode(this.substringData(n, this.getLength() - n));
        this.deleteData(0, n);
        Node node = this.getNextSibling();
        if (node == null) {
            this.getParentNode().appendChild(textImpl);
        } else {
            this.getParentNode().insertBefore(textImpl, node);
        }
        return this;
    }
}

