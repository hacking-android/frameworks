/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DisplayCutout;
import android.view.IWindow;
import android.view.IWindowId;
import android.view.InputChannel;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

public interface IWindowSession
extends IInterface {
    public int addToDisplay(IWindow var1, int var2, WindowManager.LayoutParams var3, int var4, int var5, Rect var6, Rect var7, Rect var8, Rect var9, DisplayCutout.ParcelableWrapper var10, InputChannel var11, InsetsState var12) throws RemoteException;

    public int addToDisplayWithoutInputChannel(IWindow var1, int var2, WindowManager.LayoutParams var3, int var4, int var5, Rect var6, Rect var7, InsetsState var8) throws RemoteException;

    public void cancelDragAndDrop(IBinder var1, boolean var2) throws RemoteException;

    public void dragRecipientEntered(IWindow var1) throws RemoteException;

    public void dragRecipientExited(IWindow var1) throws RemoteException;

    @UnsupportedAppUsage
    public void finishDrawing(IWindow var1) throws RemoteException;

    public void finishMovingTask(IWindow var1) throws RemoteException;

    public void getDisplayFrame(IWindow var1, Rect var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getInTouchMode() throws RemoteException;

    public IWindowId getWindowId(IBinder var1) throws RemoteException;

    public void insetsModified(IWindow var1, InsetsState var2) throws RemoteException;

    public void onRectangleOnScreenRequested(IBinder var1, Rect var2) throws RemoteException;

    public boolean outOfMemory(IWindow var1) throws RemoteException;

    @UnsupportedAppUsage
    public IBinder performDrag(IWindow var1, int var2, SurfaceControl var3, int var4, float var5, float var6, float var7, float var8, ClipData var9) throws RemoteException;

    @UnsupportedAppUsage
    public boolean performHapticFeedback(int var1, boolean var2) throws RemoteException;

    public void pokeDrawLock(IBinder var1) throws RemoteException;

    public void prepareToReplaceWindows(IBinder var1, boolean var2) throws RemoteException;

    public int relayout(IWindow var1, int var2, WindowManager.LayoutParams var3, int var4, int var5, int var6, int var7, long var8, Rect var10, Rect var11, Rect var12, Rect var13, Rect var14, Rect var15, Rect var16, DisplayCutout.ParcelableWrapper var17, MergedConfiguration var18, SurfaceControl var19, InsetsState var20) throws RemoteException;

    @UnsupportedAppUsage
    public void remove(IWindow var1) throws RemoteException;

    public void reparentDisplayContent(IWindow var1, SurfaceControl var2, int var3) throws RemoteException;

    public void reportDropResult(IWindow var1, boolean var2) throws RemoteException;

    public void reportSystemGestureExclusionChanged(IWindow var1, List<Rect> var2) throws RemoteException;

    public Bundle sendWallpaperCommand(IBinder var1, String var2, int var3, int var4, int var5, Bundle var6, boolean var7) throws RemoteException;

    @UnsupportedAppUsage
    public void setInTouchMode(boolean var1) throws RemoteException;

    public void setInsets(IWindow var1, int var2, Rect var3, Rect var4, Region var5) throws RemoteException;

    @UnsupportedAppUsage
    public void setTransparentRegion(IWindow var1, Region var2) throws RemoteException;

    public void setWallpaperDisplayOffset(IBinder var1, int var2, int var3) throws RemoteException;

    public void setWallpaperPosition(IBinder var1, float var2, float var3, float var4, float var5) throws RemoteException;

    public boolean startMovingTask(IWindow var1, float var2, float var3) throws RemoteException;

    public void updateDisplayContentLocation(IWindow var1, int var2, int var3, int var4) throws RemoteException;

    public void updatePointerIcon(IWindow var1) throws RemoteException;

    public void updateTapExcludeRegion(IWindow var1, int var2, Region var3) throws RemoteException;

    @UnsupportedAppUsage
    public void wallpaperCommandComplete(IBinder var1, Bundle var2) throws RemoteException;

    @UnsupportedAppUsage
    public void wallpaperOffsetsComplete(IBinder var1) throws RemoteException;

    public static class Default
    implements IWindowSession {
        @Override
        public int addToDisplay(IWindow iWindow, int n, WindowManager.LayoutParams layoutParams, int n2, int n3, Rect rect, Rect rect2, Rect rect3, Rect rect4, DisplayCutout.ParcelableWrapper parcelableWrapper, InputChannel inputChannel, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        @Override
        public int addToDisplayWithoutInputChannel(IWindow iWindow, int n, WindowManager.LayoutParams layoutParams, int n2, int n3, Rect rect, Rect rect2, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelDragAndDrop(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void dragRecipientEntered(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void dragRecipientExited(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void finishDrawing(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void finishMovingTask(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void getDisplayFrame(IWindow iWindow, Rect rect) throws RemoteException {
        }

        @Override
        public boolean getInTouchMode() throws RemoteException {
            return false;
        }

        @Override
        public IWindowId getWindowId(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void insetsModified(IWindow iWindow, InsetsState insetsState) throws RemoteException {
        }

        @Override
        public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException {
        }

        @Override
        public boolean outOfMemory(IWindow iWindow) throws RemoteException {
            return false;
        }

        @Override
        public IBinder performDrag(IWindow iWindow, int n, SurfaceControl surfaceControl, int n2, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException {
            return null;
        }

        @Override
        public boolean performHapticFeedback(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void pokeDrawLock(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void prepareToReplaceWindows(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public int relayout(IWindow iWindow, int n, WindowManager.LayoutParams layoutParams, int n2, int n3, int n4, int n5, long l, Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, Rect rect7, DisplayCutout.ParcelableWrapper parcelableWrapper, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        @Override
        public void remove(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void reparentDisplayContent(IWindow iWindow, SurfaceControl surfaceControl, int n) throws RemoteException {
        }

        @Override
        public void reportDropResult(IWindow iWindow, boolean bl) throws RemoteException {
        }

        @Override
        public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException {
        }

        @Override
        public Bundle sendWallpaperCommand(IBinder iBinder, String string2, int n, int n2, int n3, Bundle bundle, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public void setInTouchMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setInsets(IWindow iWindow, int n, Rect rect, Rect rect2, Region region) throws RemoteException {
        }

        @Override
        public void setTransparentRegion(IWindow iWindow, Region region) throws RemoteException {
        }

        @Override
        public void setWallpaperDisplayOffset(IBinder iBinder, int n, int n2) throws RemoteException {
        }

        @Override
        public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException {
        }

        @Override
        public boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException {
            return false;
        }

        @Override
        public void updateDisplayContentLocation(IWindow iWindow, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void updatePointerIcon(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void updateTapExcludeRegion(IWindow iWindow, int n, Region region) throws RemoteException {
        }

        @Override
        public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override
        public void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWindowSession {
        private static final String DESCRIPTOR = "android.view.IWindowSession";
        static final int TRANSACTION_addToDisplay = 1;
        static final int TRANSACTION_addToDisplayWithoutInputChannel = 2;
        static final int TRANSACTION_cancelDragAndDrop = 16;
        static final int TRANSACTION_dragRecipientEntered = 17;
        static final int TRANSACTION_dragRecipientExited = 18;
        static final int TRANSACTION_finishDrawing = 10;
        static final int TRANSACTION_finishMovingTask = 28;
        static final int TRANSACTION_getDisplayFrame = 9;
        static final int TRANSACTION_getInTouchMode = 12;
        static final int TRANSACTION_getWindowId = 25;
        static final int TRANSACTION_insetsModified = 33;
        static final int TRANSACTION_onRectangleOnScreenRequested = 24;
        static final int TRANSACTION_outOfMemory = 6;
        static final int TRANSACTION_performDrag = 14;
        static final int TRANSACTION_performHapticFeedback = 13;
        static final int TRANSACTION_pokeDrawLock = 26;
        static final int TRANSACTION_prepareToReplaceWindows = 5;
        static final int TRANSACTION_relayout = 4;
        static final int TRANSACTION_remove = 3;
        static final int TRANSACTION_reparentDisplayContent = 30;
        static final int TRANSACTION_reportDropResult = 15;
        static final int TRANSACTION_reportSystemGestureExclusionChanged = 34;
        static final int TRANSACTION_sendWallpaperCommand = 22;
        static final int TRANSACTION_setInTouchMode = 11;
        static final int TRANSACTION_setInsets = 8;
        static final int TRANSACTION_setTransparentRegion = 7;
        static final int TRANSACTION_setWallpaperDisplayOffset = 21;
        static final int TRANSACTION_setWallpaperPosition = 19;
        static final int TRANSACTION_startMovingTask = 27;
        static final int TRANSACTION_updateDisplayContentLocation = 31;
        static final int TRANSACTION_updatePointerIcon = 29;
        static final int TRANSACTION_updateTapExcludeRegion = 32;
        static final int TRANSACTION_wallpaperCommandComplete = 23;
        static final int TRANSACTION_wallpaperOffsetsComplete = 20;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWindowSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWindowSession) {
                return (IWindowSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWindowSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 34: {
                    return "reportSystemGestureExclusionChanged";
                }
                case 33: {
                    return "insetsModified";
                }
                case 32: {
                    return "updateTapExcludeRegion";
                }
                case 31: {
                    return "updateDisplayContentLocation";
                }
                case 30: {
                    return "reparentDisplayContent";
                }
                case 29: {
                    return "updatePointerIcon";
                }
                case 28: {
                    return "finishMovingTask";
                }
                case 27: {
                    return "startMovingTask";
                }
                case 26: {
                    return "pokeDrawLock";
                }
                case 25: {
                    return "getWindowId";
                }
                case 24: {
                    return "onRectangleOnScreenRequested";
                }
                case 23: {
                    return "wallpaperCommandComplete";
                }
                case 22: {
                    return "sendWallpaperCommand";
                }
                case 21: {
                    return "setWallpaperDisplayOffset";
                }
                case 20: {
                    return "wallpaperOffsetsComplete";
                }
                case 19: {
                    return "setWallpaperPosition";
                }
                case 18: {
                    return "dragRecipientExited";
                }
                case 17: {
                    return "dragRecipientEntered";
                }
                case 16: {
                    return "cancelDragAndDrop";
                }
                case 15: {
                    return "reportDropResult";
                }
                case 14: {
                    return "performDrag";
                }
                case 13: {
                    return "performHapticFeedback";
                }
                case 12: {
                    return "getInTouchMode";
                }
                case 11: {
                    return "setInTouchMode";
                }
                case 10: {
                    return "finishDrawing";
                }
                case 9: {
                    return "getDisplayFrame";
                }
                case 8: {
                    return "setInsets";
                }
                case 7: {
                    return "setTransparentRegion";
                }
                case 6: {
                    return "outOfMemory";
                }
                case 5: {
                    return "prepareToReplaceWindows";
                }
                case 4: {
                    return "relayout";
                }
                case 3: {
                    return "remove";
                }
                case 2: {
                    return "addToDisplayWithoutInputChannel";
                }
                case 1: 
            }
            return "addToDisplay";
        }

        public static boolean setDefaultImpl(IWindowSession iWindowSession) {
            if (Proxy.sDefaultImpl == null && iWindowSession != null) {
                Proxy.sDefaultImpl = iWindowSession;
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
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportSystemGestureExclusionChanged(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createTypedArrayList(Rect.CREATOR));
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? InsetsState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.insetsModified(iWindow, (InsetsState)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateTapExcludeRegion(iWindow, n, (Region)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateDisplayContentLocation(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        SurfaceControl surfaceControl = ((Parcel)object).readInt() != 0 ? SurfaceControl.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reparentDisplayContent(iWindow, surfaceControl, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updatePointerIcon(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishMovingTask(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startMovingTask(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readFloat(), ((Parcel)object).readFloat()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pokeDrawLock(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWindowId(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRectangleOnScreenRequested(iBinder, (Rect)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.wallpaperCommandComplete(iBinder, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string2 = ((Parcel)object).readString();
                        int n3 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl5 = ((Parcel)object).readInt() != 0;
                        object = this.sendWallpaperCommand(iBinder, string2, n3, n, n2, bundle, bl5);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWallpaperDisplayOffset(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.wallpaperOffsetsComplete(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWallpaperPosition(((Parcel)object).readStrongBinder(), ((Parcel)object).readFloat(), ((Parcel)object).readFloat(), ((Parcel)object).readFloat(), ((Parcel)object).readFloat());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dragRecipientExited(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dragRecipientEntered(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.cancelDragAndDrop(iBinder, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.reportDropResult(iWindow, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        SurfaceControl surfaceControl = ((Parcel)object).readInt() != 0 ? SurfaceControl.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        float f = ((Parcel)object).readFloat();
                        float f2 = ((Parcel)object).readFloat();
                        float f3 = ((Parcel)object).readFloat();
                        float f4 = ((Parcel)object).readFloat();
                        object = ((Parcel)object).readInt() != 0 ? ClipData.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.performDrag(iWindow, n2, surfaceControl, n, f, f2, f3, f4, (ClipData)object);
                        parcel.writeNoException();
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        n = this.performHapticFeedback(n, bl5) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInTouchMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setInTouchMode(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishDrawing(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = new Rect();
                        this.getDisplayFrame(iWindow, (Rect)object);
                        parcel.writeNoException();
                        parcel.writeInt(1);
                        ((Rect)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setInsets(iWindow, n, rect, rect2, (Region)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTransparentRegion(iWindow, (Region)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.outOfMemory(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl5 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.prepareToReplaceWindows(iBinder, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        int n4 = ((Parcel)object).readInt();
                        WindowManager.LayoutParams layoutParams = ((Parcel)object).readInt() != 0 ? WindowManager.LayoutParams.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        int n5 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        int n6 = ((Parcel)object).readInt();
                        long l = ((Parcel)object).readLong();
                        object = new Rect();
                        Rect rect = new Rect();
                        Rect rect3 = new Rect();
                        Rect rect4 = new Rect();
                        Rect rect5 = new Rect();
                        Rect rect6 = new Rect();
                        Rect rect7 = new Rect();
                        DisplayCutout.ParcelableWrapper parcelableWrapper = new DisplayCutout.ParcelableWrapper();
                        MergedConfiguration mergedConfiguration = new MergedConfiguration();
                        SurfaceControl surfaceControl = new SurfaceControl();
                        InsetsState insetsState = new InsetsState();
                        n = this.relayout(iWindow, n4, layoutParams, n, n5, n2, n6, l, (Rect)object, rect, rect3, rect4, rect5, rect6, rect7, parcelableWrapper, mergedConfiguration, surfaceControl, insetsState);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeInt(1);
                        ((Rect)object).writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect3.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect4.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect5.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect6.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect7.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        parcelableWrapper.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        mergedConfiguration.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        surfaceControl.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        insetsState.writeToParcel(parcel, 1);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.remove(IWindow.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        WindowManager.LayoutParams layoutParams = ((Parcel)object).readInt() != 0 ? WindowManager.LayoutParams.CREATOR.createFromParcel((Parcel)object) : null;
                        int n7 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        Rect rect = new Rect();
                        Rect rect8 = new Rect();
                        object = new InsetsState();
                        n = this.addToDisplayWithoutInputChannel(iWindow, n, layoutParams, n7, n2, rect, rect8, (InsetsState)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        rect8.writeToParcel(parcel, 1);
                        parcel.writeInt(1);
                        ((InsetsState)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IWindow iWindow = IWindow.Stub.asInterface(((Parcel)object).readStrongBinder());
                n = ((Parcel)object).readInt();
                WindowManager.LayoutParams layoutParams = ((Parcel)object).readInt() != 0 ? WindowManager.LayoutParams.CREATOR.createFromParcel((Parcel)object) : null;
                n2 = ((Parcel)object).readInt();
                int n8 = ((Parcel)object).readInt();
                Rect rect = new Rect();
                Rect rect9 = new Rect();
                Rect rect10 = new Rect();
                Rect rect11 = new Rect();
                object = new DisplayCutout.ParcelableWrapper();
                InputChannel inputChannel = new InputChannel();
                InsetsState insetsState = new InsetsState();
                n = this.addToDisplay(iWindow, n, layoutParams, n2, n8, rect, rect9, rect10, rect11, (DisplayCutout.ParcelableWrapper)object, inputChannel, insetsState);
                parcel.writeNoException();
                parcel.writeInt(n);
                parcel.writeInt(1);
                rect.writeToParcel(parcel, 1);
                parcel.writeInt(1);
                rect9.writeToParcel(parcel, 1);
                parcel.writeInt(1);
                rect10.writeToParcel(parcel, 1);
                parcel.writeInt(1);
                rect11.writeToParcel(parcel, 1);
                parcel.writeInt(1);
                ((DisplayCutout.ParcelableWrapper)object).writeToParcel(parcel, 1);
                parcel.writeInt(1);
                inputChannel.writeToParcel(parcel, 1);
                parcel.writeInt(1);
                insetsState.writeToParcel(parcel, 1);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWindowSession {
            public static IWindowSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int addToDisplay(IWindow var1_1, int var2_15, WindowManager.LayoutParams var3_16, int var4_17, int var5_18, Rect var6_19, Rect var7_20, Rect var8_21, Rect var9_22, DisplayCutout.ParcelableWrapper var10_23, InputChannel var11_24, InsetsState var12_25) throws RemoteException {
                block30 : {
                    block32 : {
                        block31 : {
                            block29 : {
                                block28 : {
                                    block27 : {
                                        var13_26 = Parcel.obtain();
                                        var14_27 = Parcel.obtain();
                                        var13_26.writeInterfaceToken("android.view.IWindowSession");
                                        if (var1_1 == null) break block27;
                                        try {
                                            var15_28 = var1_1.asBinder();
                                            break block28;
                                        }
                                        catch (Throwable var1_2) {
                                            break block30;
                                        }
                                    }
                                    var15_28 = null;
                                }
                                var13_26.writeStrongBinder((IBinder)var15_28);
                                var13_26.writeInt(var2_15);
                                if (var3_16 == null) break block29;
                                var13_26.writeInt(1);
                                var3_16.writeToParcel(var13_26, 0);
                                ** GOTO lbl25
                            }
                            var13_26.writeInt(0);
lbl25: // 2 sources:
                            var13_26.writeInt(var4_17);
                            var13_26.writeInt(var5_18);
                            var16_29 = this.mRemote.transact(1, var13_26, var14_27, 0);
                            if (var16_29) break block31;
                            try {
                                if (Stub.getDefaultImpl() == null) break block31;
                                var15_28 = Stub.getDefaultImpl();
                            }
                            catch (Throwable var1_4) {
                                break block30;
                            }
                            try {
                                var2_15 = var15_28.addToDisplay((IWindow)var1_1, var2_15, var3_16, var4_17, var5_18, var6_19, var7_20, var8_21, var9_22, var10_23, var11_24, var12_25);
                                var14_27.recycle();
                                var13_26.recycle();
                                return var2_15;
                            }
                            catch (Throwable var1_3) {}
                            break block30;
                        }
                        try {
                            var14_27.readException();
                            var2_15 = var14_27.readInt();
                            var4_17 = var14_27.readInt();
                            if (var4_17 == 0) ** GOTO lbl50
                        }
                        catch (Throwable var1_13) {}
                        try {
                            var6_19.readFromParcel(var14_27);
lbl50: // 2 sources:
                            if ((var4_17 = (var1_1 = var14_27).readInt()) == 0) ** GOTO lbl54
                        }
                        catch (Throwable var1_11) {}
                        try {
                            var7_20.readFromParcel((Parcel)var1_1);
lbl54: // 2 sources:
                            if ((var4_17 = var1_1.readInt()) == 0) ** GOTO lbl58
                        }
                        catch (Throwable var1_10) {}
                        try {
                            var8_21.readFromParcel((Parcel)var1_1);
lbl58: // 2 sources:
                            if ((var4_17 = var1_1.readInt()) == 0) ** GOTO lbl62
                        }
                        catch (Throwable var1_9) {}
                        try {
                            var9_22.readFromParcel((Parcel)var1_1);
lbl62: // 2 sources:
                            if ((var4_17 = var1_1.readInt()) == 0) ** GOTO lbl66
                        }
                        catch (Throwable var1_8) {}
                        try {
                            var10_23.readFromParcel((Parcel)var1_1);
lbl66: // 2 sources:
                            if ((var4_17 = var1_1.readInt()) == 0) ** GOTO lbl70
                        }
                        catch (Throwable var1_7) {}
                        try {
                            var11_24.readFromParcel((Parcel)var1_1);
lbl70: // 2 sources:
                            if ((var4_17 = var1_1.readInt()) == 0) break block32;
                        }
                        catch (Throwable var1_6) {}
                        try {
                            var12_25.readFromParcel((Parcel)var1_1);
                        }
                        catch (Throwable var1_5) {
                            break block30;
                        }
                    }
                    var1_1.recycle();
                    var13_26.recycle();
                    return var2_15;
                    break block30;
                    catch (Throwable var1_14) {
                        // empty catch block
                    }
                }
                var14_27.recycle();
                var13_26.recycle();
                throw var1_12;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int addToDisplayWithoutInputChannel(IWindow var1_1, int var2_10, WindowManager.LayoutParams var3_11, int var4_12, int var5_13, Rect var6_14, Rect var7_15, InsetsState var8_16) throws RemoteException {
                block19 : {
                    block18 : {
                        block17 : {
                            var9_17 = Parcel.obtain();
                            var10_18 = Parcel.obtain();
                            var9_17.writeInterfaceToken("android.view.IWindowSession");
                            var11_19 = var1_1 != null ? var1_1.asBinder() : null;
                            var9_17.writeStrongBinder(var11_19);
                            try {
                                var9_17.writeInt(var2_10);
                                if (var3_11 != null) {
                                    var9_17.writeInt(1);
                                    var3_11.writeToParcel(var9_17, 0);
                                    break block17;
                                }
                                var9_17.writeInt(0);
                            }
                            catch (Throwable var1_7) {}
                        }
                        try {
                            var9_17.writeInt(var4_12);
                        }
                        catch (Throwable var1_6) {
                            break block19;
                        }
                        try {
                            var9_17.writeInt(var5_13);
                            if (!this.mRemote.transact(2, var9_17, var10_18, 0) && Stub.getDefaultImpl() != null) {
                                var2_10 = Stub.getDefaultImpl().addToDisplayWithoutInputChannel(var1_1, var2_10, var3_11, var4_12, var5_13, var6_14, var7_15, var8_16);
                                var10_18.recycle();
                                var9_17.recycle();
                                return var2_10;
                            }
                            var10_18.readException();
                            var2_10 = var10_18.readInt();
                            var4_12 = var10_18.readInt();
                            if (var4_12 == 0) ** GOTO lbl34
                        }
                        catch (Throwable var1_5) {}
                        try {
                            var6_14.readFromParcel(var10_18);
lbl34: // 2 sources:
                            if ((var4_12 = var10_18.readInt()) == 0) ** GOTO lbl38
                        }
                        catch (Throwable var1_4) {}
                        try {
                            var7_15.readFromParcel(var10_18);
lbl38: // 2 sources:
                            if ((var4_12 = var10_18.readInt()) == 0) break block18;
                        }
                        catch (Throwable var1_3) {}
                        try {
                            var8_16.readFromParcel(var10_18);
                        }
                        catch (Throwable var1_2) {
                            break block19;
                        }
                    }
                    var10_18.recycle();
                    var9_17.recycle();
                    return var2_10;
                    break block19;
                    catch (Throwable var1_8) {
                        // empty catch block
                    }
                }
                var10_18.recycle();
                var9_17.recycle();
                throw var1_9;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelDragAndDrop(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelDragAndDrop(iBinder, bl);
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
            public void dragRecipientEntered(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dragRecipientEntered(iWindow);
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
            public void dragRecipientExited(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dragRecipientExited(iWindow);
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
            public void finishDrawing(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishDrawing(iWindow);
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
            public void finishMovingTask(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishMovingTask(iWindow);
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
            public void getDisplayFrame(IWindow iWindow, Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDisplayFrame(iWindow, rect);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() == 0) return;
                    rect.readFromParcel(parcel2);
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getInTouchMode() throws RemoteException {
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
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getInTouchMode();
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
            public IWindowId getWindowId(IBinder object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getWindowId((IBinder)object);
                        return object;
                    }
                    parcel2.readException();
                    object = IWindowId.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public void insetsModified(IWindow iWindow, InsetsState insetsState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (insetsState != null) {
                        parcel.writeInt(1);
                        insetsState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().insetsModified(iWindow, insetsState);
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
            public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRectangleOnScreenRequested(iBinder, rect);
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
            public boolean outOfMemory(IWindow iWindow) throws RemoteException {
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
                                if (iWindow == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iWindow.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().outOfMemory(iWindow);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public IBinder performDrag(IWindow object, int n, SurfaceControl surfaceControl, int n2, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException {
                void var1_4;
                Parcel parcel2;
                Parcel parcel;
                block10 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = object != null ? object.asBinder() : null;
                    parcel2.writeStrongBinder(iBinder);
                    try {
                        parcel2.writeInt(n);
                        if (surfaceControl != null) {
                            parcel2.writeInt(1);
                            surfaceControl.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeInt(n2);
                        parcel2.writeFloat(f);
                        parcel2.writeFloat(f2);
                        parcel2.writeFloat(f3);
                        parcel2.writeFloat(f4);
                        if (clipData != null) {
                            parcel2.writeInt(1);
                            clipData.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(14, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().performDrag((IWindow)object, n, surfaceControl, n2, f, f2, f3, f4, clipData);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readStrongBinder();
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_4;
            }

            @Override
            public boolean performHapticFeedback(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().performHapticFeedback(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void pokeDrawLock(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pokeDrawLock(iBinder);
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
            public void prepareToReplaceWindows(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareToReplaceWindows(iBinder, bl);
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
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int relayout(IWindow var1_1, int var2_19, WindowManager.LayoutParams var3_20, int var4_21, int var5_22, int var6_23, int var7_24, long var8_25, Rect var10_26, Rect var11_27, Rect var12_28, Rect var13_29, Rect var14_30, Rect var15_31, Rect var16_32, DisplayCutout.ParcelableWrapper var17_33, MergedConfiguration var18_34, SurfaceControl var19_35, InsetsState var20_36) throws RemoteException {
                block38 : {
                    block40 : {
                        block39 : {
                            block37 : {
                                block36 : {
                                    block35 : {
                                        var21_37 = Parcel.obtain();
                                        var22_38 = Parcel.obtain();
                                        var21_37.writeInterfaceToken("android.view.IWindowSession");
                                        if (var1_1 == null) break block35;
                                        try {
                                            var23_39 = var1_1.asBinder();
                                            break block36;
                                        }
                                        catch (Throwable var1_2) {
                                            break block38;
                                        }
                                    }
                                    var23_39 = null;
                                }
                                var21_37.writeStrongBinder((IBinder)var23_39);
                                var21_37.writeInt(var2_19);
                                if (var3_20 == null) break block37;
                                var21_37.writeInt(1);
                                var3_20.writeToParcel(var21_37, 0);
                                ** GOTO lbl25
                            }
                            var21_37.writeInt(0);
lbl25: // 2 sources:
                            var21_37.writeInt(var4_21);
                            var21_37.writeInt(var5_22);
                            var21_37.writeInt(var6_23);
                            var21_37.writeInt(var7_24);
                            var21_37.writeLong(var8_25);
                            var24_40 = this.mRemote.transact(4, var21_37, var22_38, 0);
                            if (var24_40) break block39;
                            try {
                                if (Stub.getDefaultImpl() == null) break block39;
                                var23_39 = Stub.getDefaultImpl();
                            }
                            catch (Throwable var1_4) {
                                break block38;
                            }
                            try {
                                var2_19 = var23_39.relayout((IWindow)var1_1, var2_19, var3_20, var4_21, var5_22, var6_23, var7_24, var8_25, var10_26, var11_27, var12_28, var13_29, var14_30, var15_31, var16_32, var17_33, var18_34, var19_35, var20_36);
                                var22_38.recycle();
                                var21_37.recycle();
                                return var2_19;
                            }
                            catch (Throwable var1_3) {}
                            break block38;
                        }
                        try {
                            var22_38.readException();
                            var2_19 = var22_38.readInt();
                            var4_21 = var22_38.readInt();
                            if (var4_21 == 0) ** GOTO lbl53
                        }
                        catch (Throwable var1_16) {}
                        try {
                            var10_26.readFromParcel(var22_38);
lbl53: // 2 sources:
                            if ((var4_21 = (var1_1 = var22_38).readInt()) == 0) ** GOTO lbl57
                        }
                        catch (Throwable var1_15) {}
                        try {
                            var11_27.readFromParcel((Parcel)var1_1);
lbl57: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl61
                        }
                        catch (Throwable var1_14) {}
                        try {
                            var12_28.readFromParcel((Parcel)var1_1);
lbl61: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl65
                        }
                        catch (Throwable var1_13) {}
                        try {
                            var13_29.readFromParcel((Parcel)var1_1);
lbl65: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl69
                        }
                        catch (Throwable var1_12) {}
                        try {
                            var14_30.readFromParcel((Parcel)var1_1);
lbl69: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl73
                        }
                        catch (Throwable var1_11) {}
                        try {
                            var15_31.readFromParcel((Parcel)var1_1);
lbl73: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl77
                        }
                        catch (Throwable var1_10) {}
                        try {
                            var16_32.readFromParcel((Parcel)var1_1);
lbl77: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl81
                        }
                        catch (Throwable var1_9) {}
                        try {
                            var17_33.readFromParcel((Parcel)var1_1);
lbl81: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl85
                        }
                        catch (Throwable var1_8) {}
                        try {
                            var18_34.readFromParcel((Parcel)var1_1);
lbl85: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) ** GOTO lbl89
                        }
                        catch (Throwable var1_7) {}
                        try {
                            var19_35.readFromParcel((Parcel)var1_1);
lbl89: // 2 sources:
                            if ((var4_21 = var1_1.readInt()) == 0) break block40;
                        }
                        catch (Throwable var1_6) {}
                        try {
                            var20_36.readFromParcel((Parcel)var1_1);
                        }
                        catch (Throwable var1_5) {
                            break block38;
                        }
                    }
                    var1_1.recycle();
                    var21_37.recycle();
                    return var2_19;
                    break block38;
                    catch (Throwable var1_17) {
                        // empty catch block
                    }
                }
                var22_38.recycle();
                var21_37.recycle();
                throw var1_18;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void remove(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remove(iWindow);
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
            public void reparentDisplayContent(IWindow iWindow, SurfaceControl surfaceControl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (surfaceControl != null) {
                        parcel.writeInt(1);
                        surfaceControl.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reparentDisplayContent(iWindow, surfaceControl, n);
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
            public void reportDropResult(IWindow iWindow, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iWindow == null) break block8;
                    iBinder = iWindow.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(15, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportDropResult(iWindow, bl);
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
            public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedList(list);
                    if (this.mRemote.transact(34, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().reportSystemGestureExclusionChanged(iWindow, list);
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
            public Bundle sendWallpaperCommand(IBinder object, String string2, int n, int n2, int n3, Bundle bundle, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder((IBinder)object);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        int n4 = 1;
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!bl) {
                            n4 = 0;
                        }
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().sendWallpaperCommand((IBinder)object, string2, n, n2, n3, bundle, bl);
                            parcel2.recycle();
                            parcel.recycle();
                            return object;
                        }
                        parcel2.readException();
                        object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public void setInTouchMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInTouchMode(bl);
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
            public void setInsets(IWindow iWindow, int n, Rect rect, Rect rect2, Region region) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect2 != null) {
                        parcel.writeInt(1);
                        rect2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInsets(iWindow, n, rect, rect2, region);
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
            public void setTransparentRegion(IWindow iWindow, Region region) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTransparentRegion(iWindow, region);
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
            public void setWallpaperDisplayOffset(IBinder iBinder, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWallpaperDisplayOffset(iBinder, n, n2);
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
            public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeFloat(f);
                    parcel.writeFloat(f2);
                    parcel.writeFloat(f3);
                    parcel.writeFloat(f4);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWallpaperPosition(iBinder, f, f2, f3, f4);
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
            public boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iWindow == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iWindow.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeFloat(f);
                    parcel.writeFloat(f2);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(27, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().startMovingTask(iWindow, f, f2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateDisplayContentLocation(IWindow iWindow, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateDisplayContentLocation(iWindow, n, n2, n3);
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
            public void updatePointerIcon(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePointerIcon(iWindow);
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
            public void updateTapExcludeRegion(IWindow iWindow, int n, Region region) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTapExcludeRegion(iWindow, n, region);
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
            public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wallpaperCommandComplete(iBinder, bundle);
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
            public void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wallpaperOffsetsComplete(iBinder);
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

