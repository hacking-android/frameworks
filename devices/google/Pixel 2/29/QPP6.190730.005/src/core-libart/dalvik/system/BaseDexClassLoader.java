/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.system.DexPathList;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.misc.CompoundEnumeration;

public class BaseDexClassLoader
extends ClassLoader {
    private static volatile Reporter reporter = null;
    @UnsupportedAppUsage
    private final DexPathList pathList;
    protected final ClassLoader[] sharedLibraryLoaders;

    public BaseDexClassLoader(String string, File file, String string2, ClassLoader classLoader) {
        this(string, string2, classLoader, null, false);
    }

    @UnsupportedAppUsage
    public BaseDexClassLoader(String string, File file, String string2, ClassLoader classLoader, boolean bl) {
        this(string, string2, classLoader, null, bl);
    }

    public BaseDexClassLoader(String string, String string2, ClassLoader classLoader, ClassLoader[] arrclassLoader) {
        this(string, string2, classLoader, arrclassLoader, false);
    }

    public BaseDexClassLoader(String string, String string2, ClassLoader object, ClassLoader[] arrclassLoader, boolean bl) {
        super((ClassLoader)object);
        object = arrclassLoader == null ? null : Arrays.copyOf(arrclassLoader, arrclassLoader.length);
        this.sharedLibraryLoaders = object;
        this.pathList = new DexPathList(this, string, string2, null, bl);
        if (reporter != null) {
            this.reportClassLoaderChain();
        }
    }

    public BaseDexClassLoader(ByteBuffer[] arrbyteBuffer, String string, ClassLoader classLoader) {
        super(classLoader);
        this.sharedLibraryLoaders = null;
        this.pathList = new DexPathList(this, string);
        this.pathList.initByteBufferDexPath(arrbyteBuffer);
    }

    public static Reporter getReporter() {
        return reporter;
    }

    private void reportClassLoaderChain() {
        ArrayList<ClassLoader> arrayList = new ArrayList<ClassLoader>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList.add(this);
        arrayList2.add(String.join((CharSequence)File.pathSeparator, this.pathList.getDexPaths()));
        ClassLoader classLoader = ClassLoader.getSystemClassLoader().getParent();
        for (ClassLoader classLoader2 = this.getParent(); classLoader2 != null && classLoader2 != classLoader; classLoader2 = classLoader2.getParent()) {
            arrayList.add(classLoader2);
            if (classLoader2 instanceof BaseDexClassLoader) {
                BaseDexClassLoader baseDexClassLoader = (BaseDexClassLoader)classLoader2;
                arrayList2.add(String.join((CharSequence)File.pathSeparator, baseDexClassLoader.pathList.getDexPaths()));
                continue;
            }
            arrayList2.add(null);
        }
        reporter.report(arrayList, arrayList2);
    }

    public static void setReporter(Reporter reporter) {
        BaseDexClassLoader.reporter = reporter;
    }

    @UnsupportedAppUsage
    public void addDexPath(String string) {
        this.addDexPath(string, false);
    }

    @UnsupportedAppUsage
    public void addDexPath(String string, boolean bl) {
        this.pathList.addDexPath(string, null, bl);
    }

    public void addNativePath(Collection<String> collection) {
        this.pathList.addNativePath(collection);
    }

    @Override
    protected Class<?> findClass(String object) throws ClassNotFoundException {
        Serializable serializable;
        Object object2 = this.sharedLibraryLoaders;
        if (object2 != null) {
            int n = ((ClassLoader[])object2).length;
            for (int i = 0; i < n; ++i) {
                Object object3 = object2[i];
                try {
                    object3 = ((ClassLoader)object3).loadClass((String)object);
                    return object3;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    continue;
                }
            }
        }
        if ((serializable = this.pathList.findClass((String)object, (List<Throwable>)(object2 = new ArrayList()))) == null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Didn't find class \"");
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append("\" on path: ");
            ((StringBuilder)serializable).append(this.pathList);
            object = new ClassNotFoundException(((StringBuilder)serializable).toString());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ((Throwable)object).addSuppressed((Throwable)object2.next());
            }
            throw object;
        }
        return serializable;
    }

    @Override
    public String findLibrary(String string) {
        return this.pathList.findLibrary(string);
    }

    @Override
    protected URL findResource(String string) {
        ClassLoader[] arrclassLoader = this.sharedLibraryLoaders;
        if (arrclassLoader != null) {
            int n = arrclassLoader.length;
            for (int i = 0; i < n; ++i) {
                URL uRL = arrclassLoader[i].getResource(string);
                if (uRL == null) continue;
                return uRL;
            }
        }
        return this.pathList.findResource(string);
    }

    @Override
    protected Enumeration<URL> findResources(String string) {
        ClassLoader[] arrclassLoader;
        Enumeration<URL> enumeration = this.pathList.findResources(string);
        Object[] arrobject = this.sharedLibraryLoaders;
        if (arrobject == null) {
            return enumeration;
        }
        arrobject = new Enumeration[arrobject.length + 1];
        for (int i = 0; i < (arrclassLoader = this.sharedLibraryLoaders).length; ++i) {
            try {
                arrobject[i] = arrclassLoader[i].getResources(string);
                continue;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        arrobject[arrclassLoader.length] = enumeration;
        return new CompoundEnumeration<URL>((Enumeration<E>[])arrobject);
    }

    @UnsupportedAppUsage
    public String getLdLibraryPath() {
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : this.pathList.getNativeLibraryDirectories()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(':');
            }
            stringBuilder.append(file);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Package getPackage(String string) {
        synchronized (this) {
            Package package_;
            if (string == null) return null;
            if (string.isEmpty()) return null;
            Package package_2 = package_ = super.getPackage(string);
            if (package_ != null) return package_2;
            return this.definePackage(string, "Unknown", "0.0", "Unknown", "Unknown", "0.0", "Unknown", null);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[");
        stringBuilder.append(this.pathList);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static interface Reporter {
        public void report(List<ClassLoader> var1, List<String> var2);
    }

}

