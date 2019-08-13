/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.app;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.IActivityManager;
import android.app.IUiAutomationConnection;
import android.content.pm.IPackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.SurfaceControl;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.IAccessibilityManager;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import libcore.io.IoUtils;

public final class UiAutomationConnection
extends IUiAutomationConnection.Stub {
    private static final int INITIAL_FROZEN_ROTATION_UNSPECIFIED = -1;
    private static final String TAG = "UiAutomationConnection";
    private final IAccessibilityManager mAccessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService("accessibility"));
    private final IActivityManager mActivityManager = IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
    private IAccessibilityServiceClient mClient;
    private int mInitialFrozenRotation = -1;
    private boolean mIsShutdown;
    private final Object mLock = new Object();
    private int mOwningUid;
    private final IPackageManager mPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    private final Binder mToken = new Binder();
    private final IWindowManager mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));

    private boolean isConnectedLocked() {
        boolean bl = this.mClient != null;
        return bl;
    }

    private void registerUiTestAutomationServiceLocked(IAccessibilityServiceClient iAccessibilityServiceClient, int n) {
        IAccessibilityManager iAccessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService("accessibility"));
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = -1;
        accessibilityServiceInfo.feedbackType = 16;
        accessibilityServiceInfo.flags |= 65554;
        accessibilityServiceInfo.setCapabilities(15);
        try {
            iAccessibilityManager.registerUiTestAutomationService(this.mToken, iAccessibilityServiceClient, accessibilityServiceInfo, n);
            this.mClient = iAccessibilityServiceClient;
            return;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException("Error while registering UiTestAutomationService.", remoteException);
        }
    }

    private void restoreRotationStateLocked() {
        try {
            if (this.mInitialFrozenRotation != -1) {
                this.mWindowManager.freezeRotation(this.mInitialFrozenRotation);
            } else {
                this.mWindowManager.thawRotation();
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private void storeRotationStateLocked() {
        try {
            if (this.mWindowManager.isRotationFrozen()) {
                this.mInitialFrozenRotation = this.mWindowManager.getDefaultDisplayRotation();
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private void throwIfCalledByNotTrustedUidLocked() {
        int n;
        int n2 = Binder.getCallingUid();
        if (n2 != (n = this.mOwningUid) && n != 1000 && n2 != 0) {
            throw new SecurityException("Calling from not trusted UID!");
        }
    }

    private void throwIfNotConnectedLocked() {
        if (this.isConnectedLocked()) {
            return;
        }
        throw new IllegalStateException("Not connected!");
    }

    private void throwIfShutdownLocked() {
        if (!this.mIsShutdown) {
            return;
        }
        throw new IllegalStateException("Connection shutdown!");
    }

    private void unregisterUiTestAutomationServiceLocked() {
        IAccessibilityManager iAccessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService("accessibility"));
        try {
            iAccessibilityManager.unregisterUiTestAutomationService(this.mClient);
            this.mClient = null;
            return;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException("Error while unregistering UiTestAutomationService", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void adoptShellPermissionIdentity(int n, String[] arrstring) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.startDelegateShellPermissionIdentity(n, arrstring);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clearWindowAnimationFrameStats() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            SurfaceControl.clearAnimationFrameStats();
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean clearWindowContentFrameStats(int n) throws RemoteException {
        IBinder iBinder;
        long l;
        block7 : {
            Object object = this.mLock;
            synchronized (object) {
                this.throwIfCalledByNotTrustedUidLocked();
                this.throwIfShutdownLocked();
                this.throwIfNotConnectedLocked();
            }
            int n2 = UserHandle.getCallingUserId();
            l = Binder.clearCallingIdentity();
            iBinder = this.mAccessibilityManager.getWindowToken(n, n2);
            if (iBinder != null) break block7;
            Binder.restoreCallingIdentity(l);
            return false;
        }
        try {
            boolean bl = this.mWindowManager.clearWindowContentFrameStats(iBinder);
            return bl;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect(IAccessibilityServiceClient object, int n) {
        if (object == null) {
            throw new IllegalArgumentException("Client cannot be null!");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            this.throwIfShutdownLocked();
            if (!this.isConnectedLocked()) {
                this.mOwningUid = Binder.getCallingUid();
                this.registerUiTestAutomationServiceLocked((IAccessibilityServiceClient)object, n);
                this.storeRotationStateLocked();
                return;
            }
            object = new IllegalStateException("Already connected.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            if (this.isConnectedLocked()) {
                this.mOwningUid = -1;
                this.unregisterUiTestAutomationServiceLocked();
                this.restoreRotationStateLocked();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Already disconnected.");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dropShellPermissionIdentity() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.stopDelegateShellPermissionIdentity();
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeShellCommand(String object, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor object2) throws RemoteException {
        Object object3 = this.mLock;
        synchronized (object3) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        try {
            Process process = Runtime.getRuntime().exec((String)object);
            if (parcelFileDescriptor != null) {
                object = new Thread(new Repeater(process.getInputStream(), new FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
                ((Thread)object).start();
            } else {
                object = null;
            }
            if (object2 != null) {
                object3 = process.getOutputStream();
                object3 = new Thread(new Repeater(new FileInputStream(((ParcelFileDescriptor)object2).getFileDescriptor()), (OutputStream)object3));
                ((Thread)object3).start();
            } else {
                object3 = null;
            }
            new Thread(new Runnable((Thread)object3, (Thread)object, parcelFileDescriptor, (ParcelFileDescriptor)object2, process){
                final /* synthetic */ Process val$process;
                final /* synthetic */ Thread val$readFromProcess;
                final /* synthetic */ ParcelFileDescriptor val$sink;
                final /* synthetic */ ParcelFileDescriptor val$source;
                final /* synthetic */ Thread val$writeToProcess;
                {
                    this.val$writeToProcess = thread;
                    this.val$readFromProcess = thread2;
                    this.val$sink = parcelFileDescriptor;
                    this.val$source = parcelFileDescriptor2;
                    this.val$process = process;
                }

                @Override
                public void run() {
                    try {
                        if (this.val$writeToProcess != null) {
                            this.val$writeToProcess.join();
                        }
                        if (this.val$readFromProcess != null) {
                            this.val$readFromProcess.join();
                        }
                    }
                    catch (InterruptedException interruptedException) {
                        Log.e(UiAutomationConnection.TAG, "At least one of the threads was interrupted");
                    }
                    IoUtils.closeQuietly((AutoCloseable)this.val$sink);
                    IoUtils.closeQuietly((AutoCloseable)this.val$source);
                    this.val$process.destroy();
                }
            }).start();
            return;
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Error running shell command '");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("'");
            throw new RuntimeException(((StringBuilder)object2).toString(), iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            object = new WindowAnimationFrameStats();
            SurfaceControl.getAnimationFrameStats((WindowAnimationFrameStats)object);
            return object;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public WindowContentFrameStats getWindowContentFrameStats(int n) throws RemoteException {
        long l;
        Object object;
        block7 : {
            Object object2 = this.mLock;
            synchronized (object2) {
                this.throwIfCalledByNotTrustedUidLocked();
                this.throwIfShutdownLocked();
                this.throwIfNotConnectedLocked();
            }
            int n2 = UserHandle.getCallingUserId();
            l = Binder.clearCallingIdentity();
            object = this.mAccessibilityManager.getWindowToken(n, n2);
            if (object != null) break block7;
            Binder.restoreCallingIdentity(l);
            return null;
        }
        try {
            object = this.mWindowManager.getWindowContentFrameStats((IBinder)object);
            return object;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void grantRuntimePermission(String string2, String string3, int n) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            this.mPackageManager.grantRuntimePermission(string2, string3, n);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean injectInputEvent(InputEvent inputEvent, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        int n = bl ? 2 : 0;
        long l = Binder.clearCallingIdentity();
        try {
            bl = this.mWindowManager.injectInputAfterTransactionsApplied(inputEvent, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void revokeRuntimePermission(String string2, String string3, int n) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            this.mPackageManager.revokeRuntimePermission(string2, string3, n);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean setRotation(int var1_1) {
        var2_2 = this.mLock;
        // MONITORENTER : var2_2
        this.throwIfCalledByNotTrustedUidLocked();
        this.throwIfShutdownLocked();
        this.throwIfNotConnectedLocked();
        // MONITOREXIT : var2_2
        var3_5 = Binder.clearCallingIdentity();
        if (var1_1 != -2) ** GOTO lbl12
        try {
            this.mWindowManager.thawRotation();
            return true;
lbl12: // 1 sources:
            this.mWindowManager.freezeRotation(var1_1);
            return true;
        }
        catch (RemoteException var2_4) {
            return false;
        }
        finally {
            Binder.restoreCallingIdentity(var3_5);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void shutdown() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.isConnectedLocked()) {
                this.throwIfCalledByNotTrustedUidLocked();
            }
            this.throwIfShutdownLocked();
            this.mIsShutdown = true;
            if (this.isConnectedLocked()) {
                this.disconnect();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void syncInputTransactions() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        try {
            this.mWindowManager.syncInputTransactions();
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bitmap takeScreenshot(Rect parcelable, int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCalledByNotTrustedUidLocked();
            this.throwIfShutdownLocked();
            this.throwIfNotConnectedLocked();
        }
        long l = Binder.clearCallingIdentity();
        try {
            parcelable = SurfaceControl.screenshot(parcelable, parcelable.width(), parcelable.height(), n);
            return parcelable;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public class Repeater
    implements Runnable {
        private final InputStream readFrom;
        private final OutputStream writeTo;

        public Repeater(InputStream inputStream, OutputStream outputStream) {
            this.readFrom = inputStream;
            this.writeTo = outputStream;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                try {
                    int n;
                    byte[] arrby = new byte[8192];
                    while ((n = this.readFrom.read(arrby)) >= 0) {
                        this.writeTo.write(arrby, 0, n);
                        this.writeTo.flush();
                    }
                }
                catch (IOException iOException) {
                    Log.w(UiAutomationConnection.TAG, "Error while reading/writing to streams");
                }
            }
            catch (Throwable throwable2) {}
            IoUtils.closeQuietly((AutoCloseable)this.readFrom);
            IoUtils.closeQuietly((AutoCloseable)this.writeTo);
            return;
            IoUtils.closeQuietly((AutoCloseable)this.readFrom);
            IoUtils.closeQuietly((AutoCloseable)this.writeTo);
            throw throwable2;
        }
    }

}

