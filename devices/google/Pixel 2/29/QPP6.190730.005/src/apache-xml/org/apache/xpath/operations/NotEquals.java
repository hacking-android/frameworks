/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class NotEquals
extends Operation {
    static final long serialVersionUID = -7869072863070586900L;

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        xObject = xObject.notEquals(xObject2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return xObject;
    }
}

