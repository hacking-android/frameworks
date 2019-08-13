/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Bundle;
import android.os.IBinder;

public abstract class VoiceInteractionManagerInternal {
    public abstract void startLocalVoiceInteraction(IBinder var1, Bundle var2);

    public abstract void stopLocalVoiceInteraction(IBinder var1);

    public abstract boolean supportsLocalVoiceInteraction();
}

