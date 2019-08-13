/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.serialize.SerializerUtils;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemUse;
import org.apache.xalan.transformer.ClonerToResultTree;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xpath.XPathContext;
import org.xml.sax.SAXException;

public class ElemCopy
extends ElemUse {
    static final long serialVersionUID = 5478580783896941384L;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void execute(TransformerImpl object) throws TransformerException {
        Throwable throwable2222;
        XPathContext xPathContext = ((TransformerImpl)object).getXPathContext();
        int n = xPathContext.getCurrentNode();
        xPathContext.pushCurrentNode(n);
        DTM dTM = xPathContext.getDTM(n);
        short s = dTM.getNodeType(n);
        if (9 != s && 11 != s) {
            Object object2 = ((TransformerImpl)object).getSerializationHandler();
            ClonerToResultTree.cloneToResultTree(n, s, dTM, (SerializationHandler)object2, false);
            if (1 == s) {
                super.execute((TransformerImpl)object);
                SerializerUtils.processNSDecls((SerializationHandler)object2, n, s, dTM);
                ((TransformerImpl)object).executeChildTemplates((ElemTemplateElement)this, true);
                String string = dTM.getNamespaceURI(n);
                object2 = dTM.getLocalName(n);
                ((TransformerImpl)object).getResultTreeHandler().endElement(string, (String)object2, dTM.getNodeName(n));
            }
        } else {
            super.execute((TransformerImpl)object);
            ((TransformerImpl)object).executeChildTemplates((ElemTemplateElement)this, true);
        }
        xPathContext.popCurrentNode();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (SAXException sAXException) {}
            {
                object = new TransformerException(sAXException);
                throw object;
            }
        }
        xPathContext.popCurrentNode();
        throw throwable2222;
    }

    @Override
    public String getNodeName() {
        return "copy";
    }

    @Override
    public int getXSLToken() {
        return 9;
    }
}

