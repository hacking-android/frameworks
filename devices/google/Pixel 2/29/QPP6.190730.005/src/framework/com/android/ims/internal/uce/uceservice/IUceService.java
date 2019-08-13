/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.uceservice;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;
import com.android.ims.internal.uce.uceservice.IUceListener;

public interface IUceService
extends IInterface {
    @UnsupportedAppUsage
    public int createOptionsService(IOptionsListener var1, UceLong var2) throws RemoteException;

    public int createOptionsServiceForSubscription(IOptionsListener var1, UceLong var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public int createPresenceService(IPresenceListener var1, UceLong var2) throws RemoteException;

    public int createPresenceServiceForSubscription(IPresenceListener var1, UceLong var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void destroyOptionsService(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void destroyPresenceService(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public IOptionsService getOptionsService() throws RemoteException;

    public IOptionsService getOptionsServiceForSubscription(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public IPresenceService getPresenceService() throws RemoteException;

    public IPresenceService getPresenceServiceForSubscription(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getServiceStatus() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isServiceStarted() throws RemoteException;

    @UnsupportedAppUsage
    public boolean startService(IUceListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean stopService() throws RemoteException;

    public static class Default
    implements IUceService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
            return 0;
        }

        @Override
        public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
            return 0;
        }

        @Override
        public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void destroyOptionsService(int n) throws RemoteException {
        }

        @Override
        public void destroyPresenceService(int n) throws RemoteException {
        }

        @Override
        public IOptionsService getOptionsService() throws RemoteException {
            return null;
        }

        @Override
        public IOptionsService getOptionsServiceForSubscription(String string2) throws RemoteException {
            return null;
        }

        @Override
        public IPresenceService getPresenceService() throws RemoteException {
            return null;
        }

        @Override
        public IPresenceService getPresenceServiceForSubscription(String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean getServiceStatus() throws RemoteException {
            return false;
        }

        @Override
        public boolean isServiceStarted() throws RemoteException {
            return false;
        }

        @Override
        public boolean startService(IUceListener iUceListener) throws RemoteException {
            return false;
        }

        @Override
        public boolean stopService() throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUceService {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.uceservice.IUceService";
        static final int TRANSACTION_createOptionsService = 4;
        static final int TRANSACTION_createOptionsServiceForSubscription = 5;
        static final int TRANSACTION_createPresenceService = 7;
        static final int TRANSACTION_createPresenceServiceForSubscription = 8;
        static final int TRANSACTION_destroyOptionsService = 6;
        static final int TRANSACTION_destroyPresenceService = 9;
        static final int TRANSACTION_getOptionsService = 13;
        static final int TRANSACTION_getOptionsServiceForSubscription = 14;
        static final int TRANSACTION_getPresenceService = 11;
        static final int TRANSACTION_getPresenceServiceForSubscription = 12;
        static final int TRANSACTION_getServiceStatus = 10;
        static final int TRANSACTION_isServiceStarted = 3;
        static final int TRANSACTION_startService = 1;
        static final int TRANSACTION_stopService = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUceService) {
                return (IUceService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "getOptionsServiceForSubscription";
                }
                case 13: {
                    return "getOptionsService";
                }
                case 12: {
                    return "getPresenceServiceForSubscription";
                }
                case 11: {
                    return "getPresenceService";
                }
                case 10: {
                    return "getServiceStatus";
                }
                case 9: {
                    return "destroyPresenceService";
                }
                case 8: {
                    return "createPresenceServiceForSubscription";
                }
                case 7: {
                    return "createPresenceService";
                }
                case 6: {
                    return "destroyOptionsService";
                }
                case 5: {
                    return "createOptionsServiceForSubscription";
                }
                case 4: {
                    return "createOptionsService";
                }
                case 3: {
                    return "isServiceStarted";
                }
                case 2: {
                    return "stopService";
                }
                case 1: 
            }
            return "startService";
        }

        public static boolean setDefaultImpl(IUceService iUceService) {
            if (Proxy.sDefaultImpl == null && iUceService != null) {
                Proxy.sDefaultImpl = iUceService;
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
                IInterface iInterface = null;
                Object var6_6 = null;
                Object var7_7 = null;
                Object object2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = this.getOptionsServiceForSubscription(((Parcel)object).readString());
                        parcel.writeNoException();
                        object = object2;
                        if (iInterface != null) {
                            object = iInterface.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getOptionsService();
                        parcel.writeNoException();
                        object = iInterface;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getPresenceServiceForSubscription(((Parcel)object).readString());
                        parcel.writeNoException();
                        object = var6_6;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getPresenceService();
                        parcel.writeNoException();
                        object = var7_7;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getServiceStatus() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroyPresenceService(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = IPresenceListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createPresenceServiceForSubscription((IPresenceListener)iInterface, (UceLong)object2, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        if (object2 != null) {
                            parcel.writeInt(1);
                            ((UceLong)object2).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IPresenceListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createPresenceService((IPresenceListener)object2, (UceLong)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UceLong)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroyOptionsService(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = IOptionsListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createOptionsServiceForSubscription((IOptionsListener)iInterface, (UceLong)object2, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        if (object2 != null) {
                            parcel.writeInt(1);
                            ((UceLong)object2).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IOptionsListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? UceLong.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createOptionsService((IOptionsListener)object2, (UceLong)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UceLong)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isServiceStarted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.stopService() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.startService(IUceListener.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUceService {
            public static IUceService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOptionsListener != null ? iOptionsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (uceLong != null) {
                        parcel.writeInt(1);
                        uceLong.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createOptionsService(iOptionsListener, uceLong);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() == 0) return n;
                    uceLong.readFromParcel(parcel2);
                    return n;
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
            public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOptionsListener != null ? iOptionsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (uceLong != null) {
                        parcel.writeInt(1);
                        uceLong.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createOptionsServiceForSubscription(iOptionsListener, uceLong, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() == 0) return n;
                    uceLong.readFromParcel(parcel2);
                    return n;
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
            public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPresenceListener != null ? iPresenceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (uceLong != null) {
                        parcel.writeInt(1);
                        uceLong.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createPresenceService(iPresenceListener, uceLong);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() == 0) return n;
                    uceLong.readFromParcel(parcel2);
                    return n;
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
            public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPresenceListener != null ? iPresenceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (uceLong != null) {
                        parcel.writeInt(1);
                        uceLong.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createPresenceServiceForSubscription(iPresenceListener, uceLong, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() == 0) return n;
                    uceLong.readFromParcel(parcel2);
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void destroyOptionsService(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyOptionsService(n);
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
            public void destroyPresenceService(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyPresenceService(n);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public IOptionsService getOptionsService() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IOptionsService iOptionsService = Stub.getDefaultImpl().getOptionsService();
                        return iOptionsService;
                    }
                    parcel2.readException();
                    IOptionsService iOptionsService = IOptionsService.Stub.asInterface(parcel2.readStrongBinder());
                    return iOptionsService;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IOptionsService getOptionsServiceForSubscription(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getOptionsServiceForSubscription((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = IOptionsService.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IPresenceService getPresenceService() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IPresenceService iPresenceService = Stub.getDefaultImpl().getPresenceService();
                        return iPresenceService;
                    }
                    parcel2.readException();
                    IPresenceService iPresenceService = IPresenceService.Stub.asInterface(parcel2.readStrongBinder());
                    return iPresenceService;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IPresenceService getPresenceServiceForSubscription(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPresenceServiceForSubscription((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = IPresenceService.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getServiceStatus() throws RemoteException {
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
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getServiceStatus();
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
            public boolean isServiceStarted() throws RemoteException {
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
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isServiceStarted();
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
            public boolean startService(IUceListener iUceListener) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iUceListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iUceListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().startService(iUceListener);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean stopService() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().stopService();
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

