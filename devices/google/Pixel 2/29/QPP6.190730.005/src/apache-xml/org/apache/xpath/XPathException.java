/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.w3c.dom.Node;

public class XPathException
extends TransformerException {
    static final long serialVersionUID = 4263549717619045963L;
    protected Exception m_exception;
    Object m_styleNode = null;

    public XPathException(String string) {
        super(string);
    }

    public XPathException(String string, Exception exception) {
        super(string);
        this.m_exception = exception;
    }

    public XPathException(String string, Object object) {
        super(string);
        this.m_styleNode = object;
    }

    public XPathException(String string, ExpressionNode expressionNode) {
        super(string);
        this.setLocator(expressionNode);
        this.setStylesheetNode(this.getStylesheetNode(expressionNode));
    }

    public XPathException(String string, Node node, Exception exception) {
        super(string);
        this.m_styleNode = node;
        this.m_exception = exception;
    }

    @Override
    public Throwable getException() {
        return this.m_exception;
    }

    protected ExpressionNode getExpressionOwner(ExpressionNode expressionNode) {
        for (expressionNode = expressionNode.exprGetParent(); expressionNode != null && expressionNode instanceof Expression; expressionNode = expressionNode.exprGetParent()) {
        }
        return expressionNode;
    }

    @Override
    public String getMessage() {
        Object object;
        Object object2 = super.getMessage();
        Object object3 = this.m_exception;
        do {
            object = object2;
            if (object3 == null) break;
            object = ((Throwable)object3).getMessage();
            if (object != null) {
                object2 = object;
            }
            if (object3 instanceof TransformerException) {
                object = ((TransformerException)object3).getException();
                if (object3 == object) {
                    object = object2;
                    break;
                }
            } else {
                object = null;
            }
            object3 = object;
        } while (true);
        object2 = object != null ? object : "";
        return object2;
    }

    public Object getStylesheetNode() {
        return this.m_styleNode;
    }

    public Node getStylesheetNode(ExpressionNode expressionNode) {
        if ((expressionNode = this.getExpressionOwner(expressionNode)) != null && expressionNode instanceof Node) {
            return (Node)((Object)expressionNode);
        }
        return null;
    }

    @Override
    public void printStackTrace(PrintStream object) {
        PrintStream printStream = object;
        if (object == null) {
            printStream = System.err;
        }
        try {
            super.printStackTrace(printStream);
        }
        catch (Exception exception) {}
        object = this.m_exception;
        for (int i = 0; i < 10 && object != null; ++i) {
            printStream.println("---------");
            ((Throwable)object).printStackTrace(printStream);
            if (object instanceof TransformerException) {
                Throwable throwable = ((TransformerException)object).getException();
                if (object == throwable) break;
                object = throwable;
                continue;
            }
            object = null;
        }
    }

    @Override
    public void printStackTrace(PrintWriter object) {
        PrintWriter printWriter = object;
        if (object == null) {
            printWriter = new PrintWriter(System.err);
        }
        try {
            super.printStackTrace(printWriter);
        }
        catch (Exception exception) {}
        int n = 0;
        try {
            Throwable.class.getMethod("getCause", new Class[]{null});
            n = 1;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        if (n == 0) {
            object = this.m_exception;
            for (n = 0; n < 10 && object != null; ++n) {
                printWriter.println("---------");
                try {
                    ((Throwable)object).printStackTrace(printWriter);
                }
                catch (Exception exception) {
                    printWriter.println("Could not print stack trace...");
                }
                if (object instanceof TransformerException) {
                    Throwable throwable = ((TransformerException)object).getException();
                    if (object == throwable) break;
                    object = throwable;
                    continue;
                }
                object = null;
            }
        }
    }

    public void setStylesheetNode(Object object) {
        this.m_styleNode = object;
    }
}

