/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XRTreeFrag;
import org.apache.xpath.objects.XString;

public class XRTreeFragSelectWrapper
extends XRTreeFrag
implements Cloneable {
    static final long serialVersionUID = -6526177905590461251L;

    public XRTreeFragSelectWrapper(Expression expression) {
        super(expression);
    }

    @Override
    public DTMIterator asNodeIterator() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }

    @Override
    public void detach() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        object = ((Expression)this.m_obj).execute((XPathContext)object);
        ((XObject)object).allowDetachToRelease(this.m_allowRelease);
        if (((XObject)object).getType() == 3) {
            return object;
        }
        return new XString(((XObject)object).str());
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        ((Expression)this.m_obj).fixupVariables(vector, n);
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public double num() throws TransformerException {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }

    @Override
    public int rtf() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }

    @Override
    public String str() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }

    @Override
    public XMLString xstr() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER", null));
    }
}

