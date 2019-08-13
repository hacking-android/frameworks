/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

import android.icu.lang.UCharacterEnums;

public final class UCharacterDirection
implements UCharacterEnums.ECharacterDirection {
    private UCharacterDirection() {
    }

    public static String toString(int n) {
        switch (n) {
            default: {
                return "Unassigned";
            }
            case 22: {
                return "Pop Directional Isolate";
            }
            case 21: {
                return "Right-to-Left Isolate";
            }
            case 20: {
                return "Left-to-Right Isolate";
            }
            case 19: {
                return "First Strong Isolate";
            }
            case 18: {
                return "Boundary Neutral";
            }
            case 17: {
                return "Non-Spacing Mark";
            }
            case 16: {
                return "Pop Directional Format";
            }
            case 15: {
                return "Right-to-Left Override";
            }
            case 14: {
                return "Right-to-Left Embedding";
            }
            case 13: {
                return "Right-to-Left Arabic";
            }
            case 12: {
                return "Left-to-Right Override";
            }
            case 11: {
                return "Left-to-Right Embedding";
            }
            case 10: {
                return "Other Neutrals";
            }
            case 9: {
                return "Whitespace";
            }
            case 8: {
                return "Segment Separator";
            }
            case 7: {
                return "Paragraph Separator";
            }
            case 6: {
                return "Common Number Separator";
            }
            case 5: {
                return "Arabic Number";
            }
            case 4: {
                return "European Number Terminator";
            }
            case 3: {
                return "European Number Separator";
            }
            case 2: {
                return "European Number";
            }
            case 1: {
                return "Right-to-Left";
            }
            case 0: 
        }
        return "Left-to-Right";
    }
}

