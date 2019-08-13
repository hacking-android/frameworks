/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.XML11Char;
import org.apache.xpath.XPathContext;
import org.xml.sax.SAXException;

public class ElemPI
extends ElemTemplateElement {
    static final long serialVersionUID = 5621976448020889825L;
    private AVT m_name_atv = null;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        int n = elemTemplateElement.getXSLToken();
        if (n != 9 && n != 17 && n != 28 && n != 30 && n != 42 && n != 50 && n != 78) {
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
                            break block0;
                        }
                        case 72: 
                        case 73: 
                        case 74: 
                        case 75: 
                    }
                }
                case 35: 
                case 36: 
                case 37: 
            }
        }
        return super.appendChild(elemTemplateElement);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        Vector vector = stylesheetRoot.getComposeState().getVariableNames();
        AVT aVT = this.m_name_atv;
        if (aVT != null) {
            aVT.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        }
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        Object object = transformerImpl.getXPathContext();
        int n = ((XPathContext)object).getCurrentNode();
        Object object2 = this.m_name_atv;
        object = object2 == null ? null : ((AVT)object2).evaluate((XPathContext)object, n, this);
        if (object == null) {
            return;
        }
        if (((String)object).equalsIgnoreCase("xml")) {
            transformerImpl.getMsgMgr().warn(this, "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", new Object[]{"name", object});
            return;
        }
        if (!this.m_name_atv.isSimple() && !XML11Char.isXML11ValidNCName((String)object)) {
            transformerImpl.getMsgMgr().warn(this, "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", new Object[]{"name", object});
            return;
        }
        object2 = transformerImpl.transformToString(this);
        try {
            transformerImpl.getResultTreeHandler().processingInstruction((String)object, (String)object2);
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    public AVT getName() {
        return this.m_name_atv;
    }

    @Override
    public String getNodeName() {
        return "processing-instruction";
    }

    @Override
    public int getXSLToken() {
        return 58;
    }

    public void setName(AVT aVT) {
        this.m_name_atv = aVT;
    }
}

