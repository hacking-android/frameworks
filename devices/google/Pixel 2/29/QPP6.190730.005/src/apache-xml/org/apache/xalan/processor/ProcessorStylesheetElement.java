/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.processor.XSLTSchema;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.SAXSourceLocator;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.NamespaceSupport;

public class ProcessorStylesheetElement
extends XSLTElementProcessor {
    static final long serialVersionUID = -877798927447840792L;

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        super.endElement(stylesheetHandler, string, string2, string3);
        stylesheetHandler.popElemTemplateElement();
        stylesheetHandler.popStylesheet();
    }

    protected Stylesheet getStylesheetRoot(StylesheetHandler stylesheetHandler) throws TransformerConfigurationException {
        StylesheetRoot stylesheetRoot = new StylesheetRoot(stylesheetHandler.getSchema(), stylesheetHandler.getStylesheetProcessor().getErrorListener());
        if (stylesheetHandler.getStylesheetProcessor().isSecureProcessing()) {
            stylesheetRoot.setSecureProcessing(true);
        }
        return stylesheetRoot;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void startElement(StylesheetHandler object, String object2, String object3, String string, Attributes attributes) throws SAXException {
        super.startElement((StylesheetHandler)object, (String)object2, (String)object3, string, attributes);
        try {
            int n = ((StylesheetHandler)object).getStylesheetType();
            if (n == 1) {
                try {
                    object2 = this.getStylesheetRoot((StylesheetHandler)object);
                }
                catch (TransformerConfigurationException transformerConfigurationException) {
                    object = new TransformerException(transformerConfigurationException);
                    throw object;
                }
            } else {
                object3 = ((StylesheetHandler)object).getStylesheet();
                if (n == 3) {
                    object2 = new StylesheetComposed((Stylesheet)object3);
                    ((Stylesheet)object3).setImport((StylesheetComposed)object2);
                } else {
                    object2 = new Stylesheet((Stylesheet)object3);
                    ((Stylesheet)object3).setInclude((Stylesheet)object2);
                }
            }
            ((ElemTemplateElement)object2).setDOMBackPointer(((StylesheetHandler)object).getOriginatingNode());
            ((Stylesheet)object2).setLocaterInfo(((StylesheetHandler)object).getLocator());
            ((ElemTemplateElement)object2).setPrefixes(((StylesheetHandler)object).getNamespaceSupport());
            ((StylesheetHandler)object).pushStylesheet((Stylesheet)object2);
            this.setPropertiesFromAttributes((StylesheetHandler)object, string, attributes, ((StylesheetHandler)object).getStylesheet());
            ((StylesheetHandler)object).pushElemTemplateElement(((StylesheetHandler)object).getStylesheet());
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }
}

