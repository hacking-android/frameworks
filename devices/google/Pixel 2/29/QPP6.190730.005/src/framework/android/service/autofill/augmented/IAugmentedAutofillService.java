/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.augmented.IFillCallback;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

public interface IAugmentedAutofillService
extends IInterface {
    public void onConnected(boolean var1, boolean var2) throws RemoteException;

    public void onDestroyAllFillWindowsRequest() throws RemoteException;

    public void onDisconnected() throws RemoteException;

    public void onFillRequest(int var1, IBinder var2, int var3, ComponentName var4, AutofillId var5, AutofillValue var6, long var7, IFillCallback var9) throws RemoteException;

    public static class Default
    implements IAugmentedAutofillService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnected(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void onDestroyAllFillWindowsRequest() throws RemoteException {
        }

        @Override
        public void onDisconnected() throws RemoteException {
        }

        @Override
        public void onFillRequest(int n, IBinder iBinder, int n2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAugmentedAutofillService {
        private static final String DESCRIPTOR = "android.service.autofill.augmented.IAugmentedAutofillService";
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDestroyAllFillWindowsRequest = 4;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onFillRequest = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAugmentedAutofillService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAugmentedAutofillService) {
                return (IAugmentedAutofillService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAugmentedAutofillService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onDestroyAllFillWindowsRequest";
                    }
                    return "onFillRequest";
                }
                return "onDisconnected";
            }
            return "onConnected";
        }

        public static boolean setDefaultImpl(IAugmentedAutofillService iAugmentedAutofillService) {
            if (Proxy.sDefaultImpl == null && iAugmentedAutofillService != null) {
                Proxy.sDefaultImpl = iAugmentedAutofillService;
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
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)object, n2);
                            }
                            ((Parcel)object).writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDestroyAllFillWindowsRequest();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n2 = parcel.readInt();
                    IBinder iBinder = parcel.readStrongBinder();
                    n = parcel.readInt();
                    object = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                    AutofillId autofillId = parcel.readInt() != 0 ? AutofillId.CREATOR.createFromParcel(parcel) : null;
                    AutofillValue autofillValue = parcel.readInt() != 0 ? AutofillValue.CREATOR.createFromParcel(parcel) : null;
                    this.onFillRequest(n2, iBinder, n, (ComponentName)object, autofillId, autofillValue, parcel.readLong(), IFillCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onDisconnected();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n != 0;
            if (parcel.readInt() != 0) {
                bl = true;
            }
            this.onConnected(bl2, bl);
            return true;
        }

        private static class Proxy
        implements IAugmentedAutofillService {
            public static IAugmentedAutofillService sDefaultImpl;
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
            public void onConnected(boolean bl, boolean bl2) throws RemoteException {
                int n;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnected(bl, bl2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDestroyAllFillWindowsRequest() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDestroyAllFillWindowsRequest();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDisconnected() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisconnected();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onFillRequest(int n, IBinder iBinder, int n2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback) throws RemoteException {
                void var2_5;
                Parcel parcel;
                block11 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeInt(n2);
                        if (componentName != null) {
                            parcel.writeInt(1);
                            componentName.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (autofillId != null) {
                            parcel.writeInt(1);
                            autofillId.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (autofillValue != null) {
                            parcel.writeInt(1);
                            autofillValue.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeLong(l);
                        IBinder iBinder2 = iFillCallback != null ? iFillCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onFillRequest(n, iBinder, n2, componentName, autofillId, autofillValue, l, iFillCallback);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_5;
            }
        }

    }

}

