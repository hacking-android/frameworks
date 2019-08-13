/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISystemGestureExclusionListener
extends IInterface {
    public void onSystemGestureExclusionChanged(int var1, Region var2) throws RemoteException;

    public static class Default
    implements ISystemGestureExclusionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSystemGestureExclusionChanged(int n, Region region) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISystemGestureExclusionListener {
        private static final String DESCRIPTOR = "android.view.ISystemGestureExclusionListener";
        static final int TRANSACTION_onSystemGestureExclusionChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISystemGestureExclusionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISystemGestureExclusionListener) {
                return (ISystemGestureExclusionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISystemGestureExclusionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSystemGestureExclusionChanged";
        }

        public static boolean setDefaultImpl(ISystemGestureExclusionListener iSystemGestureExclusionListener) {
            if (Proxy.sDefaultImpl == null && iSystemGestureExclusionListener != null) {
                Proxy.sDefaultImpl = iSystemGestureExclusionListener;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSystemGestureExclusionChanged(n, (Region)object);
            return true;
        }

        private static class Proxy
        implements ISystemGestureExclusionListener {
            public static ISystemGestureExclusionListener sDefaultImpl;
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
            public void onSystemGestureExclusionChanged(int n, Region region) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSystemGestureExclusionChanged(n, region);
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

