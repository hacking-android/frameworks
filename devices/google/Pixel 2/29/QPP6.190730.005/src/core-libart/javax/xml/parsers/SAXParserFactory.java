/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.parsers;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.validation.Schema;
import org.apache.harmony.xml.parsers.SAXParserFactoryImpl;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public abstract class SAXParserFactory {
    private boolean namespaceAware = false;
    private boolean validating = false;

    protected SAXParserFactory() {
    }

    public static SAXParserFactory newInstance() {
        return new SAXParserFactoryImpl();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static SAXParserFactory newInstance(String var0, ClassLoader var1_4) {
        if (var0 == null) throw new FactoryConfigurationError("factoryClassName == null");
        var2_5 = var1_4;
        if (var1_4 == null) {
            var2_5 = Thread.currentThread().getContextClassLoader();
        }
        if (var2_5 == null) ** GOTO lbl9
        try {
            var0 = var2_5.loadClass((String)var0);
            return (SAXParserFactory)var0.newInstance();
lbl9: // 1 sources:
            var0 = Class.forName((String)var0);
            return (SAXParserFactory)var0.newInstance();
        }
        catch (IllegalAccessException var0_1) {
            throw new FactoryConfigurationError(var0_1);
        }
        catch (InstantiationException var0_2) {
            throw new FactoryConfigurationError(var0_2);
        }
        catch (ClassNotFoundException var0_3) {
            throw new FactoryConfigurationError(var0_3);
        }
    }

    public abstract boolean getFeature(String var1) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

    public Schema getSchema() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    public boolean isValidating() {
        return this.validating;
    }

    public boolean isXIncludeAware() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract SAXParser newSAXParser() throws ParserConfigurationException, SAXException;

    public abstract void setFeature(String var1, boolean var2) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

    public void setNamespaceAware(boolean bl) {
        this.namespaceAware = bl;
    }

    public void setSchema(Schema object) {
        object = new StringBuilder();
        ((StringBuilder)object).append("This parser does not support specification \"");
        ((StringBuilder)object).append(this.getClass().getPackage().getSpecificationTitle());
        ((StringBuilder)object).append("\" version \"");
        ((StringBuilder)object).append(this.getClass().getPackage().getSpecificationVersion());
        ((StringBuilder)object).append("\"");
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }

    public void setValidating(boolean bl) {
        this.validating = bl;
    }

    public void setXIncludeAware(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This parser does not support specification \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationTitle());
        stringBuilder.append("\" version \"");
        stringBuilder.append(this.getClass().getPackage().getSpecificationVersion());
        stringBuilder.append("\"");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }
}

