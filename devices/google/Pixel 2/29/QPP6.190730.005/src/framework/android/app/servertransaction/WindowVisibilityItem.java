/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class WindowVisibilityItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<WindowVisibilityItem> CREATOR = new Parcelable.Creator<WindowVisibilityItem>(){

        @Override
        public WindowVisibilityItem createFromParcel(Parcel parcel) {
            return new WindowVisibilityItem(parcel);
        }

        public WindowVisibilityItem[] newArray(int n) {
            return new WindowVisibilityItem[n];
        }
    };
    private boolean mShowWindow;

    private WindowVisibilityItem() {
    }

    private WindowVisibilityItem(Parcel parcel) {
        this.mShowWindow = parcel.readBoolean();
    }

    public static WindowVisibilityItem obtain(boolean bl) {
        WindowVisibilityItem windowVisibilityItem;
        WindowVisibilityItem windowVisibilityItem2 = windowVisibilityItem = ObjectPool.obtain(WindowVisibilityItem.class);
        if (windowVisibilityItem == null) {
            windowVisibilityItem2 = new WindowVisibilityItem();
        }
        windowVisibilityItem2.mShowWindow = bl;
        return windowVisibilityItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (WindowVisibilityItem)object;
            if (this.mShowWindow != ((WindowVisibilityItem)object).mShowWindow) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions object) {
        object = this.mShowWindow ? "activityShowWindow" : "activityHideWindow";
        Trace.traceBegin(64L, (String)object);
        clientTransactionHandler.handleWindowVisibility(iBinder, this.mShowWindow);
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return this.mShowWindow * 31 + 17;
    }

    @Override
    public void recycle() {
        this.mShowWindow = false;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WindowVisibilityItem{showWindow=");
        stringBuilder.append(this.mShowWindow);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mShowWindow);
    }

}

