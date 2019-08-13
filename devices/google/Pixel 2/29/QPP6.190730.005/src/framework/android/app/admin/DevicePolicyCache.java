/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyManagerInternal;
import com.android.server.LocalServices;

public abstract class DevicePolicyCache {
    protected DevicePolicyCache() {
    }

    public static DevicePolicyCache getInstance() {
        Object object = LocalServices.getService(DevicePolicyManagerInternal.class);
        object = object != null ? ((DevicePolicyManagerInternal)object).getDevicePolicyCache() : EmptyDevicePolicyCache.INSTANCE;
        return object;
    }

    public abstract int getPasswordQuality(int var1);

    public abstract boolean getScreenCaptureDisabled(int var1);

    private static class EmptyDevicePolicyCache
    extends DevicePolicyCache {
        private static final EmptyDevicePolicyCache INSTANCE = new EmptyDevicePolicyCache();

        private EmptyDevicePolicyCache() {
        }

        @Override
        public int getPasswordQuality(int n) {
            return 0;
        }

        @Override
        public boolean getScreenCaptureDisabled(int n) {
            return false;
        }
    }

}

