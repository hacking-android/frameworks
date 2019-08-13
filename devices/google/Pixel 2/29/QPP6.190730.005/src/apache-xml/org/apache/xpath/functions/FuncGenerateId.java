/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionDef1Arg;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncGenerateId
extends FunctionDef1Arg {
    static final long serialVersionUID = 973544842091724273L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n = this.getArg0AsNode((XPathContext)object);
        if (-1 != n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("N");
            ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
            return new XString(((StringBuilder)object).toString());
        }
        return XString.EMPTYSTRING;
    }
}

