/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Parcel;

public final class _$$Lambda$Notification$hOCsSZH8tWalFSbIzQ9x9IcPa9M
implements PendingIntent.OnMarshaledListener {
    private final /* synthetic */ Notification f$0;
    private final /* synthetic */ Parcel f$1;

    public /* synthetic */ _$$Lambda$Notification$hOCsSZH8tWalFSbIzQ9x9IcPa9M(Notification notification, Parcel parcel) {
        this.f$0 = notification;
        this.f$1 = parcel;
    }

    @Override
    public final void onMarshaled(PendingIntent pendingIntent, Parcel parcel, int n) {
        this.f$0.lambda$writeToParcel$0$Notification(this.f$1, pendingIntent, parcel, n);
    }
}

