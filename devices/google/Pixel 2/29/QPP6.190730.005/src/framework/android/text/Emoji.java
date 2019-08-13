/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 */
package android.text;

import android.icu.lang.UCharacter;

public class Emoji {
    public static int CANCEL_TAG;
    public static int COMBINING_ENCLOSING_KEYCAP;
    public static int VARIATION_SELECTOR_16;
    public static int ZERO_WIDTH_JOINER;

    static {
        COMBINING_ENCLOSING_KEYCAP = 8419;
        ZERO_WIDTH_JOINER = 8205;
        VARIATION_SELECTOR_16 = 65039;
        CANCEL_TAG = 917631;
    }

    public static boolean isEmoji(int n) {
        boolean bl = Emoji.isNewEmoji(n) || UCharacter.hasBinaryProperty((int)n, (int)57);
        return bl;
    }

    public static boolean isEmojiModifier(int n) {
        return UCharacter.hasBinaryProperty((int)n, (int)59);
    }

    public static boolean isEmojiModifierBase(int n) {
        if (n != 129309 && n != 129340) {
            if (129461 <= n && n <= 129462 || 129464 <= n && n <= 129465) {
                return true;
            }
            return UCharacter.hasBinaryProperty((int)n, (int)60);
        }
        return true;
    }

    public static boolean isKeycapBase(int n) {
        boolean bl = 48 <= n && n <= 57 || n == 35 || n == 42;
        return bl;
    }

    public static boolean isNewEmoji(int n) {
        block2 : {
            boolean bl;
            block4 : {
                block3 : {
                    boolean bl2 = false;
                    if (n < 128725 || n > 129685) break block2;
                    if (n == 128725 || n == 128762 || n == 129343 || n == 129393 || n == 129403 || 128992 <= n && n <= 129003 || 129293 <= n && n <= 129295 || 129445 <= n && n <= 129450 || 129454 <= n && n <= 129455 || 129466 <= n && n <= 129471 || 129475 <= n && n <= 129482 || 129485 <= n && n <= 129487 || 129648 <= n && n <= 129651 || 129656 <= n && n <= 129658 || 129664 <= n && n <= 129666) break block3;
                    bl = bl2;
                    if (129680 > n) break block4;
                    bl = bl2;
                    if (n > 129685) break block4;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static boolean isRegionalIndicatorSymbol(int n) {
        boolean bl = 127462 <= n && n <= 127487;
        return bl;
    }

    public static boolean isTagSpecChar(int n) {
        boolean bl = 917536 <= n && n <= 917630;
        return bl;
    }
}

