/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.util.locale.BaseLocale;
import sun.util.locale.LocaleObjectCache;

public abstract class ResourceBundle {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INITIAL_CACHE_SIZE = 32;
    private static final ResourceBundle NONEXISTENT_BUNDLE = new ResourceBundle(){

        @Override
        public Enumeration<String> getKeys() {
            return null;
        }

        @Override
        protected Object handleGetObject(String string) {
            return null;
        }

        public String toString() {
            return "NONEXISTENT_BUNDLE";
        }
    };
    private static final ConcurrentMap<CacheKey, BundleReference> cacheList = new ConcurrentHashMap<CacheKey, BundleReference>(32);
    private static final ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
    private volatile CacheKey cacheKey;
    private volatile boolean expired;
    private volatile Set<String> keySet;
    private Locale locale = null;
    private String name;
    protected ResourceBundle parent = null;

    private static boolean checkList(List<?> list) {
        boolean bl = list != null && !list.isEmpty();
        boolean bl2 = bl;
        if (bl) {
            int n = list.size();
            int n2 = 0;
            do {
                bl2 = bl;
                if (!bl) break;
                bl2 = bl;
                if (n2 >= n) break;
                bl = list.get(n2) != null;
                ++n2;
            } while (true);
        }
        return bl2;
    }

    @CallerSensitive
    public static final void clearCache() {
        ResourceBundle.clearCache(ResourceBundle.getLoader(Reflection.getCallerClass()));
    }

    public static final void clearCache(ClassLoader classLoader) {
        if (classLoader != null) {
            Set set = cacheList.keySet();
            for (CacheKey cacheKey : set) {
                if (cacheKey.getLoader() != classLoader) continue;
                set.remove(cacheKey);
            }
            return;
        }
        throw new NullPointerException();
    }

