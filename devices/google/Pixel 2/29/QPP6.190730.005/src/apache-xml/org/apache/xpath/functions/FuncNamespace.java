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

public class FuncNamespace
extends FunctionDef1Arg {
    static final long serialVersionUID = -4695674566722321237L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        block2 : {
            block5 : {
                block6 : {
                    block4 : {
                        short s;
                        int n;
                        DTM dTM;
                        block3 : {
                            n = this.getArg0AsNode((XPathContext)object);
                            if (n == -1) break block2;
                            dTM = ((XPathContext)object).getDTM(n);
                            s = dTM.getNodeType(n);
                            if (s != 1) break block3;
                            object = dTM.getNamespaceURI(n);
                            break block4;
                        }
                        if (s != 2) break block5;
                        object = dTM.getNodeName(n);
                        if (((String)object).startsWith("xmlns:") || ((String)object).equals("xmlns")) break block6;
                        object = dTM.getNamespaceURI(n);
                    }
                    object = object == null ? XString.EMPTYSTRING : new XString((String)object);
                    return object;
                }
                return XString.EMPTYSTRING;
            }
            return XString.EMPTYSTRING;
        }
        return XString.EMPTYSTRING;
    }
}

