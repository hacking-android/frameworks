/*
 * Decompiled with CFR 0.145.
 */
package android.speech;

import android.os.Bundle;

public interface RecognitionListener {
    public void onBeginningOfSpeech();

    public void onBufferReceived(byte[] var1);

    public void onEndOfSpeech();

    public void onError(int var1);

    public void onEvent(int var1, Bundle var2);

    public void onPartialResults(Bundle var1);

    public void onReadyForSpeech(Bundle var1);

    public void onResults(Bundle var1);

    public void onRmsChanged(float var1);
}

