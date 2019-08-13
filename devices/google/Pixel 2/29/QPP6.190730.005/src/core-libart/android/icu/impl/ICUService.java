/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUDebug;
import android.icu.impl.ICUNotifier;
import android.icu.impl.ICURWLock;
import android.icu.util.ULocale;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ICUService
extends ICUNotifier {
    private static final boolean DEBUG = ICUDebug.enabled("service");
    private Map<String, CacheEntry> cache;
    private int defaultSize = 0;
    private LocaleRef dnref;
    private final List<Factory> factories = new ArrayList<Factory>();
    private final ICURWLock factoryLock = new ICURWLock();
    private Map<String, Factory> idcache;
    protected final String name;

    public ICUService() {
        this.name = "";
    }

    public ICUService(String string) {
        this.name = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Map<String, Factory> getVisibleIDMap() {
        synchronized (this) {
            HashMap<String, Factory> hashMap = this.idcache;
            if (hashMap == null) {
                try {
                    this.factoryLock.acquireRead();
                    hashMap = new HashMap<String, Factory>();
                    ListIterator<Factory> listIterator = this.factories.listIterator(this.factories.size());
                    while (listIterator.hasPrevious()) {
                        listIterator.previous().updateVisibleIDs(hashMap);
                    }
                    this.idcache = Collections.unmodifiableMap(hashMap);
                }
                finally {
                    this.factoryLock.releaseRead();
                }
            }
            return this.idcache;
        }
    }

    @Override
    protected boolean acceptsListener(EventListener eventListener) {
        return eventListener instanceof ServiceListener;
    }

    protected void clearCaches() {
        this.cache = null;
        this.idcache = null;
        this.dnref = null;
    }

    protected void clearServiceCache() {
        this.cache = null;
    }

    public Key createKey(String object) {
        object = object == null ? null : new Key((String)object);
        return object;
    }

    public final List<Factory> factories() {
        try {
            this.factoryLock.acquireRead();
            ArrayList<Factory> arrayList = new ArrayList<Factory>(this.factories);
            return arrayList;
        }
        finally {
            this.factoryLock.releaseRead();
        }
    }

    public Object get(String string) {
        return this.getKey(this.createKey(string), null);
    }

    public Object get(String string, String[] arrstring) {
        if (string != null) {
            return this.getKey(this.createKey(string), arrstring);
        }
        throw new NullPointerException("descriptor must not be null");
    }

    public String getDisplayName(String string) {
        return this.getDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(String string, ULocale uLocale) {
        Map<String, Factory> map = this.getVisibleIDMap();
        Factory factory = map.get(string);
        if (factory != null) {
            return factory.getDisplayName(string, uLocale);
        }
        Key key = this.createKey(string);
        while (key.fallback()) {
            factory = map.get(key.currentID());
            if (factory == null) continue;
            return factory.getDisplayName(string, uLocale);
        }
        return null;
    }

    public SortedMap<String, String> getDisplayNames() {
        return this.getDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY), null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale) {
        return this.getDisplayNames(uLocale, null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, String string) {
        return this.getDisplayNames(uLocale, null, string);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, Comparator<Object> comparator) {
        return this.getDisplayNames(uLocale, comparator, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SortedMap<String, String> getDisplayNames(ULocale object, Comparator<Object> object2, String object3) {
        Object object4 = null;
        Object object52 = this.dnref;
        LocaleRef localeRef = object52;
        if (object52 != null) {
            object4 = ((LocaleRef)object52).get((ULocale)object, (Comparator<Object>)object2);
            localeRef = object52;
        }
        while (object4 == null) {
            synchronized (this) {
                if (localeRef != this.dnref && this.dnref != null) {
                    localeRef = this.dnref;
                    object4 = localeRef.get((ULocale)object, (Comparator<Object>)object2);
                } else {
                    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>((Comparator<Object>)object2);
                    for (Object object52 : this.getVisibleIDMap().entrySet()) {
                        object4 = (String)object52.getKey();
                        treeMap.put(((Factory)object52.getValue()).getDisplayName((String)object4, (ULocale)object), object4);
                    }
                    object4 = Collections.unmodifiableSortedMap(treeMap);
                    this.dnref = object52 = new LocaleRef((SortedMap<String, String>)object4, (ULocale)object, (Comparator<Object>)object2);
                }
            }
        }
        object = this.createKey((String)object3);
        if (object == null) {
            return object4;
        }
        object3 = new TreeMap<String, String>((SortedMap<String, String>)object4);
        object2 = object3.entrySet().iterator();
        while (object2.hasNext()) {
            if (((Key)object).isFallbackOf((String)((Map.Entry)object2.next()).getValue())) continue;
            object2.remove();
        }
        return object3;
    }

    public Object getKey(Key key) {
        return this.getKey(key, null);
    }

    public Object getKey(Key key, String[] arrstring) {
        return this.getKey(key, arrstring, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Object getKey(Key var1_1, String[] var2_3, Factory var3_4) {
        block36 : {
            block33 : {
                if (this.factories.size() == 0) {
                    return this.handleDefault((Key)var1_1, (String[])var2_3);
                }
                if (ICUService.DEBUG) {
                    var4_5 = System.out;
                    var5_6 = new StringBuilder();
                    var5_6.append("Service: ");
                    var5_6.append(this.name);
                    var5_6.append(" key: ");
                    var5_6.append(var1_1.canonicalID());
                    var4_5.println(var5_6.toString());
                }
                if (var1_1 == null) break block36;
                this.factoryLock.acquireRead();
                var5_6 = this.cache;
                var4_5 = var5_6;
                if (var5_6 == null) {
                    if (ICUService.DEBUG) {
                        var5_6 = System.out;
                        var4_5 = new ConcurrentHashMap<String, CacheEntry>();
                        var4_5.append("Service ");
                        var4_5.append(this.name);
                        var4_5.append(" cache was empty");
                        var5_6.println(var4_5.toString());
                    }
                    var4_5 = new ConcurrentHashMap<String, CacheEntry>();
                }
                var6_7 = null;
                var7_8 = 0;
                var8_9 = 0;
                var9_10 = 0;
                var10_11 = 0;
                var11_12 = this.factories.size();
                var12_13 = 1;
                var5_6 = var6_7;
                var13_14 = var7_8;
                var14_15 = var8_9;
                if (var3_4 == null) break block33;
                var13_14 = 0;
                do lbl-1000: // 2 sources:
                {
                    var9_10 = var10_11;
                    if (var13_14 >= var11_12) break;
                    if (var3_4 != this.factories.get(var13_14)) break block34;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                        this.factoryLock.releaseRead();
                        throw var1_2;
                    }
                }
                {
                    block34 : {
                        var9_10 = var13_14 + 1;
                        break;
                    }
                    ++var13_14;
                    ** while (true)
                }
                if (var9_10 != 0) {
                    var12_13 = 0;
                    var5_6 = var6_7;
                    var13_14 = var7_8;
                    var14_15 = var8_9;
                } else {
                    var2_3 = new StringBuilder();
                    var2_3.append("Factory ");
                    var2_3.append(var3_4);
                    var2_3.append("not registered with service: ");
                    var2_3.append(this);
                    var1_1 = new IllegalStateException(var2_3.toString());
                    throw var1_1;
                }
            }
            do {
                block38 : {
                    block35 : {
                        block37 : {
                            var15_16 = var1_1.currentDescriptor();
                            var8_9 = var14_15;
                            if (ICUService.DEBUG) {
                                var6_7 = System.out;
                                var3_4 = new StringBuilder();
                                var3_4.append(this.name);
                                var3_4.append("[");
                                var3_4.append(var14_15);
                                var3_4.append("] looking for: ");
                                var3_4.append(var15_16);
                                var6_7.println(var3_4.toString());
                                var8_9 = var14_15 + 1;
                            }
                            if ((var16_17 = (CacheEntry)var4_5.get(var15_16)) == null) break block37;
                            var3_4 = var16_17;
                            var6_7 = var5_6;
                            var9_10 = var13_14;
                            if (ICUService.DEBUG) {
                                var3_4 = System.out;
                                var6_7 = new StringBuilder();
                                var6_7.append(this.name);
                                var6_7.append(" found with descriptor: ");
                                var6_7.append(var15_16);
                                var3_4.println(var6_7.toString());
                                var3_4 = var16_17;
                                var6_7 = var5_6;
                                var9_10 = var13_14;
                            }
                            break block35;
                        }
                        if (ICUService.DEBUG) {
                            var6_7 = System.out;
                            var3_4 = new StringBuilder();
                            var3_4.append("did not find: ");
                            var3_4.append(var15_16);
                            var3_4.append(" in cache");
                            var6_7.println(var3_4.toString());
                        }
                        var13_14 = var12_13;
                        var14_15 = var9_10;
                        while (var14_15 < var11_12) {
                            var3_4 = this.factories;
                            var7_8 = var14_15 + 1;
                            var3_4 = (Factory)var3_4.get(var14_15);
                            if (ICUService.DEBUG) {
                                var17_18 = System.out;
                                var6_7 = new StringBuilder();
                                var6_7.append("trying factory[");
                                var6_7.append(var7_8 - 1);
                                var6_7.append("] ");
                                var6_7.append(var3_4.toString());
                                var17_18.println(var6_7.toString());
                            }
                            if ((var3_4 = var3_4.create((Key)var1_1, this)) != null) {
                                var3_4 = var16_17 = new CacheEntry(var15_16, var3_4);
                                var6_7 = var5_6;
                                var9_10 = var13_14;
                                if (ICUService.DEBUG) {
                                    var3_4 = System.out;
                                    var6_7 = new StringBuilder();
                                    var6_7.append(this.name);
                                    var6_7.append(" factory supported: ");
                                    var6_7.append(var15_16);
                                    var6_7.append(", caching");
                                    var3_4.println(var6_7.toString());
                                    var3_4 = var16_17;
                                    var6_7 = var5_6;
                                    var9_10 = var13_14;
                                }
                                break block35;
                            }
                            if (ICUService.DEBUG) {
                                var6_7 = System.out;
                                var3_4 = new StringBuilder();
                                var3_4.append("factory did not support: ");
                                var3_4.append(var15_16);
                                var6_7.println(var3_4.toString());
                            }
                            var14_15 = var7_8;
                        }
                        var3_4 = var5_6;
                        if (var5_6 == null) {
                            var3_4 = new ArrayList(5);
                        }
                        var3_4.add(var15_16);
                        if (var1_1.fallback()) break block38;
                        var9_10 = var13_14;
                        var6_7 = var3_4;
                        var3_4 = var16_17;
                    }
                    if (var3_4 != null) {
                        if (var9_10 != 0) {
                            var18_19 = ICUService.DEBUG;
                            if (var18_19) {
                                var1_1 = System.out;
                                var5_6 = new StringBuilder();
                                var5_6.append("caching '");
                                var5_6.append(var3_4.actualDescriptor);
                                var5_6.append("'");
                                var1_1.println(var5_6.toString());
                            }
                            var4_5.put(var3_4.actualDescriptor, (CacheEntry)var3_4);
                            if (var6_7 != null) {
                                var16_17 = var6_7.iterator();
                                while (var16_17.hasNext()) {
                                    var5_6 = (String)var16_17.next();
                                    if (ICUService.DEBUG) {
                                        var6_7 = System.out;
                                        var1_1 = new StringBuilder();
                                        var1_1.append(this.name);
                                        var1_1.append(" adding descriptor: '");
                                        var1_1.append((String)var5_6);
                                        var1_1.append("' for actual: '");
                                        var1_1.append(var3_4.actualDescriptor);
                                        var1_1.append("'");
                                        var6_7.println(var1_1.toString());
                                    }
                                    var4_5.put((String)var5_6, (CacheEntry)var3_4);
                                }
                            }
                            this.cache = var4_5;
                        }
                        if (var2_3 != null) {
                            var2_3[0] = var3_4.actualDescriptor.indexOf("/") == 0 ? var3_4.actualDescriptor.substring(1) : var3_4.actualDescriptor;
                        }
                        if (ICUService.DEBUG) {
                            var1_1 = System.out;
                            var2_3 = new StringBuilder();
                            var2_3.append("found in service: ");
                            var2_3.append(this.name);
                            var1_1.println(var2_3.toString());
                        }
                        var1_1 = var3_4.service;
                        this.factoryLock.releaseRead();
                        return var1_1;
                    }
                    this.factoryLock.releaseRead();
                    break;
                }
                var5_6 = var3_4;
                var14_15 = var8_9;
            } while (true);
        }
        if (ICUService.DEBUG == false) return this.handleDefault((Key)var1_1, (String[])var2_3);
        var3_4 = System.out;
        var5_6 = new StringBuilder();
        var5_6.append("not found in service: ");
        var5_6.append(this.name);
        var3_4.println(var5_6.toString());
        return this.handleDefault((Key)var1_1, (String[])var2_3);
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getVisibleIDs() {
        return this.getVisibleIDs(null);
    }

    public Set<String> getVisibleIDs(String hashSet) {
        Object object = this.getVisibleIDMap().keySet();
        Key key = this.createKey((String)((Object)hashSet));
        hashSet = object;
        if (key != null) {
            hashSet = new HashSet<Object>(object.size());
            Iterator<String> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (!key.isFallbackOf((String)object)) continue;
                hashSet.add(object);
            }
        }
        return hashSet;
    }

    protected Object handleDefault(Key key, String[] arrstring) {
        return null;
    }

    public boolean isDefault() {
        boolean bl = this.factories.size() == this.defaultSize;
        return bl;
    }

    protected void markDefault() {
        this.defaultSize = this.factories.size();
    }

    @Override
    protected void notifyListener(EventListener eventListener) {
        ((ServiceListener)eventListener).serviceChanged(this);
    }

    protected void reInitializeFactories() {
        this.factories.clear();
    }

    public final Factory registerFactory(Factory factory) {
        if (factory != null) {
            try {
                this.factoryLock.acquireWrite();
                this.factories.add(0, factory);
                this.clearCaches();
                this.notifyChanged();
                return factory;
            }
            finally {
                this.factoryLock.releaseWrite();
            }
        }
        throw new NullPointerException();
    }

    public Factory registerObject(Object object, String string) {
        return this.registerObject(object, string, true);
    }

    public Factory registerObject(Object object, String string, boolean bl) {
        return this.registerFactory(new SimpleFactory(object, this.createKey(string).canonicalID(), bl));
    }

    public final void reset() {
        try {
            this.factoryLock.acquireWrite();
            this.reInitializeFactories();
            this.clearCaches();
            this.notifyChanged();
            return;
        }
        finally {
            this.factoryLock.releaseWrite();
        }
    }

    public String stats() {
        ICURWLock.Stats stats = this.factoryLock.resetStats();
        if (stats != null) {
            return stats.toString();
        }
        return "no stats";
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Object.super.toString());
        stringBuilder.append("{");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final boolean unregisterFactory(Factory factory) {
        if (factory != null) {
            boolean bl;
            block6 : {
                bl = false;
                this.factoryLock.acquireWrite();
                if (this.factories.remove(factory)) {
                    bl = true;
                    this.clearCaches();
                }
                if (!bl) break block6;
                this.notifyChanged();
            }
            return bl;
            finally {
                this.factoryLock.releaseWrite();
            }
        }
        throw new NullPointerException();
    }

    private static final class CacheEntry {
        final String actualDescriptor;
        final Object service;

        CacheEntry(String string, Object object) {
            this.actualDescriptor = string;
            this.service = object;
        }
    }

    public static interface Factory {
        public Object create(Key var1, ICUService var2);

        public String getDisplayName(String var1, ULocale var2);

        public void updateVisibleIDs(Map<String, Factory> var1);
    }

    public static class Key {
        private final String id;

        public Key(String string) {
            this.id = string;
        }

        public String canonicalID() {
            return this.id;
        }

        public String currentDescriptor() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(this.currentID());
            return stringBuilder.toString();
        }

        public String currentID() {
            return this.canonicalID();
        }

        public boolean fallback() {
            return false;
        }

        public final String id() {
            return this.id;
        }

        public boolean isFallbackOf(String string) {
            return this.canonicalID().equals(string);
        }
    }

    private static class LocaleRef {
        private Comparator<Object> com;
        private SortedMap<String, String> dnCache;
        private final ULocale locale;

        LocaleRef(SortedMap<String, String> sortedMap, ULocale uLocale, Comparator<Object> comparator) {
            this.locale = uLocale;
            this.com = comparator;
            this.dnCache = sortedMap;
        }

        SortedMap<String, String> get(ULocale object, Comparator<Object> comparator) {
            SortedMap<String, String> sortedMap = this.dnCache;
            if (sortedMap != null && this.locale.equals(object) && ((object = this.com) == comparator || object != null && object.equals(comparator))) {
                return sortedMap;
            }
            return null;
        }
    }

    public static interface ServiceListener
    extends EventListener {
        public void serviceChanged(ICUService var1);
    }

    public static class SimpleFactory
    implements Factory {
        protected String id;
        protected Object instance;
        protected boolean visible;

        public SimpleFactory(Object object, String string) {
            this(object, string, true);
        }

        public SimpleFactory(Object object, String string, boolean bl) {
            if (object != null && string != null) {
                this.instance = object;
                this.id = string;
                this.visible = bl;
                return;
            }
            throw new IllegalArgumentException("Instance or id is null");
        }

        @Override
        public Object create(Key key, ICUService iCUService) {
            if (this.id.equals(key.currentID())) {
                return this.instance;
            }
            return null;
        }

        @Override
        public String getDisplayName(String string, ULocale uLocale) {
            if (!this.visible || !this.id.equals(string)) {
                string = null;
            }
            return string;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append(", id: ");
            stringBuilder.append(this.id);
            stringBuilder.append(", visible: ");
            stringBuilder.append(this.visible);
            return stringBuilder.toString();
        }

        @Override
        public void updateVisibleIDs(Map<String, Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }
    }

}

