/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.util.Log;
import dalvik.system.PathClassLoader;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Iterator;

public class FilterFactory {
    private static final String TAG = "FilterFactory";
    private static Object mClassLoaderGuard;
    private static ClassLoader mCurrentClassLoader;
    private static HashSet<String> mLibraries;
    private static boolean mLogVerbose;
    private static FilterFactory mSharedFactory;
    private HashSet<String> mPackages = new HashSet();

    static {
        mCurrentClassLoader = Thread.currentThread().getContextClassLoader();
        mLibraries = new HashSet();
        mClassLoaderGuard = new Object();
        mLogVerbose = Log.isLoggable(TAG, 2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addFilterLibrary(String string2) {
        Object object;
        if (mLogVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Adding filter library ");
            ((StringBuilder)object).append(string2);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        object = mClassLoaderGuard;
        synchronized (object) {
            if (!mLibraries.contains(string2)) {
                mLibraries.add(string2);
                PathClassLoader pathClassLoader = new PathClassLoader(string2, mCurrentClassLoader);
                mCurrentClassLoader = pathClassLoader;
                return;
            }
            if (mLogVerbose) {
                Log.v(TAG, "Library already added");
            }
            return;
        }
    }

    public static FilterFactory sharedFactory() {
        if (mSharedFactory == null) {
            mSharedFactory = new FilterFactory();
        }
        return mSharedFactory;
    }

    public void addPackage(String string2) {
        if (mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding package ");
            stringBuilder.append(string2);
            Log.v(TAG, stringBuilder.toString());
        }
        this.mPackages.add(string2);
    }

    public Filter createFilterByClass(Class object, String string2) {
        Object object2;
        try {
            ((Class)object).asSubclass(Filter.class);
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempting to allocate class '");
            stringBuilder.append(object);
            stringBuilder.append("' which is not a subclass of Filter!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        try {
            object2 = ((Class)object).getConstructor(String.class);
            object = null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The filter class '");
            stringBuilder.append(object);
            stringBuilder.append("' does not have a constructor of the form <init>(String name)!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        try {
            object = object2 = (Filter)((Constructor)object2).newInstance(string2);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not construct the filter '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'!");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Filter createFilterByClassName(String string2, String charSequence) {
        Serializable serializable;
        StringBuilder stringBuilder;
        if (mLogVerbose) {
            serializable = new StringBuilder();
            serializable.append("Looking up class ");
            serializable.append(string2);
            Log.v(TAG, serializable.toString());
        }
        serializable = null;
        Iterator<String> iterator = this.mPackages.iterator();
        do {
            ClassLoader classLoader;
            StringBuilder stringBuilder2;
            stringBuilder = serializable;
            if (!iterator.hasNext()) break;
            String string3 = iterator.next();
            stringBuilder = serializable;
            try {
                Object object;
                if (mLogVerbose) {
                    stringBuilder = serializable;
                    stringBuilder = serializable;
                    object = new StringBuilder();
                    stringBuilder = serializable;
                    ((StringBuilder)object).append("Trying ");
                    stringBuilder = serializable;
                    ((StringBuilder)object).append(string3);
                    stringBuilder = serializable;
                    ((StringBuilder)object).append(".");
                    stringBuilder = serializable;
                    ((StringBuilder)object).append(string2);
                    stringBuilder = serializable;
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
                stringBuilder = serializable;
                object = mClassLoaderGuard;
                stringBuilder = serializable;
                synchronized (object) {
                    stringBuilder = serializable;
                    classLoader = mCurrentClassLoader;
                    stringBuilder = serializable;
                    stringBuilder = serializable;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder = serializable;
                    stringBuilder2.append(string3);
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                serializable = stringBuilder;
                continue;
            }
            {
                stringBuilder = serializable;
                stringBuilder2.append(".");
                stringBuilder = serializable;
                stringBuilder2.append(string2);
                stringBuilder = serializable;
                stringBuilder = serializable = classLoader.loadClass(stringBuilder2.toString());
                if (serializable == null) continue;
            }
            stringBuilder = serializable;
            break;
        } while (true);
        if (stringBuilder != null) {
            return this.createFilterByClass((Class)((Object)stringBuilder), (String)charSequence);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unknown filter class '");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("'!");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }
}

