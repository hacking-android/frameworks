/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

public class AppDetailsActivity
extends Activity {
    @Override
    protected void onCreate(Bundle parcelable) {
        super.onCreate((Bundle)parcelable);
        parcelable = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        ((Intent)parcelable).setData(Uri.fromParts("package", this.getPackageName(), null));
        this.startActivity((Intent)parcelable);
        this.finish();
    }
}

