/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 *  java.lang.VMClassLoader
 */
package java.lang;

import dalvik.system.PathClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sun.misc.CompoundEnumeration;
import sun.reflect.CallerSensitive;

public abstract class ClassLoader {
    private transient long allocator;
    private transient long classTable;
    private final HashMap<String, Package> packages = new HashMap();
    private final ClassLoader parent;
    public final Map<List<Class<?>>, Class<?>> proxyCache = new HashMap();

    protected ClassLoader() {
        this(ClassLoader.checkCreateClassLoader(), ClassLoader.getSystemClassLoader());
    }

    protected ClassLoader(ClassLoader classLoader) {
        this(ClassLoader.checkCreateClassLoader(), classLoader);
    }

    private ClassLoader(Void void_, ClassLoader classLoader) {
        this.parent = classLoader;
    }

    static /* synthetic */ ClassLoader access$000() {
        return ClassLoader.createSystemClassLoader();
    }

    private static Void checkCreateClassLoader() {
        return null;
    }

    private static ClassLoader createSystemClassLoader() {
        return new PathClassLoader(System.getProperty("java.class.path", "."), System.getProperty("java.library.path", ""), (ClassLoader)BootClassLoader.getInstance());
    }

    private Class<?> findBootstrapClassOrNull(String string) {
        return null;
    }

    private static URL getBootstrapResource(String string) {
        return null;
    }

    private static Enumeration<URL> getBootstrapResources(String string) throws IOException {
        return null;
    }

    static ClassLoader getClassLoader(Class<?> class_) {
        if (class_ == null) {
            return null;
        }
        return class_.getClassLoader();
    }

    @CallerSensitive
    public static ClassLoader getSystemClassLoader() {
        return SystemClassLoader.loader;
    }

    public static URL getSystemResource(String string) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        if (classLoader == null) {
            return ClassLoader.getBootstrapResource(string);
        }
        return classLoader.getResource(string);
    }

    public static InputStream getSystemResourceAsStream(String object) {
        URL uRL = ClassLoader.getSystemResource((String)object);
        object = null;
        if (uRL != null) {
            try {
                object = uRL.openStream();
            }
            catch (IOException iOException) {
                return null;
            }
        }
        return object;
    }

    public static Enumeration<URL> getSystemResources(String string) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        if (classLoader == null) {
            return ClassLoader.getBootstrapResources(string);
        }
        return classLoader.getResources(string);
    }

    @CallerSensitive
    protected static boolean registerAsParallelCapable() {
        return true;
    }

    public void clearAssertionStatus() {
    }

    protected final Class<?> defineClass(String string, ByteBuffer byteBuffer, ProtectionDomain protectionDomain) throws ClassFormatError {
        throw new UnsupportedOperationException("can't load this type of class file");
    }

    protected final Class<?> defineClass(String string, byte[] arrby, int n, int n2) throws ClassFormatError {
        throw new UnsupportedOperationException("can't load this type of class file");
    }

    protected final Class<?> defineClass(String string, byte[] arrby, int n, int n2, ProtectionDomain protectionDomain) throws ClassFormatError {
        throw new UnsupportedOperationException("can't load this type of class file");
    }

    @Deprecated
    protected final Class<?> defineClass(byte[] arrby, int n, int n2) throws ClassFormatError {
        throw new UnsupportedOperationException("can't load this type of class file");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Package definePackage(String string, String object, String string2, String string3, String string4, String string5, String string6, URL uRL) throws IllegalArgumentException {
        HashMap<String, Package> hashMap = this.packages;
        synchronized (hashMap) {
            if (this.packages.get(string) == null) {
                Package package_ = new Package(string, (String)object, string2, string3, string4, string5, string6, uRL, this);
                this.packages.put(string, package_);
                return package_;
            }
            object = new IllegalArgumentException(string);
            throw object;
        }
    }

    protected Class<?> findClass(String string) throws ClassNotFoundException {
        throw new ClassNotFoundException(string);
    }

    protected String findLibrary(String string) {
        return null;
    }

    protected final Class<?> findLoadedClass(String string) {
        ClassLoader classLoader = this == BootClassLoader.getInstance() ? null : this;
        return VMClassLoader.findLoadedClass((ClassLoader)classLoader, (String)string);
    }

    protected URL findResource(String string) {
        return null;
    }

    protected Enumeration<URL> findResources(String string) throws IOException {
        return Collections.emptyEnumeration();
    }

    protected final Class<?> findSystemClass(String string) throws ClassNotFoundException {
        return Class.forName(string, false, ClassLoader.getSystemClassLoader());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Package getPackage(String object) {
        HashMap<String, Package> hashMap = this.packages;
        synchronized (hashMap) {
            return this.packages.get(object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Package[] getPackages() {
        HashMap<String, Package> hashMap = this.packages;
        synchronized (hashMap) {
            HashMap<String, Package> hashMap2 = new HashMap<String, Package>(this.packages);
            return hashMap2.values().toArray(new Package[hashMap2.size()]);
        }
    }

    @CallerSensitive
    public final ClassLoader getParent() {
        return this.parent;
    }

    public URL getResource(String string) {
        Object object = this.parent;
        object = object != null ? ((ClassLoader)object).getResource(string) : ClassLoader.getBootstrapResource(string);
        Object object2 = object;
        if (object == null) {
            object2 = this.findResource(string);
        }
        return object2;
    }

    public InputStream getResourceAsStream(String object) {
        URL uRL = this.getResource((String)object);
        object = null;
        if (uRL != null) {
            try {
                object = uRL.openStream();
            }
            catch (IOException iOException) {
                return null;
            }
        }
        return object;
    }

    public Enumeration<URL> getResources(String string) throws IOException {
        ClassLoader classLoader = this.parent;
        Enumeration[] arrenumeration = new Enumeration[]{classLoader != null ? classLoader.getResources(string) : ClassLoader.getBootstrapResources(string), this.findResources(string)};
        return new CompoundEnumeration<URL>(arrenumeration);
    }

    public Class<?> loadClass(String string) throws ClassNotFoundException {
        return this.loadClass(string, false);
    }

    protected Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
        Class<?> class_;
        Class<?> class_2 = class_ = this.findLoadedClass(string);
        if (class_ == null) {
            block5 : {
                if (this.parent == null) break block5;
                class_ = class_2 = this.parent.loadClass(string, false);
            }
            try {
                class_ = class_2 = this.findBootstrapClassOrNull(string);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            class_2 = class_;
            if (class_ == null) {
                class_2 = this.findClass(string);
            }
        }
        return class_2;
    }

    protected final void resolveClass(Class<?> class_) {
    }

    public void setClassAssertionStatus(String string, boolean bl) {
    }

    public void setDefaultAssertionStatus(boolean bl) {
    }

    public void setPackageAssertionStatus(String string, boolean bl) {
    }

    protected final void setSigners(Class<?> class_, Object[] arrobject) {
    }

    private static class SystemClassLoader {
        public static ClassLoader loader = ClassLoader.access$000();

        private SystemClassLoader() {
        }
    }

}

