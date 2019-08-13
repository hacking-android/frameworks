/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.SharedLibraryInfo;
import android.os.Build;
import android.os.GraphicsEnvironment;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.os.ClassLoaderFactory;
import dalvik.system.PathClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApplicationLoaders {
    private static final String TAG = "ApplicationLoaders";
    private static final ApplicationLoaders gApplicationLoaders = new ApplicationLoaders();
    @UnsupportedAppUsage
    private final ArrayMap<String, ClassLoader> mLoaders = new ArrayMap();
    private Map<String, CachedClassLoader> mSystemLibsCacheMap = null;

    private void createAndCacheNonBootclasspathSystemClassLoader(SharedLibraryInfo object) {
        Object object2;
        String string2 = ((SharedLibraryInfo)object).getPath();
        Object object3 = ((SharedLibraryInfo)object).getDependencies();
        if (object3 != null) {
            object = new ArrayList(object3.size());
            object2 = object3.iterator();
            while (object2.hasNext()) {
                object3 = object2.next().getPath();
                CachedClassLoader cachedClassLoader = this.mSystemLibsCacheMap.get(object3);
                if (cachedClassLoader != null) {
                    ((ArrayList)object).add(cachedClassLoader.loader);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to find dependency ");
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(" of cachedlibrary ");
                ((StringBuilder)object).append(string2);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        } else {
            object = null;
        }
        if ((object2 = this.getClassLoader(string2, Build.VERSION.SDK_INT, true, null, null, null, null, null, (List<ClassLoader>)object)) != null) {
            object3 = new CachedClassLoader();
            ((CachedClassLoader)object3).loader = object2;
            ((CachedClassLoader)object3).sharedLibraries = object;
            object = new StringBuilder();
            ((StringBuilder)object).append("Created zygote-cached class loader: ");
            ((StringBuilder)object).append(string2);
            Log.d(TAG, ((StringBuilder)object).toString());
            this.mSystemLibsCacheMap.put(string2, (CachedClassLoader)object3);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to cache ");
        ((StringBuilder)object).append(string2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ClassLoader getClassLoader(String object, int n, boolean bl, String string2, String string3, ClassLoader classLoader, String string4, String string5, List<ClassLoader> list) {
        ClassLoader classLoader2 = ClassLoader.getSystemClassLoader().getParent();
        ArrayMap<String, ClassLoader> arrayMap = this.mLoaders;
        synchronized (arrayMap) {
            void var1_4;
            if (classLoader == null) {
                classLoader = classLoader2;
            }
            if (classLoader == classLoader2) {
                try {
                    classLoader2 = this.mLoaders.get(string4);
                    if (classLoader2 != null) {
                        return classLoader2;
                    }
                    Trace.traceBegin(64L, (String)object);
                    classLoader = ClassLoaderFactory.createClassLoader((String)object, string2, string3, classLoader, n, bl, string5, list);
                    Trace.traceEnd(64L);
                    Trace.traceBegin(64L, "setLayerPaths");
                    object = GraphicsEnvironment.getInstance();
                    ((GraphicsEnvironment)object).setLayerPaths(classLoader, string2, string3);
                }
                catch (Throwable throwable) {}
                Trace.traceEnd(64L);
                if (string4 != null) {
                    this.mLoaders.put(string4, classLoader);
                }
                return classLoader;
            } else {
                Trace.traceBegin(64L, (String)object);
                try {
                    object = ClassLoaderFactory.createClassLoader((String)object, null, classLoader, string5, list);
                    Trace.traceEnd(64L);
                    return object;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            throw var1_4;
        }
    }

    @UnsupportedAppUsage
    public static ApplicationLoaders getDefault() {
        return gApplicationLoaders;
    }

    private static boolean sharedLibrariesEquals(List<ClassLoader> list, List<ClassLoader> list2) {
        if (list == null) {
            boolean bl = list2 == null;
            return bl;
        }
        return list.equals(list2);
    }

    void addNative(ClassLoader classLoader, Collection<String> collection) {
        if (classLoader instanceof PathClassLoader) {
            ((PathClassLoader)classLoader).addNativePath(collection);
            return;
        }
        throw new IllegalStateException("class loader is not a PathClassLoader");
    }

    void addPath(ClassLoader classLoader, String string2) {
        if (classLoader instanceof PathClassLoader) {
            ((PathClassLoader)classLoader).addDexPath(string2);
            return;
        }
        throw new IllegalStateException("class loader is not a PathClassLoader");
    }

    public void createAndCacheNonBootclasspathSystemClassLoaders(SharedLibraryInfo[] arrsharedLibraryInfo) {
        if (this.mSystemLibsCacheMap == null) {
            this.mSystemLibsCacheMap = new HashMap<String, CachedClassLoader>();
            int n = arrsharedLibraryInfo.length;
            for (int i = 0; i < n; ++i) {
                this.createAndCacheNonBootclasspathSystemClassLoader(arrsharedLibraryInfo[i]);
            }
            return;
        }
        throw new IllegalStateException("Already cached.");
    }

    public ClassLoader createAndCacheWebViewClassLoader(String string2, String string3, String string4) {
        return this.getClassLoader(string2, Build.VERSION.SDK_INT, false, string3, null, null, string4, null, null);
    }

    public ClassLoader getCachedNonBootclasspathSystemLib(String charSequence, ClassLoader object, String charSequence2, List<ClassLoader> list) {
        Map<String, CachedClassLoader> map = this.mSystemLibsCacheMap;
        if (map == null) {
            return null;
        }
        if (object == null && charSequence2 == null) {
            object = map.get(charSequence);
            if (object == null) {
                return null;
            }
            if (!ApplicationLoaders.sharedLibrariesEquals(list, ((CachedClassLoader)object).sharedLibraries)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unexpected environment for cached library: (");
                ((StringBuilder)charSequence).append(list);
                ((StringBuilder)charSequence).append("|");
                ((StringBuilder)charSequence).append(((CachedClassLoader)object).sharedLibraries);
                ((StringBuilder)charSequence).append(")");
                Log.w(TAG, ((StringBuilder)charSequence).toString());
                return null;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Returning zygote-cached class loader: ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            Log.d(TAG, ((StringBuilder)charSequence2).toString());
            return ((CachedClassLoader)object).loader;
        }
        return null;
    }

    ClassLoader getClassLoader(String string2, int n, boolean bl, String string3, String string4, ClassLoader classLoader, String string5) {
        return this.getClassLoaderWithSharedLibraries(string2, n, bl, string3, string4, classLoader, string5, null);
    }

    ClassLoader getClassLoaderWithSharedLibraries(String string2, int n, boolean bl, String string3, String string4, ClassLoader classLoader, String string5, List<ClassLoader> list) {
        return this.getClassLoader(string2, n, bl, string3, string4, classLoader, string2, string5, list);
    }

    ClassLoader getSharedLibraryClassLoaderWithSharedLibraries(String string2, int n, boolean bl, String string3, String string4, ClassLoader classLoader, String string5, List<ClassLoader> list) {
        ClassLoader classLoader2 = this.getCachedNonBootclasspathSystemLib(string2, classLoader, string5, list);
        if (classLoader2 != null) {
            return classLoader2;
        }
        return this.getClassLoaderWithSharedLibraries(string2, n, bl, string3, string4, classLoader, string5, list);
    }

    private static class CachedClassLoader {
        ClassLoader loader;
        List<ClassLoader> sharedLibraries;

        private CachedClassLoader() {
        }
    }

}

