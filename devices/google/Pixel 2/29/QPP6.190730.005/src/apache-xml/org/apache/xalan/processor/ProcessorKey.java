/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.util.ArrayList;
import javax.xml.transform.SourceLocator;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTAttributeDef;
import org.apache.xalan.processor.XSLTElementDef;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xml.utils.SAXSourceLocator;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorKey
extends XSLTElementProcessor {
    static final long serialVersionUID = 4285205417566822979L;

    ProcessorKey() {
    }

    @Override
    void setPropertiesFromAttributes(StylesheetHandler stylesheetHandler, String string, Attributes arrxSLTAttributeDef, ElemTemplateElement elemTemplateElement) throws SAXException {
        XSLTElementDef object2 = this.getElemDef();
        ArrayList<XSLTAttributeDef> arrayList = new ArrayList<XSLTAttributeDef>();
        int n = arrxSLTAttributeDef.getLength();
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence;
            String string2 = arrxSLTAttributeDef.getURI(i);
            XSLTAttributeDef xSLTAttributeDef = object2.getAttributeDef(string2, (String)(charSequence = arrxSLTAttributeDef.getLocalName(i)));
            if (xSLTAttributeDef == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(arrxSLTAttributeDef.getQName(i));
                ((StringBuilder)charSequence).append("attribute is not allowed on the ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" element!");
                stylesheetHandler.error(((StringBuilder)charSequence).toString(), null);
                continue;
            }
            if (arrxSLTAttributeDef.getValue(i).indexOf("key(") >= 0) {
                stylesheetHandler.error(XSLMessages.createMessage("ER_INVALID_KEY_CALL", null), null);
            }
            arrayList.add(xSLTAttributeDef);
            xSLTAttributeDef.setAttrValue(stylesheetHandler, string2, (String)charSequence, arrxSLTAttributeDef.getQName(i), arrxSLTAttributeDef.getValue(i), elemTemplateElement);
        }
        for (XSLTAttributeDef xSLTAttributeDef : object2.getAttributes()) {
            if (xSLTAttributeDef.getDefault() != null && !arrayList.contains(xSLTAttributeDef)) {
                xSLTAttributeDef.setDefAttrValue(stylesheetHandler, elemTemplateElement);
            }
            if (!xSLTAttributeDef.getRequired() || arrayList.contains(xSLTAttributeDef)) continue;
            stylesheetHandler.error(XSLMessages.createMessage("ER_REQUIRES_ATTRIB", new Object[]{string, xSLTAttributeDef.getName()}), null);
        }
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String string, String string2, Attributes attributes) throws SAXException {
        object = new KeyDeclaration(stylesheetHandler.getStylesheet(), stylesheetHandler.nextUid());
        ((ElemTemplateElement)object).setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        ((ElemTemplateElement)object).setLocaterInfo(stylesheetHandler.getLocator());
        this.setPropertiesFromAttributes(stylesheetHandler, string2, attributes, (ElemTemplateElement)object);
        stylesheetHandler.getStylesheet().setKey((KeyDeclaration)object);
    }
}

