/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.PrintStream;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.transformer.TransformerImpl;
import org.w3c.dom.Node;

public class MsgMgr {
    private TransformerImpl m_transformer;

    public MsgMgr(TransformerImpl transformerImpl) {
        this.m_transformer = transformerImpl;
    }

    public void error(SourceLocator sourceLocator, String string) throws TransformerException {
        this.error(sourceLocator, null, null, string, null);
    }

    public void error(SourceLocator sourceLocator, String string, Exception exception) throws TransformerException {
        this.error(sourceLocator, string, null, exception);
    }

    public void error(SourceLocator sourceLocator, String string, Object[] arrobject) throws TransformerException {
        this.error(sourceLocator, null, null, string, arrobject);
    }

    public void error(SourceLocator sourceLocator, String object, Object[] object2, Exception exception) throws TransformerException {
        object2 = XSLMessages.createMessage((String)object, object2);
        object = this.m_transformer.getErrorListener();
        if (object != null) {
            object.fatalError(new TransformerException((String)object2, sourceLocator));
            return;
        }
        throw new TransformerException((String)object2, sourceLocator);
    }

    public void error(SourceLocator sourceLocator, Node node, Node node2, String string) throws TransformerException {
        this.error(sourceLocator, node, node2, string, null);
    }

    public void error(SourceLocator sourceLocator, Node object, Node object2, String string, Object[] arrobject) throws TransformerException {
        object = XSLMessages.createMessage(string, arrobject);
        object2 = this.m_transformer.getErrorListener();
        if (object2 != null) {
            object2.fatalError(new TransformerException((String)object, sourceLocator));
            return;
        }
        throw new TransformerException((String)object, sourceLocator);
    }

    public void message(SourceLocator sourceLocator, String string, boolean bl) throws TransformerException {
        block4 : {
            block3 : {
                block2 : {
                    ErrorListener errorListener = this.m_transformer.getErrorListener();
                    if (errorListener == null) break block2;
                    errorListener.warning(new TransformerException(string, sourceLocator));
                    break block3;
                }
                if (bl) break block4;
                System.out.println(string);
            }
            return;
        }
        throw new TransformerException(string, sourceLocator);
    }

    public void warn(SourceLocator sourceLocator, String string) throws TransformerException {
        this.warn(sourceLocator, null, null, string, null);
    }

    public void warn(SourceLocator sourceLocator, String string, Object[] arrobject) throws TransformerException {
        this.warn(sourceLocator, null, null, string, arrobject);
    }

    public void warn(SourceLocator sourceLocator, Node node, Node node2, String string) throws TransformerException {
        this.warn(sourceLocator, node, node2, string, null);
    }

    public void warn(SourceLocator sourceLocator, Node object, Node object2, String string, Object[] arrobject) throws TransformerException {
        object = XSLMessages.createWarning(string, arrobject);
        object2 = this.m_transformer.getErrorListener();
        if (object2 != null) {
            object2.warning(new TransformerException((String)object, sourceLocator));
        } else {
            System.out.println((String)object);
        }
    }
}

