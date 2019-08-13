/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.OneStepIteratorForward;

public class KeyIterator
extends OneStepIteratorForward {
    static final long serialVersionUID = -1349109910100249661L;
    private Vector m_keyDeclarations;
    private QName m_name;

    KeyIterator(QName qName, Vector vector) {
        super(16);
        this.m_keyDeclarations = vector;
        this.m_name = qName;
    }

    @Override
    public short acceptNode(int n) {
        int n2;
        boolean bl = false;
        boolean bl2 = false;
        Serializable serializable = (KeyIterator)this.m_lpi;
        XPathContext xPathContext = ((LocPathIterator)serializable).getXPathContext();
        Vector vector = ((KeyIterator)serializable).getKeyDeclarations();
        serializable = ((KeyIterator)serializable).getName();
        try {
            n2 = vector.size();
        }
        catch (TransformerException transformerException) {
            // empty catch block
        }
        for (int i = 0; i < n2; ++i) {
            block8 : {
                bl = bl2;
                KeyDeclaration keyDeclaration = (KeyDeclaration)vector.elementAt(i);
                bl = bl2;
                if (!keyDeclaration.getName().equals(serializable)) continue;
                boolean bl3 = true;
                bl2 = true;
                bl = bl3;
                double d = keyDeclaration.getMatch().getMatchScore(xPathContext, n);
                bl = bl3;
                keyDeclaration.getMatch();
                if (d != Double.NEGATIVE_INFINITY) break block8;
                continue;
            }
            return 1;
        }
        bl = bl2;
        if (bl) {
            return 2;
        }
        throw new RuntimeException(XSLMessages.createMessage("ER_NO_XSLKEY_DECLARATION", new Object[]{((QName)serializable).getLocalName()}));
    }

    public Vector getKeyDeclarations() {
        return this.m_keyDeclarations;
    }

    public QName getName() {
        return this.m_name;
    }
}

