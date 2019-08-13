/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class ExpiringCache {
    private int MAX_ENTRIES = 200;
    private Map<String, Entry> map;
    private long millisUntilExpiration;
    private int queryCount;
    private int queryOverflow = 300;

    ExpiringCache() {
        this(30000L);
    }

    ExpiringCache(long l) {
        this.millisUntilExpiration = l;
        this.map = new LinkedHashMap<String, Entry>(){

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Entry> entry) {
                boolean bl = this.size() > ExpiringCache.this.MAX_ENTRIES;
                return bl;
            }
        };
    }

    private void cleanup() {
        Object object = this.map.keySet();
        String[] arrstring = new String[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrstring[n] = (String)object.next();
            ++n;
        }
        for (n = 0; n < arrstring.length; ++n) {
            this.entryFor(arrstring[n]);
        }
        this.queryCount = 0;
    }

    private Entry entryFor(String string) {
        Entry entry;
        block2 : {
            block3 : {
                Entry entry2;
                entry = entry2 = this.map.get(string);
                if (entry2 == null) break block2;
                long l = System.currentTimeMillis() - entry2.timestamp();
                if (l < 0L) break block3;
                entry = entry2;
                if (l < this.millisUntilExpiration) break block2;
            }
            this.map.remove(string);
            entry = null;
        }
        return entry;
    }

    void clear() {
        synchronized (this) {
            this.map.clear();
            return;
        }
    }

    String get(String object) {
        synchronized (this) {
            block5 : {
                int n;
                this.queryCount = n = this.queryCount + 1;
                if (n >= this.queryOverflow) {
                    this.cleanup();
                }
                if ((object = this.entryFor((String)object)) == null) break block5;
                object = ((Entry)object).val();
                return object;
            }
            return null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void put(String string, String string2) {
        synchronized (this) {
            int n;
            void var2_2;
            Entry entry;
            this.queryCount = n = this.queryCount + 1;
            if (n >= this.queryOverflow) {
                this.cleanup();
            }
            if ((entry = this.entryFor(string)) != null) {
                entry.setTimestamp(System.currentTimeMillis());
                entry.setVal((String)var2_2);
            } else {
                Map<String, Entry> map = this.map;
                entry = new Entry(System.currentTimeMillis(), (String)var2_2);
                map.put(string, entry);
            }
            return;
        }
    }

    static class Entry {
        private long timestamp;
        private String val;

        Entry(long l, String string) {
            this.timestamp = l;
            this.val = string;
        }

        void setTimestamp(long l) {
            this.timestamp = l;
        }

        void setVal(String string) {
            this.val = string;
        }

        long timestamp() {
            return this.timestamp;
        }

        String val() {
            return this.val;
        }
    }

}

