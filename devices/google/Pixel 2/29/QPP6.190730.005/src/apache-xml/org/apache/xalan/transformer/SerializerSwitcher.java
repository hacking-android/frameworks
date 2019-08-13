/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;

public class SerializerSwitcher {
    private static String getOutputPropertyNoDefault(String string, Properties properties) throws IllegalArgumentException {
        return (String)properties.get(string);
    }

    public static Serializer switchSerializerIfHTML(String object, String string, Properties properties, Serializer serializer) throws TransformerException {
        Serializer serializer2;
        block10 : {
            Serializer serializer3;
            block9 : {
                serializer3 = serializer;
                if (object == null) break block9;
                serializer2 = serializer3;
                if (((String)object).length() != 0) break block10;
            }
            serializer2 = serializer3;
            if (string.equalsIgnoreCase("html")) {
                if (SerializerSwitcher.getOutputPropertyNoDefault("method", properties) != null) {
                    return serializer3;
                }
                object = new OutputProperties("html");
                ((OutputProperties)object).copyFrom(properties, true);
                object = ((OutputProperties)object).getProperties();
                serializer2 = serializer3;
                if (serializer != null) {
                    serializer2 = SerializerFactory.getSerializer((Properties)object);
                    object = serializer.getWriter();
                    if (object != null) {
                        serializer2.setWriter((Writer)object);
                    } else {
                        object = serializer2.getOutputStream();
                        if (object != null) {
                            serializer2.setOutputStream((OutputStream)object);
                        }
                    }
                }
            }
        }
        return serializer2;
    }

    public static void switchSerializerIfHTML(TransformerImpl object, String object2, String string) throws TransformerException {
        if (object == null) {
            return;
        }
        if ((object2 == null || ((String)object2).length() == 0) && string.equalsIgnoreCase("html")) {
            if (((TransformerImpl)object).getOutputPropertyNoDefault("method") != null) {
                return;
            }
            object2 = ((TransformerImpl)object).getOutputFormat().getProperties();
            object = new OutputProperties("html");
            ((OutputProperties)object).copyFrom((Properties)object2, true);
            object = ((OutputProperties)object).getProperties();
            if (false) {
                try {
                    SerializerFactory.getSerializer((Properties)object);
                    throw new NullPointerException();
                }
                catch (IOException iOException) {
                    throw new TransformerException(iOException);
                }
            }
        }
    }
}

