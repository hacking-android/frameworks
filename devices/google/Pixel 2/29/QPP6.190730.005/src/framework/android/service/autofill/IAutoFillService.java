/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.FillRequest;
import android.service.autofill.IFillCallback;
import android.service.autofill.ISaveCallback;
import android.service.autofill.SaveRequest;

public interface IAutoFillService
extends IInterface {
    public void onConnectedStateChanged(boolean var1) throws RemoteException;

    public void onFillRequest(FillRequest var1, IFillCallback var2) throws RemoteException;

    public void onSaveRequest(SaveRequest var1, ISaveCallback var2) throws RemoteException;

    public static class Default
    implements IAutoFillService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnectedStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException {
        }

        @Override
        public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAutoFillService {
        private static final String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAutoFillService) {
                return (IAutoFillService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAutoFillService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onSaveRequest";
                }
                return "onFillRequest";
            }
            return "onConnectedStateChanged";
        }

        public static boolean setDefaultImpl(IAutoFillService iAutoFillService) {
            if (Proxy.sDefaultImpl == null && iAutoFillService != null) {
                Proxy.sDefaultImpl = iAutoFillService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    object = parcel.readInt() != 0 ? SaveRequest.CREATOR.createFromParcel(parcel) : null;
                    this.onSaveRequest((SaveRequest)object, ISaveCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? FillRequest.CREATOR.createFromParcel(parcel) : null;
                this.onFillRequest((FillRequest)object, IFillCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            this.onConnectedStateChanged(bl);
            return true;
        }

        private static class Proxy
        implements IAutoFillService {
            public static IAutoFillService sDefaultImpl;
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
            public void onConnectedStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectedStateChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fillRequest != null) {
                        parcel.writeInt(1);
                        fillRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iFillCallback != null ? iFillCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onFillRequest(fillRequest, iFillCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (saveRequest != null) {
                        parcel.writeInt(1);
                        saveRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSaveCallback != null ? iSaveCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSaveRequest(saveRequest, iSaveCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

