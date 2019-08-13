/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@Deprecated
public class AppSecurityPermissions {
    public static View getPermissionItemView(Context context, CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        int n = bl ? 17302321 : 17302829;
        return AppSecurityPermissions.getPermissionItemViewOld(context, layoutInflater, charSequence, charSequence2, bl, context.getDrawable(n));
    }

    private static View getPermissionItemViewOld(Context object, LayoutInflater object2, CharSequence charSequence, CharSequence charSequence2, boolean bl, Drawable drawable2) {
        View view = ((LayoutInflater)object2).inflate(17367097, null);
        object = (TextView)view.findViewById(16909224);
        object2 = (TextView)view.findViewById(16909226);
        ((ImageView)view.findViewById(16909220)).setImageDrawable(drawable2);
        if (charSequence != null) {
            ((TextView)object).setText(charSequence);
            ((TextView)object2).setText(charSequence2);
        } else {
            ((TextView)object).setText(charSequence2);
            ((View)object2).setVisibility(8);
        }
        return view;
    }
}

