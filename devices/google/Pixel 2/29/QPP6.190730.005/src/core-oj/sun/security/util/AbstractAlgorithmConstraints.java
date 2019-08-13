/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.security.AccessController;
import java.security.AlgorithmConstraints;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;
import sun.security.util.AlgorithmDecomposer;

public abstract class AbstractAlgorithmConstraints
implements AlgorithmConstraints {
    protected final AlgorithmDecomposer decomposer;

    protected AbstractAlgorithmConstraints(AlgorithmDecomposer algorithmDecomposer) {
        this.decomposer = algorithmDecomposer;
    }

    static boolean checkAlgorithm(String[] arrstring, String string, AlgorithmDecomposer algorithmDecomposer) {
        if (string != null && string.length() != 0) {
            int n = arrstring.length;
            Object object = null;
            for (int i = 0; i < n; ++i) {
                Set<String> set;
                block8 : {
                    String string2 = arrstring[i];
                    set = object;
                    if (string2 != null) {
                        if (string2.isEmpty()) {
                            set = object;
                        } else {
                            if (string2.equalsIgnoreCase(string)) {
                                return false;
                            }
                            Set<String> set2 = object;
                            if (object == null) {
                                set2 = algorithmDecomposer.decompose(string);
                            }
                            object = set2.iterator();
                            do {
                                set = set2;
                                if (!object.hasNext()) break block8;
                            } while (!string2.equalsIgnoreCase((String)object.next()));
                            return false;
                        }
                    }
                }
                object = set;
            }
            return true;
        }
        throw new IllegalArgumentException("No algorithm name specified");
    }

    static String[] getAlgorithms(String object) {
        String[] arrstring = AccessController.doPrivileged(new PrivilegedAction<String>((String)object){
            final /* synthetic */ String val$propertyName;
            {
                this.val$propertyName = string;
            }

            @Override
            public String run() {
                return Security.getProperty(this.val$propertyName);
            }
        });
        Object var2_2 = null;
        object = var2_2;
        if (arrstring != null) {
            object = var2_2;
            if (!arrstring.isEmpty()) {
                object = arrstring;
                if (arrstring.length() >= 2) {
                    object = arrstring;
                    if (arrstring.charAt(0) == '\"') {
                        object = arrstring;
                        if (arrstring.charAt(arrstring.length() - 1) == '\"') {
                            object = arrstring.substring(1, arrstring.length() - 1);
                        }
                    }
                }
                arrstring = object.split(",");
                int n = 0;
                do {
                    object = arrstring;
                    if (n >= arrstring.length) break;
                    arrstring[n] = arrstring[n].trim();
                    ++n;
                } while (true);
            }
        }
        arrstring = object;
        if (object == null) {
            arrstring = new String[]{};
        }
        return arrstring;
    }

}

