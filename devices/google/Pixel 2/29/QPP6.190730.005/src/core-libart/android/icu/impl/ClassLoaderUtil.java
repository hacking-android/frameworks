/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassLoaderUtil {
    private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ClassLoader getBootstrapClassLoader() {
        if (BOOTSTRAP_CLASSLOADER != null) return BOOTSTRAP_CLASSLOADER;
        synchronized (ClassLoaderUtil.class) {
            PrivilegedAction<ClassLoader> privilegedAction;
            if (BOOTSTRAP_CLASSLOADER != null) return BOOTSTRAP_CLASSLOADER;
            if (System.getSecurityManager() != null) {
                privilegedAction = new PrivilegedAction<ClassLoader>(){

                    @Override
                    public BootstrapClassLoader run() {
                        return new BootstrapClassLoader();
                    }
                };
                privilegedAction = (ClassLoader)AccessController.doPrivileged(privilegedAction);
            } else {
                privilegedAction = new BootstrapClassLoader();
            }
            BOOTSTRAP_CLASSLOADER = privilegedAction;
            return BOOTSTRAP_CLASSLOADER;
        }
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader;
        ClassLoader classLoader2 = classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader2 = classLoader = ClassLoader.getSystemClassLoader();
            if (classLoader == null) {
                classLoader2 = ClassLoaderUtil.getBootstrapClassLoader();
            }
        }
        return classLoader2;
    }

    public static ClassLoader getClassLoader(Class<?> object) {
        ClassLoader classLoader = ((Class)object).getClassLoader();
        object = classLoader;
        if (classLoader == null) {
            object = ClassLoaderUtil.getClassLoader();
        }
        return object;
    }

    private static class BootstrapClassLoader
    extends ClassLoader {
        BootstrapClassLoader() {
            super(Object.class.getClassLoader());
        }
    }

}

