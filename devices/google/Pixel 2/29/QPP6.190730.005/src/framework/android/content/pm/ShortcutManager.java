/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IShortcutService;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;

public class ShortcutManager {
    private static final String TAG = "ShortcutManager";
    private final Context mContext;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final IShortcutService mService;

    public ShortcutManager(Context context) {
        this(context, IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut")));
    }

    public ShortcutManager(Context context, IShortcutService iShortcutService) {
        this.mContext = context;
        this.mService = iShortcutService;
    }

    public boolean addDynamicShortcuts(List<ShortcutInfo> list) {
        try {
            IShortcutService iShortcutService = this.mService;
            String string2 = this.mContext.getPackageName();
            ParceledListSlice<ShortcutInfo> parceledListSlice = new ParceledListSlice<ShortcutInfo>(list);
            boolean bl = iShortcutService.addDynamicShortcuts(string2, parceledListSlice, this.injectMyUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Intent createShortcutResultIntent(ShortcutInfo parcelable) {
        try {
            parcelable = this.mService.createShortcutResultIntent(this.mContext.getPackageName(), (ShortcutInfo)parcelable, this.injectMyUserId());
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, 0, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list, int n) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, n, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list, CharSequence charSequence) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, charSequence, 0, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list, String string2) {
        this.disableShortcuts(list, (CharSequence)string2);
    }

    public void enableShortcuts(List<String> list) {
        try {
            this.mService.enableShortcuts(this.mContext.getPackageName(), list, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getDynamicShortcuts() {
        try {
            List list = this.mService.getDynamicShortcuts(this.mContext.getPackageName(), this.injectMyUserId()).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getIconMaxHeight() {
        try {
            int n = this.mService.getIconMaxDimensions(this.mContext.getPackageName(), this.injectMyUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getIconMaxWidth() {
        try {
            int n = this.mService.getIconMaxDimensions(this.mContext.getPackageName(), this.injectMyUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getManifestShortcuts() {
        try {
            List list = this.mService.getManifestShortcuts(this.mContext.getPackageName(), this.injectMyUserId()).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getMaxShortcutCountForActivity() {
        return this.getMaxShortcutCountPerActivity();
    }

    public int getMaxShortcutCountPerActivity() {
        try {
            int n = this.mService.getMaxShortcutCountPerActivity(this.mContext.getPackageName(), this.injectMyUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getPinnedShortcuts() {
        try {
            List list = this.mService.getPinnedShortcuts(this.mContext.getPackageName(), this.injectMyUserId()).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getRateLimitResetTime() {
        try {
            long l = this.mService.getRateLimitResetTime(this.mContext.getPackageName(), this.injectMyUserId());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getRemainingCallCount() {
        try {
            int n = this.mService.getRemainingCallCount(this.mContext.getPackageName(), this.injectMyUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<ShareShortcutInfo> getShareTargets(IntentFilter object) {
        try {
            object = this.mService.getShareTargets(this.mContext.getPackageName(), (IntentFilter)object, this.injectMyUserId()).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean hasShareTargets(String string2) {
        try {
            boolean bl = this.mService.hasShareTargets(this.mContext.getPackageName(), string2, this.injectMyUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    protected int injectMyUserId() {
        return this.mContext.getUserId();
    }

    public boolean isRateLimitingActive() {
        try {
            int n = this.mService.getRemainingCallCount(this.mContext.getPackageName(), this.injectMyUserId());
            boolean bl = n == 0;
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinShortcutSupported() {
        try {
            boolean bl = this.mService.isRequestPinItemSupported(this.injectMyUserId(), 1);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void onApplicationActive(String string2, int n) {
        try {
            this.mService.onApplicationActive(string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeAllDynamicShortcuts() {
        try {
            this.mService.removeAllDynamicShortcuts(this.mContext.getPackageName(), this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeDynamicShortcuts(List<String> list) {
        try {
            this.mService.removeDynamicShortcuts(this.mContext.getPackageName(), list, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportShortcutUsed(String string2) {
        try {
            this.mService.reportShortcutUsed(this.mContext.getPackageName(), string2, this.injectMyUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean requestPinShortcut(ShortcutInfo shortcutInfo, IntentSender intentSender) {
        try {
            boolean bl = this.mService.requestPinShortcut(this.mContext.getPackageName(), shortcutInfo, intentSender, this.injectMyUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setDynamicShortcuts(List<ShortcutInfo> list) {
        try {
            IShortcutService iShortcutService = this.mService;
            String string2 = this.mContext.getPackageName();
            ParceledListSlice<ShortcutInfo> parceledListSlice = new ParceledListSlice<ShortcutInfo>(list);
            boolean bl = iShortcutService.setDynamicShortcuts(string2, parceledListSlice, this.injectMyUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean updateShortcuts(List<ShortcutInfo> list) {
        try {
            IShortcutService iShortcutService = this.mService;
            String string2 = this.mContext.getPackageName();
            ParceledListSlice<ShortcutInfo> parceledListSlice = new ParceledListSlice<ShortcutInfo>(list);
            boolean bl = iShortcutService.updateShortcuts(string2, parceledListSlice, this.injectMyUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static final class ShareShortcutInfo
    implements Parcelable {
        public static final Parcelable.Creator<ShareShortcutInfo> CREATOR = new Parcelable.Creator<ShareShortcutInfo>(){

            @Override
            public ShareShortcutInfo createFromParcel(Parcel parcel) {
                return new ShareShortcutInfo(parcel);
            }

            public ShareShortcutInfo[] newArray(int n) {
                return new ShareShortcutInfo[n];
            }
        };
        private final ShortcutInfo mShortcutInfo;
        private final ComponentName mTargetComponent;

        public ShareShortcutInfo(ShortcutInfo shortcutInfo, ComponentName componentName) {
            if (shortcutInfo != null) {
                if (componentName != null) {
                    this.mShortcutInfo = shortcutInfo;
                    this.mTargetComponent = componentName;
                    return;
                }
                throw new NullPointerException("target component is null");
            }
            throw new NullPointerException("shortcut info is null");
        }

        private ShareShortcutInfo(Parcel parcel) {
            this.mShortcutInfo = (ShortcutInfo)parcel.readParcelable(ShortcutInfo.class.getClassLoader());
            this.mTargetComponent = (ComponentName)parcel.readParcelable(ComponentName.class.getClassLoader());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ShortcutInfo getShortcutInfo() {
            return this.mShortcutInfo;
        }

        public ComponentName getTargetComponent() {
            return this.mTargetComponent;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeParcelable(this.mShortcutInfo, n);
            parcel.writeParcelable(this.mTargetComponent, n);
        }

    }

}

