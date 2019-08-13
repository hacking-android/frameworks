/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.media.effect.Effect;
import android.media.effect.EffectContext;
import java.io.Serializable;
import java.lang.reflect.Constructor;

public class EffectFactory {
    public static final String EFFECT_AUTOFIX = "android.media.effect.effects.AutoFixEffect";
    public static final String EFFECT_BACKDROPPER = "android.media.effect.effects.BackDropperEffect";
    public static final String EFFECT_BITMAPOVERLAY = "android.media.effect.effects.BitmapOverlayEffect";
    public static final String EFFECT_BLACKWHITE = "android.media.effect.effects.BlackWhiteEffect";
    public static final String EFFECT_BRIGHTNESS = "android.media.effect.effects.BrightnessEffect";
    public static final String EFFECT_CONTRAST = "android.media.effect.effects.ContrastEffect";
    public static final String EFFECT_CROP = "android.media.effect.effects.CropEffect";
    public static final String EFFECT_CROSSPROCESS = "android.media.effect.effects.CrossProcessEffect";
    public static final String EFFECT_DOCUMENTARY = "android.media.effect.effects.DocumentaryEffect";
    public static final String EFFECT_DUOTONE = "android.media.effect.effects.DuotoneEffect";
    public static final String EFFECT_FILLLIGHT = "android.media.effect.effects.FillLightEffect";
    public static final String EFFECT_FISHEYE = "android.media.effect.effects.FisheyeEffect";
    public static final String EFFECT_FLIP = "android.media.effect.effects.FlipEffect";
    public static final String EFFECT_GRAIN = "android.media.effect.effects.GrainEffect";
    public static final String EFFECT_GRAYSCALE = "android.media.effect.effects.GrayscaleEffect";
    public static final String EFFECT_IDENTITY = "IdentityEffect";
    public static final String EFFECT_LOMOISH = "android.media.effect.effects.LomoishEffect";
    public static final String EFFECT_NEGATIVE = "android.media.effect.effects.NegativeEffect";
    private static final String[] EFFECT_PACKAGES = new String[]{"android.media.effect.effects.", ""};
    public static final String EFFECT_POSTERIZE = "android.media.effect.effects.PosterizeEffect";
    public static final String EFFECT_REDEYE = "android.media.effect.effects.RedEyeEffect";
    public static final String EFFECT_ROTATE = "android.media.effect.effects.RotateEffect";
    public static final String EFFECT_SATURATE = "android.media.effect.effects.SaturateEffect";
    public static final String EFFECT_SEPIA = "android.media.effect.effects.SepiaEffect";
    public static final String EFFECT_SHARPEN = "android.media.effect.effects.SharpenEffect";
    public static final String EFFECT_STRAIGHTEN = "android.media.effect.effects.StraightenEffect";
    public static final String EFFECT_TEMPERATURE = "android.media.effect.effects.ColorTemperatureEffect";
    public static final String EFFECT_TINT = "android.media.effect.effects.TintEffect";
    public static final String EFFECT_VIGNETTE = "android.media.effect.effects.VignetteEffect";
    private EffectContext mEffectContext;

    EffectFactory(EffectContext effectContext) {
        this.mEffectContext = effectContext;
    }

    private static Class getEffectClassByName(String string2) {
        Serializable serializable;
        StringBuilder stringBuilder = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String[] arrstring = EFFECT_PACKAGES;
        int n = arrstring.length;
        int n2 = 0;
        do {
            serializable = stringBuilder;
            if (n2 >= n) break;
            String string3 = arrstring[n2];
            try {
                serializable = new StringBuilder();
                serializable.append(string3);
                serializable.append(string2);
                serializable = stringBuilder = (serializable = classLoader.loadClass(serializable.toString()));
                if (stringBuilder != null) {
                    serializable = stringBuilder;
                    break;
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                serializable = stringBuilder;
            }
            ++n2;
            stringBuilder = serializable;
        } while (true);
        return serializable;
    }

    private Effect instantiateEffect(Class class_, String object) {
        Constructor constructor;
        try {
            class_.asSubclass(Effect.class);
        }
        catch (ClassCastException classCastException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Attempting to allocate effect '");
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append("' which is not a subclass of Effect!");
            throw new IllegalArgumentException(((StringBuilder)object).toString(), classCastException);
        }
        try {
            constructor = class_.getConstructor(EffectContext.class, String.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("The effect class '");
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append("' does not have the required constructor.");
            throw new RuntimeException(((StringBuilder)object).toString(), noSuchMethodException);
        }
        try {
            object = (Effect)constructor.newInstance(this.mEffectContext, object);
            return object;
        }
        catch (Throwable throwable) {
            object = new StringBuilder();
            ((StringBuilder)object).append("There was an error constructing the effect '");
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append("'!");
            throw new RuntimeException(((StringBuilder)object).toString(), throwable);
        }
    }

    public static boolean isEffectSupported(String string2) {
        boolean bl = EffectFactory.getEffectClassByName(string2) != null;
        return bl;
    }

    public Effect createEffect(String string2) {
        Serializable serializable = EffectFactory.getEffectClassByName(string2);
        if (serializable != null) {
            return this.instantiateEffect((Class)serializable, string2);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Cannot instantiate unknown effect '");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append("'!");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }
}

