/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.xalan.res.XSLMessages;
import org.apache.xpath.jaxp.XPathImpl;

public class XPathFactoryImpl
extends XPathFactory {
    private static final String CLASS_NAME = "XPathFactoryImpl";
    private boolean featureSecureProcessing = false;
    private XPathFunctionResolver xPathFunctionResolver = null;
    private XPathVariableResolver xPathVariableResolver = null;

    @Override
    public boolean getFeature(String string) throws XPathFactoryConfigurationException {
        if (string != null) {
            if (string.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
                return this.featureSecureProcessing;
            }
            throw new XPathFactoryConfigurationException(XSLMessages.createXPATHMessage("ER_GETTING_UNKNOWN_FEATURE", new Object[]{string, CLASS_NAME}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_GETTING_NULL_FEATURE", new Object[]{CLASS_NAME}));
    }

    @Override
    public boolean isObjectModelSupported(String string) {
        if (string != null) {
            if (string.length() != 0) {
                return string.equals("http://java.sun.com/jaxp/xpath/dom");
            }
            throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_OBJECT_MODEL_EMPTY", new Object[]{this.getClass().getName()}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_OBJECT_MODEL_NULL", new Object[]{this.getClass().getName()}));
    }

    @Override
    public XPath newXPath() {
        return new XPathImpl(this.xPathVariableResolver, this.xPathFunctionResolver, this.featureSecureProcessing);
    }

    @Override
    public void setFeature(String string, boolean bl) throws XPathFactoryConfigurationException {
        if (string != null) {
            if (string.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
                this.featureSecureProcessing = bl;
                return;
            }
            throw new XPathFactoryConfigurationException(XSLMessages.createXPATHMessage("ER_FEATURE_UNKNOWN", new Object[]{string, CLASS_NAME, new Boolean(bl)}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_FEATURE_NAME_NULL", new Object[]{CLASS_NAME, new Boolean(bl)}));
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver xPathFunctionResolver) {
        if (xPathFunctionResolver != null) {
            this.xPathFunctionResolver = xPathFunctionResolver;
            return;
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_NULL_XPATH_FUNCTION_RESOLVER", new Object[]{CLASS_NAME}));
    }

    @Override
    public void setXPathVariableResolver(XPathVariableResolver xPathVariableResolver) {
        if (xPathVariableResolver != null) {
            this.xPathVariableResolver = xPathVariableResolver;
            return;
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_NULL_XPATH_VARIABLE_RESOLVER", new Object[]{CLASS_NAME}));
    }
}

