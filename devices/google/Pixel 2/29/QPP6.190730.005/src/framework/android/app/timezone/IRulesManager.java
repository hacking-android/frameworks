/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.ICallback;
import android.app.timezone.RulesState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IRulesManager
extends IInterface {
    public RulesState getRulesState() throws RemoteException;

    public int requestInstall(ParcelFileDescriptor var1, byte[] var2, ICallback var3) throws RemoteException;

    public void requestNothing(byte[] var1, boolean var2) throws RemoteException;

    public int requestUninstall(byte[] var1, ICallback var2) throws RemoteException;

    public static class Default
    implements IRulesManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public RulesState getRulesState() throws RemoteException {
            return null;
        }

        @Override
        public int requestInstall(ParcelFileDescriptor parcelFileDescriptor, byte[] arrby, ICallback iCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void requestNothing(byte[] arrby, boolean bl) throws RemoteException {
        }

        @Override
        public int requestUninstall(byte[] arrby, ICallback iCallback) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRulesManager {
        private static final String DESCRIPTOR = "android.app.timezone.IRulesManager";
        static final int TRANSACTION_getRulesState = 1;
        static final int TRANSACTION_requestInstall = 2;
        static final int TRANSACTION_requestNothing = 4;
        static final int TRANSACTION_requestUninstall = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRulesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRulesManager) {
                return (IRulesManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRulesManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "requestNothing";
                    }
                    return "requestUninstall";
                }
                return "requestInstall";
            }
            return "getRulesState";
        }

        public static boolean setDefaultImpl(IRulesManager iRulesManager) {
            if (Proxy.sDefaultImpl == null && iRulesManager != null) {
                Proxy.sDefaultImpl = iRulesManager;
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
            boolean bl = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        byte[] arrby = ((Parcel)object).createByteArray();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.requestNothing(arrby, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = this.requestUninstall(((Parcel)object).createByteArray(), ICallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.requestInstall(parcelFileDescriptor, ((Parcel)object).createByteArray(), ICallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getRulesState();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((RulesState)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IRulesManager {
            public static IRulesManager sDefaultImpl;
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
            public RulesState getRulesState() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        RulesState rulesState = Stub.getDefaultImpl().getRulesState();
                        parcel.recycle();
                        parcel2.recycle();
                        return rulesState;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                RulesState rulesState = parcel.readInt() != 0 ? RulesState.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return rulesState;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int requestInstall(ParcelFileDescriptor parcelFileDescriptor, byte[] arrby, ICallback iCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByteArray(arrby);
                    IBinder iBinder = iCallback != null ? iCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().requestInstall(parcelFileDescriptor, arrby, iCallback);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void requestNothing(byte[] arrby, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeByteArray(arrby);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestNothing(arrby, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int requestUninstall(byte[] arrby, ICallback iCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    IBinder iBinder = iCallback != null ? iCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().requestUninstall(arrby, iCallback);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

