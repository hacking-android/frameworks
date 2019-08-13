/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.BidirectionalTypeConverter;
import android.animation.FloatArrayEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntArrayEvaluator;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.KeyframeSet;
import android.animation.Keyframes;
import android.animation.PathKeyframes;
import android.animation.TypeConverter;
import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.PathParser;
import android.util.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class PropertyValuesHolder
implements Cloneable {
    private static Class[] DOUBLE_VARIANTS;
    private static Class[] FLOAT_VARIANTS;
    private static Class[] INTEGER_VARIANTS;
    private static final TypeEvaluator sFloatEvaluator;
    private static final HashMap<Class, HashMap<String, Method>> sGetterPropertyMap;
    private static final TypeEvaluator sIntEvaluator;
    private static final HashMap<Class, HashMap<String, Method>> sSetterPropertyMap;
    private Object mAnimatedValue;
    private TypeConverter mConverter;
    private TypeEvaluator mEvaluator;
    private Method mGetter = null;
    Keyframes mKeyframes = null;
    protected Property mProperty;
    String mPropertyName;
    Method mSetter = null;
    final Object[] mTmpValueArray = new Object[1];
    Class mValueType;

    static {
        sIntEvaluator = new IntEvaluator();
        sFloatEvaluator = new FloatEvaluator();
        FLOAT_VARIANTS = new Class[]{Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
        INTEGER_VARIANTS = new Class[]{Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
        DOUBLE_VARIANTS = new Class[]{Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
        sSetterPropertyMap = new HashMap();
        sGetterPropertyMap = new HashMap();
    }

    private PropertyValuesHolder(Property property) {
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    private PropertyValuesHolder(String string2) {
        this.mPropertyName = string2;
    }

    private Object convertBack(Object object) {
        TypeConverter typeConverter = this.mConverter;
        Object object2 = object;
        if (typeConverter != null) {
            if (typeConverter instanceof BidirectionalTypeConverter) {
                object2 = ((BidirectionalTypeConverter)typeConverter).convertBack(object);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Converter ");
                ((StringBuilder)object).append(this.mConverter.getClass().getName());
                ((StringBuilder)object).append(" must be a BidirectionalTypeConverter");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        return object2;
    }

    static String getMethodName(String string2, String charSequence) {
        if (charSequence != null && ((String)charSequence).length() != 0) {
            char c = Character.toUpperCase(((String)charSequence).charAt(0));
            String string3 = ((String)charSequence).substring(1);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append(string3);
            return ((StringBuilder)charSequence).toString();
        }
        return string2;
    }

    private Method getPropertyFunction(Class class_, String string2, Class class_2) {
        Object object;
        Object object2 = null;
        String string3 = PropertyValuesHolder.getMethodName(string2, this.mPropertyName);
        if (class_2 == null) {
            try {
                object = class_.getMethod(string3, null);
                object2 = object;
            }
            catch (NoSuchMethodException noSuchMethodException) {}
        } else {
            Class[] arrclass = new Class[1];
            object2 = class_2.equals(Float.class) ? FLOAT_VARIANTS : (class_2.equals(Integer.class) ? INTEGER_VARIANTS : (class_2.equals(Double.class) ? DOUBLE_VARIANTS : new Class[]{class_2}));
            int n = ((Class[])object2).length;
            object = null;
            for (int i = 0; i < n; ++i) {
                Method method;
                block10 : {
                    Class class_3;
                    arrclass[0] = class_3 = object2[i];
                    try {
                        method = class_.getMethod(string3, arrclass);
                        object = method;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        continue;
                    }
                    if (this.mConverter != null) break block10;
                    object = method;
                    this.mValueType = class_3;
                }
                return method;
            }
            object2 = object;
        }
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Method ");
            ((StringBuilder)object).append(PropertyValuesHolder.getMethodName(string2, this.mPropertyName));
            ((StringBuilder)object).append("() with type ");
            ((StringBuilder)object).append(class_2);
            ((StringBuilder)object).append(" not found on target class ");
            ((StringBuilder)object).append(class_);
            Log.w("PropertyValuesHolder", ((StringBuilder)object).toString());
        }
        return object2;
    }

    private static native void nCallFloatMethod(Object var0, long var1, float var3);

    private static native void nCallFourFloatMethod(Object var0, long var1, float var3, float var4, float var5, float var6);

    private static native void nCallFourIntMethod(Object var0, long var1, int var3, int var4, int var5, int var6);

    private static native void nCallIntMethod(Object var0, long var1, int var3);

    private static native void nCallMultipleFloatMethod(Object var0, long var1, float[] var3);

    private static native void nCallMultipleIntMethod(Object var0, long var1, int[] var3);

    private static native void nCallTwoFloatMethod(Object var0, long var1, float var3, float var4);

    private static native void nCallTwoIntMethod(Object var0, long var1, int var3, int var4);

    private static native long nGetFloatMethod(Class var0, String var1);

    private static native long nGetIntMethod(Class var0, String var1);

    private static native long nGetMultipleFloatMethod(Class var0, String var1, int var2);

    private static native long nGetMultipleIntMethod(Class var0, String var1, int var2);

    public static PropertyValuesHolder ofFloat(Property<?, Float> property, float ... arrf) {
        return new FloatPropertyValuesHolder(property, arrf);
    }

    public static PropertyValuesHolder ofFloat(String string2, float ... arrf) {
        return new FloatPropertyValuesHolder(string2, arrf);
    }

    public static PropertyValuesHolder ofInt(Property<?, Integer> property, int ... arrn) {
        return new IntPropertyValuesHolder(property, arrn);
    }

    public static PropertyValuesHolder ofInt(String string2, int ... arrn) {
        return new IntPropertyValuesHolder(string2, arrn);
    }

    public static PropertyValuesHolder ofKeyframe(Property property, Keyframe ... arrkeyframe) {
        return PropertyValuesHolder.ofKeyframes(property, (Keyframes)KeyframeSet.ofKeyframe(arrkeyframe));
    }

    public static PropertyValuesHolder ofKeyframe(String string2, Keyframe ... arrkeyframe) {
        return PropertyValuesHolder.ofKeyframes(string2, (Keyframes)KeyframeSet.ofKeyframe(arrkeyframe));
    }

    static PropertyValuesHolder ofKeyframes(Property object, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder((Property)object, (Keyframes.IntKeyframes)keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder((Property)object, (Keyframes.FloatKeyframes)keyframes);
        }
        object = new PropertyValuesHolder((Property)object);
        ((PropertyValuesHolder)object).mKeyframes = keyframes;
        ((PropertyValuesHolder)object).mValueType = keyframes.getType();
        return object;
    }

    static PropertyValuesHolder ofKeyframes(String object, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder((String)object, (Keyframes.IntKeyframes)keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder((String)object, (Keyframes.FloatKeyframes)keyframes);
        }
        object = new PropertyValuesHolder((String)object);
        ((PropertyValuesHolder)object).mKeyframes = keyframes;
        ((PropertyValuesHolder)object).mValueType = keyframes.getType();
        return object;
    }

    public static <T> PropertyValuesHolder ofMultiFloat(String string2, TypeConverter<T, float[]> typeConverter, TypeEvaluator<T> typeEvaluator, Keyframe ... arrkeyframe) {
        return new MultiFloatValuesHolder(string2, typeConverter, typeEvaluator, KeyframeSet.ofKeyframe(arrkeyframe));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiFloat(String string2, TypeConverter<V, float[]> typeConverter, TypeEvaluator<V> typeEvaluator, V ... arrV) {
        return new MultiFloatValuesHolder(string2, typeConverter, typeEvaluator, arrV);
    }

    public static PropertyValuesHolder ofMultiFloat(String string2, Path object) {
        object = KeyframeSet.ofPath((Path)object);
        return new MultiFloatValuesHolder(string2, (TypeConverter)new PointFToFloatArray(), null, (Keyframes)object);
    }

    public static PropertyValuesHolder ofMultiFloat(String string2, float[][] arrf) {
        if (arrf.length >= 2) {
            int n = 0;
            for (int i = 0; i < arrf.length; ++i) {
                if (arrf[i] != null) {
                    int n2 = arrf[i].length;
                    if (i == 0) {
                        n = n2;
                        continue;
                    }
                    if (n2 == n) {
                        continue;
                    }
                    throw new IllegalArgumentException("Values must all have the same length");
                }
                throw new IllegalArgumentException("values must not be null");
            }
            return new MultiFloatValuesHolder(string2, null, (TypeEvaluator)new FloatArrayEvaluator(new float[n]), (Object[])arrf);
        }
        throw new IllegalArgumentException("At least 2 values must be supplied");
    }

    public static <T> PropertyValuesHolder ofMultiInt(String string2, TypeConverter<T, int[]> typeConverter, TypeEvaluator<T> typeEvaluator, Keyframe ... arrkeyframe) {
        return new MultiIntValuesHolder(string2, typeConverter, typeEvaluator, KeyframeSet.ofKeyframe(arrkeyframe));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiInt(String string2, TypeConverter<V, int[]> typeConverter, TypeEvaluator<V> typeEvaluator, V ... arrV) {
        return new MultiIntValuesHolder(string2, typeConverter, typeEvaluator, arrV);
    }

    public static PropertyValuesHolder ofMultiInt(String string2, Path object) {
        object = KeyframeSet.ofPath((Path)object);
        return new MultiIntValuesHolder(string2, (TypeConverter)new PointFToIntArray(), null, (Keyframes)object);
    }

    public static PropertyValuesHolder ofMultiInt(String string2, int[][] arrn) {
        if (arrn.length >= 2) {
            int n = 0;
            for (int i = 0; i < arrn.length; ++i) {
                if (arrn[i] != null) {
                    int n2 = arrn[i].length;
                    if (i == 0) {
                        n = n2;
                        continue;
                    }
                    if (n2 == n) {
                        continue;
                    }
                    throw new IllegalArgumentException("Values must all have the same length");
                }
                throw new IllegalArgumentException("values must not be null");
            }
            return new MultiIntValuesHolder(string2, null, (TypeEvaluator)new IntArrayEvaluator(new int[n]), (Object[])arrn);
        }
        throw new IllegalArgumentException("At least 2 values must be supplied");
    }

    @SafeVarargs
    public static <T, V> PropertyValuesHolder ofObject(Property<?, V> object, TypeConverter<T, V> typeConverter, TypeEvaluator<T> typeEvaluator, T ... arrT) {
        object = new PropertyValuesHolder((Property)object);
        ((PropertyValuesHolder)object).setConverter(typeConverter);
        ((PropertyValuesHolder)object).setObjectValues(arrT);
        ((PropertyValuesHolder)object).setEvaluator(typeEvaluator);
        return object;
    }

    public static <V> PropertyValuesHolder ofObject(Property<?, V> object, TypeConverter<PointF, V> typeConverter, Path path) {
        object = new PropertyValuesHolder((Property)object);
        ((PropertyValuesHolder)object).mKeyframes = KeyframeSet.ofPath(path);
        ((PropertyValuesHolder)object).mValueType = PointF.class;
        ((PropertyValuesHolder)object).setConverter(typeConverter);
        return object;
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofObject(Property object, TypeEvaluator<V> typeEvaluator, V ... arrV) {
        object = new PropertyValuesHolder((Property)object);
        ((PropertyValuesHolder)object).setObjectValues(arrV);
        ((PropertyValuesHolder)object).setEvaluator(typeEvaluator);
        return object;
    }

    public static PropertyValuesHolder ofObject(String object, TypeConverter<PointF, ?> typeConverter, Path path) {
        object = new PropertyValuesHolder((String)object);
        ((PropertyValuesHolder)object).mKeyframes = KeyframeSet.ofPath(path);
        ((PropertyValuesHolder)object).mValueType = PointF.class;
        ((PropertyValuesHolder)object).setConverter(typeConverter);
        return object;
    }

    public static PropertyValuesHolder ofObject(String object, TypeEvaluator typeEvaluator, Object ... arrobject) {
        object = new PropertyValuesHolder((String)object);
        ((PropertyValuesHolder)object).setObjectValues(arrobject);
        ((PropertyValuesHolder)object).setEvaluator(typeEvaluator);
        return object;
    }

    private void setupGetter(Class class_) {
        this.mGetter = this.setupSetterOrGetter(class_, sGetterPropertyMap, "get", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Method setupSetterOrGetter(Class class_, HashMap<Class, HashMap<String, Method>> hashMap, String hashMap2, Class class_2) {
        Method method = null;
        synchronized (hashMap) {
            HashMap<String, Method> hashMap3 = hashMap.get(class_);
            boolean bl = false;
            Method method2 = method;
            if (hashMap3 != null) {
                boolean bl2 = hashMap3.containsKey(this.mPropertyName);
                method2 = method;
                bl = bl2;
                if (bl2) {
                    method2 = hashMap3.get(this.mPropertyName);
                    bl = bl2;
                }
            }
            if (!bl) {
                method2 = this.getPropertyFunction(class_, (String)((Object)hashMap2), class_2);
                hashMap2 = hashMap3;
                if (hashMap3 == null) {
                    hashMap2 = new HashMap<String, Method>();
                    hashMap.put(class_, hashMap2);
                }
                hashMap2.put(this.mPropertyName, method2);
            }
            return method2;
        }
    }

    private void setupValue(Object object, Keyframe keyframe) {
        Property property = this.mProperty;
        if (property != null) {
            keyframe.setValue(this.convertBack(property.get(object)));
        } else {
            try {
                if (this.mGetter == null) {
                    this.setupGetter(object.getClass());
                    if (this.mGetter == null) {
                        return;
                    }
                }
                keyframe.setValue(this.convertBack(this.mGetter.invoke(object, new Object[0])));
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e("PropertyValuesHolder", illegalAccessException.toString());
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.e("PropertyValuesHolder", invocationTargetException.toString());
            }
        }
    }

    void calculateValue(float f) {
        Object object = this.mKeyframes.getValue(f);
        TypeConverter typeConverter = this.mConverter;
        if (typeConverter != null) {
            object = typeConverter.convert(object);
        }
        this.mAnimatedValue = object;
    }

    public PropertyValuesHolder clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder)super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframes = this.mKeyframes.clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public String getPropertyName() {
        return this.mPropertyName;
    }

    public void getPropertyValues(PropertyValues propertyValues) {
        Keyframes keyframes;
        this.init();
        propertyValues.propertyName = this.mPropertyName;
        propertyValues.type = this.mValueType;
        propertyValues.startValue = this.mKeyframes.getValue(0.0f);
        if (propertyValues.startValue instanceof PathParser.PathData) {
            propertyValues.startValue = new PathParser.PathData((PathParser.PathData)propertyValues.startValue);
        }
        propertyValues.endValue = this.mKeyframes.getValue(1.0f);
        if (propertyValues.endValue instanceof PathParser.PathData) {
            propertyValues.endValue = new PathParser.PathData((PathParser.PathData)propertyValues.endValue);
        }
        propertyValues.dataSource = !((keyframes = this.mKeyframes) instanceof PathKeyframes.FloatKeyframesBase || keyframes instanceof PathKeyframes.IntKeyframesBase || keyframes.getKeyframes() != null && this.mKeyframes.getKeyframes().size() > 2) ? null : new PropertyValues.DataSource(){

            @Override
            public Object getValueAtFraction(float f) {
                return PropertyValuesHolder.this.mKeyframes.getValue(f);
            }
        };
    }

    public Class getValueType() {
        return this.mValueType;
    }

    void init() {
        Object object;
        if (this.mEvaluator == null) {
            object = this.mValueType;
            object = object == Integer.class ? sIntEvaluator : (object == Float.class ? sFloatEvaluator : null);
            this.mEvaluator = object;
        }
        if ((object = this.mEvaluator) != null) {
            this.mKeyframes.setEvaluator((TypeEvaluator)object);
        }
    }

    void setAnimatedValue(Object object) {
        Property property = this.mProperty;
        if (property != null) {
            property.set(object, this.getAnimatedValue());
        }
        if (this.mSetter != null) {
            try {
                this.mTmpValueArray[0] = this.getAnimatedValue();
                this.mSetter.invoke(object, this.mTmpValueArray);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e("PropertyValuesHolder", illegalAccessException.toString());
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.e("PropertyValuesHolder", invocationTargetException.toString());
            }
        }
    }

    public void setConverter(TypeConverter typeConverter) {
        this.mConverter = typeConverter;
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
        this.mKeyframes.setEvaluator(typeEvaluator);
    }

    public void setFloatValues(float ... arrf) {
        this.mValueType = Float.TYPE;
        this.mKeyframes = KeyframeSet.ofFloat(arrf);
    }

    public void setIntValues(int ... arrn) {
        this.mValueType = Integer.TYPE;
        this.mKeyframes = KeyframeSet.ofInt(arrn);
    }

    public void setKeyframes(Keyframe ... arrkeyframe) {
        int n = arrkeyframe.length;
        Keyframe[] arrkeyframe2 = new Keyframe[Math.max(n, 2)];
        this.mValueType = arrkeyframe[0].getType();
        for (int i = 0; i < n; ++i) {
            arrkeyframe2[i] = arrkeyframe[i];
        }
        this.mKeyframes = new KeyframeSet(arrkeyframe2);
    }

    public void setObjectValues(Object ... object) {
        this.mValueType = object[0].getClass();
        this.mKeyframes = KeyframeSet.ofObject(object);
        object = this.mEvaluator;
        if (object != null) {
            this.mKeyframes.setEvaluator((TypeEvaluator)object);
        }
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public void setPropertyName(String string2) {
        this.mPropertyName = string2;
    }

    void setupEndValue(Object object) {
        List<Keyframe> list = this.mKeyframes.getKeyframes();
        if (!list.isEmpty()) {
            this.setupValue(object, list.get(list.size() - 1));
        }
    }

    void setupSetter(Class class_) {
        Object object = this.mConverter;
        object = object == null ? this.mValueType : ((TypeConverter)object).getTargetType();
        this.mSetter = this.setupSetterOrGetter(class_, sSetterPropertyMap, "set", (Class)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setupSetterAndGetter(Object object) {
        Object object2;
        block16 : {
            if (this.mProperty == null) break block16;
            Object object3 = null;
            try {
                List<Keyframe> list = this.mKeyframes.getKeyframes();
                int n = list == null ? 0 : list.size();
                int n2 = 0;
                do {
                    Object object4;
                    block18 : {
                        Keyframe keyframe;
                        block17 : {
                            if (n2 >= n) {
                                return;
                            }
                            keyframe = list.get(n2);
                            if (!keyframe.hasValue()) break block17;
                            object4 = object3;
                            if (!keyframe.valueWasSetOnStart()) break block18;
                        }
                        object4 = object3;
                        if (object3 == null) {
                            object4 = this.convertBack(this.mProperty.get(object));
                        }
                        keyframe.setValue(object4);
                        keyframe.setValueWasSetOnStart(true);
                    }
                    ++n2;
                    object3 = object4;
                } while (true);
            }
            catch (ClassCastException classCastException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No such property (");
                ((StringBuilder)object2).append(this.mProperty.getName());
                ((StringBuilder)object2).append(") on target object ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(". Trying reflection instead");
                Log.w("PropertyValuesHolder", ((StringBuilder)object2).toString());
                this.mProperty = null;
            }
        }
        if (this.mProperty == null) {
            Class<?> class_ = object.getClass();
            if (this.mSetter == null) {
                this.setupSetter(class_);
            }
            int n = (object2 = this.mKeyframes.getKeyframes()) == null ? 0 : object2.size();
            for (int i = 0; i < n; ++i) {
                Keyframe keyframe = (Keyframe)object2.get(i);
                if (keyframe.hasValue() && !keyframe.valueWasSetOnStart()) continue;
                if (this.mGetter == null) {
                    this.setupGetter(class_);
                    if (this.mGetter == null) {
                        return;
                    }
                }
                try {
                    keyframe.setValue(this.convertBack(this.mGetter.invoke(object, new Object[0])));
                    keyframe.setValueWasSetOnStart(true);
                    continue;
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e("PropertyValuesHolder", illegalAccessException.toString());
                    continue;
                }
                catch (InvocationTargetException invocationTargetException) {
                    Log.e("PropertyValuesHolder", invocationTargetException.toString());
                }
            }
        }
    }

    void setupStartValue(Object object) {
        List<Keyframe> list = this.mKeyframes.getKeyframes();
        if (!list.isEmpty()) {
            this.setupValue(object, list.get(0));
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mPropertyName);
        stringBuilder.append(": ");
        stringBuilder.append(this.mKeyframes.toString());
        return stringBuilder.toString();
    }

    static class FloatPropertyValuesHolder
    extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap();
        float mFloatAnimatedValue;
        Keyframes.FloatKeyframes mFloatKeyframes;
        private FloatProperty mFloatProperty;
        long mJniSetter;

        public FloatPropertyValuesHolder(Property property, Keyframes.FloatKeyframes floatKeyframes) {
            super(property);
            this.mValueType = Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty)this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(Property property, float ... arrf) {
            super(property);
            this.setFloatValues(arrf);
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty)this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(String string2, Keyframes.FloatKeyframes floatKeyframes) {
            super(string2);
            this.mValueType = Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
        }

        public FloatPropertyValuesHolder(String string2, float ... arrf) {
            super(string2);
            this.setFloatValues(arrf);
        }

        @Override
        void calculateValue(float f) {
            this.mFloatAnimatedValue = this.mFloatKeyframes.getFloatValue(f);
        }

        @Override
        public FloatPropertyValuesHolder clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder)super.clone();
            floatPropertyValuesHolder.mFloatKeyframes = (Keyframes.FloatKeyframes)floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        @Override
        Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        @Override
        void setAnimatedValue(Object object) {
            FloatProperty floatProperty = this.mFloatProperty;
            if (floatProperty != null) {
                floatProperty.setValue(object, this.mFloatAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(object, Float.valueOf(this.mFloatAnimatedValue));
                return;
            }
            long l = this.mJniSetter;
            if (l != 0L) {
                PropertyValuesHolder.nCallFloatMethod(object, l, this.mFloatAnimatedValue);
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Float.valueOf(this.mFloatAnimatedValue);
                    this.mSetter.invoke(object, this.mTmpValueArray);
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e("PropertyValuesHolder", illegalAccessException.toString());
                }
                catch (InvocationTargetException invocationTargetException) {
                    Log.e("PropertyValuesHolder", invocationTargetException.toString());
                }
            }
        }

        @Override
        public void setFloatValues(float ... arrf) {
            super.setFloatValues(arrf);
            this.mFloatKeyframes = (Keyframes.FloatKeyframes)this.mKeyframes;
        }

        @Override
        public void setProperty(Property property) {
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty)property;
            } else {
                super.setProperty(property);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        void setupSetter(Class class_) {
            HashMap<String, Long> hashMap;
            if (this.mProperty != null) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> hashMap2 = sJNISetterPropertyMap;
            // MONITORENTER : hashMap2
            HashMap<String, Long> hashMap3 = sJNISetterPropertyMap.get(class_);
            boolean bl = false;
            if (hashMap3 != null) {
                boolean bl2;
                bl = bl2 = hashMap3.containsKey(this.mPropertyName);
                if (bl2) {
                    hashMap = hashMap3.get(this.mPropertyName);
                    bl = bl2;
                    if (hashMap != null) {
                        this.mJniSetter = (Long)((Object)hashMap);
                        bl = bl2;
                    }
                }
            }
            if (!bl) {
                hashMap = FloatPropertyValuesHolder.getMethodName("set", this.mPropertyName);
                try {
                    this.mJniSetter = PropertyValuesHolder.nGetFloatMethod(class_, (String)((Object)hashMap));
                }
                catch (NoSuchMethodError noSuchMethodError) {
                    // empty catch block
                }
                hashMap = hashMap3;
                if (hashMap3 == null) {
                    hashMap = new HashMap<String, Long>();
                    sJNISetterPropertyMap.put(class_, hashMap);
                }
                hashMap.put(this.mPropertyName, this.mJniSetter);
            }
            // MONITOREXIT : hashMap2
            if (this.mJniSetter != 0L) return;
            super.setupSetter(class_);
        }
    }

    static class IntPropertyValuesHolder
    extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap();
        int mIntAnimatedValue;
        Keyframes.IntKeyframes mIntKeyframes;
        private IntProperty mIntProperty;
        long mJniSetter;

        public IntPropertyValuesHolder(Property property, Keyframes.IntKeyframes intKeyframes) {
            super(property);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty)this.mProperty;
            }
        }

        public IntPropertyValuesHolder(Property property, int ... arrn) {
            super(property);
            this.setIntValues(arrn);
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty)this.mProperty;
            }
        }

        public IntPropertyValuesHolder(String string2, Keyframes.IntKeyframes intKeyframes) {
            super(string2);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
        }

        public IntPropertyValuesHolder(String string2, int ... arrn) {
            super(string2);
            this.setIntValues(arrn);
        }

        @Override
        void calculateValue(float f) {
            this.mIntAnimatedValue = this.mIntKeyframes.getIntValue(f);
        }

        @Override
        public IntPropertyValuesHolder clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder)super.clone();
            intPropertyValuesHolder.mIntKeyframes = (Keyframes.IntKeyframes)intPropertyValuesHolder.mKeyframes;
            return intPropertyValuesHolder;
        }

        @Override
        Object getAnimatedValue() {
            return this.mIntAnimatedValue;
        }

        @Override
        void setAnimatedValue(Object object) {
            IntProperty intProperty = this.mIntProperty;
            if (intProperty != null) {
                intProperty.setValue(object, this.mIntAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(object, this.mIntAnimatedValue);
                return;
            }
            long l = this.mJniSetter;
            if (l != 0L) {
                PropertyValuesHolder.nCallIntMethod(object, l, this.mIntAnimatedValue);
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = this.mIntAnimatedValue;
                    this.mSetter.invoke(object, this.mTmpValueArray);
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e("PropertyValuesHolder", illegalAccessException.toString());
                }
                catch (InvocationTargetException invocationTargetException) {
                    Log.e("PropertyValuesHolder", invocationTargetException.toString());
                }
            }
        }

        @Override
        public void setIntValues(int ... arrn) {
            super.setIntValues(arrn);
            this.mIntKeyframes = (Keyframes.IntKeyframes)this.mKeyframes;
        }

        @Override
        public void setProperty(Property property) {
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty)property;
            } else {
                super.setProperty(property);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        void setupSetter(Class class_) {
            HashMap<String, Long> hashMap;
            if (this.mProperty != null) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> hashMap2 = sJNISetterPropertyMap;
            // MONITORENTER : hashMap2
            HashMap<String, Long> hashMap3 = sJNISetterPropertyMap.get(class_);
            boolean bl = false;
            if (hashMap3 != null) {
                boolean bl2;
                bl = bl2 = hashMap3.containsKey(this.mPropertyName);
                if (bl2) {
                    hashMap = hashMap3.get(this.mPropertyName);
                    bl = bl2;
                    if (hashMap != null) {
                        this.mJniSetter = (Long)((Object)hashMap);
                        bl = bl2;
                    }
                }
            }
            if (!bl) {
                hashMap = IntPropertyValuesHolder.getMethodName("set", this.mPropertyName);
                try {
                    this.mJniSetter = PropertyValuesHolder.nGetIntMethod(class_, (String)((Object)hashMap));
                }
                catch (NoSuchMethodError noSuchMethodError) {
                    // empty catch block
                }
                hashMap = hashMap3;
                if (hashMap3 == null) {
                    hashMap = new HashMap<String, Long>();
                    sJNISetterPropertyMap.put(class_, hashMap);
                }
                hashMap.put(this.mPropertyName, this.mJniSetter);
            }
            // MONITOREXIT : hashMap2
            if (this.mJniSetter != 0L) return;
            super.setupSetter(class_);
        }
    }

    static class MultiFloatValuesHolder
    extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap();
        private long mJniSetter;

        public MultiFloatValuesHolder(String string2, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Keyframes keyframes) {
            super(string2);
            this.setConverter(typeConverter);
            this.mKeyframes = keyframes;
            this.setEvaluator(typeEvaluator);
        }

        public MultiFloatValuesHolder(String string2, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Object ... arrobject) {
            super(string2);
            this.setConverter(typeConverter);
            this.setObjectValues(arrobject);
            this.setEvaluator(typeEvaluator);
        }

        @Override
        void setAnimatedValue(Object object) {
            float[] arrf = (float[])this.getAnimatedValue();
            int n = arrf.length;
            long l = this.mJniSetter;
            if (l != 0L) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 4) {
                            PropertyValuesHolder.nCallMultipleFloatMethod(object, l, arrf);
                        } else {
                            PropertyValuesHolder.nCallFourFloatMethod(object, l, arrf[0], arrf[1], arrf[2], arrf[3]);
                        }
                    } else {
                        PropertyValuesHolder.nCallTwoFloatMethod(object, l, arrf[0], arrf[1]);
                    }
                } else {
                    PropertyValuesHolder.nCallFloatMethod(object, l, arrf[0]);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void setupSetter(Class class_) {
            if (this.mJniSetter != 0L) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> hashMap = sJNISetterPropertyMap;
            synchronized (hashMap) {
                HashMap<String, Long> hashMap2;
                HashMap<String, Long> hashMap3 = sJNISetterPropertyMap.get(class_);
                boolean bl = false;
                if (hashMap3 != null) {
                    boolean bl2;
                    bl = bl2 = hashMap3.containsKey(this.mPropertyName);
                    if (bl2) {
                        hashMap2 = hashMap3.get(this.mPropertyName);
                        bl = bl2;
                        if (hashMap2 != null) {
                            this.mJniSetter = (Long)((Object)hashMap2);
                            bl = bl2;
                        }
                    }
                }
                if (!bl) {
                    hashMap2 = MultiFloatValuesHolder.getMethodName("set", this.mPropertyName);
                    this.calculateValue(0.0f);
                    int n = ((float[])this.getAnimatedValue()).length;
                    try {
                        this.mJniSetter = PropertyValuesHolder.nGetMultipleFloatMethod(class_, (String)((Object)hashMap2), n);
                    }
                    catch (NoSuchMethodError noSuchMethodError) {
                        try {
                            this.mJniSetter = PropertyValuesHolder.nGetMultipleFloatMethod(class_, this.mPropertyName, n);
                        }
                        catch (NoSuchMethodError noSuchMethodError2) {
                            // empty catch block
                        }
                    }
                    hashMap2 = hashMap3;
                    if (hashMap3 == null) {
                        hashMap2 = new HashMap<String, Long>();
                        sJNISetterPropertyMap.put(class_, hashMap2);
                    }
                    hashMap2.put(this.mPropertyName, this.mJniSetter);
                }
                return;
            }
        }

        @Override
        void setupSetterAndGetter(Object object) {
            this.setupSetter(object.getClass());
        }
    }

    static class MultiIntValuesHolder
    extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap();
        private long mJniSetter;

        public MultiIntValuesHolder(String string2, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Keyframes keyframes) {
            super(string2);
            this.setConverter(typeConverter);
            this.mKeyframes = keyframes;
            this.setEvaluator(typeEvaluator);
        }

        public MultiIntValuesHolder(String string2, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Object ... arrobject) {
            super(string2);
            this.setConverter(typeConverter);
            this.setObjectValues(arrobject);
            this.setEvaluator(typeEvaluator);
        }

        @Override
        void setAnimatedValue(Object object) {
            int[] arrn = (int[])this.getAnimatedValue();
            int n = arrn.length;
            long l = this.mJniSetter;
            if (l != 0L) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 4) {
                            PropertyValuesHolder.nCallMultipleIntMethod(object, l, arrn);
                        } else {
                            PropertyValuesHolder.nCallFourIntMethod(object, l, arrn[0], arrn[1], arrn[2], arrn[3]);
                        }
                    } else {
                        PropertyValuesHolder.nCallTwoIntMethod(object, l, arrn[0], arrn[1]);
                    }
                } else {
                    PropertyValuesHolder.nCallIntMethod(object, l, arrn[0]);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void setupSetter(Class class_) {
            if (this.mJniSetter != 0L) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> hashMap = sJNISetterPropertyMap;
            synchronized (hashMap) {
                HashMap<String, Long> hashMap2;
                HashMap<String, Long> hashMap3 = sJNISetterPropertyMap.get(class_);
                boolean bl = false;
                if (hashMap3 != null) {
                    boolean bl2;
                    bl = bl2 = hashMap3.containsKey(this.mPropertyName);
                    if (bl2) {
                        hashMap2 = hashMap3.get(this.mPropertyName);
                        bl = bl2;
                        if (hashMap2 != null) {
                            this.mJniSetter = (Long)((Object)hashMap2);
                            bl = bl2;
                        }
                    }
                }
                if (!bl) {
                    hashMap2 = MultiIntValuesHolder.getMethodName("set", this.mPropertyName);
                    this.calculateValue(0.0f);
                    int n = ((int[])this.getAnimatedValue()).length;
                    try {
                        this.mJniSetter = PropertyValuesHolder.nGetMultipleIntMethod(class_, (String)((Object)hashMap2), n);
                    }
                    catch (NoSuchMethodError noSuchMethodError) {
                        try {
                            this.mJniSetter = PropertyValuesHolder.nGetMultipleIntMethod(class_, this.mPropertyName, n);
                        }
                        catch (NoSuchMethodError noSuchMethodError2) {
                            // empty catch block
                        }
                    }
                    hashMap2 = hashMap3;
                    if (hashMap3 == null) {
                        hashMap2 = new HashMap<String, Long>();
                        sJNISetterPropertyMap.put(class_, hashMap2);
                    }
                    hashMap2.put(this.mPropertyName, this.mJniSetter);
                }
                return;
            }
        }

        @Override
        void setupSetterAndGetter(Object object) {
            this.setupSetter(object.getClass());
        }
    }

    private static class PointFToFloatArray
    extends TypeConverter<PointF, float[]> {
        private float[] mCoordinates = new float[2];

        public PointFToFloatArray() {
            super(PointF.class, float[].class);
        }

        @Override
        public float[] convert(PointF pointF) {
            this.mCoordinates[0] = pointF.x;
            this.mCoordinates[1] = pointF.y;
            return this.mCoordinates;
        }
    }

    private static class PointFToIntArray
    extends TypeConverter<PointF, int[]> {
        private int[] mCoordinates = new int[2];

        public PointFToIntArray() {
            super(PointF.class, int[].class);
        }

        @Override
        public int[] convert(PointF pointF) {
            this.mCoordinates[0] = Math.round(pointF.x);
            this.mCoordinates[1] = Math.round(pointF.y);
            return this.mCoordinates;
        }
    }

    public static class PropertyValues {
        public DataSource dataSource = null;
        public Object endValue;
        public String propertyName;
        public Object startValue;
        public Class type;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("property name: ");
            stringBuilder.append(this.propertyName);
            stringBuilder.append(", type: ");
            stringBuilder.append(this.type);
            stringBuilder.append(", startValue: ");
            stringBuilder.append(this.startValue.toString());
            stringBuilder.append(", endValue: ");
            stringBuilder.append(this.endValue.toString());
            return stringBuilder.toString();
        }

        public static interface DataSource {
            public Object getValueAtFraction(float var1);
        }

    }

}

