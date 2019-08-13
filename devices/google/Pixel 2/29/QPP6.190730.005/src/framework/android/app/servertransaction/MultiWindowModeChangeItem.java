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

public class MultiWindowModeChangeItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<MultiWindowModeChangeItem> CREATOR = new Parcelable.Creator<MultiWindowModeChangeItem>(){

        @Override
        public MultiWindowModeChangeItem createFromParcel(Parcel parcel) {
            return new MultiWindowModeChangeItem(parcel);
        }

        public MultiWindowModeChangeItem[] newArray(int n) {
            return new MultiWindowModeChangeItem[n];
        }
    };
    private boolean mIsInMultiWindowMode;
    private Configuration mOverrideConfig;

    private MultiWindowModeChangeItem() {
    }

    private MultiWindowModeChangeItem(Parcel parcel) {
        this.mIsInMultiWindowMode = parcel.readBoolean();
        this.mOverrideConfig = parcel.readTypedObject(Configuration.CREATOR);
    }

    public static MultiWindowModeChangeItem obtain(boolean bl, Configuration configuration) {
        MultiWindowModeChangeItem multiWindowModeChangeItem;
        MultiWindowModeChangeItem multiWindowModeChangeItem2 = multiWindowModeChangeItem = ObjectPool.obtain(MultiWindowModeChangeItem.class);
        if (multiWindowModeChangeItem == null) {
            multiWindowModeChangeItem2 = new MultiWindowModeChangeItem();
        }
        multiWindowModeChangeItem2.mIsInMultiWindowMode = bl;
        multiWindowModeChangeItem2.mOverrideConfig = configuration;
        return multiWindowModeChangeItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (MultiWindowModeChangeItem)object;
            if (this.mIsInMultiWindowMode != ((MultiWindowModeChangeItem)object).mIsInMultiWindowMode || !Objects.equals(this.mOverrideConfig, ((MultiWindowModeChangeItem)object).mOverrideConfig)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleMultiWindowModeChanged(iBinder, this.mIsInMultiWindowMode, this.mOverrideConfig);
    }

    public int hashCode() {
        return (17 * 31 + this.mIsInMultiWindowMode) * 31 + this.mOverrideConfig.hashCode();
    }

    @Override
    public void recycle() {
        this.mIsInMultiWindowMode = false;
        this.mOverrideConfig = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MultiWindowModeChangeItem{isInMultiWindowMode=");
        stringBuilder.append(this.mIsInMultiWindowMode);
        stringBuilder.append(",overrideConfig=");
        stringBuilder.append(this.mOverrideConfig);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mIsInMultiWindowMode);
        parcel.writeTypedObject(this.mOverrideConfig, n);
    }

}

