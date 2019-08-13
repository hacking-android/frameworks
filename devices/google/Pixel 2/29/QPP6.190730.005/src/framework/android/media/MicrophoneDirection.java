/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MicrophoneDirection {
    public static final int MIC_DIRECTION_AWAY_FROM_USER = 2;
    public static final int MIC_DIRECTION_EXTERNAL = 3;
    public static final int MIC_DIRECTION_TOWARDS_USER = 1;
    public static final int MIC_DIRECTION_UNSPECIFIED = 0;

    public boolean setPreferredMicrophoneDirection(int var1);

    public boolean setPreferredMicrophoneFieldDimension(float var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DirectionMode {
    }

}

