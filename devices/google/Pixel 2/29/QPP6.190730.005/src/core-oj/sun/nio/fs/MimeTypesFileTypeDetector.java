/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.MimeUtils
 */
package sun.nio.fs;

import java.nio.file.Path;
import libcore.net.MimeUtils;
import sun.nio.fs.AbstractFileTypeDetector;

class MimeTypesFileTypeDetector
extends AbstractFileTypeDetector {
    MimeTypesFileTypeDetector() {
    }

    private static String getExtension(String string) {
        String string2;
        String string3 = string2 = "";
        if (string != null) {
            string3 = string2;
            if (!string.isEmpty()) {
                int n = string.indexOf(46);
                string3 = string2;
                if (n >= 0) {
                    string3 = string2;
                    if (n < string.length() - 1) {
                        string3 = string.substring(n + 1);
                    }
                }
            }
        }
        return string3;
    }

    @Override
    protected String implProbeContentType(Path object) {
        String string;
        Object object2;
        if ((object = object.getFileName()) == null) {
            return null;
        }
        object = object2 = MimeTypesFileTypeDetector.getExtension(object.toString());
        if (((String)object2).isEmpty()) {
            return null;
        }
        do {
            string = MimeUtils.guessMimeTypeFromExtension((String)object);
            object2 = object;
            if (string == null) {
                object2 = MimeTypesFileTypeDetector.getExtension((String)object);
            }
            if (string != null) break;
            object = object2;
        } while (!((String)object2).isEmpty());
        return string;
    }
}

