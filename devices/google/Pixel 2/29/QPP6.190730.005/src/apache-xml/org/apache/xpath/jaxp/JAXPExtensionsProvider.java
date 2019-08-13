/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.NodeList;

public class JAXPExtensionsProvider
implements ExtensionsProvider {
    private boolean extensionInvocationDisabled = false;
    private final XPathFunctionResolver resolver;

    public JAXPExtensionsProvider(XPathFunctionResolver xPathFunctionResolver) {
        this.resolver = xPathFunctionResolver;
        this.extensionInvocationDisabled = false;
    }

    public JAXPExtensionsProvider(XPathFunctionResolver xPathFunctionResolver, boolean bl) {
        this.resolver = xPathFunctionResolver;
        this.extensionInvocationDisabled = bl;
    }

    @Override
    public boolean elementAvailable(String string, String string2) throws TransformerException {
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Object extFunction(String var1_1, String var2_4, Vector var3_5, Object var4_6) throws TransformerException {
        if (var2_4 == null) ** GOTO lbl9
        try {
            block10 : {
                var4_6 = new QName((String)var1_1, (String)var2_4);
                if (this.extensionInvocationDisabled) {
                    var2_4 = XSLMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[]{var4_6.toString()});
                    var1_1 = new XPathFunctionException((String)var2_4);
                    throw var1_1;
                }
                break block10;
lbl9: // 1 sources:
                var1_1 = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"Function Name"});
                var2_4 = new NullPointerException((String)var1_1);
                throw var2_4;
            }
            var5_7 = var3_5.size();
            var2_4 = this.resolver.resolveFunction((QName)var4_6, var5_7);
            var4_6 = new ArrayList(var5_7);
            var6_8 = 0;
            while (var6_8 < var5_7) {
                var1_1 = var3_5.elementAt(var6_8);
                if (var1_1 instanceof XNodeSet) {
                    var4_6.add(var6_8, ((XNodeSet)var1_1).nodelist());
                } else if (var1_1 instanceof XObject) {
                    var4_6.add(var6_8, ((XObject)var1_1).object());
                } else {
                    var4_6.add(var6_8, var1_1);
                }
                ++var6_8;
            }
            return var2_4.evaluate((List)var4_6);
        }
        catch (Exception var1_2) {
            throw new TransformerException(var1_2);
        }
        catch (XPathFunctionException var1_3) {
            throw new WrappedRuntimeException(var1_3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object extFunction(FuncExtFunction object, Vector serializable) throws TransformerException {
        try {
            ArrayList<NodeList> arrayList = ((FuncExtFunction)object).getNamespace();
            Object object2 = ((FuncExtFunction)object).getFunctionName();
            int n = ((FuncExtFunction)object).getArgCount();
            object = new QName((String)((Object)arrayList), (String)object2);
            if (!this.extensionInvocationDisabled) {
                object2 = this.resolver.resolveFunction((QName)object, n);
                arrayList = new ArrayList<NodeList>(n);
            } else {
                object = XSLMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[]{((QName)object).toString()});
                serializable = new XPathFunctionException((String)object);
                throw serializable;
            }
            for (int i = 0; i < n; ++i) {
                object = ((Vector)serializable).elementAt(i);
                if (object instanceof XNodeSet) {
                    arrayList.add(i, ((XNodeSet)object).nodelist());
                    continue;
                }
                if (object instanceof XObject) {
                    arrayList.add(i, (NodeList)((XObject)object).object());
                    continue;
                }
                arrayList.add(i, (NodeList)object);
            }
            return object2.evaluate(arrayList);
        }
        catch (Exception exception) {
            throw new TransformerException(exception);
        }
        catch (XPathFunctionException xPathFunctionException) {
            throw new WrappedRuntimeException(xPathFunctionException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean functionAvailable(String object, String string) throws TransformerException {
        if (string != null) {
            try {
                QName qName = new QName((String)object, string);
                if (this.resolver.resolveFunction(qName, 0) != null) return true;
                return false;
            }
            catch (Exception exception) {
                return false;
            }
        }
        string = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"Function Name"});
        object = new NullPointerException(string);
        throw object;
    }
}

