/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.functions.ObjectFactory;
import org.apache.xpath.functions.SecuritySupport;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncSystemProperty
extends FunctionOneArg {
    static final String XSLT_PROPERTIES = "org/apache/xalan/res/XSLTInfo.properties";
    static final long serialVersionUID = 3694874980992204867L;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        Object object3;
        Object object2;
        String string;
        block15 : {
            string = this.m_arg0.execute((XPathContext)object).str();
            int n = string.indexOf(58);
            object3 = null;
            String string2 = null;
            object2 = "";
            Properties properties = new Properties();
            this.loadPropertyFile(XSLT_PROPERTIES, properties);
            if (n > 0) {
                object3 = n >= 0 ? string.substring(0, n) : "";
                String string3 = ((XPathContext)object).getNamespaceContext().getNamespaceForPrefix((String)object3);
                object3 = n < 0 ? string : string.substring(n + 1);
                object2 = object3;
                if (!string3.startsWith("http://www.w3.org/XSL/Transform") && !string3.equals("http://www.w3.org/1999/XSL/Transform")) {
                    this.warn((XPathContext)object, "WG_DONT_DO_ANYTHING_WITH_NS", new Object[]{string3, string});
                    try {
                        if (!((XPathContext)object).isSecureProcessing()) {
                            object3 = System.getProperty((String)object2);
                        } else {
                            this.warn((XPathContext)object, "WG_SECURITY_EXCEPTION", new Object[]{string});
                            object3 = string2;
                        }
                        if (object3 == null) {
                            return XString.EMPTYSTRING;
                        }
                        break block15;
                    }
                    catch (SecurityException securityException) {
                        this.warn((XPathContext)object, "WG_SECURITY_EXCEPTION", new Object[]{string});
                        return XString.EMPTYSTRING;
                    }
                }
                string2 = properties.getProperty((String)object2);
                object3 = string2;
                if (string2 == null) {
                    this.warn((XPathContext)object, "WG_PROPERTY_NOT_SUPPORTED", new Object[]{string});
                    return XString.EMPTYSTRING;
                }
            } else {
                if (!((XPathContext)object).isSecureProcessing()) {
                    object3 = System.getProperty(string);
                } else {
                    this.warn((XPathContext)object, "WG_SECURITY_EXCEPTION", new Object[]{string});
                }
                if (object3 != null) break block15;
                return XString.EMPTYSTRING;
            }
        }
        if (!((String)object2).equals("version")) return new XString((String)object3);
        if (((String)object3).length() <= 0) return new XString((String)object3);
        try {
            return new XString("1.0");
        }
        catch (Exception exception) {
            return new XString((String)object3);
        }
        catch (SecurityException securityException) {
            this.warn((XPathContext)object, "WG_SECURITY_EXCEPTION", new Object[]{string});
            return XString.EMPTYSTRING;
        }
    }

    public void loadPropertyFile(String object, Properties properties) {
        try {
            object = SecuritySupport.getInstance().getResourceAsStream(ObjectFactory.findClassLoader(), (String)object);
            BufferedInputStream bufferedInputStream = new BufferedInputStream((InputStream)object);
            properties.load(bufferedInputStream);
            bufferedInputStream.close();
            return;
        }
        catch (Exception exception) {
            throw new WrappedRuntimeException(exception);
        }
    }
}

