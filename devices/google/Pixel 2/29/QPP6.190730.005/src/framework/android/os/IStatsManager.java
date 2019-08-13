/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IStatsPullerCallback;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IStatsManager
extends IInterface {
    public static final int FLAG_REQUIRE_LOW_LATENCY_MONITOR = 4;
    public static final int FLAG_REQUIRE_STAGING = 1;
    public static final int FLAG_ROLLBACK_ENABLED = 2;

    public void addConfiguration(long var1, byte[] var3, String var4) throws RemoteException;

    public byte[] getData(long var1, String var3) throws RemoteException;

    public byte[] getMetadata(String var1) throws RemoteException;

    public long[] getRegisteredExperimentIds() throws RemoteException;

    public void informAlarmForSubscriberTriggeringFired() throws RemoteException;

    public void informAllUidData(ParcelFileDescriptor var1) throws RemoteException;

    public void informAnomalyAlarmFired() throws RemoteException;

    public void informDeviceShutdown() throws RemoteException;

    public void informOnePackage(String var1, int var2, long var3, String var5, String var6) throws RemoteException;

    public void informOnePackageRemoved(String var1, int var2) throws RemoteException;

    public void informPollAlarmFired() throws RemoteException;

    public void registerPullerCallback(int var1, IStatsPullerCallback var2, String var3) throws RemoteException;

    public void removeActiveConfigsChangedOperation(String var1) throws RemoteException;

    public void removeConfiguration(long var1, String var3) throws RemoteException;

    public void removeDataFetchOperation(long var1, String var3) throws RemoteException;

    public void sendAppBreadcrumbAtom(int var1, int var2) throws RemoteException;

    public void sendBinaryPushStateChangedAtom(String var1, long var2, int var4, int var5, long[] var6) throws RemoteException;

    public void sendWatchdogRollbackOccurredAtom(int var1, String var2, long var3) throws RemoteException;

    public long[] setActiveConfigsChangedOperation(IBinder var1, String var2) throws RemoteException;

    public void setBroadcastSubscriber(long var1, long var3, IBinder var5, String var6) throws RemoteException;

    public void setDataFetchOperation(long var1, IBinder var3, String var4) throws RemoteException;

    public void statsCompanionReady() throws RemoteException;

    public void systemRunning() throws RemoteException;

    public void unregisterPullerCallback(int var1, String var2) throws RemoteException;

    public void unsetBroadcastSubscriber(long var1, long var3, String var5) throws RemoteException;

    public static class Default
    implements IStatsManager {
        @Override
        public void addConfiguration(long l, byte[] arrby, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public byte[] getData(long l, String string2) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getMetadata(String string2) throws RemoteException {
            return null;
        }

        @Override
        public long[] getRegisteredExperimentIds() throws RemoteException {
            return null;
        }

        @Override
        public void informAlarmForSubscriberTriggeringFired() throws RemoteException {
        }

        @Override
        public void informAllUidData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void informAnomalyAlarmFired() throws RemoteException {
        }

        @Override
        public void informDeviceShutdown() throws RemoteException {
        }

        @Override
        public void informOnePackage(String string2, int n, long l, String string3, String string4) throws RemoteException {
        }

        @Override
        public void informOnePackageRemoved(String string2, int n) throws RemoteException {
        }

        @Override
        public void informPollAlarmFired() throws RemoteException {
        }

        @Override
        public void registerPullerCallback(int n, IStatsPullerCallback iStatsPullerCallback, String string2) throws RemoteException {
        }

        @Override
        public void removeActiveConfigsChangedOperation(String string2) throws RemoteException {
        }

        @Override
        public void removeConfiguration(long l, String string2) throws RemoteException {
        }

        @Override
        public void removeDataFetchOperation(long l, String string2) throws RemoteException {
        }

        @Override
        public void sendAppBreadcrumbAtom(int n, int n2) throws RemoteException {
        }

        @Override
        public void sendBinaryPushStateChangedAtom(String string2, long l, int n, int n2, long[] arrl) throws RemoteException {
        }

        @Override
        public void sendWatchdogRollbackOccurredAtom(int n, String string2, long l) throws RemoteException {
        }

        @Override
        public long[] setActiveConfigsChangedOperation(IBinder iBinder, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void setBroadcastSubscriber(long l, long l2, IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void setDataFetchOperation(long l, IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void statsCompanionReady() throws RemoteException {
        }

        @Override
        public void systemRunning() throws RemoteException {
        }

        @Override
        public void unregisterPullerCallback(int n, String string2) throws RemoteException {
        }

        @Override
        public void unsetBroadcastSubscriber(long l, long l2, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatsManager {
        private static final String DESCRIPTOR = "android.os.IStatsManager";
        static final int TRANSACTION_addConfiguration = 12;
        static final int TRANSACTION_getData = 10;
        static final int TRANSACTION_getMetadata = 11;
        static final int TRANSACTION_getRegisteredExperimentIds = 25;
        static final int TRANSACTION_informAlarmForSubscriberTriggeringFired = 5;
        static final int TRANSACTION_informAllUidData = 7;
        static final int TRANSACTION_informAnomalyAlarmFired = 3;
        static final int TRANSACTION_informDeviceShutdown = 6;
        static final int TRANSACTION_informOnePackage = 8;
        static final int TRANSACTION_informOnePackageRemoved = 9;
        static final int TRANSACTION_informPollAlarmFired = 4;
        static final int TRANSACTION_registerPullerCallback = 21;
        static final int TRANSACTION_removeActiveConfigsChangedOperation = 16;
        static final int TRANSACTION_removeConfiguration = 17;
        static final int TRANSACTION_removeDataFetchOperation = 14;
        static final int TRANSACTION_sendAppBreadcrumbAtom = 20;
        static final int TRANSACTION_sendBinaryPushStateChangedAtom = 23;
        static final int TRANSACTION_sendWatchdogRollbackOccurredAtom = 24;
        static final int TRANSACTION_setActiveConfigsChangedOperation = 15;
        static final int TRANSACTION_setBroadcastSubscriber = 18;
        static final int TRANSACTION_setDataFetchOperation = 13;
        static final int TRANSACTION_statsCompanionReady = 2;
        static final int TRANSACTION_systemRunning = 1;
        static final int TRANSACTION_unregisterPullerCallback = 22;
        static final int TRANSACTION_unsetBroadcastSubscriber = 19;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatsManager) {
                return (IStatsManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 25: {
                    return "getRegisteredExperimentIds";
                }
                case 24: {
                    return "sendWatchdogRollbackOccurredAtom";
                }
                case 23: {
                    return "sendBinaryPushStateChangedAtom";
                }
                case 22: {
                    return "unregisterPullerCallback";
                }
                case 21: {
                    return "registerPullerCallback";
                }
                case 20: {
                    return "sendAppBreadcrumbAtom";
                }
                case 19: {
                    return "unsetBroadcastSubscriber";
                }
                case 18: {
                    return "setBroadcastSubscriber";
                }
                case 17: {
                    return "removeConfiguration";
                }
                case 16: {
                    return "removeActiveConfigsChangedOperation";
                }
                case 15: {
                    return "setActiveConfigsChangedOperation";
                }
                case 14: {
                    return "removeDataFetchOperation";
                }
                case 13: {
                    return "setDataFetchOperation";
                }
                case 12: {
                    return "addConfiguration";
                }
                case 11: {
                    return "getMetadata";
                }
                case 10: {
                    return "getData";
                }
                case 9: {
                    return "informOnePackageRemoved";
                }
                case 8: {
                    return "informOnePackage";
                }
                case 7: {
                    return "informAllUidData";
                }
                case 6: {
                    return "informDeviceShutdown";
                }
                case 5: {
                    return "informAlarmForSubscriberTriggeringFired";
                }
                case 4: {
                    return "informPollAlarmFired";
                }
                case 3: {
                    return "informAnomalyAlarmFired";
                }
                case 2: {
                    return "statsCompanionReady";
                }
                case 1: 
            }
            return "systemRunning";
        }

        public static boolean setDefaultImpl(IStatsManager iStatsManager) {
            if (Proxy.sDefaultImpl == null && iStatsManager != null) {
                Proxy.sDefaultImpl = iStatsManager;
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
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRegisteredExperimentIds();
                        parcel.writeNoException();
                        parcel.writeLongArray((long[])object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendWatchdogRollbackOccurredAtom(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readLong());
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendBinaryPushStateChangedAtom(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createLongArray());
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterPullerCallback(((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerPullerCallback(((Parcel)object).readInt(), IStatsPullerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendAppBreadcrumbAtom(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unsetBroadcastSubscriber(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setBroadcastSubscriber(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeConfiguration(((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeActiveConfigsChangedOperation(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.setActiveConfigsChangedOperation(((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLongArray((long[])object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeDataFetchOperation(((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDataFetchOperation(((Parcel)object).readLong(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addConfiguration(((Parcel)object).readLong(), ((Parcel)object).createByteArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMetadata(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getData(((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informOnePackageRemoved(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informOnePackage(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.informAllUidData((ParcelFileDescriptor)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informDeviceShutdown();
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informAlarmForSubscriberTriggeringFired();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informPollAlarmFired();
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.informAnomalyAlarmFired();
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.statsCompanionReady();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.systemRunning();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IStatsManager {
            public static IStatsManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addConfiguration(long l, byte[] arrby, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addConfiguration(l, arrby, string2);
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
            public byte[] getData(long l, String arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString((String)arrby);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getData(l, (String)arrby);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
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
            public byte[] getMetadata(String arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getMetadata((String)arrby);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long[] getRegisteredExperimentIds() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long[] arrl = Stub.getDefaultImpl().getRegisteredExperimentIds();
                        return arrl;
                    }
                    parcel2.readException();
                    long[] arrl = parcel2.createLongArray();
                    return arrl;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void informAlarmForSubscriberTriggeringFired() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informAlarmForSubscriberTriggeringFired();
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
            public void informAllUidData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informAllUidData(parcelFileDescriptor);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void informAnomalyAlarmFired() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informAnomalyAlarmFired();
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
            public void informDeviceShutdown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informDeviceShutdown();
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void informOnePackage(String string2, int n, long l, String string3, String string4) throws RemoteException {
                Parcel parcel;
                void var1_9;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().informOnePackage(string2, n, l, string3, string4);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_9;
            }

            @Override
            public void informOnePackageRemoved(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informOnePackageRemoved(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void informPollAlarmFired() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().informPollAlarmFired();
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerPullerCallback(int n, IStatsPullerCallback iStatsPullerCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iStatsPullerCallback != null ? iStatsPullerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerPullerCallback(n, iStatsPullerCallback, string2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeActiveConfigsChangedOperation(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeActiveConfigsChangedOperation(string2);
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
            public void removeConfiguration(long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeConfiguration(l, string2);
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
            public void removeDataFetchOperation(long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeDataFetchOperation(l, string2);
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
            public void sendAppBreadcrumbAtom(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendAppBreadcrumbAtom(n, n2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void sendBinaryPushStateChangedAtom(String string2, long l, int n, int n2, long[] arrl) throws RemoteException {
                Parcel parcel;
                void var1_9;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeLongArray(arrl);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendBinaryPushStateChangedAtom(string2, l, n, n2, arrl);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_9;
            }

            @Override
            public void sendWatchdogRollbackOccurredAtom(int n, String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendWatchdogRollbackOccurredAtom(n, string2, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public long[] setActiveConfigsChangedOperation(IBinder arrl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)arrl);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrl = Stub.getDefaultImpl().setActiveConfigsChangedOperation((IBinder)arrl, string2);
                        return arrl;
                    }
                    parcel2.readException();
                    arrl = parcel2.createLongArray();
                    return arrl;
                }
                finally {
                    parcel2.recycle();
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
            public void setBroadcastSubscriber(long l, long l2, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel;
                void var5_9;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(18, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setBroadcastSubscriber(l, l2, iBinder, string2);
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
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var5_9;
            }

            @Override
            public void setDataFetchOperation(long l, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDataFetchOperation(l, iBinder, string2);
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
            public void statsCompanionReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().statsCompanionReady();
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
            public void systemRunning() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().systemRunning();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unregisterPullerCallback(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterPullerCallback(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unsetBroadcastSubscriber(long l, long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unsetBroadcastSubscriber(l, l2, string2);
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

