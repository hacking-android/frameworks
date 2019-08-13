/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemElement;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.XML11Char;
import org.xml.sax.SAXException;

public class ElemAttribute
extends ElemElement {
    static final long serialVersionUID = 8817220961566919187L;

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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void constructNode(String string, String string2, String string3, TransformerImpl object) throws TransformerException {
        if (string == null) return;
        if (string.length() <= 0) return;
        SerializationHandler serializationHandler = ((TransformerImpl)object).getSerializationHandler();
        object = ((TransformerImpl)object).transformToString(this);
        try {
            String string4 = QName.getLocalPart(string);
            if (string2 != null && string2.length() > 0) {
                serializationHandler.addAttribute(string3, string4, string, "CDATA", (String)object, true);
                return;
            }
            serializationHandler.addAttribute("", string4, string, "CDATA", (String)object, true);
            return;
        }
        catch (SAXException sAXException) {
            return;
        }
    }

    @Override
    public String getNodeName() {
        return "attribute";
    }

    @Override
    public int getXSLToken() {
        return 48;
    }

    @Override
    protected String resolvePrefix(SerializationHandler serializationHandler, String string, String string2) throws TransformerException {
        String string3;
        block2 : {
            block4 : {
                block3 : {
                    string3 = string;
                    if (string == null) break block2;
                    if (string.length() == 0) break block3;
                    string3 = string;
                    if (!string.equals("xmlns")) break block2;
                }
                if ((string = serializationHandler.getPrefix(string2)) == null || string.length() == 0) break block4;
                string3 = string;
                if (!string.equals("xmlns")) break block2;
            }
            string3 = string2.length() > 0 ? serializationHandler.getNamespaceMappings().generateNextPrefix() : "";
        }
        return string3;
    }

    @Override
    public void setName(AVT aVT) {
        if (aVT.isSimple() && aVT.getSimpleString().equals("xmlns")) {
            throw new IllegalArgumentException();
        }
        super.setName(aVT);
    }

    protected boolean validateNodeName(String string) {
        if (string == null) {
            return false;
        }
        if (string.equals("xmlns")) {
            return false;
        }
        return XML11Char.isXML11ValidQName(string);
    }
}

