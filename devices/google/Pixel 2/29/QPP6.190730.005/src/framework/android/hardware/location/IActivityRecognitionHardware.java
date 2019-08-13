/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.IActivityRecognitionHardwareSink;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IActivityRecognitionHardware
extends IInterface {
    public boolean disableActivityEvent(String var1, int var2) throws RemoteException;

    public boolean enableActivityEvent(String var1, int var2, long var3) throws RemoteException;

    public boolean flush() throws RemoteException;

    public String[] getSupportedActivities() throws RemoteException;

    public boolean isActivitySupported(String var1) throws RemoteException;

    public boolean registerSink(IActivityRecognitionHardwareSink var1) throws RemoteException;

    public boolean unregisterSink(IActivityRecognitionHardwareSink var1) throws RemoteException;

    public static class Default
    implements IActivityRecognitionHardware {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean disableActivityEvent(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean enableActivityEvent(String string2, int n, long l) throws RemoteException {
            return false;
        }

        @Override
        public boolean flush() throws RemoteException {
            return false;
        }

        @Override
        public String[] getSupportedActivities() throws RemoteException {
            return null;
        }

        @Override
        public boolean isActivitySupported(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
            return false;
        }

        @Override
        public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActivityRecognitionHardware {
        private static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardware";
        static final int TRANSACTION_disableActivityEvent = 6;
        static final int TRANSACTION_enableActivityEvent = 5;
        static final int TRANSACTION_flush = 7;
        static final int TRANSACTION_getSupportedActivities = 1;
        static final int TRANSACTION_isActivitySupported = 2;
        static final int TRANSACTION_registerSink = 3;
        static final int TRANSACTION_unregisterSink = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActivityRecognitionHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActivityRecognitionHardware) {
                return (IActivityRecognitionHardware)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActivityRecognitionHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "flush";
                }
                case 6: {
                    return "disableActivityEvent";
                }
                case 5: {
                    return "enableActivityEvent";
                }
                case 4: {
                    return "unregisterSink";
                }
                case 3: {
                    return "registerSink";
                }
                case 2: {
                    return "isActivitySupported";
                }
                case 1: 
            }
            return "getSupportedActivities";
        }

        public static boolean setDefaultImpl(IActivityRecognitionHardware iActivityRecognitionHardware) {
            if (Proxy.sDefaultImpl == null && iActivityRecognitionHardware != null) {
                Proxy.sDefaultImpl = iActivityRecognitionHardware;
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
        public boolean onTransact(int n, Parcel arrstring, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrstring, parcel, n2);
                    }
                    case 7: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.flush() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.disableActivityEvent(arrstring.readString(), arrstring.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.enableActivityEvent(arrstring.readString(), arrstring.readInt(), arrstring.readLong()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.unregisterSink(IActivityRecognitionHardwareSink.Stub.asInterface(arrstring.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.registerSink(IActivityRecognitionHardwareSink.Stub.asInterface(arrstring.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        arrstring.enforceInterface(DESCRIPTOR);
                        n = this.isActivitySupported(arrstring.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                arrstring.enforceInterface(DESCRIPTOR);
                arrstring = this.getSupportedActivities();
                parcel.writeNoException();
                parcel.writeStringArray(arrstring);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IActivityRecognitionHardware {
            public static IActivityRecognitionHardware sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean disableActivityEvent(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableActivityEvent(string2, n);
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
            public boolean enableActivityEvent(String string2, int n, long l) throws RemoteException {
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
                        parcel.writeInt(n);
                        parcel.writeLong(l);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableActivityEvent(string2, n, l);
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
            public boolean flush() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().flush();
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
            public String[] getSupportedActivities() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSupportedActivities();
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
            public boolean isActivitySupported(String string2) throws RemoteException {
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
                    if (iBinder.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isActivitySupported(string2);
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
            public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
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
                                if (iActivityRecognitionHardwareSink == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iActivityRecognitionHardwareSink.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerSink(iActivityRecognitionHardwareSink);
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
            public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
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
                                if (iActivityRecognitionHardwareSink == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iActivityRecognitionHardwareSink.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unregisterSink(iActivityRecognitionHardwareSink);
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

