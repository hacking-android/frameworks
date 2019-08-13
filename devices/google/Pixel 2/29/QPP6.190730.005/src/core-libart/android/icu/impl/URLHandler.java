/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.ICUDebug;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public abstract class URLHandler {
    private static final boolean DEBUG = ICUDebug.enabled("URLHandler");
    public static final String PROPNAME = "urlhandler.props";
    private static final Map<String, Method> handlers;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        var0 = null;
        var1_6 = null;
        var2_7 = null;
        var3_8 = null;
        var4_9 = null;
        var5_10 = var1_6;
        var6_13 = var3_8;
        try {
            var7_14 = ClassLoaderUtil.getClassLoader(URLHandler.class).getResourceAsStream("urlhandler.props");
            if (var7_14 != null) {
                var5_10 = var1_6;
                var6_13 = var3_8;
                var5_10 = var1_6;
                var6_13 = var3_8;
                var5_10 = var1_6;
                var6_13 = var3_8;
                var0 = new InputStreamReader((InputStream)var7_14);
                var5_10 = var1_6;
                var6_13 = var3_8;
                var4_9 = new BufferedReader((Reader)var0);
                var5_10 = var1_6;
                var6_13 = var4_9;
                var1_6 = var4_9.readLine();
                var0 = var2_7;
                while (var1_6 != null) {
                    block23 : {
                        var5_10 = var0;
                        var6_13 = var4_9;
                        var2_7 = var1_6.trim();
                        var1_6 = var0;
                        var5_10 = var0;
                        var6_13 = var4_9;
                        if (var2_7.length() != 0) {
                            var5_10 = var0;
                            var6_13 = var4_9;
                            if (var2_7.charAt(0) == '#') {
                                var1_6 = var0;
                            } else {
                                var5_10 = var0;
                                var6_13 = var4_9;
                                var8_15 = var2_7.indexOf(61);
                                if (var8_15 == -1) {
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    if (!URLHandler.DEBUG) break;
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var1_6 = System.err;
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var3_8 = new StringBuilder();
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var3_8.append("bad urlhandler line: '");
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var3_8.append((String)var2_7);
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var3_8.append("'");
                                    var5_10 = var0;
                                    var6_13 = var4_9;
                                    var1_6.println(var3_8.toString());
                                    break;
                                }
                                var5_10 = var0;
                                var6_13 = var4_9;
                                var9_16 = var2_7.substring(0, var8_15).trim();
                                var5_10 = var0;
                                var6_13 = var4_9;
                                var1_6 = var2_7.substring(var8_15 + 1).trim();
                                var2_7 = var0;
                                var7_14 = var0;
                                var3_8 = var0;
                                var5_10 = var0;
                                var6_13 = var4_9;
                                try {
                                    var10_17 = Class.forName((String)var1_6).getDeclaredMethod("get", new Class[]{URL.class});
                                    var1_6 = var0;
                                    if (var0 == null) {
                                        var2_7 = var0;
                                        var7_14 = var0;
                                        var3_8 = var0;
                                        var5_10 = var0;
                                        var6_13 = var4_9;
                                        var2_7 = var0;
                                        var7_14 = var0;
                                        var3_8 = var0;
                                        var5_10 = var0;
                                        var6_13 = var4_9;
                                        var1_6 = new HashMap<String, Method>();
                                    }
                                    var2_7 = var1_6;
                                    var7_14 = var1_6;
                                    var3_8 = var1_6;
                                    var5_10 = var1_6;
                                    var6_13 = var4_9;
                                    var1_6.put(var9_16, var10_17);
                                }
                                catch (SecurityException var0_1) {
                                    var1_6 = var2_7;
                                    var5_10 = var2_7;
                                    var6_13 = var4_9;
                                    if (URLHandler.DEBUG) {
                                        var5_10 = var2_7;
                                        var6_13 = var4_9;
                                        System.err.println(var0_1);
                                        var1_6 = var2_7;
                                    }
                                }
                                catch (NoSuchMethodException var0_2) {
                                    var1_6 = var7_14;
                                    var5_10 = var7_14;
                                    var6_13 = var4_9;
                                    if (URLHandler.DEBUG) {
                                        var5_10 = var7_14;
                                        var6_13 = var4_9;
                                        System.err.println(var0_2);
                                        var1_6 = var7_14;
                                    }
                                }
                                catch (ClassNotFoundException var0_3) {
                                    var1_6 = var3_8;
                                    var5_10 = var3_8;
                                    var6_13 = var4_9;
                                    if (!URLHandler.DEBUG) break block23;
                                    var5_10 = var3_8;
                                    var6_13 = var4_9;
                                    System.err.println(var0_3);
                                    var1_6 = var3_8;
                                }
                            }
                        }
                    }
                    var5_10 = var1_6;
                    var6_13 = var4_9;
                    var2_7 = var4_9.readLine();
                    var0 = var1_6;
                    var1_6 = var2_7;
                }
                var5_10 = var0;
                var6_13 = var4_9;
                var4_9.close();
            }
            var1_6 = var0;
            if (var4_9 == null) ** GOTO lbl167
            var1_6 = var0;
        }
        catch (Throwable var0_4) {
            block24 : {
                try {
                    if (URLHandler.DEBUG) {
                        System.err.println(var0_4);
                    }
                    var1_6 = var5_10;
                    if (var6_13 == null) break block24;
                    var1_6 = var5_10;
                }
                catch (Throwable var5_12) {
                    if (var6_13 == null) throw var5_12;
                    try {
                        var6_13.close();
                        throw var5_12;
                    }
                    catch (IOException var0_5) {
                        // empty catch block
                    }
                    throw var5_12;
                }
                try {
                    var6_13.close();
                }
                catch (IOException var5_11) {
                    var5_10 = var1_6;
                }
                var1_6 = var5_10;
            }
            URLHandler.handlers = var1_6;
            return;
        }
        var4_9.close();
        var5_10 = var0;
    }

    public static URLHandler get(URL uRL) {
        block9 : {
            if (uRL == null) {
                return null;
            }
            Object object = uRL.getProtocol();
            Map<String, Method> map = handlers;
            if (map != null && (object = map.get(object)) != null) {
                try {
                    object = (URLHandler)((Method)object).invoke(null, uRL);
                    if (object != null) {
                        return object;
                    }
                }
                catch (InvocationTargetException invocationTargetException) {
                    if (DEBUG) {
                        System.err.println(invocationTargetException);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    if (DEBUG) {
                        System.err.println(illegalArgumentException);
                    }
                }
                catch (IllegalAccessException illegalAccessException) {
                    if (!DEBUG) break block9;
                    System.err.println(illegalAccessException);
                }
            }
        }
        return URLHandler.getDefault(uRL);
    }

    protected static URLHandler getDefault(URL uRL) {
        FileURLHandler fileURLHandler = null;
        URLHandler uRLHandler = null;
        String string = uRL.getProtocol();
        try {
            if (string.equals("file")) {
                uRLHandler = new FileURLHandler(uRL);
            } else if (string.equals("jar") || string.equals("wsjar")) {
                uRLHandler = new JarURLHandler(uRL);
            }
        }
        catch (Exception exception) {
            uRLHandler = fileURLHandler;
        }
        return uRLHandler;
    }

    public void guide(URLVisitor uRLVisitor, boolean bl) {
        this.guide(uRLVisitor, bl, true);
    }

    public abstract void guide(URLVisitor var1, boolean var2, boolean var3);

    private static class FileURLHandler
    extends URLHandler {
        File file;

        FileURLHandler(URL uRL) {
            Serializable serializable;
            try {
                serializable = new File(uRL.toURI());
                this.file = serializable;
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
            serializable = this.file;
            if (serializable != null && ((File)serializable).exists()) {
                return;
            }
            if (DEBUG) {
                PrintStream printStream = System.err;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("file does not exist - ");
                ((StringBuilder)serializable).append(uRL.toString());
                printStream.println(((StringBuilder)serializable).toString());
            }
            throw new IllegalArgumentException();
        }

        private void process(URLVisitor uRLVisitor, boolean bl, boolean bl2, String string, File[] arrfile) {
            if (arrfile != null) {
                for (int i = 0; i < arrfile.length; ++i) {
                    StringBuilder stringBuilder;
                    Object object = arrfile[i];
                    if (((File)object).isDirectory()) {
                        if (!bl) continue;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(string);
                        stringBuilder.append(((File)object).getName());
                        stringBuilder.append('/');
                        this.process(uRLVisitor, bl, bl2, stringBuilder.toString(), ((File)object).listFiles());
                        continue;
                    }
                    if (bl2) {
                        object = ((File)object).getName();
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(string);
                        stringBuilder.append(((File)object).getName());
                        object = stringBuilder.toString();
                    }
                    uRLVisitor.visit((String)object);
                }
            }
        }

        @Override
        public void guide(URLVisitor uRLVisitor, boolean bl, boolean bl2) {
            if (this.file.isDirectory()) {
                this.process(uRLVisitor, bl, bl2, "/", this.file.listFiles());
            } else {
                uRLVisitor.visit(this.file.getName());
            }
        }
    }

    private static class JarURLHandler
    extends URLHandler {
        JarFile jarFile;
        String prefix;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        JarURLHandler(URL serializable) {
            try {
                this.prefix = ((URL)serializable).getPath();
                int n = this.prefix.lastIndexOf("!/");
                if (n >= 0) {
                    this.prefix = this.prefix.substring(n + 2);
                }
                Serializable serializable2 = serializable;
                if (!((URL)serializable).getProtocol().equals("jar")) {
                    String string = ((URL)serializable).toString();
                    n = string.indexOf(":");
                    serializable2 = serializable;
                    if (n != -1) {
                        ((StringBuilder)serializable).append("jar");
                        ((StringBuilder)serializable).append(string.substring(n));
                        super(((StringBuilder)serializable).toString());
                    }
                }
                this.jarFile = ((JarURLConnection)((URL)serializable2).openConnection()).getJarFile();
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder;
                if (DEBUG) {
                    PrintStream printStream = System.err;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("icurb jar error: ");
                    stringBuilder.append(exception);
                    printStream.println(stringBuilder.toString());
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("jar error: ");
                stringBuilder.append(exception.getMessage());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void guide(URLVisitor uRLVisitor, boolean bl, boolean bl2) {
            try {
                Enumeration<JarEntry> enumeration = this.jarFile.entries();
                while (enumeration.hasMoreElements()) {
                    String string;
                    int n;
                    Object object = enumeration.nextElement();
                    if (((ZipEntry)object).isDirectory() || !((String)(object = ((ZipEntry)object).getName())).startsWith(this.prefix) || (n = (string = ((String)object).substring(this.prefix.length())).lastIndexOf(47)) > 0 && !bl) continue;
                    object = string;
                    if (bl2) {
                        object = string;
                        if (n != -1) {
                            object = string.substring(n + 1);
                        }
                    }
                    uRLVisitor.visit((String)object);
                }
                return;
            }
            catch (Exception exception) {
                if (!DEBUG) return;
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("icurb jar error: ");
                stringBuilder.append(exception);
                printStream.println(stringBuilder.toString());
            }
        }
    }

    public static interface URLVisitor {
        public void visit(String var1);
    }

}

