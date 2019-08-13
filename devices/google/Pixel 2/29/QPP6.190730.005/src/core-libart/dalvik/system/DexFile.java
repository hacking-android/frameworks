/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import android.system.ErrnoException;
import android.system.StructStat;
import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.DexPathList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import libcore.io.Libcore;
import libcore.io.Os;

@Deprecated
public final class DexFile {
    public static final int DEX2OAT_FOR_BOOT_IMAGE = 2;
    public static final int DEX2OAT_FOR_FILTER = 3;
    public static final int DEX2OAT_FROM_SCRATCH = 1;
    public static final int NO_DEXOPT_NEEDED = 0;
    @ReachabilitySensitive
    @UnsupportedAppUsage
    private Object mCookie;
    @UnsupportedAppUsage
    private final String mFileName;
    @UnsupportedAppUsage
    private Object mInternalCookie;

    @Deprecated
    public DexFile(File file) throws IOException {
        this(file.getPath());
    }

    DexFile(File file, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        this(file.getPath(), classLoader, arrelement);
    }

    @Deprecated
    public DexFile(String string) throws IOException {
        this(string, null, null);
    }

    DexFile(String string, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        this.mInternalCookie = this.mCookie = DexFile.openDexFile(string, null, 0, classLoader, arrelement);
        this.mFileName = string;
    }

    private DexFile(String string, String string2, int n, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        if (string2 != null) {
            try {
                Serializable serializable = new File(string2);
                String string3 = serializable.getParent();
                if (Libcore.os.getuid() != Libcore.os.stat((String)string3).st_uid) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Optimized data directory ");
                    stringBuilder.append(string3);
                    stringBuilder.append(" is not owned by the current user. Shared storage cannot protect your application from code injection attacks.");
                    super(stringBuilder.toString());
                    throw serializable;
                }
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
        }
        this.mInternalCookie = this.mCookie = DexFile.openDexFile(string, string2, n, classLoader, arrelement);
        this.mFileName = string;
    }

    DexFile(ByteBuffer[] arrbyteBuffer, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        this.mInternalCookie = this.mCookie = DexFile.openInMemoryDexFiles(arrbyteBuffer, classLoader, arrelement);
        this.mFileName = null;
    }

    private static native boolean closeDexFile(Object var0);

    private static Class defineClass(String object, ClassLoader classLoader, Object object2, DexFile dexFile, List<Throwable> list) {
        block4 : {
            Object var5_7 = null;
            Object var6_8 = null;
            try {
                object = DexFile.defineClassNative((String)object, classLoader, object2, dexFile);
            }
            catch (ClassNotFoundException classNotFoundException) {
                object = var5_7;
                if (list != null) {
                    list.add(classNotFoundException);
                    object = var5_7;
                }
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                object = var6_8;
                if (list == null) break block4;
                list.add(noClassDefFoundError);
                object = var6_8;
            }
        }
        return object;
    }

    private static native Class defineClassNative(String var0, ClassLoader var1, Object var2, DexFile var3) throws ClassNotFoundException, NoClassDefFoundError;

    static native String getClassLoaderContext(ClassLoader var0, DexPathList.Element[] var1);

    @UnsupportedAppUsage
    private static native String[] getClassNameList(Object var0);

    public static OptimizationInfo getDexFileOptimizationInfo(String arrstring, String string) throws FileNotFoundException {
        arrstring = DexFile.getDexFileOptimizationStatus((String)arrstring, string);
        return new OptimizationInfo(arrstring[0], arrstring[1]);
    }

    private static native String[] getDexFileOptimizationStatus(String var0, String var1) throws FileNotFoundException;

    public static native String[] getDexFileOutputPaths(String var0, String var1) throws FileNotFoundException;

    public static native String getDexFileStatus(String var0, String var1) throws FileNotFoundException;

    public static native int getDexOptNeeded(String var0, String var1, String var2, String var3, boolean var4, boolean var5) throws FileNotFoundException, IOException;

    public static int getDexOptNeeded(String string, String string2, String string3, boolean bl, boolean bl2) throws FileNotFoundException, IOException {
        return DexFile.getDexOptNeeded(string, string2, string3, null, bl, bl2);
    }

    public static native String getNonProfileGuidedCompilerFilter(String var0);

    public static native String getSafeModeCompilerFilter(String var0);

    private static native long getStaticSizeOfDexFile(Object var0);

    private static native boolean isBackedByOatFile(Object var0);

    public static native boolean isDexOptNeeded(String var0) throws FileNotFoundException, IOException;

    public static native boolean isProfileGuidedCompilerFilter(String var0);

    public static native boolean isValidCompilerFilter(String var0);

