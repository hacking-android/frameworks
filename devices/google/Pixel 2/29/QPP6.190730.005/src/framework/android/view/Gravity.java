/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;

public class Gravity {
    public static final int AXIS_CLIP = 8;
    public static final int AXIS_PULL_AFTER = 4;
    public static final int AXIS_PULL_BEFORE = 2;
    public static final int AXIS_SPECIFIED = 1;
    public static final int AXIS_X_SHIFT = 0;
    public static final int AXIS_Y_SHIFT = 4;
    public static final int BOTTOM = 80;
    public static final int CENTER = 17;
    public static final int CENTER_HORIZONTAL = 1;
    public static final int CENTER_VERTICAL = 16;
    public static final int CLIP_HORIZONTAL = 8;
    public static final int CLIP_VERTICAL = 128;
    public static final int DISPLAY_CLIP_HORIZONTAL = 16777216;
    public static final int DISPLAY_CLIP_VERTICAL = 268435456;
    public static final int END = 8388613;
    public static final int FILL = 119;
    public static final int FILL_HORIZONTAL = 7;
    public static final int FILL_VERTICAL = 112;
    public static final int HORIZONTAL_GRAVITY_MASK = 7;
    public static final int LEFT = 3;
    public static final int NO_GRAVITY = 0;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int RIGHT = 5;
    public static final int START = 8388611;
    public static final int TOP = 48;
    public static final int VERTICAL_GRAVITY_MASK = 112;

    public static void apply(int n, int n2, int n3, Rect rect, int n4, int n5, Rect rect2) {
        int n6 = n & 6;
        if (n6 != 0) {
            if (n6 != 2) {
                if (n6 != 4) {
                    rect2.left = rect.left + n4;
                    rect2.right = rect.right + n4;
                } else {
                    rect2.right = rect.right - n4;
                    rect2.left = rect2.right - n2;
                    if ((n & 8) == 8 && rect2.left < rect.left) {
                        rect2.left = rect.left;
                    }
                }
            } else {
                rect2.left = rect.left + n4;
                rect2.right = rect2.left + n2;
                if ((n & 8) == 8 && rect2.right > rect.right) {
                    rect2.right = rect.right;
                }
            }
        } else {
            rect2.left = rect.left + (rect.right - rect.left - n2) / 2 + n4;
            rect2.right = rect2.left + n2;
            if ((n & 8) == 8) {
                if (rect2.left < rect.left) {
                    rect2.left = rect.left;
                }
                if (rect2.right > rect.right) {
                    rect2.right = rect.right;
                }
            }
        }
        n2 = n & 96;
        if (n2 != 0) {
            if (n2 != 32) {
                if (n2 != 64) {
                    rect2.top = rect.top + n5;
                    rect2.bottom = rect.bottom + n5;
                } else {
                    rect2.bottom = rect.bottom - n5;
                    rect2.top = rect2.bottom - n3;
                    if ((n & 128) == 128 && rect2.top < rect.top) {
                        rect2.top = rect.top;
                    }
                }
            } else {
                rect2.top = rect.top + n5;
                rect2.bottom = rect2.top + n3;
                if ((n & 128) == 128 && rect2.bottom > rect.bottom) {
                    rect2.bottom = rect.bottom;
                }
            }
        } else {
            rect2.top = rect.top + (rect.bottom - rect.top - n3) / 2 + n5;
            rect2.bottom = rect2.top + n3;
            if ((n & 128) == 128) {
                if (rect2.top < rect.top) {
                    rect2.top = rect.top;
                }
                if (rect2.bottom > rect.bottom) {
                    rect2.bottom = rect.bottom;
                }
            }
        }
    }

    public static void apply(int n, int n2, int n3, Rect rect, int n4, int n5, Rect rect2, int n6) {
        Gravity.apply(Gravity.getAbsoluteGravity(n, n6), n2, n3, rect, n4, n5, rect2);
    }

    public static void apply(int n, int n2, int n3, Rect rect, Rect rect2) {
        Gravity.apply(n, n2, n3, rect, 0, 0, rect2);
    }

    public static void apply(int n, int n2, int n3, Rect rect, Rect rect2, int n4) {
        Gravity.apply(Gravity.getAbsoluteGravity(n, n4), n2, n3, rect, 0, 0, rect2);
    }

