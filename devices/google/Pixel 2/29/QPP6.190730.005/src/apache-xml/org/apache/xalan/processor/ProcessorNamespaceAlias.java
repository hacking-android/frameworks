/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.Stylesheet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorNamespaceAlias
extends XSLTElementProcessor {
    static final long serialVersionUID = -6309867839007018964L;

    ProcessorNamespaceAlias() {
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3, Attributes attributes) throws SAXException {
        NamespaceAlias namespaceAlias = new NamespaceAlias(stylesheetHandler.nextUid());
        this.setPropertiesFromAttributes(stylesheetHandler, string3, attributes, namespaceAlias);
        string = string2 = namespaceAlias.getStylesheetPrefix();
        if (string2.equals("#default")) {
            string = "";
            namespaceAlias.setStylesheetPrefix("");
        }
        namespaceAlias.setStylesheetNamespace(stylesheetHandler.getNamespaceForPrefix(string));
        string3 = namespaceAlias.getResultPrefix();
        if (string3.equals("#default")) {
            namespaceAlias.setResultPrefix("");
            string = string2 = stylesheetHandler.getNamespaceForPrefix("");
            if (string2 == null) {
                stylesheetHandler.error("ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", null, null);
                string = string2;
            }
        } else {
            string = string2 = stylesheetHandler.getNamespaceForPrefix(string3);
            if (string2 == null) {
                stylesheetHandler.error("ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", new Object[]{string3}, null);
                string = string2;
            }
        }
        namespaceAlias.setResultNamespace(string);
        stylesheetHandler.getStylesheet().setNamespaceAlias(namespaceAlias);
        stylesheetHandler.getStylesheet().appendChild(namespaceAlias);
    }
}

