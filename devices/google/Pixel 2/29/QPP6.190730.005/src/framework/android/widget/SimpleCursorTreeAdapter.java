/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorTreeAdapter;
import android.widget.TextView;

public abstract class SimpleCursorTreeAdapter
extends ResourceCursorTreeAdapter {
    private int[] mChildFrom;
    private String[] mChildFromNames;
    private int[] mChildTo;
    private int[] mGroupFrom;
    private String[] mGroupFromNames;
    private int[] mGroupTo;
    private ViewBinder mViewBinder;

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int n, int n2, String[] arrstring, int[] arrn, int n3, int n4, String[] arrstring2, int[] arrn2) {
        super(context, cursor, n, n2, n3, n4);
        this.init(arrstring, arrn, arrstring2, arrn2);
    }

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int n, int n2, String[] arrstring, int[] arrn, int n3, String[] arrstring2, int[] arrn2) {
        super(context, cursor, n, n2, n3);
        this.init(arrstring, arrn, arrstring2, arrn2);
    }

    public SimpleCursorTreeAdapter(Context context, Cursor cursor, int n, String[] arrstring, int[] arrn, int n2, String[] arrstring2, int[] arrn2) {
        super(context, cursor, n, n2);
        this.init(arrstring, arrn, arrstring2, arrn2);
    }

    private void bindView(View view, Context object, Cursor cursor, int[] arrn, int[] arrn2) {
        ViewBinder viewBinder = this.mViewBinder;
        for (int i = 0; i < arrn2.length; ++i) {
            Object t = view.findViewById(arrn2[i]);
            if (t == null) continue;
            boolean bl = false;
            if (viewBinder != null) {
                bl = viewBinder.setViewValue((View)t, cursor, arrn[i]);
            }
            if (bl) continue;
            String string2 = cursor.getString(arrn[i]);
            object = string2;
            if (string2 == null) {
                object = "";
            }
            if (t instanceof TextView) {
                this.setViewText((TextView)t, (String)object);
                continue;
            }
            if (t instanceof ImageView) {
                this.setViewImage((ImageView)t, (String)object);
                continue;
            }
            throw new IllegalStateException("SimpleCursorTreeAdapter can bind values only to TextView and ImageView!");
        }
    }

    private void init(String[] arrstring, int[] arrn, String[] arrstring2, int[] arrn2) {
        this.mGroupFromNames = arrstring;
        this.mGroupTo = arrn;
        this.mChildFromNames = arrstring2;
        this.mChildTo = arrn2;
    }

    private void initFromColumns(Cursor cursor, String[] arrstring, int[] arrn) {
        for (int i = arrstring.length - 1; i >= 0; --i) {
            arrn[i] = cursor.getColumnIndexOrThrow(arrstring[i]);
        }
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean bl) {
        if (this.mChildFrom == null) {
            String[] arrstring = this.mChildFromNames;
            this.mChildFrom = new int[arrstring.length];
            this.initFromColumns(cursor, arrstring, this.mChildFrom);
        }
        this.bindView(view, context, cursor, this.mChildFrom, this.mChildTo);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean bl) {
        if (this.mGroupFrom == null) {
            String[] arrstring = this.mGroupFromNames;
            this.mGroupFrom = new int[arrstring.length];
            this.initFromColumns(cursor, arrstring, this.mGroupFrom);
        }
        this.bindView(view, context, cursor, this.mGroupFrom, this.mGroupTo);
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    protected void setViewImage(ImageView imageView, String string2) {
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

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Cursor var2, int var3);
    }

}

