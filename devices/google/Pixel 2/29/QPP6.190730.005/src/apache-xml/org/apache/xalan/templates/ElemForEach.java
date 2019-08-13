/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemSort;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.NodeSorter;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.IntStack;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.SourceTreeManager;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;

public class ElemForEach
extends ElemTemplateElement
implements ExpressionOwner {
    static final boolean DEBUG = false;
    static final long serialVersionUID = 6018140636363583690L;
    public boolean m_doc_cache_off = false;
    protected Expression m_selectExpression = null;
    protected Vector m_sortElems = null;
    protected XPath m_xpath = null;

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.m_xpath = null;
    }

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        if (64 == elemTemplateElement.getXSLToken()) {
            this.setSortElem((ElemSort)elemTemplateElement);
            return elemTemplateElement;
        }
        return super.appendChild(elemTemplateElement);
    }

    @Override
    public void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        Expression expression;
        if (bl && (expression = this.m_selectExpression) != null) {
            expression.callVisitors(this, xSLTVisitor);
        }
        int n = this.getSortElemCount();
        for (int i = 0; i < n; ++i) {
            this.getSortElem(i).callVisitors(xSLTVisitor);
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        int n = this.getSortElemCount();
        for (int i = 0; i < n; ++i) {
            this.getSortElem(i).compose(stylesheetRoot);
        }
        Vector vector = stylesheetRoot.getComposeState().getVariableNames();
        Expression expression = this.m_selectExpression;
        if (expression != null) {
            expression.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        } else {
            this.m_selectExpression = this.getStylesheetRoot().m_selectDefault.getExpression();
        }
    }

    @Override
    public void endCompose(StylesheetRoot stylesheetRoot) throws TransformerException {
        int n = this.getSortElemCount();
        for (int i = 0; i < n; ++i) {
            this.getSortElem(i).endCompose(stylesheetRoot);
        }
        super.endCompose(stylesheetRoot);
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        transformerImpl.pushCurrentTemplateRuleIsNull(true);
        try {
            this.transformSelectedNodes(transformerImpl);
            return;
        }
        finally {
            transformerImpl.popCurrentTemplateRuleIsNull();
        }
    }

    @Override
    public Expression getExpression() {
        return this.m_selectExpression;
    }

    @Override
    public String getNodeName() {
        return "for-each";
    }

    public Expression getSelect() {
        return this.m_selectExpression;
    }

    public ElemSort getSortElem(int n) {
        return (ElemSort)this.m_sortElems.elementAt(n);
    }

    public int getSortElemCount() {
        Vector vector = this.m_sortElems;
        int n = vector == null ? 0 : vector.size();
        return n;
    }

    protected ElemTemplateElement getTemplateMatch() {
        return this;
    }

    @Override
    public int getXSLToken() {
        return 28;
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_selectExpression = expression;
    }

    public void setSelect(XPath xPath) {
        this.m_selectExpression = xPath.getExpression();
        this.m_xpath = xPath;
    }

    public void setSortElem(ElemSort elemSort) {
        if (this.m_sortElems == null) {
            this.m_sortElems = new Vector();
        }
        this.m_sortElems.addElement(elemSort);
    }

    public DTMIterator sortNodes(XPathContext xPathContext, Vector vector, DTMIterator dTMIterator) throws TransformerException {
        NodeSorter nodeSorter = new NodeSorter(xPathContext);
        dTMIterator.setShouldCacheNodes(true);
        dTMIterator.runTo(-1);
        xPathContext.pushContextNodeList(dTMIterator);
        try {
            nodeSorter.sort(dTMIterator, vector, xPathContext);
            dTMIterator.setCurrentPos(0);
            return dTMIterator;
        }
        finally {
            xPathContext.popContextNodeList();
        }
    }

    public void transformSelectedNodes(TransformerImpl transformerImpl) throws TransformerException {
        Object object;
        int n;
        Serializable serializable;
        XPathContext xPathContext;
        DTMIterator dTMIterator;
        block30 : {
            xPathContext = transformerImpl.getXPathContext();
            n = xPathContext.getCurrentNode();
            object = this.m_selectExpression.asIterator(xPathContext, n);
            dTMIterator = object;
            if (this.m_sortElems == null) {
                serializable = null;
                break block30;
            }
            dTMIterator = object;
            serializable = transformerImpl.processSortKeys(this, n);
        }
        DTMIterator dTMIterator2 = object;
        if (serializable != null) {
            dTMIterator = object;
            dTMIterator2 = this.sortNodes(xPathContext, (Vector)serializable, (DTMIterator)object);
        }
        dTMIterator = dTMIterator2;
        xPathContext.pushCurrentNode(-1);
        dTMIterator = dTMIterator2;
        IntStack intStack = xPathContext.getCurrentNodeStack();
        dTMIterator = dTMIterator2;
        xPathContext.pushCurrentExpressionNode(-1);
        dTMIterator = dTMIterator2;
        IntStack intStack2 = xPathContext.getCurrentExpressionNodeStack();
        dTMIterator = dTMIterator2;
        xPathContext.pushSAXLocatorNull();
        dTMIterator = dTMIterator2;
        xPathContext.pushContextNodeList(dTMIterator2);
        dTMIterator = dTMIterator2;
        transformerImpl.pushElemTemplateElement(null);
        dTMIterator = dTMIterator2;
        try {
            object = xPathContext.getDTM(n);
        }
        catch (Throwable throwable) {
            xPathContext.popSAXLocator();
            xPathContext.popContextNodeList();
            transformerImpl.popElemTemplateElement();
            xPathContext.popCurrentExpressionNode();
            xPathContext.popCurrentNode();
            dTMIterator.detach();
            throw throwable;
        }
        int n2 = n & -65536;
        do {
            block31 : {
                dTMIterator = dTMIterator2;
                int n3 = dTMIterator2.nextNode();
                if (-1 == n3) break;
                dTMIterator = dTMIterator2;
                intStack.setTop(n3);
                dTMIterator = dTMIterator2;
                intStack2.setTop(n3);
                n = n2;
                if ((n3 & -65536) != n2) {
                    dTMIterator = dTMIterator2;
                    object = xPathContext.getDTM(n3);
                    n = n3 & -65536;
                }
                dTMIterator = dTMIterator2;
                object.getNodeType(n3);
                dTMIterator = dTMIterator2;
                serializable = this.m_firstChild;
                while (serializable != null) {
                    dTMIterator = dTMIterator2;
                    xPathContext.setSAXLocator((SourceLocator)((Object)serializable));
                    dTMIterator = dTMIterator2;
                    transformerImpl.setCurrentElement((ElemTemplateElement)serializable);
                    dTMIterator = dTMIterator2;
                    ((ElemTemplateElement)serializable).execute(transformerImpl);
                    dTMIterator = dTMIterator2;
                    serializable = ((ElemTemplateElement)serializable).m_nextSibling;
                }
                dTMIterator = dTMIterator2;
                if (!this.m_doc_cache_off) break block31;
                dTMIterator = dTMIterator2;
                xPathContext.getSourceTreeManager().removeDocumentFromCache(object.getDocument());
                dTMIterator = dTMIterator2;
                xPathContext.release((DTM)object, false);
            }
            n2 = n;
        } while (true);
        xPathContext.popSAXLocator();
        xPathContext.popContextNodeList();
        transformerImpl.popElemTemplateElement();
        xPathContext.popCurrentExpressionNode();
        xPathContext.popCurrentNode();
        dTMIterator2.detach();
        return;
    }
}

