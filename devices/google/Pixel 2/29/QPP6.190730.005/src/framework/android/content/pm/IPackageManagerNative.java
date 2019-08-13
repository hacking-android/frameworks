/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageManagerNative
extends IInterface {
    public static final int LOCATION_PRODUCT = 4;
    public static final int LOCATION_SYSTEM = 1;
    public static final int LOCATION_VENDOR = 2;

    public String getInstallerForPackage(String var1) throws RemoteException;

    public int getLocationFlags(String var1) throws RemoteException;

    public String getModuleMetadataPackageName() throws RemoteException;

    public String[] getNamesForUids(int[] var1) throws RemoteException;

    public int getTargetSdkVersionForPackage(String var1) throws RemoteException;

    public long getVersionCodeForPackage(String var1) throws RemoteException;

    public boolean[] isAudioPlaybackCaptureAllowed(String[] var1) throws RemoteException;

    public static class Default
    implements IPackageManagerNative {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getInstallerForPackage(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getLocationFlags(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getModuleMetadataPackageName() throws RemoteException {
            return null;
        }

        @Override
        public String[] getNamesForUids(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public int getTargetSdkVersionForPackage(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public long getVersionCodeForPackage(String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public boolean[] isAudioPlaybackCaptureAllowed(String[] arrstring) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageManagerNative {
        private static final String DESCRIPTOR = "android.content.pm.IPackageManagerNative";
        static final int TRANSACTION_getInstallerForPackage = 2;
        static final int TRANSACTION_getLocationFlags = 5;
        static final int TRANSACTION_getModuleMetadataPackageName = 7;
        static final int TRANSACTION_getNamesForUids = 1;
        static final int TRANSACTION_getTargetSdkVersionForPackage = 6;
        static final int TRANSACTION_getVersionCodeForPackage = 3;
        static final int TRANSACTION_isAudioPlaybackCaptureAllowed = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageManagerNative asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageManagerNative) {
                return (IPackageManagerNative)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageManagerNative getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "getModuleMetadataPackageName";
                }
                case 6: {
                    return "getTargetSdkVersionForPackage";
                }
                case 5: {
                    return "getLocationFlags";
                }
                case 4: {
                    return "isAudioPlaybackCaptureAllowed";
                }
                case 3: {
                    return "getVersionCodeForPackage";
                }
                case 2: {
                    return "getInstallerForPackage";
                }
                case 1: 
            }
            return "getNamesForUids";
        }

        public static boolean setDefaultImpl(IPackageManagerNative iPackageManagerNative) {
            if (Proxy.sDefaultImpl == null && iPackageManagerNative != null) {
                Proxy.sDefaultImpl = iPackageManagerNative;
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
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getModuleMetadataPackageName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.getTargetSdkVersionForPackage(object.readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.getLocationFlags(object.readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.isAudioPlaybackCaptureAllowed(object.createStringArray());
                        parcel.writeNoException();
                        parcel.writeBooleanArray((boolean[])object);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        long l = this.getVersionCodeForPackage(object.readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getInstallerForPackage(object.readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = this.getNamesForUids(object.createIntArray());
                parcel.writeNoException();
                parcel.writeStringArray((String[])object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPackageManagerNative {
            public static IPackageManagerNative sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public String getInstallerForPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getInstallerForPackage(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
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
            public int getLocationFlags(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLocationFlags(string2);
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
            public String getModuleMetadataPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getModuleMetadataPackageName();
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
            public String[] getNamesForUids(int[] arrobject) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])arrobject);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrobject = Stub.getDefaultImpl().getNamesForUids((int[])arrobject);
                        return arrobject;
                    }
                    parcel2.readException();
                    arrobject = parcel2.createStringArray();
                    return arrobject;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getTargetSdkVersionForPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getTargetSdkVersionForPackage(string2);
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
            public long getVersionCodeForPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getVersionCodeForPackage(string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean[] isAudioPlaybackCaptureAllowed(String[] arrobject) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray((String[])arrobject);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrobject = Stub.getDefaultImpl().isAudioPlaybackCaptureAllowed((String[])arrobject);
                        return arrobject;
                    }
                    parcel2.readException();
                    arrobject = parcel2.createBooleanArray();
                    return arrobject;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

