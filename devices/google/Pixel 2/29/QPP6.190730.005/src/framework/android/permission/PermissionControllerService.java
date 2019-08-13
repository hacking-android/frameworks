/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.UserHandle;
import android.permission.IPermissionController;
import android.permission.RuntimePermissionPresentationInfo;
import android.permission.RuntimePermissionUsageInfo;
import android.permission._$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs;
import android.permission._$$Lambda$PermissionControllerService$1$ROtJOrojS2cjqvX59tSprAvs_1o;
import android.permission._$$Lambda$PermissionControllerService$1$Sp35OTwahalQfZumoUDJ70lCKe0;
import android.permission._$$Lambda$PermissionControllerService$1$__ZsT0Jo3iLdGM0gy2UV6ea_oEw;
import android.permission._$$Lambda$PermissionControllerService$1$aoBUJn0rgfJAYfvz7rYL8N9wr_Y;
import android.permission._$$Lambda$PermissionControllerService$1$byERALVqclrc25diZo2Ly0OtfwI;
import android.permission._$$Lambda$PermissionControllerService$1$i3vGLgbFSsM1LDWQDjRkXStMIUE;
import android.permission._$$Lambda$PermissionControllerService$1$oEdK7RdXzZpRIDF40ujz7uvW1Ts;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

