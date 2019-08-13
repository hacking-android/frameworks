/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.system.PathClassLoader;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import sun.misc.CompoundEnumeration;

public final class DelegateLastClassLoader
extends PathClassLoader {
    private final boolean delegateResourceLoading;

    public DelegateLastClassLoader(String string, ClassLoader classLoader) {
        this(string, null, classLoader, true);
    }

    public DelegateLastClassLoader(String string, String string2, ClassLoader classLoader) {
        this(string, string2, classLoader, true);
    }

    public DelegateLastClassLoader(String string, String string2, ClassLoader classLoader, boolean bl) {
        super(string, string2, classLoader);
        this.delegateResourceLoading = bl;
    }

    public DelegateLastClassLoader(String string, String string2, ClassLoader classLoader, ClassLoader[] arrclassLoader) {
        super(string, string2, classLoader, arrclassLoader);
        this.delegateResourceLoading = true;
    }

    @Override
    public URL getResource(String object) {
        URL uRL = Object.class.getClassLoader().getResource((String)object);
        if (uRL != null) {
            return uRL;
        }
        uRL = this.findResource((String)object);
        if (uRL != null) {
            return uRL;
        }
        boolean bl = this.delegateResourceLoading;
        uRL = null;
        if (bl) {
            ClassLoader classLoader = this.getParent();
            object = classLoader == null ? uRL : classLoader.getResource((String)object);
            return object;
        }
        return null;
    }

    @Override
    public Enumeration<URL> getResources(String enumeration) throws IOException {
        Enumeration<URL> enumeration2 = Object.class.getClassLoader().getResources((String)((Object)enumeration));
        Enumeration<URL> enumeration3 = this.findResources((String)((Object)enumeration));
        enumeration = this.getParent() != null && this.delegateResourceLoading ? this.getParent().getResources((String)((Object)enumeration)) : null;
        return new CompoundEnumeration<URL>(new Enumeration[]{enumeration2, enumeration3, enumeration});
    }

    @Override
    protected Class<?> loadClass(String object, boolean bl) throws ClassNotFoundException {
        Class<?> class_ = this.findLoadedClass((String)object);
        if (class_ != null) {
            return class_;
        }
        try {
            class_ = Object.class.getClassLoader().loadClass((String)object);
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                Class<?> class_2 = this.findClass((String)object);
                return class_2;
            }
            catch (ClassNotFoundException classNotFoundException2) {
                try {
                    object = this.getParent().loadClass((String)object);
                    return object;
                }
                catch (ClassNotFoundException classNotFoundException3) {
                    throw classNotFoundException2;
                }
            }
        }
    }
}

