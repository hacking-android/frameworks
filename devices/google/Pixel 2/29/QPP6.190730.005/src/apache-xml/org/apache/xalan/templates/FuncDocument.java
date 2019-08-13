/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.SourceTreeManager;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class FuncDocument
extends Function2Args {
    static final long serialVersionUID = 2483304325971281424L;

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n < 1 || n > 2) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public void error(XPathContext object, String object2, Object[] object3) throws TransformerException {
        object3 = XSLMessages.createMessage((String)object2, object3);
        object2 = ((XPathContext)object).getErrorListener();
        object = new TransformerException((String)object3, ((XPathContext)object).getSAXLocator());
        if (object2 != null) {
            object2.error((TransformerException)object);
        } else {
            System.out.println((String)object3);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        int n;
        Object object;
        Object object2 = this;
        int n2 = xPathContext.getCurrentNode();
        int n3 = xPathContext.getDTM(n2).getDocumentRoot(n2);
        XObject xObject = this.getArg0().execute(xPathContext);
        Object object3 = "";
        Expression expression = this.getArg1();
        if (expression != null) {
            object = expression.execute(xPathContext);
            if (4 == ((XObject)object).getType()) {
                n = ((XObject)object).iter().nextNode();
                if (n == -1) {
                    ((FuncDocument)object2).warn(xPathContext, "WG_EMPTY_SECOND_ARG", null);
                    return new XNodeSet(xPathContext.getDTMManager());
                }
                object3 = xPathContext.getDTM(n).getDocumentBaseURI();
            } else {
                ((XObject)object).iter();
            }
        } else {
            boolean bl = xPathContext.getNamespaceContext() != null;
            ((Expression)object2).assertion(bl, "Namespace context can not be null!");
            object3 = xPathContext.getNamespaceContext().getBaseIdentifier();
        }
        XNodeSet xNodeSet = new XNodeSet(xPathContext.getDTMManager());
        NodeSetDTM nodeSetDTM = xNodeSet.mutableNodeset();
        object = 4 == xObject.getType() ? xObject.iter() : null;
        n = -1;
        do {
            int n4;
            FuncDocument funcDocument = this;
            if (object != null) {
                n = n4 = object.nextNode();
                if (-1 == n4) return xNodeSet;
            }
            XMLString xMLString = object != null ? xPathContext.getDTM(n).getStringValue(n) : xObject.xstr();
            object2 = object3;
            if (expression == null) {
                object2 = object3;
                if (-1 != n) {
                    object2 = xPathContext.getDTM(n).getDocumentBaseURI();
                }
            }
            if (xMLString == null) {
                object3 = object2;
                continue;
            }
            if (-1 == n3) {
                funcDocument.error(xPathContext, "ER_NO_CONTEXT_OWNERDOC", null);
            }
            n4 = xMLString.indexOf(58);
            int n5 = xMLString.indexOf(47);
            object3 = object2;
            if (n4 != -1) {
                object3 = object2;
                if (n5 != -1) {
                    object3 = object2;
                    if (n4 < n5) {
                        object3 = null;
                    }
                }
            }
            if (-1 != (n4 = funcDocument.getDoc(xPathContext, n2, xMLString.toString(), (String)object3)) && !nodeSetDTM.contains(n4)) {
                nodeSetDTM.addElement(n4);
            }
            if (object == null || n4 == -1) return xNodeSet;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    int getDoc(XPathContext var1_1, int var2_5, String var3_6, String var4_11) throws TransformerException {
        block25 : {
            var5_12 = var1_1.getSourceTreeManager();
            var6_13 = var1_1.getSAXLocator();
            var7_14 = var3_6;
            var6_13 = var5_12.resolveURI(var4_11, (String)var7_14, (SourceLocator)var6_13);
            var2_5 = var5_12.getNode((Source)var6_13);
            if (-1 != var2_5) {
                return var2_5;
            }
            if (var3_6.length() != 0) break block25;
            var7_14 = var1_1.getNamespaceContext().getBaseIdentifier();
            try {
                var6_13 = var5_12.resolveURI(var4_11, (String)var7_14, var1_1.getSAXLocator());
            }
            catch (IOException var3_7) {
                throw new TransformerException(var3_7.getMessage(), var1_1.getSAXLocator(), var3_7);
            }
        }
        var8_15 = null;
        var9_16 = "";
        if (var7_14 == null) ** GOTO lbl-1000
        try {
            if (var7_14.length() > 0) {
                var2_5 = var5_12.getSourceTree((Source)var6_13, var1_1.getSAXLocator(), var1_1);
            } else lbl-1000: // 2 sources:
            {
                var10_17 = new StringBuilder();
                var3_6 = var4_11 == null ? "" : var4_11;
                var10_17.append((String)var3_6);
                var10_17.append((String)var7_14);
                this.warn(var1_1, "WG_CANNOT_MAKE_URL_FROM", new Object[]{var10_17.toString()});
            }
            var3_6 = var8_15;
            ** GOTO lbl56
        }
        catch (Throwable var10_18) {
            block26 : {
                var2_5 = -1;
                while (var10_19 instanceof WrappedRuntimeException) {
                    var10_19 = ((WrappedRuntimeException)var10_19).getException();
                }
                if (var10_19 instanceof NullPointerException != false) throw new WrappedRuntimeException(var10_19);
                if (var10_19 instanceof ClassCastException != false) throw new WrappedRuntimeException(var10_19);
                var11_20 = new PrintWriter(new StringWriter());
                if (var10_19 instanceof TransformerException) break block26;
                var2_5 = -1;
                var3_6 = new StringBuilder();
                var3_6.append(" (");
                var3_6.append(var10_19.getClass().getName());
                var3_6.append("): ");
                var3_6.append(var10_19.getMessage());
                var11_20.println(var3_6.toString());
                do {
                    var3_6 = var10_19.getMessage();
lbl56: // 2 sources:
                    if (-1 != var2_5) return var2_5;
                    if (var3_6 != null) {
                        this.warn(var1_1, "WG_CANNOT_LOAD_REQUESTED_DOC", new Object[]{var3_6});
                        return var2_5;
                    }
                    if (var7_14 == null) {
                        var3_6 = new StringBuilder();
                        if (var4_11 == null) {
                            var4_11 = var9_16;
                        }
                        var3_6.append(var4_11);
                        var3_6.append((String)var7_14);
                        var3_6 = var3_6.toString();
                    } else {
                        var3_6 = var7_14.toString();
                    }
                    this.warn(var1_1, "WG_CANNOT_LOAD_REQUESTED_DOC", new Object[]{var3_6});
                    return var2_5;
                    break;
                } while (true);
                catch (TransformerException var1_2) {
                    throw new TransformerException((Throwable)var1_4);
                }
                catch (IOException var3_8) {
                    throw new TransformerException(var3_10.getMessage(), var1_1.getSAXLocator(), (Throwable)var3_10);
                }
                catch (TransformerException var1_3) {
                    // empty catch block
                }
                throw new TransformerException((Throwable)var1_4);
                catch (IOException var3_9) {
                    // empty catch block
                }
                throw new TransformerException(var3_10.getMessage(), var1_1.getSAXLocator(), (Throwable)var3_10);
            }
            var8_15 = (TransformerException)var10_19;
            var3_6 = var5_12;
            do lbl-1000: // 4 sources:
            {
                if (var8_15 == null) ** continue;
                if (var8_15.getMessage() != null) {
                    var5_12 = new StringBuilder();
                    var5_12.append(" (");
                    var5_12.append(var8_15.getClass().getName());
                    var5_12.append("): ");
                    var5_12.append(var8_15.getMessage());
                    var11_20.println(var5_12.toString());
                }
                if (var8_15 instanceof TransformerException) {
                    var12_21 = var8_15;
                    var8_15 = var12_21.getLocator();
                    if (var8_15 != null && var8_15.getSystemId() != null) {
                        var5_12 = new StringBuilder();
                        var5_12.append("   ID: ");
                        var5_12.append(var8_15.getSystemId());
                        var5_12.append(" Line #");
                        var5_12.append(var8_15.getLineNumber());
                        var5_12.append(" Column #");
                        var5_12.append(var8_15.getColumnNumber());
                        var11_20.println(var5_12.toString());
                    }
                    if (!((var8_15 = var12_21.getException()) instanceof WrappedRuntimeException)) ** continue;
                    var8_15 = ((WrappedRuntimeException)var8_15).getException();
                    ** continue;
                }
                var8_15 = null;
            } while (true);
        }
    }

    @Override
    public boolean isNodesetExpr() {
        return true;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createMessage("ER_ONE_OR_TWO", null));
    }

    @Override
    public void warn(XPathContext object, String string, Object[] object2) throws TransformerException {
        string = XSLMessages.createWarning(string, (Object[])object2);
        object2 = ((XPathContext)object).getErrorListener();
        object = new TransformerException(string, ((XPathContext)object).getSAXLocator());
        if (object2 != null) {
            object2.warning((TransformerException)object);
        } else {
            System.out.println(string);
        }
    }
}

