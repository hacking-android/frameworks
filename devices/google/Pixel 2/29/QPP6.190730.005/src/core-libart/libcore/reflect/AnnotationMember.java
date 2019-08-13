/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class AnnotationMember
implements Serializable {
    protected static final char ARRAY = '[';
    protected static final char ERROR = '!';
    protected static final Object NO_VALUE = DefaultValues.NO_VALUE;
    protected static final char OTHER = '*';
    protected transient Method definingMethod;
    protected transient Class<?> elementType;
    protected final String name;
    protected final char tag;
    protected final Object value;

    public AnnotationMember(String object, Object object2) {
        this.name = object;
        object = object2 == null ? NO_VALUE : object2;
        object = this.value = object;
        this.tag = object instanceof Throwable ? (char)33 : (object.getClass().isArray() ? (char)91 : (char)42);
    }

    public AnnotationMember(String string, Object object, Class class_, Method method) {
        this(string, object);
        this.definingMethod = method;
        this.elementType = class_ == Integer.TYPE ? Integer.class : (class_ == Boolean.TYPE ? Boolean.class : (class_ == Character.TYPE ? Character.class : (class_ == Float.TYPE ? Float.class : (class_ == Double.TYPE ? Double.class : (class_ == Long.TYPE ? Long.class : (class_ == Short.TYPE ? Short.class : (class_ == Byte.TYPE ? Byte.class : class_)))))));
    }

    public Object copyValue() throws Throwable {
        if (this.tag == '[' && Array.getLength(this.value) != 0) {
            Class<?> class_ = this.value.getClass();
            if (class_ == int[].class) {
                return ((int[])this.value).clone();
            }
            if (class_ == byte[].class) {
                return ((byte[])this.value).clone();
            }
            if (class_ == short[].class) {
                return ((short[])this.value).clone();
            }
            if (class_ == long[].class) {
                return ((long[])this.value).clone();
            }
            if (class_ == char[].class) {
                return ((char[])this.value).clone();
            }
            if (class_ == boolean[].class) {
                return ((boolean[])this.value).clone();
            }
            if (class_ == float[].class) {
                return ((float[])this.value).clone();
            }
            if (class_ == double[].class) {
                return ((double[])this.value).clone();
            }
            return ((Object[])this.value).clone();
        }
        return this.value;
    }

    public boolean equalArrayValue(Object object) {
        Class<?> class_ = this.value;
        if (class_ instanceof Object[] && object instanceof Object[]) {
            return Arrays.equals((Object[])class_, (Object[])object);
        }
        class_ = this.value.getClass();
        if (class_ != object.getClass()) {
            return false;
        }
        if (class_ == int[].class) {
            return Arrays.equals((int[])this.value, (int[])object);
        }
        if (class_ == byte[].class) {
            return Arrays.equals((byte[])this.value, (byte[])object);
        }
        if (class_ == short[].class) {
            return Arrays.equals((short[])this.value, (short[])object);
        }
        if (class_ == long[].class) {
            return Arrays.equals((long[])this.value, (long[])object);
        }
        if (class_ == char[].class) {
            return Arrays.equals((char[])this.value, (char[])object);
        }
        if (class_ == boolean[].class) {
            return Arrays.equals((boolean[])this.value, (boolean[])object);
        }
        if (class_ == float[].class) {
            return Arrays.equals((float[])this.value, (float[])object);
        }
        if (class_ == double[].class) {
            return Arrays.equals((double[])this.value, (double[])object);
        }
        return false;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof AnnotationMember) {
            char c;
            object = (AnnotationMember)object;
            if (this.name.equals(((AnnotationMember)object).name) && (c = this.tag) == ((AnnotationMember)object).tag) {
                if (c == '[') {
                    return this.equalArrayValue(((AnnotationMember)object).value);
                }
                if (c == '!') {
                    return false;
                }
                return this.value.equals(((AnnotationMember)object).value);
            }
        }
        return false;
    }

    public int hashCode() {
        int n = this.name.hashCode() * 127;
        if (this.tag == '[') {
            Class<?> class_ = this.value.getClass();
            if (class_ == int[].class) {
                return Arrays.hashCode((int[])this.value) ^ n;
            }
            if (class_ == byte[].class) {
                return Arrays.hashCode((byte[])this.value) ^ n;
            }
            if (class_ == short[].class) {
                return Arrays.hashCode((short[])this.value) ^ n;
            }
            if (class_ == long[].class) {
                return Arrays.hashCode((long[])this.value) ^ n;
            }
            if (class_ == char[].class) {
                return Arrays.hashCode((char[])this.value) ^ n;
            }
            if (class_ == boolean[].class) {
                return Arrays.hashCode((boolean[])this.value) ^ n;
            }
            if (class_ == float[].class) {
                return Arrays.hashCode((float[])this.value) ^ n;
            }
            if (class_ == double[].class) {
                return Arrays.hashCode((double[])this.value) ^ n;
            }
            return Arrays.hashCode((Object[])this.value) ^ n;
        }
        return this.value.hashCode() ^ n;
    }

    public void rethrowError() throws Throwable {
        if (this.tag == '!') {
            Object object = this.value;
            if (!(object instanceof TypeNotPresentException)) {
                if (!(object instanceof EnumConstantNotPresentException)) {
                    if (!(object instanceof ArrayStoreException)) {
                        Object object2 = ((Throwable)(object = (Throwable)object)).getStackTrace();
                        int n = object2 == null ? 512 : (((StackTraceElement[])object2).length + 1) * 80;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n);
                        object2 = new ObjectOutputStream(byteArrayOutputStream);
                        ((ObjectOutputStream)object2).writeObject(object);
                        ((ObjectOutputStream)object2).flush();
                        ((ObjectOutputStream)object2).close();
                        object = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                        object2 = (Throwable)((ObjectInputStream)object).readObject();
                        ((ObjectInputStream)object).close();
                        throw object2;
                    }
                    throw new ArrayStoreException(((ArrayStoreException)object).getMessage());
                }
                object = (EnumConstantNotPresentException)object;
                throw new EnumConstantNotPresentException(((EnumConstantNotPresentException)object).enumType(), ((EnumConstantNotPresentException)object).constantName());
            }
            object = (TypeNotPresentException)object;
            throw new TypeNotPresentException(((TypeNotPresentException)object).typeName(), ((Throwable)object).getCause());
        }
    }

    protected AnnotationMember setDefinition(AnnotationMember annotationMember) {
        this.definingMethod = annotationMember.definingMethod;
        this.elementType = annotationMember.elementType;
        return this;
    }

    public String toString() {
        if (this.tag == '[') {
            StringBuilder stringBuilder = new StringBuilder(80);
            stringBuilder.append(this.name);
            stringBuilder.append("=[");
            int n = Array.getLength(this.value);
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Array.get(this.value, i));
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("=");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }

    public Object validateValue() throws Throwable {
        Object object;
        if (this.tag == '!') {
            this.rethrowError();
        }
        if ((object = this.value) == NO_VALUE) {
            return null;
        }
        if (this.elementType != object.getClass() && !this.elementType.isInstance(this.value)) {
            throw new AnnotationTypeMismatchException(this.definingMethod, this.value.getClass().getName());
        }
        return this.copyValue();
    }

    private static enum DefaultValues {
        NO_VALUE;
        
    }

}

