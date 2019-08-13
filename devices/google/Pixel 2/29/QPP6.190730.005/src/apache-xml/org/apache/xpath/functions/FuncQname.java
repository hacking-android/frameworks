/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionDef1Arg;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncQname
extends FunctionDef1Arg {
    static final long serialVersionUID = -1532307875532617380L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n = this.getArg0AsNode((XPathContext)object);
        object = -1 != n ? ((object = ((XPathContext)object).getDTM(n).getNodeNameX(n)) == null ? XString.EMPTYSTRING : new XString((String)object)) : XString.EMPTYSTRING;
        return object;
    }
}

