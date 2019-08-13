/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DisplayCutout;
import android.view.DragEvent;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import com.android.internal.os.IResultReceiver;

public interface IWindow
extends IInterface {
    public void closeSystemDialogs(String var1) throws RemoteException;

    public void dispatchAppVisibility(boolean var1) throws RemoteException;

    public void dispatchDragEvent(DragEvent var1) throws RemoteException;

    public void dispatchGetNewSurface() throws RemoteException;

    public void dispatchPointerCaptureChanged(boolean var1) throws RemoteException;

    public void dispatchSystemUiVisibilityChanged(int var1, int var2, int var3, int var4) throws RemoteException;

    public void dispatchWallpaperCommand(String var1, int var2, int var3, int var4, Bundle var5, boolean var6) throws RemoteException;

    public void dispatchWallpaperOffsets(float var1, float var2, float var3, float var4, boolean var5) throws RemoteException;

    public void dispatchWindowShown() throws RemoteException;

    public void executeCommand(String var1, String var2, ParcelFileDescriptor var3) throws RemoteException;

    public void insetsChanged(InsetsState var1) throws RemoteException;

    public void insetsControlChanged(InsetsState var1, InsetsSourceControl[] var2) throws RemoteException;

    public void moved(int var1, int var2) throws RemoteException;

    public void requestAppKeyboardShortcuts(IResultReceiver var1, int var2) throws RemoteException;

    public void resized(Rect var1, Rect var2, Rect var3, Rect var4, Rect var5, Rect var6, boolean var7, MergedConfiguration var8, Rect var9, boolean var10, boolean var11, int var12, DisplayCutout.ParcelableWrapper var13) throws RemoteException;

    public void updatePointerIcon(float var1, float var2) throws RemoteException;

    public void windowFocusChanged(boolean var1, boolean var2) throws RemoteException;

    public static class Default
    implements IWindow {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeSystemDialogs(String string2) throws RemoteException {
        }

        @Override
        public void dispatchAppVisibility(boolean bl) throws RemoteException {
        }

        @Override
        public void dispatchDragEvent(DragEvent dragEvent) throws RemoteException {
        }

        @Override
        public void dispatchGetNewSurface() throws RemoteException {
        }

        @Override
        public void dispatchPointerCaptureChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void dispatchSystemUiVisibilityChanged(int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle, boolean bl) throws RemoteException {
        }

        @Override
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean bl) throws RemoteException {
        }

        @Override
        public void dispatchWindowShown() throws RemoteException {
        }

        @Override
        public void executeCommand(String string2, String string3, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void insetsChanged(InsetsState insetsState) throws RemoteException {
        }

        @Override
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] arrinsetsSourceControl) throws RemoteException {
        }

        @Override
        public void moved(int n, int n2) throws RemoteException {
        }

        @Override
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) throws RemoteException {
        }

        @Override
        public void resized(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, boolean bl, MergedConfiguration mergedConfiguration, Rect rect7, boolean bl2, boolean bl3, int n, DisplayCutout.ParcelableWrapper parcelableWrapper) throws RemoteException {
        }

        @Override
        public void updatePointerIcon(float f, float f2) throws RemoteException {
        }

        @Override
        public void windowFocusChanged(boolean bl, boolean bl2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWindow {
        private static final String DESCRIPTOR = "android.view.IWindow";
        static final int TRANSACTION_closeSystemDialogs = 9;
        static final int TRANSACTION_dispatchAppVisibility = 6;
        static final int TRANSACTION_dispatchDragEvent = 12;
        static final int TRANSACTION_dispatchGetNewSurface = 7;
        static final int TRANSACTION_dispatchPointerCaptureChanged = 17;
        static final int TRANSACTION_dispatchSystemUiVisibilityChanged = 14;
        static final int TRANSACTION_dispatchWallpaperCommand = 11;
        static final int TRANSACTION_dispatchWallpaperOffsets = 10;
        static final int TRANSACTION_dispatchWindowShown = 15;
        static final int TRANSACTION_executeCommand = 1;
        static final int TRANSACTION_insetsChanged = 3;
        static final int TRANSACTION_insetsControlChanged = 4;
        static final int TRANSACTION_moved = 5;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 16;
        static final int TRANSACTION_resized = 2;
        static final int TRANSACTION_updatePointerIcon = 13;
        static final int TRANSACTION_windowFocusChanged = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWindow asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWindow) {
                return (IWindow)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWindow getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 17: {
                    return "dispatchPointerCaptureChanged";
                }
                case 16: {
                    return "requestAppKeyboardShortcuts";
                }
                case 15: {
                    return "dispatchWindowShown";
                }
                case 14: {
                    return "dispatchSystemUiVisibilityChanged";
                }
                case 13: {
                    return "updatePointerIcon";
                }
                case 12: {
                    return "dispatchDragEvent";
                }
                case 11: {
                    return "dispatchWallpaperCommand";
                }
                case 10: {
                    return "dispatchWallpaperOffsets";
                }
                case 9: {
                    return "closeSystemDialogs";
                }
                case 8: {
                    return "windowFocusChanged";
                }
                case 7: {
                    return "dispatchGetNewSurface";
                }
                case 6: {
                    return "dispatchAppVisibility";
                }
                case 5: {
                    return "moved";
                }
                case 4: {
                    return "insetsControlChanged";
                }
                case 3: {
                    return "insetsChanged";
                }
                case 2: {
                    return "resized";
                }
                case 1: 
            }
            return "executeCommand";
        }

        public static boolean setDefaultImpl(IWindow iWindow) {
            if (Proxy.sDefaultImpl == null && iWindow != null) {
                Proxy.sDefaultImpl = iWindow;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.dispatchPointerCaptureChanged(bl3);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestAppKeyboardShortcuts(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispatchWindowShown();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispatchSystemUiVisibilityChanged(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updatePointerIcon(((Parcel)object).readFloat(), ((Parcel)object).readFloat());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DragEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatchDragEvent((DragEvent)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl3 = ((Parcel)object).readInt() != 0;
                        this.dispatchWallpaperCommand(string2, n, n2, n3, (Bundle)object2, bl3);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        float f = ((Parcel)object).readFloat();
                        float f2 = ((Parcel)object).readFloat();
                        float f3 = ((Parcel)object).readFloat();
                        float f4 = ((Parcel)object).readFloat();
                        bl3 = ((Parcel)object).readInt() != 0;
                        this.dispatchWallpaperOffsets(f, f2, f3, f4, bl3);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeSystemDialogs(((Parcel)object).readString());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.windowFocusChanged(bl3, bl);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispatchGetNewSurface();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.dispatchAppVisibility(bl3);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.moved(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? InsetsState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.insetsControlChanged((InsetsState)object2, ((Parcel)object).createTypedArray(InsetsSourceControl.CREATOR));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InsetsState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.insetsChanged((InsetsState)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect3 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect4 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect5 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl3 = ((Parcel)object).readInt() != 0;
                        MergedConfiguration mergedConfiguration = ((Parcel)object).readInt() != 0 ? MergedConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect6 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl = ((Parcel)object).readInt() != 0;
                        bl2 = ((Parcel)object).readInt() != 0;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? DisplayCutout.ParcelableWrapper.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resized((Rect)object2, rect, rect2, rect3, rect4, rect5, bl3, mergedConfiguration, rect6, bl, bl2, n, (DisplayCutout.ParcelableWrapper)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string3 = ((Parcel)object).readString();
                object2 = ((Parcel)object).readString();
                object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                this.executeCommand(string3, (String)object2, (ParcelFileDescriptor)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWindow {
            public static IWindow sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeSystemDialogs(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSystemDialogs(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchAppVisibility(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAppVisibility(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchDragEvent(DragEvent dragEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dragEvent != null) {
                        parcel.writeInt(1);
                        dragEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchDragEvent(dragEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchGetNewSurface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchGetNewSurface();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchPointerCaptureChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPointerCaptureChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchSystemUiVisibilityChanged(int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchSystemUiVisibilityChanged(n, n2, n3, n4);
                        return;
                    }
                    return;
                }
                finally {
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
            public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block16 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeInt(n3);
                        int n4 = 0;
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (bl) {
                            n4 = 1;
                        }
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dispatchWallpaperCommand(string2, n, n2, n3, bundle, bl);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeFloat(f);
                parcel.writeFloat(f2);
                parcel.writeFloat(f3);
                parcel.writeFloat(f4);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchWallpaperOffsets(f, f2, f3, f4, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchWindowShown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchWindowShown();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void executeCommand(String string2, String string3, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().executeCommand(string2, string3, parcelFileDescriptor);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void insetsChanged(InsetsState insetsState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (insetsState != null) {
                        parcel.writeInt(1);
                        insetsState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().insetsChanged(insetsState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] arrinsetsSourceControl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (insetsState != null) {
                        parcel.writeInt(1);
                        insetsState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedArray((Parcelable[])arrinsetsSourceControl, 0);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().insetsControlChanged(insetsState, arrinsetsSourceControl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void moved(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moved(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(16, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().requestAppKeyboardShortcuts(iResultReceiver, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void resized(Rect var1_1, Rect var2_6, Rect var3_7, Rect var4_8, Rect var5_9, Rect var6_10, boolean var7_11, MergedConfiguration var8_12, Rect var9_13, boolean var10_14, boolean var11_15, int var12_16, DisplayCutout.ParcelableWrapper var13_17) throws RemoteException {
                block36 : {
                    block37 : {
                        block35 : {
                            block39 : {
                                block34 : {
                                    block33 : {
                                        block38 : {
                                            block32 : {
                                                block31 : {
                                                    var14_18 = Parcel.obtain();
                                                    var14_18.writeInterfaceToken("android.view.IWindow");
                                                    if (var1_1 == null) break block31;
                                                    try {
                                                        var14_18.writeInt(1);
                                                        var1_1.writeToParcel(var14_18, 0);
                                                        break block32;
                                                    }
                                                    catch (Throwable var1_2) {
                                                        break block36;
                                                    }
                                                }
                                                var14_18.writeInt(0);
                                            }
                                            if (var2_6 != null) {
                                                var14_18.writeInt(1);
                                                var2_6.writeToParcel(var14_18, 0);
                                            } else {
                                                var14_18.writeInt(0);
                                            }
                                            if (var3_7 != null) {
                                                var14_18.writeInt(1);
                                                var3_7.writeToParcel(var14_18, 0);
                                            } else {
                                                var14_18.writeInt(0);
                                            }
                                            if (var4_8 != null) {
                                                var14_18.writeInt(1);
                                                var4_8.writeToParcel(var14_18, 0);
                                            } else {
                                                var14_18.writeInt(0);
                                            }
                                            if (var5_9 != null) {
                                                var14_18.writeInt(1);
                                                var5_9.writeToParcel(var14_18, 0);
                                            } else {
                                                var14_18.writeInt(0);
                                            }
                                            if (var6_10 == null) break block38;
                                            var14_18.writeInt(1);
                                            var6_10.writeToParcel(var14_18, 0);
                                            ** GOTO lbl51
                                        }
                                        var14_18.writeInt(0);
lbl51: // 2 sources:
                                        var15_19 = var7_11 != false ? 1 : 0;
                                        var14_18.writeInt(var15_19);
                                        if (var8_12 == null) break block33;
                                        var14_18.writeInt(1);
                                        var8_12.writeToParcel(var14_18, 0);
                                        break block34;
                                    }
                                    var14_18.writeInt(0);
                                }
                                if (var9_13 == null) break block39;
                                var14_18.writeInt(1);
                                var9_13.writeToParcel(var14_18, 0);
                                ** GOTO lbl70
                            }
                            var14_18.writeInt(0);
lbl70: // 2 sources:
                            var15_19 = var10_14 != false ? 1 : 0;
                            var14_18.writeInt(var15_19);
                            var15_19 = var11_15 != false ? 1 : 0;
                            var14_18.writeInt(var15_19);
                            var14_18.writeInt(var12_16);
                            if (var13_17 == null) break block35;
                            var14_18.writeInt(1);
                            var13_17.writeToParcel(var14_18, 0);
                            ** GOTO lbl83
                        }
                        var14_18.writeInt(0);
lbl83: // 2 sources:
                        if (this.mRemote.transact(2, var14_18, null, 1) || Stub.getDefaultImpl() == null) break block37;
                        var16_20 = Stub.getDefaultImpl();
                        try {
                            var16_20.resized(var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15, var12_16, var13_17);
                            var14_18.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                        break block36;
                    }
                    var14_18.recycle();
                    return;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var14_18.recycle();
                throw var1_5;
            }

            @Override
            public void updatePointerIcon(float f, float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    parcel.writeFloat(f2);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePointerIcon(f, f2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void windowFocusChanged(boolean bl, boolean bl2) throws RemoteException {
                int n;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().windowFocusChanged(bl, bl2);
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

