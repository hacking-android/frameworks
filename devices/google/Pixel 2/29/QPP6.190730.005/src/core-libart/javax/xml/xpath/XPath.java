/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import org.xml.sax.InputSource;

public interface XPath {
    public XPathExpression compile(String var1) throws XPathExpressionException;

    public Object evaluate(String var1, Object var2, QName var3) throws XPathExpressionException;

    public Object evaluate(String var1, InputSource var2, QName var3) throws XPathExpressionException;

    public String evaluate(String var1, Object var2) throws XPathExpressionException;

    public String evaluate(String var1, InputSource var2) throws XPathExpressionException;

    public NamespaceContext getNamespaceContext();

    public XPathFunctionResolver getXPathFunctionResolver();

    public XPathVariableResolver getXPathVariableResolver();

    public void reset();

    public void setNamespaceContext(NamespaceContext var1);

    public void setXPathFunctionResolver(XPathFunctionResolver var1);

    public void setXPathVariableResolver(XPathVariableResolver var1);
}

