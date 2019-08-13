/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.io.PrintStream;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XObject;

public abstract class Function
extends Expression {
    static final long serialVersionUID = 6927661240854599768L;

    public void callArgVisitors(XPathVisitor xPathVisitor) {
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitFunction(expressionOwner, this)) {
            this.callArgVisitors(xPathVisitor);
        }
    }

    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n != 0) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public boolean deepEquals(Expression expression) {
        return this.isSameClass(expression);
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        System.out.println("Error! Function.execute should not be called!");
        return null;
    }

    public void postCompileStep(Compiler compiler) {
    }

    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("zero", null));
    }

    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        this.reportWrongNumberArgs();
    }
}

