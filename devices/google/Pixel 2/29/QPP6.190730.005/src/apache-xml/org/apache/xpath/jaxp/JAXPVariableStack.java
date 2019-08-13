/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.QName;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class JAXPVariableStack
extends VariableStack {
    private final XPathVariableResolver resolver;

    public JAXPVariableStack(XPathVariableResolver xPathVariableResolver) {
        super(2);
        this.resolver = xPathVariableResolver;
    }

    @Override
    public XObject getVariableOrParam(XPathContext xPathContext, QName object) throws TransformerException, IllegalArgumentException {
        if (object != null) {
            javax.xml.namespace.QName qName = new javax.xml.namespace.QName(((QName)object).getNamespace(), ((QName)object).getLocalPart());
            object = this.resolver.resolveVariable(qName);
            if (object != null) {
                return XObject.create(object, xPathContext);
            }
            throw new TransformerException(XSLMessages.createXPATHMessage("ER_RESOLVE_VARIABLE_RETURNS_NULL", new Object[]{qName.toString()}));
        }
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"Variable qname"}));
    }
}

