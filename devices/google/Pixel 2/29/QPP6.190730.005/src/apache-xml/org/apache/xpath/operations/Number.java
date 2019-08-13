/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.UnaryOperation;

public class Number
extends UnaryOperation {
    static final long serialVersionUID = 7196954482871619765L;

    @Override
    public double num(XPathContext xPathContext) throws TransformerException {
        return this.m_right.num(xPathContext);
    }

    @Override
    public XObject operate(XObject xObject) throws TransformerException {
        if (2 == xObject.getType()) {
            return xObject;
        }
        return new XNumber(xObject.num());
    }
}

