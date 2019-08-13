/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.BlockGuard
 *  dalvik.system.VMDebug
 *  dalvik.system.VMRuntime
 *  libcore.io.Libcore
 *  libcore.io.Os
 *  libcore.util.EmptyArray
 */
package java.lang;

import android.system.OsConstants;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.BlockGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.util.EmptyArray;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class Runtime {
    private static Runtime currentRuntime = new Runtime();
    private static boolean finalizeOnExit;
    private volatile String[] mLibPaths = null;
    private List<Thread> shutdownHooks = new ArrayList<Thread>();
    private boolean shuttingDown;
    private boolean tracingMethods;

    private Runtime() {
    }

    private void checkTargetSdkVersionForLoad(String string) {
        int n = VMRuntime.getRuntime().getTargetSdkVersion();
        if (n <= 24) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" is not supported on SDK ");
        stringBuilder.append(n);
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String[] getLibPaths() {
        if (this.mLibPaths != null) return this.mLibPaths;
        synchronized (this) {
            if (this.mLibPaths != null) return this.mLibPaths;
            this.mLibPaths = Runtime.initLibPaths();
            return this.mLibPaths;
        }
    }

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private static String[] initLibPaths() {
        CharSequence charSequence = System.getProperty("java.library.path");
        if (charSequence == null) {
            return EmptyArray.STRING;
        }
        String[] arrstring = ((String)charSequence).split(":");
        for (int i = 0; i < arrstring.length; ++i) {
            if (arrstring[i].endsWith("/")) continue;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(arrstring[i]);
            ((StringBuilder)charSequence).append("/");
            arrstring[i] = ((StringBuilder)charSequence).toString();
        }
        return arrstring;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadLibrary0(ClassLoader object, Class<?> object2, String string) {
        synchronized (this) {
            Object object3;
            void var3_3;
            if (var3_3.indexOf(File.separatorChar) != -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Directory separator should not appear in library name: ");
                ((StringBuilder)object).append((String)var3_3);
                object3 = new UnsatisfiedLinkError(((StringBuilder)object).toString());
                throw object3;
            }
            if (object != null && !(object instanceof BootClassLoader)) {
                object3 = ((ClassLoader)object).findLibrary((String)var3_3);
                if (object3 == null) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(object);
                    ((StringBuilder)object3).append(" couldn't find \"");
                    ((StringBuilder)object3).append(System.mapLibraryName((String)var3_3));
                    ((StringBuilder)object3).append("\"");
                    UnsatisfiedLinkError unsatisfiedLinkError = new UnsatisfiedLinkError(((StringBuilder)object3).toString());
                    throw unsatisfiedLinkError;
                }
                if ((object = Runtime.nativeLoad((String)object3, (ClassLoader)object)) == null) {
                    return;
                }
                object3 = new UnsatisfiedLinkError((String)object);
                throw object3;
            }
            this.getLibPaths();
            object = Runtime.nativeLoad(System.mapLibraryName((String)var3_3), (ClassLoader)object, object3);
            if (object == null) {
                return;
            }
            object3 = new UnsatisfiedLinkError((String)object);
            throw object3;
        }
    }

    private static native void nativeExit(int var0);

    private native void nativeGc();

    private static String nativeLoad(String string, ClassLoader classLoader) {
        return Runtime.nativeLoad(string, classLoader, null);
    }

    private static native String nativeLoad(String var0, ClassLoader var1, Class<?> var2);

    private static native void runFinalization0();

    @Deprecated
    public static void runFinalizersOnExit(boolean bl) {
        finalizeOnExit = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addShutdownHook(Thread object) {
        if (object == null) {
            throw new NullPointerException("hook == null");
        }
        if (this.shuttingDown) {
            throw new IllegalStateException("VM already shutting down");
        }
        if (((Thread)object).started) {
            throw new IllegalArgumentException("Hook has already been started");
        }
        List<Thread> list = this.shutdownHooks;
        synchronized (list) {
            if (!this.shutdownHooks.contains(object)) {
                this.shutdownHooks.add((Thread)object);
                return;
            }
            object = new IllegalArgumentException("Hook already registered.");
            throw object;
        }
    }

    public int availableProcessors() {
        return (int)Libcore.os.sysconf(OsConstants._SC_NPROCESSORS_CONF);
    }

    public Process exec(String string) throws IOException {
        return this.exec(string, null, null);
    }

    public Process exec(String string, String[] arrstring) throws IOException {
        return this.exec(string, arrstring, null);
    }

    public Process exec(String object, String[] arrstring, File file) throws IOException {
        if (((String)object).length() != 0) {
            object = new StringTokenizer((String)object);
            String[] arrstring2 = new String[((StringTokenizer)object).countTokens()];
            int n = 0;
            while (((StringTokenizer)object).hasMoreTokens()) {
                arrstring2[n] = ((StringTokenizer)object).nextToken();
                ++n;
            }
            return this.exec(arrstring2, arrstring, file);
        }
        throw new IllegalArgumentException("Empty command");
    }

    public Process exec(String[] arrstring) throws IOException {
        return this.exec(arrstring, null, null);
    }

    public Process exec(String[] arrstring, String[] arrstring2) throws IOException {
        return this.exec(arrstring, arrstring2, null);
    }

    public Process exec(String[] arrstring, String[] arrstring2, File file) throws IOException {
        return new ProcessBuilder(arrstring).environment(arrstring2).directory(file).start();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void exit(int n) {
        synchronized (this) {
            if (!this.shuttingDown) {
                Thread[] arrthread;
                int n2;
                this.shuttingDown = true;
                Object object = this.shutdownHooks;
                synchronized (object) {
                    arrthread = new Thread[this.shutdownHooks.size()];
                    this.shutdownHooks.toArray(arrthread);
                }
                int n3 = arrthread.length;
                int n4 = 0;
                for (n2 = 0; n2 < n3; ++n2) {
                    arrthread[n2].start();
                }
                n3 = arrthread.length;
                for (n2 = n4; n2 < n3; ++n2) {
                    object = arrthread[n2];
                    try {
                        ((Thread)object).join();
                        continue;
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                if (finalizeOnExit) {
                    this.runFinalization();
                }
                Runtime.nativeExit(n);
            }
            return;
        }
    }

    @FastNative
    public native long freeMemory();

    public void gc() {
        BlockGuard.getThreadPolicy().onExplicitGc();
        this.nativeGc();
    }

    @Deprecated
    public InputStream getLocalizedInputStream(InputStream inputStream) {
        return inputStream;
    }

    @Deprecated
    public OutputStream getLocalizedOutputStream(OutputStream outputStream) {
        return outputStream;
    }

    public void halt(int n) {
        Runtime.nativeExit(n);
    }

    @CallerSensitive
    public void load(String string) {
        this.load0(Reflection.getCallerClass(), string);
    }

    void load(String string, ClassLoader classLoader) {
        this.checkTargetSdkVersionForLoad("java.lang.Runtime#load(String, ClassLoader)");
        System.logE("java.lang.Runtime#load(String, ClassLoader) is private and will be removed in a future Android release");
        if (string != null) {
            if ((string = Runtime.nativeLoad(string, classLoader)) == null) {
                return;
            }
            throw new UnsatisfiedLinkError(string);
        }
        throw new NullPointerException("absolutePath == null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void load0(Class<?> serializable, String string) {
        synchronized (this) {
            String string2;
            File file = new File(string2);
            if (!file.isAbsolute()) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Expecting an absolute path of the library: ");
                ((StringBuilder)serializable).append(string2);
                UnsatisfiedLinkError unsatisfiedLinkError = new UnsatisfiedLinkError(((StringBuilder)serializable).toString());
                throw unsatisfiedLinkError;
            }
            if (string2 == null) {
                serializable = new NullPointerException("filename == null");
                throw serializable;
            }
            if ((string2 = Runtime.nativeLoad(string2, ((Class)serializable).getClassLoader())) == null) {
                return;
            }
            serializable = new UnsatisfiedLinkError(string2);
            throw serializable;
        }
    }

    @CallerSensitive
    public void loadLibrary(String string) {
        this.loadLibrary0(Reflection.getCallerClass(), string);
    }

    public void loadLibrary(String string, ClassLoader classLoader) {
        this.checkTargetSdkVersionForLoad("java.lang.Runtime#loadLibrary(String, ClassLoader)");
        System.logE("java.lang.Runtime#loadLibrary(String, ClassLoader) is private and will be removed in a future Android release");
        this.loadLibrary0(classLoader, null, string);
    }

    void loadLibrary0(Class<?> class_, String string) {
        this.loadLibrary0(ClassLoader.getClassLoader(class_), class_, string);
    }

    void loadLibrary0(ClassLoader classLoader, String string) {
        this.loadLibrary0(classLoader, null, string);
    }

    @FastNative
    public native long maxMemory();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean removeShutdownHook(Thread thread) {
        if (thread == null) {
            throw new NullPointerException("hook == null");
        }
        if (this.shuttingDown) throw new IllegalStateException("VM already shutting down");
        List<Thread> list = this.shutdownHooks;
        synchronized (list) {
            return this.shutdownHooks.remove(thread);
        }
    }

    public void runFinalization() {
        VMRuntime.runFinalization((long)0L);
    }

    @FastNative
    public native long totalMemory();

    public void traceInstructions(boolean bl) {
    }

    public void traceMethodCalls(boolean bl) {
        if (bl != this.tracingMethods) {
            if (bl) {
                VMDebug.startMethodTracing();
            } else {
                VMDebug.stopMethodTracing();
            }
            this.tracingMethods = bl;
        }
    }
}

