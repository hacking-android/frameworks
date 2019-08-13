/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;

public interface ITunerCallback
extends IInterface {
    public void onAntennaState(boolean var1) throws RemoteException;

    public void onBackgroundScanAvailabilityChange(boolean var1) throws RemoteException;

    public void onBackgroundScanComplete() throws RemoteException;

    public void onConfigurationChanged(RadioManager.BandConfig var1) throws RemoteException;

    public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo var1) throws RemoteException;

    public void onEmergencyAnnouncement(boolean var1) throws RemoteException;

    public void onError(int var1) throws RemoteException;

    public void onParametersUpdated(Map var1) throws RemoteException;

    public void onProgramListChanged() throws RemoteException;

    public void onProgramListUpdated(ProgramList.Chunk var1) throws RemoteException;

    public void onTrafficAnnouncement(boolean var1) throws RemoteException;

    public void onTuneFailed(int var1, ProgramSelector var2) throws RemoteException;

    public static class Default
    implements ITunerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAntennaState(boolean bl) throws RemoteException {
        }

        @Override
        public void onBackgroundScanAvailabilityChange(boolean bl) throws RemoteException {
        }

        @Override
        public void onBackgroundScanComplete() throws RemoteException {
        }

        @Override
        public void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException {
        }

        @Override
        public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException {
        }

        @Override
        public void onEmergencyAnnouncement(boolean bl) throws RemoteException {
        }

        @Override
        public void onError(int n) throws RemoteException {
        }

        @Override
        public void onParametersUpdated(Map map) throws RemoteException {
        }

        @Override
        public void onProgramListChanged() throws RemoteException {
        }

        @Override
        public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
        }

        @Override
        public void onTrafficAnnouncement(boolean bl) throws RemoteException {
        }

        @Override
        public void onTuneFailed(int n, ProgramSelector programSelector) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITunerCallback {
        private static final String DESCRIPTOR = "android.hardware.radio.ITunerCallback";
        static final int TRANSACTION_onAntennaState = 7;
        static final int TRANSACTION_onBackgroundScanAvailabilityChange = 8;
        static final int TRANSACTION_onBackgroundScanComplete = 9;
        static final int TRANSACTION_onConfigurationChanged = 3;
        static final int TRANSACTION_onCurrentProgramInfoChanged = 4;
        static final int TRANSACTION_onEmergencyAnnouncement = 6;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onParametersUpdated = 12;
        static final int TRANSACTION_onProgramListChanged = 10;
        static final int TRANSACTION_onProgramListUpdated = 11;
        static final int TRANSACTION_onTrafficAnnouncement = 5;
        static final int TRANSACTION_onTuneFailed = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITunerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITunerCallback) {
                return (ITunerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITunerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "onParametersUpdated";
                }
                case 11: {
                    return "onProgramListUpdated";
                }
                case 10: {
                    return "onProgramListChanged";
                }
                case 9: {
                    return "onBackgroundScanComplete";
                }
                case 8: {
                    return "onBackgroundScanAvailabilityChange";
                }
                case 7: {
                    return "onAntennaState";
                }
                case 6: {
                    return "onEmergencyAnnouncement";
                }
                case 5: {
                    return "onTrafficAnnouncement";
                }
                case 4: {
                    return "onCurrentProgramInfoChanged";
                }
                case 3: {
                    return "onConfigurationChanged";
                }
                case 2: {
                    return "onTuneFailed";
                }
                case 1: 
            }
            return "onError";
        }

        public static boolean setDefaultImpl(ITunerCallback iTunerCallback) {
            if (Proxy.sDefaultImpl == null && iTunerCallback != null) {
                Proxy.sDefaultImpl = iTunerCallback;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onParametersUpdated(((Parcel)object).readHashMap(this.getClass().getClassLoader()));
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ProgramList.Chunk.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onProgramListUpdated((ProgramList.Chunk)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onProgramListChanged();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBackgroundScanComplete();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onBackgroundScanAvailabilityChange(bl4);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onAntennaState(bl4);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onEmergencyAnnouncement(bl4);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onTrafficAnnouncement(bl4);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RadioManager.ProgramInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCurrentProgramInfoChanged((RadioManager.ProgramInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RadioManager.BandConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onConfigurationChanged((RadioManager.BandConfig)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ProgramSelector.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTuneFailed(n, (ProgramSelector)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onError(((Parcel)object).readInt());
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITunerCallback {
            public static ITunerCallback sDefaultImpl;
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
            public void onAntennaState(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAntennaState(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBackgroundScanAvailabilityChange(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBackgroundScanAvailabilityChange(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBackgroundScanComplete() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBackgroundScanComplete();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bandConfig != null) {
                        parcel.writeInt(1);
                        bandConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfigurationChanged(bandConfig);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (programInfo != null) {
                        parcel.writeInt(1);
                        programInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCurrentProgramInfoChanged(programInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEmergencyAnnouncement(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEmergencyAnnouncement(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onParametersUpdated(Map map) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onParametersUpdated(map);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProgramListChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgramListChanged();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (chunk != null) {
                        parcel.writeInt(1);
                        chunk.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgramListUpdated(chunk);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrafficAnnouncement(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrafficAnnouncement(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTuneFailed(int n, ProgramSelector programSelector) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (programSelector != null) {
                        parcel.writeInt(1);
                        programSelector.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTuneFailed(n, programSelector);
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

