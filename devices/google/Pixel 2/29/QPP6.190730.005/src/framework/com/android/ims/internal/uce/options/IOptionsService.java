/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.OptionsCapInfo;

public interface IOptionsService
extends IInterface {
    @UnsupportedAppUsage
    public StatusCode addListener(int var1, IOptionsListener var2, UceLong var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getContactCap(int var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getContactListCap(int var1, String[] var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getMyInfo(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode getVersion(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode removeListener(int var1, UceLong var2) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode responseIncomingOptions(int var1, int var2, int var3, String var4, OptionsCapInfo var5, boolean var6) throws RemoteException;

    @UnsupportedAppUsage
    public StatusCode setMyInfo(int var1, CapInfo var2, int var3) throws RemoteException;

    public static class Default
    implements IOptionsService {
        @Override
        public StatusCode addListener(int n, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
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
        public StatusCode getMyInfo(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode getVersion(int n) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode removeListener(int n, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode responseIncomingOptions(int n, int n2, int n3, String string2, OptionsCapInfo optionsCapInfo, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public StatusCode setMyInfo(int n, CapInfo capInfo, int n2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOptionsService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getMyInfo = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_responseIncomingOptions = 8;
        static final int TRANSACTION_setMyInfo = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOptionsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOptionsService) {
                return (IOptionsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOptionsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "responseIncomingOptions";
                }
                case 7: {
                    return "getContactListCap";
                }
                case 6: {
                    return "getContactCap";
                }
                case 5: {
                    return "getMyInfo";
                }
                case 4: {
                    return "setMyInfo";
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

        public static boolean setDefaultImpl(IOptionsService iOptionsService) {
            if (Proxy.sDefaultImpl == null && iOptionsService != null) {
                Proxy.sDefaultImpl = iOptionsService;
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
                        int n3 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        OptionsCapInfo optionsCapInfo = ((Parcel)object).readInt() != 0 ? OptionsCapInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = this.responseIncomingOptions(n3, n2, n, string2, optionsCapInfo, bl);
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
                        object = this.getMyInfo(((Parcel)object).readInt(), ((Parcel)object).readInt());
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
                        n = ((Parcel)object).readInt();
                        CapInfo capInfo = ((Parcel)object).readInt() != 0 ? CapInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.setMyInfo(n, capInfo, ((Parcel)object).readInt());
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
                        Object object2 = IOptionsListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = this.addListener(n, (IOptionsListener)object2, (UceLong)object);
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
        implements IOptionsService {
            public static IOptionsService sDefaultImpl;
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
            public StatusCode addListener(int n, IOptionsListener object, UceLong uceLong) throws RemoteException {
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
                        object = Stub.getDefaultImpl().addListener(n, (IOptionsListener)object, uceLong);
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
            public StatusCode getMyInfo(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        StatusCode statusCode = Stub.getDefaultImpl().getMyInfo(n, n2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public StatusCode responseIncomingOptions(int n, int n2, int n3, String object, OptionsCapInfo optionsCapInfo, boolean bl) throws RemoteException {
                void var4_11;
                Parcel parcel;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString((String)object);
                        int n4 = 1;
                        if (optionsCapInfo != null) {
                            parcel2.writeInt(1);
                            optionsCapInfo.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!bl) {
                            n4 = 0;
                        }
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(8, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().responseIncomingOptions(n, n2, n3, (String)object, optionsCapInfo, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? StatusCode.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var4_11;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StatusCode setMyInfo(int n, CapInfo parcelable, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_6;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((CapInfo)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StatusCode statusCode = Stub.getDefaultImpl().setMyInfo(n, (CapInfo)parcelable, (int)var3_8);
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
        }

    }

}

