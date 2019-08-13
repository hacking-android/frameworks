/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
public class NetworkBadging {
    public static final int BADGING_4K = 30;
    public static final int BADGING_HD = 20;
    public static final int BADGING_NONE = 0;
    public static final int BADGING_SD = 10;

    private NetworkBadging() {
    }

    public static Drawable getWifiIcon(int n, int n2, Resources.Theme theme) {
        return Resources.getSystem().getDrawable(NetworkBadging.getWifiSignalResource(n), theme);
    }

    private static int getWifiSignalResource(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            return 17302849;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid signal level: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return 17302848;
                }
                return 17302847;
            }
            return 17302846;
        }
        return 17302845;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Badging {
    }

}

