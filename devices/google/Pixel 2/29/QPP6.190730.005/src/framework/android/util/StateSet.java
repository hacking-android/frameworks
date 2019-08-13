/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import com.android.internal.R;

public class StateSet {
    public static final int[] NOTHING;
    public static final int VIEW_STATE_ACCELERATED = 64;
    public static final int VIEW_STATE_ACTIVATED = 32;
    public static final int VIEW_STATE_DRAG_CAN_ACCEPT = 256;
    public static final int VIEW_STATE_DRAG_HOVERED = 512;
    public static final int VIEW_STATE_ENABLED = 8;
    public static final int VIEW_STATE_FOCUSED = 4;
    public static final int VIEW_STATE_HOVERED = 128;
    static final int[] VIEW_STATE_IDS;
    public static final int VIEW_STATE_PRESSED = 16;
    public static final int VIEW_STATE_SELECTED = 2;
    private static final int[][] VIEW_STATE_SETS;
    public static final int VIEW_STATE_WINDOW_FOCUSED = 1;
    public static final int[] WILD_CARD;

    static {
        VIEW_STATE_IDS = new int[]{16842909, 1, 16842913, 2, 16842908, 4, 16842910, 8, 16842919, 16, 16843518, 32, 16843547, 64, 16843623, 128, 16843624, 256, 16843625, 512};
        if (VIEW_STATE_IDS.length / 2 == R.styleable.ViewDrawableStates.length) {
            int n;
            int[] arrn;
            int n2;
            int n3;
            int[] arrn2 = new int[VIEW_STATE_IDS.length];
            for (n3 = 0; n3 < R.styleable.ViewDrawableStates.length; ++n3) {
                n = R.styleable.ViewDrawableStates[n3];
                for (n2 = 0; n2 < (arrn = VIEW_STATE_IDS).length; n2 += 2) {
                    if (arrn[n2] != n) continue;
                    arrn2[n3 * 2] = n;
                    arrn2[n3 * 2 + 1] = arrn[n2 + 1];
                }
            }
            VIEW_STATE_SETS = new int[1 << VIEW_STATE_IDS.length / 2][];
            for (n3 = 0; n3 < VIEW_STATE_SETS.length; ++n3) {
                arrn = new int[Integer.bitCount(n3)];
                n = 0;
                for (n2 = 0; n2 < arrn2.length; n2 += 2) {
                    int n4 = n;
                    if ((arrn2[n2 + 1] & n3) != 0) {
                        arrn[n] = arrn2[n2];
                        n4 = n + 1;
                    }
                    n = n4;
                }
                StateSet.VIEW_STATE_SETS[n3] = arrn;
            }
            WILD_CARD = new int[0];
            NOTHING = new int[]{0};
            return;
        }
        throw new IllegalStateException("VIEW_STATE_IDs array length does not match ViewDrawableStates style array");
    }

    public static boolean containsAttribute(int[][] arrn, int n) {
        if (arrn != null) {
            for (int[] arrn2 : arrn) {
                if (arrn2 == null) break;
                int n2 = arrn2.length;
                for (int i = 0; i < n2; ++i) {
                    int n3 = arrn2[i];
                    if (n3 != n && -n3 != n) {
                        continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static String dump(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrn.length;
        block9 : for (int i = 0; i < n; ++i) {
            switch (arrn[i]) {
                default: {
                    continue block9;
                }
                case 16843518: {
                    stringBuilder.append("A ");
                    continue block9;
                }
                case 16842919: {
                    stringBuilder.append("P ");
                    continue block9;
                }
                case 16842913: {
                    stringBuilder.append("S ");
                    continue block9;
                }
                case 16842912: {
                    stringBuilder.append("C ");
                    continue block9;
                }
                case 16842910: {
                    stringBuilder.append("E ");
                    continue block9;
                }
                case 16842909: {
                    stringBuilder.append("W ");
                    continue block9;
                }
                case 16842908: {
                    stringBuilder.append("F ");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static int[] get(int n) {
        int[][] arrn = VIEW_STATE_SETS;
        if (n < arrn.length) {
            return arrn[n];
        }
        throw new IllegalArgumentException("Invalid state set mask");
    }

    public static boolean isWildCard(int[] arrn) {
        int n = arrn.length;
        boolean bl = false;
        if (n == 0 || arrn[0] == 0) {
            bl = true;
        }
        return bl;
    }

    public static boolean stateSetMatches(int[] arrn, int n) {
        for (int n2 : arrn) {
            if (n2 == 0) {
                return true;
            }
            if (!(n2 > 0 ? n != n2 : n == -n2)) continue;
            return false;
        }
        return true;
    }

    public static boolean stateSetMatches(int[] arrn, int[] arrn2) {
        boolean bl = false;
        if (arrn2 == null) {
            if (arrn == null || StateSet.isWildCard(arrn)) {
                bl = true;
            }
            return bl;
        }
        int n = arrn.length;
        int n2 = arrn2.length;
        for (int i = 0; i < n; ++i) {
            boolean bl2;
            int n3;
            int n4 = arrn[i];
            if (n4 == 0) {
                return true;
            }
            if (n4 > 0) {
                bl2 = true;
            } else {
                bl2 = false;
                n4 = -n4;
            }
            int n5 = 0;
            int n6 = 0;
            do {
                n3 = n5;
                if (n6 >= n2) break;
                n3 = arrn2[n6];
                if (n3 == 0) {
                    n3 = n5;
                    if (!bl2) break;
                    return false;
                }
                if (n3 == n4) {
                    if (bl2) {
                        n3 = 1;
                        break;
                    }
                    return false;
                }
                ++n6;
            } while (true);
            if (!bl2 || n3 != 0) continue;
            return false;
        }
        return true;
    }

    public static int[] trimStateSet(int[] arrn, int n) {
        if (arrn.length == n) {
            return arrn;
        }
        int[] arrn2 = new int[n];
        System.arraycopy(arrn, 0, arrn2, 0, n);
        return arrn2;
    }
}

