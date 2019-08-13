/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioRecordingMonitorImpl;
import java.util.ArrayList;

public final class _$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr__fiLO_SMYka7I
implements Runnable {
    private final /* synthetic */ AudioRecordingMonitorImpl.AudioRecordingCallbackInfo f$0;
    private final /* synthetic */ ArrayList f$1;

    public /* synthetic */ _$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr__fiLO_SMYka7I(AudioRecordingMonitorImpl.AudioRecordingCallbackInfo audioRecordingCallbackInfo, ArrayList arrayList) {
        this.f$0 = audioRecordingCallbackInfo;
        this.f$1 = arrayList;
    }

    @Override
    public final void run() {
        AudioRecordingMonitorImpl.2.lambda$handleMessage$0(this.f$0, this.f$1);
    }
}