@SystemApi
public abstract class PermissionControllerService
extends Service {
    private static final String LOG_TAG = PermissionControllerService.class.getSimpleName();
    public static final String SERVICE_INTERFACE = "android.permission.PermissionControllerService";

    @Override
    public final IBinder onBind(Intent intent) {
        return new IPermissionController.Stub(){

            private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
                if (throwable != null) {
                    try {
                        autoCloseable.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    autoCloseable.close();
                }
            }

            static /* synthetic */ void lambda$countPermissionApps$3(RemoteCallback remoteCallback, int n) {
                Bundle bundle = new Bundle();
                bundle.putInt("android.permission.PermissionControllerManager.key.result", n);
                remoteCallback.sendResult(bundle);
            }

            static /* synthetic */ void lambda$getAppPermissions$2(RemoteCallback remoteCallback, List list) {
                if (list != null && !list.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableList("android.permission.PermissionControllerManager.key.result", list);
                    remoteCallback.sendResult(bundle);
                } else {
                    remoteCallback.sendResult(null);
                }
            }

            static /* synthetic */ void lambda$getPermissionUsages$4(RemoteCallback remoteCallback, List list) {
                if (list != null && !list.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableList("android.permission.PermissionControllerManager.key.result", list);
                    remoteCallback.sendResult(bundle);
                } else {
                    remoteCallback.sendResult(null);
                }
            }

            static /* synthetic */ void lambda$grantOrUpgradeDefaultRuntimePermissions$6(RemoteCallback remoteCallback) {
                remoteCallback.sendResult(Bundle.EMPTY);
            }

            static /* synthetic */ void lambda$restoreDelayedRuntimePermissionBackup$1(RemoteCallback remoteCallback, Boolean bl) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("android.permission.PermissionControllerManager.key.result", bl);
                remoteCallback.sendResult(bundle);
            }

            static /* synthetic */ void lambda$revokeRuntimePermissions$0(RemoteCallback remoteCallback, Map object) {
                Preconditions.checkNotNull(object);
                Bundle bundle = new Bundle();
                for (Map.Entry entry : object.entrySet()) {
                    Preconditions.checkNotNull((String)entry.getKey());
                    Preconditions.checkCollectionElementsNotNull((List)entry.getValue(), "permissions");
                    bundle.putStringArrayList((String)entry.getKey(), new ArrayList<String>((Collection)entry.getValue()));
                }
                object = new Bundle();
                ((Bundle)object).putBundle("android.permission.PermissionControllerManager.key.result", bundle);
                remoteCallback.sendResult((Bundle)object);
            }

            static /* synthetic */ void lambda$setRuntimePermissionGrantStateByDeviceAdmin$5(RemoteCallback remoteCallback, Boolean bl) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("android.permission.PermissionControllerManager.key.result", bl);
                remoteCallback.sendResult(bundle);
            }

            @Override
            public void countPermissionApps(List<String> list, int n, RemoteCallback remoteCallback) {
                Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
                Preconditions.checkFlagsArgument(n, 3);
                Preconditions.checkNotNull(remoteCallback, "callback");
                PermissionControllerService.this.enforceCallingPermission("android.permission.GET_RUNTIME_PERMISSIONS", null);
                PermissionControllerService.this.onCountPermissionApps(list, n, new _$$Lambda$PermissionControllerService$1$i3vGLgbFSsM1LDWQDjRkXStMIUE(remoteCallback));
            }

            @Override
            public void getAppPermissions(String string2, RemoteCallback remoteCallback) {
                Preconditions.checkNotNull(string2, "packageName");
                Preconditions.checkNotNull(remoteCallback, "callback");
                PermissionControllerService.this.enforceCallingPermission("android.permission.GET_RUNTIME_PERMISSIONS", null);
                PermissionControllerService.this.onGetAppPermissions(string2, new _$$Lambda$PermissionControllerService$1$ROtJOrojS2cjqvX59tSprAvs_1o(remoteCallback));
            }

            @Override
            public void getPermissionUsages(boolean bl, long l, RemoteCallback remoteCallback) {
                Preconditions.checkArgumentNonnegative(l);
                Preconditions.checkNotNull(remoteCallback, "callback");
                PermissionControllerService.this.enforceCallingPermission("android.permission.GET_RUNTIME_PERMISSIONS", null);
                PermissionControllerService.this.onGetPermissionUsages(bl, l, new _$$Lambda$PermissionControllerService$1$oEdK7RdXzZpRIDF40ujz7uvW1Ts(remoteCallback));
            }

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor object) {
                Preconditions.checkNotNull(userHandle);
                Preconditions.checkNotNull(object);
                PermissionControllerService.this.enforceCallingPermission("android.permission.GET_RUNTIME_PERMISSIONS", null);
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor)object);
                CountDownLatch countDownLatch = new CountDownLatch(1);
                object = PermissionControllerService.this;
                Objects.requireNonNull(countDownLatch);
                _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs = new _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs(countDownLatch);
                ((PermissionControllerService)object).onGetRuntimePermissionsBackup(userHandle, autoCloseOutputStream, _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs);
                countDownLatch.await();
                1.$closeResource(null, autoCloseOutputStream);
                return;
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        try {
                            1.$closeResource(throwable, autoCloseOutputStream);
                            throw throwable2;
                        }
                        catch (InterruptedException interruptedException) {
                            Log.e(LOG_TAG, "getRuntimePermissionBackup timed out", interruptedException);
                            return;
                        }
                        catch (IOException iOException) {
                            Log.e(LOG_TAG, "Could not open pipe to write backup to", iOException);
                        }
                    }
                }
            }

            @Override
            public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback remoteCallback) {
                Preconditions.checkNotNull(remoteCallback, "callback");
                PermissionControllerService.this.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", null);
                PermissionControllerService.this.onGrantOrUpgradeDefaultRuntimePermissions(new _$$Lambda$PermissionControllerService$1$aoBUJn0rgfJAYfvz7rYL8N9wr_Y(remoteCallback));
            }

            @Override
            public void restoreDelayedRuntimePermissionBackup(String string2, UserHandle userHandle, RemoteCallback remoteCallback) {
                Preconditions.checkNotNull(string2);
                Preconditions.checkNotNull(userHandle);
                Preconditions.checkNotNull(remoteCallback);
                PermissionControllerService.this.enforceCallingPermission("android.permission.GRANT_RUNTIME_PERMISSIONS", null);
                PermissionControllerService.this.onRestoreDelayedRuntimePermissionsBackup(string2, userHandle, new _$$Lambda$PermissionControllerService$1$byERALVqclrc25diZo2Ly0OtfwI(remoteCallback));
            }

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void restoreRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor object) {
                Preconditions.checkNotNull(userHandle);
                Preconditions.checkNotNull(object);
                PermissionControllerService.this.enforceCallingPermission("android.permission.GRANT_RUNTIME_PERMISSIONS", null);
                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor)object);
                CountDownLatch countDownLatch = new CountDownLatch(1);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(countDownLatch);
                object = new _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs(countDownLatch);
                permissionControllerService.onRestoreRuntimePermissionsBackup(userHandle, autoCloseInputStream, (Runnable)object);
                countDownLatch.await();
                1.$closeResource(null, autoCloseInputStream);
                return;
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        try {
                            1.$closeResource(throwable, autoCloseInputStream);
                            throw throwable2;
                        }
                        catch (InterruptedException interruptedException) {
                            Log.e(LOG_TAG, "restoreRuntimePermissionBackup timed out", interruptedException);
                            return;
                        }
                        catch (IOException iOException) {
                            Log.e(LOG_TAG, "Could not open pipe to read backup from", iOException);
                        }
                    }
                }
            }

            @Override
            public void revokeRuntimePermission(String string2, String string3) {
                Preconditions.checkNotNull(string2, "packageName");
                Preconditions.checkNotNull(string3, "permissionName");
                PermissionControllerService.this.enforceCallingPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", null);
                CountDownLatch countDownLatch = new CountDownLatch(1);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(countDownLatch);
                permissionControllerService.onRevokeRuntimePermission(string2, string3, new _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs(countDownLatch));
                try {
                    countDownLatch.await();
                }
                catch (InterruptedException interruptedException) {
                    Log.e(LOG_TAG, "revokeRuntimePermission timed out", interruptedException);
                }
            }

            @Override
            public void revokeRuntimePermissions(Bundle object, boolean bl, int n, String string2, RemoteCallback remoteCallback) {
                boolean bl2;
                ArrayMap<String, List<String>> arrayMap;
                block5 : {
                    Preconditions.checkNotNull(object, "bundleizedRequest");
                    Preconditions.checkNotNull(string2);
                    Preconditions.checkNotNull(remoteCallback);
                    arrayMap = new ArrayMap<String, List<String>>();
                    for (String string3 : ((BaseBundle)object).keySet()) {
                        Preconditions.checkNotNull(string3);
                        ArrayList<String> arrayList = ((Bundle)object).getStringArrayList(string3);
                        Preconditions.checkCollectionElementsNotNull(arrayList, "permissions");
                        arrayMap.put(string3, arrayList);
                    }
                    PermissionControllerService.this.enforceCallingPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", null);
                    try {
                        object = PermissionControllerService.this.getPackageManager();
                        bl2 = false;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        throw new RuntimeException(nameNotFoundException);
                    }
                    object = ((PackageManager)object).getPackageInfo(string2, 0);
                    if (1.getCallingUid() != object.applicationInfo.uid) break block5;
                    bl2 = true;
                }
                Preconditions.checkArgument(bl2);
                PermissionControllerService.this.onRevokeRuntimePermissions(arrayMap, bl, n, string2, new _$$Lambda$PermissionControllerService$1$__ZsT0Jo3iLdGM0gy2UV6ea_oEw(remoteCallback));
            }

            @Override
            public void setRuntimePermissionGrantStateByDeviceAdmin(String string2, String string3, String string4, int n, RemoteCallback remoteCallback) {
                boolean bl;
                Preconditions.checkStringNotEmpty(string2);
                Preconditions.checkStringNotEmpty(string3);
                Preconditions.checkStringNotEmpty(string4);
                boolean bl2 = bl = true;
                if (n != 1) {
                    bl2 = bl;
                    if (n != 2) {
                        bl2 = n == 0 ? bl : false;
                    }
                }
                Preconditions.checkArgument(bl2);
                Preconditions.checkNotNull(remoteCallback);
                if (n == 2) {
                    PermissionControllerService.this.enforceCallingPermission("android.permission.GRANT_RUNTIME_PERMISSIONS", null);
                }
                if (n == 2) {
                    PermissionControllerService.this.enforceCallingPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", null);
                }
                PermissionControllerService.this.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", null);
                PermissionControllerService.this.onSetRuntimePermissionGrantStateByDeviceAdmin(string2, string3, string4, n, new _$$Lambda$PermissionControllerService$1$Sp35OTwahalQfZumoUDJ70lCKe0(remoteCallback));
            }
        };
    }

    public abstract void onCountPermissionApps(List<String> var1, int var2, IntConsumer var3);

    public abstract void onGetAppPermissions(String var1, Consumer<List<RuntimePermissionPresentationInfo>> var2);

    public abstract void onGetPermissionUsages(boolean var1, long var2, Consumer<List<RuntimePermissionUsageInfo>> var4);

    public abstract void onGetRuntimePermissionsBackup(UserHandle var1, OutputStream var2, Runnable var3);

    public abstract void onGrantOrUpgradeDefaultRuntimePermissions(Runnable var1);

    public abstract void onRestoreDelayedRuntimePermissionsBackup(String var1, UserHandle var2, Consumer<Boolean> var3);

    public abstract void onRestoreRuntimePermissionsBackup(UserHandle var1, InputStream var2, Runnable var3);

    public abstract void onRevokeRuntimePermission(String var1, String var2, Runnable var3);

    public abstract void onRevokeRuntimePermissions(Map<String, List<String>> var1, boolean var2, int var3, String var4, Consumer<Map<String, List<String>>> var5);

    public abstract void onSetRuntimePermissionGrantStateByDeviceAdmin(String var1, String var2, String var3, int var4, Consumer<Boolean> var5);

}

