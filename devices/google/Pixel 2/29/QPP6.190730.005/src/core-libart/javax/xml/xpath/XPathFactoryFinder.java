/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

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
import java.util.Properties;
import javax.xml.xpath.XPathFactory;
import libcore.io.IoUtils;

final class XPathFactoryFinder {
    private static final int DEFAULT_LINE_LENGTH = 80;
    private static final Class SERVICE_CLASS;
    private static final String SERVICE_ID;
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
        SERVICE_CLASS = XPathFactory.class;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("META-INF/services/");
        ((StringBuilder)charSequence).append(SERVICE_CLASS.getName());
        SERVICE_ID = ((StringBuilder)charSequence).toString();
    }

    public XPathFactoryFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
        if (debug) {
            this.debugDisplayClassLoader();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private XPathFactory _newFactory(String string) {
        Object object;
        block19 : {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(SERVICE_CLASS.getName());
            ((StringBuilder)object2).append(":");
            ((StringBuilder)object2).append(string);
            object2 = ((StringBuilder)object2).toString();
            try {
                String string2;
                boolean bl = debug;
                if (bl) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Looking up system property '");
                    stringBuilder.append((String)object2);
                    stringBuilder.append("'");
                    XPathFactoryFinder.debugPrintln(stringBuilder.toString());
                }
                if ((string2 = System.getProperty((String)object2)) != null && string2.length() > 0) {
                    XPathFactory xPathFactory;
                    if (debug) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("The value is '");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append("'");
                        XPathFactoryFinder.debugPrintln(((StringBuilder)object).toString());
                    }
                    if ((xPathFactory = this.createInstance(string2)) != null) {
                        return xPathFactory;
                    }
                } else if (debug) {
                    XPathFactoryFinder.debugPrintln("The property is undefined.");
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                String string3 = CacheHolder.cacheProps.getProperty((String)object2);
                if (debug) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("found ");
                    ((StringBuilder)object2).append(string3);
                    ((StringBuilder)object2).append(" in $java.home/jaxp.properties");
                    XPathFactoryFinder.debugPrintln(((StringBuilder)object2).toString());
                }
                if (string3 != null && (object2 = this.createInstance(string3)) != null) {
                    return object2;
                }
            }
            catch (Exception exception) {
                if (!debug) break block19;
                exception.printStackTrace();
            }
        }
        for (URL uRL : this.createServiceFileIterator()) {
            if (debug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("looking into ");
                ((StringBuilder)object).append(uRL);
                XPathFactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            try {
                object = this.loadFromServicesFile(string, uRL.toExternalForm(), uRL.openStream());
                if (object == null) continue;
                return object;
            }
            catch (IOException iOException) {
                if (!debug) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("failed to read ");
                ((StringBuilder)object).append(uRL);
                XPathFactoryFinder.debugPrintln(((StringBuilder)object).toString());
                iOException.printStackTrace();
            }
        }
        if (string.equals("http://java.sun.com/jaxp/xpath/dom")) {
            if (debug) {
                XPathFactoryFinder.debugPrintln("attempting to use the platform default W3C DOM XPath lib");
            }
            return this.createInstance("org.apache.xpath.jaxp.XPathFactoryImpl");
        }
        if (debug) {
            XPathFactoryFinder.debugPrintln("all things were tried, but none was found. bailing out.");
        }
        return null;
    }

    private Iterable<URL> createServiceFileIterator() {
        Object object = this.classLoader;
        if (object == null) {
            return Collections.singleton(XPathFactoryFinder.class.getClassLoader().getResource(SERVICE_ID));
        }
        try {
            Enumeration<URL> enumeration = ((ClassLoader)object).getResources(SERVICE_ID);
            if (debug && !enumeration.hasMoreElements()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("no ");
                ((StringBuilder)object).append(SERVICE_ID);
                ((StringBuilder)object).append(" file was found");
                XPathFactoryFinder.debugPrintln(((StringBuilder)object).toString());
            }
            object = Collections.list(enumeration);
            return object;
        }
        catch (IOException iOException) {
            if (debug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed to enumerate resources ");
                stringBuilder.append(SERVICE_ID);
                XPathFactoryFinder.debugPrintln(stringBuilder.toString());
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
            XPathFactoryFinder.debugPrintln(stringBuilder.toString());
            return;
        }
        if (this.classLoader == ClassLoader.getSystemClassLoader()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("using system class loader (");
            stringBuilder.append(this.classLoader);
            stringBuilder.append(") for search");
            XPathFactoryFinder.debugPrintln(stringBuilder.toString());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("using class loader (");
        stringBuilder.append(this.classLoader);
        stringBuilder.append(") for search");
        XPathFactoryFinder.debugPrintln(stringBuilder.toString());
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
    private XPathFactory loadFromServicesFile(String string, String object, InputStream object2) {
        block9 : {
            Object object3;
            if (debug) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Reading ");
                ((StringBuilder)object3).append((String)object);
                XPathFactoryFinder.debugPrintln(((StringBuilder)object3).toString());
            }
            try {
                object3 = new InputStreamReader((InputStream)object2, "UTF-8");
                object = new BufferedReader((Reader)object3, 80);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                object = new BufferedReader(new InputStreamReader((InputStream)object2), 80);
            }
            object3 = null;
            do {
                do {
                    String string2 = ((BufferedReader)object).readLine();
                    object2 = object3;
                    if (string2 == null) break block9;
                    int n = string2.indexOf(35);
                    object2 = string2;
                    if (n == -1) continue;
                    object2 = string2.substring(0, n);
                } while (((String)(object2 = ((String)object2).trim())).length() == 0);
                try {
                    boolean bl = ((XPathFactory)(object2 = this.createInstance((String)object2))).isObjectModelSupported(string);
                    if (!bl) continue;
                    break block9;
                }
                catch (Exception exception) {
                    continue;
                }
                break;
            } while (true);
            catch (IOException iOException) {
                object2 = object3;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object);
        return object2;
    }

    private static String which(Class class_) {
        return XPathFactoryFinder.which(class_.getName(), class_.getClassLoader());
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
    XPathFactory createInstance(String string) {
        try {
            Serializable serializable;
            if (debug) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("instantiating ");
                ((StringBuilder)serializable).append(string);
                XPathFactoryFinder.debugPrintln(((StringBuilder)serializable).toString());
            }
            serializable = this.classLoader != null ? this.classLoader.loadClass(string) : Class.forName(string);
            if (debug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("loaded it from ");
                stringBuilder.append(XPathFactoryFinder.which((Class)serializable));
                XPathFactoryFinder.debugPrintln(stringBuilder.toString());
            }
            if ((serializable = ((Class)serializable).newInstance()) instanceof XPathFactory) {
                return (XPathFactory)((Object)serializable);
            }
            if (!debug) return null;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(" is not assignable to ");
            ((StringBuilder)serializable).append(SERVICE_CLASS.getName());
            XPathFactoryFinder.debugPrintln(((StringBuilder)serializable).toString());
            return null;
        }
        catch (Throwable throwable) {
            if (!debug) return null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed to instantiate ");
            stringBuilder.append(string);
            XPathFactoryFinder.debugPrintln(stringBuilder.toString());
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

    public XPathFactory newFactory(String string) {
        if (string != null) {
            XPathFactory xPathFactory = this._newFactory(string);
            if (debug) {
                if (xPathFactory != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("factory '");
                    stringBuilder.append(xPathFactory.getClass().getName());
                    stringBuilder.append("' was found for ");
                    stringBuilder.append(string);
                    XPathFactoryFinder.debugPrintln(stringBuilder.toString());
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unable to find a factory for ");
                    stringBuilder.append(string);
                    XPathFactoryFinder.debugPrintln(stringBuilder.toString());
                }
            }
            return xPathFactory;
        }
        throw new NullPointerException("uri == null");
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
                XPathFactoryFinder.debugPrintln(((StringBuilder)object).toString());
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
    }

}

