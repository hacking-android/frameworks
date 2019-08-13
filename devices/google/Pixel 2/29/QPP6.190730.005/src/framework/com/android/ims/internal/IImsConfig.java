/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.ImsConfigListener;

public interface IImsConfig
extends IInterface {
    public void getFeatureValue(int var1, int var2, ImsConfigListener var3) throws RemoteException;

    public String getProvisionedStringValue(int var1) throws RemoteException;

    public int getProvisionedValue(int var1) throws RemoteException;

    public void getVideoQuality(ImsConfigListener var1) throws RemoteException;

    public boolean getVolteProvisioned() throws RemoteException;

    public void setFeatureValue(int var1, int var2, int var3, ImsConfigListener var4) throws RemoteException;

    public int setProvisionedStringValue(int var1, String var2) throws RemoteException;

    public int setProvisionedValue(int var1, int var2) throws RemoteException;

    public void setVideoQuality(int var1, ImsConfigListener var2) throws RemoteException;

    public static class Default
    implements IImsConfig {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getFeatureValue(int n, int n2, ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override
        public String getProvisionedStringValue(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getProvisionedValue(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override
        public boolean getVolteProvisioned() throws RemoteException {
            return false;
        }

        @Override
        public void setFeatureValue(int n, int n2, int n3, ImsConfigListener imsConfigListener) throws RemoteException {
        }

        @Override
        public int setProvisionedStringValue(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int setProvisionedValue(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public void setVideoQuality(int n, ImsConfigListener imsConfigListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsConfig {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsConfig";
        static final int TRANSACTION_getFeatureValue = 5;
        static final int TRANSACTION_getProvisionedStringValue = 2;
        static final int TRANSACTION_getProvisionedValue = 1;
        static final int TRANSACTION_getVideoQuality = 8;
        static final int TRANSACTION_getVolteProvisioned = 7;
        static final int TRANSACTION_setFeatureValue = 6;
        static final int TRANSACTION_setProvisionedStringValue = 4;
        static final int TRANSACTION_setProvisionedValue = 3;
        static final int TRANSACTION_setVideoQuality = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsConfig asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsConfig) {
                return (IImsConfig)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsConfig getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "setVideoQuality";
                }
                case 8: {
                    return "getVideoQuality";
                }
                case 7: {
                    return "getVolteProvisioned";
                }
                case 6: {
                    return "setFeatureValue";
                }
                case 5: {
                    return "getFeatureValue";
                }
                case 4: {
                    return "setProvisionedStringValue";
                }
                case 3: {
                    return "setProvisionedValue";
                }
                case 2: {
                    return "getProvisionedStringValue";
                }
                case 1: 
            }
            return "getProvisionedValue";
        }

        public static boolean setDefaultImpl(IImsConfig iImsConfig) {
            if (Proxy.sDefaultImpl == null && iImsConfig != null) {
                Proxy.sDefaultImpl = iImsConfig;
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
                        this.setVideoQuality(((Parcel)object).readInt(), ImsConfigListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getVideoQuality(ImsConfigListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVolteProvisioned() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFeatureValue(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ImsConfigListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getFeatureValue(((Parcel)object).readInt(), ((Parcel)object).readInt(), ImsConfigListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setProvisionedStringValue(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setProvisionedValue(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProvisionedStringValue(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.getProvisionedValue(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsConfig {
            public static IImsConfig sDefaultImpl;
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
            public void getFeatureValue(int n, int n2, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = imsConfigListener != null ? imsConfigListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getFeatureValue(n, n2, imsConfigListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getProvisionedStringValue(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getProvisionedStringValue(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getProvisionedValue(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getProvisionedValue(n);
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
            public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = imsConfigListener != null ? imsConfigListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getVideoQuality(imsConfigListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean getVolteProvisioned() throws RemoteException {
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
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getVolteProvisioned();
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
            public void setFeatureValue(int n, int n2, int n3, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    IBinder iBinder = imsConfigListener != null ? imsConfigListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setFeatureValue(n, n2, n3, imsConfigListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public int setProvisionedStringValue(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setProvisionedStringValue(n, string2);
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

            @Override
            public int setProvisionedValue(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setProvisionedValue(n, n2);
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
            public void setVideoQuality(int n, ImsConfigListener imsConfigListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = imsConfigListener != null ? imsConfigListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setVideoQuality(n, imsConfigListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

