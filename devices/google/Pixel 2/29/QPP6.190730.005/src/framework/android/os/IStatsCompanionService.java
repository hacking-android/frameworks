/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.StatsDimensionsValue;
import android.os.StatsLogEventWrapper;

public interface IStatsCompanionService
extends IInterface {
    public void cancelAlarmForSubscriberTriggering() throws RemoteException;

    public void cancelAnomalyAlarm() throws RemoteException;

    public void cancelPullingAlarm() throws RemoteException;

    public StatsLogEventWrapper[] pullData(int var1) throws RemoteException;

    public void sendActiveConfigsChangedBroadcast(IBinder var1, long[] var2) throws RemoteException;

    public void sendDataBroadcast(IBinder var1, long var2) throws RemoteException;

    public void sendSubscriberBroadcast(IBinder var1, long var2, long var4, long var6, long var8, String[] var10, StatsDimensionsValue var11) throws RemoteException;

    public void setAlarmForSubscriberTriggering(long var1) throws RemoteException;

    public void setAnomalyAlarm(long var1) throws RemoteException;

    public void setPullingAlarm(long var1) throws RemoteException;

    public void statsdReady() throws RemoteException;

    public void triggerUidSnapshot() throws RemoteException;

    public static class Default
    implements IStatsCompanionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelAlarmForSubscriberTriggering() throws RemoteException {
        }

        @Override
        public void cancelAnomalyAlarm() throws RemoteException {
        }

        @Override
        public void cancelPullingAlarm() throws RemoteException {
        }

        @Override
        public StatsLogEventWrapper[] pullData(int n) throws RemoteException {
            return null;
        }

        @Override
        public void sendActiveConfigsChangedBroadcast(IBinder iBinder, long[] arrl) throws RemoteException {
        }

        @Override
        public void sendDataBroadcast(IBinder iBinder, long l) throws RemoteException {
        }

        @Override
        public void sendSubscriberBroadcast(IBinder iBinder, long l, long l2, long l3, long l4, String[] arrstring, StatsDimensionsValue statsDimensionsValue) throws RemoteException {
        }

        @Override
        public void setAlarmForSubscriberTriggering(long l) throws RemoteException {
        }

        @Override
        public void setAnomalyAlarm(long l) throws RemoteException {
        }

        @Override
        public void setPullingAlarm(long l) throws RemoteException {
        }

        @Override
        public void statsdReady() throws RemoteException {
        }

        @Override
        public void triggerUidSnapshot() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatsCompanionService {
        private static final String DESCRIPTOR = "android.os.IStatsCompanionService";
        static final int TRANSACTION_cancelAlarmForSubscriberTriggering = 7;
        static final int TRANSACTION_cancelAnomalyAlarm = 3;
        static final int TRANSACTION_cancelPullingAlarm = 5;
        static final int TRANSACTION_pullData = 8;
        static final int TRANSACTION_sendActiveConfigsChangedBroadcast = 10;
        static final int TRANSACTION_sendDataBroadcast = 9;
        static final int TRANSACTION_sendSubscriberBroadcast = 11;
        static final int TRANSACTION_setAlarmForSubscriberTriggering = 6;
        static final int TRANSACTION_setAnomalyAlarm = 2;
        static final int TRANSACTION_setPullingAlarm = 4;
        static final int TRANSACTION_statsdReady = 1;
        static final int TRANSACTION_triggerUidSnapshot = 12;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatsCompanionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatsCompanionService) {
                return (IStatsCompanionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatsCompanionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "triggerUidSnapshot";
                }
                case 11: {
                    return "sendSubscriberBroadcast";
                }
                case 10: {
                    return "sendActiveConfigsChangedBroadcast";
                }
                case 9: {
                    return "sendDataBroadcast";
                }
                case 8: {
                    return "pullData";
                }
                case 7: {
                    return "cancelAlarmForSubscriberTriggering";
                }
                case 6: {
                    return "setAlarmForSubscriberTriggering";
                }
                case 5: {
                    return "cancelPullingAlarm";
                }
                case 4: {
                    return "setPullingAlarm";
                }
                case 3: {
                    return "cancelAnomalyAlarm";
                }
                case 2: {
                    return "setAnomalyAlarm";
                }
                case 1: 
            }
            return "statsdReady";
        }

        public static boolean setDefaultImpl(IStatsCompanionService iStatsCompanionService) {
            if (Proxy.sDefaultImpl == null && iStatsCompanionService != null) {
                Proxy.sDefaultImpl = iStatsCompanionService;
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
        public boolean onTransact(int n, Parcel object, Parcel arrstring, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)arrstring, n2);
                    }
                    case 12: {
                        object.enforceInterface(DESCRIPTOR);
                        this.triggerUidSnapshot();
                        return true;
                    }
                    case 11: {
                        object.enforceInterface(DESCRIPTOR);
                        IBinder iBinder = object.readStrongBinder();
                        long l = object.readLong();
                        long l2 = object.readLong();
                        long l3 = object.readLong();
                        long l4 = object.readLong();
                        arrstring = object.createStringArray();
                        object = object.readInt() != 0 ? StatsDimensionsValue.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendSubscriberBroadcast(iBinder, l, l2, l3, l4, arrstring, (StatsDimensionsValue)object);
                        return true;
                    }
                    case 10: {
                        object.enforceInterface(DESCRIPTOR);
                        this.sendActiveConfigsChangedBroadcast(object.readStrongBinder(), object.createLongArray());
                        return true;
                    }
                    case 9: {
                        object.enforceInterface(DESCRIPTOR);
                        this.sendDataBroadcast(object.readStrongBinder(), object.readLong());
                        return true;
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.pullData(object.readInt());
                        arrstring.writeNoException();
                        arrstring.writeTypedArray(object, 1);
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        this.cancelAlarmForSubscriberTriggering();
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        this.setAlarmForSubscriberTriggering(object.readLong());
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        this.cancelPullingAlarm();
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        this.setPullingAlarm(object.readLong());
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        this.cancelAnomalyAlarm();
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        this.setAnomalyAlarm(object.readLong());
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                this.statsdReady();
                return true;
            }
            arrstring.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IStatsCompanionService {
            public static IStatsCompanionService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelAlarmForSubscriberTriggering() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAlarmForSubscriberTriggering();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void cancelAnomalyAlarm() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAnomalyAlarm();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void cancelPullingAlarm() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelPullingAlarm();
                        return;
                    }
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
            public StatsLogEventWrapper[] pullData(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StatsLogEventWrapper[] arrstatsLogEventWrapper = Stub.getDefaultImpl().pullData(n);
                        return arrstatsLogEventWrapper;
                    }
                    parcel2.readException();
                    StatsLogEventWrapper[] arrstatsLogEventWrapper = parcel2.createTypedArray(StatsLogEventWrapper.CREATOR);
                    return arrstatsLogEventWrapper;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void sendActiveConfigsChangedBroadcast(IBinder iBinder, long[] arrl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLongArray(arrl);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendActiveConfigsChangedBroadcast(iBinder, arrl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendDataBroadcast(IBinder iBinder, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendDataBroadcast(iBinder, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void sendSubscriberBroadcast(IBinder iBinder, long l, long l2, long l3, long l4, String[] arrstring, StatsDimensionsValue statsDimensionsValue) throws RemoteException {
                void var1_4;
                Parcel parcel;
                block7 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        parcel.writeLong(l3);
                        parcel.writeLong(l4);
                        parcel.writeStringArray(arrstring);
                        if (statsDimensionsValue != null) {
                            parcel.writeInt(1);
                            statsDimensionsValue.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendSubscriberBroadcast(iBinder, l, l2, l3, l4, arrstring, statsDimensionsValue);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block7;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_4;
            }

            @Override
            public void setAlarmForSubscriberTriggering(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAlarmForSubscriberTriggering(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAnomalyAlarm(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAnomalyAlarm(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPullingAlarm(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPullingAlarm(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void statsdReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().statsdReady();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void triggerUidSnapshot() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().triggerUidSnapshot();
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

