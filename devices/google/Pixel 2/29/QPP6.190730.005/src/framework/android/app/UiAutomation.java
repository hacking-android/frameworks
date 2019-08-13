/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs
 *  libcore.io.IoUtils
 */
package android.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.annotation.UnsupportedAppUsage;
import android.app.-$;
import android.app.ActivityManager;
import android.app.IUiAutomationConnection;
import android.app._$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import libcore.io.IoUtils;

public final class UiAutomation {
    private static final int CONNECTION_ID_UNDEFINED = -1;
    private static final long CONNECT_TIMEOUT_MILLIS = 5000L;
    private static final boolean DEBUG = false;
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    private static final String LOG_TAG = UiAutomation.class.getSimpleName();
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    private IAccessibilityServiceClient mClient;
    private int mConnectionId = -1;
    private final ArrayList<AccessibilityEvent> mEventQueue = new ArrayList();
    private int mFlags;
    private boolean mIsConnecting;
    private boolean mIsDestroyed;
    private long mLastEventTimeMillis;
    private final Handler mLocalCallbackHandler;
    private final Object mLock = new Object();
    private OnAccessibilityEventListener mOnAccessibilityEventListener;
    private HandlerThread mRemoteCallbackThread;
    private final IUiAutomationConnection mUiAutomationConnection;
    private boolean mWaitingForEventDelivery;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public UiAutomation(Looper looper, IUiAutomationConnection iUiAutomationConnection) {
        if (looper != null) {
            if (iUiAutomationConnection != null) {
                this.mLocalCallbackHandler = new Handler(looper);
                this.mUiAutomationConnection = iUiAutomationConnection;
                return;
            }
            throw new IllegalArgumentException("Connection cannot be null!");
        }
        throw new IllegalArgumentException("Looper cannot be null!");
    }

    private boolean isConnectedLocked() {
        boolean bl = this.mConnectionId != -1;
        return bl;
    }

    private void throwIfConnectedLocked() {
        if (this.mConnectionId == -1) {
            return;
        }
        throw new IllegalStateException("UiAutomation not connected!");
    }

    private void throwIfNotConnectedLocked() {
        if (this.isConnectedLocked()) {
            return;
        }
        throw new IllegalStateException("UiAutomation not connected!");
    }

    private void warnIfBetterCommand(String string2) {
        if (string2.startsWith("pm grant ")) {
            Log.w(LOG_TAG, "UiAutomation.grantRuntimePermission() is more robust and should be used instead of 'pm grant'");
        } else if (string2.startsWith("pm revoke ")) {
            Log.w(LOG_TAG, "UiAutomation.revokeRuntimePermission() is more robust and should be used instead of 'pm revoke'");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void adoptShellPermissionIdentity() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), null);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error executing adopting shell permission identity!", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void adoptShellPermissionIdentity(String ... arrstring) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), arrstring);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error executing adopting shell permission identity!", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clearWindowAnimationFrameStats() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.clearWindowAnimationFrameStats();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error clearing window animation frame stats!", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean clearWindowContentFrameStats(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.clearWindowContentFrameStats(n);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error clearing window content frame stats!", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void connect() {
        this.connect(0);
    }

