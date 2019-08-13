/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Plus
extends Operation {
    static final long serialVersionUID = -4492072861616504256L;

    @Override
    public double num(XPathContext xPathContext) throws TransformerException {
        return this.m_right.num(xPathContext) + this.m_left.num(xPathContext);
    }

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        return new XNumber(xObject.num() + xObject2.num());
    }
}

