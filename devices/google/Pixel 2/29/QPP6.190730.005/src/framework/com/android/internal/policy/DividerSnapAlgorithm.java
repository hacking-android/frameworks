/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.internal.policy.DockedDividerUtils;
import java.util.ArrayList;

public class DividerSnapAlgorithm {
    private static final int MIN_DISMISS_VELOCITY_DP_PER_SECOND = 600;
    private static final int MIN_FLING_VELOCITY_DP_PER_SECOND = 400;
    private static final int SNAP_FIXED_RATIO = 1;
    private static final int SNAP_MODE_16_9 = 0;
    private static final int SNAP_MODE_MINIMIZED = 3;
    private static final int SNAP_ONLY_1_1 = 2;
    private final SnapTarget mDismissEndTarget;
    private final SnapTarget mDismissStartTarget;
    private final int mDisplayHeight;
    private final int mDisplayWidth;
    private final int mDividerSize;
    private final SnapTarget mFirstSplitTarget;
    private final float mFixedRatio;
    private final Rect mInsets = new Rect();
    private boolean mIsHorizontalDivision;
    private final SnapTarget mLastSplitTarget;
    private final SnapTarget mMiddleTarget;
    private final float mMinDismissVelocityPxPerSecond;
    private final float mMinFlingVelocityPxPerSecond;
    private final int mMinimalSizeResizableTask;
    private final int mSnapMode;
    private final ArrayList<SnapTarget> mTargets = new ArrayList();
    private final int mTaskHeightInMinimizedMode;

    public DividerSnapAlgorithm(Resources resources, int n, int n2, int n3, boolean bl, Rect rect) {
        this(resources, n, n2, n3, bl, rect, -1, false);
    }

    public DividerSnapAlgorithm(Resources resources, int n, int n2, int n3, boolean bl, Rect rect, int n4) {
        this(resources, n, n2, n3, bl, rect, n4, false);
    }

    public DividerSnapAlgorithm(Resources arrayList, int n, int n2, int n3, boolean bl, Rect rect, int n4, boolean bl2) {
        this.mMinFlingVelocityPxPerSecond = arrayList.getDisplayMetrics().density * 400.0f;
        this.mMinDismissVelocityPxPerSecond = arrayList.getDisplayMetrics().density * 600.0f;
        this.mDividerSize = n3;
        this.mDisplayWidth = n;
        this.mDisplayHeight = n2;
        this.mIsHorizontalDivision = bl;
        this.mInsets.set(rect);
        n = bl2 ? 3 : ((Resources)((Object)arrayList)).getInteger(17694795);
        this.mSnapMode = n;
        this.mFixedRatio = ((Resources)((Object)arrayList)).getFraction(18022407, 1, 1);
        this.mMinimalSizeResizableTask = ((Resources)((Object)arrayList)).getDimensionPixelSize(17105123);
        this.mTaskHeightInMinimizedMode = ((Resources)((Object)arrayList)).getDimensionPixelSize(17105432);
        this.calculateTargets(bl, n4);
        this.mFirstSplitTarget = this.mTargets.get(1);
        arrayList = this.mTargets;
        this.mLastSplitTarget = (SnapTarget)arrayList.get(arrayList.size() - 2);
        this.mDismissStartTarget = this.mTargets.get(0);
        arrayList = this.mTargets;
        this.mDismissEndTarget = arrayList.get(arrayList.size() - 1);
        arrayList = this.mTargets;
        this.mMiddleTarget = arrayList.get(arrayList.size() / 2);
        this.mMiddleTarget.isMiddleTarget = true;
    }

    private void addFixedDivisionTargets(boolean bl, int n) {
        Rect rect = this.mInsets;
        int n2 = bl ? rect.top : rect.left;
        int n3 = bl ? this.mDisplayHeight - this.mInsets.bottom : this.mDisplayWidth - this.mInsets.right;
        int n4 = (int)(this.mFixedRatio * (float)(n3 - n2));
        int n5 = this.mDividerSize;
        this.addNonDismissingTargets(bl, n2 + (n4 -= n5 / 2), n3 - n4 - n5, n);
    }

    private void addMiddleTarget(boolean bl) {
        int n = DockedDividerUtils.calculateMiddlePosition(bl, this.mInsets, this.mDisplayWidth, this.mDisplayHeight, this.mDividerSize);
        this.mTargets.add(new SnapTarget(n, n, 0));
    }

    private void addMinimizedTarget(boolean bl, int n) {
        int n2;
        int n3 = n2 = this.mTaskHeightInMinimizedMode + this.mInsets.top;
        if (!bl) {
            if (n == 1) {
                n3 = n2 + this.mInsets.left;
            } else {
                n3 = n2;
                if (n == 3) {
                    n3 = this.mDisplayWidth - n2 - this.mInsets.right - this.mDividerSize;
                }
            }
        }
        this.mTargets.add(new SnapTarget(n3, n3, 0));
    }

