/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xpath.XPath;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class ElemSort
extends ElemTemplateElement {
    static final long serialVersionUID = -4991510257335851938L;
    private AVT m_caseorder_avt = null;
    private AVT m_dataType_avt = null;
    private AVT m_lang_avt = null;
    private AVT m_order_avt = null;
    private XPath m_selectExpression = null;

    @Override
    public Node appendChild(Node node) throws DOMException {
        this.error("ER_CANNOT_ADD", new Object[]{node.getNodeName(), this.getNodeName()});
        return null;
    }

    @Override
    public void compose(StylesheetRoot serializable) throws TransformerException {
        super.compose((StylesheetRoot)serializable);
        StylesheetRoot.ComposeState composeState = serializable.getComposeState();
        serializable = composeState.getVariableNames();
        Serializable serializable2 = this.m_caseorder_avt;
        if (serializable2 != null) {
            ((AVT)serializable2).fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
        if ((serializable2 = this.m_dataType_avt) != null) {
            ((AVT)serializable2).fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
        if ((serializable2 = this.m_lang_avt) != null) {
            ((AVT)serializable2).fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
        if ((serializable2 = this.m_order_avt) != null) {
            ((AVT)serializable2).fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
        if ((serializable2 = this.m_selectExpression) != null) {
            ((XPath)serializable2).fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
    }

    public AVT getCaseOrder() {
        return this.m_caseorder_avt;
    }

    public AVT getDataType() {
        return this.m_dataType_avt;
    }

    public AVT getLang() {
        return this.m_lang_avt;
    }

    @Override
    public String getNodeName() {
        return "sort";
    }

    public AVT getOrder() {
        return this.m_order_avt;
    }

    public XPath getSelect() {
        return this.m_selectExpression;
    }

    @Override
    public int getXSLToken() {
        return 64;
    }

    public void setCaseOrder(AVT aVT) {
        this.m_caseorder_avt = aVT;
    }

    public void setDataType(AVT aVT) {
        this.m_dataType_avt = aVT;
    }

    public void setLang(AVT aVT) {
        this.m_lang_avt = aVT;
    }

    public void setOrder(AVT aVT) {
        this.m_order_avt = aVT;
    }

    public void setSelect(XPath xPath) {
        if (xPath.getPatternString().indexOf("{") < 0) {
            this.m_selectExpression = xPath;
        } else {
            this.error("ER_NO_CURLYBRACE", null);
        }
    }
}