    @Deprecated
    public static DexFile loadDex(String string, String string2, int n) throws IOException {
        return DexFile.loadDex(string, string2, n, null, null);
    }

    @UnsupportedAppUsage
    static DexFile loadDex(String string, String string2, int n, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        return new DexFile(string, string2, n, classLoader, arrelement);
    }

    @UnsupportedAppUsage
    private static Object openDexFile(String string, String string2, int n, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        String string3 = new File(string).getAbsolutePath();
        string = string2 == null ? null : new File(string2).getAbsolutePath();
        return DexFile.openDexFileNative(string3, string, n, classLoader, arrelement);
    }

    @UnsupportedAppUsage
    private static native Object openDexFileNative(String var0, String var1, int var2, ClassLoader var3, DexPathList.Element[] var4);

    private static Object openInMemoryDexFiles(ByteBuffer[] arrbyteBuffer, ClassLoader classLoader, DexPathList.Element[] arrelement) throws IOException {
        byte[][] arrarrby = new byte[arrbyteBuffer.length][];
        int[] arrn = new int[arrbyteBuffer.length];
        int[] arrn2 = new int[arrbyteBuffer.length];
        for (int i = 0; i < arrbyteBuffer.length; ++i) {
            byte[] arrby = arrbyteBuffer[i].isDirect() ? null : arrbyteBuffer[i].array();
            arrarrby[i] = arrby;
            arrn[i] = arrbyteBuffer[i].position();
            arrn2[i] = arrbyteBuffer[i].limit();
        }
        return DexFile.openInMemoryDexFilesNative(arrbyteBuffer, arrarrby, arrn, arrn2, classLoader, arrelement);
    }

    private static native Object openInMemoryDexFilesNative(ByteBuffer[] var0, byte[][] var1, int[] var2, int[] var3, ClassLoader var4, DexPathList.Element[] var5);

    private static native void setTrusted(Object var0);

    private static native void verifyInBackgroundNative(Object var0, ClassLoader var1, String var2);

    public void close() throws IOException {
        Object object = this.mInternalCookie;
        if (object != null) {
            if (DexFile.closeDexFile(object)) {
                this.mInternalCookie = null;
            }
            this.mCookie = null;
        }
    }

    public Enumeration<String> entries() {
        return new DFEnum(this);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mInternalCookie != null && !DexFile.closeDexFile(this.mInternalCookie)) {
                AssertionError assertionError = new AssertionError((Object)"Failed to close dex file in finalizer.");
                throw assertionError;
            }
            this.mInternalCookie = null;
            this.mCookie = null;
            return;
        }
        finally {
            super.finalize();
        }
    }

    public String getName() {
        return this.mFileName;
    }

    public long getStaticSizeOfDexFile() {
        return DexFile.getStaticSizeOfDexFile(this.mCookie);
    }

    @UnsupportedAppUsage
    boolean isBackedByOatFile() {
        return DexFile.isBackedByOatFile(this.mCookie);
    }

    public Class loadClass(String string, ClassLoader classLoader) {
        return this.loadClassBinaryName(string.replace('.', '/'), classLoader, null);
    }

    @UnsupportedAppUsage
    public Class loadClassBinaryName(String string, ClassLoader classLoader, List<Throwable> list) {
        return DexFile.defineClass(string, classLoader, this.mCookie, this, list);
    }

    void setTrusted() {
        DexFile.setTrusted(this.mCookie);
    }

    public String toString() {
        if (this.mFileName != null) {
            return this.getName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InMemoryDexFile[cookie=");
        stringBuilder.append(Arrays.toString((long[])this.mCookie));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    void verifyInBackground(ClassLoader classLoader, String string) {
        DexFile.verifyInBackgroundNative(this.mCookie, classLoader, string);
    }

    private static class DFEnum
    implements Enumeration<String> {
        private int mIndex = 0;
        @UnsupportedAppUsage
        private String[] mNameList;

        DFEnum(DexFile dexFile) {
            this.mNameList = DexFile.getClassNameList(dexFile.mCookie);
        }

        @Override
        public boolean hasMoreElements() {
            boolean bl = this.mIndex < this.mNameList.length;
            return bl;
        }

        @Override
        public String nextElement() {
            String[] arrstring = this.mNameList;
            int n = this.mIndex;
            this.mIndex = n + 1;
            return arrstring[n];
        }
    }

    public static final class OptimizationInfo {
        private final String reason;
        private final String status;

        private OptimizationInfo(String string, String string2) {
            this.status = string;
            this.reason = string2;
        }

        public String getReason() {
            return this.reason;
        }

        public String getStatus() {
            return this.status;
        }
    }

}

