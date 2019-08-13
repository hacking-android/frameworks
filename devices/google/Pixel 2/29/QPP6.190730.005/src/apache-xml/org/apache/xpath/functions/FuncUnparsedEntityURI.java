/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncUnparsedEntityURI
extends FunctionOneArg {
    static final long serialVersionUID = 845309759097448178L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string = this.m_arg0.execute((XPathContext)object).str();
        object = ((XPathContext)object).getDTM(((XPathContext)object).getCurrentNode());
        object.getDocument();
        return new XString(object.getUnparsedEntityURI(string));
    }
}

