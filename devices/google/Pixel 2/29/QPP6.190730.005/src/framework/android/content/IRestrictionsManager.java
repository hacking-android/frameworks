/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface IRestrictionsManager
extends IInterface {
    public Intent createLocalApprovalIntent() throws RemoteException;

    public Bundle getApplicationRestrictions(String var1) throws RemoteException;

    public boolean hasRestrictionsProvider() throws RemoteException;

    public void notifyPermissionResponse(String var1, PersistableBundle var2) throws RemoteException;

    public void requestPermission(String var1, String var2, String var3, PersistableBundle var4) throws RemoteException;

    public static class Default
    implements IRestrictionsManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Intent createLocalApprovalIntent() throws RemoteException {
            return null;
        }

        @Override
        public Bundle getApplicationRestrictions(String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasRestrictionsProvider() throws RemoteException {
            return false;
        }

        @Override
        public void notifyPermissionResponse(String string2, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override
        public void requestPermission(String string2, String string3, String string4, PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRestrictionsManager {
        private static final String DESCRIPTOR = "android.content.IRestrictionsManager";
        static final int TRANSACTION_createLocalApprovalIntent = 5;
        static final int TRANSACTION_getApplicationRestrictions = 1;
        static final int TRANSACTION_hasRestrictionsProvider = 2;
        static final int TRANSACTION_notifyPermissionResponse = 4;
        static final int TRANSACTION_requestPermission = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRestrictionsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRestrictionsManager) {
                return (IRestrictionsManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRestrictionsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "createLocalApprovalIntent";
                        }
                        return "notifyPermissionResponse";
                    }
                    return "requestPermission";
                }
                return "hasRestrictionsProvider";
            }
            return "getApplicationRestrictions";
        }

        public static boolean setDefaultImpl(IRestrictionsManager iRestrictionsManager) {
            if (Proxy.sDefaultImpl == null && iRestrictionsManager != null) {
                Proxy.sDefaultImpl = iRestrictionsManager;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object = this.createLocalApprovalIntent();
                            parcel.writeNoException();
                            if (object != null) {
                                parcel.writeInt(1);
                                ((Intent)object).writeToParcel(parcel, 1);
                            } else {
                                parcel.writeInt(0);
                            }
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyPermissionResponse(string2, (PersistableBundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    String string3 = ((Parcel)object).readString();
                    String string4 = ((Parcel)object).readString();
                    String string5 = ((Parcel)object).readString();
                    object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.requestPermission(string3, string4, string5, (PersistableBundle)object);
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.hasRestrictionsProvider() ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getApplicationRestrictions(((Parcel)object).readString());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Bundle)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IRestrictionsManager {
            public static IRestrictionsManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public Intent createLocalApprovalIntent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Intent intent = Stub.getDefaultImpl().createLocalApprovalIntent();
                        parcel.recycle();
                        parcel2.recycle();
                        return intent;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return intent;
            }

            @Override
            public Bundle getApplicationRestrictions(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getApplicationRestrictions((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean hasRestrictionsProvider() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().hasRestrictionsProvider();
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
            public void notifyPermissionResponse(String string2, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPermissionResponse(string2, persistableBundle);
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
            public void requestPermission(String string2, String string3, String string4, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestPermission(string2, string3, string4, persistableBundle);
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

