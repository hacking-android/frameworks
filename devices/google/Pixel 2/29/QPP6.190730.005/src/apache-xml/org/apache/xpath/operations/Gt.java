/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Gt
extends Operation {
    static final long serialVersionUID = 8927078751014375950L;

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        xObject = xObject.greaterThan(xObject2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return xObject;
    }
}

