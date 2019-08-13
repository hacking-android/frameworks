/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.DelegateLastClassLoader
 *  dalvik.system.DexClassLoader
 *  dalvik.system.PathClassLoader
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Trace;
import dalvik.system.DelegateLastClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.util.List;

public class ClassLoaderFactory {
    private static final String DELEGATE_LAST_CLASS_LOADER_NAME;
    private static final String DEX_CLASS_LOADER_NAME;
    private static final String PATH_CLASS_LOADER_NAME;

    static {
        PATH_CLASS_LOADER_NAME = PathClassLoader.class.getName();
        DEX_CLASS_LOADER_NAME = DexClassLoader.class.getName();
        DELEGATE_LAST_CLASS_LOADER_NAME = DelegateLastClassLoader.class.getName();
    }

    private ClassLoaderFactory() {
    }

    public static ClassLoader createClassLoader(String charSequence, String string2, ClassLoader classLoader, String string3, List<ClassLoader> object) {
        object = object == null ? null : object.toArray(new ClassLoader[object.size()]);
        if (ClassLoaderFactory.isPathClassLoaderName(string3)) {
            return new PathClassLoader((String)charSequence, string2, classLoader, object);
        }
        if (ClassLoaderFactory.isDelegateLastClassLoaderName(string3)) {
            return new DelegateLastClassLoader((String)charSequence, string2, classLoader, object);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid classLoaderName: ");
        ((StringBuilder)charSequence).append(string3);
        throw new AssertionError((Object)((StringBuilder)charSequence).toString());
    }

    public static ClassLoader createClassLoader(String string2, String string3, String string4, ClassLoader classLoader, int n, boolean bl, String string5) {
        return ClassLoaderFactory.createClassLoader(string2, string3, string4, classLoader, n, bl, string5, null);
    }

    public static ClassLoader createClassLoader(String charSequence, String string2, String string3, ClassLoader classLoader, int n, boolean bl, String string4, List<ClassLoader> list) {
        classLoader = ClassLoaderFactory.createClassLoader((String)charSequence, string2, classLoader, string4, list);
        Trace.traceBegin(64L, "createClassloaderNamespace");
        string2 = ClassLoaderFactory.createClassloaderNamespace(classLoader, n, string2, string3, bl, (String)charSequence);
        Trace.traceEnd(64L);
        if (string2 == null) {
            return classLoader;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to create namespace for the classloader ");
        ((StringBuilder)charSequence).append(classLoader);
        ((StringBuilder)charSequence).append(": ");
        ((StringBuilder)charSequence).append(string2);
        throw new UnsatisfiedLinkError(((StringBuilder)charSequence).toString());
    }

    @UnsupportedAppUsage
    private static native String createClassloaderNamespace(ClassLoader var0, int var1, String var2, String var3, boolean var4, String var5);

    public static String getPathClassLoaderName() {
        return PATH_CLASS_LOADER_NAME;
    }

    public static boolean isDelegateLastClassLoaderName(String string2) {
        return DELEGATE_LAST_CLASS_LOADER_NAME.equals(string2);
    }

    public static boolean isPathClassLoaderName(String string2) {
        boolean bl = string2 == null || PATH_CLASS_LOADER_NAME.equals(string2) || DEX_CLASS_LOADER_NAME.equals(string2);
        return bl;
    }

    public static boolean isValidClassLoaderName(String string2) {
        boolean bl = string2 != null && (ClassLoaderFactory.isPathClassLoaderName(string2) || ClassLoaderFactory.isDelegateLastClassLoaderName(string2));
        return bl;
    }
}

