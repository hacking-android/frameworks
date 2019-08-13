/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.IntentSender;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageInstaller;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IPackageInstaller
extends IInterface {
    public void abandonSession(int var1) throws RemoteException;

    public int createSession(PackageInstaller.SessionParams var1, String var2, int var3) throws RemoteException;

    public ParceledListSlice getAllSessions(int var1) throws RemoteException;

    public ParceledListSlice getMySessions(String var1, int var2) throws RemoteException;

    public PackageInstaller.SessionInfo getSessionInfo(int var1) throws RemoteException;

    public ParceledListSlice getStagedSessions() throws RemoteException;

    public void installExistingPackage(String var1, int var2, int var3, IntentSender var4, int var5, List<String> var6) throws RemoteException;

    public IPackageInstallerSession openSession(int var1) throws RemoteException;

    public void registerCallback(IPackageInstallerCallback var1, int var2) throws RemoteException;

    public void setPermissionsResult(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void uninstall(VersionedPackage var1, String var2, int var3, IntentSender var4, int var5) throws RemoteException;

    public void unregisterCallback(IPackageInstallerCallback var1) throws RemoteException;

    public void updateSessionAppIcon(int var1, Bitmap var2) throws RemoteException;

    public void updateSessionAppLabel(int var1, String var2) throws RemoteException;

    public static class Default
    implements IPackageInstaller {
        @Override
        public void abandonSession(int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int createSession(PackageInstaller.SessionParams sessionParams, String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getAllSessions(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getMySessions(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public PackageInstaller.SessionInfo getSessionInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getStagedSessions() throws RemoteException {
            return null;
        }

        @Override
        public void installExistingPackage(String string2, int n, int n2, IntentSender intentSender, int n3, List<String> list) throws RemoteException {
        }

        @Override
        public IPackageInstallerSession openSession(int n) throws RemoteException {
            return null;
        }

        @Override
        public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int n) throws RemoteException {
        }

        @Override
        public void setPermissionsResult(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void uninstall(VersionedPackage versionedPackage, String string2, int n, IntentSender intentSender, int n2) throws RemoteException {
        }

        @Override
        public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
        }

        @Override
        public void updateSessionAppIcon(int n, Bitmap bitmap) throws RemoteException {
        }

        @Override
        public void updateSessionAppLabel(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageInstaller {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstaller";
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_getAllSessions = 7;
        static final int TRANSACTION_getMySessions = 8;
        static final int TRANSACTION_getSessionInfo = 6;
        static final int TRANSACTION_getStagedSessions = 9;
        static final int TRANSACTION_installExistingPackage = 13;
        static final int TRANSACTION_openSession = 5;
        static final int TRANSACTION_registerCallback = 10;
        static final int TRANSACTION_setPermissionsResult = 14;
        static final int TRANSACTION_uninstall = 12;
        static final int TRANSACTION_unregisterCallback = 11;
        static final int TRANSACTION_updateSessionAppIcon = 2;
        static final int TRANSACTION_updateSessionAppLabel = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstaller asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageInstaller) {
                return (IPackageInstaller)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageInstaller getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "setPermissionsResult";
                }
                case 13: {
                    return "installExistingPackage";
                }
                case 12: {
                    return "uninstall";
                }
                case 11: {
                    return "unregisterCallback";
                }
                case 10: {
                    return "registerCallback";
                }
                case 9: {
                    return "getStagedSessions";
                }
                case 8: {
                    return "getMySessions";
                }
                case 7: {
                    return "getAllSessions";
                }
                case 6: {
                    return "getSessionInfo";
                }
                case 5: {
                    return "openSession";
                }
                case 4: {
                    return "abandonSession";
                }
                case 3: {
                    return "updateSessionAppLabel";
                }
                case 2: {
                    return "updateSessionAppIcon";
                }
                case 1: 
            }
            return "createSession";
        }

        public static boolean setDefaultImpl(IPackageInstaller iPackageInstaller) {
            if (Proxy.sDefaultImpl == null && iPackageInstaller != null) {
                Proxy.sDefaultImpl = iPackageInstaller;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setPermissionsResult(n, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        IntentSender intentSender = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        this.installExistingPackage(string2, n2, n, intentSender, ((Parcel)object).readInt(), ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        VersionedPackage versionedPackage = ((Parcel)object).readInt() != 0 ? VersionedPackage.CREATOR.createFromParcel((Parcel)object) : null;
                        String string3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        IntentSender intentSender = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        this.uninstall(versionedPackage, string3, n, intentSender, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterCallback(IPackageInstallerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerCallback(IPackageInstallerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStagedSessions();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMySessions(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllSessions(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSessionInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PackageInstaller.SessionInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.openSession(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.abandonSession(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateSessionAppLabel(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateSessionAppIcon(n, (Bitmap)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                PackageInstaller.SessionParams sessionParams = ((Parcel)object).readInt() != 0 ? PackageInstaller.SessionParams.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.createSession(sessionParams, ((Parcel)object).readString(), ((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPackageInstaller {
            public static IPackageInstaller sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void abandonSession(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().abandonSession(n);
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
            public int createSession(PackageInstaller.SessionParams sessionParams, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionParams != null) {
                        parcel.writeInt(1);
                        sessionParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().createSession(sessionParams, string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getAllSessions(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getAllSessions(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public ParceledListSlice getMySessions(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getMySessions((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public PackageInstaller.SessionInfo getSessionInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        PackageInstaller.SessionInfo sessionInfo = Stub.getDefaultImpl().getSessionInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return sessionInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                PackageInstaller.SessionInfo sessionInfo = parcel2.readInt() != 0 ? PackageInstaller.SessionInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return sessionInfo;
            }

            @Override
            public ParceledListSlice getStagedSessions() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getStagedSessions();
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
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
            public void installExistingPackage(String string2, int n, int n2, IntentSender intentSender, int n3, List<String> list) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
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
                            parcel2.writeInt(n2);
                            if (intentSender != null) {
                                parcel2.writeInt(1);
                                intentSender.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeStringList(list);
                        if (!this.mRemote.transact(13, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().installExistingPackage(string2, n, n2, intentSender, n3, list);
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
            public IPackageInstallerSession openSession(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IPackageInstallerSession iPackageInstallerSession = Stub.getDefaultImpl().openSession(n);
                        return iPackageInstallerSession;
                    }
                    parcel2.readException();
                    IPackageInstallerSession iPackageInstallerSession = IPackageInstallerSession.Stub.asInterface(parcel2.readStrongBinder());
                    return iPackageInstallerSession;
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
            public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPackageInstallerCallback != null ? iPackageInstallerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(iPackageInstallerCallback, n);
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
            public void setPermissionsResult(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPermissionsResult(n, bl);
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
            public void uninstall(VersionedPackage versionedPackage, String string2, int n, IntentSender intentSender, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (versionedPackage != null) {
                        parcel.writeInt(1);
                        versionedPackage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().uninstall(versionedPackage, string2, n, intentSender, n2);
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
            public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPackageInstallerCallback != null ? iPackageInstallerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(iPackageInstallerCallback);
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
            public void updateSessionAppIcon(int n, Bitmap bitmap) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSessionAppIcon(n, bitmap);
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
            public void updateSessionAppLabel(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSessionAppLabel(n, string2);
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

