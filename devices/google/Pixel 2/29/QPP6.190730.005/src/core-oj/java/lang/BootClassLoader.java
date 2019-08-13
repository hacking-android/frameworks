/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.lang.FindBugsSuppressWarnings
 *  java.lang.VMClassLoader
 */
package java.lang;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

class BootClassLoader
extends ClassLoader {
    private static BootClassLoader instance;

    public BootClassLoader() {
        super(null);
    }

    @FindBugsSuppressWarnings(value={"DP_CREATE_CLASSLOADER_INSIDE_DO_PRIVILEGED"})
    public static BootClassLoader getInstance() {
        synchronized (BootClassLoader.class) {
            BootClassLoader bootClassLoader;
            if (instance == null) {
                instance = bootClassLoader = new BootClassLoader();
            }
            bootClassLoader = instance;
            return bootClassLoader;
        }
    }

    @Override
    protected Class<?> findClass(String string) throws ClassNotFoundException {
        return Class.classForName(string, false, null);
    }

    @Override
    protected URL findResource(String string) {
        return VMClassLoader.getResource((String)string);
    }

    @Override
    protected Enumeration<URL> findResources(String string) throws IOException {
        return Collections.enumeration(VMClassLoader.getResources((String)string));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Package getPackage(String string) {
        if (string == null) return null;
        if (string.isEmpty()) return null;
        synchronized (this) {
            Package package_;
            Package package_2 = package_ = super.getPackage(string);
            if (package_ != null) return package_2;
            return this.definePackage(string, "Unknown", "0.0", "Unknown", "Unknown", "0.0", "Unknown", null);
        }
    }

    @Override
    public URL getResource(String string) {
        return this.findResource(string);
    }

    @Override
    public Enumeration<URL> getResources(String string) throws IOException {
        return this.findResources(string);
    }

    @Override
    protected Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
        Class<?> class_;
        Class<?> class_2 = class_ = this.findLoadedClass(string);
        if (class_ == null) {
            class_2 = this.findClass(string);
        }
        return class_2;
    }
}

