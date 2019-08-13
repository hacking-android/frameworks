/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.hardware.display.Time;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IColorDisplayManager
extends IInterface {
    public int getColorMode() throws RemoteException;

    public int getNightDisplayAutoMode() throws RemoteException;

    public int getNightDisplayAutoModeRaw() throws RemoteException;

    public int getNightDisplayColorTemperature() throws RemoteException;

    public Time getNightDisplayCustomEndTime() throws RemoteException;

    public Time getNightDisplayCustomStartTime() throws RemoteException;

    public int getTransformCapabilities() throws RemoteException;

    public boolean isDeviceColorManaged() throws RemoteException;

    public boolean isDisplayWhiteBalanceEnabled() throws RemoteException;

    public boolean isNightDisplayActivated() throws RemoteException;

    public boolean isSaturationActivated() throws RemoteException;

    public boolean setAppSaturationLevel(String var1, int var2) throws RemoteException;

    public void setColorMode(int var1) throws RemoteException;

    public boolean setDisplayWhiteBalanceEnabled(boolean var1) throws RemoteException;

    public boolean setNightDisplayActivated(boolean var1) throws RemoteException;

    public boolean setNightDisplayAutoMode(int var1) throws RemoteException;

    public boolean setNightDisplayColorTemperature(int var1) throws RemoteException;

    public boolean setNightDisplayCustomEndTime(Time var1) throws RemoteException;

    public boolean setNightDisplayCustomStartTime(Time var1) throws RemoteException;

    public boolean setSaturationLevel(int var1) throws RemoteException;

    public static class Default
    implements IColorDisplayManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getColorMode() throws RemoteException {
            return 0;
        }

        @Override
        public int getNightDisplayAutoMode() throws RemoteException {
            return 0;
        }

        @Override
        public int getNightDisplayAutoModeRaw() throws RemoteException {
            return 0;
        }

        @Override
        public int getNightDisplayColorTemperature() throws RemoteException {
            return 0;
        }

        @Override
        public Time getNightDisplayCustomEndTime() throws RemoteException {
            return null;
        }

        @Override
        public Time getNightDisplayCustomStartTime() throws RemoteException {
            return null;
        }

        @Override
        public int getTransformCapabilities() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isDeviceColorManaged() throws RemoteException {
            return false;
        }

        @Override
        public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isNightDisplayActivated() throws RemoteException {
            return false;
        }

        @Override
        public boolean isSaturationActivated() throws RemoteException {
            return false;
        }

        @Override
        public boolean setAppSaturationLevel(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setColorMode(int n) throws RemoteException {
        }

        @Override
        public boolean setDisplayWhiteBalanceEnabled(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNightDisplayActivated(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNightDisplayAutoMode(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNightDisplayColorTemperature(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
            return false;
        }

        @Override
        public boolean setSaturationLevel(int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IColorDisplayManager {
        private static final String DESCRIPTOR = "android.hardware.display.IColorDisplayManager";
        static final int TRANSACTION_getColorMode = 17;
        static final int TRANSACTION_getNightDisplayAutoMode = 10;
        static final int TRANSACTION_getNightDisplayAutoModeRaw = 11;
        static final int TRANSACTION_getNightDisplayColorTemperature = 8;
        static final int TRANSACTION_getNightDisplayCustomEndTime = 15;
        static final int TRANSACTION_getNightDisplayCustomStartTime = 13;
        static final int TRANSACTION_getTransformCapabilities = 5;
        static final int TRANSACTION_isDeviceColorManaged = 1;
        static final int TRANSACTION_isDisplayWhiteBalanceEnabled = 19;
        static final int TRANSACTION_isNightDisplayActivated = 6;
        static final int TRANSACTION_isSaturationActivated = 4;
        static final int TRANSACTION_setAppSaturationLevel = 3;
        static final int TRANSACTION_setColorMode = 18;
        static final int TRANSACTION_setDisplayWhiteBalanceEnabled = 20;
        static final int TRANSACTION_setNightDisplayActivated = 7;
        static final int TRANSACTION_setNightDisplayAutoMode = 12;
        static final int TRANSACTION_setNightDisplayColorTemperature = 9;
        static final int TRANSACTION_setNightDisplayCustomEndTime = 16;
        static final int TRANSACTION_setNightDisplayCustomStartTime = 14;
        static final int TRANSACTION_setSaturationLevel = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IColorDisplayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IColorDisplayManager) {
                return (IColorDisplayManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IColorDisplayManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "setDisplayWhiteBalanceEnabled";
                }
                case 19: {
                    return "isDisplayWhiteBalanceEnabled";
                }
                case 18: {
                    return "setColorMode";
                }
                case 17: {
                    return "getColorMode";
                }
                case 16: {
                    return "setNightDisplayCustomEndTime";
                }
                case 15: {
                    return "getNightDisplayCustomEndTime";
                }
                case 14: {
                    return "setNightDisplayCustomStartTime";
                }
                case 13: {
                    return "getNightDisplayCustomStartTime";
                }
                case 12: {
                    return "setNightDisplayAutoMode";
                }
                case 11: {
                    return "getNightDisplayAutoModeRaw";
                }
                case 10: {
                    return "getNightDisplayAutoMode";
                }
                case 9: {
                    return "setNightDisplayColorTemperature";
                }
                case 8: {
                    return "getNightDisplayColorTemperature";
                }
                case 7: {
                    return "setNightDisplayActivated";
                }
                case 6: {
                    return "isNightDisplayActivated";
                }
                case 5: {
                    return "getTransformCapabilities";
                }
                case 4: {
                    return "isSaturationActivated";
                }
                case 3: {
                    return "setAppSaturationLevel";
                }
                case 2: {
                    return "setSaturationLevel";
                }
                case 1: 
            }
            return "isDeviceColorManaged";
        }

        public static boolean setDefaultImpl(IColorDisplayManager iColorDisplayManager) {
            if (Proxy.sDefaultImpl == null && iColorDisplayManager != null) {
                Proxy.sDefaultImpl = iColorDisplayManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.setDisplayWhiteBalanceEnabled(bl2) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDisplayWhiteBalanceEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setColorMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getColorMode();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Time.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setNightDisplayCustomEndTime((Time)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNightDisplayCustomEndTime();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Time)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Time.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setNightDisplayCustomStartTime((Time)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNightDisplayCustomStartTime();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Time)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setNightDisplayAutoMode(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNightDisplayAutoModeRaw();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNightDisplayAutoMode();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setNightDisplayColorTemperature(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNightDisplayColorTemperature();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.setNightDisplayActivated(bl2) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNightDisplayActivated() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getTransformCapabilities();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSaturationActivated() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setAppSaturationLevel(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setSaturationLevel(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.isDeviceColorManaged() ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IColorDisplayManager {
            public static IColorDisplayManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int getColorMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getColorMode();
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
            public int getNightDisplayAutoMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getNightDisplayAutoMode();
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
            public int getNightDisplayAutoModeRaw() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getNightDisplayAutoModeRaw();
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
            public int getNightDisplayColorTemperature() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getNightDisplayColorTemperature();
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
            public Time getNightDisplayCustomEndTime() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Time time = Stub.getDefaultImpl().getNightDisplayCustomEndTime();
                        parcel.recycle();
                        parcel2.recycle();
                        return time;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Time time = parcel.readInt() != 0 ? Time.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return time;
            }

            @Override
            public Time getNightDisplayCustomStartTime() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Time time = Stub.getDefaultImpl().getNightDisplayCustomStartTime();
                        parcel.recycle();
                        parcel2.recycle();
                        return time;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Time time = parcel.readInt() != 0 ? Time.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return time;
            }

            @Override
            public int getTransformCapabilities() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getTransformCapabilities();
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
            public boolean isDeviceColorManaged() throws RemoteException {
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
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceColorManaged();
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
            public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
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
                    if (iBinder.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDisplayWhiteBalanceEnabled();
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
            public boolean isNightDisplayActivated() throws RemoteException {
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
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNightDisplayActivated();
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
            public boolean isSaturationActivated() throws RemoteException {
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
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSaturationActivated();
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
            public boolean setAppSaturationLevel(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setAppSaturationLevel(string2, n);
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
            public void setColorMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setColorMode(n);
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
            public boolean setDisplayWhiteBalanceEnabled(boolean bl) throws RemoteException {
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
                    if (this.mRemote.transact(20, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setDisplayWhiteBalanceEnabled(bl);
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
            public boolean setNightDisplayActivated(boolean bl) throws RemoteException {
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
                    if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setNightDisplayActivated(bl);
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
            public boolean setNightDisplayAutoMode(int n) throws RemoteException {
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
                    if (iBinder.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setNightDisplayAutoMode(n);
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
            public boolean setNightDisplayColorTemperature(int n) throws RemoteException {
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
                    if (iBinder.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setNightDisplayColorTemperature(n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (time != null) {
                        parcel.writeInt(1);
                        time.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setNightDisplayCustomEndTime(time);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (time != null) {
                        parcel.writeInt(1);
                        time.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setNightDisplayCustomStartTime(time);
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
            public boolean setSaturationLevel(int n) throws RemoteException {
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
                    if (iBinder.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setSaturationLevel(n);
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
        }

    }

}

