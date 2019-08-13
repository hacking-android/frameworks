/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.util.Size;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SizeAreaComparator
implements Comparator<Size> {
    public static Size findLargestByArea(List<Size> list) {
        Preconditions.checkNotNull(list, "sizes must not be null");
        return Collections.max(list, new SizeAreaComparator());
    }

    @Override
    public int compare(Size size, Size size2) {
        Preconditions.checkNotNull(size, "size must not be null");
        Preconditions.checkNotNull(size2, "size2 must not be null");
        if (size.equals(size2)) {
            return 0;
        }
        long l = size.getWidth();
        long l2 = size2.getWidth();
        long l3 = (long)size.getHeight() * l;
        long l4 = (long)size2.getHeight() * l2;
        int n = 1;
        int n2 = 1;
        if (l3 == l4) {
            if (l <= l2) {
                n2 = -1;
            }
            return n2;
        }
        n2 = l3 > l4 ? n : -1;
        return n2;
    }
}