    /*
     * Exception decompiling
     */
    public void connect(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [15[UNCONDITIONALDOLOOP]], but top level block is 6[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void destroy() {
        this.disconnect();
        this.mIsDestroyed = true;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void disconnect() {
        Throwable throwable2222;
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mIsConnecting) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call disconnect() while connecting!");
            throw illegalStateException;
        }
        this.throwIfNotConnectedLocked();
        this.mConnectionId = -1;
        // MONITOREXIT : object
        this.mUiAutomationConnection.disconnect();
        this.mRemoteCallbackThread.quit();
        this.mRemoteCallbackThread = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                object = new RuntimeException("Error while disconnecting UiAutomation", remoteException);
                throw object;
            }
        }
        this.mRemoteCallbackThread.quit();
        this.mRemoteCallbackThread = null;
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dropShellPermissionIdentity() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.dropShellPermissionIdentity();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error executing dropping shell permission identity!", remoteException);
        }
    }

    /*
     * Exception decompiling
     */
    public AccessibilityEvent executeAndWaitForEvent(Runnable var1_1, AccessibilityEventFilter var2_8, long var3_9) throws TimeoutException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 29[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParcelFileDescriptor executeShellCommand(String string2) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        this.warnIfBetterCommand(string2);
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        Object object2 = null;
        Object object3 = null;
        object = null;
        try {
            try {
                Object object4 = ParcelFileDescriptor.createPipe();
                ParcelFileDescriptor parcelFileDescriptor3 = object4[0];
                object = object4 = object4[1];
                parcelFileDescriptor = parcelFileDescriptor3;
                object2 = object4;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                object3 = object4;
                this.mUiAutomationConnection.executeShellCommand(string2, (ParcelFileDescriptor)object4, null);
                parcelFileDescriptor = parcelFileDescriptor3;
                object2 = object4;
            }
            catch (RemoteException remoteException) {
                object = object2;
                Log.e(LOG_TAG, "Error executing shell command!", remoteException);
            }
            catch (IOException iOException) {
                object = object3;
                Log.e(LOG_TAG, "Error executing shell command!", iOException);
                object2 = object3;
                parcelFileDescriptor = parcelFileDescriptor2;
            }
        }
        catch (Throwable throwable2) {}
        IoUtils.closeQuietly(object2);
        return parcelFileDescriptor;
        IoUtils.closeQuietly((AutoCloseable)object);
        throw throwable2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParcelFileDescriptor[] executeShellCommandRw(String string2) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        this.warnIfBetterCommand(string2);
        ParcelFileDescriptor parcelFileDescriptor3 = null;
        ParcelFileDescriptor parcelFileDescriptor4 = null;
        Object object2 = null;
        Object object3 = null;
        object = null;
        ParcelFileDescriptor parcelFileDescriptor5 = null;
        ParcelFileDescriptor parcelFileDescriptor6 = null;
        ParcelFileDescriptor parcelFileDescriptor7 = null;
        ParcelFileDescriptor[] arrparcelFileDescriptor2 = null;
        Object object4 = null;
        ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptor7;
        ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor5;
        ParcelFileDescriptor[] arrparcelFileDescriptor = object4;
        ParcelFileDescriptor parcelFileDescriptor8 = parcelFileDescriptor6;
        ParcelFileDescriptor[] arrparcelFileDescriptor3 = arrparcelFileDescriptor2;
        try {
            try {
                Object object5 = ParcelFileDescriptor.createPipe();
                ParcelFileDescriptor parcelFileDescriptor9 = object5[0];
                object = object5 = object5[1];
                parcelFileDescriptor = parcelFileDescriptor7;
                parcelFileDescriptor4 = parcelFileDescriptor9;
                object2 = object5;
                parcelFileDescriptor2 = parcelFileDescriptor5;
                arrparcelFileDescriptor = object4;
                parcelFileDescriptor3 = parcelFileDescriptor9;
                object3 = object5;
                parcelFileDescriptor8 = parcelFileDescriptor6;
                arrparcelFileDescriptor3 = arrparcelFileDescriptor2;
                object4 = ParcelFileDescriptor.createPipe();
                parcelFileDescriptor7 = object4[0];
                object4 = object4[1];
                object = object5;
                parcelFileDescriptor = parcelFileDescriptor7;
                parcelFileDescriptor4 = parcelFileDescriptor9;
                object2 = object5;
                parcelFileDescriptor2 = parcelFileDescriptor7;
                arrparcelFileDescriptor = object4;
                parcelFileDescriptor3 = parcelFileDescriptor9;
                object3 = object5;
                parcelFileDescriptor8 = parcelFileDescriptor7;
                arrparcelFileDescriptor3 = object4;
                this.mUiAutomationConnection.executeShellCommand(string2, (ParcelFileDescriptor)object5, parcelFileDescriptor7);
                parcelFileDescriptor3 = parcelFileDescriptor9;
                object2 = object5;
                parcelFileDescriptor2 = parcelFileDescriptor7;
                arrparcelFileDescriptor = object4;
            }
            catch (RemoteException remoteException) {
                object = object2;
                parcelFileDescriptor = parcelFileDescriptor2;
                Log.e(LOG_TAG, "Error executing shell command!", remoteException);
                parcelFileDescriptor3 = parcelFileDescriptor4;
            }
            catch (IOException iOException) {
                object = object3;
                parcelFileDescriptor = parcelFileDescriptor8;
                Log.e(LOG_TAG, "Error executing shell command!", iOException);
                arrparcelFileDescriptor = arrparcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor8;
                object2 = object3;
            }
        }
        catch (Throwable throwable2) {}
        IoUtils.closeQuietly(object2);
        IoUtils.closeQuietly(parcelFileDescriptor2);
        return new ParcelFileDescriptor[]{parcelFileDescriptor3, arrparcelFileDescriptor};
        IoUtils.closeQuietly((AutoCloseable)object);
        IoUtils.closeQuietly(parcelFileDescriptor);
        throw throwable2;
    }

    public AccessibilityNodeInfo findFocus(int n) {
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getConnectionId() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            return this.mConnectionId;
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AccessibilityNodeInfo getRootInActiveWindow() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            int n = this.mConnectionId;
            return AccessibilityInteractionClient.getInstance().getRootInActiveWindow(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final AccessibilityServiceInfo getServiceInfo() {
        IAccessibilityServiceConnection iAccessibilityServiceConnection;
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (iAccessibilityServiceConnection == null) return null;
        try {
            return iAccessibilityServiceConnection.getServiceInfo();
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", remoteException);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowAnimationFrameStats();
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error getting window animation frame stats!", remoteException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public WindowContentFrameStats getWindowContentFrameStats(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowContentFrameStats(n);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error getting window content frame stats!", remoteException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AccessibilityWindowInfo> getWindows() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            int n = this.mConnectionId;
            return AccessibilityInteractionClient.getInstance().getWindows(n);
        }
    }

    public void grantRuntimePermission(String string2, String string3) {
        this.grantRuntimePermissionAsUser(string2, string3, Process.myUserHandle());
    }

    @Deprecated
    public boolean grantRuntimePermission(String string2, String string3, UserHandle userHandle) {
        this.grantRuntimePermissionAsUser(string2, string3, userHandle);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void grantRuntimePermissionAsUser(String string2, String string3, UserHandle userHandle) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.grantRuntimePermission(string2, string3, userHandle.getIdentifier());
            return;
        }
        catch (Exception exception) {
            throw new SecurityException("Error granting runtime permission", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean injectInputEvent(InputEvent inputEvent, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.injectInputEvent(inputEvent, bl);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while injecting input event!", remoteException);
            return false;
        }
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean performGlobalAction(int n) {
        IAccessibilityServiceConnection iAccessibilityServiceConnection;
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (iAccessibilityServiceConnection == null) return false;
        try {
            return iAccessibilityServiceConnection.performGlobalAction(n);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Error while calling performGlobalAction", remoteException);
        }
        return false;
    }

    public void revokeRuntimePermission(String string2, String string3) {
        this.revokeRuntimePermissionAsUser(string2, string3, Process.myUserHandle());
    }

    @Deprecated
    public boolean revokeRuntimePermission(String string2, String string3, UserHandle userHandle) {
        this.revokeRuntimePermissionAsUser(string2, string3, userHandle);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void revokeRuntimePermissionAsUser(String string2, String string3, UserHandle userHandle) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.revokeRuntimePermission(string2, string3, userHandle.getIdentifier());
            return;
        }
        catch (Exception exception) {
            throw new SecurityException("Error granting runtime permission", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnAccessibilityEventListener(OnAccessibilityEventListener onAccessibilityEventListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mOnAccessibilityEventListener = onAccessibilityEventListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean setRotation(int n) {
        Object object = this.mLock;
        // MONITORENTER : object
        this.throwIfNotConnectedLocked();
        // MONITOREXIT : object
        if (n != -2 && n != -1 && n != 0 && n != 1 && n != 2) {
            if (n != 3) throw new IllegalArgumentException("Invalid rotation.");
        }
        try {
            this.mUiAutomationConnection.setRotation(n);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while setting rotation!", remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRunAsMonkey(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            ActivityManager.getService().setUserIsMonkey(bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while setting run as monkey!", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        IAccessibilityServiceConnection iAccessibilityServiceConnection;
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance().clearCache();
            AccessibilityInteractionClient.getInstance();
            iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (iAccessibilityServiceConnection == null) return;
        try {
            iAccessibilityServiceConnection.setServiceInfo(accessibilityServiceInfo);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void syncInputTransactions() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mUiAutomationConnection.syncInputTransactions();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap takeScreenshot() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotConnectedLocked();
        }
        Object object2 = DisplayManagerGlobal.getInstance().getRealDisplay(0);
        object = new Point();
        ((Display)object2).getRealSize((Point)object);
        int n = ((Display)object2).getRotation();
        try {
            IUiAutomationConnection iUiAutomationConnection = this.mUiAutomationConnection;
            object2 = new Rect(0, 0, ((Point)object).x, ((Point)object).y);
            object = iUiAutomationConnection.takeScreenshot((Rect)object2, n);
            if (object == null) {
                return null;
            }
            ((Bitmap)object).setHasAlpha(false);
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error while taking screnshot!", remoteException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void waitForIdle(long l, long l2) throws TimeoutException {
        Object object = this.mLock;
        synchronized (object) {
            long l3;
            this.throwIfNotConnectedLocked();
            long l4 = l3 = SystemClock.uptimeMillis();
            if (this.mLastEventTimeMillis <= 0L) {
                this.mLastEventTimeMillis = l3;
                l4 = l3;
            }
            do {
                if (l2 - ((l3 = SystemClock.uptimeMillis()) - l4) <= 0L) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No idle state with idle timeout: ");
                    stringBuilder.append(l);
                    stringBuilder.append(" within global timeout: ");
                    stringBuilder.append(l2);
                    TimeoutException timeoutException = new TimeoutException(stringBuilder.toString());
                    throw timeoutException;
                }
                if ((l3 = l - (l3 - this.mLastEventTimeMillis)) <= 0L) {
                    return;
                }
                try {
                    this.mLock.wait(l3);
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

    public static interface AccessibilityEventFilter {
        public boolean accept(AccessibilityEvent var1);
    }

    private class IAccessibilityServiceClientImpl
    extends AccessibilityService.IAccessibilityServiceClientWrapper {
        public IAccessibilityServiceClientImpl(Looper looper) {
            super(null, looper, new AccessibilityService.Callbacks(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void init(int n, IBinder iBinder) {
                    Object object = UiAutomation.this.mLock;
                    synchronized (object) {
                        UiAutomation.this.mConnectionId = n;
                        UiAutomation.this.mLock.notifyAll();
                        return;
                    }
                }

                @Override
                public void onAccessibilityButtonAvailabilityChanged(boolean bl) {
                }

                @Override
                public void onAccessibilityButtonClicked() {
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Converted monitor instructions to comments
                 * Lifted jumps to return sites
                 */
                @Override
                public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                    Object object = UiAutomation.this.mLock;
                    // MONITORENTER : object
                    UiAutomation.this.mLastEventTimeMillis = accessibilityEvent.getEventTime();
                    if (UiAutomation.this.mWaitingForEventDelivery) {
                        UiAutomation.this.mEventQueue.add(AccessibilityEvent.obtain(accessibilityEvent));
                    }
                    UiAutomation.this.mLock.notifyAll();
                    OnAccessibilityEventListener onAccessibilityEventListener = UiAutomation.this.mOnAccessibilityEventListener;
                    // MONITOREXIT : object
                    if (onAccessibilityEventListener == null) return;
                    UiAutomation.this.mLocalCallbackHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs.INSTANCE, onAccessibilityEventListener, AccessibilityEvent.obtain(accessibilityEvent)));
                }

                @Override
                public void onFingerprintCapturingGesturesChanged(boolean bl) {
                }

                @Override
                public void onFingerprintGesture(int n) {
                }

                @Override
                public boolean onGesture(int n) {
                    return false;
                }

                @Override
                public void onInterrupt() {
                }

                @Override
                public boolean onKeyEvent(KeyEvent keyEvent) {
                    return false;
                }

                @Override
                public void onMagnificationChanged(int n, Region region, float f, float f2, float f3) {
                }

                @Override
                public void onPerformGestureResult(int n, boolean bl) {
                }

                @Override
                public void onServiceConnected() {
                }

                @Override
                public void onSoftKeyboardShowModeChanged(int n) {
                }
            });
        }

    }

    public static interface OnAccessibilityEventListener {
        public void onAccessibilityEvent(AccessibilityEvent var1);
    }

}

