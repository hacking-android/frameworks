/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics.palette;

public final class Target {
    public static final Target DARK_MUTED;
    public static final Target DARK_VIBRANT;
    static final int INDEX_MAX = 2;
    static final int INDEX_MIN = 0;
    static final int INDEX_TARGET = 1;
    static final int INDEX_WEIGHT_LUMA = 1;
    static final int INDEX_WEIGHT_POP = 2;
    static final int INDEX_WEIGHT_SAT = 0;
    public static final Target LIGHT_MUTED;
    public static final Target LIGHT_VIBRANT;
    private static final float MAX_DARK_LUMA = 0.45f;
    private static final float MAX_MUTED_SATURATION = 0.4f;
    private static final float MAX_NORMAL_LUMA = 0.7f;
    private static final float MIN_LIGHT_LUMA = 0.55f;
    private static final float MIN_NORMAL_LUMA = 0.3f;
    private static final float MIN_VIBRANT_SATURATION = 0.35f;
    public static final Target MUTED;
    private static final float TARGET_DARK_LUMA = 0.26f;
    private static final float TARGET_LIGHT_LUMA = 0.74f;
    private static final float TARGET_MUTED_SATURATION = 0.3f;
    private static final float TARGET_NORMAL_LUMA = 0.5f;
    private static final float TARGET_VIBRANT_SATURATION = 1.0f;
    public static final Target VIBRANT;
    private static final float WEIGHT_LUMA = 0.52f;
    private static final float WEIGHT_POPULATION = 0.24f;
    private static final float WEIGHT_SATURATION = 0.24f;
    boolean mIsExclusive = true;
    final float[] mLightnessTargets = new float[3];
    final float[] mSaturationTargets = new float[3];
    final float[] mWeights = new float[3];

    static {
        LIGHT_VIBRANT = new Target();
        Target.setDefaultLightLightnessValues(LIGHT_VIBRANT);
        Target.setDefaultVibrantSaturationValues(LIGHT_VIBRANT);
        VIBRANT = new Target();
        Target.setDefaultNormalLightnessValues(VIBRANT);
        Target.setDefaultVibrantSaturationValues(VIBRANT);
        DARK_VIBRANT = new Target();
        Target.setDefaultDarkLightnessValues(DARK_VIBRANT);
        Target.setDefaultVibrantSaturationValues(DARK_VIBRANT);
        LIGHT_MUTED = new Target();
        Target.setDefaultLightLightnessValues(LIGHT_MUTED);
        Target.setDefaultMutedSaturationValues(LIGHT_MUTED);
        MUTED = new Target();
        Target.setDefaultNormalLightnessValues(MUTED);
        Target.setDefaultMutedSaturationValues(MUTED);
        DARK_MUTED = new Target();
        Target.setDefaultDarkLightnessValues(DARK_MUTED);
        Target.setDefaultMutedSaturationValues(DARK_MUTED);
    }

    Target() {
        Target.setTargetDefaultValues(this.mSaturationTargets);
        Target.setTargetDefaultValues(this.mLightnessTargets);
        this.setDefaultWeights();
    }

    Target(Target arrf) {
        float[] arrf2 = arrf.mSaturationTargets;
        float[] arrf3 = this.mSaturationTargets;
        System.arraycopy(arrf2, 0, arrf3, 0, arrf3.length);
        arrf3 = arrf.mLightnessTargets;
        arrf2 = this.mLightnessTargets;
        System.arraycopy(arrf3, 0, arrf2, 0, arrf2.length);
        arrf2 = arrf.mWeights;
        arrf = this.mWeights;
        System.arraycopy(arrf2, 0, arrf, 0, arrf.length);
    }

    private static void setDefaultDarkLightnessValues(Target arrf) {
        arrf = arrf.mLightnessTargets;
        arrf[1] = 0.26f;
        arrf[2] = 0.45f;
    }

