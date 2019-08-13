/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.PresCapInfo;
import com.android.ims.internal.uce.presence.PresServiceInfo;

public interface IPresenceService
extends IInterface {
    @UnsupportedAppUsage
    public StatusCode addListener(int var1, IPresenceListener var2, UceLong var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getContactCap(int var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getContactListCap(int var1, String[] var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getVersion(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode publishMyCap(int var1, PresCapInfo var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode reenableService(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode removeListener(int var1, UceLong var2) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode setNewFeatureTag(int var1, String var2, PresServiceInfo var3, int var4) throws RemoteException;

    public static class Default
    implements IPresenceService {
        @Override
        public StatusCode addListener(int n, IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public StatusCode getContactCap(int n, String string2, int n2) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode getContactListCap(int n, String[] arrstring, int n2) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode getVersion(int n) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode publishMyCap(int n, PresCapInfo presCapInfo, int n2) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode reenableService(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode removeListener(int n, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode setNewFeatureTag(int n, String string2, PresServiceInfo presServiceInfo, int n2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPresenceService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_publishMyCap = 5;
        static final int TRANSACTION_reenableService = 4;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setNewFeatureTag = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPresenceService) {
                return (IPresenceService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPresenceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "setNewFeatureTag";
                }
                case 7: {
                    return "getContactListCap";
                }
                case 6: {
                    return "getContactCap";
                }
                case 5: {
                    return "publishMyCap";
                }
                case 4: {
                    return "reenableService";
                }
                case 3: {
                    return "removeListener";
                }
                case 2: {
                    return "addListener";
                }
                case 1: 
            }
            return "getVersion";
        }

        public static boolean setDefaultImpl(IPresenceService iPresenceService) {
            if (Proxy.sDefaultImpl == null && iPresenceService != null) {
                Proxy.sDefaultImpl = iPresenceService;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        PresServiceInfo presServiceInfo = ((Parcel)object).readInt() != 0 ? PresServiceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.setNewFeatureTag(n, string2, presServiceInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContactListCap(((Parcel)object).readInt(), ((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContactCap(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        PresCapInfo presCapInfo = ((Parcel)object).readInt() != 0 ? PresCapInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.publishMyCap(n, presCapInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.reenableService(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.removeListener(n, (UceLong)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Object object2 = IPresenceListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = this.addListener(n, (IPresenceListener)object2, (UceLong)object);
                        parcel.writeNoException();
                        if (object2 != null) {
                            parcel.writeInt(1);
                            ((StatusCode)object2).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UceLong)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getVersion(((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((StatusCode)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPresenceService {
            public static IPresenceService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StatusCode addListener(int n, IPresenceListener object, UceLong uceLong) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    Object var6_7 = null;
                    IBinder iBinder = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (uceLong != null) {
                        parcel.writeInt(1);
                        uceLong.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().addListener(n, (IPresenceListener)object, uceLong);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel2) : var6_7;
                    if (parcel2.readInt() == 0) return object;
                    uceLong.readFromParcel(parcel2);
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public StatusCode getContactCap(int n, String object, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeString((String)object);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getContactCap(n, (String)object, n2);
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
                object = parcel2.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public StatusCode getContactListCap(int n, String[] object, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeStringArray((String[])object);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getContactListCap(n, (String[])object, n2);
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
                object = parcel2.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public StatusCode getVersion(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        StatusCode statusCode = Stub.getDefaultImpl().getVersion(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return statusCode;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                StatusCode statusCode = parcel2.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return statusCode;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StatusCode publishMyCap(int n, PresCapInfo parcelable, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_6;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PresCapInfo)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StatusCode statusCode = Stub.getDefaultImpl().publishMyCap(n, (PresCapInfo)parcelable, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return statusCode;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        StatusCode statusCode = StatusCode.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var2_5 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var2_6;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public StatusCode reenableService(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        StatusCode statusCode = Stub.getDefaultImpl().reenableService(n, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return statusCode;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                StatusCode statusCode = parcel.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return statusCode;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StatusCode removeListener(int n, UceLong parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_6;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((UceLong)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StatusCode statusCode = Stub.getDefaultImpl().removeListener(n, (UceLong)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return statusCode;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        StatusCode statusCode = StatusCode.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var2_5 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var2_6;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StatusCode setNewFeatureTag(int n, String object, PresServiceInfo presServiceInfo, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)object);
                    if (presServiceInfo != null) {
                        parcel.writeInt(1);
                        presServiceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().setNewFeatureTag(n, (String)object, presServiceInfo, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel2) : null;
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
        }

    }

}

