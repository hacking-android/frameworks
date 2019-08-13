/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IMaintenanceActivityListener;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDeviceIdleController
extends IInterface {
    @UnsupportedAppUsage
    public void addPowerSaveTempWhitelistApp(String var1, long var2, int var4, String var5) throws RemoteException;

    public long addPowerSaveTempWhitelistAppForMms(String var1, int var2, String var3) throws RemoteException;

    public long addPowerSaveTempWhitelistAppForSms(String var1, int var2, String var3) throws RemoteException;

    public void addPowerSaveWhitelistApp(String var1) throws RemoteException;

    public void exitIdle(String var1) throws RemoteException;

    public int[] getAppIdTempWhitelist() throws RemoteException;

    public int[] getAppIdUserWhitelist() throws RemoteException;

    public int[] getAppIdWhitelist() throws RemoteException;

    public int[] getAppIdWhitelistExceptIdle() throws RemoteException;

    public String[] getFullPowerWhitelist() throws RemoteException;

    public String[] getFullPowerWhitelistExceptIdle() throws RemoteException;

    public String[] getRemovedSystemPowerWhitelistApps() throws RemoteException;

    public String[] getSystemPowerWhitelist() throws RemoteException;

    public String[] getSystemPowerWhitelistExceptIdle() throws RemoteException;

    public String[] getUserPowerWhitelist() throws RemoteException;

    public boolean isPowerSaveWhitelistApp(String var1) throws RemoteException;

    public boolean isPowerSaveWhitelistExceptIdleApp(String var1) throws RemoteException;

    public boolean registerMaintenanceActivityListener(IMaintenanceActivityListener var1) throws RemoteException;

    public void removePowerSaveWhitelistApp(String var1) throws RemoteException;

    public void removeSystemPowerWhitelistApp(String var1) throws RemoteException;

    public void resetPreIdleTimeoutMode() throws RemoteException;

    public void restoreSystemPowerWhitelistApp(String var1) throws RemoteException;

    public int setPreIdleTimeoutMode(int var1) throws RemoteException;

    public void unregisterMaintenanceActivityListener(IMaintenanceActivityListener var1) throws RemoteException;

    public static class Default
    implements IDeviceIdleController {
        @Override
        public void addPowerSaveTempWhitelistApp(String string2, long l, int n, String string3) throws RemoteException {
        }

        @Override
        public long addPowerSaveTempWhitelistAppForMms(String string2, int n, String string3) throws RemoteException {
            return 0L;
        }

        @Override
        public long addPowerSaveTempWhitelistAppForSms(String string2, int n, String string3) throws RemoteException {
            return 0L;
        }

        @Override
        public void addPowerSaveWhitelistApp(String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void exitIdle(String string2) throws RemoteException {
        }

        @Override
        public int[] getAppIdTempWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public int[] getAppIdUserWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public int[] getAppIdWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public int[] getAppIdWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override
        public String[] getFullPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public String[] getFullPowerWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override
        public String[] getRemovedSystemPowerWhitelistApps() throws RemoteException {
            return null;
        }

        @Override
        public String[] getSystemPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public String[] getSystemPowerWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override
        public String[] getUserPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public boolean isPowerSaveWhitelistApp(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPowerSaveWhitelistExceptIdleApp(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerMaintenanceActivityListener(IMaintenanceActivityListener iMaintenanceActivityListener) throws RemoteException {
            return false;
        }

        @Override
        public void removePowerSaveWhitelistApp(String string2) throws RemoteException {
        }

        @Override
        public void removeSystemPowerWhitelistApp(String string2) throws RemoteException {
        }

        @Override
        public void resetPreIdleTimeoutMode() throws RemoteException {
        }

        @Override
        public void restoreSystemPowerWhitelistApp(String string2) throws RemoteException {
        }

        @Override
        public int setPreIdleTimeoutMode(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void unregisterMaintenanceActivityListener(IMaintenanceActivityListener iMaintenanceActivityListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDeviceIdleController {
        private static final String DESCRIPTOR = "android.os.IDeviceIdleController";
        static final int TRANSACTION_addPowerSaveTempWhitelistApp = 17;
        static final int TRANSACTION_addPowerSaveTempWhitelistAppForMms = 18;
        static final int TRANSACTION_addPowerSaveTempWhitelistAppForSms = 19;
        static final int TRANSACTION_addPowerSaveWhitelistApp = 1;
        static final int TRANSACTION_exitIdle = 20;
        static final int TRANSACTION_getAppIdTempWhitelist = 14;
        static final int TRANSACTION_getAppIdUserWhitelist = 13;
        static final int TRANSACTION_getAppIdWhitelist = 12;
        static final int TRANSACTION_getAppIdWhitelistExceptIdle = 11;
        static final int TRANSACTION_getFullPowerWhitelist = 10;
        static final int TRANSACTION_getFullPowerWhitelistExceptIdle = 9;
        static final int TRANSACTION_getRemovedSystemPowerWhitelistApps = 5;
        static final int TRANSACTION_getSystemPowerWhitelist = 7;
        static final int TRANSACTION_getSystemPowerWhitelistExceptIdle = 6;
        static final int TRANSACTION_getUserPowerWhitelist = 8;
        static final int TRANSACTION_isPowerSaveWhitelistApp = 16;
        static final int TRANSACTION_isPowerSaveWhitelistExceptIdleApp = 15;
        static final int TRANSACTION_registerMaintenanceActivityListener = 21;
        static final int TRANSACTION_removePowerSaveWhitelistApp = 2;
        static final int TRANSACTION_removeSystemPowerWhitelistApp = 3;
        static final int TRANSACTION_resetPreIdleTimeoutMode = 24;
        static final int TRANSACTION_restoreSystemPowerWhitelistApp = 4;
        static final int TRANSACTION_setPreIdleTimeoutMode = 23;
        static final int TRANSACTION_unregisterMaintenanceActivityListener = 22;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceIdleController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDeviceIdleController) {
                return (IDeviceIdleController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDeviceIdleController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 24: {
                    return "resetPreIdleTimeoutMode";
                }
                case 23: {
                    return "setPreIdleTimeoutMode";
                }
                case 22: {
                    return "unregisterMaintenanceActivityListener";
                }
                case 21: {
                    return "registerMaintenanceActivityListener";
                }
                case 20: {
                    return "exitIdle";
                }
                case 19: {
                    return "addPowerSaveTempWhitelistAppForSms";
                }
                case 18: {
                    return "addPowerSaveTempWhitelistAppForMms";
                }
                case 17: {
                    return "addPowerSaveTempWhitelistApp";
                }
                case 16: {
                    return "isPowerSaveWhitelistApp";
                }
                case 15: {
                    return "isPowerSaveWhitelistExceptIdleApp";
                }
                case 14: {
                    return "getAppIdTempWhitelist";
                }
                case 13: {
                    return "getAppIdUserWhitelist";
                }
                case 12: {
                    return "getAppIdWhitelist";
                }
                case 11: {
                    return "getAppIdWhitelistExceptIdle";
                }
                case 10: {
                    return "getFullPowerWhitelist";
                }
                case 9: {
                    return "getFullPowerWhitelistExceptIdle";
                }
                case 8: {
                    return "getUserPowerWhitelist";
                }
                case 7: {
                    return "getSystemPowerWhitelist";
                }
                case 6: {
                    return "getSystemPowerWhitelistExceptIdle";
                }
                case 5: {
                    return "getRemovedSystemPowerWhitelistApps";
                }
                case 4: {
                    return "restoreSystemPowerWhitelistApp";
                }
                case 3: {
                    return "removeSystemPowerWhitelistApp";
                }
                case 2: {
                    return "removePowerSaveWhitelistApp";
                }
                case 1: 
            }
            return "addPowerSaveWhitelistApp";
        }

        public static boolean setDefaultImpl(IDeviceIdleController iDeviceIdleController) {
            if (Proxy.sDefaultImpl == null && iDeviceIdleController != null) {
                Proxy.sDefaultImpl = iDeviceIdleController;
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
        public boolean onTransact(int n, Parcel arrobject, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrobject, parcel, n2);
                    }
                    case 24: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.resetPreIdleTimeoutMode();
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        n = this.setPreIdleTimeoutMode(arrobject.readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.unregisterMaintenanceActivityListener(IMaintenanceActivityListener.Stub.asInterface(arrobject.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        n = this.registerMaintenanceActivityListener(IMaintenanceActivityListener.Stub.asInterface(arrobject.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.exitIdle(arrobject.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        long l = this.addPowerSaveTempWhitelistAppForSms(arrobject.readString(), arrobject.readInt(), arrobject.readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 18: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        long l = this.addPowerSaveTempWhitelistAppForMms(arrobject.readString(), arrobject.readInt(), arrobject.readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 17: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.addPowerSaveTempWhitelistApp(arrobject.readString(), arrobject.readLong(), arrobject.readInt(), arrobject.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        n = this.isPowerSaveWhitelistApp(arrobject.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        n = this.isPowerSaveWhitelistExceptIdleApp(arrobject.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getAppIdTempWhitelist();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])arrobject);
                        return true;
                    }
                    case 13: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getAppIdUserWhitelist();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])arrobject);
                        return true;
                    }
                    case 12: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getAppIdWhitelist();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])arrobject);
                        return true;
                    }
                    case 11: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getAppIdWhitelistExceptIdle();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])arrobject);
                        return true;
                    }
                    case 10: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getFullPowerWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 9: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getFullPowerWhitelistExceptIdle();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 8: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getUserPowerWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 7: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getSystemPowerWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 6: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getSystemPowerWhitelistExceptIdle();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 5: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        arrobject = this.getRemovedSystemPowerWhitelistApps();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])arrobject);
                        return true;
                    }
                    case 4: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.restoreSystemPowerWhitelistApp(arrobject.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.removeSystemPowerWhitelistApp(arrobject.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        arrobject.enforceInterface(DESCRIPTOR);
                        this.removePowerSaveWhitelistApp(arrobject.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                arrobject.enforceInterface(DESCRIPTOR);
                this.addPowerSaveWhitelistApp(arrobject.readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDeviceIdleController {
            public static IDeviceIdleController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addPowerSaveTempWhitelistApp(String string2, long l, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPowerSaveTempWhitelistApp(string2, l, n, string3);
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
            public long addPowerSaveTempWhitelistAppForMms(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().addPowerSaveTempWhitelistAppForMms(string2, n, string3);
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
            public long addPowerSaveTempWhitelistAppForSms(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().addPowerSaveTempWhitelistAppForSms(string2, n, string3);
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
            public void addPowerSaveWhitelistApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPowerSaveWhitelistApp(string2);
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
            public void exitIdle(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().exitIdle(string2);
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
            public int[] getAppIdTempWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAppIdTempWhitelist();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getAppIdUserWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAppIdUserWhitelist();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getAppIdWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAppIdWhitelist();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getAppIdWhitelistExceptIdle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAppIdWhitelistExceptIdle();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getFullPowerWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getFullPowerWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getFullPowerWhitelistExceptIdle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getFullPowerWhitelistExceptIdle();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
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
            public String[] getRemovedSystemPowerWhitelistApps() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getRemovedSystemPowerWhitelistApps();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSystemPowerWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSystemPowerWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSystemPowerWhitelistExceptIdle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSystemPowerWhitelistExceptIdle();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getUserPowerWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getUserPowerWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isPowerSaveWhitelistApp(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPowerSaveWhitelistApp(string2);
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
            public boolean isPowerSaveWhitelistExceptIdleApp(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPowerSaveWhitelistExceptIdleApp(string2);
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
            public boolean registerMaintenanceActivityListener(IMaintenanceActivityListener iMaintenanceActivityListener) throws RemoteException {
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
                                if (iMaintenanceActivityListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iMaintenanceActivityListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerMaintenanceActivityListener(iMaintenanceActivityListener);
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
            public void removePowerSaveWhitelistApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePowerSaveWhitelistApp(string2);
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
            public void removeSystemPowerWhitelistApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSystemPowerWhitelistApp(string2);
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
            public void resetPreIdleTimeoutMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetPreIdleTimeoutMode();
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
            public void restoreSystemPowerWhitelistApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreSystemPowerWhitelistApp(string2);
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
            public int setPreIdleTimeoutMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setPreIdleTimeoutMode(n);
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
            public void unregisterMaintenanceActivityListener(IMaintenanceActivityListener iMaintenanceActivityListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMaintenanceActivityListener != null ? iMaintenanceActivityListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterMaintenanceActivityListener(iMaintenanceActivityListener);
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

