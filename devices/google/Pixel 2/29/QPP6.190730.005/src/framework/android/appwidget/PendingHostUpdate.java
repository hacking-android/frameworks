/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.appwidget.AppWidgetProviderInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

public class PendingHostUpdate
implements Parcelable {
    public static final Parcelable.Creator<PendingHostUpdate> CREATOR = new Parcelable.Creator<PendingHostUpdate>(){

        @Override
        public PendingHostUpdate createFromParcel(Parcel parcel) {
            return new PendingHostUpdate(parcel);
        }

        public PendingHostUpdate[] newArray(int n) {
            return new PendingHostUpdate[n];
        }
    };
    static final int TYPE_PROVIDER_CHANGED = 1;
    static final int TYPE_VIEWS_UPDATE = 0;
    static final int TYPE_VIEW_DATA_CHANGED = 2;
    final int appWidgetId;
    final int type;
    int viewId;
    RemoteViews views;
    AppWidgetProviderInfo widgetInfo;

    private PendingHostUpdate(int n, int n2) {
        this.appWidgetId = n;
        this.type = n2;
    }

    private PendingHostUpdate(Parcel parcel) {
        this.appWidgetId = parcel.readInt();
        int n = this.type = parcel.readInt();
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.viewId = parcel.readInt();
                }
            } else if (parcel.readInt() != 0) {
                this.widgetInfo = new AppWidgetProviderInfo(parcel);
            }
        } else if (parcel.readInt() != 0) {
            this.views = new RemoteViews(parcel);
        }
    }

    public static PendingHostUpdate providerChanged(int n, AppWidgetProviderInfo appWidgetProviderInfo) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(n, 1);
        pendingHostUpdate.widgetInfo = appWidgetProviderInfo;
        return pendingHostUpdate;
    }

    public static PendingHostUpdate updateAppWidget(int n, RemoteViews remoteViews) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(n, 0);
        pendingHostUpdate.views = remoteViews;
        return pendingHostUpdate;
    }

    public static PendingHostUpdate viewDataChanged(int n, int n2) {
        PendingHostUpdate pendingHostUpdate = new PendingHostUpdate(n, 2);
        pendingHostUpdate.viewId = n2;
        return pendingHostUpdate;
    }

    private void writeNullParcelable(Parcelable parcelable, Parcel parcel, int n) {
        if (parcelable != null) {
            parcel.writeInt(1);
            parcelable.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.appWidgetId);
        parcel.writeInt(this.type);
        int n2 = this.type;
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    parcel.writeInt(this.viewId);
                }
            } else {
                this.writeNullParcelable(this.widgetInfo, parcel, n);
            }
        } else {
            this.writeNullParcelable(this.views, parcel, n);
        }
    }

}

