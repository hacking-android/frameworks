/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.annotation.UnsupportedAppUsage;
import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewIntentItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<NewIntentItem> CREATOR = new Parcelable.Creator<NewIntentItem>(){

        @Override
        public NewIntentItem createFromParcel(Parcel parcel) {
            return new NewIntentItem(parcel);
        }

        public NewIntentItem[] newArray(int n) {
            return new NewIntentItem[n];
        }
    };
    @UnsupportedAppUsage
    private List<ReferrerIntent> mIntents;
    private boolean mResume;

    private NewIntentItem() {
    }

    private NewIntentItem(Parcel parcel) {
        this.mResume = parcel.readBoolean();
        this.mIntents = parcel.createTypedArrayList(ReferrerIntent.CREATOR);
    }

    public static NewIntentItem obtain(List<ReferrerIntent> list, boolean bl) {
        NewIntentItem newIntentItem;
        NewIntentItem newIntentItem2 = newIntentItem = ObjectPool.obtain(NewIntentItem.class);
        if (newIntentItem == null) {
            newIntentItem2 = new NewIntentItem();
        }
        newIntentItem2.mIntents = list;
        newIntentItem2.mResume = bl;
        return newIntentItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (NewIntentItem)object;
            if (this.mResume != ((NewIntentItem)object).mResume || !Objects.equals(this.mIntents, ((NewIntentItem)object).mIntents)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityNewIntent");
        clientTransactionHandler.handleNewIntent(iBinder, this.mIntents);
        Trace.traceEnd(64L);
    }

    @Override
    public int getPostExecutionState() {
        int n = this.mResume ? 3 : -1;
        return n;
    }

    public int hashCode() {
        return (17 * 31 + this.mResume) * 31 + this.mIntents.hashCode();
    }

    @Override
    public void recycle() {
        this.mIntents = null;
        this.mResume = false;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NewIntentItem{intents=");
        stringBuilder.append(this.mIntents);
        stringBuilder.append(",resume=");
        stringBuilder.append(this.mResume);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mResume);
        parcel.writeTypedList(this.mIntents, n);
    }

}

