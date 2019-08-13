/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

public enum SignStyle {
    NORMAL,
    ALWAYS,
    NEVER,
    NOT_NEGATIVE,
    EXCEEDS_PAD;
    

    boolean parse(boolean bl, boolean bl2, boolean bl3) {
        block8 : {
            block7 : {
                int n = this.ordinal();
                boolean bl4 = false;
                boolean bl5 = false;
                if (n != 0) {
                    if (n != 1 && n != 4) {
                        bl = bl5;
                        if (!bl2) {
                            bl = bl5;
                            if (!bl3) {
                                bl = true;
                            }
                        }
                        return bl;
                    }
                    return true;
                }
                if (!bl) break block7;
                bl = bl4;
                if (bl2) break block8;
            }
            bl = true;
        }
        return bl;
    }
}

