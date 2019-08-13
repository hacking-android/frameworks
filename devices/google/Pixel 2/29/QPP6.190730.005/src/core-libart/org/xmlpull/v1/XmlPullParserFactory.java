/*
 * Decompiled with CFR 0.145.
 */
package org.xmlpull.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlPullParserFactory {
    public static final String PROPERTY_NAME = "org.xmlpull.v1.XmlPullParserFactory";
    protected String classNamesLocation = null;
    protected HashMap<String, Boolean> features = new HashMap();
    protected ArrayList parserClasses = new ArrayList();
    protected ArrayList serializerClasses = new ArrayList();

    protected XmlPullParserFactory() {
        try {
            this.parserClasses.add(Class.forName("com.android.org.kxml2.io.KXmlParser"));
            this.serializerClasses.add(Class.forName("com.android.org.kxml2.io.KXmlSerializer"));
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new AssertionError();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private XmlPullParser getParserInstance() throws XmlPullParserException {
        ArrayList<Exception> arrayList = null;
        Object object = this.parserClasses;
        Object object2 = arrayList;
        if (object == null) throw XmlPullParserFactory.newInstantiationException("Invalid parser class list", object2);
        object2 = arrayList;
        if (((ArrayList)object).isEmpty()) throw XmlPullParserFactory.newInstantiationException("Invalid parser class list", object2);
        arrayList = new ArrayList<Exception>();
        object = this.parserClasses.iterator();
        do {
            object2 = arrayList;
            if (!object.hasNext()) throw XmlPullParserFactory.newInstantiationException("Invalid parser class list", object2);
            object2 = object.next();
            if (object2 == null) continue;
            try {
                return (XmlPullParser)((Class)object2).newInstance();
            }
            catch (ClassCastException classCastException) {
                arrayList.add(classCastException);
                continue;
            }
            catch (IllegalAccessException illegalAccessException) {
                arrayList.add(illegalAccessException);
                continue;
            }
            catch (InstantiationException instantiationException) {
                arrayList.add(instantiationException);
                continue;
            }
            break;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private XmlSerializer getSerializerInstance() throws XmlPullParserException {
        ArrayList<Exception> arrayList = null;
        Object object = this.serializerClasses;
        Object object2 = arrayList;
        if (object == null) throw XmlPullParserFactory.newInstantiationException("Invalid serializer class list", object2);
        object2 = arrayList;
        if (((ArrayList)object).isEmpty()) throw XmlPullParserFactory.newInstantiationException("Invalid serializer class list", object2);
        arrayList = new ArrayList<Exception>();
        object = this.serializerClasses.iterator();
        do {
            object2 = arrayList;
            if (!object.hasNext()) throw XmlPullParserFactory.newInstantiationException("Invalid serializer class list", object2);
            object2 = object.next();
            if (object2 == null) continue;
            try {
                return (XmlSerializer)((Class)object2).newInstance();
            }
            catch (ClassCastException classCastException) {
                arrayList.add(classCastException);
                continue;
            }
            catch (IllegalAccessException illegalAccessException) {
                arrayList.add(illegalAccessException);
                continue;
            }
            catch (InstantiationException instantiationException) {
                arrayList.add(instantiationException);
                continue;
            }
            break;
        } while (true);
    }

    public static XmlPullParserFactory newInstance() throws XmlPullParserException {
        return new XmlPullParserFactory();
    }

    public static XmlPullParserFactory newInstance(String string, Class class_) throws XmlPullParserException {
        return XmlPullParserFactory.newInstance();
    }

    private static XmlPullParserException newInstantiationException(String object, ArrayList<Exception> object2) {
        if (object2 != null && !((ArrayList)object2).isEmpty()) {
            object = new XmlPullParserException((String)object);
            object2 = ((ArrayList)object2).iterator();
            while (object2.hasNext()) {
                ((Throwable)object).addSuppressed((Exception)object2.next());
            }
            return object;
        }
        return new XmlPullParserException((String)object);
    }

    public boolean getFeature(String object) {
        boolean bl = (object = this.features.get(object)) != null ? (Boolean)object : false;
        return bl;
    }

    public boolean isNamespaceAware() {
        return this.getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces");
    }

    public boolean isValidating() {
        return this.getFeature("http://xmlpull.org/v1/doc/features.html#validation");
    }

    public XmlPullParser newPullParser() throws XmlPullParserException {
        XmlPullParser xmlPullParser = this.getParserInstance();
        for (Map.Entry<String, Boolean> entry : this.features.entrySet()) {
            if (!entry.getValue().booleanValue()) continue;
            xmlPullParser.setFeature(entry.getKey(), entry.getValue());
        }
        return xmlPullParser;
    }

    public XmlSerializer newSerializer() throws XmlPullParserException {
        return this.getSerializerInstance();
    }

    public void setFeature(String string, boolean bl) throws XmlPullParserException {
        this.features.put(string, bl);
    }

    public void setNamespaceAware(boolean bl) {
        this.features.put("http://xmlpull.org/v1/doc/features.html#process-namespaces", bl);
    }

    public void setValidating(boolean bl) {
        this.features.put("http://xmlpull.org/v1/doc/features.html#validation", bl);
    }
}

