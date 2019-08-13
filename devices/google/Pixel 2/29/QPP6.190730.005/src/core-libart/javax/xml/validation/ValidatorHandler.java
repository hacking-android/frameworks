/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.validation;

import javax.xml.validation.TypeInfoProvider;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public abstract class ValidatorHandler
implements ContentHandler {
    protected ValidatorHandler() {
    }

    public abstract ContentHandler getContentHandler();

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

    public abstract TypeInfoProvider getTypeInfoProvider();

    public abstract void setContentHandler(ContentHandler var1);

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