    public static void applyDisplay(int n, Rect rect, Rect rect2) {
        if ((268435456 & n) != 0) {
            if (rect2.top < rect.top) {
                rect2.top = rect.top;
            }
            if (rect2.bottom > rect.bottom) {
                rect2.bottom = rect.bottom;
            }
        } else {
            int n2 = 0;
            if (rect2.top < rect.top) {
                n2 = rect.top - rect2.top;
            } else if (rect2.bottom > rect.bottom) {
                n2 = rect.bottom - rect2.bottom;
            }
            if (n2 != 0) {
                if (rect2.height() > rect.bottom - rect.top) {
                    rect2.top = rect.top;
                    rect2.bottom = rect.bottom;
                } else {
                    rect2.top += n2;
                    rect2.bottom += n2;
                }
            }
        }
        if ((16777216 & n) != 0) {
            if (rect2.left < rect.left) {
                rect2.left = rect.left;
            }
            if (rect2.right > rect.right) {
                rect2.right = rect.right;
            }
        } else {
            n = 0;
            if (rect2.left < rect.left) {
                n = rect.left - rect2.left;
            } else if (rect2.right > rect.right) {
                n = rect.right - rect2.right;
            }
            if (n != 0) {
                if (rect2.width() > rect.right - rect.left) {
                    rect2.left = rect.left;
                    rect2.right = rect.right;
                } else {
                    rect2.left += n;
                    rect2.right += n;
                }
            }
        }
    }

    public static void applyDisplay(int n, Rect rect, Rect rect2, int n2) {
        Gravity.applyDisplay(Gravity.getAbsoluteGravity(n, n2), rect, rect2);
    }

    public static int getAbsoluteGravity(int n, int n2) {
        int n3;
        n = n3 = n;
        if ((8388608 & n3) > 0) {
            if ((n3 & 8388611) == 8388611) {
                n = n3 & -8388612;
                n = n2 == 1 ? (n |= 5) : (n |= 3);
            } else {
                n = n3;
                if ((n3 & 8388613) == 8388613) {
                    n = n3 & -8388614;
                    n = n2 == 1 ? (n |= 3) : (n |= 5);
                }
            }
            n &= -8388609;
        }
        return n;
    }

    public static boolean isHorizontal(int n) {
        boolean bl = n > 0 && (8388615 & n) != 0;
        return bl;
    }

    public static boolean isVertical(int n) {
        boolean bl = n > 0 && (n & 112) != 0;
        return bl;
    }

    public static String toString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 119) == 119) {
            stringBuilder.append("FILL");
            stringBuilder.append(' ');
        } else {
            if ((n & 112) == 112) {
                stringBuilder.append("FILL_VERTICAL");
                stringBuilder.append(' ');
            } else {
                if ((n & 48) == 48) {
                    stringBuilder.append("TOP");
                    stringBuilder.append(' ');
                }
                if ((n & 80) == 80) {
                    stringBuilder.append("BOTTOM");
                    stringBuilder.append(' ');
                }
            }
            if ((n & 7) == 7) {
                stringBuilder.append("FILL_HORIZONTAL");
                stringBuilder.append(' ');
            } else {
                if ((n & 8388611) == 8388611) {
                    stringBuilder.append("START");
                    stringBuilder.append(' ');
                } else if ((n & 3) == 3) {
                    stringBuilder.append("LEFT");
                    stringBuilder.append(' ');
                }
                if ((n & 8388613) == 8388613) {
                    stringBuilder.append("END");
                    stringBuilder.append(' ');
                } else if ((n & 5) == 5) {
                    stringBuilder.append("RIGHT");
                    stringBuilder.append(' ');
                }
            }
        }
        if ((n & 17) == 17) {
            stringBuilder.append("CENTER");
            stringBuilder.append(' ');
        } else {
            if ((n & 16) == 16) {
                stringBuilder.append("CENTER_VERTICAL");
                stringBuilder.append(' ');
            }
            if ((n & 1) == 1) {
                stringBuilder.append("CENTER_HORIZONTAL");
                stringBuilder.append(' ');
            }
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append("NO GRAVITY");
            stringBuilder.append(' ');
        }
        if ((n & 268435456) == 268435456) {
            stringBuilder.append("DISPLAY_CLIP_VERTICAL");
            stringBuilder.append(' ');
        }
        if ((n & 16777216) == 16777216) {
            stringBuilder.append("DISPLAY_CLIP_HORIZONTAL");
            stringBuilder.append(' ');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}

