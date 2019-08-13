/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.LocaleList;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.TransformationMethod2;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

public class AllCapsTransformationMethod
implements TransformationMethod2 {
    private static final String TAG = "AllCapsTransformationMethod";
    private boolean mEnabled;
    private Locale mLocale;

    @UnsupportedAppUsage
    public AllCapsTransformationMethod(Context context) {
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
    }

    @Override
    public CharSequence getTransformation(CharSequence charSequence, View object) {
        if (!this.mEnabled) {
            Log.w(TAG, "Caller did not enable length changes; not transforming text");
            return charSequence;
        }
        if (charSequence == null) {
            return null;
        }
        Locale locale = null;
        if (object instanceof TextView) {
            locale = ((TextView)object).getTextLocale();
        }
        object = locale;
        if (locale == null) {
            object = this.mLocale;
        }
        return TextUtils.toUpperCase((Locale)object, charSequence, charSequence instanceof Spanned);
    }

    @Override
    public void onFocusChanged(View view, CharSequence charSequence, boolean bl, int n, Rect rect) {
    }

    @Override
    public void setLengthChangesAllowed(boolean bl) {
        this.mEnabled = bl;
    }
}

