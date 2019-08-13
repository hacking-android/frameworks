/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.InputSource;

public interface XPathExpression {
    public Object evaluate(Object var1, QName var2) throws XPathExpressionException;

    public Object evaluate(InputSource var1, QName var2) throws XPathExpressionException;

    public String evaluate(Object var1) throws XPathExpressionException;

    public String evaluate(InputSource var1) throws XPathExpressionException;
}

