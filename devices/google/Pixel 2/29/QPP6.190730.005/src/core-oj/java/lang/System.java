/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.StructPasswd
 *  android.system.StructUtsname
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.VMRuntime
 *  java.lang.AndroidHardcodedSystemProperties
 *  libcore.icu.ICU
 *  libcore.io.Libcore
 *  libcore.io.Os
 *  libcore.timezone.TimeZoneDataFiles
 */
package java.lang;

import android.system.ErrnoException;
import android.system.StructPasswd;
import android.system.StructUtsname;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMRuntime;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channel;
import java.nio.channels.spi.SelectorProvider;
import java.security.Permission;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyPermission;
import libcore.icu.ICU;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.timezone.TimeZoneDataFiles;
import sun.misc.VM;
import sun.misc.Version;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public final class System {
    private static final int ARRAYCOPY_SHORT_BOOLEAN_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_BYTE_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_CHAR_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_DOUBLE_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_FLOAT_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_INT_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_LONG_ARRAY_THRESHOLD = 32;
    private static final int ARRAYCOPY_SHORT_SHORT_ARRAY_THRESHOLD = 32;
    private static final Object LOCK = new Object();
    private static volatile Console cons = null;
    public static final PrintStream err;
    public static final InputStream in;
    private static boolean justRanFinalization;
    private static String lineSeparator;
    public static final PrintStream out;
    private static Properties props;
    private static boolean runGC;
    private static Properties unchangeableProps;

    static {
        unchangeableProps = System.initUnchangeableSystemProperties();
        props = System.initProperties();
        System.addLegacyLocaleSystemProperties();
        Version.initSystemProperties();
        lineSeparator = props.getProperty("line.separator");
        FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
        FileOutputStream fileOutputStream = new FileOutputStream(FileDescriptor.out);
        FileOutputStream fileOutputStream2 = new FileOutputStream(FileDescriptor.err);
        in = new BufferedInputStream(fileInputStream, 128);
        out = System.newPrintStream(fileOutputStream, props.getProperty("sun.stdout.encoding"));
        err = System.newPrintStream(fileOutputStream2, props.getProperty("sun.stderr.encoding"));
        VM.initializeOSEnvironment();
        VM.booted();
    }

    private System() {
    }

    private static void addLegacyLocaleSystemProperties() {
        Object object = System.getProperty("user.locale", "");
        if (!((String)object).isEmpty()) {
            object = Locale.forLanguageTag((String)object);
            System.setUnchangeableSystemProperty("user.language", ((Locale)object).getLanguage());
            System.setUnchangeableSystemProperty("user.region", ((Locale)object).getCountry());
            System.setUnchangeableSystemProperty("user.variant", ((Locale)object).getVariant());
        } else {
            object = System.getProperty("user.language", "");
            String string = System.getProperty("user.region", "");
            if (((String)object).isEmpty()) {
                System.setUnchangeableSystemProperty("user.language", "en");
            }
            if (string.isEmpty()) {
                System.setUnchangeableSystemProperty("user.region", "US");
            }
        }
    }

    @FastNative
    public static native void arraycopy(Object var0, int var1, Object var2, int var3, int var4);

    public static void arraycopy(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        if (arrby != null) {
            if (arrby2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrby.length - n3 && n2 <= arrby2.length - n3) {
                    if (n3 <= 32) {
                        if (arrby == arrby2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrby2[n2 + n3] = arrby[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrby2[n2 + i] = arrby[n + i];
                            }
                        }
                    } else {
                        System.arraycopyByteUnchecked(arrby, n, arrby2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrby.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrby2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(char[] arrc, int n, char[] arrc2, int n2, int n3) {
        if (arrc != null) {
            if (arrc2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrc.length - n3 && n2 <= arrc2.length - n3) {
                    if (n3 <= 32) {
                        if (arrc == arrc2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrc2[n2 + n3] = arrc[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrc2[n2 + i] = arrc[n + i];
                            }
                        }
                    } else {
                        System.arraycopyCharUnchecked(arrc, n, arrc2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrc.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrc2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(double[] arrd, int n, double[] arrd2, int n2, int n3) {
        if (arrd != null) {
            if (arrd2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrd.length - n3 && n2 <= arrd2.length - n3) {
                    if (n3 <= 32) {
                        if (arrd == arrd2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrd2[n2 + n3] = arrd[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrd2[n2 + i] = arrd[n + i];
                            }
                        }
                    } else {
                        System.arraycopyDoubleUnchecked(arrd, n, arrd2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrd.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrd2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(float[] arrf, int n, float[] arrf2, int n2, int n3) {
        if (arrf != null) {
            if (arrf2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrf.length - n3 && n2 <= arrf2.length - n3) {
                    if (n3 <= 32) {
                        if (arrf == arrf2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrf2[n2 + n3] = arrf[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrf2[n2 + i] = arrf[n + i];
                            }
                        }
                    } else {
                        System.arraycopyFloatUnchecked(arrf, n, arrf2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrf.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrf2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        if (arrn != null) {
            if (arrn2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrn.length - n3 && n2 <= arrn2.length - n3) {
                    if (n3 <= 32) {
                        if (arrn == arrn2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrn2[n2 + n3] = arrn[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrn2[n2 + i] = arrn[n + i];
                            }
                        }
                    } else {
                        System.arraycopyIntUnchecked(arrn, n, arrn2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrn.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrn2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(long[] arrl, int n, long[] arrl2, int n2, int n3) {
        if (arrl != null) {
            if (arrl2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrl.length - n3 && n2 <= arrl2.length - n3) {
                    if (n3 <= 32) {
                        if (arrl == arrl2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrl2[n2 + n3] = arrl[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrl2[n2 + i] = arrl[n + i];
                            }
                        }
                    } else {
                        System.arraycopyLongUnchecked(arrl, n, arrl2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrl.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrl2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(short[] arrs, int n, short[] arrs2, int n2, int n3) {
        if (arrs != null) {
            if (arrs2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrs.length - n3 && n2 <= arrs2.length - n3) {
                    if (n3 <= 32) {
                        if (arrs == arrs2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrs2[n2 + n3] = arrs[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrs2[n2 + i] = arrs[n + i];
                            }
                        }
                    } else {
                        System.arraycopyShortUnchecked(arrs, n, arrs2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrs.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrs2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    private static void arraycopy(boolean[] arrbl, int n, boolean[] arrbl2, int n2, int n3) {
        if (arrbl != null) {
            if (arrbl2 != null) {
                if (n >= 0 && n2 >= 0 && n3 >= 0 && n <= arrbl.length - n3 && n2 <= arrbl2.length - n3) {
                    if (n3 <= 32) {
                        if (arrbl == arrbl2 && n < n2 && n2 < n + n3) {
                            --n3;
                            while (n3 >= 0) {
                                arrbl2[n2 + n3] = arrbl[n + n3];
                                --n3;
                            }
                        } else {
                            for (int i = 0; i < n3; ++i) {
                                arrbl2[n2 + i] = arrbl[n + i];
                            }
                        }
                    } else {
                        System.arraycopyBooleanUnchecked(arrbl, n, arrbl2, n2, n3);
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src.length=");
                stringBuilder.append(arrbl.length);
                stringBuilder.append(" srcPos=");
                stringBuilder.append(n);
                stringBuilder.append(" dst.length=");
                stringBuilder.append(arrbl2.length);
                stringBuilder.append(" dstPos=");
                stringBuilder.append(n2);
                stringBuilder.append(" length=");
                stringBuilder.append(n3);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("src == null");
    }

    @FastNative
    private static native void arraycopyBooleanUnchecked(boolean[] var0, int var1, boolean[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyByteUnchecked(byte[] var0, int var1, byte[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyCharUnchecked(char[] var0, int var1, char[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyDoubleUnchecked(double[] var0, int var1, double[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyFloatUnchecked(float[] var0, int var1, float[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyIntUnchecked(int[] var0, int var1, int[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyLongUnchecked(long[] var0, int var1, long[] var2, int var3, int var4);

    @FastNative
    private static native void arraycopyShortUnchecked(short[] var0, int var1, short[] var2, int var3, int var4);

    private static void checkKey(String string) {
        if (string != null) {
            if (!string.equals("")) {
                return;
            }
            throw new IllegalArgumentException("key can't be empty");
        }
        throw new NullPointerException("key can't be null");
    }

    public static String clearProperty(String string) {
        System.checkKey(string);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission(string, "write"));
        }
        return (String)props.remove(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Console console() {
        if (cons != null) return cons;
        synchronized (System.class) {
            if (cons != null) return cons;
            cons = Console.console();
            return cons;
        }
    }

    public static native long currentTimeMillis();

    public static void exit(int n) {
        Runtime.getRuntime().exit(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void gc() {
        Object object = LOCK;
        // MONITORENTER : object
        boolean bl = justRanFinalization;
        if (bl) {
            justRanFinalization = false;
        } else {
            runGC = true;
        }
        // MONITOREXIT : object
        if (!bl) return;
        Runtime.getRuntime().gc();
    }

    public static Properties getProperties() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertiesAccess();
        }
        return props;
    }

    public static String getProperty(String string) {
        System.checkKey(string);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess(string);
        }
        return props.getProperty(string);
    }

    public static String getProperty(String string, String string2) {
        System.checkKey(string);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess(string);
        }
        return props.getProperty(string, string2);
    }

    public static SecurityManager getSecurityManager() {
        return null;
    }

    public static String getenv(String string) {
        if (string != null) {
            return Libcore.os.getenv(string);
        }
        throw new NullPointerException("name == null");
    }

    public static Map<String, String> getenv() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getenv.*"));
        }
        return ProcessEnvironment.getenv();
    }

    public static int identityHashCode(Object object) {
        if (object == null) {
            return 0;
        }
        return Object.identityHashCode(object);
    }

    public static Channel inheritedChannel() throws IOException {
        return SelectorProvider.provider().inheritedChannel();
    }

    private static Properties initProperties() {
        PropertiesWithNonOverrideableDefaults propertiesWithNonOverrideableDefaults = new PropertiesWithNonOverrideableDefaults(unchangeableProps);
        System.setDefaultChangeableProperties(propertiesWithNonOverrideableDefaults);
        return propertiesWithNonOverrideableDefaults;
    }

    private static Properties initUnchangeableSystemProperties() {
        String[] arrstring3 = VMRuntime.getRuntime();
        Properties properties = new Properties();
        properties.put("java.boot.class.path", arrstring3.bootClassPath());
        properties.put("java.class.path", arrstring3.classPath());
        String[][] arrstring2 = System.getenv("JAVA_HOME");
        Object object = arrstring2;
        if (arrstring2 == null) {
            object = "/system";
        }
        properties.put("java.home", object);
        properties.put("java.vm.version", arrstring3.vmVersion());
        try {
            properties.put("user.name", Libcore.os.getpwuid((int)Libcore.os.getuid()).pw_name);
        }
        catch (ErrnoException errnoException) {
            throw new AssertionError((Object)((Object)errnoException));
        }
        object = Libcore.os.uname();
        properties.put("os.arch", ((StructUtsname)object).machine);
        properties.put("os.name", ((StructUtsname)object).sysname);
        properties.put("os.version", ((StructUtsname)object).release);
        properties.put("android.icu.library.version", ICU.getIcuVersion());
        properties.put("android.icu.unicode.version", ICU.getUnicodeVersion());
        properties.put("android.icu.cldr.version", ICU.getCldrVersion());
        properties.put("android.icu.impl.ICUBinary.dataPath", TimeZoneDataFiles.generateIcuDataPath());
        System.parsePropertyAssignments(properties, System.specialProperties());
        System.parsePropertyAssignments(properties, arrstring3.properties());
        for (String[] arrstring3 : AndroidHardcodedSystemProperties.STATIC_PROPERTIES) {
            if (properties.containsKey(arrstring3[0])) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Ignoring command line argument: -D");
                ((StringBuilder)object).append(arrstring3[0]);
                System.logE(((StringBuilder)object).toString());
            }
            if (arrstring3[1] == null) {
                properties.remove(arrstring3[0]);
                continue;
            }
            properties.put(arrstring3[0], arrstring3[1]);
        }
        return properties;
    }

    public static String lineSeparator() {
        return lineSeparator;
    }

    @CallerSensitive
    public static void load(String string) {
        Runtime.getRuntime().load0(Reflection.getCallerClass(), string);
    }

    @CallerSensitive
    public static void loadLibrary(String string) {
        Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), string);
    }

    private static native void log(char var0, String var1, Throwable var2);

    public static void logE(String string) {
        System.log('E', string, null);
    }

    public static void logE(String string, Throwable throwable) {
        System.log('E', string, throwable);
    }

    public static void logI(String string) {
        System.log('I', string, null);
    }

    public static void logI(String string, Throwable throwable) {
        System.log('I', string, throwable);
    }

    public static void logW(String string) {
        System.log('W', string, null);
    }

    public static void logW(String string, Throwable throwable) {
        System.log('W', string, throwable);
    }

    public static native String mapLibraryName(String var0);

    public static native long nanoTime();

    private static PrintStream newPrintStream(FileOutputStream fileOutputStream, String object) {
        if (object != null) {
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 128);
                object = new PrintStream(bufferedOutputStream, true, (String)object);
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return new PrintStream(new BufferedOutputStream(fileOutputStream, 128), true);
    }

    private static void parsePropertyAssignments(Properties properties, String[] arrstring) {
        for (String string : arrstring) {
            int n = string.indexOf(61);
            properties.put(string.substring(0, n), string.substring(n + 1));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void runFinalization() {
        Object object = LOCK;
        // MONITORENTER : object
        boolean bl = runGC;
        runGC = false;
        // MONITOREXIT : object
        if (bl) {
            Runtime.getRuntime().gc();
        }
        Runtime.getRuntime().runFinalization();
        object = LOCK;
        // MONITORENTER : object
        justRanFinalization = true;
        // MONITOREXIT : object
    }

    @Deprecated
    public static void runFinalizersOnExit(boolean bl) {
        Runtime.runFinalizersOnExit(bl);
    }

    private static Properties setDefaultChangeableProperties(Properties properties) {
        if (!unchangeableProps.containsKey("java.io.tmpdir")) {
            properties.put("java.io.tmpdir", "/tmp");
        }
        if (!unchangeableProps.containsKey("user.home")) {
            properties.put("user.home", "");
        }
        return properties;
    }

    public static void setErr(PrintStream printStream) {
        System.setErr0(printStream);
    }

    private static native void setErr0(PrintStream var0);

    public static void setIn(InputStream inputStream) {
        System.setIn0(inputStream);
    }

    private static native void setIn0(InputStream var0);

    public static void setOut(PrintStream printStream) {
        System.setOut0(printStream);
    }

    private static native void setOut0(PrintStream var0);

    public static void setProperties(Properties properties) {
        PropertiesWithNonOverrideableDefaults propertiesWithNonOverrideableDefaults = new PropertiesWithNonOverrideableDefaults(unchangeableProps);
        if (properties != null) {
            propertiesWithNonOverrideableDefaults.putAll(properties);
        } else {
            System.setDefaultChangeableProperties(propertiesWithNonOverrideableDefaults);
        }
        props = propertiesWithNonOverrideableDefaults;
    }

    public static String setProperty(String string, String string2) {
        System.checkKey(string);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission(string, "write"));
        }
        return (String)props.setProperty(string, string2);
    }

    public static void setSecurityManager(SecurityManager securityManager) {
        if (securityManager == null) {
            return;
        }
        throw new SecurityException();
    }

    public static void setUnchangeableSystemProperty(String string, String string2) {
        System.checkKey(string);
        unchangeableProps.put(string, string2);
    }

    private static native String[] specialProperties();

    static final class PropertiesWithNonOverrideableDefaults
    extends Properties {
        PropertiesWithNonOverrideableDefaults(Properties properties) {
            super(properties);
        }

        @Override
        public Object put(Object object, Object object2) {
            if (this.defaults.containsKey(object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring attempt to set property \"");
                stringBuilder.append(object);
                stringBuilder.append("\" to value \"");
                stringBuilder.append(object2);
                stringBuilder.append("\".");
                System.logE(stringBuilder.toString());
                return this.defaults.get(object);
            }
            return super.put(object, object2);
        }

        @Override
        public Object remove(Object object) {
            if (this.defaults.containsKey(object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring attempt to remove property \"");
                stringBuilder.append(object);
                stringBuilder.append("\".");
                System.logE(stringBuilder.toString());
                return null;
            }
            return super.remove(object);
        }
    }

}

