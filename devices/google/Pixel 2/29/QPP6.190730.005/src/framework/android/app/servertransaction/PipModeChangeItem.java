/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class PipModeChangeItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<PipModeChangeItem> CREATOR = new Parcelable.Creator<PipModeChangeItem>(){

        @Override
        public PipModeChangeItem createFromParcel(Parcel parcel) {
            return new PipModeChangeItem(parcel);
        }

        public PipModeChangeItem[] newArray(int n) {
            return new PipModeChangeItem[n];
        }
    };
    private boolean mIsInPipMode;
    private Configuration mOverrideConfig;

    private PipModeChangeItem() {
    }

    private PipModeChangeItem(Parcel parcel) {
        this.mIsInPipMode = parcel.readBoolean();
        this.mOverrideConfig = parcel.readTypedObject(Configuration.CREATOR);
    }

    public static PipModeChangeItem obtain(boolean bl, Configuration configuration) {
        PipModeChangeItem pipModeChangeItem;
        PipModeChangeItem pipModeChangeItem2 = pipModeChangeItem = ObjectPool.obtain(PipModeChangeItem.class);
        if (pipModeChangeItem == null) {
            pipModeChangeItem2 = new PipModeChangeItem();
        }
        pipModeChangeItem2.mIsInPipMode = bl;
        pipModeChangeItem2.mOverrideConfig = configuration;
        return pipModeChangeItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PipModeChangeItem)object;
            if (this.mIsInPipMode != ((PipModeChangeItem)object).mIsInPipMode || !Objects.equals(this.mOverrideConfig, ((PipModeChangeItem)object).mOverrideConfig)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handlePictureInPictureModeChanged(iBinder, this.mIsInPipMode, this.mOverrideConfig);
    }

    public int hashCode() {
        return (17 * 31 + this.mIsInPipMode) * 31 + this.mOverrideConfig.hashCode();
    }

    @Override
    public void recycle() {
        this.mIsInPipMode = false;
        this.mOverrideConfig = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PipModeChangeItem{isInPipMode=");
        stringBuilder.append(this.mIsInPipMode);
        stringBuilder.append(",overrideConfig=");
        stringBuilder.append(this.mOverrideConfig);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mIsInPipMode);
        parcel.writeTypedObject(this.mOverrideConfig, n);
    }

}

