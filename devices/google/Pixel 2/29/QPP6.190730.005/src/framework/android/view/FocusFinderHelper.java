/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.view.FocusFinder;

public class FocusFinderHelper {
    private FocusFinder mFocusFinder;

    public FocusFinderHelper(FocusFinder focusFinder) {
        this.mFocusFinder = focusFinder;
    }

    public static int majorAxisDistance(int n, Rect rect, Rect rect2) {
        return FocusFinder.majorAxisDistance(n, rect, rect2);
    }

    public static int majorAxisDistanceToFarEdge(int n, Rect rect, Rect rect2) {
        return FocusFinder.majorAxisDistanceToFarEdge(n, rect, rect2);
    }

    public boolean beamBeats(int n, Rect rect, Rect rect2, Rect rect3) {
        return this.mFocusFinder.beamBeats(n, rect, rect2, rect3);
    }

    public boolean beamsOverlap(int n, Rect rect, Rect rect2) {
        return this.mFocusFinder.beamsOverlap(n, rect, rect2);
    }

    public boolean isBetterCandidate(int n, Rect rect, Rect rect2, Rect rect3) {
        return this.mFocusFinder.isBetterCandidate(n, rect, rect2, rect3);
    }

    public boolean isCandidate(Rect rect, Rect rect2, int n) {
        return this.mFocusFinder.isCandidate(rect, rect2, n);
    }
}

