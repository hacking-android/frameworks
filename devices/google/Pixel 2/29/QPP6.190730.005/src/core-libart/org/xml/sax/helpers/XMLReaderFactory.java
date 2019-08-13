/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.NewInstance;
import org.xml.sax.helpers.ParserAdapter;
import org.xml.sax.helpers.ParserFactory;

public final class XMLReaderFactory {
    private static final String property = "org.xml.sax.driver";

    private XMLReaderFactory() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static XMLReader createXMLReader() throws SAXException {
        block10 : {
            block11 : {
                object2 = null;
                classLoader = NewInstance.getClassLoader();
                try {
                    object2 = object = System.getProperty("org.xml.sax.driver");
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
                object = object2;
                if (object2 != null) break block10;
                if (classLoader != null) ** GOTO lbl15
                object = object2;
                inputStream = ClassLoader.getSystemResourceAsStream("META-INF/services/org.xml.sax.driver");
                break block11;
lbl15: // 1 sources:
                object = object2;
                inputStream = classLoader.getResourceAsStream("META-INF/services/org.xml.sax.driver");
            }
            object = object2;
            if (inputStream == null) break block10;
            object = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader((Reader)object);
            object = object2 = (object = bufferedReader.readLine());
            {
                catch (Throwable throwable) {
                    object = object2;
                    inputStream.close();
                    object = object2;
                    throw throwable;
                }
            }
            try {
                inputStream.close();
                object = object2;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (object != null) {
            return XMLReaderFactory.loadClass(classLoader, (String)object);
        }
        try {
            return new ParserAdapter(ParserFactory.makeParser());
        }
        catch (Exception exception) {
            throw new SAXException("Can't create default XMLReader; is system property org.xml.sax.driver set?");
        }
    }

    public static XMLReader createXMLReader(String string) throws SAXException {
        return XMLReaderFactory.loadClass(NewInstance.getClassLoader(), string);
    }

    @UnsupportedAppUsage
    private static XMLReader loadClass(ClassLoader object, String string) throws SAXException {
        try {
            object = (XMLReader)NewInstance.newInstance((ClassLoader)object, string);
            return object;
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX2 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" does not implement XMLReader");
            throw new SAXException(stringBuilder.toString(), classCastException);
        }
        catch (InstantiationException instantiationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("SAX2 driver class ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" loaded but cannot be instantiated (no empty public constructor?)");
            throw new SAXException(((StringBuilder)object).toString(), instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX2 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" found but cannot be loaded");
            throw new SAXException(stringBuilder.toString(), illegalAccessException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SAX2 driver class ");
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new SAXException(stringBuilder.toString(), classNotFoundException);
        }
    }
}

