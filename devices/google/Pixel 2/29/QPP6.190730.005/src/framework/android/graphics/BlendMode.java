/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PorterDuff;
import android.graphics.Xfermode;

public enum BlendMode {
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
    PLUS(12),
    MODULATE(13),
    SCREEN(14),
    OVERLAY(15),
    DARKEN(16),
    LIGHTEN(17),
    COLOR_DODGE(18),
    COLOR_BURN(19),
    HARD_LIGHT(20),
    SOFT_LIGHT(21),
    DIFFERENCE(22),
    EXCLUSION(23),
    MULTIPLY(24),
    HUE(25),
    SATURATION(26),
    COLOR(27),
    LUMINOSITY(28);
    
    private static final BlendMode[] BLEND_MODES = BlendMode.values();
    private final Xfermode mXfermode = new Xfermode();

    private BlendMode(int n2) {
        this.mXfermode.porterDuffMode = n2;
    }

    public static PorterDuff.Mode blendModeToPorterDuffMode(BlendMode blendMode) {
        if (blendMode != null) {
            switch (blendMode) {
                default: {
                    return null;
                }
                case OVERLAY: {
                    return PorterDuff.Mode.OVERLAY;
                }
                case PLUS: {
                    return PorterDuff.Mode.ADD;
                }
                case SCREEN: {
                    return PorterDuff.Mode.SCREEN;
                }
                case MODULATE: {
                    return PorterDuff.Mode.MULTIPLY;
                }
                case LIGHTEN: {
                    return PorterDuff.Mode.LIGHTEN;
                }
                case DARKEN: {
                    return PorterDuff.Mode.DARKEN;
                }
                case XOR: {
                    return PorterDuff.Mode.XOR;
                }
                case DST_ATOP: {
                    return PorterDuff.Mode.DST_ATOP;
                }
                case SRC_ATOP: {
                    return PorterDuff.Mode.SRC_ATOP;
                }
                case DST_OUT: {
                    return PorterDuff.Mode.DST_OUT;
                }
                case SRC_OUT: {
                    return PorterDuff.Mode.SRC_OUT;
                }
                case DST_IN: {
                    return PorterDuff.Mode.DST_IN;
                }
                case SRC_IN: {
                    return PorterDuff.Mode.SRC_IN;
                }
                case DST_OVER: {
                    return PorterDuff.Mode.DST_OVER;
                }
                case SRC_OVER: {
                    return PorterDuff.Mode.SRC_OVER;
                }
                case DST: {
                    return PorterDuff.Mode.DST;
                }
                case SRC: {
                    return PorterDuff.Mode.SRC;
                }
                case CLEAR: 
            }
            return PorterDuff.Mode.CLEAR;
        }
        return null;
    }

    public static BlendMode fromValue(int n) {
        for (BlendMode blendMode : BLEND_MODES) {
            if (blendMode.mXfermode.porterDuffMode != n) continue;
            return blendMode;
        }
        return null;
    }

    public static int toValue(BlendMode blendMode) {
        return blendMode.getXfermode().porterDuffMode;
    }

    public Xfermode getXfermode() {
        return this.mXfermode;
    }

}

