/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import com.android.ims.internal.IImsUt;

public interface IImsUtListener
extends IInterface {
    public void onSupplementaryServiceIndication(ImsSsData var1) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationCallBarringQueried(IImsUt var1, int var2, ImsSsInfo[] var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationCallForwardQueried(IImsUt var1, int var2, ImsCallForwardInfo[] var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationCallWaitingQueried(IImsUt var1, int var2, ImsSsInfo[] var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationQueried(IImsUt var1, int var2, Bundle var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationQueryFailed(IImsUt var1, int var2, ImsReasonInfo var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationUpdateFailed(IImsUt var1, int var2, ImsReasonInfo var3) throws RemoteException;

    @UnsupportedAppUsage
    public void utConfigurationUpdated(IImsUt var1, int var2) throws RemoteException;

    public static class Default
    implements IImsUtListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException {
        }

        @Override
        public void utConfigurationCallBarringQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
        }

        @Override
        public void utConfigurationCallForwardQueried(IImsUt iImsUt, int n, ImsCallForwardInfo[] arrimsCallForwardInfo) throws RemoteException {
        }

        @Override
        public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
        }

        @Override
        public void utConfigurationQueried(IImsUt iImsUt, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void utConfigurationQueryFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void utConfigurationUpdateFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void utConfigurationUpdated(IImsUt iImsUt, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsUtListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsUtListener";
        static final int TRANSACTION_onSupplementaryServiceIndication = 8;
        static final int TRANSACTION_utConfigurationCallBarringQueried = 5;
        static final int TRANSACTION_utConfigurationCallForwardQueried = 6;
        static final int TRANSACTION_utConfigurationCallWaitingQueried = 7;
        static final int TRANSACTION_utConfigurationQueried = 3;
        static final int TRANSACTION_utConfigurationQueryFailed = 4;
        static final int TRANSACTION_utConfigurationUpdateFailed = 2;
        static final int TRANSACTION_utConfigurationUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsUtListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsUtListener) {
                return (IImsUtListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsUtListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "onSupplementaryServiceIndication";
                }
                case 7: {
                    return "utConfigurationCallWaitingQueried";
                }
                case 6: {
                    return "utConfigurationCallForwardQueried";
                }
                case 5: {
                    return "utConfigurationCallBarringQueried";
                }
                case 4: {
                    return "utConfigurationQueryFailed";
                }
                case 3: {
                    return "utConfigurationQueried";
                }
                case 2: {
                    return "utConfigurationUpdateFailed";
                }
                case 1: 
            }
            return "utConfigurationUpdated";
        }

        public static boolean setDefaultImpl(IImsUtListener iImsUtListener) {
            if (Proxy.sDefaultImpl == null && iImsUtListener != null) {
                Proxy.sDefaultImpl = iImsUtListener;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsSsData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSupplementaryServiceIndication((ImsSsData)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.utConfigurationCallWaitingQueried(IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).createTypedArray(ImsSsInfo.CREATOR));
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.utConfigurationCallForwardQueried(IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).createTypedArray(ImsCallForwardInfo.CREATOR));
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.utConfigurationCallBarringQueried(IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).createTypedArray(ImsSsInfo.CREATOR));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.utConfigurationQueryFailed((IImsUt)object2, n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.utConfigurationQueried((IImsUt)object2, n, (Bundle)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.utConfigurationUpdateFailed((IImsUt)object2, n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.utConfigurationUpdated(IImsUt.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsUtListener {
            public static IImsUtListener sDefaultImpl;
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
            public void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsSsData != null) {
                        parcel.writeInt(1);
                        imsSsData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSupplementaryServiceIndication(imsSsData);
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
            public void utConfigurationCallBarringQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrimsSsInfo, 0);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationCallBarringQueried(iImsUt, n, arrimsSsInfo);
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
            public void utConfigurationCallForwardQueried(IImsUt iImsUt, int n, ImsCallForwardInfo[] arrimsCallForwardInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrimsCallForwardInfo, 0);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationCallForwardQueried(iImsUt, n, arrimsCallForwardInfo);
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
            public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrimsSsInfo, 0);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationCallWaitingQueried(iImsUt, n, arrimsSsInfo);
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
            public void utConfigurationQueried(IImsUt iImsUt, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationQueried(iImsUt, n, bundle);
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
            public void utConfigurationQueryFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationQueryFailed(iImsUt, n, imsReasonInfo);
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
            public void utConfigurationUpdateFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationUpdateFailed(iImsUt, n, imsReasonInfo);
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
            public void utConfigurationUpdated(IImsUt iImsUt, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsUt != null ? iImsUt.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().utConfigurationUpdated(iImsUt, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

