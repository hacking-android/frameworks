/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioDeviceInfo;
import android.os.Handler;

public interface AudioRouting {
    public void addOnRoutingChangedListener(OnRoutingChangedListener var1, Handler var2);

    public AudioDeviceInfo getPreferredDevice();

    public AudioDeviceInfo getRoutedDevice();

    public void removeOnRoutingChangedListener(OnRoutingChangedListener var1);

    public boolean setPreferredDevice(AudioDeviceInfo var1);

    public static interface OnRoutingChangedListener {
        public void onRoutingChanged(AudioRouting var1);
    }

}

