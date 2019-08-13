/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class HdmiUtils {
    public static final int HDMI_RELATIVE_POSITION_ABOVE = 5;
    public static final int HDMI_RELATIVE_POSITION_BELOW = 2;
    public static final int HDMI_RELATIVE_POSITION_DIFFERENT_BRANCH = 7;
    public static final int HDMI_RELATIVE_POSITION_DIRECTLY_ABOVE = 4;
    public static final int HDMI_RELATIVE_POSITION_DIRECTLY_BELOW = 1;
    public static final int HDMI_RELATIVE_POSITION_SAME = 3;
    public static final int HDMI_RELATIVE_POSITION_SIBLING = 6;
    public static final int HDMI_RELATIVE_POSITION_UNKNOWN = 0;
    private static final int NPOS = -1;
    static final int TARGET_NOT_UNDER_LOCAL_DEVICE = -1;
    static final int TARGET_SAME_PHYSICAL_ADDRESS = 0;

    private HdmiUtils() {
    }

    public static int getHdmiAddressRelativePosition(int n, int n2) {
        if (n != 65535 && n2 != 65535) {
            try {
                int n3 = HdmiUtils.physicalAddressFirstDifferentDigitPos(n, n2);
                if (n3 == -1) {
                    return 3;
                }
                int n4 = 61440 >> n3 * 4;
                ++n3;
                if ((n & n4) == 0) {
                    if (n3 == 4) {
                        return 4;
                    }
                    if ((61440 >> n3 * 4 & n2) == 0) {
                        return 4;
                    }
                    return 5;
                }
                if ((n2 & n4) == 0) {
                    if (n3 == 4) {
                        return 1;
                    }
                    if ((61440 >> n3 * 4 & n) == 0) {
                        return 1;
                    }
                    return 2;
                }
                if (n3 == 4) {
                    return 6;
                }
                if ((61440 >> n3 * 4 & n) == 0 && (61440 >> n3 * 4 & n2) == 0) {
                    return 6;
                }
                return 7;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return 0;
            }
        }
        return 0;
    }

    public static int getLocalPortFromPhysicalAddress(int n, int n2) {
        if (n2 == n) {
            return 0;
        }
        int n3 = 61440;
        int n4 = 61440;
        int n5 = n2;
        while (n5 != 0) {
            n5 = n2 & n3;
            n4 |= n3;
            n3 >>= 4;
        }
        if ((n4 << 4 & (n &= n4)) != n2) {
            return -1;
        }
        n &= n3 << 4;
        while (n >> 4 != 0) {
            n >>= 4;
        }
        return n;
    }

    public static boolean isValidPhysicalAddress(int n) {
        if (n >= 0 && n < 65535) {
            int n2 = 61440;
            boolean bl = false;
            for (int i = 0; i < 4; ++i) {
                boolean bl2;
                if ((n & n2) == 0) {
                    bl2 = true;
                } else {
                    bl2 = bl;
                    if (bl) {
                        return false;
                    }
                }
                n2 >>= 4;
                bl = bl2;
            }
            return true;
        }
        return false;
    }

    private static int physicalAddressFirstDifferentDigitPos(int n, int n2) throws IllegalArgumentException {
        if (HdmiUtils.isValidPhysicalAddress(n)) {
            if (HdmiUtils.isValidPhysicalAddress(n2)) {
                int n3 = 61440;
                for (int i = 0; i < 4; ++i) {
                    if ((n & n3) != (n2 & n3)) {
                        return i;
                    }
                    n3 >>= 4;
                }
                return -1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n2);
            stringBuilder.append(" is not a valid address.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is not a valid address.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HdmiAddressRelativePosition {
    }

}

