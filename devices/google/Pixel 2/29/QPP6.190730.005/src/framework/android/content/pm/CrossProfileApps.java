/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ICrossProfileApps;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.internal.util.UserIcons;
import java.util.List;

public class CrossProfileApps {
    private final Context mContext;
    private final Resources mResources;
    private final ICrossProfileApps mService;
    private final UserManager mUserManager;

    public CrossProfileApps(Context context, ICrossProfileApps iCrossProfileApps) {
        this.mContext = context;
        this.mService = iCrossProfileApps;
        this.mUserManager = context.getSystemService(UserManager.class);
        this.mResources = context.getResources();
    }

    private void verifyCanAccessUser(UserHandle userHandle) {
        if (this.getTargetUserProfiles().contains(userHandle)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not allowed to access ");
        stringBuilder.append(userHandle);
        throw new SecurityException(stringBuilder.toString());
    }

    public Drawable getProfileSwitchingIconDrawable(UserHandle userHandle) {
        this.verifyCanAccessUser(userHandle);
        if (this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
            return this.mResources.getDrawable(17302360, null);
        }
        return UserIcons.getDefaultUserIcon(this.mResources, 0, true);
    }

    public CharSequence getProfileSwitchingLabel(UserHandle userHandle) {
        this.verifyCanAccessUser(userHandle);
        int n = this.mUserManager.isManagedProfile(userHandle.getIdentifier()) ? 17040299 : 17041183;
        return this.mResources.getString(n);
    }

    public List<UserHandle> getTargetUserProfiles() {
        try {
            List<UserHandle> list = this.mService.getTargetUserProfiles(this.mContext.getPackageName());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(ComponentName componentName, UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), componentName, userHandle.getIdentifier(), false);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(ComponentName componentName, UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), componentName, userHandle.getIdentifier(), true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

