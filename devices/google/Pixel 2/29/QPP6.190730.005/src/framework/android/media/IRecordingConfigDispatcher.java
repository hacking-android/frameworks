/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioRecordingConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IRecordingConfigDispatcher
extends IInterface {
    public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> var1) throws RemoteException;

    public static class Default
    implements IRecordingConfigDispatcher {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecordingConfigDispatcher {
        private static final String DESCRIPTOR = "android.media.IRecordingConfigDispatcher";
        static final int TRANSACTION_dispatchRecordingConfigChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecordingConfigDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecordingConfigDispatcher) {
                return (IRecordingConfigDispatcher)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecordingConfigDispatcher getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchRecordingConfigChange";
        }

        public static boolean setDefaultImpl(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
            if (Proxy.sDefaultImpl == null && iRecordingConfigDispatcher != null) {
                Proxy.sDefaultImpl = iRecordingConfigDispatcher;
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
            this.dispatchRecordingConfigChange(parcel.createTypedArrayList(AudioRecordingConfiguration.CREATOR));
            return true;
        }

        private static class Proxy
        implements IRecordingConfigDispatcher {
            public static IRecordingConfigDispatcher sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchRecordingConfigChange(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

