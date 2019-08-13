/*
 * Decompiled with CFR 0.145.
 */
package android.content.om;

import android.annotation.UnsupportedAppUsage;
import android.content.om.OverlayInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IOverlayManager
extends IInterface {
    @UnsupportedAppUsage
    public Map getAllOverlays(int var1) throws RemoteException;

    public String[] getDefaultOverlayPackages() throws RemoteException;

    @UnsupportedAppUsage
    public OverlayInfo getOverlayInfo(String var1, int var2) throws RemoteException;

    public List getOverlayInfosForTarget(String var1, int var2) throws RemoteException;

    public boolean setEnabled(String var1, boolean var2, int var3) throws RemoteException;

    public boolean setEnabledExclusive(String var1, boolean var2, int var3) throws RemoteException;

    public boolean setEnabledExclusiveInCategory(String var1, int var2) throws RemoteException;

    public boolean setHighestPriority(String var1, int var2) throws RemoteException;

    public boolean setLowestPriority(String var1, int var2) throws RemoteException;

    public boolean setPriority(String var1, String var2, int var3) throws RemoteException;

    public static class Default
    implements IOverlayManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Map getAllOverlays(int n) throws RemoteException {
            return null;
        }

        @Override
        public String[] getDefaultOverlayPackages() throws RemoteException {
            return null;
        }

        @Override
        public OverlayInfo getOverlayInfo(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public List getOverlayInfosForTarget(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean setEnabled(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setEnabledExclusive(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setEnabledExclusiveInCategory(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setHighestPriority(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setLowestPriority(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setPriority(String string2, String string3, int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOverlayManager {
        private static final String DESCRIPTOR = "android.content.om.IOverlayManager";
        static final int TRANSACTION_getAllOverlays = 1;
        static final int TRANSACTION_getDefaultOverlayPackages = 10;
        static final int TRANSACTION_getOverlayInfo = 3;
        static final int TRANSACTION_getOverlayInfosForTarget = 2;
        static final int TRANSACTION_setEnabled = 4;
        static final int TRANSACTION_setEnabledExclusive = 5;
        static final int TRANSACTION_setEnabledExclusiveInCategory = 6;
        static final int TRANSACTION_setHighestPriority = 8;
        static final int TRANSACTION_setLowestPriority = 9;
        static final int TRANSACTION_setPriority = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOverlayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOverlayManager) {
                return (IOverlayManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOverlayManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "getDefaultOverlayPackages";
                }
                case 9: {
                    return "setLowestPriority";
                }
                case 8: {
                    return "setHighestPriority";
                }
                case 7: {
                    return "setPriority";
                }
                case 6: {
                    return "setEnabledExclusiveInCategory";
                }
                case 5: {
                    return "setEnabledExclusive";
                }
                case 4: {
                    return "setEnabled";
                }
                case 3: {
                    return "getOverlayInfo";
                }
                case 2: {
                    return "getOverlayInfosForTarget";
                }
                case 1: 
            }
            return "getAllOverlays";
        }

        public static boolean setDefaultImpl(IOverlayManager iOverlayManager) {
            if (Proxy.sDefaultImpl == null && iOverlayManager != null) {
                Proxy.sDefaultImpl = iOverlayManager;
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
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultOverlayPackages();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setLowestPriority(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setHighestPriority(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setPriority(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setEnabledExclusiveInCategory(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.setEnabledExclusive(string2, bl2, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.setEnabled(string3, bl2, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getOverlayInfo(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((OverlayInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getOverlayInfosForTarget(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeList((List)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getAllOverlays(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeMap((Map)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IOverlayManager {
            public static IOverlayManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public Map getAllOverlays(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Map map = Stub.getDefaultImpl().getAllOverlays(n);
                        return map;
                    }
                    parcel2.readException();
                    HashMap hashMap = parcel2.readHashMap(this.getClass().getClassLoader());
                    return hashMap;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getDefaultOverlayPackages() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getDefaultOverlayPackages();
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
            public OverlayInfo getOverlayInfo(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getOverlayInfo((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? OverlayInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public List getOverlayInfosForTarget(String object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getOverlayInfosForTarget((String)object, n);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readArrayList(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setEnabled(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setEnabled(string2, bl, n);
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
            public boolean setEnabledExclusive(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setEnabledExclusive(string2, bl, n);
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
            public boolean setEnabledExclusiveInCategory(String string2, int n) throws RemoteException {
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
                    bl = Stub.getDefaultImpl().setEnabledExclusiveInCategory(string2, n);
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
            public boolean setHighestPriority(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setHighestPriority(string2, n);
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
            public boolean setLowestPriority(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setLowestPriority(string2, n);
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
            public boolean setPriority(String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
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
                    bl = Stub.getDefaultImpl().setPriority(string2, string3, n);
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

