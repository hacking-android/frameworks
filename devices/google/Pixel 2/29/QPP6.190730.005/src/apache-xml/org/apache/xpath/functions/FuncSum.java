/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncSum
extends FunctionOneArg {
    static final long serialVersionUID = -2719049259574677519L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n;
        object = this.m_arg0.asIterator((XPathContext)object, ((XPathContext)object).getCurrentNode());
        double d = 0.0;
        while (-1 != (n = object.nextNode())) {
            XMLString xMLString = object.getDTM(n).getStringValue(n);
            double d2 = d;
            if (xMLString != null) {
                d2 = d + xMLString.toDouble();
            }
            d = d2;
        }
        object.detach();
        return new XNumber(d);
    }
}

