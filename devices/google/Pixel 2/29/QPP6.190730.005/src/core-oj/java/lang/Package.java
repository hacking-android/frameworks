/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package java.lang;

import dalvik.system.VMRuntime;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import sun.net.www.ParseUtil;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class Package
implements AnnotatedElement {
    private static Map<String, Manifest> mans;
    private static Map<String, Package> pkgs;
    private static Map<String, URL> urls;
    private final String implTitle;
    private final String implVendor;
    private final String implVersion;
    private final transient ClassLoader loader;
    private transient Class<?> packageInfo;
    private final String pkgName;
    private final URL sealBase;
    private final String specTitle;
    private final String specVendor;
    private final String specVersion;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        pkgs = new HashMap<String, Package>(31);
        urls = new HashMap<String, URL>(10);
        mans = new HashMap<String, Manifest>(10);
    }

    Package(String string, String string2, String string3, String string4, String string5, String string6, String string7, URL uRL, ClassLoader classLoader) {
        this.pkgName = string;
        this.implTitle = string5;
        this.implVersion = string6;
        this.implVendor = string7;
        this.specTitle = string2;
        this.specVersion = string3;
        this.specVendor = string4;
        this.sealBase = uRL;
        this.loader = classLoader;
    }

    private Package(String string, Manifest object, URL uRL, ClassLoader classLoader) {
        Object object2 = string.replace('.', '/').concat("/");
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        Object var13_13 = null;
        object2 = ((Manifest)object).getAttributes((String)object2);
        if (object2 != null) {
            string3 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_TITLE);
            string4 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_VERSION);
            string5 = ((Attributes)object2).getValue(Attributes.Name.SPECIFICATION_VENDOR);
            string6 = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            string7 = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            string8 = ((Attributes)object2).getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            string2 = ((Attributes)object2).getValue(Attributes.Name.SEALED);
        }
        Attributes attributes = ((Manifest)object).getMainAttributes();
        String string9 = string2;
        Object object3 = string3;
        String string10 = string4;
        object2 = string5;
        String string11 = string6;
        String string12 = string7;
        String string13 = string8;
        if (attributes != null) {
            object = string3;
            if (string3 == null) {
                object = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            }
            string3 = string4;
            if (string4 == null) {
                string3 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            }
            string4 = string5;
            if (string5 == null) {
                string4 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            }
            string5 = string6;
            if (string6 == null) {
                string5 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            }
            string6 = string7;
            if (string7 == null) {
                string6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            }
            string7 = string8;
            if (string8 == null) {
                string7 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            }
            string9 = string2;
            object3 = object;
            string10 = string3;
            object2 = string4;
            string11 = string5;
            string12 = string6;
            string13 = string7;
            if (string2 == null) {
                string9 = attributes.getValue(Attributes.Name.SEALED);
                string13 = string7;
                string12 = string6;
                string11 = string5;
                object2 = string4;
                string10 = string3;
                object3 = object;
            }
        }
        object = var13_13;
        if ("true".equalsIgnoreCase(string9)) {
            object = uRL;
        }
        this.pkgName = string;
        this.specTitle = object3;
        this.specVersion = string10;
        this.specVendor = object2;
        this.implTitle = string11;
        this.implVersion = string12;
        this.implVendor = string13;
        this.sealBase = object;
        this.loader = classLoader;
    }

    private static Package defineSystemPackage(final String string, final String string2) {
        return AccessController.doPrivileged(new PrivilegedAction<Package>(){

            @Override
            public Package run() {
                Object object;
                String string3 = string;
                Object object2 = object = (URL)urls.get(string2);
                if (object == null) {
                    Object object3 = new File(string2);
                    try {
                        object = object2 = ParseUtil.fileToEncodedURL((File)object3);
                    }
                    catch (MalformedURLException malformedURLException) {
                        // empty catch block
                    }
                    object2 = object;
                    if (object != null) {
                        urls.put(string2, object);
                        object2 = object;
                        if (((File)object3).isFile()) {
                            object2 = mans;
                            object3 = string2;
                            object2.put(object3, Package.loadManifest((String)object3));
                            object2 = object;
                        }
                    }
                }
                string3 = string3.substring(0, string3.length() - 1).replace('/', '.');
                object = (Manifest)mans.get(string2);
                object = object != null ? new Package(string3, (Manifest)object, (URL)object2, null) : new Package(string3, null, null, null, null, null, null, null, null);
                pkgs.put(string3, object);
                return object;
            }
        });
    }

    static Package getPackage(Class<?> object) {
        String string = ((Class)object).getName();
        int n = string.lastIndexOf(46);
        if (n != -1) {
            string = string.substring(0, n);
            if ((object = ((Class)object).getClassLoader()) != null) {
                return ((ClassLoader)object).getPackage(string);
            }
            return Package.getSystemPackage(string);
        }
        return null;
    }

    @CallerSensitive
    public static Package getPackage(String string) {
        ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (classLoader != null) {
            return classLoader.getPackage(string);
        }
        return Package.getSystemPackage(string);
    }

    private Class<?> getPackageInfo() {
        if (this.packageInfo == null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.pkgName);
                stringBuilder.append(".package-info");
                this.packageInfo = Class.forName(stringBuilder.toString(), false, this.loader);
            }
            catch (ClassNotFoundException classNotFoundException) {
                this.packageInfo = 1PackageInfoProxy.class;
            }
        }
        return this.packageInfo;
    }

    @CallerSensitive
    public static Package[] getPackages() {
        ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (classLoader != null) {
            return classLoader.getPackages();
        }
        return Package.getSystemPackages();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Package getSystemPackage(String string) {
        Map<String, Package> map = pkgs;
        synchronized (map) {
            Package package_;
            Package package_2 = package_ = pkgs.get(string);
            if (package_ != null) return package_2;
            String string2 = string.replace('.', '/').concat("/");
            string = Package.getSystemPackage0(string2);
            package_2 = package_;
            if (string == null) return package_2;
            return Package.defineSystemPackage(string2, string);
        }
    }

    private static native String getSystemPackage0(String var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Package[] getSystemPackages() {
        Object[] arrobject = Package.getSystemPackages0();
        Map<String, Package> map = pkgs;
        synchronized (map) {
            int n = 0;
            while (n < arrobject.length) {
                Package.defineSystemPackage(arrobject[n], Package.getSystemPackage0(arrobject[n]));
                ++n;
            }
            return pkgs.values().toArray(new Package[pkgs.size()]);
        }
    }

    private static native String[] getSystemPackages0();

    /*
     * Exception decompiling
     */
    private static Manifest loadManifest(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this.getPackageInfo().getAnnotation(class_);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.getPackageInfo().getAnnotations();
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> class_) {
        return this.getPackageInfo().getAnnotationsByType(class_);
    }

    public <A extends Annotation> A getDeclaredAnnotation(Class<A> class_) {
        return this.getPackageInfo().getDeclaredAnnotation(class_);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.getPackageInfo().getDeclaredAnnotations();
    }

    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> class_) {
        return this.getPackageInfo().getDeclaredAnnotationsByType(class_);
    }

    public String getImplementationTitle() {
        return this.implTitle;
    }

    public String getImplementationVendor() {
        return this.implVendor;
    }

    public String getImplementationVersion() {
        return this.implVersion;
    }

    public String getName() {
        return this.pkgName;
    }

    public String getSpecificationTitle() {
        return this.specTitle;
    }

    public String getSpecificationVendor() {
        return this.specVendor;
    }

    public String getSpecificationVersion() {
        return this.specVersion;
    }

    public int hashCode() {
        return this.pkgName.hashCode();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        return AnnotatedElement.super.isAnnotationPresent(class_);
    }

    public boolean isCompatibleWith(String object) throws NumberFormatException {
        Object object2 = this.specVersion;
        if (object2 != null && ((String)object2).length() >= 1) {
            int n;
            String[] arrstring = this.specVersion.split("\\.", -1);
            object2 = new int[arrstring.length];
            for (n = 0; n < arrstring.length; ++n) {
                object2[n] = Integer.parseInt(arrstring[n]);
                if (object2[n] >= 0) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(object2[n]);
                throw NumberFormatException.forInputString(((StringBuilder)object).toString());
            }
            arrstring = ((String)object).split("\\.", -1);
            object = new int[arrstring.length];
            for (n = 0; n < arrstring.length; ++n) {
                object[n] = Integer.parseInt(arrstring[n]);
                if (object[n] >= 0) {
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("");
                ((StringBuilder)object2).append((int)object[n]);
                throw NumberFormatException.forInputString(((StringBuilder)object2).toString());
            }
            int n2 = Math.max(((int[])object).length, ((int[])object2).length);
            for (n = 0; n < n2; ++n) {
                Object object3;
                Object object4 = n < ((Object)object2).length ? object2[n] : false;
                if (object4 < (object3 = n < ((Object)object).length ? object[n] : false)) {
                    return false;
                }
                if (object4 <= object3) continue;
                return true;
            }
            return true;
        }
        throw new NumberFormatException("Empty version string");
    }

    public boolean isSealed() {
        boolean bl = this.sealBase != null;
        return bl;
    }

    public boolean isSealed(URL uRL) {
        return uRL.equals(this.sealBase);
    }

    public String toString() {
        CharSequence charSequence;
        int n = VMRuntime.getRuntime().getTargetSdkVersion();
        if (n > 0 && n <= 24) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package ");
            stringBuilder.append(this.pkgName);
            return stringBuilder.toString();
        }
        CharSequence charSequence2 = this.specTitle;
        String string = this.specVersion;
        if (charSequence2 != null && ((String)charSequence2).length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(", ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        if (string != null && string.length() > 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(", version ");
            ((StringBuilder)charSequence2).append(string);
            string = ((StringBuilder)charSequence2).toString();
        } else {
            string = "";
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("package ");
        ((StringBuilder)charSequence2).append(this.pkgName);
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(string);
        return ((StringBuilder)charSequence2).toString();
    }

    class 1PackageInfoProxy {
        1PackageInfoProxy() {
        }
    }

}

