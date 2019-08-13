/*
 * Decompiled with CFR 0.145.
 */
package dalvik.bytecode;

public final class OpcodeInfo {
    public static final int MAXIMUM_PACKED_VALUE;
    public static final int MAXIMUM_VALUE;

    static {
        MAXIMUM_VALUE = 65535;
        MAXIMUM_PACKED_VALUE = 255;
    }

    private OpcodeInfo() {
    }

    public static boolean isInvoke(int n) {
        return false;
    }
}

