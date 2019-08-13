/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import org.apache.xml.dtm.SecuritySupport;

class ObjectFactory {
    private static final boolean DEBUG = false;
    private static final String DEFAULT_PROPERTIES_FILENAME = "xalan.properties";
    private static final String SERVICES_PATH = "META-INF/services/";
    private static long fLastModified;
    private static Properties fXalanProperties;

    static {
        fXalanProperties = null;
        fLastModified = -1L;
    }

    ObjectFactory() {
    }

    static Object createObject(String string, String string2) throws ConfigurationError {
        return ObjectFactory.createObject(string, null, string2);
    }

    static Object createObject(String string, String object, String charSequence) throws ConfigurationError {
        if ((object = ObjectFactory.lookUpFactoryClass(string, (String)object, (String)charSequence)) != null) {
            try {
                charSequence = ((Class)object).newInstance();
                object = new StringBuilder();
                ((StringBuilder)object).append("created new instance of factory ");
                ((StringBuilder)object).append(string);
                ObjectFactory.debugPrintln(((StringBuilder)object).toString());
                return charSequence;
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Provider for factory ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" could not be instantiated: ");
                ((StringBuilder)charSequence).append(exception);
                throw new ConfigurationError(((StringBuilder)charSequence).toString(), exception);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Provider for ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" cannot be found");
        throw new ConfigurationError(((StringBuilder)object).toString(), null);
    }

    private static void debugPrintln(String string) {
    }

