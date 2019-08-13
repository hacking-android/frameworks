/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecurityPermission;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import sun.security.jca.GetInstance;
import sun.security.jca.ProviderList;
import sun.security.jca.Providers;

public final class Security {
    private static final Properties props;
    private static final Map<String, Class<?>> spiMap;
    private static final AtomicInteger version;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        block14 : {
            block13 : {
                Security.version = new AtomicInteger();
                Security.props = new Properties();
                var0 = false;
                var1_1 = false;
                var2_2 = false;
                var3_3 = false;
                var4_4 = null;
                var5_8 = null;
                var6_9 = null;
                var7_10 = var4_4;
                var8_12 = var5_8;
                var9_13 = Security.class.getResourceAsStream("security.properties");
                if (var9_13 == null) {
                    var7_10 = var4_4;
                    var8_12 = var5_8;
                    System.logE("Could not find 'security.properties'.");
                    var0 = var3_3;
                    var4_4 = var6_9;
                } else {
                    var7_10 = var4_4;
                    var8_12 = var5_8;
                    var7_10 = var4_4;
                    var8_12 = var5_8;
                    var6_9 = new BufferedInputStream(var9_13);
                    var7_10 = var4_4 = var6_9;
                    var8_12 = var4_4;
                    Security.props.load(var4_4);
                    var0 = true;
                }
                var3_3 = var0;
                if (var4_4 == null) break block13;
                var3_3 = var0;
                var4_4.close();
lbl35: // 3 sources:
                do {
                    var3_3 = var0;
                    break block13;
                    break;
                } while (true);
                {
                    catch (IOException var4_5) {
                        var0 = var3_3;
                    }
                    ** GOTO lbl35
                    catch (Throwable var4_6) {
                        break block14;
                    }
                    catch (IOException var4_7) {}
                    var7_10 = var8_12;
                    {
                        System.logE("Could not load 'security.properties'", var4_7);
                        var3_3 = var2_2;
                        if (var8_12 == null) break block13;
                        var3_3 = var1_1;
                    }
                    {
                        var8_12.close();
                        ** continue;
                    }
                }
            }
            if (!var3_3) {
                Security.initializeStatic();
            }
            Security.spiMap = new ConcurrentHashMap<String, Class<?>>();
            return;
        }
        if (var7_10 == null) throw var4_6;
        try {
            var7_10.close();
            throw var4_6;
        }
        catch (IOException var7_11) {
            throw var4_6;
        }
    }

    private Security() {
    }

    public static int addProvider(Provider provider) {
        return Security.insertProviderAt(provider, 0);
    }

    @Deprecated
    public static String getAlgorithmProperty(String object, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Alg.");
        stringBuilder.append(string);
        stringBuilder.append(".");
        stringBuilder.append((String)object);
        object = Security.getProviderProperty(stringBuilder.toString());
        if (object != null) {
            return ((ProviderProperty)object).className;
        }
        return null;
    }

    public static Set<String> getAlgorithms(String string) {
        if (string != null && string.length() != 0 && !string.endsWith(".")) {
            HashSet<String> hashSet = new HashSet<String>();
            Provider[] arrprovider = Security.getProviders();
            for (int i = 0; i < arrprovider.length; ++i) {
                Enumeration<Object> enumeration = arrprovider[i].keys();
                while (enumeration.hasMoreElements()) {
                    String string2 = ((String)enumeration.nextElement()).toUpperCase(Locale.ENGLISH);
                    if (!string2.startsWith(string.toUpperCase(Locale.ENGLISH)) || string2.indexOf(" ") >= 0) continue;
                    hashSet.add(string2.substring(string.length() + 1));
                }
            }
            return Collections.unmodifiableSet(hashSet);
        }
        return Collections.emptySet();
    }

    private static LinkedHashSet<Provider> getAllQualifyingCandidates(String arrstring, String string, Provider[] arrprovider) {
        arrstring = Security.getFilterComponents((String)arrstring, string);
        return Security.getProvidersNotUsingCache(arrstring[0], arrstring[1], arrstring[2], string, arrprovider);
    }

    static String[] getFilterComponents(String string, String string2) {
        block2 : {
            block6 : {
                block7 : {
                    block8 : {
                        String string3;
                        block5 : {
                            int n;
                            block3 : {
                                block4 : {
                                    n = string.indexOf(46);
                                    if (n < 0) break block2;
                                    string3 = string.substring(0, n);
                                    Object var4_4 = null;
                                    if (string2.length() != 0) break block3;
                                    if ((string = string.substring(n + 1).trim()).length() == 0) break block4;
                                    string2 = var4_4;
                                    break block5;
                                }
                                throw new InvalidParameterException("Invalid filter");
                            }
                            int n2 = string.indexOf(32);
                            if (n2 == -1) break block6;
                            string2 = string.substring(n2 + 1).trim();
                            if (string2.length() == 0) break block7;
                            if (n2 < n || n == n2 - 1) break block8;
                            string = string.substring(n + 1, n2);
                        }
                        return new String[]{string3, string, string2};
                    }
                    throw new InvalidParameterException("Invalid filter");
                }
                throw new InvalidParameterException("Invalid filter");
            }
            throw new InvalidParameterException("Invalid filter");
        }
        throw new InvalidParameterException("Invalid filter");
    }

    static Object[] getImpl(String string, String string2, String string3) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (string3 == null) {
            return GetInstance.getInstance(string2, Security.getSpiClass(string2), string).toArray();
        }
        return GetInstance.getInstance(string2, Security.getSpiClass(string2), string, string3).toArray();
    }

    static Object[] getImpl(String string, String string2, String string3, Object object) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (string3 == null) {
            return GetInstance.getInstance(string2, Security.getSpiClass(string2), string, object).toArray();
        }
        return GetInstance.getInstance(string2, Security.getSpiClass(string2), string, object, string3).toArray();
    }

    static Object[] getImpl(String string, String string2, Provider provider) throws NoSuchAlgorithmException {
        return GetInstance.getInstance(string2, Security.getSpiClass(string2), string, provider).toArray();
    }

    static Object[] getImpl(String string, String string2, Provider provider, Object object) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return GetInstance.getInstance(string2, Security.getSpiClass(string2), string, object, provider).toArray();
    }

    public static String getProperty(String charSequence) {
        CharSequence charSequence2;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("getProperty.");
            ((StringBuilder)charSequence2).append((String)charSequence);
            securityManager.checkPermission(new SecurityPermission(((StringBuilder)charSequence2).toString()));
        }
        charSequence2 = props.getProperty((String)charSequence);
        charSequence = charSequence2;
        if (charSequence2 != null) {
            charSequence = ((String)charSequence2).trim();
        }
        return charSequence;
    }

    public static Provider getProvider(String string) {
        return Providers.getProviderList().getProvider(string);
    }

    private static String getProviderProperty(String string, Provider provider) {
        String string2;
        block1 : {
            String string3;
            string2 = string3 = provider.getProperty(string);
            if (string3 != null) break block1;
            Enumeration<Object> enumeration = provider.keys();
            do {
                string2 = string3;
                if (!enumeration.hasMoreElements()) break block1;
                string2 = string3;
                if (string3 != null) break block1;
            } while (!string.equalsIgnoreCase(string2 = (String)enumeration.nextElement()));
            string2 = provider.getProperty(string2);
        }
        return string2;
    }

    private static ProviderProperty getProviderProperty(String object) {
        List<Provider> list = Providers.getProviderList().providers();
        for (int i = 0; i < list.size(); ++i) {
            Provider provider;
            String string;
            block3 : {
                String string2;
                provider = list.get(i);
                string = string2 = provider.getProperty((String)object);
                if (string2 == null) {
                    Enumeration<Object> enumeration = provider.keys();
                    do {
                        string = string2;
                        if (!enumeration.hasMoreElements()) break block3;
                        string = string2;
                        if (string2 != null) break block3;
                    } while (!((String)object).equalsIgnoreCase(string = (String)enumeration.nextElement()));
                    string = provider.getProperty(string);
                }
            }
            if (string == null) continue;
            object = new ProviderProperty();
            ((ProviderProperty)object).className = string;
            ((ProviderProperty)object).provider = provider;
            return object;
        }
        return null;
    }

    public static Provider[] getProviders() {
        return Providers.getFullProviderList().toArray();
    }

    public static Provider[] getProviders(String string) {
        String string2;
        Object object;
        int n = string.indexOf(58);
        if (n == -1) {
            object = "";
            string2 = string;
            string = object;
        } else {
            string2 = string.substring(0, n);
            string = string.substring(n + 1);
        }
        object = new Hashtable(1);
        ((Hashtable)object).put(string2, string);
        return Security.getProviders((Map<String, String>)object);
    }

    public static Provider[] getProviders(Map<String, String> arrobject) {
        Provider[] arrprovider = Security.getProviders();
        LinkedHashSet<Provider> linkedHashSet = arrobject.keySet();
        Provider[] arrprovider2 = new LinkedHashSet(5);
        if (linkedHashSet != null && arrprovider != null) {
            int n;
            block6 : {
                int n2 = 1;
                Iterator<String> iterator = linkedHashSet.iterator();
                do {
                    linkedHashSet = arrprovider2;
                    if (!iterator.hasNext()) break block6;
                    linkedHashSet = iterator.next();
                    linkedHashSet = Security.getAllQualifyingCandidates((String)((Object)linkedHashSet), arrobject.get(linkedHashSet), arrprovider);
                    n = n2;
                    if (n2 != 0) {
                        arrprovider2 = linkedHashSet;
                        n = 0;
                    }
                    if (linkedHashSet == null || linkedHashSet.isEmpty()) break;
                    Iterator iterator2 = arrprovider2.iterator();
                    while (iterator2.hasNext()) {
                        if (linkedHashSet.contains((Provider)iterator2.next())) continue;
                        iterator2.remove();
                    }
                    n2 = n;
                } while (true);
                linkedHashSet = null;
            }
            if (linkedHashSet != null && !linkedHashSet.isEmpty()) {
                arrobject = linkedHashSet.toArray();
                arrprovider2 = new Provider[arrobject.length];
                for (n = 0; n < arrprovider2.length; ++n) {
                    arrprovider2[n] = (Provider)arrobject[n];
                }
                return arrprovider2;
            }
            return null;
        }
        return arrprovider;
    }

    private static LinkedHashSet<Provider> getProvidersNotUsingCache(String string, String string2, String string3, String string4, Provider[] arrprovider) {
        LinkedHashSet<Provider> linkedHashSet = new LinkedHashSet<Provider>(5);
        for (int i = 0; i < arrprovider.length; ++i) {
            if (!Security.isCriterionSatisfied(arrprovider[i], string, string2, string3, string4)) continue;
            linkedHashSet.add(arrprovider[i]);
        }
        return linkedHashSet;
    }

    private static Class<?> getSpiClass(String string) {
        Serializable serializable = spiMap.get(string);
        if (serializable != null) {
            return serializable;
        }
        try {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("java.security.");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append("Spi");
            serializable = Class.forName(((StringBuilder)serializable).toString());
            spiMap.put(string, (Class<?>)serializable);
            return serializable;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new AssertionError("Spi class not found", classNotFoundException);
        }
    }

    public static int getVersion() {
        return version.get();
    }

    public static void increaseVersion() {
        version.incrementAndGet();
    }

    private static void initializeStatic() {
        props.put("security.provider.1", "com.android.org.conscrypt.OpenSSLProvider");
        props.put("security.provider.2", "sun.security.provider.CertPathProvider");
        props.put("security.provider.3", "com.android.org.bouncycastle.jce.provider.BouncyCastleProvider");
        props.put("security.provider.4", "com.android.org.conscrypt.JSSEProvider");
    }

    public static int insertProviderAt(Provider object, int n) {
        synchronized (Security.class) {
            String string;
            block5 : {
                string = ((Provider)object).getName();
                ProviderList providerList = Providers.getFullProviderList();
                object = ProviderList.insertAt(providerList, (Provider)object, n - 1);
                if (providerList != object) break block5;
                return -1;
            }
            Security.increaseVersion();
            Providers.setProviderList((ProviderList)object);
            n = ((ProviderList)object).getIndex(string);
            return n + 1;
        }
    }

    private static void invalidateSMCache(String string) {
        final boolean bl = string.equals("package.access");
        boolean bl2 = string.equals("package.definition");
        if (bl || bl2) {
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    try {
                        boolean bl2;
                        AnnotatedElement annotatedElement = Class.forName("java.lang.SecurityManager", false, null);
                        if (bl) {
                            annotatedElement = ((Class)annotatedElement).getDeclaredField("packageAccessValid");
                            bl2 = ((AccessibleObject)annotatedElement).isAccessible();
                            ((AccessibleObject)annotatedElement).setAccessible(true);
                        } else {
                            annotatedElement = ((Class)annotatedElement).getDeclaredField("packageDefinitionValid");
                            bl2 = ((AccessibleObject)annotatedElement).isAccessible();
                            ((AccessibleObject)annotatedElement).setAccessible(true);
                        }
                        ((Field)annotatedElement).setBoolean(annotatedElement, false);
                        ((AccessibleObject)annotatedElement).setAccessible(bl2);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    return null;
                }
            });
        }
    }

    private static boolean isConstraintSatisfied(String string, String string2, String string3) {
        if (string.equalsIgnoreCase("KeySize")) {
            return Integer.parseInt(string2) <= Integer.parseInt(string3);
        }
        if (string.equalsIgnoreCase("ImplementedIn")) {
            return string2.equalsIgnoreCase(string3);
        }
        return false;
    }

    private static boolean isCriterionSatisfied(Provider provider, String charSequence, String charSequence2, String string, String string2) {
        CharSequence charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append((String)charSequence);
        ((StringBuilder)charSequence3).append('.');
        ((StringBuilder)charSequence3).append((String)charSequence2);
        CharSequence charSequence4 = ((StringBuilder)charSequence3).toString();
        charSequence3 = charSequence4;
        if (string != null) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append((String)charSequence4);
            ((StringBuilder)charSequence3).append(' ');
            ((StringBuilder)charSequence3).append(string);
            charSequence3 = ((StringBuilder)charSequence3).toString();
        }
        charSequence3 = Security.getProviderProperty((String)charSequence3, provider);
        charSequence4 = charSequence3;
        if (charSequence3 == null) {
            charSequence4 = new StringBuilder();
            ((StringBuilder)charSequence4).append("Alg.Alias.");
            ((StringBuilder)charSequence4).append((String)charSequence);
            ((StringBuilder)charSequence4).append(".");
            ((StringBuilder)charSequence4).append((String)charSequence2);
            charSequence4 = Security.getProviderProperty(((StringBuilder)charSequence4).toString(), provider);
            charSequence2 = charSequence3;
            if (charSequence4 != null) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(".");
                ((StringBuilder)charSequence2).append((String)charSequence4);
                charSequence = charSequence2 = ((StringBuilder)charSequence2).toString();
                if (string != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(' ');
                    ((StringBuilder)charSequence).append(string);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                charSequence2 = Security.getProviderProperty((String)charSequence, provider);
            }
            charSequence4 = charSequence2;
            if (charSequence2 == null) {
                return false;
            }
        }
        if (string == null) {
            return true;
        }
        if (Security.isStandardAttr(string)) {
            return Security.isConstraintSatisfied(string, string2, (String)charSequence4);
        }
        return string2.equalsIgnoreCase((String)charSequence4);
    }

    private static boolean isStandardAttr(String string) {
        if (string.equalsIgnoreCase("KeySize")) {
            return true;
        }
        return string.equalsIgnoreCase("ImplementedIn");
    }

    public static void removeProvider(String string) {
        synchronized (Security.class) {
            Providers.setProviderList(ProviderList.remove(Providers.getFullProviderList(), string));
            Security.increaseVersion();
            return;
        }
    }

    public static void setProperty(String string, String string2) {
        props.put(string, string2);
        Security.increaseVersion();
        Security.invalidateSMCache(string);
    }

    private static class ProviderProperty {
        String className;
        Provider provider;

        private ProviderProperty() {
        }
    }

}

