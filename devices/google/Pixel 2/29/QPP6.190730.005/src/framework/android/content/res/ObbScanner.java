/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.content.res.ObbInfo;
import java.io.File;
import java.io.IOException;

public class ObbScanner {
    private ObbScanner() {
    }

    public static ObbInfo getObbInfo(String object) throws IOException {
        if (object != null) {
            Object object2 = new File((String)object);
            if (((File)object2).exists()) {
                object2 = ((File)object2).getCanonicalPath();
                object = new ObbInfo();
                ((ObbInfo)object).filename = object2;
                ObbScanner.getObbInfo_native((String)object2, (ObbInfo)object);
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("OBB file does not exist: ");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        throw new IllegalArgumentException("file path cannot be null");
    }

    private static native void getObbInfo_native(String var0, ObbInfo var1) throws IOException;
}

