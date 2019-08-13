/*
 * Decompiled with CFR 0.145.
 */
package android.service.restrictions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;

public abstract class RestrictionsReceiver
extends BroadcastReceiver {
    private static final String TAG = "RestrictionsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.content.action.REQUEST_PERMISSION".equals(intent.getAction())) {
            String string2 = intent.getStringExtra("android.content.extra.PACKAGE_NAME");
            String string3 = intent.getStringExtra("android.content.extra.REQUEST_TYPE");
            String string4 = intent.getStringExtra("android.content.extra.REQUEST_ID");
            this.onRequestPermission(context, string2, string3, string4, (PersistableBundle)intent.getParcelableExtra("android.content.extra.REQUEST_BUNDLE"));
        }
    }

    public abstract void onRequestPermission(Context var1, String var2, String var3, String var4, PersistableBundle var5);
}

