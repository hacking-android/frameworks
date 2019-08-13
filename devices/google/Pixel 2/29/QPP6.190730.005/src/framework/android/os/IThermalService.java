/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.CoolingDevice;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IThermalEventListener;
import android.os.IThermalStatusListener;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Temperature;
import java.util.ArrayList;
import java.util.List;

public interface IThermalService
extends IInterface {
    public List<CoolingDevice> getCurrentCoolingDevices() throws RemoteException;

    public List<CoolingDevice> getCurrentCoolingDevicesWithType(int var1) throws RemoteException;

    public List<Temperature> getCurrentTemperatures() throws RemoteException;

    public List<Temperature> getCurrentTemperaturesWithType(int var1) throws RemoteException;

    public int getCurrentThermalStatus() throws RemoteException;

    public boolean registerThermalEventListener(IThermalEventListener var1) throws RemoteException;

    public boolean registerThermalEventListenerWithType(IThermalEventListener var1, int var2) throws RemoteException;

    public boolean registerThermalStatusListener(IThermalStatusListener var1) throws RemoteException;

    public boolean unregisterThermalEventListener(IThermalEventListener var1) throws RemoteException;

    public boolean unregisterThermalStatusListener(IThermalStatusListener var1) throws RemoteException;

    public static class Default
    implements IThermalService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public List<CoolingDevice> getCurrentCoolingDevices() throws RemoteException {
            return null;
        }

        @Override
        public List<CoolingDevice> getCurrentCoolingDevicesWithType(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<Temperature> getCurrentTemperatures() throws RemoteException {
            return null;
        }

        @Override
        public List<Temperature> getCurrentTemperaturesWithType(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getCurrentThermalStatus() throws RemoteException {
            return 0;
        }

        @Override
        public boolean registerThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerThermalEventListenerWithType(IThermalEventListener iThermalEventListener, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
            return false;
        }

        @Override
        public boolean unregisterThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
            return false;
        }

        @Override
        public boolean unregisterThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IThermalService {
        private static final String DESCRIPTOR = "android.os.IThermalService";
        static final int TRANSACTION_getCurrentCoolingDevices = 9;
        static final int TRANSACTION_getCurrentCoolingDevicesWithType = 10;
        static final int TRANSACTION_getCurrentTemperatures = 4;
        static final int TRANSACTION_getCurrentTemperaturesWithType = 5;
        static final int TRANSACTION_getCurrentThermalStatus = 8;
        static final int TRANSACTION_registerThermalEventListener = 1;
        static final int TRANSACTION_registerThermalEventListenerWithType = 2;
        static final int TRANSACTION_registerThermalStatusListener = 6;
        static final int TRANSACTION_unregisterThermalEventListener = 3;
        static final int TRANSACTION_unregisterThermalStatusListener = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IThermalService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IThermalService) {
                return (IThermalService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IThermalService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "getCurrentCoolingDevicesWithType";
                }
                case 9: {
                    return "getCurrentCoolingDevices";
                }
                case 8: {
                    return "getCurrentThermalStatus";
                }
                case 7: {
                    return "unregisterThermalStatusListener";
                }
                case 6: {
                    return "registerThermalStatusListener";
                }
                case 5: {
                    return "getCurrentTemperaturesWithType";
                }
                case 4: {
                    return "getCurrentTemperatures";
                }
                case 3: {
                    return "unregisterThermalEventListener";
                }
                case 2: {
                    return "registerThermalEventListenerWithType";
                }
                case 1: 
            }
            return "registerThermalEventListener";
        }

        public static boolean setDefaultImpl(IThermalService iThermalService) {
            if (Proxy.sDefaultImpl == null && iThermalService != null) {
                Proxy.sDefaultImpl = iThermalService;
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
        public boolean onTransact(int n, Parcel list, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)list), parcel, n2);
                    }
                    case 10: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getCurrentCoolingDevicesWithType(((Parcel)((Object)list)).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(list);
                        return true;
                    }
                    case 9: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getCurrentCoolingDevices();
                        parcel.writeNoException();
                        parcel.writeTypedList(list);
                        return true;
                    }
                    case 8: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.getCurrentThermalStatus();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.unregisterThermalStatusListener(IThermalStatusListener.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.registerThermalStatusListener(IThermalStatusListener.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getCurrentTemperaturesWithType(((Parcel)((Object)list)).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(list);
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getCurrentTemperatures();
                        parcel.writeNoException();
                        parcel.writeTypedList(list);
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.unregisterThermalEventListener(IThermalEventListener.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.registerThermalEventListenerWithType(IThermalEventListener.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()), ((Parcel)((Object)list)).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                n = this.registerThermalEventListener(IThermalEventListener.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder())) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IThermalService {
            public static IThermalService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public List<CoolingDevice> getCurrentCoolingDevices() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<CoolingDevice> list = Stub.getDefaultImpl().getCurrentCoolingDevices();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<CoolingDevice> arrayList = parcel2.createTypedArrayList(CoolingDevice.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<CoolingDevice> getCurrentCoolingDevicesWithType(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<CoolingDevice> list = Stub.getDefaultImpl().getCurrentCoolingDevicesWithType(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<CoolingDevice> arrayList = parcel2.createTypedArrayList(CoolingDevice.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<Temperature> getCurrentTemperatures() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<Temperature> list = Stub.getDefaultImpl().getCurrentTemperatures();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<Temperature> arrayList = parcel2.createTypedArrayList(Temperature.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<Temperature> getCurrentTemperaturesWithType(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<Temperature> list = Stub.getDefaultImpl().getCurrentTemperaturesWithType(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<Temperature> arrayList = parcel2.createTypedArrayList(Temperature.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCurrentThermalStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCurrentThermalStatus();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean registerThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
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
                                if (iThermalEventListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iThermalEventListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerThermalEventListener(iThermalEventListener);
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
            public boolean registerThermalEventListenerWithType(IThermalEventListener iThermalEventListener, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iThermalEventListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iThermalEventListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeInt(n);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerThermalEventListenerWithType(iThermalEventListener, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean registerThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
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
                                if (iThermalStatusListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iThermalStatusListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerThermalStatusListener(iThermalStatusListener);
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
            public boolean unregisterThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
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
                                if (iThermalEventListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iThermalEventListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unregisterThermalEventListener(iThermalEventListener);
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
            public boolean unregisterThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
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
                                if (iThermalStatusListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iThermalStatusListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unregisterThermalStatusListener(iThermalStatusListener);
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
        }

    }

}