    static ClassLoader findClassLoader() throws ConfigurationError {
        ClassLoader classLoader;
        SecuritySupport securitySupport = SecuritySupport.getInstance();
        ClassLoader classLoader2 = securitySupport.getContextClassLoader();
        ClassLoader classLoader3 = classLoader = securitySupport.getSystemClassLoader();
        do {
            if (classLoader2 == classLoader3) {
                classLoader2 = ObjectFactory.class.getClassLoader();
                classLoader3 = classLoader;
                do {
                    if (classLoader2 == classLoader3) {
                        return classLoader;
                    }
                    if (classLoader3 == null) {
                        return classLoader2;
                    }
                    classLoader3 = securitySupport.getParentClassLoader(classLoader3);
                } while (true);
            }
            if (classLoader3 == null) {
                return classLoader2;
            }
            classLoader3 = securitySupport.getParentClassLoader(classLoader3);
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String findJarServiceProviderName(String object) {
        Object object2;
        SecuritySupport securitySupport = SecuritySupport.getInstance();
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append(SERVICES_PATH);
        ((StringBuilder)object3).append((String)object);
        String string = ((StringBuilder)object3).toString();
        ClassLoader classLoader = ObjectFactory.findClassLoader();
        object = object2 = securitySupport.getResourceAsStream(classLoader, string);
        object3 = classLoader;
        if (object2 == null) {
            ClassLoader classLoader2 = ObjectFactory.class.getClassLoader();
            object = object2;
            object3 = classLoader;
            if (classLoader != classLoader2) {
                object3 = classLoader2;
                object = securitySupport.getResourceAsStream((ClassLoader)object3, string);
            }
        }
        if (object == null) {
            return null;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("found jar resource=");
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append(" using ClassLoader: ");
        ((StringBuilder)object2).append(object3);
        ObjectFactory.debugPrintln(((StringBuilder)object2).toString());
        try {
            object2 = new InputStreamReader((InputStream)object, "UTF-8");
            object = object3 = new BufferedReader((Reader)object2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            object = new BufferedReader(new InputStreamReader((InputStream)object));
        }
        object3 = ((BufferedReader)object).readLine();
        try {
            ((BufferedReader)object).close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (object3 == null) return null;
        if ("".equals(object3)) return null;
        object = new StringBuilder();
        ((StringBuilder)object).append("found in resource, value=");
        ((StringBuilder)object).append((String)object3);
        ObjectFactory.debugPrintln(((StringBuilder)object).toString());
        return object3;
        catch (Throwable throwable) {
            try {
                ((BufferedReader)object).close();
                throw throwable;
            }
            catch (IOException iOException) {
                throw throwable;
            }
        }
        catch (IOException iOException) {
            try {
                ((BufferedReader)object).close();
                return null;
            }
            catch (IOException iOException2) {
                return null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Class findProviderClass(String class_, ClassLoader classLoader, boolean bl) throws ClassNotFoundException, ConfigurationError {
        Object object;
        Object object2 = System.getSecurityManager();
        if (object2 != null) {
            int n = ((String)((Object)class_)).lastIndexOf(".");
            object = class_;
            if (n != -1) {
                object = ((String)((Object)class_)).substring(0, n);
            }
            ((SecurityManager)object2).checkPackageAccess((String)object);
        }
        if (classLoader == null) {
            return Class.forName(class_);
        }
        try {
            object = classLoader.loadClass((String)((Object)class_));
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (!bl) throw classNotFoundException;
            object2 = ObjectFactory.class.getClassLoader();
            if (object2 == null) {
                return Class.forName(class_);
            }
            if (classLoader == object2) throw classNotFoundException;
            return ((ClassLoader)object2).loadClass((String)((Object)class_));
        }
    }

    static Class lookUpFactoryClass(String string) throws ConfigurationError {
        return ObjectFactory.lookUpFactoryClass(string, null, null);
    }

    static Class lookUpFactoryClass(String string, String charSequence, String object) throws ConfigurationError {
        charSequence = ObjectFactory.lookUpFactoryClassName(string, (String)charSequence, (String)object);
        ClassLoader classLoader = ObjectFactory.findClassLoader();
        string = charSequence;
        if (charSequence == null) {
            string = object;
        }
        try {
            object = ObjectFactory.findProviderClass(string, classLoader, true);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("created new instance of ");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(" using ClassLoader: ");
            ((StringBuilder)charSequence).append(classLoader);
            ObjectFactory.debugPrintln(((StringBuilder)charSequence).toString());
            return object;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Provider ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" could not be instantiated: ");
            ((StringBuilder)object).append(exception);
            throw new ConfigurationError(((StringBuilder)object).toString(), exception);
        }
        catch (ClassNotFoundException classNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Provider ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" not found");
            throw new ConfigurationError(((StringBuilder)object).toString(), classNotFoundException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static String lookUpFactoryClassName(String var0, String var1_3, String var2_9) {
        block36 : {
            block38 : {
                block35 : {
                    block37 : {
                        block39 : {
                            block41 : {
                                block40 : {
                                    block33 : {
                                        block34 : {
                                            var3_12 = var1_3;
                                            var4_15 = SecuritySupport.getInstance();
                                            try {
                                                var2_9 = var4_15.getSystemProperty((String)var0);
                                                if (var2_9 != null) {
                                                    var1_3 = new StringBuilder();
                                                    var1_3.append("found system property, value=");
                                                    var1_3.append((String)var2_9);
                                                    ObjectFactory.debugPrintln(var1_3.toString());
                                                    return var2_9;
                                                }
                                            }
                                            catch (SecurityException var1_4) {
                                                // empty catch block
                                            }
                                            var5_16 = null;
                                            var6_17 = null;
                                            var7_18 = null;
                                            var8_19 = null;
                                            if (var3_12 != null) break block39;
                                            var2_9 = null;
                                            var9_20 = null;
                                            var10_22 = false;
                                            var5_16 = var4_15.getSystemProperty("java.home");
                                            var1_3 = new StringBuilder();
                                            var1_3.append(var5_16);
                                            var1_3.append(File.separator);
                                            var1_3.append("lib");
                                            var1_3.append(File.separator);
                                            var1_3.append("xalan.properties");
                                            var2_9 = var1_3 = var1_3.toString();
                                            var1_3 = var9_20;
                                            try {
                                                var1_3 = var9_20;
                                                var1_3 = var3_12 = new File((String)var2_9);
                                                var11_23 = var4_15.getFileExists((File)var3_12);
                                                var1_3 = var2_9;
                                                var9_20 = var3_12;
                                                break block33;
                                            }
                                            catch (SecurityException var3_13) {
                                                var3_12 = var2_9;
                                                var2_9 = var1_3;
                                                break block34;
                                            }
                                            catch (SecurityException var1_5) {
                                                // empty catch block
                                            }
                                        }
                                        ObjectFactory.fLastModified = -1L;
                                        ObjectFactory.fXalanProperties = null;
                                        var1_3 = var3_12;
                                        var11_23 = var10_22;
                                        var9_20 = var2_9;
                                    }
                                    // MONITORENTER : org.apache.xml.dtm.ObjectFactory.class
                                    var12_24 = false;
                                    var13_25 = false;
                                    var14_26 = false;
                                    var5_16 = null;
                                    var6_17 = null;
                                    var7_18 = null;
                                    var15_27 = var12_24;
                                    var3_12 = var5_16;
                                    var16_28 = var13_25;
                                    var2_9 = var6_17;
                                    if (ObjectFactory.fLastModified < 0L) break block40;
                                    if (!var11_23) ** GOTO lbl-1000
                                    var15_27 = var12_24;
                                    var3_12 = var5_16;
                                    var16_28 = var13_25;
                                    var2_9 = var6_17;
                                    var17_29 = ObjectFactory.fLastModified;
                                    var15_27 = var12_24;
                                    var3_12 = var5_16;
                                    var16_28 = var13_25;
                                    var2_9 = var6_17;
                                    var19_30 = var4_15.getLastModified((File)var9_20);
                                    var15_27 = var12_24;
                                    var3_12 = var5_16;
                                    var16_28 = var13_25;
                                    var2_9 = var6_17;
                                    ObjectFactory.fLastModified = var19_30;
                                    if (var17_29 < var19_30) {
                                        var21_31 = true;
                                    } else lbl-1000: // 2 sources:
                                    {
                                        var21_31 = var14_26;
                                        if (!var11_23) {
                                            var15_27 = var12_24;
                                            var3_12 = var5_16;
                                            var16_28 = var13_25;
                                            var2_9 = var6_17;
                                            ObjectFactory.fLastModified = -1L;
                                            var15_27 = var12_24;
                                            var3_12 = var5_16;
                                            var16_28 = var13_25;
                                            var2_9 = var6_17;
                                            ObjectFactory.fXalanProperties = null;
                                            var21_31 = var14_26;
                                        }
                                    }
                                    break block41;
                                }
                                var21_31 = var14_26;
                                if (var11_23) {
                                    var15_27 = true;
                                    var16_28 = true;
                                    var21_31 = true;
                                    var3_12 = var5_16;
                                    var2_9 = var6_17;
                                    ObjectFactory.fLastModified = var4_15.getLastModified((File)var9_20);
                                }
                            }
                            var2_9 = var7_18;
                            if (var21_31) {
                                var15_27 = var21_31;
                                var3_12 = var5_16;
                                var16_28 = var21_31;
                                var2_9 = var6_17;
                                var15_27 = var21_31;
                                var3_12 = var5_16;
                                var16_28 = var21_31;
                                var2_9 = var6_17;
                                var7_18 = new Properties();
                                var15_27 = var21_31;
                                var3_12 = var5_16;
                                var16_28 = var21_31;
                                var2_9 = var6_17;
                                ObjectFactory.fXalanProperties = var7_18;
                                var15_27 = var21_31;
                                var3_12 = var5_16;
                                var16_28 = var21_31;
                                var2_9 = var6_17;
                                var9_20 = var4_15.getFileInputStream((File)var9_20);
                                var15_27 = var21_31;
                                var3_12 = var9_20;
                                var16_28 = var21_31;
                                var2_9 = var9_20;
                                ObjectFactory.fXalanProperties.load((InputStream)var9_20);
                                var2_9 = var9_20;
                            }
                            if (var2_9 == null) break block35;
                            var2_9.close();
                        }
                        var8_19 = null;
                        var9_21 = null;
                        var1_3 = var9_21;
                        var2_9 = var8_19;
                        var1_3 = var9_21;
                        var2_9 = var8_19;
                        var22_32 = new File((String)var3_12);
                        var1_3 = var9_21;
                        var2_9 = var8_19;
                        var9_21 = var4_15.getFileInputStream(var22_32);
                        var1_3 = var9_21;
                        var2_9 = var9_21;
                        var1_3 = var9_21;
                        var2_9 = var9_21;
                        var8_19 = new Properties();
                        var1_3 = var9_21;
                        var2_9 = var9_21;
                        var8_19.load(var9_21);
                        var1_3 = var9_21;
                        var2_9 = var9_21;
                        var2_9 = var8_19 = var8_19.getProperty((String)var0);
                        var8_19 = var3_12;
                        var1_3 = var2_9;
                        if (var9_21 == null) break block36;
                        var1_3 = var2_9;
                        var9_21.close();
                        var1_3 = var2_9;
                        break block37;
                        {
                            catch (IOException var2_11) {}
                        }
                        catch (Throwable var0_2) {
                            if (var1_3 == null) throw var0_2;
                            try {
                                var1_3.close();
                                throw var0_2;
                            }
                            catch (IOException var1_7) {
                                throw var0_2;
                            }
                        }
                        catch (Exception var1_8) {
                            var8_19 = var3_12;
                            var1_3 = var7_18;
                            if (var2_9 == null) break block36;
                            var1_3 = var6_17;
                            var2_9.close();
                            var1_3 = var5_16;
                        }
                    }
                    var8_19 = var3_12;
                    break block36;
                    {
                        catch (IOException var2_10) {}
                        catch (Throwable var0_1) {
                            break block38;
                        }
                        catch (Exception var3_14) {}
                        var15_27 = var16_28;
                        var3_12 = var2_9;
                        {
                            ObjectFactory.fXalanProperties = null;
                            var15_27 = var16_28;
                            var3_12 = var2_9;
                            ObjectFactory.fLastModified = -1L;
                            if (var2_9 == null) break block35;
                        }
                        {
                            var2_9.close();
                        }
                    }
                }
                // MONITOREXIT : org.apache.xml.dtm.ObjectFactory.class
                var3_12 = ObjectFactory.fXalanProperties;
                var2_9 = var8_19;
                if (var3_12 != null) {
                    var2_9 = var3_12.getProperty((String)var0);
                }
                var8_19 = var1_3;
                var1_3 = var2_9;
                break block36;
            }
            if (var3_12 == null) throw var0_1;
            try {
                var3_12.close();
                throw var0_1;
            }
            catch (IOException var1_6) {
                throw var0_1;
            }
        }
        if (var1_3 == null) return ObjectFactory.findJarServiceProviderName((String)var0);
        var0 = new StringBuilder();
        var0.append("found in ");
        var0.append((String)var8_19);
        var0.append(", value=");
        var0.append((String)var1_3);
        ObjectFactory.debugPrintln(var0.toString());
        return var1_3;
    }

    static Object newInstance(String string, ClassLoader object, boolean bl) throws ConfigurationError {
        try {
            Class class_ = ObjectFactory.findProviderClass(string, (ClassLoader)object, bl);
            Object t = class_.newInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("created new instance of ");
            stringBuilder.append(class_);
            stringBuilder.append(" using ClassLoader: ");
            stringBuilder.append(object);
            ObjectFactory.debugPrintln(stringBuilder.toString());
            return t;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Provider ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" could not be instantiated: ");
            ((StringBuilder)object).append(exception);
            throw new ConfigurationError(((StringBuilder)object).toString(), exception);
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provider ");
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new ConfigurationError(stringBuilder.toString(), classNotFoundException);
        }
    }

    static class ConfigurationError
    extends Error {
        static final long serialVersionUID = 5122054096615067992L;
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

