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
import com.android.ims.internal.uce.presence.PresCmdStatus;
import com.android.ims.internal.uce.presence.PresPublishTriggerType;
import com.android.ims.internal.uce.presence.PresResInfo;
import com.android.ims.internal.uce.presence.PresRlmiInfo;
import com.android.ims.internal.uce.presence.PresSipResponse;
import com.android.ims.internal.uce.presence.PresTupleInfo;

public interface IPresenceListener
extends IInterface {
    @UnsupportedAppUsage
    public void capInfoReceived(String var1, PresTupleInfo[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public void cmdStatus(PresCmdStatus var1) throws RemoteException;

    @UnsupportedAppUsage
    public void getVersionCb(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void listCapInfoReceived(PresRlmiInfo var1, PresResInfo[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public void publishTriggering(PresPublishTriggerType var1) throws RemoteException;

    @UnsupportedAppUsage
    public void serviceAvailable(StatusCode var1) throws RemoteException;

    @UnsupportedAppUsage
    public void serviceUnAvailable(StatusCode var1) throws RemoteException;

    @UnsupportedAppUsage
    public void sipResponseReceived(PresSipResponse var1) throws RemoteException;

    @UnsupportedAppUsage
    public void unpublishMessageSent() throws RemoteException;

    public static class Default
    implements IPresenceListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void capInfoReceived(String string2, PresTupleInfo[] arrpresTupleInfo) throws RemoteException {
        }

        @Override
        public void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException {
        }

        @Override
        public void getVersionCb(String string2) throws RemoteException {
        }

        @Override
        public void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] arrpresResInfo) throws RemoteException {
        }

        @Override
        public void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException {
        }

        @Override
        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override
        public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override
        public void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException {
        }

        @Override
        public void unpublishMessageSent() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPresenceListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceListener";
        static final int TRANSACTION_capInfoReceived = 7;
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_listCapInfoReceived = 8;
        static final int TRANSACTION_publishTriggering = 4;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnAvailable = 3;
        static final int TRANSACTION_sipResponseReceived = 6;
        static final int TRANSACTION_unpublishMessageSent = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPresenceListener) {
                return (IPresenceListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPresenceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "unpublishMessageSent";
                }
                case 8: {
                    return "listCapInfoReceived";
                }
                case 7: {
                    return "capInfoReceived";
                }
                case 6: {
                    return "sipResponseReceived";
                }
                case 5: {
                    return "cmdStatus";
                }
                case 4: {
                    return "publishTriggering";
                }
                case 3: {
                    return "serviceUnAvailable";
                }
                case 2: {
                    return "serviceAvailable";
                }
                case 1: 
            }
            return "getVersionCb";
        }

        public static boolean setDefaultImpl(IPresenceListener iPresenceListener) {
            if (Proxy.sDefaultImpl == null && iPresenceListener != null) {
                Proxy.sDefaultImpl = iPresenceListener;
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
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unpublishMessageSent();
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PresRlmiInfo presRlmiInfo = ((Parcel)object).readInt() != 0 ? PresRlmiInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.listCapInfoReceived(presRlmiInfo, ((Parcel)object).createTypedArray(PresResInfo.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.capInfoReceived(((Parcel)object).readString(), ((Parcel)object).createTypedArray(PresTupleInfo.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PresSipResponse.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sipResponseReceived((PresSipResponse)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PresCmdStatus.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cmdStatus((PresCmdStatus)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PresPublishTriggerType.CREATOR.createFromParcel((Parcel)object) : null;
                        this.publishTriggering((PresPublishTriggerType)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? StatusCode.CREATOR.createFromParcel((Parcel)object) : null;
                        this.serviceUnAvailable((StatusCode)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? StatusCode.CREATOR.createFromParcel((Parcel)object) : null;
                        this.serviceAvailable((StatusCode)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.getVersionCb(((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPresenceListener {
            public static IPresenceListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void capInfoReceived(String string2, PresTupleInfo[] arrpresTupleInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedArray((Parcelable[])arrpresTupleInfo, 0);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().capInfoReceived(string2, arrpresTupleInfo);
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
            public void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (presCmdStatus != null) {
                        parcel.writeInt(1);
                        presCmdStatus.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cmdStatus(presCmdStatus);
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
            public void getVersionCb(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getVersionCb(string2);
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
            public void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] arrpresResInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (presRlmiInfo != null) {
                        parcel.writeInt(1);
                        presRlmiInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedArray((Parcelable[])arrpresResInfo, 0);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().listCapInfoReceived(presRlmiInfo, arrpresResInfo);
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
            public void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (presPublishTriggerType != null) {
                        parcel.writeInt(1);
                        presPublishTriggerType.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publishTriggering(presPublishTriggerType);
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
            public void serviceAvailable(StatusCode statusCode) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (statusCode != null) {
                        parcel.writeInt(1);
                        statusCode.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceAvailable(statusCode);
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
            public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (statusCode != null) {
                        parcel.writeInt(1);
                        statusCode.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceUnAvailable(statusCode);
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
            public void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (presSipResponse != null) {
                        parcel.writeInt(1);
                        presSipResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sipResponseReceived(presSipResponse);
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
            public void unpublishMessageSent() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unpublishMessageSent();
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

