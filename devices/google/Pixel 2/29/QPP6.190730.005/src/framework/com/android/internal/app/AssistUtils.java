/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.app.IVoiceActionCheckCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AssistUtils {
    private static final String TAG = "AssistUtils";
    private final Context mContext;
    private final IVoiceInteractionManagerService mVoiceInteractionManagerService;

    @UnsupportedAppUsage
    public AssistUtils(Context context) {
        this.mContext = context;
        this.mVoiceInteractionManagerService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService("voiceinteraction"));
    }

    public static boolean allowDisablingAssistDisclosure(Context context) {
        return context.getResources().getBoolean(17891343);
    }

    private static boolean isDisclosureEnabled(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.Secure.getInt((ContentResolver)object, "assist_disclosure_enabled", 0) != 0) {
            bl = true;
        }
        return bl;
    }

    public static boolean isPreinstalledAssistant(Context object, ComponentName componentName) {
        boolean bl = false;
        if (componentName == null) {
            return false;
        }
        try {
            object = ((Context)object).getPackageManager().getApplicationInfo(componentName.getPackageName(), 0);
            if (((ApplicationInfo)object).isSystemApp() || ((ApplicationInfo)object).isUpdatedSystemApp()) {
                bl = true;
            }
            return bl;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public static boolean shouldDisclose(Context context, ComponentName componentName) {
        boolean bl = AssistUtils.allowDisablingAssistDisclosure(context);
        boolean bl2 = true;
        if (!bl) {
            return true;
        }
        bl = bl2;
        if (!AssistUtils.isDisclosureEnabled(context)) {
            bl = !AssistUtils.isPreinstalledAssistant(context, componentName) ? bl2 : false;
        }
        return bl;
    }

    public boolean activeServiceSupportsAssistGesture() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.mVoiceInteractionManagerService != null && (bl2 = this.mVoiceInteractionManagerService.activeServiceSupportsAssist())) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call activeServiceSupportsAssistGesture", remoteException);
            return false;
        }
    }

    public boolean activeServiceSupportsLaunchFromKeyguard() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.mVoiceInteractionManagerService != null && (bl2 = this.mVoiceInteractionManagerService.activeServiceSupportsLaunchFromKeyguard())) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call activeServiceSupportsLaunchFromKeyguard", remoteException);
            return false;
        }
    }

    public ComponentName getActiveServiceComponentName() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                ComponentName componentName = this.mVoiceInteractionManagerService.getActiveServiceComponentName();
                return componentName;
            }
            return null;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call getActiveServiceComponentName", remoteException);
            return null;
        }
    }

    public void getActiveServiceSupportedActions(Set<String> set, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
                ArrayList<String> arrayList = new ArrayList<String>(set);
                iVoiceInteractionManagerService.getActiveServiceSupportedActions(arrayList, iVoiceActionCheckCallback);
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call activeServiceSupportedActions", remoteException);
            try {
                iVoiceActionCheckCallback.onComplete(null);
            }
            catch (RemoteException remoteException2) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    public ComponentName getAssistComponentForUser(int n) {
        String string2 = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "assistant", n);
        if (string2 != null) {
            return ComponentName.unflattenFromString(string2);
        }
        return null;
    }

    public void hideCurrentSession() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.hideCurrentSession();
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call hideCurrentSession", remoteException);
        }
    }

    public boolean isSessionRunning() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.mVoiceInteractionManagerService != null && (bl2 = this.mVoiceInteractionManagerService.isSessionRunning())) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call isSessionRunning", remoteException);
            return false;
        }
    }

    public void launchVoiceAssistFromKeyguard() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.launchVoiceAssistFromKeyguard();
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call launchVoiceAssistFromKeyguard", remoteException);
        }
    }

    public void onLockscreenShown() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.onLockscreenShown();
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call onLockscreenShown", remoteException);
        }
    }

    public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.registerVoiceInteractionSessionListener(iVoiceInteractionSessionListener);
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to register voice interaction listener", remoteException);
        }
    }

    public boolean showSessionForActiveService(Bundle bundle, int n, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                boolean bl = this.mVoiceInteractionManagerService.showSessionForActiveService(bundle, n, iVoiceInteractionSessionShowCallback, iBinder);
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to call showSessionForActiveService", remoteException);
        }
        return false;
    }
}

