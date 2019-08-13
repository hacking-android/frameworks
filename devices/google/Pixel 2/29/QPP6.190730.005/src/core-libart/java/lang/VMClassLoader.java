/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import libcore.io.ClassPathURLStreamHandler;

class VMClassLoader {
    private static final ClassPathURLStreamHandler[] bootClassPathUrlHandlers = VMClassLoader.createBootClassPathUrlHandlers();

    VMClassLoader() {
    }

    private static ClassPathURLStreamHandler[] createBootClassPathUrlHandlers() {
        String[] arrstring = VMClassLoader.getBootClassPathEntries();
        ArrayList<Object> arrayList = new ArrayList<Object>(arrstring.length);
        for (String string : arrstring) {
            Object object;
            try {
                object = new File(string);
                ((File)object).toURI().toString();
                object = new ClassPathURLStreamHandler(string);
                arrayList.add(object);
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to open boot classpath entry: ");
                ((StringBuilder)object).append(string);
                System.logE((String)((StringBuilder)object).toString(), (Throwable)iOException);
            }
        }
        return arrayList.toArray(new ClassPathURLStreamHandler[arrayList.size()]);
    }

    @FastNative
    static native Class findLoadedClass(ClassLoader var0, String var1);

    private static native String[] getBootClassPathEntries();

    static URL getResource(String string) {
        ClassPathURLStreamHandler[] arrclassPathURLStreamHandler = bootClassPathUrlHandlers;
        int n = arrclassPathURLStreamHandler.length;
        for (int i = 0; i < n; ++i) {
            URL uRL = arrclassPathURLStreamHandler[i].getEntryUrlOrNull(string);
            if (uRL == null) continue;
            return uRL;
        }
        return null;
    }

    static List<URL> getResources(String string) {
        ArrayList<URL> arrayList = new ArrayList<URL>();
        ClassPathURLStreamHandler[] arrclassPathURLStreamHandler = bootClassPathUrlHandlers;
        int n = arrclassPathURLStreamHandler.length;
        for (int i = 0; i < n; ++i) {
            URL uRL = arrclassPathURLStreamHandler[i].getEntryUrlOrNull(string);
            if (uRL == null) continue;
            arrayList.add(uRL);
        }
        return arrayList;
    }
}

