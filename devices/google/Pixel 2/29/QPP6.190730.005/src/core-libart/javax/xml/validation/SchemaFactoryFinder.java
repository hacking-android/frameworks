/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.validation;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.validation.SchemaFactory;
import libcore.io.IoUtils;

final class SchemaFactoryFinder {
    private static final int DEFAULT_LINE_LENGTH = 80;
    private static final Class SERVICE_CLASS;
    private static final String SERVICE_ID;
    private static final String W3C_XML_SCHEMA10_NS_URI = "http://www.w3.org/XML/XMLSchema/v1.0";
    private static final String W3C_XML_SCHEMA11_NS_URI = "http://www.w3.org/XML/XMLSchema/v1.1";
    private static boolean debug;
    private final ClassLoader classLoader;

    static {
        boolean bl = false;
        debug = false;
        CharSequence charSequence = System.getProperty("jaxp.debug");
        boolean bl2 = bl;
        if (charSequence != null) {
            bl2 = bl;
            if (!"false".equals(charSequence)) {
                bl2 = true;
            }
        }
        debug = bl2;
        SERVICE_CLASS = SchemaFactory.class;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("META-INF/services/");
        ((StringBuilder)charSequence).append(SERVICE_CLASS.getName());
        SERVICE_ID = ((StringBuilder)charSequence).toString();
    }

    public SchemaFactoryFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
        if (debug) {
            this.debugDisplayClassLoader();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private SchemaFactory _newFactory(String string) {
        block18 : {
            block17 : {
                object3 = new StringBuilder();
                object3.append(SchemaFactoryFinder.SERVICE_CLASS.getName());
                object3.append(":");
                object3.append(string);
                object3 = object3.toString();
                try {
                    if (SchemaFactoryFinder.debug) {
                        object2 = new StringBuilder();
                        object2.append("Looking up system property '");
                        object2.append((String)object3);
                        object2.append("'");
                        SchemaFactoryFinder.debugPrintln(object2.toString());
                    }
                    if ((string2 = System.getProperty((String)object3)) != null && string2.length() > 0) {
                        if (SchemaFactoryFinder.debug) {
                            object = new StringBuilder();
                            object.append("The value is '");
                            object.append(string2);
                            object.append("'");
                            SchemaFactoryFinder.debugPrintln(object.toString());
                        }
                        if ((schemaFactory = this.createInstance(string2)) == null) ** GOTO lbl45
                        return schemaFactory;
                    }
                    if (!SchemaFactoryFinder.debug) ** GOTO lbl45
                    SchemaFactoryFinder.debugPrintln("The property is undefined.");
                }
                catch (Throwable throwable) {
                    if (!SchemaFactoryFinder.debug) ** GOTO lbl45
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("failed to look up system property '");
                    stringBuilder.append((String)object3);
                    stringBuilder.append("'");
                    SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                    throwable.printStackTrace();
                }
lbl45: // 5 sources:
                try {
                    object3 = CacheHolder.access$200().getProperty((String)object3);
                    if (SchemaFactoryFinder.debug) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("found ");
                        stringBuilder.append((String)object3);
                        stringBuilder.append(" in $java.home/jaxp.properties");
                        SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                    }
                    if (object3 != null && (object3 = this.createInstance((String)object3)) != null) {
                        return object3;
                    }
                }
                catch (Exception exception) {
                    if (!SchemaFactoryFinder.debug) break block17;
                    exception.printStackTrace();
                }
            }
            object3 = this.createServiceFileIterator().iterator();
            break block18;
            catch (ThreadDeath threadDeath) {
                throw threadDeath;
            }
            catch (VirtualMachineError virtualMachineError) {
                throw virtualMachineError;
            }
            ** GOTO lbl45
        }
        while (object3.hasNext()) {
            uRL = (URL)object3.next();
            if (SchemaFactoryFinder.debug) {
                object = new StringBuilder();
                object.append("looking into ");
                object.append(uRL);
                SchemaFactoryFinder.debugPrintln(object.toString());
            }
            try {
                object = this.loadFromServicesFile(string, uRL.toExternalForm(), uRL.openStream());
                if (object == null) continue;
                return object;
            }
            catch (IOException iOException) {
                if (!SchemaFactoryFinder.debug) continue;
                stringBuilder = new StringBuilder();
                stringBuilder.append("failed to read ");
                stringBuilder.append(uRL);
                SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                iOException.printStackTrace();
            }
        }
        if (!string.equals("http://www.w3.org/2001/XMLSchema") && !string.equals("http://www.w3.org/XML/XMLSchema/v1.0")) {
            if (string.equals("http://www.w3.org/XML/XMLSchema/v1.1")) {
                if (SchemaFactoryFinder.debug == false) return this.createInstance("org.apache.xerces.jaxp.validation.XMLSchema11Factory");
                SchemaFactoryFinder.debugPrintln("attempting to use the platform default XML Schema 1.1 validator");
                return this.createInstance("org.apache.xerces.jaxp.validation.XMLSchema11Factory");
            }
            if (SchemaFactoryFinder.debug == false) return null;
            SchemaFactoryFinder.debugPrintln("all things were tried, but none was found. bailing out.");
            return null;
        }
        if (SchemaFactoryFinder.debug == false) return this.createInstance("org.apache.xerces.jaxp.validation.XMLSchemaFactory");
        SchemaFactoryFinder.debugPrintln("attempting to use the platform default XML Schema 1.0 validator");
        return this.createInstance("org.apache.xerces.jaxp.validation.XMLSchemaFactory");
    }

