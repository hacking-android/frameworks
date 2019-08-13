/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.app.role.IOnRoleHoldersChangedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.telephony.IFinancialSmsCallback;
import java.util.ArrayList;
import java.util.List;

public interface IRoleManager
extends IInterface {
    public void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener var1, int var2) throws RemoteException;

    public void addRoleHolderAsUser(String var1, String var2, int var3, int var4, RemoteCallback var5) throws RemoteException;

    public boolean addRoleHolderFromController(String var1, String var2) throws RemoteException;

    public void clearRoleHoldersAsUser(String var1, int var2, int var3, RemoteCallback var4) throws RemoteException;

    public String getDefaultSmsPackage(int var1) throws RemoteException;

    public List<String> getHeldRolesFromController(String var1) throws RemoteException;

    public List<String> getRoleHoldersAsUser(String var1, int var2) throws RemoteException;

    public void getSmsMessagesForFinancialApp(String var1, Bundle var2, IFinancialSmsCallback var3) throws RemoteException;

    public boolean isRoleAvailable(String var1) throws RemoteException;

    public boolean isRoleHeld(String var1, String var2) throws RemoteException;

    public void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener var1, int var2) throws RemoteException;

    public void removeRoleHolderAsUser(String var1, String var2, int var3, int var4, RemoteCallback var5) throws RemoteException;

    public boolean removeRoleHolderFromController(String var1, String var2) throws RemoteException;

    public void setRoleNamesFromController(List<String> var1) throws RemoteException;

    public static class Default
    implements IRoleManager {
        @Override
        public void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int n) throws RemoteException {
        }

        @Override
        public void addRoleHolderAsUser(String string2, String string3, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public boolean addRoleHolderFromController(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearRoleHoldersAsUser(String string2, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public String getDefaultSmsPackage(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getHeldRolesFromController(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getRoleHoldersAsUser(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public void getSmsMessagesForFinancialApp(String string2, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException {
        }

        @Override
        public boolean isRoleAvailable(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRoleHeld(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int n) throws RemoteException {
        }

        @Override
        public void removeRoleHolderAsUser(String string2, String string3, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public boolean removeRoleHolderFromController(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void setRoleNamesFromController(List<String> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRoleManager {
        private static final String DESCRIPTOR = "android.app.role.IRoleManager";
        static final int TRANSACTION_addOnRoleHoldersChangedListenerAsUser = 7;
        static final int TRANSACTION_addRoleHolderAsUser = 4;
        static final int TRANSACTION_addRoleHolderFromController = 10;
        static final int TRANSACTION_clearRoleHoldersAsUser = 6;
        static final int TRANSACTION_getDefaultSmsPackage = 13;
        static final int TRANSACTION_getHeldRolesFromController = 12;
        static final int TRANSACTION_getRoleHoldersAsUser = 3;
        static final int TRANSACTION_getSmsMessagesForFinancialApp = 14;
        static final int TRANSACTION_isRoleAvailable = 1;
        static final int TRANSACTION_isRoleHeld = 2;
        static final int TRANSACTION_removeOnRoleHoldersChangedListenerAsUser = 8;
        static final int TRANSACTION_removeRoleHolderAsUser = 5;
        static final int TRANSACTION_removeRoleHolderFromController = 11;
        static final int TRANSACTION_setRoleNamesFromController = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRoleManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRoleManager) {
                return (IRoleManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRoleManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "getSmsMessagesForFinancialApp";
                }
                case 13: {
                    return "getDefaultSmsPackage";
                }
                case 12: {
                    return "getHeldRolesFromController";
                }
                case 11: {
                    return "removeRoleHolderFromController";
                }
                case 10: {
                    return "addRoleHolderFromController";
                }
                case 9: {
                    return "setRoleNamesFromController";
                }
                case 8: {
                    return "removeOnRoleHoldersChangedListenerAsUser";
                }
                case 7: {
                    return "addOnRoleHoldersChangedListenerAsUser";
                }
                case 6: {
                    return "clearRoleHoldersAsUser";
                }
                case 5: {
                    return "removeRoleHolderAsUser";
                }
                case 4: {
                    return "addRoleHolderAsUser";
                }
                case 3: {
                    return "getRoleHoldersAsUser";
                }
                case 2: {
                    return "isRoleHeld";
                }
                case 1: 
            }
            return "isRoleAvailable";
        }

        public static boolean setDefaultImpl(IRoleManager iRoleManager) {
            if (Proxy.sDefaultImpl == null && iRoleManager != null) {
                Proxy.sDefaultImpl = iRoleManager;
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
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getSmsMessagesForFinancialApp(string2, bundle, IFinancialSmsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultSmsPackage(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getHeldRolesFromController(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeRoleHolderFromController(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addRoleHolderFromController(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRoleNamesFromController(((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearRoleHoldersAsUser(string3, n2, n, (RemoteCallback)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        String string5 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeRoleHolderAsUser(string4, string5, n, n2, (RemoteCallback)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addRoleHolderAsUser(string6, string7, n2, n, (RemoteCallback)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRoleHoldersAsUser(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRoleHeld(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.isRoleAvailable(((Parcel)object).readString()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRoleManager {
            public static IRoleManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnRoleHoldersChangedListener != null ? iOnRoleHoldersChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnRoleHoldersChangedListenerAsUser(iOnRoleHoldersChangedListener, n);
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
            public void addRoleHolderAsUser(String string2, String string3, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addRoleHolderAsUser(string2, string3, n, n2, remoteCallback);
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
            public boolean addRoleHolderFromController(String string2, String string3) throws RemoteException {
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
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().addRoleHolderFromController(string2, string3);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearRoleHoldersAsUser(String string2, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearRoleHoldersAsUser(string2, n, n2, remoteCallback);
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
            public String getDefaultSmsPackage(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDefaultSmsPackage(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getHeldRolesFromController(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getHeldRolesFromController((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
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
            public List<String> getRoleHoldersAsUser(String list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)list));
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getRoleHoldersAsUser((String)((Object)list), n);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
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
            public void getSmsMessagesForFinancialApp(String string2, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iFinancialSmsCallback != null ? iFinancialSmsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getSmsMessagesForFinancialApp(string2, bundle, iFinancialSmsCallback);
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
            public boolean isRoleAvailable(String string2) throws RemoteException {
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
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRoleAvailable(string2);
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
            public boolean isRoleHeld(String string2, String string3) throws RemoteException {
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
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRoleHeld(string2, string3);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnRoleHoldersChangedListener != null ? iOnRoleHoldersChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnRoleHoldersChangedListenerAsUser(iOnRoleHoldersChangedListener, n);
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
            public void removeRoleHolderAsUser(String string2, String string3, int n, int n2, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeRoleHolderAsUser(string2, string3, n, n2, remoteCallback);
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
            public boolean removeRoleHolderFromController(String string2, String string3) throws RemoteException {
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
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeRoleHolderFromController(string2, string3);
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
            public void setRoleNamesFromController(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRoleNamesFromController(list);
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

