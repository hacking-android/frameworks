/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.UnaryOperation;

public class Bool
extends UnaryOperation {
    static final long serialVersionUID = 44705375321914635L;

    @Override
    public boolean bool(XPathContext xPathContext) throws TransformerException {
        return this.m_right.bool(xPathContext);
    }

    @Override
    public XObject operate(XObject xObject) throws TransformerException {
        if (1 == xObject.getType()) {
            return xObject;
        }
        xObject = xObject.bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return xObject;
    }
}

