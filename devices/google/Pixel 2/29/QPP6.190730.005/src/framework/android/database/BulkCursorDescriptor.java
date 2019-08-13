/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.BulkCursorNative;
import android.database.CursorWindow;
import android.database.IBulkCursor;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public final class BulkCursorDescriptor
implements Parcelable {
    public static final Parcelable.Creator<BulkCursorDescriptor> CREATOR = new Parcelable.Creator<BulkCursorDescriptor>(){

        @Override
        public BulkCursorDescriptor createFromParcel(Parcel parcel) {
            BulkCursorDescriptor bulkCursorDescriptor = new BulkCursorDescriptor();
            bulkCursorDescriptor.readFromParcel(parcel);
            return bulkCursorDescriptor;
        }

        public BulkCursorDescriptor[] newArray(int n) {
            return new BulkCursorDescriptor[n];
        }
    };
    public String[] columnNames;
    public int count;
    public IBulkCursor cursor;
    public boolean wantsAllOnMoveCalls;
    public CursorWindow window;

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.cursor = BulkCursorNative.asInterface(parcel.readStrongBinder());
        this.columnNames = parcel.readStringArray();
        boolean bl = parcel.readInt() != 0;
        this.wantsAllOnMoveCalls = bl;
        this.count = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.window = CursorWindow.CREATOR.createFromParcel(parcel);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.cursor.asBinder());
        parcel.writeStringArray(this.columnNames);
        parcel.writeInt((int)this.wantsAllOnMoveCalls);
        parcel.writeInt(this.count);
        if (this.window != null) {
            parcel.writeInt(1);
            this.window.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
    }

}

