/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DeviceFilter<D extends Parcelable>
extends Parcelable {
    public static final int MEDIUM_TYPE_BLUETOOTH = 0;
    public static final int MEDIUM_TYPE_BLUETOOTH_LE = 1;
    public static final int MEDIUM_TYPE_WIFI = 2;

    public static <D extends Parcelable> boolean matches(DeviceFilter<D> deviceFilter, D d) {
        boolean bl = deviceFilter == null || deviceFilter.matches(d);
        return bl;
    }

    @UnsupportedAppUsage
    public String getDeviceDisplayName(D var1);

    public int getMediumType();

    @UnsupportedAppUsage
    public boolean matches(D var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MediumType {
    }

}

