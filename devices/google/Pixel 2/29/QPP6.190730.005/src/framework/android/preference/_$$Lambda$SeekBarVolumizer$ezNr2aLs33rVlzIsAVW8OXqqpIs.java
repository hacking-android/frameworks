/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.preference.-$
 *  android.preference.-$$Lambda
 *  android.preference.-$$Lambda$SeekBarVolumizer
 *  android.preference.-$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs
 */
package android.preference;

import android.media.audiopolicy.AudioProductStrategy;
import android.preference.-$;
import android.preference.SeekBarVolumizer;
import java.util.function.Function;

public final class _$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs
implements Function {
    public static final /* synthetic */ -$.Lambda.SeekBarVolumizer.ezNr2aLs33rVlzIsAVW8OXqqpIs INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs();
    }

    private /* synthetic */ _$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs() {
    }

    public final Object apply(Object object) {
        return SeekBarVolumizer.lambda$getVolumeGroupIdForLegacyStreamType$0((AudioProductStrategy)object);
    }
}

