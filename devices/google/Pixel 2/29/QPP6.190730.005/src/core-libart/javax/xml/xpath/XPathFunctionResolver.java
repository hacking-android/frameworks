/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;

public interface XPathFunctionResolver {
    public XPathFunction resolveFunction(QName var1, int var2);
}

