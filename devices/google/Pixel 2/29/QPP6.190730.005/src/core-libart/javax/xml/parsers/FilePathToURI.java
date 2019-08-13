/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.parsers;

import java.io.File;
import java.io.UnsupportedEncodingException;

class FilePathToURI {
    private static char[] gAfterEscaping1;
    private static char[] gAfterEscaping2;
    private static char[] gHexChs;
    private static boolean[] gNeedEscaping;

    static {
        char[] arrc;
        char[] arrc2;
        gNeedEscaping = new boolean[128];
        gAfterEscaping1 = new char[128];
        gAfterEscaping2 = new char[128];
        gHexChs = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i <= 31; ++i) {
            FilePathToURI.gNeedEscaping[i] = true;
            arrc2 = gAfterEscaping1;
            arrc = gHexChs;
            arrc2[i] = arrc[i >> 4];
            FilePathToURI.gAfterEscaping2[i] = arrc[i & 15];
        }
        FilePathToURI.gNeedEscaping[127] = true;
        FilePathToURI.gAfterEscaping1[127] = (char)55;
        FilePathToURI.gAfterEscaping2[127] = (char)70;
        char[] arrc3 = arrc = new char[15];
        arrc3[0] = 32;
        arrc3[1] = 60;
        arrc3[2] = 62;
        arrc3[3] = 35;
        arrc3[4] = 37;
        arrc3[5] = 34;
        arrc3[6] = 123;
        arrc3[7] = 125;
        arrc3[8] = 124;
        arrc3[9] = 92;
        arrc3[10] = 94;
        arrc3[11] = 126;
        arrc3[12] = 91;
        arrc3[13] = 93;
        arrc3[14] = 96;
        for (char c : arrc) {
            FilePathToURI.gNeedEscaping[c] = true;
            arrc2 = gAfterEscaping1;
            char[] arrc4 = gHexChs;
            arrc2[c] = arrc4[c >> 4];
            FilePathToURI.gAfterEscaping2[c] = arrc4[c & 15];
        }
    }

    FilePathToURI() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String filepath2URI(String string) {
        StringBuilder stringBuilder;
        block9 : {
            int n;
            byte[] arrby;
            int n2;
            if (string == null) {
                return null;
            }
            string = string.replace(File.separatorChar, '/');
            int n3 = string.length();
            stringBuilder = new StringBuilder(n3 * 3);
            stringBuilder.append("file://");
            if (n3 >= 2 && string.charAt(1) == ':' && (n2 = (int)Character.toUpperCase(string.charAt(0))) >= 65 && n2 <= 90) {
                stringBuilder.append('/');
            }
            for (n2 = 0; n2 < n3 && (n = string.charAt(n2)) < 128; ++n2) {
                if (gNeedEscaping[n]) {
                    stringBuilder.append('%');
                    stringBuilder.append(gAfterEscaping1[n]);
                    stringBuilder.append(gAfterEscaping2[n]);
                    continue;
                }
                stringBuilder.append((char)n);
            }
            if (n2 >= n3) break block9;
            try {
                arrby = string.substring(n2).getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                return string;
            }
            n3 = arrby.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n = arrby[n2];
                if (n < 0) {
                    stringBuilder.append('%');
                    stringBuilder.append(gHexChs[(n += 256) >> 4]);
                    stringBuilder.append(gHexChs[n & 15]);
                    continue;
                }
                if (gNeedEscaping[n]) {
                    stringBuilder.append('%');
                    stringBuilder.append(gAfterEscaping1[n]);
                    stringBuilder.append(gAfterEscaping2[n]);
                    continue;
                }
                stringBuilder.append((char)n);
            }
        }
        return stringBuilder.toString();
    }
}

