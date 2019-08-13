/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;

public abstract class MetaKeyKeyListener {
    private static final Object ALT;
    private static final Object CAP;
    private static final int LOCKED = 67108881;
    private static final int LOCKED_RETURN_VALUE = 2;
    public static final int META_ALT_LOCKED = 512;
    private static final long META_ALT_MASK = 0x2020200000202L;
    public static final int META_ALT_ON = 2;
    private static final long META_ALT_PRESSED = 0x20000000000L;
    private static final long META_ALT_RELEASED = 0x2000000000000L;
    private static final long META_ALT_USED = 0x200000000L;
    public static final int META_CAP_LOCKED = 256;
    private static final long META_CAP_PRESSED = 0x10000000000L;
    private static final long META_CAP_RELEASED = 0x1000000000000L;
    private static final long META_CAP_USED = 0x100000000L;
    public static final int META_SELECTING = 2048;
    private static final long META_SHIFT_MASK = 0x1010100000101L;
    public static final int META_SHIFT_ON = 1;
    public static final int META_SYM_LOCKED = 1024;
    private static final long META_SYM_MASK = 0x4040400000404L;
    public static final int META_SYM_ON = 4;
    private static final long META_SYM_PRESSED = 0x40000000000L;
    private static final long META_SYM_RELEASED = 0x4000000000000L;
    private static final long META_SYM_USED = 0x400000000L;
    private static final int PRESSED = 16777233;
    private static final int PRESSED_RETURN_VALUE = 1;
    private static final int RELEASED = 33554449;
    private static final Object SELECTING;
    private static final Object SYM;
    private static final int USED = 50331665;

    static {
        CAP = new NoCopySpan.Concrete();
        ALT = new NoCopySpan.Concrete();
        SYM = new NoCopySpan.Concrete();
        SELECTING = new NoCopySpan.Concrete();
    }

    private static void adjust(Spannable spannable, Object object) {
        int n = spannable.getSpanFlags(object);
        if (n == 16777233) {
            spannable.setSpan(object, 0, 0, 50331665);
        } else if (n == 33554449) {
            spannable.removeSpan(object);
        }
    }

    public static long adjustMetaAfterKeypress(long l) {
        long l2;
        if ((0x10000000000L & l) != 0L) {
            l2 = l & -282578783305986L | 1L | 0x100000000L;
        } else {
            l2 = l;
            if ((0x1000000000000L & l) != 0L) {
                l2 = l & -282578783305986L;
            }
        }
        if ((0x20000000000L & l2) != 0L) {
            l = l2 & -565157566611971L | 2L | 0x200000000L;
        } else {
            l = l2;
            if ((0x2000000000000L & l2) != 0L) {
                l = l2 & -565157566611971L;
            }
        }
        if ((0x40000000000L & l) != 0L) {
            l2 = l & -1130315133223941L | 4L | 0x400000000L;
        } else {
            l2 = l;
            if ((0x4000000000000L & l) != 0L) {
                l2 = l & -1130315133223941L;
            }
        }
        return l2;
    }

    public static void adjustMetaAfterKeypress(Spannable spannable) {
        MetaKeyKeyListener.adjust(spannable, CAP);
        MetaKeyKeyListener.adjust(spannable, ALT);
        MetaKeyKeyListener.adjust(spannable, SYM);
    }

    public static void clearMetaKeyState(Editable editable, int n) {
        if ((n & 1) != 0) {
            editable.removeSpan(CAP);
        }
        if ((n & 2) != 0) {
            editable.removeSpan(ALT);
        }
        if ((n & 4) != 0) {
            editable.removeSpan(SYM);
        }
        if ((n & 2048) != 0) {
            editable.removeSpan(SELECTING);
        }
    }

    private static int getActive(CharSequence charSequence, Object object, int n, int n2) {
        if (!(charSequence instanceof Spanned)) {
            return 0;
        }
        int n3 = ((Spanned)charSequence).getSpanFlags(object);
        if (n3 == 67108881) {
            return n2;
        }
        if (n3 != 0) {
            return n;
        }
        return 0;
    }

