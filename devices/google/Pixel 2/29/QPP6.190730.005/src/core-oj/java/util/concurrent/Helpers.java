/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.Collection;

class Helpers {
    private Helpers() {
    }

    static String collectionToString(Collection<?> collection) {
        Object[] arrobject = collection.toArray();
        int n = arrobject.length;
        if (n == 0) {
            return "[]";
        }
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            object = object == collection ? "(this Collection)" : Helpers.objectToString(object);
            arrobject[i] = object;
            n2 += ((String)object).length();
        }
        return Helpers.toString(arrobject, n, n2);
    }

    static String mapEntryToString(Object object, Object object2) {
        object = Helpers.objectToString(object);
        int n = ((String)object).length();
        object2 = Helpers.objectToString(object2);
        int n2 = ((String)object2).length();
        char[] arrc = new char[n + n2 + 1];
        ((String)object).getChars(0, n, arrc, 0);
        arrc[n] = (char)61;
        ((String)object2).getChars(0, n2, arrc, n + 1);
        return new String(arrc);
    }

    private static String objectToString(Object object) {
        block3 : {
            block2 : {
                if (object == null) break block2;
                String string = object.toString();
                object = string;
                if (string != null) break block3;
            }
            object = "null";
        }
        return object;
    }

    static String toString(Object[] arrobject, int n, int n2) {
        char[] arrc = new char[n * 2 + n2];
        arrc[0] = (char)91;
        n2 = 1;
        for (int i = 0; i < n; ++i) {
            int n3 = n2;
            if (i > 0) {
                int n4 = n2 + 1;
                arrc[n2] = (char)44;
                n3 = n4 + 1;
                arrc[n4] = (char)32;
            }
            String string = (String)arrobject[i];
            n2 = string.length();
            string.getChars(0, n2, arrc, n3);
            n2 = n3 + n2;
        }
        arrc[n2] = (char)93;
        return new String(arrc);
    }
}

