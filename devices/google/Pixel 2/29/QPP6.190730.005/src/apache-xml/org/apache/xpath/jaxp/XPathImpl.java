/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import java.io.IOException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.jaxp.JAXPExtensionsProvider;
import org.apache.xpath.jaxp.JAXPPrefixResolver;
import org.apache.xpath.jaxp.JAXPVariableStack;
import org.apache.xpath.jaxp.XPathExpressionImpl;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathImpl
implements javax.xml.xpath.XPath {
    private static Document d = null;
    private boolean featureSecureProcessing = false;
    private XPathFunctionResolver functionResolver;
    private NamespaceContext namespaceContext = null;
    private XPathFunctionResolver origFunctionResolver;
    private XPathVariableResolver origVariableResolver;
    private JAXPPrefixResolver prefixResolver;
    private XPathVariableResolver variableResolver;

    XPathImpl(XPathVariableResolver xPathVariableResolver, XPathFunctionResolver xPathFunctionResolver) {
        this.variableResolver = xPathVariableResolver;
        this.origVariableResolver = xPathVariableResolver;
        this.functionResolver = xPathFunctionResolver;
        this.origFunctionResolver = xPathFunctionResolver;
    }

    XPathImpl(XPathVariableResolver xPathVariableResolver, XPathFunctionResolver xPathFunctionResolver, boolean bl) {
        this.variableResolver = xPathVariableResolver;
        this.origVariableResolver = xPathVariableResolver;
        this.functionResolver = xPathFunctionResolver;
        this.origFunctionResolver = xPathFunctionResolver;
        this.featureSecureProcessing = bl;
    }

    private XObject eval(String object, Object object2) throws TransformerException {
        XPath xPath = new XPath((String)object, null, this.prefixResolver, 0);
        object = this.functionResolver;
        object = object != null ? new XPathContext(new JAXPExtensionsProvider((XPathFunctionResolver)object, this.featureSecureProcessing), false) : new XPathContext(false);
        ((XPathContext)object).setVarStack(new JAXPVariableStack(this.variableResolver));
        object = object2 instanceof Node ? xPath.execute((XPathContext)object, (Node)object2, (PrefixResolver)this.prefixResolver) : xPath.execute((XPathContext)object, -1, (PrefixResolver)this.prefixResolver);
        return object;
    }

    private static Document getDummyDocument() {
        if (d == null) {
            d = XPathImpl.getParser().getDOMImplementation().createDocument("http://java.sun.com/jaxp/xpath", "dummyroot", null);
        }
        return d;
    }

    private static DocumentBuilder getParser() {
        try {
            Object object = DocumentBuilderFactory.newInstance();
            ((DocumentBuilderFactory)object).setNamespaceAware(true);
            ((DocumentBuilderFactory)object).setValidating(false);
            object = ((DocumentBuilderFactory)object).newDocumentBuilder();
            return object;
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new Error(parserConfigurationException.toString());
        }
    }

    private Object getResultAsType(XObject xObject, QName qName) throws TransformerException {
        if (qName.equals(XPathConstants.STRING)) {
            return xObject.str();
        }
        if (qName.equals(XPathConstants.NUMBER)) {
            return new Double(xObject.num());
        }
        if (qName.equals(XPathConstants.BOOLEAN)) {
            return new Boolean(xObject.bool());
        }
        if (qName.equals(XPathConstants.NODESET)) {
            return xObject.nodelist();
        }
        if (qName.equals(XPathConstants.NODE)) {
            return xObject.nodeset().nextNode();
        }
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[]{qName.toString()}));
    }

    private boolean isSupported(QName qName) {
        return qName.equals(XPathConstants.STRING) || qName.equals(XPathConstants.NUMBER) || qName.equals(XPathConstants.BOOLEAN) || qName.equals(XPathConstants.NODE) || qName.equals(XPathConstants.NODESET);
        {
        }
    }

    @Override
    public XPathExpression compile(String object) throws XPathExpressionException {
        if (object != null) {
            try {
                XPath xPath = new XPath((String)object, null, this.prefixResolver, 0);
                object = new XPathExpressionImpl(xPath, this.prefixResolver, this.functionResolver, this.variableResolver, this.featureSecureProcessing);
                return object;
            }
            catch (TransformerException transformerException) {
                throw new XPathExpressionException(transformerException);
            }
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"XPath expression"}));
    }

    @Override
    public Object evaluate(String object, Object object2, QName qName) throws XPathExpressionException {
        if (object != null) {
            if (qName != null) {
                if (this.isSupported(qName)) {
                    try {
                        object = this.getResultAsType(this.eval((String)object, object2), qName);
                        return object;
                    }
                    catch (TransformerException transformerException) {
                        object = transformerException.getException();
                        if (object instanceof XPathFunctionException) {
                            throw (XPathFunctionException)object;
                        }
                        throw new XPathExpressionException(transformerException);
                    }
                    catch (NullPointerException nullPointerException) {
                        throw new XPathExpressionException(nullPointerException);
                    }
                }
                throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[]{qName.toString()}));
            }
            throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"returnType"}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"XPath expression"}));
    }

    @Override
    public Object evaluate(String object, InputSource inputSource, QName qName) throws XPathExpressionException {
        if (inputSource != null) {
            if (object != null) {
                if (qName != null) {
                    if (this.isSupported(qName)) {
                        try {
                            object = this.getResultAsType(this.eval((String)object, XPathImpl.getParser().parse(inputSource)), qName);
                            return object;
                        }
                        catch (TransformerException transformerException) {
                            object = transformerException.getException();
                            if (object instanceof XPathFunctionException) {
                                throw (XPathFunctionException)object;
                            }
                            throw new XPathExpressionException(transformerException);
                        }
                        catch (IOException iOException) {
                            throw new XPathExpressionException(iOException);
                        }
                        catch (SAXException sAXException) {
                            throw new XPathExpressionException(sAXException);
                        }
                    }
                    throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[]{qName.toString()}));
                }
                throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"returnType"}));
            }
            throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"XPath expression"}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"source"}));
    }

    @Override
    public String evaluate(String string, Object object) throws XPathExpressionException {
        return (String)this.evaluate(string, object, XPathConstants.STRING);
    }

    @Override
    public String evaluate(String string, InputSource inputSource) throws XPathExpressionException {
        return (String)this.evaluate(string, inputSource, XPathConstants.STRING);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }

    @Override
    public XPathFunctionResolver getXPathFunctionResolver() {
        return this.functionResolver;
    }

    @Override
    public XPathVariableResolver getXPathVariableResolver() {
        return this.variableResolver;
    }

    @Override
    public void reset() {
        this.variableResolver = this.origVariableResolver;
        this.functionResolver = this.origFunctionResolver;
        this.namespaceContext = null;
    }

    @Override
    public void setNamespaceContext(NamespaceContext namespaceContext) {
        if (namespaceContext != null) {
            this.namespaceContext = namespaceContext;
            this.prefixResolver = new JAXPPrefixResolver(namespaceContext);
            return;
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"NamespaceContext"}));
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver xPathFunctionResolver) {
        if (xPathFunctionResolver != null) {
            this.functionResolver = xPathFunctionResolver;
            return;
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"XPathFunctionResolver"}));
    }

    @Override
    public void setXPathVariableResolver(XPathVariableResolver xPathVariableResolver) {
        if (xPathVariableResolver != null) {
            this.variableResolver = xPathVariableResolver;
            return;
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"XPathVariableResolver"}));
    }
}

