/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StatusBarManager {
    public static final int CAMERA_LAUNCH_SOURCE_LIFT_TRIGGER = 2;
    public static final int CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP = 1;
    public static final int CAMERA_LAUNCH_SOURCE_WIGGLE = 0;
    public static final int DEFAULT_SETUP_DISABLE2_FLAGS = 16;
    public static final int DEFAULT_SETUP_DISABLE_FLAGS = 61145088;
    public static final int DISABLE2_GLOBAL_ACTIONS = 8;
    public static final int DISABLE2_MASK = 31;
    public static final int DISABLE2_NONE = 0;
    public static final int DISABLE2_NOTIFICATION_SHADE = 4;
    public static final int DISABLE2_QUICK_SETTINGS = 1;
    public static final int DISABLE2_ROTATE_SUGGESTIONS = 16;
    public static final int DISABLE2_SYSTEM_ICONS = 2;
    public static final int DISABLE_BACK = 4194304;
    public static final int DISABLE_CLOCK = 8388608;
    public static final int DISABLE_EXPAND = 65536;
    public static final int DISABLE_HOME = 2097152;
    public static final int DISABLE_MASK = 67043328;
    @Deprecated
    public static final int DISABLE_NAVIGATION = 18874368;
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int DISABLE_NOTIFICATION_ICONS = 131072;
    @Deprecated
    @UnsupportedAppUsage
    public static final int DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int DISABLE_RECENT = 16777216;
    public static final int DISABLE_SEARCH = 33554432;
    public static final int DISABLE_SYSTEM_INFO = 1048576;
    public static final int NAVIGATION_HINT_BACK_ALT = 1;
    public static final int NAVIGATION_HINT_IME_SHOWN = 2;
    public static final int WINDOW_NAVIGATION_BAR = 2;
    public static final int WINDOW_STATE_HIDDEN = 2;
    public static final int WINDOW_STATE_HIDING = 1;
    public static final int WINDOW_STATE_SHOWING = 0;
    public static final int WINDOW_STATUS_BAR = 1;
    @UnsupportedAppUsage
    private Context mContext;
    private IStatusBarService mService;
    @UnsupportedAppUsage
    private IBinder mToken = new Binder();

    @UnsupportedAppUsage
    StatusBarManager(Context context) {
        this.mContext = context;
    }

    @UnsupportedAppUsage
    private IStatusBarService getService() {
        synchronized (this) {
            if (this.mService == null) {
                this.mService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                if (this.mService == null) {
                    Slog.w("StatusBarManager", "warning: no STATUS_BAR_SERVICE");
                }
            }
            IStatusBarService iStatusBarService = this.mService;
            return iStatusBarService;
        }
    }

    public static String windowStateToString(int n) {
        if (n == 1) {
            return "WINDOW_STATE_HIDING";
        }
        if (n == 2) {
            return "WINDOW_STATE_HIDDEN";
        }
        if (n == 0) {
            return "WINDOW_STATE_SHOWING";
        }
        return "WINDOW_STATE_UNKNOWN";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void collapsePanels() {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.collapsePanels();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void disable(int n) {
        int n2;
        IStatusBarService iStatusBarService;
        try {
            n2 = Binder.getCallingUserHandle().getIdentifier();
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.disableForUser(n, this.mToken, this.mContext.getPackageName(), n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void disable2(int n) {
        int n2;
        IStatusBarService iStatusBarService;
        try {
            n2 = Binder.getCallingUserHandle().getIdentifier();
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.disable2ForUser(n, this.mToken, this.mContext.getPackageName(), n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void expandNotificationsPanel() {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.expandNotificationsPanel();
    }

    @UnsupportedAppUsage
    public void expandSettingsPanel() {
        this.expandSettingsPanel(null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void expandSettingsPanel(String string2) {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.expandSettingsPanel(string2);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public DisableInfo getDisableInfo() {
        void var3_5;
        IStatusBarService iStatusBarService;
        int n;
        try {
            n = Binder.getCallingUserHandle().getIdentifier();
            iStatusBarService = this.getService();
            int[] arrn = new int[]{0, 0};
            if (iStatusBarService == null) return new DisableInfo((int)var3_5[0], (int)var3_5[1]);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        int[] arrn = iStatusBarService.getDisableFlags(this.mToken, n);
        return new DisableInfo((int)var3_5[0], (int)var3_5[1]);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void removeIcon(String string2) {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.removeIcon(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public void setDisabledForSetup(boolean bl) {
        int n;
        int n2;
        IStatusBarService iStatusBarService;
        block4 : {
            int n3;
            try {
                n2 = Binder.getCallingUserHandle().getIdentifier();
                iStatusBarService = this.getService();
                if (iStatusBarService == null) return;
                n3 = 0;
                n = bl ? 61145088 : 0;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            iStatusBarService.disableForUser(n, this.mToken, this.mContext.getPackageName(), n2);
            n = n3;
            if (!bl) break block4;
            n = 16;
        }
        iStatusBarService.disable2ForUser(n, this.mToken, this.mContext.getPackageName(), n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setIcon(String string2, int n, int n2, String string3) {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.setIcon(string2, this.mContext.getPackageName(), n, n2, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setIconVisibility(String string2, boolean bl) {
        IStatusBarService iStatusBarService;
        try {
            iStatusBarService = this.getService();
            if (iStatusBarService == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStatusBarService.setIconVisibility(string2, bl);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Disable2Flags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DisableFlags {
    }

    @SystemApi
    public static final class DisableInfo {
        private boolean mNavigateHome;
        private boolean mNotificationPeeking;
        private boolean mRecents;
        private boolean mSearch;
        private boolean mStatusBarExpansion;

        public DisableInfo() {
        }

        public DisableInfo(int n, int n2) {
            boolean bl = true;
            boolean bl2 = (65536 & n) != 0;
            this.mStatusBarExpansion = bl2;
            bl2 = (2097152 & n) != 0;
            this.mNavigateHome = bl2;
            bl2 = (262144 & n) != 0;
            this.mNotificationPeeking = bl2;
            bl2 = (16777216 & n) != 0;
            this.mRecents = bl2;
            bl2 = (33554432 & n) != 0 ? bl : false;
            this.mSearch = bl2;
        }

        public boolean areAllComponentsDisabled() {
            boolean bl = this.mStatusBarExpansion && this.mNavigateHome && this.mNotificationPeeking && this.mRecents && this.mSearch;
            return bl;
        }

        @SystemApi
        public boolean areAllComponentsEnabled() {
            boolean bl = !this.mStatusBarExpansion && !this.mNavigateHome && !this.mNotificationPeeking && !this.mRecents && !this.mSearch;
            return bl;
        }

        @SystemApi
        public boolean isNavigateToHomeDisabled() {
            return this.mNavigateHome;
        }

        @SystemApi
        public boolean isNotificationPeekingDisabled() {
            return this.mNotificationPeeking;
        }

        @SystemApi
        public boolean isRecentsDisabled() {
            return this.mRecents;
        }

        @SystemApi
        public boolean isSearchDisabled() {
            return this.mSearch;
        }

        @SystemApi
        public boolean isStatusBarExpansionDisabled() {
            return this.mStatusBarExpansion;
        }

        public void setDisableAll() {
            this.mStatusBarExpansion = true;
            this.mNavigateHome = true;
            this.mNotificationPeeking = true;
            this.mRecents = true;
            this.mSearch = true;
        }

        public void setEnableAll() {
            this.mStatusBarExpansion = false;
            this.mNavigateHome = false;
            this.mNotificationPeeking = false;
            this.mRecents = false;
            this.mSearch = false;
        }

        public void setNagivationHomeDisabled(boolean bl) {
            this.mNavigateHome = bl;
        }

        public void setNotificationPeekingDisabled(boolean bl) {
            this.mNotificationPeeking = bl;
        }

        public void setRecentsDisabled(boolean bl) {
            this.mRecents = bl;
        }

        public void setSearchDisabled(boolean bl) {
            this.mSearch = bl;
        }

        public void setStatusBarExpansionDisabled(boolean bl) {
            this.mStatusBarExpansion = bl;
        }

        public Pair<Integer, Integer> toFlags() {
            int n = 0;
            if (this.mStatusBarExpansion) {
                n = 0 | 65536;
            }
            int n2 = n;
            if (this.mNavigateHome) {
                n2 = n | 2097152;
            }
            n = n2;
            if (this.mNotificationPeeking) {
                n = n2 | 262144;
            }
            n2 = n;
            if (this.mRecents) {
                n2 = n | 16777216;
            }
            n = n2;
            if (this.mSearch) {
                n = n2 | 33554432;
            }
            return new Pair<Integer, Integer>(n, 0);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DisableInfo: ");
            stringBuilder.append(" mStatusBarExpansion=");
            boolean bl = this.mStatusBarExpansion;
            String string2 = "disabled";
            String string3 = bl ? "disabled" : "enabled";
            stringBuilder.append(string3);
            stringBuilder.append(" mNavigateHome=");
            string3 = this.mNavigateHome ? "disabled" : "enabled";
            stringBuilder.append(string3);
            stringBuilder.append(" mNotificationPeeking=");
            string3 = this.mNotificationPeeking ? "disabled" : "enabled";
            stringBuilder.append(string3);
            stringBuilder.append(" mRecents=");
            string3 = this.mRecents ? "disabled" : "enabled";
            stringBuilder.append(string3);
            stringBuilder.append(" mSearch=");
            string3 = this.mSearch ? string2 : "enabled";
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WindowType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WindowVisibleState {
    }

}

