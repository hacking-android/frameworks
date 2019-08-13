/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.NoSuchPropertyException;
import android.util.Property;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectiveProperty<T, V>
extends Property<T, V> {
    private static final String PREFIX_GET = "get";
    private static final String PREFIX_IS = "is";
    private static final String PREFIX_SET = "set";
    private Field mField;
    private Method mGetter;
    private Method mSetter;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ReflectiveProperty(Class<T> serializable, Class<V> object, String object2) {
        super(object, (String)((Object)object2));
        char c = Character.toUpperCase(((String)((Object)object2)).charAt(0));
        CharSequence charSequence2 = ((String)((Object)object2)).substring(1);
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append(c);
        charSequence3.append((String)charSequence2);
        charSequence3 = charSequence3.toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(PREFIX_GET);
        ((StringBuilder)charSequence2).append((String)charSequence3);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        try {
            this.mGetter = ((Class)serializable).getMethod((String)charSequence2, null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(PREFIX_IS);
            ((StringBuilder)charSequence).append((String)charSequence3);
            charSequence = ((StringBuilder)charSequence).toString();
            try {
                this.mGetter = ((Class)serializable).getMethod((String)charSequence, null);
            }
            catch (NoSuchMethodException noSuchMethodException2) {
                try {
                    this.mField = ((Class)serializable).getField((String)((Object)object2));
                    serializable = this.mField.getType();
                    if (this.typesMatch((Class<V>)object, (Class)serializable)) {
                        return;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Underlying type (");
                    ((StringBuilder)charSequence).append(serializable);
                    ((StringBuilder)charSequence).append(") does not match Property type (");
                    ((StringBuilder)charSequence).append(object);
                    ((StringBuilder)charSequence).append(")");
                    NoSuchPropertyException noSuchPropertyException = new NoSuchPropertyException(((StringBuilder)charSequence).toString());
                    throw noSuchPropertyException;
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No accessor method or field found for property with name ");
                    stringBuilder.append((String)((Object)object2));
                    throw new NoSuchPropertyException(stringBuilder.toString());
                }
            }
        }
        object2 = this.mGetter.getReturnType();
        if (!this.typesMatch((Class<V>)object, object2)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Underlying type (");
            ((StringBuilder)serializable).append(object2);
            ((StringBuilder)serializable).append(") does not match Property type (");
            ((StringBuilder)serializable).append(object);
            ((StringBuilder)serializable).append(")");
            throw new NoSuchPropertyException(((StringBuilder)serializable).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(PREFIX_SET);
        ((StringBuilder)object).append((String)charSequence3);
        object = ((StringBuilder)object).toString();
        try {
            this.mSetter = ((Class)serializable).getMethod((String)object, object2);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    private boolean typesMatch(Class<V> class_, Class class_2) {
        block2 : {
            block3 : {
                boolean bl;
                block5 : {
                    boolean bl2;
                    block11 : {
                        block10 : {
                            block9 : {
                                block8 : {
                                    block7 : {
                                        block6 : {
                                            block4 : {
                                                bl2 = true;
                                                if (class_2 == class_) break block2;
                                                if (!class_2.isPrimitive()) break block3;
                                                if (class_2 != Float.TYPE) break block4;
                                                bl = bl2;
                                                if (class_ == Float.class) break block5;
                                            }
                                            if (class_2 != Integer.TYPE) break block6;
                                            bl = bl2;
                                            if (class_ == Integer.class) break block5;
                                        }
                                        if (class_2 != Boolean.TYPE) break block7;
                                        bl = bl2;
                                        if (class_ == Boolean.class) break block5;
                                    }
                                    if (class_2 != Long.TYPE) break block8;
                                    bl = bl2;
                                    if (class_ == Long.class) break block5;
                                }
                                if (class_2 != Double.TYPE) break block9;
                                bl = bl2;
                                if (class_ == Double.class) break block5;
                            }
                            if (class_2 != Short.TYPE) break block10;
                            bl = bl2;
                            if (class_ == Short.class) break block5;
                        }
                        if (class_2 != Byte.TYPE) break block11;
                        bl = bl2;
                        if (class_ == Byte.class) break block5;
                    }
                    bl = class_2 == Character.TYPE && class_ == Character.class ? bl2 : false;
                }
                return bl;
            }
            return false;
        }
        return true;
    }

    @Override
    public V get(T object) {
        AccessibleObject accessibleObject = this.mGetter;
        if (accessibleObject != null) {
            try {
                object = ((Method)accessibleObject).invoke(object, null);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException(invocationTargetException.getCause());
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError();
            }
            return (V)object;
        }
        accessibleObject = this.mField;
        if (accessibleObject != null) {
            try {
                object = ((Field)accessibleObject).get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError();
            }
            return (V)object;
        }
        throw new AssertionError();
    }

    @Override
    public boolean isReadOnly() {
        boolean bl = this.mSetter == null && this.mField == null;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void set(T object, V v) {
        AccessibleObject accessibleObject = this.mSetter;
        if (accessibleObject != null) {
            try {
                ((Method)accessibleObject).invoke(object, v);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException(invocationTargetException.getCause());
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError();
            }
        }
        accessibleObject = this.mField;
        if (accessibleObject != null) {
            try {
                ((Field)accessibleObject).set(object, v);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError();
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Property ");
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(" is read-only");
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }
}

