/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface SynthesisCallback {
    public int audioAvailable(byte[] var1, int var2, int var3);

    public int done();

    public void error();

    public void error(int var1);

    public int getMaxBufferSize();

    public boolean hasFinished();

    public boolean hasStarted();

    default public void rangeStart(int n, int n2, int n3) {
    }

    public int start(int var1, int var2, int var3);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SupportedAudioFormat {
    }

}

