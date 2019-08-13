/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.annotation.UnsupportedAppUsage;
import android.app.ClientTransactionHandler;
import android.app.IApplicationThread;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.ObjectPoolItem;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientTransaction
implements Parcelable,
ObjectPoolItem {
    public static final Parcelable.Creator<ClientTransaction> CREATOR = new Parcelable.Creator<ClientTransaction>(){

        @Override
        public ClientTransaction createFromParcel(Parcel parcel) {
            return new ClientTransaction(parcel);
        }

        public ClientTransaction[] newArray(int n) {
            return new ClientTransaction[n];
        }
    };
    @UnsupportedAppUsage
    private List<ClientTransactionItem> mActivityCallbacks;
    private IBinder mActivityToken;
    private IApplicationThread mClient;
    private ActivityLifecycleItem mLifecycleStateRequest;

    private ClientTransaction() {
    }

    private ClientTransaction(Parcel parcel) {
        this.mClient = (IApplicationThread)((Object)parcel.readStrongBinder());
        if (parcel.readBoolean()) {
            this.mActivityToken = parcel.readStrongBinder();
        }
        this.mLifecycleStateRequest = (ActivityLifecycleItem)parcel.readParcelable(this.getClass().getClassLoader());
        if (parcel.readBoolean()) {
            this.mActivityCallbacks = new ArrayList<ClientTransactionItem>();
            parcel.readParcelableList(this.mActivityCallbacks, this.getClass().getClassLoader());
        }
    }

    public static ClientTransaction obtain(IApplicationThread iApplicationThread, IBinder iBinder) {
        ClientTransaction clientTransaction;
        ClientTransaction clientTransaction2 = clientTransaction = ObjectPool.obtain(ClientTransaction.class);
        if (clientTransaction == null) {
            clientTransaction2 = new ClientTransaction();
        }
        clientTransaction2.mClient = iApplicationThread;
        clientTransaction2.mActivityToken = iBinder;
        return clientTransaction2;
    }

    public void addCallback(ClientTransactionItem clientTransactionItem) {
        if (this.mActivityCallbacks == null) {
            this.mActivityCallbacks = new ArrayList<ClientTransactionItem>();
        }
        this.mActivityCallbacks.add(clientTransactionItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.append(string2).println("ClientTransaction{");
        printWriter.append(string2).print("  callbacks=[");
        Object object = this.mActivityCallbacks;
        int n = object != null ? object.size() : 0;
        if (n > 0) {
            printWriter.println();
            for (int i = 0; i < n; ++i) {
                printWriter.append(string2).append("    ").println(this.mActivityCallbacks.get(i).toString());
            }
            printWriter.append(string2).println("  ]");
        } else {
            printWriter.println("]");
        }
        PrintWriter printWriter2 = printWriter.append(string2).append("  stateRequest=");
        object = this.mLifecycleStateRequest;
        object = object != null ? object.toString() : null;
        printWriter2.println((String)object);
        printWriter.append(string2).println("}");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ClientTransaction)object;
            if (!Objects.equals(this.mActivityCallbacks, ((ClientTransaction)object).mActivityCallbacks) || !Objects.equals(this.mLifecycleStateRequest, ((ClientTransaction)object).mLifecycleStateRequest) || this.mClient != ((ClientTransaction)object).mClient || this.mActivityToken != ((ClientTransaction)object).mActivityToken) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @UnsupportedAppUsage
    List<ClientTransactionItem> getCallbacks() {
        return this.mActivityCallbacks;
    }

    public IApplicationThread getClient() {
        return this.mClient;
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public ActivityLifecycleItem getLifecycleStateRequest() {
        return this.mLifecycleStateRequest;
    }

    public int hashCode() {
        return (17 * 31 + Objects.hashCode(this.mActivityCallbacks)) * 31 + Objects.hashCode(this.mLifecycleStateRequest);
    }

    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        List<ClientTransactionItem> list = this.mActivityCallbacks;
        if (list != null) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                this.mActivityCallbacks.get(i).preExecute(clientTransactionHandler, this.mActivityToken);
            }
        }
        if ((list = this.mLifecycleStateRequest) != null) {
            list.preExecute(clientTransactionHandler, this.mActivityToken);
        }
    }

    @Override
    public void recycle() {
        List<ClientTransactionItem> list = this.mActivityCallbacks;
        if (list != null) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                this.mActivityCallbacks.get(i).recycle();
            }
            this.mActivityCallbacks.clear();
        }
        if ((list = this.mLifecycleStateRequest) != null) {
            ((ActivityLifecycleItem)((Object)list)).recycle();
            this.mLifecycleStateRequest = null;
        }
        this.mClient = null;
        this.mActivityToken = null;
        ObjectPool.recycle(this);
    }

    public void schedule() throws RemoteException {
        this.mClient.scheduleTransaction(this);
    }

    public void setLifecycleStateRequest(ActivityLifecycleItem activityLifecycleItem) {
        this.mLifecycleStateRequest = activityLifecycleItem;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mClient.asBinder());
        IBinder iBinder = this.mActivityToken;
        boolean bl = true;
        boolean bl2 = iBinder != null;
        parcel.writeBoolean(bl2);
        if (bl2) {
            parcel.writeStrongBinder(this.mActivityToken);
        }
        parcel.writeParcelable(this.mLifecycleStateRequest, n);
        bl2 = this.mActivityCallbacks != null ? bl : false;
        parcel.writeBoolean(bl2);
        if (bl2) {
            parcel.writeParcelableList(this.mActivityCallbacks, n);
        }
    }

}

