/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.UnsupportedAppUsage;
import android.hardware.location.IActivityRecognitionHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IActivityRecognitionHardwareClient
extends IInterface {
    @UnsupportedAppUsage
    public void onAvailabilityChanged(boolean var1, IActivityRecognitionHardware var2) throws RemoteException;

    public static class Default
    implements IActivityRecognitionHardwareClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAvailabilityChanged(boolean bl, IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActivityRecognitionHardwareClient {
        private static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareClient";
        static final int TRANSACTION_onAvailabilityChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActivityRecognitionHardwareClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActivityRecognitionHardwareClient) {
                return (IActivityRecognitionHardwareClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActivityRecognitionHardwareClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAvailabilityChanged";
        }

        public static boolean setDefaultImpl(IActivityRecognitionHardwareClient iActivityRecognitionHardwareClient) {
            if (Proxy.sDefaultImpl == null && iActivityRecognitionHardwareClient != null) {
                Proxy.sDefaultImpl = iActivityRecognitionHardwareClient;
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
            boolean bl = parcel.readInt() != 0;
            this.onAvailabilityChanged(bl, IActivityRecognitionHardware.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IActivityRecognitionHardwareClient {
            public static IActivityRecognitionHardwareClient sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onAvailabilityChanged(boolean bl, IActivityRecognitionHardware iActivityRecognitionHardware) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder = iActivityRecognitionHardware != null ? iActivityRecognitionHardware.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAvailabilityChanged(bl, iActivityRecognitionHardware);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

