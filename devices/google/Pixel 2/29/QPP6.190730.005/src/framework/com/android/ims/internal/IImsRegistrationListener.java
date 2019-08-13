/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;

public interface IImsRegistrationListener
extends IInterface {
    @UnsupportedAppUsage
    public void registrationAssociatedUriChanged(Uri[] var1) throws RemoteException;

    @UnsupportedAppUsage
    public void registrationChangeFailed(int var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void registrationConnected() throws RemoteException;

    @UnsupportedAppUsage
    public void registrationConnectedWithRadioTech(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void registrationDisconnected(ImsReasonInfo var1) throws RemoteException;

    @UnsupportedAppUsage
    public void registrationFeatureCapabilityChanged(int var1, int[] var2, int[] var3) throws RemoteException;

    public void registrationProgressing() throws RemoteException;

    @UnsupportedAppUsage
    public void registrationProgressingWithRadioTech(int var1) throws RemoteException;

    public void registrationResumed() throws RemoteException;

    public void registrationServiceCapabilityChanged(int var1, int var2) throws RemoteException;

    public void registrationSuspended() throws RemoteException;

    @UnsupportedAppUsage
    public void voiceMessageCountUpdate(int var1) throws RemoteException;

    public static class Default
    implements IImsRegistrationListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void registrationAssociatedUriChanged(Uri[] arruri) throws RemoteException {
        }

        @Override
        public void registrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void registrationConnected() throws RemoteException {
        }

        @Override
        public void registrationConnectedWithRadioTech(int n) throws RemoteException {
        }

        @Override
        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void registrationFeatureCapabilityChanged(int n, int[] arrn, int[] arrn2) throws RemoteException {
        }

        @Override
        public void registrationProgressing() throws RemoteException {
        }

        @Override
        public void registrationProgressingWithRadioTech(int n) throws RemoteException {
        }

        @Override
        public void registrationResumed() throws RemoteException {
        }

        @Override
        public void registrationServiceCapabilityChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void registrationSuspended() throws RemoteException {
        }

        @Override
        public void voiceMessageCountUpdate(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsRegistrationListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsRegistrationListener";
        static final int TRANSACTION_registrationAssociatedUriChanged = 11;
        static final int TRANSACTION_registrationChangeFailed = 12;
        static final int TRANSACTION_registrationConnected = 1;
        static final int TRANSACTION_registrationConnectedWithRadioTech = 3;
        static final int TRANSACTION_registrationDisconnected = 5;
        static final int TRANSACTION_registrationFeatureCapabilityChanged = 9;
        static final int TRANSACTION_registrationProgressing = 2;
        static final int TRANSACTION_registrationProgressingWithRadioTech = 4;
        static final int TRANSACTION_registrationResumed = 6;
        static final int TRANSACTION_registrationServiceCapabilityChanged = 8;
        static final int TRANSACTION_registrationSuspended = 7;
        static final int TRANSACTION_voiceMessageCountUpdate = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsRegistrationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsRegistrationListener) {
                return (IImsRegistrationListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsRegistrationListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "registrationChangeFailed";
                }
                case 11: {
                    return "registrationAssociatedUriChanged";
                }
                case 10: {
                    return "voiceMessageCountUpdate";
                }
                case 9: {
                    return "registrationFeatureCapabilityChanged";
                }
                case 8: {
                    return "registrationServiceCapabilityChanged";
                }
                case 7: {
                    return "registrationSuspended";
                }
                case 6: {
                    return "registrationResumed";
                }
                case 5: {
                    return "registrationDisconnected";
                }
                case 4: {
                    return "registrationProgressingWithRadioTech";
                }
                case 3: {
                    return "registrationConnectedWithRadioTech";
                }
                case 2: {
                    return "registrationProgressing";
                }
                case 1: 
            }
            return "registrationConnected";
        }

        public static boolean setDefaultImpl(IImsRegistrationListener iImsRegistrationListener) {
            if (Proxy.sDefaultImpl == null && iImsRegistrationListener != null) {
                Proxy.sDefaultImpl = iImsRegistrationListener;
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
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registrationChangeFailed(n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationAssociatedUriChanged(((Parcel)object).createTypedArray(Uri.CREATOR));
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.voiceMessageCountUpdate(((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationFeatureCapabilityChanged(((Parcel)object).readInt(), ((Parcel)object).createIntArray(), ((Parcel)object).createIntArray());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationServiceCapabilityChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationSuspended();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationResumed();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registrationDisconnected((ImsReasonInfo)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationProgressingWithRadioTech(((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationConnectedWithRadioTech(((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registrationProgressing();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.registrationConnected();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsRegistrationListener {
            public static IImsRegistrationListener sDefaultImpl;
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
            public void registrationAssociatedUriChanged(Uri[] arruri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arruri, 0);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationAssociatedUriChanged(arruri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationChangeFailed(n, imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationConnected() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationConnected();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationConnectedWithRadioTech(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationConnectedWithRadioTech(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationDisconnected(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationFeatureCapabilityChanged(int n, int[] arrn, int[] arrn2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    parcel.writeIntArray(arrn2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationFeatureCapabilityChanged(n, arrn, arrn2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationProgressing() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationProgressing();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationProgressingWithRadioTech(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationProgressingWithRadioTech(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationResumed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationResumed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationServiceCapabilityChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationServiceCapabilityChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void registrationSuspended() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registrationSuspended();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void voiceMessageCountUpdate(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().voiceMessageCountUpdate(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

