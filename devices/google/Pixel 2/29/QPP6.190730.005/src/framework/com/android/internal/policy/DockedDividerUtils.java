/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.graphics.Rect;

public class DockedDividerUtils {
    public static void calculateBoundsForPosition(int n, int n2, Rect rect, int n3, int n4, int n5) {
        boolean bl = false;
        rect.set(0, 0, n3, n4);
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 == 4) {
                        rect.top = n + n5;
                    }
                } else {
                    rect.left = n + n5;
                }
            } else {
                rect.bottom = n;
            }
        } else {
            rect.right = n;
        }
        if (n2 == 1 || n2 == 2) {
            bl = true;
        }
        DockedDividerUtils.sanitizeStackBounds(rect, bl);
    }

    public static int calculateMiddlePosition(boolean bl, Rect rect, int n, int n2, int n3) {
        int n4 = bl ? rect.top : rect.left;
        n = bl ? n2 - rect.bottom : (n -= rect.right);
        return (n - n4) / 2 + n4 - n3 / 2;
    }

    public static int calculatePositionForBounds(Rect rect, int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return 0;
                    }
                    return rect.top - n2;
                }
                return rect.left - n2;
            }
            return rect.bottom;
        }
        return rect.right;
    }

    public static int getDockSideFromCreatedMode(boolean bl, boolean bl2) {
        if (bl) {
            if (bl2) {
                return 2;
            }
            return 1;
        }
        if (bl2) {
            return 4;
        }
        return 3;
    }

    public static int invertDockSide(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return -1;
                    }
                    return 2;
                }
                return 1;
            }
            return 4;
        }
        return 3;
    }

    public static void sanitizeStackBounds(Rect rect, boolean bl) {
        if (bl) {
            if (rect.left >= rect.right) {
                rect.left = rect.right - 1;
            }
            if (rect.top >= rect.bottom) {
                rect.top = rect.bottom - 1;
            }
        } else {
            if (rect.right <= rect.left) {
                rect.right = rect.left + 1;
            }
            if (rect.bottom <= rect.top) {
                rect.bottom = rect.top + 1;
            }
        }
    }
}

