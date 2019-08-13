/*
 * Decompiled with CFR 0.145.
 */
package sun.util.resources;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import sun.util.ResourceBundleEnumeration;

public abstract class OpenListResourceBundle
extends ResourceBundle {
    private volatile Set<String> keyset;
    private volatile Map<String, Object> lookup = null;

    protected OpenListResourceBundle() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadLookup() {
        Object[][] arrobject = this.getContents();
        Map<String, Object> map = this.createMap(arrobject.length);
        for (int i = 0; i < arrobject.length; ++i) {
            String string = (String)arrobject[i][0];
            Object object = arrobject[i][1];
            if (string != null && object != null) {
                map.put(string, object);
                continue;
            }
            throw new NullPointerException();
        }
        synchronized (this) {
            if (this.lookup == null) {
                this.lookup = map;
            }
            return;
        }
    }

    protected <K, V> Map<K, V> createMap(int n) {
        return new HashMap(n);
    }

    protected <E> Set<E> createSet() {
        return new HashSet();
    }

    protected abstract Object[][] getContents();

    @Override
    public Enumeration<String> getKeys() {
        Enumeration<String> enumeration = this.parent;
        Set<String> set = this.handleKeySet();
        enumeration = enumeration != null ? ((ResourceBundle)((Object)enumeration)).getKeys() : null;
        return new ResourceBundleEnumeration(set, enumeration);
    }

    @Override
    protected Object handleGetObject(String string) {
        if (string != null) {
            this.loadLookupTablesIfNecessary();
            return this.lookup.get(string);
        }
        throw new NullPointerException();
    }

    @Override
    protected Set<String> handleKeySet() {
        this.loadLookupTablesIfNecessary();
        return this.lookup.keySet();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Set<String> keySet() {
        if (this.keyset != null) {
            return this.keyset;
        }
        Set<String> set = this.createSet();
        set.addAll(this.handleKeySet());
        if (this.parent != null) {
            set.addAll(this.parent.keySet());
        }
        synchronized (this) {
            if (this.keyset == null) {
                this.keyset = set;
            }
            return this.keyset;
        }
    }

    void loadLookupTablesIfNecessary() {
        if (this.lookup == null) {
            this.loadLookup();
        }
    }
}

