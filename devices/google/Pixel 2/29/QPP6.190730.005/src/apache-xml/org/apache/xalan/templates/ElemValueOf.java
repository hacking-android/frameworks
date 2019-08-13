/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ElemValueOf
extends ElemTemplateElement {
    static final long serialVersionUID = 3490728458007586786L;
    private boolean m_disableOutputEscaping = false;
    private boolean m_isDot = false;
    private XPath m_selectExpression = null;

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
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        Vector vector = stylesheetRoot.getComposeState().getVariableNames();
        XPath xPath = this.m_selectExpression;
        if (xPath != null) {
            xPath.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        }
    }

    @Override
    public void execute(TransformerImpl object) throws TransformerException {
        XPathContext xPathContext;
        block10 : {
            xPathContext = ((TransformerImpl)object).getXPathContext();
            object = ((TransformerImpl)object).getResultTreeHandler();
            xPathContext.pushNamespaceContext(this);
            int n = xPathContext.getCurrentNode();
            xPathContext.pushCurrentNodeAndExpression(n, n);
            boolean bl = this.m_disableOutputEscaping;
            if (!bl) break block10;
            object.processingInstruction("javax.xml.transform.disable-output-escaping", "");
        }
        try {
            this.m_selectExpression.getExpression().executeCharsToContentHandler(xPathContext, (ContentHandler)object);
        }
        catch (Throwable throwable) {
            try {
                if (this.m_disableOutputEscaping) {
                    object.processingInstruction("javax.xml.transform.enable-output-escaping", "");
                }
                xPathContext.popNamespaceContext();
                xPathContext.popCurrentNodeAndExpression();
                throw throwable;
            }
            catch (RuntimeException runtimeException) {
                TransformerException transformerException = new TransformerException(runtimeException);
                transformerException.setLocator(this);
                throw transformerException;
            }
            catch (SAXException sAXException) {
                throw new TransformerException(sAXException);
            }
        }
        if (this.m_disableOutputEscaping) {
            object.processingInstruction("javax.xml.transform.enable-output-escaping", "");
        }
        xPathContext.popNamespaceContext();
        xPathContext.popCurrentNodeAndExpression();
    }

    public boolean getDisableOutputEscaping() {
        return this.m_disableOutputEscaping;
    }

    @Override
    public String getNodeName() {
        return "value-of";
    }

    public XPath getSelect() {
        return this.m_selectExpression;
    }

    @Override
    public int getXSLToken() {
        return 30;
    }

    public void setDisableOutputEscaping(boolean bl) {
        this.m_disableOutputEscaping = bl;
    }

    public void setSelect(XPath xPath) {
        if (xPath != null) {
            String string = xPath.getPatternString();
            boolean bl = string != null && string.equals(".");
            this.m_isDot = bl;
        }
        this.m_selectExpression = xPath;
    }
}

