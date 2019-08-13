/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import sun.net.www.ParseUtil;

public class PropertyExpander {
    public static String expand(String string) throws ExpandException {
        return PropertyExpander.expand(string, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String expand(String charSequence, boolean bl) throws ExpandException {
        if (charSequence == null) {
            return null;
        }
        int n = ((String)charSequence).indexOf("${", 0);
        if (n == -1) {
            return charSequence;
        }
        StringBuffer stringBuffer = new StringBuffer(((String)charSequence).length());
        int n2 = ((String)charSequence).length();
        int n3 = 0;
        do {
            String string;
            block16 : {
                int n4;
                if (n >= n2) return stringBuffer.toString();
                if (n > n3) {
                    stringBuffer.append(((String)charSequence).substring(n3, n));
                }
                n3 = n4 = n + 2;
                if (n4 < n2) {
                    n3 = n4;
                    if (((String)charSequence).charAt(n4) == '{') {
                        n3 = ((String)charSequence).indexOf("}}", n4);
                        if (n3 != -1 && n3 + 2 != n2) {
                            stringBuffer.append(((String)charSequence).substring(n, ++n3 + 1));
                            continue;
                        }
                        stringBuffer.append(((String)charSequence).substring(n));
                        return stringBuffer.toString();
                    }
                }
                while (n3 < n2 && ((String)charSequence).charAt(n3) != '}') {
                    ++n3;
                }
                if (n3 == n2) {
                    stringBuffer.append(((String)charSequence).substring(n, n3));
                    return stringBuffer.toString();
                }
                string = ((String)charSequence).substring(n + 2, n3);
                if (string.equals("/")) {
                    stringBuffer.append(File.separatorChar);
                    continue;
                }
                String string2 = System.getProperty(string);
                if (string2 == null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("unable to expand property ");
                    ((StringBuilder)charSequence).append(string);
                    throw new ExpandException(((StringBuilder)charSequence).toString());
                }
                string = string2;
                if (bl) {
                    try {
                        if (stringBuffer.length() <= 0) {
                            URI uRI = new URI(string2);
                            string = string2;
                            if (uRI.isAbsolute()) break block16;
                        }
                        string = ParseUtil.encodePath(string2);
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        string = ParseUtil.encodePath(string2);
                    }
                }
            }
            stringBuffer.append(string);
        } while ((n = ((String)charSequence).indexOf("${", ++n3)) != -1);
        if (n3 >= n2) return stringBuffer.toString();
        stringBuffer.append(((String)charSequence).substring(n3, n2));
        return stringBuffer.toString();
    }

    public static class ExpandException
    extends GeneralSecurityException {
        private static final long serialVersionUID = -7941948581406161702L;

        public ExpandException(String string) {
            super(string);
        }
    }

}

