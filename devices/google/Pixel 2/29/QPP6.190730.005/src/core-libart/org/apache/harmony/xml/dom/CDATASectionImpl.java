/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

public final class CDATASectionImpl
extends TextImpl
implements CDATASection {
    public CDATASectionImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl, string);
    }

    @Override
    public String getNodeName() {
        return "#cdata-section";
    }

    @Override
    public short getNodeType() {
        return 4;
    }

    public boolean needsSplitting() {
        boolean bl = this.buffer.indexOf("]]>") != -1;
        return bl;
    }

    public TextImpl replaceWithText() {
        TextImpl textImpl = new TextImpl(this.document, this.getData());
        this.parent.insertBefore(textImpl, this);
        this.parent.removeChild(this);
        return textImpl;
    }

    public void split() {
        if (!this.needsSplitting()) {
            return;
        }
        Object object = this.getParentNode();
        String[] arrstring = this.getData().split("\\]\\]>");
        Object object2 = this.document;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append(arrstring[0]);
        ((StringBuilder)object3).append("]]");
        object.insertBefore(new CDATASectionImpl((DocumentImpl)object2, ((StringBuilder)object3).toString()), this);
        for (int i = 1; i < arrstring.length - 1; ++i) {
            object3 = this.document;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(">");
            ((StringBuilder)object2).append(arrstring[i]);
            ((StringBuilder)object2).append("]]");
            object.insertBefore(new CDATASectionImpl((DocumentImpl)object3, ((StringBuilder)object2).toString()), this);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(">");
        ((StringBuilder)object).append(arrstring[arrstring.length - 1]);
        this.setData(((StringBuilder)object).toString());
    }
}

