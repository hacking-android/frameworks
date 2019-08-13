/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AlarmManager;
import android.app.IAlarmListener;
import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;

public interface IAlarmManager
extends IInterface {
    public long currentNetworkTimeMillis() throws RemoteException;

    @UnsupportedAppUsage
    public AlarmManager.AlarmClockInfo getNextAlarmClock(int var1) throws RemoteException;

    public long getNextWakeFromIdleTime() throws RemoteException;

    public void remove(PendingIntent var1, IAlarmListener var2) throws RemoteException;

    @UnsupportedAppUsage
    public void set(String var1, int var2, long var3, long var5, long var7, int var9, PendingIntent var10, IAlarmListener var11, String var12, WorkSource var13, AlarmManager.AlarmClockInfo var14) throws RemoteException;

    @UnsupportedAppUsage
    public boolean setTime(long var1) throws RemoteException;

    public void setTimeZone(String var1) throws RemoteException;

    public static class Default
    implements IAlarmManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public long currentNetworkTimeMillis() throws RemoteException {
            return 0L;
        }

        @Override
        public AlarmManager.AlarmClockInfo getNextAlarmClock(int n) throws RemoteException {
            return null;
        }

        @Override
        public long getNextWakeFromIdleTime() throws RemoteException {
            return 0L;
        }

        @Override
        public void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException {
        }

        @Override
        public void set(String string2, int n, long l, long l2, long l3, int n2, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String string3, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo) throws RemoteException {
        }

        @Override
        public boolean setTime(long l) throws RemoteException {
            return false;
        }

        @Override
        public void setTimeZone(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAlarmManager {
        private static final String DESCRIPTOR = "android.app.IAlarmManager";
        static final int TRANSACTION_currentNetworkTimeMillis = 7;
        static final int TRANSACTION_getNextAlarmClock = 6;
        static final int TRANSACTION_getNextWakeFromIdleTime = 5;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_set = 1;
        static final int TRANSACTION_setTime = 2;
        static final int TRANSACTION_setTimeZone = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAlarmManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAlarmManager) {
                return (IAlarmManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAlarmManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "currentNetworkTimeMillis";
                }
                case 6: {
                    return "getNextAlarmClock";
                }
                case 5: {
                    return "getNextWakeFromIdleTime";
                }
                case 4: {
                    return "remove";
                }
                case 3: {
                    return "setTimeZone";
                }
                case 2: {
                    return "setTime";
                }
                case 1: 
            }
            return "set";
        }

        public static boolean setDefaultImpl(IAlarmManager iAlarmManager) {
            if (Proxy.sDefaultImpl == null && iAlarmManager != null) {
                Proxy.sDefaultImpl = iAlarmManager;
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
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.currentNetworkTimeMillis();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNextAlarmClock(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((AlarmManager.AlarmClockInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getNextWakeFromIdleTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.remove(pendingIntent, IAlarmListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTimeZone(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setTime(((Parcel)object).readLong()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string2 = ((Parcel)object).readString();
                n = ((Parcel)object).readInt();
                long l = ((Parcel)object).readLong();
                long l2 = ((Parcel)object).readLong();
                long l3 = ((Parcel)object).readLong();
                n2 = ((Parcel)object).readInt();
                PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                IAlarmListener iAlarmListener = IAlarmListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string3 = ((Parcel)object).readString();
                WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? AlarmManager.AlarmClockInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.set(string2, n, l, l2, l3, n2, pendingIntent, iAlarmListener, string3, workSource, (AlarmManager.AlarmClockInfo)object);
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAlarmManager {
            public static IAlarmManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public long currentNetworkTimeMillis() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().currentNetworkTimeMillis();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public AlarmManager.AlarmClockInfo getNextAlarmClock(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        AlarmManager.AlarmClockInfo alarmClockInfo = Stub.getDefaultImpl().getNextAlarmClock(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return alarmClockInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                AlarmManager.AlarmClockInfo alarmClockInfo = parcel2.readInt() != 0 ? AlarmManager.AlarmClockInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return alarmClockInfo;
            }

            @Override
            public long getNextWakeFromIdleTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getNextWakeFromIdleTime();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException {
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
                    IBinder iBinder = iAlarmListener != null ? iAlarmListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remove(pendingIntent, iAlarmListener);
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
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void set(String var1_1, int var2_6, long var3_7, long var5_8, long var7_9, int var9_10, PendingIntent var10_11, IAlarmListener var11_12, String var12_13, WorkSource var13_14, AlarmManager.AlarmClockInfo var14_15) throws RemoteException {
                block20 : {
                    block21 : {
                        block22 : {
                            block19 : {
                                block18 : {
                                    block17 : {
                                        block16 : {
                                            var15_16 = Parcel.obtain();
                                            var16_17 = Parcel.obtain();
                                            var15_16.writeInterfaceToken("android.app.IAlarmManager");
                                            var15_16.writeString((String)var1_1);
                                            var15_16.writeInt(var2_6);
                                            var15_16.writeLong(var3_7);
                                            var15_16.writeLong(var5_8);
                                            var15_16.writeLong(var7_9);
                                            var15_16.writeInt(var9_10);
                                            if (var10_11 == null) break block16;
                                            try {
                                                var15_16.writeInt(1);
                                                var10_11.writeToParcel(var15_16, 0);
                                                break block17;
                                            }
                                            catch (Throwable var1_2) {
                                                break block20;
                                            }
                                        }
                                        var15_16.writeInt(0);
                                    }
                                    if (var11_12 != null) {
                                        var17_18 = var11_12.asBinder();
                                    } else {
                                        var17_18 = null;
                                    }
                                    var15_16.writeStrongBinder((IBinder)var17_18);
                                    var15_16.writeString(var12_13);
                                    if (var13_14 == null) break block18;
                                    var15_16.writeInt(1);
                                    var13_14.writeToParcel(var15_16, 0);
                                    break block19;
                                }
                                var15_16.writeInt(0);
                            }
                            if (var14_15 == null) break block22;
                            var15_16.writeInt(1);
                            var14_15.writeToParcel(var15_16, 0);
                            ** GOTO lbl47
                        }
                        var15_16.writeInt(0);
lbl47: // 2 sources:
                        if (this.mRemote.transact(1, var15_16, var16_17, 0) || Stub.getDefaultImpl() == null) break block21;
                        var17_18 = Stub.getDefaultImpl();
                        try {
                            var17_18.set((String)var1_1, var2_6, var3_7, var5_8, var7_9, var9_10, var10_11, var11_12, var12_13, var13_14, var14_15);
                            var16_17.recycle();
                            var15_16.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var16_17;
                    var1_1.readException();
                    var1_1.recycle();
                    var15_16.recycle();
                    return;
                    break block20;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var16_17.recycle();
                var15_16.recycle();
                throw var1_5;
            }

            @Override
            public boolean setTime(long l) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setTime(l);
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
            public void setTimeZone(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTimeZone(string2);
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

