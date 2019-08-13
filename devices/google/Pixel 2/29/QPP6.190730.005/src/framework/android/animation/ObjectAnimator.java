/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.KeyframeSet;
import android.animation.Keyframes;
import android.animation.PathKeyframes;
import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public final class ObjectAnimator
extends ValueAnimator {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "ObjectAnimator";
    private boolean mAutoCancel = false;
    private Property mProperty;
    private String mPropertyName;
    private WeakReference<Object> mTarget;

    public ObjectAnimator() {
    }

    private <T> ObjectAnimator(T t, Property<T, ?> property) {
        this.setTarget(t);
        this.setProperty(property);
    }

    private ObjectAnimator(Object object, String string2) {
        this.setTarget(object);
        this.setPropertyName(string2);
    }

    private boolean hasSameTargetAndProperties(Animator cloneable) {
        if (cloneable instanceof ObjectAnimator) {
            PropertyValuesHolder[] arrpropertyValuesHolder = ((ObjectAnimator)cloneable).getValues();
            if (((ObjectAnimator)cloneable).getTarget() == this.getTarget() && this.mValues.length == arrpropertyValuesHolder.length) {
                for (int i = 0; i < this.mValues.length; ++i) {
                    cloneable = this.mValues[i];
                    PropertyValuesHolder propertyValuesHolder = arrpropertyValuesHolder[i];
                    if (((PropertyValuesHolder)cloneable).getPropertyName() != null && ((PropertyValuesHolder)cloneable).getPropertyName().equals(propertyValuesHolder.getPropertyName())) {
                        continue;
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public static <T> ObjectAnimator ofArgb(T object, Property<T, Integer> property, int ... arrn) {
        object = ObjectAnimator.ofInt(object, property, arrn);
        ((ValueAnimator)object).setEvaluator(ArgbEvaluator.getInstance());
        return object;
    }

    public static ObjectAnimator ofArgb(Object object, String string2, int ... arrn) {
        object = ObjectAnimator.ofInt(object, string2, arrn);
        ((ValueAnimator)object).setEvaluator(ArgbEvaluator.getInstance());
        return object;
    }

    public static <T> ObjectAnimator ofFloat(T t, Property<T, Float> object, Property<T, Float> property, Path object2) {
        object2 = KeyframeSet.ofPath((Path)object2);
        object = PropertyValuesHolder.ofKeyframes(object, (Keyframes)((PathKeyframes)object2).createXFloatKeyframes());
        return ObjectAnimator.ofPropertyValuesHolder(t, new PropertyValuesHolder[]{object, PropertyValuesHolder.ofKeyframes(property, (Keyframes)((PathKeyframes)object2).createYFloatKeyframes())});
    }

    public static <T> ObjectAnimator ofFloat(T object, Property<T, Float> property, float ... arrf) {
        object = new ObjectAnimator(object, property);
        ((ObjectAnimator)object).setFloatValues(arrf);
        return object;
    }

    public static ObjectAnimator ofFloat(Object object, String object2, String string2, Path object3) {
        object3 = KeyframeSet.ofPath((Path)object3);
        object2 = PropertyValuesHolder.ofKeyframes((String)object2, (Keyframes)((PathKeyframes)object3).createXFloatKeyframes());
        return ObjectAnimator.ofPropertyValuesHolder(object, new PropertyValuesHolder[]{object2, PropertyValuesHolder.ofKeyframes(string2, (Keyframes)((PathKeyframes)object3).createYFloatKeyframes())});
    }

    public static ObjectAnimator ofFloat(Object object, String string2, float ... arrf) {
        object = new ObjectAnimator(object, string2);
        ((ObjectAnimator)object).setFloatValues(arrf);
        return object;
    }

    public static <T> ObjectAnimator ofInt(T t, Property<T, Integer> object, Property<T, Integer> property, Path object2) {
        object2 = KeyframeSet.ofPath((Path)object2);
        object = PropertyValuesHolder.ofKeyframes(object, (Keyframes)((PathKeyframes)object2).createXIntKeyframes());
        return ObjectAnimator.ofPropertyValuesHolder(t, new PropertyValuesHolder[]{object, PropertyValuesHolder.ofKeyframes(property, (Keyframes)((PathKeyframes)object2).createYIntKeyframes())});
    }

    public static <T> ObjectAnimator ofInt(T object, Property<T, Integer> property, int ... arrn) {
        object = new ObjectAnimator(object, property);
        ((ObjectAnimator)object).setIntValues(arrn);
        return object;
    }

    public static ObjectAnimator ofInt(Object object, String object2, String string2, Path object3) {
        object3 = KeyframeSet.ofPath((Path)object3);
        object2 = PropertyValuesHolder.ofKeyframes((String)object2, (Keyframes)((PathKeyframes)object3).createXIntKeyframes());
        return ObjectAnimator.ofPropertyValuesHolder(object, new PropertyValuesHolder[]{object2, PropertyValuesHolder.ofKeyframes(string2, (Keyframes)((PathKeyframes)object3).createYIntKeyframes())});
    }

    public static ObjectAnimator ofInt(Object object, String string2, int ... arrn) {
        object = new ObjectAnimator(object, string2);
        ((ObjectAnimator)object).setIntValues(arrn);
        return object;
    }

    @SafeVarargs
    public static <T> ObjectAnimator ofMultiFloat(Object object, String string2, TypeConverter<T, float[]> typeConverter, TypeEvaluator<T> typeEvaluator, T ... arrT) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiFloat(string2, typeConverter, typeEvaluator, arrT));
    }

    public static ObjectAnimator ofMultiFloat(Object object, String string2, Path path) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiFloat(string2, path));
    }

    public static ObjectAnimator ofMultiFloat(Object object, String string2, float[][] arrf) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiFloat(string2, arrf));
    }

    @SafeVarargs
    public static <T> ObjectAnimator ofMultiInt(Object object, String string2, TypeConverter<T, int[]> typeConverter, TypeEvaluator<T> typeEvaluator, T ... arrT) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiInt(string2, typeConverter, typeEvaluator, arrT));
    }

    public static ObjectAnimator ofMultiInt(Object object, String string2, Path path) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiInt(string2, path));
    }

    public static ObjectAnimator ofMultiInt(Object object, String string2, int[][] arrn) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofMultiInt(string2, arrn));
    }

    @SafeVarargs
    public static <T, V, P> ObjectAnimator ofObject(T t, Property<T, P> property, TypeConverter<V, P> typeConverter, TypeEvaluator<V> typeEvaluator, V ... arrV) {
        return ObjectAnimator.ofPropertyValuesHolder(t, PropertyValuesHolder.ofObject(property, typeConverter, typeEvaluator, arrV));
    }

    public static <T, V> ObjectAnimator ofObject(T t, Property<T, V> property, TypeConverter<PointF, V> typeConverter, Path path) {
        return ObjectAnimator.ofPropertyValuesHolder(t, PropertyValuesHolder.ofObject(property, typeConverter, path));
    }

    @SafeVarargs
    public static <T, V> ObjectAnimator ofObject(T object, Property<T, V> property, TypeEvaluator<V> typeEvaluator, V ... arrV) {
        object = new ObjectAnimator(object, property);
        ((ObjectAnimator)object).setObjectValues(arrV);
        ((ValueAnimator)object).setEvaluator(typeEvaluator);
        return object;
    }

    public static ObjectAnimator ofObject(Object object, String string2, TypeConverter<PointF, ?> typeConverter, Path path) {
        return ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofObject(string2, typeConverter, path));
    }

    public static ObjectAnimator ofObject(Object object, String string2, TypeEvaluator typeEvaluator, Object ... arrobject) {
        object = new ObjectAnimator(object, string2);
        ((ObjectAnimator)object).setObjectValues(arrobject);
        ((ValueAnimator)object).setEvaluator(typeEvaluator);
        return object;
    }

    public static ObjectAnimator ofPropertyValuesHolder(Object object, PropertyValuesHolder ... arrpropertyValuesHolder) {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        objectAnimator.setTarget(object);
        objectAnimator.setValues(arrpropertyValuesHolder);
        return objectAnimator;
    }

    @Override
    void animateValue(float f) {
        Object object = this.getTarget();
        if (this.mTarget != null && object == null) {
            this.cancel();
            return;
        }
        super.animateValue(f);
        int n = this.mValues.length;
        for (int i = 0; i < n; ++i) {
            this.mValues[i].setAnimatedValue(object);
        }
    }

    @Override
    public ObjectAnimator clone() {
        return (ObjectAnimator)super.clone();
    }

    @Override
    String getNameForTrace() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("animator:");
        stringBuilder.append(this.getPropertyName());
        return stringBuilder.toString();
    }

    public String getPropertyName() {
        Object object;
        Object var1_1 = null;
        CharSequence charSequence = null;
        if (this.mPropertyName != null) {
            object = this.mPropertyName;
        } else {
            object = this.mProperty;
            if (object != null) {
                object = ((Property)object).getName();
            } else {
                object = var1_1;
                if (this.mValues != null) {
                    object = var1_1;
                    if (this.mValues.length > 0) {
                        int n = 0;
                        do {
                            object = charSequence;
                            if (n >= this.mValues.length) break;
                            if (n == 0) {
                                object = "";
                            } else {
                                object = new StringBuilder();
                                ((StringBuilder)object).append((String)charSequence);
                                ((StringBuilder)object).append(",");
                                object = ((StringBuilder)object).toString();
                            }
                            charSequence = new StringBuilder();
                            charSequence.append((String)object);
                            charSequence.append(this.mValues[n].getPropertyName());
                            charSequence = charSequence.toString();
                            ++n;
                        } while (true);
                    }
                }
            }
        }
        return object;
    }

    public Object getTarget() {
        WeakReference<Object> weakReference = this.mTarget;
        weakReference = weakReference == null ? null : weakReference.get();
        return weakReference;
    }

    @Override
    void initAnimation() {
        if (!this.mInitialized) {
            Object object = this.getTarget();
            if (object != null) {
                int n = this.mValues.length;
                for (int i = 0; i < n; ++i) {
                    this.mValues[i].setupSetterAndGetter(object);
                }
            }
            super.initAnimation();
        }
    }

    @Override
    boolean isInitialized() {
        return this.mInitialized;
    }

    public void setAutoCancel(boolean bl) {
        this.mAutoCancel = bl;
    }

    @Override
    public ObjectAnimator setDuration(long l) {
        super.setDuration(l);
        return this;
    }

    @Override
    public void setFloatValues(float ... arrf) {
        if (this.mValues != null && this.mValues.length != 0) {
            super.setFloatValues(arrf);
        } else {
            Property property = this.mProperty;
            if (property != null) {
                this.setValues(PropertyValuesHolder.ofFloat(property, arrf));
            } else {
                this.setValues(PropertyValuesHolder.ofFloat(this.mPropertyName, arrf));
            }
        }
    }

    @Override
    public void setIntValues(int ... arrn) {
        if (this.mValues != null && this.mValues.length != 0) {
            super.setIntValues(arrn);
        } else {
            Property property = this.mProperty;
            if (property != null) {
                this.setValues(PropertyValuesHolder.ofInt(property, arrn));
            } else {
                this.setValues(PropertyValuesHolder.ofInt(this.mPropertyName, arrn));
            }
        }
    }

    @Override
    public void setObjectValues(Object ... arrobject) {
        if (this.mValues != null && this.mValues.length != 0) {
            super.setObjectValues(arrobject);
        } else {
            Property property = this.mProperty;
            if (property != null) {
                this.setValues(PropertyValuesHolder.ofObject(property, (TypeEvaluator)null, arrobject));
            } else {
                this.setValues(PropertyValuesHolder.ofObject(this.mPropertyName, (TypeEvaluator)null, arrobject));
            }
        }
    }

    public void setProperty(Property property) {
        if (this.mValues != null) {
            PropertyValuesHolder propertyValuesHolder = this.mValues[0];
            String string2 = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setProperty(property);
            this.mValuesMap.remove(string2);
            this.mValuesMap.put(this.mPropertyName, propertyValuesHolder);
        }
        if (this.mProperty != null) {
            this.mPropertyName = property.getName();
        }
        this.mProperty = property;
        this.mInitialized = false;
    }

    public void setPropertyName(String string2) {
        if (this.mValues != null) {
            PropertyValuesHolder propertyValuesHolder = this.mValues[0];
            String string3 = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setPropertyName(string2);
            this.mValuesMap.remove(string3);
            this.mValuesMap.put(string2, propertyValuesHolder);
        }
        this.mPropertyName = string2;
        this.mInitialized = false;
    }

    @Override
    public void setTarget(Object weakReference) {
        if (this.getTarget() != weakReference) {
            if (this.isStarted()) {
                this.cancel();
            }
            weakReference = weakReference == null ? null : new WeakReference<Object>(weakReference);
            this.mTarget = weakReference;
            this.mInitialized = false;
        }
    }

    @Override
    public void setupEndValues() {
        this.initAnimation();
        Object object = this.getTarget();
        if (object != null) {
            int n = this.mValues.length;
            for (int i = 0; i < n; ++i) {
                this.mValues[i].setupEndValue(object);
            }
        }
    }

    @Override
    public void setupStartValues() {
        this.initAnimation();
        Object object = this.getTarget();
        if (object != null) {
            int n = this.mValues.length;
            for (int i = 0; i < n; ++i) {
                this.mValues[i].setupStartValue(object);
            }
        }
    }

    boolean shouldAutoCancel(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        if (animationFrameCallback == null) {
            return false;
        }
        if (animationFrameCallback instanceof ObjectAnimator) {
            animationFrameCallback = (ObjectAnimator)animationFrameCallback;
            if (((ObjectAnimator)animationFrameCallback).mAutoCancel && this.hasSameTargetAndProperties((Animator)((Object)animationFrameCallback))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        AnimationHandler.getInstance().autoCancelBasedOn(this);
        super.start();
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("ObjectAnimator@");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
        ((StringBuilder)charSequence).append(", target ");
        ((StringBuilder)charSequence).append(this.getTarget());
        CharSequence charSequence2 = charSequence = ((StringBuilder)charSequence).toString();
        if (this.mValues != null) {
            int n = 0;
            do {
                charSequence2 = charSequence;
                if (n >= this.mValues.length) break;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\n    ");
                ((StringBuilder)charSequence2).append(this.mValues[n].toString());
                charSequence = ((StringBuilder)charSequence2).toString();
                ++n;
            } while (true);
        }
        return charSequence2;
    }
}