    private static void setDefaultLightLightnessValues(Target arrf) {
        arrf = arrf.mLightnessTargets;
        arrf[0] = 0.55f;
        arrf[1] = 0.74f;
    }

    private static void setDefaultMutedSaturationValues(Target arrf) {
        arrf = arrf.mSaturationTargets;
        arrf[1] = 0.3f;
        arrf[2] = 0.4f;
    }

    private static void setDefaultNormalLightnessValues(Target arrf) {
        arrf = arrf.mLightnessTargets;
        arrf[0] = 0.3f;
        arrf[1] = 0.5f;
        arrf[2] = 0.7f;
    }

    private static void setDefaultVibrantSaturationValues(Target arrf) {
        arrf = arrf.mSaturationTargets;
        arrf[0] = 0.35f;
        arrf[1] = 1.0f;
    }

    private void setDefaultWeights() {
        float[] arrf = this.mWeights;
        arrf[0] = 0.24f;
        arrf[1] = 0.52f;
        arrf[2] = 0.24f;
    }

    private static void setTargetDefaultValues(float[] arrf) {
        arrf[0] = 0.0f;
        arrf[1] = 0.5f;
        arrf[2] = 1.0f;
    }

    public float getLightnessWeight() {
        return this.mWeights[1];
    }

    public float getMaximumLightness() {
        return this.mLightnessTargets[2];
    }

    public float getMaximumSaturation() {
        return this.mSaturationTargets[2];
    }

    public float getMinimumLightness() {
        return this.mLightnessTargets[0];
    }

    public float getMinimumSaturation() {
        return this.mSaturationTargets[0];
    }

    public float getPopulationWeight() {
        return this.mWeights[2];
    }

    public float getSaturationWeight() {
        return this.mWeights[0];
    }

    public float getTargetLightness() {
        return this.mLightnessTargets[1];
    }

    public float getTargetSaturation() {
        return this.mSaturationTargets[1];
    }

    public boolean isExclusive() {
        return this.mIsExclusive;
    }

    void normalizeWeights() {
        float f = 0.0f;
        for (float f2 : this.mWeights) {
            float f3 = f;
            if (f2 > 0.0f) {
                f3 = f + f2;
            }
            f = f3;
        }
        if (f != 0.0f) {
            int n = this.mWeights.length;
            for (int i = 0; i < n; ++i) {
                float[] arrf = this.mWeights;
                if (!(arrf[i] > 0.0f)) continue;
                arrf[i] = arrf[i] / f;
            }
        }
    }

    public static final class Builder {
        private final Target mTarget;

        public Builder() {
            this.mTarget = new Target();
        }

        public Builder(Target target) {
            this.mTarget = new Target(target);
        }

        public Target build() {
            return this.mTarget;
        }

        public Builder setExclusive(boolean bl) {
            this.mTarget.mIsExclusive = bl;
            return this;
        }

        public Builder setLightnessWeight(float f) {
            this.mTarget.mWeights[1] = f;
            return this;
        }

        public Builder setMaximumLightness(float f) {
            this.mTarget.mLightnessTargets[2] = f;
            return this;
        }

        public Builder setMaximumSaturation(float f) {
            this.mTarget.mSaturationTargets[2] = f;
            return this;
        }

        public Builder setMinimumLightness(float f) {
            this.mTarget.mLightnessTargets[0] = f;
            return this;
        }

        public Builder setMinimumSaturation(float f) {
            this.mTarget.mSaturationTargets[0] = f;
            return this;
        }

        public Builder setPopulationWeight(float f) {
            this.mTarget.mWeights[2] = f;
            return this;
        }

        public Builder setSaturationWeight(float f) {
            this.mTarget.mWeights[0] = f;
            return this;
        }

        public Builder setTargetLightness(float f) {
            this.mTarget.mLightnessTargets[1] = f;
            return this;
        }

        public Builder setTargetSaturation(float f) {
            this.mTarget.mSaturationTargets[1] = f;
            return this;
        }
    }

}

