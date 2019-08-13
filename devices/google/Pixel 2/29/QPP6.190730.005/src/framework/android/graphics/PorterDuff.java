/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;

public class PorterDuff {
    public static Mode intToMode(int n) {
        switch (n) {
            default: {
                return Mode.CLEAR;
            }
            case 17: {
                return Mode.LIGHTEN;
            }
            case 16: {
                return Mode.DARKEN;
            }
            case 15: {
                return Mode.OVERLAY;
            }
            case 14: {
                return Mode.SCREEN;
            }
            case 13: {
                return Mode.MULTIPLY;
            }
            case 12: {
                return Mode.ADD;
            }
            case 11: {
                return Mode.XOR;
            }
            case 10: {
                return Mode.DST_ATOP;
            }
            case 9: {
                return Mode.SRC_ATOP;
            }
            case 8: {
                return Mode.DST_OUT;
            }
            case 7: {
                return Mode.SRC_OUT;
            }
            case 6: {
                return Mode.DST_IN;
            }
            case 5: {
                return Mode.SRC_IN;
            }
            case 4: {
                return Mode.DST_OVER;
            }
            case 3: {
                return Mode.SRC_OVER;
            }
            case 2: {
                return Mode.DST;
            }
            case 1: 
        }
        return Mode.SRC;
    }

    public static int modeToInt(Mode mode) {
        return mode.nativeInt;
    }

    public static enum Mode {
        CLEAR(0),
        SRC(1),
        DST(2),
        SRC_OVER(3),
        DST_OVER(4),
        SRC_IN(5),
        DST_IN(6),
        SRC_OUT(7),
        DST_OUT(8),
        SRC_ATOP(9),
        DST_ATOP(10),
        XOR(11),
        DARKEN(16),
        LIGHTEN(17),
        MULTIPLY(13),
        SCREEN(14),
        ADD(12),
        OVERLAY(15);
        
        @UnsupportedAppUsage
        public final int nativeInt;

        private Mode(int n2) {
            this.nativeInt = n2;
        }
    }

}

