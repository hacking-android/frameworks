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

public class FuncDoclocation
extends FunctionDef1Arg {
    static final long serialVersionUID = 7469213946343568769L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string;
        int n = this.getArg0AsNode((XPathContext)object);
        String string2 = string = null;
        if (-1 != n) {
            object = ((XPathContext)object).getDTM(n);
            int n2 = n;
            if (11 == object.getNodeType(n)) {
                n2 = object.getFirstChild(n);
            }
            string2 = string;
            if (-1 != n2) {
                string2 = object.getDocumentBaseURI();
            }
        }
        if (string2 == null) {
            string2 = "";
        }
        return new XString(string2);
    }
}

