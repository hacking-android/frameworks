/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.functions.Function;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XNull;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.res.XPATHMessages;

public class FuncExtFunction
extends Function {
    static final long serialVersionUID = 5196115554693708718L;
    Vector m_argVec = new Vector();
    String m_extensionName;
    Object m_methodKey;
    String m_namespace;

    public FuncExtFunction(String string, String string2, Object object) {
        this.m_namespace = string;
        this.m_extensionName = string2;
        this.m_methodKey = object;
    }

    @Override
    public void callArgVisitors(XPathVisitor xPathVisitor) {
        for (int i = 0; i < this.m_argVec.size(); ++i) {
            Expression expression = (Expression)this.m_argVec.elementAt(i);
            expression.callVisitors(new ArgExtOwner(expression), xPathVisitor);
        }
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        if (!((XPathContext)object).isSecureProcessing()) {
            Object object2;
            Vector<Object> vector = new Vector<Object>();
            int n = this.m_argVec.size();
            for (int i = 0; i < n; ++i) {
                object2 = ((Expression)this.m_argVec.elementAt(i)).execute((XPathContext)object);
                ((XObject)object2).allowDetachToRelease(false);
                vector.addElement(object2);
            }
            object2 = ((ExtensionsProvider)((XPathContext)object).getOwnerObject()).extFunction(this, vector);
            object = object2 != null ? XObject.create(object2, (XPathContext)object) : new XNull();
            return object;
        }
        throw new TransformerException(XPATHMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[]{this.toString()}));
    }

    @Override
    public void exprSetParent(ExpressionNode expressionNode) {
        super.exprSetParent(expressionNode);
        int n = this.m_argVec.size();
        for (int i = 0; i < n; ++i) {
            ((Expression)this.m_argVec.elementAt(i)).exprSetParent(expressionNode);
        }
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        Vector vector2 = this.m_argVec;
        if (vector2 != null) {
            int n2 = vector2.size();
            for (int i = 0; i < n2; ++i) {
                ((Expression)this.m_argVec.elementAt(i)).fixupVariables(vector, n);
            }
        }
    }

    public Expression getArg(int n) {
        if (n >= 0 && n < this.m_argVec.size()) {
            return (Expression)this.m_argVec.elementAt(n);
        }
        return null;
    }

    public int getArgCount() {
        return this.m_argVec.size();
    }

    public String getFunctionName() {
        return this.m_extensionName;
    }

    public Object getMethodKey() {
        return this.m_methodKey;
    }

    public String getNamespace() {
        return this.m_namespace;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{"Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called."}));
    }

    @Override
    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        this.m_argVec.addElement(expression);
        expression.exprSetParent(this);
    }

    public String toString() {
        CharSequence charSequence = this.m_namespace;
        if (charSequence != null && ((String)charSequence).length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{");
            ((StringBuilder)charSequence).append(this.m_namespace);
            ((StringBuilder)charSequence).append("}");
            ((StringBuilder)charSequence).append(this.m_extensionName);
            return ((StringBuilder)charSequence).toString();
        }
        return this.m_extensionName;
    }

    class ArgExtOwner
    implements ExpressionOwner {
        Expression m_exp;

        ArgExtOwner(Expression expression) {
            this.m_exp = expression;
        }

        @Override
        public Expression getExpression() {
            return this.m_exp;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FuncExtFunction.this);
            this.m_exp = expression;
        }
    }

}

