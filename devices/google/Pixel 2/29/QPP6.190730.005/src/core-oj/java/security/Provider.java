/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import sun.security.util.Debug;

public abstract class Provider
extends Properties {
    private static final int ALIAS_LENGTH;
    private static final String ALIAS_PREFIX = "Alg.Alias.";
    private static final String ALIAS_PREFIX_LOWER = "alg.alias.";
    private static final Debug debug;
    private static final Map<String, EngineDescription> knownEngines;
    private static volatile ServiceKey previousKey;
    static final long serialVersionUID = -4298000515446427739L;
    private transient Set<Map.Entry<Object, Object>> entrySet = null;
    private transient int entrySetCallCount = 0;
    private String info;
    private transient boolean initialized;
    private transient boolean legacyChanged;
    private transient Map<ServiceKey, Service> legacyMap;
    private transient Map<String, String> legacyStrings;
    private String name;
    private volatile boolean registered = false;
    private transient Map<ServiceKey, Service> serviceMap;
    private transient Set<Service> serviceSet;
    private transient boolean servicesChanged;
    private double version;

    static {
        debug = Debug.getInstance("provider", "Provider");
        ALIAS_LENGTH = ALIAS_PREFIX.length();
        previousKey = new ServiceKey("", "", false);
        knownEngines = new HashMap<String, EngineDescription>();
        Provider.addEngine("AlgorithmParameterGenerator", false, null);
        Provider.addEngine("AlgorithmParameters", false, null);
        Provider.addEngine("KeyFactory", false, null);
        Provider.addEngine("KeyPairGenerator", false, null);
        Provider.addEngine("KeyStore", false, null);
        Provider.addEngine("MessageDigest", false, null);
        Provider.addEngine("SecureRandom", false, null);
        Provider.addEngine("Signature", true, null);
        Provider.addEngine("CertificateFactory", false, null);
        Provider.addEngine("CertPathBuilder", false, null);
        Provider.addEngine("CertPathValidator", false, null);
        Provider.addEngine("CertStore", false, "java.security.cert.CertStoreParameters");
        Provider.addEngine("Cipher", true, null);
        Provider.addEngine("ExemptionMechanism", false, null);
        Provider.addEngine("Mac", true, null);
        Provider.addEngine("KeyAgreement", true, null);
        Provider.addEngine("KeyGenerator", false, null);
        Provider.addEngine("SecretKeyFactory", false, null);
        Provider.addEngine("KeyManagerFactory", false, null);
        Provider.addEngine("SSLContext", false, null);
        Provider.addEngine("TrustManagerFactory", false, null);
        Provider.addEngine("GssApiMechanism", false, null);
        Provider.addEngine("SaslClientFactory", false, null);
        Provider.addEngine("SaslServerFactory", false, null);
        Provider.addEngine("Policy", false, "java.security.Policy$Parameters");
        Provider.addEngine("Configuration", false, "javax.security.auth.login.Configuration$Parameters");
        Provider.addEngine("XMLSignatureFactory", false, null);
        Provider.addEngine("KeyInfoFactory", false, null);
        Provider.addEngine("TransformService", false, null);
        Provider.addEngine("TerminalFactory", false, "java.lang.Object");
    }

    protected Provider(String string, double d, String string2) {
        this.name = string;
        this.version = d;
        this.info = string2;
        this.putId();
        this.initialized = true;
    }

    private static void addEngine(String string, boolean bl, String object) {
        object = new EngineDescription(string, bl, (String)object);
        knownEngines.put(string.toLowerCase(Locale.ENGLISH), (EngineDescription)object);
        knownEngines.put(string, (EngineDescription)object);
    }

    private void check(String string) {
        this.checkInitialized();
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(string);
        }
    }

    private void checkInitialized() {
        if (this.initialized) {
            return;
        }
        throw new IllegalStateException();
    }

    private boolean checkLegacy(Object object) {
        if (this.registered) {
            Security.increaseVersion();
        }
        if (((String)object).startsWith("Provider.")) {
            return false;
        }
        this.legacyChanged = true;
        if (this.legacyStrings == null) {
            this.legacyStrings = new LinkedHashMap<String, String>();
        }
        return true;
    }

    private void ensureLegacyParsed() {
        if (this.legacyChanged && this.legacyStrings != null) {
            this.serviceSet = null;
            Map<ServiceKey, Service> map = this.legacyMap;
            if (map == null) {
                this.legacyMap = new LinkedHashMap<ServiceKey, Service>();
            } else {
                map.clear();
            }
            for (Map.Entry entry : this.legacyStrings.entrySet()) {
                this.parseLegacyPut((String)entry.getKey(), (String)entry.getValue());
            }
            this.removeInvalidServices(this.legacyMap);
            this.legacyChanged = false;
            return;
        }
    }

    private static String getEngineName(String string) {
        EngineDescription engineDescription;
        EngineDescription engineDescription2 = engineDescription = knownEngines.get(string);
        if (engineDescription == null) {
            engineDescription2 = knownEngines.get(string.toLowerCase(Locale.ENGLISH));
        }
        if (engineDescription2 != null) {
            string = engineDescription2.name;
        }
        return string;
    }

    private String[] getTypeAndAlgorithm(String string) {
        int n = string.indexOf(".");
        if (n < 1) {
            Debug debug = Provider.debug;
            if (debug != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring invalid entry in provider ");
                stringBuilder.append(this.name);
                stringBuilder.append(":");
                stringBuilder.append(string);
                debug.println(stringBuilder.toString());
            }
            return null;
        }
        return new String[]{string.substring(0, n), string.substring(n + 1)};
    }

    private void implClear() {
        Map<Object, Object> map = this.legacyStrings;
        if (map != null) {
            map.clear();
        }
        if ((map = this.legacyMap) != null) {
            map.clear();
        }
        if ((map = this.serviceMap) != null) {
            map.clear();
        }
        this.legacyChanged = false;
        this.servicesChanged = false;
        this.serviceSet = null;
        super.clear();
        this.putId();
        if (this.registered) {
            Security.increaseVersion();
        }
    }

    private Object implCompute(Object object, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        if (object instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.compute((String)object, biFunction);
        }
        return super.compute(object, biFunction);
    }

    private Object implComputeIfAbsent(Object object, Function<? super Object, ? extends Object> function) {
        if (object instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.computeIfAbsent((String)object, function);
        }
        return super.computeIfAbsent(object, function);
    }

    private Object implComputeIfPresent(Object object, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        if (object instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.computeIfPresent((String)object, biFunction);
        }
        return super.computeIfPresent(object, biFunction);
    }

    private Object implMerge(Object object, Object object2, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        if (object instanceof String && object2 instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.merge((String)object, (String)object2, biFunction);
        }
        return super.merge(object, object2, biFunction);
    }

    private Object implPut(Object object, Object object2) {
        if (object instanceof String && object2 instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.put((String)object, (String)object2);
        }
        return super.put(object, object2);
    }

    private void implPutAll(Map<?, ?> object2) {
        for (Map.Entry entry : object2.entrySet()) {
            this.implPut(entry.getKey(), entry.getValue());
        }
        if (this.registered) {
            Security.increaseVersion();
        }
    }

    private Object implPutIfAbsent(Object object, Object object2) {
        if (object instanceof String && object2 instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.putIfAbsent((String)object, (String)object2);
        }
        return super.putIfAbsent(object, object2);
    }

    private Object implRemove(Object object) {
        if (object instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.remove((String)object);
        }
        return super.remove(object);
    }

    private boolean implRemove(Object object, Object object2) {
        if (object instanceof String && object2 instanceof String) {
            if (!this.checkLegacy(object)) {
                return false;
            }
            this.legacyStrings.remove((String)object, object2);
        }
        return super.remove(object, object2);
    }

    private void implRemoveService(Service service) {
        if (service != null && this.serviceMap != null) {
            String string = service.getType();
            Object object2 = new ServiceKey(string, service.getAlgorithm(), false);
            if (service != this.serviceMap.get(object2)) {
                return;
            }
            this.servicesChanged = true;
            this.serviceMap.remove(object2);
            for (Object object2 : service.getAliases()) {
                this.serviceMap.remove(new ServiceKey(string, (String)object2, false));
            }
            this.removePropertyStrings(service);
            return;
        }
    }

    private Object implReplace(Object object, Object object2) {
        if (object instanceof String && object2 instanceof String) {
            if (!this.checkLegacy(object)) {
                return null;
            }
            this.legacyStrings.replace((String)object, (String)object2);
        }
        return super.replace(object, object2);
    }

    private boolean implReplace(Object object, Object object2, Object object3) {
        if (object instanceof String && object2 instanceof String && object3 instanceof String) {
            if (!this.checkLegacy(object)) {
                return false;
            }
            this.legacyStrings.replace((String)object, (String)object2, (String)object3);
        }
        return super.replace(object, object2, object3);
    }

    private void implReplaceAll(BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        this.legacyChanged = true;
        Map<String, String> map = this.legacyStrings;
        if (map == null) {
            this.legacyStrings = new LinkedHashMap<String, String>();
        } else {
            map.replaceAll(biFunction);
        }
        super.replaceAll(biFunction);
    }

    private void parseLegacyPut(String object, String string) {
        if (((String)object).toLowerCase(Locale.ENGLISH).startsWith(ALIAS_PREFIX_LOWER)) {
            if ((object = this.getTypeAndAlgorithm(((String)object).substring(ALIAS_LENGTH))) == null) {
                return;
            }
            String string2 = Provider.getEngineName(object[0]);
            String string3 = object[1].intern();
            ServiceKey serviceKey = new ServiceKey(string2, string, true);
            Service service = this.legacyMap.get(serviceKey);
            object = service;
            if (service == null) {
                object = new Service(this);
                ((Service)object).type = string2;
                ((Service)object).algorithm = string;
                this.legacyMap.put(serviceKey, (Service)object);
            }
            this.legacyMap.put(new ServiceKey(string2, string3, true), (Service)object);
            ((Service)object).addAlias(string3);
        } else {
            if ((object = this.getTypeAndAlgorithm((String)object)) == null) {
                return;
            }
            int n = object[1].indexOf(32);
            if (n == -1) {
                String string4 = Provider.getEngineName(object[0]);
                String string5 = object[1].intern();
                ServiceKey serviceKey = new ServiceKey(string4, string5, true);
                Service service = this.legacyMap.get(serviceKey);
                object = service;
                if (service == null) {
                    object = new Service(this);
                    ((Service)object).type = string4;
                    ((Service)object).algorithm = string5;
                    this.legacyMap.put(serviceKey, (Service)object);
                }
                ((Service)object).className = string;
            } else {
                String string6 = Provider.getEngineName(object[0]);
                object = object[1];
                String string7 = ((String)object).substring(0, n).intern();
                object = ((String)object).substring(n + 1);
                while (((String)object).startsWith(" ")) {
                    object = ((String)object).substring(1);
                }
                String string8 = ((String)object).intern();
                ServiceKey serviceKey = new ServiceKey(string6, string7, true);
                Service service = this.legacyMap.get(serviceKey);
                object = service;
                if (service == null) {
                    object = new Service(this);
                    ((Service)object).type = string6;
                    ((Service)object).algorithm = string7;
                    this.legacyMap.put(serviceKey, (Service)object);
                }
                ((Service)object).addAttribute(string8, string);
            }
        }
    }

    private void putId() {
        super.put("Provider.id name", String.valueOf(this.name));
        super.put("Provider.id version", String.valueOf(this.version));
        super.put("Provider.id info", String.valueOf(this.info));
        super.put("Provider.id className", this.getClass().getName());
    }

    private void putPropertyStrings(Service object) {
        String string = ((Service)object).getType();
        String string2 = ((Service)object).getAlgorithm();
        StringBuilder object22 = new StringBuilder();
        object22.append(string);
        object22.append(".");
        object22.append(string2);
        super.put(object22.toString(), ((Service)object).getClassName());
        for (String string3 : ((Service)object).getAliases()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ALIAS_PREFIX);
            stringBuilder.append(string);
            stringBuilder.append(".");
            stringBuilder.append(string3);
            super.put(stringBuilder.toString(), string2);
        }
        for (Map.Entry entry : ((Service)object).attributes.entrySet()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(".");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(entry.getKey());
            super.put(((StringBuilder)object).toString(), entry.getValue());
        }
        if (this.registered) {
            Security.increaseVersion();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.registered = false;
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : super.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue());
        }
        this.defaults = null;
        objectInputStream.defaultReadObject();
        this.implClear();
        this.initialized = true;
        this.putAll(hashMap);
    }

    private void removeInvalidServices(Map<ServiceKey, Service> object) {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            if (((Service)((Map.Entry)object.next()).getValue()).isValid()) continue;
            object.remove();
        }
    }

    private void removePropertyStrings(Service object) {
        String string = ((Service)((Object)object)).getType();
        String string2 = ((Service)((Object)object)).getAlgorithm();
        StringBuilder object22 = new StringBuilder();
        object22.append(string);
        object22.append(".");
        object22.append(string2);
        super.remove(object22.toString());
        for (CharSequence charSequence : ((Service)((Object)object)).getAliases()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ALIAS_PREFIX);
            stringBuilder.append(string);
            stringBuilder.append(".");
            stringBuilder.append((String)charSequence);
            super.remove(stringBuilder.toString());
        }
        for (Map.Entry entry : ((Service)((Object)object)).attributes.entrySet()) {
            CharSequence charSequence;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(".");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(entry.getKey());
            super.remove(((StringBuilder)charSequence).toString());
        }
        if (this.registered) {
            Security.increaseVersion();
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("clearProviderProperties.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Remove ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider properties");
                debug.println(stringBuilder.toString());
            }
            this.implClear();
            return;
        }
    }

    @Override
    public Object compute(Object object, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        synchronized (this) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("putProviderProperty.");
            ((StringBuilder)object2).append(this.name);
            this.check(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("removeProviderProperty");
            ((StringBuilder)object2).append(this.name);
            this.check(((StringBuilder)object2).toString());
            if (debug != null) {
                object2 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Compute ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                ((Debug)object2).println(stringBuilder.toString());
            }
            object = this.implCompute(object, biFunction);
            return object;
        }
    }

    @Override
    public Object computeIfAbsent(Object object, Function<? super Object, ? extends Object> function) {
        synchronized (this) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("putProviderProperty.");
            ((StringBuilder)object2).append(this.name);
            this.check(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("removeProviderProperty");
            ((StringBuilder)object2).append(this.name);
            this.check(((StringBuilder)object2).toString());
            if (debug != null) {
                object2 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ComputeIfAbsent ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                ((Debug)object2).println(stringBuilder.toString());
            }
            object = this.implComputeIfAbsent(object, function);
            return object;
        }
    }

    @Override
    public Object computeIfPresent(Object object, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("removeProviderProperty");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("ComputeIfPresent ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                debug.println(stringBuilder.toString());
            }
            object = this.implComputeIfPresent(object, biFunction);
            return object;
        }
    }

    @Override
    public Enumeration<Object> elements() {
        this.checkInitialized();
        return super.elements();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        synchronized (this) {
            block7 : {
                block6 : {
                    this.checkInitialized();
                    if (this.entrySet != null) break block6;
                    int n = this.entrySetCallCount;
                    this.entrySetCallCount = n + 1;
                    if (n != 0) return super.entrySet();
                    this.entrySet = Collections.unmodifiableMap(this).entrySet();
                }
                if (this.entrySetCallCount != 2) break block7;
                return this.entrySet;
            }
            RuntimeException runtimeException = new RuntimeException("Internal error.");
            throw runtimeException;
        }
    }

    @Override
    public void forEach(BiConsumer<? super Object, ? super Object> biConsumer) {
        synchronized (this) {
            this.checkInitialized();
            super.forEach(biConsumer);
            return;
        }
    }

    @Override
    public Object get(Object object) {
        this.checkInitialized();
        return super.get(object);
    }

    public String getInfo() {
        return this.info;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Object getOrDefault(Object object, Object object2) {
        synchronized (this) {
            this.checkInitialized();
            object = super.getOrDefault(object, object2);
            return object;
        }
    }

    @Override
    public String getProperty(String string) {
        this.checkInitialized();
        return super.getProperty(string);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Service getService(String object, String string) {
        synchronized (this) {
            void var2_2;
            this.checkInitialized();
            ServiceKey serviceKey = previousKey;
            boolean bl = serviceKey.matches((String)object, (String)var2_2);
            Object var5_5 = null;
            if (!bl) {
                previousKey = serviceKey = new ServiceKey((String)object, (String)var2_2, false);
            }
            if (this.serviceMap != null && (object = this.serviceMap.get(serviceKey)) != null) {
                return object;
            }
            this.ensureLegacyParsed();
            object = var5_5;
            if (this.legacyMap == null) return object;
            return this.legacyMap.get(serviceKey);
        }
    }

    public Set<Service> getServices() {
        synchronized (this) {
            LinkedHashSet<Service> linkedHashSet;
            this.checkInitialized();
            if (this.legacyChanged || this.servicesChanged) {
                this.serviceSet = null;
            }
            if (this.serviceSet == null) {
                this.ensureLegacyParsed();
                linkedHashSet = new LinkedHashSet<Service>();
                if (this.serviceMap != null) {
                    linkedHashSet.addAll(this.serviceMap.values());
                }
                if (this.legacyMap != null) {
                    linkedHashSet.addAll(this.legacyMap.values());
                }
                this.serviceSet = Collections.unmodifiableSet(linkedHashSet);
                this.servicesChanged = false;
            }
            linkedHashSet = this.serviceSet;
            return linkedHashSet;
        }
    }

    public double getVersion() {
        return this.version;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public Set<Object> keySet() {
        this.checkInitialized();
        return Collections.unmodifiableSet(super.keySet());
    }

    @Override
    public Enumeration<Object> keys() {
        this.checkInitialized();
        return super.keys();
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        synchronized (this) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("putProviderProperty.");
            ((StringBuilder)serializable).append(this.name);
            this.check(((StringBuilder)serializable).toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Load ");
                ((StringBuilder)serializable).append(this.name);
                ((StringBuilder)serializable).append(" provider properties");
                debug.println(((StringBuilder)serializable).toString());
            }
            serializable = new Properties();
            ((Properties)serializable).load(inputStream);
            this.implPutAll((Map<?, ?>)((Object)serializable));
            return;
        }
    }

    @Override
    public Object merge(Object object, Object object2, BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("removeProviderProperty");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Merge ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                debug.println(stringBuilder.toString());
            }
            object = this.implMerge(object, object2, biFunction);
            return object;
        }
    }

    @Override
    public Object put(Object object, Object object2) {
        synchronized (this) {
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("putProviderProperty.");
            ((StringBuilder)object3).append(this.name);
            this.check(((StringBuilder)object3).toString());
            if (debug != null) {
                object3 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Set ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property [");
                stringBuilder.append(object);
                stringBuilder.append("/");
                stringBuilder.append(object2);
                stringBuilder.append("]");
                ((Debug)object3).println(stringBuilder.toString());
            }
            object = this.implPut(object, object2);
            return object;
        }
    }

    @Override
    public void putAll(Map<?, ?> map) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Put all ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider properties");
                debug.println(stringBuilder.toString());
            }
            this.implPutAll(map);
            return;
        }
    }

    @Override
    public Object putIfAbsent(Object object, Object object2) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Set ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property [");
                stringBuilder.append(object);
                stringBuilder.append("/");
                stringBuilder.append(object2);
                stringBuilder.append("]");
                debug.println(stringBuilder.toString());
            }
            object = this.implPutIfAbsent(object, object2);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void putService(Service object) {
        synchronized (this) {
            Object object2;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("putProviderProperty.");
            ((StringBuilder)object3).append(this.name);
            this.check(((StringBuilder)object3).toString());
            if (debug != null) {
                object2 = debug;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(this.name);
                ((StringBuilder)object3).append(".putService(): ");
                ((StringBuilder)object3).append(object);
                ((Debug)object2).println(((StringBuilder)object3).toString());
            }
            if (object == null) {
                object = new NullPointerException();
                throw object;
            }
            if (((Service)object).getProvider() != this) {
                object = new IllegalArgumentException("service.getProvider() must match this Provider object");
                throw object;
            }
            if (this.serviceMap == null) {
                this.serviceMap = object3 = new LinkedHashMap();
            }
            this.servicesChanged = true;
            object3 = ((Service)object).getType();
            object2 = ((Service)object).getAlgorithm();
            Object object4 = new ServiceKey((String)object3, (String)object2, true);
            this.implRemoveService(this.serviceMap.get(object4));
            this.serviceMap.put((ServiceKey)object4, (Service)object);
            Iterator iterator = ((Service)object).getAliases().iterator();
            do {
                if (!iterator.hasNext()) {
                    this.putPropertyStrings((Service)object);
                    return;
                }
                String string = (String)iterator.next();
                object4 = this.serviceMap;
                object2 = new ServiceKey((String)object3, string, true);
                object4.put(object2, object);
            } while (true);
        }
    }

    @Override
    public Object remove(Object object) {
        synchronized (this) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("removeProviderProperty.");
            ((StringBuilder)object2).append(this.name);
            this.check(((StringBuilder)object2).toString());
            if (debug != null) {
                object2 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remove ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                ((Debug)object2).println(stringBuilder.toString());
            }
            object = this.implRemove(object);
            return object;
        }
    }

    @Override
    public boolean remove(Object object, Object object2) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Remove ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                debug.println(stringBuilder.toString());
            }
            boolean bl = this.implRemove(object, object2);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void removeService(Service object) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.name);
                stringBuilder.append(".removeService(): ");
                stringBuilder.append(object);
                debug.println(stringBuilder.toString());
            }
            if (object != null) {
                this.implRemoveService((Service)object);
                return;
            }
            object = new NullPointerException();
            throw object;
        }
    }

    @Override
    public Object replace(Object object, Object object2) {
        synchronized (this) {
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("putProviderProperty.");
            ((StringBuilder)object3).append(this.name);
            this.check(((StringBuilder)object3).toString());
            if (debug != null) {
                object3 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Replace ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                ((Debug)object3).println(stringBuilder.toString());
            }
            object = this.implReplace(object, object2);
            return object;
        }
    }

    @Override
    public boolean replace(Object object, Object object2, Object object3) {
        synchronized (this) {
            Object object4 = new StringBuilder();
            ((StringBuilder)object4).append("putProviderProperty.");
            ((StringBuilder)object4).append(this.name);
            this.check(((StringBuilder)object4).toString());
            if (debug != null) {
                object4 = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Replace ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                stringBuilder.append(object);
                ((Debug)object4).println(stringBuilder.toString());
            }
            boolean bl = this.implReplace(object, object2, object3);
            return bl;
        }
    }

    @Override
    public void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> biFunction) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("putProviderProperty.");
            stringBuilder.append(this.name);
            this.check(stringBuilder.toString());
            if (debug != null) {
                Debug debug = Provider.debug;
                stringBuilder = new StringBuilder();
                stringBuilder.append("ReplaceAll ");
                stringBuilder.append(this.name);
                stringBuilder.append(" provider property ");
                debug.println(stringBuilder.toString());
            }
            this.implReplaceAll(biFunction);
            return;
        }
    }

    public void setRegistered() {
        this.registered = true;
    }

    public void setUnregistered() {
        this.registered = false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(" version ");
        stringBuilder.append(this.version);
        return stringBuilder.toString();
    }

    @Override
    public Collection<Object> values() {
        this.checkInitialized();
        return Collections.unmodifiableCollection(super.values());
    }

    public void warmUpServiceProvision() {
        synchronized (this) {
            this.checkInitialized();
            this.ensureLegacyParsed();
            this.getServices();
            return;
        }
    }

    private static class EngineDescription {
        private volatile Class<?> constructorParameterClass;
        final String constructorParameterClassName;
        final String name;
        final boolean supportsParameter;

        EngineDescription(String string, boolean bl, String string2) {
            this.name = string;
            this.supportsParameter = bl;
            this.constructorParameterClassName = string2;
        }

        Class<?> getConstructorParameterClass() throws ClassNotFoundException {
            Class<?> class_;
            Class<?> class_2 = class_ = this.constructorParameterClass;
            if (class_ == null) {
                this.constructorParameterClass = class_2 = Class.forName(this.constructorParameterClassName);
            }
            return class_2;
        }
    }

    public static class Service {
        private static final Class<?>[] CLASS0 = new Class[0];
        private String algorithm;
        private List<String> aliases;
        private Map<UString, String> attributes;
        private String className;
        private volatile Reference<Class<?>> classRef;
        private volatile Boolean hasKeyAttributes;
        private final Provider provider;
        private boolean registered;
        private Class[] supportedClasses;
        private String[] supportedFormats;
        private String type;

        private Service(Provider provider) {
            this.provider = provider;
            this.aliases = Collections.emptyList();
            this.attributes = Collections.emptyMap();
        }

        /*
         * WARNING - void declaration
         */
        public Service(Provider object, String object22, String string, String string2, List<String> list, Map<String, String> map) {
            void var4_6;
            void var3_5;
            if (object != null && object22 != null && var3_5 != null && var4_6 != null) {
                void var6_8;
                void var5_7;
                this.provider = object;
                this.type = Provider.getEngineName((String)object22);
                this.algorithm = var3_5;
                this.className = var4_6;
                this.aliases = var5_7 == null ? Collections.emptyList() : new ArrayList<String>((Collection<String>)var5_7);
                if (var6_8 == null) {
                    this.attributes = Collections.emptyMap();
                } else {
                    this.attributes = new HashMap<UString, String>();
                    for (Map.Entry entry : var6_8.entrySet()) {
                        this.attributes.put(new UString((String)entry.getKey()), (String)entry.getValue());
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        private void addAlias(String string) {
            if (this.aliases.isEmpty()) {
                this.aliases = new ArrayList<String>(2);
            }
            this.aliases.add(string);
        }

        private final List<String> getAliases() {
            return this.aliases;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Class<?> getImplClass() throws NoSuchAlgorithmException {
            try {
                Object object = this.classRef;
                object = object == null ? null : ((Reference)object).get();
                Serializable serializable = object;
                if (object != null) return serializable;
                object = this.provider.getClass().getClassLoader();
                object = object == null ? Class.forName(this.className) : ((ClassLoader)object).loadClass(this.className);
                if (Modifier.isPublic(((Class)object).getModifiers())) {
                    serializable = new WeakReference(object);
                    this.classRef = serializable;
                    return object;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("class configured for ");
                ((StringBuilder)serializable).append(this.type);
                ((StringBuilder)serializable).append(" (provider: ");
                ((StringBuilder)serializable).append(this.provider.getName());
                ((StringBuilder)serializable).append(") is not public.");
                object = new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
                throw object;
            }
            catch (ClassNotFoundException classNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("class configured for ");
                stringBuilder.append(this.type);
                stringBuilder.append(" (provider: ");
                stringBuilder.append(this.provider.getName());
                stringBuilder.append(") cannot be found.");
                throw new NoSuchAlgorithmException(stringBuilder.toString(), classNotFoundException);
            }
        }

        private Class<?> getKeyClass(String object) {
            try {
                Class<?> class_ = Class.forName((String)object);
                return class_;
            }
            catch (ClassNotFoundException classNotFoundException) {
                block5 : {
                    ClassLoader classLoader = this.provider.getClass().getClassLoader();
                    if (classLoader == null) break block5;
                    try {
                        object = classLoader.loadClass((String)object);
                        return object;
                    }
                    catch (ClassNotFoundException classNotFoundException2) {
                        // empty catch block
                    }
                }
                return null;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean hasKeyAttributes() {
            Serializable serializable = this.hasKeyAttributes;
            Object object = serializable;
            if (serializable != null) return object.booleanValue();
            synchronized (this) {
                object = this.getAttribute("SupportedKeyFormats");
                if (object != null) {
                    this.supportedFormats = object.split("\\|");
                }
                object = this.getAttribute("SupportedKeyClasses");
                boolean bl = false;
                if (object != null) {
                    object = object.split("\\|");
                    ArrayList<Boolean> arrayList = new ArrayList<Boolean>(((String[])object).length);
                    int n = ((String[])object).length;
                    for (int i = 0; i < n; ++i) {
                        serializable = this.getKeyClass(object[i]);
                        if (serializable == null) continue;
                        arrayList.add((Boolean)serializable);
                    }
                    this.supportedClasses = arrayList.toArray(CLASS0);
                }
                if (this.supportedFormats != null || this.supportedClasses != null) {
                    bl = true;
                }
                this.hasKeyAttributes = object = Boolean.valueOf(bl);
                return object.booleanValue();
            }
        }

        private boolean isValid() {
            boolean bl = this.type != null && this.algorithm != null && this.className != null;
            return bl;
        }

        private Object newInstanceGeneric(Object object) throws Exception {
            Constructor<?>[] arrconstructor = this.getImplClass();
            if (object == null) {
                try {
                    object = arrconstructor.getConstructor(new Class[0]).newInstance(new Object[0]);
                    return object;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No public no-arg constructor found in class ");
                    stringBuilder.append(this.className);
                    throw new NoSuchAlgorithmException(stringBuilder.toString());
                }
            }
            Class<?> class_ = object.getClass();
            for (Constructor<?> constructor : arrconstructor.getConstructors()) {
                Class<?>[] arrclass = constructor.getParameterTypes();
                if (arrclass.length != 1 || !arrclass[0].isAssignableFrom(class_)) {
                    continue;
                }
                return constructor.newInstance(object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No public constructor matching ");
            ((StringBuilder)object).append(class_.getName());
            ((StringBuilder)object).append(" found in class ");
            ((StringBuilder)object).append(this.className);
            throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
        }

        private boolean supportsKeyClass(Key serializable) {
            if (this.supportedClasses == null) {
                return false;
            }
            serializable = serializable.getClass();
            Class[] arrclass = this.supportedClasses;
            int n = arrclass.length;
            for (int i = 0; i < n; ++i) {
                if (!arrclass[i].isAssignableFrom((Class<?>)serializable)) continue;
                return true;
            }
            return false;
        }

        private boolean supportsKeyFormat(Key arrstring) {
            if (this.supportedFormats == null) {
                return false;
            }
            String string = arrstring.getFormat();
            if (string == null) {
                return false;
            }
            arrstring = this.supportedFormats;
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!arrstring[i].equals(string)) continue;
                return true;
            }
            return false;
        }

        void addAttribute(String string, String string2) {
            if (this.attributes.isEmpty()) {
                this.attributes = new HashMap<UString, String>(8);
            }
            this.attributes.put(new UString(string), string2);
        }

        public final String getAlgorithm() {
            return this.algorithm;
        }

        public final String getAttribute(String string) {
            if (string != null) {
                return this.attributes.get(new UString(string));
            }
            throw new NullPointerException();
        }

        public final String getClassName() {
            return this.className;
        }

        public final Provider getProvider() {
            return this.provider;
        }

        public final String getType() {
            return this.type;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Object newInstance(Object object) throws NoSuchAlgorithmException {
            if (!this.registered) {
                if (this.provider.getService(this.type, this.algorithm) != this) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Service not registered with Provider ");
                    ((StringBuilder)object).append(this.provider.getName());
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append(this);
                    throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
                }
                this.registered = true;
            }
            try {
                Object object2 = (EngineDescription)knownEngines.get(this.type);
                if (object2 == null) {
                    return this.newInstanceGeneric(object);
                }
                if (((EngineDescription)object2).constructorParameterClassName == null) {
                    if (object == null) {
                        return this.getImplClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("constructorParameter not used with ");
                    ((StringBuilder)object).append(this.type);
                    ((StringBuilder)object).append(" engines");
                    object2 = new InvalidParameterException(((StringBuilder)object).toString());
                    throw object2;
                }
                Class<?> class_ = ((EngineDescription)object2).getConstructorParameterClass();
                if (object != null && !class_.isAssignableFrom(object.getClass())) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("constructorParameter must be instanceof ");
                    ((StringBuilder)object).append(((EngineDescription)object2).constructorParameterClassName.replace('$', '.'));
                    ((StringBuilder)object).append(" for engine type ");
                    ((StringBuilder)object).append(this.type);
                    InvalidParameterException invalidParameterException = new InvalidParameterException(((StringBuilder)object).toString());
                    throw invalidParameterException;
                }
                return this.getImplClass().getConstructor(class_).newInstance(object);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error constructing implementation (algorithm: ");
                ((StringBuilder)object).append(this.algorithm);
                ((StringBuilder)object).append(", provider: ");
                ((StringBuilder)object).append(this.provider.getName());
                ((StringBuilder)object).append(", class: ");
                ((StringBuilder)object).append(this.className);
                ((StringBuilder)object).append(")");
                throw new NoSuchAlgorithmException(((StringBuilder)object).toString(), exception);
            }
            catch (InvocationTargetException invocationTargetException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error constructing implementation (algorithm: ");
                ((StringBuilder)object).append(this.algorithm);
                ((StringBuilder)object).append(", provider: ");
                ((StringBuilder)object).append(this.provider.getName());
                ((StringBuilder)object).append(", class: ");
                ((StringBuilder)object).append(this.className);
                ((StringBuilder)object).append(")");
                throw new NoSuchAlgorithmException(((StringBuilder)object).toString(), invocationTargetException.getCause());
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw noSuchAlgorithmException;
            }
        }

        public boolean supportsParameter(Object object) {
            EngineDescription engineDescription = (EngineDescription)knownEngines.get(this.type);
            if (engineDescription == null) {
                return true;
            }
            if (engineDescription.supportsParameter) {
                if (object != null && !(object instanceof Key)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Parameter must be instanceof Key for engine ");
                    ((StringBuilder)object).append(this.type);
                    throw new InvalidParameterException(((StringBuilder)object).toString());
                }
                if (!this.hasKeyAttributes()) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.supportsKeyFormat((Key)(object = (Key)object))) {
                    return true;
                }
                return this.supportsKeyClass((Key)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("supportsParameter() not used with ");
            ((StringBuilder)object).append(this.type);
            ((StringBuilder)object).append(" engines");
            throw new InvalidParameterException(((StringBuilder)object).toString());
        }

        public String toString() {
            CharSequence charSequence;
            boolean bl = this.aliases.isEmpty();
            CharSequence charSequence2 = "";
            if (bl) {
                charSequence = "";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("\r\n  aliases: ");
                ((StringBuilder)charSequence).append(this.aliases.toString());
                charSequence = ((StringBuilder)charSequence).toString();
            }
            if (!this.attributes.isEmpty()) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("\r\n  attributes: ");
                ((StringBuilder)charSequence2).append(this.attributes.toString());
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.provider.getName());
            stringBuilder.append(": ");
            stringBuilder.append(this.type);
            stringBuilder.append(".");
            stringBuilder.append(this.algorithm);
            stringBuilder.append(" -> ");
            stringBuilder.append(this.className);
            stringBuilder.append((String)charSequence);
            stringBuilder.append((String)charSequence2);
            stringBuilder.append("\r\n");
            return stringBuilder.toString();
        }
    }

    private static class ServiceKey {
        private final String algorithm;
        private final String originalAlgorithm;
        private final String type;

        private ServiceKey(String string, String string2, boolean bl) {
            this.type = string;
            this.originalAlgorithm = string2;
            string = string2.toUpperCase(Locale.ENGLISH);
            if (bl) {
                string = string.intern();
            }
            this.algorithm = string;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof ServiceKey)) {
                return false;
            }
            object = (ServiceKey)object;
            if (!this.type.equals(((ServiceKey)object).type) || !this.algorithm.equals(((ServiceKey)object).algorithm)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return this.type.hashCode() + this.algorithm.hashCode();
        }

        boolean matches(String string, String string2) {
            boolean bl = this.type == string && this.originalAlgorithm == string2;
            return bl;
        }
    }

    private static class UString {
        final String lowerString;
        final String string;

        UString(String string) {
            this.string = string;
            this.lowerString = string.toLowerCase(Locale.ENGLISH);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof UString)) {
                return false;
            }
            object = (UString)object;
            return this.lowerString.equals(((UString)object).lowerString);
        }

        public int hashCode() {
            return this.lowerString.hashCode();
        }

        public String toString() {
            return this.string;
        }
    }

}

