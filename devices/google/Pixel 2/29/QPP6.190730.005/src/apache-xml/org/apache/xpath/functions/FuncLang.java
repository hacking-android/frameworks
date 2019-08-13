/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncLang
extends FunctionOneArg {
    static final long serialVersionUID = -7868705139354872185L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n;
        String string = this.m_arg0.execute((XPathContext)object).str();
        int n2 = ((XPathContext)object).getCurrentNode();
        int n3 = 0;
        int n4 = 0;
        object = ((XPathContext)object).getDTM(n2);
        do {
            block3 : {
                block5 : {
                    block4 : {
                        n = n3;
                        if (-1 == n2) break;
                        if (1 != object.getNodeType(n2) || -1 == (n = object.getAttributeNode(n2, "http://www.w3.org/XML/1998/namespace", "lang"))) break block3;
                        object = object.getNodeValue(n);
                        n = n3;
                        if (!((String)object).toLowerCase().startsWith(string.toLowerCase())) break;
                        n = string.length();
                        if (((String)object).length() == n) break block4;
                        n2 = n4;
                        if (((String)object).charAt(n) != '-') break block5;
                    }
                    n2 = 1;
                }
                n = n2;
                break;
            }
            n2 = object.getParent(n2);
        } while (true);
        object = n != 0 ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return object;
    }
}

