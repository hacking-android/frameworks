/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.preference;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
abstract class GenericInflater<T, P extends Parent> {
    private static final Class[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final HashMap sConstructorMap = new HashMap();
    private final boolean DEBUG;
    private final Object[] mConstructorArgs = new Object[2];
    protected final Context mContext;
    private String mDefaultPackage;
    private Factory<T> mFactory;
    private boolean mFactorySet;

    protected GenericInflater(Context context) {
        this.DEBUG = false;
        this.mContext = context;
    }

    protected GenericInflater(GenericInflater<T, P> genericInflater, Context context) {
        this.DEBUG = false;
        this.mContext = context;
        this.mFactory = genericInflater.mFactory;
    }

    private final T createItemFromTag(XmlPullParser object, String object2, AttributeSet attributeSet) {
        StringBuilder stringBuilder;
        block8 : {
            block7 : {
                if (this.mFactory != null) break block7;
                object = null;
                break block8;
            }
            try {
                object = this.mFactory.onCreateItem((String)object2, this.mContext, attributeSet);
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(attributeSet.getPositionDescription());
                stringBuilder.append(": Error inflating class ");
                stringBuilder.append((String)object2);
                object2 = new InflateException(stringBuilder.toString());
                ((Throwable)object2).initCause(exception);
                throw object2;
            }
            catch (ClassNotFoundException classNotFoundException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(attributeSet.getPositionDescription());
                stringBuilder.append(": Error inflating class ");
                stringBuilder.append((String)object2);
                object2 = new InflateException(stringBuilder.toString());
                ((Throwable)object2).initCause(classNotFoundException);
                throw object2;
            }
            catch (InflateException inflateException) {
                throw inflateException;
            }
        }
        stringBuilder = object;
        if (object == null) {
            stringBuilder = -1 == ((String)object2).indexOf(46) ? this.onCreateItem((String)object2, attributeSet) : this.createItem((String)object2, null, attributeSet);
        }
        return (T)stringBuilder;
    }

    private void rInflate(XmlPullParser xmlPullParser, T t, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2 || this.onCreateCustomFromTag(xmlPullParser, t, attributeSet)) continue;
            T t2 = this.createItemFromTag(xmlPullParser, xmlPullParser.getName(), attributeSet);
            ((Parent)t).addItemFromInflater(t2);
            this.rInflate(xmlPullParser, t2, attributeSet);
        }
    }

    public abstract GenericInflater cloneInContext(Context var1);

    public final T createItem(String object, String object2, AttributeSet object3) throws ClassNotFoundException, InflateException {
        Object object4;
        Object[] arrobject = (Object[])sConstructorMap.get(object);
        Object object5 = arrobject;
        if (arrobject == null) {
            ClassLoader classLoader;
            block17 : {
                block16 : {
                    object4 = arrobject;
                    classLoader = this.mContext.getClassLoader();
                    if (object2 == null) break block16;
                    object4 = arrobject;
                    object4 = arrobject;
                    object5 = new StringBuilder();
                    object4 = arrobject;
                    ((StringBuilder)object5).append((String)object2);
                    object4 = arrobject;
                    ((StringBuilder)object5).append((String)object);
                    object4 = arrobject;
                    object5 = ((StringBuilder)object5).toString();
                    break block17;
                }
                object5 = object;
            }
            object4 = arrobject;
            object5 = classLoader.loadClass((String)object5).getConstructor(mConstructorSignature);
            object4 = object5;
            ((AccessibleObject)object5).setAccessible(true);
            object4 = object5;
            sConstructorMap.put(object, object5);
        }
        object4 = object5;
        arrobject = this.mConstructorArgs;
        arrobject[1] = object3;
        object4 = object5;
        try {
            object5 = ((Constructor)object5).newInstance(arrobject);
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(object3.getPositionDescription());
            ((StringBuilder)object2).append(": Error inflating class ");
            ((StringBuilder)object2).append(object4.getClass().getName());
            object2 = new InflateException(((StringBuilder)object2).toString());
            ((Throwable)object2).initCause(exception);
            throw object2;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw classNotFoundException;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object5 = new StringBuilder();
            ((StringBuilder)object5).append(object3.getPositionDescription());
            ((StringBuilder)object5).append(": Error inflating class ");
            if (object2 != null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object2);
                ((StringBuilder)object3).append((String)object);
                object = ((StringBuilder)object3).toString();
            }
            ((StringBuilder)object5).append((String)object);
            object = new InflateException(((StringBuilder)object5).toString());
            ((Throwable)object).initCause(noSuchMethodException);
            throw object;
        }
        return (T)object5;
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDefaultPackage() {
        return this.mDefaultPackage;
    }

    public final Factory<T> getFactory() {
        return this.mFactory;
    }

    public T inflate(int n, P p) {
        boolean bl = p != null;
        return this.inflate(n, p, bl);
    }

    public T inflate(int n, P object, boolean bl) {
        XmlResourceParser xmlResourceParser = this.getContext().getResources().getXml(n);
        try {
            object = this.inflate(xmlResourceParser, object, bl);
            return (T)object;
        }
        finally {
            xmlResourceParser.close();
        }
    }

    public T inflate(XmlPullParser xmlPullParser, P p) {
        boolean bl = p != null;
        return this.inflate(xmlPullParser, p, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T inflate(XmlPullParser xmlPullParser, P object, boolean bl) {
        Object[] arrobject = this.mConstructorArgs;
        synchronized (arrobject) {
            Object object2 = Xml.asAttributeSet(xmlPullParser);
            this.mConstructorArgs[0] = this.mContext;
            try {
                int n;
                while ((n = xmlPullParser.next()) != 2 && n != 1) {
                }
                if (n == 2) {
                    object = this.onMergeRoots(object, bl, (Parent)this.createItemFromTag(xmlPullParser, xmlPullParser.getName(), (AttributeSet)object2));
                    this.rInflate(xmlPullParser, object, (AttributeSet)object2);
                    return (T)object;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(xmlPullParser.getPositionDescription());
                ((StringBuilder)object2).append(": No start tag found!");
                object = new InflateException(((StringBuilder)object2).toString());
                throw object;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(xmlPullParser.getPositionDescription());
                stringBuilder.append(": ");
                stringBuilder.append(iOException.getMessage());
                object = new InflateException(stringBuilder.toString());
                ((Throwable)object).initCause(iOException);
                throw object;
            }
            catch (XmlPullParserException xmlPullParserException) {
                object = new InflateException(xmlPullParserException.getMessage());
                ((Throwable)object).initCause(xmlPullParserException);
                throw object;
            }
            catch (InflateException inflateException) {
                throw inflateException;
            }
        }
    }

    protected boolean onCreateCustomFromTag(XmlPullParser xmlPullParser, T t, AttributeSet attributeSet) throws XmlPullParserException {
        return false;
    }

    protected T onCreateItem(String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        return this.createItem(string2, this.mDefaultPackage, attributeSet);
    }

    protected P onMergeRoots(P p, boolean bl, P p2) {
        return p2;
    }

    public void setDefaultPackage(String string2) {
        this.mDefaultPackage = string2;
    }

    public void setFactory(Factory<T> factory) {
        if (!this.mFactorySet) {
            if (factory != null) {
                this.mFactorySet = true;
                Factory<T> factory2 = this.mFactory;
                this.mFactory = factory2 == null ? factory : new FactoryMerger<T>(factory, factory2);
                return;
            }
            throw new NullPointerException("Given factory can not be null");
        }
        throw new IllegalStateException("A factory has already been set on this inflater");
    }

    public static interface Factory<T> {
        public T onCreateItem(String var1, Context var2, AttributeSet var3);
    }

    private static class FactoryMerger<T>
    implements Factory<T> {
        private final Factory<T> mF1;
        private final Factory<T> mF2;

        FactoryMerger(Factory<T> factory, Factory<T> factory2) {
            this.mF1 = factory;
            this.mF2 = factory2;
        }

        @Override
        public T onCreateItem(String string2, Context context, AttributeSet attributeSet) {
            T t = this.mF1.onCreateItem(string2, context, attributeSet);
            if (t != null) {
                return t;
            }
            return this.mF2.onCreateItem(string2, context, attributeSet);
        }
    }

    public static interface Parent<T> {
        public void addItemFromInflater(T var1);
    }

}

