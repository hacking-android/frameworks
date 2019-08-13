/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.serialize.SerializerUtils;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xalan.transformer.TreeWalker2Result;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.DTMTreeWalker;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;
import org.xml.sax.SAXException;

public class ElemCopyOf
extends ElemTemplateElement {
    static final long serialVersionUID = -7433828829497411127L;
    public XPath m_selectExpression = null;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        return null;
    }

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        if (bl) {
            this.m_selectExpression.getExpression().callVisitors(this.m_selectExpression, xSLTVisitor);
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot object) throws TransformerException {
        super.compose((StylesheetRoot)object);
        object = ((StylesheetRoot)object).getComposeState();
        this.m_selectExpression.fixupVariables(((StylesheetRoot.ComposeState)object).getVariableNames(), ((StylesheetRoot.ComposeState)object).getGlobalsSize());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void execute(TransformerImpl object) throws TransformerException {
        int n;
        XPathContext xPathContext;
        TreeWalker2Result treeWalker2Result;
        SerializationHandler serializationHandler;
        Object object2;
        try {
            xPathContext = ((TransformerImpl)object).getXPathContext();
            n = xPathContext.getCurrentNode();
            object2 = this.m_selectExpression.execute(xPathContext, n, (PrefixResolver)this);
            serializationHandler = ((TransformerImpl)object).getSerializationHandler();
            if (object2 == null) return;
            n = ((XObject)object2).getType();
            if (n != 1 && n != 2 && n != 3) {
                if (n != 4) {
                    if (n != 5) {
                        object = ((XObject)object2).str();
                        serializationHandler.characters(((String)object).toCharArray(), 0, ((String)object).length());
                        return;
                    }
                    SerializerUtils.outputResultTreeFragment(serializationHandler, (XObject)object2, ((TransformerImpl)object).getXPathContext());
                    return;
                }
            } else {
                object = ((XObject)object2).str();
                serializationHandler.characters(((String)object).toCharArray(), 0, ((String)object).length());
                return;
            }
            object2 = ((XObject)object2).iter();
            treeWalker2Result = new TreeWalker2Result((TransformerImpl)object, serializationHandler);
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
        block2 : while (-1 != (n = object2.nextNode())) {
            object = xPathContext.getDTMManager().getDTM(n);
            short s = object.getNodeType(n);
            if (s == 9) {
                n = object.getFirstChild(n);
                do {
                    if (n == -1) continue block2;
                    ((DTMTreeWalker)treeWalker2Result).traverse(n);
                    n = object.getNextSibling(n);
                } while (true);
            }
            if (s == 2) {
                SerializerUtils.addAttribute(serializationHandler, n);
                continue;
            }
            ((DTMTreeWalker)treeWalker2Result).traverse(n);
        }
    }

    @Override
    public String getNodeName() {
        return "copy-of";
    }

    public XPath getSelect() {
        return this.m_selectExpression;
    }

    @Override
    public int getXSLToken() {
        return 74;
    }

    public void setSelect(XPath xPath) {
        this.m_selectExpression = xPath;
    }
}

