/*
 * Decompiled with CFR 0.145.
 */
package android.service.chooser;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.chooser.ChooserTarget;
import java.util.ArrayList;
import java.util.List;

public interface IChooserTargetResult
extends IInterface {
    public void sendResult(List<ChooserTarget> var1) throws RemoteException;

    public static class Default
    implements IChooserTargetResult {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void sendResult(List<ChooserTarget> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IChooserTargetResult {
        private static final String DESCRIPTOR = "android.service.chooser.IChooserTargetResult";
        static final int TRANSACTION_sendResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IChooserTargetResult asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IChooserTargetResult) {
                return (IChooserTargetResult)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IChooserTargetResult getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "sendResult";
        }

        public static boolean setDefaultImpl(IChooserTargetResult iChooserTargetResult) {
            if (Proxy.sDefaultImpl == null && iChooserTargetResult != null) {
                Proxy.sDefaultImpl = iChooserTargetResult;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.sendResult(parcel.createTypedArrayList(ChooserTarget.CREATOR));
            return true;
        }

        private static class Proxy
        implements IChooserTargetResult {
            public static IChooserTargetResult sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void sendResult(List<ChooserTarget> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendResult(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

