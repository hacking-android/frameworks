/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegexCache {
    private LRUCache<String, Pattern> cache;

    public RegexCache(int n) {
        this.cache = new LRUCache(n);
    }

    boolean containsRegex(String string) {
        return this.cache.containsKey(string);
    }

    public Pattern getPatternForRegex(String string) {
        Pattern pattern;
        Pattern pattern2 = pattern = this.cache.get(string);
        if (pattern == null) {
            pattern2 = Pattern.compile(string);
            this.cache.put(string, pattern2);
        }
        return pattern2;
    }

    private static class LRUCache<K, V> {
        private LinkedHashMap<K, V> map;
        private int size;

        public LRUCache(int n) {
            this.size = n;
            this.map = new LinkedHashMap<K, V>(n * 4 / 3 + 1, 0.75f, true){

                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
                    boolean bl = this.size() > size;
                    return bl;
                }
            };
        }

        public boolean containsKey(K k) {
            synchronized (this) {
                boolean bl = this.map.containsKey(k);
                return bl;
            }
        }

        public V get(K object) {
            synchronized (this) {
                object = this.map.get(object);
                return (V)object;
            }
        }

        public void put(K k, V v) {
            synchronized (this) {
                this.map.put(k, v);
                return;
            }
        }

    }

}

