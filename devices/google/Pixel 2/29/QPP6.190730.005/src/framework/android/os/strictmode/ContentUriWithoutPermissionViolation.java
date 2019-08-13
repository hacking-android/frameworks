/*
 * Decompiled with CFR 0.145.
 */
package android.os.strictmode;

import android.net.Uri;
import android.os.strictmode.Violation;

public final class ContentUriWithoutPermissionViolation
extends Violation {
    public ContentUriWithoutPermissionViolation(Uri uri, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uri);
        stringBuilder.append(" exposed beyond app through ");
        stringBuilder.append(string2);
        stringBuilder.append(" without permission grant flags; did you forget FLAG_GRANT_READ_URI_PERMISSION?");
        super(stringBuilder.toString());
    }
}

