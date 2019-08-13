/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewProviderResponse;

public interface IWebViewUpdateService
extends IInterface {
    public String changeProviderAndSetting(String var1) throws RemoteException;

    public void enableMultiProcess(boolean var1) throws RemoteException;

    public WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException;

    public PackageInfo getCurrentWebViewPackage() throws RemoteException;

    @UnsupportedAppUsage
    public String getCurrentWebViewPackageName() throws RemoteException;

    @UnsupportedAppUsage
    public WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException;

    public boolean isMultiProcessEnabled() throws RemoteException;

    public void notifyRelroCreationCompleted() throws RemoteException;

    public WebViewProviderResponse waitForAndGetProvider() throws RemoteException;

    public static class Default
    implements IWebViewUpdateService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String changeProviderAndSetting(String string2) throws RemoteException {
            return null;
        }

        @Override
        public void enableMultiProcess(boolean bl) throws RemoteException {
        }

        @Override
        public WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException {
            return null;
        }

        @Override
        public PackageInfo getCurrentWebViewPackage() throws RemoteException {
            return null;
        }

        @Override
        public String getCurrentWebViewPackageName() throws RemoteException {
            return null;
        }

        @Override
        public WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException {
            return null;
        }

        @Override
        public boolean isMultiProcessEnabled() throws RemoteException {
            return false;
        }

        @Override
        public void notifyRelroCreationCompleted() throws RemoteException {
        }

        @Override
        public WebViewProviderResponse waitForAndGetProvider() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWebViewUpdateService {
        private static final String DESCRIPTOR = "android.webkit.IWebViewUpdateService";
        static final int TRANSACTION_changeProviderAndSetting = 3;
        static final int TRANSACTION_enableMultiProcess = 9;
        static final int TRANSACTION_getAllWebViewPackages = 5;
        static final int TRANSACTION_getCurrentWebViewPackage = 7;
        static final int TRANSACTION_getCurrentWebViewPackageName = 6;
        static final int TRANSACTION_getValidWebViewPackages = 4;
        static final int TRANSACTION_isMultiProcessEnabled = 8;
        static final int TRANSACTION_notifyRelroCreationCompleted = 1;
        static final int TRANSACTION_waitForAndGetProvider = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWebViewUpdateService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWebViewUpdateService) {
                return (IWebViewUpdateService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWebViewUpdateService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "enableMultiProcess";
                }
                case 8: {
                    return "isMultiProcessEnabled";
                }
                case 7: {
                    return "getCurrentWebViewPackage";
                }
                case 6: {
                    return "getCurrentWebViewPackageName";
                }
                case 5: {
                    return "getAllWebViewPackages";
                }
                case 4: {
                    return "getValidWebViewPackages";
                }
                case 3: {
                    return "changeProviderAndSetting";
                }
                case 2: {
                    return "waitForAndGetProvider";
                }
                case 1: 
            }
            return "notifyRelroCreationCompleted";
        }

        public static boolean setDefaultImpl(IWebViewUpdateService iWebViewUpdateService) {
            if (Proxy.sDefaultImpl == null && iWebViewUpdateService != null) {
                Proxy.sDefaultImpl = iWebViewUpdateService;
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
        public boolean onTransact(int n, Parcel arrparcelable, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrparcelable, parcel, n2);
                    }
                    case 9: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        if (arrparcelable.readInt() != 0) {
                            bl = true;
                        }
                        this.enableMultiProcess(bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        n = this.isMultiProcessEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.getCurrentWebViewPackage();
                        parcel.writeNoException();
                        if (arrparcelable != null) {
                            parcel.writeInt(1);
                            arrparcelable.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.getCurrentWebViewPackageName();
                        parcel.writeNoException();
                        parcel.writeString((String)arrparcelable);
                        return true;
                    }
                    case 5: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.getAllWebViewPackages();
                        parcel.writeNoException();
                        parcel.writeTypedArray(arrparcelable, 1);
                        return true;
                    }
                    case 4: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.getValidWebViewPackages();
                        parcel.writeNoException();
                        parcel.writeTypedArray(arrparcelable, 1);
                        return true;
                    }
                    case 3: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.changeProviderAndSetting(arrparcelable.readString());
                        parcel.writeNoException();
                        parcel.writeString((String)arrparcelable);
                        return true;
                    }
                    case 2: {
                        arrparcelable.enforceInterface(DESCRIPTOR);
                        arrparcelable = this.waitForAndGetProvider();
                        parcel.writeNoException();
                        if (arrparcelable != null) {
                            parcel.writeInt(1);
                            arrparcelable.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                arrparcelable.enforceInterface(DESCRIPTOR);
                this.notifyRelroCreationCompleted();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWebViewUpdateService {
            public static IWebViewUpdateService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public String changeProviderAndSetting(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().changeProviderAndSetting(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void enableMultiProcess(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableMultiProcess(bl);
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
            public WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        WebViewProviderInfo[] arrwebViewProviderInfo = Stub.getDefaultImpl().getAllWebViewPackages();
                        return arrwebViewProviderInfo;
                    }
                    parcel2.readException();
                    WebViewProviderInfo[] arrwebViewProviderInfo = parcel2.createTypedArray(WebViewProviderInfo.CREATOR);
                    return arrwebViewProviderInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PackageInfo getCurrentWebViewPackage() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        PackageInfo packageInfo = Stub.getDefaultImpl().getCurrentWebViewPackage();
                        parcel.recycle();
                        parcel2.recycle();
                        return packageInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                PackageInfo packageInfo = parcel.readInt() != 0 ? PackageInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return packageInfo;
            }

            @Override
            public String getCurrentWebViewPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCurrentWebViewPackageName();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        WebViewProviderInfo[] arrwebViewProviderInfo = Stub.getDefaultImpl().getValidWebViewPackages();
                        return arrwebViewProviderInfo;
                    }
                    parcel2.readException();
                    WebViewProviderInfo[] arrwebViewProviderInfo = parcel2.createTypedArray(WebViewProviderInfo.CREATOR);
                    return arrwebViewProviderInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isMultiProcessEnabled() throws RemoteException {
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
                    if (iBinder.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isMultiProcessEnabled();
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
            public void notifyRelroCreationCompleted() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyRelroCreationCompleted();
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
            public WebViewProviderResponse waitForAndGetProvider() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WebViewProviderResponse webViewProviderResponse = Stub.getDefaultImpl().waitForAndGetProvider();
                        parcel.recycle();
                        parcel2.recycle();
                        return webViewProviderResponse;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WebViewProviderResponse webViewProviderResponse = parcel.readInt() != 0 ? WebViewProviderResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return webViewProviderResponse;
            }
        }

    }

}

