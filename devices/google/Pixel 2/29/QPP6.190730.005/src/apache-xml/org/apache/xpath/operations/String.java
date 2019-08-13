/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.apache.xpath.operations.UnaryOperation;

public class String
extends UnaryOperation {
    static final long serialVersionUID = 2973374377453022888L;

    @Override
    public XObject operate(XObject xObject) throws TransformerException {
        return (XString)xObject.xstr();
    }
}

