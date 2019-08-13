/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.xml.sax.SAXException;

public class ElemComment
extends ElemTemplateElement {
    static final long serialVersionUID = -8813199122875770142L;

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
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        try {
            String string = transformerImpl.transformToString(this);
            transformerImpl.getResultTreeHandler().comment(string);
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    @Override
    public String getNodeName() {
        return "comment";
    }

    @Override
    public int getXSLToken() {
        return 59;
    }
}

