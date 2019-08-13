/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import java.util.Properties;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

public abstract class Transformer {
    protected Transformer() {
    }

    public abstract void clearParameters();

    public abstract ErrorListener getErrorListener();

    public abstract Properties getOutputProperties();

    public abstract String getOutputProperty(String var1) throws IllegalArgumentException;

    public abstract Object getParameter(String var1);

    public abstract URIResolver getURIResolver();

    public void reset() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This Transformer, \"");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("\", does not support the reset functionality.  Specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract void setErrorListener(ErrorListener var1) throws IllegalArgumentException;

    public abstract void setOutputProperties(Properties var1);

    public abstract void setOutputProperty(String var1, String var2) throws IllegalArgumentException;

    public abstract void setParameter(String var1, Object var2);

    public abstract void setURIResolver(URIResolver var1);

    public abstract void transform(Source var1, Result var2) throws TransformerException;
}

