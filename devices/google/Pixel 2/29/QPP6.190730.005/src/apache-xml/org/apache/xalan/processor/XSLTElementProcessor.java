/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xalan.processor.XSLTAttributeDef;
import org.apache.xalan.processor.XSLTElementDef;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xml.utils.IntStack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class XSLTElementProcessor
extends ElemTemplateElement {
    static final long serialVersionUID = 5597421564955304421L;
    private XSLTElementDef m_elemDef;
    private IntStack m_savedLastOrder;

    XSLTElementProcessor() {
    }

    public void characters(StylesheetHandler stylesheetHandler, char[] arrc, int n, int n2) throws SAXException {
        stylesheetHandler.error("ER_CHARS_NOT_ALLOWED", null, null);
    }

    public void endElement(StylesheetHandler stylesheetHandler, String object, String string, String string2) throws SAXException {
        object = this.m_savedLastOrder;
        if (object != null && !((IntStack)object).empty()) {
            this.getElemDef().setLastOrder(this.m_savedLastOrder.pop());
        }
        if (!this.getElemDef().getRequiredFound()) {
            stylesheetHandler.error("ER_REQUIRED_ELEM_NOT_FOUND", new Object[]{this.getElemDef().getRequiredElem()}, null);
        }
    }

    XSLTElementDef getElemDef() {
        return this.m_elemDef;
    }

    public void ignorableWhitespace(StylesheetHandler stylesheetHandler, char[] arrc, int n, int n2) throws SAXException {
    }

    public void notationDecl(StylesheetHandler stylesheetHandler, String string, String string2, String string3) {
    }

    public void processingInstruction(StylesheetHandler stylesheetHandler, String string, String string2) throws SAXException {
    }

    public InputSource resolveEntity(StylesheetHandler stylesheetHandler, String string, String string2) throws SAXException {
        return null;
    }

    void setElemDef(XSLTElementDef xSLTElementDef) {
        this.m_elemDef = xSLTElementDef;
    }

    /*
     * WARNING - void declaration
     */
    Attributes setPropertiesFromAttributes(StylesheetHandler stylesheetHandler, String string, Attributes arrxSLTAttributeDef, ElemTemplateElement elemTemplateElement, boolean bl) throws SAXException {
        XSLTElementDef xSLTElementDef = this.getElemDef();
        int n = stylesheetHandler.getStylesheet() != null && stylesheetHandler.getStylesheet().getCompatibleMode() || !bl ? 1 : 0;
        AttributesImpl attributesImpl = n != 0 ? new AttributesImpl() : null;
        ArrayList<XSLTAttributeDef> arrayList = new ArrayList<XSLTAttributeDef>();
        ArrayList<XSLTAttributeDef> arrayList2 = new ArrayList<XSLTAttributeDef>();
        int n2 = arrxSLTAttributeDef.getLength();
        for (int i = 0; i < n2; ++i) {
            void object;
            String string2;
            XSLTAttributeDef xSLTAttributeDef;
            String string3 = arrxSLTAttributeDef.getURI(i);
            if (string3 != null && string3.length() == 0 && (arrxSLTAttributeDef.getQName(i).startsWith("xmlns:") || arrxSLTAttributeDef.getQName(i).equals("xmlns"))) {
                String string4 = "http://www.w3.org/XML/1998/namespace";
            }
            if ((xSLTAttributeDef = xSLTElementDef.getAttributeDef((String)object, string2 = arrxSLTAttributeDef.getLocalName(i))) == null) {
                if (n == 0) {
                    stylesheetHandler.error("ER_ATTR_NOT_ALLOWED", new Object[]{arrxSLTAttributeDef.getQName(i), string}, null);
                    continue;
                }
                attributesImpl.addAttribute((String)object, string2, arrxSLTAttributeDef.getQName(i), arrxSLTAttributeDef.getType(i), arrxSLTAttributeDef.getValue(i));
                continue;
            }
            int n3 = i;
            if (stylesheetHandler.getStylesheetProcessor() == null) {
                System.out.println("stylesheet processor null");
            }
            if (xSLTAttributeDef.getName().compareTo("*") == 0 && stylesheetHandler.getStylesheetProcessor().isSecureProcessing()) {
                stylesheetHandler.error("ER_ATTR_NOT_ALLOWED", new Object[]{arrxSLTAttributeDef.getQName(n3), string}, null);
                continue;
            }
            if (xSLTAttributeDef.setAttrValue(stylesheetHandler, (String)object, string2, arrxSLTAttributeDef.getQName(n3), arrxSLTAttributeDef.getValue(n3), elemTemplateElement)) {
                arrayList.add(xSLTAttributeDef);
                continue;
            }
            arrayList2.add(xSLTAttributeDef);
        }
        for (XSLTAttributeDef xSLTAttributeDef : xSLTElementDef.getAttributes()) {
            if (xSLTAttributeDef.getDefault() != null && !arrayList.contains(xSLTAttributeDef)) {
                xSLTAttributeDef.setDefAttrValue(stylesheetHandler, elemTemplateElement);
            }
            if (!xSLTAttributeDef.getRequired() || arrayList.contains(xSLTAttributeDef) || arrayList2.contains(xSLTAttributeDef)) continue;
            stylesheetHandler.error(XSLMessages.createMessage("ER_REQUIRES_ATTRIB", new Object[]{string, xSLTAttributeDef.getName()}), null);
        }
        return attributesImpl;
    }

    void setPropertiesFromAttributes(StylesheetHandler stylesheetHandler, String string, Attributes attributes, ElemTemplateElement elemTemplateElement) throws SAXException {
        this.setPropertiesFromAttributes(stylesheetHandler, string, attributes, elemTemplateElement, true);
    }

    public void skippedEntity(StylesheetHandler stylesheetHandler, String string) throws SAXException {
    }

    public void startElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3, Attributes attributes) throws SAXException {
        if (this.m_savedLastOrder == null) {
            this.m_savedLastOrder = new IntStack();
        }
        this.m_savedLastOrder.push(this.getElemDef().getLastOrder());
        this.getElemDef().setLastOrder(-1);
    }

    public void startNonText(StylesheetHandler stylesheetHandler) throws SAXException {
    }

    public void unparsedEntityDecl(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4) {
    }
}

