/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import libcore.io.IoUtils;

final class FactoryFinder {
    private static final String CLASS_NAME = "javax.xml.datatype.FactoryFinder";
    private static final int DEFAULT_LINE_LENGTH = 80;
    private static boolean debug;

    static {
        boolean bl = false;
        debug = false;
        String string = System.getProperty("jaxp.debug");
        boolean bl2 = bl;
        if (string != null) {
            bl2 = bl;
            if (!"false".equals(string)) {
                bl2 = true;
            }
        }
        debug = bl2;
    }

    private FactoryFinder() {
    }

    private static void debugPrintln(String string) {
        if (debug) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javax.xml.datatype.FactoryFinder:");
            stringBuilder.append(string);
            printStream.println(stringBuilder.toString());
        }
    }

    static Object find(String charSequence, String charSequence2) throws ConfigurationError {
        ClassLoader classLoader;
        Object object;
        block9 : {
            classLoader = FactoryFinder.findClassLoader();
            object = System.getProperty((String)charSequence);
            if (object != null && ((String)object).length() > 0) {
                if (debug) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("found ");
                    ((StringBuilder)charSequence2).append((String)object);
                    ((StringBuilder)charSequence2).append(" in the system property ");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    FactoryFinder.debugPrintln(((StringBuilder)charSequence2).toString());
                }
                return FactoryFinder.newInstance((String)object, classLoader);
            }
            String string = CacheHolder.cacheProps.getProperty((String)charSequence);
            if (debug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("found ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" in $java.home/jaxp.properties");
                FactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            if (string == null) break block9;
            try {
                object = FactoryFinder.newInstance(string, classLoader);
                return object;
            }
            catch (Exception exception) {
                if (!debug) break block9;
                exception.printStackTrace();
            }
        }
        if ((object = FactoryFinder.findJarServiceProvider((String)charSequence)) != null) {
            return object;
        }
        if (charSequence2 != null) {
            if (debug) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("loaded from fallback value: ");
                ((StringBuilder)charSequence).append((String)charSequence2);
                FactoryFinder.debugPrintln(((StringBuilder)charSequence).toString());
            }
            return FactoryFinder.newInstance((String)charSequence2, classLoader);
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Provider for ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(" cannot be found");
        throw new ConfigurationError(((StringBuilder)charSequence2).toString(), null);
    }

    private static ClassLoader findClassLoader() throws ConfigurationError {
        Object object;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (debug) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Using context class loader: ");
            ((StringBuilder)object).append(classLoader);
            FactoryFinder.debugPrintln(((StringBuilder)object).toString());
        }
        object = classLoader;
        if (classLoader == null) {
            classLoader = FactoryFinder.class.getClassLoader();
            object = classLoader;
            if (debug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Using the class loader of FactoryFinder: ");
                ((StringBuilder)object).append(classLoader);
                FactoryFinder.debugPrintln(((StringBuilder)object).toString());
                object = classLoader;
            }
        }
        return object;
    }

    private static Object findJarServiceProvider(String object) throws ConfigurationError {
        block10 : {
            Object object2;
            Object object3;
            block11 : {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("META-INF/services/");
                ((StringBuilder)object2).append((String)object);
                Object object4 = ((StringBuilder)object2).toString();
                object = null;
                object2 = Thread.currentThread().getContextClassLoader();
                if (object2 != null) {
                    object = ((ClassLoader)object2).getResourceAsStream((String)object4);
                }
                object3 = object;
                if (object == null) {
                    object2 = FactoryFinder.class.getClassLoader();
                    object3 = ((ClassLoader)object2).getResourceAsStream((String)object4);
                }
                if (object3 == null) {
                    return null;
                }
                if (debug) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("found jar resource=");
                    ((StringBuilder)object).append((String)object4);
                    ((StringBuilder)object).append(" using ClassLoader: ");
                    ((StringBuilder)object).append(object2);
                    FactoryFinder.debugPrintln(((StringBuilder)object).toString());
                }
                try {
                    object4 = new InputStreamReader((InputStream)object3, "UTF-8");
                    object = new BufferedReader((Reader)object4, 80);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    object = new BufferedReader(new InputStreamReader((InputStream)object3), 80);
                }
                try {
                    object3 = ((BufferedReader)object).readLine();
                    if (object3 == null || "".equals(object3)) break block10;
                    if (!debug) break block11;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("found in resource, value=");
                }
                catch (IOException iOException) {
                    return null;
                }
                finally {
                    IoUtils.closeQuietly((AutoCloseable)object);
                }
                ((StringBuilder)object).append((String)object3);
                FactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            return FactoryFinder.newInstance((String)object3, (ClassLoader)object2);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Object newInstance(String var0, ClassLoader var1_1) throws ConfigurationError {
        if (var1_1 != null) ** GOTO lbl5
        try {
            block3 : {
                var1_1 = Class.forName(var0);
                break block3;
lbl5: // 1 sources:
                var1_1 = var1_1.loadClass(var0);
            }
            if (FactoryFinder.debug == false) return var1_1.newInstance();
            var2_3 = new StringBuilder();
            var2_3.append("Loaded ");
            var2_3.append(var0);
            var2_3.append(" from ");
            var2_3.append(FactoryFinder.which((Class)var1_1));
            FactoryFinder.debugPrintln(var2_3.toString());
            return var1_1.newInstance();
        }
        catch (Exception var2_4) {
            var1_1 = new StringBuilder();
            var1_1.append("Provider ");
            var1_1.append(var0);
            var1_1.append(" could not be instantiated: ");
            var1_1.append(var2_4);
            throw new ConfigurationError(var1_1.toString(), var2_4);
        }
        catch (ClassNotFoundException var1_2) {
            var2_5 = new StringBuilder();
            var2_5.append("Provider ");
            var2_5.append(var0);
            var2_5.append(" not found");
            throw new ConfigurationError(var2_5.toString(), var1_2);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String which(Class object) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Class)object).getName().replace('.', '/'));
            stringBuilder.append(".class");
            String string = stringBuilder.toString();
            object = ((Class)object).getClassLoader();
            object = object != null ? ((ClassLoader)object).getResource(string) : ClassLoader.getSystemResource(string);
            if (object == null) return "unknown location";
            return ((URL)object).toString();
        }
        catch (Throwable throwable) {
            if (!debug) return "unknown location";
            throwable.printStackTrace();
        }
        return "unknown location";
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
        catch (VirtualMachineError virtualMachineError) {
            throw virtualMachineError;
        }
    }

    private static class CacheHolder {
        private static Properties cacheProps = new Properties();

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static {
            Object object = System.getProperty("java.home");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(File.separator);
            stringBuilder.append("lib");
            stringBuilder.append(File.separator);
            stringBuilder.append("jaxp.properties");
            object = new File(stringBuilder.toString());
            if (!((File)object).exists()) return;
            if (debug) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Read properties file ");
                stringBuilder.append(object);
                FactoryFinder.debugPrintln(stringBuilder.toString());
            }
            FileInputStream fileInputStream = new FileInputStream((File)object);
            cacheProps.load(fileInputStream);
            fileInputStream.close();
            return;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        fileInputStream.close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
                            throw throwable2;
                        }
                        catch (Exception exception) {
                            if (!debug) return;
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }

        private CacheHolder() {
        }
    }

    static class ConfigurationError
    extends Error {
        private static final long serialVersionUID = -3644413026244211347L;
        private Exception exception;

        ConfigurationError(String string, Exception exception) {
            super(string);
            this.exception = exception;
        }

        Exception getException() {
            return this.exception;
        }
    }

}

