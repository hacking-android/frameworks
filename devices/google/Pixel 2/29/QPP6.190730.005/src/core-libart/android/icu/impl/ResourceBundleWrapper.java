/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheBase;
import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.ICUDebug;
import android.icu.impl.SoftCache;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class ResourceBundleWrapper
extends UResourceBundle {
    private static CacheBase<String, ResourceBundleWrapper, Loader> BUNDLE_CACHE = new SoftCache<String, ResourceBundleWrapper, Loader>(){

        @Override
        protected ResourceBundleWrapper createInstance(String string, Loader loader) {
            return loader.load();
        }
    };
    private static final boolean DEBUG = ICUDebug.enabled("resourceBundleWrapper");
    private String baseName = null;
    private ResourceBundle bundle = null;
    private List<String> keys = null;
    private String localeID = null;

    private ResourceBundleWrapper(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
    }

    static /* synthetic */ ResourceBundleWrapper access$100(String string, String string2, String string3, ClassLoader classLoader, boolean bl) {
        return ResourceBundleWrapper.instantiateBundle(string, string2, string3, classLoader, bl);
    }

    static /* synthetic */ void access$300(ResourceBundleWrapper resourceBundleWrapper, ResourceBundle resourceBundle) {
        resourceBundleWrapper.setParent(resourceBundle);
    }

    static /* synthetic */ String access$402(ResourceBundleWrapper resourceBundleWrapper, String string) {
        resourceBundleWrapper.baseName = string;
        return string;
    }

    static /* synthetic */ String access$502(ResourceBundleWrapper resourceBundleWrapper, String string) {
        resourceBundleWrapper.localeID = string;
        return string;
    }

    static /* synthetic */ boolean access$600() {
        return DEBUG;
    }

    static /* synthetic */ void access$700(ResourceBundleWrapper resourceBundleWrapper, ResourceBundle resourceBundle) {
        resourceBundleWrapper.setParent(resourceBundle);
    }

    static /* synthetic */ boolean access$800(String string, String string2) {
        return ResourceBundleWrapper.localeIDStartsWithLangSubtag(string, string2);
    }

    static /* synthetic */ void access$900(ResourceBundleWrapper resourceBundleWrapper) {
        resourceBundleWrapper.initKeysVector();
    }

    public static ResourceBundleWrapper getBundleInstance(String string, String string2, ClassLoader object, boolean bl) {
        Object object2 = object;
        if (object == null) {
            object2 = ClassLoaderUtil.getClassLoader();
        }
        if ((object = bl ? ResourceBundleWrapper.instantiateBundle(string, string2, null, (ClassLoader)object2, bl) : ResourceBundleWrapper.instantiateBundle(string, string2, ULocale.getDefault().getBaseName(), (ClassLoader)object2, bl)) == null) {
            object = "_";
            if (string.indexOf(47) >= 0) {
                object = "/";
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Could not find the bundle ");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(string2);
            throw new MissingResourceException(((StringBuilder)object2).toString(), "", "");
        }
        return object;
    }

    private void initKeysVector() {
        this.keys = new ArrayList<String>();
        for (ResourceBundleWrapper resourceBundleWrapper = this; resourceBundleWrapper != null; resourceBundleWrapper = (ResourceBundleWrapper)resourceBundleWrapper.getParent()) {
            Enumeration<String> enumeration = resourceBundleWrapper.bundle.getKeys();
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement();
                if (this.keys.contains(string)) continue;
                this.keys.add(string);
            }
        }
    }

    private static ResourceBundleWrapper instantiateBundle(final String string, final String string2, final String string3, final ClassLoader classLoader, final boolean bl) {
        CharSequence charSequence;
        CharSequence charSequence2;
        if (string2.isEmpty()) {
            charSequence = string;
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append('_');
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        if (bl) {
            charSequence2 = charSequence;
        } else {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append('#');
            ((StringBuilder)charSequence2).append(string3);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return BUNDLE_CACHE.getInstance((String)charSequence2, new Loader((String)charSequence){
            final /* synthetic */ String val$name;
            {
                this.val$name = string4;
            }

            /*
             * Exception decompiling
             */
            @Override
            public ResourceBundleWrapper load() {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                throw new IllegalStateException("Decompilation failed");
            }

        });
    }

    private static boolean localeIDStartsWithLangSubtag(String string, String string2) {
        boolean bl = string.startsWith(string2) && (string.length() == string2.length() || string.charAt(string2.length()) == '_');
        return bl;
    }

    @Override
    protected String getBaseName() {
        return this.bundle.getClass().getName().replace('.', '/');
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keys);
    }

    @Override
    protected String getLocaleID() {
        return this.localeID;
    }

    @Override
    public UResourceBundle getParent() {
        return (UResourceBundle)this.parent;
    }

    @Override
    public ULocale getULocale() {
        return new ULocale(this.localeID);
    }

    @Override
    protected Object handleGetObject(String string) {
        Object object;
        Object object2 = this;
        Object object3 = null;
        do {
            object = object3;
            if (object2 == null) break;
            try {
                object = ((ResourceBundleWrapper)object2).bundle.getObject(string);
            }
            catch (MissingResourceException missingResourceException) {
                object2 = (ResourceBundleWrapper)((ResourceBundleWrapper)object2).getParent();
                continue;
            }
            break;
        } while (true);
        if (object != null) {
            return object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Can't find resource for bundle ");
        ((StringBuilder)object2).append(this.baseName);
        ((StringBuilder)object2).append(", key ");
        ((StringBuilder)object2).append(string);
        throw new MissingResourceException(((StringBuilder)object2).toString(), this.getClass().getName(), string);
    }

    private static abstract class Loader {
        private Loader() {
        }

        abstract ResourceBundleWrapper load();
    }

}

