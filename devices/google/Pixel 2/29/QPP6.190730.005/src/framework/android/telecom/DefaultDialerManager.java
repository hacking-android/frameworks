/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telecom._$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class DefaultDialerManager {
    private static final String TAG = "DefaultDialerManager";

    private static List<String> filterByIntent(Context object, List<String> list, Intent object2, int n) {
        if (list != null && !list.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            object2 = ((Context)object).getPackageManager().queryIntentActivitiesAsUser((Intent)object2, 0, n);
            int n2 = object2.size();
            for (n = 0; n < n2; ++n) {
                object = ((ResolveInfo)object2.get((int)n)).activityInfo;
                if (object == null || !list.contains(((ActivityInfo)object).packageName) || arrayList.contains(((ActivityInfo)object).packageName)) continue;
                arrayList.add(((ActivityInfo)object).packageName);
            }
            return arrayList;
        }
        return new ArrayList<String>();
    }

    public static String getDefaultDialerApplication(Context context) {
        return DefaultDialerManager.getDefaultDialerApplication(context, context.getUserId());
    }

    public static String getDefaultDialerApplication(Context object, int n) {
        long l = Binder.clearCallingIdentity();
        try {
            object = CollectionUtils.firstOrNull(((Context)object).getSystemService(RoleManager.class).getRoleHoldersAsUser("android.app.role.DIALER", UserHandle.of(n)));
            return object;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static List<String> getInstalledDialerApplications(Context context) {
        return DefaultDialerManager.getInstalledDialerApplications(context, Process.myUserHandle().getIdentifier());
    }

    public static List<String> getInstalledDialerApplications(Context context, int n) {
        Object object = context.getPackageManager();
        Cloneable cloneable = new Intent("android.intent.action.DIAL");
        object = ((PackageManager)object).queryIntentActivitiesAsUser((Intent)cloneable, 0, n);
        cloneable = new ArrayList();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo)iterator.next();
            object = resolveInfo.activityInfo;
            if (object == null || cloneable.contains(((ActivityInfo)object).packageName) || resolveInfo.targetUserId != -2) continue;
            cloneable.add(((ActivityInfo)object).packageName);
        }
        object = new Intent("android.intent.action.DIAL");
        ((Intent)object).setData(Uri.fromParts("tel", "", null));
        return DefaultDialerManager.filterByIntent(context, (List<String>)((Object)cloneable), (Intent)object, n);
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager)context.getSystemService("telecom");
    }

    public static boolean isDefaultOrSystemDialer(Context object, String string2) {
        boolean bl = TextUtils.isEmpty(string2);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (string2.equals(((TelecomManager)(object = DefaultDialerManager.getTelecomManager((Context)object))).getDefaultDialerPackage()) || string2.equals(((TelecomManager)object).getSystemDialerPackage())) {
            bl2 = true;
        }
        return bl2;
    }

    static /* synthetic */ void lambda$setDefaultDialerApplication$0(CompletableFuture completableFuture, Boolean bl) {
        if (bl.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new RuntimeException());
        }
    }

    public static boolean setDefaultDialerApplication(Context context, String string2) {
        return DefaultDialerManager.setDefaultDialerApplication(context, string2, ActivityManager.getCurrentUser());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean setDefaultDialerApplication(Context context, String string2, int n) {
        Throwable throwable2222;
        long l = Binder.clearCallingIdentity();
        CompletableFuture completableFuture = new CompletableFuture();
        _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0 _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0 = new _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0(completableFuture);
        context.getSystemService(RoleManager.class).addRoleHolderAsUser("android.app.role.DIALER", string2, 0, UserHandle.of(n), AsyncTask.THREAD_POOL_EXECUTOR, _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0);
        completableFuture.get(5L, TimeUnit.SECONDS);
        Binder.restoreCallingIdentity(l);
        return true;
        {
            catch (Throwable throwable2222) {
            }
            catch (InterruptedException | ExecutionException | TimeoutException exception) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to set default dialer to ");
                stringBuilder.append(string2);
                stringBuilder.append(" for user ");
                stringBuilder.append(n);
                Slog.e(TAG, stringBuilder.toString(), exception);
            }
            Binder.restoreCallingIdentity(l);
            return false;
        }
        Binder.restoreCallingIdentity(l);
        throw throwable2222;
    }
}