    private void addNonDismissingTargets(boolean bl, int n, int n2, int n3) {
        this.maybeAddTarget(n, n - this.mInsets.top);
        this.addMiddleTarget(bl);
        this.maybeAddTarget(n2, n3 - this.mInsets.bottom - (this.mDividerSize + n2));
    }

    private void addRatio16_9Targets(boolean bl, int n) {
        Rect rect = this.mInsets;
        int n2 = bl ? rect.top : rect.left;
        int n3 = bl ? this.mDisplayHeight - this.mInsets.bottom : this.mDisplayWidth - this.mInsets.right;
        rect = this.mInsets;
        int n4 = bl ? rect.left : rect.top;
        int n5 = bl ? this.mDisplayWidth - this.mInsets.right : this.mDisplayHeight - this.mInsets.bottom;
        n4 = (int)Math.floor((float)(n5 - n4) * 0.5625f);
        this.addNonDismissingTargets(bl, n2 + n4, n3 - n4 - this.mDividerSize, n);
    }

    private void calculateTargets(boolean bl, int n) {
        int n2;
        this.mTargets.clear();
        int n3 = bl ? this.mDisplayHeight : this.mDisplayWidth;
        Rect rect = this.mInsets;
        int n4 = bl ? rect.bottom : rect.right;
        int n5 = n2 = -this.mDividerSize;
        if (n == 3) {
            n5 = n2 + this.mInsets.left;
        }
        this.mTargets.add(new SnapTarget(n5, n5, 1, 0.35f));
        n5 = this.mSnapMode;
        if (n5 != 0) {
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 == 3) {
                        this.addMinimizedTarget(bl, n);
                    }
                } else {
                    this.addMiddleTarget(bl);
                }
            } else {
                this.addFixedDivisionTargets(bl, n3);
            }
        } else {
            this.addRatio16_9Targets(bl, n3);
        }
        this.mTargets.add(new SnapTarget(n3 - n4, n3, 2, 0.35f));
    }

    public static DividerSnapAlgorithm create(Context context, Rect rect) {
        DisplayInfo displayInfo = new DisplayInfo();
        context.getSystemService(DisplayManager.class).getDisplay(0).getDisplayInfo(displayInfo);
        int n = context.getResources().getDimensionPixelSize(17105142);
        int n2 = context.getResources().getDimensionPixelSize(17105141);
        Resources resources = context.getResources();
        int n3 = displayInfo.logicalWidth;
        int n4 = displayInfo.logicalHeight;
        int n5 = context.getApplicationContext().getResources().getConfiguration().orientation;
        boolean bl = true;
        if (n5 != 1) {
            bl = false;
        }
        return new DividerSnapAlgorithm(resources, n3, n4, n - n2 * 2, bl, rect);
    }

    private int getEndInset() {
        if (this.mIsHorizontalDivision) {
            return this.mInsets.bottom;
        }
        return this.mInsets.right;
    }

    private int getStartInset() {
        if (this.mIsHorizontalDivision) {
            return this.mInsets.top;
        }
        return this.mInsets.left;
    }

    private void maybeAddTarget(int n, int n2) {
        if (n2 >= this.mMinimalSizeResizableTask) {
            this.mTargets.add(new SnapTarget(n, n, 0));
        }
    }

    private SnapTarget snap(int n, boolean bl) {
        int n2 = -1;
        float f = Float.MAX_VALUE;
        int n3 = this.mTargets.size();
        for (int i = 0; i < n3; ++i) {
            float f2;
            SnapTarget snapTarget = this.mTargets.get(i);
            float f3 = f2 = (float)Math.abs(n - snapTarget.position);
            if (bl) {
                f3 = f2 / snapTarget.distanceMultiplier;
            }
            f2 = f;
            if (f3 < f) {
                n2 = i;
                f2 = f3;
            }
            f = f2;
        }
        return this.mTargets.get(n2);
    }

    public float calculateDismissingFraction(int n) {
        if (n < this.mFirstSplitTarget.position) {
            return 1.0f - (float)(n - this.getStartInset()) / (float)(this.mFirstSplitTarget.position - this.getStartInset());
        }
        if (n > this.mLastSplitTarget.position) {
            return (float)(n - this.mLastSplitTarget.position) / (float)(this.mDismissEndTarget.position - this.mLastSplitTarget.position - this.mDividerSize);
        }
        return 0.0f;
    }

    public SnapTarget calculateNonDismissingSnapTarget(int n) {
        SnapTarget snapTarget = this.snap(n, false);
        if (snapTarget == this.mDismissStartTarget) {
            return this.mFirstSplitTarget;
        }
        if (snapTarget == this.mDismissEndTarget) {
            return this.mLastSplitTarget;
        }
        return snapTarget;
    }

    public SnapTarget calculateSnapTarget(int n, float f) {
        return this.calculateSnapTarget(n, f, true);
    }

    public SnapTarget calculateSnapTarget(int n, float f, boolean bl) {
        if (n < this.mFirstSplitTarget.position && f < -this.mMinDismissVelocityPxPerSecond) {
            return this.mDismissStartTarget;
        }
        if (n > this.mLastSplitTarget.position && f > this.mMinDismissVelocityPxPerSecond) {
            return this.mDismissEndTarget;
        }
        if (Math.abs(f) < this.mMinFlingVelocityPxPerSecond) {
            return this.snap(n, bl);
        }
        if (f < 0.0f) {
            return this.mFirstSplitTarget;
        }
        return this.mLastSplitTarget;
    }

    public SnapTarget cycleNonDismissTarget(SnapTarget object, int n) {
        int n2 = this.mTargets.indexOf(object);
        if (n2 != -1) {
            object = this.mTargets;
            if ((object = (SnapTarget)((ArrayList)object).get((((ArrayList)object).size() + n2 + n) % this.mTargets.size())) == this.mDismissStartTarget) {
                return this.mLastSplitTarget;
            }
            if (object == this.mDismissEndTarget) {
                return this.mFirstSplitTarget;
            }
            return object;
        }
        return object;
    }

    public SnapTarget getClosestDismissTarget(int n) {
        if (n < this.mFirstSplitTarget.position) {
            return this.mDismissStartTarget;
        }
        if (n > this.mLastSplitTarget.position) {
            return this.mDismissEndTarget;
        }
        if (n - this.mDismissStartTarget.position < this.mDismissEndTarget.position - n) {
            return this.mDismissStartTarget;
        }
        return this.mDismissEndTarget;
    }

    public SnapTarget getDismissEndTarget() {
        return this.mDismissEndTarget;
    }

    public SnapTarget getDismissStartTarget() {
        return this.mDismissStartTarget;
    }

    public SnapTarget getFirstSplitTarget() {
        return this.mFirstSplitTarget;
    }

    public SnapTarget getLastSplitTarget() {
        return this.mLastSplitTarget;
    }

    public SnapTarget getMiddleTarget() {
        return this.mMiddleTarget;
    }

    public SnapTarget getNextTarget(SnapTarget snapTarget) {
        int n = this.mTargets.indexOf(snapTarget);
        if (n != -1 && n < this.mTargets.size() - 1) {
            return this.mTargets.get(n + 1);
        }
        return snapTarget;
    }

    public SnapTarget getPreviousTarget(SnapTarget snapTarget) {
        int n = this.mTargets.indexOf(snapTarget);
        if (n != -1 && n > 0) {
            return this.mTargets.get(n - 1);
        }
        return snapTarget;
    }

    public boolean isFirstSplitTargetAvailable() {
        boolean bl = this.mFirstSplitTarget != this.mMiddleTarget;
        return bl;
    }

    public boolean isLastSplitTargetAvailable() {
        boolean bl = this.mLastSplitTarget != this.mMiddleTarget;
        return bl;
    }

    public boolean isSplitScreenFeasible() {
        int n = this.mInsets.top;
        int n2 = this.mIsHorizontalDivision ? this.mInsets.bottom : this.mInsets.right;
        int n3 = this.mIsHorizontalDivision ? this.mDisplayHeight : this.mDisplayWidth;
        boolean bl = (n3 - n2 - n - this.mDividerSize) / 2 >= this.mMinimalSizeResizableTask;
        return bl;
    }

    public boolean showMiddleSplitTargetForAccessibility() {
        int n = this.mTargets.size();
        boolean bl = true;
        if (n - 2 <= 1) {
            bl = false;
        }
        return bl;
    }

    public static class SnapTarget {
        public static final int FLAG_DISMISS_END = 2;
        public static final int FLAG_DISMISS_START = 1;
        public static final int FLAG_NONE = 0;
        private final float distanceMultiplier;
        public final int flag;
        public boolean isMiddleTarget;
        public final int position;
        public final int taskPosition;

        public SnapTarget(int n, int n2, int n3) {
            this(n, n2, n3, 1.0f);
        }

        public SnapTarget(int n, int n2, int n3, float f) {
            this.position = n;
            this.taskPosition = n2;
            this.flag = n3;
            this.distanceMultiplier = f;
        }
    }

}