    public static final int getMetaState(long l) {
        int n;
        int n2 = 0;
        if ((256L & l) != 0L) {
            n2 = 0 | 256;
        } else if ((1L & l) != 0L) {
            n2 = false | true;
        }
        if ((512L & l) != 0L) {
            n = n2 | 512;
        } else {
            n = n2;
            if ((2L & l) != 0L) {
                n = n2 | 2;
            }
        }
        if ((1024L & l) != 0L) {
            n2 = n | 1024;
        } else {
            n2 = n;
            if ((4L & l) != 0L) {
                n2 = n | 4;
            }
        }
        return n2;
    }

    public static final int getMetaState(long l, int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    return 0;
                }
                if ((1024L & l) != 0L) {
                    return 2;
                }
                return (4L & l) != 0L;
            }
            if ((512L & l) != 0L) {
                return 2;
            }
            return (2L & l) != 0L;
        }
        if ((256L & l) != 0L) {
            return 2;
        }
        return (1L & l) != 0L;
    }

    public static final int getMetaState(CharSequence charSequence) {
        return MetaKeyKeyListener.getActive(charSequence, CAP, 1, 256) | MetaKeyKeyListener.getActive(charSequence, ALT, 2, 512) | MetaKeyKeyListener.getActive(charSequence, SYM, 4, 1024) | MetaKeyKeyListener.getActive(charSequence, SELECTING, 2048, 2048);
    }

    public static final int getMetaState(CharSequence charSequence, int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 2048) {
                        return 0;
                    }
                    return MetaKeyKeyListener.getActive(charSequence, SELECTING, 1, 2);
                }
                return MetaKeyKeyListener.getActive(charSequence, SYM, 1, 2);
            }
            return MetaKeyKeyListener.getActive(charSequence, ALT, 1, 2);
        }
        return MetaKeyKeyListener.getActive(charSequence, CAP, 1, 2);
    }

    public static final int getMetaState(CharSequence charSequence, int n, KeyEvent keyEvent) {
        int n2;
        int n3 = n2 = keyEvent.getMetaState();
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1) {
            n3 = n2 | MetaKeyKeyListener.getMetaState(charSequence);
        }
        if (2048 == n) {
            return (n3 & 2048) != 0;
        }
        return MetaKeyKeyListener.getMetaState(n3, n);
    }

    public static final int getMetaState(CharSequence charSequence, KeyEvent keyEvent) {
        int n;
        int n2 = n = keyEvent.getMetaState();
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1) {
            n2 = n | MetaKeyKeyListener.getMetaState(charSequence);
        }
        return n2;
    }

    public static long handleKeyDown(long l, int n, KeyEvent keyEvent) {
        if (n != 59 && n != 60) {
            if (n != 57 && n != 58 && n != 78) {
                if (n == 63) {
                    return MetaKeyKeyListener.press(l, 4, 0x4040400000404L, 1024L, 0x40000000000L, 0x4000000000000L, 0x400000000L);
                }
                return l;
            }
            return MetaKeyKeyListener.press(l, 2, 0x2020200000202L, 512L, 0x20000000000L, 0x2000000000000L, 0x200000000L);
        }
        return MetaKeyKeyListener.press(l, 1, 0x1010100000101L, 256L, 0x10000000000L, 0x1000000000000L, 0x100000000L);
    }

    public static long handleKeyUp(long l, int n, KeyEvent keyEvent) {
        if (n != 59 && n != 60) {
            if (n != 57 && n != 58 && n != 78) {
                if (n == 63) {
                    return MetaKeyKeyListener.release(l, 4, 0x4040400000404L, 0x40000000000L, 0x4000000000000L, 0x400000000L, keyEvent);
                }
                return l;
            }
            return MetaKeyKeyListener.release(l, 2, 0x2020200000202L, 0x20000000000L, 0x2000000000000L, 0x200000000L, keyEvent);
        }
        return MetaKeyKeyListener.release(l, 1, 0x1010100000101L, 0x10000000000L, 0x1000000000000L, 0x100000000L, keyEvent);
    }

    public static boolean isMetaTracker(CharSequence charSequence, Object object) {
        boolean bl = object == CAP || object == ALT || object == SYM || object == SELECTING;
        return bl;
    }

    public static boolean isSelectingMetaTracker(CharSequence charSequence, Object object) {
        boolean bl = object == SELECTING;
        return bl;
    }

    private static long press(long l, int n, long l2, long l3, long l4, long l5, long l6) {
        if ((l & l4) == 0L) {
            if ((l & l5) != 0L) {
                l = l2 & l | (long)n | l3;
            } else if ((l & l6) == 0L) {
                l = (l & l3) != 0L ? l2 & l : (long)n | l4 | l;
            }
        }
        return l;
    }

    private void press(Editable editable, Object object) {
        int n = editable.getSpanFlags(object);
        if (n != 16777233) {
            if (n == 33554449) {
                editable.setSpan(object, 0, 0, 67108881);
            } else if (n != 50331665) {
                if (n == 67108881) {
                    editable.removeSpan(object);
                } else {
                    editable.setSpan(object, 0, 0, 16777233);
                }
            }
        }
    }

    private static long release(long l, int n, long l2, long l3, long l4, long l5, KeyEvent keyEvent) {
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() != 1) {
            l2 = l & l2;
        } else if ((l & l5) != 0L) {
            l2 = l & l2;
        } else {
            l2 = l;
            if ((l & l3) != 0L) {
                l2 = l | ((long)n | l4);
            }
        }
        return l2;
    }

    private void release(Editable editable, Object object, KeyEvent keyEvent) {
        int n = editable.getSpanFlags(object);
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() != 1) {
            editable.removeSpan(object);
        } else if (n == 50331665) {
            editable.removeSpan(object);
        } else if (n == 16777233) {
            editable.setSpan(object, 0, 0, 33554449);
        }
    }

    private static void resetLock(Spannable spannable, Object object) {
        if (spannable.getSpanFlags(object) == 67108881) {
            spannable.removeSpan(object);
        }
    }

    public static long resetLockedMeta(long l) {
        long l2 = l;
        if ((256L & l) != 0L) {
            l2 = l & -282578783305986L;
        }
        l = l2;
        if ((512L & l2) != 0L) {
            l = l2 & -565157566611971L;
        }
        l2 = l;
        if ((1024L & l) != 0L) {
            l2 = l & -1130315133223941L;
        }
        return l2;
    }

    protected static void resetLockedMeta(Spannable spannable) {
        MetaKeyKeyListener.resetLock(spannable, CAP);
        MetaKeyKeyListener.resetLock(spannable, ALT);
        MetaKeyKeyListener.resetLock(spannable, SYM);
        MetaKeyKeyListener.resetLock(spannable, SELECTING);
    }

    public static void resetMetaState(Spannable spannable) {
        spannable.removeSpan(CAP);
        spannable.removeSpan(ALT);
        spannable.removeSpan(SYM);
        spannable.removeSpan(SELECTING);
    }

    @UnsupportedAppUsage
    public static void startSelecting(View view, Spannable spannable) {
        spannable.setSpan(SELECTING, 0, 0, 16777233);
    }

    @UnsupportedAppUsage
    public static void stopSelecting(View view, Spannable spannable) {
        spannable.removeSpan(SELECTING);
    }

    public long clearMetaKeyState(long l, int n) {
        long l2 = l;
        if ((n & 1) != 0) {
            l2 = l;
            if ((256L & l) != 0L) {
                l2 = l & -282578783305986L;
            }
        }
        l = l2;
        if ((n & 2) != 0) {
            l = l2;
            if ((512L & l2) != 0L) {
                l = l2 & -565157566611971L;
            }
        }
        l2 = l;
        if ((n & 4) != 0) {
            l2 = l;
            if ((1024L & l) != 0L) {
                l2 = l & -1130315133223941L;
            }
        }
        return l2;
    }

    public void clearMetaKeyState(View view, Editable editable, int n) {
        MetaKeyKeyListener.clearMetaKeyState(editable, n);
    }

    public boolean onKeyDown(View view, Editable editable, int n, KeyEvent keyEvent) {
        if (n != 59 && n != 60) {
            if (n != 57 && n != 58 && n != 78) {
                if (n == 63) {
                    this.press(editable, SYM);
                    return true;
                }
                return false;
            }
            this.press(editable, ALT);
            return true;
        }
        this.press(editable, CAP);
        return true;
    }

    public boolean onKeyUp(View view, Editable editable, int n, KeyEvent keyEvent) {
        if (n != 59 && n != 60) {
            if (n != 57 && n != 58 && n != 78) {
                if (n == 63) {
                    this.release(editable, SYM, keyEvent);
                    return true;
                }
                return false;
            }
            this.release(editable, ALT, keyEvent);
            return true;
        }
        this.release(editable, CAP, keyEvent);
        return true;
    }
}

