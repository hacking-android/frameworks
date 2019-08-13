/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.io.Serializable;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementDef;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xml.utils.SAXSourceLocator;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.NamespaceSupport;

public class ProcessorTemplateElem
extends XSLTElementProcessor {
    static final long serialVersionUID = 8344994001943407235L;

    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        ElemTemplateElement elemTemplateElement2 = stylesheetHandler.getElemTemplateElement();
        if (elemTemplateElement2 != null) {
            elemTemplateElement2.appendChild(elemTemplateElement);
            stylesheetHandler.pushElemTemplateElement(elemTemplateElement);
        }
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        super.endElement(stylesheetHandler, string, string2, string3);
        stylesheetHandler.popElemTemplateElement().setEndLocaterInfo(stylesheetHandler.getLocator());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String object2, String string, Attributes attributes) throws SAXException {
        super.startElement(stylesheetHandler, (String)object, (String)object2, string, attributes);
        try {
            block5 : {
                Serializable serializable = this.getElemDef().getClassObject();
                object2 = null;
                object = null;
                try {
                    serializable = (ElemTemplateElement)((Class)serializable).newInstance();
                    object = serializable;
                    object2 = serializable;
                    ((ElemTemplateElement)serializable).setDOMBackPointer(stylesheetHandler.getOriginatingNode());
                    object = serializable;
                    object2 = serializable;
                    ((ElemTemplateElement)serializable).setLocaterInfo(stylesheetHandler.getLocator());
                    object = serializable;
                    object2 = serializable;
                    ((ElemTemplateElement)serializable).setPrefixes(stylesheetHandler.getNamespaceSupport());
                    object2 = serializable;
                }
                catch (IllegalAccessException illegalAccessException) {
                    stylesheetHandler.error("ER_FAILED_CREATING_ELEMTMPL", null, illegalAccessException);
                    break block5;
                }
                catch (InstantiationException instantiationException) {
                    stylesheetHandler.error("ER_FAILED_CREATING_ELEMTMPL", null, instantiationException);
                }
                object = object2;
            }
            this.setPropertiesFromAttributes(stylesheetHandler, string, attributes, (ElemTemplateElement)object);
            this.appendAndPush(stylesheetHandler, (ElemTemplateElement)object);
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }
}

