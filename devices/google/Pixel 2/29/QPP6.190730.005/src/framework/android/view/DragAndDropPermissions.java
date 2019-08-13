/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.DragEvent;
import com.android.internal.view.IDragAndDropPermissions;

public final class DragAndDropPermissions
implements Parcelable {
    public static final Parcelable.Creator<DragAndDropPermissions> CREATOR = new Parcelable.Creator<DragAndDropPermissions>(){

        @Override
        public DragAndDropPermissions createFromParcel(Parcel parcel) {
            return new DragAndDropPermissions(parcel);
        }

        public DragAndDropPermissions[] newArray(int n) {
            return new DragAndDropPermissions[n];
        }
    };
    private final IDragAndDropPermissions mDragAndDropPermissions;
    private IBinder mTransientToken;

    private DragAndDropPermissions(Parcel parcel) {
        this.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
        this.mTransientToken = parcel.readStrongBinder();
    }

    private DragAndDropPermissions(IDragAndDropPermissions iDragAndDropPermissions) {
        this.mDragAndDropPermissions = iDragAndDropPermissions;
    }

    public static DragAndDropPermissions obtain(DragEvent dragEvent) {
        if (dragEvent.getDragAndDropPermissions() == null) {
            return null;
        }
        return new DragAndDropPermissions(dragEvent.getDragAndDropPermissions());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void release() {
        try {
            this.mDragAndDropPermissions.release();
            this.mTransientToken = null;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public boolean take(IBinder iBinder) {
        try {
            this.mDragAndDropPermissions.take(iBinder);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean takeTransient() {
        try {
            Binder binder = new Binder();
            this.mTransientToken = binder;
            this.mDragAndDropPermissions.takeTransient(this.mTransientToken);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongInterface(this.mDragAndDropPermissions);
        parcel.writeStrongBinder(this.mTransientToken);
    }

}

