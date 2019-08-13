/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.URIResolver;

public abstract class TransformerFactory {
    protected TransformerFactory() {
    }

    public static TransformerFactory newInstance() throws TransformerFactoryConfigurationError {
        try {
            TransformerFactory transformerFactory = (TransformerFactory)Class.forName("org.apache.xalan.processor.TransformerFactoryImpl").newInstance();
            return transformerFactory;
        }
        catch (Exception exception) {
            throw new NoClassDefFoundError("org.apache.xalan.processor.TransformerFactoryImpl");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static TransformerFactory newInstance(String var0, ClassLoader var1_4) throws TransformerFactoryConfigurationError {
        if (var0 == null) throw new TransformerFactoryConfigurationError("factoryClassName == null");
        var2_5 = var1_4;
        if (var1_4 == null) {
            var2_5 = Thread.currentThread().getContextClassLoader();
        }
        if (var2_5 == null) ** GOTO lbl9
        try {
            var0 = var2_5.loadClass((String)var0);
            return (TransformerFactory)var0.newInstance();
lbl9: // 1 sources:
            var0 = Class.forName((String)var0);
            return (TransformerFactory)var0.newInstance();
        }
        catch (IllegalAccessException var0_1) {
            throw new TransformerFactoryConfigurationError(var0_1);
        }
        catch (InstantiationException var0_2) {
            throw new TransformerFactoryConfigurationError(var0_2);
        }
        catch (ClassNotFoundException var0_3) {
            throw new TransformerFactoryConfigurationError(var0_3);
        }
    }

    public abstract Source getAssociatedStylesheet(Source var1, String var2, String var3, String var4) throws TransformerConfigurationException;

    public abstract Object getAttribute(String var1);

    public abstract ErrorListener getErrorListener();

    public abstract boolean getFeature(String var1);

    public abstract URIResolver getURIResolver();

    public abstract Templates newTemplates(Source var1) throws TransformerConfigurationException;

    public abstract Transformer newTransformer() throws TransformerConfigurationException;

    public abstract Transformer newTransformer(Source var1) throws TransformerConfigurationException;

    public abstract void setAttribute(String var1, Object var2);

    public abstract void setErrorListener(ErrorListener var1);

    public abstract void setFeature(String var1, boolean var2) throws TransformerConfigurationException;

    public abstract void setURIResolver(URIResolver var1);
}

