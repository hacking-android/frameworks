/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;

public interface IImsService
extends IInterface {
    public void addRegistrationListener(int var1, int var2, IImsRegistrationListener var3) throws RemoteException;

    public void close(int var1) throws RemoteException;

    public ImsCallProfile createCallProfile(int var1, int var2, int var3) throws RemoteException;

    public IImsCallSession createCallSession(int var1, ImsCallProfile var2, IImsCallSessionListener var3) throws RemoteException;

    public IImsConfig getConfigInterface(int var1) throws RemoteException;

    public IImsEcbm getEcbmInterface(int var1) throws RemoteException;

    public IImsMultiEndpoint getMultiEndpointInterface(int var1) throws RemoteException;

    public IImsCallSession getPendingCallSession(int var1, String var2) throws RemoteException;

    public IImsUt getUtInterface(int var1) throws RemoteException;

    public boolean isConnected(int var1, int var2, int var3) throws RemoteException;

    public boolean isOpened(int var1) throws RemoteException;

    public int open(int var1, int var2, PendingIntent var3, IImsRegistrationListener var4) throws RemoteException;

    public void setRegistrationListener(int var1, IImsRegistrationListener var2) throws RemoteException;

    public void setUiTTYMode(int var1, int var2, Message var3) throws RemoteException;

    public void turnOffIms(int var1) throws RemoteException;

    public void turnOnIms(int var1) throws RemoteException;

    public static class Default
    implements IImsService {
        @Override
        public void addRegistrationListener(int n, int n2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close(int n) throws RemoteException {
        }

        @Override
        public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
            return null;
        }

        @Override
        public IImsConfig getConfigInterface(int n) throws RemoteException {
            return null;
        }

        @Override
        public IImsEcbm getEcbmInterface(int n) throws RemoteException {
            return null;
        }

        @Override
        public IImsMultiEndpoint getMultiEndpointInterface(int n) throws RemoteException {
            return null;
        }

        @Override
        public IImsCallSession getPendingCallSession(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IImsUt getUtInterface(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isConnected(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isOpened(int n) throws RemoteException {
            return false;
        }

        @Override
        public int open(int n, int n2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            return 0;
        }

        @Override
        public void setRegistrationListener(int n, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override
        public void setUiTTYMode(int n, int n2, Message message) throws RemoteException {
        }

        @Override
        public void turnOffIms(int n) throws RemoteException {
        }

        @Override
        public void turnOnIms(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsService {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsService";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_createCallProfile = 7;
        static final int TRANSACTION_createCallSession = 8;
        static final int TRANSACTION_getConfigInterface = 11;
        static final int TRANSACTION_getEcbmInterface = 14;
        static final int TRANSACTION_getMultiEndpointInterface = 16;
        static final int TRANSACTION_getPendingCallSession = 9;
        static final int TRANSACTION_getUtInterface = 10;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setRegistrationListener = 5;
        static final int TRANSACTION_setUiTTYMode = 15;
        static final int TRANSACTION_turnOffIms = 13;
        static final int TRANSACTION_turnOnIms = 12;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsService) {
                return (IImsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "getMultiEndpointInterface";
                }
                case 15: {
                    return "setUiTTYMode";
                }
                case 14: {
                    return "getEcbmInterface";
                }
                case 13: {
                    return "turnOffIms";
                }
                case 12: {
                    return "turnOnIms";
                }
                case 11: {
                    return "getConfigInterface";
                }
                case 10: {
                    return "getUtInterface";
                }
                case 9: {
                    return "getPendingCallSession";
                }
                case 8: {
                    return "createCallSession";
                }
                case 7: {
                    return "createCallProfile";
                }
                case 6: {
                    return "addRegistrationListener";
                }
                case 5: {
                    return "setRegistrationListener";
                }
                case 4: {
                    return "isOpened";
                }
                case 3: {
                    return "isConnected";
                }
                case 2: {
                    return "close";
                }
                case 1: 
            }
            return "open";
        }

        public static boolean setDefaultImpl(IImsService iImsService) {
            if (Proxy.sDefaultImpl == null && iImsService != null) {
                Proxy.sDefaultImpl = iImsService;
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
                Object var5_5 = null;
                Object var6_6 = null;
                Object var7_7 = null;
                Object var8_8 = null;
                IImsMultiEndpoint iImsMultiEndpoint = null;
                Object object2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iImsMultiEndpoint = this.getMultiEndpointInterface(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = object2;
                        if (iImsMultiEndpoint != null) {
                            object = iImsMultiEndpoint.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Message.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUiTTYMode(n2, n, (Message)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getEcbmInterface(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = var5_5;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.turnOffIms(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.turnOnIms(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getConfigInterface(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = var6_6;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getUtInterface(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = var7_7;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getPendingCallSession(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        object = var8_8;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = this.createCallSession(n, (ImsCallProfile)object2, IImsCallSessionListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = iImsMultiEndpoint;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createCallProfile(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ImsCallProfile)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addRegistrationListener(((Parcel)object).readInt(), ((Parcel)object).readInt(), IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRegistrationListener(((Parcel)object).readInt(), IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isOpened(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConnected(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.close(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                n2 = ((Parcel)object).readInt();
                object2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.open(n, n2, (PendingIntent)object2, IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsService {
            public static IImsService sDefaultImpl;
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
            public void addRegistrationListener(int n, int n2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addRegistrationListener(n, n2, iImsRegistrationListener);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close(n);
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
            public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ImsCallProfile imsCallProfile = Stub.getDefaultImpl().createCallProfile(n, n2, n3);
                        parcel2.recycle();
                        parcel.recycle();
                        return imsCallProfile;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ImsCallProfile imsCallProfile = parcel2.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return imsCallProfile;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IImsCallSession createCallSession(int n, ImsCallProfile object, IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ImsCallProfile)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iImsCallSessionListener != null ? iImsCallSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createCallSession(n, (ImsCallProfile)object, iImsCallSessionListener);
                        return object;
                    }
                    parcel2.readException();
                    object = IImsCallSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsConfig getConfigInterface(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsConfig iImsConfig = Stub.getDefaultImpl().getConfigInterface(n);
                        return iImsConfig;
                    }
                    parcel2.readException();
                    IImsConfig iImsConfig = IImsConfig.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsConfig;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsEcbm getEcbmInterface(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsEcbm iImsEcbm = Stub.getDefaultImpl().getEcbmInterface(n);
                        return iImsEcbm;
                    }
                    parcel2.readException();
                    IImsEcbm iImsEcbm = IImsEcbm.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsEcbm;
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
            public IImsMultiEndpoint getMultiEndpointInterface(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMultiEndpoint iImsMultiEndpoint = Stub.getDefaultImpl().getMultiEndpointInterface(n);
                        return iImsMultiEndpoint;
                    }
                    parcel2.readException();
                    IImsMultiEndpoint iImsMultiEndpoint = IImsMultiEndpoint.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMultiEndpoint;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsCallSession getPendingCallSession(int n, String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPendingCallSession(n, (String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = IImsCallSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsUt getUtInterface(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsUt iImsUt = Stub.getDefaultImpl().getUtInterface(n);
                        return iImsUt;
                    }
                    parcel2.readException();
                    IImsUt iImsUt = IImsUt.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsUt;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isConnected(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConnected(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isOpened(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isOpened(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int open(int n, int n2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().open(n, n2, pendingIntent, iImsRegistrationListener);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
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
            public void setRegistrationListener(int n, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRegistrationListener(n, iImsRegistrationListener);
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
            public void setUiTTYMode(int n, int n2, Message message) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (message != null) {
                        parcel.writeInt(1);
                        message.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUiTTYMode(n, n2, message);
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
            public void turnOffIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOffIms(n);
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
            public void turnOnIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOnIms(n);
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

