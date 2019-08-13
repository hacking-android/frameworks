/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Locale;

public abstract class AbstractFileTypeDetector
extends FileTypeDetector {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";

    protected AbstractFileTypeDetector() {
    }

    private static boolean isTokenChar(char c) {
        boolean bl = c > ' ' && c < '' && TSPECIALS.indexOf(c) < 0;
        return bl;
    }

    private static boolean isValidToken(String string) {
        int n = string.length();
        if (n == 0) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (AbstractFileTypeDetector.isTokenChar(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private static String parse(String charSequence) {
        int n = ((String)charSequence).indexOf(47);
        int n2 = ((String)charSequence).indexOf(59);
        if (n < 0) {
            return null;
        }
        String string = ((String)charSequence).substring(0, n).trim().toLowerCase(Locale.ENGLISH);
        if (!AbstractFileTypeDetector.isValidToken(string)) {
            return null;
        }
        charSequence = n2 < 0 ? ((String)charSequence).substring(n + 1) : ((String)charSequence).substring(n + 1, n2);
        String string2 = ((String)charSequence).trim().toLowerCase(Locale.ENGLISH);
        if (!AbstractFileTypeDetector.isValidToken(string2)) {
            return null;
        }
        charSequence = new StringBuilder(string.length() + string2.length() + 1);
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append('/');
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    protected abstract String implProbeContentType(Path var1) throws IOException;

    @Override
    public final String probeContentType(Path object) throws IOException {
        if (object != null) {
            object = (object = this.implProbeContentType((Path)object)) == null ? null : AbstractFileTypeDetector.parse((String)object);
            return object;
        }
        throw new NullPointerException("'file' is null");
    }
}

