/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.media.midi.IMidiDeviceServer;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMidiDeviceOpenCallback
extends IInterface {
    public void onDeviceOpened(IMidiDeviceServer var1, IBinder var2) throws RemoteException;

    public static class Default
    implements IMidiDeviceOpenCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDeviceOpened(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMidiDeviceOpenCallback {
        private static final String DESCRIPTOR = "android.media.midi.IMidiDeviceOpenCallback";
        static final int TRANSACTION_onDeviceOpened = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceOpenCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMidiDeviceOpenCallback) {
                return (IMidiDeviceOpenCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMidiDeviceOpenCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onDeviceOpened";
        }

        public static boolean setDefaultImpl(IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
            if (Proxy.sDefaultImpl == null && iMidiDeviceOpenCallback != null) {
                Proxy.sDefaultImpl = iMidiDeviceOpenCallback;
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
            this.onDeviceOpened(IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder()), parcel.readStrongBinder());
            return true;
        }

        private static class Proxy
        implements IMidiDeviceOpenCallback {
            public static IMidiDeviceOpenCallback sDefaultImpl;
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
            public void onDeviceOpened(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iMidiDeviceServer != null ? iMidiDeviceServer.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onDeviceOpened(iMidiDeviceServer, iBinder);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

