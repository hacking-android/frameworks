/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import java.util.Map;

public class VintfObject {
    private VintfObject() {
    }

    public static native String[] getHalNamesAndVersions();

    public static native String getSepolicyVersion();

    public static native Long getTargetFrameworkCompatibilityMatrixVersion();

    public static native Map<String, String[]> getVndkSnapshots();

    public static native String[] report();

    public static native int verify(String[] var0);

    public static native int verifyWithoutAvb();
}

