/*
 * Decompiled with CFR 0.145.
 */
package android.view;

public class SoundEffectConstants {
    public static final int CLICK = 0;
    public static final int NAVIGATION_DOWN = 4;
    public static final int NAVIGATION_LEFT = 1;
    public static final int NAVIGATION_RIGHT = 3;
    public static final int NAVIGATION_UP = 2;

    private SoundEffectConstants() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getContantForFocusDirection(int n) {
        if (n == 1) return 2;
        if (n == 2) return 4;
        if (n == 17) return 1;
        if (n == 33) return 2;
        if (n == 66) return 3;
        if (n == 130) return 4;
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
    }
}

