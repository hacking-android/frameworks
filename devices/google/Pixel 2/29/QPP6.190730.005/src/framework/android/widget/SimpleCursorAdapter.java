/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class SimpleCursorAdapter
extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected int[] mFrom;
    String[] mOriginalFrom;
    private int mStringConversionColumn = -1;
    @UnsupportedAppUsage
    protected int[] mTo;
    private ViewBinder mViewBinder;

    @Deprecated
    public SimpleCursorAdapter(Context context, int n, Cursor cursor, String[] arrstring, int[] arrn) {
        super(context, n, cursor);
        this.mTo = arrn;
        this.mOriginalFrom = arrstring;
        this.findColumns(cursor, arrstring);
    }

    public SimpleCursorAdapter(Context context, int n, Cursor cursor, String[] arrstring, int[] arrn, int n2) {
        super(context, n, cursor, n2);
        this.mTo = arrn;
        this.mOriginalFrom = arrstring;
        this.findColumns(cursor, arrstring);
    }

    private void findColumns(Cursor cursor, String[] arrstring) {
        if (cursor != null) {
            int n = arrstring.length;
            int[] arrn = this.mFrom;
            if (arrn == null || arrn.length != n) {
                this.mFrom = new int[n];
            }
            for (int i = 0; i < n; ++i) {
                this.mFrom[i] = cursor.getColumnIndexOrThrow(arrstring[i]);
            }
        } else {
            this.mFrom = null;
        }
    }

    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        ViewBinder viewBinder = this.mViewBinder;
        int n = this.mTo.length;
        int[] arrn = this.mFrom;
        int[] arrn2 = this.mTo;
        for (int i = 0; i < n; ++i) {
            Object t = ((View)object).findViewById(arrn2[i]);
            if (t == null) continue;
            boolean bl = false;
            if (viewBinder != null) {
                bl = viewBinder.setViewValue((View)t, cursor, arrn[i]);
            }
            if (bl) continue;
            String string2 = cursor.getString(arrn[i]);
            object2 = string2;
            if (string2 == null) {
                object2 = "";
            }
            if (t instanceof TextView) {
                this.setViewText((TextView)t, (String)object2);
                continue;
            }
            if (t instanceof ImageView) {
                this.setViewImage((ImageView)t, (String)object2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(t.getClass().getName());
            ((StringBuilder)object).append(" is not a  view that can be bounds by this SimpleCursorAdapter");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
    }

    public void changeCursorAndColumns(Cursor cursor, String[] arrstring, int[] arrn) {
        this.mOriginalFrom = arrstring;
        this.mTo = arrn;
        this.findColumns(cursor, this.mOriginalFrom);
        super.changeCursor(cursor);
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        CursorToStringConverter cursorToStringConverter = this.mCursorToStringConverter;
        if (cursorToStringConverter != null) {
            return cursorToStringConverter.convertToString(cursor);
        }
        int n = this.mStringConversionColumn;
        if (n > -1) {
            return cursor.getString(n);
        }
        return super.convertToString(cursor);
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter;
    }

    public void setStringConversionColumn(int n) {
        this.mStringConversionColumn = n;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView imageView, String string2) {
        try {
            imageView.setImageResource(Integer.parseInt(string2));
        }
        catch (NumberFormatException numberFormatException) {
            imageView.setImageURI(Uri.parse(string2));
        }
    }

    public void setViewText(TextView textView, String string2) {
        textView.setText(string2);
    }

    @Override
    public Cursor swapCursor(Cursor cursor) {
        this.findColumns(cursor, this.mOriginalFrom);
        return super.swapCursor(cursor);
    }

    public static interface CursorToStringConverter {
        public CharSequence convertToString(Cursor var1);
    }

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Cursor var2, int var3);
    }

}

