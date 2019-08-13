/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.validation;

import java.io.File;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactoryFinder;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public abstract class SchemaFactory {
    protected SchemaFactory() {
    }

    public static SchemaFactory newInstance(String string) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object object = classLoader;
        if (classLoader == null) {
            object = SchemaFactory.class.getClassLoader();
        }
        if ((object = new SchemaFactoryFinder((ClassLoader)object).newFactory(string)) != null) {
            return object;
        }
        throw new IllegalArgumentException(string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static SchemaFactory newInstance(String var0, String var1_4, ClassLoader var2_5) {
        if (var0 == null) throw new NullPointerException("schemaLanguage == null");
        if (var1_4 == null) throw new NullPointerException("factoryClassName == null");
        var3_6 = var2_5;
        if (var2_5 == null) {
            var3_6 = Thread.currentThread().getContextClassLoader();
        }
        if (var3_6 == null) ** GOTO lbl10
        try {
            block6 : {
                var1_4 = var3_6.loadClass((String)var1_4);
                break block6;
lbl10: // 1 sources:
                var1_4 = Class.forName((String)var1_4);
            }
            var1_4 = (SchemaFactory)var1_4.newInstance();
            if (var1_4 != null && var1_4.isSchemaLanguageSupported(var0)) {
                return var1_4;
            }
            var1_4 = new IllegalArgumentException(var0);
            throw var1_4;
        }
        catch (IllegalAccessException var0_1) {
            throw new IllegalArgumentException(var0_1);
        }
        catch (InstantiationException var0_2) {
            throw new IllegalArgumentException(var0_2);
        }
        catch (ClassNotFoundException var0_3) {
            throw new IllegalArgumentException(var0_3);
        }
    }

    public abstract ErrorHandler getErrorHandler();

    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string == null) {
            throw new NullPointerException("name == null");
        }
        throw new SAXNotRecognizedException(string);
    }

    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string == null) {
            throw new NullPointerException("name == null");
        }
        throw new SAXNotRecognizedException(string);
    }

    public abstract LSResourceResolver getResourceResolver();

    public abstract boolean isSchemaLanguageSupported(String var1);

    public abstract Schema newSchema() throws SAXException;

    public Schema newSchema(File file) throws SAXException {
        return this.newSchema(new StreamSource(file));
    }

    public Schema newSchema(URL uRL) throws SAXException {
        return this.newSchema(new StreamSource(uRL.toExternalForm()));
    }

    public Schema newSchema(Source source) throws SAXException {
        return this.newSchema(new Source[]{source});
    }

    public abstract Schema newSchema(Source[] var1) throws SAXException;

    public abstract void setErrorHandler(ErrorHandler var1);

    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string == null) {
            throw new NullPointerException("name == null");
        }
        throw new SAXNotRecognizedException(string);
    }

    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (string == null) {
            throw new NullPointerException("name == null");
        }
        throw new SAXNotRecognizedException(string);
    }

    public abstract void setResourceResolver(LSResourceResolver var1);
}

