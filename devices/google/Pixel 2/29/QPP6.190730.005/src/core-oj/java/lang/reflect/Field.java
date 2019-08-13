/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.reflect.AnnotatedElements
 *  libcore.reflect.GenericSignatureParser
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;
import libcore.reflect.AnnotatedElements;
import libcore.reflect.GenericSignatureParser;
import sun.reflect.CallerSensitive;

public final class Field
extends AccessibleObject
implements Member {
    private int accessFlags;
    private Class<?> declaringClass;
    private int dexFieldIndex;
    private int offset;
    private Class<?> type;

    private Field() {
    }

    @FastNative
    private native <A extends Annotation> A getAnnotationNative(Class<A> var1);

    @FastNative
    private native String getNameInternal();

    @FastNative
    private native String[] getSignatureAnnotation();

    private String getSignatureAttribute() {
        String[] arrstring = this.getSignatureAnnotation();
        if (arrstring == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrstring[i]);
        }
        return stringBuilder.toString();
    }

    @FastNative
    private native boolean isAnnotationPresentNative(Class<? extends Annotation> var1);

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof Field) {
            object = (Field)object;
            if (this.getDeclaringClass() == ((Field)object).getDeclaringClass() && this.getName() == ((Field)object).getName() && this.getType() == ((Field)object).getType()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @CallerSensitive
    @FastNative
    public native Object get(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        Objects.requireNonNull(class_);
        return this.getAnnotationNative(class_);
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> class_) {
        return AnnotatedElements.getDirectOrIndirectAnnotationsByType((AnnotatedElement)this, class_);
    }

    @FastNative
    public native long getArtField();

    @CallerSensitive
    @FastNative
    public native boolean getBoolean(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native byte getByte(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native char getChar(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @FastNative
    @Override
    public native Annotation[] getDeclaredAnnotations();

    @Override
    public Class<?> getDeclaringClass() {
        return this.declaringClass;
    }

    public int getDexFieldIndex() {
        return this.dexFieldIndex;
    }

    @CallerSensitive
    @FastNative
    public native double getDouble(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native float getFloat(Object var1) throws IllegalArgumentException, IllegalAccessException;

    public Type getGenericType() {
        Object object = this.getSignatureAttribute();
        Object object2 = new GenericSignatureParser(this.declaringClass.getClassLoader());
        object2.parseForField(this.declaringClass, (String)object);
        object2 = object2.fieldType;
        object = object2;
        if (object2 == null) {
            object = this.getType();
        }
        return object;
    }

    @CallerSensitive
    @FastNative
    public native int getInt(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native long getLong(Object var1) throws IllegalArgumentException, IllegalAccessException;

    @Override
    public int getModifiers() {
        return this.accessFlags & 65535;
    }

    @Override
    public String getName() {
        if (this.dexFieldIndex == -1) {
            if (this.declaringClass.isProxy()) {
                return "throws";
            }
            throw new AssertionError();
        }
        return this.getNameInternal();
    }

    public int getOffset() {
        return this.offset;
    }

    @CallerSensitive
    @FastNative
    public native short getShort(Object var1) throws IllegalArgumentException, IllegalAccessException;

    public Class<?> getType() {
        return this.type;
    }

    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        if (class_ != null) {
            return this.isAnnotationPresentNative(class_);
        }
        throw new NullPointerException("annotationType == null");
    }

    public boolean isEnumConstant() {
        boolean bl = (this.getModifiers() & 16384) != 0;
        return bl;
    }

    @Override
    public boolean isSynthetic() {
        return Modifier.isSynthetic(this.getModifiers());
    }

    @CallerSensitive
    @FastNative
    public native void set(Object var1, Object var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setBoolean(Object var1, boolean var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setByte(Object var1, byte var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setChar(Object var1, char var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setDouble(Object var1, double var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setFloat(Object var1, float var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setInt(Object var1, int var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setLong(Object var1, long var2) throws IllegalArgumentException, IllegalAccessException;

    @CallerSensitive
    @FastNative
    public native void setShort(Object var1, short var2) throws IllegalArgumentException, IllegalAccessException;

    public String toGenericString() {
        CharSequence charSequence;
        int n = this.getModifiers();
        Type type = this.getGenericType();
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 0) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Modifier.toString(n));
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(type.getTypeName());
        stringBuilder.append(" ");
        stringBuilder.append(this.getDeclaringClass().getTypeName());
        stringBuilder.append(".");
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }

    public String toString() {
        CharSequence charSequence;
        int n = this.getModifiers();
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 0) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Modifier.toString(n));
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(this.getType().getTypeName());
        stringBuilder.append(" ");
        stringBuilder.append(this.getDeclaringClass().getTypeName());
        stringBuilder.append(".");
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }
}

