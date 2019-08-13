/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.Context;
import android.util.Log;

class LegacyErrorStrings {
    private static final String LOGTAG = "Http";

    private LegacyErrorStrings() {
    }

    private static int getResource(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Using generic message for unknown error code: ");
                stringBuilder.append(n);
                Log.w(LOGTAG, stringBuilder.toString());
                return 17040091;
            }
            case 0: {
                return 17040099;
            }
            case -1: {
                return 17040091;
            }
            case -2: {
                return 17040098;
            }
            case -3: {
                return 17040104;
            }
            case -4: {
                return 17040092;
            }
            case -5: {
                return 17040100;
            }
            case -6: {
                return 17040093;
            }
            case -7: {
                return 17040097;
            }
            case -8: {
                return 17040102;
            }
            case -9: {
                return 17040101;
            }
            case -10: {
                return 17039368;
            }
            case -11: {
                return 17040094;
            }
            case -12: {
                return 17039367;
            }
            case -13: {
                return 17040095;
            }
            case -14: {
                return 17040096;
            }
            case -15: 
        }
        return 17040103;
    }

    static String getString(int n, Context context) {
        return context.getText(LegacyErrorStrings.getResource(n)).toString();
    }
}