    private static ResourceBundle findBundle(CacheKey object, List<Locale> object2, List<String> object3, int n, Control control, ResourceBundle object4) {
        Locale locale = object2.get(n);
        ResourceBundle resourceBundle = null;
        if (n != object2.size() - 1) {
            object2 = ResourceBundle.findBundle((CacheKey)object, object2, object3, n + 1, control, (ResourceBundle)object4);
        } else {
            object2 = resourceBundle;
            if (object4 != null) {
                object2 = resourceBundle;
                if (Locale.ROOT.equals(locale)) {
                    return object4;
                }
            }
        }
        while ((object4 = referenceQueue.poll()) != null) {
            cacheList.remove(((CacheKeyReference)object4).getCacheKey());
        }
        boolean bl = false;
        ((CacheKey)object).setLocale(locale);
        resourceBundle = ResourceBundle.findBundleInCache((CacheKey)object, control);
        if (ResourceBundle.isValidBundle(resourceBundle)) {
            boolean bl2;
            bl = bl2 = resourceBundle.expired;
            if (!bl2) {
                if (resourceBundle.parent == object2) {
                    return resourceBundle;
                }
                object4 = (BundleReference)cacheList.get(object);
                bl = bl2;
                if (object4 != null) {
                    bl = bl2;
                    if (((SoftReference)object4).get() == resourceBundle) {
                        cacheList.remove(object, object4);
                        bl = bl2;
                    }
                }
            }
        }
        if (resourceBundle != NONEXISTENT_BUNDLE) {
            object4 = (CacheKey)((CacheKey)object).clone();
            try {
                object3 = ResourceBundle.loadBundle((CacheKey)object, object3, control, bl);
                if (object3 != null) {
                    if (((ResourceBundle)object3).parent == null) {
                        ((ResourceBundle)object3).setParent((ResourceBundle)object2);
                    }
                    ((ResourceBundle)object3).locale = locale;
                    object = ResourceBundle.putBundleInCache((CacheKey)object, (ResourceBundle)object3, control);
                    return object;
                }
                ResourceBundle.putBundleInCache((CacheKey)object, NONEXISTENT_BUNDLE, control);
            }
            finally {
                if (((CacheKey)object4).getCause() instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return object2;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static ResourceBundle findBundleInCache(CacheKey object, Control control) {
        void var1_5;
        ResourceBundle resourceBundle;
        void var0_3;
        Object object2;
        block15 : {
            BundleReference bundleReference = (BundleReference)cacheList.get(object);
            if (bundleReference == null) {
                return null;
            }
            resourceBundle = (ResourceBundle)bundleReference.get();
            if (resourceBundle == null) {
                return null;
            }
            object2 = resourceBundle.parent;
            if (object2 != null && ((ResourceBundle)object2).expired) {
                resourceBundle.expired = true;
                resourceBundle.cacheKey = null;
                cacheList.remove(object, bundleReference);
                return null;
            }
            object2 = bundleReference.getCacheKey();
            long l = ((CacheKey)object2).expirationTime;
            if (resourceBundle.expired) return resourceBundle;
            if (l < 0L) return resourceBundle;
            if (l > System.currentTimeMillis()) {
                return resourceBundle;
            }
            if (resourceBundle == NONEXISTENT_BUNDLE) {
                cacheList.remove(object, bundleReference);
                return null;
            }
            // MONITORENTER : resourceBundle
            long l2 = ((CacheKey)object2).expirationTime;
            if (resourceBundle.expired) return resourceBundle;
            if (l2 < 0L) return resourceBundle;
            l = System.currentTimeMillis();
            if (l2 > l) return resourceBundle;
            try {
                resourceBundle.expired = var1_5.needsReload(((CacheKey)object2).getName(), ((CacheKey)object2).getLocale(), ((CacheKey)object2).getFormat(), ((CacheKey)object2).getLoader(), resourceBundle, ((CacheKey)object2).loadTime);
            }
            catch (Exception exception) {
                ((CacheKey)object).setCause(exception);
            }
            if (!resourceBundle.expired) break block15;
            resourceBundle.cacheKey = null;
            cacheList.remove(object, bundleReference);
            return resourceBundle;
        }
        try {
            ResourceBundle.setExpirationTime((CacheKey)object2, (Control)var1_5);
            // MONITOREXIT : resourceBundle
        }
        catch (Throwable throwable) {
            throw var0_3;
        }
        return resourceBundle;
        catch (Throwable throwable) {
            // empty catch block
        }
        throw var0_3;
    }

    @CallerSensitive
    public static final ResourceBundle getBundle(String string) {
        return ResourceBundle.getBundleImpl(string, Locale.getDefault(), ResourceBundle.getLoader(Reflection.getCallerClass()), ResourceBundle.getDefaultControl(string));
    }

    @CallerSensitive
    public static final ResourceBundle getBundle(String string, Locale locale) {
        return ResourceBundle.getBundleImpl(string, locale, ResourceBundle.getLoader(Reflection.getCallerClass()), ResourceBundle.getDefaultControl(string));
    }

    public static ResourceBundle getBundle(String string, Locale locale, ClassLoader classLoader) {
        if (classLoader != null) {
            return ResourceBundle.getBundleImpl(string, locale, classLoader, ResourceBundle.getDefaultControl(string));
        }
        throw new NullPointerException();
    }

    public static ResourceBundle getBundle(String string, Locale locale, ClassLoader classLoader, Control control) {
        if (classLoader != null && control != null) {
            return ResourceBundle.getBundleImpl(string, locale, classLoader, control);
        }
        throw new NullPointerException();
    }

    @CallerSensitive
    public static final ResourceBundle getBundle(String string, Locale locale, Control control) {
        return ResourceBundle.getBundleImpl(string, locale, ResourceBundle.getLoader(Reflection.getCallerClass()), control);
    }

    @CallerSensitive
    public static final ResourceBundle getBundle(String string, Control control) {
        return ResourceBundle.getBundleImpl(string, Locale.getDefault(), ResourceBundle.getLoader(Reflection.getCallerClass()), control);
    }

    private static ResourceBundle getBundleImpl(String string, Locale locale, ClassLoader object, Control object2) {
        if (locale != null && object2 != null) {
            CacheKey cacheKey = new CacheKey(string, locale, (ClassLoader)object);
            object = null;
            Object object3 = (BundleReference)cacheList.get(cacheKey);
            if (object3 != null) {
                object = (ResourceBundle)((SoftReference)object3).get();
            }
            if (ResourceBundle.isValidBundle((ResourceBundle)object) && ResourceBundle.hasValidParentChain((ResourceBundle)object)) {
                return object;
            }
            boolean bl = object2 == Control.INSTANCE || object2 instanceof SingleFormatControl;
            List<String> list = ((Control)object2).getFormats(string);
            if (!bl && !ResourceBundle.checkList(list)) {
                throw new IllegalArgumentException("Invalid Control: getFormats");
            }
            object3 = object;
            object = null;
            Locale locale2 = locale;
            while (locale2 != null) {
                Object object4 = ((Control)object2).getCandidateLocales(string, locale2);
                if (!bl && !ResourceBundle.checkList(object4)) {
                    throw new IllegalArgumentException("Invalid Control: getCandidateLocales");
                }
                object3 = ResourceBundle.findBundle(cacheKey, object4, list, 0, (Control)object2, (ResourceBundle)object);
                if (ResourceBundle.isValidBundle((ResourceBundle)object3)) {
                    boolean bl2 = Locale.ROOT.equals(((ResourceBundle)object3).locale);
                    if (!bl2 || ((ResourceBundle)object3).locale.equals(locale) || object4.size() == 1 && ((ResourceBundle)object3).locale.equals(object4.get(0))) break;
                    object4 = object;
                    if (bl2) {
                        object4 = object;
                        if (object == null) {
                            object4 = object3;
                        }
                    }
                } else {
                    object4 = object;
                }
                locale2 = ((Control)object2).getFallbackLocale(string, locale2);
                object = object4;
            }
            object2 = object3;
            if (object3 == null) {
                if (object == null) {
                    ResourceBundle.throwMissingResourceException(string, locale, cacheKey.getCause());
                }
                object2 = object;
            }
            return object2;
        }
        throw new NullPointerException();
    }

    private static Control getDefaultControl(String string) {
        return Control.INSTANCE;
    }

    private static ClassLoader getLoader(Class<?> object) {
        object = object == null ? null : ((Class)object).getClassLoader();
        Object object2 = object;
        if (object == null) {
            object2 = RBClassLoader.INSTANCE;
        }
        return object2;
    }

    private static boolean hasValidParentChain(ResourceBundle resourceBundle) {
        long l = System.currentTimeMillis();
        while (resourceBundle != null) {
            long l2;
            if (resourceBundle.expired) {
                return false;
            }
            CacheKey cacheKey = resourceBundle.cacheKey;
            if (cacheKey != null && (l2 = cacheKey.expirationTime) >= 0L && l2 <= l) {
                return false;
            }
            resourceBundle = resourceBundle.parent;
        }
        return true;
    }

    private static boolean isValidBundle(ResourceBundle resourceBundle) {
        boolean bl = resourceBundle != null && resourceBundle != NONEXISTENT_BUNDLE;
        return bl;
    }

    private static ResourceBundle loadBundle(CacheKey cacheKey, List<String> list, Control control, boolean bl) {
        ResourceBundle resourceBundle;
        Locale locale = cacheKey.getLocale();
        int n = list.size();
        ResourceBundle resourceBundle2 = null;
        int n2 = 0;
        do {
            resourceBundle = resourceBundle2;
            if (n2 >= n) break;
            String string = list.get(n2);
            try {
                resourceBundle2 = resourceBundle = control.newBundle(cacheKey.getName(), locale, string, cacheKey.getLoader(), bl);
            }
            catch (Exception exception) {
                cacheKey.setCause(exception);
            }
            catch (LinkageError linkageError) {
                cacheKey.setCause(linkageError);
            }
            if (resourceBundle2 != null) {
                cacheKey.setFormat(string);
                resourceBundle2.name = cacheKey.getName();
                resourceBundle2.locale = locale;
                resourceBundle2.expired = false;
                resourceBundle = resourceBundle2;
                break;
            }
            ++n2;
        } while (true);
        return resourceBundle;
    }

    private static ResourceBundle putBundleInCache(CacheKey object, ResourceBundle resourceBundle, Control object2) {
        ResourceBundle.setExpirationTime((CacheKey)object, (Control)object2);
        object2 = resourceBundle;
        if (((CacheKey)object).expirationTime != -1L) {
            CacheKey cacheKey = (CacheKey)((CacheKey)object).clone();
            object = new BundleReference(resourceBundle, referenceQueue, cacheKey);
            resourceBundle.cacheKey = cacheKey;
            BundleReference bundleReference = cacheList.putIfAbsent(cacheKey, (BundleReference)object);
            object2 = resourceBundle;
            if (bundleReference != null) {
                object2 = (ResourceBundle)bundleReference.get();
                if (object2 != null && !((ResourceBundle)object2).expired) {
                    resourceBundle.cacheKey = null;
                    ((Reference)object).clear();
                } else {
                    cacheList.put(cacheKey, (BundleReference)object);
                    object2 = resourceBundle;
                }
            }
        }
        return object2;
    }

    private static void setExpirationTime(CacheKey object, Control control) {
        long l;
        block4 : {
            block3 : {
                block2 : {
                    l = control.getTimeToLive(((CacheKey)object).getName(), ((CacheKey)object).getLocale());
                    if (l < 0L) break block2;
                    long l2 = System.currentTimeMillis();
                    ((CacheKey)object).loadTime = l2;
                    ((CacheKey)object).expirationTime = l2 + l;
                    break block3;
                }
                if (l < -2L) break block4;
                ((CacheKey)object).expirationTime = l;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid Control: TTL=");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static void throwMissingResourceException(String string, Locale locale, Throwable object) {
        Throwable throwable = object;
        if (object instanceof MissingResourceException) {
            throwable = null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't find bundle for base name ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(", locale ");
        ((StringBuilder)object).append(locale);
        object = ((StringBuilder)object).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("_");
        stringBuilder.append(locale);
        throw new MissingResourceException((String)object, stringBuilder.toString(), "", throwable);
    }

    public boolean containsKey(String string) {
        if (string != null) {
            ResourceBundle resourceBundle = this;
            while (resourceBundle != null) {
                if (resourceBundle.handleKeySet().contains(string)) {
                    return true;
                }
                resourceBundle = resourceBundle.parent;
            }
            return false;
        }
        throw new NullPointerException();
    }

    public String getBaseBundleName() {
        return this.name;
    }

    public abstract Enumeration<String> getKeys();

    public Locale getLocale() {
        return this.locale;
    }

    public final Object getObject(String string) {
        Object object;
        Object object2 = object = this.handleGetObject(string);
        if (object == null) {
            object2 = this.parent;
            if (object2 != null) {
                object = ((ResourceBundle)object2).getObject(string);
            }
            if (object != null) {
                object2 = object;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can't find resource for bundle ");
                ((StringBuilder)object).append(this.getClass().getName());
                ((StringBuilder)object).append(", key ");
                ((StringBuilder)object).append(string);
                throw new MissingResourceException(((StringBuilder)object).toString(), this.getClass().getName(), string);
            }
        }
        return object2;
    }

    public final String getString(String string) {
        return (String)this.getObject(string);
    }

    public final String[] getStringArray(String string) {
        return (String[])this.getObject(string);
    }

    protected abstract Object handleGetObject(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Set<String> handleKeySet() {
        if (this.keySet != null) return this.keySet;
        synchronized (this) {
            if (this.keySet != null) return this.keySet;
            HashSet<String> hashSet = new HashSet<String>();
            Enumeration<String> enumeration = this.getKeys();
            do {
                if (!enumeration.hasMoreElements()) {
                    this.keySet = hashSet;
                    return this.keySet;
                }
                String string = enumeration.nextElement();
                if (this.handleGetObject(string) == null) continue;
                hashSet.add(string);
            } while (true);
        }
    }

    public Set<String> keySet() {
        HashSet<String> hashSet = new HashSet<String>();
        ResourceBundle resourceBundle = this;
        while (resourceBundle != null) {
            hashSet.addAll(resourceBundle.handleKeySet());
            resourceBundle = resourceBundle.parent;
        }
        return hashSet;
    }

    protected void setParent(ResourceBundle resourceBundle) {
        this.parent = resourceBundle;
    }

    private static class BundleReference
    extends SoftReference<ResourceBundle>
    implements CacheKeyReference {
        private CacheKey cacheKey;

        BundleReference(ResourceBundle resourceBundle, ReferenceQueue<Object> referenceQueue, CacheKey cacheKey) {
            super(resourceBundle, referenceQueue);
            this.cacheKey = cacheKey;
        }

        @Override
        public CacheKey getCacheKey() {
            return this.cacheKey;
        }
    }

    private static class CacheKey
    implements Cloneable {
        private Throwable cause;
        private volatile long expirationTime;
        private String format;
        private int hashCodeCache;
        private volatile long loadTime;
        private LoaderReference loaderRef;
        private Locale locale;
        private String name;

        CacheKey(String string, Locale locale, ClassLoader classLoader) {
            this.name = string;
            this.locale = locale;
            this.loaderRef = classLoader == null ? null : new LoaderReference(classLoader, referenceQueue, this);
            this.calculateHashCode();
        }

        private void calculateHashCode() {
            this.hashCodeCache = this.name.hashCode() << 3;
            this.hashCodeCache ^= this.locale.hashCode();
            ClassLoader classLoader = this.getLoader();
            if (classLoader != null) {
                this.hashCodeCache ^= classLoader.hashCode();
            }
        }

        private Throwable getCause() {
            return this.cause;
        }

        private void setCause(Throwable throwable) {
            Throwable throwable2 = this.cause;
            if (throwable2 == null) {
                this.cause = throwable;
            } else if (throwable2 instanceof ClassNotFoundException) {
                this.cause = throwable;
            }
        }

        public Object clone() {
            try {
                CacheKey cacheKey = (CacheKey)super.clone();
                if (this.loaderRef != null) {
                    LoaderReference loaderReference;
                    cacheKey.loaderRef = loaderReference = new LoaderReference((ClassLoader)this.loaderRef.get(), referenceQueue, cacheKey);
                }
                cacheKey.cause = null;
                return cacheKey;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError(cloneNotSupportedException);
            }
        }

        public boolean equals(Object object) {
            boolean bl;
            block14 : {
                block13 : {
                    boolean bl2;
                    Object object2;
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    bl2 = true;
                                    bl = true;
                                    if (this == object) {
                                        return true;
                                    }
                                    object2 = (CacheKey)object;
                                    if (this.hashCodeCache == ((CacheKey)object2).hashCodeCache) break block9;
                                    return false;
                                }
                                if (this.name.equals(((CacheKey)object2).name)) break block10;
                                return false;
                            }
                            if (this.locale.equals(((CacheKey)object2).locale)) break block11;
                            return false;
                        }
                        if (this.loaderRef != null) break block12;
                        if (((CacheKey)object2).loaderRef != null) {
                            bl = false;
                        }
                        return bl;
                    }
                    object = (ClassLoader)this.loaderRef.get();
                    object2 = ((CacheKey)object2).loaderRef;
                    if (object2 == null || object == null) break block13;
                    try {
                        object2 = ((Reference)object2).get();
                        if (object != object2) break block13;
                        bl = bl2;
                        break block14;
                    }
                    catch (ClassCastException | NullPointerException runtimeException) {
                        return false;
                    }
                }
                bl = false;
            }
            return bl;
        }

        String getFormat() {
            return this.format;
        }

        ClassLoader getLoader() {
            Object object = this.loaderRef;
            object = object != null ? (ClassLoader)((Reference)object).get() : null;
            return object;
        }

        Locale getLocale() {
            return this.locale;
        }

        String getName() {
            return this.name;
        }

        public int hashCode() {
            return this.hashCodeCache;
        }

        void setFormat(String string) {
            this.format = string;
        }

        CacheKey setLocale(Locale locale) {
            if (!this.locale.equals(locale)) {
                this.locale = locale;
                this.calculateHashCode();
            }
            return this;
        }

        CacheKey setName(String string) {
            if (!this.name.equals(string)) {
                this.name = string;
                this.calculateHashCode();
            }
            return this;
        }

        public String toString() {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = this.locale.toString();
            if (((String)charSequence).length() == 0) {
                if (this.locale.getVariant().length() != 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("__");
                    ((StringBuilder)charSequence2).append(this.locale.getVariant());
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                } else {
                    charSequence2 = "\"\"";
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("CacheKey[");
            ((StringBuilder)charSequence).append(this.name);
            ((StringBuilder)charSequence).append(", lc=");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(", ldr=");
            ((StringBuilder)charSequence).append(this.getLoader());
            ((StringBuilder)charSequence).append("(format=");
            ((StringBuilder)charSequence).append(this.format);
            ((StringBuilder)charSequence).append(")]");
            return ((StringBuilder)charSequence).toString();
        }
    }

    private static interface CacheKeyReference {
        public CacheKey getCacheKey();
    }

    public static class Control {
        private static final CandidateListCache CANDIDATES_CACHE;
        public static final List<String> FORMAT_CLASS;
        public static final List<String> FORMAT_DEFAULT;
        public static final List<String> FORMAT_PROPERTIES;
        private static final Control INSTANCE;
        public static final long TTL_DONT_CACHE = -1L;
        public static final long TTL_NO_EXPIRATION_CONTROL = -2L;

        static {
            FORMAT_DEFAULT = Collections.unmodifiableList(Arrays.asList("java.class", "java.properties"));
            FORMAT_CLASS = Collections.unmodifiableList(Arrays.asList("java.class"));
            FORMAT_PROPERTIES = Collections.unmodifiableList(Arrays.asList("java.properties"));
            INSTANCE = new Control();
            CANDIDATES_CACHE = new CandidateListCache();
        }

        protected Control() {
        }

        public static final Control getControl(List<String> list) {
            if (list.equals(FORMAT_PROPERTIES)) {
                return SingleFormatControl.PROPERTIES_ONLY;
            }
            if (list.equals(FORMAT_CLASS)) {
                return SingleFormatControl.CLASS_ONLY;
            }
            if (list.equals(FORMAT_DEFAULT)) {
                return INSTANCE;
            }
            throw new IllegalArgumentException();
        }

        public static final Control getNoFallbackControl(List<String> list) {
            if (list.equals(FORMAT_DEFAULT)) {
                return NoFallbackControl.NO_FALLBACK;
            }
            if (list.equals(FORMAT_PROPERTIES)) {
                return NoFallbackControl.PROPERTIES_ONLY_NO_FALLBACK;
            }
            if (list.equals(FORMAT_CLASS)) {
                return NoFallbackControl.CLASS_ONLY_NO_FALLBACK;
            }
            throw new IllegalArgumentException();
        }

        private String toResourceName0(String string, String string2) {
            if (string.contains("://")) {
                return null;
            }
            return this.toResourceName(string, string2);
        }

        public List<Locale> getCandidateLocales(String string, Locale locale) {
            if (string != null) {
                return new ArrayList<Locale>((Collection)CANDIDATES_CACHE.get(locale.getBaseLocale()));
            }
            throw new NullPointerException();
        }

        public Locale getFallbackLocale(String object, Locale locale) {
            if (object != null) {
                object = Locale.getDefault();
                if (locale.equals(object)) {
                    object = null;
                }
                return object;
            }
            throw new NullPointerException();
        }

        public List<String> getFormats(String string) {
            if (string != null) {
                return FORMAT_DEFAULT;
            }
            throw new NullPointerException();
        }

        public long getTimeToLive(String string, Locale locale) {
            if (string != null && locale != null) {
                return -2L;
            }
            throw new NullPointerException();
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean needsReload(String object, Locale locale, String string, ClassLoader classLoader, ResourceBundle resourceBundle, long l) {
            boolean bl2;
            boolean bl;
            void var1_8;
            block15 : {
                if (resourceBundle == null) throw new NullPointerException();
                if (string.equals("java.class") || string.equals("java.properties")) {
                    string = string.substring(5);
                }
                bl2 = false;
                bl = false;
                object = this.toBundleName((String)object, locale);
                object = this.toResourceName0((String)object, string);
                if (object != null) break block15;
                return false;
            }
            try {
                object = classLoader.getResource((String)object);
                if (object == null) return bl;
                long l3 = 0L;
                object = ((URL)object).openConnection();
                bl = false;
                long l2 = l3;
                if (object != null) {
                    ((URLConnection)object).setUseCaches(false);
                    if (object instanceof JarURLConnection) {
                        object = ((JarURLConnection)object).getJarEntry();
                        l2 = l3;
                        if (object != null) {
                            l2 = l3 = ((ZipEntry)object).getTime();
                            if (l3 == -1L) {
                                l2 = 0L;
                            }
                        }
                    } else {
                        l2 = ((URLConnection)object).getLastModified();
                    }
                }
                if (l2 < l) return bl;
                return true;
            }
            catch (Exception exception) {
                return bl2;
            }
            catch (NullPointerException nullPointerException) {
                throw var1_8;
            }
            catch (Exception exception) {
                return bl2;
            }
            catch (NullPointerException nullPointerException) {
                throw var1_8;
            }
            catch (Exception exception) {
                // empty catch block
            }
            return bl2;
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
            throw var1_8;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public ResourceBundle newBundle(String object, Locale object2, String privilegedExceptionAction, final ClassLoader object3, final boolean bl) throws IllegalAccessException, InstantiationException, IOException {
            String string = this.toBundleName((String)object, (Locale)object2);
            object2 = null;
            object = null;
            if (((String)((Object)privilegedExceptionAction)).equals("java.class")) {
                block11 : {
                    try {
                        object3 = ((ClassLoader)((Object)object3)).loadClass(string);
                        if (!ResourceBundle.class.isAssignableFrom(object3)) break block11;
                        object2 = (ResourceBundle)object3.newInstance();
                        return object2;
                    }
                    catch (ClassNotFoundException classNotFoundException) {}
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(object3.getName());
                ((StringBuilder)object2).append(" cannot be cast to ResourceBundle");
                privilegedExceptionAction = new ClassCastException(((StringBuilder)object2).toString());
                throw privilegedExceptionAction;
                return object;
            }
            if (((String)((Object)privilegedExceptionAction)).equals("java.properties")) {
                object = this.toResourceName0(string, "properties");
                if (object == null) {
                    return null;
                }
                try {
                    privilegedExceptionAction = new PrivilegedExceptionAction<InputStream>((String)object){
                        final /* synthetic */ String val$resourceName;
                        {
                            this.val$resourceName = string;
                        }

                        @Override
                        public InputStream run() throws IOException {
                            InputStream inputStream;
                            Object var1_1 = null;
                            if (bl) {
                                Object object = object3.getResource(this.val$resourceName);
                                inputStream = var1_1;
                                if (object != null) {
                                    object = ((URL)object).openConnection();
                                    inputStream = var1_1;
                                    if (object != null) {
                                        ((URLConnection)object).setUseCaches(false);
                                        inputStream = ((URLConnection)object).getInputStream();
                                    }
                                }
                            } else {
                                inputStream = object3.getResourceAsStream(this.val$resourceName);
                            }
                            return inputStream;
                        }
                    };
                    privilegedExceptionAction = (InputStream)AccessController.doPrivileged(privilegedExceptionAction);
                    object = object2;
                    if (privilegedExceptionAction == null) return object;
                }
                catch (PrivilegedActionException privilegedActionException) {
                    throw (IOException)privilegedActionException.getException();
                }
                try {
                    object = new InputStreamReader((InputStream)((Object)privilegedExceptionAction), StandardCharsets.UTF_8);
                    object = new PropertyResourceBundle((Reader)object);
                    return object;
                }
                finally {
                    ((InputStream)((Object)privilegedExceptionAction)).close();
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unknown format: ");
            ((StringBuilder)object).append((String)((Object)privilegedExceptionAction));
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public String toBundleName(String charSequence, Locale object) {
            if (object == Locale.ROOT) {
                return charSequence;
            }
            String string = ((Locale)object).getLanguage();
            String string2 = ((Locale)object).getScript();
            String string3 = ((Locale)object).getCountry();
            object = ((Locale)object).getVariant();
            if (string == "" && string3 == "" && object == "") {
                return charSequence;
            }
            charSequence = new StringBuilder((String)charSequence);
            ((StringBuilder)charSequence).append('_');
            if (string2 != "") {
                if (object != "") {
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append(string3);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append((String)object);
                } else if (string3 != "") {
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append(string3);
                } else {
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append('_');
                    ((StringBuilder)charSequence).append(string2);
                }
            } else if (object != "") {
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append('_');
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append('_');
                ((StringBuilder)charSequence).append((String)object);
            } else if (string3 != "") {
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append('_');
                ((StringBuilder)charSequence).append(string3);
            } else {
                ((StringBuilder)charSequence).append(string);
            }
            return ((StringBuilder)charSequence).toString();
        }

        public final String toResourceName(String string, String string2) {
            StringBuilder stringBuilder = new StringBuilder(string.length() + 1 + string2.length());
            stringBuilder.append(string.replace('.', '/'));
            stringBuilder.append('.');
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        private static class CandidateListCache
        extends LocaleObjectCache<BaseLocale, List<Locale>> {
            private CandidateListCache() {
            }

            private static List<Locale> getDefaultList(String string, String object, String string2, String object2) {
                Object object3;
                LinkedList linkedList = null;
                if (((String)object2).length() > 0) {
                    object3 = new LinkedList();
                    int n = ((String)object2).length();
                    do {
                        linkedList = object3;
                        if (n == -1) break;
                        object3.add(((String)object2).substring(0, n));
                        n = ((String)object2).lastIndexOf(95, n - 1);
                    } while (true);
                }
                object2 = new LinkedList();
                if (linkedList != null) {
                    object3 = linkedList.iterator();
                    while (object3.hasNext()) {
                        object2.add(Locale.getInstance(string, (String)object, string2, (String)object3.next(), null));
                    }
                }
                if (string2.length() > 0) {
                    object2.add(Locale.getInstance(string, (String)object, string2, "", null));
                }
                if (((String)object).length() > 0) {
                    object2.add(Locale.getInstance(string, (String)object, "", "", null));
                    if (linkedList != null) {
                        object = linkedList.iterator();
                        while (object.hasNext()) {
                            object2.add(Locale.getInstance(string, "", string2, (String)object.next(), null));
                        }
                    }
                    if (string2.length() > 0) {
                        object2.add(Locale.getInstance(string, "", string2, "", null));
                    }
                }
                if (string.length() > 0) {
                    object2.add(Locale.getInstance(string, "", "", "", null));
                }
                object2.add(Locale.ROOT);
                return object2;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            protected List<Locale> createObject(BaseLocale var1_1) {
                block13 : {
                    block14 : {
                        block21 : {
                            block15 : {
                                block20 : {
                                    block16 : {
                                        block17 : {
                                            block18 : {
                                                block19 : {
                                                    var2_2 = var1_1.getLanguage();
                                                    var3_3 = var1_1.getScript();
                                                    var4_4 = var1_1.getRegion();
                                                    var1_1 = var1_1.getVariant();
                                                    var5_5 = 0;
                                                    var6_6 = 0;
                                                    var7_7 = var1_1;
                                                    var8_8 = var5_5;
                                                    var9_9 = var6_6;
                                                    if (var2_2.equals("no")) {
                                                        if (var4_4.equals("NO") && var1_1.equals("NY")) {
                                                            var7_7 = "";
                                                            var9_9 = 1;
                                                            var8_8 = var5_5;
                                                        } else {
                                                            var8_8 = 1;
                                                            var9_9 = var6_6;
                                                            var7_7 = var1_1;
                                                        }
                                                    }
                                                    if (var2_2.equals("nb") || var8_8 != 0) break block13;
                                                    if (var2_2.equals("nn") || var9_9 != 0) break block14;
                                                    var10_10 = var3_3;
                                                    var1_1 = var4_4;
                                                    if (var2_2.equals("zh") == false) return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                                                    var5_5 = var3_3.length();
                                                    var8_8 = 0;
                                                    var9_9 = 0;
                                                    if (var5_5 != 0 || var4_4.length() <= 0) break block15;
                                                    var8_8 = var4_4.hashCode();
                                                    if (var8_8 == 2155) break block16;
                                                    if (var8_8 == 2307) break block17;
                                                    if (var8_8 == 2466) break block18;
                                                    if (var8_8 == 2644) break block19;
                                                    if (var8_8 != 2691 || !var4_4.equals("TW")) ** GOTO lbl-1000
                                                    break block20;
                                                }
                                                if (!var4_4.equals("SG")) ** GOTO lbl-1000
                                                var9_9 = 4;
                                                break block20;
                                            }
                                            if (!var4_4.equals("MO")) ** GOTO lbl-1000
                                            var9_9 = 2;
                                            break block20;
                                        }
                                        if (!var4_4.equals("HK")) ** GOTO lbl-1000
                                        var9_9 = 1;
                                        break block20;
                                    }
                                    if (var4_4.equals("CN")) {
                                        var9_9 = 3;
                                    } else lbl-1000: // 5 sources:
                                    {
                                        var9_9 = -1;
                                    }
                                }
                                var1_1 = var9_9 != 0 && var9_9 != 1 && var9_9 != 2 ? (var9_9 != 3 && var9_9 != 4 ? var3_3 : "Hans") : "Hant";
                                var10_10 = var1_1;
                                var1_1 = var4_4;
                                return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                            }
                            var10_10 = var3_3;
                            var1_1 = var4_4;
                            if (var3_3.length() <= 0) return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                            var10_10 = var3_3;
                            var1_1 = var4_4;
                            if (var4_4.length() != 0) return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                            switch (var3_3.hashCode()) {
                                case 2241695: {
                                    if (!var3_3.equals("Hant")) break;
                                    var9_9 = 1;
                                    break block21;
                                }
                                case 2241694: {
                                    if (!var3_3.equals("Hans")) break;
                                    var9_9 = var8_8;
                                    break block21;
                                }
                            }
                            ** break;
lbl74: // 1 sources:
                            var9_9 = -1;
                        }
                        if (var9_9 == 0) {
                            var1_1 = "CN";
                            var10_10 = var3_3;
                            return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                        }
                        if (var9_9 != 1) {
                            var10_10 = var3_3;
                            var1_1 = var4_4;
                            return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                        }
                        var1_1 = "TW";
                        var10_10 = var3_3;
                        return CandidateListCache.getDefaultList(var2_2, (String)var10_10, var1_1, var7_7);
                    }
                    var1_1 = CandidateListCache.getDefaultList("nn", (String)var3_3, var4_4, var7_7);
                    var8_8 = var1_1.size() - 1;
                    var9_9 = var8_8 + 1;
                    var1_1.add(var8_8, (Locale)Locale.getInstance("no", "NO", "NY"));
                    var8_8 = var9_9 + 1;
                    var1_1.add(var9_9, Locale.getInstance("no", "NO", ""));
                    var1_1.add(var8_8, Locale.getInstance("no", "", ""));
                    return var1_1;
                }
                var7_7 = CandidateListCache.getDefaultList("nb", (String)var3_3, var4_4, (String)var7_7);
                var1_1 = new LinkedList<Locale>();
                var3_3 = var7_7.iterator();
                while (var3_3.hasNext() != false) {
                    var7_7 = (Locale)var3_3.next();
                    var1_1.add((Locale)var7_7);
                    if (var7_7.getLanguage().length() == 0) {
                        return var1_1;
                    }
                    var1_1.add(Locale.getInstance("no", var7_7.getScript(), var7_7.getCountry(), var7_7.getVariant(), null));
                }
                return var1_1;
            }
        }

    }

    private static class LoaderReference
    extends WeakReference<ClassLoader>
    implements CacheKeyReference {
        private CacheKey cacheKey;

        LoaderReference(ClassLoader classLoader, ReferenceQueue<Object> referenceQueue, CacheKey cacheKey) {
            super(classLoader, referenceQueue);
            this.cacheKey = cacheKey;
        }

        @Override
        public CacheKey getCacheKey() {
            return this.cacheKey;
        }
    }

    private static final class NoFallbackControl
    extends SingleFormatControl {
        private static final Control CLASS_ONLY_NO_FALLBACK;
        private static final Control NO_FALLBACK;
        private static final Control PROPERTIES_ONLY_NO_FALLBACK;

        static {
            NO_FALLBACK = new NoFallbackControl(FORMAT_DEFAULT);
            PROPERTIES_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_PROPERTIES);
            CLASS_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_CLASS);
        }

        protected NoFallbackControl(List<String> list) {
            super(list);
        }

        @Override
        public Locale getFallbackLocale(String string, Locale locale) {
            if (string != null && locale != null) {
                return null;
            }
            throw new NullPointerException();
        }
    }

    private static class RBClassLoader
    extends ClassLoader {
        private static final RBClassLoader INSTANCE = AccessController.doPrivileged(new PrivilegedAction<RBClassLoader>(){

            @Override
            public RBClassLoader run() {
                return new RBClassLoader();
            }
        });
        private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

        private RBClassLoader() {
        }

        @Override
        public URL getResource(String string) {
            ClassLoader classLoader = loader;
            if (classLoader != null) {
                return classLoader.getResource(string);
            }
            return ClassLoader.getSystemResource(string);
        }

        @Override
        public InputStream getResourceAsStream(String string) {
            ClassLoader classLoader = loader;
            if (classLoader != null) {
                return classLoader.getResourceAsStream(string);
            }
            return ClassLoader.getSystemResourceAsStream(string);
        }

        @Override
        public Class<?> loadClass(String string) throws ClassNotFoundException {
            ClassLoader classLoader = loader;
            if (classLoader != null) {
                return classLoader.loadClass(string);
            }
            return Class.forName(string);
        }

    }

    private static class SingleFormatControl
    extends Control {
        private static final Control CLASS_ONLY;
        private static final Control PROPERTIES_ONLY;
        private final List<String> formats;

        static {
            PROPERTIES_ONLY = new SingleFormatControl(FORMAT_PROPERTIES);
            CLASS_ONLY = new SingleFormatControl(FORMAT_CLASS);
        }

        protected SingleFormatControl(List<String> list) {
            this.formats = list;
        }

        @Override
        public List<String> getFormats(String string) {
            if (string != null) {
                return this.formats;
            }
            throw new NullPointerException();
        }
    }

}

