/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.VolumePolicy;
import android.util.IntArray;

public abstract class AudioManagerInternal {
    public abstract void adjustStreamVolumeForUid(int var1, int var2, int var3, String var4, int var5);

    public abstract void adjustSuggestedStreamVolumeForUid(int var1, int var2, int var3, String var4, int var5);

    public abstract int getRingerModeInternal();

    public abstract void setAccessibilityServiceUids(IntArray var1);

    public abstract void setRingerModeDelegate(RingerModeDelegate var1);

    public abstract void setRingerModeInternal(int var1, String var2);

    public abstract void setStreamVolumeForUid(int var1, int var2, int var3, String var4, int var5);

    public abstract void silenceRingerModeInternal(String var1);

    public abstract void updateRingerModeAffectedStreamsInternal();

    public static interface RingerModeDelegate {
        public boolean canVolumeDownEnterSilent();

        public int getRingerModeAffectedStreams(int var1);

        public int onSetRingerModeExternal(int var1, int var2, String var3, int var4, VolumePolicy var5);

        public int onSetRingerModeInternal(int var1, int var2, String var3, int var4, VolumePolicy var5);
    }

}

