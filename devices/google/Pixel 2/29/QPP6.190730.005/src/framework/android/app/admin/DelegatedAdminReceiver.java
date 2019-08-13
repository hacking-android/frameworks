/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

public class DelegatedAdminReceiver
extends BroadcastReceiver {
    private static final String TAG = "DelegatedAdminReceiver";

    public String onChoosePrivateKeyAlias(Context context, Intent intent, int n, Uri uri, String string2) {
        throw new UnsupportedOperationException("onChoosePrivateKeyAlias should be implemented");
    }

    public void onNetworkLogsAvailable(Context context, Intent intent, long l, int n) {
        throw new UnsupportedOperationException("onNetworkLogsAvailable should be implemented");
    }

    @Override
    public final void onReceive(Context object, Intent intent) {
        String string2 = intent.getAction();
        if ("android.app.action.CHOOSE_PRIVATE_KEY_ALIAS".equals(string2)) {
            this.setResultData(this.onChoosePrivateKeyAlias((Context)object, intent, intent.getIntExtra("android.app.extra.CHOOSE_PRIVATE_KEY_SENDER_UID", -1), (Uri)intent.getParcelableExtra("android.app.extra.CHOOSE_PRIVATE_KEY_URI"), intent.getStringExtra("android.app.extra.CHOOSE_PRIVATE_KEY_ALIAS")));
        } else if ("android.app.action.NETWORK_LOGS_AVAILABLE".equals(string2)) {
            this.onNetworkLogsAvailable((Context)object, intent, intent.getLongExtra("android.app.extra.EXTRA_NETWORK_LOGS_TOKEN", -1L), intent.getIntExtra("android.app.extra.EXTRA_NETWORK_LOGS_COUNT", 0));
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unhandled broadcast: ");
            ((StringBuilder)object).append(string2);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
    }
}

