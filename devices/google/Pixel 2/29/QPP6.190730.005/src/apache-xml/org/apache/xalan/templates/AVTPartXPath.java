/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVTPart;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathFactory;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.compiler.XPathParser;
import org.apache.xpath.objects.XObject;

public class AVTPartXPath
extends AVTPart {
    static final long serialVersionUID = -4460373807550527675L;
    private XPath m_xpath;

    public AVTPartXPath(String string, PrefixResolver prefixResolver, XPathParser xPathParser, XPathFactory xPathFactory, XPathContext xPathContext) throws TransformerException {
        this.m_xpath = new XPath(string, null, prefixResolver, 0, xPathContext.getErrorListener());
    }

    public AVTPartXPath(XPath xPath) {
        this.m_xpath = xPath;
    }

    @Override
    public void callVisitors(XSLTVisitor xSLTVisitor) {
        this.m_xpath.getExpression().callVisitors(this.m_xpath, xSLTVisitor);
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        return this.m_xpath.getExpression().canTraverseOutsideSubtree();
    }

    @Override
    public void evaluate(XPathContext object, FastStringBuffer fastStringBuffer, int n, PrefixResolver prefixResolver) throws TransformerException {
        if ((object = this.m_xpath.execute((XPathContext)object, n, prefixResolver)) != null) {
            ((XObject)object).appendToFsb(fastStringBuffer);
        }
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        this.m_xpath.fixupVariables(vector, n);
    }

    @Override
    public String getSimpleString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.m_xpath.getPatternString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

