/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Quo
extends Operation {
    static final long serialVersionUID = 693765299196169905L;

    @Override
    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        return new XNumber((int)(xObject.num() / xObject2.num()));
    }
}

