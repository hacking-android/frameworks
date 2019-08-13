/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AlgorithmDecomposer {
    private static final Pattern pattern;
    private static final Pattern transPattern;

    static {
        transPattern = Pattern.compile("/");
        pattern = Pattern.compile("with|and", 2);
    }

    private static Set<String> decomposeImpl(String arrstring) {
        arrstring = transPattern.split((CharSequence)arrstring);
        HashSet<String> hashSet = new HashSet<String>();
        for (String string : arrstring) {
            if (string == null || string.length() == 0) continue;
            for (String string2 : pattern.split(string)) {
                if (string2 == null || string2.length() == 0) continue;
                hashSet.add(string2);
            }
        }
        return hashSet;
    }

    public static Set<String> decomposeOneHash(String object) {
        if (object != null && ((String)object).length() != 0) {
            object = AlgorithmDecomposer.decomposeImpl((String)object);
            AlgorithmDecomposer.hasLoop((Set<String>)object, "SHA-1", "SHA1");
            AlgorithmDecomposer.hasLoop((Set<String>)object, "SHA-224", "SHA224");
            AlgorithmDecomposer.hasLoop((Set<String>)object, "SHA-256", "SHA256");
            AlgorithmDecomposer.hasLoop((Set<String>)object, "SHA-384", "SHA384");
            AlgorithmDecomposer.hasLoop((Set<String>)object, "SHA-512", "SHA512");
            return object;
        }
        return new HashSet<String>();
    }

    private static void hasLoop(Set<String> set, String string, String string2) {
        if (set.contains(string)) {
            if (!set.contains(string2)) {
                set.add(string2);
            }
            set.remove(string);
        }
    }

    public static String hashName(String string) {
        return string.replace("-", "");
    }

    public Set<String> decompose(String object) {
        if (object != null && ((String)object).length() != 0) {
            if ((object = AlgorithmDecomposer.decomposeImpl((String)object)).contains("SHA1") && !object.contains("SHA-1")) {
                object.add("SHA-1");
            }
            if (object.contains("SHA-1") && !object.contains("SHA1")) {
                object.add("SHA1");
            }
            if (object.contains("SHA224") && !object.contains("SHA-224")) {
                object.add("SHA-224");
            }
            if (object.contains("SHA-224") && !object.contains("SHA224")) {
                object.add("SHA224");
            }
            if (object.contains("SHA256") && !object.contains("SHA-256")) {
                object.add("SHA-256");
            }
            if (object.contains("SHA-256") && !object.contains("SHA256")) {
                object.add("SHA256");
            }
            if (object.contains("SHA384") && !object.contains("SHA-384")) {
                object.add("SHA-384");
            }
            if (object.contains("SHA-384") && !object.contains("SHA384")) {
                object.add("SHA384");
            }
            if (object.contains("SHA512") && !object.contains("SHA-512")) {
                object.add("SHA-512");
            }
            if (object.contains("SHA-512") && !object.contains("SHA512")) {
                object.add("SHA512");
            }
            return object;
        }
        return new HashSet<String>();
    }
}

