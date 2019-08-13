/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFactoryFinder;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

public abstract class XPathFactory {
    public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";
    public static final String DEFAULT_PROPERTY_NAME = "javax.xml.xpath.XPathFactory";

    protected XPathFactory() {
    }

    public static final XPathFactory newInstance() {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance(DEFAULT_OBJECT_MODEL_URI);
            return xPathFactory;
        }
        catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("XPathFactory#newInstance() failed to create an XPathFactory for the default object model: http://java.sun.com/jaxp/xpath/dom with the XPathFactoryConfigurationException: ");
            stringBuilder.append(xPathFactoryConfigurationException.toString());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public static final XPathFactory newInstance(String string) throws XPathFactoryConfigurationException {
        if (string != null) {
            if (string.length() != 0) {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Object object = classLoader;
                if (classLoader == null) {
                    object = XPathFactory.class.getClassLoader();
                }
                if ((object = new XPathFactoryFinder((ClassLoader)object).newFactory(string)) != null) {
                    return object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("No XPathFactory implementation found for the object model: ");
                ((StringBuilder)object).append(string);
                throw new XPathFactoryConfigurationException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }
        throw new NullPointerException("uri == null");
    }

    public static XPathFactory newInstance(String string, String object, ClassLoader classLoader) throws XPathFactoryConfigurationException {
        if (string != null) {
            if (string.length() != 0) {
                if (object != null) {
                    ClassLoader classLoader2 = classLoader;
                    if (classLoader == null) {
                        classLoader2 = Thread.currentThread().getContextClassLoader();
                    }
                    if ((object = new XPathFactoryFinder(classLoader2).createInstance((String)object)) != null && ((XPathFactory)object).isObjectModelSupported(string)) {
                        return object;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No XPathFactory implementation found for the object model: ");
                    ((StringBuilder)object).append(string);
                    throw new XPathFactoryConfigurationException(((StringBuilder)object).toString());
                }
                throw new XPathFactoryConfigurationException("factoryClassName cannot be null.");
            }
            throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }
        throw new NullPointerException("uri == null");
    }

    public abstract boolean getFeature(String var1) throws XPathFactoryConfigurationException;

    public abstract boolean isObjectModelSupported(String var1);

    public abstract XPath newXPath();

    public abstract void setFeature(String var1, boolean var2) throws XPathFactoryConfigurationException;

    public abstract void setXPathFunctionResolver(XPathFunctionResolver var1);

    public abstract void setXPathVariableResolver(XPathVariableResolver var1);
}

