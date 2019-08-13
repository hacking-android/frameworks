/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.Cursor;
import android.widget.Filter;

class CursorFilter
extends Filter {
    CursorFilterClient mClient;

    CursorFilter(CursorFilterClient cursorFilterClient) {
        this.mClient = cursorFilterClient;
    }

    @Override
    public CharSequence convertResultToString(Object object) {
        return this.mClient.convertToString((Cursor)object);
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence object) {
        Cursor cursor = this.mClient.runQueryOnBackgroundThread((CharSequence)object);
        object = new Filter.FilterResults();
        if (cursor != null) {
            ((Filter.FilterResults)object).count = cursor.getCount();
            ((Filter.FilterResults)object).values = cursor;
        } else {
            ((Filter.FilterResults)object).count = 0;
            ((Filter.FilterResults)object).values = null;
        }
        return object;
    }

    @Override
    protected void publishResults(CharSequence object, Filter.FilterResults filterResults) {
        object = this.mClient.getCursor();
        if (filterResults.values != null && filterResults.values != object) {
            this.mClient.changeCursor((Cursor)filterResults.values);
        }
    }

    static interface CursorFilterClient {
        public void changeCursor(Cursor var1);

        public CharSequence convertToString(Cursor var1);

        public Cursor getCursor();

        public Cursor runQueryOnBackgroundThread(CharSequence var1);
    }

}