    private Iterable<URL> createServiceFileIterator() {
        Object object = this.classLoader;
        if (object == null) {
            return Collections.singleton(SchemaFactoryFinder.class.getClassLoader().getResource(SERVICE_ID));
        }
        try {
            Enumeration<URL> enumeration = ((ClassLoader)object).getResources(SERVICE_ID);
            if (debug && !enumeration.hasMoreElements()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("no ");
                ((StringBuilder)object).append(SERVICE_ID);
                ((StringBuilder)object).append(" file was found");
                SchemaFactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            object = Collections.list(enumeration);
            return object;
        }
        catch (IOException iOException) {
            if (debug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed to enumerate resources ");
                stringBuilder.append(SERVICE_ID);
                SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                iOException.printStackTrace();
            }
            return Collections.emptySet();
        }
    }

    private void debugDisplayClassLoader() {
        if (this.classLoader == Thread.currentThread().getContextClassLoader()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("using thread context class loader (");
            stringBuilder.append(this.classLoader);
            stringBuilder.append(") for search");
            SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
            return;
        }
        if (this.classLoader == ClassLoader.getSystemClassLoader()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("using system class loader (");
            stringBuilder.append(this.classLoader);
            stringBuilder.append(") for search");
            SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("using class loader (");
        stringBuilder.append(this.classLoader);
        stringBuilder.append(") for search");
        SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
    }

    private static void debugPrintln(String string) {
        if (debug) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JAXP: ");
            stringBuilder.append(string);
            printStream.println(stringBuilder.toString());
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private SchemaFactory loadFromServicesFile(String string, String object, InputStream object2) {
        Object object3;
        block9 : {
            if (debug) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Reading ");
                ((StringBuilder)object3).append((String)object);
                SchemaFactoryFinder.debugPrintln(((StringBuilder)object3).toString());
            }
            try {
                object3 = new InputStreamReader((InputStream)object2, "UTF-8");
                object = new BufferedReader((Reader)object3, 80);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                object = new BufferedReader(new InputStreamReader((InputStream)object2), 80);
            }
            Object var5_8 = null;
            do {
                do {
                    object2 = ((BufferedReader)object).readLine();
                    object3 = var5_8;
                    if (object2 == null) break block9;
                    int n = ((String)object2).indexOf(35);
                    object3 = object2;
                    if (n == -1) continue;
                    object3 = ((String)object2).substring(0, n);
                } while (((String)(object2 = ((String)object3).trim())).length() == 0);
                try {
                    object3 = this.createInstance((String)object2);
                    boolean bl = ((SchemaFactory)object3).isSchemaLanguageSupported(string);
                    if (!bl) continue;
                    break block9;
                }
                catch (Exception exception) {
                    continue;
                }
                break;
            } while (true);
            catch (IOException iOException) {
                object3 = var5_8;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object);
        return object3;
    }

    private static String which(Class class_) {
        return SchemaFactoryFinder.which(class_.getName(), class_.getClassLoader());
    }

    private static String which(String object, ClassLoader classLoader) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(((String)object).replace('.', '/'));
        charSequence.append(".class");
        charSequence = charSequence.toString();
        object = classLoader;
        if (classLoader == null) {
            object = ClassLoader.getSystemClassLoader();
        }
        object = (object = ((ClassLoader)object).getResource((String)charSequence)) != null ? ((URL)object).toString() : null;
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    SchemaFactory createInstance(String string) {
        try {
            Serializable serializable;
            if (debug) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("instantiating ");
                ((StringBuilder)serializable).append(string);
                SchemaFactoryFinder.debugPrintln(((StringBuilder)serializable).toString());
            }
            serializable = this.classLoader != null ? this.classLoader.loadClass(string) : Class.forName(string);
            if (debug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("loaded it from ");
                stringBuilder.append(SchemaFactoryFinder.which((Class)serializable));
                SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
            }
            if ((serializable = ((Class)serializable).newInstance()) instanceof SchemaFactory) {
                return (SchemaFactory)((Object)serializable);
            }
            if (!debug) return null;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(" is not assignable to ");
            ((StringBuilder)serializable).append(SERVICE_CLASS.getName());
            SchemaFactoryFinder.debugPrintln(((StringBuilder)serializable).toString());
            return null;
        }
        catch (Throwable throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed to instantiate ");
            stringBuilder.append(string);
            SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
            if (!debug) return null;
            throwable.printStackTrace();
        }
        return null;
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
        catch (VirtualMachineError virtualMachineError) {
            throw virtualMachineError;
        }
    }

    public SchemaFactory newFactory(String string) {
        if (string != null) {
            SchemaFactory schemaFactory = this._newFactory(string);
            if (debug) {
                if (schemaFactory != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("factory '");
                    stringBuilder.append(schemaFactory.getClass().getName());
                    stringBuilder.append("' was found for ");
                    stringBuilder.append(string);
                    SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unable to find a factory for ");
                    stringBuilder.append(string);
                    SchemaFactoryFinder.debugPrintln(stringBuilder.toString());
                }
            }
            return schemaFactory;
        }
        throw new NullPointerException("schemaLanguage == null");
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
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append(File.separator);
            ((StringBuilder)serializable).append("lib");
            ((StringBuilder)serializable).append(File.separator);
            ((StringBuilder)serializable).append("jaxp.properties");
            serializable = new File(((StringBuilder)serializable).toString());
            if (!((File)serializable).exists()) return;
            if (debug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Read properties file ");
                ((StringBuilder)object).append(serializable);
                SchemaFactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            object = new FileInputStream((File)serializable);
            cacheProps.load((InputStream)object);
            ((FileInputStream)object).close();
            return;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        ((FileInputStream)object).close();
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

        static /* synthetic */ Properties access$200() {
            return cacheProps;
        }
    }

}

