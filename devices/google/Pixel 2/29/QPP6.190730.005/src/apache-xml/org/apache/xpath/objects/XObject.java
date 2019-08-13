/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathException;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XMLStringFactoryImpl;
import org.apache.xpath.objects.XObjectFactory;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XObject
extends Expression
implements Serializable,
Cloneable {
    public static final int CLASS_BOOLEAN = 1;
    public static final int CLASS_NODESET = 4;
    public static final int CLASS_NULL = -1;
    public static final int CLASS_NUMBER = 2;
    public static final int CLASS_RTREEFRAG = 5;
    public static final int CLASS_STRING = 3;
    public static final int CLASS_UNKNOWN = 0;
    public static final int CLASS_UNRESOLVEDVARIABLE = 600;
    static final long serialVersionUID = -821887098985662951L;
    protected Object m_obj;

    public XObject() {
    }

    public XObject(Object object) {
        this.setObject(object);
    }

    public static XObject create(Object object) {
        return XObjectFactory.create(object);
    }

    public static XObject create(Object object, XPathContext xPathContext) {
        return XObjectFactory.create(object, xPathContext);
    }

    public void allowDetachToRelease(boolean bl) {
    }

    public void appendToFsb(FastStringBuffer fastStringBuffer) {
        fastStringBuffer.append(this.str());
    }

    public boolean bool() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_NUMBER", new Object[]{this.getTypeString()});
        return false;
    }

    public boolean boolWithSideEffects() throws TransformerException {
        return this.bool();
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        this.assertion(false, "callVisitors should not be called for this object!!!");
    }

    public Object castToType(int n, XPathContext object) throws TransformerException {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            this.error("ER_CANT_CONVERT_TO_TYPE", new Object[]{this.getTypeString(), Integer.toString(n)});
                            object = null;
                        } else {
                            object = this.iter();
                        }
                    } else {
                        object = this.str();
                    }
                } else {
                    object = new Double(this.num());
                }
            } else {
                object = new Boolean(this.bool());
            }
        } else {
            object = this.m_obj;
        }
        return object;
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!this.isSameClass(expression)) {
            return false;
        }
        return this.equals((XObject)expression);
    }

    public void destruct() {
        if (this.m_obj != null) {
            this.allowDetachToRelease(true);
            this.detach();
            this.setObject(null);
        }
    }

    public void detach() {
    }

    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
        this.xstr().dispatchCharactersEvents(contentHandler);
    }

    public boolean equals(XObject xObject) {
        if (xObject.getType() == 4) {
            return xObject.equals(this);
        }
        Object object = this.m_obj;
        if (object != null) {
            return object.equals(xObject.m_obj);
        }
        boolean bl = xObject.m_obj == null;
        return bl;
    }

    protected void error(String string) throws TransformerException {
        this.error(string, null);
    }

    protected void error(String string, Object[] arrobject) throws TransformerException {
        throw new XPathException(XSLMessages.createXPATHMessage(string, arrobject), this);
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return this;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }

    public XObject getFresh() {
        return this;
    }

    public int getType() {
        return 0;
    }

    public String getTypeString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#UNKNOWN (");
        stringBuilder.append(this.object().getClass().getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean greaterThan(XObject xObject) throws TransformerException {
        if (xObject.getType() == 4) {
            return xObject.lessThan(this);
        }
        boolean bl = this.num() > xObject.num();
        return bl;
    }

    public boolean greaterThanOrEqual(XObject xObject) throws TransformerException {
        if (xObject.getType() == 4) {
            return xObject.lessThanOrEqual(this);
        }
        boolean bl = this.num() >= xObject.num();
        return bl;
    }

    public DTMIterator iter() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_NODELIST", new Object[]{this.getTypeString()});
        return null;
    }

    public boolean lessThan(XObject xObject) throws TransformerException {
        if (xObject.getType() == 4) {
            return xObject.greaterThan(this);
        }
        boolean bl = this.num() < xObject.num();
        return bl;
    }

    public boolean lessThanOrEqual(XObject xObject) throws TransformerException {
        if (xObject.getType() == 4) {
            return xObject.greaterThanOrEqual(this);
        }
        boolean bl = this.num() <= xObject.num();
        return bl;
    }

    public NodeSetDTM mutableNodeset() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_MUTABLENODELIST", new Object[]{this.getTypeString()});
        return (NodeSetDTM)this.m_obj;
    }

    public NodeList nodelist() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_NODELIST", new Object[]{this.getTypeString()});
        return null;
    }

    public NodeIterator nodeset() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_NODELIST", new Object[]{this.getTypeString()});
        return null;
    }

    public boolean notEquals(XObject xObject) throws TransformerException {
        if (xObject.getType() == 4) {
            return xObject.notEquals(this);
        }
        return this.equals(xObject) ^ true;
    }

    public double num() throws TransformerException {
        this.error("ER_CANT_CONVERT_TO_NUMBER", new Object[]{this.getTypeString()});
        return 0.0;
    }

    public double numWithSideEffects() throws TransformerException {
        return this.num();
    }

    public Object object() {
        return this.m_obj;
    }

    public void reset() {
    }

    public int rtf() {
        return -1;
    }

    public int rtf(XPathContext object) {
        int n;
        int n2 = n = this.rtf();
        if (-1 == n) {
            object = ((XPathContext)object).createDocumentFragment();
            object.appendTextChild(this.str());
            n2 = object.getDocument();
        }
        return n2;
    }

    public DocumentFragment rtree() {
        return null;
    }

    public DocumentFragment rtree(XPathContext object) {
        int n = this.rtf();
        if (-1 == n) {
            object = ((XPathContext)object).createDocumentFragment();
            object.appendTextChild(this.str());
            object = (DocumentFragment)object.getNode(object.getDocument());
        } else {
            object = ((XPathContext)object).getDTM(n);
            object = (DocumentFragment)object.getNode(object.getDocument());
        }
        return object;
    }

    protected void setObject(Object object) {
        this.m_obj = object;
    }

    public String str() {
        Object object = this.m_obj;
        object = object != null ? object.toString() : "";
        return object;
    }

    public String toString() {
        return this.str();
    }

    public XMLString xstr() {
        return XMLStringFactoryImpl.getFactory().newstr(this.str());
    }
}

