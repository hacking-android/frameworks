/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.net.Uri;
import java.util.List;

public class ContentUris {
    public static Uri.Builder appendId(Uri.Builder builder, long l) {
        return builder.appendEncodedPath(String.valueOf(l));
    }

    public static long parseId(Uri object) {
        long l = (object = ((Uri)object).getLastPathSegment()) == null ? -1L : Long.parseLong((String)object);
        return l;
    }

    public static Uri removeId(Uri object) {
        Object object2 = ((Uri)object).getLastPathSegment();
        if (object2 != null) {
            Long.parseLong((String)object2);
            object2 = ((Uri)object).getPathSegments();
            object = ((Uri)object).buildUpon();
            ((Uri.Builder)object).path((String)null);
            for (int i = 0; i < object2.size() - 1; ++i) {
                ((Uri.Builder)object).appendPath((String)object2.get(i));
            }
            return ((Uri.Builder)object).build();
        }
        throw new IllegalArgumentException("No path segments to remove");
    }

    public static Uri withAppendedId(Uri uri, long l) {
        return ContentUris.appendId(uri.buildUpon(), l).build();
    }
}

