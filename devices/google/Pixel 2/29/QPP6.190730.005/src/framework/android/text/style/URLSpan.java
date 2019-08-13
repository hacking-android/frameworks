/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

public class URLSpan
extends ClickableSpan
implements ParcelableSpan {
    private final String mURL;

    public URLSpan(Parcel parcel) {
        this.mURL = parcel.readString();
    }

    public URLSpan(String string2) {
        this.mURL = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 11;
    }

    public String getURL() {
        return this.mURL;
    }

    @Override
    public void onClick(View object) {
        Uri uri = Uri.parse(this.getURL());
        Context context = ((View)object).getContext();
        object = new Intent("android.intent.action.VIEW", uri);
        ((Intent)object).putExtra("com.android.browser.application_id", context.getPackageName());
        try {
            context.startActivity((Intent)object);
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Actvity was not found for intent, ");
            stringBuilder.append(((Intent)object).toString());
            Log.w("URLSpan", stringBuilder.toString());
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeString(this.mURL);
    }
}

