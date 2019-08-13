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
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;

public interface IImsMMTelFeature
extends IInterface {
    public void addRegistrationListener(IImsRegistrationListener var1) throws RemoteException;

    public ImsCallProfile createCallProfile(int var1, int var2, int var3) throws RemoteException;

    public IImsCallSession createCallSession(int var1, ImsCallProfile var2) throws RemoteException;

    public void endSession(int var1) throws RemoteException;

    public IImsConfig getConfigInterface() throws RemoteException;

    public IImsEcbm getEcbmInterface() throws RemoteException;

    public int getFeatureStatus() throws RemoteException;

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    public IImsCallSession getPendingCallSession(int var1, String var2) throws RemoteException;

    public IImsUt getUtInterface() throws RemoteException;

    public boolean isConnected(int var1, int var2) throws RemoteException;

    public boolean isOpened() throws RemoteException;

    public void removeRegistrationListener(IImsRegistrationListener var1) throws RemoteException;

    public void setUiTTYMode(int var1, Message var2) throws RemoteException;

    public int startSession(PendingIntent var1, IImsRegistrationListener var2) throws RemoteException;

    public void turnOffIms() throws RemoteException;

    public void turnOnIms() throws RemoteException;

    public static class Default
    implements IImsMMTelFeature {
        @Override
        public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile) throws RemoteException {
            return null;
        }

        @Override
        public void endSession(int n) throws RemoteException {
        }

        @Override
        public IImsConfig getConfigInterface() throws RemoteException {
            return null;
        }

        @Override
        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        @Override
        public int getFeatureStatus() throws RemoteException {
            return 0;
        }

        @Override
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        @Override
        public IImsCallSession getPendingCallSession(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        @Override
        public boolean isConnected(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isOpened() throws RemoteException {
            return false;
        }

        @Override
        public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override
        public void setUiTTYMode(int n, Message message) throws RemoteException {
        }

        @Override
        public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            return 0;
        }

        @Override
        public void turnOffIms() throws RemoteException {
        }

        @Override
        public void turnOnIms() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsMMTelFeature {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsMMTelFeature";
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_createCallProfile = 8;
        static final int TRANSACTION_createCallSession = 9;
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_getConfigInterface = 12;
        static final int TRANSACTION_getEcbmInterface = 15;
        static final int TRANSACTION_getFeatureStatus = 5;
        static final int TRANSACTION_getMultiEndpointInterface = 17;
        static final int TRANSACTION_getPendingCallSession = 10;
        static final int TRANSACTION_getUtInterface = 11;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_removeRegistrationListener = 7;
        static final int TRANSACTION_setUiTTYMode = 16;
        static final int TRANSACTION_startSession = 1;
        static final int TRANSACTION_turnOffIms = 14;
        static final int TRANSACTION_turnOnIms = 13;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsMMTelFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsMMTelFeature) {
                return (IImsMMTelFeature)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsMMTelFeature getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 17: {
                    return "getMultiEndpointInterface";
                }
                case 16: {
                    return "setUiTTYMode";
                }
                case 15: {
                    return "getEcbmInterface";
                }
                case 14: {
                    return "turnOffIms";
                }
                case 13: {
                    return "turnOnIms";
                }
                case 12: {
                    return "getConfigInterface";
                }
                case 11: {
                    return "getUtInterface";
                }
                case 10: {
                    return "getPendingCallSession";
                }
                case 9: {
                    return "createCallSession";
                }
                case 8: {
                    return "createCallProfile";
                }
                case 7: {
                    return "removeRegistrationListener";
                }
                case 6: {
                    return "addRegistrationListener";
                }
                case 5: {
                    return "getFeatureStatus";
                }
                case 4: {
                    return "isOpened";
                }
                case 3: {
                    return "isConnected";
                }
                case 2: {
                    return "endSession";
                }
                case 1: 
            }
            return "startSession";
        }

        public static boolean setDefaultImpl(IImsMMTelFeature iImsMMTelFeature) {
            if (Proxy.sDefaultImpl == null && iImsMMTelFeature != null) {
                Proxy.sDefaultImpl = iImsMMTelFeature;
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
                Object object2 = null;
                IImsCallSession iImsCallSession = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getMultiEndpointInterface();
                        parcel.writeNoException();
                        object = iImsCallSession;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Message.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUiTTYMode(n, (Message)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getEcbmInterface();
                        parcel.writeNoException();
                        object = var5_5;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.turnOffIms();
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.turnOnIms();
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getConfigInterface();
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
                        object2 = this.getUtInterface();
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
                        object2 = this.getPendingCallSession(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        object = var8_8;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        iImsCallSession = this.createCallSession(n, (ImsCallProfile)object);
                        parcel.writeNoException();
                        object = object2;
                        if (iImsCallSession != null) {
                            object = iImsCallSession.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 8: {
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
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeRegistrationListener(IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addRegistrationListener(IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFeatureStatus();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isOpened() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConnected(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.endSession(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.startSession((PendingIntent)object2, IImsRegistrationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsMMTelFeature {
            public static IImsMMTelFeature sDefaultImpl;
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
            public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addRegistrationListener(iImsRegistrationListener);
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
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
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

            @Override
            public IImsCallSession createCallSession(int n, ImsCallProfile object) throws RemoteException {
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
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createCallSession(n, (ImsCallProfile)object);
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
            public void endSession(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endSession(n);
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
            public IImsConfig getConfigInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsConfig iImsConfig = Stub.getDefaultImpl().getConfigInterface();
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
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsEcbm iImsEcbm = Stub.getDefaultImpl().getEcbmInterface();
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

            @Override
            public int getFeatureStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getFeatureStatus();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMultiEndpoint iImsMultiEndpoint = Stub.getDefaultImpl().getMultiEndpointInterface();
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
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public IImsUt getUtInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsUt iImsUt = Stub.getDefaultImpl().getUtInterface();
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
            public boolean isConnected(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConnected(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isOpened() throws RemoteException {
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
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isOpened();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeRegistrationListener(iImsRegistrationListener);
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
            public void setUiTTYMode(int n, Message message) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (message != null) {
                        parcel.writeInt(1);
                        message.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUiTTYMode(n, message);
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
            public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iImsRegistrationListener != null ? iImsRegistrationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startSession(pendingIntent, iImsRegistrationListener);
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
            public void turnOffIms() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOffIms();
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
            public void turnOnIms() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOnIms();
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

