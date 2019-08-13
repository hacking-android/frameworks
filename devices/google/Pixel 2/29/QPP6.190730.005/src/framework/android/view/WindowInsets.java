/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.DisplayCutout;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public final class WindowInsets {
    @UnsupportedAppUsage
    public static final WindowInsets CONSUMED = new WindowInsets(null, null, false, false, null);
    private final boolean mAlwaysConsumeSystemBars;
    private final DisplayCutout mDisplayCutout;
    private final boolean mDisplayCutoutConsumed;
    private final boolean mIsRound;
    private final boolean mStableInsetsConsumed;
    private final boolean mSystemWindowInsetsConsumed;
    private Rect mTempRect;
    private final Insets[] mTypeInsetsMap;
    private final Insets[] mTypeMaxInsetsMap;
    private final boolean[] mTypeVisibilityMap;

    @UnsupportedAppUsage
    public WindowInsets(Rect rect) {
        this(WindowInsets.createCompatTypeMap(rect), null, new boolean[7], false, false, null);
    }

    public WindowInsets(Rect rect, Rect rect2, boolean bl, boolean bl2, DisplayCutout displayCutout) {
        this(WindowInsets.createCompatTypeMap(rect), WindowInsets.createCompatTypeMap(rect2), WindowInsets.createCompatVisibilityMap(WindowInsets.createCompatTypeMap(rect)), bl, bl2, displayCutout);
    }

    public WindowInsets(WindowInsets windowInsets) {
        boolean bl = windowInsets.mSystemWindowInsetsConsumed;
        Insets[] arrinsets = null;
        Insets[] arrinsets2 = bl ? null : windowInsets.mTypeInsetsMap;
        if (!windowInsets.mStableInsetsConsumed) {
            arrinsets = windowInsets.mTypeMaxInsetsMap;
        }
        this(arrinsets2, arrinsets, windowInsets.mTypeVisibilityMap, windowInsets.mIsRound, windowInsets.mAlwaysConsumeSystemBars, WindowInsets.displayCutoutCopyConstructorArgument(windowInsets));
    }

    public WindowInsets(Insets[] arrinsets, Insets[] arrinsets2, boolean[] arrbl, boolean bl, boolean bl2, DisplayCutout displayCutout) {
        boolean bl3 = true;
        boolean bl4 = arrinsets == null;
        this.mSystemWindowInsetsConsumed = bl4;
        arrinsets = this.mSystemWindowInsetsConsumed ? new Insets[7] : (Insets[])arrinsets.clone();
        this.mTypeInsetsMap = arrinsets;
        bl4 = arrinsets2 == null;
        this.mStableInsetsConsumed = bl4;
        arrinsets = this.mStableInsetsConsumed ? new Insets[7] : (Insets[])arrinsets2.clone();
        this.mTypeMaxInsetsMap = arrinsets;
        this.mTypeVisibilityMap = arrbl;
        this.mIsRound = bl;
        this.mAlwaysConsumeSystemBars = bl2;
        bl = displayCutout == null ? bl3 : false;
        this.mDisplayCutoutConsumed = bl;
        if (this.mDisplayCutoutConsumed || displayCutout.isEmpty()) {
            displayCutout = null;
        }
        this.mDisplayCutout = displayCutout;
    }

    static void assignCompatInsets(Insets[] arrinsets, Rect rect) {
        arrinsets[Type.indexOf((int)1)] = Insets.of(0, rect.top, 0, 0);
        arrinsets[Type.indexOf((int)4)] = Insets.of(rect.left, 0, rect.right, rect.bottom);
    }

    private static Insets[] createCompatTypeMap(Rect rect) {
        if (rect == null) {
            return null;
        }
        Insets[] arrinsets = new Insets[7];
        WindowInsets.assignCompatInsets(arrinsets, rect);
        return arrinsets;
    }

    private static boolean[] createCompatVisibilityMap(Insets[] arrinsets) {
        boolean[] arrbl = new boolean[7];
        if (arrinsets == null) {
            return arrbl;
        }
        for (int i = 1; i <= 64; i <<= 1) {
            int n = Type.indexOf(i);
            if (Insets.NONE.equals(arrinsets[n])) continue;
            arrbl[n] = true;
        }
        return arrbl;
    }

    private static DisplayCutout displayCutoutCopyConstructorArgument(WindowInsets object) {
        if (((WindowInsets)object).mDisplayCutoutConsumed) {
            return null;
        }
        object = ((WindowInsets)object).mDisplayCutout;
        if (object == null) {
            return DisplayCutout.NO_CUTOUT;
        }
        return object;
    }

    private static Insets getInsets(Insets[] object, int n) {
        Insets insets = null;
        for (int i = 1; i <= 64; i <<= 1) {
            Insets insets2;
            if ((n & i) == 0 || (insets2 = object[Type.indexOf(i)]) == null) continue;
            insets = insets == null ? insets2 : Insets.max(insets, insets2);
        }
        object = insets == null ? Insets.NONE : insets;
        return object;
    }

    private static Insets insetInsets(Insets insets, int n, int n2, int n3, int n4) {
        int n5 = Math.max(0, insets.left - n);
        int n6 = Math.max(0, insets.top - n2);
        int n7 = Math.max(0, insets.right - n3);
        int n8 = Math.max(0, insets.bottom - n4);
        if (n5 == n && n6 == n2 && n7 == n3 && n8 == n4) {
            return insets;
        }
        return Insets.of(n5, n6, n7, n8);
    }

    private static Insets[] insetInsets(Insets[] arrinsets, int n, int n2, int n3, int n4) {
        boolean bl = false;
        for (int i = 0; i < 7; ++i) {
            Insets[] arrinsets2;
            boolean bl2;
            Insets insets = arrinsets[i];
            if (insets == null) {
                bl2 = bl;
                arrinsets2 = arrinsets;
            } else {
                Insets insets2 = WindowInsets.insetInsets(insets, n, n2, n3, n4);
                bl2 = bl;
                arrinsets2 = arrinsets;
                if (insets2 != insets) {
                    bl2 = bl;
                    arrinsets2 = arrinsets;
                    if (!bl) {
                        arrinsets2 = (Insets[])arrinsets.clone();
                        bl2 = true;
                    }
                    arrinsets2[i] = insets2;
                }
            }
            bl = bl2;
            arrinsets = arrinsets2;
        }
        return arrinsets;
    }

    private static void setInsets(Insets[] arrinsets, int n, Insets insets) {
        for (int i = 1; i <= 64; i <<= 1) {
            if ((n & i) == 0) continue;
            arrinsets[Type.indexOf((int)i)] = insets;
        }
    }

    public WindowInsets consumeDisplayCutout() {
        Insets[] arrinsets = this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap;
        Insets[] arrinsets2 = this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap;
        return new WindowInsets(arrinsets, arrinsets2, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, null);
    }

    public WindowInsets consumeStableInsets() {
        Insets[] arrinsets = this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap;
        return new WindowInsets(arrinsets, null, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, WindowInsets.displayCutoutCopyConstructorArgument(this));
    }

    public WindowInsets consumeSystemWindowInsets() {
        Insets[] arrinsets = this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap;
        return new WindowInsets(null, arrinsets, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, WindowInsets.displayCutoutCopyConstructorArgument(this));
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof WindowInsets) {
            object = (WindowInsets)object;
            if (!(this.mIsRound == ((WindowInsets)object).mIsRound && this.mAlwaysConsumeSystemBars == ((WindowInsets)object).mAlwaysConsumeSystemBars && this.mSystemWindowInsetsConsumed == ((WindowInsets)object).mSystemWindowInsetsConsumed && this.mStableInsetsConsumed == ((WindowInsets)object).mStableInsetsConsumed && this.mDisplayCutoutConsumed == ((WindowInsets)object).mDisplayCutoutConsumed && Arrays.equals(this.mTypeInsetsMap, ((WindowInsets)object).mTypeInsetsMap) && Arrays.equals(this.mTypeMaxInsetsMap, ((WindowInsets)object).mTypeMaxInsetsMap) && Arrays.equals(this.mTypeVisibilityMap, ((WindowInsets)object).mTypeVisibilityMap) && Objects.equals(this.mDisplayCutout, ((WindowInsets)object).mDisplayCutout))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public Insets getInsets(int n) {
        return WindowInsets.getInsets(this.mTypeInsetsMap, n);
    }

    public Insets getMandatorySystemGestureInsets() {
        return WindowInsets.getInsets(this.mTypeInsetsMap, 16);
    }

    public Insets getMaxInsets(int n) throws IllegalArgumentException {
        if ((n & 2) == 0) {
            return WindowInsets.getInsets(this.mTypeMaxInsetsMap, n);
        }
        throw new IllegalArgumentException("Unable to query the maximum insets for IME");
    }

    public int getStableInsetBottom() {
        return this.getStableInsets().bottom;
    }

    public int getStableInsetLeft() {
        return this.getStableInsets().left;
    }

    public int getStableInsetRight() {
        return this.getStableInsets().right;
    }

    public int getStableInsetTop() {
        return this.getStableInsets().top;
    }

    public Insets getStableInsets() {
        return WindowInsets.getInsets(this.mTypeMaxInsetsMap, Type.compatSystemInsets());
    }

    public Insets getSystemGestureInsets() {
        return WindowInsets.getInsets(this.mTypeInsetsMap, 8);
    }

    public int getSystemWindowInsetBottom() {
        return this.getSystemWindowInsets().bottom;
    }

    public int getSystemWindowInsetLeft() {
        return this.getSystemWindowInsets().left;
    }

    public int getSystemWindowInsetRight() {
        return this.getSystemWindowInsets().right;
    }

    public int getSystemWindowInsetTop() {
        return this.getSystemWindowInsets().top;
    }

    public Insets getSystemWindowInsets() {
        return WindowInsets.getInsets(this.mTypeInsetsMap, Type.compatSystemInsets());
    }

    @Deprecated
    public Rect getSystemWindowInsetsAsRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Insets insets = this.getSystemWindowInsets();
        this.mTempRect.set(insets.left, insets.top, insets.right, insets.bottom);
        return this.mTempRect;
    }

    public Insets getTappableElementInsets() {
        return WindowInsets.getInsets(this.mTypeInsetsMap, 32);
    }

    public boolean hasInsets() {
        boolean bl = !WindowInsets.getInsets(this.mTypeInsetsMap, Type.all()).equals(Insets.NONE) || !WindowInsets.getInsets(this.mTypeMaxInsetsMap, Type.all()).equals(Insets.NONE) || this.mDisplayCutout != null;
        return bl;
    }

    public boolean hasStableInsets() {
        return this.getStableInsets().equals(Insets.NONE) ^ true;
    }

    public boolean hasSystemWindowInsets() {
        return this.getSystemWindowInsets().equals(Insets.NONE) ^ true;
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.mTypeInsetsMap), Arrays.hashCode(this.mTypeMaxInsetsMap), Arrays.hashCode(this.mTypeVisibilityMap), this.mIsRound, this.mDisplayCutout, this.mAlwaysConsumeSystemBars, this.mSystemWindowInsetsConsumed, this.mStableInsetsConsumed, this.mDisplayCutoutConsumed);
    }

    public WindowInsets inset(int n, int n2, int n3, int n4) {
        DisplayCutout displayCutout;
        Preconditions.checkArgumentNonnegative(n);
        Preconditions.checkArgumentNonnegative(n2);
        Preconditions.checkArgumentNonnegative(n3);
        Preconditions.checkArgumentNonnegative(n4);
        Insets[] arrinsets = this.mSystemWindowInsetsConsumed ? null : WindowInsets.insetInsets(this.mTypeInsetsMap, n, n2, n3, n4);
        Insets[] arrinsets2 = this.mStableInsetsConsumed ? null : WindowInsets.insetInsets(this.mTypeMaxInsetsMap, n, n2, n3, n4);
        boolean[] arrbl = this.mTypeVisibilityMap;
        boolean bl = this.mIsRound;
        boolean bl2 = this.mAlwaysConsumeSystemBars;
        displayCutout = this.mDisplayCutoutConsumed ? null : ((displayCutout = this.mDisplayCutout) == null ? DisplayCutout.NO_CUTOUT : displayCutout.inset(n, n2, n3, n4));
        return new WindowInsets(arrinsets, arrinsets2, arrbl, bl, bl2, displayCutout);
    }

    public WindowInsets inset(Insets insets) {
        return this.inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    @Deprecated
    public WindowInsets inset(Rect rect) {
        return this.inset(rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean isConsumed() {
        boolean bl = this.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed && this.mDisplayCutoutConsumed;
        return bl;
    }

    public boolean isRound() {
        return this.mIsRound;
    }

    boolean isSystemWindowInsetsConsumed() {
        return this.mSystemWindowInsetsConsumed;
    }

    public boolean isVisible(int n) {
        for (int i = 1; i <= 64; i <<= 1) {
            if ((n & i) == 0 || this.mTypeVisibilityMap[Type.indexOf(i)]) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(int n, int n2, int n3, int n4) {
        if (this.mSystemWindowInsetsConsumed) {
            return this;
        }
        return new Builder(this).setSystemWindowInsets(Insets.of(n, n2, n3, n4)).build();
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(Rect rect) {
        return this.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean shouldAlwaysConsumeSystemBars() {
        return this.mAlwaysConsumeSystemBars;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WindowInsets{systemWindowInsets=");
        stringBuilder.append(this.getSystemWindowInsets());
        stringBuilder.append(" stableInsets=");
        stringBuilder.append(this.getStableInsets());
        stringBuilder.append(" sysGestureInsets=");
        stringBuilder.append(this.getSystemGestureInsets());
        Object object = this.mDisplayCutout;
        String string2 = "";
        if (object != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" cutout=");
            ((StringBuilder)object).append(this.mDisplayCutout);
            object = ((StringBuilder)object).toString();
        } else {
            object = "";
        }
        stringBuilder.append((String)object);
        object = string2;
        if (this.isRound()) {
            object = " round";
        }
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final class Builder {
        private boolean mAlwaysConsumeSystemBars;
        private DisplayCutout mDisplayCutout;
        private boolean mIsRound;
        private boolean mStableInsetsConsumed = true;
        private boolean mSystemInsetsConsumed = true;
        private final Insets[] mTypeInsetsMap;
        private final Insets[] mTypeMaxInsetsMap;
        private final boolean[] mTypeVisibilityMap;

        public Builder() {
            this.mTypeInsetsMap = new Insets[7];
            this.mTypeMaxInsetsMap = new Insets[7];
            this.mTypeVisibilityMap = new boolean[7];
        }

        public Builder(WindowInsets windowInsets) {
            this.mTypeInsetsMap = (Insets[])windowInsets.mTypeInsetsMap.clone();
            this.mTypeMaxInsetsMap = (Insets[])windowInsets.mTypeMaxInsetsMap.clone();
            this.mTypeVisibilityMap = (boolean[])windowInsets.mTypeVisibilityMap.clone();
            this.mSystemInsetsConsumed = windowInsets.mSystemWindowInsetsConsumed;
            this.mStableInsetsConsumed = windowInsets.mStableInsetsConsumed;
            this.mDisplayCutout = WindowInsets.displayCutoutCopyConstructorArgument(windowInsets);
            this.mIsRound = windowInsets.mIsRound;
            this.mAlwaysConsumeSystemBars = windowInsets.mAlwaysConsumeSystemBars;
        }

        public WindowInsets build() {
            Insets[] arrinsets = this.mSystemInsetsConsumed ? null : this.mTypeInsetsMap;
            Insets[] arrinsets2 = this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap;
            return new WindowInsets(arrinsets, arrinsets2, this.mTypeVisibilityMap, this.mIsRound, this.mAlwaysConsumeSystemBars, this.mDisplayCutout);
        }

        public Builder setAlwaysConsumeSystemBars(boolean bl) {
            this.mAlwaysConsumeSystemBars = bl;
            return this;
        }

        public Builder setDisplayCutout(DisplayCutout displayCutout) {
            if (displayCutout == null) {
                displayCutout = DisplayCutout.NO_CUTOUT;
            }
            this.mDisplayCutout = displayCutout;
            return this;
        }

        public Builder setInsets(int n, Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeInsetsMap, n, insets);
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setMandatorySystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 16, insets);
            return this;
        }

        public Builder setMaxInsets(int n, Insets insets) throws IllegalArgumentException {
            if (n != 2) {
                Preconditions.checkNotNull(insets);
                WindowInsets.setInsets(this.mTypeMaxInsetsMap, n, insets);
                this.mStableInsetsConsumed = false;
                return this;
            }
            throw new IllegalArgumentException("Maximum inset not available for IME");
        }

        public Builder setRound(boolean bl) {
            this.mIsRound = bl;
            return this;
        }

        public Builder setStableInsets(Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.assignCompatInsets(this.mTypeMaxInsetsMap, insets.toRect());
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setSystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 8, insets);
            return this;
        }

        public Builder setSystemWindowInsets(Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.assignCompatInsets(this.mTypeInsetsMap, insets.toRect());
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setTappableElementInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 32, insets);
            return this;
        }

        public Builder setVisible(int n, boolean bl) {
            for (int i = 1; i <= 64; i <<= 1) {
                if ((n & i) == 0) continue;
                this.mTypeVisibilityMap[Type.indexOf((int)i)] = bl;
            }
            return this;
        }
    }

    public static final class Type {
        static final int FIRST = 1;
        static final int IME = 2;
        static final int LAST = 64;
        static final int MANDATORY_SYSTEM_GESTURES = 16;
        static final int SIDE_BARS = 4;
        static final int SIZE = 7;
        static final int SYSTEM_GESTURES = 8;
        static final int TAPPABLE_ELEMENT = 32;
        static final int TOP_BAR = 1;
        static final int WINDOW_DECOR = 64;

        private Type() {
        }

        public static int all() {
            return -1;
        }

        static int compatSystemInsets() {
            return 7;
        }

        public static int ime() {
            return 2;
        }

        static int indexOf(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 8) {
                            if (n != 16) {
                                if (n != 32) {
                                    if (n == 64) {
                                        return 6;
                                    }
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("type needs to be >= FIRST and <= LAST, type=");
                                    stringBuilder.append(n);
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                return 5;
                            }
                            return 4;
                        }
                        return 3;
                    }
                    return 2;
                }
                return 1;
            }
            return 0;
        }

        public static int mandatorySystemGestures() {
            return 16;
        }

        public static int sideBars() {
            return 4;
        }

        public static int systemBars() {
            return 5;
        }

        public static int systemGestures() {
            return 8;
        }

        public static int tappableElement() {
            return 32;
        }

        public static int topBar() {
            return 1;
        }

        public static int windowDecor() {
            return 64;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface InsetType {
        }

    }

}

