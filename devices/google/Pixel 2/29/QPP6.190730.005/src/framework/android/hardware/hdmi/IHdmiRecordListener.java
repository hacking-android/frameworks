/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHdmiRecordListener
extends IInterface {
    public byte[] getOneTouchRecordSource(int var1) throws RemoteException;

    public void onClearTimerRecordingResult(int var1, int var2) throws RemoteException;

    public void onOneTouchRecordResult(int var1, int var2) throws RemoteException;

    public void onTimerRecordingResult(int var1, int var2) throws RemoteException;

    public static class Default
    implements IHdmiRecordListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public byte[] getOneTouchRecordSource(int n) throws RemoteException {
            return null;
        }

        @Override
        public void onClearTimerRecordingResult(int n, int n2) throws RemoteException {
        }

        @Override
        public void onOneTouchRecordResult(int n, int n2) throws RemoteException {
        }

        @Override
        public void onTimerRecordingResult(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiRecordListener {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiRecordListener";
        static final int TRANSACTION_getOneTouchRecordSource = 1;
        static final int TRANSACTION_onClearTimerRecordingResult = 4;
        static final int TRANSACTION_onOneTouchRecordResult = 2;
        static final int TRANSACTION_onTimerRecordingResult = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiRecordListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiRecordListener) {
                return (IHdmiRecordListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiRecordListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onClearTimerRecordingResult";
                    }
                    return "onTimerRecordingResult";
                }
                return "onOneTouchRecordResult";
            }
            return "getOneTouchRecordSource";
        }

        public static boolean setDefaultImpl(IHdmiRecordListener iHdmiRecordListener) {
            if (Proxy.sDefaultImpl == null && iHdmiRecordListener != null) {
                Proxy.sDefaultImpl = iHdmiRecordListener;
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
        public boolean onTransact(int n, Parcel arrby, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)arrby, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        arrby.enforceInterface(DESCRIPTOR);
                        this.onClearTimerRecordingResult(arrby.readInt(), arrby.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    arrby.enforceInterface(DESCRIPTOR);
                    this.onTimerRecordingResult(arrby.readInt(), arrby.readInt());
                    parcel.writeNoException();
                    return true;
                }
                arrby.enforceInterface(DESCRIPTOR);
                this.onOneTouchRecordResult(arrby.readInt(), arrby.readInt());
                parcel.writeNoException();
                return true;
            }
            arrby.enforceInterface(DESCRIPTOR);
            arrby = this.getOneTouchRecordSource(arrby.readInt());
            parcel.writeNoException();
            parcel.writeByteArray(arrby);
            return true;
        }

        private static class Proxy
        implements IHdmiRecordListener {
            public static IHdmiRecordListener sDefaultImpl;
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
            public byte[] getOneTouchRecordSource(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getOneTouchRecordSource(n);
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onClearTimerRecordingResult(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClearTimerRecordingResult(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onOneTouchRecordResult(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOneTouchRecordResult(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onTimerRecordingResult(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimerRecordingResult(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

