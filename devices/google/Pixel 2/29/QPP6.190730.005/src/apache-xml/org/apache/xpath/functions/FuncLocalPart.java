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

public class FuncLocalPart
extends FunctionDef1Arg {
    static final long serialVersionUID = 7591798770325814746L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n = this.getArg0AsNode((XPathContext)object);
        if (-1 == n) {
            return XString.EMPTYSTRING;
        }
        object = ((XPathContext)object).getDTM(n);
        object = n != -1 ? object.getLocalName(n) : "";
        if (!((String)object).startsWith("#") && !((String)object).equals("xmlns")) {
            return new XString((String)object);
        }
        return XString.EMPTYSTRING;
    }
}

