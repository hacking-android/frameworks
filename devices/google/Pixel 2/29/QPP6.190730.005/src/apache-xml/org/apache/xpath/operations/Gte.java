/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Gte
extends Operation {
    static final long serialVersionUID = 9142945909906680220L;

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        xObject = xObject.greaterThanOrEqual(xObject2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return xObject;
    }
}

