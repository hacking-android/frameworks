/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.Hashtable;
import java.util.Properties;
import org.apache.xml.serializer.ObjectFactory;
import org.apache.xml.serializer.OutputPropertiesFactory;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerConstants;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.apache.xml.serializer.utils.WrappedRuntimeException;
import org.xml.sax.ContentHandler;

public final class SerializerFactory {
    private static Hashtable m_formats = new Hashtable();

    private SerializerFactory() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Serializer getSerializer(Properties object) {
        try {
            Object t;
            ClassLoader classLoader;
            Object object2 = ((Properties)object).getProperty("method");
            if (object2 == null) {
                String string = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[]{"method"});
                object = new IllegalArgumentException(string);
                throw object;
            }
            String string = ((Properties)object).getProperty("{http://xml.apache.org/xalan}content-handler");
            Object object3 = string;
            if (string == null) {
                object3 = OutputPropertiesFactory.getDefaultMethodProperties((String)object2);
                if ((object3 = ((Properties)object3).getProperty("{http://xml.apache.org/xalan}content-handler")) == null) {
                    object = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[]{"{http://xml.apache.org/xalan}content-handler"});
                    object3 = new IllegalArgumentException((String)object);
                    throw object3;
                }
            }
            if ((t = ((Class)(object2 = ObjectFactory.findProviderClass((String)object3, classLoader = ObjectFactory.findClassLoader(), true))).newInstance()) instanceof SerializationHandler) {
                object3 = (Serializer)((Class)object2).newInstance();
                object3.setOutputFormat((Properties)object);
                return object3;
            }
            if (t instanceof ContentHandler) {
                object3 = ObjectFactory.findProviderClass(SerializerConstants.DEFAULT_SAX_SERIALIZER, classLoader, true);
                object3 = (SerializationHandler)((Class)object3).newInstance();
                object3.setContentHandler((ContentHandler)t);
                object3.setOutputFormat((Properties)object);
                return object3;
            }
            object = new Exception(Utils.messages.createMessage("ER_SERIALIZER_NOT_CONTENTHANDLER", new Object[]{object3}));
            throw object;
        }
        catch (Exception exception) {
            throw new WrappedRuntimeException(exception);
        }
    }
}

