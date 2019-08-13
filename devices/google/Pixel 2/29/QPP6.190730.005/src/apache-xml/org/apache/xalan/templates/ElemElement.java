/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemUse;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.XML11Char;
import org.apache.xpath.XPathContext;
import org.xml.sax.SAXException;

public class ElemElement
extends ElemUse {
    static final long serialVersionUID = -324619535592435183L;
    protected AVT m_name_avt = null;
    protected AVT m_namespace_avt = null;

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        if (bl) {
            AVT aVT = this.m_name_avt;
            if (aVT != null) {
                aVT.callVisitors(xSLTVisitor);
            }
            if ((aVT = this.m_namespace_avt) != null) {
                aVT.callVisitors(xSLTVisitor);
            }
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot serializable) throws TransformerException {
        super.compose((StylesheetRoot)serializable);
        StylesheetRoot.ComposeState composeState = serializable.getComposeState();
        serializable = composeState.getVariableNames();
        AVT aVT = this.m_name_avt;
        if (aVT != null) {
            aVT.fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
        if ((aVT = this.m_namespace_avt) != null) {
            aVT.fixupVariables((Vector)serializable, composeState.getGlobalsSize());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void constructNode(String string, String string2, String string3, TransformerImpl transformerImpl) throws TransformerException {
        try {
            boolean bl;
            SerializationHandler serializationHandler = transformerImpl.getResultTreeHandler();
            if (string == null) {
                bl = false;
            } else {
                if (string2 != null) {
                    serializationHandler.startPrefixMapping(string2, string3, true);
                }
                serializationHandler.startElement(string3, QName.getLocalPart(string), string);
                super.execute(transformerImpl);
                bl = true;
            }
            transformerImpl.executeChildTemplates((ElemTemplateElement)this, bl);
            if (string != null) {
                serializationHandler.endElement(string3, QName.getLocalPart(string), string);
                if (string2 != null) {
                    serializationHandler.endPrefixMapping(string2);
                }
            }
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        block11 : {
            block12 : {
                block10 : {
                    serializationHandler = transformerImpl.getSerializationHandler();
                    xPathContext = transformerImpl.getXPathContext();
                    n = xPathContext.getCurrentNode();
                    object = this.m_name_avt;
                    string3 = object == null ? null : object.evaluate(xPathContext, n, this);
                    string2 = null;
                    string4 = "";
                    if (string3 == null || this.m_name_avt.isSimple() || XML11Char.isXML11ValidQName(string3)) break block10;
                    transformerImpl.getMsgMgr().warn(this, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"name", string3});
                    object = null;
                    string = string4;
                    break block11;
                }
                object = string3;
                string = string4;
                if (string3 == null) break block11;
                string5 = QName.getPrefixPart(string3);
                object = this.m_namespace_avt;
                if (object == null) break block12;
                string = object.evaluate(xPathContext, n, this);
                if (string != null && (string5 == null || string5.length() <= 0 || string.length() != 0)) {
                    string2 = this.resolvePrefix(serializationHandler, string5, string);
                    if (string2 == null) {
                        string2 = "";
                    }
                    if (string2.length() > 0) {
                        object = new StringBuilder();
                        object.append(string2);
                        object.append(":");
                        object.append(QName.getLocalPart(string3));
                        object = object.toString();
                    } else {
                        object = QName.getLocalPart(string3);
                    }
                } else {
                    transformerImpl.getMsgMgr().error(this, "ER_NULL_URI_NAMESPACE");
                    object = string3;
                    string2 = string5;
                }
                break block11;
            }
            string = string4;
            try {
                string2 = this.getNamespaceForPrefix(string5);
                if (string2 != null) ** GOTO lbl-1000
                string = string2;
                if (string5.length() == 0) {
                    string = "";
                    object = string3;
                } else lbl-1000: // 2 sources:
                {
                    object = string3;
                    string = string2;
                    if (string2 == null) {
                        string = string2;
                        transformerImpl.getMsgMgr().warn(this, "WG_COULD_NOT_RESOLVE_PREFIX", new Object[]{string5});
                        object = null;
                        string = string2;
                    }
                }
                string2 = string5;
            }
            catch (Exception exception) {
                transformerImpl.getMsgMgr().warn(this, "WG_COULD_NOT_RESOLVE_PREFIX", new Object[]{string5});
                object = null;
                string2 = string5;
            }
        }
        this.constructNode((String)object, string2, string, transformerImpl);
    }

    public AVT getName() {
        return this.m_name_avt;
    }

    public AVT getNamespace() {
        return this.m_namespace_avt;
    }

    @Override
    public String getNodeName() {
        return "element";
    }

    @Override
    public int getXSLToken() {
        return 46;
    }

    protected String resolvePrefix(SerializationHandler serializationHandler, String string, String string2) throws TransformerException {
        return string;
    }

    public void setName(AVT aVT) {
        this.m_name_avt = aVT;
    }

    public void setNamespace(AVT aVT) {
        this.m_namespace_avt = aVT;
    }
}

