/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.util.Strings;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Properties {
    private static final ThreadLocal threadProperties = new ThreadLocal();

    private Properties() {
    }

    public static BigInteger asBigInteger(String string) {
        if ((string = Properties.fetchProperty(string)) != null) {
            return new BigInteger(string);
        }
        return null;
    }

    public static Set<String> asKeySet(String object) {
        HashSet<String> hashSet = new HashSet<String>();
        if ((object = Properties.fetchProperty((String)object)) != null) {
            object = new StringTokenizer((String)object, ",");
            while (((StringTokenizer)object).hasMoreElements()) {
                hashSet.add(Strings.toLowerCase(((StringTokenizer)object).nextToken()).trim());
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static String fetchProperty(final String string) {
        return (String)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                Map map = (Map)threadProperties.get();
                if (map != null) {
                    return map.get(string);
                }
                return System.getProperty(string);
            }
        });
    }

    public static boolean isOverrideSet(String string) {
        block3 : {
            try {
                string = Properties.fetchProperty(string);
                if (string == null) break block3;
            }
            catch (AccessControlException accessControlException) {
                return false;
            }
            boolean bl = "true".equals(Strings.toLowerCase(string));
            return bl;
        }
        return false;
    }

    public static boolean removeThreadOverride(String string) {
        boolean bl = Properties.isOverrideSet(string);
        Map map = (Map)threadProperties.get();
        if (map == null) {
            return false;
        }
        map.remove(string);
        if (map.isEmpty()) {
            threadProperties.remove();
        } else {
            threadProperties.set(map);
        }
        return bl;
    }

    public static boolean setThreadOverride(String string, boolean bl) {
        boolean bl2 = Properties.isOverrideSet(string);
        Object object = (HashMap<String, Object>)threadProperties.get();
        HashMap<String, Object> hashMap = object;
        if (object == null) {
            hashMap = new HashMap<String, Object>();
        }
        object = bl ? "true" : "false";
        hashMap.put(string, object);
        threadProperties.set(hashMap);
        return bl2;
    }

}

