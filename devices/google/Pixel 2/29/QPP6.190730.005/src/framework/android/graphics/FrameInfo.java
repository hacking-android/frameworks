/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class FrameInfo {
    private static final int ANIMATION_START = 6;
    private static final int DRAW_START = 8;
    private static final int FLAGS = 0;
    public static final long FLAG_SURFACE_CANVAS = 4L;
    public static final long FLAG_WINDOW_LAYOUT_CHANGED = 1L;
    private static final int HANDLE_INPUT_START = 5;
    private static final int INTENDED_VSYNC = 1;
    private static final int NEWEST_INPUT_EVENT = 4;
    private static final int OLDEST_INPUT_EVENT = 3;
    private static final int PERFORM_TRAVERSALS_START = 7;
    private static final int VSYNC = 2;
    public long[] frameInfo = new long[9];

    public void addFlags(long l) {
        long[] arrl = this.frameInfo;
        arrl[0] = arrl[0] | l;
    }

    public void markAnimationsStart() {
        this.frameInfo[6] = System.nanoTime();
    }

    public void markDrawStart() {
        this.frameInfo[8] = System.nanoTime();
    }

    public void markInputHandlingStart() {
        this.frameInfo[5] = System.nanoTime();
    }

    public void markPerformTraversalsStart() {
        this.frameInfo[7] = System.nanoTime();
    }

    public void setVsync(long l, long l2) {
        long[] arrl = this.frameInfo;
        arrl[1] = l;
        arrl[2] = l2;
        arrl[3] = Long.MAX_VALUE;
        arrl[4] = 0L;
        arrl[0] = 0L;
    }

    public void updateInputEventTime(long l, long l2) {
        long[] arrl = this.frameInfo;
        if (l2 < arrl[3]) {
            arrl[3] = l2;
        }
        if (l > (arrl = this.frameInfo)[4]) {
            arrl[4] = l;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FrameInfoFlags {
    }

}

