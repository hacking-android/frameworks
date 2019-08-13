/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.format;

import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.NativeBuffer;

public class ObjectFormat {
    private static int bytesPerSampleForClass(Class class_, int n) {
        if (n == 2) {
            if (NativeBuffer.class.isAssignableFrom(class_)) {
                try {
                    n = ((NativeBuffer)class_.newInstance()).getElementSize();
                    return n;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not determine the size of an element in a native object-based frame of type ");
                    stringBuilder.append(class_);
                    stringBuilder.append("! Perhaps it is missing a default constructor?");
                    throw new RuntimeException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Native object-based formats must be of a NativeBuffer subclass! (Received class: ");
            stringBuilder.append(class_);
            stringBuilder.append(").");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return 1;
    }

    public static MutableFrameFormat fromClass(Class class_, int n) {
        return ObjectFormat.fromClass(class_, 0, n);
    }

    public static MutableFrameFormat fromClass(Class class_, int n, int n2) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(8, n2);
        mutableFrameFormat.setObjectClass(ObjectFormat.getBoxedClass(class_));
        if (n != 0) {
            mutableFrameFormat.setDimensions(n);
        }
        mutableFrameFormat.setBytesPerSample(ObjectFormat.bytesPerSampleForClass(class_, n2));
        return mutableFrameFormat;
    }

    public static MutableFrameFormat fromObject(Object object, int n) {
        object = object == null ? new MutableFrameFormat(8, n) : ObjectFormat.fromClass(object.getClass(), 0, n);
        return object;
    }

    public static MutableFrameFormat fromObject(Object object, int n, int n2) {
        object = object == null ? new MutableFrameFormat(8, n2) : ObjectFormat.fromClass(object.getClass(), n, n2);
        return object;
    }

    private static Class getBoxedClass(Class class_) {
        if (class_.isPrimitive()) {
            if (class_ == Boolean.TYPE) {
                return Boolean.class;
            }
            if (class_ == Byte.TYPE) {
                return Byte.class;
            }
            if (class_ == Character.TYPE) {
                return Character.class;
            }
            if (class_ == Short.TYPE) {
                return Short.class;
            }
            if (class_ == Integer.TYPE) {
                return Integer.class;
            }
            if (class_ == Long.TYPE) {
                return Long.class;
            }
            if (class_ == Float.TYPE) {
                return Float.class;
            }
            if (class_ == Double.TYPE) {
                return Double.class;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown primitive type: ");
            stringBuilder.append(class_.getSimpleName());
            stringBuilder.append("!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return class_;
    }
}

