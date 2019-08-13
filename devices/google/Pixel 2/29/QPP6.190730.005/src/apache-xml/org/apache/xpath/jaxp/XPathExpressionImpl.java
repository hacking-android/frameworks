/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import java.io.Serializable;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.apache.xpath.objects.XObject;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

public class XPathExpressionImpl
implements XPathExpression {
    static Document d;
    static DocumentBuilder db;
    static DocumentBuilderFactory dbf;
    private boolean featureSecureProcessing = false;
    private XPathFunctionResolver functionResolver;
    private JAXPPrefixResolver prefixResolver;
    private XPathVariableResolver variableResolver;
    private XPath xpath;

    static {
        dbf = null;
        db = null;
        d = null;
    }

    protected XPathExpressionImpl() {
    }

    protected XPathExpressionImpl(XPath xPath, JAXPPrefixResolver jAXPPrefixResolver, XPathFunctionResolver xPathFunctionResolver, XPathVariableResolver xPathVariableResolver) {
        this.xpath = xPath;
        this.prefixResolver = jAXPPrefixResolver;
        this.functionResolver = xPathFunctionResolver;
        this.variableResolver = xPathVariableResolver;
        this.featureSecureProcessing = false;
    }

    protected XPathExpressionImpl(XPath xPath, JAXPPrefixResolver jAXPPrefixResolver, XPathFunctionResolver xPathFunctionResolver, XPathVariableResolver xPathVariableResolver, boolean bl) {
        this.xpath = xPath;
        this.prefixResolver = jAXPPrefixResolver;
        this.functionResolver = xPathFunctionResolver;
        this.variableResolver = xPathVariableResolver;
        this.featureSecureProcessing = bl;
    }

    private XObject eval(Object object) throws TransformerException {
        Object object2 = this.functionResolver;
        object2 = object2 != null ? new XPathContext(new JAXPExtensionsProvider((XPathFunctionResolver)object2, this.featureSecureProcessing), false) : new XPathContext(false);
        ((XPathContext)object2).setVarStack(new JAXPVariableStack(this.variableResolver));
        Node node = (Node)object;
        object = node;
        if (node == null) {
            object = XPathExpressionImpl.getDummyDocument();
        }
        return this.xpath.execute((XPathContext)object2, (Node)object, (PrefixResolver)this.prefixResolver);
    }

    private static Document getDummyDocument() {
        try {
            if (dbf == null) {
                dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                dbf.setValidating(false);
            }
            db = dbf.newDocumentBuilder();
            Document document = d = db.getDOMImplementation().createDocument("http://java.sun.com/jaxp/xpath", "dummyroot", null);
            return document;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
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

    public Object eval(Object object, QName qName) throws TransformerException {
        return this.getResultAsType(this.eval(object), qName);
    }

    @Override
    public Object evaluate(Object object, QName serializable) throws XPathExpressionException {
        if (serializable != null) {
            if (this.isSupported((QName)serializable)) {
                try {
                    object = this.eval(object, (QName)serializable);
                    return object;
                }
                catch (TransformerException transformerException) {
                    serializable = transformerException.getException();
                    if (serializable instanceof XPathFunctionException) {
                        throw (XPathFunctionException)serializable;
                    }
                    throw new XPathExpressionException(transformerException);
                }
                catch (NullPointerException nullPointerException) {
                    throw new XPathExpressionException(nullPointerException);
                }
            }
            throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[]{((QName)serializable).toString()}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"returnType"}));
    }

    @Override
    public Object evaluate(InputSource object, QName qName) throws XPathExpressionException {
        if (object != null && qName != null) {
            if (this.isSupported(qName)) {
                try {
                    if (dbf == null) {
                        dbf = DocumentBuilderFactory.newInstance();
                        dbf.setNamespaceAware(true);
                        dbf.setValidating(false);
                    }
                    db = dbf.newDocumentBuilder();
                    object = this.eval(db.parse((InputSource)object), qName);
                    return object;
                }
                catch (Exception exception) {
                    throw new XPathExpressionException(exception);
                }
            }
            throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[]{qName.toString()}));
        }
        throw new NullPointerException(XSLMessages.createXPATHMessage("ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL", null));
    }

    @Override
    public String evaluate(Object object) throws XPathExpressionException {
        return (String)this.evaluate(object, XPathConstants.STRING);
    }

    @Override
    public String evaluate(InputSource inputSource) throws XPathExpressionException {
        return (String)this.evaluate(inputSource, XPathConstants.STRING);
    }

    public void setXPath(XPath xPath) {
        this.xpath = xPath;
    }
}

