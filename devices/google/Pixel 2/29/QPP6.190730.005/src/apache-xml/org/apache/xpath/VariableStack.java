/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;

public class VariableStack
implements Cloneable {
    public static final int CLEARLIMITATION = 1024;
    private static XObject[] m_nulls = new XObject[1024];
    private int _currentFrameBottom;
    int _frameTop;
    int[] _links;
    int _linksTop;
    XObject[] _stackFrames;

    public VariableStack() {
        this.reset();
    }

    public VariableStack(int n) {
        this.reset(n, n * 2);
    }

    public void clearLocalSlots(int n, int n2) {
        int n3 = this._currentFrameBottom;
        System.arraycopy(m_nulls, 0, this._stackFrames, n + n3, n2);
    }

    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            VariableStack variableStack = (VariableStack)super.clone();
            variableStack._stackFrames = (XObject[])this._stackFrames.clone();
            variableStack._links = (int[])this._links.clone();
            return variableStack;
        }
    }

    public XObject elementAt(int n) {
        return this._stackFrames[n];
    }

    public XObject getGlobalVariable(XPathContext object, int n) throws TransformerException {
        XObject xObject = this._stackFrames[n];
        if (xObject.getType() == 600) {
            XObject[] arrxObject = this._stackFrames;
            arrxObject[n] = object = xObject.execute((XPathContext)object);
            return object;
        }
        return xObject;
    }

    public XObject getGlobalVariable(XPathContext object, int n, boolean bl) throws TransformerException {
        XObject xObject = this._stackFrames[n];
        if (xObject.getType() == 600) {
            XObject[] arrxObject = this._stackFrames;
            arrxObject[n] = object = xObject.execute((XPathContext)object);
            return object;
        }
        object = bl ? xObject : xObject.getFresh();
        return object;
    }

    public XObject getLocalVariable(int n, int n2) throws TransformerException {
        return this._stackFrames[n + n2];
    }

    public XObject getLocalVariable(XPathContext object, int n) throws TransformerException {
        XObject xObject = this._stackFrames[n += this._currentFrameBottom];
        if (xObject != null) {
            if (xObject.getType() == 600) {
                XObject[] arrxObject = this._stackFrames;
                arrxObject[n] = object = xObject.execute((XPathContext)object);
                return object;
            }
            return xObject;
        }
        throw new TransformerException(XSLMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), ((XPathContext)object).getSAXLocator());
    }

    public XObject getLocalVariable(XPathContext object, int n, boolean bl) throws TransformerException {
        XObject xObject = this._stackFrames[n += this._currentFrameBottom];
        if (xObject != null) {
            if (xObject.getType() == 600) {
                XObject[] arrxObject = this._stackFrames;
                arrxObject[n] = object = xObject.execute((XPathContext)object);
                return object;
            }
            object = bl ? xObject : xObject.getFresh();
            return object;
        }
        throw new TransformerException(XSLMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), ((XPathContext)object).getSAXLocator());
    }

    public int getStackFrame() {
        return this._currentFrameBottom;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public XObject getVariableOrParam(XPathContext var1_1, QName var2_2) throws TransformerException {
        block3 : {
            var3_3 = var1_1.getNamespaceContext();
            if (!(var3_3 instanceof ElemTemplateElement)) throw new TransformerException(XSLMessages.createXPATHMessage("ER_VAR_NOT_RESOLVABLE", new Object[]{var2_2.toString()}));
            var4_4 = var3_3 = (ElemTemplateElement)var3_3;
            if (var3_3 instanceof Stylesheet) break block3;
            do {
                block2 : {
                    var4_4 = var3_3;
                    if (var3_3.getParentNode() instanceof Stylesheet) break;
                    var4_4 = var3_3;
                    do lbl-1000: // 3 sources:
                    {
                        var5_5 = var4_4 = var4_4.getPreviousSiblingElem();
                        if (var4_4 == null) break block2;
                        var4_4 = var5_5;
                        if (!(var5_5 instanceof ElemVariable)) ** GOTO lbl-1000
                        var6_6 = (ElemVariable)var5_5;
                        var4_4 = var5_5;
                    } while (!var6_6.getName().equals(var2_2));
                    return this.getLocalVariable(var1_1, var6_6.getIndex());
                }
                var3_3 = var3_3.getParentElem();
            } while (true);
        }
        if ((var3_3 = var4_4.getStylesheetRoot().getVariableOrParamComposed(var2_2)) == null) throw new TransformerException(XSLMessages.createXPATHMessage("ER_VAR_NOT_RESOLVABLE", new Object[]{var2_2.toString()}));
        return this.getGlobalVariable(var1_1, var3_3.getIndex());
    }

    public boolean isLocalSet(int n) throws TransformerException {
        boolean bl = this._stackFrames[this._currentFrameBottom + n] != null;
        return bl;
    }

    public int link(int n) {
        int n2;
        Object[] arrobject;
        this._currentFrameBottom = n2 = this._frameTop;
        this._frameTop = n2 + n;
        Object[] arrobject2 = this._stackFrames;
        if ((n2 = this._frameTop) >= arrobject2.length) {
            arrobject = new XObject[arrobject2.length + 4096 + n];
            System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
            this._stackFrames = arrobject;
        }
        if ((n = this._linksTop) + 1 >= (arrobject = this._links).length) {
            arrobject2 = new int[arrobject.length + 2048];
            System.arraycopy(arrobject, 0, arrobject2, 0, arrobject.length);
            this._links = arrobject2;
        }
        arrobject2 = this._links;
        n2 = this._linksTop;
        this._linksTop = n2 + 1;
        n = this._currentFrameBottom;
        arrobject2[n2] = (XObject)n;
        return n;
    }

    public void reset() {
        Object[] arrobject = this._links;
        int n = arrobject == null ? 4096 : arrobject.length;
        arrobject = this._stackFrames;
        int n2 = arrobject == null ? 8192 : arrobject.length;
        this.reset(n, n2);
    }

    protected void reset(int n, int n2) {
        this._frameTop = 0;
        this._linksTop = 0;
        if (this._links == null) {
            this._links = new int[n];
        }
        int[] arrn = this._links;
        n = this._linksTop;
        this._linksTop = n + 1;
        arrn[n] = 0;
        this._stackFrames = new XObject[n2];
    }

    public void setGlobalVariable(int n, XObject xObject) {
        this._stackFrames[n] = xObject;
    }

    public void setLocalVariable(int n, XObject xObject) {
        this._stackFrames[this._currentFrameBottom + n] = xObject;
    }

    public void setLocalVariable(int n, XObject xObject, int n2) {
        this._stackFrames[n + n2] = xObject;
    }

    public void setStackFrame(int n) {
        this._currentFrameBottom = n;
    }

    public int size() {
        return this._frameTop;
    }

    public void unlink() {
        int n;
        int[] arrn = this._links;
        this._linksTop = n = this._linksTop - 1;
        this._frameTop = arrn[n];
        this._currentFrameBottom = arrn[this._linksTop - 1];
    }

    public void unlink(int n) {
        int n2;
        int[] arrn = this._links;
        this._linksTop = n2 = this._linksTop - 1;
        this._frameTop = arrn[n2];
        this._currentFrameBottom = n;
    }
}

