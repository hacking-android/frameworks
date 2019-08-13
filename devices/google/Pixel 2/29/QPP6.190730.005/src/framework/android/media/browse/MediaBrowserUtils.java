/*
 * Decompiled with CFR 0.145.
 */
package android.media.browse;

import android.os.Bundle;

public class MediaBrowserUtils {
    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null) {
            if (bundle2.getInt("android.media.browse.extra.PAGE", -1) != -1 || bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) != -1) {
                bl3 = false;
            }
            return bl3;
        }
        if (bundle2 == null) {
            bl3 = bundle.getInt("android.media.browse.extra.PAGE", -1) == -1 && bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1 ? bl : false;
            return bl3;
        }
        bl3 = bundle.getInt("android.media.browse.extra.PAGE", -1) == bundle2.getInt("android.media.browse.extra.PAGE", -1) && bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) == bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) ? bl2 : false;
        return bl3;
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        int n = bundle == null ? -1 : bundle.getInt("android.media.browse.extra.PAGE", -1);
        int n2 = bundle2 == null ? -1 : bundle2.getInt("android.media.browse.extra.PAGE", -1);
        int n3 = bundle == null ? -1 : bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        int n4 = bundle2 == null ? -1 : bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (n != -1 && n3 != -1) {
            n = n3 * n;
            n3 = n + n3 - 1;
        } else {
            n = 0;
            n3 = Integer.MAX_VALUE;
        }
        if (n2 != -1 && n4 != -1) {
            n2 = n4 * n2;
            n4 = n2 + n4 - 1;
        } else {
            n2 = 0;
            n4 = Integer.MAX_VALUE;
        }
        if (n <= n2 && n2 <= n3) {
            return true;
        }
        return n <= n4 && n4 <= n3;
    }
}

