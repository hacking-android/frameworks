/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExtensionNamespaceSupport;
import org.apache.xalan.extensions.ExtensionNamespacesManager;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElemExsltFunction
extends ElemTemplate {
    static final long serialVersionUID = 272154954793534771L;

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        String string = this.getName().getNamespace();
        String string2 = stylesheetRoot.getExtensionHandlerClass();
        ExtensionNamespaceSupport extensionNamespaceSupport = new ExtensionNamespaceSupport(string, string2, new Object[]{string, stylesheetRoot});
        stylesheetRoot.getExtensionNamespacesManager().registerExtension(extensionNamespaceSupport);
        if (!string.equals("http://exslt.org/functions")) {
            extensionNamespaceSupport = new ExtensionNamespaceSupport("http://exslt.org/functions", string2, new Object[]{"http://exslt.org/functions", stylesheetRoot});
            stylesheetRoot.getExtensionNamespacesManager().registerExtension(extensionNamespaceSupport);
        }
    }

    public void execute(TransformerImpl transformerImpl, XObject[] arrxObject) throws TransformerException {
        VariableStack variableStack = transformerImpl.getXPathContext().getVarStack();
        int n = variableStack.getStackFrame();
        int n2 = variableStack.link(this.m_frameSize);
        if (this.m_inArgsSize >= arrxObject.length) {
            if (this.m_inArgsSize > 0) {
                variableStack.clearLocalSlots(0, this.m_inArgsSize);
                if (arrxObject.length > 0) {
                    variableStack.setStackFrame(n);
                    NodeList nodeList = this.getChildNodes();
                    for (int i = 0; i < arrxObject.length; ++i) {
                        nodeList.item(i);
                        if (!(nodeList.item(i) instanceof ElemParam)) continue;
                        variableStack.setLocalVariable(((ElemParam)nodeList.item(i)).getIndex(), arrxObject[i], n2);
                    }
                    variableStack.setStackFrame(n2);
                }
            }
            variableStack.setStackFrame(n2);
            transformerImpl.executeChildTemplates((ElemTemplateElement)this, true);
            variableStack.unlink(n);
            return;
        }
        throw new TransformerException("function called with too many args");
    }

    @Override
    public String getNodeName() {
        return "function";
    }

    @Override
    public int getXSLToken() {
        return 88;
    }
}

