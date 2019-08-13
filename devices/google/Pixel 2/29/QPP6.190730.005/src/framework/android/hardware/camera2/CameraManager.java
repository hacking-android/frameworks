/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.CameraInfo;
import android.hardware.CameraStatus;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceListener;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2._$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao;
import android.hardware.camera2._$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM;
import android.hardware.camera2._$$Lambda$CameraManager$CameraManagerGlobal$w1y8myi6vgxAcTEs8WArI_NN3R0;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.CameraDeviceUserShim;
import android.hardware.camera2.legacy.LegacyMetadataMapper;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class CameraManager {
    private static final int API_VERSION_1 = 1;
    private static final int API_VERSION_2 = 2;
    private static final int CAMERA_TYPE_ALL = 1;
    private static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    private static final String TAG = "CameraManager";
    private static final int USE_CALLING_UID = -1;
    private final boolean DEBUG;
    private final Context mContext;
    private ArrayList<String> mDeviceIdList;
    private final Object mLock;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CameraManager(Context context) {
        this.DEBUG = false;
        Object object = this.mLock = new Object();
        synchronized (object) {
            this.mContext = context;
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Size getDisplaySize() {
        Size size = new Size(0, 0);
        Object object = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
        int n = ((Display)object).getWidth();
        int n2 = ((Display)object).getHeight();
        int n3 = n;
        int n4 = n2;
        object = new Size(n3, n4);
        if (n2 <= n) return object;
        n4 = n;
        try {
            n3 = ((Display)object).getHeight();
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getDisplaySize Failed. ");
            stringBuilder.append(exception.toString());
            Log.e(TAG, stringBuilder.toString());
        }
        return size;
    }

    public static boolean isHiddenPhysicalCamera(String string2) {
        ICameraService iCameraService;
        block3 : {
            try {
                iCameraService = CameraManagerGlobal.get().getCameraService();
                if (iCameraService != null) break block3;
                return false;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        boolean bl = iCameraService.isHiddenPhysicalCamera(string2);
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private CameraDevice openCameraDeviceUserAsync(String var1_1, CameraDevice.StateCallback var2_5, Executor var3_11, int var4_12) throws CameraAccessException {
        block16 : {
            block20 : {
                block19 : {
                    block18 : {
                        block17 : {
                            var5_13 = this.getCameraCharacteristics((String)var1_1);
                            var6_14 = this.mLock;
                            // MONITORENTER : var6_14
                            var7_15 = null;
                            var8_16 = null;
                            var9_17 = new CameraDeviceImpl((String)var1_1, (CameraDevice.StateCallback)var2_5, (Executor)var3_11, (CameraCharacteristics)var5_13, this.mContext.getApplicationInfo().targetSdkVersion);
                            var5_13 = var9_17.getCallbacks();
                            if (!this.supportsCamera2ApiLocked((String)var1_1)) ** GOTO lbl20
                            var2_5 = CameraManagerGlobal.get().getCameraService();
                            if (var2_5 == null) ** GOTO lbl17
                            var3_11 = this.mContext.getOpPackageName();
                            try {
                                var1_1 = var2_5.connectDevice((ICameraDeviceCallbacks)var5_13, (String)var1_1, (String)var3_11, var4_12);
                                break block16;
lbl17: // 1 sources:
                                var1_1 = new ServiceSpecificException(4, "Camera service is currently unavailable");
                                throw var1_1;
lbl20: // 2 sources:
                                var4_12 = Integer.parseInt((String)var1_1);
                                Log.i("CameraManager", "Using legacy camera HAL.");
                                var1_1 = CameraDeviceUserShim.connectBinderShim((ICameraDeviceCallbacks)var5_13, var4_12, this.getDisplaySize());
                                break block16;
                            }
                            catch (RemoteException var1_2) {
                                break block17;
                            }
                            catch (ServiceSpecificException var2_6) {
                                break block18;
                            }
                            catch (NumberFormatException var2_7) {
                                var3_11 = new StringBuilder();
                                var3_11.append("Expected cameraId to be numeric, but it was: ");
                                var3_11.append((String)var1_1);
                                var2_8 = new IllegalArgumentException(var3_11.toString());
                                throw var2_8;
                            }
                            catch (RemoteException var1_3) {
                                // empty catch block
                            }
                        }
                        var1_1 = new ServiceSpecificException(4, "Camera service is currently unavailable");
                        var9_17.setRemoteFailure((ServiceSpecificException)var1_1);
                        CameraManager.throwAsPublicException((Throwable)var1_1);
                        var1_1 = var7_15;
                        break block16;
                        catch (ServiceSpecificException var2_9) {
                            // empty catch block
                        }
                    }
                    if (var2_10.errorCode == 9) {
                        var1_1 = new AssertionError((Object)"Should've gone down the shim path");
                        throw var1_1;
                    }
                    if (var2_10.errorCode == 7 || var2_10.errorCode == 8 || var2_10.errorCode == 6 || var2_10.errorCode == 4 || var2_10.errorCode == 10) break block19;
                    CameraManager.throwAsPublicException((Throwable)var2_10);
                    var1_1 = var8_16;
                    break block16;
                }
                var9_17.setRemoteFailure((ServiceSpecificException)var2_10);
                if (var2_10.errorCode == 6 || var2_10.errorCode == 4) break block20;
                var1_1 = var8_16;
                if (var2_10.errorCode != 7) break block16;
            }
            CameraManager.throwAsPublicException((Throwable)var2_10);
            var1_1 = var8_16;
        }
        var9_17.setRemoteDevice((ICameraDeviceUser)var1_1);
        // MONITOREXIT : var6_14
        return var9_17;
    }

    private boolean supportsCamera2ApiLocked(String string2) {
        return this.supportsCameraApiLocked(string2, 2);
    }

    private boolean supportsCameraApiLocked(String string2, int n) {
        ICameraService iCameraService;
        block3 : {
            try {
                iCameraService = CameraManagerGlobal.get().getCameraService();
                if (iCameraService != null) break block3;
                return false;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        boolean bl = iCameraService.supportsCameraApi(string2, n);
        return bl;
    }

    public static void throwAsPublicException(Throwable throwable) throws CameraAccessException {
        if (throwable instanceof ServiceSpecificException) {
            int n;
            throwable = (ServiceSpecificException)throwable;
            switch (((ServiceSpecificException)throwable).errorCode) {
                default: {
                    n = 3;
                    break;
                }
                case 9: {
                    n = 1000;
                    break;
                }
                case 8: {
                    n = 5;
                    break;
                }
                case 7: {
                    n = 4;
                    break;
                }
                case 6: {
                    n = 1;
                    break;
                }
                case 4: {
                    n = 2;
                    break;
                }
                case 2: 
                case 3: {
                    throw new IllegalArgumentException(throwable.getMessage(), throwable);
                }
                case 1: {
                    throw new SecurityException(throwable.getMessage(), throwable);
                }
            }
            throw new CameraAccessException(n, throwable.getMessage(), throwable);
        }
        if (!(throwable instanceof DeadObjectException)) {
            if (!(throwable instanceof RemoteException)) {
                if (!(throwable instanceof RuntimeException)) {
                    return;
                }
                throw (RuntimeException)throwable;
            }
            throw new UnsupportedOperationException("An unknown RemoteException was thrown which should never happen.", throwable);
        }
        throw new CameraAccessException(2, "Camera service has died unexpectedly", throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CameraCharacteristics getCameraCharacteristics(String object) throws CameraAccessException {
        Object var2_3 = null;
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            Object object3 = CameraManagerGlobal.get().getCameraService();
            if (object3 == null) {
                object = new CameraAccessException(2, "Camera service is currently unavailable");
                throw object;
            }
            try {
                Size size = this.getDisplaySize();
                if (!CameraManager.isHiddenPhysicalCamera((String)object) && !this.supportsCamera2ApiLocked((String)object)) {
                    int n = Integer.parseInt((String)object);
                    return LegacyMetadataMapper.createCharacteristics(object3.getLegacyParameters(n), object3.getCameraInfo(n), n, size);
                }
                object3 = object3.getCameraCharacteristics((String)object);
                try {
                    ((CameraMetadataNative)object3).setCameraId(Integer.parseInt((String)object));
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to parse camera Id ");
                    stringBuilder.append((String)object);
                    stringBuilder.append(" to integer");
                    Log.e(TAG, stringBuilder.toString());
                }
                ((CameraMetadataNative)object3).setDisplaySize(size);
                return new CameraCharacteristics((CameraMetadataNative)object3);
            }
            catch (RemoteException remoteException) {
                object = new CameraAccessException(2, "Camera service is currently unavailable", remoteException);
                throw object;
            }
            catch (ServiceSpecificException serviceSpecificException) {
                CameraManager.throwAsPublicException(serviceSpecificException);
                return var2_3;
            }
        }
    }

    public String[] getCameraIdList() throws CameraAccessException {
        return CameraManagerGlobal.get().getCameraIdList();
    }

    public void openCamera(String string2, CameraDevice.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        this.openCameraForUid(string2, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), -1);
    }

    public void openCamera(String string2, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        if (executor != null) {
            this.openCameraForUid(string2, stateCallback, executor, -1);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void openCameraForUid(String string2, CameraDevice.StateCallback stateCallback, Executor executor, int n) throws CameraAccessException {
        if (string2 != null) {
            if (stateCallback != null) {
                if (!CameraManagerGlobal.sCameraServiceDisabled) {
                    this.openCameraDeviceUserAsync(string2, stateCallback, executor, n);
                    return;
                }
                throw new IllegalArgumentException("No cameras available on device");
            }
            throw new IllegalArgumentException("callback was null");
        }
        throw new IllegalArgumentException("cameraId was null");
    }

    public void registerAvailabilityCallback(AvailabilityCallback availabilityCallback, Handler handler) {
        CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, CameraDeviceImpl.checkAndWrapHandler(handler));
    }

    public void registerAvailabilityCallback(Executor executor, AvailabilityCallback availabilityCallback) {
        if (executor != null) {
            CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, executor);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void registerTorchCallback(TorchCallback torchCallback, Handler handler) {
        CameraManagerGlobal.get().registerTorchCallback(torchCallback, CameraDeviceImpl.checkAndWrapHandler(handler));
    }

    public void registerTorchCallback(Executor executor, TorchCallback torchCallback) {
        if (executor != null) {
            CameraManagerGlobal.get().registerTorchCallback(torchCallback, executor);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void setTorchMode(String string2, boolean bl) throws CameraAccessException {
        if (!CameraManagerGlobal.sCameraServiceDisabled) {
            CameraManagerGlobal.get().setTorchMode(string2, bl);
            return;
        }
        throw new IllegalArgumentException("No cameras available on device");
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback availabilityCallback) {
        CameraManagerGlobal.get().unregisterAvailabilityCallback(availabilityCallback);
    }

    public void unregisterTorchCallback(TorchCallback torchCallback) {
        CameraManagerGlobal.get().unregisterTorchCallback(torchCallback);
    }

    public static abstract class AvailabilityCallback {
        public void onCameraAccessPrioritiesChanged() {
        }

        public void onCameraAvailable(String string2) {
        }

        public void onCameraUnavailable(String string2) {
        }
    }

    private static final class CameraManagerGlobal
    extends ICameraServiceListener.Stub
    implements IBinder.DeathRecipient {
        private static final String CAMERA_SERVICE_BINDER_NAME = "media.camera";
        private static final String TAG = "CameraManagerGlobal";
        private static final CameraManagerGlobal gCameraManager = new CameraManagerGlobal();
        public static final boolean sCameraServiceDisabled = SystemProperties.getBoolean("config.disable_cameraservice", false);
        private final int CAMERA_SERVICE_RECONNECT_DELAY_MS;
        private final boolean DEBUG;
        private final ArrayMap<AvailabilityCallback, Executor> mCallbackMap = new ArrayMap();
        private ICameraService mCameraService;
        private final ArrayMap<String, Integer> mDeviceStatus = new ArrayMap();
        private final Object mLock = new Object();
        private final ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
        private final ArrayMap<TorchCallback, Executor> mTorchCallbackMap = new ArrayMap();
        private Binder mTorchClientBinder = new Binder();
        private final ArrayMap<String, Integer> mTorchStatus = new ArrayMap();

        private CameraManagerGlobal() {
            this.DEBUG = false;
            this.CAMERA_SERVICE_RECONNECT_DELAY_MS = 1000;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void connectCameraServiceLocked() {
            if (this.mCameraService != null) return;
            if (sCameraServiceDisabled) {
                return;
            }
            Log.i(TAG, "Connecting to camera service");
            Object object = ServiceManager.getService(CAMERA_SERVICE_BINDER_NAME);
            if (object == null) {
                return;
            }
            int n = 0;
            object.linkToDeath(this, 0);
            object = ICameraService.Stub.asInterface((IBinder)object);
            try {
                CameraMetadataNative.setupGlobalVendorTagDescriptor();
            }
            catch (ServiceSpecificException serviceSpecificException) {
                this.handleRecoverableSetupErrors(serviceSpecificException);
            }
            try {
                CameraStatus[] arrcameraStatus = object.addListener(this);
                int n2 = arrcameraStatus.length;
                while (n < n2) {
                    CameraStatus cameraStatus = arrcameraStatus[n];
                    this.onStatusChangedLocked(cameraStatus.status, cameraStatus.cameraId);
                    ++n;
                }
                this.mCameraService = object;
                return;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
            catch (ServiceSpecificException serviceSpecificException) {
                throw new IllegalStateException("Failed to register a camera service listener", serviceSpecificException);
            }
            catch (RemoteException remoteException) {
                return;
            }
        }

        public static CameraManagerGlobal get() {
            return gCameraManager;
        }

        private void handleRecoverableSetupErrors(ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 4) {
                Log.w(TAG, serviceSpecificException.getMessage());
                return;
            }
            throw new IllegalStateException(serviceSpecificException);
        }

        private boolean isAvailable(int n) {
            return n == 1;
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$0(TorchCallback torchCallback, String string2, int n) {
            boolean bl = n == 2;
            torchCallback.onTorchModeChanged(string2, bl);
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$1(TorchCallback torchCallback, String string2) {
            torchCallback.onTorchModeUnavailable(string2);
        }

        private void onStatusChangedLocked(int n, String string2) {
            if (!this.validStatus(n)) {
                Log.e(TAG, String.format("Ignoring invalid device %s status 0x%x", string2, n));
                return;
            }
            Object object = n == 0 ? this.mDeviceStatus.remove(string2) : this.mDeviceStatus.put(string2, n);
            if (object != null && (Integer)object == n) {
                return;
            }
            if (object != null && this.isAvailable(n) == this.isAvailable((Integer)object)) {
                return;
            }
            int n2 = this.mCallbackMap.size();
            for (int i = 0; i < n2; ++i) {
                object = this.mCallbackMap.valueAt(i);
                this.postSingleUpdate(this.mCallbackMap.keyAt(i), (Executor)object, string2, n);
            }
        }

        private void onTorchStatusChangedLocked(int n, String string2) {
            if (!this.validTorchStatus(n)) {
                Log.e(TAG, String.format("Ignoring invalid device %s torch status 0x%x", string2, n));
                return;
            }
            Object object = this.mTorchStatus.put(string2, n);
            if (object != null && (Integer)object == n) {
                return;
            }
            int n2 = this.mTorchCallbackMap.size();
            for (int i = 0; i < n2; ++i) {
                object = this.mTorchCallbackMap.valueAt(i);
                this.postSingleTorchUpdate(this.mTorchCallbackMap.keyAt(i), (Executor)object, string2, n);
            }
        }

        private void postSingleAccessPriorityChangeUpdate(final AvailabilityCallback availabilityCallback, Executor executor) {
            long l = Binder.clearCallingIdentity();
            try {
                Runnable runnable = new Runnable(){

                    @Override
                    public void run() {
                        availabilityCallback.onCameraAccessPrioritiesChanged();
                    }
                };
                executor.execute(runnable);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        private void postSingleTorchUpdate(TorchCallback torchCallback, Executor executor, String string2, int n) {
            long l;
            if (n != 1 && n != 2) {
                long l2 = Binder.clearCallingIdentity();
                try {
                    _$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao _$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao = new _$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao(torchCallback, string2);
                    executor.execute(_$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao);
                }
                finally {
                    Binder.restoreCallingIdentity(l2);
                }
            } else {
                l = Binder.clearCallingIdentity();
                _$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM _$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM = new _$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM(torchCallback, string2, n);
                executor.execute(_$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM);
            }
            return;
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        private void postSingleUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String string2, int n) {
            long l;
            if (this.isAvailable(n)) {
                long l2 = Binder.clearCallingIdentity();
                try {
                    Runnable runnable = new Runnable(){

                        @Override
                        public void run() {
                            availabilityCallback.onCameraAvailable(string2);
                        }
                    };
                    executor.execute(runnable);
                }
                finally {
                    Binder.restoreCallingIdentity(l2);
                }
            } else {
                l = Binder.clearCallingIdentity();
                Runnable runnable = new Runnable(){

                    @Override
                    public void run() {
                        availabilityCallback.onCameraUnavailable(string2);
                    }
                };
                executor.execute(runnable);
            }
            return;
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        private void scheduleCameraServiceReconnectionLocked() {
            if (this.mCallbackMap.isEmpty() && this.mTorchCallbackMap.isEmpty()) {
                return;
            }
            try {
                ScheduledExecutorService scheduledExecutorService = this.mScheduler;
                _$$Lambda$CameraManager$CameraManagerGlobal$w1y8myi6vgxAcTEs8WArI_NN3R0 _$$Lambda$CameraManager$CameraManagerGlobal$w1y8myi6vgxAcTEs8WArI_NN3R0 = new _$$Lambda$CameraManager$CameraManagerGlobal$w1y8myi6vgxAcTEs8WArI_NN3R0(this);
                scheduledExecutorService.schedule(_$$Lambda$CameraManager$CameraManagerGlobal$w1y8myi6vgxAcTEs8WArI_NN3R0, 1000L, TimeUnit.MILLISECONDS);
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to schedule camera service re-connect: ");
                stringBuilder.append(rejectedExecutionException);
                Log.e(TAG, stringBuilder.toString());
            }
        }

        private void updateCallbackLocked(AvailabilityCallback availabilityCallback, Executor executor) {
            for (int i = 0; i < this.mDeviceStatus.size(); ++i) {
                this.postSingleUpdate(availabilityCallback, executor, this.mDeviceStatus.keyAt(i), this.mDeviceStatus.valueAt(i));
            }
        }

        private void updateTorchCallbackLocked(TorchCallback torchCallback, Executor executor) {
            for (int i = 0; i < this.mTorchStatus.size(); ++i) {
                this.postSingleTorchUpdate(torchCallback, executor, this.mTorchStatus.keyAt(i), this.mTorchStatus.valueAt(i));
            }
        }

        private boolean validStatus(int n) {
            return n == -2 || n == 0 || n == 1 || n == 2;
        }

        private boolean validTorchStatus(int n) {
            return n == 0 || n == 1 || n == 2;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void binderDied() {
            Object object = this.mLock;
            synchronized (object) {
                int n;
                if (this.mCameraService == null) {
                    return;
                }
                this.mCameraService = null;
                for (n = 0; n < this.mDeviceStatus.size(); ++n) {
                    this.onStatusChangedLocked(0, this.mDeviceStatus.keyAt(n));
                }
                n = 0;
                do {
                    if (n >= this.mTorchStatus.size()) {
                        this.scheduleCameraServiceReconnectionLocked();
                        return;
                    }
                    this.onTorchStatusChangedLocked(0, this.mTorchStatus.keyAt(n));
                    ++n;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String[] getCameraIdList() {
            Object object = this.mLock;
            synchronized (object) {
                int n;
                int n2;
                this.connectCameraServiceLocked();
                int n3 = 0;
                int n4 = 0;
                for (n2 = 0; n2 < this.mDeviceStatus.size(); ++n2) {
                    int n5 = this.mDeviceStatus.valueAt(n2);
                    n = n3;
                    if (n5 != 0) {
                        n = n5 == 2 ? n3 : n3 + 1;
                    }
                    n3 = n;
                }
                String[] arrstring = new String[n3];
                n = 0;
                n2 = n4;
                do {
                    if (n2 >= this.mDeviceStatus.size()) {
                        // MONITOREXIT [4, 6, 10] lbl18 : MonitorExitStatement: MONITOREXIT : var1_1
                        Arrays.sort(arrstring, new Comparator<String>(){

                            @Override
                            public int compare(String string2, String string3) {
                                int n;
                                int n2;
                                try {
                                    n2 = Integer.parseInt(string2);
                                }
                                catch (NumberFormatException numberFormatException) {
                                    n2 = -1;
                                }
                                try {
                                    n = Integer.parseInt(string3);
                                }
                                catch (NumberFormatException numberFormatException) {
                                    n = -1;
                                }
                                if (n2 >= 0 && n >= 0) {
                                    return n2 - n;
                                }
                                if (n2 >= 0) {
                                    return -1;
                                }
                                if (n >= 0) {
                                    return 1;
                                }
                                return string2.compareTo(string3);
                            }
                        });
                        return arrstring;
                    }
                    n4 = this.mDeviceStatus.valueAt(n2);
                    n3 = n;
                    if (n4 != 0) {
                        if (n4 == 2) {
                            n3 = n;
                        } else {
                            arrstring[n] = this.mDeviceStatus.keyAt(n2);
                            n3 = n + 1;
                        }
                    }
                    ++n2;
                    n = n3;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ICameraService getCameraService() {
            Object object = this.mLock;
            synchronized (object) {
                this.connectCameraServiceLocked();
                if (this.mCameraService != null) return this.mCameraService;
                if (sCameraServiceDisabled) return this.mCameraService;
                Log.e(TAG, "Camera service is unavailable");
                return this.mCameraService;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$scheduleCameraServiceReconnectionLocked$2$CameraManager$CameraManagerGlobal() {
            if (this.getCameraService() != null) return;
            Object object = this.mLock;
            synchronized (object) {
                this.scheduleCameraServiceReconnectionLocked();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCameraAccessPrioritiesChanged() {
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mCallbackMap.size();
                int n2 = 0;
                while (n2 < n) {
                    Executor executor = this.mCallbackMap.valueAt(n2);
                    this.postSingleAccessPriorityChangeUpdate(this.mCallbackMap.keyAt(n2), executor);
                    ++n2;
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
        public void onStatusChanged(int n, String string2) throws RemoteException {
            Object object = this.mLock;
            synchronized (object) {
                this.onStatusChangedLocked(n, string2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onTorchStatusChanged(int n, String string2) throws RemoteException {
            Object object = this.mLock;
            synchronized (object) {
                this.onTorchStatusChangedLocked(n, string2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void registerAvailabilityCallback(AvailabilityCallback availabilityCallback, Executor executor) {
            Object object = this.mLock;
            synchronized (object) {
                this.connectCameraServiceLocked();
                if (this.mCallbackMap.put(availabilityCallback, executor) == null) {
                    this.updateCallbackLocked(availabilityCallback, executor);
                }
                if (this.mCameraService == null) {
                    this.scheduleCameraServiceReconnectionLocked();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void registerTorchCallback(TorchCallback torchCallback, Executor executor) {
            Object object = this.mLock;
            synchronized (object) {
                this.connectCameraServiceLocked();
                if (this.mTorchCallbackMap.put(torchCallback, executor) == null) {
                    this.updateTorchCallbackLocked(torchCallback, executor);
                }
                if (this.mCameraService == null) {
                    this.scheduleCameraServiceReconnectionLocked();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setTorchMode(String object, boolean bl) throws CameraAccessException {
            Object object2 = this.mLock;
            synchronized (object2) {
                Throwable throwable2;
                if (object != null) {
                    try {
                        ICameraService iCameraService = this.getCameraService();
                        if (iCameraService == null) {
                            object = new CameraAccessException(2, "Camera service is currently unavailable");
                            throw object;
                        }
                        try {
                            iCameraService.setTorchMode((String)object, bl, this.mTorchClientBinder);
                        }
                        catch (RemoteException remoteException) {
                            CameraAccessException cameraAccessException = new CameraAccessException(2, "Camera service is currently unavailable");
                            throw cameraAccessException;
                        }
                        catch (ServiceSpecificException serviceSpecificException) {
                            CameraManager.throwAsPublicException(serviceSpecificException);
                        }
                        return;
                    }
                    catch (Throwable throwable2) {}
                } else {
                    object = new IllegalArgumentException("cameraId was null");
                    throw object;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void unregisterAvailabilityCallback(AvailabilityCallback availabilityCallback) {
            Object object = this.mLock;
            synchronized (object) {
                this.mCallbackMap.remove(availabilityCallback);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void unregisterTorchCallback(TorchCallback torchCallback) {
            Object object = this.mLock;
            synchronized (object) {
                this.mTorchCallbackMap.remove(torchCallback);
                return;
            }
        }

    }

    public static abstract class TorchCallback {
        public void onTorchModeChanged(String string2, boolean bl) {
        }

        public void onTorchModeUnavailable(String string2) {
        }
    }

}

