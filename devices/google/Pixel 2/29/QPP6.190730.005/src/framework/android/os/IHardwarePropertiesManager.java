/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.CpuUsageInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IHardwarePropertiesManager
extends IInterface {
    public CpuUsageInfo[] getCpuUsages(String var1) throws RemoteException;

    public float[] getDeviceTemperatures(String var1, int var2, int var3) throws RemoteException;

    public float[] getFanSpeeds(String var1) throws RemoteException;

    public static class Default
    implements IHardwarePropertiesManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public CpuUsageInfo[] getCpuUsages(String string2) throws RemoteException {
            return null;
        }

        @Override
        public float[] getDeviceTemperatures(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public float[] getFanSpeeds(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHardwarePropertiesManager {
        private static final String DESCRIPTOR = "android.os.IHardwarePropertiesManager";
        static final int TRANSACTION_getCpuUsages = 2;
        static final int TRANSACTION_getDeviceTemperatures = 1;
        static final int TRANSACTION_getFanSpeeds = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHardwarePropertiesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHardwarePropertiesManager) {
                return (IHardwarePropertiesManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHardwarePropertiesManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "getFanSpeeds";
                }
                return "getCpuUsages";
            }
            return "getDeviceTemperatures";
        }

        public static boolean setDefaultImpl(IHardwarePropertiesManager iHardwarePropertiesManager) {
            if (Proxy.sDefaultImpl == null && iHardwarePropertiesManager != null) {
                Proxy.sDefaultImpl = iHardwarePropertiesManager;
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
        public boolean onTransact(int n, Parcel arrparcelable, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)arrparcelable, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    arrparcelable.enforceInterface(DESCRIPTOR);
                    arrparcelable = this.getFanSpeeds(arrparcelable.readString());
                    parcel.writeNoException();
                    parcel.writeFloatArray((float[])arrparcelable);
                    return true;
                }
                arrparcelable.enforceInterface(DESCRIPTOR);
                arrparcelable = this.getCpuUsages(arrparcelable.readString());
                parcel.writeNoException();
                parcel.writeTypedArray(arrparcelable, 1);
                return true;
            }
            arrparcelable.enforceInterface(DESCRIPTOR);
            arrparcelable = this.getDeviceTemperatures(arrparcelable.readString(), arrparcelable.readInt(), arrparcelable.readInt());
            parcel.writeNoException();
            parcel.writeFloatArray((float[])arrparcelable);
            return true;
        }

        private static class Proxy
        implements IHardwarePropertiesManager {
            public static IHardwarePropertiesManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public CpuUsageInfo[] getCpuUsages(String arrcpuUsageInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrcpuUsageInfo);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrcpuUsageInfo = Stub.getDefaultImpl().getCpuUsages((String)arrcpuUsageInfo);
                        return arrcpuUsageInfo;
                    }
                    parcel2.readException();
                    arrcpuUsageInfo = parcel2.createTypedArray(CpuUsageInfo.CREATOR);
                    return arrcpuUsageInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float[] getDeviceTemperatures(String arrf, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrf);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrf = Stub.getDefaultImpl().getDeviceTemperatures((String)arrf, n, n2);
                        return arrf;
                    }
                    parcel2.readException();
                    arrf = parcel2.createFloatArray();
                    return arrf;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float[] getFanSpeeds(String arrf) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrf);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrf = Stub.getDefaultImpl().getFanSpeeds((String)arrf);
                        return arrf;
                    }
                    parcel2.readException();
                    arrf = parcel2.createFloatArray();
                    return arrf;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

