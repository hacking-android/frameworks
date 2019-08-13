/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncCount
extends FunctionOneArg {
    static final long serialVersionUID = -7116225100474153751L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        object = this.m_arg0.asIterator((XPathContext)object, ((XPathContext)object).getCurrentNode());
        int n = object.getLength();
        object.detach();
        return new XNumber(n);
    }
}

