/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Equals
extends Operation {
    static final long serialVersionUID = -2658315633903426134L;

    @Override
    public boolean bool(XPathContext object) throws TransformerException {
        XObject xObject = this.m_left.execute((XPathContext)object, true);
        object = this.m_right.execute((XPathContext)object, true);
        boolean bl = xObject.equals((XObject)object);
        xObject.detach();
        ((XObject)object).detach();
        return bl;
    }

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        xObject = xObject.equals(xObject2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return xObject;
    }
}

