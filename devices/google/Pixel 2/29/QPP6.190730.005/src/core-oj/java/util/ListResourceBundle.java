/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import sun.util.ResourceBundleEnumeration;

public abstract class ListResourceBundle
extends ResourceBundle {
    private volatile Map<String, Object> lookup = null;

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadLookup() {
        synchronized (this) {
            Object object = this.lookup;
            if (object != null) {
                return;
            }
            Object[][] arrobject = this.getContents();
            HashMap<String, Object> hashMap = new HashMap<String, Object>(arrobject.length);
            int n = 0;
            do {
                if (n >= arrobject.length) {
                    this.lookup = hashMap;
                    return;
                }
                String string = (String)arrobject[n][0];
                object = arrobject[n][1];
                if (string == null || object == null) break;
                hashMap.put(string, object);
                ++n;
            } while (true);
            object = new NullPointerException();
            throw object;
        }
    }

    protected abstract Object[][] getContents();

    @Override
    public Enumeration<String> getKeys() {
        if (this.lookup == null) {
            this.loadLookup();
        }
        Enumeration<String> enumeration = this.parent;
        Set<String> set = this.lookup.keySet();
        enumeration = enumeration != null ? ((ResourceBundle)((Object)enumeration)).getKeys() : null;
        return new ResourceBundleEnumeration(set, enumeration);
    }

    @Override
    public final Object handleGetObject(String string) {
        if (this.lookup == null) {
            this.loadLookup();
        }
        if (string != null) {
            return this.lookup.get(string);
        }
        throw new NullPointerException();
    }

    @Override
    protected Set<String> handleKeySet() {
        if (this.lookup == null) {
            this.loadLookup();
        }
        return this.lookup.keySet();
    }
}

