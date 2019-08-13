/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.graphics.Point;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.Curve;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.WifiDisplayStatus;
import android.media.projection.IMediaProjection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.DisplayInfo;
import android.view.Surface;

public interface IDisplayManager
extends IInterface {
    public void connectWifiDisplay(String var1) throws RemoteException;

    public int createVirtualDisplay(IVirtualDisplayCallback var1, IMediaProjection var2, String var3, String var4, int var5, int var6, int var7, Surface var8, int var9, String var10) throws RemoteException;

    public void disconnectWifiDisplay() throws RemoteException;

    public void forgetWifiDisplay(String var1) throws RemoteException;

    public ParceledListSlice getAmbientBrightnessStats() throws RemoteException;

    public BrightnessConfiguration getBrightnessConfigurationForUser(int var1) throws RemoteException;

    public ParceledListSlice getBrightnessEvents(String var1) throws RemoteException;

    public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException;

    public int[] getDisplayIds() throws RemoteException;

    @UnsupportedAppUsage
    public DisplayInfo getDisplayInfo(int var1) throws RemoteException;

    public Curve getMinimumBrightnessCurve() throws RemoteException;

    public int getPreferredWideGamutColorSpaceId() throws RemoteException;

    public Point getStableDisplaySize() throws RemoteException;

    public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException;

    public boolean isUidPresentOnDisplay(int var1, int var2) throws RemoteException;

    public void pauseWifiDisplay() throws RemoteException;

    public void registerCallback(IDisplayManagerCallback var1) throws RemoteException;

    public void releaseVirtualDisplay(IVirtualDisplayCallback var1) throws RemoteException;

    public void renameWifiDisplay(String var1, String var2) throws RemoteException;

    public void requestColorMode(int var1, int var2) throws RemoteException;

    public void resizeVirtualDisplay(IVirtualDisplayCallback var1, int var2, int var3, int var4) throws RemoteException;

    public void resumeWifiDisplay() throws RemoteException;

    public void setBrightnessConfigurationForUser(BrightnessConfiguration var1, int var2, String var3) throws RemoteException;

    public void setTemporaryAutoBrightnessAdjustment(float var1) throws RemoteException;

    public void setTemporaryBrightness(int var1) throws RemoteException;

    public void setVirtualDisplayState(IVirtualDisplayCallback var1, boolean var2) throws RemoteException;

    public void setVirtualDisplaySurface(IVirtualDisplayCallback var1, Surface var2) throws RemoteException;

    public void startWifiDisplayScan() throws RemoteException;

    public void stopWifiDisplayScan() throws RemoteException;

    public static class Default
    implements IDisplayManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connectWifiDisplay(String string2) throws RemoteException {
        }

        @Override
        public int createVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String string2, String string3, int n, int n2, int n3, Surface surface, int n4, String string4) throws RemoteException {
            return 0;
        }

        @Override
        public void disconnectWifiDisplay() throws RemoteException {
        }

        @Override
        public void forgetWifiDisplay(String string2) throws RemoteException {
        }

        @Override
        public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
            return null;
        }

        @Override
        public BrightnessConfiguration getBrightnessConfigurationForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getBrightnessEvents(String string2) throws RemoteException {
            return null;
        }

        @Override
        public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
            return null;
        }

        @Override
        public int[] getDisplayIds() throws RemoteException {
            return null;
        }

        @Override
        public DisplayInfo getDisplayInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public Curve getMinimumBrightnessCurve() throws RemoteException {
            return null;
        }

        @Override
        public int getPreferredWideGamutColorSpaceId() throws RemoteException {
            return 0;
        }

        @Override
        public Point getStableDisplaySize() throws RemoteException {
            return null;
        }

        @Override
        public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
            return null;
        }

        @Override
        public boolean isUidPresentOnDisplay(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void pauseWifiDisplay() throws RemoteException {
        }

        @Override
        public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException {
        }

        @Override
        public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
        }

        @Override
        public void renameWifiDisplay(String string2, String string3) throws RemoteException {
        }

        @Override
        public void requestColorMode(int n, int n2) throws RemoteException {
        }

        @Override
        public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void resumeWifiDisplay() throws RemoteException {
        }

        @Override
        public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int n, String string2) throws RemoteException {
        }

        @Override
        public void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException {
        }

        @Override
        public void setTemporaryBrightness(int n) throws RemoteException {
        }

        @Override
        public void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean bl) throws RemoteException {
        }

        @Override
        public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException {
        }

        @Override
        public void startWifiDisplayScan() throws RemoteException {
        }

        @Override
        public void stopWifiDisplayScan() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDisplayManager {
        private static final String DESCRIPTOR = "android.hardware.display.IDisplayManager";
        static final int TRANSACTION_connectWifiDisplay = 7;
        static final int TRANSACTION_createVirtualDisplay = 15;
        static final int TRANSACTION_disconnectWifiDisplay = 8;
        static final int TRANSACTION_forgetWifiDisplay = 10;
        static final int TRANSACTION_getAmbientBrightnessStats = 22;
        static final int TRANSACTION_getBrightnessConfigurationForUser = 24;
        static final int TRANSACTION_getBrightnessEvents = 21;
        static final int TRANSACTION_getDefaultBrightnessConfiguration = 25;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getMinimumBrightnessCurve = 28;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 29;
        static final int TRANSACTION_getStableDisplaySize = 20;
        static final int TRANSACTION_getWifiDisplayStatus = 13;
        static final int TRANSACTION_isUidPresentOnDisplay = 3;
        static final int TRANSACTION_pauseWifiDisplay = 11;
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_releaseVirtualDisplay = 18;
        static final int TRANSACTION_renameWifiDisplay = 9;
        static final int TRANSACTION_requestColorMode = 14;
        static final int TRANSACTION_resizeVirtualDisplay = 16;
        static final int TRANSACTION_resumeWifiDisplay = 12;
        static final int TRANSACTION_setBrightnessConfigurationForUser = 23;
        static final int TRANSACTION_setTemporaryAutoBrightnessAdjustment = 27;
        static final int TRANSACTION_setTemporaryBrightness = 26;
        static final int TRANSACTION_setVirtualDisplayState = 19;
        static final int TRANSACTION_setVirtualDisplaySurface = 17;
        static final int TRANSACTION_startWifiDisplayScan = 5;
        static final int TRANSACTION_stopWifiDisplayScan = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDisplayManager) {
                return (IDisplayManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDisplayManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 29: {
                    return "getPreferredWideGamutColorSpaceId";
                }
                case 28: {
                    return "getMinimumBrightnessCurve";
                }
                case 27: {
                    return "setTemporaryAutoBrightnessAdjustment";
                }
                case 26: {
                    return "setTemporaryBrightness";
                }
                case 25: {
                    return "getDefaultBrightnessConfiguration";
                }
                case 24: {
                    return "getBrightnessConfigurationForUser";
                }
                case 23: {
                    return "setBrightnessConfigurationForUser";
                }
                case 22: {
                    return "getAmbientBrightnessStats";
                }
                case 21: {
                    return "getBrightnessEvents";
                }
                case 20: {
                    return "getStableDisplaySize";
                }
                case 19: {
                    return "setVirtualDisplayState";
                }
                case 18: {
                    return "releaseVirtualDisplay";
                }
                case 17: {
                    return "setVirtualDisplaySurface";
                }
                case 16: {
                    return "resizeVirtualDisplay";
                }
                case 15: {
                    return "createVirtualDisplay";
                }
                case 14: {
                    return "requestColorMode";
                }
                case 13: {
                    return "getWifiDisplayStatus";
                }
                case 12: {
                    return "resumeWifiDisplay";
                }
                case 11: {
                    return "pauseWifiDisplay";
                }
                case 10: {
                    return "forgetWifiDisplay";
                }
                case 9: {
                    return "renameWifiDisplay";
                }
                case 8: {
                    return "disconnectWifiDisplay";
                }
                case 7: {
                    return "connectWifiDisplay";
                }
                case 6: {
                    return "stopWifiDisplayScan";
                }
                case 5: {
                    return "startWifiDisplayScan";
                }
                case 4: {
                    return "registerCallback";
                }
                case 3: {
                    return "isUidPresentOnDisplay";
                }
                case 2: {
                    return "getDisplayIds";
                }
                case 1: 
            }
            return "getDisplayInfo";
        }

        public static boolean setDefaultImpl(IDisplayManager iDisplayManager) {
            if (Proxy.sDefaultImpl == null && iDisplayManager != null) {
                Proxy.sDefaultImpl = iDisplayManager;
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
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPreferredWideGamutColorSpaceId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMinimumBrightnessCurve();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Curve)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTemporaryAutoBrightnessAdjustment(((Parcel)object).readFloat());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTemporaryBrightness(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultBrightnessConfiguration();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((BrightnessConfiguration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBrightnessConfigurationForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((BrightnessConfiguration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BrightnessConfiguration brightnessConfiguration = ((Parcel)object).readInt() != 0 ? BrightnessConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setBrightnessConfigurationForUser(brightnessConfiguration, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAmbientBrightnessStats();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBrightnessEvents(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStableDisplaySize();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Point)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IVirtualDisplayCallback iVirtualDisplayCallback = IVirtualDisplayCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setVirtualDisplayState(iVirtualDisplayCallback, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseVirtualDisplay(IVirtualDisplayCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IVirtualDisplayCallback iVirtualDisplayCallback = IVirtualDisplayCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVirtualDisplaySurface(iVirtualDisplayCallback, (Surface)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resizeVirtualDisplay(IVirtualDisplayCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IVirtualDisplayCallback iVirtualDisplayCallback = IVirtualDisplayCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IMediaProjection iMediaProjection = IMediaProjection.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        int n3 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        Surface surface = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createVirtualDisplay(iVirtualDisplayCallback, iMediaProjection, string2, string3, n3, n2, n, surface, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestColorMode(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWifiDisplayStatus();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WifiDisplayStatus)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resumeWifiDisplay();
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pauseWifiDisplay();
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forgetWifiDisplay(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.renameWifiDisplay(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disconnectWifiDisplay();
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.connectWifiDisplay(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWifiDisplayScan();
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWifiDisplayScan();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerCallback(IDisplayManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUidPresentOnDisplay(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDisplayIds();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getDisplayInfo(((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((DisplayInfo)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDisplayManager {
            public static IDisplayManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void connectWifiDisplay(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectWifiDisplay(string2);
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
            public int createVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String string2, String string3, int n, int n2, int n3, Surface surface, int n4, String string4) throws RemoteException {
                void var1_4;
                Parcel parcel2;
                Parcel parcel;
                block9 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var13_16 = null;
                    IBinder iBinder = iVirtualDisplayCallback != null ? iVirtualDisplayCallback.asBinder() : null;
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = var13_16;
                    if (iMediaProjection != null) {
                        iBinder = iMediaProjection.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    try {
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeInt(n3);
                        if (surface != null) {
                            parcel2.writeInt(1);
                            surface.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeInt(n4);
                        parcel2.writeString(string4);
                        if (!this.mRemote.transact(15, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().createVirtualDisplay(iVirtualDisplayCallback, iMediaProjection, string2, string3, n, n2, n3, surface, n4, string4);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block9;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_4;
            }

            @Override
            public void disconnectWifiDisplay() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnectWifiDisplay();
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
            public void forgetWifiDisplay(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forgetWifiDisplay(string2);
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
            public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getAmbientBrightnessStats();
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

            @Override
            public BrightnessConfiguration getBrightnessConfigurationForUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        BrightnessConfiguration brightnessConfiguration = Stub.getDefaultImpl().getBrightnessConfigurationForUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return brightnessConfiguration;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                BrightnessConfiguration brightnessConfiguration = parcel2.readInt() != 0 ? BrightnessConfiguration.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return brightnessConfiguration;
            }

            @Override
            public ParceledListSlice getBrightnessEvents(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getBrightnessEvents((String)((Object)parceledListSlice));
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
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(25, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        BrightnessConfiguration brightnessConfiguration = Stub.getDefaultImpl().getDefaultBrightnessConfiguration();
                        parcel.recycle();
                        parcel2.recycle();
                        return brightnessConfiguration;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                BrightnessConfiguration brightnessConfiguration = parcel.readInt() != 0 ? BrightnessConfiguration.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return brightnessConfiguration;
            }

            @Override
            public int[] getDisplayIds() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getDisplayIds();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public DisplayInfo getDisplayInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        DisplayInfo displayInfo = Stub.getDefaultImpl().getDisplayInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return displayInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                DisplayInfo displayInfo = parcel2.readInt() != 0 ? DisplayInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return displayInfo;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public Curve getMinimumBrightnessCurve() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(28, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Curve curve = Stub.getDefaultImpl().getMinimumBrightnessCurve();
                        parcel.recycle();
                        parcel2.recycle();
                        return curve;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Curve curve = parcel.readInt() != 0 ? Curve.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return curve;
            }

            @Override
            public int getPreferredWideGamutColorSpaceId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPreferredWideGamutColorSpaceId();
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
            public Point getStableDisplaySize() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Point point = Stub.getDefaultImpl().getStableDisplaySize();
                        parcel.recycle();
                        parcel2.recycle();
                        return point;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Point point = parcel.readInt() != 0 ? Point.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return point;
            }

            @Override
            public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WifiDisplayStatus wifiDisplayStatus = Stub.getDefaultImpl().getWifiDisplayStatus();
                        parcel.recycle();
                        parcel2.recycle();
                        return wifiDisplayStatus;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WifiDisplayStatus wifiDisplayStatus = parcel.readInt() != 0 ? WifiDisplayStatus.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return wifiDisplayStatus;
            }

            @Override
            public boolean isUidPresentOnDisplay(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUidPresentOnDisplay(n, n2);
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
            public void pauseWifiDisplay() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pauseWifiDisplay();
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
            public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iDisplayManagerCallback != null ? iDisplayManagerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(iDisplayManagerCallback);
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
            public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVirtualDisplayCallback != null ? iVirtualDisplayCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseVirtualDisplay(iVirtualDisplayCallback);
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
            public void renameWifiDisplay(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().renameWifiDisplay(string2, string3);
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
            public void requestColorMode(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestColorMode(n, n2);
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
            public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVirtualDisplayCallback != null ? iVirtualDisplayCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizeVirtualDisplay(iVirtualDisplayCallback, n, n2, n3);
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
            public void resumeWifiDisplay() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumeWifiDisplay();
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
            public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (brightnessConfiguration != null) {
                        parcel.writeInt(1);
                        brightnessConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBrightnessConfigurationForUser(brightnessConfiguration, n, string2);
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
            public void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTemporaryAutoBrightnessAdjustment(f);
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
            public void setTemporaryBrightness(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTemporaryBrightness(n);
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
            public void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVirtualDisplayCallback == null) break block8;
                    iBinder = iVirtualDisplayCallback.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(19, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVirtualDisplayState(iVirtualDisplayCallback, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVirtualDisplayCallback != null ? iVirtualDisplayCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVirtualDisplaySurface(iVirtualDisplayCallback, surface);
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
            public void startWifiDisplayScan() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWifiDisplayScan();
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
            public void stopWifiDisplayScan() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWifiDisplayScan();
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

