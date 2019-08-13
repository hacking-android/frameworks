/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;

public class ExternalRingtonesCursorWrapper
extends CursorWrapper {
    private Uri mUri;

    public ExternalRingtonesCursorWrapper(Cursor cursor, Uri uri) {
        super(cursor);
        this.mUri = uri;
    }

    @Override
    public String getString(int n) {
        if (n == 2) {
            return this.mUri.toString();
        }
        return super.getString(n);
    }
}

