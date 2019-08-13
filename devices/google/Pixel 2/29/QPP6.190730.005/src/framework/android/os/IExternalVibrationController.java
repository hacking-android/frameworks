/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IExternalVibrationController
extends IInterface {
    public boolean mute() throws RemoteException;

    public boolean unmute() throws RemoteException;

    public static class Default
    implements IExternalVibrationController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean mute() throws RemoteException {
            return false;
        }

        @Override
        public boolean unmute() throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IExternalVibrationController {
        private static final String DESCRIPTOR = "android.os.IExternalVibrationController";
        static final int TRANSACTION_mute = 1;
        static final int TRANSACTION_unmute = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IExternalVibrationController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IExternalVibrationController) {
                return (IExternalVibrationController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IExternalVibrationController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "unmute";
            }
            return "mute";
        }

        public static boolean setDefaultImpl(IExternalVibrationController iExternalVibrationController) {
            if (Proxy.sDefaultImpl == null && iExternalVibrationController != null) {
                Proxy.sDefaultImpl = iExternalVibrationController;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.unmute() ? 1 : 0;
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = this.mute() ? 1 : 0;
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IExternalVibrationController {
            public static IExternalVibrationController sDefaultImpl;
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
            public boolean mute() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().mute();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean unmute() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().unmute();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }
        }

    }

}

