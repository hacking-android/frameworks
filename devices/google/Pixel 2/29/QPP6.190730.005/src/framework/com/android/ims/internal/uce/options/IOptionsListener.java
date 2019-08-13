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
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.options.OptionsCapInfo;
import com.android.ims.internal.uce.options.OptionsCmdStatus;
import com.android.ims.internal.uce.options.OptionsSipResponse;

public interface IOptionsListener
extends IInterface {
    @UnsupportedAppUsage
    public void cmdStatus(OptionsCmdStatus var1) throws RemoteException;

    @UnsupportedAppUsage
    public void getVersionCb(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void incomingOptions(String var1, OptionsCapInfo var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void serviceAvailable(StatusCode var1) throws RemoteException;

    @UnsupportedAppUsage
    public void serviceUnavailable(StatusCode var1) throws RemoteException;

    @UnsupportedAppUsage
    public void sipResponseReceived(String var1, OptionsSipResponse var2, OptionsCapInfo var3) throws RemoteException;

    public static class Default
    implements IOptionsListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException {
        }

        @Override
        public void getVersionCb(String string2) throws RemoteException {
        }

        @Override
        public void incomingOptions(String string2, OptionsCapInfo optionsCapInfo, int n) throws RemoteException {
        }

        @Override
        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override
        public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
        }

        @Override
        public void sipResponseReceived(String string2, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOptionsListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsListener";
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_incomingOptions = 6;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnavailable = 3;
        static final int TRANSACTION_sipResponseReceived = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOptionsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOptionsListener) {
                return (IOptionsListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOptionsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "incomingOptions";
                }
                case 5: {
                    return "cmdStatus";
                }
                case 4: {
                    return "sipResponseReceived";
                }
                case 3: {
                    return "serviceUnavailable";
                }
                case 2: {
                    return "serviceAvailable";
                }
                case 1: 
            }
            return "getVersionCb";
        }

        public static boolean setDefaultImpl(IOptionsListener iOptionsListener) {
            if (Proxy.sDefaultImpl == null && iOptionsListener != null) {
                Proxy.sDefaultImpl = iOptionsListener;
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
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        OptionsCapInfo optionsCapInfo = ((Parcel)object).readInt() != 0 ? OptionsCapInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.incomingOptions(string2, optionsCapInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? OptionsCmdStatus.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cmdStatus((OptionsCmdStatus)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        OptionsSipResponse optionsSipResponse = ((Parcel)object).readInt() != 0 ? OptionsSipResponse.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? OptionsCapInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sipResponseReceived(string3, optionsSipResponse, (OptionsCapInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? StatusCode.CREATOR.createFromParcel((Parcel)object) : null;
                        this.serviceUnavailable((StatusCode)object);
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
        implements IOptionsListener {
            public static IOptionsListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (optionsCmdStatus != null) {
                        parcel.writeInt(1);
                        optionsCmdStatus.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cmdStatus(optionsCmdStatus);
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
            public void incomingOptions(String string2, OptionsCapInfo optionsCapInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (optionsCapInfo != null) {
                        parcel.writeInt(1);
                        optionsCapInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().incomingOptions(string2, optionsCapInfo, n);
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
            public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
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
                        Stub.getDefaultImpl().serviceUnavailable(statusCode);
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
            public void sipResponseReceived(String string2, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (optionsSipResponse != null) {
                        parcel.writeInt(1);
                        optionsSipResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (optionsCapInfo != null) {
                        parcel.writeInt(1);
                        optionsCapInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sipResponseReceived(string2, optionsSipResponse, optionsCapInfo);
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

