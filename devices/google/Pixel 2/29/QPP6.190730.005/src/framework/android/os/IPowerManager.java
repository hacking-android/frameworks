/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.BatterySaverPolicyConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.os.WorkSource;

public interface IPowerManager
extends IInterface {
    public void acquireWakeLock(IBinder var1, int var2, String var3, String var4, WorkSource var5, String var6) throws RemoteException;

    public void acquireWakeLockWithUid(IBinder var1, int var2, String var3, String var4, int var5) throws RemoteException;

    public void boostScreenBrightness(long var1) throws RemoteException;

    public void crash(String var1) throws RemoteException;

    public boolean forceSuspend() throws RemoteException;

    public int getLastShutdownReason() throws RemoteException;

    public int getLastSleepReason() throws RemoteException;

    public int getPowerSaveModeTrigger() throws RemoteException;

    public PowerSaveState getPowerSaveState(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void goToSleep(long var1, int var3, int var4) throws RemoteException;

    public boolean isDeviceIdleMode() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isInteractive() throws RemoteException;

    public boolean isLightDeviceIdleMode() throws RemoteException;

    public boolean isPowerSaveMode() throws RemoteException;

    public boolean isScreenBrightnessBoosted() throws RemoteException;

    public boolean isWakeLockLevelSupported(int var1) throws RemoteException;

    public void nap(long var1) throws RemoteException;

    public void powerHint(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void reboot(boolean var1, String var2, boolean var3) throws RemoteException;

    public void rebootSafeMode(boolean var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void releaseWakeLock(IBinder var1, int var2) throws RemoteException;

    public boolean setAdaptivePowerSaveEnabled(boolean var1) throws RemoteException;

    public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig var1) throws RemoteException;

    public void setAttentionLight(boolean var1, int var2) throws RemoteException;

    public void setDozeAfterScreenOff(boolean var1) throws RemoteException;

    public boolean setDynamicPowerSaveHint(boolean var1, int var2) throws RemoteException;

    public boolean setPowerSaveModeEnabled(boolean var1) throws RemoteException;

    public void setStayOnSetting(int var1) throws RemoteException;

    public void shutdown(boolean var1, String var2, boolean var3) throws RemoteException;

    public void updateWakeLockUids(IBinder var1, int[] var2) throws RemoteException;

    public void updateWakeLockWorkSource(IBinder var1, WorkSource var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void userActivity(long var1, int var3, int var4) throws RemoteException;

    public void wakeUp(long var1, int var3, String var4, String var5) throws RemoteException;

    public static class Default
    implements IPowerManager {
        @Override
        public void acquireWakeLock(IBinder iBinder, int n, String string2, String string3, WorkSource workSource, String string4) throws RemoteException {
        }

        @Override
        public void acquireWakeLockWithUid(IBinder iBinder, int n, String string2, String string3, int n2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void boostScreenBrightness(long l) throws RemoteException {
        }

        @Override
        public void crash(String string2) throws RemoteException {
        }

        @Override
        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        @Override
        public int getLastShutdownReason() throws RemoteException {
            return 0;
        }

        @Override
        public int getLastSleepReason() throws RemoteException {
            return 0;
        }

        @Override
        public int getPowerSaveModeTrigger() throws RemoteException {
            return 0;
        }

        @Override
        public PowerSaveState getPowerSaveState(int n) throws RemoteException {
            return null;
        }

        @Override
        public void goToSleep(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public boolean isDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInteractive() throws RemoteException {
            return false;
        }

        @Override
        public boolean isLightDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isPowerSaveMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isScreenBrightnessBoosted() throws RemoteException {
            return false;
        }

        @Override
        public boolean isWakeLockLevelSupported(int n) throws RemoteException {
            return false;
        }

        @Override
        public void nap(long l) throws RemoteException {
        }

        @Override
        public void powerHint(int n, int n2) throws RemoteException {
        }

        @Override
        public void reboot(boolean bl, String string2, boolean bl2) throws RemoteException {
        }

        @Override
        public void rebootSafeMode(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void releaseWakeLock(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public boolean setAdaptivePowerSaveEnabled(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
            return false;
        }

        @Override
        public void setAttentionLight(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setDozeAfterScreenOff(boolean bl) throws RemoteException {
        }

        @Override
        public boolean setDynamicPowerSaveHint(boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setPowerSaveModeEnabled(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setStayOnSetting(int n) throws RemoteException {
        }

        @Override
        public void shutdown(boolean bl, String string2, boolean bl2) throws RemoteException {
        }

        @Override
        public void updateWakeLockUids(IBinder iBinder, int[] arrn) throws RemoteException {
        }

        @Override
        public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String string2) throws RemoteException {
        }

        @Override
        public void userActivity(long l, int n, int n2) throws RemoteException {
        }

        @Override
        public void wakeUp(long l, int n, String string2, String string3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPowerManager {
        private static final String DESCRIPTOR = "android.os.IPowerManager";
        static final int TRANSACTION_acquireWakeLock = 1;
        static final int TRANSACTION_acquireWakeLockWithUid = 2;
        static final int TRANSACTION_boostScreenBrightness = 29;
        static final int TRANSACTION_crash = 25;
        static final int TRANSACTION_forceSuspend = 33;
        static final int TRANSACTION_getLastShutdownReason = 26;
        static final int TRANSACTION_getLastSleepReason = 27;
        static final int TRANSACTION_getPowerSaveModeTrigger = 19;
        static final int TRANSACTION_getPowerSaveState = 14;
        static final int TRANSACTION_goToSleep = 10;
        static final int TRANSACTION_isDeviceIdleMode = 20;
        static final int TRANSACTION_isInteractive = 12;
        static final int TRANSACTION_isLightDeviceIdleMode = 21;
        static final int TRANSACTION_isPowerSaveMode = 13;
        static final int TRANSACTION_isScreenBrightnessBoosted = 30;
        static final int TRANSACTION_isWakeLockLevelSupported = 7;
        static final int TRANSACTION_nap = 11;
        static final int TRANSACTION_powerHint = 5;
        static final int TRANSACTION_reboot = 22;
        static final int TRANSACTION_rebootSafeMode = 23;
        static final int TRANSACTION_releaseWakeLock = 3;
        static final int TRANSACTION_setAdaptivePowerSaveEnabled = 18;
        static final int TRANSACTION_setAdaptivePowerSavePolicy = 17;
        static final int TRANSACTION_setAttentionLight = 31;
        static final int TRANSACTION_setDozeAfterScreenOff = 32;
        static final int TRANSACTION_setDynamicPowerSaveHint = 16;
        static final int TRANSACTION_setPowerSaveModeEnabled = 15;
        static final int TRANSACTION_setStayOnSetting = 28;
        static final int TRANSACTION_shutdown = 24;
        static final int TRANSACTION_updateWakeLockUids = 4;
        static final int TRANSACTION_updateWakeLockWorkSource = 6;
        static final int TRANSACTION_userActivity = 8;
        static final int TRANSACTION_wakeUp = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPowerManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPowerManager) {
                return (IPowerManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPowerManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 33: {
                    return "forceSuspend";
                }
                case 32: {
                    return "setDozeAfterScreenOff";
                }
                case 31: {
                    return "setAttentionLight";
                }
                case 30: {
                    return "isScreenBrightnessBoosted";
                }
                case 29: {
                    return "boostScreenBrightness";
                }
                case 28: {
                    return "setStayOnSetting";
                }
                case 27: {
                    return "getLastSleepReason";
                }
                case 26: {
                    return "getLastShutdownReason";
                }
                case 25: {
                    return "crash";
                }
                case 24: {
                    return "shutdown";
                }
                case 23: {
                    return "rebootSafeMode";
                }
                case 22: {
                    return "reboot";
                }
                case 21: {
                    return "isLightDeviceIdleMode";
                }
                case 20: {
                    return "isDeviceIdleMode";
                }
                case 19: {
                    return "getPowerSaveModeTrigger";
                }
                case 18: {
                    return "setAdaptivePowerSaveEnabled";
                }
                case 17: {
                    return "setAdaptivePowerSavePolicy";
                }
                case 16: {
                    return "setDynamicPowerSaveHint";
                }
                case 15: {
                    return "setPowerSaveModeEnabled";
                }
                case 14: {
                    return "getPowerSaveState";
                }
                case 13: {
                    return "isPowerSaveMode";
                }
                case 12: {
                    return "isInteractive";
                }
                case 11: {
                    return "nap";
                }
                case 10: {
                    return "goToSleep";
                }
                case 9: {
                    return "wakeUp";
                }
                case 8: {
                    return "userActivity";
                }
                case 7: {
                    return "isWakeLockLevelSupported";
                }
                case 6: {
                    return "updateWakeLockWorkSource";
                }
                case 5: {
                    return "powerHint";
                }
                case 4: {
                    return "updateWakeLockUids";
                }
                case 3: {
                    return "releaseWakeLock";
                }
                case 2: {
                    return "acquireWakeLockWithUid";
                }
                case 1: 
            }
            return "acquireWakeLock";
        }

        public static boolean setDefaultImpl(IPowerManager iPowerManager) {
            if (Proxy.sDefaultImpl == null && iPowerManager != null) {
                Proxy.sDefaultImpl = iPowerManager;
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
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.forceSuspend() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl8 = true;
                        }
                        this.setDozeAfterScreenOff(bl8);
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl8 = true;
                        }
                        this.setAttentionLight(bl8, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isScreenBrightnessBoosted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.boostScreenBrightness(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setStayOnSetting(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLastSleepReason();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLastShutdownReason();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.crash(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = ((Parcel)object).readInt() != 0;
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.shutdown(bl8, string2, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = ((Parcel)object).readInt() != 0;
                        bl2 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.rebootSafeMode(bl8, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = ((Parcel)object).readInt() != 0;
                        String string3 = ((Parcel)object).readString();
                        bl2 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.reboot(bl8, string3, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isLightDeviceIdleMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceIdleMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPowerSaveModeTrigger();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl8 = true;
                        }
                        n = this.setAdaptivePowerSaveEnabled(bl8) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BatterySaverPolicyConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setAdaptivePowerSavePolicy((BatterySaverPolicyConfig)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl8 = true;
                        }
                        n = this.setDynamicPowerSaveHint(bl8, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl8 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl8 = true;
                        }
                        n = this.setPowerSaveModeEnabled(bl8) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPowerSaveState(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PowerSaveState)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPowerSaveMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInteractive() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.nap(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.goToSleep(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.wakeUp(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.userActivity(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWakeLockLevelSupported(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateWakeLockWorkSource(iBinder, workSource, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.powerHint(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateWakeLockUids(((Parcel)object).readStrongBinder(), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseWakeLock(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acquireWakeLockWithUid(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                n = ((Parcel)object).readInt();
                String string4 = ((Parcel)object).readString();
                String string5 = ((Parcel)object).readString();
                WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                this.acquireWakeLock(iBinder, n, string4, string5, workSource, ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPowerManager {
            public static IPowerManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void acquireWakeLock(IBinder iBinder, int n, String string2, String string3, WorkSource workSource, String string4) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string3);
                            if (workSource != null) {
                                parcel2.writeInt(1);
                                workSource.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string4);
                        if (!this.mRemote.transact(1, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().acquireWakeLock(iBinder, n, string2, string3, workSource, string4);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public void acquireWakeLockWithUid(IBinder iBinder, int n, String string2, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acquireWakeLockWithUid(iBinder, n, string2, string3, n2);
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
            public void boostScreenBrightness(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().boostScreenBrightness(l);
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
            public void crash(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().crash(string2);
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
            public boolean forceSuspend() throws RemoteException {
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
                    if (iBinder.transact(33, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().forceSuspend();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getLastShutdownReason() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLastShutdownReason();
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
            public int getLastSleepReason() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLastSleepReason();
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
            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPowerSaveModeTrigger();
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
            public PowerSaveState getPowerSaveState(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(14, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        PowerSaveState powerSaveState = Stub.getDefaultImpl().getPowerSaveState(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return powerSaveState;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                PowerSaveState powerSaveState = parcel2.readInt() != 0 ? PowerSaveState.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return powerSaveState;
            }

            @Override
            public void goToSleep(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().goToSleep(l, n, n2);
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
            public boolean isDeviceIdleMode() throws RemoteException {
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
                    if (iBinder.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceIdleMode();
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

            @Override
            public boolean isInteractive() throws RemoteException {
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
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInteractive();
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

            @Override
            public boolean isLightDeviceIdleMode() throws RemoteException {
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
                    if (iBinder.transact(21, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isLightDeviceIdleMode();
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

            @Override
            public boolean isPowerSaveMode() throws RemoteException {
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
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPowerSaveMode();
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

            @Override
            public boolean isScreenBrightnessBoosted() throws RemoteException {
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
                    if (iBinder.transact(30, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isScreenBrightnessBoosted();
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

            @Override
            public boolean isWakeLockLevelSupported(int n) throws RemoteException {
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
                    if (iBinder.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWakeLockLevelSupported(n);
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
            public void nap(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().nap(l);
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
            public void powerHint(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().powerHint(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reboot(boolean bl, String string2, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                parcel.writeString(string2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reboot(bl, string2, bl2);
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
            public void rebootSafeMode(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rebootSafeMode(bl, bl2);
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
            public void releaseWakeLock(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseWakeLock(iBinder, n);
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
            public boolean setAdaptivePowerSaveEnabled(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setAdaptivePowerSaveEnabled(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
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
            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (batterySaverPolicyConfig != null) {
                        parcel.writeInt(1);
                        batterySaverPolicyConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setAdaptivePowerSavePolicy(batterySaverPolicyConfig);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void setAttentionLight(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAttentionLight(bl, n);
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
            public void setDozeAfterScreenOff(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDozeAfterScreenOff(bl);
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
            public boolean setDynamicPowerSaveHint(boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(16, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setDynamicPowerSaveHint(bl, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean setPowerSaveModeEnabled(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(15, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setPowerSaveModeEnabled(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setStayOnSetting(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStayOnSetting(n);
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
            public void shutdown(boolean bl, String string2, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                parcel.writeString(string2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown(bl, string2, bl2);
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
            public void updateWakeLockUids(IBinder iBinder, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWakeLockUids(iBinder, arrn);
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
            public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWakeLockWorkSource(iBinder, workSource, string2);
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
            public void userActivity(long l, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userActivity(l, n, n2);
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
            public void wakeUp(long l, int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wakeUp(l, n, string2, string3);
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

